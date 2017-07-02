package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import ar.com.larreta.rest.business.BusinessListener;

public abstract class IteratorCallBusinessListener<E extends Serializable> extends IteratorListener<E> {

	private Set<BusinessListener> callBusinessListeners;

	public Set<BusinessListener> getCallBusinessListeners() {
		return callBusinessListeners;
	}

	public void setCallBusinessListeners(Set<BusinessListener> callBusinessListeners) {
		this.callBusinessListeners = callBusinessListeners;
	}

	@Override
	public void exectue(Serializable sourceFromCollection, Serializable targetToCollection) throws Exception {
		BusinessImpl.callListeners(callBusinessListeners, sourceFromCollection, targetToCollection, null);
	}

	@Override
	public Collection initializePersist(Serializable target) {
		//Do nothing
		return null;
	}

	@Override
	public void persist(Collection collection, Serializable targetToCollection) {
		//Do nothing
	}
}
