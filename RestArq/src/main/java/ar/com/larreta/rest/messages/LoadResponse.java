package ar.com.larreta.rest.messages;

import java.util.Collection;

import ar.com.larreta.prototypes.JSONable;

public class LoadResponse<T extends JSONable> extends Response {

	private Collection<T> result;

	public Collection<T> getResult() {
		return result;
	}

	public void setResult(Collection<T> result) {
		this.result = result;
	}
	
}
