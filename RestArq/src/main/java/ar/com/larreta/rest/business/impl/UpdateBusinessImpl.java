package ar.com.larreta.rest.business.impl;

import javax.transaction.Transactional;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.messages.Body;

@Transactional
public abstract class UpdateBusinessImpl<B extends Body, E extends Entity> extends CreateBusinessImpl<B, E> {

	@Override
	protected void persist(E entity) {
		standardDAO.update(entity);
	}

}
