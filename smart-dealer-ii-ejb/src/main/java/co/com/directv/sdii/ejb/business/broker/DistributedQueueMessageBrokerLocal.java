package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.dto.MovementInventoryListDTO;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkOrder;

/**
 * Define las operaciones del broker de servicios para la cola
 * 
 * Fecha de Creación: 14/02/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface DistributedQueueMessageBrokerLocal {

	/**
	 * Metodo encargado de enviar un mensaje a la cola de movimientos de inventario
	 * @param request datos necesarios del movimiento de inventarios de la work order.
	 * @throws BusinessException
	 */
	public void setQueueMovementInventory(MovementInventoryListDTO request) throws BusinessException;
	
	public void setQueueCalculateKpi(KpiCalculateDTO request) throws BusinessException;
	
	public void sendESBDispatchWorkOrderMessages(String systemToNotify, User user, Employee employee, List<WorkOrder> woList) throws BusinessException;
	
	public void sendESBWithdrawWorkOrderMessages(String systemToNotify, User user, Employee employee, List<WorkOrder> woList) throws BusinessException;
	
}
