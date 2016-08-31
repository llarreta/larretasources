package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ShippingOrderElementVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ShippingOrderElement.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ShippingOrderElementFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ShippingOrderElement
	 * @param obj - ShippingOrderElementVO  objeto que encapsula la información de un ShippingOrderElementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ShippingOrderElement
	 * @param obj - ShippingOrderElementVO  objeto que encapsula la información de un ShippingOrderElementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ShippingOrderElement
	 * @param obj - ShippingOrderElementVO  información del ShippingOrderElementVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ShippingOrderElement por su identificador
	 * @param id - Long identificador del ShippingOrderElement a ser consultado
	 * @return objeto con la información del ShippingOrderElementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ShippingOrderElementVO getShippingOrderElementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ShippingOrderElement almacenados en la persistencia
	 * @return List<ShippingOrderElementVO> Lista con los ShippingOrderElementVO existentes, una lista vacia en caso que no existan ShippingOrderElementVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ShippingOrderElementVO> getAllShippingOrderElements() throws BusinessException;

}
