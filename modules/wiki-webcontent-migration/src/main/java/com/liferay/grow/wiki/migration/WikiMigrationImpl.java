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
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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

	public JournalArticle convert(WikiPage page) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(page.getGroupId());

		List<FileEntry> attachments = page.getAttachmentsFileEntries();

		System.out.println("attachments=" + attachments.size());

		for (FileEntry attachment : attachments) {
			System.out.println("attachment=" + attachment.getFileName());
		}

		String content = getContentXml(page);

		Locale locale = LocaleUtil.fromLanguageId("en_US");

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(locale, page.getTitle());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, page.getSummary());

		JournalArticle article = JournalArticleLocalServiceUtil.addArticle(
			page.getUserId(), page.getGroupId(), 0, titleMap, descriptionMap,
			content, _growStruct.getStructureKey(), _growTemp.getTemplateKey(),
			serviceContext);

		/*long id = CounterLocalServiceUtil.increment();

		ps.setString(1, getContentXml(page));
		ps.setLong(2, id);
		ps.executeUpdate();*/

		// Get childpages and run the main method for them as well

		List<WikiPage> childPages = page.getChildPages();
		List<JournalArticle> childArticles = new LinkedList<>();

		for (WikiPage childPage : childPages) {
			childArticles.add(convert(childPage));
		}

		// Create asset links

		_handleChildPages(article, childArticles);

		return article;
	}

	public String getContentXml(WikiPage page) {
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
				rootElement, "format", "list", "keyword", "bgea", "en_US",
				format);
			_addElement(
				rootElement, "content", "text_area", "text", "clfy", "en_US",
				content);

			_handleAttachments(rootElement, page.getAttachmentsFileEntries());

			return XMLUtil.formatXML(document.asXML());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void migrateWikis() {
		System.out.println("Starting Wiki migration");

		_init();

		for (WikiPage page : _pages) {
			if (Validator.isNotNull(page.getParentTitle())) {
				continue;
			}

			try {
				if (_resourcePrimKeys.contains(page.getResourcePrimKey())) {
					convert(page);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
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

	private void _handleAttachments(
		Element rootElement, List<FileEntry> attachments) {

		for(FileEntry attachment: attachments) {
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
				"trnm", "en_US", sb.toString());
		}
	}

	private void _handleChildPages(
			JournalArticle article, List<JournalArticle> childArticles)
		throws PortalException {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		for (JournalArticle childArticle : childArticles) {
			AssetEntry childAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				JournalArticle.class.getName(),
				childArticle.getResourcePrimKey());

			AssetLinkLocalServiceUtil.addLink(
				assetEntry.getUserId(), assetEntry.getEntryId(),
				childAssetEntry.getEntryId(), AssetLinkConstants.TYPE_RELATED,
				0);
		}
	}

	private void _init() {
		Collections.addAll(_resourcePrimKeys, 1499375L);

		List<DDMStructure> structs =
			DDMStructureLocalServiceUtil.getStructures();

		for (DDMStructure struct : structs) {
			String structName = struct.getNameCurrentValue();

			if (structName.contentEquals("GROW Article")) {
				_growStruct = struct;

				break;
			}
		}

		List<DDMTemplate> growTemplates = _growStruct.getTemplates();

		_growTemp = growTemplates.get(0);

		System.out.println(
			"-- Found structure: \"" + _growStruct.getNameCurrentValue() +
				"\"");
		System.out.println(
			"-- Found template: \"" + _growTemp.getNameCurrentValue() + "\"");

		_pages = WikiPageLocalServiceUtil.getPages("creole");

		System.out.println("n=" + _pages.size());
	}

	private DDMStructure _growStruct;
	private DDMTemplate _growTemp;
	private List<WikiPage> _pages;
	private Set<Long> _resourcePrimKeys;

}