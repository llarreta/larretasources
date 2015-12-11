package ar.com.larreta.commons.services;

import java.util.Collection;

import ar.com.larreta.commons.exceptions.NotImplementedException;

public interface MessageService extends StandardService {
	public Collection load() throws NotImplementedException;
}
