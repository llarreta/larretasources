package co.com.directv.sdii.ejb.business.stock;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.Warehouse;
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
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.model.vo.MassiveMovementVO;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.QualityControlNotSerializedVO;
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
 * Interfaz de las operaciones Tipo CRUD de la Entidad WarehouseElement.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseElementBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto WarehouseElementVO
	 * @param obj objeto que encapsula la información de un WarehouseElementVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de WarehouseElement
	 * @author gfandino
	 */
	public void createWarehouseElement(WarehouseElementVO obj) throws BusinessException;
	
	
	
	/**
	 * Metodo: actualiza la información de un WarehouseElementVO
	 * @param obj objeto que encapsula la información de un WarehouseElementVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de WarehouseElement
	 * @author gfandino
	 */
	public void updateWarehouseElement(WarehouseElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un WarehouseElementVO
	 * @param obj información del WarehouseElementVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de WarehouseElement
	 * @author gfandino
	 */
	public void deleteWarehouseElement(WarehouseElementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un WarehouseElementVO por su identificador
	 * @param id identificador del WarehouseElementVO a ser consultado
	 * @return objeto con la información del WarehouseElementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseElement por ID
	 * @author gfandino
	 */
	public WarehouseElementVO getWarehouseElementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseElementVO almacenados en la persistencia
	 * @return Lista con los WarehouseElementVO existentes, una lista vacia en caso que no existan WarehouseElementVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los WarehouseElement
	 * @author gfandino
	 */
	public List<WarehouseElementVO> getAllWarehouseElements() throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los WarehouseElement dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseElementResponse, lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public WareHouseElementResponse getWarehouseElementsByFilters(
			Long wareHouseId, Long dealerId, Long branchDealerId, Long crewId,
			Long warehouseTypeId, String elementTypeCode, Long elementModelId,  RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	
	/**
	 * 
	 * Metodo: Consulta de elementos por bodega 
	 * la bodega de Trasnito.
	 * @param whElementSearchFilter
	 * @param requestCollInfo
	 * @return WareHouseElementResponse
	 * @author mrugeles
	 * @throws BusinessException 
	 * @throws BusinessException 
	 */
	public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(
			WhElementSearchFilter whElementSearchFilter,
			RequestCollectionInfo requestCollInfo,boolean doCount) throws BusinessException;
	
	
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
	public WareHouseElementCustomerResponse getWarehouseElementsByWarehouseCustomerActual(WareHouseElementClientFilterRequestDTO request,RequestCollectionInfo requestCollInfo) throws BusinessException;
	
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
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementResponse, List<WarehouseElementVO> Lista con los elementos que cumplen con el filtro
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author jnova
	 */
	public WareHouseElementResponse getWhElementsByCriteria(Long warehouseId , String isSerialized , Long elementId, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	/**
	 * Metodo: Permite realizar el movimiento masivo de elementos no serializados entre bodegas
	 * @param moveObject El objeto que encapsula toda la informacion requerida en el movimiento masivo
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author Jimmy Vélez Muñoz 
	 */
	public void massiveMovementOfNotSerializedElementsBetweenWareHouse( MassiveMovementBetweenWareHouseDTO moveObject, Long documentId, String documentClass,Long userId)throws BusinessException;
	
	/**
	 * Metodo: Permite realizar el movimiento masivo de elementos no serializados entre bodegas indicando los tipos de movimiento
	 * @param moveObject El objeto que encapsula toda la informacion requerida en el movimiento masivo
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author Jimmy Vélez Muñoz 
	 */
	public void massiveMovementOfNotSerializedElementsBetweenWareHouse(
			MassiveMovementBetweenWareHouseDTO moveObject,
			String movementTypeIn, String movementTypeOut, Long documentId, String documentClass,Long userId)
			throws BusinessException;
	
	/**
	 * Metodo: Consulta warehouseElements a partir del codigo del dealer, el crew, el tipo de elemento y el serial
	 * @param dealerCode Codigo del dealer
	 * @param crewId identificador del crew
	 * @param elementTypeCode Tipo de elemento
	 * @param serial Numero de Serie
	 * @return WarehouseElementVO Elementos encontrado
	 * @throws BusinessException 
	 * @author jforero 09/08/2010
	 */
	public WarehouseElementVO getWarehouseElementByDealerAndCrewAndTypeAndSerial(String dealerCode, Long crewId, String elementTypeCode, String serial) throws BusinessException;
	
	/**
	 * Caso de uso INV - 42 Actualizar los seriales de los elementos vinculados, 
	 * para elementos en una misma bodega Y 43 Actualizar los seriales de los 
	 * elementos vinculados, para elementos en diferentes bodegas
	 * 
	 * Metodo: Realiza el cambio de el serializado enlazado para un elemento de bodega dado
	 * @param whId Identificador de la bodega del elemento a modificar
	 * @param elementSerial Serial del elemento de bodega
	 * @param elementId Identificador del elemento de bodega
	 * @param oldLinkedElementSerial Serial anterior del elemento enlazado
	 * @param newLinkedElementSerial Serial nuevo del elemento enlazado
	 * @param whIdLinked Identificador de la bodega del elemento a ser asociado
	 * @throws BusinessException
	 * @author jforero 12/08/2010
	 */
	public void updateLinkedElementSerialUpdate(Long whId, String elementSerial, Long elementId, 
			 String oldLinkedElementSerial, String newLinkedElementSerial, Long whIdLinked,String newLinkedTypeCode) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene los registros de todos los objetos Serialized asociados a un WareHouse
	 * @return Lista con los registros de todos los objetos Serialized propios de un WareHouse
	 * @throws BusinessException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	public List<SerializedVO> getSerializedsByWareHouseId( WarehouseVO warehouseId ) throws BusinessException;
	
	/**
	 * Metodo: consulta la cantidad actual de un tipo de elemento en una bodega.
	 * Caso de Uso Inv_47 Consultar  las cantidades existentes de cada  elemento en una bodega
	 * @param warehouseId Identificador de la bodega
	 * @param elementTypeId Identificador del tipo de elemento
	 * @param elementModelId Identificador del modelo del elemento
	 * @return WarehouseElementQuantityVO
	 * @throws BusinessException
	 */
	public WarehouseElementQuantityVO getCurrentQuantityInWarehouseByElementType(Long warehouseId, Long elementTypeId, Long elementModelId) throws BusinessException;
	
	
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
	 * Metodo: Permite realizar el movimiento masivo de elementos serializados entre bodegas
	 * @param moveObject El objeto que encapsula toda la informacion requerida en el movimiento masivo
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author Jimmy Vélez Muñoz 
	 */
	public void massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse(MassiveMovementBetweenWareHouseDTO moveObject, Long documentId, String documentClass, Long userId)throws BusinessException;
	
	
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
	public MovedElementSerializedResponse getMovedWareHouseElementSerializedByLinkedOrSerialCode(String linkedOrSerialCode,Long countryId, RequestCollectionInfo requestCollInfo )throws BusinessException;
	
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
	 * Metodo: UC_INV_38, UC_INV_40 Permite mover elementos no serializados entre bodega cliente y compañia
	 * y viceversa.
	 * @param ibsCustomerCode - Código IBS del cliente (Obligatorio)
	 * @param elementId - Elemento a mover (Opcional si se envía elementTypeId)
	 * @param elementTypeId - Tipo del elemento a mover (Opcional si se envía elementId)
	 * @param quantity - Cantidad a mover
	 * @param whDealer - Bodega destino
	 * @param customerIsSource - si está en true, el movimiento tiene como origen el cliente. Si es false, el origen es la compañía
	 * @throws BusinessException en caso de error moviento el elemento no serializado
	 * @author Jimmy Vélez Muñoz
	 */
	public void moveNotSerElementBetweenCustomerWhAndDealerWh (MovementElementDTO dto, boolean customerIsSource)throws BusinessException;
	
	/**
	 * Metodo: UC_INV_41 Permite mover elementos no serializados entre bodega cliente y compañia
	 * 
	 * @param ibsCustomerCode - Código IBS del cliente (Obligatorio)
	 * @param elementId - Id del elemento a mover (Opcional si se envia serial o ird)
	 * @param serial - Serial del elemento a mover (Opcional si se envía ird)
	 * @param ird - Ird del elemento a mover (Opcional si se envía serial)
	 * @param whDealer - Bodega de la compañía (Obligatorio)
	 * @throws BusinessException en caso de error moviento el elemento no serializado
	 * @author Jimmy Vélez Muñoz
	 */
	public void moveSerElementBetweenCustomerWhAndDealerWh (MovementElementDTO dto, boolean customerIsSource)throws BusinessException;
	
	
		
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
	 * Metodo: UC Inv11, retorna los elementos no serializados de la Bodega de Calidad, segun el dealerId (Operador Logistico.) 
	 * @param dealerId
	 * @return
	 * @throws BusinessException
	 * @author hcorredor
	 */
	public WareHouseElementResponse getWarehouseQANOTSerializedElements( Long dealerId, RequestCollectionInfo requestCollInfo ) throws BusinessException;
	
	/**
	 * 
	 * @param dealer
	 * @param elementType
	 * @param serialCode
	 * @param requestCollInfo
	 * @throws BusinessException
	 */
	public WareHouseElementResponse getWarehouseQASerializedElements(Dealer dealer, String elementType, String serialCode, RequestCollectionInfo requestCollInfo ) throws BusinessException;

	/**
	 * 
	 * @param elementIds
	 * @param quantity
	 * @author hcorredor
	 * @throws BusinessException 
	 */
	public void moveNotSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementsVO, Dealer dealer, UserVO user, Long documentId, String documentClass) throws BusinessException;
	
	/**
	 * 
	 * @param elementsVO
	 * @param dealerId
	 * @throws BusinessException
	 */
	public void moveNotSerializedElementBetweenWareHouseQualityReturns(List<ElementVO> elementsVO, Dealer dealer, UserVO user, Long documentId, String documentClass) throws BusinessException;

	/**
	 * 
	 * @param elementsVO
	 * @param dealer
	 * @throws BusinessException
	 */
	public void moveSerializedElementBetweenWareHouseQualityAvailable(List<ElementVO> elementsVO, Dealer dealer, Long documentId, String documentClass, Long userId) throws BusinessException;

	/**
	 * 
	 * @param elementsVO
	 * @param dealer
	 */
	public void moveSerializedElementBetweenWareHouseQualityReturn(List<ElementVO> elementsVO, Dealer dealer, Long documentId, String documentClass, Long userId) throws BusinessException;

	
	/**
	 * Método: Permite consultar el elemento que se encuentra en una bodega actualmente
	 * @param whID - Long identificador de la bodega
	 * @param serialCode - String Serial del elemento (si es serializado)
	 * @param ird - String RID del elemento (si es serializado)
	 * @param elementId - Identificador del elemento
	 * @return WarehouseElementVO
	 * @throws BusinessException en caso de error en la consulta del elemento en la bodega
	 */
	public WarehouseElementVO getWareHouseElementByActualWhAndElementID(Long whID, String serialCode, String ird , Long elementId) throws BusinessException;

	/**
	 * Metodo: 
	 * @param warehouseId
	 * @param serial
	 * @param elementTypeId
	 * @param quantity
	 * @return
	 * @throws BusinessException 
	 * @author
	 */
	public WarehouseElementVO getWarehouseElementBySerialOrElementType(Long warehouseId, String serial, Long elementTypeId, Double quantity) throws BusinessException;


	/**
	 * 
	 *  Metodo: Servicio encargado de mover los elementos de las  cuadrillas a la 
	 * bodega de disponibles de la compania.
	 * @param companies
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public MassiveMovementVO moveElementsFromWHCrewToWHCompany(List<DealerVO> companies, UserVO user)
			throws BusinessException;
	
	
	
	
	
	/**
	 * 
	 *  Metodo encargado de buscar un objeto serializado y que se encuentre activo en una bodega
	 * @param companies
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public WarehouseElementVO getWarehouseElementBySerialActive(
			String rowSerialElement,Long countryId) throws BusinessException;
	
	/**
	 * Metodo: UC_INV_40 Permite mover elementos no serializados entre bodega cliente y compañia
	 * 
	 * @param ElementMovementDTO dto objeto que contiene informacion necesaria para realizar los movimientos
	 * @throws BusinessException en caso de error moviento el elemento no serializado
	 * @author Jimmy Vélez Muñoz
	 * @author jnova
	 */
	public void moveNotSerElementBetweenCustomerWhAndDealerWh (MovementElementDTO dto)throws BusinessException;
	

	
	/**
	 * 
	 * Metodo: Método encargado de crear los registros historicos del cambio de tipo de elemento, y actualizar el elemento
	 * Se asume que los datos ya estan prevalidados
	 * @param warehouseElementMain
	 * @param warehouseElementLink
	 * @param movementTypeE
	 * @param movementTypeS
	 * @param elementTypeMainElement
	 * @param elementTypeLinkElement
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void changeElementTypeSerializedElement(WarehouseElement warehouseElementMain, WarehouseElement warehouseElementLink, MovementType movementTypeE, MovementType movementTypeS, ElementType elementTypeMainElement, ElementType elementTypeLinkElement) throws BusinessException;

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
			WareHouseElementClientFilterRequestDTO request, RequestCollectionInfo requestCollInfo)
			throws BusinessException;

	
	/**
	 * Metodo: unión de movimientos de elementos no serializados de bodega de calidad
	 * a bodega de disponibles y de devoluciones
	 * @param elementsAvailables
	 * @param elementsReturns
	 * @param dealer
	 * @param userVO 
	 * @throws BusinessException
	 * @author wjimenez
	 */
	public void moveNotSerializedElementBetweenWareHouseQuality(
			List<ElementVO> elementsAvailables,
			List<ElementVO> elementsReturns, Dealer dealer, UserVO userVO, Long documentId, String documentClass) throws BusinessException;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param elementId
	 * @param whType
	 * @return <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	public List<WarehouseElement> getWhElementByElementIdAndNotWhType(
			Long elementId, String whType) throws BusinessException;

	public void moveNotSerializedElementBetweenWareHousesInconsistencies(ImportLog importLog, ImportLogInconsistencyVO importLogInconsistencyVO, String documentClass) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException;
	

		

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
	public WareHouseElementResponse getSerializedElementsLastByCriteria(
			WareHouseRequestDTO wareHouseRequestDTO)
		throws BusinessException;
	
	/**
	 * 
	 * Metodo: Operación encargada de consultar elementos no serializados en estado LAST según los filtros enviados
	 * @param wareHouseRequestDTO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public WareHouseElementResponse getNotSerializedElementsLastByCriteria(WareHouseRequestDTO wareHouseRequestDTO)
		throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los WarehouseElement dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @param isSerialize boolean Indica si ese consulta elemento serializado o no serializado
	 * @return WareHouseElementResponse, lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jnova
	 * 
	 */
	public WareHouseElementResponse getWarehouseElementsByFiltersAndIsSerializedLast(
			FilterSerializedElementDTO filterSerializedElement, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo que trae el identificador de la Bodega, si no existe la ubicaciÃ³n de transito la crea de nuevo
	 * @param dealerId
	 * @param whTypeCode
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 * @author waguilera
	 */
	public Warehouse getWareHouseOrCreateIfNotExists(Long dealerId, String whTypeCode) throws BusinessException;
	
	public Map<String, Object[]> getWarehouseElementBySerialActive(List<String> multipleSerialCode,Long countryId) throws BusinessException ;
}
