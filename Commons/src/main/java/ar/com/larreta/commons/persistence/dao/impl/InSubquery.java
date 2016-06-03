package ar.com.larreta.commons.persistence.dao.impl;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public class InSubquery extends Subquery {
	private static final String IN = "IN";

	public InSubquery(LoadArguments args, String field, LoadArguments argsSub) {
		super(args, field, IN, argsSub);
	}
}
