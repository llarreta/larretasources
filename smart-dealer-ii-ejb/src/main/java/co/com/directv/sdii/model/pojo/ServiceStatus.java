package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ServiceStatus entity. @author MyEclipse Persistence Tools
 */

public class ServiceStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6954761761172896003L;
	private Long id;
	private String serviceStatusName;
	private String serviceStatusCode;

	// Constructors

	/** default constructor */
	public ServiceStatus() {
	}

	/** minimal constructor */
	public ServiceStatus(String serviceStatusName, String serviceStatusCode) {
		this.serviceStatusName = serviceStatusName;
		this.serviceStatusCode = serviceStatusCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceStatusName() {
		return this.serviceStatusName;
	}

	public void setServiceStatusName(String serviceStatusName) {
		this.serviceStatusName = serviceStatusName;
	}

	public String getServiceStatusCode() {
		return this.serviceStatusCode;
	}

	public void setServiceStatusCode(String serviceStatusCode) {
		this.serviceStatusCode = serviceStatusCode;
	}

	@Override
	public String toString() {
		return "ServiceStatus [id=" + id + ", serviceStatusCode="
				+ serviceStatusCode + "]";
	}
}