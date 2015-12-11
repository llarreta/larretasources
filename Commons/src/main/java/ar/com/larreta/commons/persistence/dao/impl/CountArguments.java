package ar.com.larreta.commons.persistence.dao.impl;

public class CountArguments extends LoadArguments {

	public CountArguments(Class type) {
		super(new Count(type));
		this.getMainEntity().setLoadArguments(this);
	}

}
