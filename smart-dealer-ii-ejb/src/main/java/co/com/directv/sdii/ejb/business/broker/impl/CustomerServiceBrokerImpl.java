/**
 * Creado 20/10/2010 10:31:04
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.toa.CustomerServiceTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.CustomerResourcesDTO;
import co.com.directv.sdii.ws.model.dto.CustomerResourcesResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomersByIdentificationResponseDTO;

import com.directvla.contract.crm.customer.v1_0.CustomerPortType;
import com.directvla.contract.crm.customer.v1_0.GetCustomerAddressesException;
import com.directvla.contract.crm.customer.v1_0.GetCustomerAddressesException_Exception;
import com.directvla.contract.crm.customer.v1_0.GetCustomerCharacteristicsException;
import com.directvla.contract.crm.customer.v1_0.GetCustomerCharacteristicsException_Exception;
import com.directvla.contract.crm.customer.v1_0.GetCustomerInfoException;
import com.directvla.contract.crm.customer.v1_0.GetCustomerInfoException_Exception;
import com.directvla.contract.crm.customer.v1_0.GetCustomerInquiriesException;
import com.directvla.contract.crm.customer.v1_0.GetCustomerInquiriesException_Exception;
import com.directvla.contract.crm.customer.v1_0.GetCustomerProductsException;
import com.directvla.contract.crm.customer.v1_0.GetCustomerProductsException_Exception;
import com.directvla.contract.crm.customer.v1_0.GetCustomerResources;
import com.directvla.contract.crm.customer.v1_0.GetCustomerResourcesException;
import com.directvla.contract.crm.customer.v1_0.GetCustomerResourcesException_Exception;
import com.directvla.contract.crm.customer.v1_0.GetCustomerResourcesRequest;
import com.directvla.contract.crm.customer.v1_0.GetCustomerResourcesResult;
import com.directvla.contract.crm.customer.v1_0.GetCustomersByCreditCardException;
import com.directvla.contract.crm.customer.v1_0.GetCustomersByCreditCardException_Exception;
import com.directvla.contract.crm.customer.v1_0.GetCustomersByIdentification;
import com.directvla.contract.crm.customer.v1_0.GetCustomersByIdentificationException;
import com.directvla.contract.crm.customer.v1_0.GetCustomersByIdentificationException_Exception;
import com.directvla.contract.crm.customer.v1_0.GetCustomersByIdentificationRequest;
import com.directvla.contract.crm.customer.v1_0.GetCustomersByIdentificationResult;
import com.directvla.schema.util.commondatatypes.v1_0.RequestMetadataType;

/**
 * Implementa la comunicaciÃ³n de servicios web para obtener la informaciÃ³n del cliente desde IBS
 * 
 * Fecha de CreaciÃ³n: 20/10/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal
 * @see co.com.directv.sdii.ejb.business.IBSWSBase
 */
