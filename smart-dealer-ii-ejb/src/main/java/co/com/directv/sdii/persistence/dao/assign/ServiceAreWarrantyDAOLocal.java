package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceAreWarranty;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ServiceAreWarranty
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceAreWarrantyDAOLocal {

	/**
	 * Metodo:  persiste la información de un ServiceAreWarranty
	 * @param obj objeto que encapsula la información de un ServiceAreWarranty
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceAreWarranty(ServiceAreWarranty obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ServiceAreWarranty
	 * @param obj objeto que encapsula la información de un ServiceAreWarranty
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceAreWarranty(ServiceAreWarranty obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ServiceAreWarranty
	 * @param obj información del ServiceAreWarranty a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteServiceAreWarranty(ServiceAreWarranty obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ServiceAreWarranty por su identificador
	 * @param id identificador del ServiceAreWarranty a ser consultado
	 * @return objeto con la información del ServiceAreWarranty dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ServiceAreWarranty getServiceAreWarrantyByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ServiceAreWarranty almacenados en la persistencia
	 * @return Lista con los ServiceAreWarranty existentes, una lista vacia en caso que no existan ServiceAreWarranty en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ServiceAreWarranty> getAllServiceAreWarrantys() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las service warranties de la tabla service are warranties.
	 * @param id, de serviceWarranty
	 * @author waltuzarra 
	 * @return List<ServiceAreWarranty>
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public List<ServiceAreWarranty> getServiceAreWarrantyByServiceWarranty(Long ServiceWarrantyId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: obtiene una lista que solamente contiene el identificador del Service
	 * y el de ServiceTypeWarranty, buscando en las entidades ServiceAreWarranty por el
	 * identificador del serviceTypeWarranty
	 * @param serviceTypeWarrantyId identificador por el que se realiza la búsqueda
	 * @return Listado de ServiceAreWarranty que coincide con el criterio de búsqueda, pero que
	 * no contiene la estructura de información completa
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<ServiceAreWarranty> getServiceAreWarrantiesByServiceTypeWarrantyId(Long serviceTypeWarrantyId) throws DAOSQLException,DAOServiceException;

	/**
	 * Metodo: Elimina toda la configuración de ServiceAreWarranty para un 
	 * serviceTypeWarranty específico
	 * @param serviceTypeWarrantyId
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	void deleteByServiceTypeWarrantyId(Long serviceTypeWarrantyId) throws DAOServiceException, DAOSQLException;

}