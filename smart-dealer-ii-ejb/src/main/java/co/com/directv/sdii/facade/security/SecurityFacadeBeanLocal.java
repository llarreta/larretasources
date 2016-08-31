package co.com.directv.sdii.facade.security;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.MenuItemVO;
import co.com.directv.sdii.model.vo.MenuVO;
import co.com.directv.sdii.model.vo.ProfileVO;
import co.com.directv.sdii.model.vo.RolVO;
import co.com.directv.sdii.model.vo.RoleTypeVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Define los métodos de acceso al módulo de seguridad
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
@Local
public interface SecurityFacadeBeanLocal {

	/**
	 * Método que permite crear un usuario.
	 * 
	 * @param user objeto a ser creado
	 * @throws BusinessException en caso de error al crear el objeto
	 * @author jjimenezh
	 */
	public void createUser(UserVO user) throws BusinessException;
	
	/**
	 * Método que permite actualizar un usuario.
	 * @param user objeto a ser actualizado
	 * @throws BusinessException en caso de error al actualizar el objeto
	 * @author jjimenezh
	 */
	public void updateUser(UserVO user) throws BusinessException;
	
	/**
	 * Método que permite eliminar un usuario.
	 * 
	 * @param user objeto a ser eliminado
	 * @throws BusinessException en caso de error al borrar el objeto
	 * @author jjimenezh
	 */
	public void deleteUser(UserVO user) throws BusinessException;
	
	/**
	 * Método que permite consultar un usuario por ID.
	 * @param id
	 * @return
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public UserVO getUserById(Long id) throws BusinessException;
	
	/**
	 * Método que permite consultar un usuario por ID y por pais
	 * @param id
	 * @param countryId
	 * @return
	 * @throws BusinessException
	 * @author jnova
	 */
	public UserVO getUserByIdAndCountry(Long id, Long countryId) throws BusinessException;
	
	/**
	 * Método que permite consultar un usuario por Login y Dealer.
	 * @param login
	 * @param dealer
	 * @return
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public UserVO getUserByLoginAndDealer(String login, DealerVO dealer) throws BusinessException;
	
	/**
	 * Método que permite consultar la lista de usuarios por Login.
	 * 
	 * @param login
	 * @return
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public List<UserVO> getUsersByLogin(String login) throws BusinessException;
	
	/**
	 * Método que permite consultar la lista de usuarios de un Dealer.
	 * @param dealer
	 * @return
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public List<UserVO> getUsersByDealer(DealerVO dealer) throws BusinessException;
	
	/**
	 * Método que permite consultar la lista completa de todos los usuarios en el sistema.
	 * 
	 * @return
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public List<UserVO> getAllUsers() throws BusinessException;
	
	/**
	 * Método que consulta la lista de usuarios por Rol.
	 * 
	 * @param rol
	 * @return
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public List<UserVO> getUsersByRol(RolVO rol) throws BusinessException;
	
	/**
	 * Método que permite consultar la lista de usuarios por Rol y Dealer.
	 * 
	 * @param rol
	 * @param dealer
	 * @return
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public List<UserVO> getUsersByRolAndDealer(RolVO rol, DealerVO dealer) throws BusinessException;


	/**
	 * Método que permite crear un Rol.
	 * 
	 * @param rol
	 * @throws BusinessException
	 */
	public void createRol(RolVO rol) throws BusinessException;
	
	/**
	 * Método que permite actualizar un Rol.
	 * @param rol
	 * @throws BusinessException
	 */
	public void updateRol(RolVO rol) throws BusinessException;
	
	/**
	 * Método que permite eliminar un Rol.
	 * 
	 * @param rol
	 * @throws BusinessException
	 */
	public void deleteRol(RolVO rol) throws BusinessException;
	
	/**
	 * Método que permite consultar un Rol por ID.
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public RolVO getRolById(Long id) throws BusinessException;
	
	/**
	 * Método que permite consultar un Rol por código.
	 * 
	 * @param code
	 * @return RolVO
	 * @throws BusinessException
	 */
	public RolVO getRolByCode(String code) throws BusinessException;
	
	/**
	 * Método que permite consultar un Rol por su nombre
	 * 
	 * @param rolName
	 * @return RolVO
	 * @throws BusinessException
	 */
	public RolVO getRolByName(String rolName) throws BusinessException;
	
