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

import java.util.Stack;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A store for entity managers. It is basically a ThreadLocal which stores the entity manager.
 * The {@link TransactionInterceptor} is expected to register entity manager. The application code
 * can get the current entity manager either by injecting the store or the {@link EntityManagerDelegate}.
 *
 * @author Sebastian Hennebrueder
 */

@ApplicationScoped
public class EntityManagerStoreImpl implements EntityManagerStore {

	private final Logger logger = LoggerFactory.getLogger(EntityManagerStoreImpl.class);

	@Inject
	private EntityManagerFactory emf;

	private final ThreadLocal<Stack<EntityManager>> emStackThreadLocal = new ThreadLocal<Stack<EntityManager>>();

	@Override
	public EntityManager get() {
		logger.debug("Getting the current entity manager");
		final Stack<EntityManager> entityManagerStack = emStackThreadLocal.get();
		if (entityManagerStack == null || entityManagerStack.isEmpty()) {
			// if nothing is found, we return null to cause a NullPointer exception in the business code.
			// This leeds to a nicer stack trace starting with client code.
			logger.warn("No entity manager was found. Did you forget to mark your method as transactional?");
			return null;
		} else {
			return entityManagerStack.peek();
		}
	}

	/**
	 * Creates an entity manager and stores it in a stack. The use of a stack allows to implement
	 * transaction with a 'requires new' behaviour.
	 *
	 * @return the created entity manager
	 */
	@Override
	public EntityManager createAndRegister() {
		logger.debug("Creating and registering an entity manager");
		Stack<EntityManager> entityManagerStack = emStackThreadLocal.get();
		if (entityManagerStack == null) {
			entityManagerStack = new Stack<EntityManager>();
			emStackThreadLocal.set(entityManagerStack);
		}
		final EntityManager entityManager = emf.createEntityManager();
		entityManagerStack.push(entityManager);
		return entityManager;
	}

	/**
	 * Removes an entity manager from the thread local stack. It needs to be created using the
	 * {@link #createAndRegister()} method.
	 *
	 * @param entityManager - the entity manager to remove
	 * @throws IllegalStateException in case the entity manager was not found on the stack
	 */
	@Override
	public void unregister(final EntityManager entityManager) {
		logger.debug("Unregistering an entity manager");
		final Stack<EntityManager> entityManagerStack = emStackThreadLocal.get();
		if (entityManagerStack == null || entityManagerStack.isEmpty()) {
			throw new IllegalStateException("Removing of entity manager failed. Your entity manager was not found.");
		}
		if (entityManagerStack.peek() != entityManager) {
			throw new IllegalStateException("Removing of entity manager failed. Your entity manager was not found.");
		}
		entityManagerStack.pop();
	}
}
