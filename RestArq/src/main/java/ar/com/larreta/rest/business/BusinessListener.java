package ar.com.larreta.rest.business;

import java.io.Serializable;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;

public interface BusinessListener {

	public Serializable process(JSONable json, Entity entity, Object... args) throws BusinessException;
	
}
