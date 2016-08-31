package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.HistoDealerBuilding;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad HistoDealerBuilding
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerBuildingDAOLocal {

	/**
	 * Metodo:  persiste la información de un HistoDealerBuilding
	 * @param obj objeto que encapsula la información de un HistoDealerBuilding
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerBuilding(HistoDealerBuilding obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerBuilding
	 * @param obj objeto que encapsula la información de un HistoDealerBuilding
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerBuilding(HistoDealerBuilding obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerBuilding
	 * @param obj información del HistoDealerBuilding a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerBuilding(HistoDealerBuilding obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerBuilding por su identificador
	 * @param id identificador del HistoDealerBuilding a ser consultado
	 * @return objeto con la información del HistoDealerBuilding dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerBuilding getHistoDealerBuildingByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerBuilding almacenados en la persistencia
	 * @return Lista con los HistoDealerBuilding existentes, una lista vacia en caso que no existan HistoDealerBuilding en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerBuilding> getAllHistoDealerBuildings() throws DAOServiceException, DAOSQLException;


}