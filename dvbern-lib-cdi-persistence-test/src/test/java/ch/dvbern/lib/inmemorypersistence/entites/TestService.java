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

package ch.dvbern.lib.inmemorypersistence.entites;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ch.dvbern.lib.cdipersistence.Persistence;
import ch.dvbern.lib.cditest.runner.CDIRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Beispiel fuer einen Service-Test
 */
@SuppressWarnings({ "CdiInjectionPointsInspection", "JUnitTestClassNamingConvention" })
@RunWith(CDIRunner.class)
public class TestService {

	@Inject
	private Persistence<TestEntity> persistence;

	@Test
	public void doTest() {
		final TestEntity entity = new TestEntity();
		final TestEntity persistedEntity = persistence.persist(entity);
		Assert.assertNotNull(persistedEntity);
		Assert.assertNotNull(persistedEntity.getId());
	}

	@Test
	public void doTestRemove() {
		final TestEntity entity = new TestEntity();
		TestEntity persistedEntity = persistence.persist(entity);
		Assert.assertNotNull(persistedEntity);
		Assert.assertNotNull(persistedEntity.getId());
		//im cdi container muss mit geReference statt mit find  oder merge vor remove gearbeitet werden
		persistedEntity = persistence.getReference(TestEntity.class, persistedEntity.getId());
		persistence.remove(persistedEntity);
		persistedEntity = persistence.find(TestEntity.class, persistedEntity.getId());
		Assert.assertNull(persistedEntity);

	}

	@Test
	public void testGetCriteriaSingleResult() {
		final TestEntity entity = new TestEntity("testattribut");
		final TestEntity persistedEntity = persistence.persist(entity);
		final CriteriaBuilder builder = persistence.getCriteriaBuilder();
		final CriteriaQuery<TestEntity> query = builder.createQuery(TestEntity.class);
		final Root<TestEntity> testEntityQuery = query.from(TestEntity.class);
		query.where(builder.equal(testEntityQuery.get("attribut"), persistedEntity.getAttribut()));
		//actual test
		final TestEntity loadedEntity = persistence.getCriteriaSingleResult(query);
		Assert.assertNotNull(loadedEntity);
		Assert.assertEquals(persistedEntity.getAttribut(), loadedEntity.getAttribut());

		final TestEntity entity2 = new TestEntity("testattribut");
		persistence.persist(entity2);

		try {
			persistence.getCriteriaSingleResult(query);
			Assert.fail("Attribut is not unique, should throw exception");

		} catch (final NonUniqueResultException exception) {
			//noop
		}
	}

	@Test
	public void testMaxResults() {
		// 10 Objekte erstellen
		for (int i = 0; i < 10; i++) {
			final TestEntity entity = new TestEntity("testattribut");
			persistence.persist(entity);
		}
		final CriteriaBuilder builder = persistence.getCriteriaBuilder();
		final CriteriaQuery<TestEntity> query = builder.createQuery(TestEntity.class);
		final Root<TestEntity> testEntityQuery = query.from(TestEntity.class);
		// Fall 1: Weniger als Max vorhanden
		final List<TestEntity> resultWithoutMax = persistence.getCriteriaResults(query);
		final List<TestEntity> resultWithMaxNotExceeded = persistence.getCriteriaResults(query, 11);
		final List<TestEntity> resultWithMaxExceeded = persistence.getCriteriaResults(query, 5);
		Assert.assertEquals(10, resultWithoutMax.size());
		Assert.assertEquals(10, resultWithMaxNotExceeded.size());
		Assert.assertEquals(5, resultWithMaxExceeded.size());
	}
}
