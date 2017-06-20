package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Equal;
import ar.com.larreta.rest.exceptions.BusinessException;

public abstract class LoadArgsWhereEqualPropertyBusinessListener extends BusinessListenerImpl {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		LoadArguments loadArgs = (LoadArguments) args[0];
		
		if ((source!=null) && (loadArgs!=null)){
			Object value = beanUtils.read(source, getSourceProperty());
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
