package co.com.directv.sdii.ws.business.security;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.MenuItemVO;
import co.com.directv.sdii.model.vo.MenuVO;
import co.com.directv.sdii.model.vo.ProfileVO;
import co.com.directv.sdii.model.vo.RolVO;
import co.com.directv.sdii.model.vo.RoleTypeVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Servicio web que expone la funcionalidad del módulo de seguridad
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal
 */
@MTOM
@WebService()
@Stateless()
public class SecurityWS {

	@EJB(name="SecurityFacadeBeanLocal", beanInterface=SecurityFacadeBeanLocal.class)
	private SecurityFacadeBeanLocal securityFacadeBean;
	
	
	/**
	 * Metodo: Operación para crear un menú
	 * @param menu menú a ser persistido en el sistema
	 * @throws BusinessException En caso de error al tratar de crear el menú,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "createMenu", action = "createMenu")
	public void createMenu(
			@WebParam(name = "menu") MenuVO menu) throws BusinessException {
		securityFacadeBean.createMenu(menu);
	}

	/**
	 * Metodo: Operación para crear un menú item
	 * @param menuItem menú item a ser creado, no debe venir especificada la propiedad id
	 * @throws BusinessException En caso de error al crear el menú item,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "createMenuItem", action = "createMenuItem")
	public void createMenuItem(
			@WebParam(name = "menuItem") MenuItemVO menuItem) throws BusinessException {
		securityFacadeBean.createMenuItem(menuItem);
	}

	/**
	 * Metodo: Operación que permite la creación de un perfil que asocia un rol
	 * con un menú
	 * @param profile información del perfil a ser creado
	 * @throws BusinessException En caso de error al tratar de crear el perfil,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_code_name_already_exist</code> En caso que exista un registro de perfil con el mismo id de menú item y rol<br>
	 * @author gfandino
	 */
	@WebMethod(operationName = "createProfile", action = "createProfile")
	public void createProfile(
			@WebParam(name = "profile") ProfileVO profile) throws BusinessException {
		securityFacadeBean.createProfile(profile);
	}

	/**
	 * Metodo: Operación que permite la creación de una colección de perfiles en el sistema
	 * @param profiles lista con los perfiles a ser creados en el sistema
	 * @throws BusinessException En caso de error al tratar de crear la colección de perfiles,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_code_name_already_exist</code> En caso que exista un registro de perfil con el mismo id de menú item y rol<br>
	 * @author gfandino
	 */
	@WebMethod(operationName = "createProfiles", action = "createProfiles")
	public void createProfiles(
			@WebParam(name = "profiles") List<ProfileVO> profiles)
			throws BusinessException {
		securityFacadeBean.createProfiles(profiles);
	}
	
	/**
	 * Metodo: Operación que permite la creación de una colección de perfiles en el sistema, eliminando
	 * previamente los asignados a rol
	 * @param profiles - List<ProfileVO>
	 * @param rol - RolVO
	 * @throws BusinessException
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_code_name_already_exist</code> En caso que exista un registro de perfil con el mismo id de menú item y rol<br> 
	 * @author gfandino
	 */
	@WebMethod(operationName = "deleteByRolAndCreateProfiles", action = "createProfiles")
	public void deleteByRolAndCreateProfiles(
			@WebParam(name = "profiles") List<ProfileVO> profiles, 
			@WebParam(name = "rol") RolVO rol)
			throws BusinessException {
		securityFacadeBean.deleteByRolAndCreateProfiles(profiles,rol);
	}

	/**
	 * Metodo: Operación que permite la creación de un rol
	 * @param rol rol a ser creado
	 * @throws BusinessException En caso de error al crear el rol,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author lcardozo
	 */
	@WebMethod(operationName = "createRol", action = "createRol")
	public void createRol(
			@WebParam(name = "rol") RolVO rol) throws BusinessException {
		securityFacadeBean.createRol(rol);
	}

