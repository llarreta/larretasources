package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ElementStatus entity. @author MyEclipse Persistence Tools
 */

public class ReasonIbs implements java.io.Serializable,Auditable {


	private static final long serialVersionUID = -1527434061734562107L;
	
	private Long id;
	
	private String reasonCode;
	
	private String reasonName;
	
	private Country country;
	
	public ReasonIbs(){
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	
	
}