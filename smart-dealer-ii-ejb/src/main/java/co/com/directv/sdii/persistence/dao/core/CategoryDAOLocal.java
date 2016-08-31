package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Category;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Category
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CategoryDAOLocal {

	/**
	 * Metodo:  persiste la información de un Category
	 * @param obj objeto que encapsula la información de un Category
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createCategory(Category obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Category
	 * @param obj objeto que encapsula la información de un Category
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateCategory(Category obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Category
	 * @param obj información del Category a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteCategory(Category obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Category por su identificador
	 * @param id identificador del Category a ser consultado
	 * @return objeto con la información del Category dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public Category getCategoryByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los categories padre y verifiquen un criterio de filtro. En esta ocasión, filtra por el valor S.
	 * @return Lista con los Categories padre, una lista vacia en caso que no existan Categories padres en el sistema.
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<Category> getParentCategories() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los categories hijos dado un padre y que a su vez verifiquen un criterio de filtro. En esta ocasión, filtra por el valor S.
	 * @param parentId el id del padre del cual se quieren obtener los hijos.
	 * @return Lista con los Categories padre, una lista vacia en caso que no existan Categories padres en el sistema.
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<Category> getChildrenCategories(Long parentId) throws DAOServiceException, DAOSQLException;
	
}