package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * Country entity. @author MyEclipse Persistence Tools
 */

public class Country implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 316807858363127863L;
	private Long id;
	private String countryName;
	private String countryCode;
	private String iso3Code;

	// Constructors

	public String getIso3Code() {
		return iso3Code;
	}

	public void setIso3Code(String iso3Code) {
		this.iso3Code = iso3Code;
	}

	/** default constructor */
	public Country() {
	}
	
	public Country(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public Country(String countryName, String countryCode) {
		this.countryName = countryName;
		this.countryCode = countryCode;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return "Country [countryCode=" + countryCode + ", id=" + id + "]";
	}

}