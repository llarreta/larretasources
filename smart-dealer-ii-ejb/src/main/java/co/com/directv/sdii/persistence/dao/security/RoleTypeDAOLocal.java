package co.com.directv.sdii.persistence.dao.security;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RoleType;

/**
 * Define las operaciones de acceso a la persistencia para la administración de roles
 * Fecha de Creación: 28/05/2010
 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Rol
 */
@Local
public interface RoleTypeDAOLocal {

	/**
	 * Metodo: Crea un rol
	 * @param rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public void createRoleType(RoleType roleType)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Elimina un rol
	 * @param rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public void deleteRoleType(RoleType roleType)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene todos los roles
	 * @return List
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<RoleType> getAllRoleTypes() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene un rol por el codigo
	 * @param rolCode
	 * @return Rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public RoleType getRoleTypeByCode(String roleTypeCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene un rol por el nombre de este
	 * @param rolName
	 * @return Rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public RoleType getRoleTypeByName(String roleTypeName)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Obtiene un rol por el nombre y el codigo
	 * @param rolName
	 * @param rolCode
	 * @return Rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public RoleType getRoleTypeByNameOrCode(String roleTypeName, String roleTypeCode) throws DAOServiceException,DAOSQLException;
	
	
	/**
	 * Obtiene un rol por su id
	 * @param id
	 * @return Rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public RoleType getRoleTypeById(Long id)throws DAOServiceException, DAOSQLException;
	

	/**
	 * Actualiza un rol
	 * @param rol
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public void updateRoleType(RoleType roleType)throws DAOServiceException, DAOSQLException;
}
