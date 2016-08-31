package co.com.directv.sdii.ejb.business.broker.impl;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.MarkWorkOrderServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.toa.MarkWorkOrderServiceTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeFault_Exception;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeRequest;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeResponse;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.HSPSupportAndReadinessPt;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeFault_Exception;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeRequest;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeResponse;
import com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeResponseType;

/**
 * Implementa la comunicación de servicios web para obtener la información de si requiere contrato
 * 
 * Fecha de Creación: 19/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="MarkWorkOrderServiceBrokerLocal",mappedName="ejb/MarkWorkOrderServiceBrokerLocal")
public class MarkWorkOrderServiceBrokerImpl extends IBSWSBase implements MarkWorkOrderServiceBrokerLocal, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(MarkWorkOrderServiceBrokerImpl.class);
	
	/**
	 * Metodo: Permite obtener el puerto de invocacion del servicio de customer
	 * @return CustomerPortType Puerto de invocacion para el WS de IBS de customer
	 * @throws ServiceLocatorException 
	 */
	private HSPSupportAndReadinessPt getMarkWorkOrderServicePort() throws ServiceLocatorException  {
		
		HSPSupportAndReadinessPt annexs = ServiceLocator.getInstance().getMarkWorkOrderService();
		return annexs;
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.MarkWorkOrderServiceBrokerLocal#requiredContractWorkOrder(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean requiredContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException{
		
		log.debug("== Inicia la operación requiredContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		try {
			HSPSupportAndReadinessPt annexs = getMarkWorkOrderServicePort();
			MarkWorkOrderServiceTOA.logOperationInvoke("requiredContractWorkOrder",request.getCountryCode(),request.getServiceIbsCode(),request.getCustomerCode());
			RequiredAnnexIBSCustomerCodeRequest requiredContractWorkOrderRequest = MarkWorkOrderServiceTOA.buildRequiredContractWorkOrderRequest(request);
			RequiredAnnexIBSCustomerCodeResponse result = annexs.requiredAnnexIBSCustomerCode(requiredContractWorkOrderRequest);
			RequiredAnnexIBSCustomerCodeResponseType response = result.getRequiredAnnexIBSCustomerCode();
			return response.isRequiredAnnex();
		} catch (Throwable e){
			log.error("Error al consultar la información básica del cliente", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.debug("== Termina la operación requiredContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		}
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.MarkWorkOrderServiceBrokerLocal#downLoadContractWorkOrder(java.lang.String, java.lang.String, java.lang.String)
	 */
	public FileResponseDTO downLoadContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException{
		
		log.debug("== Inicia la operación downLoadContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		try {
			
			HSPSupportAndReadinessPt annexs = getMarkWorkOrderServicePort();
			MarkWorkOrderServiceTOA.logOperationInvoke("downLoadContractWorkOrder",request.getCountryCode(),request.getServiceIbsCode(),request.getCustomerCode());
			GetAnnexIBSCustomerCodeRequest getAnnexIBSCustomerCodeRequest = MarkWorkOrderServiceTOA.buildGetDownLoadContractWorkOrderRequest(request);
			GetAnnexIBSCustomerCodeResponse r = annexs.getAnnexIBSCustomerCode(getAnnexIBSCustomerCodeRequest);
			FileResponseDTO response = MarkWorkOrderServiceTOA.buildFileResponseDTO(r.getGetAnnexIBSCustomerCode());
			return response;
			
		} catch (Throwable e){
			log.error("Error al consultar la información básica del cliente", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.debug("== Termina la operación downLoadContractWorkOrder/MarkWorkOrderServiceBrokerImpl ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.broker.IServiceBroker#manageServiceException(java.lang.Throwable)
	 */
	@Override
	public BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;
		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();

		if (e instanceof com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeFault_Exception) {
			errorMessage = getGetAnnexIBSCustomerCodeFault_Exception(((GetAnnexIBSCustomerCodeFault_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else if (e instanceof com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeFault_Exception) {
			errorMessage = getRequiredAnnexIBSCustomerCodeFault_Exception(((RequiredAnnexIBSCustomerCodeFault_Exception)e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, errorMessage);
		}else{
			be = super.manageException(e);
		}
		
		//be = super.manageException(e);
		return be;
	}
	
	private String getGetAnnexIBSCustomerCodeFault_Exception(
			com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeFault faultInfo,
			String errorMessage) {

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
	
	private String getRequiredAnnexIBSCustomerCodeFault_Exception(
			com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeFault faultInfo,
			String errorMessage) {

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

}
