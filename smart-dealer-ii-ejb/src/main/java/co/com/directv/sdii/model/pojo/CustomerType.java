package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * CustomerCodeType entity. @author MyEclipse Persistence Tools
 */

public class CustomerType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5211280697766461684L;
	private Long id;
	private String customerTypeName;
	private String customerTypeCode;
	private Country country;
	// Constructors

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	/** default constructor */
	public CustomerType() {
	}

	/** minimal constructor */
	public CustomerType(String customerTypeName, String customerTypeCode) {
		this.customerTypeName = customerTypeName;
		this.customerTypeCode = customerTypeCode;
	}

	
	
	public CustomerType(CustomerType copy) {
		super();
		this.id = copy.id;
		this.customerTypeName = copy.customerTypeName;
		this.customerTypeCode = copy.customerTypeCode;
		this.country = copy.country;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerTypeName() {
		return this.customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public String getCustomerTypeCode() {
		return this.customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}

	@Override
	public String toString() {
		return "CustomerType [customerTypeCode=" + customerTypeCode
				+ ", id=" + id + "]";
	}

}