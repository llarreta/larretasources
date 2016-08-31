package co.com.directv.sdii.ejb.business.stock;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.User;


/**
 * Interfaz que define los métodos para realizar la operaciones sobre la Entidad
 * MovCmdQueue
 * 
 * Session Bean implementation class MovementQueueBusinessBean
 * 
 * @author waguilera
 */

@Local
public interface MovementQueueHelperLocal {

	

	/**
	 * Operacion encargada de la creacion de un registro en la cola de la 
	 * entidad MovCmdQueue en una nueva transacción
	 * 
	 * @param dto
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void createMovCmdQueueRegisterByWorkOrderSuccess(MovCmdQueue movCmdQueue, User user)
		throws BusinessException;
	

	/**
	 * Operacion encargada de la creacion de un registro en la cola de la 
	 * entidad MovCmdQueue en una nueva transacción representando un error
	 * 
	 * @param dto
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void createMovCmdQueueRegisterByWorkOrderError(MovCmdQueue movCmdQueue, String message, User user)
		throws BusinessException;
	
	
	

}
