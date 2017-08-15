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

import javax.persistence.EntityManager;

public interface EntityManagerStore {

	/**
	 * Looks for the current entity manager and returns it. If no entity manager
	 * was found, this method logs a warn message and returns null. This will cause a NullPointerException in most
	 * cases and will cause a stack trace starting from your service method.
	 *
	 * @return the currently used entity manager or {@code null} if none was found
	 */
	EntityManager get();

	/**
	 * Creates an entity manager and stores it in a stack. The use of a stack allows to implement
	 * transaction with a 'requires new' behaviour.
	 *
	 * @return the created entity manager
	 */
	EntityManager createAndRegister();

	/**
	 * Removes an entity manager from the thread local stack. It needs to be created using the
	 * {@link #createAndRegister()} method.
	 *
	 * @param entityManager - the entity manager to remove
	 * @throws IllegalStateException in case the entity manager was not found on the stack
	 */
	void unregister(EntityManager entityManager);

}
