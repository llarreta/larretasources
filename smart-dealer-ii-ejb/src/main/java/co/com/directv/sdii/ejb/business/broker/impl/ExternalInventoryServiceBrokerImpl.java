package co.com.directv.sdii.ejb.business.broker.impl;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ExternalInventoryServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.broker.IExternalInventoryServiceBroker;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.toa.InventoryInterfaceTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;

import com.directvla.inventoryinterface.CtgetNotSerializedResourceFault;
import com.directvla.inventoryinterface.CtgetResourcesByServiceTypeFault;
import com.directvla.inventoryinterface.CtgetSerializedResourceFault;
import com.directvla.inventoryinterface.CtregisterNotSerializedResourceFault;
import com.directvla.inventoryinterface.CtregisterSerializedResourceFault;
import com.directvla.inventoryinterface.GetNotSerializedResourceRequest;
import com.directvla.inventoryinterface.GetNotSerializedResourceResponse;
import com.directvla.inventoryinterface.GetResourcesByServiceTypeRequest;
import com.directvla.inventoryinterface.GetResourcesByServiceTypeResponse;
import com.directvla.inventoryinterface.GetSerializedResourceRequest;
import com.directvla.inventoryinterface.GetSerializedResourceResponse;
import com.directvla.inventoryinterface.IInventory;
import com.directvla.inventoryinterface.IInventoryGetNotSerializedResourceGetNotSerializedResourceFaultFaultMessage;
import com.directvla.inventoryinterface.IInventoryGetResourcesByServiceTypeGetResourcesByServiceTypeFaultFaultMessage;
import com.directvla.inventoryinterface.IInventoryGetSerializedResourceGetSerializedResourceFaultFaultMessage;
import com.directvla.inventoryinterface.IInventoryRegisterNotSerializedResourceRegisterNotSerializedResourceFaultFaultMessage;
import com.directvla.inventoryinterface.IInventoryRegisterSerializedResourcesRegisterSerializedResourcesFaultFaultMessage;
import com.directvla.inventoryinterface.RegisterNotSerializedResourceRequest;
import com.directvla.inventoryinterface.RegisterNotSerializedResourceResponse;
import com.directvla.inventoryinterface.RegisterSerializedResourcesRequest;
import com.directvla.inventoryinterface.RegisterSerializedResourcesResponse;

