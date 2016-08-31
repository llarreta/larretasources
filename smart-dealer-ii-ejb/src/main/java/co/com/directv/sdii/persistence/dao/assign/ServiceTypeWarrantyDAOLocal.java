package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceTypeWarranty;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ServiceTypeWarranty
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceTypeWarrantyDAOLocal {

	/**
	 * Metodo:  persiste la información de un ServiceTypeWarranty
	 * @param obj objeto que encapsula la información de un ServiceTypeWarranty
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceTypeWarranty(ServiceTypeWarranty obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ServiceTypeWarranty
	 * @param obj objeto que encapsula la información de un ServiceTypeWarranty
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceTypeWarranty(ServiceTypeWarranty obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ServiceTypeWarranty
	 * @param obj información del ServiceTypeWarranty a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteServiceTypeWarranty(ServiceTypeWarranty obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ServiceTypeWarranty por su identificador
	 * @param id identificador del ServiceTypeWarranty a ser consultado
	 * @return objeto con la información del ServiceTypeWarranty dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ServiceTypeWarranty getServiceTypeWarrantyByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ServiceTypeWarranty almacenados en la persistencia
	 * @return Lista con los ServiceTypeWarranty existentes, una lista vacia en caso que no existan ServiceTypeWarranty en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ServiceTypeWarranty> getAllServiceTypeWarrantys() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las service warranties de un servicio dependiendo del pais
	 * @param serviceTypeId
	 * @param countryCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author waltuzarra
	 */
	public List<ServiceTypeWarranty> getServiceTypeWarrantiesByServiceType(Long serviceTypeId, Long countryCode) throws DAOServiceException, DAOSQLException ;

	/**
	 * Metodo: obtiene la configuración de garantías para todos los tipos de servicio existentes
	 * en un país específico
	 * @param countryId identificador del país
	 * @return List<ServiceTypeWarranty> listado de garantías. Si un tipo de servicio no tiene garantía configurada,
	 * se incluye un ítem en la lista con el país y tipo de servicio asignados. 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<ServiceTypeWarranty> getServiceTypeWarrantiesConfigurationByCountryId(Long countryId) throws DAOSQLException, DAOServiceException;
	
}