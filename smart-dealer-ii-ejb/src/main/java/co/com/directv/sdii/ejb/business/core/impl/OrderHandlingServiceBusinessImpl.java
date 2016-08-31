package co.com.directv.sdii.ejb.business.core.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.OrderHandlingServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.core.OrderHandlingServiceBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.OrderHandlingServiceRequestDTO;

/**
 * Session Bean implementation class OrderHandlingServiceBusinessImpl
 */
@Stateless
public class OrderHandlingServiceBusinessImpl extends BusinessBase implements OrderHandlingServiceBusinessLocal {

	@EJB
	private OrderHandlingServiceBrokerLocal orderHandlingServiceBrokerLocal;
	
    /**
     * Default constructor. 
     */
    public OrderHandlingServiceBusinessImpl() {
    }

    public com.directvla.schema.businessdomain.common.orderhandling.v1_0.ShippingOrder getShippingOrder(OrderHandlingServiceRequestDTO request) throws BusinessException{
    	return orderHandlingServiceBrokerLocal.getShippingOrder(request);
    }
    
}
