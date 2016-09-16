package co.com.directv.sdii.ejb.business.wo.impl;

import java.util.Date;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.dto.esb.notifyWorkOrdersOptimus.WorkOrderStatusChange;
import co.com.directv.sdii.ejb.business.config.OptimusStatusEventBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.wo.RequestProcessorStatusMessage;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.OptimusStatus;
import co.com.directv.sdii.model.pojo.OptimusStatusEvent;
import co.com.directv.sdii.model.pojo.OptimusStatusHistory;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.OptimusStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.OptimusStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;


@Stateless(name = "StatusMessageProcessor", mappedName = "ejb/StatusMessageProcessor")
public class StatusMessageProcessor implements RequestProcessorStatusMessage{
	
	private final static Logger log = UtilsBusiness.getLog4J(DeclineWOProcessor.class);

    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal userDAO;
    
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDAO;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name = "CustomerDAOLocal", beanInterface = CustomerDAOLocal.class)
	private CustomerDAOLocal customerDAO;
	
	@EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAO;	
	
	@EJB(name="OptimusStatusDAOLocal", beanInterface=OptimusStatusDAOLocal.class)
	private OptimusStatusDAOLocal optimusStatusDAO;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDAO;
	
	@EJB(name="OptimusStatusHistoryDAOLocal", beanInterface=OptimusStatusHistoryDAOLocal.class)
	private OptimusStatusHistoryDAOLocal optimusStatusHistoryDAO;
	
	@EJB(name="EmployeeDAOLocal", beanInterface=EmployeeDAOLocal.class)
	private EmployeeDAOLocal employeeDAO;
	
	@EJB(name = "OptimusStatusEventBusinessBeanLocal", beanInterface=OptimusStatusEventBusinessBeanLocal.class)
	private OptimusStatusEventBusinessBeanLocal optimusStatusEventBusinessBean;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void start(WorkOrderStatusChange workOrderStatusChange, OptimusStatusEvent optimusStatusEvent) throws PropertiesException, BusinessException {
		
		log.debug("== Inicia start/StatusMessageProcessor ==");
		
		try {
			this.validateRequiredData(workOrderStatusChange);
            
			WorkOrder workOrder = this.getWorkOrder(workOrderStatusChange);
			//Employee employee = this.getEmployee(workOrderStatusChange);
			OptimusStatus optimusStatus = this.getOptimusStatus(workOrderStatusChange);
			User userAdmin = this.getUserAdmin(workOrderStatusChange);
			Date createdDate = this.getCreatedDateInTZ(workOrderStatusChange, userAdmin);
			
			/*** guardo el estado de historial de optimusStatusHistories ***/
			OptimusStatusHistory osh = new OptimusStatusHistory();
			osh.setWorkOrder(workOrder);
			osh.setOptimusStatus(optimusStatus);
			//osh.setEmployee(employee);
			osh.setOptimusDate(createdDate);
			optimusStatusHistoryDAO.createOptimusStatusHistory(osh);
			
			/**** actualizo la work order con la marca que llego desde optimus y la fecha***/
			workOrder.setOptimusStatus(optimusStatus);
			workOrder.setOptimusStatusDate(createdDate);
			workOrderDAO.updateWorkOrder(workOrder, CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_OPTIMUS.getCodeEntity());
			
			optimusStatusEvent.setLogDescription(null);
			optimusStatusEvent.setStatus(CodesBusinessEntityEnum.OPTIMUS_STATUS_EVENT_OK.getCodeEntity());
			optimusStatusEventBusinessBean.updateOptimusStatusEvent(optimusStatusEvent);
			
		} catch (Throwable t) {
			log.error(t.getMessage());
			optimusStatusEvent.setLogDescription(t.getMessage());
			optimusStatusEvent.setStatus(CodesBusinessEntityEnum.OPTIMUS_STATUS_EVENT_ERROR.getCodeEntity());
			optimusStatusEventBusinessBean.updateOptimusStatusEvent(optimusStatusEvent);
		} finally {
	        log.debug("== Termina start/StatusMessageProcessor ==");	 
	    }
	}

	private void validateRequiredData(WorkOrderStatusChange workOrderStatusChange) throws BusinessException {
		UtilsBusiness.assertNotNull(workOrderStatusChange, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getRequestMetadata(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		//UtilsBusiness.assertNotNull(workOrderStatusChange.getRequestMetadata().getUserID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	    //UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getTechnician(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());		
	    //UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getTechnician().getID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());		
		UtilsBusiness.assertNotNull(workOrderStatusChange.getRequestMetadata().getSender(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());		
		UtilsBusiness.assertNotNull(workOrderStatusChange.getRequestMetadata().getSender().getCountry(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());		
		
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getInteractionDate() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		UtilsBusiness.assertNotNull(workOrderStatusChange.getStatus() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		//UtilsBusiness.assertNotNull(workOrderStatusChange.getStatus().getId() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getStatus().getName() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getStatus().getCode() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}

	private User getUserAdmin(WorkOrderStatusChange workOrderStatusChange) throws BusinessException, DAOServiceException, DAOSQLException {
		String countryCode = workOrderStatusChange.getRequestMetadata().getSender().getCountry();
		Country country = countryDAO.getCountriesByCode(countryCode);
		
		if (country == null){
			throw new BusinessException(ErrorBusinessMessages.COUNTRY_NOT_EXIST.getCode() , ErrorBusinessMessages.COUNTRY_NOT_EXIST.getMessage() );
		}
		
		Long userAdminId = UtilsBusiness.getUserIdAdmin(userDAO, systemParameterDAO, country.getId());
		User userAdmin = userDAO.getUserById(userAdminId);
			
		if (userAdmin == null){
			throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode() , ErrorBusinessMessages.USER_NOT_EXIST.getMessage() );
		}
		return userAdmin;
	}
	
	private Date getCreatedDateInTZ(WorkOrderStatusChange workOrderStatusChange, User user) throws BusinessException {		

		TimeZone countryTZ = TimeZone.getTimeZone(user.getSdiiTimeZone().getTimeZoneKey());
		
		Date createdDate = UtilsBusiness.convertDateInTimezone(workOrderStatusChange.getCustWorkOrd().getInteractionDate(), countryTZ);
		if(createdDate == null){
			throw new BusinessException(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getCode() , ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getMessage() );
		}
		
		return createdDate;
	}
	
	private OptimusStatus getOptimusStatus(WorkOrderStatusChange workOrderStatusChange) throws DAOSQLException, DAOServiceException, BusinessException {
		String optimusStatusCode = workOrderStatusChange.getStatus().getCode();
		OptimusStatus optimusStatus = optimusStatusDAO.getOptimusStatusById(optimusStatusCode);
		
		if (optimusStatus == null){
			throw new BusinessException( " No existe el codigo ", "No existe el codigo");
		}
		return optimusStatus;
	}

	/*private Employee getEmployee(WorkOrderStatusChange workOrderStatusChange) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		//Employee employee = employeeDAO.getEmployeeByDocumentNumberAndStatus(workOrderStatusChange.getRequestMetadata().getUserID(), CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());
		Employee employee = employeeDAO.getEmployeeByDocumentNumberAndStatus(workOrderStatusChange.getCustWorkOrd().getTechnician().getID() , CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());

		if (employee == null){
			throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode() , ErrorBusinessMessages.USER_NOT_EXIST.getMessage() );
		}
		return employee;
	}*/

	private WorkOrder getWorkOrder(WorkOrderStatusChange workOrderStatusChange) throws DAOServiceException, DAOSQLException, BusinessException {
		String workOrderId = workOrderStatusChange.getCustWorkOrd().getID();
		WorkOrder workOrder = workOrderDAO.getWorkOrderByCode(workOrderId);		
		
		if(workOrder == null){
			throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getCode() , ErrorBusinessMessages.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getMessage());
		}
		return workOrder;
	}

}
