package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import ar.com.larreta.persistence.exceptions.PersistenceException;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.tools.TypedClassesUtils;

/**
 * Asigna un objeto serializable sobre un objeto de tipo json
 * en su totalidad 
 *
 * @param <E>
 */
public abstract class JsonAsignBusinessListener <E extends JSONable>  extends BusinessListenerImpl
		implements SourcedListener, TargetedListener {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {

		JSONable json = (JSONable) applicationContext.getBean(TypedClassesUtils.getGenerics(JsonAsignBusinessListener.class, this, 0));
		Serializable data = (Serializable) beanUtils.read(source, getSourceProperty());
		
		beanUtils.copy(data, json);
		
		beanUtils.write(target, getTargetProperty(), json);
		
		return null;
	}

}
