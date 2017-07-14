package ar.com.larreta.persistence.query;

import org.springframework.stereotype.Component;

@Component
public class SelectInstruction extends Instruction {

	public static final String INSTRUCTION_NAME = "SELECT";

	public SelectInstruction() {
		super(INSTRUCTION_NAME);
	}

}
