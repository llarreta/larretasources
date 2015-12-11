package ar.com.larreta.commons.persistence.dao.impl;

public class MaxArguments extends LoadArguments {

	public MaxArguments(Class type, String field) {
		super(new Max(type, field));
		this.getMainEntity().setLoadArguments(this);
	}

}
