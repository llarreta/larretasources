package co.com.directv.sdii.ws.business.schedule.impl;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.SchedulerTaskDTO;
import co.com.directv.sdii.model.dto.SchedulerTaskDetailsDTO;
import co.com.directv.sdii.model.vo.SchedulerTaskVO;

@WebService(name="ISchedulerTaskWS",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface ISchedulerTaskWS {
	
	@WebMethod(operationName="getSchedulerTaskByStateAndId", action="getSchedulerTaskByStateAndId", exclude = false)
	public List<SchedulerTaskDTO> getSchedulerTaskById(String state, Long id) throws BusinessException;
	
	@WebMethod(operationName="getSchedulerTaskByStateAndId", action="getSchedulerTaskByStateAndId", exclude = false)
	public void updateSchedulerTask(SchedulerTaskDTO obj) throws  BusinessException;

	@WebMethod(operationName="getSchedulerTaskTypes", action="getSchedulerTaskTypes", exclude = false)
	public void getSchedulerTaskTypes(SchedulerTaskDTO obj) throws  BusinessException;
	
	@WebMethod(operationName="getSchedulerTaskByStateAndId", action="getSchedulerTaskByStateAndId", exclude = false)
	public List<SchedulerTaskDTO> getSchedulerTaskByCountryTypeAndState(Long countryId, Long typeId, Boolean state) throws BusinessException;
	
	@WebMethod(operationName="getSchedulerTaskByStateAndId", action="getSchedulerTaskByStateAndId", exclude = false)
	public List<SchedulerTaskDetailsDTO> getSchedulerTaskDetailsBySchedulerTaskId(Long SchedulerTaskId) throws BusinessException;
}
