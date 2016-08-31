/**
 * Creado 7/07/2010 9:01:26
 */
package co.com.ig.common.error.ejb.business;

import java.util.List;

import javax.ejb.Local;

import org.hibernate.SessionFactory;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.ig.common.error.pojo.ErrorReason;
import co.com.ig.common.error.vo.ErrorMessageVO;
import co.com.ig.common.error.vo.ErrorReasonVO;

/**
 * Define los métodos para la administración de los mensajes de error
 * 
 * Fecha de Creación: 7/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface ErrorMessageBusinessLocal {
	
	/**
	 * Metodo: Persiste la información de un nuevo mensaje de error
	 * @param errorMessage mensaje de error a ser persistido
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public void createErrorMessage(ErrorMessageVO errorMessage);
	
	/**
	 * Metodo: Actualiza la información de un mensaje de error en la persistencia
	 * @param errorMessage mensaje de error a ser actualizado
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public void updateErrorMessage(ErrorMessageVO errorMessage);
	
	/**
	 * Metodo: borra un mensaje de error de la persistencia
	 * @param errorMessage mensaje a ser borrado, requiere la propiedad id
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public void deleteErrorMessage(ErrorMessageVO errorMessage);
	
	/**
	 * Metodo: Obtiene la información de un mensaje de error dado el error key
	 * @param errorKey llave del error por el cual se consultará
	 * @return objeto con la información del mensaje de error encapsulada, nulo en caso que no encuentre
	 * el mensaje por la llave especificada
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public ErrorMessageVO getErrorMessageByErrorKey(String errorKey);
	
	/**
	 * Metodo: Obtiene una lista de mensajes de error
	 * @return lista de mensajes de error existentes en el sistema, una lista vacia en caso que no existan
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public List<ErrorMessageVO> getAllErrorMessages();
	
	public void setSessionFactory(SessionFactory sessionFactory);
	
	/**
	 * Metodo: Consulta la reason de un errormessage por su codigo
	 * @param reasonCode Codigo de la reason
	 * @return ErrorReason Reason encontrada
	 * @author jforero 11/08/2010
	 */
	public ErrorReason getErrorReasonByReasonCode(String reasonCode);
	
	
	/**
	 * Metodo: Consulta todos los ErrorReasons
	 * @return List<ErrorReason> Datos de los errorreasons consultados
	 * @author jforero 11/08/2010
	 */
	public List<ErrorReasonVO> getAllErrorReasons();
	
}
