package co.com.directv.sdii.persistence.dao.security;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Rol;

/**
 * Define las operaciones de acceso a la persistencia para la administración de roles
 * Fecha de Creación: 28/05/2010
 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Rol
 */
@Local
public interface RolDAOLocal {

	/**
	 * Metodo: Crea un rol
	 * @param rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public void createRol(Rol rol)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Elimina un rol
	 * @param rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public void deleteRol(Rol rol)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene todos los roles
	 * @return List
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<Rol> getAllRoles() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene un rol por el codigo
	 * @param rolCode
	 * @return Rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Rol getRolByCode(String rolCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene una lista de roles dado el tipo de rol
	 * @param roleTypeId identificador del tipo de rol asociado al rol
	 * @return Lista con los roles asociados al tipo de rol, una lista vacia en caso
	 * que no se encuentre ningún rol relacionado
	 * @throws BusinessException en caso de error al consultar los roles por tipo
	 * @author jjimenezh
	 */
	public List<Rol> getRolesByRoleTypeId(Long roleTypeId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene un rol por el nombre de este
	 * @param rolName
	 * @return Rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Rol getRolByName(String rolName)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Obtiene un rol por el nombre y el codigo
	 * @param rolName
	 * @param rolCode
	 * @return Rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<Rol> getRolByNameOrCode(String rolName, String rolCode) throws DAOServiceException,DAOSQLException;
	
	
	/**
	 * Obtiene un rol por su id
	 * @param id
	 * @return Rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Rol getRolById(Long id)throws DAOServiceException, DAOSQLException;
	

	/**
	 * Actualiza un rol
	 * @param rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public void updateRol(Rol rol)throws DAOServiceException, DAOSQLException;
}
