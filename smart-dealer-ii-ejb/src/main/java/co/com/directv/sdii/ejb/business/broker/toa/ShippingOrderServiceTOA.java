package co.com.directv.sdii.ejb.business.broker.toa;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * Fecha de Creación: 25/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public final class ShippingOrderServiceTOA {
	
	private ShippingOrderServiceTOA(){}
	
	private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderServiceTOA.class);
	
	public static void getShipOrderResponse(String serviceResponse) throws BusinessException, PropertiesException {
		String responseCode = StringUtils.substringBetween(serviceResponse, CodesBusinessEntityEnum.SHIP_ORDER_SER_RESP_CODE_INIT_TAG.getCodeEntity(), CodesBusinessEntityEnum.SHIP_ORDER_SER_RESP_CODE_END_TAG.getCodeEntity());
		if( responseCode == null || !responseCode.equals( CodesBusinessEntityEnum.SHIP_ORDER_SER_SUCCESS_CODE.getCodeEntity() ) ){
			String serviceMsj = StringUtils.substringBetween(serviceResponse, CodesBusinessEntityEnum.SHIP_ORDER_SER_RESP_MSJ_INIT_TAG.getCodeEntity(), CodesBusinessEntityEnum.SHIP_ORDER_SER_RESP_MSJ_END_TAG.getCodeEntity());
			Object params[] = {serviceMsj};
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(serviceMsj);
			throw new BusinessException(ErrorBusinessMessages.CORE_CR055.getCode(), ErrorBusinessMessages.CORE_CR055.getMessage(params),paramsList);
		}
		
	}

}
