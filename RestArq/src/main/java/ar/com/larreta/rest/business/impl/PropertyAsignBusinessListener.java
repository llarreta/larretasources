package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import ar.com.larreta.persistence.exceptions.PersistenceException;
import ar.com.larreta.rest.exceptions.BusinessException;

/**
 * Asigna la propiedad source de un objeto serializable
 * en la propiedad target de otro objeto serializable
 * @author leonel.g.larreta
 *
 */
public abstract class PropertyAsignBusinessListener extends BusinessListenerImpl
		implements SourcedListener, TargetedListener, ValuedListener {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		
		Object value = getValuedListener(source, target, args);
		if (value==null){
			value = beanUtils.read(source, getSourceProperty());
		}
		
		beanUtils.write(target, getTargetProperty(), value);
		
		return null;
	}

	@Override
	public Object getValuedListener(Serializable source, Serializable target, Object... args) {
		return null;
	}

}
