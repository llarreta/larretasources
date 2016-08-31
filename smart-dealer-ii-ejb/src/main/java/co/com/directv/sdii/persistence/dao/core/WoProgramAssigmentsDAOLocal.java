package co.com.directv.sdii.persistence.dao.core;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoProgramAssigments;

/**
 * Interface local para la gestion del CRUD de la entidad WoProgramAssigments
 * @author jnova
 *
 */
@Local
public interface WoProgramAssigmentsDAOLocal {

	/**
	 * Metodo: Crea un registro en la tabla de historial asignacion de programas. En el caso que exista
	 * un registro, pone el existente en estado inactivo y crea el nuevo
	 * @param obj WoProgramAssigments Objeto que se desea crear
	 * @throws DAOServiceException En caso de error al ejecutar la operacion
	 * @throws DAOSQLException En caso de error al ejecutar la operacion
	 * @author jnova
	 */
	public void createWoProgramAssigments(WoProgramAssigments obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza un registro de WoProgramAssigments
	 * @param obj WoProgramAssigments Registro que se va a actualizar
	 * @throws DAOServiceException En caso de error al ejecutar la operacion
	 * @throws DAOSQLException En caso de error al ejecutar la operacion
	 * @author jnova
	 */
	public void updateWoProgramAssigments(WoProgramAssigments obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene el ultimo registro activo de la tabla woProgramAssigment a partir de un id de WO
	 * @param woId Long Id de la WO
	 * @return WoProgramAssigments Ultimo registro activo
	 * @throws DAOServiceException En caso de error al ejecutar la operacion
	 * @throws DAOSQLException En caso de error al ejecutar la operacion
	 * @author jnova
	 */
	public WoProgramAssigments getLastWoProgramAssigments(Long woId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Actualiza las asignaciones de programas activas de una WO y las deja inactivas
	 * @param woId Id de la WO
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public void unassignWoProgramAssigment(Long woId) throws DAOServiceException, DAOSQLException;
}
