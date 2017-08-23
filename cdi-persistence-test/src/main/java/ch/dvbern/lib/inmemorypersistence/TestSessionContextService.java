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

import java.security.Principal;

import javax.enterprise.inject.Alternative;

import ch.dvbern.lib.cdipersistence.ISessionContextService;

/**
 * Alternativer SessionContext fuer Tests.
 * Im WELD-Container gibt es keinen SessionContext -> dies ist ein Beispiel, wie z.b. der eingeloggte
 * Benutzer in einem Test "gemockt" werden kann.
 */
@Alternative
public class TestSessionContextService implements ISessionContextService {

	@Override
	public Principal getCallerPrincipal() {
		return new Principal() {
			@Override
			public String getName() {
				return "123456";
			}
		};
	}

	@Override
	public boolean isCallerInRole(final String s) {
		return true;
	}
}
