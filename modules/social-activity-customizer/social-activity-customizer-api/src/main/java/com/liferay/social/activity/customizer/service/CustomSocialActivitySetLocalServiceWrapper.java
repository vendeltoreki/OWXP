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

package com.liferay.social.activity.customizer.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CustomSocialActivitySetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CustomSocialActivitySetLocalService
 * @generated
 */
@ProviderType
public class CustomSocialActivitySetLocalServiceWrapper
	implements CustomSocialActivitySetLocalService,
		ServiceWrapper<CustomSocialActivitySetLocalService> {
	public CustomSocialActivitySetLocalServiceWrapper(
		CustomSocialActivitySetLocalService customSocialActivitySetLocalService) {
		_customSocialActivitySetLocalService = customSocialActivitySetLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _customSocialActivitySetLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _customSocialActivitySetLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _customSocialActivitySetLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _customSocialActivitySetLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _customSocialActivitySetLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the custom social activity set to the database. Also notifies the appropriate model listeners.
	*
	* @param customSocialActivitySet the custom social activity set
	* @return the custom social activity set that was added
	*/
	@Override
	public com.liferay.social.activity.customizer.model.CustomSocialActivitySet addCustomSocialActivitySet(
		com.liferay.social.activity.customizer.model.CustomSocialActivitySet customSocialActivitySet) {
		return _customSocialActivitySetLocalService.addCustomSocialActivitySet(customSocialActivitySet);
	}

	/**
	* Creates a new custom social activity set with the primary key. Does not add the custom social activity set to the database.
	*
	* @param id the primary key for the new custom social activity set
	* @return the new custom social activity set
	*/
	@Override
	public com.liferay.social.activity.customizer.model.CustomSocialActivitySet createCustomSocialActivitySet(
		long id) {
		return _customSocialActivitySetLocalService.createCustomSocialActivitySet(id);
	}

	/**
	* Deletes the custom social activity set from the database. Also notifies the appropriate model listeners.
	*
	* @param customSocialActivitySet the custom social activity set
	* @return the custom social activity set that was removed
	*/
	@Override
	public com.liferay.social.activity.customizer.model.CustomSocialActivitySet deleteCustomSocialActivitySet(
		com.liferay.social.activity.customizer.model.CustomSocialActivitySet customSocialActivitySet) {
		return _customSocialActivitySetLocalService.deleteCustomSocialActivitySet(customSocialActivitySet);
	}

	/**
	* Deletes the custom social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the custom social activity set
	* @return the custom social activity set that was removed
	* @throws PortalException if a custom social activity set with the primary key could not be found
	*/
	@Override
	public com.liferay.social.activity.customizer.model.CustomSocialActivitySet deleteCustomSocialActivitySet(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return _customSocialActivitySetLocalService.deleteCustomSocialActivitySet(id);
	}

	@Override
	public com.liferay.social.activity.customizer.model.CustomSocialActivitySet fetchCustomSocialActivitySet(
		long id) {
		return _customSocialActivitySetLocalService.fetchCustomSocialActivitySet(id);
	}

	/**
	* Returns the custom social activity set with the primary key.
	*
	* @param id the primary key of the custom social activity set
	* @return the custom social activity set
	* @throws PortalException if a custom social activity set with the primary key could not be found
	*/
	@Override
	public com.liferay.social.activity.customizer.model.CustomSocialActivitySet getCustomSocialActivitySet(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return _customSocialActivitySetLocalService.getCustomSocialActivitySet(id);
	}

	/**
	* Updates the custom social activity set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param customSocialActivitySet the custom social activity set
	* @return the custom social activity set that was updated
	*/
	@Override
	public com.liferay.social.activity.customizer.model.CustomSocialActivitySet updateCustomSocialActivitySet(
		com.liferay.social.activity.customizer.model.CustomSocialActivitySet customSocialActivitySet) {
		return _customSocialActivitySetLocalService.updateCustomSocialActivitySet(customSocialActivitySet);
	}

	/**
	* Returns the number of custom social activity sets.
	*
	* @return the number of custom social activity sets
	*/
	@Override
	public int getCustomSocialActivitySetsCount() {
		return _customSocialActivitySetLocalService.getCustomSocialActivitySetsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _customSocialActivitySetLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _customSocialActivitySetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.social.activity.customizer.model.impl.CustomSocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _customSocialActivitySetLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.social.activity.customizer.model.impl.CustomSocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _customSocialActivitySetLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the custom social activity sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.social.activity.customizer.model.impl.CustomSocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of custom social activity sets
	* @param end the upper bound of the range of custom social activity sets (not inclusive)
	* @return the range of custom social activity sets
	*/
	@Override
	public java.util.List<com.liferay.social.activity.customizer.model.CustomSocialActivitySet> getCustomSocialActivitySets(
		int start, int end) {
		return _customSocialActivitySetLocalService.getCustomSocialActivitySets(start,
			end);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _customSocialActivitySetLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _customSocialActivitySetLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public CustomSocialActivitySetLocalService getWrappedService() {
		return _customSocialActivitySetLocalService;
	}

	@Override
	public void setWrappedService(
		CustomSocialActivitySetLocalService customSocialActivitySetLocalService) {
		_customSocialActivitySetLocalService = customSocialActivitySetLocalService;
	}

	private CustomSocialActivitySetLocalService _customSocialActivitySetLocalService;
}