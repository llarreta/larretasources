package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.rest.exceptions.BusinessException;

@Component
public class LoadArgsMaxResultsBusinessListener extends BusinessListenerImpl  {
	private static final String MAX_RESULTS = "maxResults";

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		LoadArguments loadArgs = (LoadArguments) args[0];
		if ((source!=null) && (loadArgs!=null)){
			Object value = beanUtils.read(source, MAX_RESULTS);
			if (value!=null){
				loadArgs.setMaxResults((Integer) value);
			}	
		}
		return null;
	}
}