	/**
	 * Metodo: Operación que permite la creación de un usuario
	 * @param user información del usuario a ser creado
	 * @throws BusinessException en caso de error al tratar de crear el usuario,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_invalid_mail</code> En caso que el email del usuario sea inválido<br>
     * <code>sdii_CODE_object_already_exist</code> En caso que ya exista un usuario con el mismo nombre de usuario o número de documento en la misma compañía instaladora, 
     * o si no está asociado a una compañía instaladora, en el mismo país<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "createUser", action = "createUser")
	public void createUser(
			@WebParam(name = "user") UserVO user) throws BusinessException {
		securityFacadeBean.createUser(user);
	}

	/**
	 * Metodo: Permite borrar de la persistencia la información de un menú
	 * @param menu información del menú a ser borrado, se requiere únicamente que la
	 * propiedad id contenga un valor válido
	 * @throws BusinessException En caso de error al borrar el menú,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "deleteMenu", action = "deleteMenu")
	public void deleteMenu(
			@WebParam(name = "menu") MenuVO menu) throws BusinessException {
		securityFacadeBean.deleteMenu(menu);
	}

	/**
	 * Metodo: Borra de la persistencia la información asociada a un menú item
	 * @param menuItem información del menú item a ser borrado, solo es necesario el id
	 * @throws BusinessException En caso de error al borrar la información del menú item,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "deleteMenuItem", action = "deleteMenuItem")
	public void deleteMenuItem(
			@WebParam(name = "menuItem") MenuItemVO menuItem) throws BusinessException {
		securityFacadeBean.deleteMenuItem(menuItem);
	}

	/**
	 * Metodo: Borra la información de un perfil
	 * @param profile información de perfil a eliminar
	 * @throws BusinessException En caso de error al tratar de borrar el perfil,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName = "deleteProfile", action = "deleteProfile")
	public void deleteProfile(
			@WebParam(name = "profile") ProfileVO profile) throws BusinessException {
		securityFacadeBean.deleteProfile(profile);
	}

	/**
	 * Metodo: Borra la información de un perfil por rol
	 * @param rol información de rol con el cual se borrarán los perfiles
	 * @throws BusinessException en caso de error al tratar de borrar los perfiles por rol,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName = "deleteProfilesByRol", action = "deleteProfilesByRol")
	public void deleteProfilesByRol(
			@WebParam(name = "rol") RolVO rol) throws BusinessException {
		securityFacadeBean.deleteProfilesByRol(rol);
	}

	/**
	 * Metodo: Borra la información de un rol
	 * @param rol rol a ser eliminado
	 * @throws BusinessException en caso de error al borrar la información del rol,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author lcardozo
	 */
	@WebMethod(operationName = "deleteRol", action = "deleteRol")
	public void deleteRol(
			@WebParam(name = "rol") RolVO rol) throws BusinessException {
		securityFacadeBean.deleteRol(rol);
	}

