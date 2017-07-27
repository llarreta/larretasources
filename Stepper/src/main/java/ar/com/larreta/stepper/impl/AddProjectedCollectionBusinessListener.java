package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Query;
import ar.com.larreta.stepper.exceptions.BusinessException;

public abstract class AddProjectedCollectionBusinessListener extends StepImpl {

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException {
		Query loadArgs = (Query) args[0];
		loadArgs.addProjections(getProjectedCollection());
		return null;
	}
	
	public abstract String getProjectedCollection();

}
