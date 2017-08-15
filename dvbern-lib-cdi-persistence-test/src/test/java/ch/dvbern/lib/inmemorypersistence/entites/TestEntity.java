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

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

/**
 * This is a Entity that is used to test the lib
 */
@Entity
public class TestEntity {

	@Id
	private String id;
	private String attribut;

	public TestEntity(final String attribut) {
		this.attribut = attribut;
	}

	public TestEntity() {
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getAttribut() {
		return attribut;
	}

	public void setAttribut(final String attribut) {
		this.attribut = attribut;
	}

	@PrePersist
	public void onPrePersist() {
		setId(UUID.randomUUID().toString());
	}
}
