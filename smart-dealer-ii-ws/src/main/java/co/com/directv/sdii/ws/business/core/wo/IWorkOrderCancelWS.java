package co.com.directv.sdii.ws.business.core.wo;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WorkOrderCanceledDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledResponse;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

@WebService(name="CoreWoCancelWS",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface IWorkOrderCancelWS {
	
	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @param requestInfo parametros de paginacion de la consulta
	 * @return resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@WebMethod(operationName = "getCanceledWorkOrders", action = "getCanceledWorkOrders")
	public WorkOrderCanceledResponse getCanceledWorkOrders(@WebParam(name = "filter") WorkOrderCanceledFilterDTO filter, @WebParam(name = "requestInfo") RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * Metodo que realiza la consulta de cuantas work order se cancelaron en los ultimos dias, para un pais especifico, dado el numero de dias, el pais y la fecha actual
	 * @param countryId Id del pais
	 * @return numero de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@WebMethod(operationName = "getCanceledWorkOrdersCount", action = "getCanceledWorkOrdersCount")
	public Long getCanceledWorkOrdersCount(@WebParam(name = "countryId") Long countryId, @WebParam(name = "userId") Long userId) throws BusinessException;

	@WebMethod(operationName = "manageCanceledWorkOrder", action = "manageCanceledWorkOrder")
	public void manageCanceledWorkOrder(@WebParam(name = "workOrderId") Long workOrderId, @WebParam(name = "userId") Long userId) throws BusinessException;
}
