package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * EmployeeVehicleId entity. @author MyEclipse Persistence Tools
 */

public class EmployeeVehicleId implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2543983626701816830L;
	private Long employeeId;
	private Long vehicleId;

	// Constructors

	/** default constructor */
	public EmployeeVehicleId() {
	}

	/** full constructor */
	public EmployeeVehicleId(Long employeeId, Long vehicleId) {
		this.employeeId = employeeId;
		this.vehicleId = vehicleId;
	}

	// Property accessors

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmployeeVehicleId))
			return false;
		EmployeeVehicleId castOther = (EmployeeVehicleId) other;

		return ((this.getEmployeeId() == castOther.getEmployeeId()) || (this
				.getEmployeeId() != null
				&& castOther.getEmployeeId() != null && this.getEmployeeId()
				.equals(castOther.getEmployeeId())))
				&& ((this.getVehicleId() == castOther.getVehicleId()) || (this
						.getVehicleId() != null
						&& castOther.getVehicleId() != null && this
						.getVehicleId().equals(castOther.getVehicleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getEmployeeId() == null ? 0 : this.getEmployeeId()
						.hashCode());
		result = 37 * result
				+ (getVehicleId() == null ? 0 : this.getVehicleId().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "EmployeeVehicleId [employeeId=" + employeeId + ", vehicleId="
				+ vehicleId + "]";
	}

}