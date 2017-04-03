package ar.com.larreta.rest.business;

import ar.com.larreta.prototypes.JSONable;

public interface Business {

	public JSONable execute(JSONable input);
	
}
