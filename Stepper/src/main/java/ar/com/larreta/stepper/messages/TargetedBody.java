package ar.com.larreta.stepper.messages;

import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.validators.annotations.NotNull;

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
