package co.com.directv.sdii.persistence.dao.security;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.User;

/**
 * Interfaz que define los métodos de acceso a la persistencia para la entidad Usuarios
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface UserDAOLocal {

	/**
	 * Metodo: Persiste la información de un usuario en la base de datos
	 * @param user objeto a ser persistido
	 * @throws DAOServiceException en caso de error al tratar de persistir el objeto
	 * @throws DAOSQLException En caso de error al ejecutar el método
	 * @author jjimenezh
	 */
	public void createUser(User user)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Actualiza la información de un usuario
	 * @param user usuario a actualizar la información
	 * @throws DAOServiceException en caso de error al tratar de actualizar la información del usuario
	 * @throws DAOSQLException en caso de error al tratar de actualizar la información del usuario
	 * @author jjimenezh
	 */
	public void updateUser(User user)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los usuarios que existen en la persistencia
	 * @return Lista con la información de todos los usuarios que existen en la persistencia
	 * @throws DAOServiceException en caso de error al tratar de obtener la información de todos los usuarios
	 * @throws DAOSQLException en caso de error al tratar de obtener la información de todos los usuarios
	 * @author jjimenezh
	 */
	public List<User> getAllUsers()throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un usuario dado el identificador del registro en la persistencia
	 * @param userId identificador del usuario
	 * @return Objeto con la información del usuario, nulo en caso que no exista
	 * @throws DAOServiceException en caso de error al tratar de obtener la información del usuario por identificador del registro
	 * @throws DAOSQLException en caso de error al tratar de obtener la información del usuario por identificador del registro
	 * @author jjimenezh
	 */
	public User getUserById(Long userId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un usuario dado el identificador del registro en la persistencia
	 * @param userId identificador del usuario
	 * @return Objeto con la información del usuario, nulo en caso que no exista
	 * @throws DAOServiceException en caso de error al tratar de obtener la información del usuario por identificador del registro
	 * @throws DAOSQLException en caso de error al tratar de obtener la información del usuario por identificador del registro
	 * @author aharker
	 */
	public User getUserByIdWithNewTransaction(Long userId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un usuario dado el número de documento de identificación
	 * @param documentId número de documento de identificación
	 * @return información del usuario dado el número de documento de identificación
	 * @throws DAOServiceException En caso de error al tratar de obtener la información del usuario
	 * @throws DAOSQLException En caso de error al tratar de obtener la información del usuario
	 * @author jjimenezh
	 */
	public User getUserByDocId(String documentId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un usuario dado el identificador 'loginName'
	 * @param loginName  Identificador con el que el usuario ingresa al sistema 
	 * @return información del usuario correspondiente
	 * @throws DAOServiceException En caso de error al tratar de obtener la información del usuario
	 * @throws DAOSQLException En caso de error al tratar de obtener la información del usuario
	 * @author rdelarosa
	 */
	public User getUserByLoginName(String loginName,Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de los usuarios que concuerden con los datos especificados en el objeto que se pasa como muestra
	 * @param userSample objeto de muestra que sirve para filtrar la información del usuario
	 * @return Lista con los usuarios cuya información coincide con la del objeto de muestra
	 * @throws DAOServiceException en caso de error al consultar la información de los usuarios por muestra
	 * @throws DAOSQLException en caso de error al consultar la información de los usuarios por muestra
	 * @author jjimenezh
	 */
	public List<User> getUserBySample(User userSample)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite borrar la información de un usuario dado su identificador
	 * @param user objeto usuario con la propiedad id asignada
	 * @throws DAOServiceException en caso de error al tratar de borrar la información del usuario
	 * @throws DAOSQLException en caso de error al tratar de borrar la información del usuario
	 * @author jjimenezh
	 */
	public void deleteUser(User user)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite obtener la información de un usuario que haya hecho una modificacion en una remision
	 * @param refID Long ID de la remision en la que se hizo la modificacion
	 * @param modificationType String Codigo de tipo de modificacion que se hizo
	 * @return Usuario que realizo la modificacion
	 * @throws DAOServiceException En caso de error al tratar de consultar la informacion del usuario
	 * @throws DAOSQLException En caso de error al tratar de consultar la informacion del usuario
	 * @author jnova
	 */
	public User getUserReferenceModificationsByReferenceIDAndModificationType(Long refID,String modificationType)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de usuarios dado el código del tipo de rol e identificador del dealer asociado
	 * @param dealerId identificador del dealer asociado
	 * @param roleTypeCode Código del tipo de rol
	 * @return Lista de usuarios que están asociados al dealer especificado y tienen el código especificado
	 * @author jjimenezh
	 */
	public List<User> getUsersByDealerIdAndRoleTypeCode(Long dealerId, String roleTypeCode)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de usuarios dado el código del tipo de rol y el identificador del pais del usuario
	 * @param roleTypeCode Código del tipo de rol
	 * @param countryId identificador del pais del usuario
	 * @return Lista de usuarios que están asociados al pais especificado y tienen el código especificado
	 * @author wjimenez
	 */
	public List<User> getUsersByRoleTypeCodeAndCountryId(String roleTypeCode, Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obitne una lista de usuario dado el codigo de tipo de rol
	 * @param roleTypeCode codigo de tipo de rol
	 * @return Lista de usuartis que tiene el codigo de rol especificado
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<User> getUsersByRoleTypeCode(String roleTypeCode)throws DAOServiceException, DAOSQLException;
	
}
