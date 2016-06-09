package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "input")
@DiscriminatorValue(value = "input")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Input extends ValuedElement {
	
	public Input(){
		super();
	}
	
	public Input(String bindingObject, String bindingProperty){
		super();
		setBindingObject(bindingObject);
		setBindingProperty(bindingProperty);
	}
}