	/**
	 * Metodo: Borra la información de un usuario
	 * @param user usuario a ser borrado
	 * @throws BusinessException En caso de error al tratar de borrar la información,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "deleteUser", action = "deleteUser")
	public void deleteUser(
			@WebParam(name = "user") UserVO user) throws BusinessException {
		securityFacadeBean.deleteUser(user);
	}

	/**
	 * Metodo: Obtiene toda la información de los menú items almacenados
	 * @return lista con los menú items que existen en el sistema, una lista vacia en caso que no se encuentren
	 * objetos en el sistema
	 * @throws BusinessException En caso de error al consultar los menú items,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getAllMenuItems", action = "getAllMenuItems")
	public List<MenuItemVO> getAllMenuItems() throws BusinessException {
		return securityFacadeBean.getAllMenuItems();
	}

	/**
	 * Metodo: Obtiene toda la información de los menú que existen en el sistema
	 * @return lista con todos los menú del sistema, una lista vacia en caso que no se encuentren
	 * objetos en el sistema
	 * @throws BusinessException en caso de error al consultar los menú,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getAllMenus", action = "getAllMenus")
	public List<MenuVO> getAllMenus() throws BusinessException {
		return securityFacadeBean.getAllMenus();
	}

	/**
	 * Metodo: Obtiene la información de todos los roles que existen en el sistema
	 * @return lista con la información de todos los roles registrados en el sistema, una lista vacia en caso que no se encuentren
	 * objetos en el sistema
	 * @throws BusinessException En caso de error al obtener la información de todos los roles,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getAllRoles", action = "getAllRoles")
	public List<RolVO> getAllRoles() throws BusinessException {
		return securityFacadeBean.getAllRoles();
	}

	/**
	 * Metodo: obtiene todos los usuarios registrados en el sistema
	 * @return lista con la información de todos los usuarios registrados en el sistema,, una lista vacia en caso que no se encuentren
	 * objetos en el sistema
	 * @throws BusinessException En caso de error al tratar de obtener la información de los usuarios,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getAllUsers", action = "getAllUsers")
	public List<UserVO> getAllUsers() throws BusinessException {
		return securityFacadeBean.getAllUsers();
	}

	/**
	 * Metodo: Obtiene la información de un menú dado el código único que
	 * lo identifica
	 * @param code código que identifica el menú a consultar
	 * @return Información del menú con el código especificado, nulo en caso que no se encuentre la
	 * entidad con el criterio establecido
	 * @throws BusinessException En caso de error al ejecutar la operación,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getMenuByCode", action = "getMenuByCode")
	public MenuVO getMenuByCode(
			@WebParam(name = "code") String code) throws BusinessException {
		return securityFacadeBean.getMenuByCode(code);
	}

	/**
	 * Metodo: Obtiene la información de un menú dado su identificador
	 * @param id identificador del menú
	 * @return información del menú, nulo en caso que no se encuentre la
	 * entidad con el criterio establecido
	 * @throws BusinessException En caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getMenuById", action = "getMenuById")
	public MenuVO getMenuById(
			@WebParam(name = "id") Long id) throws BusinessException {
		return securityFacadeBean.getMenuById(id);
	}

	/**
	 * Metodo: obtiene la información de un menú item por código
	 * @param code código del menú item
	 * @return información del menú item, nulo en caso que no se encuentre la
	 * entidad con el criterio establecido
	 * @throws BusinessException En caso de error al consultar la información del menú item,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getMenuItemByCode", action = "getMenuItemByCode")
	public MenuItemVO getMenuItemByCode(
			@WebParam(name = "code") String code) throws BusinessException {
		return securityFacadeBean.getMenuItemByCode(code);
	}

	/**
	 * Metodo: Obtiene la información de un menú item dado el id
	 * @param id identificador del menú item
	 * @return información del menú item por identificador, nulo en caso que no se encuentre la
	 * entidad con el criterio establecido
	 * @throws BusinessException En caso de error al consultar la información del menú item,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getMenuItemById", action = "getMenuItemById")
	public MenuItemVO getMenuItemById(
			@WebParam(name = "id") Long id) throws BusinessException {
		return securityFacadeBean.getMenuItemById(id);
	}

	/**
	 * Metodo: Obtiene la información de un menú item dado el menú padre
	 * @param menu información del menú padre
	 * @return Lista con los menú items asociados al menú especificado, una lista vacia en caso que no
	 * se encuentren menú items asociados al menú
	 * @throws BusinessException en caso de error al tratar de consultar la información de los menú items,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getMenuItemsByMenu", action = "getMenuItemsByMenu")
	public List<MenuItemVO> getMenuItemsByMenu(
			@WebParam(name = "menu") MenuVO menu)
			throws BusinessException {
		return securityFacadeBean.getMenuItemsByMenu(menu);
	}

	/**
	 * Metodo: Obtiene la información de menús por el identificador del padre
	 * @param fatherId identificador del menú padre
	 * @return lista con los menú hijos, lista vacia en caso que no se encuentren menús
	 * asociados al menú padre especificado
	 * @throws BusinessException En caso de error al consultar los menú padres,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getMenusByFather", action = "getMenusByFather")
	public List<MenuVO> getMenusByFather(
			@WebParam(name = "fatherId") Long fatherId)
			throws BusinessException {
		return securityFacadeBean.getMenusByFather(fatherId);
	}

	/**
	 * Metodo: Obtiene la información de perfiles por rol
	 * @param rol rol para la consulta
	 * @return listado con los perfiles del rol, una lista vacia si no se encuentran perfiles
	 * asociados al rol
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName = "getProfileByRol", action = "getProfileByRol")
	public List<ProfileVO> getProfileByRol(
			@WebParam(name = "rol") RolVO rol) throws BusinessException {
		return securityFacadeBean.getProfileByRol(rol);
	}
	
	/**
	 * Metodo: Obtiene la información de perfiles por menú item
	 * @param menuItem información de menú item
	 * @return lista de los perfiles que aplican a ese menú item, una lista vacia si ese menú item
	 * no se encuentra asociado con ningun perfil
	 * @throws BusinessException  en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName = "getProfileByMenuItem", action = "getProfileByMenuItem")
	public List<ProfileVO> getProfileByMenuItem(
			@WebParam(name = "menuItem") MenuItemVO menuItem) throws BusinessException{
		return securityFacadeBean.getProfileByMenuItem(menuItem);
	}
	
	/**
	 * Metodo: Obtiene la información de un perfil dado un rol y menú item
	 * @param rol rol para la consulta
	 * @param menuItem menú item para la consulta
	 * @return información del perfil, nulo en caso que no se encuentre un perfil asociado con
	 * ese rol y menú item.
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName = "getProfileByRolAndMenuItem", action = "getProfileByRolAndMenuItem")
	public ProfileVO getProfileByRolAndMenuItem(
			@WebParam(name = "rol") RolVO rol,
			@WebParam(name = "menuItem") MenuItemVO menuItem) throws BusinessException{
		return securityFacadeBean.getProfileByRolAndMenuItem(rol, menuItem);
	}

	/**
	 * Metodo: Obtiene la información de un rol por código
	 * @param code código del rol a ser consultado
	 * @return información del rol que coincide con el código especificado, nulo en caso que
	 * no se encuentre un rol con ese código
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author lcardozo
	 */
	@WebMethod(operationName = "getRolByCode", action = "getRolByCode")
	public RolVO getRolByCode(
			@WebParam(name = "code") String code) throws BusinessException {
		return securityFacadeBean.getRolByCode(code);
	}
	
