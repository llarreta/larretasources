package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "menuButton")
@DiscriminatorValue(value = "menuButton")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class MenuButton extends Menu {
	public MenuButton() {
		super();
	}

	public MenuButton(String styleClass) {
		super(styleClass);
	}
}
