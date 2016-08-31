package co.com.directv.sdii.ws.business.core.wo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
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
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.model.vo.WoAssignmentVO;
import co.com.directv.sdii.model.vo.WoStatusHistoryVO;
import co.com.directv.sdii.model.vo.WorkOrderAgendaVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;

/**
 * 
 * 
 * Fecha de Creación: 25/06/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="CoreWoWS",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface ICoreWoWS {
	
	/**
	 * Caso de Uso ADS - 17 - Crear Work Order.
	 * 
	 * Método que se encarga de crear una WorkOrder que proviene de IBS. Se debe
	 * almacenar en la tabla WORK_ORDERS. Se debe invocar un WebService de IBS
	 * que retornará la lista de WorkOrders a crear en SmartDealer II.
	 * 
	 * Se debe notificar cambio de estado de la WorkOrder a IBS.
	 * 
	 * @param countryCode
	 * @throws BusinessException
	 * @throws PropertiesException 
	 */
	@WebMethod(operationName = "addWorkOrder", action = "addWorkOrder")
	public void addWorkOrder(@WebParam(name = "countryCode") String countryCode) throws BusinessException;
	
	/**
	 * Permite adicionar un servicio a la WO. Retorna -1 si hubo error o el numero del ID del la workorder en caso de exito
	 * 
	 * @author jjimenezh
	 * 
	 * @param workOrderId
	 * @param serviceId
	 * @param decoSerialNumber
	 * @param countryCode
	 * @param userId
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "addServiceToWorkOrder", action = "addServiceToWorkOrder")
	public void addServiceToWorkOrder(
			@WebParam(name = "workOrderId")Long workOrderId,
			@WebParam(name = "serviceId")Long serviceId,
			@WebParam(name = "decoSerialNumber")String decoSerialNumber,
			@WebParam(name = "countryCode")String countryCode,
			@WebParam(name = "userId")Long userId) throws BusinessException;

	/**
	 * Caso de Uso ADS - 21 - Asignar Cuadrilla Dinamica a Work Orders.
	 * 
	 * Método que se encarga de asignar una cuadrilla dinámica a una WorkOrder.
	 * Se debe almacenar en la tabla WO_ASSIGNMENTS. La WorkOrder ya debe
	 * existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workOrderList
	 * @param crew
	 * @return true/false
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "assignDynamicCrewToWorkOrder", action = "assignDynamicCrewToWorkOrder")
	public boolean assignDynamicCrewToWorkOrder(
			@WebParam(name = "workOrderList") List<WorkOrderVO> workOrderList,
			@WebParam(name = "crew") CrewVO crew,
			@WebParam(name = "userId") Long userId) throws BusinessException;

	/**
	 * Caso de Uso ADS - 19 - Asignar Programa a Work Orders.
	 * 
	 * Método que se encarga de asignar un programa a una WorkOrder. Se debe
	 * almacenar en la tabla WO_ASSIGNMENTS. La WorkOrder ya debe existir en la
	 * tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workorder
	 * @param program
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "assignProgramToWorkOrder", action = "assignProgramToWorkOrder")
	public void assignProgramToWorkOrder(
			@WebParam(name = "workorder") WorkOrderVO workorder,
			@WebParam(name = "program") ProgramVO program)
			throws BusinessException;

	/**
	 * Caso de Uso ADS - 19 - Asignar Programa a Work Orders.
	 * 
	 * Metodo que se encarga de asignar un programa a una WorkOrder. Se debe
	 * almacenar en la tabla WO_ASSIGNMENTS. La WorkOrder ya debe existir en la
	 * tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workorders
	 * @param program
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "assignProgramToWorkOrders", action = "assignProgramToWorkOrders")
	public void assignProgramToWorkOrders(
			@WebParam(name = "workorders") List<WorkOrderVO> workorders,
			@WebParam(name = "program") ProgramVO program)
			throws BusinessException;

	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los
	 * criterios de entrada del Dealer.
	 * 
	 * @param dealer
	 * @param requestCollectionInfo
	 * @return WOByDealerPaginationResponse
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrderByDealer", action = "getWorkOrderByDealer")
	public WOByDealerPaginationResponse getWorkOrderByDealer(
			@WebParam(name = "dealer") DealerVO dealer, @WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;

	/**
	 * Caso de Uso ADS - 25 - Detalle de la Work Order.
	 * 
	 * Método que se encarga de obtener una WorkOrder por ID. Se lee de la tabla
	 * WORK_ORDERS.
	 * 
	 * @param id
	 * @return WorkOrderVO
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrderByID", action = "getWorkOrderByID")
	public WorkOrderVO getWorkOrderByID(@WebParam(name = "id") Long id)
			throws BusinessException;

	/**
	 * Metodo que garantiza que un dealer q no es el logueado no vea la
	 * workorder correspondiente y retorna una workorder por el id
	 * 
	 * @param dealerId
	 * @param workOrderId
	 * @return WorkOrderVO
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrderByDealerIdAndWorkOrderId", action = "getWorkOrderByDealerIdAndWorkOrderId")
	public WorkOrderVO getWorkOrderByDealerIdAndWorkOrderId(
			@WebParam(name = "dealerId") Long dealerId,
			@WebParam(name = "workOrderId") Long workOrderId)
			throws BusinessException;

	/**
	 * 
	 * Metodo: realiza la validacion del cambio de estado actual a otro y
	 * verifica si es posible realizar el cambio de estado. Retorna true si es
	 * posible realizar el cambio, de lo contrario genera un BusinessException.
	 * Este metodo sera cambiando por validateStatusChangeWorkOrderByCodes
	 * 
	 * @param WorkOrderVO
	 *            workOrder
	 * @param Long
	 *            statusChange
	 * @return boolean, true si es posible el cambio de estado.
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jalopez
	 */
	@WebMethod(operationName = "validateStatusChangeWorkOrder", action = "validateStatusChangeWorkOrder")
	@Deprecated
	public boolean validateStatusChangeWorkOrder(
			@WebParam(name = "workOrder") WorkOrderVO workOrder,
			@WebParam(name = "statusChange") Long statusChange)
			throws BusinessException;

	/**
	 * Servicio que permite asignar cuadrilla a una orden de trabajo
	 * 
	 * @param workorder
	 *            - WorkOrderVO
	 * @param crew
	 *            - crew
	 * @throws BusinessException
	 * @author gfandino
	 */
	@WebMethod(operationName = "assignFixedCrewToWorkOrder", action = "assignFixedCrewToWorkOrder")
	public void assignFixedCrewToWorkOrder(
			@WebParam(name = "workorder") WorkOrderVO workorder,
			@WebParam(name = "crew") CrewVO crew,
			@WebParam(name = "userId") Long userId) throws BusinessException;

	/**
	 * Servicio que permite asignar cuadrilla a una orden de trabajo.
	 * 
	 * @author gfandino
	 * 
	 * @param workorders
	 * @param crew
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "assignFixedCrewToWorkOrders", action = "assignFixedCrewToWorkOrders")
	public void assignFixedCrewToWorkOrders(
			@WebParam(name = "workordersCrewsList")List<WorkOrderInfoServiceVinculationDTO> workordersCrewsList,
			@WebParam(name = "userId") Long userId) throws BusinessException;

	/**
	 * Caso de Uso ADS - 36 - Asignacón Manual de Work Orders.
	 * 
	 * Método que se encarga de asignar una WorkOrder a un Dealer.
	 * 
	 * Se debe notificar el cambio de estado a IBS. Se debe afectar la capacidad
	 * del DEALER en DEALERS_SERVICE_CAPACITY (invocar método
	 * updateDealerServiceCapacity).
	 * 
	 * @param dealer
	 * @param workorders
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "assignWorkOrdersToDealer", action = "assignWorkOrdersToDealer")
	public void assignWorkOrdersToDealer(
			@WebParam(name = "dto") AssignWorkOrderDTO dto,
			@WebParam(name = "workorders") List<WorkOrderVO> workorders)
			throws BusinessException;

	/**
	 * Servicio que permite rechazar una orden de trabajo
	 * 
	 * @author gfandino
	 * @author jnova
	 * 
	 * @param TrayWOManagmentDTO trayWOManagmentDTO dto que encapsula de la WO
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "rejectWorkOrder", action = "rejectWorkOrder")
	public void rejectWorkOrder(@WebParam(name = "trayWOManagmentDTO") List<TrayWOManagmentDTO> trayWOManagmentDTO)throws BusinessException;

	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignaci�n Manual o
	 * Cancelaci�n.
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders en estado
	 * WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING. Se lee de la tabla
	 * WORK_ORDERS.
	 * 
	 * @param actualStatus
	 * @return List<WorkOrderVO>
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrdersActiveAndSuspend", action = "getWorkOrdersActiveAndSuspend")
	public List<WorkOrderVO> getWorkOrdersActiveAndSuspend()
			throws BusinessException;

	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignaci�n Manual o
	 * Cancelación.
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders en estado
	 * WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING. Se lee de la tabla
	 * WORK_ORDERS.
	 * 
	 * @param countryId
	 * @param requestCollectionInfo
	 * @return WOActiveAndSuspendByCountryIdPaginationResponse
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrdersActiveAndSuspendByCountryId", action = "getWorkOrdersActiveAndSuspendByCountryId")
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersActiveAndSuspendByCountryId(@WebParam(name = "countryId") Long countryId, @WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;

	
	/**
	 * Caso de Uso ADS - 24 - Dificultad en Work Order.
	 * 
	 * Método que se encarga de registrar una dificultad en una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS, ejecutando
	 * el caso de uso CU ADS - 33 Notificar cambio de estado de Work Order a
	 * IBS.
	 * 
	 * @param TrayWOManagmentDTO trayWOManagmentDTO dto que encapsula de la WO
	 * @throws BusinessException
	 * @author jnova
	 */
	@WebMethod(operationName = "reportDifficultyToWorkOrder", action = "reportDifficultyToWorkOrder")
	public void reportDifficultyToWorkOrder(@WebParam(name = "trayWOManagmentDTO") List<TrayWOManagmentDTO> trayWOManagmentDTO)throws BusinessException;

	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignaci�n Manual o
	 * Cancelaci�n.
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders en estado
	 * WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING. Se lee de la tabla
	 * WORK_ORDERS.
	 * 
	 * @param dealer
	 * @param workorder
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "assignWorkOrderToDealer", action = "assignWorkOrderToDealer")
	public void assignWorkOrderToDealer(
			@WebParam(name = "dto") AssignWorkOrderDTO dto)
			throws BusinessException;

	/**
	 * Servicio que permite consultar la lista de workorders por varios
	 * criterios de consulta.
	 * 
	 * @author jvelezmu
	 * 
	 * @param dealerId
	 * @param woCode
	 * @param serviceCategoryId
	 * @param ServiceTypeId
	 * @param woStatusId
	 * @param stateId
	 * @param cityId
	 * @param postalCodeId
	 * @param creationDate
	 * @param programmingDate
	 * @param requestCollectionInfo
	 * @return WOByDealerWorkOrderQBEPaginationResponse
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrdersByDealerWorkOrderQBE", action = "getWorkOrdersByDealerWorkOrderQBE")
	public WOByDealerWorkOrderQBEPaginationResponse getWorkOrdersByWorkOrderQBE(
			@WebParam(name = "dealerId") Long dealerId,
			@WebParam(name = "woCode") String woCode,
			@WebParam(name = "serviceCategoryId") Long serviceCategoryId,
			@WebParam(name = "ServiceTypeId") Long ServiceTypeId,
			@WebParam(name = "woStatusId") Long woStatusId,
			@WebParam(name = "stateId") Long stateId,
			@WebParam(name = "cityId") Long cityId,
			@WebParam(name = "postalCodeId") Long postalCodeId,
			@WebParam(name = "creationDate") Date creationDate,
			@WebParam(name = "programmingDate") Date programmingDate,
			@WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;

	/**
	 * Servicio que permite consultar la lista de workorders por dealer, fecha y
	 * cuadrilla.
	 * 
	 * @author jvelezmu
	 * 
	 * @param dealerId
	 * @param dateId
	 * @param crewId
	 * @param requestCollectionInfo
	 * @return WOByDealerDateCrewQBEPaginationResponse
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrdersByDealerDateCrewQBE", action = "getWorkOrdersByDealerDateCrewQBE")
	public WOByDealerDateCrewQBEPaginationResponse getWorkOrdersByDealerDateCrewQBE(
			@WebParam(name = "dealerId") Long dealerId,
			@WebParam(name = "dateId") Long dateId,
			@WebParam(name = "crewId") Long crewId,
			@WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo) throws BusinessException;

	/**
	 * Servicio que permite consultar la lista de workorders por datos del
	 * cliente.
	 * 
	 * @author jvelezmu
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
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrdersByDealerCustomerQBE", action = "getWorkOrdersByDealerCustomerQBE")
	public WOByCustomerQBEPaginationResponse getWorkOrdersByCustomerQBE(
			@WebParam(name = "dealerId") Long dealerId,
			@WebParam(name = "ibsCode") String ibsCode,
			@WebParam(name = "officePhone") String officePhone,
			@WebParam(name = "homePhone") String homePhone,
			@WebParam(name = "faxPhone") String faxPhone,
			@WebParam(name = "cellPhone") String cellPhone,
			@WebParam(name = "idNumber") String idNumber,
			@WebParam(name = "name") String name,
			@WebParam(name = "lastName") String lastName,
			@WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;

	/**
	 * Caso de Uso ADS - 22 - Agendar Work Order.
	 * 
	 * Método que se encarga de agendar una WorkOrder. Se debe almacenar en la
	 * tabla WO_AGENDA. La WorkOrder ya debe existir en la tabla WORK_ORDERS y
	 * WO_ASSIGNMENTS.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS. Se debe
	 * afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar
	 * Método updateDealerServiceCapacity).
	 * 
	 * @param TrayWOManagmentDTO objeto que encapsula informacion para agendamiento
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "agendaWorkOrder", action = "agendaWorkOrder")
	public void agendaWorkOrder(@WebParam(name = "trayWOManagmentDTO") TrayWOManagmentDTO trayWOManagmentDTO)throws BusinessException;

	/**
	 * Query By Example. Obtiene las workorders que se encuentran en estado
	 * ACTIVE o PENDING, deacuerdo a los criterios ingresados.
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
	@WebMethod(operationName = "getWorkOrdersByWorkOrderDataQBE", action = "getWorkOrdersByWorkOrderDataQBE")
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByWorkOrderDataQBE(
			@WebParam(name = "dealerId") Long dealerId,
			@WebParam(name = "woCode") String woCode,
			@WebParam(name = "ServiceTypeId") Long ServiceTypeId,
			@WebParam(name = "serviceCategoryId") Long serviceCategoryId,
			@WebParam(name = "woStatusId") Long woStatusId,
			@WebParam(name = "countryId") Long countryId,
			@WebParam(name = "stateId") Long stateId,
			@WebParam(name = "cityId") Long cityId,
			@WebParam(name = "postalCodeId") Long postalCodeId,
			@WebParam(name = "creationDate") Date creationDate,
			@WebParam(name = "programmingDate") Date programmingDate,
			@WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;

	/**
	 * Query By Example. Obtiene las workorders que se encuentran en estado
	 * ACTIVE o PENDING, deacuerdo a los criterios ingresados.
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
	@WebMethod(operationName = "getWorkOrdersByCustomerDataQBE", action = "getWorkOrdersByCustomerDataQBE")
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByCustomerDataQBE(
			@WebParam(name = "countryId") Long countryId,
			@WebParam(name = "ibsCode") String ibsCode,
			@WebParam(name = "officePhone") String officePhone,
			@WebParam(name = "homePhone") String homePhone,
			@WebParam(name = "faxPhone") String faxPhone,
			@WebParam(name = "cellPhone") String cellPhone,
			@WebParam(name = "idNumber") String idNumber,
			@WebParam(name = "name") String name,
			@WebParam(name = "lastName") String lastName,
			@WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;


	/**
	 * Caso de Uso ADS - 37 - Cancelar Work Order.
	 * 
	 * Metodo que se encarga de registrar la razon de la cancelacion de una
	 * WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS. Se debe
	 * afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar
	 * metodo updateDealerServiceCapacity).
	 * 
	 * @param List<TrayWOManagmentDTO> trayWOManagmentDTOList
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "cancelWorkOrders", action = "cancelWorkOrders")
	public void cancelWorkOrders(@WebParam(name = "trayWOManagmentDTOList") List<TrayWOManagmentDTO> trayWOManagmentDTOList)throws BusinessException;

	/**
	 * Lee las pdfs asociadas a un dealer especificado para luego ser enviadas
	 * por email
	 * 
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param dealerCode
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrdersFilesByDealer", action = "getWorkOrdersFilesByDealer")
	public List<String> getWorkOrdersFilesByDealer(
			@WebParam(name = "dealerCode") Long dealerCode)
			throws BusinessException;

	/**
	 * Lee las pdfs asociadas a una cuadrilla especificado para luego ser enviadas
	 * por email
	 * @author jjimenezh
	 * @param crewId identificador de la cuadrilla
	 * @throws BusinessException en caso de error
	 */
	@WebMethod(operationName = "getWorkOrdersFilesByCrewId", action = "getWorkOrdersFilesByCrewId")
	public List<String> getWorkOrdersFilesByCrewId(@WebParam(name = "crewId") Long crewId)
			throws BusinessException;

	
	/**
	 * 
	 * Metodo: Retorna un WoStatusHistory con la workorderreason del ultimo
	 * estado de una workorder.
	 * 
	 * @param woId
	 *            Long
	 * @param woStatusId
	 *            Long
	 * @return WoStatusHistoryVO
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getWorkorderReasonByWoHistory", action = "getWorkorderReasonByWoHistory")
	public WoStatusHistoryVO getWorkorderReasonByWoHistory(
			@WebParam(name = "woId") Long woId,
			@WebParam(name = "woStatusId") Long woStatusId)
			throws BusinessException;

	/**
	 * Metodo: Retorna la lista de WorkorderReasonVO que se encuentran asociadas
	 * a una WorkorderStatusVO
	 * 
	 * @param woStatus
	 *            - WorkorderStatusVO
	 * @param  Long userId
	 * @return List<WorkorderReasonVO>
	 * @throws BusinessException
	 *             <tipo> <descripcion>
	 * @author gfandino
	 */
	@WebMethod(operationName = "getWorkOrderReasonByWoStatus", action = "getWorkOrderReasonByWoStatus")
	public List<WorkorderReasonVO> getWorkOrderReasonByWoStatus(
			@WebParam(name = "woStatus") WorkorderStatusVO woStatus,@WebParam(name = "userId") Long userId)
			throws BusinessException;

	/**
	 * 
	 * Metodo: Retorna un listado de WorkOrderAgenda, filtrando por el dealerId
	 * y WorkOrderId por medio de la WorkOrder Assignment
	 * 
	 * @param woId
	 *            Long
	 * @param dealerId
	 *            Long
	 * @return List<WorkOrderAgendaVO>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	@WebMethod(operationName = "getWorkOrderAgendaByWoAssignment", action = "getWorkOrderAgendaByWoAssignment")
	public List<WorkOrderAgendaVO> getWorkOrderAgendaByWoAssignment(
			@WebParam(name = "woId") Long woId,
			@WebParam(name = "dealerId") Long dealerId)
			throws BusinessException;

	/**
	 * Caso de Uso ADS - 40 - Notificar cambio de estado de Work Order por IBS.
	 * 
	 * Metodo que se encarga de cambiar el estado de una WorkOrder en
	 * SmartDealer II, proveniente desde IBS.
	 * 
	 * Por cada cambio de estado se debe almacenar un registro en la tabla
	 * WO_STATUS_HISTORY.
	 * 
	 * Antes de realizar el cambio de estado es necesario validar si el cambio
	 * es posible. Estos cambios de estado se validan con la operaci�n
	 * workOrderStateMachine.
	 * 
	 * Este metodo invoca la operacion (WebService) sobre IBS que permite
	 * consultar los cambio de estado.
	 * 
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "changeWorkOrderStatus", action = "changeWorkOrderStatus")
	public void changeWorkOrderStatus(
			@WebParam(name = "customerId") Long customerId,
			@WebParam(name = "countryId") Long countryId,
			@WebParam(name = "workOrderCode") String workOrderCode,
			@WebParam(name = "workOrderReason2ChangeCode") String workOrderReason2ChangeCode,
			@WebParam(name = "workOrderReason2CompleteCode") String workOrderReason2CompleteCode,
			@WebParam(name = "flag2KnowProcess2Execute") String flag2KnowProcess2Execute,
			@WebParam(name = "newWorkOrderStatusCode") String newWorkOrderStatusCode)
			throws BusinessException;

	/**
	 * Caso de Uso ADS - 43 - Quitar Service de Work Order.
	 * 
	 * Metodo que permite quitar un servicio a una WorkOrder proveniente de IBS.
	 * 
	 * @param workOrderId
	 * @param serviceId
	 * @param decoderSerialNumber
	 * @param countryCode
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "deleteServiceToWorkOrder", action = "deleteServiceToWorkOrder")
	public void deleteServiceToWorkOrder(
			@WebParam(name = "workOrderId") Long workOrderId,
			@WebParam(name = "serviceId") Long serviceId,
			@WebParam(name = "decoderSerialNumber") String decoderSerialNumber,
			@WebParam(name = "countryCode") String countryCode)
			throws BusinessException;

	/**
	 * @param actualStatus
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWorkOrderByActualStatus", action = "getWorkOrderByActualStatus")
	public List<WorkOrderVO> getWorkOrderByActualStatus(
			@WebParam(name = "actualStatus") WorkorderStatusVO actualStatus)
			throws BusinessException;

	/**
	 * @param customerId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getCustomerById", action = "getCustomerById")
	public CustomerVO getCustomerById(
			@WebParam(name = "customerId") Long customerId)
			throws BusinessException;

	/**
	 * @param customerCode
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getCustomerByCode", action = "getCustomerByCode")
	public CustomerVO getCustomerByCode(
			@WebParam(name = "customerCode") String customerCode)
			throws BusinessException;

	/**
	 * @param workOrderId
	 * @param workOrderCode
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getCustomerByWoIdOrWoCode", action = "getCustomerByWoIdOrWoCode")
	public CustomerVO getCustomerByWoIdOrWoCode(
			@WebParam(name = "workOrderId") Long workOrderId,
			@WebParam(name = "workOrderCode") String workOrderCode)
			throws BusinessException;

	/**
	 * Metodo: Obtiene la última asignación del dealer a una work order
	 * 
	 * @param workOrderId
	 *            identificador de la work order
	 * @return asignación de la work order
	 * @throws BusinessException
	 *             En caso de error
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getLastWorkOrderAssigmentByWoId", action = "getLastWorkOrderAssigmentByWoId")
	public WoAssignmentVO getLastWorkOrderAssigmentByWoId(
			@WebParam(name = "workOrderId") Long workOrderId)
			throws BusinessException;
	
	/**
	 * Metodo: Obtiene la lista de las relaciones entre la work order y los servicios
	 * @param workOrderId identificador de la work order
	 * @return Lista con las relaciones de servicios con las work order, una lista vacia en caso que no
	 * se obtengan resultados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getWorkOrderServicesByWoId", action = "getWorkOrderServicesByWoId")
	public List<WorkOrderServiceVO> getWorkOrderServicesByWoId(@WebParam(name = "workOrderId")Long workOrderId)throws BusinessException;

	 /**
	 * Method: Valida si una Work order tiene agendamiento
	 * @param workOrderId Long
	 * @param dealerId Long
	 * @return Boolean
	 * @throws BusinessException
	 * @author jalopez
	 */
	@WebMethod(operationName = "getValidateWorkOrderScheduled", action = "getValidateWorkOrderScheduled")
    public Boolean getValidateWorkOrderScheduled(@WebParam(name = "workOrderId") Long workOrderId, @WebParam(name = "dealerId") Long dealerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza el agendamiento masivo de workorders
	 * @param List<TrayWOManagmentDTO> Lista de objetos que contienen informacion necesaria para el agendamiento masivo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "agendaWorkOrders", action = "agendaWorkOrders")
	public void agendaWorkOrders(@WebParam(name = "trayWOManagmentDTOList") List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Permite consultar si se agendan las wo se sobreagenda el dealer
	 * @param DealerWorkCapacityCriteria filtro del capacityDate, countryId, dealerId, serviceHourId, superCategoryServiceId;
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	@WebMethod(operationName = "checkAboveScheduled", action = "checkAboveScheduled")
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduled(@WebParam(name = "dealerWorkCapacityCriteria") DealerWorkCapacityCriteria dealerWorkCapacityCriteria, @WebParam(name = "quantityWorkOrder")int quantityWorkOrder)throws BusinessException;
	
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
	@WebMethod(operationName = "updateInfoWorkOrderIBSToHSP", action = "updateInfoWorkOrderIBSToHSP")
	public void updateInfoWorkOrderIBSToHSP(@WebParam(name = "countryCode") String countryCode,@WebParam(name = "woCode")String woCode) throws BusinessException;
	
	//REQ001 - WO Canceladas.
	/**
	 * Dado el id de un dealer se buscaran todas las WO canceladas.
	 *  
	 * @param dealerId el id del dealer.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author martlago
	 */
	@WebMethod(operationName = "getCanceledWorkOrdersByDealerId", action = "getCanceledWorkOrdersByDealerId")
	public List<WorkOrderVO> getCanceledWorkOrdersByDealerId(@WebParam(name = "dealerId") Long dealerId) throws BusinessException;
}
