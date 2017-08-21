package ar.com.larreta.mystic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "personEmail")
public class PersonEmailRelationship extends ar.com.larreta.mystic.model.Entity {

	private Person 		person;
	private Email 		email;
	private EmailType 	emailType;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Person.class)
	@JoinColumn (name="idPerson")
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Email.class, cascade=CascadeType.ALL)
	@JoinColumn (name="idEmail")
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=EmailType.class)
	@JoinColumn (name="idEmailType")
	public EmailType getEmailType() {
		return emailType;
	}
	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}
	
	
	
}
