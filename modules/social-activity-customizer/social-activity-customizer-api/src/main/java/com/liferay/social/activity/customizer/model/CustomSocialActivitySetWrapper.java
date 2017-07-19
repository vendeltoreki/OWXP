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

package com.liferay.social.activity.customizer.model;

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
 * This class is a wrapper for {@link CustomSocialActivitySet}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomSocialActivitySet
 * @generated
 */
@ProviderType
public class CustomSocialActivitySetWrapper implements CustomSocialActivitySet,
	ModelWrapper<CustomSocialActivitySet> {
	public CustomSocialActivitySetWrapper(
		CustomSocialActivitySet customSocialActivitySet) {
		_customSocialActivitySet = customSocialActivitySet;
	}

	@Override
	public Class<?> getModelClass() {
		return CustomSocialActivitySet.class;
	}

	@Override
	public String getModelClassName() {
		return CustomSocialActivitySet.class.getName();
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
	public CustomSocialActivitySet toEscapedModel() {
		return new CustomSocialActivitySetWrapper(_customSocialActivitySet.toEscapedModel());
	}

	@Override
	public CustomSocialActivitySet toUnescapedModel() {
		return new CustomSocialActivitySetWrapper(_customSocialActivitySet.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _customSocialActivitySet.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _customSocialActivitySet.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _customSocialActivitySet.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _customSocialActivitySet.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CustomSocialActivitySet> toCacheModel() {
		return _customSocialActivitySet.toCacheModel();
	}

	@Override
	public int compareTo(CustomSocialActivitySet customSocialActivitySet) {
		return _customSocialActivitySet.compareTo(customSocialActivitySet);
	}

	@Override
	public int hashCode() {
		return _customSocialActivitySet.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _customSocialActivitySet.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CustomSocialActivitySetWrapper((CustomSocialActivitySet)_customSocialActivitySet.clone());
	}

	@Override
	public java.lang.String toString() {
		return _customSocialActivitySet.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _customSocialActivitySet.toXmlString();
	}

	/**
	* Returns the ID of this custom social activity set.
	*
	* @return the ID of this custom social activity set
	*/
	@Override
	public long getId() {
		return _customSocialActivitySet.getId();
	}

	/**
	* Returns the primary key of this custom social activity set.
	*
	* @return the primary key of this custom social activity set
	*/
	@Override
	public long getPrimaryKey() {
		return _customSocialActivitySet.getPrimaryKey();
	}

	@Override
	public void persist() {
		_customSocialActivitySet.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_customSocialActivitySet.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_customSocialActivitySet.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_customSocialActivitySet.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_customSocialActivitySet.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the ID of this custom social activity set.
	*
	* @param id the ID of this custom social activity set
	*/
	@Override
	public void setId(long id) {
		_customSocialActivitySet.setId(id);
	}

	@Override
	public void setNew(boolean n) {
		_customSocialActivitySet.setNew(n);
	}

	/**
	* Sets the primary key of this custom social activity set.
	*
	* @param primaryKey the primary key of this custom social activity set
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_customSocialActivitySet.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_customSocialActivitySet.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CustomSocialActivitySetWrapper)) {
			return false;
		}

		CustomSocialActivitySetWrapper customSocialActivitySetWrapper = (CustomSocialActivitySetWrapper)obj;

		if (Objects.equals(_customSocialActivitySet,
					customSocialActivitySetWrapper._customSocialActivitySet)) {
			return true;
		}

		return false;
	}

	@Override
	public CustomSocialActivitySet getWrappedModel() {
		return _customSocialActivitySet;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _customSocialActivitySet.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _customSocialActivitySet.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_customSocialActivitySet.resetOriginalValues();
	}

	private final CustomSocialActivitySet _customSocialActivitySet;
}