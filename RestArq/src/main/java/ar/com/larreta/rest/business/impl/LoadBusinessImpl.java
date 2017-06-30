package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.rest.messages.LoadBody;
import ar.com.larreta.tools.TypedClassesUtils;

@Transactional
public abstract class LoadBusinessImpl <B extends JSONable, E extends Entity> extends BusinessImpl {

	private static @Log Logger LOG;	
	
	private Set<BusinessListener> beforeLoadListeners;
	private Set<BusinessListener> afterLoadListeners;
	
	@Override
	public Serializable execute(Serializable input) throws Exception {
		try {
			Class<?> loadDataType 	= TypedClassesUtils.getGenerics(LoadBusinessImpl.class, this, 0);
			Class<?> entityType 	= TypedClassesUtils.getGenerics(LoadBusinessImpl.class, this, 1);

			JSONableCollection<B> jsonableCollection = new JSONableCollection<B>();	
			LoadBody<B> response = new LoadBody<>();
			response.setResult(jsonableCollection);
			
			LoadArguments args = createLoadArgs(entityType, input);
			
			callListeners(beforeLoadListeners,(JSONable) input, null, args);
			
			Collection result = standardDAO.load(args);
				
			if (result!=null){
				Iterator<E> it = result.iterator();
				while (it.hasNext()) {
					E entity = (E) it.next();
					B bodyElement = (B) applicationContext.getBean(loadDataType);
					
					beanUtils.copy(entity, bodyElement);
					
					callListeners(afterLoadListeners, entity, bodyElement, input);
					
					jsonableCollection.add(bodyElement);
				}
			}
			
			Integer firstResult = args.getFirstResult();
			if (firstResult==null){
				firstResult=1;
			}
			response.setFirstResult(firstResult);
			
			Integer maxResults = args.getMaxResults();
			if (maxResults==null){
				maxResults = result.size();
			}
			response.setMaxResults(maxResults);
			return response;
		} catch (Exception e){
			LOG.error("Ocurrio un error ejecutando LoadBusinessImpl", e);
		}
		throw new BusinessException();
	}

	protected LoadArguments createLoadArgs(Class<?> entityType, Serializable input) {
		return new LoadArguments(entityType);
	}

	public Set<BusinessListener> getBeforeLoadListeners() {
		return beforeLoadListeners;
	}

	public void setBeforeLoadListeners(Set<BusinessListener> beforeLoadListeners) {
		this.beforeLoadListeners = beforeLoadListeners;
	}

	public Set<BusinessListener> getAfterLoadListeners() {
		return afterLoadListeners;
	}

	public void setAfterLoadListeners(Set<BusinessListener> afterLoadListeners) {
		this.afterLoadListeners = afterLoadListeners;
	}
}
