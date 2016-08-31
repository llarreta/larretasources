package co.com.directv.sdii.ejb.business.core;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.OrderHandlingServiceRequestDTO;

@Local
public interface OrderHandlingServiceBusinessLocal {

	public com.directvla.schema.businessdomain.common.orderhandling.v1_0.ShippingOrder getShippingOrder(OrderHandlingServiceRequestDTO request) throws BusinessException;
	
}
