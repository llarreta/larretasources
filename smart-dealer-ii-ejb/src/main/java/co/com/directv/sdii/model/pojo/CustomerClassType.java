package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

import co.com.directv.sdii.audit.Auditable;

public class CustomerClassType implements Serializable, Auditable {

	private Long id;
	private CustomerClass customerClass;
	private CustomerType customerType;

	public CustomerClassType() {
		super();
	}
	public CustomerClassType(Long id, CustomerClass customerClass,
			CustomerType customerType) {
		super();
		this.id = id;
		this.customerClass = customerClass;
		this.customerType = customerType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CustomerClass getCustomerClass() {
		return customerClass;
	}
	public void setCustomerClass(CustomerClass customerClass) {
		this.customerClass = customerClass;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
}
