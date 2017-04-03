package ar.com.larreta.rest.messages;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ar.com.larreta.prototypes.JSONable;

public class UpdateRequest<T extends JSONable> extends Request {

	@NotNull(message="target required")
	@Valid
	private T target;

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}
	
}
