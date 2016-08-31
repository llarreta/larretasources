package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ResponseSearchKpiResultsResponse;
import co.com.directv.sdii.model.vo.KpiResultVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad KpiResult.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiResultBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto KpiResultVO
	 * @param obj objeto que encapsula la información de un KpiResultVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiResult(KpiResultVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un KpiResultVO
	 * @param obj objeto que encapsula la información de un KpiResultVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiResult(KpiResultVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiResultVO
	 * @param obj información del KpiResultVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiResult(KpiResultVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un KpiResultVO por su identificador
	 * @param id identificador del KpiResultVO a ser consultado
	 * @return objeto con la información del KpiResultVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiResultVO getKpiResultByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiResultVO almacenados en la persistencia
	 * @return Lista con los KpiResultVO existentes, una lista vacia en caso que no existan KpiResultVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiResultVO> getAllKpiResults() throws BusinessException;

	/**
	 * 
	 * Metodo: Obtiene el resultado almacenado del cálculo de un KPI para el dealer indicado
	 * @param kpiConfigurationId identificador de la configuración de KPI
	 * @param dealerId identificador del dealer
	 * @return KpiResultVO objeto que encapsula el resultado del kpi
	 * @throws BusinessException en caso de error en la consulta
	 * @author wjimenez
	 */
	public KpiResultVO getDealerIndicatorResultByKpiConfigurationIdAndDealerId(Long kpiConfigurationId, Long dealerId) throws BusinessException;

	/**
	 * Metodo: Obtiene el último valor calculado para un dealer para un indicador dado
	 * @param countryId identificador del país
	 * @param serviceSuperCategoryId identificador de la super categoría de servicio
	 * @param zoneTypeId identificador del tipo de zona
	 * @param indicatorId identificador del indicador
	 * @param dealerId identificador de la compañía
	 * @return último resultado calculado
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public KpiResultVO getKpiResultByCriteria(Long countryId, Long serviceSuperCategoryId, Long zoneTypeId, Long indicatorId, Long dealerId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n de los dealer segun el filtro
	 * @param RequestSearchKpiResultsDTO Objeto para transportar los filtros
	 * @param RequestCollectionInfo Objeto utilizado para la paginacion
	 * @return Lista con los Dealers existentes, una lista vacia en caso que no existan Kpi en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author cduarte
	 */
	public ResponseSearchKpiResultsResponse getKpiResultDealersByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request, RequestCollectionInfo requestCollInfo ) throws BusinessException;

	/**
	 * Metodo: Obtiene la informaciÃ³n del promedio por mes de los Kpi
	 * @param RequestSearchKpiResultsDTO Objeto para transportar los filtros
	 * @param RequestCollectionInfo Objeto utilizado para la paginacion
	 * @return Lista con los Kpi existentes, una lista vacia en caso que no existan Kpi en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operaciÃ³n
	 * @author cduarte
	 */
	public ResponseSearchKpiResultsResponse getKpiResultByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request) throws BusinessException;

}
