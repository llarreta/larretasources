package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.exceptions.BusinessException;

public abstract class CallAnotherBusinessListener extends BusinessListenerImpl {

	public static final Long NOT_EXECUTE = new Long(-1);

	private static final String ERROR_MESSAGE = "Ocurrio un error invocando a servicio desde listener";

	private static @Log Logger LOG;	
	
	private Business business;
	
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		try {
			if (isExecuteAvaiable(source, target, args)){
				return business.execute(getParam(source, target, args));
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