/**
 * 
 * Clase de que define un patron Borker para la implementacion
 * de las operaciones de inventorios que son consumidas de un
 * sistema externo a la aplicacion.
 * 
 * Fecha de Creación: 21/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ExternalInventoryServiceBrokerImpl implements IExternalInventoryServiceBroker, IServiceBroker {

	private static ExternalInventoryServiceBrokerImpl broker;
	private final static Logger log = UtilsBusiness.getLog4J(ExternalInventoryServiceBrokerImpl.class);
	
	/**
	 * 
	 * Metodo: retorna una instacia de ExternalInventoryServiceBrokerImpl, patron singleton.
	 * @return ContactCoreBrokerImpl
	 * @throws ServiceLocatorException
	 * @author jalopez
	 */
	public static ExternalInventoryServiceBrokerImpl getInstance() throws ServiceLocatorException{
		try{
			if( broker == null ){
				broker = new ExternalInventoryServiceBrokerImpl();
			}
			return broker;
		}catch (Exception e) {
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(),e);
		}
	}
	
	@Override
	public ElementDTO getSerializedResource(InventoryDTO inventoryDTO) throws BusinessException {
		log.info("== Inicia getSerializedResource/ContactCoreBrokerImpl ==");
		try {
			IInventory inventoryInterface = null;
			GetSerializedResourceResponse response = null; 
			inventoryInterface = ExternalInventoryServiceLocator.getInstance().getInventoryInterfaceService();			
			GetSerializedResourceRequest request = InventoryInterfaceTOA.populateSerializedResourceInventory(inventoryDTO);
			response = inventoryInterface.getSerializedResource(request);			
			return InventoryInterfaceTOA.populateSerializedResourceElement(response);
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSerializedResource/ContactCoreBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.info("== Termina getSerializedResource/ContactCoreBrokerImpl ==");
        }
	}

	@Override
	public ElementDTO getNotSerializedResource(InventoryDTO inventoryDTO) throws BusinessException {
		log.info("== Inicia getNotSerializedResource/ContactCoreBrokerImpl ==");
		try {
			IInventory inventoryInterface = null;
			GetNotSerializedResourceResponse response = null;
			inventoryInterface = ExternalInventoryServiceLocator.getInstance().getInventoryInterfaceService();
			GetNotSerializedResourceRequest request = InventoryInterfaceTOA.populateNotSerializedResourceInventory(inventoryDTO);
			response = inventoryInterface.getNotSerializedResource(request);
			return InventoryInterfaceTOA.populateNotSerializedResourceElement(response);
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getNotSerializedResource/ContactCoreBrokerImpl ==");
	    	throw manageServiceException(ex);
	    } finally {
	        log.info("== Termina getNotSerializedResource/ContactCoreBrokerImpl ==");
	    }
	}

	@Override
	public RequiredServiceElementDTO getResourcesByServiceType(InventoryDTO inventoryDTO) throws BusinessException {
		log.info("== Inicia getResourcesByServiceType/ContactCoreBrokerImpl ==");
		try {			
			IInventory inventoryInterface = null;
			inventoryInterface = ExternalInventoryServiceLocator.getInstance().getInventoryInterfaceService();
			GetResourcesByServiceTypeRequest request = InventoryInterfaceTOA.populateResourcetsByServiceTypeInventory(inventoryDTO);
			GetResourcesByServiceTypeResponse response = inventoryInterface.getResourcesByServiceType(request);
			return InventoryInterfaceTOA.populateResourcetsByServiceTypeElement(response);
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getResourcesByServiceType/ContactCoreBrokerImpl ==");
	    	throw manageServiceException(ex);
	    } finally {
	        log.info("== Termina getResourcesByServiceType/ContactCoreBrokerImpl ==");
	    }
	}

	@Override
	public EnvelopeEncapsulateResponse registerNotSerializedResources( InventoryDTO inventoryDTO) throws BusinessException {
		log.info("== Inicia registerNotSerializedResources/ContactCoreBrokerImpl ==");
		try {			
			IInventory inventoryInterface = null;
			inventoryInterface = ExternalInventoryServiceLocator.getInstance().getInventoryInterfaceService();
			RegisterNotSerializedResourceRequest request = InventoryInterfaceTOA.populateRegisterNotSerializedResourcesInventory(inventoryDTO);
			RegisterNotSerializedResourceResponse response = inventoryInterface.registerNotSerializedResource(request);
			EnvelopeEncapsulateResponse responseEnvelope = InventoryInterfaceTOA.populateRegisterNotSerializedResourcesElement(response);
			log.info("Respuesta Servicio registerNotSerializedResource Field Service: "+responseEnvelope.getExceptions().get(0).getExceptionMessage());
			return responseEnvelope;
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación registerNotSerializedResources/ContactCoreBrokerImpl ==");
	    	throw manageServiceException(ex);
	    } finally {
	        log.info("== Termina registerNotSerializedResources/ContactCoreBrokerImpl ==");
	    }
	}

	@Override
	public EnvelopeEncapsulateResponse registerSerializedResources(InventoryDTO inventoryDTO) throws BusinessException {
		log.info("== Inicia registerSerializedResources/ContactCoreBrokerImpl ==");
		try {
			IInventory inventoryInterface = null;
			inventoryInterface = ExternalInventoryServiceLocator.getInstance().getInventoryInterfaceService();
			RegisterSerializedResourcesRequest request = InventoryInterfaceTOA.populateRegisterSerializedResourcesInventory(inventoryDTO);
			RegisterSerializedResourcesResponse response = inventoryInterface.registerSerializedResources(request);
			EnvelopeEncapsulateResponse responseEnvelope = InventoryInterfaceTOA.populateRegisterSerializedResourcesElement(response);
			log.info("Respuesta Servicio registerSerializedResources Field Service: "+responseEnvelope.getExceptions().get(0).getExceptionMessage());
			return responseEnvelope;
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación registerSerializedResources/ContactCoreBrokerImpl ==");
	    	throw manageServiceException(ex);
	    } finally {
	        log.info("== Termina registerSerializedResources/ContactCoreBrokerImpl ==");
	    }
	}

	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		if ( e instanceof IInventoryGetSerializedResourceGetSerializedResourceFaultFaultMessage){
			errorMessage = this.getSerializedResourceFault(((IInventoryGetSerializedResourceGetSerializedResourceFaultFaultMessage)e).getFaultInfo());
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof IInventoryGetNotSerializedResourceGetNotSerializedResourceFaultFaultMessage ){
			errorMessage = this.getNotSerializedResourceFault(((IInventoryGetNotSerializedResourceGetNotSerializedResourceFaultFaultMessage)e).getFaultInfo());
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof IInventoryGetResourcesByServiceTypeGetResourcesByServiceTypeFaultFaultMessage ){
			errorMessage = this.getResourcesByServiceTypeFault(((IInventoryGetResourcesByServiceTypeGetResourcesByServiceTypeFaultFaultMessage)e).getFaultInfo());
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof IInventoryRegisterSerializedResourcesRegisterSerializedResourcesFaultFaultMessage ){
			errorMessage = this.getRegisterSerializedResourcesFault(((IInventoryRegisterSerializedResourcesRegisterSerializedResourcesFaultFaultMessage)e).getFaultInfo());
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof IInventoryRegisterNotSerializedResourceRegisterNotSerializedResourceFaultFaultMessage ){
			errorMessage = this.getRegisterNotSerializedResourceFault(((IInventoryRegisterNotSerializedResourceRegisterNotSerializedResourceFaultFaultMessage)e).getFaultInfo());
			be = new BusinessException(errorCode, errorMessage);
		}else if( e instanceof SOAPFaultException ){
			//07/12/2011 se agrega validacion por caso que el servicio esta caido y el soap fault no llega con la excepcion bien armada
			if( ((SOAPFaultException) e).getFault() != null 
					&& ((SOAPFaultException) e).getFault().getDetail() != null 
					&& ((SOAPFaultException) e).getFault().getDetail().getFirstChild() != null 
					&& ((SOAPFaultException) e).getFault().getDetail().getFirstChild().getFirstChild() != null 
					&& ((SOAPFaultException) e).getFault().getDetail().getFirstChild().getFirstChild().getFirstChild() != null 
					&& ((SOAPFaultException) e).getFault().getDetail().getFirstChild().getFirstChild().getFirstChild().getNextSibling() != null 
					&& ((SOAPFaultException) e).getFault().getDetail().getFirstChild().getFirstChild().getFirstChild().getNextSibling().getFirstChild() != null){
				Node node = ((SOAPFaultException) e).getFault().getDetail().getFirstChild().getFirstChild().getFirstChild().getNextSibling().getFirstChild();
				be = new BusinessException(errorCode, node.getNodeValue());
			} else {
				be = new BusinessException(errorCode, e.getMessage());
			}
		}else if( e instanceof BusinessException ){		
			be = new BusinessException(((BusinessException) e).getMessageCode(), e.getMessage(), ((BusinessException) e).getParameters());
		}else{
			be = new BusinessException(errorCode, e.getMessage());
		}
		return be;
	}
	
	/**
	 * 
	 * Metodo: Se encarga de obtner el mensaje de error
	 * del Fault del Servicio
	 * @param faultException
	 * @return String, mensaje de error retornado por el servicio
	 * @author jalopez
	 */
	private String getSerializedResourceFault (CtgetSerializedResourceFault faultException){
		String errorMessage = "";
		if( faultException.getEntityAlreadyExistsException() != null ){
			errorMessage =  faultException.getEntityAlreadyExistsException().getMessage();
		}else if( faultException.getEntityInUseException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getEntityNotFoundException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getInternalErrorException() != null ){
			errorMessage =  faultException.getInternalErrorException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidQueryParameterException() != null ){
			errorMessage =  faultException.getInvalidQueryParameterException().getMessage();
		}else if( faultException.getInvalidRequestException() != null ){
			errorMessage =  faultException.getInvalidRequestException().getMessage();
		}else if( faultException.getInvalidResponseException() != null ){
			errorMessage =  faultException.getInvalidResponseException().getMessage();
		}else if( faultException.getInvalidSecurityTokenException() != null ){
			errorMessage =  faultException.getInvalidSecurityTokenException().getMessage();
		}else if( faultException.getMissingParameterException() != null ){
			errorMessage =  faultException.getMissingParameterException().getMessage();
		}else if( faultException.getOperationNotYetImplementedException() != null ){
			errorMessage =  faultException.getOperationNotYetImplementedException().getMessage();
		}else if( faultException.getServiceUnavailableException() != null ){
			errorMessage =  faultException.getServiceUnavailableException().getMessage();
		}
		return errorMessage;
	}
	
	/**
	 * 
	 * Metodo: Se encarga de obtner el mensaje de error
	 * del Fault del Servicio
	 * @param faultException
	 * @return String, mensaje de error retornado por el servicio
	 * @author jalopez
	 */
	private String getNotSerializedResourceFault (CtgetNotSerializedResourceFault faultException){
		String errorMessage = "";
		if( faultException.getEntityAlreadyExistsException() != null ){
			errorMessage =  faultException.getEntityAlreadyExistsException().getMessage();
		}else if( faultException.getEntityInUseException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getEntityNotFoundException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getInternalErrorException() != null ){
			errorMessage =  faultException.getInternalErrorException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidQueryParameterException() != null ){
			errorMessage =  faultException.getInvalidQueryParameterException().getMessage();
		}else if( faultException.getInvalidRequestException() != null ){
			errorMessage =  faultException.getInvalidRequestException().getMessage();
		}else if( faultException.getInvalidResponseException() != null ){
			errorMessage =  faultException.getInvalidResponseException().getMessage();
		}else if( faultException.getInvalidSecurityTokenException() != null ){
			errorMessage =  faultException.getInvalidSecurityTokenException().getMessage();
		}else if( faultException.getMissingParameterException() != null ){
			errorMessage =  faultException.getMissingParameterException().getMessage();
		}else if( faultException.getOperationNotYetImplementedException() != null ){
			errorMessage =  faultException.getOperationNotYetImplementedException().getMessage();
		}else if( faultException.getServiceUnavailableException() != null ){
			errorMessage =  faultException.getServiceUnavailableException().getMessage();
		}
		return errorMessage;
	}
	
	/**
	 * 
	 * Metodo: Se encarga de obtner el mensaje de error
	 * del Fault del Servicio
	 * @param faultException
	 * @return String, mensaje de error retornado por el servicio
	 * @author jalopez
	 */
	private String getResourcesByServiceTypeFault (CtgetResourcesByServiceTypeFault faultException){
		String errorMessage = "";
		if( faultException.getEntityAlreadyExistsException() != null ){
			errorMessage =  faultException.getEntityAlreadyExistsException().getMessage();
		}else if( faultException.getEntityInUseException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getEntityNotFoundException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getInternalErrorException() != null ){
			errorMessage =  faultException.getInternalErrorException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidQueryParameterException() != null ){
			errorMessage =  faultException.getInvalidQueryParameterException().getMessage();
		}else if( faultException.getInvalidRequestException() != null ){
			errorMessage =  faultException.getInvalidRequestException().getMessage();
		}else if( faultException.getInvalidResponseException() != null ){
			errorMessage =  faultException.getInvalidResponseException().getMessage();
		}else if( faultException.getInvalidSecurityTokenException() != null ){
			errorMessage =  faultException.getInvalidSecurityTokenException().getMessage();
		}else if( faultException.getMissingParameterException() != null ){
			errorMessage =  faultException.getMissingParameterException().getMessage();
		}else if( faultException.getOperationNotYetImplementedException() != null ){
			errorMessage =  faultException.getOperationNotYetImplementedException().getMessage();
		}else if( faultException.getServiceUnavailableException() != null ){
			errorMessage =  faultException.getServiceUnavailableException().getMessage();
		}
		return errorMessage;
	}
	
	/**
	 * 
	 * Metodo: Se encarga de obtner el mensaje de error
	 * del Fault del Servicio
	 * @param faultException
	 * @return String, mensaje de error retornado por el servicio
	 * @author jalopez
	 */
	private String getRegisterSerializedResourcesFault (CtregisterSerializedResourceFault faultException){
		String errorMessage = "";
		if( faultException.getEntityAlreadyExistsException() != null ){
			errorMessage =  faultException.getEntityAlreadyExistsException().getMessage();
		}else if( faultException.getEntityInUseException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getEntityNotFoundException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getInternalErrorException() != null ){
			errorMessage =  faultException.getInternalErrorException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidQueryParameterException() != null ){
			errorMessage =  faultException.getInvalidQueryParameterException().getMessage();
		}else if( faultException.getInvalidRequestException() != null ){
			errorMessage =  faultException.getInvalidRequestException().getMessage();
		}else if( faultException.getInvalidResponseException() != null ){
			errorMessage =  faultException.getInvalidResponseException().getMessage();
		}else if( faultException.getInvalidSecurityTokenException() != null ){
			errorMessage =  faultException.getInvalidSecurityTokenException().getMessage();
		}else if( faultException.getMissingParameterException() != null ){
			errorMessage =  faultException.getMissingParameterException().getMessage();
		}else if( faultException.getOperationNotYetImplementedException() != null ){
			errorMessage =  faultException.getOperationNotYetImplementedException().getMessage();
		}else if( faultException.getServiceUnavailableException() != null ){
			errorMessage =  faultException.getServiceUnavailableException().getMessage();
		}
		return errorMessage;
	}
	
	/**
	 * 
	 * Metodo: Se encarga de obtner el mensaje de error
	 * del Fault del Servicio
	 * @param faultException
	 * @return String, mensaje de error retornado por el servicio
	 * @author jalopez
	 */
	private String getRegisterNotSerializedResourceFault (CtregisterNotSerializedResourceFault faultException){
		String errorMessage = "";
		if( faultException.getEntityAlreadyExistsException() != null ){
			errorMessage =  faultException.getEntityAlreadyExistsException().getMessage();
		}else if( faultException.getEntityInUseException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getEntityNotFoundException() != null ){
			errorMessage =  faultException.getEntityInUseException().getMessage();
		}else if( faultException.getInternalErrorException() != null ){
			errorMessage =  faultException.getInternalErrorException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidParameterValueException() != null ){
			errorMessage =  faultException.getInvalidParameterValueException().getMessage();
		}else if( faultException.getInvalidQueryParameterException() != null ){
			errorMessage =  faultException.getInvalidQueryParameterException().getMessage();
		}else if( faultException.getInvalidRequestException() != null ){
			errorMessage =  faultException.getInvalidRequestException().getMessage();
		}else if( faultException.getInvalidResponseException() != null ){
			errorMessage =  faultException.getInvalidResponseException().getMessage();
		}else if( faultException.getInvalidSecurityTokenException() != null ){
			errorMessage =  faultException.getInvalidSecurityTokenException().getMessage();
		}else if( faultException.getMissingParameterException() != null ){
			errorMessage =  faultException.getMissingParameterException().getMessage();
		}else if( faultException.getOperationNotYetImplementedException() != null ){
			errorMessage =  faultException.getOperationNotYetImplementedException().getMessage();
		}else if( faultException.getServiceUnavailableException() != null ){
			errorMessage =  faultException.getServiceUnavailableException().getMessage();
		}
		return errorMessage;
	}
}
