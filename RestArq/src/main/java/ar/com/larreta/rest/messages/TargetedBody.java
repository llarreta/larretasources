package ar.com.larreta.rest.messages;

import org.springframework.stereotype.Component;

@Component
public class TargetedBody extends Body {

	protected Long target;

	public Long getTarget() {
		return target;
	}

	public void setTarget(Long target) {
		this.target = target;
	}
	
}
