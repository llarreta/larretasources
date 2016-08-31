/**
 * 
 */
package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.IBSMoveResourceBetweenCustAndDealerDTO;
import co.com.directv.sdii.model.dto.IBSSerElemMovementDTO;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;


/**
 * Interface que define las operaciones de los web services
 * que exponen los servicios de Inventarios de IBS.
 * 
 * Fecha de Creación: 11/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.impl.InventoryServiceBrokerImpl
 */
@Local
public interface InventoryServiceBrokerLocal {
	
	
	/**
	 * Metodo: Reporta a ibs el movimiento de elementos serializados
	 * @param MovCmdQueueVO record objeto que encapsula la información de un objeto a ser enviado a ibs
	 * @throws BusinessException En caso de error al reportar el movimiento de elemento serializado
	 * @author jnova
	 */
	public boolean moveResourceToStockHandler(MovCmdQueueVO record) throws BusinessException;
	
	/**
	 * Metodo: Actualiza el estado de un elemento serializado a IBS
	 * @param MovCmdQueueVO record objeto que encapsula la información de un objeto a ser enviado a ibs
	 * @return True si se realiza la invocación exitosamente
	 * @throws BusinessException En caso de error al reportar el movimiento de elemento serializado
	 */
	public boolean updateResourceStatus(MovCmdQueueVO record) throws BusinessException; 
}
