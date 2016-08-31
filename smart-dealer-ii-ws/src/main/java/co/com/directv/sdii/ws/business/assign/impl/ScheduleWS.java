
/**
 * Creado 10/06/2011 17:28:22
 */
package co.com.directv.sdii.ws.business.assign.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.assign.assignment.AssignmentFacade;
import co.com.directv.sdii.assign.schedule.dto.DayServiceHourWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.ws.business.assign.IScheduleWS;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleResponseDTO;

/**
 * implementa las operaciones de consulta de la agenda
 * 
 * Fecha de Creación: 10/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@MTOM
@WebService(serviceName="ScheduleWSService",
		endpointInterface="co.com.directv.sdii.ws.business.assign.IScheduleWS",
		targetNamespace="http://assign.business.ws.sdii.directv.com.co/",
		portName="ScheduleWSPort")
@Stateless()
public class ScheduleWS implements IScheduleWS {

	@EJB(name="AssignmentFacade", beanInterface=AssignmentFacade.class)
	private AssignmentFacade assignmentFacade;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IScheduleWS#getSchedule(co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO)
	 */
	@Override
	public AssignScheduleResponseDTO getSchedule(AssignScheduleRequestDTO request) throws BusinessException {
		AssignScheduleResponseDTO result;
		try {
			result = assignmentFacade.getSchedule(request);
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessageCode(), e.getMessage(), e.getParameters());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IScheduleWS#getScheduleCSR(co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO)
	 */
	@Override
	public AssignScheduleResponseDTO getScheduleCSR(AssignScheduleRequestDTO request) throws BusinessException, PropertiesException {
		return assignmentFacade.getScheduleCSR(request);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IScheduleWS#getPreScheduleCSR(co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO, co.com.directv.sdii.model.dto.WorkOrderCSRDTO)
	 */
	public AssignRequestDTO getPreScheduleCSR(AssignScheduleRequestDTO request)throws BusinessException, PropertiesException {
		return assignmentFacade.getPreScheduleCSR(request);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.IScheduleWS#agendaWorkOrdersCSR(co.com.directv.sdii.model.dto.WorkOrderCSRDTO)
	 */
	@Override
	public void agendaWorkOrdersCSR(WorkOrderCSRDTO request) throws BusinessException{
		assignmentFacade.agendaWorkOrdersCSR(request);
	}
	
	@Override
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduledCSR(DayServiceHourWorkLoad dayServiceHourWorkLoad, int quantityWorkOrder) throws BusinessException {
		return assignmentFacade.checkAboveScheduledCSR(dayServiceHourWorkLoad,quantityWorkOrder);
	}
	
}
