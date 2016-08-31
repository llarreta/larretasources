package co.com.directv.sdii.model.pojo;

import java.util.Date;

/**
 * Pojo Table SCHEDULER_TASK_DETAILS
 * 
 * Fecha de Creación: 8/05/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class SchedulerTaskDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 470111272399884490L;

	private Long id;
	private SchedulerTask schedulerTask;
	private SchedulerTaskDetailsStatus schedulerTaskDetailsStatus;
	private java.util.Date initDate;
	private java.util.Date endDate;
	private String description;
	
	public SchedulerTaskDetails() {
		super();
	}
	
	public SchedulerTaskDetails(Long id, SchedulerTask schedulerTask,
			SchedulerTaskDetailsStatus schedulerTaskDetailsStatus,
			Date initDate, Date endDate, String description) {
		super();
		this.id = id;
		this.schedulerTask = schedulerTask;
		this.schedulerTaskDetailsStatus = schedulerTaskDetailsStatus;
		this.initDate = initDate;
		this.endDate = endDate;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SchedulerTask getSchedulerTask() {
		return schedulerTask;
	}

	public void setSchedulerTask(SchedulerTask schedulerTask) {
		this.schedulerTask = schedulerTask;
	}

	public SchedulerTaskDetailsStatus getSchedulerTaskDetailsStatus() {
		return schedulerTaskDetailsStatus;
	}

	public void setSchedulerTaskDetailsStatus(
			SchedulerTaskDetailsStatus schedulerTaskDetailsStatus) {
		this.schedulerTaskDetailsStatus = schedulerTaskDetailsStatus;
	}

	public java.util.Date getInitDate() {
		return initDate;
	}

	public void setInitDate(java.util.Date initDate) {
		this.initDate = initDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}