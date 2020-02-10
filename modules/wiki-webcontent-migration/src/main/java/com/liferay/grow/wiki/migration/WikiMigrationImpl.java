package com.liferay.grow.wiki.migration;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.wiki.model.WikiPage;
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

	private Set<Long> resourcePrimKeys;
	private DDMTemplate growTemp;
	private DDMStructure growStruct;
	private List<WikiPage> pages;
	private List<FileEntry> pageAttachments = new LinkedList<FileEntry>();
	private List<FileEntry> contentImages = new LinkedList<FileEntry>();
}
