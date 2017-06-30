package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

public interface ValuedListener {
	public Object getValuedListener(Serializable source, Serializable target, Object... args);
}
