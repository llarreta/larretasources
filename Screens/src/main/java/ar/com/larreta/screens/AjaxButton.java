package ar.com.larreta.screens;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

//@Entity
//@DiscriminatorValue("ajaxButton")
public class AjaxButton extends Button {

	private String actionListener;
	private String update;
	private Set<Attribute> attributes;

	public AjaxButton() {
		super();
		ajax = Boolean.TRUE;
	}
	
	@OneToMany (mappedBy="button", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Attribute.class)
	@Where(clause="deleted IS NULL")
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Basic @Column(name="updateValue")
	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
	
	@Basic
	public String getActionListener() {
		return actionListener;
	}

	public void setActionListener(String actionListener) {
		this.actionListener = actionListener;
	}
	
}
