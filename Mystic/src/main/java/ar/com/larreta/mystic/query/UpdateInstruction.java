package ar.com.larreta.mystic.query;

import org.springframework.stereotype.Component;

@Component
public class UpdateInstruction extends Instruction {
	public static final String INSTRUCTION_NAME = "UPDATE";

	public UpdateInstruction() {
		super(INSTRUCTION_NAME);
	}
}
