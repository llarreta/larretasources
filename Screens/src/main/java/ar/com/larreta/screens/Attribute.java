package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "attribute")
@DiscriminatorValue(value = "attribute")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Attribute extends ScreenElement {

	private String name;
	private String value;
	private AjaxButton button;
	
	@ManyToOne (fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=AjaxButton.class)
	@JoinColumn (name="idButton")
	public AjaxButton getButton() {
		return button;
	}
	public void setButton(AjaxButton button) {
		this.button = button;
	}
	
	@Basic
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	public Object getValueEvaluated(){
		return ScreenUtils.evaluate(getValue());
	}
	
	@Basic
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
