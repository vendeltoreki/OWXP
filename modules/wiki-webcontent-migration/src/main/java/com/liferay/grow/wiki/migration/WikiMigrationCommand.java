package com.liferay.grow.wiki.migration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	property = {
		"osgi.command.function=executeMigration",
		"osgi.command.scope=grow"
	},
	service = WikiMigrationCommand.class
)
public class WikiMigrationCommand {

	public void executeMigration() {
		wikiMigration.migrateWikis();
	}

	@Reference
	private WikiMigration wikiMigration;
}
