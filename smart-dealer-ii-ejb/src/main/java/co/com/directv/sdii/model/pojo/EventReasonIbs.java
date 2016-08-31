package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ElementStatus entity. @author MyEclipse Persistence Tools
 */

public class EventReasonIbs implements java.io.Serializable,Auditable {


	private static final long serialVersionUID = -1527434061734562107L;
	
	private Long id;
	
	private EventIbs eventIbs;
	
	private ReasonIbs reasonIbs;
	
	private WarehouseType warehouseType;
	
	public EventReasonIbs(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EventIbs getEventIbs() {
		return eventIbs;
	}

	public void setEventIbs(EventIbs eventIbs) {
		this.eventIbs = eventIbs;
	}

	public ReasonIbs getReasonIbs() {
		return reasonIbs;
	}

	public void setReasonIbs(ReasonIbs reasonIbs) {
		this.reasonIbs = reasonIbs;
	}

	public WarehouseType getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(WarehouseType warehouseType) {
		this.warehouseType = warehouseType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}