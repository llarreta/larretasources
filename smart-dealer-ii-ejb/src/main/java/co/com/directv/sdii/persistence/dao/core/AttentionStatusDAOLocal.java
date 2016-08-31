package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AttentionStatus;

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
public interface AttentionStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un AttentionStatus
	 * @param obj objeto que encapsula la información de un AttentionStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createAttentionStatus(AttentionStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un AttentionStatus
	 * @param obj objeto que encapsula la información de un AttentionStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAttentionStatus(AttentionStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un AttentionStatus
	 * @param obj información del AttentionStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteAttentionStatus(AttentionStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un AttentionStatus por su identificador
	 * @param id identificador del AttentionStatus a ser consultado
	 * @return objeto con la información del AttentionStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AttentionStatus getAttentionStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los AttentionStatus almacenados en la persistencia
	 * @return Lista con los AttentionStatus existentes, una lista vacia en caso que no existan AttentionStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<AttentionStatus> getAllAttentionStatus() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param woAttentionStatusCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public AttentionStatus getAttentionStatusByCode(String woAttentionStatusCode)throws DAOServiceException, DAOSQLException;


}