package ar.com.larreta.screens;

import java.util.Set;

public class AjaxButton extends Button {

	private String actionListener;
	private String update;
	private Set<Attribute> attributes;

	public AjaxButton() {
		super();
		ajax = Boolean.TRUE;
	}
	
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
	
	public String getActionListener() {
		return actionListener;
	}

	public void setActionListener(String actionListener) {
		this.actionListener = actionListener;
	}
	
}
