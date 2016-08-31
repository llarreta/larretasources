package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ServiceHourStatus entity. @author MyEclipse Persistence Tools
 */

public class ServiceHourStatus implements java.io.Serializable, Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3321326216174455966L;
	private Long id;
	private String serviceHoursStatusCode;
	private String serviceHoursStatusName;

	// Constructors

	/** default constructor */
	public ServiceHourStatus() {
	}

	/** minimal constructor */
	public ServiceHourStatus(String serviceHoursStatusCode,
			String serviceHoursStatusName) {
		this.serviceHoursStatusCode = serviceHoursStatusCode;
		this.serviceHoursStatusName = serviceHoursStatusName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceHoursStatusCode() {
		return this.serviceHoursStatusCode;
	}

	public void setServiceHoursStatusCode(String serviceHoursStatusCode) {
		this.serviceHoursStatusCode = serviceHoursStatusCode;
	}

	public String getServiceHoursStatusName() {
		return this.serviceHoursStatusName;
	}

	public void setServiceHoursStatusName(String serviceHoursStatusName) {
		this.serviceHoursStatusName = serviceHoursStatusName;
	}

	@Override
	public String toString() {
		return "ServiceHourStatus [id=" + id + ", serviceHoursStatusCode="
				+ serviceHoursStatusCode + "]";
	}
}