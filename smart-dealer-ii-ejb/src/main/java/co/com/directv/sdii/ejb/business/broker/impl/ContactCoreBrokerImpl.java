package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.Random;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.CustomerContactsServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.ContactCoreBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.vo.ContactVO;

import com.directvla.contract.crm.customer.v1.contacts.AddCustomerInquiryException;
import com.directvla.contract.crm.customer.v1.contacts.PtCustomer;
import com.directvla.schema.crm.common.v1.contacts.CustomerInquiry;
import com.directvla.schema.crm.common.v1.contacts.RequestMetadataType;
import com.directvla.schema.crm.customer.v1.contacts.AddCustomerInquiry;
import com.directvla.schema.crm.customer.v1.contacts.AddCustomerInquiryRequest;
import com.directvla.schema.crm.customer.v1.contacts.AddCustomerInquiryResponse;
import com.directvla.schema.crm.customer.v1.contacts.AddCustomerInquiryResult;


/**
 * 
 * Provee la implementacion de la operacion de Creacion de Contact que ofrece el servicio
 * de Core en un sistema externo, se especifica para reducir el acoplamiento
 * con componentes generados desde definiciones de servicios web de terceros
 *  
 * 
 * Fecha de Creación: 18/11/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.ContactCoreBrokerLocal
 */
public class ContactCoreBrokerImpl extends BusinessBase implements ContactCoreBrokerLocal, IServiceBroker {

	private static ContactCoreBrokerImpl broker;
	private final static Logger log = UtilsBusiness.getLog4J(DealersServiceBrokerImpl.class);
	
	/**
	 * 
	 * Metodo: retorna una instacia de ContactCoreBrokerImpl, patron singleton.
	 * @return ContactCoreBrokerImpl
	 * @throws ServiceLocatorException
	 * @author jalopez
	 */
	public static ContactCoreBrokerImpl getInstance() throws ServiceLocatorException{
		try{
			if( broker == null ){
				broker = new ContactCoreBrokerImpl();
			}
			return broker;
		}catch (Exception e) {
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(),e);
		}
	}
	
	@Override
	public String createContactCoreIBS(ContactVO contactVO,String countryCode)	throws BusinessException {
		log.info("== Inicia createContactCoreIBS/ContactCoreBrokerImpl ==");
		try {
			Random r = new Random();
			PtCustomer customer = CustomerContactsServiceLocator.getInstance().getCustomerContactsService();
			AddCustomerInquiryRequest request = new AddCustomerInquiryRequest();
			AddCustomerInquiryResponse response;
			RequestMetadataType requestMetadata = new RequestMetadataType();
			AddCustomerInquiryResult result;
			AddCustomerInquiry addCustomerInquiry = new AddCustomerInquiry();
			String customerInquiry = "";
			//Construcccion del mensaje para la Metadata
			//se genera valor aleatorio para enviar en el request
			int requestId =  r.nextInt(1000000);
			requestMetadata.setRequestID( requestId+"" );
			requestMetadata.setSourceID( countryCode );
			request.setRequestMetadata( requestMetadata );
					
			addCustomerInquiry.setCustInq( buildContactIntoCustomerInquiry(contactVO) );
			//jalopez el servicio requiere que se envie un 0 en el parametro reason
			//este parametro no fue incluido dentro del CC puesto que fue
			//informado despues de levantado el requerimiento ademas no 
			//impacta el negocio segun informo DTV, solo es requerido.
			addCustomerInquiry.setReason(0);
			
			request.setAddCustomerInquiry( addCustomerInquiry );
			response = customer.addCustomerInquiry( request );			
			result = response.getAddCustomerInquiryResult();
			customerInquiry = result.getCustomerInquiryKey();
			log.info("== Resultado de la invocacion de la Operacion addCustomerInquiry CustomerInquiryKey: "+customerInquiry+" ==");
			return customerInquiry;
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createContactCoreIBS/ContactCoreBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.info("== Termina createContactCoreIBS/ContactCoreBrokerImpl ==");
        }
	}		
	
	/**
	 * 
	 * Metodo: Control Cambios Creacion Contacts
	 * Transforma un objeto de negocio de tipo ContactVO
	 * a un objeto de tipo Contract CustomerInquiry.
	 * @param contactVO ContactVO - Contiene los datos para generar el Contact
	 * @return CustomerInquiry
	 * @author jalopez
	 */
	private CustomerInquiry buildContactIntoCustomerInquiry( ContactVO contactVO ){
		
		CustomerInquiry customerInquiry =  new CustomerInquiry();
		
		//jalopez - el contrato debe ser modificaco por DTV para que reciba un valor numerico
		customerInquiry.setType( String.valueOf( contactVO.getContactConfiguration().getCategory().getIbsCode() ) );
		//jalopez - el contrato debe ser modificaco por DTV para que reciba un valor cadena
		customerInquiry.setWorkOrderId( Integer.parseInt( contactVO.getWoCode() ) );
		customerInquiry.setDescription( contactVO.getProblemDescription() );
		customerInquiry.setInteractionDate( UtilsBusiness.dateToGregorianCalendar(contactVO.getCreationDate()) );
		customerInquiry.setInteractionStatus( contactVO.getContactConfiguration().getContactStatus().getIbsCode() );
		customerInquiry.setCustomerKey( contactVO.getCustomerCode() );
		customerInquiry.setCharacteristicValue( contactVO.getContactConfiguration().getInputMethod().getIbsCode() );
		
		//Se escribe la informacion enviada en el log
		logCustomerInquiry(customerInquiry);
		
		return customerInquiry;
	}
	
	/**
	 * Metodo: Control Cambios Creacion Contacts
	 * @param customerInquiry
	 */
	private void logCustomerInquiry(CustomerInquiry customerInquiry){
		//Objetos Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		buffer.append(" Type:"+customerInquiry.getType());
		buffer.append(" WorkOrderId:"+customerInquiry.getWorkOrderId());
		buffer.append(" Description:"+customerInquiry.getDescription());
		buffer.append(" InteractionDate:"+customerInquiry.getInteractionDate());
		buffer.append(" InteractionStatus:"+customerInquiry.getInteractionStatus());
		buffer.append(" CustomerKey:"+customerInquiry.getCustomerKey());
		buffer.append(" CharacteristicValue:"+customerInquiry.getCharacteristicValue());
		
		//Construcccion del mensaje
		params[0] = "AddCustomerInquiry";
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		
		log.info( message.toString() );
	}

