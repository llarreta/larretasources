package ar.com.larreta.school.messages;

import javax.validation.constraints.Size;

import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.validators.annotations.NotNull;
import ar.com.larreta.validators.annotations.ValidParametricEntity;

public class UpdateStudentBody extends Body {

	@NotNull(message="id.required", avaiableActions={"update"})
	private Long id;
	@NotNull(message="name.required") @Size(min=5, message="name.min.length")
	private String 			name;
	@NotNull(message="surname.required") @Size(min=5, message="surname.min.length")
	private String 			surname;
	@ValidParametricEntity(message="documentType.inexistent", parametricEntity="ar.com.larreta.persistence.model.DocumentType")
	private Long 			documentType;
	private String 			documentNumber;
	private String 			photo;

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
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