@Stateless(name="CustomerServiceBrokerLocal",mappedName="ejb/CustomerServiceBrokerLocal")
public class CustomerServiceBrokerImpl extends IBSWSBase implements
		CustomerServiceBrokerLocal, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(CustomerServiceBrokerImpl.class);
	
	/**
	 * Metodo: Permite obtener el puerto de invocacion del servicio de customer
	 * @return CustomerPortType Puerto de invocacion para el WS de IBS de customer
	 * @throws ServiceLocatorException 
	 */
	private CustomerPortType getCustomerServicePort() throws ServiceLocatorException  {
		CustomerPortType customer = ServiceLocator.getInstance().getCustomerService();
		return customer;
	}
	
	/**
	 * Metodo: Obtiene la cabecera que se usarÃ¡ para la invocaciÃ³n de cualquiera de las operaciones relacionadas con el servicio de clientes
	 * @param countryCode cÃ³digo del paÃ­s
	 * @return Cabecera que serÃ¡ usada en la invocaciÃ³n de operaciones del servicio web de clientes
	 * @author jjimenenezh
	 * @throws BusinessException 
	 */
	private com.directvla.schema.util.commondatatypes.v1_0.RequestMetadataType getCustomerMetadataType(String countryCode) throws BusinessException {
		com.directvla.schema.util.commondatatypes.v1_0.RequestMetadataType requestMetadataType = new com.directvla.schema.util.commondatatypes.v1_0.RequestMetadataType();
		Random r = new Random();
		int requestId =  r.nextInt(1000000);
		requestMetadataType.setRequestID(requestId+"");
		requestMetadataType.setSourceID(countryCode);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.debug("== Error al tratar de ejecutar la operación getCustomerMetadataType/CustomerServiceBrokerImpl ==");
			throw manageServiceException(ex);
		} finally {
			log.debug("== Termina la operación getCustomerMetadataType/CustomerServiceBrokerImpl ==");
		}
		return requestMetadataType;
	}
	
	
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal#getCustomerResourcesFromIBS(java.lang.String, java.lang.String)
	 */
	@Override
	public CustomerResourcesResponseDTO getCustomerResourcesFromIBS(String customerKey, String sourceId) throws BusinessException {

		log.debug("== Inicia la operaciÃ³n getCustomerResourcesFromIBS/CustomerServiceBrokerImpl ==");
		
		CustomerResourcesResponseDTO response = new CustomerResourcesResponseDTO();
		GetCustomerResourcesResult r = null;
		RequestMetadataType requestMetadata = getCustomerMetadataType(sourceId);
		GetCustomerResourcesRequest request = new GetCustomerResourcesRequest();
		GetCustomerResources info = new GetCustomerResources();
		info.setCustomerKey(customerKey);
		
		request.setGetCustomerResources(info);
		request.setRequestMetadata(requestMetadata);

		try {
			CustomerPortType portType = getCustomerServicePort();
			this.logOperationInvoke("getCustomerResources", sourceId);
			r = portType.getCustomerResources(request).getGetCustomerResourcesResult();
			response.setGetCustomerResourcesResult(r);
		} catch (Throwable e){
			log.error("Error al consultar la informaciÃ³n de equipos del cliente", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.debug("== Termina la operaciÃ³n getCustomerResourcesFromIBS/CustomerServiceBrokerImpl ==");
		}
		
		return response;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal#getCustomerResourcesFromIBSIntoSDModel(java.lang.String, java.lang.String)
	 */
	@Override
	public List<CustomerResourcesDTO> getActiveCustomerResourcesFromIBSIntoSDModel(String customerKey, String sourceId) throws BusinessException{
		log.debug("== Inicia la operaciÃ³n getCustomerResourcesFromIBSIntoSDModel/CustomerServiceBrokerImpl ==");
		
		GetCustomerResourcesResult serviceResponse = null;
		RequestMetadataType requestMetadata = getCustomerMetadataType(sourceId);
		GetCustomerResourcesRequest request = new GetCustomerResourcesRequest();
		GetCustomerResources info = new GetCustomerResources();
		info.setCustomerKey(customerKey);
		
		request.setGetCustomerResources(info);
		request.setRequestMetadata(requestMetadata);

		try {
			CustomerPortType portType = getCustomerServicePort();
			this.logOperationInvoke("getCustomerResources", sourceId);
			serviceResponse = portType.getCustomerResources(request).getGetCustomerResourcesResult();
			return CustomerServiceTOA.populateCustomerResourcesDTO(serviceResponse);
		} catch (Throwable e){
			log.error("Error al consultar la informaciÃ³n de equipos del cliente getCustomerResourcesFromIBSIntoSDModel/CustomerServiceBrokerImpl", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.debug("== Termina la operaciÃ³n getCustomerResourcesFromIBSIntoSDModel/CustomerServiceBrokerImpl ==");
		}
		
	}

//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal#getCustomerWorkOrdersFromIBS(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public CustomerWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(String customerKey, String sourceId) throws BusinessException {
//
//		log.debug("== Inicia la operaciÃ³n getCustomerWorkOrdersFromIBS/CustomerServiceBrokerImpl ==");
//		
//		CustomerWorkOrdersResponseDTO response = new CustomerWorkOrdersResponseDTO();
//		/*GetCustomerWorkOrdersResult r = null;
//		RequestMetadataType requestMetadata = getCustomerMetadataType(sourceId);
//		GetCustomerWorkOrdersRequest request = new GetCustomerWorkOrdersRequest();
//		GetCustomerWorkOrders info = new GetCustomerWorkOrders();
//		info.setCustomerKey(customerKey);
//		
//		request.setGetCustomerWorkOrders(info);
//		request.setRequestMetadata(requestMetadata);
//		
//		try {
//			CustomerPortType portType = getCustomerServicePort();
//			this.logOperationInvoke("getCustomerWorkOrders", sourceId);
//			r = portType.getCustomerWorkOrders(request).getGetCustomerWorkOrdersResult();
//			response.setGetCustomerWorkOrdersResult(r);
//		} catch (Throwable e){
//			log.error("Error al consultar la informaciÃ³n de WorkOrders del cliente", e);
//			e.printStackTrace();
//			throw manageServiceException(e);
//		} finally {
//			log.debug("== Termina la operaciÃ³n getCustomerWorkOrdersFromIBS/CustomerServiceBrokerImpl ==");	
//		}*/
//		
//		return response;
//	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal#getCustomersByIdentificationFromIBS(java.lang.String, java.lang.String)
	 */
	@Override
	public CustomersByIdentificationResponseDTO getCustomersByIdentificationFromIBS(String customerKey, String sourceId) throws BusinessException {

		log.debug("== Inicia la operaciÃ³n getCustomersByIdentificationFromIBS/CustomerServiceBrokerImpl ==");
		
		CustomersByIdentificationResponseDTO response = new CustomersByIdentificationResponseDTO();
		GetCustomersByIdentificationResult r = null;
		RequestMetadataType requestMetadata = getCustomerMetadataType(sourceId);
		GetCustomersByIdentificationRequest request = new GetCustomersByIdentificationRequest();
		GetCustomersByIdentification info = new GetCustomersByIdentification();
		info.setCustomerIdentification(customerKey);
		
		request.setGetCustomersByIdentification(info);
		request.setRequestMetadata(requestMetadata);
		
		try {
			CustomerPortType portType = getCustomerServicePort();
			this.logOperationInvoke("getCustomersByIdentification", sourceId);
			r = portType.getCustomersByIdentification(request).getGetCustomersByIdentificationResult();
			response.setGetCustomersByIdentificationResult(r);
		} catch (Throwable e){
			log.error("Error al consultar la informaciÃ³n del cliente por identificaciÃ³n", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.debug("== Termina la operaciÃ³n getCustomersByIdentificationFromIBS/CustomerServiceBrokerImpl ==");
		}
		
		return response;
	}

//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal#getFinancialInfoFromIBS(java.lang.Long, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public FinancialInfoResponseDTO getFinancialInfoFromIBS(Long customerCode, String accountNumber, String sourceId) throws BusinessException {
//
//		log.debug("== Inicia la operaciÃ³n getFinancialInfoFromIBS/CustomerServiceBrokerImpl ==");
//		
//		FinancialInfoResponseDTO response = new FinancialInfoResponseDTO();
//		GetFinancialInfoResult r = null;
//		RequestMetadataType requestMetadata = getCustomerMetadataType(sourceId);
//		GetFinancialInfoRequest request = new GetFinancialInfoRequest();
//		GetFinancialInfo info = new GetFinancialInfo();
//		info.setCodClient(customerCode);
//		info.setAccountNumber(accountNumber);
//		
//		request.setGetFinancialInfo(info);
//		request.setRequestMetadata(requestMetadata);
//
//		try {
//			CustomerPortType portType = getCustomerServicePort();
//			this.logOperationInvoke("getCustomerFinancialInfo", sourceId);
//			r = portType.getCustomerFinancialInfo(request).getGetFinancialInfoResult();
//			response.setGetFinancialInfoResult(r);
//		} catch (Throwable e){
//			log.error("Error al consultar la informaciÃ³n financiera del cliente", e);
//			e.printStackTrace();
//			throw manageServiceException(e);
//		} finally {
//			log.debug("== Termina la operaciÃ³n getFinancialInfoFromIBS/CustomerServiceBrokerImpl ==");
//		}
//		
//		return response;
//	}
//
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.ejb.business.broker.CustomerServiceBrokerLocal#getInvoiceFromIBS(java.lang.Integer, java.lang.String)
//	 */
//	@Override
//	public InvoiceResponseDTO getInvoiceFromIBS(Integer customerId, String sourceId) throws BusinessException {
//
//		log.debug("== Inicia la operaciÃ³n getInvoiceFromIBS/CustomerServiceBrokerImpl ==");
//		
//		InvoiceResponseDTO response = new InvoiceResponseDTO();
//		GetInvoiceResult r = null;
//		RequestMetadataType requestMetadata = getCustomerMetadataType(sourceId);
//		GetInvoiceRequest request = new GetInvoiceRequest();
//		GetInvoice info = new GetInvoice();
//		info.setCustomerId(customerId);
//		
//		request.setGetInvoice(info);
//		request.setRequestMetadata(requestMetadata);
//		
//		try {
//			CustomerPortType portType = getCustomerServicePort();
//			this.logOperationInvoke("getInvoice", sourceId);
//			r = portType.getInvoice(request).getGetInvoiceResult();
//			response.setGetInvoiceResult(r);
//		} catch (Throwable e){
//			log.error("Error al consultar la informaciÃ³n financiera del cliente", e);
//			e.printStackTrace();
//			throw manageServiceException(e);
//		} finally {
//			log.debug("== Termina la operaciÃ³n getInvoiceFromIBS/CustomerServiceBrokerImpl ==");
//		}
//		
//		return response;
//	}
//	
	/**
	 * 
	 * Metodo: Escribe en el log la operacion
	 * consumida del servicio
	 * @param operation String
	 * @param sourceId String
	 * @author jalopez
	 * @throws BusinessException 
	 */
	public void logOperationInvoke(String operation, String sourceId) throws BusinessException{
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		buffer.append(logMetadata(sourceId));
		
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
	 * 
	 * Metodo: Retorna el log de la informacion
	 * enviada de la Metadata
	 * @param inventoryDTO
	 * @return String
	 * @author jalopez
	 * @throws BusinessException 
	 */
	private String logMetadata(String sourceId) throws BusinessException {
		StringBuilder buffer = new StringBuilder();
		RequestMetadataType metadata = getCustomerMetadataType(sourceId);
		buffer.append(" Metadata [");
		buffer.append(" RequestID: "+metadata.getRequestID());
		buffer.append(" SourceID: "+metadata.getSourceID());
		buffer.append(" SystemId: "+metadata.getSystemID());
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
		
		if(e instanceof GetCustomerAddressesException_Exception){
			errorMessage = getGetCustomerAddressesExceptionExceptionMessage(((GetCustomerAddressesException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof GetCustomerCharacteristicsException_Exception){
			errorMessage = getGetCustomerCharacteristicsExceptionExceptionMessage(((GetCustomerCharacteristicsException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof GetCustomerInfoException_Exception){
			errorMessage = getGetGetCustomerInfoExceptionExceptionMessage(((GetCustomerInfoException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if (e instanceof GetCustomerProductsException_Exception){
			errorMessage = getCustomerProductsExceptionExceptionExceptionMessage(((GetCustomerProductsException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if (e instanceof GetCustomersByIdentificationException_Exception){
			errorMessage = getCustomersByIdentificationExceptionExceptionMessage(((GetCustomersByIdentificationException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if (e instanceof GetCustomerResourcesException_Exception){
			errorMessage = getCustomerResourcesExceptionExceptionMessage(((GetCustomerResourcesException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if (e instanceof GetCustomerInquiriesException_Exception) {
			errorMessage = getCustomerInquiriesExceptionExceptionMessage(((GetCustomerInquiriesException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if (e instanceof GetCustomersByCreditCardException_Exception){
			errorMessage = getCustomersByCreditCardExceptionExceptionMessage(((GetCustomersByCreditCardException_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if(e instanceof BusinessException){
			be = (BusinessException)e;
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
	private String getCustomersByCreditCardExceptionExceptionMessage(
			GetCustomersByCreditCardException faultInfo, String errorMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return errorMessage;
	}

	/**
	 * @param faultInfo
	 * @param errorMessage
	 * @return
	 */
	private String getCustomerInquiriesExceptionExceptionMessage(
			GetCustomerInquiriesException faultInfo, String errorMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return errorMessage;
	}

	/**
	 * @param faultInfo
	 * @param errorMessage
	 * @return
	 */
	private String getCustomerResourcesExceptionExceptionMessage(
			GetCustomerResourcesException faultInfo, String errorMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return errorMessage;
	}

	/**
	 * @param faultInfo
	 * @param errorMessage
	 * @return
	 */
	private String getCustomersByIdentificationExceptionExceptionMessage(
			GetCustomersByIdentificationException faultInfo, String errorMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return errorMessage;
	}

	/**
	 * @param faultInfo
	 * @param errorMessage
	 * @return
	 */
	/*private String getCustomerWorkOrdersException_ExceptionMessage(
			GetCustomerWorkOrdersException faultInfo, String errorMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return errorMessage;
	}*/

	/**
	 * @param faultInfo
	 * @param errorMessage
	 * @return
	 */
	private String getCustomerProductsExceptionExceptionExceptionMessage(
			GetCustomerProductsException faultInfo, String errorMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return errorMessage;
	}

	/**
	 * @param faultInfo
	 * @param defaultMessage
	 * @return
	 */
	private String getGetGetCustomerInfoExceptionExceptionMessage(
		GetCustomerInfoException faultInfo, String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return defaultMessage;
}

	/**
	 * @param faultInfo
	 * @param defaultMessage
	 * @return
	 */
	private String getGetCustomerCharacteristicsExceptionExceptionMessage(
		GetCustomerCharacteristicsException faultInfo, String defaultMessage) {
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return defaultMessage;
}

	/**
	 * @param faultInfo
	 * @param defaultMessage
	 * @return
	 */
	private String getGetCustomerAddressesExceptionExceptionMessage(
			GetCustomerAddressesException faultInfo, String defaultMessage) {
		
		if(faultInfo.getAccessDeniedException() != null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null){
			return faultInfo.getInternalErrorException().getMessage();
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
		}else if(faultInfo.getMissingParameterException() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return defaultMessage;
	}

	

	//------------------------------------FIN DEL MANEJO DE EXCEPCIONES---------------------------------------------------------------------------------------------------
	
}
