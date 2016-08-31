package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ElementBrand entity. @author MyEclipse Persistence Tools
 */

public class ElementBrand implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3583603585807338662L;
	private Long id;
	private String brandName;
	private String brandCode;
	private String brandDescription;
	private String isActive;

	// Constructors

	/** default constructor */
	public ElementBrand() {
	}

	/** minimal constructor */
	public ElementBrand(String brandName, String brandCode, String isActive) {
		this.brandName = brandName;
		this.brandCode = brandCode;
		this.isActive = isActive;
	}

	/** full constructor */
	public ElementBrand(String brandName, String brandCode,
			String brandDescription, String isActive) {
		this.brandName = brandName;
		this.brandCode = brandCode;
		this.brandDescription = brandDescription;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandCode() {
		return this.brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandDescription() {
		return this.brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "ElementBrand [brandCode=" + brandCode + ", id=" + id + "]";
	}

}