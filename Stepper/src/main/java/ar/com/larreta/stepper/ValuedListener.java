package ar.com.larreta.stepper;

import java.io.Serializable;

public interface ValuedListener {
	public Object getValue(Serializable source, Serializable target, Object... args);
}
