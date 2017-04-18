package ar.com.larreta.rest.messages.status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NOP extends State {
	public NOP() {
		super("NOP", "not.permited");
	}
}
