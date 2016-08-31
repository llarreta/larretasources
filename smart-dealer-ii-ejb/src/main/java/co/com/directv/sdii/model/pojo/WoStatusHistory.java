package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * WoStatusHistory entity. @author MyEclipse Persistence Tools
 */

public class WoStatusHistory implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 9075115352733924535L;
	private Long id;
	private WorkOrder workOrder;
	private WorkorderReason workorderReason;
	private WorkorderStatus workorderStatus;
	private Date statusDate;
	private String description;
	private String ibsHistoryCodeEvent;
	private String typeChange;
	private User user;

	
	// Constructors
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/** default constructor */
	public WoStatusHistory() {
	}

	/** minimal constructor */
	public WoStatusHistory(WorkOrder workOrder,
			WorkorderStatus workorderStatus, Date statusDate) {
		this.workOrder = workOrder;
		this.workorderStatus = workorderStatus;
		this.statusDate = statusDate;
	}

	/** full constructor */
	public WoStatusHistory(WorkOrder workOrder,
			WorkorderReason workorderReason, WorkorderStatus workorderStatus,
			Date statusDate, String description, String typeChange) {
		this.workOrder = workOrder;
		this.workorderReason = workorderReason;
		this.workorderStatus = workorderStatus;
		this.statusDate = statusDate;
		this.description = description;
		this.typeChange = typeChange;
	}
	
	/** minimal constructor */
	public WoStatusHistory(WorkorderReason workorderReason) {
		this.workorderReason = workorderReason;		
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkOrder getWorkOrder() {
		return this.workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public WorkorderReason getWorkorderReason() {
		return this.workorderReason;
	}

	public void setWorkorderReason(WorkorderReason workorderReason) {
		this.workorderReason = workorderReason;
	}

	public WorkorderStatus getWorkorderStatus() {
		return this.workorderStatus;
	}

	public void setWorkorderStatus(WorkorderStatus workorderStatus) {
		this.workorderStatus = workorderStatus;
	}

	public Date getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIbsHistoryCodeEvent() {
		return ibsHistoryCodeEvent;
	}

	public void setIbsHistoryCodeEvent(String ibsHistoryCodeEvent) {
		this.ibsHistoryCodeEvent = ibsHistoryCodeEvent;
	}
	
	public String getTypeChange() {
		return typeChange;
	}

	public void setTypeChange(String typeChange) {
		this.typeChange = typeChange;
	}

	@Override
	public String toString() {
		return "WoStatusHistory [description=" + description + ", id=" + id
				+ "]";
	}
}