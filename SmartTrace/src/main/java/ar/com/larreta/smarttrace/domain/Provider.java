package ar.com.larreta.smarttrace.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "provider")
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
