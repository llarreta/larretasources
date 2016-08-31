package co.com.directv.sdii.ws.business.core.wo.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.WorkOrderCancelFacadeLocal;
import co.com.directv.sdii.model.dto.WorkOrderCanceledDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.ws.business.core.wo.IWorkOrderCancelWS;

@MTOM(threshold=3072)
@WebService(serviceName="WorkOrderCancelWS",
		endpointInterface="co.com.directv.sdii.ws.business.core.wo.IWorkOrderCancelWS",
		targetNamespace="http://core.business.ws.sdii.directv.com.co/",
		portName="WorkOrderCancelPort")	
@Stateless()
public class WorkOrderCancelWS implements IWorkOrderCancelWS {

	@EJB
	private WorkOrderCancelFacadeLocal workOrderCancelFacadeLocal;
	
	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @param requestInfo parametros de paginacion de la consulta
	 * @return resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public WorkOrderCanceledResponse getCanceledWorkOrders(
			WorkOrderCanceledFilterDTO filter, RequestCollectionInfo requestInfo)
			throws BusinessException {
		return workOrderCancelFacadeLocal.getCanceledWorkOrders(filter, requestInfo);
	}

	/**
	 * Metodo que realiza la consulta de cuantas work order se cancelaron en los ultimos dias, para un pais especifico, dado el numero de dias, el pais y la fecha actual
	 * @param countryId Id del pais
	 * @return numero de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public Long getCanceledWorkOrdersCount(Long countryId, Long userId)
			throws BusinessException {
		return workOrderCancelFacadeLocal.getCanceledWorkOrdersCount(countryId, userId);
	}

	@Override
	public void manageCanceledWorkOrder(Long workOrderId, Long userId)
			throws BusinessException {
		workOrderCancelFacadeLocal.manageCanceledWorkOrder(workOrderId,userId);
	}

	
}
