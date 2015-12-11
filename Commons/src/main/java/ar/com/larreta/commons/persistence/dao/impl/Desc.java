package ar.com.larreta.commons.persistence.dao.impl;

public class Desc extends Order {

	private static final String DESC_TEXT = "DESC";

	public Desc(LoadArguments args, String field) {
		super(args, field, DESC_TEXT);
	}

}
