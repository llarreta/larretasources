package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * Domain entity. 
 * @author waguilera
 */

public class Domain implements java.io.Serializable,Auditable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String domainCode;
	
	private String domainName;
	
	private String domainValue;
	
	private String domainValuePar;
	
	private String domainDescription;
	
	private Country country;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainValue() {
		return domainValue;
	}

	public void setDomainValue(String domainValue) {
		this.domainValue = domainValue;
	}

	public String getDomainDescription() {
		return domainDescription;
	}

	public void setDomainDescription(String domainDescription) {
		this.domainDescription = domainDescription;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getDomainValuePar() {
		return domainValuePar;
	}

	public void setDomainValuePar(String domainValuePar) {
		this.domainValuePar = domainValuePar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "Domain [id=" + id + ", domainCode=" + domainCode + "]";
	}

}