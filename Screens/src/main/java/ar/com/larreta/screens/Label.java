package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "label")
@DiscriminatorValue(value = "label")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Label extends ValuedElement {

	public Label(){
		setBindingProperty("value");
	}
	
	public Label(String messageValue, String styleClass){
		this(messageValue);
		setStyleClass(styleClass);
	}
	
	public Label(String messageValue){
		this();
		setMessageValue(messageValue);
	}
	
}