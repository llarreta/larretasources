package ar.com.larreta.rest.business;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.messages.Body;

public interface PersistBusinessListener {

	public void beforePersist(Body body, Entity entity);
	public void afterPersist(Body body, Entity entity);
	
}
