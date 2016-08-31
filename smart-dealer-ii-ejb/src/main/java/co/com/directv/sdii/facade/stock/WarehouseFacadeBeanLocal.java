package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.ws.model.dto.ResponseWareHouseIndicatorAlarmDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Warehouse.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Warehouse
	 * @param obj - WarehouseVO  objeto que encapsula la información de un WarehouseVO
	 * @throws BusinessException en caso de error al ejecutar la creación de Warehouse
	 * @author gfandino
	 */
	public void createWarehouse(WarehouseVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Warehouse
	 * @param obj - WarehouseVO  objeto que encapsula la información de un WarehouseVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de Warehouse
	 * @author gfandino
	 */
	public void updateWarehouse(WarehouseVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Warehouse
	 * @param obj - WarehouseVO  información del WarehouseVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de Warehouse
	 * @author gfandino
	 */
	public void deleteWarehouse(WarehouseVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Warehouse por su identificador
	 * @param id - Long identificador del Warehouse a ser consultado
	 * @return objeto con la información del WarehouseVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Warehouse por ID
	 * @author
	 */
	public WarehouseVO getWarehouseByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Warehouse almacenados en la persistencia
	 * @return List<WarehouseVO> Lista con los WarehouseVO existentes, una lista vacia en caso que no existan WarehouseVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los Warehouse
	 * @author gfandino
	 */
	public List<WarehouseVO> getAllWarehouses() throws BusinessException;
	
	/**
	 * Método: Consulta todas las bodegas en cualquier estado
	 * @param countryId - identificacor del país
	 * @param code - String con el código de la ubicación; nulo si no aplica
	 * @param requestCollInfo - WareHouseResponse datos de la paginación 
	 * @return WarehouseInfoResponseDetailDTO con las bodegas paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las Warehouse
	 */ 
	public WarehouseInfoResponseDetailDTO  getAllWarehousesByCountryId(	Long countryId, 
																		String code, 
																		RequestCollectionInfo requestCollInfo
	) throws BusinessException;

	/**
	 * 
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
     *
	 * Método: Consulta las bodegas segun los parametros 
	 * @param countryId - identificacor del país
	 * @param warehouseTypeId - String con el identificador de la ubicación; opcional, nulo permitido
	 * @param dealerId - String con el identificador de la compania; opcional, nulo permitido
	 * @param branchId - String con el identificador de la sucursal; opcional, nulo permitido
	 * @param crewCodeId - String con el identificador de la cuadrilla; opcional, nulo permitido
	 * @param requestCollInfo - WareHouseResponse datos de la paginación 
	 * @return WarehouseInfoResponseDetailDTO con las bodegas paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta 
	 */ 
	public WarehouseInfoResponseDetailDTO  getWhByWhTypeDealerBranchCrewIds( Long countryId, 
																				 String  warehouseTypeId, 
																				 String  dealerId, 
																				 String  branchId,
																				 String  crewCodeId ,
																				 RequestCollectionInfo requestCollInfo
	) throws BusinessException;

	
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
	 * Metodo: Obtiene las bodegas por país segun la condición del dealer: vendedor|instalador
	 * @param countryId identificador del país a ser consultado
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @param isSeller identifica si se trata de un dealer vendedor
	 * @parame isInstaller identifica si se trata de un dealer instalador
	 * @return WarehouseInfoResponseDTO, lista con detalle de ubicaciones de un país
	 * @throws BusinessException En caso de error al consultar las bodegas
	 * @author carlos lopez
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
	 * @param Long countryId - id del pais de la Bodega
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
	public List<NotSerializedVO> validateNotSerializedInventory(Long whID,List<NotSerializedVO> noSerializadosFisicos,Long countryId)throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de las bodegas de un dealer
	 * @param dealerId identificador del dealer
	 * @return Lista con la información de las bodegas del dealer, una lista vacia en caso que
	 * el dealer no tenga bodegas
	 * @throws BusinessException En caso de error al tratar de consultar la información de las bodegas del dealer
	 * @author jjimenezh
	 */
	public List<WarehouseVO> getWarehousesByDealerId(Long dealerId)throws BusinessException;

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
			   WarehouseTypeVO wareHouseTypeId, RequestCollectionInfo requestCollInfo )throws BusinessException;

	/**
	 * Metodo: Permite filtrar las bodegas para realizar ajustes a los elementos NO serializados
	 * CU INV 26
	 * @param wareHouseId Long ID de la bodega
	 * @param dealerId Long ID del dealer
	 * @param branchId Long ID de la sucursal
	 * @param crewId Long ID de la cuadrilla
	 * @param wareHouseTypeId Long ID del tipo de bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros  para paginacion.
	 * @return WareHouseResponse, List<WarehouseVO> Lista con las bodegas que cumplen con el filtro y de donde se
	 * pueden realizar ajustes a elementos NO serializados
	 * @throws BusinessException En caso de error al consultar la informacion de las bodegas
	 * @author jnova
	 */
	public WareHouseResponse getWarehousesByAdjustNotSerElemCriteria(Long wareHouseId , Long dealerId , Long branchId , Long crewId , Long wareHouseTypeId, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Permite recuperar un objeto serializado por medio de su id de Element
	 * @param elementId El objeto ElementVO que encapsula el identificador asociado
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author garciniegas 
	 */
	public SerializedVO getSerializedByElementId( ElementVO elementId )throws BusinessException;

	/**
	 * Metodo: Permite detectar cuando hay insuficiencia por stock para poder lanzar una alarma por 
	 * stock insuficiente de elementos
	 * @param warehouse la bodega a verificar
	 * @param user el usuario involucrado
	 * @return un boolean indicando si la alarma de verificacionde stock insuficiente se disparo
	 * @throws BusinessException En caso de error al tratar de ejecutar la operacion
	 * @author garciniegas
	 */
	public boolean checkShortcomingInWarehouse( Long warehouse,Long user )throws BusinessException;
	
	/**
	 * Metodo: Permite detectar cuando hay insuficiencia por stock para poder lanzar una alarma por 
	 * stock insuficiente de elementos
	 * @param dealerId las bodegas del dealer a verificar 
	 * @param warehouseType el tipo de bodega a verificar
	 * @param user el usuario involucrado
	 * @return un boolean indicando si la alarma de verificacionde stock insuficiente se disparo
	 * @throws BusinessException En caso de error al tratar de ejecutar la operacion
	 * @author cduarte
	 */
	public List<ResponseWareHouseIndicatorAlarmDTO> checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser( Long dealerId,String warehouseType,Long user )throws BusinessException;
	
	/**
	 * Método: Obtiene las bodegas del país que se encuentran en estado activo
	 * @param countryId - identificación del país
	 * @param dealerId - identificador del dealer (-1 si no aplica)
	 * @return List<WarehouseVO>
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de las bodegas activas
	 * @author gfandino
	 * 
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
	 * Método: Crado para UC Inv 123
	 * @param objWarehouse
	 */
	public WarehouseVO createWarehouseTransit(WarehouseVO objWarehouse)  throws BusinessException;
	
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
	 * 
	 * Metodo: <Descripcion>
	 * @param dealerId
	 * @param warehouseType
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public WarehouseVO getWarehouseTypeByDealerId(Long dealerId, String warehouseType) throws BusinessException;
	
	public WarehouseVO getWarehouseByDealerIDIBSCodeDocumentCustomer(
			Long warehouseId, String codeIBS, String documentCustomer)
			throws BusinessException; 
	
	/**
	 * 
	 * Metodo:Obtiene una lista Warehouse almacenados en la persistencia 
	 * Reglas: Si es analista logistico de directv no muestra las ubicaciones de la compañia 
	 * @param dealerID
	 * @param countryID
	 * @param userId
	 * @param searchSourceWh indica si se debe buscar bodegas de origen. Si es falso, se buscan bodegas destino
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<WarehouseVO> getWarehouseByCountryIdAndOptionalDealerId(
			Long dealerID, Long countryID, Long userId, boolean searchSourceWh)
			throws BusinessException;

	/**
	 * Metodo: Permite obtener una lista de las bodegas segun el dealer y el tipo de bodega
	 * @param dealerId id de dealer para filtrar las bodegas
	 * @param whTypeCode codigo del tipo de bodega
	 * @param withBranch Indica si se consultan las sucursales
	 * @return Lista de WarehouseVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<WarehouseVO> getWarehousesByDealerIdAndWhTypeCodeWithBranch(Long dealerId,String whTypeCode,boolean withBranch)  throws BusinessException;;
}
