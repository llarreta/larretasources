package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.tools.TypedClassesUtils;

@Transactional
public abstract class CreateBusinessImpl<J extends JSONable, E extends Entity> extends BusinessImpl {
	
	private static @Log Logger LOG;	
	
	private Set<BusinessListener> beforePersistListeners;
	private Set<BusinessListener> afterPersistListeners;

	@Override
	public Serializable execute(Serializable input) throws Exception {
		try {
			Class<?> entityType = TypedClassesUtils.getGenerics(CreateBusinessImpl.class, this, 1);
			
			E entity = (E) applicationContext.getBean(entityType);
			beanUtils.copy(input, entity);
			
			callListeners(beforePersistListeners, input, entity);
			persist(entity);
			callListeners(afterPersistListeners, input, entity);
			
			return entity.getId();
		} catch (Exception e){
			LOG.error("Ocurrio un error ejecutando CreateBusinessImpl", e);
		}
		throw new BusinessException();
	}

	protected void persist(E entity) {
		persister.save(entity);
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
