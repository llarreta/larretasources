package co.com.directv.sdii.ws.business.stock;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MovementInventoryDTO;
import co.com.directv.sdii.model.dto.collection.MovCmdQueueDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MovCmdStatusVO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

/**
 * Servicio web que expone las operaciones relacionadas con Warehouse
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="MovementQueueInventoryWS",targetNamespace="http://stock.business.ws.sdii.directv.com.co/")
public interface IMovementQueueInventoryWS {
	
	
	/**
	 * Método: operación encargada de la consulta del resultados de los
	 * movimiento del inventario
	 * 
	 * @param dto
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName="getMovementQueueHspToIbsByFilter", action="getMovementQueueHspToIbsByFilter")
	public MovCmdQueueDTOResponse getMovementQueueHspToIbsByFilter(
			@WebParam(name="dtoFilter")MovCmdQueueFilterDTO dtoFilter, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	@WebMethod(operationName="getStatusByTypeMovement", action="getStatusByTypeMovement")
	public List<MovCmdStatusVO> getStatusByTypeMovement(
			@WebParam(name="typeMovement")String typeMovement) throws BusinessException;
	
	/**
	 * Metodo encargado de marcar un registro MovCmdQueue y marcarlo como comentado
	 * @param movementQueueId
	 * @param comment
	 * @param userId
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName="setCommentForMovementQueue", action="setCommentForMovementQueue")
	public void setCommentForMovementQueue(
			@WebParam(name="movementQueueId")Long movementQueueId,
			@WebParam(name="comment")String comment,
			@WebParam(name="userId")Long userId) throws BusinessException;
	
	/**
	 * 
	 * @param request
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createQueueMovementInventoryByWorkOrder", action="createQueueMovementInventoryByWorkOrder")
	public void createQueueMovementInventoryByWorkOrder(@WebParam(name="request")MovementInventoryDTO request) throws BusinessException;
	
}
