package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.stepper.messages.Body;

@Transactional
public abstract class UpdateBusinessImpl<B extends Body, E extends Entity> extends CreateBusinessImpl<B, E> {

	private static @Log Logger LOG;	
	
	@Override
	public void process(E entity, Serializable source, Serializable target, Object... args) {
		try {
			persister.update(entity);
		} catch (PersistenceException e) {
			LOG.error("Ocurrio un error actualizando", e);
		}
	}

}
