package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.springframework.core.ResolvableType;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.PersistBusinessListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.Body;

@Transactional
public abstract class CreateBusinessImpl<B extends Body, E extends Entity> extends BusinessImpl {
	
	private static @Log Logger LOG;	
	
	private Collection<PersistBusinessListener> listeners = new ArrayList<>();
	
	public void addListener(PersistBusinessListener listener){
		listeners.add(listener);
	}

	@Override
	public Serializable execute(Serializable input) throws Exception {
		try {
			Class<?>[] generics = ResolvableType.forClass(CreateBusinessImpl.class, getClass()).resolveGenerics();
			
			if (generics.length==2){		
					Class<?> bodyType = generics[0];
					Class<?> entityType = generics[1];
					
					B body = (B) input;
					E entity = (E) entityType.newInstance();
					beanUtils.copy(body, entity);
					
					callListeners(body, entity, "beforePersist");
					persist(entity);
					callListeners(body, entity, "afterPersist");
					
					
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
	
	protected void callListeners(B body, E entity, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		Iterator<PersistBusinessListener> it = listeners.iterator();
		while (it.hasNext()) {
			PersistBusinessListener listener = (PersistBusinessListener) it.next();
			MethodUtils.invokeExactMethod(listener, methodName, body, entity);
		}
	}

}