	/**
	 * Metodo: Obtiene la información de un rol por nombre
	 * @param code código del rol a ser consultado
	 * @return información del rol que coincide con el nombre especificado, nulo en caso que no
	 * se encuentre información del rol con ese nombre 
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author lcardozo
	 */
	@WebMethod(operationName = "getRolByName", action = "getRolByName")
	public RolVO getRolByName(
			@WebParam(name = "rolName") String rolName) throws BusinessException {
		return securityFacadeBean.getRolByName(rolName);
	}
	
	
	/**
	 * Metodo: Obtiene la información de un rol por código o nombre
	 * @param code código del rol a ser consultado
	 * @param rolName nombre del rol a ser consultado
	 * @return información del rol que coincide con el código o nombre especificados, nulo en caso
	 * que no se encuentre un rol por el nombre o código especificados
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author lcardozo
	 */
	@WebMethod(operationName = "getRolByNameOrCode", action = "getRolByNameOrCode")
	public List<RolVO> getRolByNameOrCode(
			@WebParam(name = "rolName") String rolName, @WebParam(name = "rolCode") String rolCode) throws BusinessException {
		return securityFacadeBean.getRolByNameOrCode(rolName, rolCode);
	}


	/**
	 * Metodo: Obtiene la información de un rol dado el identificador
	 * @param id identificador del rol
	 * @return información del rol, nulo en caso que no se encuentre el rol
	 * con ese id
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author lcardozo
	 */
	@WebMethod(operationName = "getRolById", action = "getRolById")
	public RolVO getRolById(
			@WebParam(name = "id") Long id) throws BusinessException {
		return securityFacadeBean.getRolById(id);
	}
	
	/**
	 * Metodo: Obtiene la información de un rol dado el identificador
	 * @param id identificador del rol
	 * @return información del rol, nulo en caso que no se encuentre el rol
	 * con ese id
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author lcardozo
	 */
	@WebMethod(operationName = "getRolByRoleTypeId", action = "getRolByRoleTypeId")
	public List<RolVO> getRolesByRoleTypeId(@WebParam(name = "roleTypeId") Long roleTypeId) throws BusinessException {
		return securityFacadeBean.getRolesByRoleTypeId(roleTypeId);
	}

