package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RefIncStatus;
import co.com.directv.sdii.model.pojo.RefInconsistency;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.collection.RefInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad RefInconsistency
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefInconsistencyDAOLocal {

	/**
	 * Metodo:  persiste la información de un RefInconsistency
	 * @param obj objeto que encapsula la información de un RefInconsistency
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefInconsistency(RefInconsistency obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un RefInconsistency
	 * @param obj objeto que encapsula la información de un RefInconsistency
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public RefInconsistency updateRefInconsistency(RefInconsistency obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefInconsistency
	 * @param obj información del RefInconsistency a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRefInconsistency(RefInconsistency obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un RefInconsistency por su identificador
	 * @param id identificador del RefInconsistency a ser consultado
	 * @return objeto con la información del RefInconsistency dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefInconsistency getRefInconsistencyByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefInconsistency almacenados en la persistencia
	 * @return Lista con los RefInconsistency existentes, una lista vacia en caso que no existan RefInconsistency en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RefInconsistency> getAllRefInconsistencys() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la informacion de las inconsistencias asociadas a una remision junto con los usuario que las originaron
	 * @param refID Long ID de la referencia de la que se desea buscar las inconsistencias
	 * @return List<Object[]> Lista de objetos en donde en cada posicion va la inconsistencia junto con el usuario
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jnova
	 */
	public List<Object[]> getRefInconsistencysAndUserCreationByReferenceID(Long refID) throws DAOServiceException, DAOSQLException;


	/**
	 * Metodo: Consulta objetos RefInconsistency asociados a un Reference
	 * Caso de uso INV 37
	 * @param refID Identificador de remision
	 * @return List<RefInconsistency> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public List<RefInconsistency>getReferenceInconsistencyByReferenceID( Long id )throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Actualiza el estado de las inconsistencias asociados a un Reference
	 * Caso de uso INV 37
	 * @param inconsistenciesListIds Una lista con los identificadores de los objetos RefInconsistency a cambiar de estado
	 * @param status El estado a  definir
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public void updateReferenceInconsistencyStatus( List<Long>inconsistenciesListIds,RefIncStatus status )throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene una remision a partir del id de una inconsistencia
	 * @param incId Id de la inconsistencia
	 * @return Reference Objeto reference al que esta asociada la inconsistencia
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 * @author jnova
	 */
	public Reference getReferenceByInconsistencyId(Long incId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la lista de inconsistencias asociadas a una remision por sus estado
	 * @param refID Id d ela remision
	 * @param refStatusCode Estado del en el cual se busca la inconsistencia
	 * @return Lista de inconsistencias asociadas a la remision y en el estado enviados
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 * @author jnova
	 */
	public RefInconsistencyResponse getReferenceInconsistencyByReferenceIdAndStatus( Long refId , String refStatusCode, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: verifica si todas las inconsistencias de una remisión se encuentran en un estado específico
	 * @param referenceId
	 * @param refStatusCode
	 * @return verdadero si todas las inconsistencias de la remisión se encuentran en el estado especificado
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 * @author wjimenez
	 */
	boolean areAllRefInconsistenciesInStatus(Long referenceId, String refStatusCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: buscar inconsistencias especificando el estado y el tipo de inconsistencia
	 * @param refId
	 * @param refStatusCode
	 * @param refIncTypeCode
	 * @param requestCollInfo
	 * @return listado de inconsistencias que coinciden con los filtros
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 * @author wjimenez
	 */
	public RefInconsistencyResponse getReferenceInconsistencyByReferenceIdAndStatusAndIncTtype(
			Long refId, String refStatusCode, String refIncTypeCode,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: retorna las remisiones que se han generado por cierre de inconsistencias
	 * de menos elementos físicos
	 * @param refIncId
	 * @return
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 * @author wjimenez
	 */
	public List<Reference> getGeneratedReferencesByRefIncId(Long refIncId)
			throws DAOServiceException, DAOSQLException;
	
}