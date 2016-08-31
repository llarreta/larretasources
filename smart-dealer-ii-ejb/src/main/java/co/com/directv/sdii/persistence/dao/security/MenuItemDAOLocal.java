package co.com.directv.sdii.persistence.dao.security;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Menu;
import co.com.directv.sdii.model.pojo.MenuItem;

/**
 * Define las operaciones de persistencia para los MenuItems
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.MenuItem
 * @see co.com.directv.sdii.model.pojo.Menu
 * @see co.com.directv.sdii.exceptions.DAOServiceException
 * @see co.com.directv.sdii.exceptions.DAOSQLException
 */
@Local
public interface MenuItemDAOLocal {

	/**
	 * Metodo: Persiste la información de un menú item
	 * @param menuItem información de menú item a ser persistida
	 * @throws DAOServiceException En caso de error
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public void createMenuItem(MenuItem menuItem)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un menú item
	 * @param menuItem información de menú item a ser borrada
	 * @throws DAOServiceException En caso de error
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public void deleteMenuItem(MenuItem menuItem)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los menú item del sistema
	 * @return Lista con la información de los menú item, lista vacia en caso que no se encuentre
	 * @throws DAOServiceException En caso de error 
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public List<MenuItem> getAllMenuItems()throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un menú item por código
	 * @param code código de menú item a ser consultado
	 * @return objeto con información encapsulada, nulo en caso que no se encuentre el menú item 
	 * con el código especificado
	 * @throws DAOServiceException En caso de error
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public MenuItem getMenuItemByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un menú item por identificador
	 * @param id identificador del menú item a ser consultado
	 * @return objeto con la información del menú item, nulo en caso que no se haya 
	 * encontrado el menú item con ese id
	 * @throws DAOServiceException En caso de error
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public MenuItem getMenuItemById(Long id)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los menú items dado el menú padre
	 * @param menu información del menú padre, solo es necesaria la propiedad id
	 * @return Lista con los menú items del menú, lista vacia en caso que el menú no
	 * tenga menú items
	 * @throws DAOServiceException En caso de error
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public List<MenuItem> getMenuItemsByMenu(Menu menu)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Actualiza la información de un menú item
	 * @param menuItem información de menú item a ser actualizada
	 * @throws DAOServiceException En caso de error
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public void updateMenuItem(MenuItem menuItem)throws DAOServiceException, DAOSQLException;
}
