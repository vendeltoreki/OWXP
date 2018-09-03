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

package com.liferay.recommend.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.recommend.service.RecommendEntityServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link RecommendEntityServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecommendEntityServiceSoap
 * @see HttpPrincipal
 * @see RecommendEntityServiceUtil
 * @generated
 */
@ProviderType
public class RecommendEntityServiceHttp {
	public static com.liferay.portal.kernel.json.JSONObject getTopMostViewed(
		HttpPrincipal httpPrincipal, int resultCount,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		try {
			MethodKey methodKey = new MethodKey(RecommendEntityServiceUtil.class,
					"getTopMostViewed", _getTopMostViewedParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					resultCount, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject getTopMostViewedRandomized(
		HttpPrincipal httpPrincipal, int resultCount, int sampleCount,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		try {
			MethodKey methodKey = new MethodKey(RecommendEntityServiceUtil.class,
					"getTopMostViewedRandomized",
					_getTopMostViewedRandomizedParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					resultCount, sampleCount, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RecommendEntityServiceHttp.class);
	private static final Class<?>[] _getTopMostViewedParameterTypes0 = new Class[] {
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _getTopMostViewedRandomizedParameterTypes1 = new Class[] {
			int.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}