/*
 * Copyright 2017 DV Bern AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * limitations under the License.
 */

package ch.dvbern.lib.cdipersistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Persistenz für Basisentitaeten
 */
@SuppressWarnings("ElementOnlyUsedFromTestCode")
public interface Persistence<T> {

	/**
	 * Make an instance managed and persistent.
	 *
	 * @param entity entity instance
	 * @return the persisted entity
	 * @throws javax.persistence.EntityExistsException        if the entity already exists.
	 *                                                        (If the entity already exists, the {@code EntityExistsException} may
	 *                                                        be thrown when the persist operation is invoked, or the
	 *                                                        {@code EntityExistsException} or another {@code PersistenceException} may be
	 *                                                        thrown at flush or commit time.)
	 * @throws IllegalArgumentException                       if the instance is not an
	 *                                                        entity
	 */
	T persist(T entity);

	/**
	 * Merge the state of the given entity into the
	 * current persistence context.
	 *
	 * @param entity entity instance
	 * @return the managed instance that the state was merged to
	 * @throws IllegalArgumentException                       if instance is not an
	 *                                                        entity or is a removed entity
	 * @throws javax.persistence.TransactionRequiredException if invoked on a
	 *                                                        container-managed entity manager of type
	 *                                                        {@code PersistenceContextType.TRANSACTION} and there is
	 *                                                        no transaction
	 */
	T merge(T entity);

	/**
	 * Remove the entity instance.
	 *
	 * @param entity entity instance
	 * @throws IllegalArgumentException                       if the instance is not an
	 *                                                        entity or is a detached entity
	 * @throws javax.persistence.TransactionRequiredException if invoked on a
	 *                                                        container-managed entity manager of type
	 *                                                        {@code PersistenceContextType.TRANSACTION} and there is
	 *                                                        no transaction
	 */
	void remove(T entity);

	/**
	 * Find by primary key.
	 * Search for an entity of the specified class and primary key.
	 * If the entity instance is contained in the persistence context,
	 * it is returned from there.
	 *
	 * @param entityClass entity class
	 * @param primaryKey primary key
	 * @return the found entity instance or null if the entity does
	 * not exist
	 * @throws IllegalArgumentException if the first argument does
	 *                                  not denote an entity type or the second argument is
	 *                                  is not a valid type for that entitys primary key or
	 *                                  is null
	 */
	T find(Class<T> entityClass, Object primaryKey);

	/**
	 * Get an instance, whose state may be lazily fetched.
	 * If the requested instance does not exist in the database,
	 * the {@code EntityNotFoundException} is thrown when the instance
	 * state is first accessed. (The persistence provider runtime is
	 * permitted to throw the {@code EntityNotFoundException} when
	 * {@code getReference} is called.)
	 * The application should not expect that the instance state will
	 * be available upon detachment, unless it was accessed by the
	 * application while the entity manager was open.
	 *
	 * @param entityClass entity class
	 * @param primaryKey primary key
	 * @return the found entity instance
	 * @throws IllegalArgumentException                  if the first argument does
	 *                                                   not denote an entity type or the second argument is
	 *                                                   not a valid type for that entitys primary key or
	 *                                                   is null
	 * @throws javax.persistence.EntityNotFoundException if the entity state
	 *                                                   cannot be accessed
	 */
	T getReference(Class<T> entityClass, Object primaryKey);

	/**
	 * Remove by primary Key.
	 */
	void remove(Class<T> entityClass, Object primaryKey);

	/**
	 * Return an instance of {@code CriteriaBuilder} for the creation of
	 * {@code CriteriaQuery} objects.
	 *
	 * @return CriteriaBuilder instance
	 * @throws IllegalStateException if the entity manager has
	 *                               been closed
	 * @since Java Persistence 2.0
	 */
	CriteriaBuilder getCriteriaBuilder();

	/**
	 * @param query the query to execute
	 * @param <T> Type of Entity to return
	 * @return resultlist
	 */
	List<T> getCriteriaResults(CriteriaQuery<T> query);

	/**
	 * @param query the query to execute
	 * @param <T> Type of Entity to return
	 * @return resultlist with maxResults entries
	 */
	List<T> getCriteriaResults(final CriteriaQuery<T> query, int maxResults);

	/**
	 * @param query the query to execute
	 * @param <T> Type of Entity to return
	 * @return single result, <tt>null</tt> if there is no result, or
	 * throws an exception if more than one result is found.
	 * @throws javax.persistence.NonUniqueResultException if more than one result
	 * @throws IllegalStateException                      if called for a Java Persistence query language UPDATE or DELETE statement
	 */
	T getCriteriaSingleResult(CriteriaQuery<T> query);

	/**
	 * Returns the EntityManager
	 */
	EntityManager getEntityManager();
}
