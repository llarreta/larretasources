package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.core.ResolvableType;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.LoadBusinessListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.JSONableCollectionBody;

@Transactional
public abstract class LoadBusinessImpl <B extends Body, E extends Entity> extends BusinessImpl {

	private static @Log Logger LOG;	
	
	private Collection<LoadBusinessListener> listeners = new ArrayList<>();
	
	public void addListener(LoadBusinessListener listener){
		listeners.add(listener);
	}
	
	@Override
	public Serializable execute(Serializable input) throws Exception {
		try {
			Class<?>[] generics = ResolvableType.forClass(LoadBusinessImpl.class, getClass()).resolveGenerics();
			
			if (generics.length==2){		
				Class<?> bodyType = generics[0];
				Class<?> entityType = generics[1];
					
				JSONableCollectionBody<B> collectionBody = new JSONableCollectionBody<B>();	

				Collection result = standardDAO.load(entityType);
					
				if (result!=null){
					Iterator<E> it = result.iterator();
					while (it.hasNext()) {
						E entity = (E) it.next();
						B bodyElement = (B) bodyType.newInstance();
						
						beanUtils.copy(entity, bodyElement);
						
						callListeners(entity, bodyElement);
						
						collectionBody.add(bodyElement);
					}
				}
				return collectionBody;
			}
			LOG.error("No se encontraron la catidad de elementos genericos necesarios para la clase");
		} catch (Exception e){
			LOG.error("Ocurrio un error ejecutando DeleteBusinessImpl", e);
		}
		throw new BusinessException();
	}

	private void callListeners(E entity, B bodyElement) {
		Iterator<LoadBusinessListener> it = listeners.iterator();
		while (it.hasNext()) {
			LoadBusinessListener listener = (LoadBusinessListener) it.next();
			listener.processItem(bodyElement, entity);
		}
	}

}
