package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.InputMethod;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad InputMethod
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface InputMethodDAOLocal {

	/**
	 * Metodo:  persiste la información de un InputMethod
	 * @param obj objeto que encapsula la información de un InputMethod
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createInputMethod(InputMethod obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un InputMethod
	 * @param obj objeto que encapsula la información de un InputMethod
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateInputMethod(InputMethod obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un InputMethod
	 * @param obj información del InputMethod a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteInputMethod(InputMethod obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un InputMethod por su identificador
	 * @param id identificador del InputMethod a ser consultado
	 * @return objeto con la información del InputMethod dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public InputMethod getInputMethodByID(Long id) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un InputMethod por su code
	 * @param code código del InputMethod a ser consultado
	 * @return objeto con la información del InputMethod dado su código, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public InputMethod getInputMethodByCode(String code) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los InputMethod almacenados en la persistencia
	 * @return Lista con los InputMethod existentes, una lista vacia en caso que no existan InputMethod en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<InputMethod> getAllInputMethods() throws DAOServiceException, DAOSQLException;


}