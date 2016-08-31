package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * EmployeeDevicesId entity. @author MyEclipse Persistence Tools
 */

public class EmployeeDevicesId implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7180051564890901810L;
	private MobileDevice mobileDevice;
	private Employee employee;

	// Constructors

	/** default constructor */
	public EmployeeDevicesId() {
	}

	/** full constructor */
	public EmployeeDevicesId(MobileDevice mobileDevice, Employee employee) {
		this.mobileDevice = mobileDevice;
		this.employee = employee;
	}

	// Property accessors

	public MobileDevice getMobileDevice() {
		return this.mobileDevice;
	}

	public void setMobileDevice(MobileDevice mobileDevice) {
		this.mobileDevice = mobileDevice;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmployeeDevicesId))
			return false;
		EmployeeDevicesId castOther = (EmployeeDevicesId) other;

		return ((this.getMobileDevice() == castOther.getMobileDevice()) || (this
				.getMobileDevice() != null
				&& castOther.getMobileDevice() != null && this
				.getMobileDevice().equals(castOther.getMobileDevice())))
				&& ((this.getEmployee() == castOther.getEmployee()) || (this
						.getEmployee() != null
						&& castOther.getEmployee() != null && this
						.getEmployee().equals(castOther.getEmployee())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getMobileDevice() == null ? 0 : this.getMobileDevice()
						.hashCode());
		result = 37 * result
				+ (getEmployee() == null ? 0 : this.getEmployee().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "EmployeeDevicesId [employee=" + employee + ", mobileDevice="
				+ mobileDevice + "]";
	}

}