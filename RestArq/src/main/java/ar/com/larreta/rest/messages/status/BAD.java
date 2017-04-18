package ar.com.larreta.rest.messages.status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BAD extends State {
	public BAD() {
		super("BAD", "bad.input");
	}
}
