package ar.com.larreta.compiler.model;

import java.util.List;

public class Import extends Instruction {

	@Override
	public void process(List<JavaProgramUnit> inProgress) {
		expressions = inProgress;
	}

}
