package co.com.directv.sdii.persistence.dao.config;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ReportWorkOrdersLastDayDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportAndFileResponseDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.WoType;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ServiceResponse;
import co.com.directv.sdii.model.pojo.collection.WOActiveAndSuspendByCountryIdPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByCustomerQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerDateCrewQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerWorkOrderQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderResponse;
import co.com.directv.sdii.model.vo.TechnologyVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.reports.dto.ReportWorkOrderDTO;
/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad WorkOrder
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WorkOrderDAOLocal {
	
    /**
     * Permite crear una orden de servicio
     * @param obj - WorkOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createWorkOrder(WorkOrder obj) throws DAOServiceException, DAOSQLException;

    /**
     * permite consutlar una orden de servicio por su ID
     * @param id - Long
     * @return WorkOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WorkOrder getWorkOrderByID(Long id) throws DAOServiceException, DAOSQLException;
    
    /**
     * permite consultar un codigo de work orden de servicio por su ID
     * @param id - Long
     * @return codeWorkOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public String getCodeWorkOrderByID(Long id) throws DAOServiceException, DAOSQLException;
   
    /**
     * Permite actualizar una orden de servicio
     * @param obj - WorkOrder
	 * @return Object
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Object updateWorkOrder(WorkOrder obj,String originUpdateEvent) throws DAOServiceException, DAOSQLException;

    /**
     * Permite eliminar una orden de servicio
     * @param obj - WorkOrder
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteWorkOrder(WorkOrder obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consutlar todas las ordenes de servicio
     * @return List<WorkOrder>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkOrder> getAll() throws DAOServiceException, DAOSQLException;
    
    
    /**
     * Permite consutlar todas las ordenes de servicio
     * @return List<WorkOrder>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
 public List<WorkOrder> getWorkOrdersByPage(Long workOrderId, 
    		                                   Long pageSize,
    		                                   int delayAllocator,
    		                                   int delayWorkOrderCSRStatusPendent,
    		                                   Long userId,
    		                                   Long dealerDummie,
    		                                   Long countryId,
			                                   String statusProccessAllocatorCode) throws DAOServiceException, DAOSQLException;
    
    /**
     * Permite consutlar ordenes de servicio por dealer
     * @param  Dealer
     * @param RequestCollectionInfo
     * @return WOByDealerPaginationResponse
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WOByDealerPaginationResponse getWorkOrderByDealer(Dealer dealer, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consultar las WO por su estado actual
     * @param actualStatus - WorkorderStatus
     * @return List<WorkOrder>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkOrder> getWorkOrderByActualStatus(WorkorderStatus actualStatus) throws DAOServiceException, DAOSQLException;
    
    
    /**
     * Permite consultar las WO por su estado actual
     * @param actualStatus - WorkorderStatus
     * @return List<WorkOrder>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkOrder> getWorkOrderByActualStatusAndCountryId(WorkorderStatus actualStatus, Long countryId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Consulta las work order
     * 
     * @param countryId
     * @param requestCollectionInfo
     * @param actualStatusCodes
     * @return WOActiveAndSuspendByCountryIdPaginationResponse
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrderByActualStatusCodeAndCountryId(Long countryId, RequestCollectionInfo requestCollectionInfo,  String... actualStatusCodes) throws DAOServiceException, DAOSQLException;
    
    /**
     * Permite consultar las WO por su estado actual y el dealer asociado
     * @param dealer - Dealer
     * @param actualStatus - WorkorderStatus
     * @return List<WorkOrder> 
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<WorkOrder> getWorkOrderByActualStatus(Dealer dealer,
			WorkorderStatus actualStatus) throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar una orden de servicio por Dealer y el código
	 * @param dealer - Dealer
	 * @param code - Stirng
	 * @return WorkOrder
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WorkOrder getWorkOrderByDealerAndCode(Dealer dealer,	String code)throws DAOServiceException, DAOSQLException;

	
	/**
	 * Permite consultar ordenes de servicio por Dealer y estado
	 * @param dealer - Dealer
	 * @param workOrderStatus - WorkorderStatus
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByPreviusStatus(Dealer dealer, WorkorderStatus workOrderStatus)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio por Dealer y tipo de orden
	 * @param dealer - Dealer
	 * @param woType - WoType
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByPreviusType(Dealer dealer,
			WoType woType)throws  DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio por Dealer y código postal en un rango de fecha de programaci�n
	 * @param dealer - Dealer
	 * @param postalCode - PostalCode
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByDealerPostalCodeProgrammingDate(
			Dealer dealer, PostalCode postalCode, Date initDate,
			Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio por Dealer y código postal en un rango de fecha de realizaci�n
	 * @param dealer - Dealer
	 * @param postalCode - PostalCode
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByDealerPostalCodeRealizationDate(
			Dealer dealer, PostalCode postalCode, Date initDate,
			Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio por Customer y un rango de fecha de creaci�n
	 * @param customer - Customer
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByCustomerAndCreationDate(
			Customer customer, Date initDate, Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio por Customer y un rango de fecha de programaci�n
	 * @param customer - Customer
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByCustomerAndProgrammingDate(
			Customer customer, Date initDate, Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consulta de Ordenes de trabajo por el tipo actual
	 * @param woType - WoType
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByActualType(WoType woType)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Permite consulta de Ordenes de trabajo por el dealer y el tipo actual
	 * @param dealer - Dealer
	 * @param woType - WoType
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByActualType(Dealer dealer,
			WoType woType)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio por Dealer y un rango de fecha de programaci�n
	 * @param dealer - Deales
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByDealerAndProgrammingDate(
			Dealer dealer, Date initDate, Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio un rango de fecha de programaci�n
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByProgrammingDate(Date initDate,
			Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio un rango de fecha de creación
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByCreationDate(Date initDate,
			Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio por Dealer, código postal y un rango de fecha de creación
	 * @param dealer - Dealer
	 * @param postalCode - PostalCode
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByDealerPostalCodeCreationDate(
			Dealer dealer, PostalCode postalCode, Date initDate,
			Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ordenes de servicio por Estado
	 * @param woStatus - WorkorderStatus
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<WorkOrder> getWorkOrderByPreviusStatus(
			WorkorderStatus woStatus)throws DAOServiceException, DAOSQLException;

	

	/**
	 * Consulta una orden de trabajo de la base de datos dado el código que debe ser único
	 * @param string Código de la WorkOrder
	 * @return WorkOrder dado el código o nulo en caso que no se encuentre la WorkOrder con el código
	 * especificado
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WorkOrder getWorkOrderByCode(String string)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del Dealer.
	 * 
	 * @param dealerId
	 * @param woCode
	 * @param ServiceTypeId
	 * @param serviceCategoryId
	 * @param woStatusId
	 * @param stateId
	 * @param cityId
	 * @param postalCodeId
	 * @param creationDate
	 * @param programmingDate
	 * @param requestCollectionInfo
	 * @return WOByDealerWorkOrderQBEPaginationResponse
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WOByDealerWorkOrderQBEPaginationResponse getWorkOrdersByDealerWorkOrderQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del Cliente.
	 * 
	 * @param dealerId
	 * @param ibsCode
	 * @param officePhone
	 * @param homePhone
	 * @param faxPhone
	 * @param cellPhone
	 * @param idNumber
	 * @param name
	 * @param lastName
	 * @param requestCollectionInfo
	 * @return WOByCustomerQBEPaginationResponse
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WOByCustomerQBEPaginationResponse getWorkOrdersByDealerCustomerQBE(Long dealerId, String ibsCode, String officePhone, String homePhone, String faxPhone, String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del dealer, fecha y cuadrilla.
	 * 
	 * @param dealerId
	 * @param dateId
	 * @param crewId
	 * @param requestCollectionInfo
	 * @return WOByDealerDateCrewQBEPaginationResponse
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WOByDealerDateCrewQBEPaginationResponse getWorkOrdersByDealerDateCrewQBE(Long dealerId, Long dateId, Long crewId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * Query By Example. Obtiene las workorders que se encuentran en estado ACTIVE o PENDING, deacuerdo a los criterios ingresados.
	 * 
	 * @param dealerId
	 * @param dealerName
	 * @param woCode
	 * @param ServiceTypeId
	 * @param serviceCategoryId
	 * @param woStatusId
	 * @param stateId
	 * @param cityId
	 * @param postalCodeId
	 * @param creationDate
	 * @param programmingDate
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByWorkOrderDataQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId, Long countryId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Query By Example. Obtiene las workorders que se encuentran en estado ACTIVE o PENDING, deacuerdo a los criterios ingresados.
	 * 
	 * @param ibsCode
	 * @param officePhone
	 * @param homePhone
	 * @param faxPhone
	 * @param cellPhone
	 * @param idNumber
	 * @param name
	 * @param lastName
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByCustomerDataQBE(Long countryId, String ibsCode,
			String officePhone, String homePhone, String faxPhone,
			String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Obtiene una lista de órdenes de trabajo dada una lista de los identificadores
	 * @param workOrderIds Lista de <code>java.lang.Long</code> con los identificadores de las work order
	 * @return Lista de WorkOrder
	 * @throws DAOServiceException en caso de error al consultar la lista
	 * @throws DAOSQLException en caso de error al consultar la lista
	 */
	public List<WorkOrder> getWorkOrdersByIds(List<Long> workOrderIds)throws DAOServiceException, DAOSQLException;

	public List<WorkOrder> getWorkOrdersByCodes(List<String> workOrderIds)throws DAOServiceException, DAOSQLException;
	/**
	 * Permite consultar las WO por el dealer asociado y el tipo actual y previo
	 * @param dealer
	 * @param actualType
	 * @param previusType
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByActualAndPreviusType(
			Dealer dealer, WoType actualType, WoType previusType)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar las WO por cliente
	 * @param customer - Customer
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByCustomer(Customer customer)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Permite consultar las WO por cliente y su fecha de realización no es nula
	 * @param customer - Customer
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByCustomerAndRealizationDateExist(Customer customer)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite consultar las WO por Addres del cliente y su fecha de realización no es nula
	 * @param customer
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByCustomerAddressAndRealizationDateExist(
			Customer customer)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar las WO por su estado actual y el previo
	 * @param actualType - WoType
	 * @param previusType - WoType
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByActualAndPreviusType(
			WoType actualType, WoType previusType)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar las WO por su cliente y fecha de realización
	 * @param customer - Customer
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByCustomerAndRealizationDate(
			Customer customer, Date initDate, Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar las WO por su Tipo previo
	 * @param previusType - WoType
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByPreviusType(WoType previusType)throws DAOServiceException, DAOSQLException;

	/**
	 * permite consultar las WO por su dealer y fecha de realización
	 * @param dealer - Dealer
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByDealerAndRealizationDate(
			Dealer dealer, Date initDate, Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar las WO por el Dealer y el Postal Code asociados
	 * @param dealer - Dealer
	 * @param postalCode - PostalCode
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByDealerAndPostalCode(Dealer dealer,
			PostalCode postalCode)throws DAOServiceException, DAOSQLException;

	
	
	/**
	 * Metodo que garantiza que un dealer q no es el logueado no vea la workorder correspondiente y retorna una workorder por el id
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param dealerId
	 * @param workOrderId
	 * @return WorkOrder
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public WorkOrder getWorkOrderByDealerIdAndWorkOrderId(
			final Long dealerId, final Long workOrderId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Permite consultar las Wo por su fecha de realización
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByDealerAndRealizationDate(
			Date initDate, Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consulatar los WO por su fecha de creación
	 * @param delaer - Dealer
	 * @param initDate - Date
	 * @param endDate - Date
	 * @return List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public List<WorkOrder> getWorkOrderByDealerAndCreationDate(Dealer dealer, Date initDate,
			Date endDate)throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Calcula el back log:
	 * Obtiene el back log  total consultando en la tabla de ordenes de trabajo, 
	 * el tiempo total que le tomará atender las ordenes que tiene asignada 
	 * la compañía para fechas anteriores a la fecha actual y 
	 * que se encuentran en estado agendado (AG) o en dificultad (X) (pendiente) 
	 * solucionable por la compañía instaladora, 
	 * @param dealerId identificador de la compañía
	 * @param currentDate fecha actual
	 * @return cantidad de días que tardará la compañía en solucionar las órdenes que tiene
	 * pendientes o agendadas
	 * @throws DAOServiceException en caso de errror
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	public Long calculateBackLogByDealerIdAndCurrentDate(Long dealerId, Date currentDate)throws DAOServiceException, DAOSQLException;

	/**
     * permite consutlar una orden de servicio por su ID
     * @param id - Codigo de la Work Order
     * @param id - Codigo del Pais
     * @return WorkOrder Datos de la Work Order Consultada
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jforero 04/08/2010
     */
    public WorkOrder getWorkOrderByCodeAndCountry(String id, String countryCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene los datos de una work order dado el identficador del elemento asociado a una de las shipping orders
	 * de la work order
	 * @param elementId identificador del elemento
	 * @return datos de la work order asociada, nulo en caso que no se encuentre work order asociada
	 * @throws DAOServiceException en caso de error al realizar la consulta
	 * @throws DAOSQLException en caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	public WorkOrder getWorkOrderByElementId(Long elementId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Consulta una orden de trabajo de la base de datos dado el código que debe ser único
	 * @param string Código de la WorkOrder
	 * @param countryId id del pais de la WO
	 * @param customerId id del cliente
	 * @return WorkOrder dado el código o nulo en caso que no se encuentre la WorkOrder con el código
	 * especificado
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public WorkOrder getWorkOrderByCodeAndCountryAndCustomer(String woCode, String countryId, String customerId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Lista de una lista de work orders el subconjunto que esté asignado a una cuadrilla
	 * @param workOrderIds identificador de las work order
	 * @param crewId identificador de la cuadrilla
	 * @return Lista de las WO cuya última asignación está a esa cuadrilla
	 * @throws DAOServiceException en caso de error al generar el reporte
	 * @throws DAOSQLException En caso de error
	 * @author jjimenezh
	 */
	public Map<WorkOrder, WorkOrderAgenda> getWorkOrdersByIdsAndCrewAssignment(List<Long> workOrderIds, Long crewId, Long maxWoPerPdfFile)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene el código de una WO por id
	 * @param woId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public String getWoCodeByWoId(Long woId)throws DAOServiceException, DAOSQLException;

	/**
	 * Obtiene las work orders que están en estado Finalizado o realizado y:
	 * @param buildingCode código del edificio
	 * @param countryId identificador del país de la work order
	 * @return Lista de Work Orders que cumplen los criterios seleccionados ordenadas por fecha de ateción descendente es
	 * decir desde la más reciente
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 */
	public List<WorkOrder> getAttendedWorkOrdersByBuildingCodeAndCountryId(	String buildingCode, Long countryId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: filtra las wo de una dealer para la bandeja de workorders de compania instaladora CU ADS - 18
	 * @param filter WorkOrderFilterTrayDTO Objeto que contiene todos los filtros de la consulta
	 * @param RequestCollectionInfo requestCollectionInfo, datos para realizar la paginacion
	 * @return WorkOrderResponse Objeto que contiene una lista de id's de workorders que cumple con el filtro
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 */
	public WorkOrderResponse getWorkOrdersForDealerTray(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo, boolean isOrderByBuilding) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
	 * Retorna los servicios asociados a una WorkOrder.
	 * @param woId
	 * @param requestCollectionInfo informacion para paginacion
	 * @return ServiceResponse servicios asociados a la WorkOrder
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	public ServiceResponse getWorkOrderServices(Long woId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta la cantidad de WO por fecha de vencimiento
	 * @param expirationGroupingDate fecha de vencimiento
	 * @param countryId id del pais
	 * @return Cantidad de WO por fecha de vencimiento
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long countWoForExpirationGrouping(WorkOrderFilterTrayDTO filter) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta las workorders provenientes del filtro del acordeon
	 * @param filter filtro para realizar consulta
	 * @param requestCollectionInfo objeto para manejo de paginacion
	 * @return WorkOrderResponse objeto que contiene respuesta de consulta
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public WorkOrderResponse getWorkOrdersForDealerTrayWithExpirationGrouping(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene los dealers vendedores asignados a las WO de un dealer
	 * @param dealerId
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<Dealer> getSaleDealersByWoAsignDealerIdAndCountry(Long dealerId,Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**	Valida, si un código de IBS solo debe tener una WorkOrder con estado diferente 
		de cancelado o finalizado para el cliente de la WO cuando el servicio sea de subcategoría Basica
	 * @param woIbsCode String 
	 * @param woCustCodeIbs String 
	 * @param categoryCode String
	 * @param basicInstallationCategoty String 
	 * @param woCountryCode Long
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Long getWorkOrderByStateIbsWoCodeIbsWoCustCodeServiceCategoryCode(String woIbsCode, String woCustCodeIbs, String basicInstallationCategoty, Long woCountryCode)throws DAOServiceException, DAOSQLException;
	
	/** Metodo que retorna la cantidad de work orders a consultar para el reporte de jasper
	 * @param WoId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Long countWoForJasper(Long woId) throws DAOServiceException, DAOSQLException ;
	
    /**
     * Metodo: Retorna una lista de elementos de tipo WorkOrder que tengan asociado los valores 'countryIso2Code', 'ibsCustomerCode', 'postalCode' y 'addressCode'. 
     * Si scheduleDate no es null entonces en la lista de WorkOrder los elementos tienen que tener como woProgrammingDate a scheduleDate. 
     * Si scheduleDate es null entonces en la lista de WorkOrder los elementos tienen que tener como woProgrammingDate un dia posterior al dia de hoy.
     * @param countryIso2Code
     * @param ibsCustomerCode
     * @param postalCode
     * @param scheduleDate
     * @param addressCode
     * @param address
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author rdelarosa
     */
	public List<WorkOrder> getWorkOrdersByCountryCustomerPostalCodeScheduleDateWOAddress(String countryIso2Code, String ibsCustomerCode, String postalCode, java.util.Date scheduleDate, String addressCode, String address)
	   throws DAOServiceException , DAOSQLException;
	
	/**
	 * 
	 * Metodo: Calcula la cantidad de work orders que tiene pendientes de atender (asignadas y en estado agendado, reagendado, pendiente) 
	 * en la super categoría de servicio en la fecha y jornada especificadas
	 * @param dealerWCCriteria
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public int getDealerUsedCapacity(DealerWorkCapacityCriteria dealerWCCriteria)throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Metodo: Obtiene el dealer de la ultima WO en estado realizada o finalizada de un Cliente.
	 * @param ibsCustomerCode String, codigo del cliente a consultar las WO
	 * @param countryCode, codigo del pais
	 * @return List<DealerVO>, lista de dealers encontrados
	 * @author waltuzarra
	 */
	public List<Dealer> getDealerFromLastWoFromCustomer(String ibsCustomerCode, Long countryCode) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Metodo: Obtiene la ultima work order de un cliente en estado realizado o ternmido
	 * @param ibsCustomerCode
	 * @param countryCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author waltuzarra
	 */
	public List<WorkOrder> getLastWoFromCustomerFinisherOrRealized(String ibsCustomerCode, Long countryCode) throws DAOServiceException, DAOSQLException ;		
	
	/**
	 * Metodo: Obtiene la ultima work order de un cliente en estado realizado o terminado de categoria instalacion
	 * @param ibsCustomerCode
	 * @param countryCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<Dealer> getDealerFromLastServiceInstallWoFromCustomer(String ibsCustomerCode, Long countryCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Consulta las agendas de las wo teniendo en cuenta que esten asignadas a una cuadrilla
	 * @param workOrderIds
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Object[]> getWorkOrdersAgendaByIdsAndCrewNotNull(List<Long> workOrderIds) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * 
	 * Metodo: Consulta las workorders con agenda activas sin tener en cuenta la cuadrilla
	 * @param workOrderIds
	 * @return Lista de id's de wo que no tienen agenda, junto con los codigos
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Object[]> getWorkOrdersActiveAgendaByIds(List<Long> workOrderIds) throws DAOServiceException, DAOSQLException ;
	
	
	/**
	 * 
	 * Metodo: Consulta la cantidad de WO de un cliente que se encuentran en estado finalizado en los ultimos 90 dias
	 * @param customerId
	 * @return Long cantidad de WO del cliente en estado finalizado en los ultimos 90 dias
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getLast90DaysWosByCustomerAndServiceTypeStatusInFinishedStatus(Long customerId) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * 
	 * Metodo: Obtiene los dealers vendedores asignados a las WO de unos dealers
	 * @param dealerIds
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<Dealer> getSaleDealersByWoAsignDealersIdAndCountry(List<Long> dealerIds,Long countryId,
			String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Retorna un listado con los resultados del query que genera el reporte de excel
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aquevedo
	 */
	public List<ReportWorkOrderDTO> getWorkOrderByIDReport(List<Long> woIDs,Timestamp aSysdate,List<TechnologyVO> technologies,
            String activeStatus,
            String anIsResponsible,
            String anIsNotResponsible,
            String statusActiva,
            String statusAsignada,
            String statusAgendada,
            String statusReagendada,
            String statusRealizada,
            String statusFinalizada,
            String statusPendiente,
            String statusRechazada,
            String statusCancelada,
            String statusReasignada,
            String recordStatusLast,
            String elementClassDecoder,
            String workOrderTypeService,
            String woStatusWorkOrderTypeChangeHsp,
            String workOrderStatusRealized,
            String woManagmentElementClassDeco,
            String woManagmentElementClassSC,
            String woAttentionRer,
            String woAttentionReu,
            String elementIsSerialized,
            String mediaContactTypeTelepCode,
            String mediaContactTypeTelephWorkCode,
            String mediaContactTypeMobileCode,
            String mediaContactTypeMail,
            String mediaContactTypeFax,
            String codeUserControlTower,
            boolean isViewCustomerDocument,
            boolean isViewCustomerMail,
            String activeWorkOrderMark,
            String codeRequiredcontractMark,
            boolean isBuilding) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Retorna un listado con los resultados del query que genera la informacion necesaria para las bandejas
	 * @param id identificador de la WO 
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public List<Object[]> getWorkOrderByIDForTray(List<Long> woIDs, List<TechnologyVO> technologies, boolean isOrderByBuilding, Long... idUser) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta utilizada para obtener todos los servicios que tienen garantia de la ultima work order
	 * @param ibsCustomerCode
	 * @param countryCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List getServiceWithWarranty(String ibsCustomerCode, Long countryCode) throws DAOServiceException, DAOSQLException;
	/**
	 * @author Aharker
	 * Metodo: realiza la consulta del reporte de las wo atendidas y finalizadas el dia anterior para un pais
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<ReportWorkOrdersLastDayDTO> getWorkOrdersForLastDay(Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del reporte Productivity Report de core
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return DTO's del reporte
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public ReportsProductivityReportAndFileResponseDTO getReportsProductivityReport(ReportsComplyAndScheduleFilterDTO filter, 
			                                                                RequestCollectionInfo requestInfo,
			                                                                Long countryId,
				                                                            Date sysdate) throws DAOSQLException, DAOServiceException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del Reporte Pending Wo by date Report de core
	 * @param filter tiene los campos de filtro Lista de códigos de servicios y Código de la categoría.
	 * @param requestInfo en caso de usar paginacion
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public ReportsPendingServicesByDateAndFileResponseDTO getReportsPendingServicesByDate(ReportsPendingServicesByDateFilterDTO filter, 
			                                                                      RequestCollectionInfo requestInfo,
			                                                                      Long countryId,
					                                                              Date sysdate) throws DAOSQLException, DAOServiceException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del Reporte Reporte Cumplimiento Agendamiento de core
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return DTO's del reporte
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public ReportsComplyAndScheduleAndFileResponseDTO getReportsComplyAndSchedule(ReportsComplyAndScheduleFilterDTO filter, 
			                                                              RequestCollectionInfo requestInfo,
			                                                              Long countryId) throws DAOSQLException, DAOServiceException;
	
	public Long getCountryIdOfWorkOrderId(Long woId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Merodo encargado de traer las work order asociadas a un determinado grupo de work orders, teniendo en cuenta que una work order asociada es una
	 * work order que pertenezca al mismo dealer, al mismo cliente y este en un conjunto de estados dado
	 * @param states Estados posibles de las work order asociadas que buscara para la generacion del PDF
	 * @param wos work orders de las cuales se desea buscar sus work order asociadas
	 * @return work orders asociadas
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aharker
	 */
	public List<WorkOrderTrayDTO> getWorkOrdersByStatesAndCustomerAndDealer(List<String> states, List<WorkOrder> wos) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Merodo encargado de traer las work order asociadas a un determinado grupo de work orders para la generacion del PDF de la wo, 
	 * teniendo en cuenta que una work order asociada es una
	 * work order que pertenezca al mismo dealer, al mismo cliente y este en un conjunto de estados dado
	 * @param states Estados posibles de las work order asociadas que buscara para la generacion del PDF
	 * @param wos work orders de las cuales se desea buscar sus work order asociadas
	 * @return work orders asociadas.
	 * @throws DAOServiceException 
	 * @throws DAOSQLException 
	 * @author aharker
	 */
	public List<WorkOrder> getWorkOrdersByStatesAndCustomerAndDealerForPdf(List<String> states, List<WorkOrder> wos) throws DAOServiceException, DAOSQLException;

	//REQ001 - WO Canceladas.
	/**
	 * Dado el id de un dealer y un rango de fechas, se buscaran todas las WO canceladas.<br/>
	 * Para ese dealer en ese lapso de fechas.
	 *  
	 * @param dealerId el id del dealer.
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author martlago
	 */
	 public List<WorkOrder> getCanceledWorkOrdersByDealerIdAndDates(Long dealerId, Date fromDate, Date toDate) throws DAOServiceException, DAOSQLException;

}
