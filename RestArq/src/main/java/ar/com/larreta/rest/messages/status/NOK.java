package ar.com.larreta.rest.messages.status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NOK extends State {

	public NOK() {
		super("NOK", "execution.with.issues");
	}


}
