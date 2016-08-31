package co.com.directv.sdii.ws.business.schedule;

import java.util.List;

import javax.ejb.EJB;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.schedule.SchedulerTaskFacadeLocal;
import co.com.directv.sdii.model.dto.SchedulerTaskDTO;
import co.com.directv.sdii.model.dto.SchedulerTaskDetailsDTO;
import co.com.directv.sdii.ws.business.schedule.impl.ISchedulerTaskWS;

public class SchedulerTaskWS implements ISchedulerTaskWS{

	@EJB
	private SchedulerTaskFacadeLocal schedulerTaskFacade;

	@Override
	public List<SchedulerTaskDTO> getSchedulerTaskByCountryTypeAndState(
			Long countryId, Long typeId, Boolean state)
			throws BusinessException {
		return null;
	}

	@Override
	public List<SchedulerTaskDTO> getSchedulerTaskById(String state, Long id)
			throws BusinessException {
		return null;
	}

	@Override
	public List<SchedulerTaskDetailsDTO> getSchedulerTaskDetailsBySchedulerTaskId(
			Long SchedulerTaskId) throws BusinessException {
		return null;
	}

	@Override
	public void getSchedulerTaskTypes(SchedulerTaskDTO obj)
			throws BusinessException {
		
	}

	@Override
	public void updateSchedulerTask(SchedulerTaskDTO obj)
			throws BusinessException {
		
	}

	
}