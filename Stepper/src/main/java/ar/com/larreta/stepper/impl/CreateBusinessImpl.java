package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.tools.TypedClassesUtils;

@Transactional
public abstract class CreateBusinessImpl<J extends JSONable, E extends Entity> extends StepImpl {
	
	private static @Log Logger LOG;	
	
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		try {
			Class<?> entityType = TypedClassesUtils.getGenerics(CreateBusinessImpl.class, this, 1);
			
			E entity = (E) applicationContext.getBean(entityType);
			beanUtils.copy(source, entity);
			
			process(entity, source, target, args);
			
			return entity.getId();
		} catch (Exception e){
			LOG.error("Ocurrio un error ejecutando CreateBusinessImpl", e);
		}
		throw new BusinessException();
	}

	public void process(E entity, Serializable source, Serializable target, Object... args) {
		persister.save(entity);
	}
	
}
