package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Collection;

import ar.com.larreta.persistence.model.Entity;

public abstract class IdIteratorListener extends IteratorListener {

	@Override
	public void persist(Collection collection, Serializable targetToCollection) {
		Entity entity = (Entity) targetToCollection;
		collection.add(entity.getId());
	}

	@Override
	public void exectue(Serializable sourceFromCollection, Serializable targetToCollection) throws Exception {
		//Do nothing
	}

	@Override
	protected Serializable getTargetToCollection(Serializable sourceFromCollection) {
		return sourceFromCollection;
	}

}
