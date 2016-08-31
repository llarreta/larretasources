package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.DeliveryVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Delivery.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DeliveryBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DeliveryVO
	 * @param obj objeto que encapsula la información de un DeliveryVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDelivery(DeliveryVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DeliveryVO
	 * @param obj objeto que encapsula la información de un DeliveryVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDelivery(DeliveryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DeliveryVO
	 * @param obj información del DeliveryVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDelivery(DeliveryVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DeliveryVO por su identificador
	 * @param id identificador del DeliveryVO a ser consultado
	 * @return objeto con la información del DeliveryVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DeliveryVO getDeliveryByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DeliveryVO almacenados en la persistencia
	 * @return Lista con los DeliveryVO existentes, una lista vacia en caso que no existan DeliveryVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DeliveryVO> getAllDeliverys() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informacion de todos los deliveries asociados a una referencia dandole valor al usuario 
	 * que realizo el delivery
	 * @param referenceId Long ID de la referencia
	 * @return List<DeliveryVO> Lista con los deliveries asociados a una referencia
	 * @throws BusinessException En caso de error en la consulta
	 * @author jnova
	 */
	public List<DeliveryVO> getDeliveriesByReferenceID(Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: almacena las entregas asociadas a una remision
	 * @param referenceId Long ID de la remision
	 * @param deliveries List<DeliveryVO> Lista de entregas a almacenar
	 * @throws BusinessException En caso de error en el momento de almacenar las entregas
	 * @author jnova
	 */
	public void createDeliveries(Long referenceId , List<DeliveryVO> deliveries) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informacion de todos los deliveries asociados a una referencia dandole valor al usuario 
	 * que realizo el delivery y eliminando informacion para bajar el peso a respuesta SOAP CU 34
	 * @param referenceId Long ID de la referencia
	 * @return List<DeliveryVO> Lista con los deliveries asociados a una referencia
	 * @throws BusinessException En caso de error en la consulta
	 * @author jnova
	 */
	public List<DeliveryVO> getDeliveriesForDetailsByReferenceID(Long referenceId) throws BusinessException;
	
	/**
	 * Metodo: Calcula el total de datos de ingreso de un elemento en una remision 
	 * CU 34
	 * @param referenceId Long Id de la remision
	 * @param elementId Long Id del elemento
	 * @return Double Total de datos de ingresos de un elemento
	 * @throws BusinessException En caso de error en la consulta
	 */
	public Double getTotalDeliveriesByElementIdAndReferenceId(Long referenceId , Long elementId) throws BusinessException;

}
