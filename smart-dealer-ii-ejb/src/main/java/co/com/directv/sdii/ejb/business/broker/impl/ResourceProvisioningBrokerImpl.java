package co.com.directv.sdii.ejb.business.broker.impl;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.ResourceProvisioningBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.toa.ResourceProvisioningServiceTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;

import com.directvla.contract.businessdomain.resourceprovisioning.v1_0.ResourceProvisioningPt;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.UpdateResourceStatusException;
import com.directvla.schema.businessdomain.resourceprovisioning.v1_0.UpdateResourceStatusRequest;

/**
 * Este EJB es el encargado del consumo de las operaciones del servicio ResourceProvisioning de RESB
 * implementa la interface Local ResourceProvisioningBrokerLocal y es sin estado.
 * @author Aharker
 *
 */
@Stateless(name="ResourceProvisioningBroker",
		mappedName = "ejb/ResourceProvisioningBroker")
public class ResourceProvisioningBrokerImpl extends IBSWSBase implements ResourceProvisioningBrokerLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ResourceProvisioningServiceBrokerImpl.class);
	
    /**
     * Default constructor. 
     */
    public ResourceProvisioningBrokerImpl() {
    }
    
    
	@Override
	public boolean updateResourceStatus(MovCmdQueueVO movCmdQueueVO) throws BusinessException {
		log.info("== Inicia updateResourceStatus/ResourceProvisioningServiceBrokerImpl ==");
		try{
			ResourceProvisioningPt resourceProvisioning = null;
			resourceProvisioning = ServiceLocator.getInstance().getResourceProvisioningService();
			UpdateResourceStatusRequest request=ResourceProvisioningServiceTOA.buildUpdateResourceStatus(movCmdQueueVO);
			resourceProvisioning.updateResourceStatus(request);
			return true;
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operaci�n updateResourceStatus/ResourceProvisioningServiceBrokerImpl ==");
        	throw this.manageServiceException(ex);
        } finally {
            log.info("== Termina updateResourceStatus/ResourceProvisioningServiceBrokerImpl ==");
        }
	}
	
	/***
	 * El metodo se encarga de tomar cualquier excepcion y encapsularla en una excepcion de HSP+
	 * @param e La excepcion de forma generica que se desea tratar
	 * @return La excepcion encapsulada en una excepcion de negocio de HSP+
	 * @author Aharker
	 */
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();
		
		if (e instanceof com.directvla.contract.businessdomain.resourceprovisioning.v1_0.UpdateResourceStatusException) {
			errorMessage = getUpdateResourceStatus_ExceptionMessage(((com.directvla.contract.businessdomain.resourceprovisioning.v1_0.UpdateResourceStatusException)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else{
			be = super.manageException(e);
		}
		return be;
	}

	/***
	 * El metodo esta dirigido a extraer el mensaje de error de las excepciones generadas por la operacion UpdateResourceStatus de RESB.
	 * @param faultInfo Informacion de la falla de la operacion UpdateResourceStatus de ESB, esta informacion basicamente contiene en sus atributos todas
	 *  		todas las posibles excepciones del servicio UpdateResourceStatus
	 * @param errorMessage mensaje de error por defecto que se retornara en caso de no poder clasificar la excepcion
	 * @return mensaje de la excepcion real generada por el servicio
	 * @author Aharker
	 */
	private String getUpdateResourceStatus_ExceptionMessage(
			UpdateResourceStatusException faultInfo, String errorMessage) {
		if(faultInfo.getEntityAlreadyExistsException()!=null){
			return faultInfo.getEntityAlreadyExistsException().getMessage();
		}else if(faultInfo.getEntityNotFoundException()!=null){
			return faultInfo.getEntityNotFoundException().getMessage();
		}else if(faultInfo.getEntityInUseException()!=null){
			return faultInfo.getEntityInUseException().getMessage();
		}else if(faultInfo.getInternalErrorException()!=null){
			return faultInfo.getInternalErrorException().getMessage();
		}else if(faultInfo.getMissingParameterException()!=null){
			return faultInfo.getMissingParameterException().getMessage();
		}else if(faultInfo.getInvalidParameterValueException()!=null){
			return faultInfo.getInvalidParameterValueException().getMessage();
		}else if(faultInfo.getInvalidQueryParameterException()!=null){
			return faultInfo.getInvalidQueryParameterException().getMessage();
		}else if(faultInfo.getInvalidRequestException()!=null){
			return faultInfo.getInvalidRequestException().getMessage();
		}else if(faultInfo.getInvalidResponseException()!=null){
			return faultInfo.getInvalidResponseException().getMessage();
		}else if(faultInfo.getInvalidSecurityTokenException()!=null){
			return faultInfo.getInvalidSecurityTokenException().getMessage();
		}else if(faultInfo.getAccessDeniedException()!=null){
			return faultInfo.getAccessDeniedException().getMessage();
		}else if(faultInfo.getServiceUnavailableException()!=null){
			return faultInfo.getServiceUnavailableException().getMessage();
		}else if(faultInfo.getOperationNotYetImplementedException()!=null){
			return faultInfo.getOperationNotYetImplementedException().getMessage();
		}
		return errorMessage;
	}

}
