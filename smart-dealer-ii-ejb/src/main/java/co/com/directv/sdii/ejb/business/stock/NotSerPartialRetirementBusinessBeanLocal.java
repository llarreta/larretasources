package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad NotSerPartialRetirement.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface NotSerPartialRetirementBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto NotSerPartialRetirementVO
	 * @param obj objeto que encapsula la información de un NotSerPartialRetirementVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un NotSerPartialRetirementVO
	 * @param obj objeto que encapsula la información de un NotSerPartialRetirementVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un NotSerPartialRetirementVO
	 * @param obj información del NotSerPartialRetirementVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un NotSerPartialRetirementVO por su identificador
	 * @param id identificador del NotSerPartialRetirementVO a ser consultado
	 * @return objeto con la información del NotSerPartialRetirementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public NotSerPartialRetirementVO getNotSerPartialRetirementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los NotSerPartialRetirementVO almacenados en la persistencia
	 * @return Lista con los NotSerPartialRetirementVO existentes, una lista vacia en caso que no existan NotSerPartialRetirementVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<NotSerPartialRetirementVO> getAllNotSerPartialRetirements() throws BusinessException;

}
