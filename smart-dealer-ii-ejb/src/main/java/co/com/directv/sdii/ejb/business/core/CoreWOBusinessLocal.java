package co.com.directv.sdii.ejb.business.core;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.EmailMessageException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.exceptions.ScheduleException;
import co.com.directv.sdii.model.dto.AssignWorkOrderDTO;
import co.com.directv.sdii.model.dto.CustomerInquiriesByCriteriaIBSDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.dto.WorkOrderInfoServiceVinculationDTO;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.WoProcessSource;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WOActiveAndSuspendByCountryIdPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByCustomerQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerDateCrewQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerWorkOrderQBEPaginationResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.model.vo.ShippingOrderVO;
import co.com.directv.sdii.model.vo.WoAssignmentVO;
import co.com.directv.sdii.model.vo.WoStatusHistoryVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkOrderAgendaVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;
import co.com.directv.sdii.ws.model.dto.ResponseSendMailDTO;

/**
 * Interfaz que define los métodos de negocio de los servicios de Core.
 * 
 * Casos de uso:
 * 
 * Caso de Uso ADS - 26 - Detalle del Cliente ==> Se debe realizar cuando se haga el m�dulo de Clientes
 * Caso de Uso ADS - 27 - Diligenciar Encuesta de Servicio
 * Caso de Uso ADS - 30 - Exportar Bandeja de Work Orders a Excel ==> Todas las tablas se deben exportar a Excel
 * Caso de Uso ADS - 60 - Registrar elementos NO serializados utilizados en la atención del servicio ==> Se debe realizar cuando se haga el módulo de Inventarios 
 * Caso de Uso ADS - 61 - Registrar elementos serializados utilizados en la atención del servicio ==> Se debe realizar cuando se haga el módulo de Inventarios
 * Caso de Uso ADS - 62 - Registrar  cambio  de  elementos serializados donde el cliente  en  la ate ==> Se debe realizar cuando se haga el módulo de Inventarios
 * Caso de Uso ADS - 63 - Registrar Recuperación  de  elementos serializados  donde el cliente ==> Se debe realizar cuando se haga el módulo de Inventarios
 * Caso de Uso ADS - 64 - Registrar Recuperación  de  elementos NO serializados  donde el cliente ==> Se debe realizar cuando se haga el módulo de Inventarios
 * 
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface CoreWOBusinessLocal {
	
	/**
	 * Caso de Uso ADS - 17 - Crear Work Order.
	 * 
	 * Método que se encarga de crear una WorkOrder que proviene
	 * de IBS. Se debe almacenar en la tabla WORK_ORDERS.
	 * Se debe invocar un WebService de IBS que retornará la lista
	 * de WorkOrders a crear en SmartDealer II.
	 * 
	 * Se debe notificar cambio de estado de la WorkOrder a IBS.
	 *   
	 * @throws BusinessException
	 */
	public void persistWorkOrder(String countryCode, WorkOrderDTO ibsWorkOrder) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 17 - Crear Work Order.
	 * 
	 * Método que se encarga de crear una WorkOrder que proviene
	 * de IBS. Se debe almacenar en la tabla WORK_ORDERS.
	 * Se debe invocar un WebService de IBS que retornará la lista
	 * de WorkOrders a crear en SmartDealer II.
	 * 
	 * Se debe notificar cambio de estado de la WorkOrder a IBS.
	 * @param countryCode codigo del pais del cual se consultaran de ESB las work order encoladas
	 * @return  
	 * @throws BusinessException
	 */
	public List<com.directvla.schema.crm.common.v1_1.WorkOrder> getWorkordersFromIBS(String countryCode) throws BusinessException;

	
	/**
	 * Caso de Uso ADS - 18 - Visualizar Bandeja de Work Orders de la Compañía Instaladora.
	 *  
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer dealer del que se buscan las work order
	 * @param requestCollectionInfo
	 * @return WOByDealerPaginationResponse
	 * @throws BusinessException
	 */
	public WOByDealerPaginationResponse getWorkOrderByDealer(DealerVO dealer, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del Dealer.
	 * 
	 * @param dealerId id del dealer
	 * @param woCode codigo de la work order
	 * @param serviceTypeId id del tipo de servicio
	 * @param serviceCategoryId id de la categoria de servicio
	 * @param woStatusId id de estado de la work order
	 * @param stateId id del estado
	 * @param cityId id de la ciudad
	 * @param postalCodeId id del codigo postal
	 * @param creationDate fecha de creacion
	 * @param programmingDate
	 * @param requestCollectionInfo
	 * @return WOByDealerWorkOrderQBEPaginationResponse
	 * @throws BusinessException
	 */
	public WOByDealerWorkOrderQBEPaginationResponse getWorkOrdersByDealerWorkOrderQBE(Long dealerId, String woCode,
			Long serviceTypeId, Long serviceCategoryId, Long woStatusId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del Cliente.
	 * 
	 * @param dealerId id del dealer
	 * @param ibsCode codigo de IBS
	 * @param officePhone telefono de la oficina
	 * @param homePhone telefono de la casa
	 * @param faxPhone telefono fax
	 * @param cellPhone telefono celular
	 * @param idNumber
	 * @param name
	 * @param lastName
	 * @param requestCollectionInfo
	 * @return WOByCustomerQBEPaginationResponse
	 * @throws BusinessException
	 */
	public WOByCustomerQBEPaginationResponse getWorkOrdersByDealerCustomerQBE(Long dealerId, String ibsCode, String officePhone, String homePhone, 
			String faxPhone, String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo) throws 
			BusinessException;
	
	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del dealer, fecha y cuadrilla.
	 * 
	 * @param dealerId id del dealer
	 * @param dateId
	 * @param crewId id de la cuadrilla
	 * @param requestCollectionInfo
	 * @return
	 * @throws BusinessException
	 */
	public WOByDealerDateCrewQBEPaginationResponse getWorkOrdersByDealerDateCrewQBE(Long dealerId, Long dateId, Long crewId, 
			RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	

	/**
	 * Caso de Uso ADS - 19 - Asignar Programa a Work Orders.
	 * 
	 * Método que se encarga de asignar un programa a una WorkOrder.
	 * Se debe almacenar en la tabla WO_ASSIGNMENTS.
	 * La WorkOrder ya debe existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workorder
	 * @param program
	 * @throws BusinessException
	 */
	public void assignProgramToWorkOrder(WorkOrderVO workorder, ProgramVO program) throws BusinessException;
	
	
	/**
	 * Caso de Uso ADS - 19 - Asignar Programa a Work Orders.
	 * 
	 * Metodo que se encarga de asignar un programa a una WorkOrder.
	 * Se debe almacenar en la tabla WO_ASSIGNMENTS.
	 * La WorkOrder ya debe existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workorder
	 * @param program
	 * @throws BusinessException
	 */
	public void assignProgramToWorkOrders(List<WorkOrderVO> workorders, ProgramVO program) throws BusinessException;

	/**
	 * Caso de Uso ADS - 20 - Asignar Cuadrilla a Work Orders.
	 * 
	 * Método que se encarga de asignar una cuadrilla fija a una WorkOrder.
	 * Se debe almacenar en la tabla WO_ASSIGNMENTS.
	 * La WorkOrder ya debe existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workorder - WorkOrderVO
	 * @param crew - CrewVO
	 * @throws BusinessException
	 * @author gfandino
	 */
	public void assignFixedCrewToWorkOrder(WorkOrderVO workorder, CrewVO crew, Long userId) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 20 - Asignar Cuadrilla a Work Orders.
	 * 
	 * Método que se encarga de asignar una cuadrilla fija a un conjunto de WorkOrder.
	 * Se debe almacenar en la tabla WO_ASSIGNMENTS.
	 * La WorkOrder ya debe existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workorder - WorkOrderVO
	 * @param crew - CrewVO
	 * @throws BusinessException
	 * @author gfandino, aharker
	 */
	public void assignFixedCrewToWorkOrders(List<WorkOrderInfoServiceVinculationDTO> workordersCrews, Long userId) throws BusinessException ;

	/**
	 * Caso de Uso ADS - 21 - Asignar Cuadrilla Dinamica a Work Orders.
	 *  
	 * Método que se encarga de asignar una cuadrilla dinámica a una WorkOrder.
	 * Se debe almacenar en la tabla WO_ASSIGNMENTS.
	 * La WorkOrder ya debe existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workorder
	 * @param crew
	 * @throws BusinessException
	 */
	public boolean assignDynamicCrewToWorkOrder(List<WorkOrderVO> workOrderList, CrewVO crew, Long userId) throws BusinessException;

	/**
	 * Caso de Uso ADS - 22 - Agendar Work Order.
	 * 
	 * Método que se encarga de agendar una WorkOrder.
	 * Se debe almacenar en la tabla WO_AGENDA.
	 * La WorkOrder ya debe existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar método updateDealerServiceCapacity).
	 * 
	 * @param trayWOManagmentDTO TrayWOManagmentDTO objeto que encapsula informacion para agendamiento
	 * @throws BusinessException
	 */
	public void agendaWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException;

	
	/**
	 * Caso de Uso ADS - 23 - Rechazar Work Order.
	 * 
	 * Método que se encarga de registrar la raz�n del rechazo de una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar método updateDealerServiceCapacity).
	 * 
	 * @param trayWOManagmentDTO TrayWOManagmentDTO dto que encapsula informacion para rechazo de WO
	 * @throws BusinessException
	 * @author gfandino
	 * @author jnova
	 */
	public void rejectWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException;

	/**
	 * Caso de Uso ADS - 24 - Dificultad en Work Order.
	 * 
	 * Método que se encarga de registrar una dificultad en una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS, ejecutando el caso de uso
	 * CU ADS - 33 Notificar cambio de estado de Work Order a IBS.
	 * 
	 * @param trayWOManagmentDTO TrayWOManagmentDTO objeto que encapsula informacion de la WO
	 * @throws BusinessException
	 * @author jnova
	 */
	public void reportDifficultyToWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException;

	/**
	 * Caso de Uso ADS - 25 - Detalle de la Work Order.
	 * 
	 * Método que se encarga de obtener una WorkOrder por ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param id id de la work order que se desea buscar
	 * @return WorkOrderVO
	 * @throws BusinessException
	 */
	public WorkOrderVO getWorkOrderByID(Long id) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 29 - Generar Planilla en PDF.
	 * 
	 * Método que genera los formatos de las WorkOrder en PDF y los deja
	 * en una carpeta del servidor para luego ser descargadas por el usuario.
	 * 
	 * Por cada WorkOrder se generan: 
	 * Ver página 22 del documento anexo Capitulo #3 - Administración de Servicios - Smart Dealer.doc
	 * 
	 * No se trata de un proceso en línea. Ejecuta una tarea con Quartz que
	 * realiza la generaci�n de los PDF en background.
	 * 
	 * @param workOrderIds
	 * @param crew
	 * @return
	 * @throws PDFException
	 * @throws BusinessException 
	 */
	public String generateCrewWorkOrdersPDF(List<Long> workOrderIds, CrewVO crew) throws PDFException, BusinessException;
	
	/**
	 * Caso de Uso ADS - 29 - Generar Planilla en PDF.
	 * 
	 * Método que genera los formatos de las WorkOrder en PDF y los deja
	 * en una carpeta del servidor para luego ser descargadas por el usuario.
	 * 
	 * Por cada WorkOrder se generan: 
	 * Ver página 22 del documento anexo Capitulo #3 - Administración de Servicios - Smart Dealer.doc
	 * 
	 * No se trata de un proceso en línea. Ejecuta una tarea con Quartz que
	 * realiza la generaci�n de los PDF en background.
	 * 
	 * @param workOrderIds
	 * @param crewIds
	 * @throws PDFException
	 * @throws BusinessException 
	 */
	public String generateCrewWorkOrdersPDF(List<Long> workOrderIds, List<Long> crewIds) throws PDFException, BusinessException;

	
	/**
	 * Retorna la lista de archivos de reporte PDF para que luego sean enviados por email
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param dealerCode
	 * @return List<String>
	 * @throws BusinessException
	 */
	public List<String> getWorkOrdersFilesByDealer(final Long dealerCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 31 - Enviar Work Orders vía email.
	 * 
	 * Método que permite enviar una lista de WorkOrders en formato PDF a una cuadrilla.
	 *  
	 * @param fileNames nombres de archivos que se enviaran adjuntos
	 * @param filterCrewId
	 * @param countryId id del pais
	 * @throws EmailMessageException
	 */
	public ResponseSendMailDTO sendWorkOrdersPDFEmail(String[] fileNames, final Long filterCrewId, Long countryId) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 33 - Notificar cambio de estado de Work Order a IBS.
	 * 
	 * Método que se encarga de cambiar el estado de una WorkOrder.
	 * Por cada cambio de estado se debe almacenar un registro en la tabla WO_STATUS_HISTORY.
	 * 
	 * Antes de realizar el cambio de estado es necesario validar si el cambio es posible. Estos cambios
	 * de estado se validan con la operación workOrderStateMachine.
	 * 
	 * Este método invoca la operación (WebService) sobre IBS que permite notificarle dicho cambio de estado.
	 * 
	 * @param workorder work order a la que se le cambiara el estado
	 * @param newWorkOrderStatus nuevo estado de la work order
	 * @param reason razon del cambio de estado
	 * @throws BusinessException
	 */
	public void changeWorkOrderStatus(WorkOrderVO workorder, WorkorderStatusVO newWorkOrderStatus, WorkorderReasonVO reason) throws BusinessException;

	/**
	 * Método: Se genera para soportar el proceso de negocio
	 * @param woDTO DTO de representacion de la work order
	 * @param newStatusId id del nuevo estado de la work order
	 * @param woReason razon del cambio de estado de la work order
	 * @param description descripcion del cambio
	 * @param userId id del usuario
	 * @return
	 * @throws BusinessException
	 */
	public WorkOrder changeWorkOrderStatus(WorkOrderDTO woDTO, Long newStatusId, WorkorderReason woReason, String description,Long userId,WoStatusHistory woStatusHistory,String woDescription) throws 
		BusinessException;
	
	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignación Manual o Cancelación.
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders en estado WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING.
	 * Se lee de la tabla WORK_ORDERS.
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrdersActiveAndSuspend() throws BusinessException;

	/**
	 * Método que se encarga de obtener una lista de WorkOrders en estado WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING.
	 * Se lee de la tabla WORK_ORDERS.
	 * @param countryId id del pais
	 * @param requestCollectionInfo
	 * @return
	 * @throws BusinessException En caso de error al obtener las wo acticvas y pendientes
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersActiveAndSuspendByCountryId(Long countryId, RequestCollectionInfo requestCollectionInfo)throws BusinessException;

	
	
	/**
	 * Caso de Uso ADS - 36 - Asignac�n Manual de Work Order.
	 * 
	 * Método que se encarga de asignar una WorkOrder a un Dealer.
	 * 
	 * Se debe notificar el cambio de estado a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar m�todo updateDealerServiceCapacity).
	 * 
	 * @param dto - AssignWorkOrderDTO
	 * @throws BusinessException
	 */
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 36 - Asignac�n Manual de Work Order.
	 * 
	 * M�todo que se encarga de asignar una WorkOrder a un Dealer.
	 * 
	 * Se debe notificar el cambio de estado a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar m�todo updateDealerServiceCapacity).
	 * 
	 * @param dto - AssignWorkOrderDTO
	 * @param notify2Ibs - boolean
	 * @throws BusinessException
	 */
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto, boolean notify2Ibs) throws BusinessException;
	
	
	/**
	 * Caso de Uso ADS - 36 - Asignacón Manual de Work Order.
	 * 
	 * Método que se encarga de asignar una WorkOrder a un Dealer.
	 * 
	 * Se debe notificar el cambio de estado a IBS.
	 * Si la work order ya había sido agendada y no tenía una asignación previa a otro dealer, se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar método updateDealerServiceCapacity).
	 * 
	 * @param dto - AssignWorkOrderDTO
	 * @param workorderReasonCode - String
	 * @throws BusinessException
	 */
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto, String workorderReasonCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 36 - Asignacón Manual de Work Orders.
	 * 
	 * Método que se encarga de asignar una WorkOrder a un Dealer.
	 * 
	 * Se debe notificar el cambio de estado a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar método updateDealerServiceCapacity).
	 * 
	 * @param dto
	 * @param workorders
	 * @throws BusinessException
	 */
	public void assignWorkOrdersToDealer(AssignWorkOrderDTO dto,List<WorkOrderVO> workorders) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 37 - Cancelar Work Order.
	 * 
	 * Método que se encarga de registrar la raz�n de la cancelaci�n de una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar m�todo updateDealerServiceCapacity).
	 * 
	 * @param trayWOManagmentDTO TrayWOManagmentDTO 
	 * @throws BusinessException
	 */
	public void cancelWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 37 - Cancelar Work Order.
	 * 
	 * Método que se encarga de registrar la razon de la cancelacion de lista de WorkOrders.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar m�todo updateDealerServiceCapacity).
	 * 
	 * @param trayWOManagmentDTOList - List<TrayWOManagmentDTO> 
	 * @throws BusinessException
	 */
	public void cancelWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 40 - Notificar cambio de estado de Work Order por IBS.
	 * 
	 * Método que se encarga de cambiar el estado de una WorkOrder en SmartDealer II, proveniente desde IBS.
	 * 
	 * Por cada cambio de estado se debe almacenar un registro en la tabla WO_STATUS_HISTORY.
	 * 
	 * Antes de realizar el cambio de estado es necesario validar si el cambio es posible. Estos cambios
	 * de estado se validan con la operación workOrderStateMachine.
	 * 
	 * Este método invoca la operación (WebService) sobre IBS que permite consultar los cambios de estado.
	 * 
	 * @param customerId id del cliente
	 * @param countryId id del pais
	 * @param workOrderCode codigo de la work order
	 * @param workOrderReason2ChangeCode
	 * @param workOrderReason2CompleteCode
	 * @param flag2KnowProcess2Execute
	 * @param newWorkOrderStatusCode
	 * @throws BusinessException
	 */
	public void changeWorkOrderStatus(Long customerId, Long countryId, String workOrderCode, String workOrderReason2ChangeCode, String workOrderReason2CompleteCode, String flag2KnowProcess2Execute, String newWorkOrderStatusCode) throws BusinessException;

