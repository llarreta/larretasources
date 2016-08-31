package co.com.directv.sdii.ws.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.MovementQueueFacadeBeanLocal;
import co.com.directv.sdii.model.dto.MovementInventoryDTO;
import co.com.directv.sdii.model.dto.collection.MovCmdQueueDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MovCmdStatusVO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;
import co.com.directv.sdii.ws.business.stock.IMovementQueueInventoryWS;

/**
 * Servicio web que expone las operaciones relacionadas con Warehouse
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal
 */
@MTOM(threshold=3072)
@WebService(serviceName="MovementQueueInventoryService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IMovementQueueInventoryWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="MovementQueueInventoryPort")
@Stateless()
public class MovementQueueInventoryWS implements IMovementQueueInventoryWS {
	
	@EJB
    private MovementQueueFacadeBeanLocal ejbRef;

	@Override
	public MovCmdQueueDTOResponse getMovementQueueHspToIbsByFilter(
			MovCmdQueueFilterDTO dtoFilter, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef.getMovementQueueHspToIbsByFilter(dtoFilter, requestCollInfo);
	}
	

	@Override
	public List<MovCmdStatusVO> getStatusByTypeMovement(String typeMovement)
			throws BusinessException {
		return ejbRef.getStatusByTypeMovement(typeMovement);
	}

	@Override
	public void setCommentForMovementQueue(
			Long movementQueueId, String comment, Long userId)
			throws BusinessException {
		this.ejbRef.setCommentForMovementQueue(movementQueueId, comment, userId);
	}


	@Override
	public void createQueueMovementInventoryByWorkOrder(
			MovementInventoryDTO request) throws BusinessException {
		this.ejbRef.createQueueMovementInventoryByWorkOrder(request);
		
	}
	
	
	
}
