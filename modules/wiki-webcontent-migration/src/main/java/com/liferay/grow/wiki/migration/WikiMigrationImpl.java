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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.ratings.kernel.exception.NoSuchStatsException;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;
import com.liferay.wiki.escape.WikiEscapeUtil;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vendel Toreki
 * @author Laszlo Hudak
 */
@Component(service = WikiMigration.class)
public class WikiMigrationImpl implements WikiMigration {

	@Override
	public void migrateWikiPage(long wikiPageResourcePrimKey) throws Exception {
		if (_log.isInfoEnabled()) {
			_logInfo(
				"Starting single Wiki page migration: resourcePrimKey=" +
					wikiPageResourcePrimKey);
		}

		_init();

		_executeMigrationByResourcePrimKey(wikiPageResourcePrimKey);

		_postProcessChildPages();

		if (_log.isInfoEnabled()) {
			_logInfo("Wiki migration completed");
		}
	}

	@Override
	public void migrateWikis() throws Exception {
		if (_log.isInfoEnabled()) {
			_logInfo("Starting Wiki migration");
		}

		_init();

		_executeMigrationByResourcePrimKeys(null);

		_postProcessChildPages();

		if (_log.isInfoEnabled()) {
			_logInfo("Wiki migration completed");
		}
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
			if (_log.isWarnEnabled()) {
				_logWarn("ERROR in addArticle", e);
			}
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

	private String _createAbsoluteLinkFromTitle(String title, long nodeId) {
		title = StringUtil.replace(
			title, CharPool.NO_BREAK_SPACE, StringPool.SPACE);

		WikiPage wikiPage = WikiPageLocalServiceUtil.fetchPage(nodeId, title);

		if (wikiPage == null) {
			return title;
		}

		String category = "Share";

		try {
			category = _getWikiPageCategoryName(wikiPage);
		}
		catch (PortalException pe) {
			_logWarn("Error during retrieving wiki page category", pe);
		}

		category = category.toLowerCase();

		String escapedTitle = WikiEscapeUtil.escapeName(wikiPage.getTitle());

		escapedTitle = escapedTitle.replaceAll("<", "_");
		escapedTitle = escapedTitle.replaceAll(">", "_");
		escapedTitle = URLCodec.encodeURL(escapedTitle);

		return "https://grow.liferay.com/" + category + "/" + escapedTitle;
	}

	private AssetVocabulary _createGrowVocabulary(String vocabularyName)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(_groupId);

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				_defaultUserId, _groupId, vocabularyName, serviceContext);

		long journalClassNameId = ClassNameLocalServiceUtil.getClassNameId(
			JournalArticle.class);
		long growStructureId = _growStruct.getStructureId();

		UnicodeProperties settingsProperties =
			vocabulary.getSettingsProperties();

		settingsProperties.setProperty("multiValued", "false");
		settingsProperties.setProperty(
			"selectedClassNameIds", journalClassNameId + ":" + growStructureId);

		vocabulary.setSettings(settingsProperties.toString());

		AssetVocabularyLocalServiceUtil.updateAssetVocabulary(vocabulary);

		for (String categoryName :
				new String[] {"Share", "People", "Excellence", "Learn"}) {

			AssetCategoryLocalServiceUtil.addCategory(
				_defaultUserId, _groupId, categoryName,
				vocabulary.getVocabularyId(), serviceContext);
		}

		return vocabulary;
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
					if (_log.isDebugEnabled()) {
						_log.debug(
							"--Page found: version=" + page.getVersion());
					}

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

	private String _getAbsoluteLinkFromTitle(String title, long nodeId) {
		if (_titleLinkMap.containsKey(title)) {
			return _titleLinkMap.get(title);
		}
		else {
			String link = _createAbsoluteLinkFromTitle(title, nodeId);

			_titleLinkMap.put(title, link);

			return link;
		}
	}

	private String _getContentXml(WikiPage page) {
		try {
			String format = page.getFormat();

			String content = null;

			if (format.equalsIgnoreCase("markdown")) {
				format = "markdown";

				content = page.getContent();
			}
			else {
				format = "html";

				String originalContent = page.getContent();

				if (originalContent.contains("<<TableOfContents>>")) {
					originalContent = originalContent.replaceAll(
						"<<TableOfContents>>", "[TOC]");
				}

				originalContent = _replaceRelativeLinks(
					originalContent, page.getNodeId());

				page.setContent(originalContent);

				PortletURL viewPageUrl = null;

				WikiPageDisplay display =
					WikiPageLocalServiceUtil.getPageDisplay(
						page, viewPageUrl, null,
						"/documents/" + _groupId + "/" + _attachmentFolderId +
							"/");

				content = display.getFormattedContent();
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"FormattedContent=\n>>>>>>>>>>>>\n" + content +
						"\n<<<<<<<<<<<<<<<<");
			}

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
			if (_log.isWarnEnabled()) {
				_logWarn("ERROR in _getContentXml", e);
			}
		}

