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

package com.liferay.recommend.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.recommend.service.http.RecommendEntityServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.recommend.service.http.RecommendEntityServiceSoap
 * @generated
 */
@ProviderType
public class RecommendEntitySoap implements Serializable {
	public static RecommendEntitySoap toSoapModel(RecommendEntity model) {
		RecommendEntitySoap soapModel = new RecommendEntitySoap();

		soapModel.setId(model.getId());

		return soapModel;
	}

	public static RecommendEntitySoap[] toSoapModels(RecommendEntity[] models) {
		RecommendEntitySoap[] soapModels = new RecommendEntitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RecommendEntitySoap[][] toSoapModels(
		RecommendEntity[][] models) {
		RecommendEntitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RecommendEntitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new RecommendEntitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RecommendEntitySoap[] toSoapModels(
		List<RecommendEntity> models) {
		List<RecommendEntitySoap> soapModels = new ArrayList<RecommendEntitySoap>(models.size());

		for (RecommendEntity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RecommendEntitySoap[soapModels.size()]);
	}

	public RecommendEntitySoap() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	private long _id;
}