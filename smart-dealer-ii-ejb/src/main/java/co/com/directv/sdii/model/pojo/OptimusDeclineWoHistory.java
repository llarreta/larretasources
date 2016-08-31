package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;


public class OptimusDeclineWoHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1266636157128207408L;
	private Long id;
	private WorkOrder workOrder;
	private Date declineDate;
	private String codeReason;
	private String description;
	private Employee employee;
	
	public OptimusDeclineWoHistory(){
		
	}	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WorkOrder getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}
	public Date getDeclineDate() {
		return declineDate;
	}
	public void setDeclineDate(Date declineDate) {
		this.declineDate = declineDate;
	}
	public String getCodeReason() {
		return codeReason;
	}
	public void setCodeReason(String codeReason) {
		this.codeReason = codeReason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
