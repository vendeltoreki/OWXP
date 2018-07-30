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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.recommend.exception.NoSuchRecommendEntityException;
import com.liferay.recommend.model.RecommendEntity;

/**
 * The persistence interface for the recommend entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.recommend.service.persistence.impl.RecommendEntityPersistenceImpl
 * @see RecommendEntityUtil
 * @generated
 */
@ProviderType
public interface RecommendEntityPersistence extends BasePersistence<RecommendEntity> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RecommendEntityUtil} to access the recommend entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the recommend entity in the entity cache if it is enabled.
	*
	* @param recommendEntity the recommend entity
	*/
	public void cacheResult(RecommendEntity recommendEntity);

	/**
	* Caches the recommend entities in the entity cache if it is enabled.
	*
	* @param recommendEntities the recommend entities
	*/
	public void cacheResult(java.util.List<RecommendEntity> recommendEntities);

	/**
	* Creates a new recommend entity with the primary key. Does not add the recommend entity to the database.
	*
	* @param id the primary key for the new recommend entity
	* @return the new recommend entity
	*/
	public RecommendEntity create(long id);

	/**
	* Removes the recommend entity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the recommend entity
	* @return the recommend entity that was removed
	* @throws NoSuchRecommendEntityException if a recommend entity with the primary key could not be found
	*/
	public RecommendEntity remove(long id)
		throws NoSuchRecommendEntityException;

	public RecommendEntity updateImpl(RecommendEntity recommendEntity);

	/**
	* Returns the recommend entity with the primary key or throws a {@link NoSuchRecommendEntityException} if it could not be found.
	*
	* @param id the primary key of the recommend entity
	* @return the recommend entity
	* @throws NoSuchRecommendEntityException if a recommend entity with the primary key could not be found
	*/
	public RecommendEntity findByPrimaryKey(long id)
		throws NoSuchRecommendEntityException;

	/**
	* Returns the recommend entity with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the recommend entity
	* @return the recommend entity, or <code>null</code> if a recommend entity with the primary key could not be found
	*/
	public RecommendEntity fetchByPrimaryKey(long id);

	@Override
	public java.util.Map<java.io.Serializable, RecommendEntity> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the recommend entities.
	*
	* @return the recommend entities
	*/
	public java.util.List<RecommendEntity> findAll();

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
	public java.util.List<RecommendEntity> findAll(int start, int end);

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
	public java.util.List<RecommendEntity> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecommendEntity> orderByComparator);

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
	public java.util.List<RecommendEntity> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RecommendEntity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the recommend entities from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of recommend entities.
	*
	* @return the number of recommend entities
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}