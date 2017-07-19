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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.social.activity.customizer.exception.NoSuchCustomSocialActivitySetException;
import com.liferay.social.activity.customizer.model.CustomSocialActivitySet;

/**
 * The persistence interface for the custom social activity set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.social.activity.customizer.service.persistence.impl.CustomSocialActivitySetPersistenceImpl
 * @see CustomSocialActivitySetUtil
 * @generated
 */
@ProviderType
public interface CustomSocialActivitySetPersistence extends BasePersistence<CustomSocialActivitySet> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CustomSocialActivitySetUtil} to access the custom social activity set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the custom social activity set in the entity cache if it is enabled.
	*
	* @param customSocialActivitySet the custom social activity set
	*/
	public void cacheResult(CustomSocialActivitySet customSocialActivitySet);

	/**
	* Caches the custom social activity sets in the entity cache if it is enabled.
	*
	* @param customSocialActivitySets the custom social activity sets
	*/
	public void cacheResult(
		java.util.List<CustomSocialActivitySet> customSocialActivitySets);

	/**
	* Creates a new custom social activity set with the primary key. Does not add the custom social activity set to the database.
	*
	* @param id the primary key for the new custom social activity set
	* @return the new custom social activity set
	*/
	public CustomSocialActivitySet create(long id);

	/**
	* Removes the custom social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the custom social activity set
	* @return the custom social activity set that was removed
	* @throws NoSuchCustomSocialActivitySetException if a custom social activity set with the primary key could not be found
	*/
	public CustomSocialActivitySet remove(long id)
		throws NoSuchCustomSocialActivitySetException;

	public CustomSocialActivitySet updateImpl(
		CustomSocialActivitySet customSocialActivitySet);

	/**
	* Returns the custom social activity set with the primary key or throws a {@link NoSuchCustomSocialActivitySetException} if it could not be found.
	*
	* @param id the primary key of the custom social activity set
	* @return the custom social activity set
	* @throws NoSuchCustomSocialActivitySetException if a custom social activity set with the primary key could not be found
	*/
	public CustomSocialActivitySet findByPrimaryKey(long id)
		throws NoSuchCustomSocialActivitySetException;

	/**
	* Returns the custom social activity set with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the custom social activity set
	* @return the custom social activity set, or <code>null</code> if a custom social activity set with the primary key could not be found
	*/
	public CustomSocialActivitySet fetchByPrimaryKey(long id);

	@Override
	public java.util.Map<java.io.Serializable, CustomSocialActivitySet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the custom social activity sets.
	*
	* @return the custom social activity sets
	*/
	public java.util.List<CustomSocialActivitySet> findAll();

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
	public java.util.List<CustomSocialActivitySet> findAll(int start, int end);

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
	public java.util.List<CustomSocialActivitySet> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomSocialActivitySet> orderByComparator);

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
	public java.util.List<CustomSocialActivitySet> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CustomSocialActivitySet> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the custom social activity sets from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of custom social activity sets.
	*
	* @return the number of custom social activity sets
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}