package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

public class SchedulerTaskDTO  implements Serializable{

	private Long id;
	private java.util.Date nextExecutionDate;
	private String countryCode;
	private String schedulerTaskTypesCode;
	private String description;
	
	private Long period;
	
	private String schedulerTaskStatusCode;
	
	public SchedulerTaskDTO() {
		super();
	}
	
	public SchedulerTaskDTO(String countryCode,
			String schedulerTaskTypesCode, String description,
			Date nextExecutionDate, Long period, String schedulerTaskStatusCode) {
		super();
		this.countryCode = countryCode;
		this.schedulerTaskTypesCode = schedulerTaskTypesCode;
		this.description = description;
		this.nextExecutionDate = nextExecutionDate;
		this.period = period;
		this.schedulerTaskStatusCode = schedulerTaskStatusCode;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getSchedulerTaskTypesCode() {
		return schedulerTaskTypesCode;
	}
	public void setSchedulerTaskTypesCode(String schedulerTaskTypesCode) {
		this.schedulerTaskTypesCode = schedulerTaskTypesCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public java.util.Date getNextExecutionDate() {
		return nextExecutionDate;
	}
	public void setNextExecutionDate(java.util.Date nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
	}
	public Long getPeriod() {
		return period;
	}
	public void setPeriod(Long period) {
		this.period = period;
	}
	public String getSchedulerTaskStatusCode() {
		return schedulerTaskStatusCode;
	}
	public void setSchedulerTaskStatusCode(String schedulerTaskStatusCode) {
		this.schedulerTaskStatusCode = schedulerTaskStatusCode;
	}
	
	
}
