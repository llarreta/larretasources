package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DeliveryVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Delivery.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DeliveryFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Delivery
	 * @param obj - DeliveryVO  objeto que encapsula la información de un DeliveryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDelivery(DeliveryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Delivery
	 * @param obj - DeliveryVO  objeto que encapsula la información de un DeliveryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDelivery(DeliveryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Delivery
	 * @param obj - DeliveryVO  información del DeliveryVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDelivery(DeliveryVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Delivery por su identificador
	 * @param id - Long identificador del Delivery a ser consultado
	 * @return objeto con la información del DeliveryVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DeliveryVO getDeliveryByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Delivery almacenados en la persistencia
	 * @return List<DeliveryVO> Lista con los DeliveryVO existentes, una lista vacia en caso que no existan DeliveryVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DeliveryVO> getAllDeliverys() throws BusinessException;

}
