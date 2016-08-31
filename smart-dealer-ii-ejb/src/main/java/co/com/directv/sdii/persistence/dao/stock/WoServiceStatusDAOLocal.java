package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoServiceStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad WoServiceStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WoServiceStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un WoServiceStatus
	 * @param obj objeto que encapsula la información de un WoServiceStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createWoServiceStatus(WoServiceStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un WoServiceStatus
	 * @param obj objeto que encapsula la información de un WoServiceStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateWoServiceStatus(WoServiceStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un WoServiceStatus
	 * @param obj información del WoServiceStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteWoServiceStatus(WoServiceStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un WoServiceStatus por su identificador
	 * @param id identificador del WoServiceStatus a ser consultado
	 * @return objeto con la información del WoServiceStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public WoServiceStatus getWoServiceStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los WoServiceStatus almacenados en la persistencia
	 * @return Lista con los WoServiceStatus existentes, una lista vacia en caso que no existan WoServiceStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<WoServiceStatus> getAllWoServiceStatuss() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param codeEntity
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public WoServiceStatus getWoServiceStatusByCode(String codeEntity)throws DAOServiceException, DAOSQLException;


}