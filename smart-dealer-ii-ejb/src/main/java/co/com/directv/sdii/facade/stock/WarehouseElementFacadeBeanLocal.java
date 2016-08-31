package co.com.directv.sdii.facade.stock;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.collection.MovedElementSerializedResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedWhElementsByCriteriaPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementCustomerResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementHistoricalResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.MassiveMovementVO;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WHElementQtySummaryVO;
import co.com.directv.sdii.model.vo.WarehouseElementQuantityVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.model.vo.WhElementSearchFilterVO;
import co.com.directv.sdii.reports.dto.FilterSerializedElementDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad WarehouseElement.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */

@Local
public interface WarehouseElementFacadeBeanLocal {

	
	/**
	 * Metodo:  Permite crear en el sistema un objeto WarehouseElement
	 * @param obj - WarehouseElementVO  objeto que encapsula la información de un WarehouseElementVO
	 * @throws BusinessException en caso de error al ejecutar la creación de WarehouseElement
	 * @author gfandino
	 */
	public void createWarehouseElement(WarehouseElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un WarehouseElement
	 * @param obj - WarehouseElementVO  objeto que encapsula la información de un WarehouseElementVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de WarehouseElement
	 * @author gfandino
	 */
	public void updateWarehouseElement(WarehouseElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un WarehouseElement
	 * @param obj - WarehouseElementVO  información del WarehouseElementVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de WarehouseElement
	 * @author gfandino
	 */
	public void deleteWarehouseElement(WarehouseElementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un WarehouseElement por su identificador
	 * @param id - Long identificador del WarehouseElement a ser consultado
	 * @return objeto con la información del WarehouseElementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de WarehouseElement por ID
	 * @author gfandino
	 */
	public WarehouseElementVO getWarehouseElementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los WarehouseElement almacenados en la persistencia
	 * @return List<WarehouseElementVO> Lista con los WarehouseElementVO existentes, una lista vacia en caso que no existan WarehouseElementVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los WarehouseElement
	 * @author gfandino
	 */
	public List<WarehouseElementVO> getAllWarehouseElements() throws BusinessException;
	
	/**
	 * Metodo: Permite registrar el movimiento de un elemento a una bodega
	 * @param ElementMovementDTO dto
	 * @throws BusinessException
	 * 	
	 * @author Jimmy Vélez muñoz
	 * @author jnova
	 */
	public void moveSerializedElementToWareHouse(MovementElementDTO dto) throws BusinessException;
	
	/**
	 * Metodo: Permite registrar el movimiento de una lista de elementos a una bodega
	 * @param sourceWhId
	 * @param targetWsId
	 * @param serializedList
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @throws BusinessException
	 * @author Jimmy Vélez Muñoz
	 */
	public void moveSerializedElementsToWareHouse(Long sourceWhId, Long targetWsId, List<SerializedVO> serializedList, String movTypeCodeE, String movTypeCodeS) throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las cantidades totales de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param dealerId identificador de la compañía propietaria de la bodega
	 * @param warehouseId identificador de la bodega
	 * @param crewId identificador de la cuadrilla dueña de la bodega
	 * @param warehouseTypeId identificador del tipo de bodega
	 * @param elementTypeId identificador del tipo de elemento
	 * @param elementModelId identificador del modelo de elemento
	 * @param initialDate fecha inicial de la búsqueda
	 * @param finalDate fecha final de la búsqueda
	 * @return Lista con las estadísticas de cantidades de elementos en bodega
	 * @throws BusinessException En caso de error al consultar la información de cantidades del elemento
	 * @author jjimenezh
	 */
	public List<WHElementQtySummaryVO> getWhElementQuantitySummariesByFilters(Long dealerId, Long warehouseId, Long crewId, Long warehouseTypeId, 
			Long elementTypeId, Long elementModelId, Date initialDate, Date finalDate)throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las cantidades totales de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param QuantityWarehouseElementsDTO criterios de búsqueda
	 * @return QuantityWarehouseElementResponse Lista con las estadísticas de cantidades de elementos en bodega
	 * @throws BusinessException En caso de error al consultar la información de cantidades del elemento
	 * @author cduarte
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsSummariesByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las cantidades detalladas de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param QuantityWarehouseElementsDTO criterios de búsqueda
	 * @return QuantityWarehouseElementResponse Lista con las estadísticas de cantidades de elementos en bodega
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsDetailsByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los WarehouseElement dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementResponse, lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public WareHouseElementResponse getWarehouseElementsByFilters(
			Long wareHouseId, Long dealerId, Long branchDealerId, Long crewId,
			Long warehouseTypeId, String elementTypeCode, Long elementModelId, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	

	/**
	 * Metodo: Obtiene todos los WarehouseElement que tengan la fecha de salida en null y cuyo estado de registro sea 'U' es decir último dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseElementResponse, lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public WareHouseElementResponse getWarehouseElementsBySearchFilter(WhElementSearchFilterVO whElementSearchFilterVO, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar los elementos que un cliente tenia instalados
	 * Inv_21 Consultar el historial de  los elementos  en un cliente
	 * @param code - String código del cliente (IBS) que se va a consultar
	 * @param documentType - Long identificador del tipo de identificación del cliente
	 * @param documentNumber - String Número de identificación del cliente
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return List<HistoryElementsOfCustomerDTO> lista de elementos existentes en la bodega del cliente,
	 * lista vacia en caso que no se encuentren elementos activos en la bodega del cliente
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de historial por cliente
	 * @author cduarte
	 */
	public CustomerElementsResponse getElementsByCustomerIBSCodeDocTypeIdAndDocNumber(
			String customerIbsCode, Long documentTypeId,
			String customerDocumentNumber, Long countryId,
			RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @return Lista con los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @throws BusinessException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public List<NotSerializedVO> getNotSerializedsByWareHouseId( WarehouseVO warehouseId ) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar un elemento de una bodega por bodega, por id de elemento 
	 * y si es o no serializado
	 * @param warehouseId Long Id de la bodega - Obligatorio
	 * @param isSerialized String Indica si es o no serializado 
	 * @param elementId Long Id del elemento, es opcional
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseElementResponse, List<WarehouseElementVO> Lista con los elementos que cumplen con el filtro
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author jnova
	 */
	public WareHouseElementResponse getWhElementsByCriteria(Long warehouseId , String isSerialized , Long elementId, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	/**
	 * Metodo: Permite realizar el movimiento masivo de elementos no serializados entre bodegas
	 * @param moveObject El objeto que encapsula toda la informacion requerida en el movimiento masivo
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author garciniegas 
	 */
	public void massiveMovementOfNotSerializedElementsBetweenWareHouse( MassiveMovementBetweenWareHouseDTO moveObject )throws BusinessException;

	 /**
	 * Metodo: Obtiene los registros de todos los objetos Serialized asociados a un WareHouse
	 * @return Lista con los registros de todos los objetos Serialized propios de un WareHouse
	 * @throws BusinessException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public List<SerializedVO> getSerializedsByWareHouseId( WarehouseVO warehouseId ) throws BusinessException;

	/**
	 * Metodo: Obtiene los elementos de bodega que estén activos dados los códigos de tipo de elemento
	 * y los seriales.
	 * CU INV 45 Desvincular dos elementos serializados de una misma bodega
	 * @return Lista con los elementos serializados de la bodega que cumplan los criterios de filtro
	 * @param warehouseId identificador de la bodega en donde se devincularán los elementos
	 * @param elementTypeId1 código del tipo de elemento 1
	 * @param elementTypeId2 código del tipo de elemento 2
	 * @param serialEl1 
	 * @param serialEl2
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh 
	 */
	public List<WarehouseElementVO> getWhElementsByWhIdAndElementTypeCodeAndSerials(
			Long warehouseId, Long elementTypeId1, Long elementTypeId2,
			String serialEl1, String serialEl2)throws BusinessException;

	/**
	 * Metodo: Permite separar dos objetos vinculados dado el identificador de los elementos en la bodega
	 * Caso de uso INV 45 Desvincular dos elementos serializados de una misma bodega
	 * @param warehouseId identificador de la bodega
	 * @param whElementMasterId identificador del elemento de bodega maestro
	 * @param whElementLinkedId identificador del elemento de bodega detalle
	 * @throws BusinessException En caso de error al ejecutar la operación o que los elementos no estén vinculados<br>
	 * @author jjimenezh
	 */
	public void separateLinkedSerializedElementsInWh(Long warehouseId,
			Long whElementMasterId, Long whElementLinkedId)throws BusinessException;
	
	
	
	/**
	 * Metodo: Permite realizar el movimiento masivo de elementos serializados entre bodegas con su elemento vinculado si lo tiene
	 * @param moveObject El objeto que encapsula toda la informacion requerida en el movimiento masivo
	 * @param CauseAdjustment Causa de ajuste
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author garciniegas 
	 */
	public void massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse( MassiveMovementBetweenWareHouseDTO moveObject, Long userId)throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene los elementos no serializados de la bodega de control de calidad del dealer especificado
	 * CU INV 11 Registrar control de calidad de elementos NO serializados
	 * @param dealerId identificador del dealer
	 * @return Lista con los elementos de una bodega
	 * @throws BusinessException En caso de error al ejecutar la operación,
	 * @author jjimenezh
	 */
	public List<WarehouseElementVO> getQualityControlWhNotSerElementsByDealerId(
			Long dealerId)throws BusinessException;

	
	/**
	 * Metodo: Regresa una lista de objetos WareHouseElementHistoricalDTO la cual representa un historico de los
	 * movimientos realizados al objeto serializado
	 * @param dealerId
	 * @param elementModelId
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementHistoricalResponse, una lista de objetos WareHouseElementHistoricalDTO
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas 
	 */
	public WareHouseElementHistoricalResponse getWareHouseElementHistoricalForSerializedElement( Long serializedId, RequestCollectionInfo requestCollInfo )throws BusinessException;
	
	/**
	 * Metodo: Regresa una lista de objetos WareHouseElementHistoricalDTO la cual representa un historico de los
	 * movimientos realizados al objeto serializado
	 * @param linkedOrSerialCode
	 * @return WareHouseElementHistoricalDTO, una lista de objetos WareHouseElementHistoricalDTO
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author cduarte 
	 */
	public MovedElementSerializedResponse getMovedWareHouseElementSerializedByLinkedOrSerialCode(String linkedOrSerialCode,Long countryId, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los elementos serializados de una bodega dependiento del tipo del elemento y
	 * del modelo del elemento. En este caso la cantidad es opcional. 
	 * y si es o no serializado
	 * @author jnova
	 * @param warehouseId
	 * @param typeId
	 * @param modelId
	 * @param requestCollectionInfo
	 * @return SerializedWhElementsByCriteriaPaginationResponse
	 * @throws BusinessException
	 */
	public SerializedWhElementsByCriteriaPaginationResponse getSerializedWhElementsByCriteria(Long warehouseId , Long typeId, Long modelId, RequestCollectionInfo requestCollectionInfo)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los elementos serialalizados en bodega de calidad del dealer asociado de un tipo de elemento
	 * @param dealer - DealerVO que va a ser consultado. Id en cero si no hace parte del filtro
	 * @param elementType - Long Que va a ser consultado. Id en cero si no hace parte del filtro
	 * @param serialCode - Long Que va a ser consultado. Cadena vacia (tamaño 0) si no hace parte del filtro
	 * @return  List<WarehouseElementVO> correspondiente a los filtros especificados
	 * @throws BusinessException En caso de erro al consultar los elementos de bodegas especificadas.
	 * @author gfadnino
	 */
	public List<WarehouseElementVO> getWhouseSerializedElementsByElementTypeAndQAWh(DealerVO dealer,
			ElementTypeVO elementType, String serialCode) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un objeto que encapsula la información de la canntidad de elementos en una bodega
	 * @param warehouseId identificador de la bodega
	 * @param elementTypeId tipo de elemento
	 * @param elementModelId modelo del elemento
	 * @return Objeto que encapsula la información de cantidades de un elemento en la base de datos
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public WarehouseElementQuantityVO getCurrentQuantityInWarehouseByElementType(Long warehouseId, Long elementTypeId, Long elementModelId)throws BusinessException;
	
	/**
	 * Metodo: Permite obtener los elementos serializados en una bodega retornando dentro del VO la fecha de entrada a la bodega
	 * @param warehouseId Bodega donde se desean buscar los elementos serializados
	 * @return List<SerializedVO> lista de elementos en la bodega
	 * @throws BusinessException En caso de error realizando la consulta
	 * @author jnova
	 */
	public List<SerializedVO> getSerializedsAndWhEntryDateByWareHouseId( WarehouseVO warehouseId ) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener los movimientos parciales de elementos no serializados a partir del id del elemento
	 * @param elementId Long id del elemento
	 * @return List<NotSerPartialRetirement> Lista con de movimientos parciales de un elemento
	 * @throws BusinessException En caso de error realizando la consulta
	 * @author garciniegas
	 */
	public List<NotSerPartialRetirementVO> getNotSerPartialRetirementByElementId(Long elementId)  throws BusinessException;
	
	/**
	 * UC_INV_14 Mover Elementos serializados entre bodegas 
	 * @param ElementMovementDTO dto
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 * @author jnova
	 */
	public void moveSerializedElementBetweenWareHouses(MovementElementDTO dto) throws BusinessException;
	
	
	/**
	 * UC_INV_12
	 * 
	 * Método: Permite mover un elemento no serializado entre bodega de origen y bodega de destino.
	 * 
	 * @param ElementMovementDTO dto
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 * @author jnova
	 */
	public void moveNotSerializedElementBetweenWareHouses(MovementElementDTO dto) throws BusinessException;
	
	
	/**
	 * 
	 * @param dealerId
	 * @return
	 * @throws BusinessException
	 */
	public WareHouseElementResponse getWarehouseQANOTSerializedElements( Long dealerId, RequestCollectionInfo requestCollInfo ) throws BusinessException;

	/**
	 * 
	 * @param elementIds
	 * @param quantity
	 */
	public void moveNotSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementsVO, Dealer dealer) throws BusinessException;

	/**
	 * 
	 * @param elementsVO
	 * @param dealerId
	 * @throws BusinessException
	 */
	public void moveNotSerializedElementBetweenWareHouseQualityReturns(List<ElementVO> elementsVO, Dealer dealer)throws BusinessException;

	/**
	 * 
	 * @param dealer
	 * @param elementType
	 * @param serialCode
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public WareHouseElementResponse getWarehouseQASerializedElements(Dealer dealer, String elementType, String serialCode, RequestCollectionInfo requestCollInfo ) throws BusinessException;

	/**
	 * 
	 * @param elementsVO
	 * @param dealer
	 * @throws BusinessException 
	 */
	public void moveSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementsVO, Dealer dealer, Long userId) throws BusinessException;

	/**
	 * 
	 * @param elementsVO
	 * @param dealer
	 * @throws BusinessException 
	 */
	public void moveSerializedElementBetweenWareHouseQualityReturn(List<ElementVO> elementsVO, Dealer dealer, Long userId) throws BusinessException;

	/**
	 * 
	 * Metodo: Encargado de mover los elementos de las bodegas de las cuadrillas
	 * a la bodega Stock de la compania principal. UC Inv131
	 * @param companies
	 * @param user
	 * @throws BusinessException <tipo> <descripcion>
	 * @author hcorredor
	 */
	public MassiveMovementVO moveElementsFromWHCrewToWHCompany(List<DealerVO> companies, UserVO user) throws BusinessException;
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param warehouseId
	 * @param serial
	 * @param elementTypeId
	 * @param quantity
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author wjimenez
	 */
	public WarehouseElementVO getWarehouseElementBySerialOrElementType(
			Long warehouseId, String serial, Long elementTypeId, Double quantity)
			throws BusinessException;

	/**
	 * Metodo: Obtiene los elementos de un cliente, con la posibilidad de filtrar por el serial
	 * @param customerId identificador del cliente
	 * @param serial serial del elemento que se desea buscar (opcional)
	 * @param startDate fecha de inicio para la búsqueda por fecha de movimiento del elemento al sitio del cliente
	 * @param endDate fecha fin para la búsqueda por fecha de movimiento del elemento al sitio del cliente
	 * @return listado de elementos en bodega del cliente
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author wjimenez
	 */
	public CustomerElementsResponse getWarehouseElementsByCustomerIdSerialAndDatesRange(
			WareHouseElementClientFilterRequestDTO request, RequestCollectionInfo reqCollInfo)
			throws BusinessException;

	/**
	 * Metodo: unión de movimientos de elementos no serializados de bodega de calidad
	 * a bodega de disponibles y de devoluciones
	 * @param elementsAvailables
	 * @param elementsReturns
	 * @param dealer
	 * @param user 
	 * @throws BusinessException
	 * @author wjimenez
	 */
	public void moveNotSerializedElementBetweenWareHouseQuality(
			List<ElementVO> elementsAvailables,
			List<ElementVO> elementsReturns, Dealer dealer, UserVO user) throws BusinessException;

	/**
	 * 
	 * Metodo: Metodo que sirve para validar si ya se realziaron movimientos fuera de 
	 * la bodega de Trasnito.
	 * @param elementId
	 * @param whType
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	public List<WarehouseElement> getWhElementByElementIdAndNotWhType(
			Long elementId, String whType) throws BusinessException;

	/**
	 * 
	 * Metodo: Consulta de elementos por bodega 
	 * la bodega de Trasnito.
	 * @param whElementSearchFilter
	 * @param requestCollInfo
	 * @return WareHouseElementResponse
	 * @author mrugeles
	 * @throws BusinessException 
	 */
	public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(
			WhElementSearchFilter whElementSearchFilter,
			RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	/**
	 * Operación encargada de consultar las existencias de elementos en la
	 * ubicación del cliente
	 * 
	 * @param whElementSearchFilter
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public WareHouseElementCustomerResponse getWarehouseElementsByWarehouseCustomerActual(
			WareHouseElementClientFilterRequestDTO request,
			RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: obtiene el elemento serializado o no serializado y valida que el objeto no se encuentre
	 * en una inconsistencia abierta de tipo mas elementos físicos dentro de la remisión
	 * @param refId
	 * @param warehouseId
	 * @param serial
	 * @param elementTypeId
	 * @param quantity
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author wjimenez
	 */
	public WarehouseElementVO getWarehouseElementValidatingExistenceInMoreElementsRefInc(
			Long refId, Long warehouseId, String serial, Long elementTypeId,
			Double quantity, Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Operación encargada de consultar elementos en estado LAST según los filtros enviados
	 * @param wareHouseRequestDTO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public WareHouseElementResponse getSerializedElementsLastByCriteria(WareHouseRequestDTO wareHouseRequestDTO)
		throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: Operación encargada de consultar elementos no serializados en estado LAST según los filtros enviados
	 * @param wareHouseRequestDTO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public WareHouseElementResponse getNotSerializedElementsLastByCriteria(
			WareHouseRequestDTO wareHouseRequestDTO)
			throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene todos los WarehouseElement dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @param isSerialize boolean Indica si ese consulta elemento serializado o no serializado
	 * @return WareHouseElementResponse, lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author waguilera
	 */
	public WareHouseElementResponse getWarehouseElementsByFiltersAndIsSerializedLast(
			FilterSerializedElementDTO filterSerializedElement, RequestCollectionInfo requestCollInfo)throws BusinessException;


}