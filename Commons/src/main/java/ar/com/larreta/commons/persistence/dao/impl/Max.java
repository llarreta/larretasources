package ar.com.larreta.commons.persistence.dao.impl;

import ar.com.larreta.commons.persistence.exceptions.UnreportedEntityException;

public class Max extends MainEntity {
	
	private static final String MAX_TEXT = "MAX(?)";

	public Max(Class type, String field) throws UnreportedEntityException {
		super(type, MAX_TEXT.replace("?", field));
	}

	public String getProjection(){
		return uniqueProjectionField;
	}
}
