package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.DealerInfoDTO;
import co.com.directv.sdii.model.dto.DealerInfoRequestDTO;
import co.com.directv.sdii.model.dto.DealerInfoResponseDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerType;
import co.com.directv.sdii.model.pojo.PostalCode;

/**
 * 
 * Interface Local para la gestion del CRUD de la Entidad Dealers
 * 
 * Fecha de Creaci�n: Mar 3, 2010
 * 
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealersDAOLocal {
	
	public List<Dealer> getDealerByBranchId(Long dealerPrincipalId)
	throws DAOServiceException, DAOSQLException;

	public void createDealer(Dealer obj) throws DAOServiceException,
	DAOSQLException;

	public Dealer getDealerByID(Long id) throws DAOServiceException,
	DAOSQLException;

	public List<Dealer> getDealerByName(String name)
	throws DAOServiceException, DAOSQLException;

	public void updateDealer(Dealer obj) throws DAOServiceException,
	DAOSQLException;

	public void deleteDealer(Dealer obj) throws DAOServiceException,
	DAOSQLException;

	public List<Dealer> getAll() throws DAOServiceException, DAOSQLException;

	public Dealer getDealerByDepotID(String id) throws DAOServiceException,
	DAOSQLException;

	public Dealer getDealerByDepotCodeOrDealerCode(String depotCode,
			Long dealerCode) throws DAOServiceException, DAOSQLException;

	public List<Dealer> getActiveDealerBranchesByDealerId(Long dealerPrincipalId, String isSeller, String isInstaller)
	throws DAOServiceException, DAOSQLException;

	public Dealer getDealerByDealerCode(Long dealerCode)
	throws DAOServiceException, DAOSQLException;

	public List<Dealer> getAllDealersOnlyBasicInfo()
	throws DAOServiceException, DAOSQLException;

	public List<Dealer> getAllActive()
	throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta los Dealers activos por el código postal
	 * @param postalCode - PostalCode
	 * @return List<Dealer>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 */
	public List<Dealer> getAllActiveByPostalCode(PostalCode postalCode)
	throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta los Dealers activos por el Tipo de Dealer y código postal 
	 * @param dealerType - DealerType
	 * @param postalCode - PostalCode
	 * @return List<Dealer>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author gfandino
	 */
	public List<Dealer> getAllActiveByDealerTypeAndPostalCode(DealerType dealerType, PostalCode postalCode)
	throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta los Dealers activos por el Tipo de Dealer y código postal y que se puedan asignar
	 * @param dealerType - DealerType
	 * @param postalCode - PostalCode
	 * @return List<Dealer>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author gfandino
	 */
	public List<Dealer> getAllActiveByDealerTypeAndPostalCodeAndIsAlloc(DealerType dealerType, PostalCode postalCode)
	throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta los Dealer activos por código postal y que sean asignables
	 * @param postalCode - PostalCode
	 * @return List<Dealer>
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author gfandino
	 */
	public List<Dealer> getAllActiveByPostalCodeAndIsAlloc(PostalCode postalCode)
	throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta los Dealer activos por código postal y que sean asignables
	 *         y que atiendan edificios.
	 * @param postalCode - PostalCode
	 * @return List<Dealer>
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author rdelarosa
	 */
	public List<Dealer> getAllActiveByPostalCodeAndIsAllocAndAttendsBuildings(PostalCode postalCode)
	throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene todos los dealers activos que atienden una zona postal (identificada por un codigo postal).
	 * Pueden prestar servicios y atienden edificios.
	 * @param postalCode Codigo postal de la zona postal atendida.
	 * @return La lista de los dealers
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<Dealer> 
	getAllActiveWhoAttendsAPostalZoneAndIsAllocAndAttendsBuildings(PostalCode postalCode)
	throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene la lista de dealeres dado el identificador del país
	 * @param countryId identificador del país
	 * @param isSeller identifica si se trata de un dealer vendedor
	 * @param isInstaller identifica si se trata de un dealer instalador 
	 * @return Lista de dealers dado el identificador
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author jjimenezh Agregado por control de cambios 2010-04-26
	 */
	public List<Dealer> getAllDealersByCountryId(Long countryId, String isSeller, String isInstaller)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Permite consultar los dealer principales que se encuentra registrados en el sistema  
     * asociados a un país.
	 * @param countryId identificador del país
	 * @param isSeller identifica si es un dealer vendedor
	 * @param isInstaller identifica si es un dealer instalador
	 * @return Lista de dealers dado el identificador
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author waguilera
	 */
	public List<Dealer> getMajorDealersByCountryId(Long countryId, String isSeller, String isInstaller)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Obtiene la lista de dealeres dado el identificador del tipo
	 * @param dealerTypeCode identificador del tipo
	 * @return Lista de dealers dado el identificador
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 */
	public List<Dealer> getDealersByDealerTypeCode(String dealerTypeCode)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene el identificador de un dealer dado el código dealer
	 * se agrega el método para optimizar los tiempos de respuesta del sistema
	 * en la creación de work orders desde ibs
	 * @param dealerCode código del dealer
	 * @return identificador del dealer, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public Long getDealerIdByDealerCode(Long dealerCode)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene todas las sucursales del dealer dado el identificador de la casa matríz
	 * @param dealerId identificador de la casa matríz
	 * @return Lista con la información de las compañías
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public List<Dealer> getDealerBranchesByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene todas las sucursales del dealer dado el identificador de la casa matríz
	 * @param dealerId identificador de la casa matríz
	 * @param isSeller identifica si es vendedor
	 * @param isInstaller identifica si es instalador
	 * @return Lista con la información de las compañías
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException En caso de error
	 * @author carlopez
	 */
	public List<Dealer> getDealerBranchesByDealerIdAndFilter(Long dealerId, String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: consulta los dealer por id y tipo
	 * @param id Identificador del dealer
	 * @param dealerTypeId Identificador del tipo de dealer
	 * @return Dealer Datos del dealer consultado
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author jforero 05/08/2010
	 */
	public Dealer getDealerByCodeAndType(Long id, String dealerTypeId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta por códido de tipo dealer y id de país
	 * @param dealerTypeCode código del tipo de dealer
	 * @param countryId identificador del país
	 * @return lista con los dealers que cumplen con los criterios de filtro
	 * @throws DAOServiceException en caso de error al realizar la consulta
	 * @throws DAOSQLException en caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	public List<Dealer> getDealersByDealerTypeCodeAndCountryId(String dealerTypeCode, Long countryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Obtiene el identificador del dealer dado el código del mismo
	 * @param dealerCode código del dealer
	 * @param countryid identificador del país
	 * @return identificador del dealer
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Long getDealerIdByDealerCodeAndCountryId(Long dealerCode, Long countryid)throws DAOServiceException, DAOSQLException;

	/**
	 * Obtiene un dealer por el código y el país
	 * @param dealerCode código del dealer a ser consultado
	 * @param countryId identificador del país al que pertenece el dealer
	 * @return dealer
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Dealer getDealerByDealerCodeAndCountryId(Long dealerCode, Long countryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene todas las compañías instaladoras por identificador de país que están activas y que su tipo de dealer permite asignación
	 * @param countryId identificador del país
	 * @param isSEller identifica si se trata de un dealer 
	 * @param isInstaller identifica si se trata de un dealer
	 * @return lista de compañías en el país
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public List<Dealer> getAllActiveByCountryIdAndIsAlloc(Long countryId, String isSeller, String isInstaller)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los codigos de los dealers que se encuentran creados que corresponen a los que se mandan por parametro
	 * @param dealerCodes String con codigos concatenado 
	 * @return List<String> Lista con codigos de dealers creados en el sistema
	 * @throws DAOServiceException En caso de error al realizar la consulta
	 * @throws DAOSQLException En caso de error al realizar la consulta
	 */
	public List<Long> getDealersCodesByDealerCode(String dealerCodes) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Retorna Los Dealers principales por pais
	 * @param countryId Long - id del pais
	 * @param isSeller determina si el dealer es vendedor
	 * @param isInstaller determina si el dealer es instalador 
	 * @return List<Dealer>  - Listado de Dealers's principales
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author jalopez
	 */
	public List<Dealer> getPrincipalsDealersByAndCountryId(Long countryId, String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los dealers por tipo de dealer, por pais y por estado
	 * @param dealerTypeCode Tipo de dealer
	 * @param countryId Id del pais
	 * @param dealerStatus Estado del dealer
	 * @return Lista de dealers que cumplen con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public List<Dealer> getDealersByDealerTypeCodeAndCountryIdAnStatusCode(String dealerTypeCode, Long countryId,String dealerStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta la informacion de un dealer por codigo depot, por codigo de dealer y por pais
	 * @param dealerCode Codigo de dealer
	 * @param depotCode Codigo depot del dealer
	 * @param countryId Identificador del pais del dealer
	 * @return Dealer objeto que cumple con los parametro del filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public Dealer getDealerByDepotCodeOrDealerCode(Long dealerCode, String depotCode, Long countryId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de compañías por el identificador del tipo de dealer
	 * @param dealerTypeId identificador del tipo de dealers
	 * @return Lista con las compañías cuyo tipo 
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<Dealer> getDealersByDealerTypeId(Long dealerTypeId) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Obtiene una lista de compañías por el código del país y el tipo de dealer
	 * @param countryId id del país
	 * @param dealerTypeCode código del tipo de dealer
	 * @return List<Dealer>  - Listado de Dealers's que coinciden con los parámetros de búsqueda
	 * @author wjimenez
	 */
	public List<Dealer> getDealersByCountryIdAndTypeCode(Long countryId,
			String dealerTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los dealer que estan activos y que tienen configuracion en dealer_details
	 * @param countryId
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<Dealer> getDealersForGenerateKpis(Long countryId) throws DAOSQLException, DAOServiceException;
	
	/**
	 * 
	 * Metodo: Consulta un dealer por su codigo, codigo de tipo de dealer y pais
	 * @param dealerCode codigo de dealer
	 * @param dealerTypeCode codigo de tipo de dealer
	 * @param countryId id de pais
	 * @param statusCode estado del dealer
	 * @return Dealer que cumple con los parametros
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Dealer getDealerByDealerCodeAndTypeCodeAndCountryIdAndStatus(Long dealerCode, String dealerTypeCode, Long countryId,String statusCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta las sucursales de un dealer por tipo y por pais
	 * @param dealerCode codigo de la compañia principal
	 * @param dealerTypeCode tipo de las sucursales
	 * @param countryId pais de las sucursales
	 * @param statusCode estado del dealer
	 * @return Lista de sucursales
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Dealer> getDealerBranchesByDealerCodeAndBranchesTypesAndCountryAndStatus(Long dealerCode,String dealerTypeCode, Long countryId,String statusCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene un dealer por el código, país y estado
	 * @param dealerCode código del dealer a ser consultado
	 * @param countryId identificador del país al que pertenece el dealer
	 * @param statusCode codigo de estado del dealer
	 * @return dealer
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public Dealer getDealerByDealerCodeAndCountryIdAndStatusCode(Long dealerCode, Long countryId,String statusCode)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la compañía para los filtros indicados
	 * @param dealerCode
	 * @param depotCode
	 * @return Dealer
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author wjimenez
	 */
	public Dealer getDealerByDealerCodeAndDepotCode(Long dealerCode, String depotCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene  las sucursales del dealer dado identificadores de dealers,
	 * Se retornan unicamente los dealers en estado Normal
	 * @param dealerId identificador de la casa matríz
	 * @return Lista con la información de las compañías
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException En caso de error
	 * @author jnova
	 */
	public List<Dealer> getDealerBranchesByDealerId(List<Long> dealerIds) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Retorna las compañias que tengan cierto tipo de Bodegas.
	 * @param warehouseType
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<Dealer> getDealersByWarehouseTypeCode(String warehouseType, Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Metodo que permite retornar todos los dealer dependiendo del usuario y el tipo de dealer
	 * @param userId id usuario de la interfaz
	 * @param dealerTypeCode codigo del tipo de dealer
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<Dealer> getAllActiveDealerByUserIdAndDealerTypeCode(Long userId,Long countryId,String dealerTypeCode,String warehouseTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene todos los dealers que atienden clientes pares o impares por pais
	 * @param countryId
	 * @param codeEvenOrOdd
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<Dealer> getDealerEvenOrOddByCountryId(Long countryId,String codeEvenOrOdd) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo encargado de consultar los dealers existentes en HSP+ ordenados por nombre de dealer de forma ascendente
	 * @param getHSPDealers parametro de consulta, principalmente el codigo del pais del dealer
	 * @param ri parametros de paginacion de la consulta
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public DealerInfoResponseDTO getHSPDealers(DealerInfoRequestDTO getHSPDealers, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo ri)throws DAOServiceException, DAOSQLException;
	
}