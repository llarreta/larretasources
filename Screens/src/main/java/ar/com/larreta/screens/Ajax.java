package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ajax")
@DiscriminatorValue(value = "ajax")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Ajax extends StandardContainer {

	private static final String CHANGE = "change";
	
	private String event;
	private String update;
	private String listener;
	private String eventElementId;
	
	public String getEventElementId() {
		return eventElementId;
	}
	public void setEventElementId(String eventElementId) {
		this.eventElementId = eventElementId;
	}
	@Basic
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public void setChangeEvent(){
		this.event = CHANGE;
	}
	
	@Basic @Column(name="updateAttribute")
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	
	@Basic
	public String getListener() {
		return listener;
	}
	public void setListener(String listener) {
		this.listener = listener;
	}
	
}
