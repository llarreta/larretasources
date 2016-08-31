package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.HistoDealerWeekCapacity;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad HistoDealerWeekCapacity
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerWeekCapacityDAOLocal {

	/**
	 * Metodo:  persiste la información de un HistoDealerWeekCapacity
	 * @param obj objeto que encapsula la información de un HistoDealerWeekCapacity
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerWeekCapacity(HistoDealerWeekCapacity obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerWeekCapacity
	 * @param obj objeto que encapsula la información de un HistoDealerWeekCapacity
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerWeekCapacity(HistoDealerWeekCapacity obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerWeekCapacity
	 * @param obj información del HistoDealerWeekCapacity a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerWeekCapacity(HistoDealerWeekCapacity obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerWeekCapacity por su identificador
	 * @param id identificador del HistoDealerWeekCapacity a ser consultado
	 * @return objeto con la información del HistoDealerWeekCapacity dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerWeekCapacity getHistoDealerWeekCapacityByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerWeekCapacity almacenados en la persistencia
	 * @return Lista con los HistoDealerWeekCapacity existentes, una lista vacia en caso que no existan HistoDealerWeekCapacity en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerWeekCapacity> getAllHistoDealerWeekCapacitys() throws DAOServiceException, DAOSQLException;


}