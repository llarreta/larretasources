package ar.com.larreta.mystic.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "person")
@Inheritance(strategy=InheritanceType.JOINED)
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Person SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public abstract class Person extends ar.com.larreta.mystic.model.Entity {

	private String 			name;
	private String 			surname;
	private DocumentType 	documentType;
	private String 			documentNumber;
	private String 			photo;
	private Date 			birthdate;
	private Country 		nationality;
	private Set<PersonEmailRelationship> 		emails;
	private Set<PersonTelephoneRelationship> 	telephones;
	private Set<PersonAddressRelationship> 		addresses;

	@OneToMany (mappedBy="person", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=PersonEmailRelationship.class)
	@Where(clause="deleted IS NULL")		
	public Set<PersonEmailRelationship> getEmails() {
		return emails;
	}
	public void setEmails(Set<PersonEmailRelationship> emails) {
		this.emails = emails;
	}
	
	@OneToMany (mappedBy="person", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=PersonTelephoneRelationship.class)
	@Where(clause="deleted IS NULL")	
	public Set<PersonTelephoneRelationship> getTelephones() {
		return telephones;
	}
	public void setTelephones(Set<PersonTelephoneRelationship> telephones) {
		this.telephones = telephones;
	}

	@OneToMany (mappedBy="person", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=PersonAddressRelationship.class)
	@Where(clause="deleted IS NULL")	
	public Set<PersonAddressRelationship> getAddresses() {
		return addresses;
	}
	public void setAddresses(Set<PersonAddressRelationship> addresses) {
		this.addresses = addresses;
		writeToAll(addresses, "person", this);
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Country.class)
	@JoinColumn (name="idNationality")
	public Country getNationality() {
		return nationality;
	}
	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}
	@Basic @Column (name="birthdate")
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	@Basic @Column (name="photo", columnDefinition="TEXT")
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Basic @Column (name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic @Column (name="surname")
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=DocumentType.class)
	@JoinColumn (name="idDocumentType")
	public DocumentType getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentType documentType) {
		this.documentType = (DocumentType) documentType;
	}
	
	@Basic @Column (name="documentNumber")
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	//FIXME: Ver que hacemos con este mapeo
	
	/*private Set<PaymentUnit> paymentsThatBenefitMe;
	private Set<Payment> paymentsMade;
	
	@OneToMany (mappedBy="personBenefiting", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=PaymentUnit.class)
	@Where(clause="deleted IS NULL")
	public Set<PaymentUnit> getPaymentsThatBenefitMe() {
		return paymentsThatBenefitMe;
	}
	public void setPaymentsThatBenefitMe(Set<PaymentUnit> paymentsThatBenefitMe) {
		this.paymentsThatBenefitMe = paymentsThatBenefitMe;
	}

	@OneToMany (mappedBy="personWhoPays", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Payment.class)
	@Where(clause="deleted IS NULL")
	public Set<Payment> getPaymentsMade() {
		return paymentsMade;
	}
	public void setPaymentsMade(Set<Payment> paymentsMade) {
		this.paymentsMade = paymentsMade;
	}
	
	
*/
	
}
