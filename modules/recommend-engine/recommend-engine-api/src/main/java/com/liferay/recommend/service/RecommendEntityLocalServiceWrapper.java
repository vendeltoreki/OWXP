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
 * Provides a wrapper for {@link RecommendEntityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RecommendEntityLocalService
 * @generated
 */
@ProviderType
public class RecommendEntityLocalServiceWrapper
	implements RecommendEntityLocalService,
		ServiceWrapper<RecommendEntityLocalService> {
	public RecommendEntityLocalServiceWrapper(
		RecommendEntityLocalService recommendEntityLocalService) {
		_recommendEntityLocalService = recommendEntityLocalService;
	}

	/**
	* Adds the recommend entity to the database. Also notifies the appropriate model listeners.
	*
	* @param recommendEntity the recommend entity
	* @return the recommend entity that was added
	*/
	@Override
	public com.liferay.recommend.model.RecommendEntity addRecommendEntity(
		com.liferay.recommend.model.RecommendEntity recommendEntity) {
		return _recommendEntityLocalService.addRecommendEntity(recommendEntity);
	}

	/**
	* Creates a new recommend entity with the primary key. Does not add the recommend entity to the database.
	*
	* @param id the primary key for the new recommend entity
	* @return the new recommend entity
	*/
	@Override
	public com.liferay.recommend.model.RecommendEntity createRecommendEntity(
		long id) {
		return _recommendEntityLocalService.createRecommendEntity(id);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recommendEntityLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the recommend entity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the recommend entity
	* @return the recommend entity that was removed
	* @throws PortalException if a recommend entity with the primary key could not be found
	*/
	@Override
	public com.liferay.recommend.model.RecommendEntity deleteRecommendEntity(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return _recommendEntityLocalService.deleteRecommendEntity(id);
	}

	/**
	* Deletes the recommend entity from the database. Also notifies the appropriate model listeners.
	*
	* @param recommendEntity the recommend entity
	* @return the recommend entity that was removed
	*/
	@Override
	public com.liferay.recommend.model.RecommendEntity deleteRecommendEntity(
		com.liferay.recommend.model.RecommendEntity recommendEntity) {
		return _recommendEntityLocalService.deleteRecommendEntity(recommendEntity);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _recommendEntityLocalService.dynamicQuery();
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
		return _recommendEntityLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.recommend.model.impl.RecommendEntityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _recommendEntityLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.recommend.model.impl.RecommendEntityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _recommendEntityLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _recommendEntityLocalService.dynamicQueryCount(dynamicQuery);
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
		return _recommendEntityLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.recommend.model.RecommendEntity fetchRecommendEntity(
		long id) {
		return _recommendEntityLocalService.fetchRecommendEntity(id);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _recommendEntityLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _recommendEntityLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _recommendEntityLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _recommendEntityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the recommend entities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.recommend.model.impl.RecommendEntityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recommend entities
	* @param end the upper bound of the range of recommend entities (not inclusive)
	* @return the range of recommend entities
	*/
	@Override
	public java.util.List<com.liferay.recommend.model.RecommendEntity> getRecommendEntities(
		int start, int end) {
		return _recommendEntityLocalService.getRecommendEntities(start, end);
	}

	/**
	* Returns the number of recommend entities.
	*
	* @return the number of recommend entities
	*/
	@Override
	public int getRecommendEntitiesCount() {
		return _recommendEntityLocalService.getRecommendEntitiesCount();
	}

	/**
	* Returns the recommend entity with the primary key.
	*
	* @param id the primary key of the recommend entity
	* @return the recommend entity
	* @throws PortalException if a recommend entity with the primary key could not be found
	*/
	@Override
	public com.liferay.recommend.model.RecommendEntity getRecommendEntity(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return _recommendEntityLocalService.getRecommendEntity(id);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getStuff(
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return _recommendEntityLocalService.getStuff(serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getTopMostViewed(
		int resultCount,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return _recommendEntityLocalService.getTopMostViewed(resultCount,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getTopMostViewedRanomized(
		int resultCount, int sampleCount,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return _recommendEntityLocalService.getTopMostViewedRanomized(resultCount,
			sampleCount, serviceContext);
	}

	/**
	* Updates the recommend entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param recommendEntity the recommend entity
	* @return the recommend entity that was updated
	*/
	@Override
	public com.liferay.recommend.model.RecommendEntity updateRecommendEntity(
		com.liferay.recommend.model.RecommendEntity recommendEntity) {
		return _recommendEntityLocalService.updateRecommendEntity(recommendEntity);
	}

	@Override
	public RecommendEntityLocalService getWrappedService() {
		return _recommendEntityLocalService;
	}

	@Override
	public void setWrappedService(
		RecommendEntityLocalService recommendEntityLocalService) {
		_recommendEntityLocalService = recommendEntityLocalService;
	}

	private RecommendEntityLocalService _recommendEntityLocalService;
}