package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoAttentionStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Category
 * 
 * Fecha de Creación: Dic 3, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WoAttentionStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un WoAttentionStatus
	 * @param obj objeto que encapsula la información de un WoAttentionStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createWoAttentionStatus(WoAttentionStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un WoAttentionStatus
	 * @param obj objeto que encapsula la información de un WoAttentionStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateWoAttentionStatus(WoAttentionStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un WoAttentionStatus
	 * @param obj información del WoAttentionStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteWoAttentionStatus(WoAttentionStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un WoAttentionStatus por su identificador
	 * @param id identificador del WoAttentionStatus a ser consultado
	 * @return objeto con la información del WoAttentionStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public WoAttentionStatus getWoAttentionStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los WoAttentionStatus almacenados en la persistencia
	 * @return Lista con los WoAttentionStatus existentes, una lista vacia en caso que no existan WoAttentionStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<WoAttentionStatus> getAllWoAttentionStatus() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param workOrderId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public WoAttentionStatus getLastWoAttentionStatusByWoId(Long workOrderId) throws DAOServiceException, DAOSQLException;


}