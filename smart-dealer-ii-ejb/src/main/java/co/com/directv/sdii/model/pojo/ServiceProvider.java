package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * ServiceProvider entity. @author MyEclipse Persistence Tools
 */

public class ServiceProvider implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 905183440722667312L;
	private Long id;
	private Country country;
	private String providerCode;
	private String providerName;
	private String providerDescription;

	// Constructors

	/** default constructor */
	public ServiceProvider() {
	}

	/** minimal constructor */
	public ServiceProvider(Long id, Country country, String providerName) {
		this.id = id;
		this.country = country;
		this.providerName = providerName;
	}

	/** full constructor */
	public ServiceProvider(Long id, Country country, String providerCode,
			String providerName, String providerDescription) {
		this.id = id;
		this.country = country;
		this.providerCode = providerCode;
		this.providerName = providerName;
		this.providerDescription = providerDescription;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getProviderCode() {
		return this.providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderName() {
		return this.providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderDescription() {
		return this.providerDescription;
	}

	public void setProviderDescription(String providerDescription) {
		this.providerDescription = providerDescription;
	}

	@Override
	public String toString() {
		return "ServiceProvider [id=" + id + ", providerCode=" + providerCode
				+ "]";
	}
}