package ar.com.larreta.stepper.impl;

import java.io.Serializable;

public class ExistData implements Serializable {

	private Class entityType;
	private String linkedField;
	private Object value;
	
	public ExistData(Class entityType, String linkedField, Object value){
		this.entityType = entityType;
		this.linkedField = linkedField;
		this.value = value;
	}
	
	public Class getEntityType() {
		return entityType;
	}
	public void setEntityType(Class entityType) {
		this.entityType = entityType;
	}
	public String getLinkedField() {
		return linkedField;
	}
	public void setLinkedField(String linkedField) {
		this.linkedField = linkedField;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	

	
	
	
}
