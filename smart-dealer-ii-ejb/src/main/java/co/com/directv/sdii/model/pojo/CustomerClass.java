package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * CustomerClass entity. @author MyEclipse Persistence Tools
 */

public class CustomerClass implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5725234178231206640L;
	private Long id;
	private String customerClassName;
	private String customerClassCode;
	private Country country;
	
	private CustomerCategory category;

	private String isPriority;

	public String getIsPriority() {
		return isPriority;
	}

	public void setIsPriority(String isPriority) {
		this.isPriority = isPriority;
	}

	public CustomerClass(CustomerClass copy) {
		super();
		this.id = copy.id;
		this.customerClassName = copy.customerClassName;
		this.customerClassCode = copy.customerClassCode;
		this.country = copy.country;
		this.category = copy.category;
	}

	public CustomerCategory getCategory() {
		return category;
	}

	public void setCategory(CustomerCategory category) {
		this.category = category;
	}

	public CustomerClass(Long id, String customerClassName,
			String customerClassCode, Country country, CustomerCategory category) {
		super();
		this.id = id;
		this.customerClassName = customerClassName;
		this.customerClassCode = customerClassCode;
		this.country = country;
		this.category = category;
	}

	// Constructors

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	/** default constructor */
	public CustomerClass() {
	}

	/** minimal constructor */
	public CustomerClass(String customerClassName, String customerClassCode) {
		this.customerClassName = customerClassName;
		this.customerClassCode = customerClassCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerClassName() {
		return this.customerClassName;
	}

	public void setCustomerClassName(String customerClassName) {
		this.customerClassName = customerClassName;
	}

	public String getCustomerClassCode() {
		return this.customerClassCode;
	}

	public void setCustomerClassCode(String customerClassCode) {
		this.customerClassCode = customerClassCode;
	}

	@Override
	public String toString() {
		return "CustomerClass [customerClassCode=" + customerClassCode
				+ ", id=" + id + "]";
	}

}