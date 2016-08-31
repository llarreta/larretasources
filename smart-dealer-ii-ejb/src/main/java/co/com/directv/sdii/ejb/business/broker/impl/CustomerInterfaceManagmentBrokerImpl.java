/**
 * Creado 20/10/2010 10:31:04
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.CustomerInterfaceManagmentBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.AddCustomerInquiryIBSDTO;
import co.com.directv.sdii.model.dto.CustomerInquiriesByCriteriaIBSDTO;
import co.com.directv.sdii.model.pojo.ContactStatus;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.IbsContactReason;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.IbsContactVO;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ContactStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.core.IbsContactReasonDAOLocal;

import com.directvla.contract.businessdomain.customerinterfacemanagement.v1_0.CustomerInterfaceManagementPt;
import com.directvla.contract.businessdomain.customerinterfacemanagement.v1_0.GetCustomerInquiriesByCriteriaException;
import com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.BusinessInteractionType;
import com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.Category;
import com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.CriteriaSpecification;
import com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.FilterCollection;
import com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.GreaterThan;
import com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.GreaterThanOrEqualTo;
import com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.LessThanOrEqualTo;
import com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.Restriction;
import com.directvla.schema.businessdomain.customer.customerinterfacemanagement.v1_0.CustomerInquiry;
import com.directvla.schema.businessdomain.customerinterfacemanagement.v1_0.AddCustomerInquiry;
import com.directvla.schema.businessdomain.customerinterfacemanagement.v1_0.AddCustomerInquiryRequest;
import com.directvla.schema.businessdomain.customerinterfacemanagement.v1_0.AddCustomerInquiryResponse;
import com.directvla.schema.businessdomain.customerinterfacemanagement.v1_0.GetCustomerInquiriesByCriteria;
import com.directvla.schema.businessdomain.customerinterfacemanagement.v1_0.GetCustomerInquiriesByCriteriaRequest;
import com.directvla.schema.businessdomain.customerinterfacemanagement.v1_0.GetCustomerInquiriesByCriteriaResponse;

/**
 * Implementa la comunicación de servicios web para obtener la información de los contacts del cliente desde IBS 
 * 
 * Fecha de Creación: 26/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="CustomerInterfaceManagmentBrokerLocal",mappedName="ejb/CustomerInterfaceManagmentBrokerLocal")
public class CustomerInterfaceManagmentBrokerImpl extends IBSWSBase implements CustomerInterfaceManagmentBrokerLocal, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(CustomerInterfaceManagmentBrokerImpl.class);
	
	
	@EJB(name="CustomerDAOLocal", beanInterface=CustomerDAOLocal.class)
	private CustomerDAOLocal customerDAO;
	
	@EJB(name="WorkOrderDAOLocal", beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAO;
	
	@EJB(name="ContactStatusDAOLocal", beanInterface=ContactStatusDAOLocal.class)
	private ContactStatusDAOLocal contactStatusDAO;
	
	@EJB(name="IbsContactReasonDAOLocal", beanInterface=IbsContactReasonDAOLocal.class)
	private IbsContactReasonDAOLocal ibsContactReasonDAO;
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.CustomerInterfaceManagmentBrokerLocal#getCustomerInquiriesByCriteria(co.com.directv.sdii.model.dto.CustomerInquiriesByCriteriaIBSDTO)
	 */
	public List<CustomerInquiry> getCustomerInquiriesByCriteria(CustomerInquiriesByCriteriaIBSDTO request) throws BusinessException{
		log.debug("== Inicia la operación getCustomerInquiriesByCriteria/CustomerInterfaceManagmentBrokerImpl ==");
		
		List<CustomerInquiry> r = null;

		try {
			
			GetCustomerInquiriesByCriteriaRequest getCustomerInquiriesByCriteriaRequest = getGetCustomerInquiriesByCriteriaRequest(request);
			CustomerInterfaceManagementPt portType = getCustomerInterfaceManagment();
			this.logOperationInvoke("getCustomerInquiriesByCriteria", getCustomerInquiriesByCriteriaRequest.getRequestMetadata());
			GetCustomerInquiriesByCriteriaResponse getCustomerInquiriesByCriteriaResponse = portType.getCustomerInquiriesByCriteria(getCustomerInquiriesByCriteriaRequest);
			if(getCustomerInquiriesByCriteriaResponse!=null){
				if(getCustomerInquiriesByCriteriaResponse.getGetCustomerInquiriesByCriteriaResult()!=null){
					if(getCustomerInquiriesByCriteriaResponse.getGetCustomerInquiriesByCriteriaResult().getCustomerinquiries()!=null){
						if(getCustomerInquiriesByCriteriaResponse.getGetCustomerInquiriesByCriteriaResult().getCustomerinquiries().getCustomerInquiry()!=null
						   && !getCustomerInquiriesByCriteriaResponse.getGetCustomerInquiriesByCriteriaResult().getCustomerinquiries().getCustomerInquiry().isEmpty()){
							r = getCustomerInquiriesByCriteriaResponse.getGetCustomerInquiriesByCriteriaResult().getCustomerinquiries().getCustomerInquiry();
						}
						
					}
					
				}
				
			}
			
		} catch (Throwable e){
			log.error("Error al consultar la información de los contacts del cliente", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.debug("== Termina la operación getCustomerInquiriesByCriteria/CustomerInterfaceManagmentBrokerImpl ==");
		}
		
		return r;

	}
	
	public IbsContactVO populateIbsContactVO(CustomerInquiry customerInquiry,Country country) throws DAOServiceException, DAOSQLException, BusinessException{
		
		IbsContactVO ibsContactVO = new IbsContactVO();
		
		ibsContactVO.setIbsContactCode(customerInquiry.getID());
		
		Customer customer = customerDAO.getCustomerByCode(customerInquiry.getCustomerKey());
		if(customer == null){
			//no se exceptiona para seguir con el proceso de descarga de wo.
			//throw new BusinessException(ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage());
			String message = " El customer con código: "+customerInquiry.getCustomerKey()+" no existe en HSP+.";
			log.error(message);
			return null;
		}
		ibsContactVO.setCustomer(customer);
		
		WorkOrder workOrder = workOrderDAO.getWorkOrderByCode(String.valueOf(customerInquiry.getWorkOrderId()));
		if(workOrder == null){
			//no se exceptiona para seguir con el proceso de descarga de wo.
			//throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			String message = " El Work Order con código: "+String.valueOf(customerInquiry.getWorkOrderId())+" no existe en HSP+.";
			log.error(message);
			return null;
		}
		ibsContactVO.setWorkOrder(workOrder);
		
		ContactStatus contactStatus = contactStatusDAO.getContactStatusByIbsCode(customerInquiry.getInteractionStatus().getId());
		if(contactStatus == null){
			throw new BusinessException(ErrorBusinessMessages.CORE_NOT_EXIST_CONTACT_STATUS.getCode(), ErrorBusinessMessages.CORE_NOT_EXIST_CONTACT_STATUS.getMessage());
		}
		ibsContactVO.setContactStatus(contactStatus);
		
		IbsContactReason ibsContactReason = ibsContactReasonDAO.getIbsContactReasonByCode(customerInquiry.getType().getId(), country.getId());
		ibsContactVO.setIbsContactReason(ibsContactReason);
		
		ibsContactVO.setCountry(country);
		ibsContactVO.setDescription(customerInquiry.getDescription());
		ibsContactVO.setCreationDate(UtilsBusiness.dateFromGregorianCalendar(customerInquiry.getInteractionDate()));
		ibsContactVO.setCharacteristicValue(customerInquiry.getCharacteristicValue());
		ibsContactVO.setUserName(customerInquiry.getInvolvesName());
		ibsContactVO.setActionTaken(customerInquiry.getActionTaken());
		
		return ibsContactVO;
		
	}
	
	/**
	 * Metodo: Permite obtener el puerto de invocacion del servicio de customer
	 * @return CustomerPortType Puerto de invocacion para el WS de IBS de customer
	 * @throws ServiceLocatorException 
	 */
	private CustomerInterfaceManagementPt getCustomerInterfaceManagment() throws ServiceLocatorException  {
		CustomerInterfaceManagementPt customerInterfaceManagementPt = ServiceLocator.getInstance().getCustomerInterfaceManagment();
		return customerInterfaceManagementPt;
	}
	
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones relacionadas con el servicio de clientes
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del servicio web de clientes
	 * @author 
	 * @throws BusinessException 
	 */
	private com.directvla.schema.util.commondatatypes.customerinterfacemanagement.v1_0.RequestMetadataType getCustomerMetadataType(String countryCode) throws BusinessException {
		com.directvla.schema.util.commondatatypes.customerinterfacemanagement.v1_0.RequestMetadataType requestMetadataType = new com.directvla.schema.util.commondatatypes.customerinterfacemanagement.v1_0.RequestMetadataType();
		requestMetadataType.setRequestID(countryCode);
		requestMetadataType.setSourceID(countryCode);
		try{
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			}catch (Throwable e){
				log.error("Error al consultar la información de los contacts del cliente", e);
				e.printStackTrace();
				throw manageServiceException(e);
			} finally {
				log.debug("== Termina la operación getCustomerMetadataType/CustomerInterfaceManagmentBrokerImpl ==");
		}
		return requestMetadataType;
	}
	
	
	
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones relacionadas con el servicio de clientes
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del servicio web de clientes
	 * @author 
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 */
	private GetCustomerInquiriesByCriteriaRequest getGetCustomerInquiriesByCriteriaRequest(CustomerInquiriesByCriteriaIBSDTO request) throws PropertiesException, BusinessException {
		GetCustomerInquiriesByCriteriaRequest getCustomerInquiriesByCriteriaRequest  = new GetCustomerInquiriesByCriteriaRequest();
		getCustomerInquiriesByCriteriaRequest.setRequestMetadata(getCustomerMetadataType(request.getCountry().getCountryCode()));
		getCustomerInquiriesByCriteriaRequest.setGetCustomerInquiriesByCriteria(getGetCustomerInquiriesByCriteria(request));
		return getCustomerInquiriesByCriteriaRequest;
	}
	
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones relacionadas con el servicio de contacts
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del servicio web de clientes
	 * @author 
	 * @throws PropertiesException 
	 */
	private GetCustomerInquiriesByCriteria getGetCustomerInquiriesByCriteria(CustomerInquiriesByCriteriaIBSDTO request) throws PropertiesException {
		GetCustomerInquiriesByCriteria getCustomerInquiriesByCriteria  = new GetCustomerInquiriesByCriteria();
		
		CriteriaSpecification criteriaSpecification = new CriteriaSpecification();
		getCustomerInquiriesByCriteria.setCriterias(criteriaSpecification);


		FilterCollection filterCollection = new FilterCollection();
		List<Restriction> restrictions= filterCollection.getFilterItem();
		
		GreaterThan greaterThan = new GreaterThan();
		greaterThan.setKey(CodesBusinessEntityEnum.DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_THAN_KEY.getCodeEntity());
		greaterThan.setValue(request.getWorkOrderIdGreaterThan());
		restrictions.add(greaterThan);
		
		GreaterThanOrEqualTo greaterThanOrEqualTo = new GreaterThanOrEqualTo();
		greaterThanOrEqualTo.setKey(CodesBusinessEntityEnum.DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_OR_LESS_THAN_OR_EQUAL_KEY.getCodeEntity());
		greaterThanOrEqualTo.setValue(UtilsBusiness.DateToISO8601(request.getBeginDate()));
		greaterThanOrEqualTo.setValueOption(CodesBusinessEntityEnum.DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_OR_LESS_THAN_OR_EQUAL_VALUE_OPTION.getCodeEntity());
		restrictions.add(greaterThanOrEqualTo);

		/*
		LessThanOrEqualTo lessThanOrEqualTo = new LessThanOrEqualTo();
		lessThanOrEqualTo.setKey(CodesBusinessEntityEnum.DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_OR_LESS_THAN_OR_EQUAL_KEY.getCodeEntity());
		lessThanOrEqualTo.setValue(UtilsBusiness.DateToISO8601(request.getEndDate().getTime()));
		lessThanOrEqualTo.setValueOption(CodesBusinessEntityEnum.DOWNLOAD_CUSTOMER_CONTACT_TEMPLATE_GREATER_OR_LESS_THAN_OR_EQUAL_VALUE_OPTION.getCodeEntity());
		restrictions.add(lessThanOrEqualTo);
		*/
		
		criteriaSpecification.setFilters(filterCollection);
		
		com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.Paging paging = new com.directvla.schema.businessdomain.common.customerinterfacemanagement.v1_0.Paging();
		paging.setPages(request.getPageIndex());
		paging.setPageSize(request.getPageSize());
		criteriaSpecification.setPaging(paging);

		return getCustomerInquiriesByCriteria;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.CustomerInterfaceManagmentBrokerLocal#addCustomerInquiry(co.com.directv.sdii.model.dto.AddCustomerInquiryIBSDTO)
	 */
	@Override
	public void addCustomerInquiry(AddCustomerInquiryIBSDTO request) throws BusinessException{
		log.debug("== Inicia la operación addCustomerInquiry/CustomerInterfaceManagmentBrokerImpl ==");

		try {			
			AddCustomerInquiryRequest addCustomerInquiryRequest = buildAddCustomerInquiryRequest(request);
			CustomerInterfaceManagementPt portType = this.getCustomerInterfaceManagment();
			
			AddCustomerInquiryResponse addCustomerInquiryResponse = portType.addCustomerInquiry(addCustomerInquiryRequest);
			
		} catch (Throwable e){
			log.error("Error al consultar la información de los contacts del cliente", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.debug("== Termina la operación addCustomerInquiry/CustomerInterfaceManagmentBrokerImpl ==");
		}
	}
	
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones relacionadas con el servicio de clientes
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del servicio web de clientes
	 * @author 
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 */
	private AddCustomerInquiryRequest buildAddCustomerInquiryRequest(AddCustomerInquiryIBSDTO request) throws PropertiesException, BusinessException {
		AddCustomerInquiryRequest addCustomerInquiryRequest  = new AddCustomerInquiryRequest();
		addCustomerInquiryRequest.setRequestMetadata(this.getCustomerMetadataType(request.getCountry().getCountryCode()));
		addCustomerInquiryRequest.getRequestMetadata().setSystemID(request.getSystemId());
		addCustomerInquiryRequest.setAddCustomerInquiry(this.buildAddCustomerInquiry(request));
		return addCustomerInquiryRequest;
	}
	
	/**
	 * Metodo: Obtiene la cabecera que se usará para la invocación de cualquiera de las operaciones relacionadas con el servicio de contacts
	 * @param countryCode código del país
	 * @return Cabecera que será usada en la invocación de operaciones del servicio web de clientes
	 * @author 
	 * @throws PropertiesException 
	 */
	private AddCustomerInquiry buildAddCustomerInquiry(AddCustomerInquiryIBSDTO request) throws PropertiesException {
		AddCustomerInquiry addCustomerInquiry  = new AddCustomerInquiry();
		CustomerInquiry customerInquiry = new CustomerInquiry();
		
		customerInquiry.setCustomerKey(request.getCustomerCode()+"");
		customerInquiry.setInteractionDate(UtilsBusiness.dateToGregorianCalendar(request.getCreationDate()));
		Category interactionStatus = new Category();
		interactionStatus.setId(request.getContactStatus());
		customerInquiry.setInteractionStatus(interactionStatus);
		customerInquiry.setDescription(request.getDescription());
		//customerInquiry.setInvolves(request.getIbsUser());
		BusinessInteractionType type = new BusinessInteractionType(); 
		type.setId(request.getCategoryIbsCode()+"");
		customerInquiry.setType(type);
		customerInquiry.setCharacteristicValue(request.getInputMethod());
		
		addCustomerInquiry.setCustInq(customerInquiry);
		addCustomerInquiry.setReason(0);
		
		this.logOperation("addCustomerInquiry", request.getCustomerCode(), request.getCreationDate().toString(), request.getDescription(),
				request.getContactStatus(), request.getIbsUser(), request.getCategoryIbsCode()+"", request.getInputMethod(), "0");
		
		return addCustomerInquiry;
	}
	
	private void logOperation(String operation, String customerCode, String creationDate, String description, String contactStatus, String ibsUser, String categoryId, String inputMethod, String reason) {
		HashMap<String, String> logRequestValues = new HashMap<String, String>();
		logRequestValues.put("CustomerKey", customerCode);
		logRequestValues.put("InteractionDate", creationDate);
		logRequestValues.put("description", description);
		logRequestValues.put("ContactStatus", contactStatus);
		logRequestValues.put("IBS User", ibsUser);
		logRequestValues.put("CategoryId", categoryId);
		logRequestValues.put("InputMethod", inputMethod);
		logRequestValues.put("Reason", reason);
	
		logOperationInvokeHash(operation, logRequestValues);
	}
	
	public void logOperationInvokeHash(String operation, HashMap<String, String> requestValueMap){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = new Object[2];	
		
		buffer.append(logMetadataHash(requestValueMap));
		
		//Construcccion del mensaje
		params[0] = operation;
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
		
	}
	
	private String logMetadataHash(HashMap<String, String> requestValueMap){
		StringBuilder buffer = new StringBuilder();
		buffer.append(" Metadata [");
		java.util.Iterator<Entry<String, String>> iterator = requestValueMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,String> each = iterator.next();
			buffer.append(" " + each.getKey() + ":" + each.getValue());
		}
		buffer.append(" ]");
		return buffer.toString();
	}
	
	/**
	 * 
	 * Metodo: Escribe en el log la operacion
	 * consumida del servicio
	 * @param operation String
	 * @param sourceId String
	 * @author cduarte
	 */
	public void logOperationInvoke(String operation, com.directvla.schema.util.commondatatypes.customerinterfacemanagement.v1_0.RequestMetadataType metadata){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		buffer.append(logMetadata(metadata));
		
		//Construcccion del mensaje
		params[0] = operation;
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
	}
	
	/**
	 * Metodo: Retorna el log de la informacion
	 * @param sourceId
	 * @return <tipo> <descripcion>
	 * @author
	 */
	private String logMetadata(com.directvla.schema.util.commondatatypes.customerinterfacemanagement.v1_0.RequestMetadataType metadata){
		StringBuilder buffer = new StringBuilder();
		buffer.append(" Metadata [");
		buffer.append(" RequestID: "+metadata.getRequestID());
		buffer.append(" SourceID: "+metadata.getSourceID());
		buffer.append(" ]");
		return buffer.toString();
	}
	
//-----------------------------------INICIA EL MANEJO DE EXCEPCIONES---------------------------------------------------------------------------------------------------	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IServiceBroker#manageServiceException(java.lang.Throwable)
	 */
	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		if(e instanceof GetCustomerInquiriesByCriteriaException){
			errorMessage = getGetCustomerInquiriesByCriteriaException(((GetCustomerInquiriesByCriteriaException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else{
			be = super.manageException(e);
		}
		return be;
	}

	/**
	 * @param faultInfo
	 * @param errorMessage
	 * @return
	 */
	private String getGetCustomerInquiriesByCriteriaException(
			com.directvla.schema.businessdomain.customerinterfacemanagement.v1_0.GetCustomerInquiriesByCriteriaException faultInfo, String errorMessage) {
		
		if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException() != null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return errorMessage;
	}


	//------------------------------------FIN DEL MANEJO DE EXCEPCIONES---------------------------------------------------------------------------------------------------
	
}
