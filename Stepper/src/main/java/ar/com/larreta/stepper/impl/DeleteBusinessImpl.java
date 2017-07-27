package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.core.ResolvableType;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.stepper.exceptions.BusinessException;

@Transactional
public abstract class DeleteBusinessImpl<E extends Entity> extends StepImpl {
	
	private static @Log Logger LOG;	

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		Long id = (Long) source;
		
		try {
			Class<?>[] generics = ResolvableType.forClass(DeleteBusinessImpl.class, getClass()).resolveGenerics();
			
			if (generics.length==1){		
					Class<?> entityType = generics[0];
					E entity = (E) applicationContext.getBean(entityType);
					entity.setId(id);
					persister.delete(entity);
					return id;
			}
			LOG.error("No se encontraron la catidad de elementos genericos necesarios para la clase");
		} catch (Exception e){
			LOG.error("Ocurrio un error ejecutando DeleteBusinessImpl", e);
		}
		throw new BusinessException();
	}

}
