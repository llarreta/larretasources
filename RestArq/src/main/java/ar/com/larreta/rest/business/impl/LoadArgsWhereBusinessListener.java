package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Like;
import ar.com.larreta.persistence.dao.impl.Where;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class LoadArgsWhereBusinessListener<W extends Where> extends BusinessListenerImpl implements SourcedListener, TargetedListener, ValuedListener{

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		LoadArguments loadArgs = (LoadArguments) args[0];
		
		if ((source!=null) && (loadArgs!=null)){
			Object value = getValue(source, target, args);
			if (value==null){
				beanUtils.read(source, getSourceProperty());
			}
			if (!StringUtils.isEmpty((CharSequence) value)){
				Like like = new Like(loadArgs, getTargetProperty(), value);
				Collection instanceArgs = new ArrayList<>();
				instanceArgs.add(loadArgs);
				instanceArgs.add(getTargetProperty());
				instanceArgs.add(value);
				loadArgs.addWhere((Where) beanUtils.newInstance(
						TypedClassesUtils.getGenerics(LoadArgsWhereBusinessListener.class, this, 0),
						instanceArgs.toArray()));
			}	
		}
		return null;
	}

	@Override
	public Object getValue(Serializable source, Serializable target, Object... args) {
		return null;
	}
	
}
