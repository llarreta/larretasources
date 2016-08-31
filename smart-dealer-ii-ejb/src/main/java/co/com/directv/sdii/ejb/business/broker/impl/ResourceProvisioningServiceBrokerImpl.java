package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.AddIRDChangesException;
import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.GetSwopsException;
import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.ResourceProvisioningPt;
import com.directvla.contract.crm.inventory.v1_0.PtInventory;
import com.directvla.contract.crm.inventory.v1_0.UpdateResourceStatusRequest;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.AddIRDChangesRequest;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.AddIRDChangesResponse;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetSwopsResponse;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetUpgradeDowngradeResponse;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.ProductType;
import com.directvla.schema.util.commondatatypes.inventory.v1_0.RequestMetadataType;


import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.broker.IResourceProvisioningServiceBroker;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.toa.ResourceProvisioningServiceTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.IBSSerElemMovementDTO;
import co.com.directv.sdii.model.dto.SwopsDTORequest;
import co.com.directv.sdii.model.dto.SwopsDTOResponse;
import co.com.directv.sdii.model.dto.UpgradeAndDowngradeDTORequest;
import co.com.directv.sdii.model.dto.UpgradeAndDowngradeDTOResponse;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.pojo.SystemParameter;

import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.AddIRDChangesException;
import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.GetSwopsException;
import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.ResourceProvisioningPt;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.AddIRDChangesRequest;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.AddIRDChangesResponse;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetSwopsResponse;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetUpgradeDowngradeResponse;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.ProductType;




