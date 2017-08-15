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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.Entity;

import ch.dvbern.lib.cdifixtures.events.FixtureBeanConfigured;
import ch.dvbern.lib.cdifixtures.events.PersistentConfigurationFinished;
import ch.dvbern.lib.cdifixtures.events.PersistentConfigurationStarted;
import ch.dvbern.lib.cdipersistence.Persistence;
import ch.dvbern.lib.cditest.event.TestFinished;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

/**
 * Manages Persistence in Tests. Cleans up all persistenced Entities after Tests.
 */
@SuppressWarnings("CdiInjectionPointsInspection")
@Singleton
public class TestPersistenceManager {

	private int persistFixtures = 0;

	@Inject
	private Persistence persistence;

	private Connection connection;

	private final Queue entitiesToPersist = new LinkedList();

	public void onPersistentConfigStarted(@Observes final PersistentConfigurationStarted started) {
		persistFixtures++;
	}

	@Transactional
	public void onPersistentConfigFinished(@Observes final PersistentConfigurationFinished finished) {
		persistEntitiesAndClearQueue();
		persistFixtures--;
	}

	@SuppressWarnings("unchecked")
	private void persistEntitiesAndClearQueue() {
		while (!entitiesToPersist.isEmpty()) {
			final Object abstractEntity = entitiesToPersist.poll();
			persistence.persist(abstractEntity);
		}
		entitiesToPersist.clear();
	}

	@SuppressWarnings("unchecked")
	public void onFixtureBeanConfigured(@Observes final FixtureBeanConfigured fixtureBeanConfigured) {
		if (persistFixtures > 0 && !fixtureBeanConfigured.isNonPersistent()) {
			final Object abstractEntity = fixtureBeanConfigured.getFixtureBean();
			if (abstractEntity.getClass().isAnnotationPresent(Entity.class)) {
				entitiesToPersist.add(abstractEntity);
			}
		}
	}

	public void onTestFinished(@Observes final TestFinished testFinished) throws SQLException {
		try {
			setDBReferntialIntegrity(false);
			wipeTables();
			setDBReferntialIntegrity(true);
		} finally {
			closeConnection();
		}
	}

	private void closeConnection() {
		DbUtils.closeQuietly(connection);
		this.connection = null;
	}

	private Connection getConnection() throws SQLException {
		if (connection == null) {
			DbUtils.loadDriver("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:mem:inmemorytest", "sa", "");
		}
		return connection;
	}

	private void wipeTables() throws SQLException {
		final QueryRunner qrun = new QueryRunner();
		final List<String> tableNames = qrun.query(connection, "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.SYSTEM_TABLES WHERE TABLE_SCHEM='PUBLIC'", new
				AbstractListHandler<String>() {
			@Override
			protected String handleRow(final ResultSet rs) throws SQLException {
				return rs.getString(1);
			}
		});
		for (final String tableName : tableNames) {
			qrun.update(connection, "DELETE FROM " + tableName);
		}
	}

	private void setDBReferntialIntegrity(final boolean b) throws SQLException {
		final QueryRunner qrun = new QueryRunner();
		qrun.update(getConnection(), "SET DATABASE REFERENTIAL INTEGRITY " + (b ? "TRUE" : "FALSE"));
	}
}
