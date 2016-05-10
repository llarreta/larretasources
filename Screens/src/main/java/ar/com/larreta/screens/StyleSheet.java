package ar.com.larreta.screens;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "styleSheet")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class StyleSheet extends Library {

	public StyleSheet(String library, String name) {
		super(library, name);
	}

}
