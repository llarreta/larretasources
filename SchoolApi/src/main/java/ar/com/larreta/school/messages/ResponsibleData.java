package ar.com.larreta.school.messages;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.Country;
import ar.com.larreta.mystic.model.DocumentType;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.stepper.messages.PersonEmailRelationshipData;
import ar.com.larreta.stepper.messages.PersonTelephoneRelationshipData;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.Format;
import ar.com.larreta.stepper.validators.annotations.NotNull;

@Component
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
	
	@Format(formatType=Format.FormatType.DATE, message="birthdate.student.invalid")
	private String 			birthdate;
	
	@Exist(message="nationality.inexistent", entityType=Country.class)
	private Long 			nationality;

	private String 			cbu;
	
	private String 			cuil;
	
	private String profession;
	
	@Valid
	private JSONableCollection<PersonEmailRelationshipData> emails;
	
	@Valid
	private JSONableCollection<PersonTelephoneRelationshipData> telephones;

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	public JSONableCollection<PersonEmailRelationshipData> getEmails() {
		return emails;
	}

	public void setEmails(JSONableCollection<PersonEmailRelationshipData> emails) {
		this.emails = emails;
	}

	public JSONableCollection<PersonTelephoneRelationshipData> getTelephones() {
		return telephones;
	}

	public void setTelephones(JSONableCollection<PersonTelephoneRelationshipData> telephones) {
		this.telephones = telephones;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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
