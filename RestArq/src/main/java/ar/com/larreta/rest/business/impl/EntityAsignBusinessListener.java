package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class EntityAsignBusinessListener<E extends Entity> extends BusinessListenerImpl implements SourcedListener, TargetedListener{

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException {
		Serializable id = (Serializable) beanUtils.read(source, getSourceProperty());
		
		if (id!=null){
			beanUtils.write(target, getTargetProperty(), standardDAO.getEntity(
					TypedClassesUtils.getGenerics(EntityAsignBusinessListener.class, this, 0), id));
		}
		return null;
	}
	
}
