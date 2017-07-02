package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

public interface ValuedListener {
	public Object getValue(Serializable source, Serializable target, Object... args);
}
