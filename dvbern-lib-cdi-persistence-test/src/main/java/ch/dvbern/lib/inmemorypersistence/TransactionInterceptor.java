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

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@Interceptor
@Transactional
public class TransactionInterceptor {

	@Inject
	private EntityManagerStore entityManagerStore;

	private final Logger logger = LoggerFactory.getLogger(TransactionInterceptor.class);

	@SuppressWarnings({ "ProhibitedExceptionDeclared", "ProhibitedExceptionThrown" }) // Wir werfen weiter, was daherkommt
	@AroundInvoke
	public Object runInTransaction(final InvocationContext invocationContext) throws Exception {
		final EntityManager em = entityManagerStore.createAndRegister();
		Object result = null;
		try {
			em.getTransaction().begin();
			result = invocationContext.proceed();
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
					logger.debug("Rolled back transaction");
				}
			} catch (Exception e1) {
				logger.warn("Rollback of transaction failed -> " + e1);
			}
			throw e;
		} finally {
			if (em != null) {
				entityManagerStore.unregister(em);
				em.close();
			}
		}
		return result;
	}
}
