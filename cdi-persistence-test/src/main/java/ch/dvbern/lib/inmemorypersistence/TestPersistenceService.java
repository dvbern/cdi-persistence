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

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import ch.dvbern.lib.cdipersistence.Persistence;

/**
 * Alternative Persistenz fuer Tests.
 */
@Alternative
@Transactional
@Dependent
public class TestPersistenceService implements Persistence {

	@Inject
	private EntityManager entityManager;

	@Override
	public <T> T persist(final T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public <T> T merge(final T entity) {
		return entityManager.merge(entity);
	}

	@Override
	public <T> void remove(final T entity) {
		entityManager.remove(entity);
	}

	@Override
	public <T> T find(final Class<T> entityClass, final Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	@Override
	public <T> T getReference(final Class<T> entityClass, final Object primaryKey) {
		return entityManager.getReference(entityClass, primaryKey);
	}

	@Override
	public <T> void remove(final Class<T> entityClass, final Object primaryKey) {
		final T entity = entityManager.find(entityClass, primaryKey);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	@Override
	public <T> List<T> getCriteriaResults(final CriteriaQuery<T> query) {
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public <T> List<T> getCriteriaResults(final CriteriaQuery<T> query, int maxResults) {
		TypedQuery<T> query1 = entityManager.createQuery(query);
		query1.setMaxResults(maxResults);
		return query1.getResultList();
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public <T> T getCriteriaSingleResult(final CriteriaQuery<T> query) {
		try {
			return entityManager.createQuery(query).getSingleResult();
		} catch (NoResultException ignored) {
			return null;
		}
	}
}
