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

package com.liferay.recommend.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RecommendEntityService}.
 *
 * @author Brian Wing Shun Chan
 * @see RecommendEntityService
 * @generated
 */
@ProviderType
public class RecommendEntityServiceWrapper implements RecommendEntityService,
	ServiceWrapper<RecommendEntityService> {
	public RecommendEntityServiceWrapper(
		RecommendEntityService recommendEntityService) {
		_recommendEntityService = recommendEntityService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _recommendEntityService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getStuff(
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return _recommendEntityService.getStuff(serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getTopMostViewed(
		int resultCount,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return _recommendEntityService.getTopMostViewed(resultCount,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getTopMostViewedRanomized(
		int resultCount, int sampleCount,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return _recommendEntityService.getTopMostViewedRanomized(resultCount,
			sampleCount, serviceContext);
	}

	@Override
	public RecommendEntityService getWrappedService() {
		return _recommendEntityService;
	}

	@Override
	public void setWrappedService(RecommendEntityService recommendEntityService) {
		_recommendEntityService = recommendEntityService;
	}

	private RecommendEntityService _recommendEntityService;
}