package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.KpiResult;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ResponseSearchKpiResultsResponse;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad KpiResult
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiResultDAOLocal {

	/**
	 * Metodo:  persiste la información de un KpiResult
	 * @param obj objeto que encapsula la información de un KpiResult
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiResult(KpiResult obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un KpiResult
	 * @param obj objeto que encapsula la información de un KpiResult
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiResult(KpiResult obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiResult
	 * @param obj información del KpiResult a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiResult(KpiResult obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un KpiResult por su identificador
	 * @param id identificador del KpiResult a ser consultado
	 * @return objeto con la información del KpiResult dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiResult getKpiResultByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiResult almacenados en la persistencia
	 * @return Lista con los KpiResult existentes, una lista vacia en caso que no existan KpiResult en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiResult> getAllKpiResults() throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Obtiene el resultado de un KPI para un dealer
	 * @param kpiConfigurationId idenfificador de la configuración de KPI asociada
	 * @param dealerId identificador del dealer
	 * @return KpiResult con el resultado del cálculo del KPI
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public KpiResult getDealerIndicatorResultByKpiConfigurationIdAndDealerId(
			Long kpiConfigurationId, Long dealerId) throws DAOSQLException, DAOServiceException;

	/**
	 * Metodo: Obtiene el último valor calculado para un dealer para un indicador dado
	 * @param countryId identificador del país
	 * @param serviceSuperCategoryId identificador de la super categoría de servicio
	 * @param zoneTypeId identificador del tipo de zona
	 * @param indicatorId identificador del indicador
	 * @param dealerId identificador de la compañía
	 * @return último resultado calculado
	 * @throws DAOSQLException En caso de error
	 * @throws DAOServiceException En caso de error
	 * @author jjimenezh
	 */
	public KpiResult getKpiResultByCriteria(Long countryId,
			Long serviceSuperCategoryId, Long zoneTypeId, Long indicatorId,
			Long dealerId)throws DAOSQLException, DAOServiceException;
	
	/**
	 * Metodo: Obtiene  los dealers que tienen kpi segun el filtro 
	 * @param RequestSearchKpiResultsDTO Objeto para transportar los filtros
	 * @param RequestCollectionInfo Objeto utilizado para la paginacion
	 * @return Lista con los Dealers existentes, una lista vacia en caso que no existan Kpi en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author cduarte
	 */
	public ResponseSearchKpiResultsResponse getKpiResultDealersByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request, RequestCollectionInfo requestCollInfo ) throws DAOSQLException, DAOServiceException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n del promedio por mes de los Kpi segun el filtro
	 * @param RequestSearchKpiResultsDTO Objeto para transportar los filtros
	 * @param RequestCollectionInfo Objeto utilizado para la paginacion
	 * @return Lista con los Kpi existentes, una lista vacia en caso que no existan Kpi en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author cduarte
	 */
	public ResponseSearchKpiResultsResponse getKpiResultByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request) throws DAOSQLException, DAOServiceException;

}