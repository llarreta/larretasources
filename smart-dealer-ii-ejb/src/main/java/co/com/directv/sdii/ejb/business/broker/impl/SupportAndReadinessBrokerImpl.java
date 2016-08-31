package co.com.directv.sdii.ejb.business.broker.impl;

import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.ejb.business.broker.ISupportAndReadinessBroker;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;

/**
 * 
 * Implementa las operaciones de comunicación con un sistema externo
 * para el consumo del servicio CRMSupportAndReadiness
 * 
 * Fecha de Creación: 8/07/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.ISupportAndReadinessBroker
 */
public class SupportAndReadinessBrokerImpl implements ISupportAndReadinessBroker, IServiceBroker {

	@Override
	public void upgradeDowngradeCustomerResource(WOAttentionElementsRequestDTO request) throws BusinessException {
		
	}

	@Override
	public BusinessException manageServiceException(Throwable e) {
		return null;
	}

}