	/**
	 * Método que permite consultar un Rol por su nombre o codigo
	 * @param rolName
	 * @param rolCode
	 * @return RolVO
	 * @throws BusinessException
	 */
	public List<RolVO> getRolByNameOrCode(String rolName, String rolCode) throws BusinessException;
	
	/**
	 * Método que permite consultar la lista de Roles en el sistema.
	 * @return List
	 * @throws BusinessException
	 */
	public List<RolVO> getAllRoles() throws BusinessException;


	/**
	 * Método que permite crear un Menú.
	 * 
	 * @param menu
	 * @throws BusinessException
	 */
	public void createMenu(MenuVO menu) throws BusinessException;
	
	/**
	 * Método que permite actualizar un Menú.
	 * 
	 * @param menu
	 * @throws BusinessException
	 */
	public void updateMenu(MenuVO menu) throws BusinessException;
	
	/**
	 * Método para eliminar un Menú.
	 * 
	 * @param menu
	 * @throws BusinessException
	 */
	public void deleteMenu(MenuVO menu) throws BusinessException;
	
	/**
	 * Método que permite consultar un Menú por ID.
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public MenuVO getMenuById(Long id) throws BusinessException;
	
	/**
	 * Método que permite consultar un Menú por Código.
	 * 
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	public MenuVO getMenuByCode(String code) throws BusinessException;
	
	/**
	 * Método que permite consultar la lista de todos los Menús de la aplicación.
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<MenuVO> getAllMenus() throws BusinessException;
	
	/**
	 * Método que permite consultar la lista de menús dado el ID del menú padre.
	 * 
	 * @param fatherId
	 * @return
	 * @throws BusinessException
	 */
	public List<MenuVO> getMenusByFather(Long fatherId) throws BusinessException;
	

	/**
	 * Método que permite crear un Item de Menú.
	 * 
	 * @param menuItem
	 * @throws BusinessException
	 */
	public void createMenuItem(MenuItemVO menuItem) throws BusinessException;
	
	/**
	 * Método que oermite actualizar un Item de Menú.
	 * 
	 * @param menuItem
	 * @throws BusinessException
	 */
	public void updateMenuItem(MenuItemVO menuItem) throws BusinessException;
	
	/**
	 * Método que permite eliminar un Item de Menú.
	 * 
	 * @param menuItem
	 * @throws BusinessException
	 */
	public void deleteMenuItem(MenuItemVO menuItem) throws BusinessException;
	
	/**
	 * Método que permite consultar un Item de Menú por ID.
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public MenuItemVO getMenuItemById(Long id) throws BusinessException;
	
	/**
	 * Método que permite Consultar un Item de Menú por Código.
	 * 
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	public MenuItemVO getMenuItemByCode(String code) throws BusinessException;
	
	/**
	 * Método que permite consultar la lista de todos los Items de Menú.
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<MenuItemVO> getAllMenuItems() throws BusinessException;
	
	/**
	 * Método que permite consultar la lista de Items de Menú por el Menú.
	 * 
	 * @param menu
	 * @return
	 * @throws BusinessException
	 */
	public List<MenuItemVO> getMenuItemsByMenu(MenuVO menu) throws BusinessException;

	/**
	 * Metodo: Método que permite crear un perfil. Un registro de perfil es la combinación de un Rol y un Item de Menú.
	 * @param profile - ProfileVO
	 * @throws BusinessException 
	 * @author gfandino
	 */
	public void createProfile(ProfileVO profile) throws BusinessException;
	
		
	
	/**
	 * Metodo: Método que permite eliminar un perfíl. Un registro de perfil es la combinación de un Rol y un Item de Menú.
	 * Para el caso de esta eliminación, si el registro existe y ya no se va a dar acceso a la opción del menu al rol
	 * especificado, se debe eliminar de la tabla. 
	 * @param profile - ProfileVO
	 * @throws BusinessException 
	 * @author gfandino
	 */
	public void deleteProfile(ProfileVO profile) throws BusinessException;
	
	
	
	/**
	 * Metodo: Método que permite obtener la lista de perfiles de un Rol.
	 * @param rol - RolVO
	 * @return List<ProfileVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author gfandino
	 */
	public List<ProfileVO> getProfileByRol(RolVO rol) throws BusinessException;
	
	

	/**
	 * Metodo: Método que permite obtener la lista de perfiles de un Menu Item.
	 * @param menuItem - MenuItemVO
	 * @return List<ProfileVO>
	 * @throws BusinessException 
	 * @author gfandino
	 */
	public List<ProfileVO> getProfileByMenuItem(MenuItemVO menuItem) throws BusinessException;
	
