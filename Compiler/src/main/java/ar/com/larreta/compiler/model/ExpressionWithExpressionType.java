package ar.com.larreta.compiler.model;

import java.util.Iterator;
import java.util.List;

import ar.com.larreta.compiler.Expression;

public class ExpressionWithExpressionType extends Expression {
	
	private Expression expression;

	private ExpressionType expressionType;

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public ExpressionType getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(ExpressionType expressionType) {
		this.expressionType = expressionType;
	}
	
	@Override
	public void process(List<JavaProgramUnit> inProgress) {
		Iterator<JavaProgramUnit> it = inProgress.iterator();
		expression = (Expression) it.next();
		expressionType = (ExpressionType) it.next();
	}
}
