package co.com.directv.sdii.ejb.business.broker.toa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.broker.impl.MarkWorkOrderServiceBrokerImpl;
import co.com.directv.sdii.model.dto.OrderHandlingServiceRequestDTO;

import com.directvla.schema.businessdomain.orderhandling.v1_0.GetShippingOrder;
import com.directvla.schema.businessdomain.orderhandling.v1_0.GetShippingOrderRequest;

public class OrderHandlingServiceTOA {
	
	private final static Logger log = UtilsBusiness.getLog4J(OrderHandlingServiceTOA.class);
	
	public static GetShippingOrderRequest buildGetShippingOrderRequest(OrderHandlingServiceRequestDTO params){
		GetShippingOrderRequest getShippingOrderRequestReturnValue = new GetShippingOrderRequest();
		getShippingOrderRequestReturnValue.setRequestMetadata(buildRequestMetadataType(params.getCountryCode()));
		getShippingOrderRequestReturnValue.setGetShippingOrder(new GetShippingOrder());
		getShippingOrderRequestReturnValue.getGetShippingOrder().setShippingOrderId(Integer.parseInt(params.getShippingOrderCode()));
		return getShippingOrderRequestReturnValue;
	}

	private static com.directvla.schema.util.commondatatypes.orderhandling.v1_0.RequestMetadataType buildRequestMetadataType(String countryCode) {
		com.directvla.schema.util.commondatatypes.orderhandling.v1_0.RequestMetadataType requestMetadataType = new com.directvla.schema.util.commondatatypes.orderhandling.v1_0.RequestMetadataType();
		requestMetadataType.setRequestID(countryCode);
		requestMetadataType.setSourceID(countryCode);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		}catch (Throwable ex) { 
	         ex.printStackTrace();
	         log.debug("== Error al tratar de ejecutar la operaciÃ³n buildRequestMetadataType/OrderHandlingServiceTOA ==");
	    }finally{
	         log.debug("== Termina la operaciÃ³n buildRequestMetadataType/OrderHandlingServiceTOA ==");
	    }
		return requestMetadataType;
	}
	
	public static void logOperationInvoke(String operation, OrderHandlingServiceRequestDTO request) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		buffer.append(logMetadata(request));
		
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
	
	private static String logMetadata(OrderHandlingServiceRequestDTO request) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		StringBuilder buffer = new StringBuilder();
		Method[] methods=request.getClass().getMethods();
		buffer.append(" Metadata [");
		for(int i=0; i<methods.length; ++i){
			String a;
			if(methods[i].getName().length()>3 && methods[i].getName().substring(0, 2).equalsIgnoreCase("get")){
				buffer.append(methods[i].getName().substring(3)+" : "+methods[i].invoke(request, null));
			}
		}
		buffer.append(" ]");
		return buffer.toString();
	}
	
}
