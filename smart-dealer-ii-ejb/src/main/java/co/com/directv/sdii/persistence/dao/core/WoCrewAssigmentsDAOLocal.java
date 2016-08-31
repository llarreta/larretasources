package co.com.directv.sdii.persistence.dao.core;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoCrewAssigments;
import co.com.directv.sdii.model.pojo.WorkOrder;

/**
 * Interface local para la gestion del CRUD de la entidad WoCrewAssigment
 * @author jnova
 *
 */
@Local
public interface WoCrewAssigmentsDAOLocal {
	
	/**
	 * Metodo: Crea un registro en la tabla de historial asignacion de cuadrillas. En el caso que exista
	 * un registro, pone el existente en estado inactivo y crea el nuevo
	 * @param obj WoCrewAssigments Objeto que se desea crear
	 * @throws DAOServiceException En caso de error al ejecutar la operacion
	 * @throws DAOSQLException En caso de error al ejecutar la operacion
	 * @author jnova
	 */
	public void createWoCrewAssigments(WoCrewAssigments obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza un registro de WoCrewAssigments
	 * @param obj WoCrewAssigments Registro que se va a actualizar
	 * @throws DAOServiceException En caso de error al ejecutar la operacion
	 * @throws DAOSQLException En caso de error al ejecutar la operacion
	 * @author jnova
	 */
	public void updateWoCrewAssigments(WoCrewAssigments obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene el ultimo registro activo de la tabla woProgramAssigment a partir de un id de WO
	 * @param woId Long Id de la WO
	 * @return WoProgramAssigments Ultimo registro activo
	 * @throws DAOServiceException En caso de error al ejecutar la operacion
	 * @throws DAOSQLException En caso de error al ejecutar la operacion
	 * @author jnova
	 */
	public WoCrewAssigments getLastWoCrewAssigments(Long woId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Actualiza los registro que se encuentran activos a una WO poniendolos en inactivos
	 * @param woId Id de la WO a la que se le van a inactivar las cuadrillas asignadas
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public void unassignWoCrewAssigment(Long woId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el Ãºltimo registro activo de cada WO la tabla WO_CREW_ASSIGNMENTS.
	 * @param woIds List de id de la WO
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 */
	public List<WoCrewAssigments> getActiveWoCrewAssigmentsByWoIds(List<WorkOrder> workOrderList) throws DAOServiceException, DAOSQLException;

}
