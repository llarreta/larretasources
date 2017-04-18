package ar.com.larreta.rest.messages.status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NOT extends State {

	public NOT() {
		super("NOT", "resource.not.found");
	}

}
