package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;


/**
 * Interfaz que define las operaciones para interactuar
 * con el servicio externo del ESB ServiceProblemManagement
 * 
 * Fecha de Creación: 8/09/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface IServiceProblemManagementBroker {
	
	/**
	 * Metodo: : La operación ResourceChangeDamaged se usa para intercambiar, 
	 * en un cliente, un dispositivo por otro de la misma tecnología, 
	 * se hace cambiando tanto el IRD como la Smartcard, 
	 * además se envían los comandos necesarios. 
	 * Esta operación se usa cuando un dispositivo de un cliente 
	 * presenta una falla y debe ser reemplazado.
	 * @param attentionElements WOAttentionElementsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void resourceChangeDamaged(WOAttentionElementsRequestDTO attentionElements) throws BusinessException;

}
