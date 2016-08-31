package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

public class WoProgramAssigments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3527758816508071118L;
	
	private Long id;
	private WorkOrder woId;
	private Date programAssigmentDate;
	private Program programId;
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
	public Date getProgramAssigmentDate() {
		return programAssigmentDate;
	}
	public void setProgramAssigmentDate(Date programAssigmentDate) {
		this.programAssigmentDate = programAssigmentDate;
	}
	public Program getProgramId() {
		return programId;
	}
	public void setProgramId(Program programId) {
		this.programId = programId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
