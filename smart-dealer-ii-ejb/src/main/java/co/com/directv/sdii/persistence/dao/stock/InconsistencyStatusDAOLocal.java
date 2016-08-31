package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.InconsistencyStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad InconsistencyStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface InconsistencyStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un InconsistencyStatus
	 * @param obj objeto que encapsula la información de un InconsistencyStatus
	 * @throws DAOServiceException en caso de error al ejecutar la creación de InconsistencyStatus
	 * @throws DAOSQLException en caso de error al ejecutar la creación de InconsistencyStatus
	 * @author gfandino
	 */
	public void createInconsistencyStatus(InconsistencyStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un InconsistencyStatus
	 * @param obj objeto que encapsula la información de un InconsistencyStatus
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de InconsistencyStatus
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de InconsistencyStatus
	 * @author gfandino
	 */ 
	public void updateInconsistencyStatus(InconsistencyStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un InconsistencyStatus
	 * @param obj información del InconsistencyStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de InconsistencyStatus
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de InconsistencyStatus
	 * @author gfandino
	 */
	public void deleteInconsistencyStatus(InconsistencyStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un InconsistencyStatus por su identificador
	 * @param id identificador del InconsistencyStatus a ser consultado
	 * @return objeto con la información del InconsistencyStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de InconsistencyStatus por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de InconsistencyStatus por ID
	 * @author gfandino
	 */
	public InconsistencyStatus getInconsistencyStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los InconsistencyStatus almacenados en la persistencia
	 * @return Lista con los InconsistencyStatus existentes, una lista vacia en caso que no existan InconsistencyStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los InconsistencyStatus
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los InconsistencyStatus
	 * @author gfandino
	 */
	public List<InconsistencyStatus> getAllInconsistencyStatuss() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un InconsistencyStatus por su código
	 * @param code - String código del InconsistencyStatus a ser consultado
	 * @return InconsistencyStatus objeto con la información del InconsistencyStatus dado su código, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de InconsistencyStatus por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de InconsistencyStatus por código
	 * @author gfandino
	 */
	public InconsistencyStatus getInconsistencyStatusByCode(String code) throws DAOServiceException, DAOSQLException;


}