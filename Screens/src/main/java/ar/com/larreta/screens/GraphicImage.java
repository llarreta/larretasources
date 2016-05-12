package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "graphicImage")
@DiscriminatorValue(value = "graphicImage")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class GraphicImage extends Library {

	public GraphicImage(){}
	
	public GraphicImage(String library, String name) {
		super(library, name);
	}

}
