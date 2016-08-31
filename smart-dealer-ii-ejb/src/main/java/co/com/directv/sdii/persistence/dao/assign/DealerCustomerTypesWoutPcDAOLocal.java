package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerCustomerTypesWoutPc;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DealerCustomerTypesWoutPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerCustomerTypesWoutPcDAOLocal {

	/**
	 * Metodo:  persiste la información de un DealerCustomerTypesWoutPc
	 * @param obj objeto que encapsula la información de un DealerCustomerTypesWoutPc
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerCustomerTypesWoutPc
	 * @param obj objeto que encapsula la información de un DealerCustomerTypesWoutPc
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerCustomerTypesWoutPc
	 * @param obj información del DealerCustomerTypesWoutPc a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DealerCustomerTypesWoutPc por su identificador
	 * @param id identificador del DealerCustomerTypesWoutPc a ser consultado
	 * @return objeto con la información del DealerCustomerTypesWoutPc dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerCustomerTypesWoutPc getDealerCustomerTypesWoutPcByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerCustomerTypesWoutPc almacenados en la persistencia
	 * @return Lista con los DealerCustomerTypesWoutPc existentes, una lista vacia en caso que no existan DealerCustomerTypesWoutPc en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerCustomerTypesWoutPc> getAllDealerCustomerTypesWoutPcs() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: A partir de los filtros, obtiene las compañías que atienden el tipo de cliente
	 * @param countryCode código del país
	 * @param customerTypeCode código del tipo de cliente
	 * @return Lista de compañías que atienden el tipo de cliente
	 * @author waltuzarra
	 */
	public List<Dealer> getDealersWhoAttendCustomerTypeWoutCoverage(String countryCode, String customerTypeCode, String customerClassCode)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los DealerCustomerTypesWoutPc existentes
	 * para un dealer específico
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @param countryId 
	 * @return List<DealerCustomerTypesWoutPc> Lista con los DealerCustomerTypesWoutPc correspondientes,
	 * una lista vacia en caso que no se encuentren registros 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	
	public List<DealerCustomerTypesWoutPc> getDealerCustomerTypesWoutPcByDealerIdCountryIdAndCustomerTypeCodeId(
			Long dealerId,Long countryId, Long customerTypeCodeId,String activeStatus)
			throws DAOServiceException, DAOSQLException;
			
	public List<DealerCustomerTypesWoutPc> getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId(Long dealerId, Long countryId) throws DAOServiceException, DAOSQLException;


	public List<Dealer> getDealersWhoAttendCustomerTypeWoutCoverage(
			String countryCode, Long customerClassTypeId)
			throws DAOServiceException, DAOSQLException ;
	
}