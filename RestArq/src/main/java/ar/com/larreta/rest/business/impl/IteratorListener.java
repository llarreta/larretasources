package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class IteratorListener<E extends Serializable>  extends BusinessListenerImpl implements SourcedListener, TargetedListener{

	private static @Log Logger LOG;
	
	private Set<BusinessListener> beforeIterateListeners;
	private Set<BusinessListener> afterIterateListeners;

	public String property;
	
	public IteratorListener(String property) {
		super();
		this.property = property;
	}

	@Override
	public String getSourceProperty() {
		return property;
	}

	@Override
	public String getTargetProperty() {
		return property;
	}
	
	public Set<BusinessListener> getBeforeIterateListeners() {
		return afterIterateListeners;
	}

	public void setBeforeIterateListeners(Set<BusinessListener> beforeIterateListeners) {
		this.afterIterateListeners = beforeIterateListeners;
	}

	public Set<BusinessListener> getAfterIterateListeners() {
		return afterIterateListeners;
	}

	public void setAfterIterateListeners(Set<BusinessListener> afterIterateListeners) {
		this.afterIterateListeners = afterIterateListeners;
	}
	
	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		Collection collection = (Collection) beanUtils.newInstanceType(target, getTargetProperty());
		beanUtils.write(target, getTargetProperty(), collection);

		Iterator it = beanUtils.iterator(source, getSourceProperty());
		
		while (it.hasNext()) {
			try {
				Serializable sourceFromCollection = (Serializable) it.next();
				Serializable targetToCollection = (Serializable) applicationContext.getBean(
							TypedClassesUtils.getGenerics(IteratorListener.class, this, 0));

				beanUtils.copy(sourceFromCollection, targetToCollection);
				
				BusinessImpl.callListeners(afterIterateListeners, sourceFromCollection, targetToCollection, null);
				collection.add(targetToCollection);
				BusinessImpl.callListeners(beforeIterateListeners, sourceFromCollection, targetToCollection, null);
			} catch (Exception e){
				LOG.error("Ocurrio un error en IteratorListener", e);
			}
		}
		
		return null;
	}


}
