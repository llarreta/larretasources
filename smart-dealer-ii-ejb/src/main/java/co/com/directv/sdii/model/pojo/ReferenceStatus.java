package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * ReferenceStatus entity. @author MyEclipse Persistence Tools
 */

public class ReferenceStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -536923245209616017L;
	private Long id;
	private String refStatusCode;
	private String refStatusName;
	private String refEstatusDescription;

	// Constructors

	/** default constructor */
	public ReferenceStatus() {
	}
	
	public ReferenceStatus(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public ReferenceStatus(String refStatusCode, String refStatusName) {
		this.refStatusCode = refStatusCode;
		this.refStatusName = refStatusName;
	}

	/** full constructor */
	public ReferenceStatus(String refStatusCode, String refStatusName,
			String refEstatusDescription) {
		this.refStatusCode = refStatusCode;
		this.refStatusName = refStatusName;
		this.refEstatusDescription = refEstatusDescription;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefStatusCode() {
		return this.refStatusCode;
	}

	public void setRefStatusCode(String refStatusCode) {
		this.refStatusCode = refStatusCode;
	}

	public String getRefStatusName() {
		return this.refStatusName;
	}

	public void setRefStatusName(String refStatusName) {
		this.refStatusName = refStatusName;
	}

	public String getRefEstatusDescription() {
		return this.refEstatusDescription;
	}

	public void setRefEstatusDescription(String refEstatusDescription) {
		this.refEstatusDescription = refEstatusDescription;
	}

	@Override
	public String toString() {
		return "ReferenceStatus [id=" + id + ", refStatusCode=" + refStatusCode
				+ "]";
	}
}