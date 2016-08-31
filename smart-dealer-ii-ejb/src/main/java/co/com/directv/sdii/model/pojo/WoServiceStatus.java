package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * WoServiceStatus entity. @author MyEclipse Persistence Tools
 */

public class WoServiceStatus implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4500008173001544389L;
	// Fields

	private Long id;
	private String statusName;
	private String statusCode;

	// Constructors

	/** default constructor */
	public WoServiceStatus() {
	}

	/** minimal constructor */
	public WoServiceStatus(String statusName, String statusCode) {
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
		return "WoServiceStatus [id=" + id + ", statusCode=" + statusCode + "]";
	}
}