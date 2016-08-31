package co.com.directv.sdii.ejb.business.core.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.impl.CSRBusiness;
import co.com.directv.sdii.ejb.business.broker.impl.ContactCoreBrokerImpl;
import co.com.directv.sdii.ejb.business.core.ContactCSRBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.model.pojo.Contact;
import co.com.directv.sdii.model.pojo.ContactConfiguration;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WoType;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.ContactVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ContactConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ContactDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * Gestina la creacion del contact CSR 
 * 
 * Fecha de Creación: 12/04/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="ContactCSRBusinessBeanLocal",mappedName="ejb/ContactCSRBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactCSRBusinessBean extends BusinessBase implements ContactCSRBusinessBeanLocal {

	@EJB(name="UserDAOLocal",beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDAO;
	
	@EJB(name="WoStatusHistoryDAOLocal", beanInterface=WoStatusHistoryDAOLocal.class)
	private WoStatusHistoryDAOLocal woStatusHistoryDAO;
	
	@EJB(name="ContactDAOLocal", beanInterface=ContactDAOLocal.class)
    private ContactDAOLocal contactDAO;
	
	@EJB(name="ShippingOrderDAOLocal", beanInterface=ShippingOrderDAOLocal.class)
	private ShippingOrderDAOLocal shippingDAO;
	
	@EJB(name="ContactConfigurationDAOLocal", beanInterface=ContactConfigurationDAOLocal.class)
	private ContactConfigurationDAOLocal contactConfigDAO;
	
	@EJB(name="ServiceDAOLocal", beanInterface=ServiceDAOLocal.class)
	private ServiceDAOLocal serviceDao;
	
	@EJB(name="WoTypeDAOLocal", beanInterface=WoTypeDAOLocal.class)
	private WoTypeDAOLocal woTypeDao;
	
	@EJB(name="CoreWOBusinessLocal", beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal coreWOBusiness;
	
	@EJB(name="WorkorderReasonDAOLocal", beanInterface=WorkorderReasonDAOLocal.class)
	private WorkorderReasonDAOLocal woReasonDAO;
	
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
    private final static Logger log = UtilsBusiness.getLog4J(CSRBusiness.class);
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.ContactCSRBusinessBeanLocal#createWoContact(co.com.directv.sdii.model.pojo.WoStatusHistory, co.com.directv.sdii.model.pojo.WorkorderReason, java.lang.String, co.com.directv.sdii.model.pojo.WorkorderStatus, co.com.directv.sdii.model.dto.WorkOrderCSRDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWoContact(WoStatusHistory woStatusHistory, 
			                    WorkorderReason woReason, 
			                    String countryCode,
			                    WorkorderStatus woStatus,
			                    WorkOrderCSRDTO request,
			                    WorkOrder workOrderPojo) throws BusinessException{
		
		log.debug("== Inicia createWoContact/ContactCSRBusiness ==");
		
		try{
			
			//CC Creacion de Contacts jalopez			
			ContactDTO contactDTO = new ContactDTO();
			contactDTO.setWoCode(request.getWoCode());
			if(workOrderPojo !=null)
				contactDTO.setWoStatusHistoryId(woStatusHistory.getId());
			contactDTO.setWoReasonId( woReason.getId() );
			contactDTO.setUserId(request.getUserId());
			contactDTO.setCountryCode(countryCode);
			contactDTO.setCountryId(request.getCountryId());
			contactDTO.setCustomerCode(request.getIbsCustomerCode());
			
			WoType woType = linkTypeServiceOrder(request.getServices());
			
			createContactCoreCSR( contactDTO,request, woStatus,woType, workOrderPojo);
			
		} catch (Throwable ex) {
			
			log.debug("== Error al tratar de ejecutar la operación createWoContact/createWoContact");
			throw super.manageException(ex);
			
		} finally {
			
			log.debug("== Termina createWoContact/ContactCSRBusiness ==");
			
		}		
	}

	/**
	 * Metodo: crea el contact dependiendo si es agendamiento o reagendamiento
	 * @param contactDTO
	 * @param workOrderCSRDTO
	 * @param woStatus
	 * @param woType
	 * @param workOrderPojo
	 * @throws BusinessException
	 */
	private void createContactCoreCSR(ContactDTO contactDTO,
			 WorkOrderCSRDTO workOrderCSRDTO,
           WorkorderStatus woStatus,
           WoType woType,
           WorkOrder workOrderPojo) throws BusinessException{
		
		log.debug("== Inicia la operación createContactCoreCSR/ContactCSRBusiness ==");
		
		try{
		
			Object[] params = null;
			params = new Object[2];			
			params[1] = "createContactCore";			
			params[0] = "contactDTO";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "WoId";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO.getWoCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			
			//se valida si ya existe un contact asociado al history del WO
			if( workOrderPojo != null ){
			
				//Se realiza la consulta de la WO para obtener los datos necesarios
				//para la creacion del Contact, puesto que todos los datos pueden no
				//estar cargados en la WO recibida por parametro.

				if( workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity()) 
				    || workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity())	
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity())){
							
					//Se consultan las shipping orders de la WO si la tiene
					String shippingOrders = this.getShippingOrderByWorkOrder( workOrderPojo.getId() );
					
					WorkOrderVO workOrderVO = coreWOBusiness.getWorkOrderByDealerIdAndWorkOrderId(workOrderPojo.getDealerId(), workOrderPojo.getId());
					contactDTO.setWorkorder(workOrderVO);
					
					Contact contactPojo = new Contact();	
					//gfandino 27-05-2011 Se modifica el setWoStatusHistoryId dado que cambió por la relación deon WoStatusHistoyy
					//contactPojo.setWoStatusHistoryId( contactDTO.getWoStatusHistoryId() );
					WoStatusHistory woStatusHistory = woStatusHistoryDAO.getWoStatusHistoryByID(contactDTO.getWoStatusHistoryId());
					contactPojo.setWoStatusHistoryId( woStatusHistory );

					contactPojo.setWoCode( workOrderVO.getWoCode() );
					contactPojo.setShippingOrderCode( shippingOrders.toString() );
					contactPojo.setCreationDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( contactDTO.getUserId(), userDAO) );
					contactPojo.setContactConfiguration( getContactConfiguration( workOrderVO ) );
					contactPojo.setProblemDescription( this.getContactProblemDescription( contactDTO,workOrderCSRDTO,workOrderVO.getWoDescription() ) );
					contactPojo.setCustomerCode( workOrderVO.getCustomer().getCustomerCode() );
					
					//Invoca la creacion del Contact en SDII
					contactDAO.createContact(contactPojo);
					
					//Invoca la creacion del Contact en IBS
					ContactVO contactVO = UtilsBusiness.copyObject(ContactVO.class, contactPojo);
					String contactCodeIbs = this.createContactCoreIBS(contactVO, workOrderVO.getCountry().getCountryCode());
					
					//Se actualiza el Contact de SDII con el contact code retornado por IBS.
					contactPojo.setContactCode( contactCodeIbs );
					contactDAO.updateContact(contactPojo);
					
					}
				}else{				
			
					//Se consultan las shipping orders de la WO si la tiene
					String shippingOrders = this.getShippingOrderByWorkOrder( workOrderCSRDTO.getShippingOrders() );
					
					Contact contactPojo = new Contact();	
					contactPojo.setWoStatusHistoryId( null );
					contactPojo.setWoCode( contactDTO.getWoCode() );
					contactPojo.setShippingOrderCode( shippingOrders.toString() );
					contactPojo.setCreationDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( contactDTO.getUserId(), userDAO) );
					contactPojo.setContactConfiguration( getContactConfiguration( woType,
																				  woStatus.getId(),
																				  woStatus.getWoStateCode(),
												                                  contactDTO.getCountryId() ) );
					String woDescription = "";
					
					contactPojo.setProblemDescription( this.getContactProblemDescription( contactDTO,workOrderCSRDTO,woDescription ) );
					contactPojo.setCustomerCode( contactDTO.getCustomerCode());
					
					//Invoca la creacion del Contact en IBS
					ContactVO contactVO = UtilsBusiness.copyObject(ContactVO.class, contactPojo);
					String contactCodeIbs = createContactCoreIBS(contactVO, contactDTO.getCountryCode());
					
				}
		} catch (Throwable ex) {
			
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación createContactCoreCSR/ContactCSRBusiness ==");
			throw super.manageException(ex);
			
		}finally{
			
			log.debug("== Termina la operación createContactCoreCSR/ContactCSRBusiness ==");
			
		}
	}
	
	/**
	 * Metodo: permite listar los codigos de las shipping orders de la WO
	 * @param woId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private String getShippingOrderByWorkOrder(Long woId) throws BusinessException{
		
		log.debug("== Inicia la operacion getShippingOrderByWorkOrder/ContactCSRBusiness ==");
		
		try{
		
			List<ShippingOrder> listShippingOrderPojo;
			listShippingOrderPojo = shippingDAO.getShippingOrdersByWorkOrder(woId);			
			
			StringBuffer shippingOrders = new StringBuffer();
			
			for (ShippingOrder shippingOrder : listShippingOrderPojo) {
			
				shippingOrders.append(shippingOrder.getShippingOrderCode());
				shippingOrders.append("-");
				
			}			
			
			return shippingOrders.toString();
			
		} catch(Throwable ex){
			
			log.error("== Error en la operacion getShippingOrderByWorkOrder/ContactCSRBusiness ==");
			throw this.manageException(ex);
			
		} finally {
			
			log.debug("== Termina la operacion getShippingOrderByWorkOrder/ContactCSRBusiness ==");
			
		}	
	}
	
	/**
	 * Metodo: permite listar los codigos de las shipping orders de la WO
	 * @param listShippingOrderPojo
	 * @return
	 * @throws BusinessException
	 */
	private String getShippingOrderByWorkOrder(List<Long> listShippingOrderPojo) throws BusinessException{
		
		log.debug("== Inicia la operacion getShippingOrderByWorkOrder/ContactCSRBusiness ==");
		
		try{
		
			StringBuffer shippingOrders = new StringBuffer();
			for (Long shippingOrder : listShippingOrderPojo) {
			
				shippingOrders.append(shippingOrder);
				shippingOrders.append("-");
				
			}			
			
			return shippingOrders.toString();
		} catch(Throwable ex){
			
			log.error("== Error en la operacion getShippingOrderByWorkOrder/ContactCSRBusiness ==");
			throw this.manageException(ex);
			
		} finally {
			
			log.debug("== Termina la operacion getShippingOrderByWorkOrder/ContactCSRBusiness ==");
			
		}	
	}
	
	/**
	 * Metodo: Permite consultar la configuracion del Contact
	 * @param woTypePojo
	 * @param woStatus
	 * @param woStateCode
	 * @param country
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private ContactConfiguration getContactConfiguration(WoType woTypePojo,
	                               Long woStatus,
	                               String woStateCode,
	                               Long country) throws BusinessException{
		
		log.debug("== Inicia la operacion getContactConfiguration/ContactCSRBusiness ==");
		
		try{
		
			Long woType =woTypePojo.getId();
			ContactConfiguration config = contactConfigDAO.getContactConfigurationByRule(woType, woStatus, country);	
			
			if( config == null ){
			
				Object[] params = null;
				params = new Object[2];				
				params[0] = woTypePojo.getWoTypeCode();				
				params[1] = woStateCode;
				throw new BusinessException(ErrorBusinessMessages.CORE_CR029.getCode(),ErrorBusinessMessages.CORE_CR029.getMessage(params),UtilsBusiness.getParametersWS(params));
				
			}
			
			return config;
			
		} catch(Throwable ex){
			
			log.error("== Error en la operacion getContactConfiguration/ContactCSRBusiness ==");
			throw this.manageException(ex);
			
		} finally {
			
			log.debug("== Termina la operacion getContactConfiguration/ContactCSRBusiness ==");
			
		}	
	}
	
	/**
	 * Metodo: Construye el problem description del Contact
	 * @param contactDTO
	 * @param workOrderCSRDTO
	 * @param woDescription
	 * @return
	 * @throws BusinessException
	 */
	private String getContactProblemDescription(ContactDTO contactDTO,WorkOrderCSRDTO workOrderCSRDTO,String woDescription) throws BusinessException{
		
		log.debug("== Inicia la operacion getContactProblemDescription/ContactCSRBusiness ==");
		
		try{
		
			//Se validan los parametros requeridos
			Object[] params = null;
			params = new Object[2];			
			params[1] = "getContactProblemDescription";	
			params[0] = "WoReasonId";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO.getWoReasonId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));			
			params[0] = "UserId";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO.getUserId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			
			//Se consulta el usuario
			User user = userDAO.getUserById( contactDTO.getUserId() );
			
			StringBuffer problem = new 	StringBuffer();
			String separatorCode = ":";
			String separator = ",";
			String separatorSpace = " ";	
			
			//se asigna los parametros realcionados a todos los estados, que es
			//el encabezado de la descripcion del problema.
			problem.append( contactDTO.getWoCode() );
			problem.append( separatorCode );
			
			//Adicion del usuario que genera el Contact
			problem.append( user.getLogin() );
			problem.append( separator );
			
			problem.append( UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_APPLICATION_NAME.getCodeEntity(),contactDTO.getCountryId(),systemParameterDAO) );
			problem.append( separator );
			
			//Si se encuentra en estado Agendada, se construye con el siguiente formato
			if(workOrderCSRDTO.isReSchedule()){
				
				problem.append( this.getDescriptionReScheduler(workOrderCSRDTO.getScheduleDate(),
						   workOrderCSRDTO.getContactPerson(),
						   woDescription,
						   separator, 
                      separatorSpace, 
                      contactDTO.getWoReasonId(), 
                      contactDTO) );
				
			}else{
				
				problem.append( this.getDescriptionScheduler(workOrderCSRDTO.getScheduleDate(),
						 workOrderCSRDTO.getContactPerson(),
						 woDescription,
                      separator, 
                      contactDTO,
                      workOrderCSRDTO.getCommentManagment()) );
				
			}		
			
			return problem.toString();
			
		} catch(Throwable ex){
			
			log.error("== Error en la operacion getContactProblemDescription/ContactCSRBusiness ==");
			throw this.manageException(ex);
			
		} finally {
			
			log.debug("== Termina la operacion getContactProblemDescription/ContactCSRBusiness ==");
			
		}	
	}
	
	/**
	 * Metodo: Se encarga de invocar la operacion del servicio
	 *         de creacion de contacts de IBS
	 * @param contactVO
	 * @param countryCode
	 * @return
	 * @throws ServiceLocatorException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private String createContactCoreIBS (ContactVO contactVO,String countryCode) throws ServiceLocatorException, BusinessException{
	
		log.debug("== Inicia la operacion createContactCoreIBS/ContactCSRBusiness ==");
		ContactCoreBrokerImpl broker;
		broker = ContactCoreBrokerImpl.getInstance();		
		String customerContact = broker.createContactCoreIBS( contactVO,countryCode );		
		log.debug("== Termina la operacion createContactCoreIBS/ContactCSRBusiness ==");
		return customerContact;
		
	}
		
	/**
	 * Metodo: Vincula el tipo de WO de SDII resultado
	 *         del tipo de servicio que proviene en la WO de IBS,
	 *         se relaciona el tipo de servicio de la categoria de
	 *         la WO de IBS con el tipo de WO de SDII.
	 * @param services
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	private WoType linkTypeServiceOrder(List<String> services) throws BusinessException, PropertiesException, DAOServiceException, DAOSQLException{
		
		String woTypeservice = null;
		
		//Se itera la lista de servicios para obtener uno de los codigos, puesto que el 
		//servicio no retorna el tipo de wo, se debe obtener uno de los codigos, se hace la
		//presuncion que todos los servicios son del mismo tipo ya que el contrato no cumple
		//con la necesidad, cambio aprobado por DTV, Responsable Milton Jimenez
		Service service = null;
		if(services != null ){
		
			if( !services.isEmpty() ){
			
				service = serviceDao.getServiceBySimpleCode( services.get(0) );
				woTypeservice = service.getServiceCategory().getServiceType().getServiceTypeCode();
				
			}
		}	
		
		//Se carga el tipo de WO del servicio asociado, de lo contrario se crea la WO como SERVICE por defecto
		if( woTypeservice == null ){
			
			woTypeservice = CodesBusinessEntityEnum.WORKORDER_TYPE_SERVICE.getCodeEntity();
			
		}
		
		return woTypeDao.getWoTypeByCode(woTypeservice);		
	
	}
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * @param workOrder
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private ContactConfiguration getContactConfiguration(WorkOrderVO workOrder) throws BusinessException{
		
		log.debug("== Inicia la operacion getContactConfiguration/ContactCSRBusiness ==");
		
		try{
		
			Long woType = workOrder.getWoTypeByWoTypeId().getId();
			Long woStatus = workOrder.getWorkorderStatusByActualStatusId().getId();
			Long country = workOrder.getCountry().getId();
			ContactConfiguration config = contactConfigDAO.getContactConfigurationByRule(woType, woStatus, country);	
			if( config == null ){
			
				Object[] params = null;
				params = new Object[2];				
				params[0] = workOrder.getWoTypeByWoTypeId().getWoTypeCode();				
				params[1] = workOrder.getWorkorderStatusByActualStatusId().getWoStateCode();
				throw new BusinessException(ErrorBusinessMessages.CORE_CR029.getCode(),ErrorBusinessMessages.CORE_CR029.getMessage(params),UtilsBusiness.getParametersWS(params));
				
			}
			
			return config;
			
		} catch(Throwable ex){
			
			log.error("== Error en la operacion getContactConfiguration/ContactCSRBusiness ==");
			throw this.manageException(ex);
			
		} finally {
			
			log.debug("== Termina la operacion getContactConfiguration/ContactCSRBusiness ==");
			
		}
		
	}

	/**
	 * Metodo: Retorna la descripcion del Contact para un estado de Agendado
	 * @param agendationDate
	 * @param contactPerson
	 * @param woDescription
	 * @param separator
	 * @param contactDTO
	 * @param commentManagment
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	private String getDescriptionScheduler(Date agendationDate,
			           String contactPerson,
			           String woDescription,
	                 String separator, 
	                 ContactDTO contactDTO,
	                 String commentManagment) throws BusinessException, DAOServiceException, DAOSQLException{
	
		StringBuffer problem = new StringBuffer();
		//Se validan los parametros requeridos
		
		//Adicion de la Persona de Contacto
		problem.append( contactPerson );
		problem.append( separator );
		//Adicion de la descripcion
		if(woDescription!=null && !woDescription.equals(""))
			problem.append( woDescription );
		else
			problem.append( ApplicationTextEnum.SCHEDULED_BY_CSR.getApplicationTextValue() );
		problem.append( separator );
		//Adicion de la Fecha de Agendamiento o Reagendamiento
		problem.append( UtilsBusiness.dateToString(agendationDate, UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS) );
		//Adición del mensaje de agendamiento ingresado por el usuario
		problem.append( separator );
		problem.append( " - " );
		problem.append( commentManagment );
		return problem.toString();
		
	}
	
	/**
	 * Metodo: Retorna la descripcion del Contact para un estado de Reagendado
	 * @param agendationDate
	 * @param contactPerson
	 * @param woDescription
	 * @param separator
	 * @param separatorSpace
	 * @param reasonId
	 * @param contactDTO
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	private String getDescriptionReScheduler(Date agendationDate,
							 String contactPerson,
							 String woDescription,
							 String separator, 
							 String separatorSpace, 
							 Long reasonId, 
							 ContactDTO contactDTO) throws BusinessException, DAOServiceException, DAOSQLException{
	
		StringBuffer problem = new StringBuffer();
		
		//Se consulta la WO_REASON y se verifica que exista
		WorkorderReason woReason = woReasonDAO.getWorkorderReasonByID( reasonId );
		
		if( woReason != null ){
			
			WoStatusHistory woStatusHistory = woStatusHistoryDAO.getWoStatusHistoryByID(contactDTO.getWoStatusHistoryId());
			
			String description="";
			if(woStatusHistory!=null)
				description=woStatusHistory.getDescription();
			//Adicion de la Persona de Contacto
			problem.append( contactPerson );
			problem.append( separator );
			//Adicion de la WO Reason del motivo de la Dificultad o Rechazo
			problem.append( woReason.getWorkorderReasonCode() );
			problem.append( separatorSpace );
			problem.append( woReason.getWorkorderReasonName() );
			problem.append( separator );
			//Adicion de la descripcion
			if(woDescription==null || woDescription.equals(""))
				problem.append( ApplicationTextEnum.RE_SCHEDULED_BYE_CSR.getApplicationTextValue() );
			else
				problem.append( woDescription );
			problem.append( separator );
			//Adicion de la Fecha de Agendamiento o Reagendamiento
			problem.append( UtilsBusiness.dateToString(agendationDate, UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS) );
			//Adición del mensaje de agendamiento ingresado por el usuario
			problem.append( separator );
			problem.append( " - " );
			problem.append( description );
			
		}
		
		return problem.toString();
	}
	
}
