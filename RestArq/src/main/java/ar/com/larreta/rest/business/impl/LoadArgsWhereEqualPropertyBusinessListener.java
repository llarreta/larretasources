package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Equal;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.messages.JSONable;

public abstract class LoadArgsWhereEqualPropertyBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) {
		LoadArguments loadArgs = (LoadArguments) args[0];
		if ((json!=null) && (loadArgs!=null)){
			Object value = beanUtils.getValue(json, getSourceProperty());
			if (value!=null){
				Equal equal = new Equal(loadArgs, getTargetProperty(), value);
				loadArgs.addWhere(equal);
			}	
		}
		return null;
	}
	
	public abstract String getSourceProperty();
	public abstract String getTargetProperty();
}
