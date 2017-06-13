package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.messages.JSONable;

@Component
public class LoadArgsFirstResultBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) {
		LoadArguments loadArgs = (LoadArguments) args[0];
		if ((json!=null) && (loadArgs!=null)){
			Object value = beanUtils.read(json, "firstResult");
			if (value!=null){
				loadArgs.setFirstResult((Integer) value);
			}	
		}
		return null;
	}

}
