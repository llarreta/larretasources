package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.IBSCRMSupportAndReadinessBrokerLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CustomerAgreementTypesDTO;
import co.com.directv.sdii.model.dto.CustomerResponseByIdentificationResbDTO;
import co.com.directv.sdii.model.dto.CustomersResourcesResbDTO;

import com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.CRMSupportAndReadinessPt;
import com.directvla.schema.businessdomain.common.crmsupportandreadiness.v1_0.Agreement;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomerAgreements;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomerAgreementsRequest;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomerAgreementsResponse;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomerResources;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomerResourcesException;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomerResourcesRequest;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomerResourcesResponse;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomersByIdentification;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomersByIdentificationRequest;
import com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomersByIdentificationResponse;

/**
 * Session Bean implementation class IBSCRMSupportAndReadinessBrokerImpl
 */
@Stateless(name="IBSCRMSupportAndReadinessBroker",
		mappedName = "ejb/IBSCRMSupportAndReadinessBroker")
public class IBSCRMSupportAndReadinessBrokerImpl extends IBSWSBase implements IBSCRMSupportAndReadinessBrokerLocal {

	private final static Logger log = UtilsBusiness.getLog4J(IBSCRMSupportAndReadinessBrokerImpl.class);
	
    /**
     * Default constructor. 
     */
    public IBSCRMSupportAndReadinessBrokerImpl() {
    }