	/**
	 * Metodo: Obtiene la información de un usuario dado el identificador
	 * @param id identificador del usuario a consultar
	 * @return información de usuario consultado, nulo en caso que no se encuentre el usuario
	 * con el id especificado
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getUserById", action = "getUserById")
	public UserVO getUserById(
			@WebParam(name = "id") Long id) throws BusinessException {
		return securityFacadeBean.getUserById(id);
	}

	/**
	 * Metodo: Obperación que obtiene la información de un usuario dado el login y dealer
	 * @param login login del usuario a consultar
	 * @param dealer información del dealer asociado al usuario 
	 * @return información del usuario consultado, nulo en caso que no exista un usuario con el
	 * login en la compañía especificados
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getUserByLoginAndDealer", action = "getUserByLoginAndDealer")
	public UserVO getUserByLoginAndDealer(
			@WebParam(name = "login") String login,
			@WebParam(name = "dealer") DealerVO dealer)
			throws BusinessException {
		return securityFacadeBean.getUserByLoginAndDealer(login, dealer);
	}
	
	/**
	 * Metodo: Obtiene los datos de usuarios dado el login o el número de 
	 * documento de los usuarios
	 * @param login login del usuario o usuarios a consultar
	 * @param docNumber número de documento de identidad
	 * @return información de los usuarios consultados, lista vacia en caso que no se encuentren usuarios con
	 * los criterios especificados
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getUsersByLoginOrDocNumber", action = "getUsersByLoginOrDocNumber")
	public List<UserVO> getUsersByLoginOrDocNumber(
			@WebParam(name = "login") String login,
			@WebParam(name = "docNumber") String docNumber)
			throws BusinessException {
		return securityFacadeBean.getUsersByLoginOrDocNumber(login, docNumber);
	}

	/**
	 * Metodo: Obtiene la información de los usuarios asociados a un dealer
	 * @param dealer dealer por el que se realizará el filtro de usuarios
	 * @return lista con los datos de los usuarios asociados al dealer especificado,
	 * una lista vacia en caso que el dealer no tenga usuarios asociados
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getUsersByDealer", action = "getUsersByDealer")
	public List<UserVO> getUsersByDealer(
			@WebParam(name = "dealer") DealerVO dealer)
			throws BusinessException {
		return securityFacadeBean.getUsersByDealer(dealer);
	}

	/**
	 * Metodo: Obtiene los datos de los usuarios cuyo nombre de usuario coincide
	 * @param login login de usuario por el que se realizará la búsqueda
	 * @return lista con la información de los usuarios cuyo login coincida, una lista vacia en
	 * caso que no se encuentren usuarios con ese login.
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getUsersByLogin", action = "getUsersByLogin")
	public List<UserVO> getUsersByLogin(
			@WebParam(name = "login") String login) throws BusinessException {
		return securityFacadeBean.getUsersByLogin(login);
	}

	/**
	 * Metodo: Obtiene una lista de los usuarios que tienen asociado un rol
	 * @param rol rol por el que se llevará a cabo la búsqueda
	 * @return lista con los datos de los usuarios que tienen asociado ese rol, una lista vacia en caso
	 * que no existan usuarios con el rol especificado
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getUsersByRol", action = "getUsersByRol")
	public List<UserVO> getUsersByRol(
			@WebParam(name = "rol") RolVO rol) throws BusinessException {
		return securityFacadeBean.getUsersByRol(rol);
	}

	/**
	 * Metodo: obtiene una lista de usuarios que tienen asociado el rol especificado
	 * y pertenecen al dealer especificado
	 * @param rol rol por el que se realizará la consulta
	 * @param dealer dealer por el que se realizará la consulta
	 * @return lista con la información de los usuarios, una lista vacia en caso que no se encuentren usuarios por el rol y dealer
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getUsersByRolAndDealer", action = "getUsersByRolAndDealer")
	public List<UserVO> getUsersByRolAndDealer(
			@WebParam(name = "rol") RolVO rol, 
			@WebParam(name = "dealer") DealerVO dealer)
			throws BusinessException {
		return securityFacadeBean.getUsersByRolAndDealer(rol, dealer);
	}

	/**
	 * Metodo: Actualiza la información de un menú
	 * @param menu datos del menú a ser actualizado
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "updateMenu", action = "updateMenu")
	public void updateMenu(
			@WebParam(name = "menu") MenuVO menu) throws BusinessException {
		securityFacadeBean.updateMenu(menu);
	}

	/**
	 * Metodo: Actualiza la información de un menú item
	 * @param menuItem datos del menú item a ser actualizado
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "updateMenuItem", action = "updateMenuItem")
	public void updateMenuItem(
			@WebParam(name = "menuItem") MenuItemVO menuItem) throws BusinessException {
		securityFacadeBean.updateMenuItem(menuItem);
	}

	/**
	 * Metodo: Actauliza la información de un rol
	 * @param rol datos del rol a ser actualizado
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author lcardozo
	 */
	@WebMethod(operationName = "updateRol", action = "updateRol")
	public void updateRol(
			@WebParam(name = "rol") RolVO rol) throws BusinessException {
		securityFacadeBean.updateRol(rol);
	}

