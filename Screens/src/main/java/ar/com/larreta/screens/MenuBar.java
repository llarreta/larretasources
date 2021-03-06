package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "menuBar")
@DiscriminatorValue(value = "menuBar")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class MenuBar extends Menu {

	public MenuBar() {
		super();
	}

	public MenuBar(String styleClass) {
		super(styleClass);
	}
}