/**
 * 
 * Desacopla la invocación de servicio web externo de ResourceProvisioningService
 * para convertir la respuesta en objetos locales  
 * 
 * Fecha de Creación: 30/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ResourceProvisioningServiceBrokerImpl implements IResourceProvisioningServiceBroker, IServiceBroker {

	private static ResourceProvisioningServiceBrokerImpl broker;
	private final static Logger log = UtilsBusiness.getLog4J(ResourceProvisioningServiceBrokerImpl.class);
	
	/**
	 * 
	 * Metodo: retorna una instacia de ResourceProvisioningServiceBrokerImpl, patron singleton.
	 * @return ContactCoreBrokerImpl
	 * @throws ServiceLocatorException
	 * @author jalopez
	 */
	public static ResourceProvisioningServiceBrokerImpl getInstance() throws ServiceLocatorException{
		try{
			if( broker == null ){
				broker = new ResourceProvisioningServiceBrokerImpl();
			}
			return broker;
		}catch (Exception e) {
			throw new ServiceLocatorException(ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getCode(),ErrorBusinessMessages.SERVICE_LOCATOR_NOT_INSTANCE.getMessage(),e);
		}
	}
	
	@Override
	public void addIRDChanges(WOAttentionElementsRequestDTO woAttentionDTO, SystemParameter reasonNew, SystemParameter reasonOld) throws BusinessException {
		log.info("== Inicia addIRDChanges/ResourceProvisioningServiceBrokerImpl ==");
		try {
			ResourceProvisioningPt resourceProvisioning = null;
			AddIRDChangesResponse response = null; 
			resourceProvisioning = ServiceLocator.getInstance().getResourceProvisioningService();
			AddIRDChangesRequest request = ResourceProvisioningServiceTOA.populateAddIRDChangesRequest(woAttentionDTO, reasonNew, reasonOld);
			
			response = resourceProvisioning.addIRDChanges(request);			
			log.info("== Resultado de la invocacion de la Operacion addIRDChanges RequestID: "+response.getResponseMetadata().getRequestID()+" ==");
		} catch(Throwable ex){
        	log.fatal("== Error al tratar de ejecutar la operación addIRDChanges/ResourceProvisioningServiceBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.info("== Termina addIRDChanges/ResourceProvisioningServiceBrokerImpl ==");
        }
	}

	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		if(e instanceof AddIRDChangesException){
			errorMessage = getAddIRDChangesExceptionMessage(((AddIRDChangesException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if( e instanceof GetSwopsException ){
			errorMessage = getGetSwopsExceptionMessage(((GetSwopsException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		} else if( e instanceof BusinessException ){		
			be = new BusinessException(((BusinessException) e).getMessageCode(), e.getMessage(), ((BusinessException) e).getParameters());
		}else{
			be = new BusinessException(errorCode, errorMessage);
		}
		
		return be;
	}
	
	private String getGetSwopsExceptionMessage(com.directvla.schema.businessdomain.resourceprovisioning.v1_0.GetSwopsException faultInfo , String defaultMessage){
		if( faultInfo.getAccessDeniedException() != null ){
			return faultInfo.getAccessDeniedException().getMessage();
		} else if( faultInfo.getEntityAlreadyExistsException() != null ){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		} else if( faultInfo.getEntityInUseException() != null ){
			return faultInfo.getEntityInUseException().getMessage();
		} else if( faultInfo.getEntityNotFoundException() != null ){
			return faultInfo.getEntityNotFoundException().getMessage();
		} else if( faultInfo.getInternalErrorException() != null ){
			return faultInfo.getInternalErrorException().getMessage();
		} else if( faultInfo.getInvalidParameterValueException() != null ){
			return faultInfo.getInvalidParameterValueException().getMessage();
		} else if( faultInfo.getInvalidQueryParameterException() != null ){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		} else if( faultInfo.getInvalidRequestException() != null ){
			return faultInfo.getInvalidRequestException().getMessage();
		} else if( faultInfo.getInvalidResponseException() != null ){
			return faultInfo.getInvalidResponseException().getMessage();
		} else if( faultInfo.getInvalidSecurityTokenException() != null ){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		} else if( faultInfo.getMissingParameterException() != null ){
			return faultInfo.getMissingParameterException().getMessage();
		} else if( faultInfo.getOperationNotYetImplementedException() != null ){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		} else if( faultInfo.getServiceUnavailableException() != null ){
			return faultInfo.getServiceUnavailableException().getMessage();
		} 
		
		return defaultMessage;
	}
	
	/**
	 * 
	 * Metodo: Encapsula el error generado
	 * por IBS
	 * @param faultInfo
	 * @param defaultMessage
	 * @return String
	 * @author jalopez
	 */
	private String getAddIRDChangesExceptionMessage(com.directvla.schema.businessdomain.resourceprovisioning.v1_0.AddIRDChangesException faultInfo, String defaultMessage) {
		
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

	@Override
	public List<SwopsDTOResponse> getSwops(SwopsDTORequest swopsDTO) throws BusinessException {
		log.info("== Inicia getSwops/ResourceProvisioningServiceBrokerImpl ==");
		try {
			List<SwopsDTOResponse> businessResponse = new ArrayList<SwopsDTOResponse>();
			ResourceProvisioningPt resourceProvisioning = null;
			resourceProvisioning = ServiceLocator.getInstance().getResourceProvisioningService();
			GetSwopsResponse response = resourceProvisioning.getSwops( ResourceProvisioningServiceTOA.populateGetSwopsRequest(swopsDTO) );
			if(  response.getListOfProducts() != null 
					&& response.getListOfProducts().getProduct() != null 
					&& !response.getListOfProducts().getProduct().isEmpty() ){
				for( ProductType object : response.getListOfProducts().getProduct() ){
					SwopsDTOResponse swopsDTOResponse = new SwopsDTOResponse();
					swopsDTOResponse.setCountryCode( swopsDTO.getCountryCode() );
					swopsDTOResponse.setCustomerCode( swopsDTO.getCustomerCode() );
					
					if( object.getCustomerHistoryEvent() != null ){
						if( object.getCustomerHistoryEvent().getEvent() != null ){
							swopsDTOResponse.setEventId( object.getCustomerHistoryEvent().getEvent().getId() );
						}
						if( object.getCustomerHistoryEvent().getReason() != null ){
							swopsDTOResponse.setReasonId( object.getCustomerHistoryEvent().getReason().getId() );							
						}
						swopsDTOResponse.setSwapHistoryEvent( object.getCustomerHistoryEvent().getID() );
					}
					if( object.getPhysicalResource() != null ){
						if( object.getPhysicalResource().getStockHandler() != null ){
							swopsDTOResponse.setDealerCode( object.getPhysicalResource().getStockHandler().getPartyRoleId() );
						}
						swopsDTOResponse.setSerialCode( object.getPhysicalResource().getProductID() );
					}
					businessResponse.add( swopsDTOResponse );
				}
			}
			return businessResponse;
		} catch(Throwable ex){
        	log.fatal("== Error al tratar de ejecutar la operación getSwops/ResourceProvisioningServiceBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.info("== Termina getSwops/ResourceProvisioningServiceBrokerImpl ==");
        }		
	}
	

	public List<UpgradeAndDowngradeDTOResponse> getUpgradeDowngrade(UpgradeAndDowngradeDTORequest upgradeAndDowngradeDTORequest) throws BusinessException {
		log.info("== Inicia getUpgradeDowngrade/ResourceProvisioningServiceBrokerImpl ==");
		try {
			List<UpgradeAndDowngradeDTOResponse> businessResponse = new ArrayList<UpgradeAndDowngradeDTOResponse>();
			ResourceProvisioningPt resourceProvisioning = null;
			resourceProvisioning = ServiceLocator.getInstance().getResourceProvisioningService();
			GetUpgradeDowngradeResponse response = resourceProvisioning.getUpgradeDowngrade( ResourceProvisioningServiceTOA.populateGetUpgradeAndDowngradeRequest(upgradeAndDowngradeDTORequest) );
			if(  response.getListOfProducts() != null 
					&& response.getListOfProducts().getProduct() != null 
					&& !response.getListOfProducts().getProduct().isEmpty() ){
				for( ProductType object : response.getListOfProducts().getProduct() ){
					UpgradeAndDowngradeDTOResponse upgradeAndDowngradeDTOResponse = new UpgradeAndDowngradeDTOResponse();
					upgradeAndDowngradeDTOResponse.setCountryCode( upgradeAndDowngradeDTORequest.getCountryCode() );
					upgradeAndDowngradeDTOResponse.setCustomerCode( upgradeAndDowngradeDTORequest.getCustomerCode() );
					
					if( object.getCustomerHistoryEvent() != null ){
						if( object.getCustomerHistoryEvent().getEvent() != null ){
							upgradeAndDowngradeDTOResponse.setEventId( object.getCustomerHistoryEvent().getEvent().getId() );
						}
						if( object.getCustomerHistoryEvent().getReason() != null ){
							upgradeAndDowngradeDTOResponse.setReasonId( object.getCustomerHistoryEvent().getReason().getId() );							
						}
						upgradeAndDowngradeDTOResponse.setSwapHistoryEvent( object.getCustomerHistoryEvent().getID() );
					}
					if( object.getPhysicalResource() != null ){
						if( object.getPhysicalResource().getStockHandler() != null ){
							upgradeAndDowngradeDTOResponse.setDealerCode( object.getPhysicalResource().getStockHandler().getPartyRoleId() );
						}
						upgradeAndDowngradeDTOResponse.setSerialCode( object.getPhysicalResource().getProductID() );
					}
					businessResponse.add( upgradeAndDowngradeDTOResponse );
				}
			}
			return businessResponse;
		} catch(Throwable ex){
        	log.fatal("== Error al tratar de ejecutar la operación getUpgradeDowngrade/ResourceProvisioningServiceBrokerImpl ==");
        	throw manageServiceException(ex);
        } finally {
            log.info("== Termina getUpgradeDowngrade/ResourceProvisioningServiceBrokerImpl ==");
        }		
	}
	
}
