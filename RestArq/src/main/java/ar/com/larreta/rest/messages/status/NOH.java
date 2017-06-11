package ar.com.larreta.rest.messages.status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NOH extends State {
	public NOH() {
		super("NOH", "not.help.avaiable");
	}
}
