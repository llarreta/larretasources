package ar.com.larreta.school.messages;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.Country;
import ar.com.larreta.mystic.model.DocumentType;
import ar.com.larreta.stepper.messages.Body;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.stepper.messages.PersonAddressRelationshipData;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.Format;
import ar.com.larreta.stepper.validators.annotations.NotNull;

@Component
public class UpdateStudentBody extends Body {

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
	
	private Long 			course;
	
	private JSONableCollection<Long> paymentPlans;
	
	private String 			email;
	
	@Format(formatType=Format.FormatType.DATE, message="birthdate.student.invalid")
	private String 			birthdate;
	
	@Exist(message="nationality.inexistent", entityType=Country.class)
	private Long 			nationality;
	
	@Valid
	private JSONableCollection<PersonAddressRelationshipData> addressesRelationship;
	

	public JSONableCollection<PersonAddressRelationshipData> getAddressesRelationship() {
		return addressesRelationship;
	}
	public void setAddressesRelationship(JSONableCollection<PersonAddressRelationshipData> addressesRelationship) {
		this.addressesRelationship = addressesRelationship;
	}
	public Long getNationality() {
		return nationality;
	}
	public void setNationality(Long nationality) {
		this.nationality = nationality;
	}
	
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public JSONableCollection<Long> getPaymentPlans() {
		return paymentPlans;
	}
	public void setPaymentPlans(JSONableCollection<Long> paymentPlans) {
		this.paymentPlans = paymentPlans;
	}
	public Long getCourse() {
		return course;
	}
	public void setCourse(Long course) {
		this.course = course;
	}
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
