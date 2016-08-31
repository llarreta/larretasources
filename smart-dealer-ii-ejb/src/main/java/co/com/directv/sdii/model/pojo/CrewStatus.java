package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * CrewStatus entity. @author MyEclipse Persistence Tools
 */

public class CrewStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7280280197055134461L;
	private Long id;
	private String statusName;
	private String statusCode;

	// Constructors

	/** default constructor */
	public CrewStatus() {
	}

	/** minimal constructor */
	public CrewStatus(String statusName, String statusCode) {
		this.statusName = statusName;
		this.statusCode = statusCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "CrewStatus [id=" + id + ", statusCode=" + statusCode + "]";
	}

}