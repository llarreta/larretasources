package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * EmployeeCrewId entity. @author MyEclipse Persistence Tools
 */

public class EmployeeCrewId implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8291888034788276064L;
	private Long crewId;
	private Long employeeId;

	// Constructors

	/** default constructor */
	public EmployeeCrewId() {
	}

	/** full constructor */
	public EmployeeCrewId(Long crewId, Long employeeId) {
		this.crewId = crewId;
		this.employeeId = employeeId;
	}

	// Property accessors

	public Long getCrewId() {
		return this.crewId;
	}

	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmployeeCrewId))
			return false;
		EmployeeCrewId castOther = (EmployeeCrewId) other;

		return ((this.getCrewId() == castOther.getCrewId()) || (this
				.getCrewId() != null
				&& castOther.getCrewId() != null && this.getCrewId().equals(
				castOther.getCrewId())))
				&& ((this.getEmployeeId() == castOther.getEmployeeId()) || (this
						.getEmployeeId() != null
						&& castOther.getEmployeeId() != null && this
						.getEmployeeId().equals(castOther.getEmployeeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCrewId() == null ? 0 : this.getCrewId().hashCode());
		result = 37
				* result
				+ (getEmployeeId() == null ? 0 : this.getEmployeeId()
						.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "EmployeeCrewId [crewId=" + crewId + ", employeeId="
				+ employeeId + "]";
	}

}