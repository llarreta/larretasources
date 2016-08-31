package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * Pojo Table SCHEDULER_TASK
 * 
 * Fecha de Creación: 8/05/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class SchedulerTask implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 470111272399884490L;

	/**
	 * Fields
	 */
	/**
	 * id del programador de tareas
	 */
	private Long id;
	
	/**
	 * Pais del programador de tareas
	 */
	private Country country;
	
	/**
	 * Tipos del programador de tareas
	 */
	private SchedulerTaskTypes schedulerTaskTypes;
	
	/**
	 * descripcion del programador de tareas
	 */
	private String description;
	
	/**
	 * Proxima fecha de ejecucion del programador de tareas
	 */
	private java.util.Date nextExecutionDate;
	
	/**
	 * Periodo de ejecucion del programador de tareas
	 */
	private Long period;
	
	/**
	 * Estado del programador de tareas
	 */
	private SchedulerTaskStatus schedulerTaskStatus;
	
	/**
	 * Constructor: vacio
	 * @author
	 */
	public SchedulerTask() {
		super();
	}

	/**
	 * Constructor: Con todos los atributos
	 * @param id
	 * @param country
	 * @param schedulerTaskTypes
	 * @param description
	 * @param nextExecutionDate
	 * @param period
	 * @param schedulerTaskStatus
	 * @author
	 */
	public SchedulerTask(Long id, Country country,
			SchedulerTaskTypes schedulerTaskTypes, String description,
			Date nextExecutionDate, Long period,
			SchedulerTaskStatus schedulerTaskStatus) {
		super();
		this.id = id;
		this.country = country;
		this.schedulerTaskTypes = schedulerTaskTypes;
		this.description = description;
		this.nextExecutionDate = nextExecutionDate;
		this.period = period;
		this.schedulerTaskStatus = schedulerTaskStatus;
	}
	
	public SchedulerTask(SchedulerTask schedulerTask) {
		super();
		this.id = schedulerTask.getId();
		this.country = schedulerTask.getCountry();
		this.schedulerTaskTypes = schedulerTask.getSchedulerTaskTypes();
		this.description = schedulerTask.getDescription();
		this.nextExecutionDate = schedulerTask.getNextExecutionDate();
		this.period = schedulerTask.getPeriod();
		this.schedulerTaskStatus = schedulerTask.getSchedulerTaskStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public SchedulerTaskTypes getSchedulerTaskTypes() {
		return schedulerTaskTypes;
	}

	public void setSchedulerTaskTypes(SchedulerTaskTypes schedulerTaskTypes) {
		this.schedulerTaskTypes = schedulerTaskTypes;
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

	public SchedulerTaskStatus getSchedulerTaskStatus() {
		return schedulerTaskStatus;
	}

	public void setSchedulerTaskStatus(SchedulerTaskStatus schedulerTaskStatus) {
		this.schedulerTaskStatus = schedulerTaskStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}