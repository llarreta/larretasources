package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.stepper.SourcedListener;
import ar.com.larreta.stepper.TargetedListener;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

/**
 * Asigna una entidad de persistencia a partir de un id recibido desde un serializable 
 *
 * @param <E>
 */
public abstract class EntityAsignBusinessListener<E extends Entity> extends StepImpl implements SourcedListener, TargetedListener{

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		Serializable id = (Serializable) beanUtils.read(source, getSourceProperty());
		
		if (id!=null){
			beanUtils.write(target, getTargetProperty(), persister.get(
					TypedClassesUtils.getGenerics(EntityAsignBusinessListener.class, this, 0), id));
		}
		return null;
	}
	
}
