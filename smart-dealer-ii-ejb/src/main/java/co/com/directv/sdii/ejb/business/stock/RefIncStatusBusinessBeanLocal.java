package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.RefIncStatusVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad RefIncStatus.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefIncStatusBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto RefIncStatusVO
	 * @param obj objeto que encapsula la información de un RefIncStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefIncStatus(RefIncStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RefIncStatusVO
	 * @param obj objeto que encapsula la información de un RefIncStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefIncStatus(RefIncStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefIncStatusVO
	 * @param obj información del RefIncStatusVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRefIncStatus(RefIncStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un RefIncStatusVO por su identificador
	 * @param id identificador del RefIncStatusVO a ser consultado
	 * @return objeto con la información del RefIncStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefIncStatusVO getRefIncStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefIncStatusVO almacenados en la persistencia
	 * @return Lista con los RefIncStatusVO existentes, una lista vacia en caso que no existan RefIncStatusVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RefIncStatusVO> getAllRefIncStatuss() throws BusinessException;

}
