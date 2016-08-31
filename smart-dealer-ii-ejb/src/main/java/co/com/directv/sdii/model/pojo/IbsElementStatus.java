package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * IbsElementStatus entity. @author MyEclipse Persistence Tools
 */

public class IbsElementStatus implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5482037052528028961L;
	// Fields

	private Long id;
	private Long eventId;
	private Long reasonId;
	private String reasonDescription;
	private Long ibsElementStatusId;
	private String ibsElementStatusDescription;
	private String ibsElementStatusCode;
	private Country country;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}
	
	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}
	
	public Long getIbsElementStatusId() {
		return ibsElementStatusId;
	}
	
	public void setIbsElementStatusId(Long ibsElementStatusId) {
		this.ibsElementStatusId = ibsElementStatusId;
	}
	
	public String getIbsElementStatusDescription() {
		return ibsElementStatusDescription;
	}
	
	public void setIbsElementStatusDescription(String ibsElementStatusDescription) {
		this.ibsElementStatusDescription = ibsElementStatusDescription;
	}

	public String getIbsElementStatusCode() {
		return ibsElementStatusCode;
	}

	public void setIbsElementStatusCode(String ibsElementStatusCode) {
		this.ibsElementStatusCode = ibsElementStatusCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "ElementStatus [elementStatusCode=" + ibsElementStatusCode
				+ ", id=" + id + "]";
	}
	
}