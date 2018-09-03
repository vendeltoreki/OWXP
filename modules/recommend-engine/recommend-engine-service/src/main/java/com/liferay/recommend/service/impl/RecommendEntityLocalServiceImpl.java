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

package com.liferay.recommend.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.recommend.service.base.RecommendEntityLocalServiceBaseImpl;
import com.liferay.recommend.service.util.WikiTextExtractor;
import com.liferay.wiki.model.WikiPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The implementation of the recommend entity local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.recommend.service.RecommendEntityLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecommendEntityLocalServiceBaseImpl
 * @see com.liferay.recommend.service.RecommendEntityLocalServiceUtil
 */
public class RecommendEntityLocalServiceImpl
	extends RecommendEntityLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.recommend.service.RecommendEntityLocalServiceUtil} to access the recommend entity local service.
	 */
	public JSONObject getTopMostViewed(
		int resultCount, ServiceContext serviceContext) {

		if (_log.isDebugEnabled()) {
			_log.debug("getTopMostViewed(" + resultCount + ") called.");
		}

		List<AssetEntry> assetEntries = _getMostViewedWikiPageAssets(
			resultCount);

		JSONArray recommendationsJSONArray =
			_createJsonArrayFromWikiPageAssetEntries(
				serviceContext, assetEntries);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("topRecommendations", recommendationsJSONArray);

		return jsonObject;
	}

	public JSONObject getTopMostViewedRandomized(
		int resultCount, int sampleCount, ServiceContext serviceContext) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"getTopMostViewedRanomized(" + resultCount + ", " +
					sampleCount + ") called.");
		}

		List<AssetEntry> assetEntries = _getMostViewedWikiPageAssetsRandomized(
			resultCount, sampleCount);

		JSONArray recommendationsJSONArray =
			_createJsonArrayFromWikiPageAssetEntries(
				serviceContext, assetEntries);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("topRecommendations", recommendationsJSONArray);

		return jsonObject;
	}

	private JSONArray _createJsonArrayFromWikiPageAssetEntries(
		ServiceContext serviceContext, List<AssetEntry> topViewedEntries) {

		JSONArray recommendationsJSONArray = JSONFactoryUtil.createJSONArray();

		WikiTextExtractor wikiTextExtractor = new WikiTextExtractor();

		wikiTextExtractor.setTitleSeparator("|");

		for (AssetEntry assetEntry : topViewedEntries) {
			if (_log.isDebugEnabled()) {
				_log.debug("Top Entry: " + assetEntry);
			}

			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

			if ((assetRenderer != null) &&
				(assetRenderer.getAssetObject() instanceof WikiPage)) {

				WikiPage wikiPage = (WikiPage)assetRenderer.getAssetObject();

				String userPortraitUrl = null;

				try {
					ThemeDisplay themeDisplay = new ThemeDisplay();

					User user = _userLocalService.fetchUser(
						assetEntry.getUserId());

					userPortraitUrl = user.getPortraitURL(themeDisplay);
				}
				catch (PortalException pe) {
					_log.error("Error while retrieving user portrait URL", pe);
				}

				String url =
					serviceContext.getPortalURL() + "/share/" +
						_getNormalizedTitle(assetEntry.getTitle());

				JSONObject entryJSONObject = JSONFactoryUtil.createJSONObject();

				entryJSONObject.put("id", assetEntry.getClassPK());

				entryJSONObject.put("userName", assetEntry.getUserName());

				entryJSONObject.put("viewCount", assetEntry.getViewCount());

				entryJSONObject.put("title", assetEntry.getTitle());

				entryJSONObject.put(
					"contentSample",
					wikiTextExtractor.truncateWikiContent(
						wikiPage.getContent(), wikiPage.getFormat(), 1000));

				entryJSONObject.put("url", url);

				entryJSONObject.put("userPortraitUrl", userPortraitUrl);

				recommendationsJSONArray.put(entryJSONObject);
			}
		}

		return recommendationsJSONArray;
	}

	private boolean _equalsAny(String text, String[] search) {
		for (String searchString : search) {
			if ((searchString != null) && searchString.equals(text)) {
				return true;
			}
		}

		return false;
	}

	private List<AssetEntry> _getMostViewedWikiPageAssets(int resultCount) {
		List<AssetEntry> wikiPageAssets = _removeRootTitles(
			_assetEntryLocalService.getTopViewedEntries(
				WikiPage.class.getCanonicalName(), false, 0,
				resultCount + _ROOT_TITLES.length));

		return _truncateList(wikiPageAssets, resultCount);
	}

	private List<AssetEntry> _getMostViewedWikiPageAssetsRandomized(
		int resultCount, int sampleCount) {

		List<AssetEntry> wikiPageAssets = _removeRootTitles(
			_assetEntryLocalService.getTopViewedEntries(
				WikiPage.class.getCanonicalName(), false, 0,
				sampleCount + _ROOT_TITLES.length));

		Collections.shuffle(wikiPageAssets);

		return _truncateList(wikiPageAssets, resultCount);
	}

	private String _getNormalizedTitle(String title) {
		String normalized = StringUtil.toLowerCase(title);

		return StringUtil.replace(normalized, _REPLACE, _REPLACE_WITH);
	}

	private List<AssetEntry> _removeRootTitles(List<AssetEntry> assetEntries) {
		List<AssetEntry> res = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			if (!_equalsAny(assetEntry.getTitle(), _ROOT_TITLES)) {
				res.add(assetEntry);
			}
		}

		return res;
	}

	private List<AssetEntry> _truncateList(
		List<AssetEntry> assetList, int resultCount) {

		if (assetList.size() > resultCount) {
			return assetList.subList(0, resultCount);
		}
		else {
			return assetList;
		}
	}

	private static final char[] _REPLACE = {
		'\u00e1', '\u00e9', '\u00ed', '\u00fa', '\u00fc', '\u0171', '\u00f3',
		'\u00f6', '\u0151', '&', '\'', '@', ']', ')', ':', ',', '$', '=', '!',
		'[', '(', '#', '?', ';', '/', '*', '+', ' '
	};

	private static final String[] _REPLACE_WITH = {
		"a", "e", "i", "u", "u", "u", "o", "o", "o", "AMPERSAND", "APOSTROPHE",
		"AT", "CLOSE_BRACKET", "<CLOSE_PARENTHESIS>", "<COLON>", "<COMMA>",
		"<DOLLAR>", "<EQUAL>", "<EXCLAMATION>", "<OPEN_BRACKET>",
		"<OPEN_PARENTHESIS>", "<POUND>", "<QUESTION>", "<SEMICOLON>", "<SLASH>",
		"<STAR>", "<PLUS>", "+"
	};

	private static final String[] _ROOT_TITLES =
		{"Share", "People", "Excellence", "Learn"};

	private static final Log _log = LogFactoryUtil.getLog(
		RecommendEntityLocalServiceImpl.class);

	@BeanReference(type = AssetEntryLocalService.class)
	private AssetEntryLocalService _assetEntryLocalService;

	@BeanReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}