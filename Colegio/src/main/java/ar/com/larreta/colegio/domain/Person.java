package ar.com.larreta.colegio.domain;

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

@Entity
@Table(name = "person")
@Inheritance(strategy=InheritanceType.JOINED)
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Person SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public abstract class Person extends ar.com.larreta.commons.domain.Entity {

	private String name;
	private String surname;
	private DocumentType documentType;
	private Integer documentNumber;
	private Set<PaymentUnit> paymentsThatBenefitMe;
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
		this.documentType = documentType;
	}
	
	@Basic @Column (name="documentNumber")
	public Integer getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(Integer documentNumber) {
		this.documentNumber = documentNumber;
	}
	
}