		return null;
	}

	private AssetVocabulary _getVocabularyByName(String vocabularyName) {
		AssetVocabulary growVocabulary = null;

		List<AssetVocabulary> vocs =
			AssetVocabularyLocalServiceUtil.getAssetVocabularies(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (AssetVocabulary voc : vocs) {
			String vocName = voc.getName();

			if (vocName.equals(vocabularyName)) {
				growVocabulary = voc;

				if (_log.isInfoEnabled()) {
					_logInfo(
						"-- Found Vocabulary: \"" + growVocabulary.getName() +
							"\"");
				}

				break;
			}
		}

		return growVocabulary;
	}

	private String _getWikiPageCategoryName(WikiPage wikiPage)
		throws PortalException {

		String parentTitle = wikiPage.getTitle();

		if (!parentTitle.equals("Learn") && !parentTitle.equals("Share") &&
			!parentTitle.equals("People") &&
			!parentTitle.equals("Excellence")) {

			parentTitle = wikiPage.getParentTitle();

			while (Validator.isNotNull(parentTitle) &&
				   !parentTitle.equals("Learn") &&
				   !parentTitle.equals("Share") &&
				   !parentTitle.equals("People") &&
				   !parentTitle.equals("Excellence")) {

				wikiPage = wikiPage.getParentPage();

				parentTitle = wikiPage.getParentTitle();
			}
		}

		if (!parentTitle.equals("Excellence") && !parentTitle.equals("Learn") &&
			!parentTitle.equals("Share") && !parentTitle.equals("People")) {

			parentTitle = "Share";
		}

		return parentTitle;
	}

	private void _handleAssetCategory(WikiPage wikiPage, JournalArticle article)
		throws PortalException {

		AssetEntry journalAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		String categoryName = _getWikiPageCategoryName(wikiPage);

		AssetCategoryLocalServiceUtil.addAssetEntryAssetCategory(
			journalAssetEntry.getEntryId(), _categoriesMap.get(categoryName));
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

			_attachmentFolderId = attachment.getFolderId();

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
			if (_log.isWarnEnabled()) {
				_logWarn("ERROR in _handleHeadVersion", e);
			}
		}

		_handleAssetCategory(page, article);

		_handleAssetTags(page, article);

		_handleRatings(page, article);
	}

	private void _handleRatings(WikiPage page, JournalArticle article)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(page.getGroupId());

		try {
			List<RatingsEntry> ratingsEntriesWiki =
				RatingsEntryLocalServiceUtil.getEntries(
					WikiPage.class.getName(), page.getResourcePrimKey());

			for (RatingsEntry ratingsEntry : ratingsEntriesWiki) {
				ratingsEntry.setClassName(JournalArticle.class.getName());
				ratingsEntry.setClassPK(article.getResourcePrimKey());

				RatingsEntryLocalServiceUtil.updateEntry(
					ratingsEntry.getUserId(), JournalArticle.class.getName(),
					article.getResourcePrimKey(), ratingsEntry.getScore(),
					serviceContext);
			}
		}
		catch (NoSuchStatsException nsse) {
			if (_log.isDebugEnabled()) {
				_log.debug("-- No likes for this page");
			}
		}
	}

	private void _init() throws Exception {
		if (_log.isInfoEnabled()) {
			_logInfo("Initializing");
		}

		_initGroup();

		List<DDMStructure> structs =
			DDMStructureLocalServiceUtil.getStructures();

		for (DDMStructure struct : structs) {
			String structName = struct.getNameCurrentValue();

			if (structName.equals("GROW Article")) {
				_growStruct = struct;

				if (_log.isInfoEnabled()) {
					_log.info(
						"-- Found structure: \"" +
							_growStruct.getNameCurrentValue() + "\"");
				}

				break;
			}
		}

		if (_growStruct == null) {
			throw new NoSuchStructureException();
		}

		List<DDMTemplate> growTemplates = _growStruct.getTemplates();

		if (!growTemplates.isEmpty()) {
			_growTemp = growTemplates.get(0);

			if (_log.isInfoEnabled()) {
				_logInfo(
					"-- Found template: \"" + _growTemp.getNameCurrentValue() +
						"\"");
			}
		}

		_initCategories();

		_keysMap.clear();
	}

	private void _initCategories() throws Exception {
		String vocabularyName = "GROW Category";

		AssetVocabulary growVocabulary = _getVocabularyByName(vocabularyName);

		if (growVocabulary == null) {
			growVocabulary = _createGrowVocabulary(vocabularyName);
		}

		List<AssetCategory> cats =
			AssetCategoryLocalServiceUtil.getVocabularyCategories(
				growVocabulary.getVocabularyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (AssetCategory cat : cats) {
			_categoriesMap.put(cat.getName(), cat.getCategoryId());
		}
	}

	private void _initGroup() throws Exception {
		List<Group> groups = GroupLocalServiceUtil.getGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			String groupName = group.getNameCurrentValue();

			if (groupName.equals("Guest")) {
				_groupId = group.getGroupId();

				if (_log.isInfoEnabled()) {
					_logInfo("-- groupId=" + _groupId);
				}

				_companyId = group.getCompanyId();

				if (_log.isInfoEnabled()) {
					_logInfo("-- companyId=" + _companyId);
				}

				_defaultUserId = UserLocalServiceUtil.getDefaultUserId(
					_companyId);

				if (_defaultUserId == 0) {
					_defaultUserId = group.getCreatorUserId();
				}

				if (_log.isInfoEnabled()) {
					_logInfo("-- defaultUserId=" + _defaultUserId);
				}

				return;
			}
		}
	}

	private void _logInfo(String message) {
		_log.info(message);

		System.out.println(message);
	}

	private void _logWarn(String message, Throwable throwable) {
		_log.warn(message);

		System.err.println(message);

		throwable.printStackTrace();
	}

	private void _postProcessChildPages() throws PortalException {
		for (Map.Entry<Long, Long> entry : _parentsMap.entrySet()) {
			long childResourcePrimKey = entry.getKey();
			long parentResourcePrimKey = entry.getValue();

			if (_keysMap.containsKey(childResourcePrimKey) &&
				_keysMap.containsKey(parentResourcePrimKey)) {

				long childArticleId = _keysMap.get(childResourcePrimKey);

				AssetEntry childAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						JournalArticle.class.getName(), childArticleId);

				long parentArticleId = _keysMap.get(parentResourcePrimKey);

				AssetEntry parentAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						JournalArticle.class.getName(), parentArticleId);

				AssetLinkLocalServiceUtil.addLink(
					parentAssetEntry.getUserId(), parentAssetEntry.getEntryId(),
					childAssetEntry.getEntryId(),
					AssetLinkConstants.TYPE_RELATED, 0);
			}
		}
	}

	private String _replaceRelativeLinks(String content, long nodeId) {
		StringBuffer sb = new StringBuffer();

		Matcher m = _creoleLinkPattern.matcher(content);

		while (m.find()) {
			String link = m.group(1);

			if (link.startsWith("http://") || link.startsWith("https://")) {
				m.appendReplacement(sb, "[[" + link + "]]");
			}
			else {
				String title = null;
				String linkText = null;

				if (link.contains("|")) {
					String[] linkElements = link.split("\\|");

					title = linkElements[0];
					linkText = linkElements[1];
				}
				else {
					title = link;
					linkText = link;
				}

				m.appendReplacement(
					sb,
					"[[" + _getAbsoluteLinkFromTitle(title, nodeId) + "|" +
						linkText + "]]");
			}
		}

		m.appendTail(sb);

		return sb.toString();
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

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Article updated: id=" + article.getId() + ", version=" +
						article.getVersion());
			}

			_updateTimestamp(article, page);

			if (page.isHead()) {
				_handleHeadVersion(page, article);
			}

			return article;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_logWarn("ERROR in updateArticle", e);
			}
		}

		return null;
	}

	private void _updateTimestamp(JournalArticle article, WikiPage page) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Updating timestamp: id=" + article.getId() + ", statusDate=" +
					page.getStatusDate());
		}

		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = DataAccess.getConnection();

			ps = connection.prepareStatement(
				"update JournalArticle set statusDate=? where id_ = ?");

			Date statusDate = page.getStatusDate();

			ps.setTimestamp(1, new Timestamp(statusDate.getTime()));

			ps.setLong(2, article.getId());

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("ERROR in updateTimestamp", e);
			}
		}
		finally {
			if (ps != null) {
				try {
					ps.close();
				}
				catch (Throwable t) {
					/* Ignore */
				}
			}

			if (connection != null) {
				try {
					connection.close();
				}
				catch (Throwable t) {
					/* Ignore */
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiMigrationImpl.class);

	private long _attachmentFolderId;
	private Map<String, Long> _categoriesMap = new HashMap<>();
	private long _companyId;
	private final Pattern _creoleLinkPattern = Pattern.compile(
		"\\[\\[(.+?)\\]\\]");
	private long _defaultUserId;
	private long _groupId;
	private DDMStructure _growStruct;
	private DDMTemplate _growTemp;
	private Map<Long, Long> _keysMap = new HashMap<>();
	private Map<Long, Long> _parentsMap = new HashMap<>();
	private Map<String, String> _titleLinkMap = new HashMap<>();

}