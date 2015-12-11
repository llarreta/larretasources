package ar.com.larreta.commons.persistence.dao.impl;

public class Asc extends Order {

	private static final String ASC_TEXT = "ASC";

	public Asc(LoadArguments args, String field) {
		super(args, field, ASC_TEXT);
	}

}
