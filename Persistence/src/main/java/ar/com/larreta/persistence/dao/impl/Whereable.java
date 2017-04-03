package ar.com.larreta.persistence.dao.impl;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public interface Whereable {

	public Where toWhere(LoadArguments args);
	
}
