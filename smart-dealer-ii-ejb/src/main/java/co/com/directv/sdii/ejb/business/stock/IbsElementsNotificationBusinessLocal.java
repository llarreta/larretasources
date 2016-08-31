package co.com.directv.sdii.ejb.business.stock;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;

/**
 * 
 * Encapsula la lógica de negocio encargada de notificar a IBS los movimientos de elementos
 * serializados en una transaccion independiente 
 * 
 * Fecha de Creación: Oct 21, 2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface IbsElementsNotificationBusinessLocal {

	/**
	 * 
	 * Metodo: realiza la lectura de los elementos en la cola para notificar a IBS el 
	 * cambio de los estados
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void sendStatusCmdToIBS(Long countryId) throws BusinessException;

	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * Metodo: realiza la lectura de los elementos en la tabla MOVEMENT_COMAND_QUEUES para encolar en MovCmdQueue
	 * los movimientos de inventario a procesar en paralelo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author ialessan
	 */
	public void sendToMovCmdQueue(Long countryId) throws BusinessException;		
	
	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * Metodo: 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author ialessan
	 */	
	public void callServiceInventoryParallel(MovCmdQueueVO record, User user) throws BusinessException;
	
}
