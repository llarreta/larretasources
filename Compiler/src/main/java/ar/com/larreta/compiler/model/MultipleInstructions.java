package ar.com.larreta.compiler.model;

import java.util.List;

@Deprecated
public class MultipleInstructions extends Instruction {

	private List<JavaProgramUnit> instructions;

	public List<JavaProgramUnit> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<JavaProgramUnit> instructions) {
		this.instructions = instructions;
	}

	@Override
	public void process(List<JavaProgramUnit> inProgress) {
		this.instructions = inProgress;
	}	
}
