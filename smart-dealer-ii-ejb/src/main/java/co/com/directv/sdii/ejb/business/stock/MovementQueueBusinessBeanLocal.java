package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MovementInventoryDTO;
import co.com.directv.sdii.model.dto.MovementInventoryListDTO;
import co.com.directv.sdii.model.dto.collection.MovCmdQueueDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MovCmdStatusVO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;


/**
 * Interfaz que define los métodos para realizar la operaciones sobre la Entidad
 * MovCmdQueue
 * 
 * Session Bean implementation class MovementQueueBusinessBean
 * 
 * @author waguilera
 */

@Local
public interface MovementQueueBusinessBeanLocal {

	

	/**
	 * Método: operación encargada de la consulta del resultados de los
	 * movimiento del inventario
	 * 
	 * @param dto
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
		public MovCmdQueueDTOResponse getMovementQueueHspToIbsByFilter(MovCmdQueueFilterDTO dto, RequestCollectionInfo requestCollInfo, boolean isFromFrontal)
			throws BusinessException ;
	
	
	
	
	
	
	/**
	 * Metodo encargado de consultar lo estados con respecto al tipo de movimiento
	 * @param typeMovement
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public List<MovCmdStatusVO> getStatusByTypeMovement(String typeMovement) throws BusinessException;

	/**
	 * Metodo encargado de almacenar el registro de los movimientos pendientes a partir de la Wo descargadas
	 * @param movCmdQueueVO
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void createQueueMovementInventoryByWorkOrder(MovementInventoryListDTO request) throws BusinessException;
	
	/**
	 * Metodo encargado de marcar un registro MovCmdQueue y marcarlo como comentado
	 * @param movementQueueId
	 * @param comment
	 * @param userId
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void setCommentForMovementQueue(
			Long movementQueueId, String comment, Long userId)
			throws BusinessException;

}
