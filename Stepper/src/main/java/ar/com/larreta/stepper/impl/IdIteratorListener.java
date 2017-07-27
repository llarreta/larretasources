package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import ar.com.larreta.mystic.model.Entity;

public abstract class IdIteratorListener extends IteratorListener {

	@Override
	public Serializable process(Serializable sourceFromCollection, Serializable targetToCollection) {
		Entity entity = (Entity) targetToCollection;
		return entity.getId();
	}

	@Override
	protected Serializable getTargetToCollection(Serializable sourceFromCollection) {
		return sourceFromCollection;
	}

}
