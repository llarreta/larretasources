package ar.com.larreta.rest.business.impl;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.messages.JSONable;

@Component
public class LoadArgsMaxResultsBusinessListener extends BusinessListenerImpl  {
	@Override
	public void process(JSONable json, Entity entity, Object... args) {
		LoadArguments loadArgs = (LoadArguments) args[0];
		if ((json!=null) && (loadArgs!=null)){
			Object value = beanUtils.getValue(json, "maxResults");
			if (value!=null){
				loadArgs.setMaxResults((Integer) value);
			}	
		}
	}
}
