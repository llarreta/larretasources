package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ReferenceModification entity. @author cduarte
 */

public class IbsContactReason implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 456326954443337476L;
	
	
	private Long id;
	private String code;
	private String name;
	private Country country;
	private String category;
	private String subCategory;
	private String isReclaim;

	// Constructors
	public IbsContactReason() {
		super();
	}

	public IbsContactReason(Long id, String code, String name, Country country,
			String category, String subCategory, String isReclaim) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.country = country;
		this.category = category;
		this.subCategory = subCategory;
		this.isReclaim = isReclaim;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getIsReclaim() {
		return isReclaim;
	}

	public void setIsReclaim(String isReclaim) {
		this.isReclaim = isReclaim;
	}

	
}