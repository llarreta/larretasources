package ar.com.larreta.commons.persistence.dao.impl;

public class Equal extends Where {
	
	public static final String EQUAL_TEXT = "=";
	
	public Equal(LoadArguments args, String field, Object value) {
		super(args, field, EQUAL_TEXT, value);
	}

}
