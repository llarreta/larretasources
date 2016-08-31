package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * Eps entity. @author MyEclipse Persistence Tools
 */

public class Eps implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2300686388313278472L;
	private Long id;
	private String epsName;
	private String epsCode;
	private Country country;

	// Constructors

	/** default constructor */
	public Eps() {
	}
	
	/** minimal constructor */
	public Eps(String epsName, String epsCode) {
		this.epsName = epsName;
		this.epsCode = epsCode;
	}
	
	public Eps(String epsName, String epsCode,Country country) {
		this.epsName = epsName;
		this.epsCode = epsCode;
		this.country = country;
	}
	

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEpsName() {
		return this.epsName;
	}

	public void setEpsName(String epsName) {
		this.epsName = epsName;
	}

	public String getEpsCode() {
		return this.epsCode;
	}

	public void setEpsCode(String epsCode) {
		this.epsCode = epsCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Eps [epsCode=" + epsCode + ", id=" + id + "]";
	}
	
}