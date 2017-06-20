package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.rest.exceptions.BusinessException;

public abstract class LoadArgsAddProjectedCollectionBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException {
		LoadArguments loadArgs = (LoadArguments) args[0];
		
		loadArgs.addProjectedCollection(getProjectedCollection());
		
		return null;
	}
	
	public abstract String getProjectedCollection();

}
