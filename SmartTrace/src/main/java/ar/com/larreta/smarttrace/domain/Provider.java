package ar.com.larreta.smarttrace.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "provider")
@Where(clause="deleted IS NULL")
//FIXME: Luego agregar todos los detalles relevantes referentes a un proveedor, ejm, direccion, telefono
public class Provider extends ar.com.larreta.commons.domain.Entity {
	
	private String description;

	@Basic
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
