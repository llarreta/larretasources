package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ItemStatusVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ItemStatus.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ItemStatusFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ItemStatus
	 * @param obj - ItemStatusVO  objeto que encapsula la información de un ItemStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createItemStatus(ItemStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ItemStatus
	 * @param obj - ItemStatusVO  objeto que encapsula la información de un ItemStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateItemStatus(ItemStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ItemStatus
	 * @param obj - ItemStatusVO  información del ItemStatusVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteItemStatus(ItemStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ItemStatus por su identificador
	 * @param id - Long identificador del ItemStatus a ser consultado
	 * @return objeto con la información del ItemStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ItemStatusVO getItemStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ItemStatus almacenados en la persistencia
	 * @return List<ItemStatusVO> Lista con los ItemStatusVO existentes, una lista vacia en caso que no existan ItemStatusVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ItemStatusVO> getAllItemStatuss() throws BusinessException;

	/**
	 * Metodo: Obtiene el estado de un ítem por el código
	 * @param itemStatusCode código del estado de un Item
	 * @return Estado del item dado el código, nulo en caso que no exista
	 * @throws BusinessException En caso de error al consultar el estado de un item por código
	 * @author jjimenezh
	 */
	public ItemStatusVO getItemStatusByCode(String itemStatusCode)throws BusinessException;

}
