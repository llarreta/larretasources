package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Warehouse.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto WarehouseVO
	 * @param obj objeto que encapsula la información de un WarehouseVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de Warehouse
	 * @author gfandino
	 */
	public WarehouseVO createWarehouse(WarehouseVO obj) throws BusinessException;
	
	/**
	 * Metodo:  INV 123-1234 Permite crear una bodega de transito dada una bodega
	 * @param obj objeto que encapsula la información de un WarehouseVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de Warehouse
	 * @author gfandino
	 */
	public WarehouseVO createWarehouseTransit(WarehouseVO obj) throws BusinessException;
	
	/**
	 * 
	 * Metodo: INV 123-124 Crea bodega de transito a partir del codigo de dealer
	 * @param obj bodega a la cual se le va a crear una bodega de transito
	 * @param warehouseType Tipo de bodega a crear
	 * @return WarehouseVO bodega creada
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseVO createWarehouseTransit(WarehouseVO obj,WarehouseTypeVO warehouseType) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un WarehouseVO
	 * @param obj objeto que encapsula la información de un WarehouseVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de Warehouse
	 * @author gfandino
	 */
	public void updateWarehouse(WarehouseVO obj) throws BusinessException;

	/**
	 * Metodo: activar o inactivar bodegas de transito asociadas a una cuadrilla
	 * @param warehouse bodega principal de la cuadrilla
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de Warehouse
	 * @author clopez
	 */
	public void updateWarehouseTransit(WarehouseVO warehouse, String isActive) throws BusinessException;

	
	/**
	 * Metodo: Borra de la persistencia la información de un WarehouseVO
	 * @param obj información del WarehouseVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de Warehouse
	 * @author gfandino
	 */
	public void deleteWarehouse(WarehouseVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un WarehouseVO por su identificador
	 * @param id identificador del WarehouseVO a ser consultado
	 * @return objeto con la información del WarehouseVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public WarehouseVO getWarehouseByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseVO almacenados en la persistencia
	 * @return Lista con los WarehouseVO existentes, una lista vacia en caso que no existan WarehouseVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las Warehouse
	 * @author gfandino
	 */ 
	public List<WarehouseVO> getAllWarehouses() throws BusinessException;

	
	/**
	 * Método: Consulta todas las bodegas en cualquier estado
	 * @param countryId - identificacor del país
	 * @param code  - String con el código de la ubicación; nulo si no aplica
	 * @param requestCollInfo - WareHouseResponse datos de la paginación 
	 * @return WarehouseInfoResponseDetailDTO con las bodegas paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las Warehouse
	 */ 
	public WarehouseInfoResponseDetailDTO  getAllWarehousesByCountryId(Long countryId, 
																       String code, 
																       RequestCollectionInfo requestCollInfo
	) throws BusinessException;
	
	/**
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
     *	 
	 * Método: Consulta las bodegas segun los parametros
	 * @param countryId - identificacor del país
	 * @param warehouseTypeId  - String con el id del tipo de bodega; opcional, admite nulo
	 * @param dealerId  - String con el id del tipo de la compania; opcional, admite nulo
	 * @param branchId  - String con el id de la sucursal; opcional, admite nulo
	 * @param crewCodeId  - String con el id del tipo de la cuadrilla; opcional, admite nulo
	 * @param requestCollInfo - WareHouseResponse datos de la paginación 
	 * @return WarehouseInfoResponseDetailDTO con las bodegas paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las Warehouse
	 */ 
	public WarehouseInfoResponseDetailDTO  getWhByWhTypeDealerBranchCrewIds( Long countryId, 
																				 String  warehouseTypeId, 
																				 String  dealerId, 
																				 String  branchId,
																				 String  crewCodeId ,
																				 RequestCollectionInfo requestCollInfo	
	) throws BusinessException;

	
	/**
	 * Método: Obtiene las bodegas del país que se encuentran en estado activo
	 * @param countryId - identificación del país
	 * @param dealerId - identificación del país
	 * @return List<WarehouseVO>
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de las bodegas activas
	 * @author gfandino
	 */
	public List<WarehouseVO> getWarehousesByCountryIdAndActive(Long countryId, Long dealerId) throws BusinessException;
	
	/**
	 * Método: Obtiene las bodegas del país que se encuentran en estado activo paginado
	 * @param countryId - String identificación del país
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return WareheouseResponse con la información de la consulta
	 * @throws BusinessException en caso de error consultando las bodegas activas paginadas
	 * @author gfandino
	 */
	public WareHouseResponse getWarehousesByCountryIdAndActive(Long countryId,RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene las bodegas por país
	 * @param countryId identificador del país a ser consultado
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseResponse, lista con las bodegas de un país
	 * @throws BusinessException En caso de error al consultar las bodegas
	 * @author jjimenezh
	 */
	public WareHouseResponse getWarehousesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Método: Obtiene las bodegas del país segun el tipo de dealer: vendedor|instalador 
	 * @param countryId - String identificación del país
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @param isISeller indica si se trata de un dealer vendedor
	 * @param isInstaller indica si se trata de un dealer instalador 
	 * @return WarehouseInfoResponseDTO con la información de la consulta
	 * @throws BusinessException en caso de error consultando las bodegas activas paginadas
	 * @author clopez
	 */
	public WarehouseInfoResponseDTO getWarehousesByCountryIdAndDealerCondition(Long countryId, RequestCollectionInfo requestCollInfo,
				String isSeller, String isInstaller) throws BusinessException;
		
	/**
	 * Metodo: Obtiene la información de un WarehouseVO por su código
	 * @param code - String identificador del WarehouseVO a ser Código
	 * @param country - Long identificador del pa�s
	 * @return WarehouseVO con la información del WarehouseVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Warehouse por código
	 * @author gfandino
	 */
	public WarehouseVO getWarehouseByCodeAndByCountry(String code,Long country) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de las bodegas que corresponden a una cuadrilla y un tipo de bodega
	 * Inv_25 Comparar Inventario Físico de una Bodega  VS  Inventario en SmartDealer
	 * @param crewId - Long cuadrilla a la cual se le consultarán las bodegas
	 * @param whTypeId - Long Tipo de cuadrilla que se va a consultar
	 * @return List<WarehouseVO> al tipo de bodega de la cuadrilla; vacia en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Warehouse por cuadrilla y tipo de bodega
	 * @author gfandino
	 */
	public List<WarehouseVO> getWhByCrewAndWhType(Long crewId,Long whTypeId)throws BusinessException;
	
	/**
	 * Metodo: Permite validar el inventario de elementos serializados
	 * Inv_25 Comparar Inventario Físico de una Bodega  VS  Inventario en SmartDealer
	 * @param whID - Identificador de la bodega
	 * @param serializadosFisicos - List<SerializedVO> Elementos físicos serializados que se van a validar
	 * @param Long countryId - Pais de la bodega
	 * @return List<SerializedVO> Lista de elementos serializos comparados entre Físico y SDII
	 * @throws BusinessException en caso de error al tratar de ejecutar la validación de SerializedVO de una bodega
	 * @author gfandino
	 */
	public List<SerializedVO> validateSerializedInventory(Long whID,List<SerializedVO> serializadosFisicos,Long countryId)throws BusinessException;
	
	/**
	 * Metodo: Permite validar el inventario de elementos no serializados
	 * Inv_25 Comparar Inventario Físico de una Bodega  VS  Inventario en SmartDealer
	 * @param whID - Identificador de la bodega
	 * @param noSerializadosFisicos - List<NotSerializedVO> Elementos físicos no serializados que se van a validar
	 * @param Long countryId - id del pais
	 * @return List<NotSerializedVO> Lista de elementos serializos comparados entre Físico y SDII
	 * @throws BusinessException en caso de error al tratar de ejecutar la validación de NotSerializedVO de una bodega
	 * @author gfandino
	 */
	public List<NotSerializedVO> validateNotSerializedInventory(Long whID,List<NotSerializedVO> noSerializadosFisicos, Long countryId)throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de las bodegas de un dealer
	 * @param dealerId identificador del dealer
	 * @return Lista con la información de las bodegas del dealer, una lista vacia en caso que
	 * el dealer no tenga bodegas
	 * @throws BusinessException En caso de error al tratar de consultar la información de las bodegas del dealer
	 * @author jjimenezh
	 */
	public List<WarehouseVO> getWarehousesByDealerId(Long dealerId) throws BusinessException;

	/**
	 * Metodo: Obtiene las bodegas asociadas a un dealer así como las bodegas que hayan sido
	 * asignadas como destino por algún elemento de la tabla warehouse_elements
	 * @param dealerId identificador del dealer sobre el que se realizará la consulta de bodegas
	 * @param warehouseTypeId identificador del tipo de bodega sobre el que se realizará la consulta
	 * @return Lista de bodegas asociadas al dealer tanto propias como definidas como destino
	 * @author jjimenezh
	 */
	public List<WarehouseVO> getWarehousesByDealerIdOwnAndRelated(
			Long dealerId, Long warehouseTypeId)throws BusinessException;
	
	/**
	 * Caso de uso IVR - 05 Validar Existencia Deco o SmartCard
	 * 
	 * Metodo: Consulta Warehouse correspondientes a un dealerId y creaId dados
	 * @param dealerId Identificador del Dealer
	 * @param crewId identificador del Crew
	 * @return List<WarehouseVO> Lista de Warehouse encontradas
	 * @throws BusinessException 
	 * @author jforero 05/08/2010
	 */
	public List<WarehouseVO> getWarehousesByDealerIdAndCrewId(Long dealerId, Long crewId) throws BusinessException;

	
	/**
	 * Metodo: Obtiene las bodegas filtradas por codigo de bodega,codigo de compa�ia,codigo
	 * de sucursal, codigo de cuadrilla y codigo para tipo de bodega
	 * @param wareHouseId identificador de la bodega
	 * @param companyId identificador de la compa�ia
	 * @param branchId identificador de la sucursal
	 * @param crewId identificador de la cuadrilla
	 * @param wareHouseTypeId identificador del tipo de bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseResponse, Lista de bodegas producto filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author garciniegas
	 */
	public WareHouseResponse getWarehousesByComplexFilter( WarehouseVO wareHouseId,DealerVO companyId,DealerVO branchId,CrewVO crewId,
			WarehouseTypeVO wareHouseTypeId, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Permite filtrar las bodegas para realizar ajustes a los elementos NO serializados
	 * CU INV 26
	 * @param wareHouseId Long ID de la bodega
	 * @param dealerId Long ID del dealer
	 * @param branchId Long ID de la sucursal
	 * @param crewId Long ID de la cuadrilla
	 * @param wareHouseTypeId Long ID del tipo de bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para paginacion
	 * @return WareHouseResponse, List<WarehouseVO> Lista con las bodegas que cumplen con el filtro y de donde se
	 * pueden realizar ajustes a elementos NO serializados
	 * @throws BusinessException En caso de error al consultar la informacion de las bodegas
	 * @author jnova
	 */
	public WareHouseResponse getWarehousesByAdjustNotSerElemCriteria(Long wareHouseId , Long dealerId , Long branchId , Long crewId , Long wareHouseTypeId, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	/**
	 * Metodo: Consulta la bodega correspondiente el cliente indicado
	 * @param customerId Identificador del cliente
	 * @return WarehouseVO Bodega consultada
	 * @throws BusinessException 
	 * @author jforero 13/08/2010
	 */
	public List<WarehouseVO> getWarehousesByCustomerId(Long customerId) throws BusinessException;
	
	/**
	 * Metodo: Permite recuperar un objeto serializado por medio de su id de Element
	 * @param elementId El objeto ElementVO que encapsula el identificador asociado
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author garciniegas 
	 */
	public SerializedVO getSerializedByElementId( ElementVO elementId )throws BusinessException;
	
	/**
	 * Método: Retorna la ubicación de transito de la ubicación ingresada
	 * @param whSource - WarehouseVO ubicación de la cual se obtendrá la ubicación transito
	 * @return WarehouseVO correspondiente a la ubicación ingresada
	 * @throws BusinessException en caso de error consultando la ubicación de transito
	 * @author gfandino
	 */
	public WarehouseVO getWhTransitDealerOrCrew(WarehouseVO whSource)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de las bodegas que corresponden a una cuadrilla o a un dealer y un tipo de bodega
	 * CU 31
	 * @param dealerId - Long identificador del dealer
	 * @param crewId - Long cuadrilla a la cual se le consultarán las bodegas
	 * @param whTypeId - Long Tipo de cuadrilla que se va a consultar
	 * @return List<WarehouseVO> al tipo de bodega de la cuadrilla o del dealer; vacia en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Warehouse por cuadrilla y tipo de bodega
	 * @author jnova
	 */
	public List<WarehouseVO> getWhByCrewAndDealerAndWhType(Long dealerId,Long crewId,Long whTypeId)throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de las bodegas que corresponden a una cuadrilla o a un dealer y un tipo de bodega
	 * excluyendo la bodegas virtuales
	 * @param dealerId - Long identificador del dealer
	 * @param crewId - Long cuadrilla a la cual se le consultarán las bodegas
	 * @param whTypeId - Long Tipo de cuadrilla que se va a consultar
	 * @return List<WarehouseVO> al tipo de bodega de la cuadrilla o del dealer; vacia en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Warehouse por cuadrilla y tipo de bodega
	 * @author waguilera
	 */
	public List<WarehouseVO> getWhByCrewAndDealerAndWhTypeNotVirtual(Long dealerId,Long crewId,Long whTypeId)throws BusinessException;
	

	/**
	 * Metodo: Obtiene la información de un WarehouseVO por su identificador
	 * @param id identificador del WarehouseVO a ser consultado
	 * @return objeto con la información del WarehouseVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public WarehouseVO getWarehouseBySerialCode(String warehouseCode) throws BusinessException;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param dealerId
	 * @param warehouseType
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public WarehouseVO getWarehouseTypeByDealerId(Long dealerId, String warehouseType) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene el Warehouse almacenados en la persistencia que coincida con alguno de los creiterios de busqueda
	 * @param warehouseId
	 * @param codeIBS
	 * @param documentCustomer
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public WarehouseVO getWarehouseByDealerIDIBSCodeDocumentCustomer(
			Long warehouseId, String codeIBS, String documentCustomer)
			throws BusinessException;
	
	/**
	 * 
	 * Metodo:Obtiene una lista Warehouse almacenados en la persistencia 
	 * @param dealerID opcional. identificador del dealer por el que se quiere hacer la búsqueda. Solo tendrá efecto si se indica searchSingleDealer = true
	 * @param countryID identificador del país por el que se quiere buscar
	 * @param userId identificador del usuario que solicita la operación para poder filtrar de acuerdo al tipo de rol
	 * @param searchSourceWh indica si se debe buscar bodegas de origen. Si es falso, se buscan bodegas destino
	 * @return listado de bodegas origen o destino pertienentes para el usuario que consulta
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public List<WarehouseVO> getWarehouseByCountryIdAndOptionalDealerId(
			Long dealerID, Long countryID, Long userId, boolean searchSourceWh)
			throws BusinessException;
	
	/**
	 * 
	 * Metodo utilitario que genera el nombre de la ubicació en función a su configuracion
	 * @param warehouseId
	 * @param codeIBS
	 * @param documentCustomer
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void genWareHouseName(WarehouseVO warehouse)
			throws BusinessException;

	
	public WarehouseElementStockDTO setNameWarehouseElementStockList(WarehouseElementStockDTO whElementsStockDTO) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener una lista de las bodegas segun el dealer y el tipo de bodega
	 * @param dealerId id de dealer para filtrar las bodegas
	 * @param whTypeCode codigo del tipo de bodega
	 * @param withBranch indica si consulta las sucursales
	 * @return Lista de WarehouseVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<WarehouseVO> getWarehousesByDealerIdAndWhTypeCodeWithBranch(Long dealerId,
			String whTypeCode,
			boolean withBranch)  throws BusinessException;
	
	/**
	 * Metodo: Crea la bodega de tipo '01' asociado al cliente del codigo IBS del parametro en caso
	 * que no exista
	 * @param String customerCode Codigo del cliente al que se le va a crear la bodega
	 * @param String countryCode Pais asociado al cliente
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de Warehouse
	 * @author jnova
	 */
	public void createCustomerWarehouse(String customerCode,String countryCode) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la bodega asociada a un cliente
	 * @param customerId
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public Warehouse getCustomerWarehouseByCountry(Long customerId, Long countryId) throws BusinessException;
	
	/***
	 * 	 * @param wh el objeto bodega del cual se quiere armar el WH_CODe
	 * @return Retorna el String armado de como debe quedar el whCode, si faltan datos genera excepciones
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public String buildWhCode(Warehouse wh) throws DAOServiceException, DAOSQLException, BusinessException;
	
	/**
	 * El metodo busca las bodegas de una cuadrilla y coloca el WH_CODE dependiendo del representante de la cuadrilla
	 * @param crewId El id de la cuadrilla de la cual se desea modificar el WH_CODE
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public void changeTheWhCodeForWareHousesByCrewCode(Long crewId)throws DAOServiceException, DAOSQLException, BusinessException;
	
	/**
	 * Retorna todas las bodegas no virtuales de una cuadrilla dado el id de la cuadrilla
	 * @param crewId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public List<Warehouse> getNoVirtualWarehousesByCrewId(Long crewId)throws DAOServiceException, DAOSQLException, BusinessException;
	
	/**
	 * Metodo: Permite  obtener la bodega de transito de ajustes
	 * @param countryId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public Warehouse getWareHouseAdjusmentTransit(Long countryId) throws BusinessException;

	/**
	 * Metodo: Permite validar el estado de una cuadrilla asociada a una bodega
	 * @param obj
	 * @param crewStatus
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void validateCrew(WarehouseVO obj, String crewStatus) throws BusinessException;

}
