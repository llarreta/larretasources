package ar.com.larreta.compiler.model;

import java.util.List;

public class JavaClass extends JavaProgramUnit {
	private List<JavaProgramUnit> expressions;
	
	public List<JavaProgramUnit> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<JavaProgramUnit> expressions) {
		this.expressions = expressions;
	}

	@Override
	public void process(List<JavaProgramUnit> inProgress) {
		expressions = inProgress;
	}
}
