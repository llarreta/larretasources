package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "styleSheet")
@DiscriminatorValue(value = "styleSheet")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class StyleSheet extends Library {

	public StyleSheet(){}
	
	public StyleSheet(String library, String name) {
		super(library, name);
	}

}
