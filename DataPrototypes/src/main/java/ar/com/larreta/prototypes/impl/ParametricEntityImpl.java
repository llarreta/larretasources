package ar.com.larreta.prototypes.impl;

import ar.com.larreta.prototypes.ParametricEntity;

public abstract class ParametricEntityImpl extends EntityImpl implements ParametricEntity{
	
	public static final String DESCRIPTION = "description";
	
	private String description;
	
	public ParametricEntityImpl(){}
	
	public ParametricEntityImpl(Long id, String description){
		this.id = id;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

} 
