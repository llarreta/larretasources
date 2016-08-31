package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.ItemStatusVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ItemStatus.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ItemStatusBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ItemStatusVO
	 * @param obj objeto que encapsula la información de un ItemStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createItemStatus(ItemStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ItemStatusVO
	 * @param obj objeto que encapsula la información de un ItemStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateItemStatus(ItemStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ItemStatusVO
	 * @param obj información del ItemStatusVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteItemStatus(ItemStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ItemStatusVO por su identificador
	 * @param id identificador del ItemStatusVO a ser consultado
	 * @return objeto con la información del ItemStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ItemStatusVO getItemStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ItemStatusVO almacenados en la persistencia
	 * @return Lista con los ItemStatusVO existentes, una lista vacia en caso que no existan ItemStatusVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ItemStatusVO> getAllItemStatuss() throws BusinessException;

	/**
	 * Metodo: Obtiene un item status por código
	 * @param itemStatusCode código del item status
	 * @return item status por código especificado
	 * @throws BusinessException En caso de error al realizar la consulta por el código especificado
	 * @author jjimenezh
	 */
	public ItemStatusVO getItemStatusByCode(String itemStatusCode)throws BusinessException;

}
