package ar.com.larreta.commons.persistence.dao.impl;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public class Asc extends Order {

	private static final String ASC_TEXT = "ASC";

	public Asc(LoadArguments args, String field) {
		super(args, field, ASC_TEXT);
	}

}
