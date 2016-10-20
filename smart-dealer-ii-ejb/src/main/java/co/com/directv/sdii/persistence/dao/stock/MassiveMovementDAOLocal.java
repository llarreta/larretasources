package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.MassiveMovement;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad MassiveMovement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MassiveMovementDAOLocal {

	/**
	 * Metodo:  persiste la información de un MassiveMovement
	 * @param obj objeto que encapsula la información de un MassiveMovement
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public MassiveMovement createMassiveMovement(MassiveMovement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un MassiveMovement
	 * @param obj objeto que encapsula la información de un MassiveMovement
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateMassiveMovement(MassiveMovement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MassiveMovement
	 * @param obj información del MassiveMovement a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteMassiveMovement(MassiveMovement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un MassiveMovement por su identificador
	 * @param id identificador del MassiveMovement a ser consultado
	 * @return objeto con la información del MassiveMovement dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public MassiveMovement getMassiveMovementByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los MassiveMovement almacenados en la persistencia
	 * @return Lista con los MassiveMovement existentes, una lista vacia en caso que no existan MassiveMovement en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<MassiveMovement> getAllMassiveMovements() throws DAOServiceException, DAOSQLException;


}