package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ElementStatus entity. @author MyEclipse Persistence Tools
 */

public class EventIbs implements java.io.Serializable,Auditable {


	private static final long serialVersionUID = -1527434061734562107L;
	
	private Long id;
	
	private String eventCode;
	
	private String eventName;
	
	private String typeEvent;
	
	private Country country;
	
	
	public EventIbs(){
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEventCode() {
		return eventCode;
	}
	
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getTypeEvent() {
		return typeEvent;
	}

	public void setTypeEvent(String typeEvent) {
		this.typeEvent = typeEvent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}