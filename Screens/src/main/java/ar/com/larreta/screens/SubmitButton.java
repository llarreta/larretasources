package ar.com.larreta.screens;

import java.util.Set;

public class SubmitButton extends Button {

	private String action;
	private Set<PropertyActionListener> properties;

	public SubmitButton() {
		super();
		ajax = Boolean.FALSE;
	}
	
	public Set<PropertyActionListener> getProperties() {
		return properties;
	}

	public void setProperties(Set<PropertyActionListener> properties) {
		this.properties = properties;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
