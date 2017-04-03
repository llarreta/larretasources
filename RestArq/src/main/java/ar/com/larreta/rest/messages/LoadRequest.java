package ar.com.larreta.rest.messages;

import ar.com.larreta.prototypes.JSONable;

public class LoadRequest<T extends JSONable> extends Request {
	
	private T filter;

	public T getFilter() {
		return filter;
	}

	public void setFilter(T filter) {
		this.filter = filter;
	}
	
}
