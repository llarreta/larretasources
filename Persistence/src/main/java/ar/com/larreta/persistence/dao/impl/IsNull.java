package ar.com.larreta.persistence.dao.impl;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class IsNull extends Where {

	private static final String IS_NULL = "IS NULL";

	public IsNull(LoadArguments args, String field) {
		super(args, field, IS_NULL, null);
	}

}
