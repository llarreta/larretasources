package ar.com.larreta.compiler;

import ar.com.larreta.compiler.model.JavaProgramUnit;

public class Expression extends JavaProgramUnit{

	protected String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Expression(){}
	
	public Expression(String text){
		this.text = text;
	}

	@Override
	public int hashCode() {
		return text.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Expression) {
			Expression exp = (Expression) obj;
			return text.equals(exp.getText());
		}
		return super.equals(obj);
	}

	
	@Override
	public String toString() {
		return text;
	}

}
