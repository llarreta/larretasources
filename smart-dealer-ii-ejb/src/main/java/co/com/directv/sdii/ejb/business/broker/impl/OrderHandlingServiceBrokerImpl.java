package co.com.directv.sdii.ejb.business.broker.impl;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.OrderHandlingServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.toa.OrderHandlingServiceTOA;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.dto.OrderHandlingServiceRequestDTO;

import com.directvla.contract.businessdomain.orderhandling.v1_0.OrderHandlingPt;
import com.directvla.schema.businessdomain.orderhandling.v1_0.GetShippingOrderException;
import com.directvla.schema.businessdomain.orderhandling.v1_0.GetShippingOrderRequest;
import com.directvla.schema.businessdomain.orderhandling.v1_0.GetShippingOrderResponse;
import com.directvla.schema.businessdomain.orderhandling.v1_0.GetShippingOrderResult;

/**
 * Session Bean implementation class OrderHandlingServiceBrokerImpl
 */
@Stateless
public class OrderHandlingServiceBrokerImpl extends IBSWSBase implements OrderHandlingServiceBrokerLocal {

	private final static Logger log = UtilsBusiness.getLog4J(OrderHandlingServiceBrokerImpl.class);
	
    /**
     * Default constructor. 
     */
    public OrderHandlingServiceBrokerImpl() {
    }

	public com.directvla.schema.businessdomain.common.orderhandling.v1_0.ShippingOrder getShippingOrder(OrderHandlingServiceRequestDTO request) throws BusinessException{
		
		log.info("== Inicia la operación GetShippingOrder/OrderHandlingServiceBrokerImpl ==");
		try {
			OrderHandlingPt orderHandlingPt = getOrderHandlingServicePort();
			
			OrderHandlingServiceTOA.logOperationInvoke("GetShippingOrder",request);
			GetShippingOrderRequest getShippingOrderRequest = OrderHandlingServiceTOA.buildGetShippingOrderRequest(request);
			GetShippingOrderResponse response = orderHandlingPt.getShippingOrder(getShippingOrderRequest);
			GetShippingOrderResult getShippingOrderResult = response.getGetShippingOrderResult();
			com.directvla.schema.businessdomain.common.orderhandling.v1_0.ShippingOrder ibsShippingOrder = getShippingOrderResult.getShippingOrder();
			return ibsShippingOrder;
		} catch (Throwable e){
			log.error("Error al invocar la operacion GetShippingOrder/OrderHandlingServiceBrokerImpl ", e);
			e.printStackTrace();
			throw manageServiceException(e);
		} finally {
			log.info("== Termina la operación GetShippingOrder/OrderHandlingServiceBrokerImpl ==");
		}
		
	}
    
	private BusinessException manageServiceException(Throwable e) {
		BusinessException be = null;

		String errorCode = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getCode();
		String errorMessage = ErrorBusinessMessages.WEB_SERVICE_EXCEPTION.getMessage();

		if (e instanceof com.directvla.contract.businessdomain.orderhandling.v1_0.GetShippingOrderException) {
			errorMessage = getGetShippingOrderException_ExceptionMessage(((com.directvla.contract.businessdomain.orderhandling.v1_0
					.GetShippingOrderException) e).getFaultInfo(), errorMessage);
			be = new BusinessException(errorCode, e.getMessage());
		} else {
			be = super.manageException(e);
		}
		return be;
	}

	private String getGetShippingOrderException_ExceptionMessage(
			GetShippingOrderException faultInfo, String errorMessage) {
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

	private OrderHandlingPt getOrderHandlingServicePort() throws ServiceLocatorException  {
		
		OrderHandlingPt orderHandlingService = ServiceLocator.getInstance().getOrderHandlingService();
		return orderHandlingService;
		
	}
	
}
