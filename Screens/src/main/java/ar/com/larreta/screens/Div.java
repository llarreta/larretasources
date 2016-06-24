package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "divtag")
@DiscriminatorValue(value = "div")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Div extends StandardContainer {
	
	public Div(){}
	
	public Div(String styleClass){
		this();
		setStyleClass(styleClass);
	}
	
}
