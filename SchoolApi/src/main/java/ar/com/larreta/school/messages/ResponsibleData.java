package ar.com.larreta.school.messages;

import javax.validation.constraints.Size;

import ar.com.larreta.mystic.model.Country;
import ar.com.larreta.mystic.model.DocumentType;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.Format;
import ar.com.larreta.stepper.validators.annotations.NotNull;

public class ResponsibleData extends JSONable {
	@NotNull(message="id.required", avaiableActions={"update"})
	private Long id;
	
	@NotNull(message="name.required") @Size(min=3, message="name.min.length")
	private String 			name;
	
	@NotNull(message="surname.required") @Size(min=3, message="surname.min.length")
	private String 			surname;
	
	@Exist(message="documentType.inexistent", entityType=DocumentType.class)
	private Long 			documentType;
	
	private String 			documentNumber;
	
	private String 			photo;

	private String 			email;
	
	@Format(formatType=Format.FormatType.DATE, message="birthdate.student.invalid")
	private String 			birthdate;
	
	@Exist(message="nationality.inexistent", entityType=Country.class)
	private Long 			nationality;

	private String 			cbu;
	
	private String 			cuil;

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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Long getNationality() {
		return nationality;
	}

	public void setNationality(Long nationality) {
		this.nationality = nationality;
	}

	public String getCbu() {
		return cbu;
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	
}
