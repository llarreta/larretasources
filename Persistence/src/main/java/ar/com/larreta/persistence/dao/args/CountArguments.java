package ar.com.larreta.persistence.dao.args;

import ar.com.larreta.persistence.dao.impl.Count;

public class CountArguments extends LoadArguments {

	public CountArguments(Class type){
		super(new Count(type));
		this.getMainEntity().setLoadArguments(this);
	}

}
