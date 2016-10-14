package co.com.directv.sdii.persistence.dao.stock;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WarehouseElementQuantity;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.collection.InventoryElementGroupDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedWhElementsByCriteriaPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementCustomerResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.reports.dto.FilterSerializedElementDTO;
import co.com.directv.sdii.reports.dto.WarehouseSerializedNotSerializedActualDTO;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad WarehouseElement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseElementDAOLocal { 

	/**
	 * Metodo:  persiste la información de un WarehouseElement
	 * @param obj objeto que encapsula la información de un WarehouseElement
	 * @throws DAOServiceException en caso de error al ejecutar la creación de WarehouseElement
	 * @throws DAOSQLException en caso de error al ejecutar la creación de WarehouseElement
	 * @author gfandino
	 */
	public WarehouseElement createWarehouseElement(WarehouseElement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un WarehouseElement
	 * @param obj objeto que encapsula la información de un WarehouseElement
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de WarehouseElement
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de WarehouseElement
	 * @author gfandino
	 */
	public void updateWarehouseElement(WarehouseElement obj) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Ejecuta el Store Procedure de la BD que realiza el movimiento de los elementos serializados de una remisión
	 * @param 
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de WarehouseElement
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de WarehouseElement
	 * @author JFP
	 */
	public List<Object[]> executeUpdateWarehousePackage(long referenceId, long warehouseTransit, long warehouseTarget, 
			long movTypeCodeE, long movTypeCodeS, String recordStatusU, String recordStatusH, String statusPending,
			String typeComunicationHspToIbs, String documentClass, long countryId, String processCode, String isManagement,
			String MovCmdNoConfig, Long userId)
	throws DAOServiceException, DAOSQLException;
	

	/**
	 * Metodo: Borra de la persistencia la información de un WarehouseElement
	 * @param obj información del WarehouseElement a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de WarehouseElement
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de WarehouseElement
	 * @author gfandino
	 */
	public void deleteWarehouseElement(WarehouseElement obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un WarehouseElement por su identificador
	 * @param id identificador del WarehouseElement a ser consultado
	 * @return objeto con la información del WarehouseElement dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement por ID
	 * @author gfandino
	 */
	public WarehouseElement getWarehouseElementByID(Long id) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Obtiene los elementos de una bodega especificada de origen
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param id
	 * @return List<WarehouseElement>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WarehouseElement> getWarehouseElementsByWHIdSource(Long whId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseElement almacenados en la persistencia
	 * @return Lista con los WarehouseElement existentes, una lista vacia en caso que no existan WarehouseElement en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los WarehouseElement
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los WarehouseElement
	 * @author gfandino
	 */
	public List<WarehouseElement> getAllWarehouseElements() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la informacion de WarehouseElement almacenados en la persistencia que corresponde al elemento
	 * serializado y no tiene fecha  de salida de bodega
	 * @param elementId - Long Identificador del elemento serializado 
	 * @return WarehouseElement con los WarehouseElement correspondiente al elemento serializado y sin fecha de salida, nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement por elemento serializado y sin fecha de salida
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement por elemento serializado y sin fecha de salida
	 * @author gfandino
	 */
	public WarehouseElement getWarehouseElementBySerialNumAndExitDateNull(Long elementId ,Long wareHouseId)throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Permite consultar el historial de elementos dado el código de cliente ordenado por fecha (antiguo a resiente)
	 * @param documentType - Long identificador del tipo de documento de identidad
	 * @param document - String documento del cliente
	 * @return List<WarehouseElement> Lista correspondiente al código especificado, vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement por cliente
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement por por cliente
	 * @author gfandino
	 */
	public List<WarehouseElement> getWarehouseElementsByCustomer(Long documentType,String document)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar el historial de elementos dado el código de cliente (IBS) ordenado por fecha (antiguo a resiente)
	 * @param ibsCode - String código del cliente (IBS) que se va a consultar
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseElementResponse, List<WarehouseElement> Lista correspondiente al código especificado, vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement por cliente
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement por por cliente
	 * @author gfandino
	 */
	public WareHouseElementResponse getWarehouseElementsByCustomerIBSCode(String ibsCode,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite la consulta de un WhElement Serializado que se encuentra actualmente en la bodega especificada
	 * @param whID - Long Identificador de la bodega que se va a consultar
	 * @param serialCode - Serial del elemento que se va a consultar
	 * @return WarehouseElement correspondiente a la bodega en la fecha actual; nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement en la bodega actualmente
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement en la bodega actualmente
	 * @author gfandino
	 */
	public WarehouseElement getWareHouseElementByActualWhAndWhAndElement(
			Long whID, String serialCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite la consulta de un WhElement no serializado que se encuentra actualmente en la bodega especificada
	 * @param whID - Long Identificador de la bodega que se va a consultar
	 * @param idElement - Long Identificador del elemento que se va a consultar
	 * @return WarehouseElement correspondiente a la bodega en la fecha actual; nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement en la bodega actualmente
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement en la bodega actualmente
	 * @author gfandino
	 */
	public WarehouseElement getWareHouseElementByActualWhAndWhAndElement(
			Long whID, Long idElement)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar elementos que tengan ciertas caracteristicas iguales al objeto enviado para traslados
	 * en el caso que sea para consultar elementos con cantidad actual mayor
	 * @param obj - WarehouseElement Objeto para realizar la comparacion
	 * @return WarehouseElement correspondiente al que cumpla con los requisitos nulo en caso contrario
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement con los criterios enviados
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement con los criterios enviados
	 * @author jnova
	 */
	public WarehouseElement getWareHouseElementByCriteriaToMoveNotSerializedGreater(WarehouseElement obj)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar elementos que tengan ciertas caracteristicas iguales al objeto enviado para traslados
	 * en el caso que sea para consultar elementos con cantidad actual menor
	 * @param obj - WarehouseElement Objeto para realizar la comparacion
	 * @return List<WarehouseElement> Lista  con los elementos que cumplen con los requisitos nulo en caso contrario
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement con los criterios enviados
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement con los criterios enviados
	 * @author jnova
	 */
	public List<WarehouseElement> getWareHouseElementByCriteriaToMoveNotSerializedLower(WarehouseElement obj)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene todos los WarehouseElement dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseElementResponse, lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement en la bodega actualmente
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement en la bodega actualmente
	 * @author jjimenezh
	 */
	public WareHouseElementResponse getWarehouseElementsByFilters(WhElementSearchFilter whElSerachFilter, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	
	
	/**
	 * Metodo: Permite consultar los elementos que un cliente tiene actualmente instalados
	 * Inv_23 Consultar elementos actualmente instalados en un cliente
	 * @param code - String código del cliente (IBS) que se va a consultar
	 * @param documentType - Long identificador del tipo de identificación del cliente
	 * @param documentNumber - String Número de identificación del cliente
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseElementResponse, List<WarehouseElement> lista de elementos existentes en la bodega del cliente en donde la fecha de salida es nula,
	 * lista vacia en caso que no se encuentren elementos activos en la bodega del cliente
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public WareHouseElementResponse getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber(
			String customerIbsCode, Long documentTypeId,
			String customerDocumentNumber, Long countryId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar los elementos que un cliente tiene actualmente o historicos instalados
	 * Inv_21 Consultar el historial de  los elementos  en un cliente
	 * @param code - String código del cliente (IBS) que se va a consultar
	 * @param documentType - Long identificador del tipo de identificación del cliente
	 * @param documentNumber - String Número de identificación del cliente
	 * @param whType - String Tipo de Bodega. La bodega del cliente es CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity() 
	 * @param recordStatus - String Tipo de Estado del elemento en la bodega Ej. CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity() es el Ultimo  
	 * @param whElemMovTypeClass - String Tipo de movimiento del elemento en la bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseElementResponse, List<WarehouseElement> lista de elementos existentes en la bodega del cliente en donde la fecha de salida es nula,
	 * lista vacia en caso que no se encuentren elementos activos en la bodega del cliente
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author cduarte
	 */
	public WareHouseElementResponse getWarehouseElementsByCustomerIBSCodeDocTypeIdDocNumberTypeWarehouseAndRecordStatus(
			String customerIbsCode, Long documentTypeId,
			String customerDocumentNumber, Long countryId, 
            String whType,
            String recordStatus,
            String whElemMovTypeClass,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;

	/**
	 * Metodo: Permite consultar un tipo de elemento en una bodega determinada
	 * @param dealerId - Long identificador del dealer
	 * @param idElementType - Long identificador del tipo de elemento
	 * @param whType - Long identificador del tipo de bodega	 *  
	 * @param serialCode - String serial del elemento
	 * @return List<WarehouseElement> correspondiente al tipo de elemento y bodegas especificados; vacio en otro caso
	 * @throws DAOServiceException en caso de error al ejecutar la consulta de elemento en bodega por su tipo y tipo de bodega
	 * @throws DAOSQLException en caso de error al ejecutar la consulta de elemento en bodega por su tipo y tipo de bodega
	 * @author gfandino
	 * 
	 */
	public List<WarehouseElement> getWarehouseElementsByElementTypeAndWhType(Long dealerID, Long idElementType, String serialCode, Long whType)throws DAOServiceException, DAOSQLException;
	

	/**
	 * Metodo: Permite consultar un elemento de una bodega por bodega, por id de elemento 
	 * y si es o no serializado
	 * @param warehouseId Long Id de la bodega - Obligatorio
	 * @param isSerialized String Indica si es o no serializado 
	 * @param elementId Long Id del elemento, es opcional
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementResponse, List<WarehouseElement> Lista con los elementos que cumplen con el filtro
	 * @throws DAOServiceException en caso de error al ejecutar la consulta de elementos en una bodega 
	 * @throws DAOSQLException en caso de error al ejecutar la consulta de elementos en una bodega
	 * @author jnova 
	 */
	public WareHouseElementResponse getWhElementsByCriteriaAndGroupByElementId(Long warehouseId , String isSerialized , Long elementId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar un elemento de una bodega por bodega, por id de elemento 
	 * y si es o no serializado
	 * @param warehouseId Long Id de la bodega - Obligatorio
	 * @param isSerialized String Indica si es o no serializado 
	 * @param elementId Long Id del elemento, es opcional
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementResponse, List<WarehouseElement> Lista con los elementos que cumplen con el filtro
	 * @throws DAOServiceException en caso de error al ejecutar la consulta de elementos en una bodega 
	 * @throws DAOSQLException en caso de error al ejecutar la consulta de elementos en una bodega
	 * @author jnova 
	 */
	public WareHouseElementResponse getWhElementsByCriteriaAndGroupByElementIdAndWh(Long warehouseId , String isSerialized , Long elementId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite realizar consulta de elementos por id de elemento y id de bodega en donde se encuentra el 
	 * elemento
	 * @param warehouseId Long id de la bodega donde esta el elemento
	 * @param isSerialized String Para realizar la busqueda por elemento serializado o no serializado
	 * @param elementId Long Id del elemento
	 * @return List<WarehouseElement> Lista con elementos que cumplen con el filtro
	 * @throws DAOServiceException Error al realizar la busqueda con el filtro
	 * @throws DAOSQLException Error al realizar la busqueda con el filtro
	 */
	public List<WarehouseElement> getWhElementByElementIdAndWhSource(Long warehouseId, String isSerialized, Long elementId) throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Consulta warehouseelements por dealer, crew, type y serial
	 * @param dealerCode Codigo del dealer
	 * @param crewId identificador del Crew
	 * @param elementTypeCode Tipo de elemento
	 * @param serial Numero de serie
	 * @return WarehouseElement Elementos encontrado
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author jforero 09/08/2010
	 */
	public WarehouseElement getWarehouseElementByDealerAndCrewAndTypeAndSerial(
			String dealerCode, Long crewId, String elementTypeCode, String serial)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta warehouseelements por pais y serial
	 * @param serial Numero de serie
	 * @param countryId
	 * @return WarehouseElement Elementos encontrado
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 */
	public Long existsWarehouseElementByCountryAndSerial(
			String serial, Long countryId)
	throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar un elemento de una bodega por el id del elemento y el id de la bodega
	 * @param warehouseId Long ID de la bodega
	 * @param isSerialized String Indica si es un elemento serializado o no serializado
	 * @param elementId Long ID del elemento
	 * @return WarehouseElement Elemento si esta en la bodega y nulo si no esta
	 * @throws DAOServiceException Si ocurre un error cuando se consula en la base de datos
	 * @throws DAOSQLException Si ocurre un error cuando se consula en la base de datos
	 */
	public WarehouseElement getWhElementInWHByWarehouseIdAndElementID(Long warehouseId, String isSerialized, Long elementId) throws DAOServiceException, DAOSQLException;
	

	/**
	 * Metodo: consulta la cantidad actual de un tipo de elemento en una bodega.
	 * Caso de Uso Inv_47 Consultar  las cantidades existentes de cada  elemento en una bodega
	 * @param warehouseId Identificador de la bodega
	 * @param elementTypeId Identificador del tipo de elemento
	 * @param elementModelId Identificador del modelo del elemento
	 * @return Double
	 * @throws DAOServiceException Si ocurre un error cuando se consula en la base de datos
	 * @throws DAOSQLException Si ocurre un error cuando se consula en la base de datos
	 */
	public Double getCurrentQuantityInWarehouseByElementType(Long warehouseId, Long elementTypeId, Long elementModelId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consula los elementos serializados dados los criterios para el caso de uso:<br>
	 * INV 45 Desvincular dos elementos serializados de una misma bodega
	 * @param warehouseId identificador de la bodega
	 * @param elementTypeId1 identificador del tipo de elemento 1
	 * @param elementTypeId2 identificador del tipo de elemento 2
	 * @param serialEl1 serial del elemento 1
	 * @param serialEl2 serial del elemento 2
	 * @return Lista con los elementos de bodega en estado actual que cumplan con los criterios
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<WarehouseElement> getWhElementsByWhIdAndElementTypeCodeAndSerials(
			Long warehouseId, Long elementTypeId1, Long elementTypeId2,String serialEl1, String serialEl2) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene los elementos activos de una bodega, es decir
	 * aquellos cuya fecha de salida es nula y su estado es 
	 * @param warehouseId identificador de la bodega que se consulta
	 * @return lista con los elementos actuales de la bodega
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author jjimenezh
	 */
	public List<WarehouseElement> getLastWarehouseElementsByWHIdTarget(Long warehouseId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param dealerId
	 * @param warehouseId
	 * @param crewId
	 * @param warehouseTypeId
	 * @param elementTypeId
	 * @param elementModelId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public List<WarehouseElementQuantity> getCurrentElementQuantityInWHByFilters(Long dealerId, Long warehouseId, Long crewId, Long warehouseTypeId, 
			Long elementTypeId, Long elementModelId, Date initialDate, Date finalDate)
			throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene una lista de las cantidades totales de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param QuantityWarehouseElementsDTO criterios de búsqueda
	 * @return QuantityWarehouseElementResponse Lista con las estadísticas de cantidades de elementos en bodega
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsSummariesByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de las cantidades detalladas de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param QuantityWarehouseElementsDTO criterios de búsqueda
	 * @return QuantityWarehouseElementResponse Lista con las estadísticas de cantidades de elementos en bodega
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsDetailsByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo )throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Obtiene una lista de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param QuantityWarehouseElementsDTO criterios de búsqueda
	 * @return QuantityWarehouseElementResponse Lista con los elementos en bodega
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	//CC053
	//public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(WhElementSearchFilter whElementSearchFilter,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(WhElementSearchFilter whElementSearchFilter,RequestCollectionInfo requestCollInfo, boolean filterDealer, boolean doCount)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param QuantityWarehouseElementsDTO criterios de búsqueda
	 * @return QuantityWarehouseElementResponse Lista con los elementos en bodega
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public WareHouseElementCustomerResponse getWarehouseElementsByWarehouseCustomerActual(WareHouseElementClientFilterRequestDTO request,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Regresa una lista de objetos WareHouseElement la cual representa un historico de los
	 * movimientos realizados al objeto serializado
	 * @param serializedId
	 * @param RequestCollectionInfo requestCollInfo, parametros para la pagiancion.
	 * @return WareHouseElementResponse, una lista de objetos WareHouseElement
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author cduarte 
	 */
	public WareHouseElementResponse getWareHouseElementHistoricalForSerializedElement( Long serializedId, RequestCollectionInfo requestCollInfo,
            boolean woutMovementTypeOut )throws DAOServiceException, DAOSQLException;
	
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
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public SerializedWhElementsByCriteriaPaginationResponse getSerializedWhElementsByCriteria(Long warehouseId , Long typeId, Long modelId, RequestCollectionInfo requestCollectionInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Permite consultar un elemento en bodega dado el tipo de elemento
	 * @param idWh - Long identificador de la bodega destino a consultar
	 * @param elementType - Long idnetificador del tipo de elemento
	 * @param isSerialized - String indica si el tipo del elemento es serialziado o no
	 * @param serial - String Serial del elemento. Aplica solo si isSerialized indica que es serializado
	 * @param recordStatusCode - String Código del estado del registro a consultar
	 * @return  List<WarehouseElement> lista de elementos en bodega cliente con los
	 * @throws DAOServiceException en caso de error al consutlar el elemento en bodega cliente 
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author gfandino
	 */
	public List<WarehouseElement> getWhElementByWhAndElementType(Long idWh,
			Long elementType,String isSerialized, String serial,String recordStatusCode,Long countryId)throws DAOServiceException, DAOSQLException;

    
    /**
     * Metodo: realiza el merge de un elemento de bodega
     * @param obj Objeto a persistir
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jforero 26/08/2010
     */
    public void mergeWarehouseElement(WarehouseElement obj) throws DAOServiceException, DAOSQLException;
    
    
    /**
	 * Metodo: Regresa una lista de objetos ElementType asociados a una bodega filtrados al ser serializados o no
	 * @param wareHouseId la bodega en que se va a buscar
	 * @param serialized un booleano que indica si se deben buscar los elementos serializados o no serializados
	 * @return una lista de objetos ElementType propios de una bodega filtrados al ser serializados o no
	 * @throws DAOServiceException en caso de error al ejecutar la consulta
	 * @throws DAOSQLException en caso de error al ejecutar la consulta
	 * @author garciniegas 
	 */
    public List<ElementType>getElementsTypesInWareHouseByType( Long wareHouseId,boolean serialized )throws DAOServiceException, DAOSQLException;
    
    
    /**
     * Metodo: Consulta elementos de una bodega verificando que estos todavia se encuentran en ella
     * @param warehouseId Identificador de la bodega
     * @param isSerialized Indicador de Serializado o No serializado
     * @param elementId Identificador del elemento
     * @return List<WarehouseElement> Lista de elementos encontrados
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jforero 26/08/2010
     */
    public List<WarehouseElement> getWhElementsByWhSourceIdAndSerializedAndElementIdInWH(
			Long warehouseId, String isSerialized, Long elementId)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene los elementos de una bodega especificada de destino
	 * @param id
	 * @return List<WarehouseElement>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public List<WarehouseElement> getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast(Long whId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene los elementos de una bodega especificada de destino
	 * @param id
	 * @return List<WarehouseElement>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author cduarte
	 */
	public List<WarehouseSerializedNotSerializedActualDTO> getWarehouseElementsByWHIdAndRecordLast(Long whId) throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Obtiene Los elementos en bodega dados los criterios de filtro
	 * @param targetWhId identificador de la bodega destino
	 * @param elementTypeId identificador del tipo de elementos 
	 * @param quantity cantidad de elementos no serializados a consultar
	 * @return Lista de elementos en la bodega que coinciden con los criterios de búsqueda
	 * @author jjimenezh
	 */
	public List<WarehouseElement> getWarehouseElementByTargetWhIdAndElementTypeIdNotSer(Long targetWhId, Long elementTypeId, Double quantity) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite la consulta de un WhElement Serializado que se encuentra actualmente en la bodega especificada
	 * @param whID - Long Identificador de la bodega que se va a consultar
	 * @param serialCode - Serial del elemento que se va a consultar
	 * @param ird - IRD del elementos que se va a consultar
	 * @param elementId - id del elemento
	 * @return WarehouseElement correspondiente a la bodega en la fecha actual; nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseElement en la bodega actualmente
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseElement en la bodega actualmente
	 * @author jnova
	 */
	public WarehouseElement getWareHouseElementByActualWhAndElementID(Long whID, String serialCode, String ird , Long elementId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: permite la consulta de un WhElement Serializado 
	 * que se encuentra actualmente en la bodega especificada, 
	 * aplicando los filtros recibidos, todos los parametros 
	 * son obligatorios a excepcion de ird, linkedSerialCode,
	 * lote, measureUnit y actualQuantity.
	 * @param whCode String,
	 * @param countryId Long
	 * @param serialCode String
	 * @param ird String
	 * @param linkedSerialCode String
	 * @param elementCode String
	 * @param elementStatusCode String
	 * @param isSerialized String
	 * @param String modelCode
	 * @param String lote
	 * @param String measureUnitCode
	 * @param Long actualQuantity
	 * @return WarehouseElement
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WarehouseElement getWHouseElementByActualElementsCodes(String whCode, 
			Long countryId, String serialCode, String elementCode, String elementStatusCode, 
			String isSerialized, String ird , String linkedSerialCode, String modelCode,String lote,
			String measureUnitCode, Long actualQuantity) throws DAOServiceException,DAOSQLException;
	
	/**
	 * Metodo: permite la consulta de un WhElement No Serializado 
	 * que se encuentra actualmente en la bodega especificada, 
	 * aplicando los filtros recibidos, todos los parametros 
	 * son obligatorios a excepcion lote, modelCode.
	 * @param whCode String,
	 * @param countryId Long
	 * @param elementCode String
	 * @param elementStatusCode String
	 * @param isSerialized String
	 * @param String modelCode
	 * @param String lote
	 * @param String measureUnitCode
	 * @param Double actualQuantity
	 * @return List<WarehouseElement>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WarehouseElement> getWHElementByActualElementNotSerialized(Long whID, Long countryId, 
			String lote, String measureUnitCode, String elementCode, String isSerialized, 
			String elementStatusCode, String modelCode, Double actualQuantity) throws DAOServiceException,	DAOSQLException;
	
	/**
	 * Metodo: Suma la cantidad actual de elementos por filtro
	 * @param whCode String,
	 * @param countryId Long
	 * @param elementCode String
	 * @param elementStatusCode String
	 * @param isSerialized String
	 * @param String modelCode
	 * @param String lote
	 * @param String measureUnitCode
	 * @param Double actualQuantity
	 * @return Cantidad del elemento
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Double countWHElementByActualElementNotSerialized(Long whID, Long countryId, 
			String lote, String measureUnitCode, String elementCode, String isSerialized, 
			String elementStatusCode, String modelCode) throws DAOServiceException,	DAOSQLException;
	
	/**
	 * Metodo: permite consultar la maxima fecha actual de elementos por filtro
	 * @param whCode String,
	 * @param countryId Long
	 * @param elementCode String
	 * @param elementStatusCode String
	 * @param isSerialized String
	 * @param String modelCode
	 * @param String lote
	 * @param String measureUnitCode
	 * @param Double actualQuantity
	 * @return Cantidad del elemento
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Date maxDateWHElementByActualElementNotSerialized(Long whID, Long countryId, 
			String lote, String measureUnitCode, String elementCode, String isSerialized, 
			String elementStatusCode, String modelCode) throws DAOServiceException,	DAOSQLException;
	
	/**
	 * Método: Permite obtener los elementos que están actualmente en una bodega
	 * @param idWh - Long Identificador de la bodega
	 * @return List<WarehouseElement>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WarehouseElement> getWHElementByWarehouseAndLastRecord(Long idWh) throws DAOServiceException,	DAOSQLException;
	
	/**
	 * Metodo> Retorna todos los elementos NO serializados, segun el tipo de Bodega y el dealer (Operador Logistico)
	 * @param whTypeCode, Codigo de la Bodega.
	 * @param dealerId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WareHouseElementResponse getNOTSerializedElementsByWHTypeIdDealerId(
			String whTypeCode, Long dealerId, RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException;

	/**
	 * 
	 * @param codeEntity
	 * @param dealer
	 * @param elementType
	 * @param serialCode
	 * @param requestCollInfo
	 * @return
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public WareHouseElementResponse getSerializedElementsByWHTypeDealerElementTypeSerialCode(
			String codeEntity, Dealer dealer, String elementType,
			String serialCode, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * @param whID
	 * @param idElement
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WarehouseElement getWareHouseElementByActualWhAndWhAndSerializedElement(
			Long whID, Long idElement) throws DAOServiceException,
			DAOSQLException;

	/**
	 * 
	 * @param isSerialized
	 * @param elementId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WarehouseElement getWareHouseElementByserialID(String isSerialized,
			Long elementId) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Consulta los elementos ultimos segun el identificador de la bodega
	 * @param whId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author hcorredor
	 */
	public List<WarehouseElement> getWarehouseElementsByWHIdSourceAndLastRecordStatus(Long whId,boolean notLinked) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: consulta los elmentos en una bodega que estén disponibles y que coincidan con
	 * un número de serial O con un tipo de elmento determinado
	 * @param warehouseId identificador de la bodega donde buscar
	 * @param serial serial del elemento a buscar
	 * @param elementTypeId tipo de elemento a buscar
	 * @return elemento de la bodega que cumpla con el serial O con el tipo de elemento
	 * @throws BusinessException en caso de algún error al ejecutar la operación
	 * @author wjimenez
	 */
	public WarehouseElement getWarehouseElementBySerialOrElementType(Long warehouseId,
			String serial, Long elementTypeId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo encargado de obtener un objeto de tipo  WarehouseElement que tenga el serial 
	 * enviado como parametro y que se encuentre activo
	 * @param serialCode
	 * @param elementTypeId
	 * @return
	 * @throws BusinessException en caso de algún error al ejecutar la operación
	 * @author wjimenez
	 */
	public WarehouseElement getWarehouseElementBySerialActive(String serialCode,Long countryId) throws DAOServiceException, DAOSQLException;

	
	public Map<String,WarehouseElement> getWarehouseElementBySerialActiveMassive(Set<String> serialCode,Long countryId) throws DAOServiceException, DAOSQLException;
	/**
	 *  Metodo encargado de obtener un objeto de tipo  WarehouseElement del elementId 
	 * enviado como parametro y que se encuentre activo
	 * @param elementId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public WarehouseElement getWarehouseElementByElementIdActive(Long elementId)
		throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: calcula la cantidad total de un elemento no serializado existente en una bodega específica
	 * @param warehouseId
	 * @param elementTypeId
	 * @return cantidad total de un elemento no serializado existente en una bodega específica
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author wjimenez
	 */
	public Double getWarehouseElementQuantityByElementType(Long warehouseId,
			Long elementTypeId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un dealer a partir del serial del elemento, 
	 * del identificador del dealer y del tipo de bodega. Busca tanto en la bodedga del dealer como la de las
	 * sucursales
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCode
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndDealerId(String elementSerial, Long dealerId, String whTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un dealer a partir del serial del elemento, 
	 * del identificador del dealer y tipos de bodegas. Busca tanto en la bodedga del dealer como la de las
	 * sucursales
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCodes
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndDealerIdAndWhTypes(String elementSerial, Long dealerId, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta un elemento por serial, tipo de bodega en las bodegas del dealer, sus sucursales y sus cuadrillas
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCodes
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndDealerCodeAndWhTypes(String elementSerial, Long dealerCode, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un dealer a partir del serial del elemento vinculado, 
	 * del codigo del dealer y tipos de bodegas. Busca tanto en la bodedga del dealer como la de las
	 * sucursales
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCodes
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementByLinkedSerialAndDealerCodeAndWhTypes(String elementSerial, Long dealerCode, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un dealer a partir del serial del elemento, 
	 * del identificador del dealer y del tipo de bodega. Busca en las bodegas de las cuadrilla del dealer
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCode
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndDealerIdInCrewWh(String elementSerial, Long dealerId, String whTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un dealer a partir del serial del elemento, 
	 * del identificador del dealer y del tipo de bodega. Busca en las bodegas de las cuadrilla del dealer
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCode
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndDealerIdInCrewWhAndWhTypes(String elementSerial, Long dealerId, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un dealer a partir del serial del elemento, 
	 * del identificador del dealer y del tipo de bodega. Busca en las bodegas de las cuadrilla del dealer
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCode
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndDealerIdWhAndWhTypes(String elementSerial, Long dealerId, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un dealer a partir del serial del elemento, 
	 * del codigo del dealer y del tipo de bodega. Busca en las bodegas de las cuadrilla del dealer
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCode
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndDealerCodeInCrewWhAndWhTypes(String elementSerial, Long dealerCode, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un dealer a partir del serial del vinculado de un elemento, 
	 * del codigo del dealer y del tipo de bodega. Busca en las bodegas de las cuadrilla del dealer
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @param whTypeCode
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementByLinkedSerialAndDealerCodeInCrewWhAndWhTypes(String elementSerial, Long dealerCode, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene el elemento serializado de la bodega de un customer
	 * @param elementSerial Serial
	 * @param dealerId id del dealer
	 * @return Elemento que comuple con el filtro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndCustomerId(String elementSerial, Long customerId) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Elimina elemento no serializado.
	 * @param elementId
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public void deleteNotSerializedWarehouseElementByElementID(Long elementId) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Elimina elemento Serializado.
	 * @param elementId
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public void deletetSerializedWarehouseElementByElementID(Long elementId) throws DAOServiceException, DAOSQLException;

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
	public WareHouseElementResponse getWarehouseElementsByCustomerIdSerialAndDatesRange(
			WareHouseElementClientFilterRequestDTO request, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo encargado de obtener un objeto de tipo  WarehouseElement que tenga
	 * el Id del elemento enviado y sea el último
	 * @param serialCode
	 * @param elementTypeId
	 * @return
	 * @throws BusinessException en caso de algún error al ejecutar la operación
	 * @author wjimenez
	 */
	public WarehouseElement getWarehouseElementSerializedLastByElementID(Long elementId) throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Retorna lista con los elementos que no pertenecen a una determinada
	 * 	bodega, esto para validar si existe un elemento que ha sido trasladado
	 *  o movido de la bodega de transito.
	 * @param elementId
	 * @param whType
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<WarehouseElement> getWhElementByElementIdAndNotWhType(Long elementId,
			String whType) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Permite obtener la cantidad de warehouse element de las bodega disponibles de las crew
	 * para validar antes de mover de las cuadrillas a las bodegas
	 * @param dealerId
	 * @param whTypeCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public Long getQuantityWarehouseElementByDealersAndWarehouseType(List<Long> dealerId,List<String> whTypeCode) throws DAOServiceException, DAOSQLException;
	
	
	
	/**
	 * 
	 * Metodo: Operación encargada de consultar elementos en estado LAST según los filtros enviados
	 * @param wareHouseRequestDTO
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author waguilera
	 */
	public WareHouseElementResponse getSerializedElementsLastByCriteria(
			                        WareHouseRequestDTO wareHouseRequestDTO)
	    throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Operación encargada de consultar elementos no serializados en estado LAST según los filtros enviados
	 * @param wareHouseRequestDTO
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author waguilera
	 */
	public WareHouseElementResponse getNotSerializedElementsLastByCriteria(WareHouseRequestDTO wareHouseRequestDTO)
		throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Operacion encargada de la consulta de elementos serializados
	 * por diversos filtros
	 * @param countryId
	 * @param dealerId
	 * @param crewId
	 * @param warehouseId
	 * @param warehouseTypeId
	 * @param serialCode
	 * @param elementTypeId
	 * @param requestCollInfo
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public WareHouseElementResponse getWarehouseElementsByFiltersAndIsSerializedLast(FilterSerializedElementDTO filterSerializedElement, RequestCollectionInfo requestCollInfo )
		throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta un elemento por serial en la bodega de un cliente por codigo
	 * @param elementSerial 
	 * @param customerCode
	 * @return WarehouseElement
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementBySerialAndCustomerCode(String elementSerial, String customerCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta un elemento por serial del elemento vinculado en la bodega de un cliente por codigo
	 * @param elementSerial 
	 * @param customerCode
	 * @return WarehouseElement
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WarehouseElement getElementByLinkedSerialAndCustomerCode(String elementSerial, String customerCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param idDealerType
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author waguilera
	 */
	public WarehouseElement getWhSerializedElementsLastByElementID(
			Long elementId) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Consulta warehouseelements por pais y elementID
	 * @param serial Numero de serie
	 * @param countryId
	 * @return WarehouseElement Elementos encontrado
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 */
	public Long existsWarehouseElementByCountryAndElementID(
			Long elementID)
	throws DAOServiceException, DAOSQLException;
	
	
	public List<Object[]> getWarehouseElementBySerialActive(List<String> multipleSerialCode,Long countryId)
	throws DAOServiceException, DAOSQLException ;
	
	public InventoryElementGroupDTOResponse getElementGroupInventory(Country country, List<String> listWarehouseTypes, RequestCollectionInfo requestCollInfo)
		throws DAOServiceException, DAOSQLException ;
	
	public InventoryElementGroupDTOResponse getElementGroupInventoryWithDate(Country country, List<String> listWarehouseTypes, RequestCollectionInfo requestCollInfo)
		throws DAOServiceException, DAOSQLException;
	
	public boolean existElementInCustomer(
			Long customerId, String serialCode)throws DAOServiceException, DAOSQLException ;
	
}