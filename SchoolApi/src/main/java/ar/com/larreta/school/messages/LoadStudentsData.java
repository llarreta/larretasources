package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class LoadStudentsData extends JSONable {

	private Long 			id;
	private String 			name;
	private String 			surname;
	private Long 			documentType;
	private String 			documentNumber;
	private Long 			course;
	private JSONableCollection<Long> paymentPlans;
	private String 			email;
	
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
