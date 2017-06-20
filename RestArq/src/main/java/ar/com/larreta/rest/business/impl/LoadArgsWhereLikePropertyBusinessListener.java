package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Like;
import ar.com.larreta.rest.exceptions.BusinessException;

public abstract class LoadArgsWhereLikePropertyBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		LoadArguments loadArgs = (LoadArguments) args[0];
		if ((source!=null) && (loadArgs!=null)){
			Object value = beanUtils.read(source, getProperty());
			if (!StringUtils.isEmpty((CharSequence) value)){
				Like like = new Like(loadArgs, getProperty(), value);
				loadArgs.addWhere(like);
			}	
		}
		return null;
	}
	
	public abstract String getProperty();
}
