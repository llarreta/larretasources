package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * EmployeeRetirement entity. @author MyEclipse Persistence Tools
 */

public class EmployeeRetirement implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9220052695751096678L;
	private Long id;
	private Employee employee;
	private Date retirementDate;
	private String retirementDescription;

	// Constructors

	/** default constructor */
	public EmployeeRetirement() {
	}

	/** full constructor */
	public EmployeeRetirement(Employee employee, Date retirementDate, String retirementDescription) {
		this.employee = employee;
		this.retirementDate = retirementDate;
		this.retirementDescription = retirementDescription;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getRetirementDate() {
		return this.retirementDate;
	}

	public void setRetirementDate(Date retirementDate) {
		this.retirementDate = retirementDate;
	}

	public String getRetirementDescription() {
		return this.retirementDescription;
	}

	public void setRetirementDescription(String retirementDescription) {
		this.retirementDescription = retirementDescription;
	}

	@Override
	public String toString() {
		return "EmployeeRetirement [id=" + id + "]";
	}

}