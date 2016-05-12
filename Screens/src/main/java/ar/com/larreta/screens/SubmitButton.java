package ar.com.larreta.screens;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

//@Entity
//@DiscriminatorValue("submitButton")
public class SubmitButton extends Button {

	private String action;
	private Set<PropertyActionListener> properties;

	public SubmitButton() {
		super();
		ajax = Boolean.FALSE;
	}
	
	@OneToMany (mappedBy="button", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=PropertyActionListener.class)
	@Where(clause="deleted IS NULL")
	public Set<PropertyActionListener> getProperties() {
		return properties;
	}

	public void setProperties(Set<PropertyActionListener> properties) {
		this.properties = properties;
	}

	@Basic
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
