/**
 * 
 */
package co.com.directv.sdii.facade.stock.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.ejb.business.stock.MovementQueueBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.MovementQueueFacadeBeanLocal;
import co.com.directv.sdii.model.dto.MovementInventoryDTO;
import co.com.directv.sdii.model.dto.MovementInventoryListDTO;
import co.com.directv.sdii.model.dto.collection.MovCmdQueueDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MovCmdStatusVO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

/**
 * Clase que implementa los métodos que permiten informar el estado de los elementos
 * y los movimientos que se realizan entre bodegas de SmartDealerII a IBS.
 * 
 * @author Jimmy Vélez Muñoz
 *
 */
@Stateless(name="MovementQueueFacadeBeanLocal")
@Local({MovementQueueFacadeBeanLocal.class})
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MovementQueueFacadeBean implements MovementQueueFacadeBeanLocal {

	@EJB(name="MovementQueueBusinessBeanLocal", beanInterface=MovementQueueBusinessBeanLocal.class)
	private MovementQueueBusinessBeanLocal businessMovementQueue;
	
	@Override
	public MovCmdQueueDTOResponse getMovementQueueHspToIbsByFilter(
			MovCmdQueueFilterDTO dto, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessMovementQueue.getMovementQueueHspToIbsByFilter(dto, requestCollInfo, true);
	}
	
	

	@Override
	public List<MovCmdStatusVO> getStatusByTypeMovement(String typeMovement)
			throws BusinessException {
		return businessMovementQueue.getStatusByTypeMovement(typeMovement);
	}

	@Override
	public void setCommentForMovementQueue(
			Long movementQueueId, String comment, Long userId)
			throws BusinessException {
		this.businessMovementQueue.setCommentForMovementQueue(movementQueueId, comment, userId);
	}


	/**
	 * Metodo encargado de almacenar el registro de los movimientos pendientes a partir de la Wo descargadas
	 * @param movCmdQueueVO
	 * @throws BusinessException
	 * @author waguilera
	 */
	@Override
	public void createQueueMovementInventoryByWorkOrder(
			MovementInventoryDTO request) throws BusinessException {
		MovementInventoryListDTO movementInventoryListDTO = new MovementInventoryListDTO();
		movementInventoryListDTO.setMovementInventoryDTOList(new ArrayList<MovementInventoryDTO>());
		movementInventoryListDTO.getMovementInventoryDTOList().add(request);
		if(request!=null){
			movementInventoryListDTO.setCustomerId(request.getCustomerId());
			movementInventoryListDTO.setUserId(request.getUserId());
		}
		this.businessMovementQueue.createQueueMovementInventoryByWorkOrder(movementInventoryListDTO);
		
	}



}
