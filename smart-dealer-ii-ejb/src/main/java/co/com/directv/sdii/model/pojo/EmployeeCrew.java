package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

// default package

/**
 * EmployeeCrew entity. @author MyEclipse Persistence Tools
 */

public class EmployeeCrew implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6212707156636012822L;
	private EmployeeCrewId id;
	private Crew crew;
	private Employee employee;
	private EmployeeRol employeeRol;
	private Date allocationDate;
	private String isResponsible;

	// Constructors

	/** default constructor */
	public EmployeeCrew() {
	}
	/** minimal constructor */
	public EmployeeCrew(Crew crew, Employee employee) {
		this.crew = crew;
		this.employee = employee;
	}
	
	/** full constructor */
	public EmployeeCrew(EmployeeCrewId id, Crew crew, Employee employee,
			EmployeeRol employeeRol, Date allocationDate, String isResponsible) {
		this.id = id;
		this.crew = crew;
		this.employee = employee;
		this.employeeRol = employeeRol;
		this.allocationDate = allocationDate;
		this.isResponsible = isResponsible;
	}

	// Property accessors

	public EmployeeCrewId getId() {
		return this.id;
	}

	public void setId(EmployeeCrewId id) {
		this.id = id;
	}

	public Crew getCrew() {
		return this.crew;
	}

	public void setCrew(Crew crew) {
		this.crew = crew;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeeRol getEmployeeRol() {
		return this.employeeRol;
	}

	public void setEmployeeRol(EmployeeRol employeeRol) {
		this.employeeRol = employeeRol;
	}

	public Date getAllocationDate() {
		return this.allocationDate;
	}

	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}

	public String getIsResponsible() {
		return this.isResponsible;
	}

	public void setIsResponsible(String isResponsible) {
		this.isResponsible = isResponsible;
	}
	
	@Override
	public String toString() {
		return "EmployeeCrew [id=" + id + "]";
	}

}