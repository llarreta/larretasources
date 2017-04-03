package ar.com.larreta.persistence.dao.impl;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class Desc extends Order {

	private static final String DESC_TEXT = "DESC";

	public Desc(LoadArguments args, String field) {
		super(args, field, DESC_TEXT);
	}

}
