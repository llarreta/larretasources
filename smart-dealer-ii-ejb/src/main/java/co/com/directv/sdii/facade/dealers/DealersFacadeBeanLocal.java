package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.DealerInfoRequestDTO;
import co.com.directv.sdii.model.dto.DealerInfoResponseDTO;
import co.com.directv.sdii.model.dto.DealersCodesDTO;
import co.com.directv.sdii.model.dto.InfoDealerCountryWarehouseDTO;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 *
 * Interfaz que define la Session Facade de las operaciones a realizar para el módulo de Dealers
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface DealersFacadeBeanLocal {

	/**
	 * Método que permite crear nuevos Dealers.
	 * @param obj
	 * @throws BusinessException
	 */
    public void createDealer(DealerVO obj) throws BusinessException;

    /**
     * Método que permite consultar un Dealers por ID.
     */
    public DealerVO getDealerByID(Long id) throws BusinessException;

    /**
     * Método que permite consultar un Dealer por Nombre.
     */
    public List<DealerVO> getDealerByName(String name) throws BusinessException;


    /**
     * metodo que obtiene un dealer dependiendo de su Depot ID
     */
    public DealerVO getDealerByDepotID(String id) throws BusinessException;

        /**
     * Método que permite actualizar un Dealer.
     */
    public void updateDealer(DealerVO obj) throws BusinessException;

    /**
     * Método que permite eliminar un Dealer.
     */

    public void deleteDealer(DealerVO obj) throws BusinessException;

    /**
     * Método que obtiene todos los Dealer almacenados.
     */

    public List<DealerVO> getAll() throws BusinessException;

    /**
     * 
     * @param dealersDTO
     * @throws BusinessException
     */
    public void createDealersFromIBS(List<DealersCodesDTO> dealersDTO) throws BusinessException;

    /**
     * 
     * @param depotCode
     * @param dealerCode
     * @return
     * @throws BusinessException
     */
    public DealerVO getDealerByDepotCodeOrDealerCode(String depotCode, Long dealerCode) throws BusinessException;

	public List<DealerVO> getAllDealersOnlyBasicInfo()throws BusinessException;

	/**
	 * Obtiene la lista de dealeres dado el identificador del país
	 * @param countryId identificador del país
	 * @param isSeller identifica si un dealer es vendedor
	 * @param isInstaller identifica si un dealer es instalador
	 * @return Lista de dealers dado el identificador
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author jjimenezh Agregado por control de cambios 2010-04-26
	 */
	public List<DealerVO> getAllDealersByCountryId(Long countryId, String isSeller, String isInstaller)throws BusinessException;

	/**
	 * Metodo: Permite consultar los dealer principales que se encuentra registrados en el sistema  
     * asociados a un país.
	 * @param countryId identificador del país
	 * @param isSeller identifica si un dealer es vendedor
	 * @param isInstaller identifica si un dealer es instalador
	 * @return Lista de dealers dado el identificador
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * @author waguilera
	 */
	public List<DealerVO> getMajorDealersByCountryId(Long countryId, String isSeller, String isInstaller)throws BusinessException;
	
	/**
	 * Metodo: Obtiene todas las sucursales del dealer dado el identificador de la casa matríz
	 * @param dealerId identificador de la casa matríz
	 * @return Lista con la información de las compañías
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public List<DealerVO> getDealerBranchesByDealerId(Long dealerId)throws BusinessException;
	

	/**
	 * Metodo: Obtiene todas las sucursales del dealer dado el identificador de la casa matríz
	 * @param dealerId identificador de la casa matríz
	 * @param isSeller identifica si el dealer es vendedor
	 * @parame isInstaller identifica si el dealer es installador	
	 * @return Lista con la información de las compañías
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public List<DealerVO> getDealerBranchesByDealerIdAndFilter(Long dealerId, String isSeller, String isInstaller)throws BusinessException;
	

	/**
	 * Caso de uso IVR - 02 Consulta Depot
	 * 
	 * Metodo: Consulta el dealer por id y tipo
	 * @param dealerId Identificador del dealer
	 * @param dealerType Identificador del tipo de dealer
	 * @return DealerVO Datos del dealer consultado
	 * @throws BusinessException 
	 * @author jforero 05/08/2010
	 */
	public DealerVO getDealerByDealerCodeAndType(Long dealerId,String dealerType) throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de dealers dados los criterios de filtro código del tipo de dealer y país
	 * @param dealerTypeCode código del tipo de dealer
	 * @param countryId identificador del país
	 * @return Lista con los dealer cuyas propiedades coincidan con los criterios seleccionados
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<DealerVO> getDealersByDealerTypeCodeAndCountryId(String dealerTypeCode, Long countryId)throws BusinessException;
    
	/**
	 * 
	 * Metodo: Retorna Los Dealers principales por pais
	 * @param countryId Long - id del pais
	 * @param isSeller determina si un dealer es vendedor
	 * @param isInstaller determina si un dealer es instalador
	 * @return List<DealerVO>  - Listado de Dealers's principales
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author jalopez
	 */
	public List<DealerVO> getPrincipalsDealersByAndCountryId(Long countryId, String isSeller, String isInstaller) throws BusinessException;
	
	/**
	 * Metodo: Actualiza un dealer dado su id de SDII consultando la informacion en IBS
	 * @param id Identiifcador del dealer en SDII
	 * @throws BusinessException En caso de algun error al actualizar la informacion
	 * @author jnova
	 */
    public void updateDealerFromIbs(Long id) throws BusinessException;
    
   /**
    * Metodo: Obtiene la informacion de un dealer por su codigo depot, su codigo de dealer y su pais
    * @param dealerCode Codigo de dealer
    * @param depotCode Codigo depot del dealer 
    * @param countryId Identificador del pais del dealer
    * @return DealerVO Informacion del dealer que se esta consultando
    * @throws BusinessException En caso de error en la consulta
    * @author jnova
    */
    public DealerVO getDealerByDepotCodeAndDealerCodeAndCountryId(Long dealerCode, String depotCode, Long countryId) throws BusinessException;
    
    /**
	 * Metodo: Obtiene todas las compañías instaladoras por identificador de país que están activas y que su tipo de dealer permite asignación
	 * @param countryId identificador del país
	 * @param isSeller identifica si un dealer es vendedor
	 * @param isInstaller identifica si un dealer es instalador
	 * @return lista de compañías en el país
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public List<DealerVO> getAllActiveByCountryIdAndIsAlloc(Long countryId, String isSeller, String isInstaller) throws BusinessException;
	
	/**
	 * Metodo: Obtiene  las sucursales del dealer dado identificadores de dealers,
	 * Se retornan unicamente los dealers en estado Normal
	 * @param dealerId identificador de la casa matríz
	 * @return Lista con la información de las compañías
	 * @throws BusinessException En caso de error
	 * @author jnova
	 */
	public List<DealerVO> getDealerBranchesByDealerId(List<Long> dealerIds)throws BusinessException;
    
	/**
	 * Metodo: Obtiene una lista de las compañías principales y dentro de cada una una lista
	 * con sus sucursales con la información básica de id, código, depot, nombre, dirección y representante legal
	 * @param countryId identificador del país
	 * @return lista con las compañías principales y dentro de cada una una lista
	 * con sus sucursales con la información básica de id, código, depot, nombre, dirección y representante legal
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author jjimenezh
	 */
	public List<DealerVO> getActiveMajorDealersAndBranchesByCountryId(Long countryId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las compañías principales y dentro de cada una una lista
	 * con sus sucursales con la información básica de id, código, depot, nombre, dirección y representante legal
	 * @param userId identificador del usuario
	 * @param isSeller identifica si un dealer es vendedor
	 * @param isInstaller identifica si un dealer es instaldor
	 * @return lista con las compañías principales y dentro de cada una una lista
	 * con sus sucursales con la información básica de id, código, depot, nombre, dirección y representante legal
	 * @throws BusinessException En caso de error al realizar la operación
	 * @author cduarte
	 */
	public List<DealerVO> getActiveMajorDealersAndBranchesByUserId(Long userId, String isSeller, String isInstaller) throws BusinessException;

	/**
	 * 
	 * Metodo: retorna las compañias segun el tipo de Bodega.
	 * @param warehouseType
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	public List<DealerVO> getDealersByWarehouseTypeCode(String warehouseType, Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Metodo que permite retornar todos los dealer dependiendo del usuario y el tipo de dealer
	 * @param userId id usuario de la interfaz
	 * @param codeLogisticsOperator bandera para indicar si consulta los delaer con codigo operador logistico
	 * @return
	 * @throws BusinessException 
	 * @author cduarte
	 */
	public List<DealerVO> getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode(InfoDealerCountryWarehouseDTO infoDealerCountryWarehouseDTO) throws BusinessException;

	/**
	 * Metodo encargado de consultar los dealers existentes en HSP+ ordenados por nombre de dealer de forma ascendente
	 * @param getHSPDealers parametro de consulta, paginacion, codigo de pais y modo de respuesta (en archivo o en objetos)
	 * @return
	 * @throws BusinessException
	 * @author Aharker
	 */
	public DealerInfoResponseDTO getHSPDealers(DealerInfoRequestDTO getHSPDealers)throws BusinessException;
	
}
