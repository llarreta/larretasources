package ar.com.larreta.persistence.dao.impl;

public class Max extends MainEntity {
	
	private static final String MAX_TEXT = "MAX(?)";

	public Max(Class type, String field){
		super(type, MAX_TEXT.replace("?", field));
	}

	public String getProjection(){
		return uniqueProjectionField;
	}
}
