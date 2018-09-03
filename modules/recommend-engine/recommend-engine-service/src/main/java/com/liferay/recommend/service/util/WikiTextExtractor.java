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

package com.liferay.recommend.service.util;

public class WikiTextExtractor {

	public String getTitleSeparator() {
		return _titleSeparator;
	}

	public void setTitleSeparator(String titleSeparator) {
		_titleSeparator = titleSeparator;
	}

	public String truncateWikiContent(String content, String format, int size) {
		content = _removeSpecialChars(content);

		if (format.equals("creole")) {
			content = _processCreoleContent(content);
		}
		else if (format.equals("markdown")) {
			content = _processMarkdownContent(content);
		}
		else if (format.equals("html")) {
			content = _processHtmlContent(content);
		}

		content = _finalFormat(content);

		if (content.length() > size) {
			return content.substring(0, size);
		}
		else {
			return content;
		}
	}

	private String _collapseSpaces(String text) {
		text = text.replaceAll("\\s+", " ");

		return text;
	}

	private String _finalFormat(String text) {
		text = text.replaceAll("\\: \\*", ":");

		text = text.replaceAll("\\. \\*", ".");

		text = text.replaceAll("\\, \\*", " *");

		text = text.replaceAll("\\? \\*", "?");

		text = text.replaceAll("\\! \\*", "!");

		text = text.replaceAll("\\] \\*", "]");

		text = text.replaceAll(" : ", ": ");

		text = text.replaceAll(" \\.", ".");

		text = text.replaceAll(" \\,", ",");

		text = text.replaceAll(" \\?", "?");

		text = text.replaceAll(" \\!", "!");

		text = text.replaceAll(" \\)", ")");

		text = text.replaceAll("\\( ", "(");

		if (text.startsWith("* ")) {
			text = text.substring(2);
		}

		text = text.replaceAll("^Description \\* ", "");

		text = text.replaceAll("^Overview \\* ", "");

		text = text.replaceAll("^Back to (.+?) page \\* ", "");

		if (!_titleSeparator.equals("*")) {
			text = text.replaceAll("\\*", _titleSeparator);
		}

		return text;
	}

	private String _processCreoleContent(String content) {
		content = content.replaceAll("<<TableOfContents>>", "");

		content = _replaceCreoleLinks(content);

		content = _replaceCreoleImages(content);

		content = _replaceCreoleTitles(content);

		content = content.replaceAll("~/", "/");

		content = content.replaceAll("~\\]", "]");

		content = content.replaceAll("~\\[", "[");

		content = content.replaceAll("~\\\\", "\\\\");

		content = content.replaceAll("\\n{2,}", " * ");

		content = content.replaceAll("\\{{3}\\s*", "\"");

		content = content.replaceAll("\\s*\\}{3}", "\"");

		content = _collapseSpaces(content).trim();

		content = _removeSpecialChars(content);

		content = content.replaceAll("\\/\\/", "");

		content = content.replaceAll("\\-{3,}", "");

		content = content.replaceAll("\\~{2,}", "");

		content = content.replaceAll("\\*{2,}", "");

		content = content.replaceAll("\\?{2,}", "");

		content = content.replaceAll("\\:{2,}", "");

		content = content.replaceAll("\\\\{2,}", "");

		content = content.replaceAll("\\: \\*", ": ");

		content = content.replaceAll("\\#", " * ");

		content = content.replaceAll("\\|", " * ");

		content = content.replaceAll("\\*", " * ");

		content = _removeDuplicateAsterisks(content);

		content = _collapseSpaces(content).trim();

		return content;
	}

	private String _processHtmlContent(String content) {
		content = _collapseSpaces(content);

		content = content.replaceAll("<style.+?<\\/style>", "");

		// add asterisks

		content = content.replaceAll("(<td.*?>)", " * $1");

		content = content.replaceAll("(<th.*?>)", " * $1");

		content = content.replaceAll("(<p>)", " * $1");

		content = content.replaceAll("(<p .+?>)", " * $1");

		content = content.replaceAll("(<li>)", " * $1");

		content = content.replaceAll("(<li .+?>)", " * $1");

		content = _resolveHtmlSymbols(content);

		content = _removeHtmlTags(content);

		content = _removeDuplicateAsterisks(content);

		content = _collapseSpaces(content).trim();

		return content;
	}

	private String _processMarkdownContent(String content) {
		content = content.replaceAll("<iframe .+?</iframe>", "");

		content = content.replaceAll("<br.*?>", "");

		content = _replaceCreoleLinks(content);

		//content = replaceCreoleImages(content);
		//content = replaceCreoleTitles(content);

		content = content.replaceAll("\\-{3,}", "*");

		content = content.replaceAll("\\*{2,}", "");

		content = content.replaceAll("\\?{2,}", "");

		content = content.replaceAll("\\:{2,}", "");

		content = content.replaceAll("\\\\{2,}", "");

		content = content.replaceAll("```", "");

		content = content.replaceAll("`", "");

		content = content.replaceAll("\\: \\*", ": ");

		content = content.replaceAll("\\#", " * ");

		content = content.replaceAll("\\*", " * ");

		content = _collapseSpaces(content).trim();

		content = _removeDuplicateAsterisks(content);

		return content;
	}

	private String _removeDuplicateAsterisks(String text) {
		String prev = text;

		int n = 0;

		do {
			prev = text;

			text = text.replaceAll(" \\* +\\* ", " * ");

			++n;
		} while (!prev.equals(text) && n < 10);

		return text;
	}

	private String _removeHtmlTags(String content) {
		content = content.replaceAll("<.+?>", " ");

		return content;
	}

	private String _removeSpecialChars(String text) {
		text = text.replaceAll("[\\^\u00a0\u25c4\u2190]", " ");

		return text;
	}

	private String _replaceCreoleImages(String text) {
		text = text.replaceAll("\\{\\{(.+?)\\}\\}", "");

		return text;
	}

	private String _replaceCreoleLinks(String text) {
		text = text.replaceAll("\\[\\[(.+?)\\|(.+?)\\]\\]", "$2");

		text = text.replaceAll("\\[\\[https://.+/(.+?)\\]\\]", "$1");

		text = text.replaceAll("\\[\\[http://.+/(.+?)\\]\\]", "$1");

		text = text.replaceAll("\\[\\[(.+?)\\]\\]", "$1");

		text = text.replaceAll("\\[(.+?)\\]\\(.+?\\)", "$1");

		return text;
	}

	private String _replaceCreoleTitles(String text) {
		text = text.replaceAll("\\=\\=\\= *(.+?) *\\=\\=\\=", " * $1: ");

		text = text.replaceAll("\\=\\= *(.+?) *\\=\\=", " * $1: ");

		text = text.replaceAll("\\= *(.+?) *\\=", " * $1: ");

		return text;
	}

	private String _resolveHtmlSymbols(String content) {
		content = content.replaceAll("&quot;", "\"");

		content = content.replaceAll("&amp;", "&");

		content = content.replaceAll("&gt;", ">");

		content = content.replaceAll("&lt;", "<");

		return content;
	}

	private String _titleSeparator = "*";

}