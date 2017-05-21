package ar.com.larreta.school.messages;

import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.LoadBody;

public class LoadStudentsBody extends LoadBody {

	private Long 			id;
	private String 			name;
	private String 			surname;
	private Long 			documentType;
	private String 			documentNumber;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Long getDocumentType() {
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
