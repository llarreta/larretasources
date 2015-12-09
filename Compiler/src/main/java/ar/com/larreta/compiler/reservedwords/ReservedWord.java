package ar.com.larreta.compiler.reservedwords;

import ar.com.larreta.compiler.model.JavaProgramUnit;

public abstract class ReservedWord extends JavaProgramUnit {

	private String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public ReservedWord(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		return text.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReservedWord) {
			ReservedWord exp = (ReservedWord) obj;
			return text.equals(exp.getText());
		}
		return super.equals(obj);
	}

	
	@Override
	public String toString() {
		return text;
	}
}
