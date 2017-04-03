package ar.com.larreta.persistence.dao.impl;

public class Count extends MainEntity {

	private static final String COUNT_TEXT = "COUNT(*)";

	public Count(Class type) {
		super(type, COUNT_TEXT);
	}

	public String getProjection(){
		return uniqueProjectionField;
	}
}
