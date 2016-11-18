package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "calendar")
@DiscriminatorValue(value = "calendar")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Calendar extends ValuedElement {

	
	public Calendar(){
		super();
	}
	
	public Calendar(String bindingObject, String bindingProperty){
		super();
		setBindingObject(bindingObject);
		setBindingProperty(bindingProperty);
	}
}
