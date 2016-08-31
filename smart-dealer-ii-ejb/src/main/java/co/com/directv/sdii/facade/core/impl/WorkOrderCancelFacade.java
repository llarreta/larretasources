package co.com.directv.sdii.facade.core.impl;

import java.util.List;

import co.com.directv.sdii.ejb.business.core.WorkOrderCancelBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.WorkOrderCancelFacadeLocal;
import co.com.directv.sdii.model.dto.WorkOrderCanceledDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class WorkOrderCancelFacade
 */
@Stateless
public class WorkOrderCancelFacade implements WorkOrderCancelFacadeLocal {

	@EJB
	private WorkOrderCancelBusinessLocal workOrderCancelBusinessLocal;
	
    /**
     * Default constructor. 
     */
    public WorkOrderCancelFacade() {

    }

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
		return workOrderCancelBusinessLocal.getCanceledWorkOrders(filter, requestInfo);
	}

	/**
	 * Metodo que realiza la consulta de cuantas work order se cancelaron en los ultimos dias, para un pais especifico, dado el numero de dias, el pais y la fecha actual
	 * @param countryId Id del pais
	 * @return numero de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public Long getCanceledWorkOrdersCount(Long countryId, Long userId) throws BusinessException {
		return workOrderCancelBusinessLocal.getCanceledWorkOrdersCount(countryId, userId);
	}

	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @return archivo de resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public FileResponseDTO getCanceledWorkOrdersReport(
			WorkOrderCanceledFilterDTO filter) throws BusinessException {
		return workOrderCancelBusinessLocal.getCanceledWorkOrdersReport(filter);
	}

	@Override
	public void manageCanceledWorkOrder(Long workOrderId, Long userId)
			throws BusinessException {
		workOrderCancelBusinessLocal.manageCanceledWorkOrder(workOrderId,userId);
		
	}


}
