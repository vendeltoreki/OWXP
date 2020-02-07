package com.liferay.grow.wiki.migration;

import com.liferay.grow.wiki.migration.WikiMigration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Liferay
 */

public interface WikiMigration {

	public void migrateWikis();
}