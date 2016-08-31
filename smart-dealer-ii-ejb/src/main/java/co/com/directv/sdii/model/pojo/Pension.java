package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * Pension entity. @author MyEclipse Persistence Tools
 */

public class Pension implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4266680495526019182L;
	private Long id;
	private String pensionName;
	private String pensionCode;
	private Country country;

	// Constructors

	/** default constructor */
	public Pension() {
	}

	/** minimal constructor */
	public Pension(String pensionName, String pensionCode) {
		this.pensionName = pensionName;
		this.pensionCode = pensionCode;
	}
	
	/** full constructor */
	public Pension(String pensionName, String pensionCode,Country country) {
		this.pensionName = pensionName;
		this.pensionCode = pensionCode;
		this.country = country;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPensionName() {
		return this.pensionName;
	}

	public void setPensionName(String pensionName) {
		this.pensionName = pensionName;
	}

	public String getPensionCode() {
		return this.pensionCode;
	}

	public void setPensionCode(String pensionCode) {
		this.pensionCode = pensionCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Pension [id=" + id + ", pensionCode=" + pensionCode + "]";
	}
}