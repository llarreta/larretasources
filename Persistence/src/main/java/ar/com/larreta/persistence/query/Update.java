package ar.com.larreta.persistence.query;

public class Update extends Instruction {
	public static final String INSTRUCTION_NAME = "UPDATE";

	public Update() {
		super(INSTRUCTION_NAME);
	}
}
