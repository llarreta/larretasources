package co.com.directv.sdii.persistence.dao.allocator;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AllocatorEvent;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad AllocatorEvent
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AllocatorEventDAOLocal {

	/**
	 * Metodo:  persiste la información de un AllocatorEvent
	 * @param obj objeto que encapsula la información de un AllocatorEvent
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createAllocatorEvent(AllocatorEvent obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un AllocatorEvent
	 * @param obj objeto que encapsula la información de un AllocatorEvent
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAllocatorEvent(AllocatorEvent obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un AllocatorEvent
	 * @param obj información del AllocatorEvent a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteAllocatorEvent(AllocatorEvent obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un AllocatorEvent por su identificador
	 * @param id identificador del AllocatorEvent a ser consultado
	 * @return objeto con la información del AllocatorEvent dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AllocatorEvent getAllocatorEventByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los AllocatorEvent almacenados en la persistencia
	 * @return Lista con los AllocatorEvent existentes, una lista vacia en caso que no existan AllocatorEvent en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<AllocatorEvent> getAllAllocatorEvents() throws DAOServiceException, DAOSQLException;


}