//	/**
//	 * Caso de Uso ADS - 41 - Notificar Cambio Fecha de Atenci�n del Servicio desde IBS.
//	 * 
//	 * Método que permite informar a IBS el cambio de fecha de atenci�n de una WorkOrder.
//	 * 
//	 * @param Long customerID id del cliente
//	 * @param Long workOrderNumer codigo de la workorder
//	 * @param String newRealizationDate nueva fecha de servicio
//	 * @param String workOrderCountryId codigo del pais
//	 * @param newRealizationDate
//	 * @param newRealizationDate
//	 * @throws BusinessException
//	 */
//	public Long changeRealizationDateIBS(Long customerID, Long workOrderNumer, String newRealizationDate, String workOrderCountryId) throws BusinessException;
	
	/**
	 * Metodo: Caso de Uso ADS - 42 - Adicionar Service a Work Order.
	 * Permite adicionar un nuevo servicio a una WorkOrder proveniente de IBS. Retorna mensaje 
	 * con resultado
	 * @param workOrderId id de la work order
	 * @param serviceId id del servicio
	 * @param decoSerialNumber serial del decodificador
	 * @param countryCode codigo del pais
	 * @param userId id del usuario
	 * @throws BusinessException <tipo> <descripcion>
	 * @author gfandino
	 */
	public void addServiceToWorkOrder(Long workOrderId, Long serviceId, String decoSerialNumber, String countryCode, Long userId) throws 
		BusinessException;
	
	/**
	 * Caso de Uso ADS - 43 - Quitar Service de Work Order.
	 * 
	 * Método que permite quitar un servicio a una WorkOrder proveniente de IBS.
	 * 
	 * @param workOrderId id de la work order
	 * @param serviceId id del servicio
	 * @param decoderSerialNumber serial del decodificador
	 * @param countryCode codigo del pais
	 * @throws BusinessException
	 */
	public void deleteServiceToWorkOrder(Long workOrderId, Long serviceId, String decoderSerialNumber, String countryCode) throws BusinessException;
	
	// =============== M�todos utilitarios para el m�dulo de Core =====================
	
	/**
	 * 
	 * Método que se encarga de obtener una WorkOrder por DEALER_ID y WO_CODE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param code
	 * @return WorkOrderVO
	 * @throws BusinessException
	 */
	public WorkOrderVO getWorkOrderByDealerAndCode(DealerVO dealer, String code) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y ACTUAL_STATUS_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param actualStatus estado actual de work order, por el cual se desea consultar
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByActualStatus(DealerVO dealer, WorkorderStatusVO actualStatus) throws BusinessException;
	
	
	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por PREVIUS_STATUS_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param previusStatus
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByPreviusStatus(WorkorderStatusVO previusStatus) throws BusinessException;
	
	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y PREVIUS_STATUS_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param previusStatus
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByPreviusStatus(DealerVO dealer, WorkorderStatusVO previusStatus) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por WO_TYPE_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param actualType
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByActualType(WoTypeVO actualType) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y WO_TYPE_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param actualType
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByActualType(DealerVO dealer, WoTypeVO actualType) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por PREVIUS_WO_TYPE_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param previusType
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByPreviusType(WoTypeVO previusType) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y PREVIUS_WO_TYPE_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param previusType
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByPreviusType(DealerVO dealer, WoTypeVO previusType) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por WO_TYPE_ID y PREVIUS_WO_TYPE_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param actualType
	 * @param previusType
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByActualAndPreviusType(WoTypeVO actualType, WoTypeVO previusType) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID, WO_TYPE_ID y PREVIUS_WO_TYPE_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param actualType
	 * @param previusType
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByActualAndPreviusType(DealerVO dealer, WoTypeVO actualType, WoTypeVO previusType) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y WO_PROGRAMMING_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByDealerAndProgrammingDate(DealerVO dealer, Date initDate, Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y WO_REALIZATION_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByDealerAndRealizationDate(DealerVO dealer, Date initDate, Date endDate) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y CREATION_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByDealerAndCreationDate(DealerVO dealer, Date initDate, Date endDate) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por CREATION_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByCreationDate(Date initDate, Date endDate) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por WO_PROGRAMMING_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByProgrammingDate(Date initDate, Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por WO_REALIZATION_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByRealizationDate(Date initDate, Date endDate) throws BusinessException;
	

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y POSTAL_CODE_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param postalCode
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByDealerAndPostalCode(DealerVO dealer, PostalCodeVO postalCode) throws BusinessException;
	
	/**
	 * Método que garantiza que un dealer q no es el logueado no vea la workorder correspondiente y retorna una workorder por el id
	 * @param dealerId id del dealer
	 * @param workOrderId id de la work order
	 * @return WorkOrderVO
	 * @throws BusinessException
	 */
	public WorkOrderVO getWorkOrderByDealerIdAndWorkOrderId(final Long dealerId, final Long workOrderId) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID, POSTAL_CODE_ID y CREATION_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param postalCode
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeCreationDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate) throws 
		BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID, POSTAL_CODE_ID y WO_PROGRAMMING_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param postalCode
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeProgrammingDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate) 
		throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID, POSTAL_CODE_ID y WO_REALIZATION_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param postalCode
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeRealizationDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate) 
		throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por CUSTOMER_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param customer cliente del cual se quieren consultar las work order
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByCustomer(CustomerVO customer) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por CUSTOMER_ID y CREATION_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param customer
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByCustomerAndCreationDate(CustomerVO customer,Date initDate, Date endDate) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por CUSTOMER_ID y WO_PROGRAMMING_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param customer
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByCustomerAndProgrammingDate(CustomerVO customer,Date initDate, Date endDate) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por CUSTOMER_ID y WO_REALIZATION_DATE.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param customer
	 * @param initDate
	 * @param endDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByCustomerAndRealizationDate(CustomerVO customer,Date initDate, Date endDate) throws BusinessException;
	
	/**
	 * Método utilitario para obtener una lista de WorkOrder Por estado actual.
	 * @param actualStatus estado de las work orders que se desean consultar
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByActualStatus(WorkorderStatusVO actualStatus) throws BusinessException;
	
	/**
	 * Query By Example. Obtiene las workorders que se encuentran en estado ACTIVE o PENDING, deacuerdo a los criterios ingresados.
	 * 
	 * @param dealerId id del dealer
	 * @param dealerName nombre del dealer
	 * @param woCode codigo IBS de la work order
	 * @param ServiceTypeId id del tipo de servicio
	 * @param serviceCategoryId id de la categoria de servicio
	 * @param woStatusId id del estado de work order
	 * @param stateId id del estado
	 * @param cityId id de la ciudad
	 * @param postalCodeId id del codigo postal
	 * @param creationDate fecha de creacion
	 * @param programmingDate
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByWorkOrderDataQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId, Long countryId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	
	/**
	 * Query By Example. Obtiene las workorders que se encuentran en estado ACTIVE o PENDING, deacuerdo a los criterios ingresados.
	 * @param countryId id del pais
	 * @param ibsCode codigo ibs
	 * @param officePhone telefono de la oficina
	 * @param homePhone telefono de la casa
	 * @param faxPhone telefono fax
	 * @param cellPhone telefono celular
	 * @param idNumber
	 * @param name
	 * @param lastName
	 * @param requestCollectionInfo
	 * @return
	 * @throws BusinessException
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByCustomerDataQBE(Long countryId, String ibsCode,
			String officePhone, String homePhone, String faxPhone,
			String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;
	
	/**
	 * 
	 * Método: Retorna un WoStatusHistory con la workorderreason
	 * del ultimo estado de una workorder.
	 * @param woId Long
	 * @param woStatusId Long
	 * @return WoStatusHistoryVO
	 * @throws BusinessException 
	 * @author jalopez
	 */
	 public WoStatusHistoryVO getWorkorderReasonByWoHistory(Long woId,Long woStatusId) throws BusinessException;
	 
	 /**
     * 
     * Método: Retorna un listado de WorkOrderAgenda, filtrando por
     * el dealerId y WorkOrderId por medio de la WorkOrder Assignment
     * @param woId Long
     * @param dealerId Long
     * @return List<WorkOrderAgenda>
     * @throws BusinessException
     * @author jalopez
     */
	 public List<WorkOrderAgendaVO> getWorkOrderAgendaByWoAssignment(Long woId,Long dealerId) throws BusinessException;

	 /**
	  * Método: Permite consultar las WorkorderReason que pertenecen a las WorkorderStatus
	  * @param woStatus - WorkorderStatusVO
	  * @param userId Long
	  * @return List<WorkorderReasonVO>
	  * @throws BusinessException
	  * @author gfandino
	  */
	public List<WorkorderReasonVO> getWorkOrderReasonByWoStatus(WorkorderStatusVO woStatus,Long userId) throws BusinessException;
	
	/**
	 * Busca un cliente por id
	 * @param customerId id del cliente que se desea buscar
	 * @return
	 * @throws BusinessException
	 */
	public CustomerVO getCustomerById(Long customerId) throws BusinessException;

	/**
	 * 
	 * @param customerCode codigo ibs del cliente
	 * @return
	 * @throws BusinessException
	 */
	public CustomerVO getCustomerByCode(String customerCode) throws BusinessException;

	/**
	 * 
	 * @param workOrderId id de la work order
	 * @param workOrderCode codigo ibs de la work order
	 * @return
	 * @throws BusinessException
	 */
	public CustomerVO getCustomerByWoIdOrWoCode(Long workOrderId,String workOrderCode) throws BusinessException;

	/**
	 * Método: Obtiene la última asignación del dealer a una work order
	 * @param workOrderId identificador de la work order
	 * @return asignación de la work order
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public WoAssignmentVO getLastWorkOrderAssigmentByWoId(Long workOrderId) throws BusinessException;
	
	/**
	 * Método: Obtiene la lista de las relaciones entre la work order y los servicios
	 * @param workOrderId identificador de la work order
	 * @return Lista con las relaciones de servicios con las work order, una lista vacia en caso que no
	 * se obtengan resultados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<WorkOrderServiceVO> getWorkOrderServicesByWoId(Long workOrderId)throws BusinessException;
	
	/**
	 * Caso de Uso IVR - 01 - Consulta de Work Order.
	 * 
	 * Método que se encarga de obtener una WorkOrder por ID y CountryCode.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param id Identificador de la Word Order
	 * @param countryCode Codigo del Pais 
	 * @return WorkOrderVO Datos del a Work Order Obtenida
	 * @throws BusinessException
	 * @author jforero 04/08/2010
	 */
	public WorkOrderVO getWorkOrderByCodeAndCountry(String id, String countryCode) throws BusinessException;
	
	/**
	 * Metodo: permite obtener una WorkOrder por woCode y CountryCode
	 * de la tabla WORK_ORDERS.
	 * @param countryCode codigo del pais
	 * @param woCode codigo ibs de la work order
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public WorkOrder getWorkOrderByWoCodeAndCountryCode(String countryCode,String woCode)throws BusinessException;
	
	
	/**
	 * Caso de uso IVR - 03 Consultar una shipping order
	 * 
	 * Método: Consulta una shipping order por si identificador
	 * @param shipOrderID Identificador de la shipping order
	 * @param woCode Identificador de la work order
	 * @param countryCode Identificador del pais
	 * @return ShippingOrderVO Datos de la shippinf order consultada
	 * @throws BusinessException 
	 * @author jforero 09/08/2010
	 */
	public ShippingOrderVO getShippingOrderByCodeAndWOCodeAndCountry(String shipOrderID, String woCode, String countryCode) throws BusinessException;

	/**
	 * Obtener nombres de archivos 
	 * @param crewId identificador de la cuadrilla
	 * @return lista con
	 * @throws BusinessException
	 */
	public List<String> getWorkOrdersFilesByCrewId(Long crewId)throws BusinessException;
	
	/**
	 * Método: Consulta de shipping orders por codigo de work orer y pais de la work order
	 * @param woCode Codigo de la workorder
	 * @param countryCode Codigo del pais
	 * @return List<ShippingOrderVO> Lista de shipping orders encontradas
	 * @throws BusinessException 
	 * @author jforero	11/08/2010
	 */
	public List<ShippingOrderVO> getShippingOrdersByWOCodeAndCountry(String woCode, String countryCode)throws BusinessException;
	
	 
    /**
     * Método: Permite consultar las work order paginadas
     * resultado
     * @param countryId id del pais
     * @return Lista de work orders
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public List<WorkOrderVO> getWorkOrdersByPage(Long countryId, Long woId, String statusCodeProccess) throws BusinessException;

	/**
	 * Método: Cambia el estado de una work order sin notificar a ibs
	 * @param workOrder work order a la cual se le desea cambiar el estado
	 * @param workorderStatusVO
	 * @param woReasonVO razon por la cual se cambia el estado de la work order
	 * @throws BusinessException 
	 * @author jjimenezh
	 */
	public void changeWorkOrderStatusWithoutIbsNotification(WorkOrderVO workOrder, WorkorderStatusVO workorderStatusVO,
			WorkorderReasonVO woReasonVO) throws BusinessException;
	
	/**
	 * Metodo: Cambia el estado de una orden de trabajo sin notificar a ibs por que previamente ya se habia enviado un agendamiento
	 * @param woDTO
	 * @param newStatusId
	 * @param woReason
	 * @param description
	 * @param userId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public WorkOrder changeWorkOrderStatusWithoutIbsNotificationScheduleCSR(WorkOrderDTO woDTO, 
			                                                                Long newStatusId, 
			                                                                WorkorderReason woReason, 
			                                                                String description,
			                                                                Long userId) throws BusinessException;
	
	/**
	 * Método: realiza el cambio de estado de una work order
	 * @param customerCode Codigo del customer
	 * @param countryCode Copdigo del Pais
	 * @param workOrderCode Codigo de la Work order
	 * @param workOrderReason2ChangeCode Codigo de la razon del cambio
	 * @param newWorkOrderStatusCode Codigo del nuevo estado
	 * @throws BusinessException 
	 * @author jforero 26/08/2010
	 */
	public void changeWorkOrderStatus(String customerCode, String countryCode, String workOrderCode, String workOrderReason2ChangeCode,String newWorkOrderStatusCode) throws BusinessException;
	
	/**
	 * Method: Valida si una Work order tiene agendamiento
	 * @param workOrderId Long
	 * @param dealerId Long 
	 * @return Boolean
	 * @throws BusinessException
	 * @author jalopez
	 */
	public Boolean getValidateWorkOrderScheduled(Long workOrderId, Long dealerId) throws BusinessException;

	/**
	 * 
	 * Metodo: Calcula la cantidad de work orders que tiene pendientes de atender (asignadas y en estado agendado, reagendado, pendiente) 
	 * en la super categoría de servicio en la fecha y jornada especificadas
	 * @param dealerWCCriteria citerios para realizar el calculo
	 * @return Cantidad de WO que cumplen con el criterio
	 * @throws ScheduleException <tipo> <descripcion>
	 * @author jnova
	 */
	public int getDealerUsedCapacity(DealerWorkCapacityCriteria dealerWCCriteria) throws ScheduleException;
	
	/**
	 * 
	 * Metodo: Crea el historico de cambio de estado
	 * de la WorkOrder de ibs
	 * @param workOrder WorkOrder
	 * @param woReason WorkorderReason
	 * @param description String
	 * @param newWorkorderStatus WorkorderStatus
	 * @param ibsHistoryEventCode
	 * @param typeChange
	 * @return WoStatusHistory
	 * @throws BusinessException
	 * @author jalopez
	 */
	public WoStatusHistory createWoStatusHistory(WorkOrder workOrder,WorkorderReason woReason, String description, WorkorderStatus newWorkorderStatus,String ibsHistoryEventCode,String typeChange, Long userId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Crea el historico de cambio de estado
	 * de la WorkOrder en HSP
	 * @param workOrder WorkOrder
	 * @param woReason WorkorderReason
	 * @param description String
	 * @param newWorkorderStatus WorkorderStatus
	 * @param ibsHistoryEventCode
	 * @return WoStatusHistory
	 * @throws BusinessException
	 * @author cduarte
	 */
	WoStatusHistory createWoStatusHistoryHSP(WorkOrder workOrder,
			WorkorderReason woReason, String description,
			WorkorderStatus newWorkorderStatus, String ibsHistoryEventCode, Long userId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Crea un Contact de Gestion de la WorkOrder
	 * @param workOrderId Long
	 * @param woStatusHistory WoStatusHistory
	 * @param woReason WorkorderReason
	 * @param userId Long
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void createWoContact(Long workOrderId, WoStatusHistory woStatusHistory, WorkorderReason woReason, Long userId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Genera el historico de cambio de estado
	 * de la WO y crea el Contact asociado.
	 * @param workOrderId Long
	 * @param newStatusId Long
	 * @param woReason WorkorderReason
	 * @param description String
	 * @param Long userId
	 * @return WorkOrder
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public WorkOrder changeWorkOrderHistoryCreateContact(Long workOrderId, Long newStatusId, WorkorderReason woReason, String description,Long userId) throws BusinessException;

	/** metodo usado para validar si una WO es valida para generar PDF, este devuelve una lista con un codigo 
	 * de WO y las excepciones que tenga tal WO. Si la WO no tiene problemas entonces este no devuelve el codigo
	 * de la WO en la lista 
	 * @param woIds List<Long>
	 * @param countryId
	 * @return List<WoPdfExceptionDTO>
	 * @throws BusinessException
	 */
	public List<EnvelopeEncapsulateResponse> validateWoBeforeGeneratingPdf(List<Long> woIds, Long countryId)  throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza el agendamiento masivo de workorders
	 * @param trayWOManagmentDTOList List<TrayWOManagmentDTO> Lista de objetos que contienen informacion necesaria para el agendamiento masivo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void agendaWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException;
	
	 /**
     * 
     * Metodo: Consulta los registros de cambios de estado de WO por codigo de workorder y key del cambio de estado de IBS
     * @param woCode codigo de WO
     * @param historyKey key retornado por ibs en el momento de realizar el historial
     * @return List<WoStatusHistoryVO>
     * @throws throws BusinessException
     * @author jnova
     */
    public List<WoStatusHistoryVO> getWoStatusHistoryByWoCodeAndHistoryKey(String woCode , String historyKey) throws BusinessException;
    
    
	/**
	 * 
	 * Metodo: Permite consultar si se agendan las wo se sobreagenda el dealer
	 * @param dealerWorkCapacityCriteria DealerWorkCapacityCriteria filtro del capacityDate, countryId, dealerId, serviceHourId, superCategoryServiceId;
	 * @param quantityWorkOrder
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduled(DealerWorkCapacityCriteria dealerWorkCapacityCriteria, int quantityWorkOrder) throws BusinessException;
	
	/**
	 * Metodo: Verifica si la fecha de agendamiento es valida
	 * @param userId id del usuario
	 * @param countryId id del pais
	 * @param agendationDate
	 * @param serviceHour
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public boolean checkServiceHourForWoDownload(Long userId,
            Long countryId,
            Date agendationDate,
            ServiceHour serviceHour) throws BusinessException;
	
	/**
	 * Método encargado de actualizar el estado ibs de la workorder cuando el proceso fué gestionado directamente 
	 * desde HSP+, cuando las WO vuelve a bajar se verifica el estado con respecto a IBS para actualizar solamente el estado
	 * @param woCode codigo de la work order
	 * @param ibsStatusCode codigo ibs del estado que se le dejara en IBS a la work order
	 * @throws BusinessException
	 * @autor waguilera
	 */
	public void updateIBSStatusForWorkOrder(String woCode, String ibsStatusCode) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener el WoProcessSource dependiendo si del CSR o IBS
	 * @param woCode codigo de la work order
	 * @param actualStatusCode codigo del estado actual de la work order
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public WoProcessSource getWoProcessSourceByWoCodeActualStatus(String woCode, String actualStatusCode) throws BusinessException;
	
	/**
	 * Metodo: Consulta la informacion principal de una WorkOrder
	 * @param countryCode codigo del pais
	 * @param woCode codigo de la work order
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder getCustomerWorkOrdersById(String countryCode,String woCode) throws BusinessException;
	
	/**
	 * Metodo: Convierte la work order de IBS en una de SDII
	 * @param ibsWo
	 * @param countryCode
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public WorkOrderDTO convertIbsWoIntoSdiiWo(co.com.directv.sdii.dto.esb.WorkOrder ibsWorkorder,String countryCode) throws BusinessException;
	
	/**
	 * Metodo: permite cambiar la work order a estado agendamiento antes de persistir
	 * @param countryCode
	 * @param workOrderDto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void callEsbByChangeStateScheduleBeforePersistentWorkOrder(String countryCode,WorkOrderDTO workOrderDto) throws BusinessException;


	/**
	 * Metodo: Permite asignar el dealer vendedor a la work order en el caso de exceptionar se coloca el dealerDummy
	 * @param workOrderDto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void assignDealerSaleInformation(WorkOrderDTO workOrderDto) throws BusinessException;
	
	/**
	 * REQ001 - WO Canceladas.
	 * Dado el id de un dealer se buscaran todas las WO canceladas.<br/>
	 * Entre la primera hora del dia de hoy y la ultima hora de 3 dias adelante.. 
	 *  
	 * @param dealerId el id del dealer.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author martlago
	 */
	public List<WorkOrderVO> getCanceledWorkOrdersByDealerId(Long dealerId) throws BusinessException;

/**
	 * Metodo: Permite descargar los contact del cliente para persistirlos en HSP+
	 * @param countryCode
	 * @param processDate
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void downloadWorkOrderContact(CustomerInquiriesByCriteriaIBSDTO request) throws BusinessException;
	
	/**
	 * Metodo: Permite poblar un CustomerInquiriesByCriteriaIBSDTO para consultar los contact de IBS
	 * @param countryId
	 * @param processDate
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public CustomerInquiriesByCriteriaIBSDTO populateCustomerInquiriesByCriteriaIBSDTO(Long countryId, Date processDate) throws BusinessException;
	
}
