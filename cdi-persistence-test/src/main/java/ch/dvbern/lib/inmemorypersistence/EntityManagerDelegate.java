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

package ch.dvbern.lib.inmemorypersistence;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

@ApplicationScoped
public class EntityManagerDelegate implements EntityManager {

	@Inject
	private EntityManagerStore entityManagerStore;

	@Override
	public void clear() {
		entityManagerStore.get().clear();
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(final String name, final Class<T> resultClass) {
		return entityManagerStore.get().createNamedQuery(name, resultClass);
	}

	@Override
	public boolean isOpen() {
		return entityManagerStore.get().isOpen();
	}

	@Override
	public <T> T find(final Class<T> entityClass, final Object primaryKey, final Map<String, Object> properties) {
		return entityManagerStore.get().find(entityClass, primaryKey, properties);
	}

	@Override
	public Query createNativeQuery(final String sqlString, final Class resultClass) {
		return entityManagerStore.get().createNativeQuery(sqlString, resultClass);
	}

	@Override
	public Query createNamedQuery(final String name) {
		return entityManagerStore.get().createNamedQuery(name);
	}

	@Override
	public <T> T getReference(final Class<T> entityClass, final Object primaryKey) {
		return entityManagerStore.get().getReference(entityClass, primaryKey);
	}

	@Override
	public <T> T merge(final T entity) {
		return entityManagerStore.get().merge(entity);
	}

	@Override
	public Object getDelegate() {
		return entityManagerStore.get().getDelegate();
	}

	@Override
	public Metamodel getMetamodel() {
		return entityManagerStore.get().getMetamodel();
	}

	@Override
	public EntityTransaction getTransaction() {
		return entityManagerStore.get().getTransaction();
	}

	@Override
	public Query createNativeQuery(final String sqlString, final String resultSetMapping) {
		return entityManagerStore.get().createNativeQuery(sqlString, resultSetMapping);
	}

	@Override
	public FlushModeType getFlushMode() {
		return entityManagerStore.get().getFlushMode();
	}

	@Override
	public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockMode, final Map<String, Object> properties) {
		return entityManagerStore.get().find(entityClass, primaryKey, lockMode, properties);
	}

	@Override
	public void persist(final Object entity) {
		entityManagerStore.get().persist(entity);
	}

	@Override
	public void detach(final Object entity) {
		entityManagerStore.get().detach(entity);
	}

	@Override
	public void remove(final Object entity) {
		entityManagerStore.get().remove(entity);
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerStore.get().getEntityManagerFactory();
	}

	@Override
	public <T> TypedQuery<T> createQuery(final String qlString, final Class<T> resultClass) {
		return entityManagerStore.get().createQuery(qlString, resultClass);
	}

	@Override
	public boolean contains(final Object entity) {
		return entityManagerStore.get().contains(entity);
	}

	@Override
	public void close() {
		entityManagerStore.get().close();
	}

	@Override
	public Query createNativeQuery(final String sqlString) {
		return entityManagerStore.get().createNativeQuery(sqlString);
	}

	@Override
	public Query createQuery(final String qlString) {
		return entityManagerStore.get().createQuery(qlString);
	}

	@Override
	public void refresh(final Object entity) {
		entityManagerStore.get().refresh(entity);
	}

	@Override
	public void refresh(final Object entity, final Map<String, Object> properties) {
		entityManagerStore.get().refresh(entity, properties);
	}

	@Override
	public void refresh(final Object entity, final LockModeType lockMode) {
		entityManagerStore.get().refresh(entity, lockMode);
	}

	@Override
	public void setProperty(final String propertyName, final Object value) {
		entityManagerStore.get().setProperty(propertyName, value);
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return entityManagerStore.get().getCriteriaBuilder();
	}

	@Override
	public void lock(final Object entity, final LockModeType lockMode, final Map<String, Object> properties) {
		entityManagerStore.get().lock(entity, lockMode, properties);
	}

	@Override
	public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockMode) {
		return entityManagerStore.get().find(entityClass, primaryKey, lockMode);
	}

	@Override
	public void refresh(final Object entity, final LockModeType lockMode, final Map<String, Object> properties) {
		entityManagerStore.get().refresh(entity, lockMode, properties);
	}

	@Override
	public LockModeType getLockMode(final Object entity) {
		return entityManagerStore.get().getLockMode(entity);
	}

	@Override
	public void joinTransaction() {
		entityManagerStore.get().joinTransaction();
	}

	@Override
	public Map<String, Object> getProperties() {
		return entityManagerStore.get().getProperties();
	}

	@Override
	public <T> T find(final Class<T> entityClass, final Object primaryKey) {
		return entityManagerStore.get().find(entityClass, primaryKey);
	}

	@Override
	public void lock(final Object entity, final LockModeType lockMode) {
		entityManagerStore.get().lock(entity, lockMode);
	}

	@Override
	public void setFlushMode(final FlushModeType flushMode) {
		entityManagerStore.get().setFlushMode(flushMode);
	}

	@Override
	public <T> T unwrap(final Class<T> cls) {
		return entityManagerStore.get().unwrap(cls);
	}

	@Override
	public <T> TypedQuery<T> createQuery(final CriteriaQuery<T> criteriaQuery) {
		return entityManagerStore.get().createQuery(criteriaQuery);
	}

	@Override
	public void flush() {
		entityManagerStore.get().flush();
	}

	@Override
	public Query createQuery(CriteriaUpdate updateQuery) {
		return entityManagerStore.get().createQuery(updateQuery);
	}

	@Override
	public Query createQuery(CriteriaDelete deleteQuery) {
		return entityManagerStore.get().createQuery(deleteQuery);
	}

	@Override
	public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
		return entityManagerStore.get().createNamedStoredProcedureQuery(name);
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
		return entityManagerStore.get().createStoredProcedureQuery(procedureName);

		}

		@Override
		public StoredProcedureQuery createStoredProcedureQuery (String procedureName, Class...resultClasses){
			return entityManagerStore.get().createStoredProcedureQuery(procedureName, resultClasses);
		}

		@Override
		public StoredProcedureQuery createStoredProcedureQuery (String procedureName, String...resultSetMappings){
			return entityManagerStore.get().createStoredProcedureQuery(procedureName, resultSetMappings);
		}

		@Override
		public boolean isJoinedToTransaction () {
			return entityManagerStore.get().isJoinedToTransaction();
		}

		@Override
		public <T> EntityGraph<T> createEntityGraph (Class < T > rootType) {
			return entityManagerStore.get().createEntityGraph(rootType);
		}

		@Override
		public EntityGraph<?> createEntityGraph (String graphName){
			return entityManagerStore.get().createEntityGraph(graphName);
		}

		@Override
		public EntityGraph<?> getEntityGraph (String graphName){
			return entityManagerStore.get().getEntityGraph(graphName);
		}

		@Override
		public <T> List<EntityGraph<? super T>> getEntityGraphs (Class < T > entityClass) {
			return entityManagerStore.get().getEntityGraphs(entityClass);
		}
	}
