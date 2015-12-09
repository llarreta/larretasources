package ar.com.larreta.compiler.model;

import java.util.List;

public class Instruction extends JavaProgramUnit {

	protected List<JavaProgramUnit> expressions;

	@Override
	public String toString() {
		return expressions.toString();
	}
	
	public List<JavaProgramUnit> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<JavaProgramUnit> expressions) {
		this.expressions = expressions;
	}

	@Override
	public void process(List<JavaProgramUnit> inProgress) {
		this.expressions = inProgress;
	}	
}
