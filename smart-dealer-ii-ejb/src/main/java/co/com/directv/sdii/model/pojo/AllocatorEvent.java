package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * AllocatorEvent entity. @author MyEclipse Persistence Tools
 */

public class AllocatorEvent implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 790129693729822577L;
	private Long id;
	private WorkOrder workOrder;
	private String eventCode;
	private String eventMessage;
	private Date eventDate;
	private AllocatorEventMaster allocatorEventMaster;

	// Constructors

	/** default constructor */
	public AllocatorEvent() {
	}

	/**
	 * 
	 * @param workOrder
	 * @param eventCode
	 * @param eventMessage
	 * @param eventDate
	 * @param allocatorEventMaster
	 */
	public AllocatorEvent(WorkOrder workOrder, String eventCode,String eventMessage, Date eventDate, AllocatorEventMaster allocatorEventMaster ) {
		
		this.workOrder = workOrder;
		this.eventCode = eventCode;
		this.eventMessage = eventMessage;
		this.eventDate = eventDate;
		this.allocatorEventMaster = allocatorEventMaster;
		
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

	public String getEventCode() {
		return this.eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventMessage() {
		return this.eventMessage;
	}

	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}

	public Date getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@Override
	public String toString() {
		return "AllocatorEvent [eventCode=" + eventCode + ", id=" + id + "]";
	}

	public AllocatorEventMaster getAllocatorEventMaster() {
		return allocatorEventMaster;
	}

	public void setAllocatorEventMaster(AllocatorEventMaster allocatorEventMaster) {
		this.allocatorEventMaster = allocatorEventMaster;
	}

	
}