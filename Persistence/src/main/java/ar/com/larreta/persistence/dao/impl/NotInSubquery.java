package ar.com.larreta.persistence.dao.impl;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class NotInSubquery extends Subquery {

	private static final String NOT_IN = "NOT IN";

	public NotInSubquery(LoadArguments args, String field, LoadArguments argsSub) {
		super(args, field, NOT_IN, argsSub);
	}

}
