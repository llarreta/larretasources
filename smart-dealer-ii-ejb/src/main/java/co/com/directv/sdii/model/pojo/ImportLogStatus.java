package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ImportLogStatus entity. @author MyEclipse Persistence Tools
 */

public class ImportLogStatus implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4369684305732813928L;
	// Fields

	private Long id;
	private String statusCode;
	private String statusName;

	// Constructors

	/** default constructor */
	public ImportLogStatus() {
	}

	/** minimal constructor */
	public ImportLogStatus(String statusCode) {
		this.statusCode = statusCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "ImportLogStatus [id=" + id + ", statusCode=" + statusCode + "]";
	}
	
}