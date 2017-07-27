package ar.com.larreta.school.messages;

import ar.com.larreta.mystic.model.Person;
import ar.com.larreta.school.persistence.PaymentDirection;
import ar.com.larreta.school.persistence.PaymentEntity;
import ar.com.larreta.school.persistence.Product;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.NotNull;

public class PayUnitData extends JSONable {

	@NotNull(message="payUnit.value.required")
	private Double value; 
	
	@NotNull(message="payUnit.personBenefiting.required")
	@Exist(message="personBenefiting.inexistent", entityType=Person.class)
	private Long personBenefiting;
	
	@NotNull(message="payUnit.product.required")
	@Exist(message="product.inexistent", entityType=Product.class)
	private Long product;
	
	@NotNull(message="payUnit.paymentDirection.required")
	@Exist(message="paymentDirection.inexistent", entityType=PaymentDirection.class)
	private Long paymentDirection;
	
	@NotNull(message="payUnit.paymentEntity.required")
	@Exist(message="paymentEntity.inexistent", entityType=PaymentEntity.class)
	private Long paymentEntity;
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Long getPersonBenefiting() {
		return personBenefiting;
	}
	public void setPersonBenefiting(Long personBenefiting) {
		this.personBenefiting = personBenefiting;
	}
	public Long getProduct() {
		return product;
	}
	public void setProduct(Long product) {
		this.product = product;
	}
	public Long getPaymentDirection() {
		return paymentDirection;
	}
	public void setPaymentDirection(Long paymentDirection) {
		this.paymentDirection = paymentDirection;
	}
	public Long getPaymentEntity() {
		return paymentEntity;
	}
	public void setPaymentEntity(Long paymentEntity) {
		this.paymentEntity = paymentEntity;
	}
	
}
