package ar.com.larreta.rest.business;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.messages.Body;

public interface LoadBusinessListener {

	public void processItem(Body body, Entity entity);
	
}
