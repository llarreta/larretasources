package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

public class WoCrewAssigments implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8801112146702198620L;
	
	private Long id;
	private WorkOrder woId;
	private Date crewAssigmentDate;
	private Crew crewId;
	private String isActive;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WorkOrder getWoId() {
		return woId;
	}
	public void setWoId(WorkOrder woId) {
		this.woId = woId;
	}
	public Date getCrewAssigmentDate() {
		return crewAssigmentDate;
	}
	public void setCrewAssigmentDate(Date crewAssigmentDate) {
		this.crewAssigmentDate = crewAssigmentDate;
	}
	public Crew getCrewId() {
		return crewId;
	}
	public void setCrewId(Crew crewId) {
		this.crewId = crewId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
