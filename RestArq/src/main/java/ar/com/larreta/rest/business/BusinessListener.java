package ar.com.larreta.rest.business;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.messages.JSONable;

public interface BusinessListener {

	public void process(JSONable json, Entity entity, Object... args);
	
}
