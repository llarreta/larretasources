package ar.com.larreta.compiler.model;

import java.util.List;

import ar.com.larreta.compiler.Expression;

public class Cast extends Expression {
	protected List<JavaProgramUnit> expressions;

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
