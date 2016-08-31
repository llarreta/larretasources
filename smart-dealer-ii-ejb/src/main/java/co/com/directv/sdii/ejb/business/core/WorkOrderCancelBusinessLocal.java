package co.com.directv.sdii.ejb.business.core;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.WorkOrderCanceledDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

@Local
public interface WorkOrderCancelBusinessLocal {

	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @param requestInfo parametros de paginacion de la consulta
	 * @return resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	public WorkOrderCanceledResponse getCanceledWorkOrders(WorkOrderCanceledFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * Metodo que realiza la consulta de cuantas work order se cancelaron en los ultimos dias, para un pais especifico, dado el numero de dias, el pais y la fecha actual
	 * @param countryId Id del pais
	 * @return numero de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	public Long getCanceledWorkOrdersCount(Long countryId, Long userId) throws BusinessException;
	
	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @return archivo de resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	public FileResponseDTO getCanceledWorkOrdersReport(WorkOrderCanceledFilterDTO filter) throws BusinessException;

	public void manageCanceledWorkOrder(Long workOrderId, Long userId) throws BusinessException;
	
}
