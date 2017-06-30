package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.exceptions.PersistenceException;
import ar.com.larreta.rest.exceptions.BusinessException;

public abstract class LoadArgsAddInnerJoin extends BusinessListenerImpl {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		LoadArguments loadArgs = (LoadArguments) args[0];		
		loadArgs.addInnerJoin(getInnerJoin());
		return null;
	}

	public abstract String getInnerJoin();
	
}
