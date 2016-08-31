package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoLoad;

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
public interface WoLoadDAOLocal {

	/**
	 * Metodo:  persiste la información de un WoLoad
	 * @param obj objeto que encapsula la información de un WoLoad
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createWoLoad(WoLoad obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un WoLoad
	 * @param obj objeto que encapsula la información de un WoLoad
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateWoLoad(WoLoad obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un WoLoad
	 * @param obj información del WoLoad a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteWoLoad(WoLoad obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un WoLoad por su identificador
	 * @param id identificador del WoLoad a ser consultado
	 * @return objeto con la información del WoLoad dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public WoLoad getWoLoadByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el ultimo WO LOAD
	 * @param Long countryId
	 * @return WoLoad
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	public WoLoad getLastWoLoad(Long countryId) throws DAOServiceException, DAOSQLException;
}