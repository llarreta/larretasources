package ar.com.larreta.commons.persistence.dao.impl;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public interface Whereable {

	public Where toWhere(LoadArguments args);
	
}
