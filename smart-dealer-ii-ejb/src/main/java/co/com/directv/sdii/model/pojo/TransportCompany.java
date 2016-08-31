package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * TransportCompany entity. @author MyEclipse Persistence Tools
 */

public class TransportCompany implements java.io.Serializable, Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5987346343125568001L;
	private Long id;
	private String companyCode;
	private String companyName;
	private String companyDescription;
	private String companyAddress;
	private String isActive;
	private Country countryCodeId;

	// Constructors

	/** default constructor */
	public TransportCompany() {
	}

	/** minimal constructor */
	public TransportCompany(String companyCode, String companyName,
			String isActive) {
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.isActive = isActive;
	}

	/** full constructor */
	public TransportCompany(String companyCode, String companyName,
			String companyDescription, String companyAddress, String isActive) {
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.companyDescription = companyDescription;
		this.companyAddress = companyAddress;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyDescription() {
		return this.companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Country getCountryCodeId() {
		return countryCodeId;
	}

	public void setCountryCodeId(Country countryCodeId) {
		this.countryCodeId = countryCodeId;
	}

	@Override
	public String toString() {
		return "TransportCompany [companyCode=" + companyCode + ", id=" + id
				+ "]";
	}
}