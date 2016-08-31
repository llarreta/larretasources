package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.RefIncTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad RefIncType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefIncTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto RefIncTypeVO
	 * @param obj objeto que encapsula la información de un RefIncTypeVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefIncType(RefIncTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RefIncTypeVO
	 * @param obj objeto que encapsula la información de un RefIncTypeVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefIncType(RefIncTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefIncTypeVO
	 * @param obj información del RefIncTypeVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRefIncType(RefIncTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un RefIncTypeVO por su identificador
	 * @param id identificador del RefIncTypeVO a ser consultado
	 * @return objeto con la información del RefIncTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefIncTypeVO getRefIncTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefIncTypeVO almacenados en la persistencia
	 * @return Lista con los RefIncTypeVO existentes, una lista vacia en caso que no existan RefIncTypeVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RefIncTypeVO> getAllRefIncTypes() throws BusinessException;

}
