package ar.com.larreta.rest.business;

import java.io.Serializable;

import ar.com.larreta.rest.exceptions.BusinessException;

public interface BusinessListener {

	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException;
	
}
