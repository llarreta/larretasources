package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerServiceSubCategoryWithPc;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DealerServiceSubCategoryWithPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerServiceSubCategoryWithPcDAOLocal {

	/**
	 * Metodo:  persiste la información de un DealerServiceSubCategoryWithPc
	 * @param obj objeto que encapsula la información de un DealerServiceSubCategoryWithPc
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerServiceSubCategoryWithPc
	 * @param obj objeto que encapsula la información de un DealerServiceSubCategoryWithPc
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerServiceSubCategoryWithPc
	 * @param obj información del DealerServiceSubCategoryWithPc a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DealerServiceSubCategoryWithPc por su identificador
	 * @param id identificador del DealerServiceSubCategoryWithPc a ser consultado
	 * @return objeto con la información del DealerServiceSubCategoryWithPc dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerServiceSubCategoryWithPc getDealerServiceSubCategoryWithPcByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerServiceSubCategoryWithPc almacenados en la persistencia
	 * @return Lista con los DealerServiceSubCategoryWithPc existentes, una lista vacia en caso que no existan DealerServiceSubCategoryWithPc en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerServiceSubCategoryWithPc> getAllDealerServiceSubCategoryWithPcs() throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Método: Obtiene la información de DealerServiceSubCategoryWithPc por su estado y país del DealerCovarage
	 * @param countryId - Long identificador del país
	 * @param status - String 
	 * @return List<DealerServiceSubCategoryWithPc>
	 * @throws DAOServiceException en caso de error ejecutando la consulta de DealerServiceSubCategoryWithPc por su estado y país del DealerCovarage
	 * @throws DAOSQLException en caso de error ejecutando la consulta de DealerServiceSubCategoryWithPc por su estado y país del DealerCovarage
	 */
	public List<DealerServiceSubCategoryWithPc> getDealerServiceSubCategoryWithPcsByCountryAndStatus(Long countryId,String status) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los Dealer que atienden la sucategoria del servicio 'serviceCode' en la zona 
	 * postal cuyo codigo postal es 'postalCode' del pais con codigo 'countryCode'
	 * @return Lista con los Dealers que cumplan el proposito de este metodo 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<Dealer> getDealersWhoAttendServiceSubCategoryWithCoverage(String countryCode, String serviceCode, String postalCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los DealerServiceSubCategoryWithPc almacenados en la persistencia que coinciden
	 * con los filtros seleccionados
	 * @param dealerCoverageId
	 * @return List<DealerServiceSubCategoryWithPc>
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public List<DealerServiceSubCategoryWithPc> getDealerServiceSubCategoriesWithPcActiveByDealerCoverageIdOrderedByServiceType(
			Long dealerCoverageId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: obtiene el DealerServiceSubCategoryWithPcVO correspondiente a una cobertura y
	 * categoría de servicio específica
	 * @param dealerCoverageId
	 * @param serviceCategoryId
	 * @return
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public DealerServiceSubCategoryWithPc getByDealerCoverageIdAndServiceCategotyId(
			Long dealerCoverageId, Long serviceCategoryId) throws DAOServiceException, DAOSQLException;
	
}