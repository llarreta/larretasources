package ar.com.larreta.persistence.dao.impl;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class Equal extends Where {
	
	public static final String EQUAL_TEXT = "=";
	
	public Equal(LoadArguments args, String field, Object value) {
		super(args, field, EQUAL_TEXT, value);
	}

}
