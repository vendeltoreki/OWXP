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

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vendel Toreki
 * @author Laszlo Hudak
 */
@Component(
	property = {
		"osgi.command.function=executeMigration", "osgi.command.scope=grow"
	},
	service = WikiMigrationCommand.class
)
public class WikiMigrationCommand {

	public void executeMigration() {
		try {
			_wikiMigration.migrateWikis();
		}
		catch (Exception e) {
			if (e instanceof NoSuchStructureException) {
				System.out.println("No GROW structure found");
			}

			e.printStackTrace();
		}
	}

	public void executeMigration(long wikiPageResourcePrimKey) {
		try {
			_wikiMigration.migrateWikiPage(wikiPageResourcePrimKey);
		}
		catch (Exception e) {
			if (e instanceof NoSuchStructureException) {
				System.out.println("No GROW structure found");
			}

			e.printStackTrace();
		}
	}

	@Reference
	private WikiMigration _wikiMigration;

}