package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Query;
import ar.com.larreta.stepper.exceptions.BusinessException;

public abstract class AddInnerJoin extends StepImpl {

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException {
		Query query = (Query) args[0];		
		query.addInnerJoin(getInnerJoin());
		return null;
	}

	public abstract String getInnerJoin();
	
}