    /**
     * 
     * @param e la excepcion que se desea encapsular en una excepcion de negocio
     * @return La excepcion que genere un metodo encapsulada en una excepcion de negocio
     * @author Aharker
     */
	public BusinessException manageServiceException(Throwable e) {
			BusinessException be = null;
		
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		if (e instanceof com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.GetCustomerResourcesException) {
			errorMessage = getGetCustomerResourcesException_ExceptionMessage(((com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.GetCustomerResourcesException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.GetCustomersByIdentificationException) {
			errorMessage = getGetCustomersByIdentificationException_ExceptionMessage(((com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.GetCustomersByIdentificationException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.GetCustomerAgreementsException) {
			errorMessage = getGetCustomerAgreementsException_ExceptionMessage(((com.directvla.contract.businessdomain.crmsupportandreadiness.v1_0.GetCustomerAgreementsException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else{
			be = super.manageException(e);
		}
		
		return be;
	}
	
	/**
	 * Metodo para extraer el mensaje de error de una excepcion generada por el metodo GetCustomersByIdentification
	 * @param faultInfo informacion de la falla de ESB
	 * @param errorMessage mensaje de error donde se encapsulara lo que se muestra
	 * @return mensaje de error donde se encapsulara lo que se muestra
	 * @author Aharker
	 */
	private String getGetCustomersByIdentificationException_ExceptionMessage(
			com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomersByIdentificationException faultInfo,
			String errorMessage) {
		if (faultInfo.getEntityAlreadyExistsException()!=null){	
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if (faultInfo.getEntityNotFoundException()!=null){	
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if (faultInfo.getEntityInUseException()!=null){	
			return faultInfo.getEntityInUseException().getMessage();
		}else if (faultInfo.getInternalErrorException()!=null){	
			return faultInfo.getInternalErrorException().getMessage();
		}else if (faultInfo.getMissingParameterException()!=null){	
			return faultInfo.getMissingParameterException().getMessage();
		}else if (faultInfo.getInvalidParameterValueException()!=null){	
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if (faultInfo.getInvalidQueryParameterException()!=null){	
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if (faultInfo.getInvalidRequestException()!=null){	
			return faultInfo.getInvalidRequestException().getMessage();
		}else if (faultInfo.getInvalidResponseException()!=null){	
			return faultInfo.getInvalidResponseException().getMessage();
		}else if (faultInfo.getInvalidSecurityTokenException()!=null){	
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if (faultInfo.getAccessDeniedException()!=null){	
			return faultInfo.getAccessDeniedException().getMessage();
		}else if (faultInfo.getServiceUnavailableException()!=null){	
			return faultInfo.getServiceUnavailableException().getMessage();
		}else if (faultInfo.getOperationNotYetImplementedException()!=null){	
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return errorMessage;
	}

	/**
	 * Metodo para extraer el mensaje de error de una excepcion generada por el metodo GetCustomerResources
	 * @param faultInfo informacion de la falla de ESB
	 * @param errorMessage mensaje de error donde se encapsulara lo que se muestra
	 * @return mensaje de error donde se encapsulara lo que se muestra
	 * @author Aharker
	 */
	private String getGetCustomerResourcesException_ExceptionMessage(
			GetCustomerResourcesException faultInfo, String errorMessage) {

		if (faultInfo.getEntityAlreadyExistsException() != null) {
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		} else if (faultInfo.getEntityNotFoundException() != null) {
			return faultInfo.getEntityNotFoundException().getMessage();
		} else if (faultInfo.getEntityInUseException() != null) {
			return faultInfo.getEntityInUseException().getMessage();
		} else if (faultInfo.getInternalErrorException() != null) {
			return faultInfo.getInternalErrorException().getMessage();
		} else if (faultInfo.getMissingParameterException() != null) {
			return faultInfo.getMissingParameterException().getMessage();
		} else if (faultInfo.getInvalidParameterValueException() != null) {
			return faultInfo.getInvalidParameterValueException().getMessage();
		} else if (faultInfo.getInvalidQueryParameterException() != null) {
			return faultInfo.getInvalidQueryParameterException().getMessage();
		} else if (faultInfo.getInvalidRequestException() != null) {
			return faultInfo.getInvalidRequestException().getMessage();
		} else if (faultInfo.getInvalidResponseException() != null) {
			return faultInfo.getInvalidResponseException().getMessage();
		} else if (faultInfo.getInvalidSecurityTokenException() != null) {
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		} else if (faultInfo.getAccessDeniedException() != null) {
			return faultInfo.getAccessDeniedException().getMessage();
		} else if (faultInfo.getServiceUnavailableException() != null) {
			return faultInfo.getServiceUnavailableException().getMessage();
		} else if (faultInfo.getOperationNotYetImplementedException() != null) {
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return errorMessage;
	}

	
	/**
	 * Metodo para extraer el mensaje de error de una excepcion generada por el metodo GetCustomerResources
	 * @param faultInfo informacion de la falla de ESB
	 * @param errorMessage mensaje de error donde se encapsulara lo que se muestra
	 * @return mensaje de error donde se encapsulara lo que se muestra
	 * @author cduarte
	 */
	private String getGetCustomerAgreementsException_ExceptionMessage(
			com.directvla.schema.businessdomain.crmsupportandreadiness.v1_0.GetCustomerAgreementsException faultInfo, String errorMessage) {

		if (faultInfo.getEntityAlreadyExistsException() != null) {
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		} else if (faultInfo.getEntityNotFoundException() != null) {
			return faultInfo.getEntityNotFoundException().getMessage();
		} else if (faultInfo.getEntityInUseException() != null) {
			return faultInfo.getEntityInUseException().getMessage();
		} else if (faultInfo.getInternalErrorException() != null) {
			return faultInfo.getInternalErrorException().getMessage();
		} else if (faultInfo.getMissingParameterException() != null) {
			return faultInfo.getMissingParameterException().getMessage();
		} else if (faultInfo.getInvalidParameterValueException() != null) {
			return faultInfo.getInvalidParameterValueException().getMessage();
		} else if (faultInfo.getInvalidQueryParameterException() != null) {
			return faultInfo.getInvalidQueryParameterException().getMessage();
		} else if (faultInfo.getInvalidRequestException() != null) {
			return faultInfo.getInvalidRequestException().getMessage();
		} else if (faultInfo.getInvalidResponseException() != null) {
			return faultInfo.getInvalidResponseException().getMessage();
		} else if (faultInfo.getInvalidSecurityTokenException() != null) {
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		} else if (faultInfo.getAccessDeniedException() != null) {
			return faultInfo.getAccessDeniedException().getMessage();
		} else if (faultInfo.getServiceUnavailableException() != null) {
			return faultInfo.getServiceUnavailableException().getMessage();
		} else if (faultInfo.getOperationNotYetImplementedException() != null) {
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return errorMessage;
	}
   /*
	@Override
	public List<CustomerAgreementTypesDTO> getCustomerAgreements(String customerKey, String countryCode)
			throws BusinessException {
		log.debug("=== Iniciando llamado a servicio web getCustomerAgreements/IBSSPRMSupportAndReadinessBrokerImpl ===");
		try {
			GetCustomerAgreementsRequest request = this.generateGetCustomerAgreementsRequest(customerKey, countryCode);

			CRMSupportAndReadinessPt crmSupportAndReadinessPt = ServiceLocator.getInstance().getCRMSupportAndReadinessService(); //getInstance().getCRMSupportAndReadinessService();
			GetCustomerAgreementsResponse response = crmSupportAndReadinessPt.getCustomerAgreements(request);
			
			List<CustomerAgreementTypesDTO> listCustomerAgreementsDTOs = new ArrayList<CustomerAgreementTypesDTO>();
			if(response!=null && response.getGetCustomerAgreementsResult() !=null){
				if(response.getGetCustomerAgreementsResult().getCustomerAgreement()!=null
						&& response.getGetCustomerAgreementsResult().getCustomerAgreement().size() > 0L){
					CustomerAgreementTypesDTO agreementsDTO = new CustomerAgreementTypesDTO();
					for(Agreement getCustomerAgreement: response.getGetCustomerAgreementsResult().getCustomerAgreement()){
						agreementsDTO = new CustomerAgreementTypesDTO();
						agreementsDTO.setCustomerCode(customerKey);
						agreementsDTO.setIbsAdreementTypeId(getCustomerAgreement.getType().getId());
						listCustomerAgreementsDTOs.add(agreementsDTO);
					}
					
				}
			}
			return listCustomerAgreementsDTOs;
		}catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operacion getCustomerAgreements/IBSSPRMSupportAndReadinessBrokerImpl ==");
			throw manageServiceException(ex);
		} finally {
			log.debug("== Termina getCustomerAgreements/IBSSPRMSupportAndReadinessBrokerImpl ==");
		}
	}
	*/
	/**
	 * Método encargado del poblar el request de la operación 
	 * GetCustomerAgreements
	 * @param customerKey
	 * @param countryCode
	 * @return GetCustomerAgreementsRequest
	 * @throws BusinessException
	 * @author waguilera
	 */
	private GetCustomerAgreementsRequest generateGetCustomerAgreementsRequest(String customerKey, String countryCode) throws BusinessException{
		log.debug("== Termina generateGetCustomerAgreementsRequest/IBSSPRMSupportAndReadinessBrokerImpl ==");
		try{
			GetCustomerAgreementsRequest request = new GetCustomerAgreementsRequest();
			request.setRequestMetadata(new com.directvla.schema.util.commondatatypes.crmsupportandreadiness.v1_0.RequestMetadataType());
			request.getRequestMetadata().setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
			request.getRequestMetadata().setRequestID(countryCode);
			request.getRequestMetadata().setSourceID(countryCode);
			request.setGetCustomerAgreements(new GetCustomerAgreements());
			request.getGetCustomerAgreements().setCustomerKey(customerKey);
			return request;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operacion generateGetCustomerAgreementsRequest/IBSSPRMSupportAndReadinessBrokerImpl ==");
			throw manageServiceException(ex);
		} finally {
			log.debug("== Termina generateGetCustomerAgreementsRequest/IBSSPRMSupportAndReadinessBrokerImpl ==");
		}
	}
	
		public List<CustomerResponseByIdentificationResbDTO> getCustomersByIdentification(String customerKey, String sourceId) throws BusinessException{
		log.debug("=== Iniciando llamado a servicio web getDealerCodes ===");
        try {
        	
        	UtilsBusiness.assertNotNull(customerKey, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(sourceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	
    		GetCustomersByIdentificationRequest request = new GetCustomersByIdentificationRequest();
    		request.setRequestMetadata(new com.directvla.schema.util.commondatatypes.crmsupportandreadiness.v1_0.RequestMetadataType());
    		request.getRequestMetadata().setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
    		request.setGetCustomersByIdentification(new GetCustomersByIdentification());
    		request.getRequestMetadata().setRequestID(sourceId);
    		request.getRequestMetadata().setSourceID(sourceId);
    		request.getGetCustomersByIdentification().setCustomerIdentification(customerKey);
    		
        	CRMSupportAndReadinessPt cRMSupportAndReadinessPt = ServiceLocator.getInstance().getCRMSupportAndReadinessService();
        	GetCustomersByIdentificationResponse response = cRMSupportAndReadinessPt.getCustomersByIdentification(request);
        	List<CustomerResponseByIdentificationResbDTO> returnList = new ArrayList<CustomerResponseByIdentificationResbDTO>();
        	for(com.directvla.schema.businessdomain.customer.crmsupportandreadiness.v1_0.Customer cust : 
        		response.getGetCustomersByIdentificationResult().getCustomerList().getCustomer()){ 
        		returnList.add(new CustomerResponseByIdentificationResbDTO(cust.getID()));
        	}
        	return returnList;
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerCodes/IBSSPRMSupportAndReadinessBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.debug("== Termina getDealerCodes/IBSSPRMSupportAndReadinessBrokerImpl ==");
        }
	}
	

	public List<CustomersResourcesResbDTO> getCustomerResources(String customerKey, String sourceId) throws BusinessException{
		log.debug("=== Iniciando llamado a servicio web getCustomerResources ===");
        try {
        	
        	UtilsBusiness.assertNotNull(customerKey, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(sourceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	
        	GetCustomerResourcesRequest request = new GetCustomerResourcesRequest();
    		request.setRequestMetadata(new com.directvla.schema.util.commondatatypes.crmsupportandreadiness.v1_0.RequestMetadataType());
    		request.getRequestMetadata().setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
    		request.setGetCustomerResources(new GetCustomerResources());
    		request.getRequestMetadata().setRequestID(sourceId);
    		request.getRequestMetadata().setSourceID(sourceId);
    		request.getGetCustomerResources().setCustomerKey(customerKey);
    		
        	CRMSupportAndReadinessPt cRMSupportAndReadinessPt = ServiceLocator.getInstance().getCRMSupportAndReadinessService();
        	GetCustomerResourcesResponse response = cRMSupportAndReadinessPt.getCustomerResources(request);
        	List<CustomersResourcesResbDTO> returnList = new ArrayList<CustomersResourcesResbDTO>();
        	for(com.directvla.schema.businessdomain.product.crmsupportandreadiness.v1_0.Product prod : 
        		response.getGetCustomerResourcesResult().getResources().getProduct()){ 
        		for(com.directvla.schema.businessdomain.common.crmsupportandreadiness.v1_0.PhysicalResource pr:
        			prod.getProductHasPhysicalResources().getPhysicalResources()){
        			returnList.add(new CustomersResourcesResbDTO(pr.getSerialNumber(), pr.getStatus().getName(), pr.getDeviceModel().getName()));
        		}
        	}
        	
        	return returnList;
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getCustomerResources/IBSSPRMSupportAndReadinessBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.debug("== Termina getCustomerResources/IBSSPRMSupportAndReadinessBrokerImpl ==");
        }
	}
	
	
}
