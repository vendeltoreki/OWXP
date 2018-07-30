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

package com.liferay.recommend.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.recommend.exception.NoSuchRecommendEntityException;
import com.liferay.recommend.model.RecommendEntity;
import com.liferay.recommend.model.impl.RecommendEntityImpl;
import com.liferay.recommend.model.impl.RecommendEntityModelImpl;
import com.liferay.recommend.service.persistence.RecommendEntityPersistence;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the recommend entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecommendEntityPersistence
 * @see com.liferay.recommend.service.persistence.RecommendEntityUtil
 * @generated
 */
@ProviderType
public class RecommendEntityPersistenceImpl extends BasePersistenceImpl<RecommendEntity>
	implements RecommendEntityPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RecommendEntityUtil} to access the recommend entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RecommendEntityImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
			RecommendEntityModelImpl.FINDER_CACHE_ENABLED,
			RecommendEntityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
			RecommendEntityModelImpl.FINDER_CACHE_ENABLED,
			RecommendEntityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
			RecommendEntityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public RecommendEntityPersistenceImpl() {
		setModelClass(RecommendEntity.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("id", "id_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the recommend entity in the entity cache if it is enabled.
	 *
	 * @param recommendEntity the recommend entity
	 */
	@Override
	public void cacheResult(RecommendEntity recommendEntity) {
		entityCache.putResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
			RecommendEntityImpl.class, recommendEntity.getPrimaryKey(),
			recommendEntity);

		recommendEntity.resetOriginalValues();
	}

	/**
	 * Caches the recommend entities in the entity cache if it is enabled.
	 *
	 * @param recommendEntities the recommend entities
	 */
	@Override
	public void cacheResult(List<RecommendEntity> recommendEntities) {
		for (RecommendEntity recommendEntity : recommendEntities) {
			if (entityCache.getResult(
						RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
						RecommendEntityImpl.class,
						recommendEntity.getPrimaryKey()) == null) {
				cacheResult(recommendEntity);
			}
			else {
				recommendEntity.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all recommend entities.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RecommendEntityImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the recommend entity.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RecommendEntity recommendEntity) {
		entityCache.removeResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
			RecommendEntityImpl.class, recommendEntity.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<RecommendEntity> recommendEntities) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RecommendEntity recommendEntity : recommendEntities) {
			entityCache.removeResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
				RecommendEntityImpl.class, recommendEntity.getPrimaryKey());
		}
	}

	/**
	 * Creates a new recommend entity with the primary key. Does not add the recommend entity to the database.
	 *
	 * @param id the primary key for the new recommend entity
	 * @return the new recommend entity
	 */
	@Override
	public RecommendEntity create(long id) {
		RecommendEntity recommendEntity = new RecommendEntityImpl();

		recommendEntity.setNew(true);
		recommendEntity.setPrimaryKey(id);

		return recommendEntity;
	}

	/**
	 * Removes the recommend entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the recommend entity
	 * @return the recommend entity that was removed
	 * @throws NoSuchRecommendEntityException if a recommend entity with the primary key could not be found
	 */
	@Override
	public RecommendEntity remove(long id)
		throws NoSuchRecommendEntityException {
		return remove((Serializable)id);
	}

	/**
	 * Removes the recommend entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the recommend entity
	 * @return the recommend entity that was removed
	 * @throws NoSuchRecommendEntityException if a recommend entity with the primary key could not be found
	 */
	@Override
	public RecommendEntity remove(Serializable primaryKey)
		throws NoSuchRecommendEntityException {
		Session session = null;

		try {
			session = openSession();

			RecommendEntity recommendEntity = (RecommendEntity)session.get(RecommendEntityImpl.class,
					primaryKey);

			if (recommendEntity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecommendEntityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(recommendEntity);
		}
		catch (NoSuchRecommendEntityException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected RecommendEntity removeImpl(RecommendEntity recommendEntity) {
		recommendEntity = toUnwrappedModel(recommendEntity);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(recommendEntity)) {
				recommendEntity = (RecommendEntity)session.get(RecommendEntityImpl.class,
						recommendEntity.getPrimaryKeyObj());
			}

			if (recommendEntity != null) {
				session.delete(recommendEntity);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (recommendEntity != null) {
			clearCache(recommendEntity);
		}

		return recommendEntity;
	}

	@Override
	public RecommendEntity updateImpl(RecommendEntity recommendEntity) {
		recommendEntity = toUnwrappedModel(recommendEntity);

		boolean isNew = recommendEntity.isNew();

		Session session = null;

		try {
			session = openSession();

			if (recommendEntity.isNew()) {
				session.save(recommendEntity);

				recommendEntity.setNew(false);
			}
			else {
				recommendEntity = (RecommendEntity)session.merge(recommendEntity);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
			RecommendEntityImpl.class, recommendEntity.getPrimaryKey(),
			recommendEntity, false);

		recommendEntity.resetOriginalValues();

		return recommendEntity;
	}

	protected RecommendEntity toUnwrappedModel(RecommendEntity recommendEntity) {
		if (recommendEntity instanceof RecommendEntityImpl) {
			return recommendEntity;
		}

		RecommendEntityImpl recommendEntityImpl = new RecommendEntityImpl();

		recommendEntityImpl.setNew(recommendEntity.isNew());
		recommendEntityImpl.setPrimaryKey(recommendEntity.getPrimaryKey());

		recommendEntityImpl.setId(recommendEntity.getId());

		return recommendEntityImpl;
	}

	/**
	 * Returns the recommend entity with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the recommend entity
	 * @return the recommend entity
	 * @throws NoSuchRecommendEntityException if a recommend entity with the primary key could not be found
	 */
	@Override
	public RecommendEntity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecommendEntityException {
		RecommendEntity recommendEntity = fetchByPrimaryKey(primaryKey);

		if (recommendEntity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecommendEntityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return recommendEntity;
	}

	/**
	 * Returns the recommend entity with the primary key or throws a {@link NoSuchRecommendEntityException} if it could not be found.
	 *
	 * @param id the primary key of the recommend entity
	 * @return the recommend entity
	 * @throws NoSuchRecommendEntityException if a recommend entity with the primary key could not be found
	 */
	@Override
	public RecommendEntity findByPrimaryKey(long id)
		throws NoSuchRecommendEntityException {
		return findByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns the recommend entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the recommend entity
	 * @return the recommend entity, or <code>null</code> if a recommend entity with the primary key could not be found
	 */
	@Override
	public RecommendEntity fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
				RecommendEntityImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		RecommendEntity recommendEntity = (RecommendEntity)serializable;

		if (recommendEntity == null) {
			Session session = null;

			try {
				session = openSession();

				recommendEntity = (RecommendEntity)session.get(RecommendEntityImpl.class,
						primaryKey);

				if (recommendEntity != null) {
					cacheResult(recommendEntity);
				}
				else {
					entityCache.putResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
						RecommendEntityImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
					RecommendEntityImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return recommendEntity;
	}

	/**
	 * Returns the recommend entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the recommend entity
	 * @return the recommend entity, or <code>null</code> if a recommend entity with the primary key could not be found
	 */
	@Override
	public RecommendEntity fetchByPrimaryKey(long id) {
		return fetchByPrimaryKey((Serializable)id);
	}

	@Override
	public Map<Serializable, RecommendEntity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RecommendEntity> map = new HashMap<Serializable, RecommendEntity>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RecommendEntity recommendEntity = fetchByPrimaryKey(primaryKey);

			if (recommendEntity != null) {
				map.put(primaryKey, recommendEntity);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
					RecommendEntityImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (RecommendEntity)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_RECOMMENDENTITY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (RecommendEntity recommendEntity : (List<RecommendEntity>)q.list()) {
				map.put(recommendEntity.getPrimaryKeyObj(), recommendEntity);

				cacheResult(recommendEntity);

				uncachedPrimaryKeys.remove(recommendEntity.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(RecommendEntityModelImpl.ENTITY_CACHE_ENABLED,
					RecommendEntityImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the recommend entities.
	 *
	 * @return the recommend entities
	 */
	@Override
	public List<RecommendEntity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<RecommendEntity> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<RecommendEntity> findAll(int start, int end,
		OrderByComparator<RecommendEntity> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<RecommendEntity> findAll(int start, int end,
		OrderByComparator<RecommendEntity> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<RecommendEntity> list = null;

		if (retrieveFromCache) {
			list = (List<RecommendEntity>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_RECOMMENDENTITY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RECOMMENDENTITY;

				if (pagination) {
					sql = sql.concat(RecommendEntityModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<RecommendEntity>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecommendEntity>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the recommend entities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RecommendEntity recommendEntity : findAll()) {
			remove(recommendEntity);
		}
	}

	/**
	 * Returns the number of recommend entities.
	 *
	 * @return the number of recommend entities
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RECOMMENDENTITY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RecommendEntityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the recommend entity persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(RecommendEntityImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_RECOMMENDENTITY = "SELECT recommendEntity FROM RecommendEntity recommendEntity";
	private static final String _SQL_SELECT_RECOMMENDENTITY_WHERE_PKS_IN = "SELECT recommendEntity FROM RecommendEntity recommendEntity WHERE id_ IN (";
	private static final String _SQL_COUNT_RECOMMENDENTITY = "SELECT COUNT(recommendEntity) FROM RecommendEntity recommendEntity";
	private static final String _ORDER_BY_ENTITY_ALIAS = "recommendEntity.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RecommendEntity exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(RecommendEntityPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"id"
			});
}