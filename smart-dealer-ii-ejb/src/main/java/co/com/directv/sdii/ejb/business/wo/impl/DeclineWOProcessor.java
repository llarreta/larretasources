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
import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.OptimusStatusEventBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.wo.RequestProcessorDeclineWorkOrder;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.OptimusDeclineWoHistory;
import co.com.directv.sdii.model.pojo.OptimusStatusEvent;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderMark;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.OptimusDeclineWoHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderWorkOrderMarkDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

@Stateless(name="DeclineWOProcessor", mappedName="ejb/DeclineWOProcessor")
public class DeclineWOProcessor implements RequestProcessorDeclineWorkOrder{

	private final static Logger log = UtilsBusiness.getLog4J(DeclineWOProcessor.class);
	
	@EJB(name="WorkOrderWorkOrderMarkDAOLocal", beanInterface=WorkOrderWorkOrderMarkDAOLocal.class)
	private WorkOrderWorkOrderMarkDAOLocal workOrderWorkOrderMarkDAO;
	
	@EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAO;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDAO;
	
	@EJB(name="CustomerDAOLocal", beanInterface=CustomerDAOLocal.class)
	private CustomerDAOLocal customerDAO;
	
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal userDAO;
    
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDAO;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
    
    @EJB(name="OptimusDeclineWoHistoryDAOLocal", beanInterface=OptimusDeclineWoHistoryDAOLocal.class)
    private OptimusDeclineWoHistoryDAOLocal optimusDeclineWoHistoryDAO;
    
	@EJB(name="OptimusStatusEventBusinessBeanLocal", beanInterface=OptimusStatusEventBusinessBeanLocal.class)
	private OptimusStatusEventBusinessBeanLocal optimusStatusEventBusinessBean;
	
	@EJB(name="WorkOrderMarkBusinessBeanLocal",beanInterface=WorkOrderMarkBusinessBeanLocal.class)
	private WorkOrderMarkBusinessBeanLocal workOrderMarkBusiness;
	
	@EJB(name="EmployeeDAOLocal", beanInterface=EmployeeDAOLocal.class)
	private EmployeeDAOLocal employeeDAO;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void start(WorkOrderStatusChange workOrderStatusChange, OptimusStatusEvent optimusStatusEvent) throws PropertiesException, BusinessException {
		log.debug("== Inicia start/DeclineWOProcessor ==");
		
		 try {
			  this.validateRequiredData(workOrderStatusChange);	

			  WorkOrder workOrder = this.getWorkOrder(workOrderStatusChange);
			  Employee employee = this.getEmployee(workOrderStatusChange);
			  User userAdmin = this.getUserAdmin(workOrderStatusChange);
			  Date createdDate = this.getCreatedDateInTZ(workOrderStatusChange, userAdmin);
			  String reasonCode = workOrderStatusChange.getReason().getId();
			  String reasonDescription = workOrderStatusChange.getReason().getName();
			  
			  Long workOrderMarkId = CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_REJECTED_BY_CREW.getIdEntity(WorkOrderMark.class.getName());
			  workOrderMarkBusiness.markWorkOrder(workOrder.getId(), workOrderMarkId, userAdmin.getId());
			  
			  OptimusDeclineWoHistory optimusDeclineHistory = new OptimusDeclineWoHistory();
			  optimusDeclineHistory.setWorkOrder(workOrder);
			  optimusDeclineHistory.setDeclineDate(createdDate);
			  optimusDeclineHistory.setCodeReason(reasonCode);
			  optimusDeclineHistory.setDescription(reasonDescription);
			  optimusDeclineHistory.setEmployee(employee);
			  optimusDeclineWoHistoryDAO.createOptimusDeclineWoHistory(optimusDeclineHistory);
			  
			  workOrder.setOptimusDeclineCode(reasonCode);
			  workOrder.setOptimusDeclineDescription(reasonDescription);
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
		     log.debug("== Termina start/DeclineWOProcessor ==");	 
		 }
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
		
		TimeZone countryTZ = TimeZone.getTimeZone(user.getSdiiTimeZone().getTimeZoneKey()) ;
		
		Date createdDate = UtilsBusiness.convertDateInTimezone(workOrderStatusChange.getCustWorkOrd().getInteractionDate(), countryTZ);
		if(createdDate == null){
			throw new BusinessException(ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getCode() , ErrorBusinessMessages.DATE_OR_HOUR_RANGE_INVALID.getMessage() );
		}
		
		return createdDate;
	}
	
	private WorkOrder getWorkOrder(WorkOrderStatusChange workOrderStatusChange) throws DAOServiceException, DAOSQLException, BusinessException {
		String workOrderCode = workOrderStatusChange.getCustWorkOrd().getID();
		  WorkOrder workOrder = workOrderDAO.getWorkOrderByCode(workOrderCode);
		
		  if(workOrder == null){
			  throw new BusinessException(ErrorBusinessMessages.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getCode() , ErrorBusinessMessages.CORE_THE_WORK_ORDER_DOES_NOT_EXIST.getMessage());
		  }
		return workOrder;
	}
	
	private Employee getEmployee(WorkOrderStatusChange workOrderStatusChange) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		//Employee employee = employeeDAO.getEmployeeByDocumentNumberAndStatus(workOrderStatusChange.getRequestMetadata().getUserID(), CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());
		Employee employee = employeeDAO.getEmployeeByDocumentNumberAndStatus(workOrderStatusChange.getCustWorkOrd().getTechnician().getID() , CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());

		if (employee == null){
			throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode() , ErrorBusinessMessages.USER_NOT_EXIST.getMessage() );
		}
		return employee;
	}
	
	private void validateRequiredData(WorkOrderStatusChange workOrderStatusChange) throws BusinessException {
		UtilsBusiness.assertNotNull(workOrderStatusChange, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getRequestMetadata(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		//UtilsBusiness.assertNotNull(workOrderStatusChange.getRequestMetadata().getUserID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getTechnician(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());		
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getTechnician().getID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());		
		UtilsBusiness.assertNotNull(workOrderStatusChange.getRequestMetadata().getSender(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getRequestMetadata().getSender().getCountry(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getCustomerKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getCustWorkOrd().getInteractionDate() , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderStatusChange.getReason(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());	
		UtilsBusiness.assertNotNull(workOrderStatusChange.getReason().getName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());	
		UtilsBusiness.assertNotNull(workOrderStatusChange.getReason().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}

}
