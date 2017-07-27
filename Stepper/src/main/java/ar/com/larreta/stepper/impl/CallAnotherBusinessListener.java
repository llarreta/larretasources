package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.exceptions.BusinessException;

@Deprecated
//FIXME: Revisar necesidad de esta clase
public abstract class CallAnotherBusinessListener extends StepImpl {

	public static final Long NOT_EXECUTE = new Long(-1);

	private static final String ERROR_MESSAGE = "Ocurrio un error invocando a servicio desde listener";

	private static @Log Logger LOG;	
	
	private Step business;
	
	public Step getBusiness() {
		return business;
	}

	public void setBusiness(Step business) {
		this.business = business;
	}

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		try {
			if (isExecuteAvaiable(source, target, args)){
				return business.execute(getParam(source, target, args), null, null);
			}
			return NOT_EXECUTE;
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			throw new BusinessException(ERROR_MESSAGE, e);
		}
	}
	
	public Boolean isExecuteAvaiable(Serializable source, Serializable target, Object... args){
		return Boolean.TRUE;
	}

	public abstract Serializable getParam(Serializable source, Serializable target, Object... args);

}
