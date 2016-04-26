package ar.com.larreta.screens;

public class Div extends Container {
	
	private String className;

	public Div(String className){
		setClassName(className);
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
