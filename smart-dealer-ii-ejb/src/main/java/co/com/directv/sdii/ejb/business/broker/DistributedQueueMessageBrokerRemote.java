package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Remote;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.dto.MovementInventoryDTO;
import co.com.directv.sdii.model.dto.MovementInventoryListDTO;

/**
 * Define las operaciones del broker de servicios para la cola
 * 
 * Fecha de Creación: 14/02/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Remote
public interface DistributedQueueMessageBrokerRemote {

	/**
	 * Metodo encargado de enviar un mensaje a la cola de movimientos de inventario
	 * @param request datos necesarios del movimiento de inventarios de la work order.
	 * @throws BusinessException
	 */
	public void setQueueMovementInventory(MovementInventoryListDTO request) throws BusinessException;
	
	public void setQueueCalculateKpi(KpiCalculateDTO request) throws BusinessException;
	
}
