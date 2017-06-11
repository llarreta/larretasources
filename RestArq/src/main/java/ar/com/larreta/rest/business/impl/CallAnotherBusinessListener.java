package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.Business;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;

public abstract class CallAnotherBusinessListener extends BusinessListenerImpl {

	public static final Long NOT_EXECUTE = new Long(-1);

	private static final String ERROR_MESSAGE = "Ocurrio un error invocando a servicio desde listener";

	private static @Log Logger LOG;	
	
	private Business business;
	
	protected JSONable json;
	
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) throws BusinessException{
		try {
			this.json = json;
			if (isExecuteAvaiable()){
				return business.execute(getParam());
			}
			return NOT_EXECUTE;
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			throw new BusinessException(ERROR_MESSAGE, e);
		}
	}
	
	public Boolean isExecuteAvaiable(){
		return Boolean.TRUE;
	}

	public abstract JSONable getParam();

}
