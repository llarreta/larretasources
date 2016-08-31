package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerBulding;
import co.com.directv.sdii.model.pojo.collection.DealerBuildingResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DealerBulding
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerBuldingDAOLocal {

	/**
	 * Metodo:  persiste la información de un DealerBulding
	 * @param obj objeto que encapsula la información de un DealerBulding
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerBulding(DealerBulding obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerBulding
	 * @param obj objeto que encapsula la información de un DealerBulding
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerBulding(DealerBulding obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerBulding
	 * @param obj información del DealerBulding a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerBulding(DealerBulding obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DealerBulding por su identificador
	 * @param id identificador del DealerBulding a ser consultado
	 * @return objeto con la información del DealerBulding dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerBulding getDealerBuldingByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Si hay un DealerBuilding que atiende al edificio con id 'ibsBuildingCode' regresa este objeto.
	 *         De lo contrario, regresa null.
	 * @param  ibsBuildingCode identificador del Bulding 
	 * @return Objeto con la información del DealerBulding asociado con el building identificado por 'ibsBuildingCode' 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<Dealer> getDealersWhoAttendsABuilding(Long ibsBuildingCode, String ocuntryCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerBulding almacenados en la persistencia
	 * @return Lista con los DealerBulding existentes, una lista vacia en caso que no existan DealerBulding en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerBulding> getAllDealerBuldings() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Obtiene DealerBulding por país y estado
	 * @param idCountry - Long identificador del país
	 * @param status - String Estado en el que se encuentra DealerBulding
	 * @return List<DealerBulding>
	 * @throws DAOServiceException en caso de error en la consulta de DealerBulding por país y estado
	 * @throws DAOSQLException en caso de error en la consulta de DealerBulding por país y estado
	 */
	public List<DealerBulding> getDealerBuldingsByCountryAndStatus(Long idCountry, String status) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene todos los edificios para un código postal, y si el edificio ya
	 * tiene un dealer asociado para que lo atienda, se carga la información de este
	 * dealer
	 * @param postalCodeId
	 * @return DealerBuildingResponse
	 * @throws DAOServiceException
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public DealerBuildingResponse getDealerBuildingsByPostalCodeId(Long postalCodeId, RequestCollectionInfo requestCollectionInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los DealerBulding almacenados en la persistencia
	 * que cumplan con los filtros especificados
	 * @param dealerCoverageId
	 * @param buildingId
	 * @return
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public DealerBulding getDealerBuildingByDealerCoverageIdAndBuildingId(
			Long dealerCoverageId, Long buildingId) throws DAOServiceException, DAOSQLException;

}