package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Like;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.messages.JSONable;

public abstract class LoadArgsWhereLikePropertyBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(JSONable json, Entity entity, Object... args) {
		LoadArguments loadArgs = (LoadArguments) args[0];
		if ((json!=null) && (loadArgs!=null)){
			Object value = beanUtils.read(json, getProperty());
			if (!StringUtils.isEmpty((CharSequence) value)){
				Like like = new Like(loadArgs, getProperty(), value);
				loadArgs.addWhere(like);
			}	
		}
		return null;
	}
	
	public abstract String getProperty();
}
