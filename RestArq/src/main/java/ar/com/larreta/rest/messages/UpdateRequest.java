package ar.com.larreta.rest.messages;

import javax.validation.Valid;

import ar.com.larreta.prototypes.JSONable;
import ar.com.larreta.validators.annotations.NotNull;

public class UpdateRequest<T extends JSONable> extends Request {

	@NotNull(avaiableActions = { "create", "update" })
	@Valid
	private T target;

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}
	
}
