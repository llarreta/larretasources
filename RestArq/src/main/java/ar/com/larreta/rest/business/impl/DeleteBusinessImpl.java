package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.core.ResolvableType;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.exceptions.BusinessException;

@Transactional
public abstract class DeleteBusinessImpl<E extends Entity> extends BusinessImpl {
	
	private static @Log Logger LOG;	

	@Override
	public Serializable execute(Serializable input) throws Exception {
		Long id = (Long) input;
		
		try {
			Class<?>[] generics = ResolvableType.forClass(DeleteBusinessImpl.class, getClass()).resolveGenerics();
			
			if (generics.length==1){		
					Class<?> entityType = generics[0];
					E entity = (E) applicationContext.getBean(entityType);
					entity.setId(id);
					standardDAO.delete(entity);
					return id;
			}
			LOG.error("No se encontraron la catidad de elementos genericos necesarios para la clase");
		} catch (Exception e){
			LOG.error("Ocurrio un error ejecutando DeleteBusinessImpl", e);
		}
		throw new BusinessException();
	}

}
