package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerWeekCapacity;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DealerWeekCapacity
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerWeekCapacityDAOLocal {

	/**
	 * Metodo:  persiste la información de un DealerWeekCapacity
	 * @param obj objeto que encapsula la información de un DealerWeekCapacity
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createDealerWeekCapacity(DealerWeekCapacity obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerWeekCapacity
	 * @param obj objeto que encapsula la información de un DealerWeekCapacity
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateDealerWeekCapacity(DealerWeekCapacity obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerWeekCapacity
	 * @param obj información del DealerWeekCapacity a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteDealerWeekCapacity(DealerWeekCapacity obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DealerWeekCapacity por su identificador
	 * @param id identificador del DealerWeekCapacity a ser consultado
	 * @return objeto con la información del DealerWeekCapacity dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public DealerWeekCapacity getDealerWeekCapacityByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerWeekCapacity almacenados en la persistencia
	 * @return Lista con los DealerWeekCapacity existentes, una lista vacia en caso que no existan DealerWeekCapacity en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<DealerWeekCapacity> getAllDealerWeekCapacitys() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la configuración de capacidad de una semana por los filtros
	 * especificados
	 * @param countryId identificador del país
	 * @param dealerId identificador de la compañía instaladora
	 * @param serviceHourId identificador de la jornada
	 * @param serviceSuperCategoryId identificador de la super categoría
	 * @return Información de la capacidad configurada para la semana tipo por jornada y super categoría de servicio
	 * @author jjimenezh
	 */
	public DealerWeekCapacity getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(
			Long countryId, Long dealerId, Long serviceHourId,
			Long serviceSuperCategoryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la configuración de capacidad de una semana por los filtros
	 * @param dealerId identificador del dealer
	 * @return List<DealerWeekCapacity>
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<DealerWeekCapacity> getDealerWeekCapacityByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;

}