package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ShippingOrderElement;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ShippingOrderElement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ShippingOrderElementDAOLocal {

	/**
	 * Metodo:  persiste la información de un ShippingOrderElement
	 * @param obj objeto que encapsula la información de un ShippingOrderElement
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ShippingOrderElement
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ShippingOrderElement
	 * @author gfandino
	 */
	public void createShippingOrderElement(ShippingOrderElement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ShippingOrderElement
	 * @param obj objeto que encapsula la información de un ShippingOrderElement
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ShippingOrderElement
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ShippingOrderElement
	 * @author gfandino
	 */
	public void updateShippingOrderElement(ShippingOrderElement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ShippingOrderElement
	 * @param obj información del ShippingOrderElement a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ShippingOrderElement
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ShippingOrderElement
	 * @author gfandino
	 */
	public void deleteShippingOrderElement(ShippingOrderElement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ShippingOrderElement por su identificador
	 * @param id identificador del ShippingOrderElement a ser consultado
	 * @return objeto con la información del ShippingOrderElement dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ShippingOrderElement por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ShippingOrderElement por ID
	 * @author gfandino
	 */
	public ShippingOrderElement getShippingOrderElementByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ShippingOrderElement almacenados en la persistencia
	 * @return Lista con los ShippingOrderElement existentes, una lista vacia en caso que no existan ShippingOrderElement en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos ShippingOrderElement
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos ShippingOrderElement
	 * @author gfandino
	 */
	public List<ShippingOrderElement> getAllShippingOrderElements() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta los elementos de una Shipping order por el id de shippingorder
	 * @param id Identificador de la shipping order
	 * @return List<ShippingOrderElement> Lista de shipping elements encontrados
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jforero 11/08/2010
	 */
	public List<ShippingOrderElement> getShippingOrderElementBySOID(Long id) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de los elementos de las diferentes shipping orders asociadas a la work order
	 * @param workOrderId identificador de la work order que tiene asociadas las shipping orders
	 * @return Lista con los elementos asociados a las shipping order de la work order
	 * @author jjimenezh
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public List<ShippingOrderElement> getShippingOrderElementByWorkOrderId(Long workOrderId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Borra los elementos de una shipping order asociada a una work order
	 * @param workOrderId identificador de la work order
	 * @throws DAOServiceException en caso de error al borrar la información
	 * @throws DAOSQLException En caso de error al borrar la información
	 * @author
	 */
	public void deleteShippingOrderElementByWoId(Long workOrderId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Control Cambios Creacion Contacts
	 * Retorna los elementos de la shipping order de tipo
	 * MultiSwitch y Antena
	 * @param workOrderId Long - id de la workorder
	 * @return List<ShippingOrderElement>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<ShippingOrderElement> getSOrderElementByWorkOrderIdByElementTypeId(Long workOrderId) throws DAOServiceException, DAOSQLException;

}