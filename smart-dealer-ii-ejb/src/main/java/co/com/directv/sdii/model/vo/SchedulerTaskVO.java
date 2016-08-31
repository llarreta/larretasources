package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.SchedulerTask;

public class SchedulerTaskVO  extends SchedulerTask implements Serializable {

	private static final long serialVersionUID = 3825591268629443978L;
	
	public SchedulerTaskVO() {
	}
	
	public SchedulerTaskVO(SchedulerTask schedulerTask) {
		setId(schedulerTask.getId());
		setCountry(schedulerTask.getCountry());
		setSchedulerTaskTypes(schedulerTask.getSchedulerTaskTypes());
		setDescription(schedulerTask.getDescription());
		setNextExecutionDate(schedulerTask.getNextExecutionDate());
		setPeriod(schedulerTask.getPeriod());
		setSchedulerTaskStatus(schedulerTask.getSchedulerTaskStatus());
	}
	
}
