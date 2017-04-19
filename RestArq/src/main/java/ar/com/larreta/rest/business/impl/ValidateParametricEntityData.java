package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

public class ValidateParametricEntityData implements Serializable {

	private String parametricEntity;
	private Long id;
	
	public ValidateParametricEntityData(String parametricEntity, Long id){
		this.parametricEntity = parametricEntity;
		this.id = id;
	}
	
	public String getParametricEntity() {
		return parametricEntity;
	}
	public void setParametricEntity(String parametricEntity) {
		this.parametricEntity = parametricEntity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
