package ar.com.larreta.rest.messages;

import org.springframework.stereotype.Component;

import ar.com.larreta.validators.annotations.NotNull;

@Component
public class TargetedBody extends Body {

	@NotNull(message="target.required")
	protected Long target;

	public Long getTarget() {
		return target;
	}

	public void setTarget(Long target) {
		this.target = target;
	}
	
}
