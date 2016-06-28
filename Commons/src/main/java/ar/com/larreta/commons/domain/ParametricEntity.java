package ar.com.larreta.commons.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class ParametricEntity extends Entity {
	
	public static final String DESCRIPTION = "description";
	
	private String description;
	
	public ParametricEntity(){}
	
	public ParametricEntity(Long id, String description){
		this.id = id;
		this.description = description;
	}
	
	@Basic
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Se retorna siempre nulo para que no sea posible eliminar ningun valor
	 */
	@Override
	@Transient
	public Date getDeleted(){
		return null;
	}

	@Override
	public String toString() {
		return getDescription();
	}

} 
