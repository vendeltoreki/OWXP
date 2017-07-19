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

package com.liferay.social.activity.customizer.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.social.activity.customizer.model.CustomSocialActivitySet;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the custom social activity set service. This utility wraps {@link com.liferay.social.activity.customizer.service.persistence.impl.CustomSocialActivitySetPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomSocialActivitySetPersistence
 * @see com.liferay.social.activity.customizer.service.persistence.impl.CustomSocialActivitySetPersistenceImpl
 * @generated
 */
@ProviderType
public class CustomSocialActivitySetUtil {
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
	public static void clearCache(
		CustomSocialActivitySet customSocialActivitySet) {
		getPersistence().clearCache(customSocialActivitySet);
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
	public static List<CustomSocialActivitySet> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CustomSocialActivitySet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CustomSocialActivitySet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CustomSocialActivitySet> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CustomSocialActivitySet update(
		CustomSocialActivitySet customSocialActivitySet) {
		return getPersistence().update(customSocialActivitySet);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CustomSocialActivitySet update(
		CustomSocialActivitySet customSocialActivitySet,
		ServiceContext serviceContext) {
		return getPersistence().update(customSocialActivitySet, serviceContext);
	}

	/**
	* Caches the custom social activity set in the entity cache if it is enabled.
	*
	* @param customSocialActivitySet the custom social activity set
	*/
	public static void cacheResult(
		CustomSocialActivitySet customSocialActivitySet) {
		getPersistence().cacheResult(customSocialActivitySet);
	}

	/**
	* Caches the custom social activity sets in the entity cache if it is enabled.
	*
	* @param customSocialActivitySets the custom social activity sets
	*/
	public static void cacheResult(
		List<CustomSocialActivitySet> customSocialActivitySets) {
		getPersistence().cacheResult(customSocialActivitySets);
	}

	/**
	* Creates a new custom social activity set with the primary key. Does not add the custom social activity set to the database.
	*
	* @param id the primary key for the new custom social activity set
	* @return the new custom social activity set
	*/
	public static CustomSocialActivitySet create(long id) {
		return getPersistence().create(id);
	}

	/**
	* Removes the custom social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the custom social activity set
	* @return the custom social activity set that was removed
	* @throws NoSuchCustomSocialActivitySetException if a custom social activity set with the primary key could not be found
	*/
	public static CustomSocialActivitySet remove(long id)
		throws com.liferay.social.activity.customizer.exception.NoSuchCustomSocialActivitySetException {
		return getPersistence().remove(id);
	}

	public static CustomSocialActivitySet updateImpl(
		CustomSocialActivitySet customSocialActivitySet) {
		return getPersistence().updateImpl(customSocialActivitySet);
	}

	/**
	* Returns the custom social activity set with the primary key or throws a {@link NoSuchCustomSocialActivitySetException} if it could not be found.
	*
	* @param id the primary key of the custom social activity set
	* @return the custom social activity set
	* @throws NoSuchCustomSocialActivitySetException if a custom social activity set with the primary key could not be found
	*/
	public static CustomSocialActivitySet findByPrimaryKey(long id)
		throws com.liferay.social.activity.customizer.exception.NoSuchCustomSocialActivitySetException {
		return getPersistence().findByPrimaryKey(id);
	}

	/**
	* Returns the custom social activity set with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the custom social activity set
	* @return the custom social activity set, or <code>null</code> if a custom social activity set with the primary key could not be found
	*/
	public static CustomSocialActivitySet fetchByPrimaryKey(long id) {
		return getPersistence().fetchByPrimaryKey(id);
	}

	public static java.util.Map<java.io.Serializable, CustomSocialActivitySet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the custom social activity sets.
	*
	* @return the custom social activity sets
	*/
	public static List<CustomSocialActivitySet> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the custom social activity sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CustomSocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of custom social activity sets
	* @param end the upper bound of the range of custom social activity sets (not inclusive)
	* @return the range of custom social activity sets
	*/
	public static List<CustomSocialActivitySet> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the custom social activity sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CustomSocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of custom social activity sets
	* @param end the upper bound of the range of custom social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of custom social activity sets
	*/
	public static List<CustomSocialActivitySet> findAll(int start, int end,
		OrderByComparator<CustomSocialActivitySet> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the custom social activity sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CustomSocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of custom social activity sets
	* @param end the upper bound of the range of custom social activity sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of custom social activity sets
	*/
	public static List<CustomSocialActivitySet> findAll(int start, int end,
		OrderByComparator<CustomSocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the custom social activity sets from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of custom social activity sets.
	*
	* @return the number of custom social activity sets
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CustomSocialActivitySetPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CustomSocialActivitySetPersistence, CustomSocialActivitySetPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CustomSocialActivitySetPersistence.class);
}