package ar.com.larreta.mystic.query;

import org.springframework.stereotype.Component;

@Component
public class DeleteInstruction extends Instruction {
	public static final String INSTRUCTION_NAME = "DELETE";

	public DeleteInstruction() {
		super(INSTRUCTION_NAME);
	}
}
