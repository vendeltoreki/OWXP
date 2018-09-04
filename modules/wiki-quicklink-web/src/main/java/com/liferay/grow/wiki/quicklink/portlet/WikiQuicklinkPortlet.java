package com.liferay.grow.wiki.quicklink.portlet;

import com.liferay.grow.wiki.quicklink.constants.WikiQuicklinkPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.LinkedList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"javax.portlet.display-name=Wiki Quicklink Portlet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + WikiQuicklinkPortletKeys.WIKI_QUICKLINK,
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class WikiQuicklinkPortlet extends MVCPortlet {
	@Override
	public void serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				resourceRequest);

			JSONArray jsonArray = getJSONArray(request);

			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				resourceResponse);

			response.setContentType(ContentTypes.APPLICATION_JSON);

			ServletResponseUtil.write(response, jsonArray.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected JSONArray getJSONArray(HttpServletRequest request)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String query = ParamUtil.getString(request, "query");

		List<WikiPage> pages = getPages(query, request);

		for (WikiPage page : pages) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("title", page.getTitle());

			String quicklink = 
				themeDisplay.getPortalURL() + "/share/" +
					getNormalizedTitle(page.getTitle());

			if (true) {
				quicklink =
					"<a href=\"" + quicklink + "\">" + page.getTitle() +
						"</a>";
			}

			jsonObject.put("quicklink", quicklink);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public List<WikiPage> getPages(String keywords, HttpServletRequest request)
		throws PortalException {

		keywords = StringUtil.replace(keywords, CharPool.PLUS, CharPool.SPACE);

		Indexer<WikiPage> indexer = IndexerRegistryUtil.getIndexer(
			WikiPage.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			request);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setAttribute("title", keywords);

		Hits hits = indexer.search(searchContext);

		List<SearchResult> searchResults =
			SearchResultUtil.getSearchResults(
				hits, themeDisplay.getLocale());

		List<WikiPage> results = new LinkedList<>();

		for (SearchResult searchResult : searchResults) {
			WikiPage wikiPage = _wikiPageLocalService.getPage(
				searchResult.getClassPK());

			results.add(wikiPage);
		}
		
		return results;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(WikiPageLocalService wikiPageLocalService) {
		_wikiPageLocalService = wikiPageLocalService;
	}

	private String getNormalizedTitle(String title) {
		String normalized = StringUtil.toLowerCase(title);

		return StringUtil.replace(normalized, REPLACE, REPLACE_WITH);
	}

	private static final char[] REPLACE = {
		'á', 'é', 'í', 'ú', 'ü', '\u0171', 'ó', 'ö', '\u0151', '&', '\'', '@', ']', ')',
		':', ',', '$', '=', '!', '[', '(', '#', '?', ';', '/', '*', '+', ' ', '\u00a0'
	};

	private static final String[] REPLACE_WITH = {
		"a", "e", "i", "u", "u", "u", "o", "o", "o", "<AMPERSAND>",
		"<APOSTROPHE>","<AT>", "<CLOSE_BRACKET>", "<CLOSE_PARENTHESIS>",
		"<COLON>", "<COMMA>","<DOLLAR>", "<EQUAL>", "<EXCLAMATION>",
		"<OPEN_BRACKET>", "<OPEN_PARENTHESIS>", "<POUND>", "<QUESTION>",
		"<SEMICOLON>","<SLASH>", "<STAR>","<PLUS>","+","%c2%a0"
	};

	private WikiPageLocalService _wikiPageLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		WikiQuicklinkPortlet.class);
}