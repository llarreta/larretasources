package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RefIncStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad RefIncStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefIncStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un RefIncStatus
	 * @param obj objeto que encapsula la información de un RefIncStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefIncStatus(RefIncStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un RefIncStatus
	 * @param obj objeto que encapsula la información de un RefIncStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefIncStatus(RefIncStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefIncStatus
	 * @param obj información del RefIncStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRefIncStatus(RefIncStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un RefIncStatus por su identificador
	 * @param id identificador del RefIncStatus a ser consultado
	 * @return objeto con la información del RefIncStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefIncStatus getRefIncStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefIncStatus almacenados en la persistencia
	 * @return Lista con los RefIncStatus existentes, una lista vacia en caso que no existan RefIncStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RefIncStatus> getAllRefIncStatuss() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un RefIncStatus por su codigo
	 * @param codigo codigo del RefIncStatus a ser consultado
	 * @return objeto con la información del RefIncStatus dado su codigo, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefIncStatus getRefIncStatusByCode(String code) throws DAOServiceException, DAOSQLException;

}