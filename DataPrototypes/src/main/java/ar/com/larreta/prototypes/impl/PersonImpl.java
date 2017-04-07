package ar.com.larreta.prototypes.impl;

import java.io.Serializable;

import javax.validation.constraints.Size;

import ar.com.larreta.prototypes.Person;
import ar.com.larreta.validators.annotations.NotNull;

public class PersonImpl extends EntityImpl implements Person{

	@NotNull(avaiableActions = { "" }) @Size(min=5, message="name min size 5 char")
	private String 			name;
	@NotNull(avaiableActions = { "" }) @Size(min=5, message="surname min size 5 char")
	private String 			surname;
	private Long 			documentType;
	private String 			documentNumber;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Serializable getDocumentType() {
		return documentType;
	}
	public void setDocumentType(Long documentType) {
		this.documentType = documentType;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	
}
