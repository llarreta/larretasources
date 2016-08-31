package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * WorkOrderAgenda entity. @author MyEclipse Persistence Tools
 */

public class WorkOrderAgenda implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3966937332722367721L;
	private Long id;
	private WoAssignment woAssignment;
	private ServiceHour serviceHour;
	private String contactPerson;
	private String description;
	private Date agendationDate;
	private String status;

	// Constructors

	/** default constructor */
	public WorkOrderAgenda() {
	}

	/** minimal constructor */
	public WorkOrderAgenda(WoAssignment woAssignment, ServiceHour serviceHour,
			String contactPerson, Date agendationDate) {
		this.woAssignment = woAssignment;
		this.serviceHour = serviceHour;
		this.contactPerson = contactPerson;
		this.agendationDate = agendationDate;
	}
	
	/** minimal constructor */
	public WorkOrderAgenda(ServiceHour serviceHour,	Date agendationDate) {		
		this.serviceHour = serviceHour;		
		this.agendationDate = agendationDate;
	}

	/** full constructor */
	public WorkOrderAgenda(WoAssignment woAssignment, ServiceHour serviceHour,
			String contactPerson, String description, Date agendationDate) {
		this.woAssignment = woAssignment;
		this.serviceHour = serviceHour;
		this.contactPerson = contactPerson;
		this.description = description;
		this.agendationDate = agendationDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WoAssignment getWoAssignment() {
		return this.woAssignment;
	}

	public void setWoAssignment(WoAssignment woAssignment) {
		this.woAssignment = woAssignment;
	}

	public ServiceHour getServiceHour() {
		return this.serviceHour;
	}

	public void setServiceHour(ServiceHour serviceHour) {
		this.serviceHour = serviceHour;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getAgendationDate() {
		return this.agendationDate;
	}

	public void setAgendationDate(Date agendationDate) {
		this.agendationDate = agendationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WorkOrderAgenda [agendationDate=" + agendationDate + ", id="
				+ id + ", woAssignment=" + woAssignment + "]";
	}
}