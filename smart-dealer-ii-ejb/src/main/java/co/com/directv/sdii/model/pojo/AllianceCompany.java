package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * AllianceCompany entity. @author MyEclipse Persistence Tools
 */

public class AllianceCompany implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2889787688783568739L;
	private Long id;
	private String companyCode;
	private String companyName;
	private Country country;

	// Constructors

	/** default constructor */
	public AllianceCompany() {
	}

	@Override
	public String toString() {
		return "AllianceCompany [companyCode=" + companyCode + ", id=" + id
				+ "]";
	}

	/**
	 * 
	 * @param companyCode
	 * @param companyName
	 */
	public AllianceCompany(String companyCode, String companyName) {
		this.companyCode = companyCode;
		this.companyName = companyName;		
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param companyName
	 * @param country
	 */
	public AllianceCompany(String companyCode, String companyName, Country country) {
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.country = country;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}