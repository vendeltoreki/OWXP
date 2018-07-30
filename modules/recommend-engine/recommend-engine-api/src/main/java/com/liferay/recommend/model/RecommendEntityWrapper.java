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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link RecommendEntity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecommendEntity
 * @generated
 */
@ProviderType
public class RecommendEntityWrapper implements RecommendEntity,
	ModelWrapper<RecommendEntity> {
	public RecommendEntityWrapper(RecommendEntity recommendEntity) {
		_recommendEntity = recommendEntity;
	}

	@Override
	public Class<?> getModelClass() {
		return RecommendEntity.class;
	}

	@Override
	public String getModelClassName() {
		return RecommendEntity.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("id", getId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new RecommendEntityWrapper((RecommendEntity)_recommendEntity.clone());
	}

	@Override
	public int compareTo(RecommendEntity recommendEntity) {
		return _recommendEntity.compareTo(recommendEntity);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _recommendEntity.getExpandoBridge();
	}

	/**
	* Returns the ID of this recommend entity.
	*
	* @return the ID of this recommend entity
	*/
	@Override
	public long getId() {
		return _recommendEntity.getId();
	}

	/**
	* Returns the primary key of this recommend entity.
	*
	* @return the primary key of this recommend entity
	*/
	@Override
	public long getPrimaryKey() {
		return _recommendEntity.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _recommendEntity.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _recommendEntity.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _recommendEntity.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _recommendEntity.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _recommendEntity.isNew();
	}

	@Override
	public void persist() {
		_recommendEntity.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_recommendEntity.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_recommendEntity.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_recommendEntity.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_recommendEntity.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the ID of this recommend entity.
	*
	* @param id the ID of this recommend entity
	*/
	@Override
	public void setId(long id) {
		_recommendEntity.setId(id);
	}

	@Override
	public void setNew(boolean n) {
		_recommendEntity.setNew(n);
	}

	/**
	* Sets the primary key of this recommend entity.
	*
	* @param primaryKey the primary key of this recommend entity
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_recommendEntity.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_recommendEntity.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<RecommendEntity> toCacheModel() {
		return _recommendEntity.toCacheModel();
	}

	@Override
	public RecommendEntity toEscapedModel() {
		return new RecommendEntityWrapper(_recommendEntity.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _recommendEntity.toString();
	}

	@Override
	public RecommendEntity toUnescapedModel() {
		return new RecommendEntityWrapper(_recommendEntity.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _recommendEntity.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RecommendEntityWrapper)) {
			return false;
		}

		RecommendEntityWrapper recommendEntityWrapper = (RecommendEntityWrapper)obj;

		if (Objects.equals(_recommendEntity,
					recommendEntityWrapper._recommendEntity)) {
			return true;
		}

		return false;
	}

	@Override
	public RecommendEntity getWrappedModel() {
		return _recommendEntity;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _recommendEntity.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _recommendEntity.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_recommendEntity.resetOriginalValues();
	}

	private final RecommendEntity _recommendEntity;
}