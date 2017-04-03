package ar.com.larreta.persistence.model.impl;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ar.com.larreta.prototypes.ParametricEntity;


//FIXME: Esta clase deberia estar en DataPrototypes?
@MappedSuperclass
public abstract class PersistenceParametricEntityImpl extends PersistenceEntityImpl implements ParametricEntity{
	
	public static final String DESCRIPTION = "description";
	
	private String description;
	
	public PersistenceParametricEntityImpl(){}
	
	public PersistenceParametricEntityImpl(Long id, String description){
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
