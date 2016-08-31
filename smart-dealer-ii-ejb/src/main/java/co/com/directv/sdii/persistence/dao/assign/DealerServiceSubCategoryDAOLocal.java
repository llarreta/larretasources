package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerServiceSubCategory;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DealerServiceSubCategory
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerServiceSubCategoryDAOLocal {

	/**
	 * Metodo:  persiste la información de un DealerServiceSubCategory
	 * @param obj objeto que encapsula la información de un DealerServiceSubCategory
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerServiceSubCategory(DealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerServiceSubCategory
	 * @param obj objeto que encapsula la información de un DealerServiceSubCategory
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerServiceSubCategory(DealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerServiceSubCategory
	 * @param obj información del DealerServiceSubCategory a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerServiceSubCategory(DealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DealerServiceSubCategory por su identificador
	 * @param id identificador del DealerServiceSubCategory a ser consultado
	 * @return objeto con la información del DealerServiceSubCategory dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerServiceSubCategory getDealerServiceSubCategoryByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerServiceSubCategory almacenados en la persistencia
	 * @return Lista con los DealerServiceSubCategory existentes, una lista vacia en caso que no existan DealerServiceSubCategory en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerServiceSubCategory> getAllDealerServiceSubCategorys() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene las compañías que atienden una categoría de servicio de un servicio asociado
	 * @param countryCode código del país
	 * @param serviceCode código del servicio
	 * @return Lista de compañías que atienden la categoría del servicio especificado
	 * @throws DAOServiceException en caso de error en la consulta
	 * @throws DAOSQLException  en caso de error en la consulta
	 * @author jjimenezh
	 */
	public List<Dealer> getDealersWhoAttendServiceCategory(String countryCode, String serviceCode)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las companias que atienden una subcategoría de servicio de un servicio asociado
	 * @param countryCode código del país
	 * @param serviceCode código del servicio
	 * @return Lista de compañías que atienden la categoría del servicio especificado
	 * @throws DAOServiceException en caso de error en la consulta
	 * @throws DAOSQLException  en caso de error en la consulta
	 * @author rdelarosa@intergrupo.com
	 */
	public List<Dealer> getDealersWhoAttendServiceSubCategory(String countryCode, String serviceCode)throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Metodo: Obtiene la configuración completa de todos los DealerServiceSubCategory existentes
	 * para un dealer específico. Esto significa que si un DealerServiceSubCategory no se ha configurado para
	 * una categoría específica, también se retornan estos registros para que se les agregue la configuración
	 * y se puedam persistir.
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @param countryId 
	 * @return List<DealerServiceSubCategory> Lista con los DealerServiceSubCategory correspondientes. 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<DealerServiceSubCategory> getAllConfigurationActiveByDealerIdAndCountryIdOrderedByServiceType(Long dealerId, Long countryId) throws DAOServiceException, DAOSQLException;
	
}