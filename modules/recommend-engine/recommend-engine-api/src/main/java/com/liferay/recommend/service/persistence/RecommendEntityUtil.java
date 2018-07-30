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

package com.liferay.recommend.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.recommend.model.RecommendEntity;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the recommend entity service. This utility wraps {@link com.liferay.recommend.service.persistence.impl.RecommendEntityPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecommendEntityPersistence
 * @see com.liferay.recommend.service.persistence.impl.RecommendEntityPersistenceImpl
 * @generated
 */
@ProviderType
public class RecommendEntityUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(RecommendEntity recommendEntity) {
		getPersistence().clearCache(recommendEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RecommendEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RecommendEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RecommendEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RecommendEntity> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RecommendEntity update(RecommendEntity recommendEntity) {
		return getPersistence().update(recommendEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RecommendEntity update(RecommendEntity recommendEntity,
		ServiceContext serviceContext) {
		return getPersistence().update(recommendEntity, serviceContext);
	}

	/**
	* Caches the recommend entity in the entity cache if it is enabled.
	*
	* @param recommendEntity the recommend entity
	*/
	public static void cacheResult(RecommendEntity recommendEntity) {
		getPersistence().cacheResult(recommendEntity);
	}

	/**
	* Caches the recommend entities in the entity cache if it is enabled.
	*
	* @param recommendEntities the recommend entities
	*/
	public static void cacheResult(List<RecommendEntity> recommendEntities) {
		getPersistence().cacheResult(recommendEntities);
	}

	/**
	* Creates a new recommend entity with the primary key. Does not add the recommend entity to the database.
	*
	* @param id the primary key for the new recommend entity
	* @return the new recommend entity
	*/
	public static RecommendEntity create(long id) {
		return getPersistence().create(id);
	}

	/**
	* Removes the recommend entity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the recommend entity
	* @return the recommend entity that was removed
	* @throws NoSuchRecommendEntityException if a recommend entity with the primary key could not be found
	*/
	public static RecommendEntity remove(long id)
		throws com.liferay.recommend.exception.NoSuchRecommendEntityException {
		return getPersistence().remove(id);
	}

	public static RecommendEntity updateImpl(RecommendEntity recommendEntity) {
		return getPersistence().updateImpl(recommendEntity);
	}

	/**
	* Returns the recommend entity with the primary key or throws a {@link NoSuchRecommendEntityException} if it could not be found.
	*
	* @param id the primary key of the recommend entity
	* @return the recommend entity
	* @throws NoSuchRecommendEntityException if a recommend entity with the primary key could not be found
	*/
	public static RecommendEntity findByPrimaryKey(long id)
		throws com.liferay.recommend.exception.NoSuchRecommendEntityException {
		return getPersistence().findByPrimaryKey(id);
	}

	/**
	* Returns the recommend entity with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the recommend entity
	* @return the recommend entity, or <code>null</code> if a recommend entity with the primary key could not be found
	*/
	public static RecommendEntity fetchByPrimaryKey(long id) {
		return getPersistence().fetchByPrimaryKey(id);
	}

	public static java.util.Map<java.io.Serializable, RecommendEntity> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the recommend entities.
	*
	* @return the recommend entities
	*/
	public static List<RecommendEntity> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the recommend entities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecommendEntityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recommend entities
	* @param end the upper bound of the range of recommend entities (not inclusive)
	* @return the range of recommend entities
	*/
	public static List<RecommendEntity> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the recommend entities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecommendEntityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recommend entities
	* @param end the upper bound of the range of recommend entities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of recommend entities
	*/
	public static List<RecommendEntity> findAll(int start, int end,
		OrderByComparator<RecommendEntity> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the recommend entities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecommendEntityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of recommend entities
	* @param end the upper bound of the range of recommend entities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of recommend entities
	*/
	public static List<RecommendEntity> findAll(int start, int end,
		OrderByComparator<RecommendEntity> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the recommend entities from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of recommend entities.
	*
	* @return the number of recommend entities
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static RecommendEntityPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<RecommendEntityPersistence, RecommendEntityPersistence> _serviceTracker =
		ServiceTrackerFactory.open(RecommendEntityPersistence.class);
}