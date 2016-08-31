package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Delivery;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Delivery
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DeliveryDAOLocal {

	/**
	 * Metodo:  persiste la información de un Delivery
	 * @param obj objeto que encapsula la información de un Delivery
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDelivery(Delivery obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Delivery
	 * @param obj objeto que encapsula la información de un Delivery
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDelivery(Delivery obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Delivery
	 * @param obj información del Delivery a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDelivery(Delivery obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Delivery por su identificador
	 * @param id identificador del Delivery a ser consultado
	 * @return objeto con la información del Delivery dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public Delivery getDeliveryByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Delivery almacenados en la persistencia
	 * @return Lista con los Delivery existentes, una lista vacia en caso que no existan Delivery en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<Delivery> getAllDeliverys() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene los envios realizados a partir de un numero de remision
	 * @param referenceId Long numero de remision
	 * @return List<Delivery> lista de los envios asociados a la remision
	 * @throws DAOServiceException En caso de error al tratar de consultas los envios
	 * @throws DAOSQLException En caso de error al tratar de consultas los envios
	 * @author jnova
	 */
	public List<Object[]> getDeliveriesByReferenceID(Long referenceId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Calcula el total de datos de ingreso de un elemento en una remision
	 * CU 34
	 * @param referenceId Long Id de la remision
	 * @param elementId Long Id del elemento
	 * @return Double Total de datos de ingresos de un elemento
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 */
	public Double getTotalDeliveriesByElementIdAndReferenceId(Long referenceId , Long elementId) throws DAOServiceException, DAOSQLException;
}