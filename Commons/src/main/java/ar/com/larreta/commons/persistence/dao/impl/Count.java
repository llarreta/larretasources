package ar.com.larreta.commons.persistence.dao.impl;

import ar.com.larreta.commons.persistence.exceptions.UnreportedEntityException;

public class Count extends MainEntity {

	private static final String COUNT_TEXT = "COUNT(*)";

	public Count(Class type) throws UnreportedEntityException {
		super(type, COUNT_TEXT);
	}

	public String getProjection(){
		return uniqueProjectionField;
	}
}
