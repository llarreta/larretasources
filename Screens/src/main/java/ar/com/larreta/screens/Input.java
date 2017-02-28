package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "input")
@DiscriminatorValue(value = "input")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Input extends ValuedElement {
	
	private String onClick;
	private String placeHolder;
	
	public Input(){
		super();
	}
	
	public Input(String bindingObject, String bindingProperty){
		super();
		setBindingObject(bindingObject);
		setBindingProperty(bindingProperty);
	}

	@Basic
	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	
	@Basic
	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}
}