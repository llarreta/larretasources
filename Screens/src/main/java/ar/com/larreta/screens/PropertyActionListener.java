package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "propertyActionListener")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class PropertyActionListener extends ScreenElement {

	private String target;
	private String value;
	private String forAttributes;
	private SubmitButton button;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=SubmitButton.class)
	@JoinColumn (name="idButton")
	public SubmitButton getButton() {
		return button;
	}
	public void setButton(SubmitButton button) {
		this.button = button;
	}
	
	@Basic
	public String getForAttributes() {
		return forAttributes;
	}
	public void setForAttributes(String forAttributes) {
		this.forAttributes = forAttributes;
	}
	
	@Basic
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	@Basic
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
