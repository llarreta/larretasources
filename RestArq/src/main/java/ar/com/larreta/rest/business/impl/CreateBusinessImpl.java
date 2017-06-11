package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.core.ResolvableType;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.JSONable;

@Transactional
public abstract class CreateBusinessImpl<J extends JSONable, E extends Entity> extends BusinessImpl {
	
	private static @Log Logger LOG;	
	
	private Set<BusinessListener> beforePersistListeners;
	private Set<BusinessListener> afterPersistListeners;

	@Override
	public Serializable execute(Serializable input) throws Exception {
		try {
			Class<?>[] generics = ResolvableType.forClass(CreateBusinessImpl.class, getClass()).resolveGenerics();
			
			if (generics.length==2){		
					Class<?> bodyType = generics[0];
					Class<?> entityType = generics[1];
					
					J json = (J) input;
					E entity = (E) applicationContext.getBean(entityType);
					beanUtils.copy(json, entity);
					
					callListeners(beforePersistListeners, json, entity);
					persist(entity);
					callListeners(afterPersistListeners, json, entity);
					
					return entity.getId();
			}
			LOG.error("No se encontraron la catidad de elementos genericos necesarios para la clase");
		} catch (Exception e){
			LOG.error("Ocurrio un error ejecutando CreateBusinessImpl", e);
		}
		throw new BusinessException();
	}

	protected void persist(E entity) {
		standardDAO.save(entity);
	}
	
	public Set<BusinessListener> getBeforePersistListeners() {
		return beforePersistListeners;
	}

	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		this.beforePersistListeners = beforePersistListeners;
	}

	public Set<BusinessListener> getAfterPersistListeners() {
		return afterPersistListeners;
	}

	public void setAfterPersistListeners(Set<BusinessListener> afterPersistListeners) {
		this.afterPersistListeners = afterPersistListeners;
	}
	
}
