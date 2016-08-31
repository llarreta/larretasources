package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ComercialProduct entity. @author MyEclipse Persistence Tools
 */

public class ComercialProduct implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3271517649939252690L;
	private Long id;
	private String productName;
	private String productCode;
	private Country country;

	// Constructors

	/** default constructor */
	public ComercialProduct() {
	}
	
	/**
	 * 
	 * @param productName
	 * @param productCode
	 */
	public ComercialProduct(String productName, String productCode) {
		this.productName = productName;
		this.productCode = productCode;
	}
	
	public ComercialProduct(String productName, String productCode,Country country) {
		this.productName = productName;
		this.productCode = productCode;
		this.country = country;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "ComercialProduct [id=" + id + ", productCode=" + productCode
				+ "]";
	}

}