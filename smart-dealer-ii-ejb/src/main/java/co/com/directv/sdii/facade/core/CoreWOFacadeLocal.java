package co.com.directv.sdii.facade.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.EmailMessageException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.model.dto.AssignWorkOrderDTO;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.WorkOrderInfoServiceVinculationDTO;
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
import co.com.directv.sdii.model.vo.ServiceHourVO;
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
 * 
 * Interfaz que define las operaciones para el modulo de core
 * asociadas a el componente de negocio de core y relacionadas
 * con los CU:
 * Caso de Uso ADS - 26 - Detalle del Cliente ==> Se debe realizar cuando se haga el m�dulo de Clientes
 * Caso de Uso ADS - 27 - Diligenciar Encuesta de Servicio
 * Caso de Uso ADS - 30 - Exportar Bandeja de Work Orders a Excel ==> Todas las tablas se deben exportar a Excel
 * Caso de Uso ADS - 60 - Registrar elementos NO serializados utilizados en la atenci�n del servicio ==> Se debe realizar cuando se haga el m�dulo de Inventarios 
 * Caso de Uso ADS - 61 - Registrar elementos serializados utilizados en la atenci�n del servicio ==> Se debe realizar cuando se haga el m�dulo de Inventarios
 * Caso de Uso ADS - 62 - Registrar  cambio  de  elementos serializados donde el cliente  en  la ate ==> Se debe realizar cuando se haga el m�dulo de Inventarios
 * Caso de Uso ADS - 63 - Registrar Recuperaci�n  de  elementos serializados  donde el cliente ==> Se debe realizar cuando se haga el m�dulo de Inventarios
 * Caso de Uso ADS - 64 - Registrar Recuperaci�n  de  elementos NO serializados  donde el cliente ==> Se debe realizar cuando se haga el m�dulo de Inventarios
 *  
 * 
 * Fecha de Creación: 20/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CoreWOFacadeLocal {
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
	 * @param countryCode codigo del pais
	 * 
	 * @throws BusinessException
	 */
	public void addWorkOrder(String countryCode) throws BusinessException;

	/**
	 * Caso de Uso ADS - 18 - Visualizar Bandeja de Work Orders de la Compa��a Instaladora.
	 *  
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param dealer dealer para el que se consultaran las work orders
	 * @param requestCollectionInfo RequestCollectionInfo
	 * @return WOByDealerPaginationResponse
	 * @throws BusinessException
	 */
	public WOByDealerPaginationResponse getWorkOrderByDealer(DealerVO dealer, RequestCollectionInfo requestCollectionInfo) throws BusinessException;

	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del Dealer.
	 * 
	 * @param dealerId id del dealer
	 * @param woCode codigo de la work order
	 * @param ServiceTypeId id del tipo de servicio
	 * @param serviceCategoryId id de la caegoria de servicio
	 * @param woStatusId id del estado de work order
	 * @param stateId id del departamento
	 * @param cityId id de la ciudad
	 * @param postalCodeId id del codigo postal
	 * @param creationDate
	 * @param programmingDate
	 * @param requestCollectionInfo
	 * @return WOByDealerWorkOrderQBEPaginationResponse
	 * @throws BusinessException
	 */
	public WOByDealerWorkOrderQBEPaginationResponse getWorkOrdersByDealerWorkOrderQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del Cliente.
	 * 
	 * @param dealerId id del dealer
	 * @param ibsCode codigo ibs
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
	 * @return WOByDealerDateCrewQBEPaginationResponse
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
	 * @param workorders
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
	 * @param workorders - List<WorkOrderVO>
	 * @param crew - CrewVO
	 * @throws BusinessException
	 * @author gfandino
	 */
	public void assignFixedCrewToWorkOrders(List<WorkOrderInfoServiceVinculationDTO> workordersCrews, Long userId) throws BusinessException;

	/**
	 * Caso de Uso ADS - 21 - Asignar Cuadrilla Dinamica a Work Orders.
	 *  
	 * Método que se encarga de asignar una cuadrilla din�mica a una WorkOrder.
	 * Se debe almacenar en la tabla WO_ASSIGNMENTS.
	 * La WorkOrder ya debe existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workOrderList lista de work orders
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
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar Método updateDealerServiceCapacity).
	 * 
	 * @param trayWOManagmentDTO TrayWOManagmentDTO objeto que encapsula informacion para agendamiento
	 * @throws BusinessException
	 * @author jnova
	 */
	public void agendaWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException;

	
	/**
	 * Caso de Uso ADS - 23 - Rechazar Work Order.
	 * 
	 * Método que se encarga de registrar la raz�n del rechazo de una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar Método updateDealerServiceCapacity).
	 * 
	 * @param trayWOManagmentDTO TrayWOManagmentDTO dto que encapsula informacion para rechazo de WO
	 * @throws BusinessException
	 * @author gfandino
	 * @author jnova
	 */
	public void rejectWorkOrder(List<TrayWOManagmentDTO> trayWOManagmentDTO) throws BusinessException;

	/**
	 * Caso de Uso ADS - 24 - Dificultad en Work Order.
	 * 
	 * Método que se encarga de registrar una dificultad en una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS, ejecutando el caso de uso
	 * CU ADS - 33 Notificar cambio de estado de Work Order a IBS.
	 * 
	 * @param trayWOManagmentDTO TrayWOManagmentDTO dto que encapsula de la WO
	 * @throws BusinessException
	 * @author jnova
	 */
	public void reportDifficultyToWorkOrder(List<TrayWOManagmentDTO> trayWOManagmentDTO) throws BusinessException;

	/**
	 * Caso de Uso ADS - 25 - Detalle de la Work Order.
	 * 
	 * Método que se encarga de obtener una WorkOrder por ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param id id de la work order que busca
	 * @return WorkOrderVO
	 * @throws BusinessException
	 */
	public WorkOrderVO getWorkOrderByID(Long id) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 27 - Diligenciar Encuesta de Servicio
	 * 
	 * Método que permite consultar la Encuesta activa
	 * Se lee de la tabla SURVEY_QUESTION.
	 * 
	 * @return List<SurveyQuestionVO>
	 * @throws BusinessException
	 * @author gfandino
	 */
//	public List<QuestionClassVO> getSurveyQuestionByActiveSurvay() throws BusinessException;
	
	
	/**
	 * Metodo: Consulta la información de la encuesta dado el identificador de la WO
	 * @param workOrderId identificador de la Work order
	 * @return Lista de información de la encuesta para ser diligenciada
	 * @throws BusinessException En caso de error al realizar la consulta
	 * @author jjimenezh
	 */
//	public List<QuestionClassVO> getActiveSurveyQuestionsByWOId(Long workOrderId)throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 27 - Diligenciar Encuesta de Servicio
	 * 
	 * Método que permite almacenar las respuestas de una encuesta
	 * Se almacena en la tabla SURVEY_ANSWERS.
	 * @param surveyAnswers - List<SurveyAnswerVO>
	 * @return SurveyQuestionVO
	 * @throws BusinessException
	 * @author gfandino
	 */
//	public void registerSurveyAnswer(List<SurveyAnswerVO> surveyAnswers) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 27 - Diligenciar Encuesta de Servicio
	 * Método que permite consultar las respuestas de una encuesta por WO
	 * Se almacena en la tabla SURVEY_ANSWERS.
	 * @param workOrder - WorkOrderVO
	 * @return List<SurveyAnswerVO>
	 * @throws BusinessException
	 * @author gfandino
	 */
//	public List<SurveyAnswerVO> getSurveyAnswersByWO(WorkOrderVO workOrder) throws BusinessException;
	
	/**
	 * Retorna los pdfs asociados al dealer especificado para luego ser enviados por email
	 * @param dealerCode codigo ibs del dealer
	 * @return List<String>
	 * @throws BusinessException
	 */
	public List<String> getWorkOrdersFilesByDealer(final Long dealerCode) throws BusinessException;

	/**
	 * Caso de Uso ADS - 29 - Generar Planilla en PDF.
	 * 
	 * Método que genera los formatos de las WorkOrder en PDF y los deja
	 * en una carpeta del servidor para luego ser descargadas por el usuario.
	 * 
	 * Por cada WorkOrder se generan: 
	 * Ver p�gina 22 del documento anexo Capitulo #3 - Administraci�n de Servicios - Smart Dealer.doc
	 * 
	 * No se trata de un proceso en l�nea. Ejecuta una tarea con Quartz que
	 * realiza la generaci�n de los PDF en background.
	 * 
	 * @param workOrderIds id's de work order
	 * @param crew cuadrilla
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
	 * Ver p�gina 22 del documento anexo Capitulo #3 - Administraci�n de Servicios - Smart Dealer.doc
	 * 
	 * No se trata de un proceso en l�nea. Ejecuta una tarea con Quartz que
	 * realiza la generaci�n de los PDF en background.
	 * 
	 * @param workOrderIds ids de las work orders
	 * @param crewIds ids de las cuadrillas
	 * @throws PDFException
	 * @throws BusinessException 
	 */
	public String generateCrewWorkOrdersPDF(List<Long> workOrderIds, List<Long> crewIds) throws PDFException, BusinessException;

	/**
	 * Caso de Uso ADS - 31 - Enviar Work Orders v�a email.
	 * 
	 * Método que permite enviar una lista de WorkOrders en formato PDF a una cuadrilla.
	 *  
	 * @param fileNames nombres de los archivos adjuntos que se enviaran
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
	 * de estado se validan con la operaci�n workOrderStateMachine.
	 * 
	 * Est� Método invoca la operaci�n (WebService) sobre IBS que permite notificarle dicho cambio de estado.
	 * 
	 * @param workorder work order a la que se le cambiara el estado
	 * @param newWorkOrderStatus nuevo estado de la work order
	 * @param reason razon por la que cambia el estado de la work order
	 * @throws BusinessException
	 */
	public void changeWorkOrderStatus(WorkOrderVO workorder, WorkorderStatusVO newWorkOrderStatus, WorkorderReasonVO reason) throws BusinessException;

	/**
	 * Caso de Uso ADS - 34 - Afectar Capacidad de Compa�ia Instaladora.
	 * 
	 * Este Método permite afectar la capacidad de un Dealer en un PostalCode y en una Jornada.
	 * 
	 * Se debe informar a IBS el cambio de la capacidad del Dealer por medio de un WebService.
	 * 
	 * @param dealer
	 * @param postalCode
	 * @param serviceHour
	 * @throws BusinessException
	 */
	public void updateDealerServiceCapacity(DealerVO dealer, PostalCodeVO postalCode, ServiceHourVO serviceHour) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignaci�n Manual o Cancelaci�n.
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders en estado WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING.
	 * Se lee de la tabla WORK_ORDERS.
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrdersActiveAndSuspend() throws BusinessException;

	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignaci�n Manual o Cancelaci�n.
	 * 
	 * Metodo que se encarga de obtener una lista de WorkOrders en estado WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param countryId
	 * @param requestCollectionInfo
	 * @return WOActiveAndSuspendByCountryIdPaginationResponse
	 * @throws BusinessException
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersActiveAndSuspendByCountryId(Long countryId, RequestCollectionInfo requestCollectionInfo) throws BusinessException;

	
	/**
	 * Caso de Uso ADS - 36 - Asignac�n Manual de Work Order.
	 * 
	 * Método que se encarga de asignar una WorkOrder a un Dealer.
	 * 
	 * Se debe notificar el cambio de estado a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar Método updateDealerServiceCapacity).
	 * 
	 * @param dealer
	 * @param workorder
	 * @throws BusinessException
	 */
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 36 - Asignac�n Manual de Work Order.
	 * 
	 * Método que se encarga de asignar una WorkOrder a un Dealer.
	 * 
	 * Se debe notificar el cambio de estado a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar Método updateDealerServiceCapacity).
	 * 
	 * @param dealer
	 * @param workorder
	 * @throws BusinessException
	 */
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto, String workOrderReasonCode) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 36 - Asignacón Manual de Work Orders.
	 * 
	 * Método que se encarga de asignar una WorkOrder a un Dealer.
	 * 
	 * Se debe notificar el cambio de estado a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar método updateDealerServiceCapacity).
	 * 
	 * @param dealer
	 * @param workorder
	 * @throws BusinessException
	 */
	public void assignWorkOrdersToDealer(AssignWorkOrderDTO dto, List<WorkOrderVO> workorders) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 37 - Cancelar Work Order.
	 * 
	 * Método que se encarga de registrar la raz�n de la cancelaci�n de una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar Método updateDealerServiceCapacity).
	 * 
	 * @param TrayWOManagmentDTO trayWOManagmentDTO
	 * @throws BusinessException
	 */
	public void cancelWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 37 - Cancelar Work Order.
	 * 
	 * Metodo que se encarga de registrar la raz�n de la cancelacion de una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar Método updateDealerServiceCapacity).
	 * 
	 * @param List<TrayWOManagmentDTO> trayWOManagmentDTOList
	 * @throws BusinessException
	 */
	public void cancelWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException;

	/**
	 * Caso de Uso ADS - 39 - Atenci�n de la Work Order desde IBS.
	 * 
	 * Método que permite registrar la atenci�n a una WorkOrder desde IBS.
	 * 
	 * Se debe consumir servicio en IBS que obtenga las atenciones realizadas.
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar Método updateDealerServiceCapacity).
	 * 
	 * @throws BusinessException
	 */
	//public void attendWorkOrderIBS(WorkOrderVO workOrder, ServiceVO service,List<NotSerializedVO> notServialized, List<SerializedVO> servialized, WorkorderReasonVO reason, String comment) throws BusinessException;

	/**
	 * Caso de Uso ADS - 40 - Notificar cambio de estado de Work Order por IBS.
	 * 
	 * Método que se encarga de cambiar el estado de una WorkOrder en SmartDealer II, proveniente desde IBS.
	 * 
	 * Por cada cambio de estado se debe almacenar un registro en la tabla WO_STATUS_HISTORY.
	 * 
	 * Antes de realizar el cambio de estado es necesario validar si el cambio es posible. Estos cambios
	 * de estado se validan con la operaci�n workOrderStateMachine.
	 * 
	 * Est� Método invoca la operaci�n (WebService) sobre IBS que permite consultar los cambio de estado.
	 * 
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
//	 * @throws BusinessException
//	 */
//	public Long changeRealizationDateIBS(Long customerID, Long workOrderNumer, String newRealizationDate, String workOrderCountryId) throws BusinessException;

	/**
	 * Metodo: Caso de Uso ADS - 42 - Adicionar Service a Work Order.
	 * Método que permite adicionar un nuevo servicio a una WorkOrder proveniente de IBS. Retorna mensaje 
	 * con resultado
	 * @param workOrderId
	 * @param serviceId
	 * @param decoSerialNumber
	 * @param countryCode
	 * @param userId
	 * @throws BusinessException <tipo> <descripcion>
	 * @author gfandino
	 */
	public void addServiceToWorkOrder(Long workOrderId, Long serviceId, String decoSerialNumber, String countryCode, Long userId) throws BusinessException;
	
	/**
	 * Caso de Uso ADS - 43 - Quitar Service de Work Order.
	 * 
	 * Método que permite quitar un servicio a una WorkOrder proveniente de IBS.
	 * @param decoderSerialNumber número de serie del decodificador
	 * @param serviceCode código del servicio a ser removido
	 * @param workOrderCode Código de la work order a la que se le borrará el servicio
	 * 
	 * @throws BusinessException
	 */
	public void deleteServiceToWorkOrder(Long workOrderId, Long serviceId, String decoderSerialNumber, String countryCode) throws BusinessException;
	
	// =============== Métodos utilitarios para el módulo de Core =====================
	
	/**
	 * Método que se encarga de eliminar una WorkOrder.
	 * Se debe almacenar en la tabla WORK_ORDERS.
	 * 
	 * @param workorder
	 * @throws BusinessException
	 */
	public void deleteWorkOrder(WorkOrderVO workorder) throws BusinessException;
	
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
	 * @param actualStatus
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByActualStatus(DealerVO dealer, WorkorderStatusVO actualStatus) throws BusinessException;
	
	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por DEALER_ID y ACTUAL_STATUS_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param actualStatus
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public List<WorkOrderVO> getWorkOrderByActualStatus(WorkorderStatusVO actualStatus) throws BusinessException;
	
	
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
	 * Metodo que garantiza que un dealer q no es el logueado no vea la workorder correspondiente y retorna una workorder por el id
	 * @param dealerId
	 * @param workOrderId
	 * @return WorkOrderVO
	 * @throws BusinessException
	 */
	public WorkOrderVO getWorkOrderByDealerIdAndWorkOrderId(Long dealerId, Long workOrderId) throws BusinessException;
	
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
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeCreationDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate) throws BusinessException;

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
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeProgrammingDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate) throws BusinessException;

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
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeRealizationDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate) throws BusinessException;

	/**
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders por CUSTOMER_ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param customer
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
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByWorkOrderDataQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId, Long countryId, 
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
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
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByCustomerDataQBE(Long countryId, String ibsCode,
			String officePhone, String homePhone, String faxPhone,
			String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna un WoStatusHistory con la workorderreason
	 * del ultimo estado de una workorder.
	 * @param woId Long
	 * @param woStatusId Long
	 * @return WoStatusHistoryVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public WoStatusHistoryVO getWorkorderReasonByWoHistory(Long woId,Long woStatusId) throws BusinessException;
	
	/**
     * 
     * Metodo: Retorna un listado de WorkOrderAgenda, filtrando por
     * el dealerId y WorkOrderId por medio de la WorkOrder Assignment
     * @param woId Long
     * @param dealerId Long
     * @return List<WorkOrderAgenda>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
	public List<WorkOrderAgendaVO> getWorkOrderAgendaByWoAssignment(Long woId,Long dealerId) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las WorkorderReason que pertenecen a las WorkorderStatus
	 * @param woStatus - WorkorderStatusVO
	 * @param Long userId
	 * @return List<WorkorderReasonVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author gfandino
	 */
	public List<WorkorderReasonVO> getWorkOrderReasonByWoStatus (WorkorderStatusVO woStatus,Long userId) throws BusinessException;

	public CustomerVO getCustomerById(Long customerId) throws BusinessException;

	public CustomerVO getCustomerByCode(String customerCode) throws BusinessException;

	public CustomerVO getCustomerByWoIdOrWoCode(Long workOrderId,String workOrderCode) throws BusinessException;

	/**
	 * Metodo: Obtiene la última asignación del dealer a una work order
	 * @param workOrderId identificador de la work order
	 * @return asignación de la work order
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public WoAssignmentVO getLastWorkOrderAssigmentByWoId(Long workOrderId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la lista de las relaciones entre la work order y los servicios
	 * @param workOrderId identificador de la work order
	 * @return Lista con las relaciones de servicios con las work order, una lista vacia en caso que no
	 * se obtengan resultados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<WorkOrderServiceVO> getWorkOrderServicesByWoId(Long workOrderId)throws BusinessException;
	
	/**
	 * Caso de Uso IVR - 01 - Consulta de la Work Order.
	 * 
	 * Metodo que se encarga de obtener una WorkOrder por ID y countryCode.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param id Identificador de la Work Order
	 * @param countryCode Codigo del Pais 
	 * @return WorkOrderVO Datos de la Work Order consultada
	 * @throws BusinessException
	 * @author jforero 04/08/2010
	 */
	public WorkOrderVO getWorkOrderByCodeAndCountry(String id, String countryCode) throws BusinessException;
	
	
	
	/**
	 * Caso de uso IVR - 03 Consultar una shipping order
	 * 
	 * Metodo: Consulta una shipping order por si identificador
	 * @param shipOrderID Identificador de la shipping order
	 * @param woCode identificador de la workorder
	 * @param countryCode Codigo del pais
	 * @return ShippingOrderVO Datos de la shippinf order consultada
	 * @throws BusinessException 
	 * @author jforero 05/08/2010
	 */
	public ShippingOrderVO getShippingOrderByCodeAndWOCodeAndCountry(String shipOrderID, String woCode, String countryCode) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la lista de los archivos generados por id de cuadrilla
	 * @param crewId identificador de la cuadrilla
	 * @return Lista de nombres de archivos de cuadrilla
	 */
	public List<String> getWorkOrdersFilesByCrewId(Long crewId)throws BusinessException;

	 /**
     * Metodo: Permite consultar las work order paginadas
     * resultado
     * @return Lista de work orders
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public List<WorkOrderVO> getWorkOrdersByPage(Long countryId) throws BusinessException;
    
    /**
	 * Method: Valida si una Work order tiene agendamiento
	 * @param workOrderId Long
	 * @param Long dealerId Long
	 * @return Boolean
	 * @throws BusinessException
	 * @author jalopez
	 */
    public Boolean getValidateWorkOrderScheduled(Long workOrderId, Long dealerId) throws BusinessException;
    
    /**
	 * 
	 * Metodo: Realiza el agendamiento masivo de workorders
	 * @param List<TrayWOManagmentDTO> Lista de objetos que contienen informacion necesaria para el agendamiento masivo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void agendaWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Permite consultar si se agendan las wo se sobreagenda el dealer
	 * @param DealerWorkCapacityCriteria filtro del capacityDate, countryId, dealerId, serviceHourId, superCategoryServiceId;
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduled(DealerWorkCapacityCriteria dealerWorkCapacityCriteria, int quantityWorkOrder)throws BusinessException;
	
	/**
	 * Control de cambios MigracionRESB 
	 * 
	 * Método que se encarga de actualizar cierta informacion de HSP
	 * a una workOrder consultada en ESB. 
	 * @param countryCode codigo del pais de la workOrder
	 * @param woCode Codigo del a workOrder a actualizar
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void updateInfoWorkOrderIBSToHSP(String countryCode,String woCode) throws BusinessException;

	/**
	 * REQ001 - WO Canceladas
	 * Dado el id de un dealer se buscaran todas las WO canceladas.
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
	public void downloadWorkOrderContact(Long countryId, Date processDate) throws BusinessException;
	
}