	/**
	 * Metodo: Actauliza la información de un usuario
	 * @param user datos del usuario a ser actualizado
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_invalid_mail</code> En caso que el email del usuario sea inválido<br>
     * <code>sdii_CODE_object_already_exist</code> En caso que ya exista un usuario con el mismo nombre de usuario o número de documento en la misma compañía instaladora, 
     * o si no está asociado a una compañía instaladora, en el mismo país<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "updateUser", action = "updateUser")
	public void updateUser(
			@WebParam(name = "user") UserVO user) throws BusinessException {
		securityFacadeBean.updateUser(user);
	}
	
	@WebMethod(operationName = "getMenusByRole", action = "getMenusByRole")
	public List<MenuVO> getMenusByRole(@WebParam(name = "role") RolVO role)throws BusinessException{
		return securityFacadeBean.getMenusByRole(role);
	}
	
	@WebMethod(operationName = "getMenuItemsByMenuAndRole", action = "getMenuItemsByMenuAndRole")
	public List<MenuItemVO> getMenuItemsByMenuAndRole(@WebParam(name = "menu") MenuVO menu, @WebParam(name = "role") RolVO role)throws BusinessException{
		return securityFacadeBean.getMenuItemsByMenuAndRole(menu, role);
	}
	
	@WebMethod(operationName = "authorizeMenuItem", action = "authorizeMenuItem")
	public boolean authorizeMenuItem(@WebParam(name = "menuItemCode") String menuItemCode, @WebParam(name = "roleCode") String roleCode)throws BusinessException{
		return securityFacadeBean.authorizeMenuItem(menuItemCode, roleCode);
	}
	
	@WebMethod(operationName = "getAllRoleTypes", action = "getAllRoleTypes")
	public List<RoleTypeVO> getAllRoleTypes()throws BusinessException{
		return securityFacadeBean.getAllRoleTypes();
	}
	
	@WebMethod(operationName = "getRoleTypesByRoleTypeCode", action = "getRoleTypesByRoleTypeCode")
	public List<RoleTypeVO> getRoleTypesByRoleTypeCode(String roleTypeCode)throws BusinessException{
		return securityFacadeBean.getRoleTypesByRoleTypeCode(roleTypeCode);
	}
	
	/**
	 * Metodo: Obtiene los datos de usuarios dado el login o el número de 
	 * documento de los usuarios o por el rol
	 * @param login login del usuario o usuarios a consultar
	 * @param docNumber número de documento de identidad
	 * @param rol RolVO objeto que encapsula el id del rol por el que se va a filtrar
	 * @param countryId Id del pais por el que se va a filtrar
	 * @return información de los usuarios consultados, lista vacia en caso que no se encuentren usuarios con
	 * los criterios especificados
	 * @throws BusinessException en caso de error,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jnova
	 */
	@WebMethod(operationName = "getUsersByLoginOrDocNumberAndRol", action = "getUsersByLoginOrDocNumberAndRol")
	public List<UserVO> getUsersByLoginOrDocNumberAndRol(
			@WebParam(name = "login") String login,
			@WebParam(name = "docNumber") String docNumber,@WebParam(name = "rol") RolVO rol,@WebParam(name = "countryId")Long countryId)
			throws BusinessException {
		return securityFacadeBean.getUsersByLoginOrDocNumberAndRol(login, docNumber,rol,countryId);
	}
	
	/**
	 * Método que permite consultar un usuario por ID y por pais
	 * @param id
	 * @param countryId
	 * @return
	 * @throws BusinessException
	 * @author jnova
	 */
	@WebMethod(operationName = "getUserByIdAndCountry", action="getUserByIdAndCountry")
	public UserVO getUserByIdAndCountry(@WebParam(name = "userId") Long userId, @WebParam(name = "countryId") Long countryId) throws BusinessException{
		return securityFacadeBean.getUserByIdAndCountry(userId, countryId);
	}
}
