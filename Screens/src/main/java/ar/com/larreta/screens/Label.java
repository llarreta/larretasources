package ar.com.larreta.screens;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;

public class Label extends ScreenElement {
	private String value;

	public Label(){}
	
	public Label(String value){
		setValue(value);
	}
	
	public String getValue() {
		return StandardControllerImpl.getMessage(value);
	}

	public void setValue(String value) {
		this.value = value;
	}
}
