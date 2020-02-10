package com.liferay.grow.wiki.migration;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

@Component(
	service = WikiMigration.class
)
public class WikiMigrationImpl implements WikiMigration {

	@Override
	public void migrateWikis() {
		System.out.println("Starting Wiki migration");

		init();
	}

	private void init() {
		resourcePrimKeys.addAll(Arrays.asList(
			//1489681L
			1499375L
		));

		List<DDMStructure> structs = DDMStructureLocalServiceUtil.getStructures();
		for (DDMStructure struct : structs) {
			if (struct.getNameCurrentValue().contentEquals("GROW Article")) {
				growStruct = struct;
				break;
			}
		}

		growTemp = growStruct.getTemplates().get(0);

		System.out.println("-- Found structure: \""+growStruct.getNameCurrentValue()+"\"");
		System.out.println("-- Found template: \""+growTemp.getNameCurrentValue()+"\"");

		pages = WikiPageLocalServiceUtil.getPages("creole");
		System.out.println("n="+pages.size());
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

			System.out.println("FormattedContent=\n>>>>>>>>>>>>\n"+content+"\n<<<<<<<<<<<<<<<<");

			Document document = SAXReaderUtil.createDocument();

			Element rootElement = document.addElement("root");

			rootElement.addAttribute("available-locales", "en_US");
			rootElement.addAttribute("default-locale", "en_US");

			addElement(rootElement, "format", "list", "keyword", "bgea", "en_US", format);
			addElement(rootElement, "content", "text_area", "text", "clfy", "en_US", content);
			addElement(rootElement, "ParentArticle", "ddm-journal-article", "keyword", "dswq", "en_US", "");

			return XMLUtil.formatXML(document.asXML());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void addElement(Element rootElement, String name, String type, 
		String indexType, String instanceId, String languageId, 
		String content) {
		
		Element dynamicElementElement = rootElement.addElement("dynamic-element");
		dynamicElementElement.addAttribute("name", name);
		dynamicElementElement.addAttribute("type", type);
		dynamicElementElement.addAttribute("index-type", indexType);
		dynamicElementElement.addAttribute("instance-id", instanceId);

		Element dynamicContentElement = dynamicElementElement.addElement("dynamic-content");
		dynamicContentElement.addAttribute("language-id", languageId);
		dynamicContentElement.addCDATA(content);		
	}

	private void handleChildPages(
		JournalArticle article, List<JournalArticle> childArticles) 
			throws PortalException {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		for (JournalArticle childArticle: childArticles) {
			AssetEntry childAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				JournalArticle.class.getName(),
				childArticle.getResourcePrimKey());

			AssetLinkLocalServiceUtil.addLink(assetEntry.getUserId(),
				assetEntry.getEntryId(), childAssetEntry.getEntryId(),
				AssetLinkConstants.TYPE_RELATED, 0);
		}
	}

	private Set<Long> resourcePrimKeys;
	private DDMTemplate growTemp;
	private DDMStructure growStruct;
	private List<WikiPage> pages;
	private List<FileEntry> pageAttachments = new LinkedList<FileEntry>();
	private List<FileEntry> contentImages = new LinkedList<FileEntry>();
}
