package ar.com.larreta.colegio.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "paymentUnit")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE PaymentUnit SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class PaymentUnit extends ar.com.larreta.commons.domain.Entity {
	
	private Double value;
	private Person personBenefiting;
	private Product product;
	private PaymentDirection paymentDirection;
	private PaymentEntity paymentEntity;
	private Payment payment;
	private String description;
	
	@Basic @Column (name="value")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Person.class)
	@JoinColumn (name="idPersonBenefiting")
	public Person getPersonBenefiting() {
		return personBenefiting;
	}
	public void setPersonBenefiting(Person personBenefiting) {
		this.personBenefiting = personBenefiting;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Product.class)
	@JoinColumn (name="idProduct")
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=PaymentDirection.class)
	@JoinColumn (name="idPaymentDirection")
	public PaymentDirection getPaymentDirection() {
		return paymentDirection;
	}
	public void setPaymentDirection(PaymentDirection paymentDirection) {
		this.paymentDirection = paymentDirection;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=PaymentEntity.class)
	@JoinColumn (name="idPaymentEntity")
	public PaymentEntity getPaymentEntity() {
		return paymentEntity;
	}
	public void setPaymentEntity(PaymentEntity paymentEntity) {
		this.paymentEntity = paymentEntity;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Payment.class)
	@JoinColumn (name="idPayment")
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	@Basic @Column (name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
