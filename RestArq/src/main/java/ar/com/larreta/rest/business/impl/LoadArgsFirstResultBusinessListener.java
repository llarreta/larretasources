package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.rest.exceptions.BusinessException;

@Component(LoadArgsFirstResultBusinessListener.BUSINESS_LISTENER_NAME)
public class LoadArgsFirstResultBusinessListener extends BusinessListenerImpl {

	public static final String BUSINESS_LISTENER_NAME = "LoadArgsFirstResultBusinessListener";
	public static final String FIRST_RESULT = "firstResult";

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		LoadArguments loadArgs = (LoadArguments) args[0];
		if ((source!=null) && (loadArgs!=null)){
			Object value = beanUtils.read(source, FIRST_RESULT);
			if (value!=null){
				loadArgs.setFirstResult((Integer) value);
			}	
		}
		return null;
	}

}
