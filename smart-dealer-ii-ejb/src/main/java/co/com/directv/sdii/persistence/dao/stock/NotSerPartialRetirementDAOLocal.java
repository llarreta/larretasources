package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.NotSerPartialRetirement;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad NotSerPartialRetirement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface NotSerPartialRetirementDAOLocal {

	/**
	 * Metodo:  persiste la información de un NotSerPartialRetirement
	 * @param obj objeto que encapsula la información de un NotSerPartialRetirement
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createNotSerPartialRetirement(NotSerPartialRetirement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un NotSerPartialRetirement
	 * @param obj objeto que encapsula la información de un NotSerPartialRetirement
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateNotSerPartialRetirement(NotSerPartialRetirement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un NotSerPartialRetirement
	 * @param obj información del NotSerPartialRetirement a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteNotSerPartialRetirement(NotSerPartialRetirement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un NotSerPartialRetirement por su identificador
	 * @param id identificador del NotSerPartialRetirement a ser consultado
	 * @return objeto con la información del NotSerPartialRetirement dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public NotSerPartialRetirement getNotSerPartialRetirementByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los NotSerPartialRetirement almacenados en la persistencia
	 * @return Lista con los NotSerPartialRetirement existentes, una lista vacia en caso que no existan NotSerPartialRetirement en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<NotSerPartialRetirement> getAllNotSerPartialRetirements() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la secuencia de la tabla de retiros parciales
	 * @return Long Valor en que va la secuencia de la tabla de retiros parciales
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public Long getNotSerPartialRetirementSequence() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite obtener los movimientos parciales de elementos no serializados a partir del id del elemento
	 * @param elementId Long id del elemento
	 * @return List<NotSerPartialRetirement> Lista con de movimientos parciales de un elemento
	 * @throws DAOServiceException En caso de error al tratar de hacer la consulta
	 * @throws DAOSQLException En caso de error al tratar de hacer la consulta
	 */
	public List<NotSerPartialRetirement> getNotSerPartialRetirementByElementId(Long elementId)  throws DAOServiceException, DAOSQLException;

}