package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad RefConfirmation.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefConfirmationFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto RefConfirmation
	 * @param obj - RefConfirmationVO  objeto que encapsula la información de un RefConfirmationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefConfirmation(RefConfirmationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un RefConfirmation
	 * @param obj - RefConfirmationVO  objeto que encapsula la información de un RefConfirmationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefConfirmation(RefConfirmationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un RefConfirmation
	 * @param obj - RefConfirmationVO  información del RefConfirmationVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteRefConfirmation(RefConfirmationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un RefConfirmation por su identificador
	 * @param id - Long identificador del RefConfirmation a ser consultado
	 * @return objeto con la información del RefConfirmationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public RefConfirmationVO getRefConfirmationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los RefConfirmation almacenados en la persistencia
	 * @return List<RefConfirmationVO> Lista con los RefConfirmationVO existentes, una lista vacia en caso que no existan RefConfirmationVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<RefConfirmationVO> getAllRefConfirmations() throws BusinessException;
	
	/**
	 * Metodo: Persiste la confirmacion de un elemento NO serializado
	 * CU 34 opcion 7
	 * @param confirmation RefConfirmationVO Objeto que encapsula la informacion de la confirmacion
	 * @param user UserVO Objeto que encapsula la informacion del usuario que realiza la confirmacion
	 * @param referenceId Long Id de la referencia del elemento al que se le desea añadir una confirmacion
	 * @throws BusinessException en caso de error al ejecutar la operación
     * @author jnova
	 */
	public void saveNotSerializedReferenceElementItemConfirmation(RefConfirmationVO confirmation , UserVO user, Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Persiste la confirmacion de un elemento serializado
	 * CU 34 opcion 8
	 * @param confirmation List<RefConfirmationVO> Lista de objetos que encapsulan la informacion de las confirmaciones
	 * @param user UserVO Objeto que encapsula la informacion del usuario que realiza la confirmacion
	 * @param referenceId Long Id de la referencia del elemento al que se le desea añadir una confirmacion
	 * @throws BusinessException en caso de error al ejecutar la operación
     * @author jnova
	 */
	public void saveSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmation , UserVO user, Long referenceId) throws BusinessException;

	/**
	 * Metodo: Persiste la confirmacion de varios elementos NO serializado
	 * CU 34 opcion 7
	 * @param confirmation List<RefConfirmationVO> Objetos que encapsulan la informacion de las confirmaciones
	 * @param user UserVO Objeto que encapsula la informacion del usuario que realiza la confirmacion
	 * @param referenceId Long Id de la referencia del elemento al que se le desea añadir una confirmacion
	 * @throws BusinessException en caso de error al ejecutar la operación
     * @author jnova
	 */
	public void saveAllNotSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmations,UserVO user,Long referenceId) throws BusinessException;
}
