package co.com.directv.sdii.persistence.dao.security;

import java.util.List;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Profile;

/**
 * interface local para la gestión de Profiles 
 * 
 * Fecha de Creación: 28/05/2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public interface ProfileDAOLocal {

	/**
	 * Metodo: Permite crear un Profile
	 * @param obj - Profile 
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author gfandino
	 */
	public void createProfile(Profile obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar Profiles por Id de Rol
	 * @param idRol - Long
	 * @return List<Profile>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author gfandino
	 */
	public List<Profile> getProfileByIDRol(Long idRol) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Permite consutlar Profiles por id del Menu Item
	 * @param idMenuItem - Long
	 * @return List<Profile>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<Profile> getProfileByIDMenuItem(Long idMenuItem) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Permite consultar un Profile por id Rol y idMenuItem
	 * @param idRol - Long
	 * @param idMenuItem - Long
	 * @return Profile
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public Profile getProfileByIDMenuItemAndIDRol(Long idRol,Long idMenuItem) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite eliminar un Profile
	 * @param obj - Profile
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author gfandino
	 */
	public void deleteProfile(Profile obj) throws DAOServiceException, DAOSQLException;
}
