package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ReportedElement;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ReportedElementDAOLocal
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReportedElementDAOLocal {

	/**
	 * Metodo:  persiste la información de un Delivery
	 * @param obj objeto que encapsula la información de un Delivery
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createReportedElement(ReportedElement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ReportedElement
	 * @param obj objeto que encapsula la información de un ReportedElement
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateReportedElement(ReportedElement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReportedElement
	 * @param obj información del ReportedElement a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteReportedElement(ReportedElement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ReportedElement por su identificador
	 * @param id identificador del ReportedElement a ser consultado
	 * @return objeto con la información del ReportedElement dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ReportedElement getReportedElementByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReportedElement almacenados en la persistencia
	 * @return Lista con los ReportedElement existentes, una lista vacia en caso que no existan ReportedElement en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ReportedElement> getAllReportedElements() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta los elementos reportados como inconsistencia de remisión para una inconsistencia en particular
	 * @param refInconsistencyId identificador de la inconsistencia de remisión
	 * @param incluirSobrantes indica si los resultados deben incluir inconsistencias con cantidades positivas
	 * @param incluirFaltantes indica si los resultados deben incluir inconsistencias con cantidades negativas
	 * @return List<ReportedElementVO>
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public List<ReportedElement> getReportedElementsByRefInconsistencyId(
			Long refInconsistencyId, boolean incluirSobrantes,
			boolean incluirFaltantes) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Elimina los todos elementos reportados relacionados a una inconsistencia de remisión
	 * @param refInconsitencyId identificador de la inconsistencia de remisión
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void deleteReportedElementsByRefInconsitencyId(Long refInconsitencyId) throws DAOServiceException, DAOSQLException;

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
	public Double getQuantityReportedElementsByRefElementItemId(
			Long refElementItemId, boolean incluirSobrantes, boolean incluirFaltantes)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: cuenta el número de registros en una remisión para un tipo de elemento específico
	 * @param referenceId
	 * @param elementTypeId
	 * @param incluirSobrantes
	 * @param incluirFaltantes
	 * @return
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public Long getCountReportedElementsByRefIdAndElementTypeId(Long referenceId,
			Long elementTypeId, boolean incluirSobrantes,
			boolean incluirFaltantes) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: busca un elemento reportado de una remisión por su serial
	 * @param referenceId
	 * @param serialCode
	 * @return elemento reportado. Nulo si no encuentra
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public ReportedElement getReportedElementByReferenceIdAndSerial(Long referenceId,
			String serialCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: obtiene el elemento reportado no serializado de una referencia específica
	 * de acuerdo al tipo de elemento 
	 * @param referenceId
	 * @param elementTypeId
	 * @param incluirSobrantes
	 * @param incluirFaltantes
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public ReportedElement getReportedElementByRefIdAndElementTypeId(Long referenceId,
			Long elementTypeId, boolean incluirSobrantes,
			boolean incluirFaltantes) throws DAOServiceException,
			DAOSQLException;

}