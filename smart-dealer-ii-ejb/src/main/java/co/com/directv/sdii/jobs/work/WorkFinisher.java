package co.com.directv.sdii.jobs.work;

import javax.naming.NamingException;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.ejb.business.config.SchedulerTaskBussinessRemote;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;

public class WorkFinisher {
	
	private Long idScheduler;
	private Long idSchedulerDetail;
	private SchedulerTaskBussinessRemote schedulerTaskBussinessLocal;
	private Long countryId;
	
	public WorkFinisher(Long countryId,Long idParam) throws NamingException, PropertiesException{
		idScheduler = idParam;
		this.countryId = countryId;
		schedulerTaskBussinessLocal = AssignmentFacadeLocator.getInstance().getSchedulerTaskFacade();
	}
	
	public void beginWork() throws BusinessException, PropertiesException{
		idSchedulerDetail = schedulerTaskBussinessLocal.beginWork(idScheduler, countryId);
	}
	
	public void finishWork(String detail, boolean fail) throws BusinessException, PropertiesException{
		schedulerTaskBussinessLocal.finishWork(idSchedulerDetail, detail, fail);
	}

	public Long getIdScheduler() {
		return idScheduler;
	}

	public void setIdScheduler(Long idScheduler) {
		this.idScheduler = idScheduler;
	}

	public Long getIdSchedulerDetail() {
		return idSchedulerDetail;
	}

	public void setIdSchedulerDetail(Long idSchedulerDetail) {
		this.idSchedulerDetail = idSchedulerDetail;
	}

	public SchedulerTaskBussinessRemote getSchedulerTaskBussinessLocal() {
		return schedulerTaskBussinessLocal;
	}

	public void setSchedulerTaskBussinessLocal(
			SchedulerTaskBussinessRemote schedulerTaskBussinessLocal) {
		this.schedulerTaskBussinessLocal = schedulerTaskBussinessLocal;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	
}
