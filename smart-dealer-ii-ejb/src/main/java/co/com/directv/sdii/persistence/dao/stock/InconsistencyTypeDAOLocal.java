package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.InconsistencyType;

/**
 * Interface Local para la gestión del CRUD de la
 * Entidad InconsistencyType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface InconsistencyTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un InconsistencyType
	 * @param obj objeto que encapsula la información de un InconsistencyType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createInconsistencyType(InconsistencyType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un InconsistencyType
	 * @param obj objeto que encapsula la información de un InconsistencyType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateInconsistencyType(InconsistencyType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un InconsistencyType
	 * @param obj información del InconsistencyType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteInconsistencyType(InconsistencyType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un InconsistencyType por su identificador
	 * @param id identificador del InconsistencyType a ser consultado
	 * @return objeto con la información del InconsistencyType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public InconsistencyType getInconsistencyTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los InconsistencyType almacenados en la persistencia
	 * @return Lista con los InconsistencyType existentes, una lista vacia en caso que no existan InconsistencyType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<InconsistencyType> getAllInconsistencyTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los InconsistencyType almacenados en la persistencia
	 * @return Lista con los InconsistencyType existentes, una lista vacia en caso que no existan InconsistencyType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<InconsistencyType> getActiveInconsistencyTypes() throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Obtiene la información de un tipo de inconsistencia dado el código
	 * @param code código de la inconsistencia a ser consultada
	 * @return tipo de inconsistencia dado el código, nulo en caso que no exista
	 * la inconsistencia con el código especificado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public InconsistencyType getInconsistencyTypeByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un tipo de inconsistencia dado el código y que se encuentre activa
	 * @param code código de la inconsistencia a ser consultada
	 * @return tipo de inconsistencia dado el código, nulo en caso que no exista
	 * la inconsistencia con el código especificado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jnova
	 */
	public InconsistencyType getInconsistencyTypeByCodeAndActive(String code)throws DAOServiceException, DAOSQLException;


}