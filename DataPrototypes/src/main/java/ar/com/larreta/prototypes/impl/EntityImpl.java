package ar.com.larreta.prototypes.impl;

import ar.com.larreta.prototypes.JSONable;

public class EntityImpl extends JSONable {
	
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
