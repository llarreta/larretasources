package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "checkBox")
@DiscriminatorValue(value = "checkBox")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class CheckBox extends ValuedElement {
	public CheckBox(){
		super();
	}
	
	public CheckBox(String bindingObject, String bindingProperty){
		super();
		setBindingObject(bindingObject);
		setBindingProperty(bindingProperty);
	}
}
