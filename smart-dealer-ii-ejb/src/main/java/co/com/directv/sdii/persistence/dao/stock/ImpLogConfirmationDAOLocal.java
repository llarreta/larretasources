package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ImpLogConfirmation;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ImpLogConfirmation
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImpLogConfirmationDAOLocal {

	/**
	 * Metodo:  persiste la información de un ImpLogConfirmation
	 * @param obj objeto que encapsula la información de un ImpLogConfirmation
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jalopez
	 */
	public void createImpLogConfirmation(ImpLogConfirmation obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ImpLogConfirmation
	 * @param obj objeto que encapsula la información de un ImpLogConfirmation
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateImpLogConfirmation(ImpLogConfirmation obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImpLogConfirmation
	 * @param obj información del ImpLogConfirmation a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteImpLogConfirmation(ImpLogConfirmation obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ImpLogConfirmation por su identificador
	 * @param id identificador del ImpLogConfirmation a ser consultado
	 * @return objeto con la información del ImpLogConfirmation dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ImpLogConfirmation getImpLogConfirmationByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImpLogConfirmation almacenados en la persistencia
	 * @return Lista con los ImpLogConfirmation existentes, una lista vacia en caso que no existan ImpLogConfirmation en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ImpLogConfirmation> getAllImpLogConfirmations() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de las confirmaciones de items de registros de importación por item
	 * @param importLogItemId identificador del ítem de registro de importación
	 * @return Lista con las confirmaciones que se han realizado ordenada por fecha de la mas reciente a
	 * la mas antígua, una lista vacía en caso que no se hayan realizado previamente confirmaciones de elementos
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author jjimenezh
	 */
	public List<ImpLogConfirmation> getImpLogConfirmationsByImpLogItemId(Long importLogItemId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene una confirmacion del item del registro de importación.
	 * CU INV 04
	 * @param importLogItemId identificador del ítem de registro de importación
	 * @return ImpLogConfirmation confirmacion del registro de importacion
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author jalopez
	 */
	public ImpLogConfirmation getImpLogConfirmationByImpLogItemId(Long importLogItemId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Elmina las confirmacion hechas a un registro elemento de un importacion 
	 * @param impLogItemId Long - Id del impLogItem del cual se vana  borrar las confirmacion
	 * @throws DAOServiceException en caso de error al ejecutar la operacion
	 * @throws DAOSQLException en caso de error al ejecutar la operacion
	 * @author jnova
	 */
	public void deleteImpLogConfirmationByImpLogItemId(Long impLogItemId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: consulta la suma de confirmaciones a partir del id del import log item
	 * @param impLogItemID Long id del impLogItem
	 * @return Cantidad confirmada
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public Double getImportLogItemConfirmationSumByImpLogItemId(Long impLogItemID) throws DAOServiceException, DAOSQLException;
	
}