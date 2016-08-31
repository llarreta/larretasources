package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * VehicleStatus entity. @author MyEclipse Persistence Tools
 */

public class VehicleStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5754865807027254034L;
	private Long id;
	private String statusName;
	private String statusCode;

	// Constructors

	/** default constructor */
	public VehicleStatus() {
	}

	/** minimal constructor */
	public VehicleStatus(String statusName, String statusCode) {
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
		return "VehicleStatus [id=" + id + ", statusCode=" + statusCode + "]";
	}
}