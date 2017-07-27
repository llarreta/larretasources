package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.stepper.exceptions.BusinessException;

/**
 * Asigna la propiedad source de un objeto serializable
 * en la propiedad target de otro objeto serializable
 * @author leonel.g.larreta
 *
 */
public abstract class PropertyAsignBusinessListener extends StepImpl
		implements SourcedListener, TargetedListener, ValuedListener {

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		
		Object value = getValue(source, target, args);
		if (value==null){
			value = beanUtils.read(source, getSourceProperty());
		}
		
		beanUtils.write(target, getTargetProperty(), value);
		
		return null;
	}

	@Override
	public Object getValue(Serializable source, Serializable target, Object... args) {
		return null;
	}

}
