package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.stepper.messages.Body;

@Transactional
public abstract class UpdateBusinessImpl<B extends Body, E extends Entity> extends CreateBusinessImpl<B, E> {

	@Override
	public void process(E entity, Serializable source, Serializable target, Object... args) {
		persister.update(entity);
	}

}
