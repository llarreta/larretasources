package ar.com.larreta.stepper.messages.status;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component
@Scope(Const.PROTOTYPE)
public class NOP extends State {
	public NOP() {
		super("NOP", "not.permited");
	}
}
