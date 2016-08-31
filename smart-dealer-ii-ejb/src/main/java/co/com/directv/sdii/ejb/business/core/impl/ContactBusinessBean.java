package co.com.directv.sdii.ejb.business.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import co.com.directv.sdii.ejb.business.broker.CustomerInterfaceManagmentBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.impl.ContactCoreBrokerImpl;
import co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.AddCustomerInquiryIBSDTO;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.pojo.Category;
import co.com.directv.sdii.model.pojo.Contact;
import co.com.directv.sdii.model.pojo.ContactConfiguration;
import co.com.directv.sdii.model.pojo.ContactStatus;
import co.com.directv.sdii.model.pojo.InputMethod;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.ShippingOrderElement;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.vo.ContactVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.core.CategoryDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ContactConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ContactDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ContactStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.core.InputMethodDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD Contact
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see persistence.dao.core.ContactDAOLocal
 * @see co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal
 */
@Stateless(name="ContactBusinessBeanLocal",mappedName="ejb/ContactBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactBusinessBean extends BusinessBase implements ContactBusinessBeanLocal {

    @EJB(name="ContactDAOLocal", beanInterface=ContactDAOLocal.class)
    private ContactDAOLocal contactDAO;
    
    @EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
    private WorkOrderDAOLocal woDAO;
    
    @EJB(name="WoStatusHistoryDAOLocal", beanInterface=WoStatusHistoryDAOLocal.class)
    private WoStatusHistoryDAOLocal woStatusHistoryDAO;
	
	@EJB(name="ContactConfigurationDAOLocal", beanInterface=ContactConfigurationDAOLocal.class)
	private ContactConfigurationDAOLocal contactConfigDAO;
	
	@EJB(name="WorkOrderAgendaDAOLocal", beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAO;
	
	@EJB(name="CoreWOBusinessLocal", beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal coreWOBusiness;	
    
	@EJB(name="ShippingOrderDAOLocal", beanInterface=ShippingOrderDAOLocal.class)
	private ShippingOrderDAOLocal shippingDAO;
	
	@EJB(name="ShippingOrderElementDAOLocal", beanInterface=ShippingOrderElementDAOLocal.class)
	private ShippingOrderElementDAOLocal shippingElementDAO;
	
	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal serializedDAO;
	
	@EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
	private NotSerializedDAOLocal notSerDAO;
	
	@EJB(name="WorkorderReasonDAOLocal", beanInterface=WorkorderReasonDAOLocal.class)
	private WorkorderReasonDAOLocal woReasonDAO;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDAO;
	
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
    
    @EJB(name="CategoryDAOLocal", beanInterface=CategoryDAOLocal.class)
    private CategoryDAOLocal categoryDAO;
    
    @EJB(name="InputMethodDAOLocal", beanInterface=InputMethodDAOLocal.class)
    private InputMethodDAOLocal inputMethodDAO;
    
    @EJB(name="ContactStatusDAOLocal", beanInterface=ContactStatusDAOLocal.class)
    private ContactStatusDAOLocal contactStatusDAO;
    
	@EJB(name="CustomerInterfaceManagmentBrokerLocal",beanInterface=CustomerInterfaceManagmentBrokerLocal.class)
	private CustomerInterfaceManagmentBrokerLocal customerInterfaceManagmentBroker;
    
    private final static Logger log = UtilsBusiness.getLog4J(ContactBusinessBean.class);

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal#getContactsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ContactVO getContactByID(Long id) throws BusinessException {
        log.debug("== Inicia la operacion getContactByID/ContactBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Contact objPojo = contactDAO.getContactByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ContactVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error en la operacion getContactByID/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getContactByID/ContactBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal#createContact(co.com.directv.sdii.model.vo.ContactVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createContact(ContactVO obj) throws BusinessException {
        log.debug("== Inicia la operacion createContact/ContactBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            Contact objPojo =  UtilsBusiness.copyObject(Contact.class, obj);
            contactDAO.createContact(objPojo);
        } catch(Throwable ex){
        	log.error("== Error en la operaciion createContact/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion createContact/ContactBusinessBean ==");
        }		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal#createTryContact(co.com.directv.sdii.model.vo.ContactVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createTryContact(ContactDTO contactDTO) throws BusinessException {
        log.debug("== Inicia la operacion createTryContact/ContactBusinessBean ==");
        UtilsBusiness.assertNotNull(contactDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(contactDTO.getWoCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(contactDTO.getCustomerCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(contactDTO.getUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(contactDTO.getCategoryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	//validar que la WO esté en N-Pendiente.
        	/*WorkOrder wo = woDAO.getWorkOrderByCode(contactDTO.getWoCode());
        	String woStatusCode = wo.getWorkorderStatusByActualStatusId().getWoStateCode();
        	String pendingStatusCode = CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity();
        	if(!woStatusCode.equals(pendingStatusCode)){
        		log.error("La WO no se encuentra en estado N-Pendiente.");
        		throw new BusinessException(ErrorBusinessMessages.CORE_NOT_EXIST_CONFIG_CONTACT.getCode(), ErrorBusinessMessages.CORE_NOT_EXIST_CONFIG_CONTACT.getMessage());
        	}*/
        	
        	Date creationDate = UtilsBusiness.getCurrentTimeZoneDateByUserId(contactDTO.getUserId(), userDAO);
        	User user = userDAO.getUserById(contactDTO.getUserId());
        	
        	InputMethod inputMethodAutomatic = inputMethodDAO.getInputMethodByCode(CodesBusinessEntityEnum.INPUT_METHOD_AUTOMATIC.getCodeEntity());
        	ContactStatus contactStatusComplete = contactStatusDAO.getContactStatusByCode(CodesBusinessEntityEnum.CONTACT_STATUS_COMPLETE.getCodeEntity());
        	Category category = categoryDAO.getCategoryByID(contactDTO.getCategoryId());
        	
        	AddCustomerInquiryIBSDTO addCustomerInquiryIBSDTO = new AddCustomerInquiryIBSDTO(user.getCountry(), 
						        															CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity(), 
						        															contactDTO.getCustomerCode(), 
						        															creationDate, 
						        															contactDTO.getDescription(), 
						        															contactStatusComplete.getIbsCode(),
						        															user.getLogin(), 
						        															category.getIbsCode(), 
						        															inputMethodAutomatic.getIbsCode());
        	//informa a IBS el nuevo contacto.
        	customerInterfaceManagmentBroker.addCustomerInquiry(addCustomerInquiryIBSDTO);
        	
        	
        	Contact contact =  new Contact();
        	contact.setWoCode(contactDTO.getWoCode());
        	contact.setCustomerCode(contactDTO.getCustomerCode());
        	contact.setProblemDescription(contactDTO.getDescription());
        	contact.setCreationDate( creationDate );
        	contact.setCategory(category);
        	
            contactDAO.createContact(contact);
        } catch(Throwable ex){
        	log.error("== Error en la operaciion createTryContact/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion createTryContact/ContactBusinessBean ==");
        }		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal#updateContact(co.com.directv.sdii.model.vo.ContactVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateContact(ContactVO obj) throws BusinessException {
        log.debug("== Inicia la operacion updateContact/ContactBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            Contact objPojo =  UtilsBusiness.copyObject(Contact.class, obj);
            contactDAO.updateContact(objPojo);
        } catch(Throwable ex){
        	log.error("== Error en la operacion updateContact/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion updateContact/ContactBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal#deleteContact(co.com.directv.sdii.model.vo.ContactVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteContact(ContactVO obj) throws BusinessException {
        log.debug("== Inicia la operacion deleteContact/ContactBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Contact objPojo =  UtilsBusiness.copyObject(Contact.class, obj);
            contactDAO.deleteContact(objPojo);
        } catch(Throwable ex){
        	log.error("== Error en la operacion deleteContact/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion deleteContact/ContactBusinessBean ==");
        }
	}

	@Override
	public List<ContactVO> getContactsWorkOrderByDealer(ContactDTO contactDTO) throws BusinessException {
		log.debug("== Inicia la operacion getContactsWorkOrderByDealer/ContactBusinessBean ==");
		UtilsBusiness.assertNotNull(contactDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());       
        UtilsBusiness.assertNotNull(contactDTO.getWoCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<Contact> listContactsPojo = new ArrayList<Contact>();
        	List<ContactVO> listContactsVo = new ArrayList<ContactVO>();
        	
        	//Se verifica si el id de la work order enviado en el consumo del servicio es null
        	//Si lo es se consulta por el codigo de la WO que es requerido
        	WorkOrder wo = new WorkOrder();
        	if( contactDTO.getWoId() == null || contactDTO.getWoId().longValue() <= 0 ){
	        	//Se consulta el id de la WO
	        	wo = woDAO.getWorkOrderByCode(contactDTO.getWoCode());
        	}else{
        		wo.setId(contactDTO.getWoId());
        	}
        	
        	//Si el dealer es null se retornan todos los Contacts ya que es un usuario DTV
        	if( contactDTO.getDealerId() == null || contactDTO.getDealerId().longValue() <= 0 ){
        		log.debug("== La WO "+wo.getWoCode()+" se encuentra en estado: "+wo.getWorkorderStatusByActualStatusId().getWoStateName()+" Se retornan todos los Contacts para el usuario sin Dealer. ==");
        		listContactsPojo = contactDAO.getContactsByWorkOrder(contactDTO.getWoCode());
        	}else{
	        	//Se verifica si el estado de la WO es pendiente (Rechazo-Dificultad), ya que si 
	        	//se encuentra en este estado se deben retornar todos los contacts, no solo los del
	        	//dealer.
	        	if( wo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity() ) 
	        		|| wo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase( CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity() )	){
	        		log.debug("== La WO "+wo.getWoCode()+" se encuentra en estado: "+wo.getWorkorderStatusByActualStatusId().getWoStateName()+" Se retornan todos los Contacts. ==");
	        		listContactsPojo = contactDAO.getContactsByWorkOrder(contactDTO.getWoCode());
	        	}else{       		        	
		            //Se consultan los cambios de estado de la WO realizados por el Dealer,
		        	//para consultar los Contacts generados por el Dealer.
	        		log.debug("== La WO "+wo.getWoCode()+" se encuentra en estado: "+wo.getWorkorderStatusByActualStatusId().getWoStateName()+" Se retornan los Contacts creados por el Dealer. ==");
		        	List<WoStatusHistory> listWoStatusH = woStatusHistoryDAO.getWoStatusHistoryByDealer(wo.getId(), contactDTO.getDealerId());
		        	for (WoStatusHistory woStatusHistory : listWoStatusH) {
						Contact contact = contactDAO.getContactsByWoStatusHistory( woStatusHistory.getId() );
						if(contact != null)
							listContactsPojo.add(contact);					
					}
	        	}
        	}
        	
        	if(!listContactsPojo.isEmpty()){
	        	listContactsVo = UtilsBusiness.convertList(listContactsPojo, ContactVO.class);	        	
        	}
        	
        	log.debug("== Se retornan "+listContactsVo.size()+" Contacts para la WO: "+wo.getWoCode()+" ==");
        	
        	return listContactsVo;
        } catch(Throwable ex){
        	log.error("== Error en la operacion getContactsWorkOrderByDealer/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getContactsWorkOrderByDealer/ContactBusinessBean ==");
        }		
	}

	@Override
	public List<ContactVO> getContactsTriesByWorkOrder(String woCode) throws BusinessException{
		log.debug("== Inicia la operacion getContactsTriesByWorkOrder/ContactBusinessBean ==");
		UtilsBusiness.assertNotNull(woCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<ContactVO> listContactsVo = null;
        	List<Contact> contactTries = contactDAO.getContactsTriesByWorkOrder(woCode);
        	
        	if(!contactTries.isEmpty()){
	        	listContactsVo = UtilsBusiness.convertList(contactTries, ContactVO.class);	        	
        	}
        	
        	return listContactsVo;
        } catch(Throwable ex){
        	log.error("== Error en la operacion getContactsTriesByWorkOrder/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getContactsTriesByWorkOrder/ContactBusinessBean ==");
        }
	}
	
	@Override
	public void createContactCore(ContactDTO contactDTO) throws BusinessException{
		log.debug("== Inicia la operación createContactCore/CoreWorkOrderServiceBrokerImpl ==");
		try{
			Object[] params = null;
			params = new Object[2];			
			params[1] = "createContactCore";			
			params[0] = "contactDTO";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "WoId";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO.getWoId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "WoStatusHistoryId";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO.getWoStatusHistoryId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			
			
			//se valida si ya existe un contact asociado al history del WO
			if (!validateContactExistence( contactDTO.getWoStatusHistoryId() )){
				//Se realiza la consulta de la WO para obtener los datos necesarios
				//para la creacion del Contact, puesto que todos los datos pueden no
				//estar cargados en la WO recibida por parametro.
				WorkOrder workOrderPojo;			
				workOrderPojo = woDAO.getWorkOrderByID( contactDTO.getWoId() );					
				
				if( workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity())	
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity()) 
					|| workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity())
					){
									
					//imprime el log de creacion del contact
					log.info(logCreateContact(workOrderPojo));
				
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
					contactPojo.setProblemDescription( this.getContactProblemDescription( contactDTO ) );
					contactPojo.setCustomerCode( workOrderVO.getCustomer().getCustomerCode() );
					
					//Invoca la creacion del Contact en SDII
					contactDAO.createContact(contactPojo);
					
					//Invoca la creacion del Contact en IBS
					ContactVO contactVO = UtilsBusiness.copyObject(ContactVO.class, contactPojo);
					String contactCodeIbs = this.createContactCoreIBS(contactVO, workOrderVO.getCountry().getCountryCode());
					
					//Se actualiza el Contact de SDII con el contact code retornado por IBS.
					contactPojo.setContactCode( contactCodeIbs );
					contactDAO.updateContact(contactPojo);
				}else{				
					log.info(logNotCreateContact(workOrderPojo));
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación createContactCore/CoreWorkOrderServiceBrokerImpl ==");
			throw super.manageException(ex);
		}finally{
			log.debug("== Termina la operación createContactCore/CoreWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Valida si ya existe un contact asociado al history
	 * de la WO, es decir que ese cambio de estado ya fue
	 * procesado y se creo un contact.
	 * @param woStatusHistory
	 * @return boolean, false si no existe
	 * @author jalopez
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private boolean validateContactExistence(Long woStatusHistory) throws DAOServiceException, DAOSQLException{
		boolean res = false;
		Contact contact = contactDAO.getContactsByWoStatusHistory( woStatusHistory );
		if( contact != null ){
			log.info(" Ya fue creado un Contact asociado al WO_STATUS_HISTORY, Contact existente: "+contact.getContactCode());
			res = true;
		}
		return res;
	}
	/**
	 * 	 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna un mensaje de log
	 * @param workOrderPojo
	 * @return String log
	 * @author jalopez
	 */
	private String logCreateContact(WorkOrder workOrderPojo){
		StringBuffer logError = new StringBuffer();
		logError.append( "== Inicia proceso creacion Contact WO: " );
		logError.append(workOrderPojo.getWoCode());
		logError.append(" cambio estado: " );
		logError.append(workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateName());
		logError.append(" ==" );
		return logError.toString();
	}
	
	/**
	 * 	 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna un mensaje de log
	 * @param workOrderPojo
	 * @return String log
	 * @author jalopez
	 */
	private String logNotCreateContact(WorkOrder workOrderPojo){
		StringBuffer logError = new StringBuffer();
		logError.append( "== No se Crea Contact WO: " );
		logError.append(workOrderPojo.getWoCode());
		logError.append(" cambio estado: " );
		logError.append(workOrderPojo.getWorkorderStatusByActualStatusId().getWoStateName());
		logError.append(" ==" );
		return logError.toString();
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Se encarga de invocar la operacion del servicio
	 * de creacion de contacts de IBS
	 * @param contactVO ContactVO - datos del Contact
	 * @param countryCode String - Codigo ISO2 del pais
	 * @return String con el codigo del contact retornado por el servicio
	 * @throws ServiceLocatorException
	 * @throws BusinessException
	 * @author jalopez
	 */
	private String createContactCoreIBS (ContactVO contactVO,String countryCode) throws ServiceLocatorException, BusinessException{
		log.debug("== Inicia la operacion createContactCoreIBS/ContactBusinessBean ==");
		ContactCoreBrokerImpl broker;
		broker = ContactCoreBrokerImpl.getInstance();		
		String customerContact = broker.createContactCoreIBS( contactVO,countryCode );		
		log.debug("== Termina la operacion createContactCoreIBS/ContactBusinessBean ==");
		return customerContact;
	}
	
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Construye el problem description del Contact
	 * @param workOrder
	 * @return String, retorna el problem description
	 * @throws BusinessException
	 * @author jalopez
	 */
	private String getContactProblemDescription(ContactDTO contactDTO) throws BusinessException{
		log.debug("== Inicia la operacion getContactProblemDescription/ContactBusinessBean ==");
		try{
			//Se validan los parametros requeridos
			Object[] params = null;
			params = new Object[2];			
			params[1] = "getContactProblemDescription";	
			params[0] = "Workorder";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO.getWorkorder(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "WoReasonId";
			UtilsBusiness.validateRequestResponseWebService(params, contactDTO.getWoReasonId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));			
			params[0] = "UserId";
            UtilsBusiness.validateRequestResponseWebService(params, contactDTO.getUserId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));

			//Se consulta el usuario
			User user = userDAO.getUserById( contactDTO.getUserId() );
			
			WorkOrderVO workOrder = contactDTO.getWorkorder();
			StringBuffer problem = new 	StringBuffer();
			String separatorCode = ":";
			String separator = ",";
			String separatorSpace = " ";	
			
			//se asigna los parametros realcionados a todos los estados, que es
			//el encabezado de la descripcion del problema.
			problem.append( workOrder.getWoCode() );
			problem.append( separatorCode );
			
			//Adicion del usuario que genera el Contact
			problem.append( user.getLogin() );
			problem.append( separator );
			
			//Adcion del nombre de la palicacion
			problem.append( UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_APPLICATION_NAME.getCodeEntity(), 
					                                         contactDTO.getWorkorder().getCountry().getId(), 
					                                         systemParameterDAO) );
			//problem.append( CodesBusinessEntityEnum.APPLICATION_NAME.getCodeEntity() );
			problem.append( separator );
			
			//Si se encuentra en estado Agendada, se construye con el siguiente formato
			//Código WorkOrder+:+userName+applicationName+Persona Contacto+Comentarios+Fecha Agendamiento
            if( workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity()) ){
				
				problem.append( this.getDescriptionScheduler(workOrder, separator, contactDTO) );
			}
			//Si se encuentra en estado ReAgendada, se construye con el siguiente formato
			//Código WorkOrder+:+Persona Contacto+Razon(Código WO Reason + Descripción WO Reason)+Comentarios+Fecha ReAgendamiento
			if( workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity())	){
				
				problem.append( this.getDescriptionReScheduler(workOrder, separator, separatorSpace, contactDTO.getWoReasonId(), contactDTO) );
			//Si se encuentra en estado Pendiente(Dificultad,Rechazado), se construye con el siguiente formato
			//Código WorkOrder+:+userName+applicationName+Nombre Cliente+Razon(Código WO Reason + Descripción WO Reason)+Comentarios
			}else if(workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()) 
					|| workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity())){
				
				problem.append( this.getDescriptionPending(workOrder, contactDTO.getWoReasonId(), separatorSpace, separatorSpace, contactDTO) );
			//Si se encuentra en estado Atencion, se construye con el siguiente formato
			//Código WorkOrder+:+userName+applicationName+Tecnico Asignado+Numero Servicio(IRD+SmartCard asociado al servicio)+Comentarios
			}else if( workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity()) ){
				
				problem.append( this.getDescriptionAttention(workOrder, separatorSpace, contactDTO) );
			//Si se encuentra en estado Finalizado, se construye con el siguiente formato
			//Código WorkOrder+:+userName+applicationName+Elementos No Serializados+Comentarios
			}else if( workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity()) ){
				
				problem.append( this.getDescriptionCompleted(workOrder, separatorSpace, contactDTO) );
			//Si se encuentra en estado Cancelado, se construye con el siguiente formato
			//Código WorkOrder+:+userName+applicationName+Elementos No Serializados+Comentarios
			}else if( workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity()) ){
				
				problem.append( this.getDescriptionCanceled(workOrder, contactDTO.getWoReasonId(), separatorSpace, separatorSpace, contactDTO) );
			}
			
			return problem.toString();
		 } catch(Throwable ex){
        	log.error("== Error en la operacion getContactProblemDescription/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getContactProblemDescription/ContactBusinessBean ==");
        }	
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna la descripcion del Contact para un estado de
	 * Agendado
	 * @param workOrder WorkOrder
	 * @param separator String
	 * @param contactDTO ContactDTO
	 * @return String
	 * @throws BusinessException
	 * @author jalopez
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private String getDescriptionScheduler(WorkOrderVO workOrder,String separator, ContactDTO contactDTO) throws BusinessException, DAOServiceException, DAOSQLException{
		
		StringBuffer problem = new StringBuffer();
		//Se validan los parametros requeridos
		UtilsBusiness.assertNotNull(workOrder.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		WorkOrderAgenda woAgenda = this.getWorkOrderAgenda(workOrder.getId());
		WoStatusHistory woStatusHistory = woStatusHistoryDAO.getWoStatusHistoryByID(contactDTO.getWoStatusHistoryId());
		
		//Adicion de la Persona de Contacto
		problem.append( woAgenda.getContactPerson() );
		problem.append( separator );
		//Adicion de la descripcion
		problem.append( workOrder.getWoDescription() );
		problem.append( separator );
		//Adicion de la Fecha de Agendamiento o Reagendamiento
		problem.append( UtilsBusiness.dateToString(woAgenda.getAgendationDate(), UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS) );
		//Adición del mensaje de agendamiento ingresado por el usuario
		problem.append( separator );
		problem.append( " - " );
		problem.append( woStatusHistory.getDescription() );
		return problem.toString();
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna la descripcion del Contact para un estado de
	 * Reagendado
	 * @param workOrder WorkOrderVO
	 * @param separator String
	 * @param separatorSpace String
	 * @param reasonId Long
	 * @param contactDTO ContactDTO
	 * @return String
	 * @throws BusinessException
	 * @author jalopez
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private String getDescriptionReScheduler(WorkOrderVO workOrder, String separator, String separatorSpace, Long reasonId, ContactDTO contactDTO) throws BusinessException, DAOServiceException, DAOSQLException{
		
		StringBuffer problem = new StringBuffer();
		//Se validan los parametros requeridos
		UtilsBusiness.assertNotNull(workOrder.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		//Se consulta la WO_REASON y se verifica que exista
		WorkorderReason woReason = woReasonDAO.getWorkorderReasonByID( reasonId );
		
		if( woReason != null ){
			WorkOrderAgenda woAgenda = this.getWorkOrderAgenda(workOrder.getId());
			WoStatusHistory woStatusHistory = woStatusHistoryDAO.getWoStatusHistoryByID(contactDTO.getWoStatusHistoryId());
			
			//Adicion de la Persona de Contacto
			problem.append( woAgenda.getContactPerson() );
			problem.append( separator );
			//Adicion de la WO Reason del motivo de la Dificultad o Rechazo
			problem.append( woReason.getWorkorderReasonCode() );
			problem.append( separatorSpace );
			problem.append( woReason.getWorkorderReasonName() );
			problem.append( separator );
			//Adicion de la descripcion
			problem.append( workOrder.getWoDescription() );
			problem.append( separator );
			//Adicion de la Fecha de Agendamiento o Reagendamiento
			problem.append( UtilsBusiness.dateToString(woAgenda.getAgendationDate(), UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS) );
			//Adición del mensaje de agendamiento ingresado por el usuario
			problem.append( separator );
			problem.append( " - " );
			problem.append( woStatusHistory.getDescription() );
		}else{
			log.error(" No se puede construir el Problem Description porque no existe la WO_REASON con id:  "+reasonId);
		}
		
		return problem.toString();
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * @param workOrder WorkOrderVO 
	 * @param reasonId Long
	 * @param separatorSpace String
	 * @param separator String
	 * @param contactDTO ContactDTO 
	 * @return String
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author jalopez
	 */
	private String getDescriptionPending(WorkOrderVO workOrder,Long reasonId,String separatorSpace, String separator, ContactDTO contactDTO) throws DAOServiceException, DAOSQLException, BusinessException{
		
		StringBuffer problem = new StringBuffer();
		//Se consulta la WO_REASON y se verifica que exista
		WorkorderReason woReason = woReasonDAO.getWorkorderReasonByID( reasonId );
		WoStatusHistory woStatusHistory = woStatusHistoryDAO.getWoStatusHistoryByID(contactDTO.getWoStatusHistoryId());		
		
		if( woReason != null && woStatusHistory != null){
			//Se validan los parametros requeridos
			UtilsBusiness.assertNotNull( woReason.getWorkorderReasonCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull( woReason.getWorkorderReasonName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			//Adicion de el nombre del Cliente
			problem.append( workOrder.getCustomer().getFirstName() );
			problem.append( separatorSpace );
			problem.append( workOrder.getCustomer().getLastName());
			problem.append( separator );
			//Adicion de la WO Reason del motivo de la Dificultad o Rechazo
			problem.append( woReason.getWorkorderReasonCode() );
			problem.append( separatorSpace );
			problem.append( woReason.getWorkorderReasonName() );
			problem.append( separator );
			//Adicion de la descripcion
			problem.append( workOrder.getWoDescription() );
			problem.append( separator );
			problem.append( " - " );
			//Adicion de la descripcion
			problem.append( woStatusHistory.getDescription() );
		}else{
			log.error(" No se puede construir el Problem Description porque no existe la WO_REASON con id:  "+reasonId);
		}
		
		return problem.toString();
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * @param workOrder WorkOrderVO 
	 * @param reasonId Long
	 * @param separatorSpace String
	 * @param separator String
	 * @param contactDTO ContactDTO
	 * @return String
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author jnova
	 */
	private String getDescriptionCanceled(WorkOrderVO workOrder,Long reasonId,String separatorSpace, String separator, ContactDTO contactDTO) throws DAOServiceException, DAOSQLException, BusinessException{
		
		StringBuffer problem = new StringBuffer();
		//Se consulta la WO_REASON y se verifica que exista
		WorkorderReason woReason = woReasonDAO.getWorkorderReasonByID( reasonId );
		WoStatusHistory woStatusHistory = woStatusHistoryDAO.getWoStatusHistoryByID(contactDTO.getWoStatusHistoryId());		
		
		if( woReason != null && woStatusHistory != null){
			//Se validan los parametros requeridos
			UtilsBusiness.assertNotNull( woReason.getWorkorderReasonCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull( woReason.getWorkorderReasonName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			//Adicion de el nombre del Cliente
			problem.append( workOrder.getCustomer().getFirstName() );
			problem.append( separatorSpace );
			problem.append( workOrder.getCustomer().getLastName());
			problem.append( separator );
			//Adicion de la WO Reason del motivo de la Dificultad o Rechazo
			problem.append( woReason.getWorkorderReasonCode() );
			problem.append( separatorSpace );
			problem.append( woReason.getWorkorderReasonName() );
			problem.append( separator );
			//Adicion de la descripcion
			problem.append( workOrder.getWoDescription() );
			problem.append( separator );
			problem.append( " - " );
			//Adicion de la descripcion
			problem.append( woStatusHistory.getDescription() );
		}else{
			log.error(" No se puede construir el Problem Description porque no existe la WO_REASON con id:  "+reasonId);
		}
		
		return problem.toString();
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * @param workOrder
	 * @param separator
	 * @param ContactDTO contactDTO
	 * @return String 
	 * @throws BusinessException <tipo> <descripcion>
	 * @throws DAOSQLException 
	 * @throws DAOServiceException
	 * @author jalopez
	 *  
	 */
	private String getDescriptionAttention(WorkOrderVO workOrder, String separator, ContactDTO contactDTO) throws BusinessException, DAOServiceException, DAOSQLException{
		
		StringBuffer problem = new StringBuffer();
		StringBuffer services = new StringBuffer();
		
		//Adicion del Tecnico Asignado
		if( workOrder.getTecnicianName() != null )
			problem.append( workOrder.getTecnicianName() );
		else
			problem.append( ApplicationTextEnum.NO_TECHNICIAN_ASSIGNED.getApplicationTextValue() );
		problem.append( separator );
		
		//Adicion de los Servicios asociados a la WO
		//se iteran los servicios para construir la descripcion
		for (Iterator iterator = workOrder.getWorkOrderServices().iterator(); iterator.hasNext();) {
			WorkOrderService type = (WorkOrderService) iterator.next();
			services.append( type.getService().getServiceName() );		
			if(iterator.hasNext())
				services.append("-");
		}
		
		if( services.toString().equals("") )
			services.append(ApplicationTextEnum.WO_NOT_SERVICE.getApplicationTextValue());
		problem.append(services);
		
		//Adicion de los elementos asociados a la WO
		//Se obtienen las shipping orders elements utilizados en la atencion

		problem.append( separator );
		
		//Adicion de la descripcion
		problem.append( workOrder.getWoDescription() );
		
		//Adición de la descripción puesta en la atención
		WoStatusHistory woStatusHistory = woStatusHistoryDAO.getWoStatusHistoryByID(contactDTO.getWoStatusHistoryId());
		problem.append( separator );
		problem.append( " - " );
		problem.append( woStatusHistory.getDescription() );
		
		return problem.toString();
	}
	
	/**
	 * 
	 * Metodo: Genera el problem description
	 * cuando la WO fue finalizada
	 * @param workOrder WorkOrderVO
	 * @param separator String
	 * @param contactDTO ContactDTO
	 * @return String
	 * @throws BusinessException
	 * @author jalopez
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private String getDescriptionCompleted(WorkOrderVO workOrder, String separator, ContactDTO contactDTO) throws BusinessException, DAOServiceException, DAOSQLException{
		
		StringBuffer problem = new StringBuffer();
						
		//Se obtinen los elementos no serializados utilizados en la atencion de la WO.
		WoStatusHistory woStatusHistory = woStatusHistoryDAO.getWoStatusHistoryByID(contactDTO.getWoStatusHistoryId());		
		if( woStatusHistory != null ){
			//Adicion de los elementos no serializados
			//Adicion de la descripcion de la creacion de la WorkOrder
			problem.append( workOrder.getWoDescription() );
			problem.append( separator );
			problem.append( " - " );
			//Adicion del tecnico lider de la cuadrilla
			problem.append( separator );
			problem.append( ApplicationTextEnum.TECHNICAL.getApplicationTextValue()+": " );
			problem.append( workOrder.getTecnicianName() );
			problem.append( separator );
			problem.append( " - " );
			//Adicion de la descripcion de la finalizacion
			problem.append( woStatusHistory.getDescription() );
		}
		
		return problem.toString();
	}
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna el ultima agendamiento de la WO.
	 * @param woId Long - id de la wo
	 * @return WorkOrderAgenda
	 * @throws BusinessException
	 * @author jalopez
	 */
	private WorkOrderAgenda getWorkOrderAgenda(Long woId) throws BusinessException{
		log.debug("== Inicia la operacion getWorkOrderAgenda/ContactBusinessBean ==");
		try{
			//Se consulta el ultimo agendamiento de la WO
			WorkOrderAgenda woAgenda = this.workOrderAgendaDAO.getLastWorkOrderAgendaByWoID(woId);
            //Se valida si la orden de trabajo tiene agendamiento.
            if(woAgenda == null){
            	log.debug(ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getMessage());
       		 	throw new BusinessException(ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getMessage());
			}
            return woAgenda;
		 } catch(Throwable ex){
        	log.error("== Error en la operacion getWorkOrderAgenda/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getWorkOrderAgenda/ContactBusinessBean ==");
        }	
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts. 
	 * Consulta la configuracion del Contact
	 * segun la regla especificada.
	 * @param workOrder
	 * @return ContactConfiguration - retorna un objeto ContactConfiguration
	 * @throws BusinessException
	 * @author jalopez
	 */
	private ContactConfiguration getContactConfiguration(WorkOrderVO workOrder) throws BusinessException{
		log.debug("== Inicia la operacion getContactConfiguration/ContactBusinessBean ==");
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
        	log.error("== Error en la operacion getContactConfiguration/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getContactConfiguration/ContactBusinessBean ==");
        }	
	}
		
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna un listado de los codigos de las shipping orders de la WO.
	 * @param woId Long - id de la WO
	 * @return String - contiene los codigos de las Shipping order separados por (-)
	 * @throws BusinessException
	 * @author jalopez
	 */
	private String getShippingOrderByWorkOrder(Long woId) throws BusinessException{
		log.debug("== Inicia la operacion getShippingOrderByWorkOrder/ContactBusinessBean ==");
		try{
			List<ShippingOrder> listShippingOrderPojo;
			listShippingOrderPojo = shippingDAO.getShippingOrdersByWorkOrder(woId);			
			
			StringBuffer shippingOrders = new StringBuffer();
			for (Iterator iterator = listShippingOrderPojo.iterator(); iterator.hasNext();) {
				ShippingOrder shippingOrder = (ShippingOrder) iterator.next();
				shippingOrders.append(shippingOrder.getShippingOrderCode());
				if( iterator.hasNext() )
					shippingOrders.append("-");
			}			
			return shippingOrders.toString();
		 } catch(Throwable ex){
        	log.error("== Error en la operacion getShippingOrderByWorkOrder/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getShippingOrderByWorkOrder/ContactBusinessBean ==");
        }	
	}
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna los seriales de los elementos empleados
	 * en la Shipping Order.
	 * @param workOrderId
	 * @return String - Contiene los seriales de los elementos,
	 * son retornados por medio del sig. formato: 
	 * Nombre Tipo Elemento: Codigo serial, n
	 * @throws BusinessException
	 * @author jalopez
	 */
	private String getShippingOrderElementsSerialized(Long workOrderId) throws BusinessException{
		log.debug("== Inicia la operacion getShippingOrderElementsSerialized/ContactBusinessBean ==");
		try{
			StringBuffer elementSO = new StringBuffer();
			List<ShippingOrderElement> elements = shippingElementDAO.getSOrderElementByWorkOrderIdByElementTypeId(workOrderId);
			for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
				ShippingOrderElement shippingOrderElement = (ShippingOrderElement) iterator.next();
				Serialized serialized = serializedDAO.getSerializedElementByElementId( shippingOrderElement.getElement().getId() );
				if( serialized != null ){
					elementSO.append(serialized.getElement().getElementType().getTypeElementName());
					elementSO.append(":");
					elementSO.append(serialized.getSerialCode());
					if (iterator.hasNext())
						elementSO.append(",");
				}
			}
			return elementSO.toString();
			
		 } catch(Throwable ex){
        	log.error("== Error en la operacion getShippingOrderElementsSerialized/ContactBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina la operacion getShippingOrderElementsSerialized/ContactBusinessBean ==");
        }	
	}
	
}
