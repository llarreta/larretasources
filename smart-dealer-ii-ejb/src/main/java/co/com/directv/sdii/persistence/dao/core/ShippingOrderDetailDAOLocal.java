package co.com.directv.sdii.persistence.dao.core;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ShippingOrderDetail;
import co.com.directv.sdii.model.pojo.Technology;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ShippingOrderDetail
 * 
 * Fecha de Creación: 1/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ShippingOrderDetailDAOLocal {

	/**
	 * 
	 * Metodo: Crea un detalle de shipping order
	 * @param detail Detalle de shipping que se desea crear
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public void createShippingOrderDetail(ShippingOrderDetail detail) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Actualiza un detalle de de shipping order
	 * @param detail Detalle que se va a actualizar
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public void updateShippingOrderDetail(ShippingOrderDetail detail) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene todos los detalles de una shipping order por el id de la shipping order
	 * @param shippingOrderId Identificador de la Shipping Order
	 * @return List<ShippingOrderDetail> Lista de detalles de SO que se encuentran asociados al id enviado
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<ShippingOrderDetail> getShippingOrderDetailsByShippingOrderId(Long shippingOrderId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene todos los detalles de una shipping order por el codigo de la shipping order
	 * @param shippingOrderCode Codigo de la Shipping Order
	 * @return List<ShippingOrderDetail> Lista de detalles de SO que se encuentran asociados al codigo enviado
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<ShippingOrderDetail> getShippingOrderDetailsByShippingOrderCode(String shippingOrderCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Elimina los detalles de una shipping order por el identificador de la workorder a la que esta asociada la SO
	 * @param workOrderId Identificador de la SO
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public void deleteShippingOrderDetailsByWorkOrderId(Long workOrderId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene las tecnologias asociadas a una shipping order
	 * @param soId Identificador de la SO
	 * @return Lista de tecnologias asociadas a una SO
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Technology> getShippingOrderTechnologiesBySOId(Long soId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene la tecnologia de una SO por codigo de tecnologia
	 * @param soId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Technology> getShippingOrderTechnologiesBySOIdAndTecCode(Long soId,String tecCode) throws DAOServiceException, DAOSQLException;
}
