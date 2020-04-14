/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.grow.wiki.migration;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vendel Toreki
 * @author Laszlo Hudak
 */
@Component(service = WikiMigration.class)
public class WikiMigrationImpl implements WikiMigration {

	@Override
	public void migrateWikiPage(long wikiPageResourcePrimKey) throws Exception {
		System.out.println(
			"Starting single Wiki page migration: " + wikiPageResourcePrimKey);

		_init();

		_executeMigrationByResourcePrimKey(wikiPageResourcePrimKey);

		_postProcessChildPages();
	}

	@Override
	public void migrateWikis() throws Exception {
		System.out.println("Starting Wiki migration");

		_init();

		_executeMigrationByResourcePrimKeys(null);

		_postProcessChildPages();
	}

	private JournalArticle _addArticle(
		WikiPage page, String structKey, String tempKey) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(page.getGroupId());

		String content = _getContentXml(page);

		Locale locale = LocaleUtil.fromLanguageId("en_US");

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(locale, page.getTitle());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, page.getSummary());

		try {
			JournalArticle article = JournalArticleLocalServiceUtil.addArticle(
				page.getUserId(), page.getGroupId(), 0, titleMap,
				descriptionMap, content, structKey, tempKey, serviceContext);

			_updateTimestamp(article, page);

			if (page.isHead()) {
				_handleHeadVersion(page, article);
			}

			return article;
		}
		catch (Exception e) {
			System.err.println("ERROR in addArticle");
			e.printStackTrace();
		}

		return null;
	}

	private void _addElement(
		Element rootElement, String name, String type, String indexType,
		String instanceId, String languageId, String content) {

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("name", name);
		dynamicElementElement.addAttribute("type", type);
		dynamicElementElement.addAttribute("index-type", indexType);
		dynamicElementElement.addAttribute("instance-id", instanceId);

		Element dynamicContentElement = dynamicElementElement.addElement(
			"dynamic-content");

		dynamicContentElement.addAttribute("language-id", languageId);
		dynamicContentElement.addCDATA(content);
	}

	private void _executeMigrationByResourcePrimKey(long resourcePrimKey)
		throws Exception {

		Set<Long> resourcePrimKeys = new HashSet<>();

		resourcePrimKeys.add(resourcePrimKey);

		_executeMigrationByResourcePrimKeys(resourcePrimKeys);
	}

	private void _executeMigrationByResourcePrimKeys(Set<Long> resourcePrimKeys)
		throws Exception {

		ActionableDynamicQuery adq =
			WikiPageLocalServiceUtil.getActionableDynamicQuery();

		if ((resourcePrimKeys != null) && !resourcePrimKeys.isEmpty()) {
			adq.setAddCriteriaMethod(
				new ActionableDynamicQuery.AddCriteriaMethod() {

					@Override
					public void addCriteria(DynamicQuery dynamicQuery) {
						dynamicQuery.add(
							RestrictionsFactoryUtil.in(
								"resourcePrimKey", resourcePrimKeys));
					}

				});
		}

		adq.setAddOrderCriteriaMethod(
			new ActionableDynamicQuery.AddOrderCriteriaMethod() {

				@Override
				public void addOrderCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.addOrder(OrderFactoryUtil.asc("statusDate"));
				}

			});

		adq.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<WikiPage>() {

				@Override
				public void performAction(WikiPage page) {
					System.out.println(
						"--Page found: version=" + page.getVersion());

					if (!_keysMap.containsKey(page.getResourcePrimKey())) {
						JournalArticle article = _addArticle(
							page, _growStruct.getStructureKey(),
							_growTemp.getTemplateKey());

						if (article != null) {
							_keysMap.put(
								page.getResourcePrimKey(),
								article.getResourcePrimKey());
						}
					}
					else {
						_updateArticle(
							_keysMap.get(page.getResourcePrimKey()), page);
					}
				}

			});

		adq.performActions();
	}

	private String _getContentXml(WikiPage page) {
		try {
			String format = page.getFormat();

			if (!format.equalsIgnoreCase("markdown")) {
				format = "html";
			}

			WikiPageDisplay display = WikiPageLocalServiceUtil.getPageDisplay(
				page, null, null, "");

			String content = display.getFormattedContent();

			System.out.println(
				"FormattedContent=\n>>>>>>>>>>>>\n" + content +
					"\n<<<<<<<<<<<<<<<<");

			Document document = SAXReaderUtil.createDocument();

			Element rootElement = document.addElement("root");

			rootElement.addAttribute("available-locales", "en_US");
			rootElement.addAttribute("default-locale", "en_US");

			_addElement(
				rootElement, "format", "list", "keyword", StringUtil.randomId(),
				"en_US", format);
			_addElement(
				rootElement, "content", "text_area", "text",
				StringUtil.randomId(), "en_US", content);

			_handleAttachments(rootElement, page.getAttachmentsFileEntries());

			return XMLUtil.formatXML(document.asXML());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void _handleAssetTags(WikiPage page, JournalArticle article)
		throws PortalException {

		AssetEntry wikiAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		List<AssetTag> tags = wikiAssetEntry.getTags();

		AssetEntry journalAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		AssetTagLocalServiceUtil.addAssetEntryAssetTags(
			journalAssetEntry.getEntryId(), tags);
	}

	private void _handleAttachments(
		Element rootElement, List<FileEntry> attachments) {

		for (FileEntry attachment : attachments) {
			StringBundler sb = new StringBundler();

			sb.append("{\"classPK\":");
			sb.append(attachment.getFileEntryId());
			sb.append(",\"groupId\":");
			sb.append(attachment.getGroupId());
			sb.append(",\"title\":\"");
			sb.append(attachment.getTitle());
			sb.append("\",\"type\":\"document\",\"uuid\":\"");
			sb.append(attachment.getUuid());
			sb.append("\"}");

			_addElement(
				rootElement, "attachments", "document_library", "keyword",
				StringUtil.randomId(), "en_US", sb.toString());
		}
	}

	private void _handleHeadVersion(WikiPage page, JournalArticle article)
		throws PortalException {

		try {
			WikiPage parentPage = page.getParentPage();

			if (parentPage != null) {
				long parentResourcePrimKey = parentPage.getResourcePrimKey();

				_parentsMap.put(
					page.getResourcePrimKey(), parentResourcePrimKey);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		_handleAssetTags(page, article);
	}

	private void _init() throws Exception {
		System.out.println("Initializing.");

		Collections.addAll(_resourcePrimKeys, 1499375L);

		List<DDMStructure> structs =
			DDMStructureLocalServiceUtil.getStructures();

		for (DDMStructure struct : structs) {
			String structName = struct.getNameCurrentValue();

			if (structName.contentEquals("GROW Article")) {
				_growStruct = struct;

				System.out.println(
					"-- Found structure: \"" +
						_growStruct.getNameCurrentValue() + "\"");

				break;
			}
		}

		if (Validator.isNull(_growStruct)) {
			throw new NoSuchStructureException();
		}

		List<DDMTemplate> growTemplates = _growStruct.getTemplates();

		if (!growTemplates.isEmpty()) {
			_growTemp = growTemplates.get(0);

			System.out.println(
				"-- Found template: \"" + _growTemp.getNameCurrentValue() +
					"\"");
		}

		_keysMap.clear();
	}

	private void _postProcessChildPages() throws PortalException {
		for (Map.Entry<Long, Long> entry : _parentsMap.entrySet()) {
			long childResourcePrimKey = entry.getKey();

			long childArticleId = _keysMap.get(childResourcePrimKey);

			AssetEntry childAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				JournalArticle.class.getName(), childArticleId);

			long parentResourcePrimKey = entry.getValue();

			long parentArticleId = _keysMap.get(parentResourcePrimKey);

			AssetEntry parentAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				JournalArticle.class.getName(), parentArticleId);

			AssetLinkLocalServiceUtil.addLink(
				parentAssetEntry.getUserId(), parentAssetEntry.getEntryId(),
				childAssetEntry.getEntryId(), AssetLinkConstants.TYPE_RELATED,
				0);
		}
	}

	private JournalArticle _updateArticle(
		long articleResourcePrimKey, WikiPage page) {

		try {
			JournalArticle article =
				JournalArticleLocalServiceUtil.getLatestArticle(
					articleResourcePrimKey);

			String content = _getContentXml(page);

			article.setContent(content);

			Locale locale = LocaleUtil.fromLanguageId("en_US");

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(page.getGroupId());

			Map<Locale, String> titleMap = article.getTitleMap();
			Map<Locale, String> descriptionMap = article.getDescriptionMap();

			titleMap.put(locale, page.getTitle());
			descriptionMap.put(locale, page.getSummary());

			article = JournalArticleLocalServiceUtil.updateArticle(
				page.getUserId(), article.getGroupId(), article.getFolderId(),
				article.getArticleId(), article.getVersion(), titleMap,
				descriptionMap, content, null, serviceContext);

			System.out.println(
				"Article updated: id=" + article.getId() + ", version=" +
					article.getVersion());

			_updateTimestamp(article, page);

			if (page.isHead()) {
				_handleHeadVersion(page, article);
			}

			return article;
		}
		catch (Exception e) {
			System.err.println("ERROR in updateArticle");
			e.printStackTrace();
		}

		return null;
	}

	private void _updateTimestamp(JournalArticle article, WikiPage page) {
		System.out.println(
			"Updating timestamp: id=" + article.getId() + ", statusDate=" +
				page.getStatusDate());

		try {
			Connection connection = DataAccess.getConnection();

			PreparedStatement ps = connection.prepareStatement(
				"update JournalArticle set statusDate=? where id_ = ?");

			Date statusDate = page.getStatusDate();

			ps.setTimestamp(1, new Timestamp(statusDate.getTime()));

			ps.setLong(2, article.getId());

			ps.executeUpdate();
		}
		catch (Exception e) {
			System.err.println("ERROR in updateTimestamp");
			e.printStackTrace();
		}
	}

	private DDMStructure _growStruct;
	private DDMTemplate _growTemp;
	private Map<Long, Long> _keysMap = new HashMap<>();
	private Map<Long, Long> _parentsMap = new HashMap<>();
	private Set<Long> _resourcePrimKeys = new HashSet<>();

}