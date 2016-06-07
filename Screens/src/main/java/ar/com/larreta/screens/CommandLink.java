package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "commandLink")
@DiscriminatorValue(value = "commandLink")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class CommandLink extends ScreenElement {

	public CommandLink(){}
	
	public CommandLink(String styleClass){
		this();
		setStyleClass(styleClass);
	}
	
}
