package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EmailType;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad EmailType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmailTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un EmailType
	 * @param obj objeto que encapsula la información de un EmailType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createEmailType(EmailType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un EmailType
	 * @param obj objeto que encapsula la información de un EmailType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateEmailType(EmailType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un EmailType
	 * @param obj información del EmailType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteEmailType(EmailType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un EmailType por su identificador
	 * @param id identificador del EmailType a ser consultado
	 * @return objeto con la información del EmailType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public EmailType getEmailTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los EmailType almacenados en la persistencia
	 * @return Lista con los EmailType existentes, una lista vacia en caso que no existan EmailType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<EmailType> getAllEmailTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene un tipo de correo dado el código
	 * @param code código del tipo de correo a consultar
	 * @return tipo de correo con el código, nulo en caso que no
	 * se encuentre el tipo de correo con el código especificado
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public EmailType getEmailTypeByCode(String code)throws DAOServiceException, DAOSQLException;


	/**
	 * Metodo: Obtiene los tipos de correo activos
	 * @return lista con los tipos de correo activos
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public List<EmailType> getActiveEmailTypes() throws DAOServiceException, DAOSQLException;
	
}