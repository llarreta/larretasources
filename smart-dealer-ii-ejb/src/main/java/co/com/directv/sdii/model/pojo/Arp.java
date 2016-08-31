package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * Arp entity. @author MyEclipse Persistence Tools
 */

public class Arp implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8471525927993323209L;
	private Long id;
	private String arpName;
	private String arpCode;
	private Country country;

	// Constructors

	/** default constructor */
	public Arp() {
	}

	/**
	 * 
	 * @param arpName
	 * @param arpCode
	 */
	public Arp(String arpName, String arpCode) {
		this.arpName = arpName;
		this.arpCode = arpCode;
	}
	
	/**
	 * 
	 * @param arpName
	 * @param arpCode
	 * @param country
	 */
	public Arp(String arpName, String arpCode,Country country) {
		this.arpName = arpName;
		this.arpCode = arpCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArpName() {
		return this.arpName;
	}

	public void setArpName(String arpName) {
		this.arpName = arpName;
	}

	public String getArpCode() {
		return this.arpCode;
	}

	public void setArpCode(String arpCode) {
		this.arpCode = arpCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Arp [arpCode=" + arpCode + ", id=" + id + "]";
	}

}