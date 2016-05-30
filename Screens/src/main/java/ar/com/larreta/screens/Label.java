package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "label")
@DiscriminatorValue(value = "label")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Label extends ScreenElement {
	private String value;

	public Label(){}
	
	public Label(String value){
		setValue(value);
	}
	
	@Transient
	public String getValueEvaluated() {
		return (String) ScreenUtils.evaluate(getValue());
	}
	
	@Basic
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}