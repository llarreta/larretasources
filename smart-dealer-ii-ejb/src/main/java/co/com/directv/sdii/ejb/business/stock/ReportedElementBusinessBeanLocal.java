package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.ReportedElementVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ReportedElement.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReportedElementBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ReportedElementVO
	 * @param obj objeto que encapsula la información de un ReportedElementVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createReportedElement(ReportedElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReportedElementVO
	 * @param obj objeto que encapsula la información de un ReportedElementVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateReportedElement(ReportedElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReportedElementVO
	 * @param obj información del ReportedElementVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteReportedElement(ReportedElementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ReportedElementVO por su identificador
	 * @param id identificador del ReportedElementVO a ser consultado
	 * @return objeto con la información del ReportedElementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ReportedElementVO getReportedElementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReportedElementVO almacenados en la persistencia
	 * @return Lista con los ReportedElementVO existentes, una lista vacia en caso que no existan ReportedElementVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ReportedElementVO> getAllReportedElements() throws BusinessException;

	/**
	 * Metodo: Consulta los elementos reportados como inconsistencia de remisión para una inconsistencia en particular
	 * @param refInconsistencyId identificador de la inconsistencia de remisión
	 * @param incluirSobrantes indica si los resultados deben incluir inconsistencias con cantidades positivas
	 * @param incluirFaltantes indica si los resultados deben incluir inconsistencias con cantidades negativas
	 * @return List<ReportedElementVO>
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public List<ReportedElementVO> getReportedElementsByRefInconsistencyId(
			Long refInconsistencyId, boolean incluirSobrantes,
			boolean incluirFaltantes) throws BusinessException;

	/**
	 * Metodo: elimina todos los elementos reportados de una inconsistencia de remisión
	 * @param refInconsistencyId identificador de la inconsistencia
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void deleteReportedElementsByRefInconsitencyId(Long refInconsistencyId) throws BusinessException;
	
	/**
	 * Metodo: cuenta la cantidad total en inconsistencias abiertas de un elemento de una remisión
	 * @param refElementItemId
	 * @param incluirSobrantes
	 * @param incluirFaltantes
	 * @return
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public Double getCountReportedElementsByRefElementItemId(
			Long refElementItemId, boolean incluirSobrantes, boolean incluirFaltantes)
			throws BusinessException;
	
}