//------------------------------INICIA EL MANEJO DE EXCEPCIONES--------------------------------------------------------------------------------------------------------	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IServiceBroker#manageServiceException(java.lang.Throwable)
	 */
	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		if(e instanceof AddCustomerInquiryException){
			errorMessage = getAddCustomerInquiryExceptionMessage(((AddCustomerInquiryException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if(e instanceof BusinessException){
			be = (BusinessException)e;
		}else{
			be = super.manageException(e);
		}
		return be;
	}

	private String getAddCustomerInquiryExceptionMessage(
			com.directvla.schema.crm.customer.v1.contacts.AddCustomerInquiryException faultInfo,
			String errorMessage) {
		if(faultInfo.getAccessDeniedException() != null && faultInfo.getAccessDeniedException().getMessage() != null ){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getEntityAlreadyExistsException() != null && faultInfo.getEntityAlreadyExistsException().getMessage() != null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityInUseException() != null && faultInfo.getEntityInUseException().getMessage() != null ){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getEntityNotFoundException() != null && faultInfo.getEntityNotFoundException().getMessage() != null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getInternalErrorException() != null && faultInfo.getInternalErrorException().getMessage() != null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException() != null && faultInfo.getInvalidParameterValueException().getMessage() != null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException() != null && faultInfo.getInvalidQueryParameterException().getMessage() != null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException() != null && faultInfo.getInvalidRequestException().getMessage() != null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException() != null && faultInfo.getInvalidResponseException().getMessage() != null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException() != null && faultInfo.getInvalidSecurityTokenException().getMessage() != null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getMissingParameterException() != null && faultInfo.getMissingParameterException().getMessage() != null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException() != null && faultInfo.getOperationNotYetImplementedException().getMessage() != null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		
		return errorMessage;
	}

	//------------------------------FINALIZA EL MANEJO DE EXCEPCIONES--------------------------------------------------------------------------------------------------------	
}
