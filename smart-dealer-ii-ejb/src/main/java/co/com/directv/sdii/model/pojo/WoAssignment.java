package co.com.directv.sdii.model.pojo;

import java.util.Date;


/**
 * WoAssignment entity. @author MyEclipse Persistence Tools
 */

public class WoAssignment implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680285090223673351L;
	private Long id;
	private Program program;
	private WorkOrder workOrder;
	private Long dealerId;
	private Date dealerAssignmentDate;
	private Long crewId;
	private Date crewAssignmentDate;
	private Date programAssignmentDate;
	private String isActive;
	private Date dealerUnassignmentDate;
	private WoStatusHistory woStatusHistory;

	// Constructors

	/** default constructor */
	public WoAssignment() {
	}
	
	/** minimal constructor */
	public WoAssignment(Long id,Program program,
			WorkOrder workOrder,Long dealerId,Date dealerAssignmentDate,
			Long crewId,Date crewAssignmentDate,Date programAssignmentDate,
			String isActive) {
		this.id=id;
		this.program=program;
		this.workOrder=workOrder;
		this.dealerId=dealerId;
		this.dealerAssignmentDate=dealerAssignmentDate;
		this.crewId=crewId;
		this.crewAssignmentDate=crewAssignmentDate;
		this.programAssignmentDate=programAssignmentDate;
		this.isActive = isActive;
	}

	/** minimal constructor */
	public WoAssignment(WorkOrder workOrder, Long dealerId) {
		this.workOrder = workOrder;
		this.dealerId = dealerId;
	}
	
	/** full constructor */
	public WoAssignment(Long id,Program program,
			WorkOrder workOrder,Long dealerId,Date dealerAssignmentDate,
			Long crewId,Date crewAssignmentDate,Date programAssignmentDate) {
		this.id=id;
		this.program=program;
		this.workOrder=workOrder;
		this.dealerId=dealerId;
		this.dealerAssignmentDate=dealerAssignmentDate;
		this.crewId=crewId;
		this.crewAssignmentDate=crewAssignmentDate;
		this.programAssignmentDate=programAssignmentDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Program getProgram() {
		return this.program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public WorkOrder getWorkOrder() {
		return this.workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Long getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public Date getDealerAssignmentDate() {
		return this.dealerAssignmentDate;
	}

	public void setDealerAssignmentDate(Date dealerAssignmentDate) {
		this.dealerAssignmentDate = dealerAssignmentDate;
	}

	public Long getCrewId() {
		return this.crewId;
	}

	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}

	public Date getCrewAssignmentDate() {
		return this.crewAssignmentDate;
	}

	public void setCrewAssignmentDate(Date crewAssignmentDate) {
		this.crewAssignmentDate = crewAssignmentDate;
	}

	public Date getProgramAssignmentDate() {
		return this.programAssignmentDate;
	}

	public void setProgramAssignmentDate(Date programAssignmentDate) {
		this.programAssignmentDate = programAssignmentDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public Date getDealerUnassignmentDate() {
		return dealerUnassignmentDate;
	}

	public void setDealerUnassignmentDate(Date dealerUnassignmentDate) {
		this.dealerUnassignmentDate = dealerUnassignmentDate;
	}

	public WoStatusHistory getWoStatusHistory() {
		return woStatusHistory;
	}

	public void setWoStatusHistory(WoStatusHistory woStatusHistory) {
		this.woStatusHistory = woStatusHistory;
	}

	@Override
	public String toString() {
		return "WoAssignment [crewId=" + crewId + ", dealerId=" + dealerId
				+ ", id=" + id + ", workOrder=" + workOrder + "]";
	}
}