package co.com.directv.sdii.persistence.dao.config;
import java.util.Date;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

@Local
public interface WorkOrderCancelDAOLocal {

	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @param dateNow fecha actual para el pais que realiza la consulta
	 * @param days dias hacia atras en los que busca las work order canceladas
	 * @param requestInfo parametros de paginacion de la consulta
	 * @return resultado de la consulta de work orders canceladas
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public WorkOrderCanceledResponse getCanceledWorkOrders(WorkOrderCanceledFilterDTO filter,Date dateNow, Integer days, RequestCollectionInfo requestInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo que realiza la consulta de cuantas work order se cancelaron en los ultimos dias, para un pais especifico, dado el numero de dias, el pais y la fecha actual
	 * @param countryId Id del pais
	 * @param days dias hacia atras con los cuales realizara la consulta
	 * @param dateNow fecha actual del sistema para el pais
	 * @return numero de work orders canceladas
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public Long getCanceledWorkOrdersCount(Long countryId, Integer days, Date dateNow, Long dealerId) throws DAOServiceException, DAOSQLException;

	public void manageCanceledWorkOrder(Long workOrderId, Long userId)throws DAOServiceException, DAOSQLException ;
	
}
