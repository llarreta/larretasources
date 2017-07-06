package ar.com.larreta.rest.messages.status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component
@Scope(Const.PROTOTYPE)
public class OK extends State {

	public OK() {
		super("OK", "successful.execution");
	}

}
