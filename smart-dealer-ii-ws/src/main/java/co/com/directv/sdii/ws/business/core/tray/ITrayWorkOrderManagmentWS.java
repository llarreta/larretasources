package co.com.directv.sdii.ws.business.core.tray;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.EmployeeCrewDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.VerifyDesconectionSerialsDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderInfoServiceVinculationDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WorkOrderServiceResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderTrayResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ExpirationGroupingVO;
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;

/**
 * 
 * Caso de Uso ADS - 18 - Visualizar Bandeja de Work Orders de la Compañía Instaladora
 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignación Manual o Cancelación
 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
 * Caso de Uso ADS - 65 - Atencion y Finalizacion Work Orders
 * Caso de Uso ADS - 66 -  Finalizacion Work Orders
 * Interfaz que define las operaciones de neegocio para
 * la gestion de Workorders.
 * 
 * Fecha de Creación: 6/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="ITrayWorkOrderManagment",targetNamespace="http://directvla.com/contract/sdii/trayworkorders")
public interface ITrayWorkOrderManagmentWS {

	/**
     * Caso de Uso ADS - 65 - Atención y Finalización Work Orders
     * Retorna todas los workorders por dealer y que se encuentren en los siguientes estados:
     * WORKORDER_STATUS_SCHEDULED, WORKORDER_STATUS_RESCHEDULED, y WORKORDER_STATUS_RALIZED.
     * 
     * El resultado de esta consulta despliega las WorkOrders en el listado principal
     * de la funcionalidad.
     * 
     * @param WorkOrderFilterTrayDTO filterDTO - Objeto con los filtros para la consulta
     * @param RequestCollectionInfo requestCollectionInfo - Objeto para la paginacion
     * @return WOByDealerPaginationResponse
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
	@WebMethod(operationName = "getWorkOrdersAttentionFinalization", action = "getWorkOrdersAttentionFinalization")
	public WorkOrderTrayResponse getWorkOrdersAttentionFinalization(@WebParam(name = "filterDTO") WorkOrderFilterTrayDTO filterDTO, @WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
	 * Retorna los servicios asociados a una WorkOrder.
	 * @param woId
	 * @param requestCollectionInfo informacion para paginacion
	 * @return WorkOrderServiceResponse servicios asociados a la WorkOrder
	 * @throws BusinessException
	 * @author jalopez
	 */
	@WebMethod(operationName = "getWorkOrderServices", action = "getWorkOrderServices")
	public WorkOrderServiceResponse getWorkOrderServices(@WebParam(name = "woId") Long woId, @WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo: 
	 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
	 * Retorna los servicios asociados a una WorkOrder para realizar la atencion.
	 * @param woId
	 * @param requestCollectionInfo informacion para paginacion
	 * @return WorkOrderServiceResponse servicios asociados a la WorkOrder
	 * @throws BusinessException
	 * @author jalopez
	 */
	@WebMethod(operationName = "getWorkOrderServicesAttention", action = "getWorkOrderServicesAttention")
	public WorkOrderServiceResponse getWorkOrderServicesAttention(@WebParam(name = "woId") Long woId, @WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo: 
	 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
	 * Caso de Uso ADS - 66 -  Finalizacion Work Orders
	 * Retorna los servicios asociados a una WorkOrder para realizar la finalizacion.
	 * @param woId
	 * @param requestCollectionInfo informacion para paginacion
	 * @return WorkOrderServiceResponse servicios asociados a la WorkOrder
	 * @throws BusinessException
	 * @author jalopez
	 */
	@WebMethod(operationName = "getWorkOrderServicesFinalization", action = "getWorkOrderServicesFinalization")
	public WorkOrderServiceResponse getWorkOrderServicesFinalization(@WebParam(name = "woId") Long woId, @WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo: 
	 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
	 * Caso de Uso ADS - 66 -  Finalizacion Work Orders
	 * Realiza el proceso de atencion de una WorkOrder.
	 * Realiza el proceso de atencion con finalizacion de una WorkOrder.
	 * Objetos Requeridos:
	 * workorderVo(id)
	 * workOrderFinished
	 * Servicios Serializados
	 * 	workorderServices(id, [WorkOrderServiceElementVO(elementTypeId, newElementSerial, oldElementSerial, objectRecovery)])
	 * Servicios No Serializados
	 * 	
	 * @param request TrayWOManagmentDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	@WebMethod(operationName = "attentionWorkOrder", action = "attentionWorkOrder")
	public void attentionWorkOrder(@WebParam(name = "request") TrayWOManagmentDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene los servicios a partir de un tipo de WO
     * @param woTypeId Long identificador del tipo de WO
     * @return List<Service> Lista de servicios que por tipo estan asociados al tipo enviado por parametro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getServicesByWoTypeId", action = "getServicesByWoTypeId")
	public List<ServiceVO> getServicesByWoTypeId(@WebParam(name = "woTypeId") Long woTypeId) throws BusinessException;

	/**
	 * 
	 * Metodo: Obtiene los estados permitidos para la bandeja del dealer
	 * @return List<WorkorderStatusVO> Lista de estados permitidos para la bandeja del dealer
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getDealerTrayFilterStatuss", action = "getDealerTrayFilterStatuss")
	public List<WorkorderStatusVO> getDealerTrayFilterStatuss() throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los detalles de una WO
	 * @param WorkOrderFilterTrayDTO filtro para consultar la WO, en este caso solo debe llevar el pais y el codigo de WO
	 * @return WorkOrderTrayDTO Objeto con la informacion necesaria para desplegar los detalles de una WO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkorderDetail", action = "getWorkorderDetail")
	public WorkOrderTrayDTO getWorkorderDetail(@WebParam(name = "filter") WorkOrderFilterTrayDTO filter) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los elementos para el acordeon por fecha de vencimiento junto con los valores necesarios para
	 * la visualizacion. el filtro debe contener obligatorio el id del pais y dependiendo del usuario, el id del dealer y de la
	 * sucursal
	 * @param filter Filtro para consultar elementos. 
	 * @return List<ExpirationGroupingVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getAllExpirationGroupingVO", action = "getAllExpirationGroupingVO")
	public List<ExpirationGroupingVO> getAllExpirationGroupingVO(@WebParam(name = "filter") WorkOrderFilterTrayDTO filter) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta todos los empleados responsables de las cuadrillas por pais y por dealer. SI el dealer viene nulo, se filtra solo
	 * por pais
	 * @param countryId
	 * @param dealerId
	 * @return Lista de responsables con la cuadrilla asociada
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getAllResponsableEmployeeCrewByCountryAndDealerId", action = "getAllResponsableEmployeeCrewByCountryAndDealerId")
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerId(@WebParam(name = "countryId") Long countryId,@WebParam(name = "dealerId") Long dealerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene todas las categorias
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getAllServiceCategories", action = "getAllServiceCategories")
	public List<ServiceCategoryVO> getAllServiceCategories() throws BusinessException;
	
	/**
	 * Método: Permite consultar las categorias de servicio a partir de una lista de tipos de categoría
	 * @param typesId - List<Long>
	 * @return List<ServiceCategoryVO>
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getServiceCategoryByTypesId", action = "getServiceCategoryByTypesId")
	public List<ServiceCategoryVO>  getServiceCategoryByTypesId(@WebParam(name = "typesId")List<Long> typesId)throws BusinessException;
	
	/**
	 * Método: Consulta los Servicios por la Categoría activa
	 * @param categoriesId - List<Long> lista de categorías
	 * @return List<ServiceVO>
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getActiveServicesByServiceCategories", action = "getActiveServicesByServiceCategories")
	public List<ServiceVO> getActiveServicesByServiceCategories(@WebParam(name = "categoriesId")List<Long> categoriesId)throws BusinessException;
	
	/**
	 * Método: Consulta los ServiceType
	 * @return List<ServiceTypeVO> 
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getAllServiceType", action = "getAllServiceType")
	public List<ServiceTypeVO> getAllServiceType() throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene todos los tipos de WO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getAllWorkorderTypes", action = "getAllWorkorderTypes")
	public List<WoTypeVO> getAllWorkorderTypes() throws BusinessException;
	
	/**
	 * 
	 * Metodo: consulta los programas asociados al dealer, si el dealer va nulo, no lo tiene en cuenta
	 * @param dealerId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getActiveProgramsByDealerId", action = "getActiveProgramsByDealerId")
	public List<ProgramVO> getActiveProgramsByDealerId(@WebParam(name = "dealerId") Long dealerId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene los vendedores asociados a las WO asignadas a un dealer. en caos que el dealer este nulo, consulta todos los
	 * vendedores
	 * @param dealerId
	 * @param countryId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getSaleDealersByWoAsignDealerIdAndCountry", action = "getSaleDealersByWoAsignDealerIdAndCountry")
	public List<DealerVO> getSaleDealersByWoAsignDealerIdAndCountry(@WebParam(name = "dealerId") Long dealerId,@WebParam(name = "countryId") Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna los elementos requeridos por servicio
	 * @param serviceId
	 * @return RequiredServiceElementVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getRequiredServiceElements", action = "getRequiredServiceElements")
	public RequiredServiceElementVO getRequiredServiceElements(@WebParam(name = "request") TrayWOManagmentDTO request) throws BusinessException;
	
	/**
	 * Metodo: filtra las wo de una dealer para la bandeja de workorders de compania instaladora CU ADS - 18
	 * @param filter WorkOrderFilterTrayDTO Objeto que contiene todos los filtros de la consulta
	 * @param RequestCollectionInfo requestCollectionInfo, datos para realizar la paginacion
	 * @return WorkOrderTrayResponse Objeto que contiene la lista de WO que cumplen con el filtro y elementos necesarios
	 * para la paginacion
	 * @throws BusinessException En caso de error en la consulta
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkOrdersForDealerTray", action = "getWorkOrdersForDealerTray")
	public WorkOrderTrayResponse getWorkOrdersForDealerTray(@WebParam(name = "filter") WorkOrderFilterTrayDTO filter, @WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	
	/**
	 * Metodo: 
	 * CU ADS - 35 
	 * filtra las wo de una dealer para la bandeja de workorders de asignacion
	 * @param filter WorkOrderFilterTrayDTO Objeto que contiene todos los filtros de la consulta
	 * @param RequestCollectionInfo requestCollectionInfo, datos para realizar la paginacion
	 * @return WorkOrderTrayResponse Objeto que contiene la lista de WO que cumplen con el filtro y elementos necesarios
	 * para la paginacion
	 * @throws BusinessException En caso de error en la consulta
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkOrdersForAllocator", action = "getWorkOrdersForAllocator")
	public WorkOrderTrayResponse getWorkOrdersForAllocator(@WebParam(name = "filter") WorkOrderFilterTrayDTO filter, @WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Realiza el registro de los elementos empleados
	 * en la atencion de la WorkOrder para realizar la
	 * finalizacion.
	 * @param List<TrayWOManagmentDTO> trayRequest
	 * @param EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	@WebMethod(operationName = "workOrderFinalization", action = "workOrderFinalization")
	public EnvelopeEncapsulateResponse workOrderFinalization(@WebParam(name = "trayRequest") List<TrayWOManagmentDTO> trayRequest) throws BusinessException;
	
	/**
     * Metodo: Obtiene todos los estados de las workorders para la lista de work orders.
     * @return lista con los estados de work orders registrados en el sistema
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jnova
     */
	@WebMethod(operationName = "getWorkOrderStatusForDealerTray", action = "getWorkOrderStatusForDealerTray")
    public List<WorkorderStatusVO> getWorkOrderStatusForDealerTray() throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la informacion de un Elemento
	 * filtrando por el serial
	 * @param serialCode String
	 * @param Long woId
	 * @return SerializedVO
	 * @throws BusinessException
	 * @author jalopez
	 */
	@WebMethod(operationName = "getElementBySerialCode", action = "getElementBySerialCode")
	public SerializedVO getElementBySerialCode(@WebParam(name = "request") TrayWOManagmentDTO request) throws BusinessException;
    
	/**
     * Method: Retorna un listado de Crews por el
     * id del Dealer
     * @param  Long dealerId
     * @return - List<CrewVO>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
	@WebMethod(operationName = "getCrewsByDealerId", action = "getCrewsByDealerId")
	public List<CrewVO> getCrewsByDealerId(@WebParam(name = "dealerId") Long dealerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Valida que una lista de WO tengan agenda activa
	 * @param workOrderIds
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "validateWorkOrdersAgenda", action = "validateWorkOrdersAgenda")
	public void validateWorkOrdersAgenda(@WebParam(name = "workOrderIds") List<Long> workOrderIds) throws BusinessException;
	
	/**
	 * Valida que un listado de wo sean o sea valido para generar un PDF 
	 * @param woIds List<Long>
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @author waltuzarra
	 */
	@WebMethod(operationName = "validateWoBeforeGeneratingPdf", action = "validateWoBeforeGeneratingPdf")
	public List<EnvelopeEncapsulateResponse> validateWoBeforeGeneratingPdf(@WebParam(name = "woIds") List<Long> woIds,@WebParam(name = "countryId") Long countryId)  throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna las WorkOrders relacionadas
	 * al mismo cliente para realizar la finalizacion
	 * @param trayRequest
	 * @return List<WorkOrderTrayDTO>
	 * @throws BusinessException
	 * @author jalopez
	 */
	@WebMethod(operationName = "getWorkordersFinalization", action = "getWorkordersFinalization")
	public List<WorkOrderTrayDTO> getWorkordersFinalization(TrayWOManagmentDTO trayRequest)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna las WorkOrders relacionadas al mismo cliente para realizar el agendamiento
	 * @param trayRequest
	 * @return List<WorkOrderTrayDTO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkordersToSchedule", action = "getWorkordersToSchedule")
	public List<WorkOrderTrayDTO> getWorkordersToSchedule(@WebParam(name = "trayRequest") TrayWOManagmentDTO trayRequest)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta todos los empleados responsables de las cuadrillas por pais y por dealers. SI el dealer viene nulo, se filtra solo
	 * por pais
	 * @param countryId
	 * @param dealerIds
	 * @return Lista de responsables con la cuadrilla asociada
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getAllResponsableEmployeeCrewByCountryAndDealerIds", action = "getAllResponsableEmployeeCrewByCountryAndDealerIds")
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerIds(@WebParam(name = "countryId") Long countryId,@WebParam(name = "dealerIds") List<Long> dealerIds) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene los vendedores asociados a las WO asignadas a unos dealers. en caos que el dealer este nulo, consulta todos los
	 * vendedores
	 * @param dealerIds
	 * @param countryId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getSaleDealersByWoAsignDealerIdsAndCountry", action = "getSaleDealersByWoAsignDealerIdsAndCountry")
	public List<DealerVO> getSaleDealersByWoAsignDealerIdsAndCountry(@WebParam(name = "dealerIds") List<Long> dealerIds,@WebParam(name = "countryId") Long countryId,
			@WebParam(name="isSeller")String isSeller,@WebParam(name="isInstaller")String isInstaller) throws BusinessException;
	
	/**
	 * 
	 * Metodo: consulta los programas asociados a dealers, si los dealers van nulo, no lo tiene en cuenta
	 * @param dealerIds
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getActiveProgramsByDealerIds", action = "getActiveProgramsByDealerIds")
	public List<ProgramVO> getActiveProgramsByDealerIds(@WebParam(name = "dealerId") List<Long> dealerIds)throws BusinessException;

	/**
	 * Llama al servicio de cliente IBS para obtener los elementos que estan asociados a este
	 * @param customerCode
	 * @return CustomerVO
	 * @throws BusinessException
	 */
	public CustomerVO getCustomerResources(@WebParam(name = "customerCode") String customerCode, @WebParam(name = "userId") Long userId) throws BusinessException;

	@WebMethod(operationName = "getWorkOrdersForTheSameClientForDificulty", action = "getWorkOrdersForTheSameClientForDificulty")
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForDificulty(
			@WebParam(name = "woId")Long woId) throws BusinessException;

	@WebMethod(operationName = "getWorkOrdersForTheSameClientForReject", action = "getWorkOrdersForTheSameClientForReject")
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForReject(
			@WebParam(name = "woId")Long woId) throws BusinessException;

	@WebMethod(operationName = "getWorkOrdersForTheSameClientForAssignDinamicCrew", action = "getWorkOrdersForTheSameClientForAssignDinamicCrew")
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForAssignDinamicCrew(
			@WebParam(name = "woIds")List<Long> woIds) throws BusinessException;

	@WebMethod(operationName = "getWorkOrdersForTheSameClientForAssignStaticCrew", action = "getWorkOrdersForTheSameClientForAssignStaticCrew")
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForAssignStaticCrew(
			@WebParam(name = "woIds")List<Long> woIds) throws BusinessException;
	
	/**
	 * Metodo encargado de validar los seriales ingresados para la atencion de una work order de desconexion
	 * @param request
	 * @throws BusinessException
	 * @author Aharker
	 */
	@WebMethod(operationName = "verifyDesconectionSerials", action = "verifyDesconectionSerials")
	public void verifyDesconectionSerials(@WebParam(name = "request")VerifyDesconectionSerialsDTO request) throws BusinessException ;

	/**
	 * Metodo que verifica en la atencion de una work order de desconexion si los seriales ingresados coinciden con los asociados al servicio
	 * @param request datos de los seriales, la work order y el servicio al cual se le realizara la validacion
	 * @throws BusinessException
	 * @author aharker
	 */
	@WebMethod(operationName = "getSerialForAttentionDesconectionWO", action = "getSerialForAttentionDesconectionWO")
	public String getSerialForAttentionDesconectionWO(@WebParam(name = "woId")Long woId, @WebParam(name = "woServiceId")Long woServiceId) throws BusinessException;
	
}
