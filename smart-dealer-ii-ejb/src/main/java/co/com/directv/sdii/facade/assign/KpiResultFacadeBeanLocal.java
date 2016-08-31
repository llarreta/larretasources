package co.com.directv.sdii.facade.assign;

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
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad KpiResult.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiResultFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto KpiResult
	 * @param obj - KpiResultVO  objeto que encapsula la información de un KpiResultVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiResult(KpiResultVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un KpiResult
	 * @param obj - KpiResultVO  objeto que encapsula la información de un KpiResultVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiResult(KpiResultVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un KpiResult
	 * @param obj - KpiResultVO  información del KpiResultVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteKpiResult(KpiResultVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un KpiResult por su identificador
	 * @param id - Long identificador del KpiResult a ser consultado
	 * @return objeto con la información del KpiResultVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public KpiResultVO getKpiResultByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los KpiResult almacenados en la persistencia
	 * @return List<KpiResultVO> Lista con los KpiResultVO existentes, una lista vacia en caso que no existan KpiResultVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<KpiResultVO> getAllKpiResults() throws BusinessException;
	
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
