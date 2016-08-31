package co.com.directv.sdii.persistence.dao.security;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Menu;

/**
 * Define las operaciones de acceso a la persistencia para la administración de los menús
 * del módulo de seguridad
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Menu
 * @see co.com.directv.sdii.exceptions.DAOServiceException
 * @see co.com.directv.sdii.exceptions.DAOSQLException
 */
@Local
public interface MenuDAOLocal {

	/**
	 * Metodo: Crea la información de un menú en la persistencia
	 * @param menu información de menú a ser persistida
	 * @throws DAOServiceException en caso de errror
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public void createMenu(Menu menu)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra la información de un menú de la persistencia
	 * @param menu información de menú a ser borrada se requiere que venga especificado el id
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public void deleteMenu(Menu menu)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los menús persistidos en el sistema
	 * @return lista con la información de los menú persistidos en el sistema
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public List<Menu> getAllMenus() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un menú por el código de menú
	 * @param code código de menú
	 * @return información del menú, nulo en caso que no exista el menú con el código especificado
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public Menu getMenuByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un menú dado el identificador
	 * @param id identificador del menú a ser consultado
	 * @return información del menú consultado, nulo en caso que no exista el menú consultado
	 * @throws DAOServiceException en caso de error al tratar de consultar la información del menú
	 * @throws DAOSQLException en caso de error al tratar de consultar la información del menú
	 * @author jjimenezh
	 */
	public Menu getMenuById(Long id)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los menú dado el identificador del menú padre
	 * @param fatherId identificador del menú padre
	 * @return Lista con la información de los submenús dado el identificador de un padre, una lista 
	 * vacia en caso que el menú no tenga hijos
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public List<Menu> getMenusByFather(Long fatherId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Actualiza la información de un menú en la persistencia
	 * @param menu información del menú a ser actualizada
	 * @throws DAOServiceException en caso de error al tratar de actualizar la información
	 * @throws DAOSQLException en caso de error al tratar de actualizar la información
	 * @author jjimenezh
	 */
	public void updateMenu(Menu menu)throws DAOServiceException, DAOSQLException;
}
