package ar.com.larreta.persistence.query;

public class Delete extends Instruction {
	public static final String INSTRUCTION_NAME = "DELETE";

	public Delete() {
		super(INSTRUCTION_NAME);
	}
}
