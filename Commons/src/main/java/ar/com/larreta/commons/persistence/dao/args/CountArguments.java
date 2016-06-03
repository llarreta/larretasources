package ar.com.larreta.commons.persistence.dao.args;

import ar.com.larreta.commons.persistence.dao.impl.Count;

public class CountArguments extends LoadArguments {

	public CountArguments(Class type) {
		super(new Count(type));
		this.getMainEntity().setLoadArguments(this);
	}

}
