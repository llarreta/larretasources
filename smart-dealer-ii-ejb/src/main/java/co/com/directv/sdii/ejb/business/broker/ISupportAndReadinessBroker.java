package co.com.directv.sdii.ejb.business.broker;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;

/**
 * 
 * Interfaz que define las operaciones de comunicación con un sistema externo
 * para el consumo del servicio CRMSupportAndReadiness
 * 
 * Fecha de Creación: 8/07/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public interface ISupportAndReadinessBroker {

	public void upgradeDowngradeCustomerResource(WOAttentionElementsRequestDTO request) throws BusinessException;
}
