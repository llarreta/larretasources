package co.com.directv.sdii.model.pojo;

// default package

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;


/**
 * EmployeeVehicle entity. @author MyEclipse Persistence Tools
 */

public class EmployeeVehicle implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8717119965974250890L;
	private EmployeeVehicleId id;
	private Employee employee;
	private Vehicle vehicle;
        private Date allocationDate;

	// Constructors

	/** default constructor */
	public EmployeeVehicle() {
	}

	/** full constructor */
	public EmployeeVehicle(EmployeeVehicleId id, Employee employee,
			Vehicle vehicle, Date allocationDate) {
		this.id = id;
		this.employee = employee;
		this.vehicle = vehicle;
		this.allocationDate = allocationDate;
	}
	
	public EmployeeVehicle(EmployeeVehicleId id, Employee employee, Date allocationDate){
		this.id = id;
		this.employee = employee;
		this.allocationDate = allocationDate;
	}

	// Property accessors

	public EmployeeVehicleId getId() {
		return this.id;
	}

	public void setId(EmployeeVehicleId id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Date getAllocationDate() {
		return this.allocationDate;
	}

	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}

	@Override
	public String toString() {
		return "EmployeeVehicle [id=" + id + "]";
	}
	
}