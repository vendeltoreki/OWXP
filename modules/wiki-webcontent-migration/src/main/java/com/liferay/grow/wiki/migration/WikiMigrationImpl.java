package com.liferay.grow.wiki.migration;

import org.osgi.service.component.annotations.Component;

@Component(
	service = WikiMigration.class
)
public class WikiMigrationImpl implements WikiMigration {

	@Override
	public void migrateWikis() {
		System.out.println("Starting Wiki migration");
	}

}
