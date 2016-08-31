package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.ShippingOrderElementVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ShippingOrderElement.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ShippingOrderElementBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ShippingOrderElementVO
	 * @param obj objeto que encapsula la información de un ShippingOrderElementVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ShippingOrderElementVO
	 * @param obj objeto que encapsula la información de un ShippingOrderElementVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ShippingOrderElementVO
	 * @param obj información del ShippingOrderElementVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ShippingOrderElementVO por su identificador
	 * @param id identificador del ShippingOrderElementVO a ser consultado
	 * @return objeto con la información del ShippingOrderElementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ShippingOrderElementVO getShippingOrderElementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ShippingOrderElementVO almacenados en la persistencia
	 * @return Lista con los ShippingOrderElementVO existentes, una lista vacia en caso que no existan ShippingOrderElementVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ShippingOrderElementVO> getAllShippingOrderElements() throws BusinessException;
	
	
	
	/**
	 * Metodo: Consulta los Shipping order elements correpondientes a una shipping order
	 * @param id Identificador de la shippingorder
	 * @return List<ShippingOrderElementVO> Lista de los shipping elements encontrados
	 * @throws BusinessException 
	 * @author jforero 11/08/2010
	 */
	public List<ShippingOrderElementVO> getShippingOrderElementBySOID(Long id) throws BusinessException;

}
