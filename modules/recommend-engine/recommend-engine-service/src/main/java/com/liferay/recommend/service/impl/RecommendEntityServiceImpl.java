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

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.recommend.service.RecommendEntityService;
import com.liferay.recommend.service.base.RecommendEntityServiceBaseImpl;

/**
 * The implementation of the recommend entity remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.recommend.service.RecommendEntityService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecommendEntityServiceBaseImpl
 * @see com.liferay.recommend.service.RecommendEntityServiceUtil
 */
@Component(immediate = true, service = RecommendEntityService.class)
public class RecommendEntityServiceImpl extends RecommendEntityServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.recommend.service.RecommendEntityServiceUtil} to access the recommend entity remote service.
	 */

	@JSONWebService
	public JSONObject getStuff(ServiceContext serviceContext) {
		return recommendEntityLocalService.getStuff(serviceContext);
	}

	@JSONWebService
	public JSONObject getTopMostViewed(int resultCount, ServiceContext serviceContext) {
		return recommendEntityLocalService.getTopMostViewed(resultCount, serviceContext);
	}

	@JSONWebService
	public JSONObject getTopMostViewedRandomized(int resultCount, int sampleCount, ServiceContext serviceContext) {
		return recommendEntityLocalService.getTopMostViewedRandomized(resultCount, sampleCount, serviceContext);
	}
	
}