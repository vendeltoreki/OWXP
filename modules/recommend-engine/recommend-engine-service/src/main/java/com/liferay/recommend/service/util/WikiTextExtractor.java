package com.liferay.recommend.service.util;

public class WikiTextExtractor {
	private String titleSeparator = "*";

	public String getTitleSeparator() {
		return titleSeparator;
	}

	public void setTitleSeparator(String titleSeparator) {
		this.titleSeparator = titleSeparator;
	}

	public String truncateWikiContent(String content, String format, int size) {
		content = removeSpecialChars(content);
		
		if (format.equals("creole")) {
			content = processCreoleContent(content);
		} else if (format.equals("markdown")) {
			content = processMarkdownContent(content);
		} else if (format.equals("html")) {
			content = processHtmlContent(content);
		}
		
		content = finalFormat(content);
		
		if (content.length() > size) {
			return content.substring(0, size);
		} else {
			return content;
		}
	}

	private String processCreoleContent(String content) {
		content = content.replaceAll("<<TableOfContents>>", "");
		content = replaceCreoleLinks(content);
		content = replaceCreoleImages(content);
		content = replaceCreoleTitles(content);
		content = content.replaceAll("~/", "/");
		content = content.replaceAll("~\\]", "]");
		content = content.replaceAll("~\\[", "[");
		content = content.replaceAll("~\\\\", "\\\\");
		content = content.replaceAll("\\n{2,}", " * ");
		content = content.replaceAll("\\{{3}\\s*", "\"");
		content = content.replaceAll("\\s*\\}{3}", "\"");
		content = collapseSpaces(content).trim();
		content = removeSpecialChars(content);
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

		content = removeDuplicateAsterisks(content);
		
		content = collapseSpaces(content).trim();
		return content;
	}

	private String processMarkdownContent(String content) {
		content = content.replaceAll("<iframe .+?</iframe>","");
		content = content.replaceAll("<br.*?>","");
		content = replaceCreoleLinks(content);
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
		
		content = collapseSpaces(content).trim();
		content = removeDuplicateAsterisks(content);
		return content;
	}
	
	private String processHtmlContent(String content) {
		content = collapseSpaces(content);
		content = content.replaceAll("<style.+?<\\/style>", "");
		
		// add asterisks
		content = content.replaceAll("(<td.*?>)", " * $1");
		content = content.replaceAll("(<th.*?>)", " * $1");
		content = content.replaceAll("(<p>)", " * $1");
		content = content.replaceAll("(<p .+?>)", " * $1");
		content = content.replaceAll("(<li>)", " * $1");
		content = content.replaceAll("(<li .+?>)", " * $1");

		content = resolveHtmlSymbols(content);
		
		content = removeHtmlTags(content);
		content = removeDuplicateAsterisks(content);
		content = collapseSpaces(content).trim();
		return content;
	}

	private String resolveHtmlSymbols(String content) {
		content = content.replaceAll("&quot;", "\"");
		content = content.replaceAll("&amp;", "&");
		content = content.replaceAll("&gt;", ">");
		content = content.replaceAll("&lt;", "<");
		return content;
	}

	private String removeHtmlTags(String content) {
		content = content.replaceAll("<.+?>", " ");
		return content;
	}

	private String removeDuplicateAsterisks(String text) {
		String prev = text;
		int n = 0;
		do {
			prev = text;
			text = text.replaceAll(" \\* +\\* ", " * ");
			++n;
		} while (!prev.equals(text) && n<10);
		return text;
	}

	private String removeSpecialChars(String text) {
		text = text.replaceAll("[\\^\u00a0\u25c4\u2190]"," ");

		return text;
	}

	private String replaceCreoleTitles(String text) {
		text = text.replaceAll("\\=\\=\\= *(.+?) *\\=\\=\\=", " * $1: ");
		text = text.replaceAll("\\=\\= *(.+?) *\\=\\=", " * $1: ");
		text = text.replaceAll("\\= *(.+?) *\\=", " * $1: ");
		return text;
	}

	private String collapseSpaces(String text) {
		text = text.replaceAll("\\s+", " ");
		return text;
	}

	private String replaceCreoleImages(String text) {
		text = text.replaceAll("\\{\\{(.+?)\\}\\}", "");
		return text;
	}

	private String replaceCreoleLinks(String text) {
		text = text.replaceAll("\\[\\[(.+?)\\|(.+?)\\]\\]", "$2");
		text = text.replaceAll("\\[\\[https://.+/(.+?)\\]\\]", "$1");
		text = text.replaceAll("\\[\\[http://.+/(.+?)\\]\\]", "$1");
		text = text.replaceAll("\\[\\[(.+?)\\]\\]", "$1");

		text = text.replaceAll("\\[(.+?)\\]\\(.+?\\)", "$1");
		
		return text;
	}
	
	private String finalFormat(String text) {
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

		if (!titleSeparator.equals("*")) {
			text = text.replaceAll("\\*", titleSeparator);
		}

		return text;
	}	

}
