package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad RefConfirmation.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefConfirmationBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto RefConfirmationVO
	 * @param obj objeto que encapsula la información de un RefConfirmationVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefConfirmation(RefConfirmationVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RefConfirmationVO
	 * @param obj objeto que encapsula la información de un RefConfirmationVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefConfirmation(RefConfirmationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefConfirmationVO
	 * @param obj información del RefConfirmationVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRefConfirmation(RefConfirmationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un RefConfirmationVO por su identificador
	 * @param id identificador del RefConfirmationVO a ser consultado
	 * @return objeto con la información del RefConfirmationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefConfirmationVO getRefConfirmationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefConfirmationVO almacenados en la persistencia
	 * @return Lista con los RefConfirmationVO existentes, una lista vacia en caso que no existan RefConfirmationVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RefConfirmationVO> getAllRefConfirmations() throws BusinessException;
	
	/**
	 * Metodo: Persiste la confirmacion de varios elementos NO serializado
	 * CU 34 opcion 7
	 * @param confirmations List<RefConfirmationVO> Objetos que encapsulan la informacion de las confirmaciones
	 * @param user UserVO Objeto que encapsula la informacion del usuario que realiza la confirmacion
	 * @param referenceId Long Id de la referencia del elemento al que se le desea añadir una confirmacion
	 * @throws BusinessException en caso de error al ejecutar la operación
     * @author jnova
	 */
	public void saveAllNotSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmations,UserVO user,Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Persiste la confirmacion de un elemento NO serializado
	 * CU 34 opcion 7
	 * @param confirmation RefConfirmationVO Objeto que encapsula la informacion de la confirmacion
	 * @param user UserVO Objeto que encapsula la informacion del usuario que realiza la confirmacion
	 * @param referenceId Long Id de la referencia del elemento al que se le desea añadir una confirmacion
	 * @throws BusinessException en caso de error al ejecutar la operación
     * @author jnova
	 */
	public void saveNotSerializedReferenceElementItemConfirmation(RefConfirmationVO confirmation,UserVO user,Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Persiste la confirmacion de un elemento serializado
	 * CU 34 opcion 8
	 * @param List<RefConfirmationVO> RefConfirmationVO Objetos que encapsulan la informacion de la confirmacion
	 * @param user UserVO Objeto que encapsula la informacion del usuario que realiza la confirmacion
	 * @param referenceId Long Id de la referencia del elemento al que se le desea añadir una confirmacion
	 * @throws BusinessException en caso de error al ejecutar la operación
     * @author jnova
	 */
	public void saveSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmation,UserVO user,Long referenceId) throws BusinessException;

}