	/**
	 * Metodo: Método que permite obtener perfile de un Menu Item y un Rol
	 * @param rol - RolVO
	 * @param menuItem - MenuItemVO
	 * @return ProfileVO
	 * @throws BusinessException 
	 * @author gfandino
	 */
	public ProfileVO getProfileByRolAndMenuItem(RolVO rol,MenuItemVO menuItem) throws BusinessException;	
	
	/**
	 * Método que permite eliminar todos los registros de Profile dado el Rol.
	 * 
	 * @param rol
	 * @throws BusinessException
	 */
	public void deleteProfilesByRol(RolVO rol) throws BusinessException;
	
	/**
	 * Método que permite crear una lista completa de Profiles para un Rol.
	 * @param profiles
	 * @throws BusinessException
	 */
	public void createProfiles(List<ProfileVO> profiles) throws BusinessException;
	
	/**
	 * Metodo: Método que permite eliminar los perfiles asociados a rol y crear una perfil. Un registro de perfil es la combinación de un Rol y un Item de Menú.
	 * @param profiles - List<ProfileVO>
	 * @param rol - RolVO
	 * @throws BusinessException
	 * @author gfandino
	 */
	public void deleteByRolAndCreateProfiles(List<ProfileVO> profiles, RolVO rol) throws BusinessException;	

	/**
	 * Metodo: Obtiene una lista de usuarios por el login o el número de documento
	 * @param login login
	 * @param docNumber docNumber
	 * @return Lista de usuarios
	 */
	public List<UserVO> getUsersByLoginOrDocNumber(String login, String docNumber)throws BusinessException;
	
	/**
	 * Metodo: Autentica el usuario
	 * @param userName nombre de usuario
	 * @param password contraseña
	 * @param countryId identificador del país asociado al usuario
	 * @return información de credenciales del usuario
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 * 
	 */
	public UserVO authenticateLDAPUser(String userName, String password, Long countryId) throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de menús que son autorizados para el rol
	 * @param role rol para consultar la información de los menús autorizados
	 * @return Lista con los menús del rol, una lista vacia en caso que el rol no tenga menús autorizados
	 * @throws BusinessException En caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	public List<MenuVO> getMenusByRole(RolVO role) throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de los menú item que están relacionados con un menú y que además
	 * están autorizados para un rol
	 * @param menu información del menú
	 * @param role información del rol
	 * @return Lista con la información de menú items
	 * @throws BusinessException En caso de error al obtener la información
	 * @author jjimenezh
	 */
	public List<MenuItemVO> getMenuItemsByMenuAndRole(MenuVO menu, RolVO role)throws BusinessException;

	/**
	 * Metodo: Método que autoriza el acceso a un menú item dado el rol
	 * @param menuItemCode código del menú item
	 * @param roleCode código del rol
	 * @return verdadero si el rol tiene acceso al menú item, falso en otro caso
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public boolean authorizeMenuItem(String menuItemCode, String roleCode)throws BusinessException;

	/**
	 * Metodo: Obtiene todos los tipos de rol
	 * @return Lista con todos los tipos de rol
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<RoleTypeVO> getAllRoleTypes()throws BusinessException;

	/**
	 * Metodo: Obtiene el tipo de role por código
	 * @param roleTypeCode código del tipo de rol
	 * @return Lista con los tipos de rol autorizados para el tipo de rol especificado
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public List<RoleTypeVO> getRoleTypesByRoleTypeCode(String roleTypeCode)throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de roles dado el tipo de rol
	 * @param roleTypeId identificador del tipo de rol asociado al rol
	 * @return Lista con los roles asociados al tipo de rol, una lista vacia en caso
	 * que no se encuentre ningún rol relacionado
	 * @throws BusinessException en caso de error al consultar los roles por tipo
	 * @author jjimenezh
	 */
	public List<RolVO> getRolesByRoleTypeId(Long roleTypeId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de usuarios por login o número de documento o por el rol
	 * @param login
	 * @param docNumber
	 * @param rol RolVO objeto que encapsula el id del rol por el que se va a filtrar
	 * @param countryId Id del pais por el que se va a filtrar
	 * @return Lista de usuarios
	 * @author jnova
	 */
	public List<UserVO> getUsersByLoginOrDocNumberAndRol(String login,String docNumber,RolVO rol,Long countryId)throws BusinessException;
	
}
