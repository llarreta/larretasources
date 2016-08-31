package co.com.directv.sdii.facade.core.tray;

import java.util.List;

import javax.ejb.Local;
import javax.jws.WebParam;

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
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;
/**
 * Caso de Uso ADS - 18 - Visualizar Bandeja de Work Orders de la Compañía Instaladora
 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignación Manual o Cancelación
 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
 * Caso de Uso ADS - 65 - Atencion y Finalizacion Work Orders
 * Caso de Uso ADS - 66 -  Finalizacion Work Orders
 * Fachada que define las operaciones de negocio para la gestion de
 * WorkOrders
 * 
 * Fecha de Creación: 6/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TrayWorkOrderManagmentFacadeLocal {
	
	/**
     * Caso de Uso ADS - 65 - Atención y Finalización Work Orders
     * Retorna todas los workorders por dealer y que se encuentren en los siguientes estados:
     * WORKORDER_STATUS_SCHEDULED, WORKORDER_STATUS_RESCHEDULED, y WORKORDER_STATUS_RALIZED.
     * 
     * El resultado de esta consulta despliega las WorkOrders en el listado principal
     * de la funcionalidad.
     * @param WorkOrderFilterTrayDTO filterDTO - Objeto con los filtros para la consulta
     * @param RequestCollectionInfo requestCollectionInfo - Objeto para la paginacion
     * @return WOByDealerPaginationResponse
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */	
	public WorkOrderTrayResponse getWorkOrdersAttentionFinalization(WorkOrderFilterTrayDTO filterDTO, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
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
	public WorkOrderServiceResponse getWorkOrderServices(Long woId, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
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
	public WorkOrderServiceResponse getWorkOrderServicesAttention(Long woId, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
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
	public WorkOrderServiceResponse getWorkOrderServicesFinalization(Long woId, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	/**
	 * Metodo: filtra las wo de una dealer para la bandeja de workorders de compania instaladora CU ADS - 18
	 * @param filter WorkOrderFilterTrayDTO Objeto que contiene todos los filtros de la consulta
	 * @param RequestCollectionInfo requestCollectionInfo, datos para realizar la paginacion
	 * @return WorkOrderTrayResponse Objeto que contiene la lista de WO que cumplen con el filtro y elementos necesarios
	 * para la paginacion
	 * @throws BusinessException En caso de error en la consulta
	 * @author jnova
	 */
	public WorkOrderTrayResponse getWorkOrdersForDealerTray(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
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
	public WorkOrderTrayResponse getWorkOrdersForAllocator(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo) throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: 
	 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
	 * Caso de Uso ADS - 66 -  Finalizacion Work Orders
	 * Realiza el proceso de atencion de una WorkOrder.
	 * Realiza el proceso de atencion con finalizacion de una WorkOrder.
	 * @param request TrayWOManagmentDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void attentionWorkOrder(TrayWOManagmentDTO request) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene la informacion de un elemento por id del dealer. Lo busca en la bodega de disponibles de la compañia instaladora
	 * y de las cuadrillas de esta compañia. 
	 * @param elementSerial Serial del elemento que esta buscando
	 * @param dealerId id del dealer en donde va a buscar el elemento
	 * @return elemento encontrado en la bodega de disponibles
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	//public WarehouseElementVO getElementBySerialAndDealerId(String elementSerial, Long dealerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene los servicios a partir de un tipo de WO
     * @param woTypeId Long identificador del tipo de WO
     * @return List<Service> Lista de servicios que por tipo estan asociados al tipo enviado por parametro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<ServiceVO> getServicesByWoTypeId(Long woTypeId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene la informacion de un elemento por id del customer.
	 * @param elementSerial Serial del elemento que esta buscando
	 * @param customerId id del customer
	 * @return elemento encontrado en la bodega de disponibles
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	//public WarehouseElementVO getElementBySerialAndCustomerId(String elementSerial, Long customerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene los estados permitidos para la bandeja del dealer
	 * @return List<WorkorderStatusVO> Lista de estados permitidos para la bandeja del dealer
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<WorkorderStatusVO> getDealerTrayFilterStatuss() throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los detalles de una WO
	 * @param WorkOrderFilterTrayDTO filtro para consultar la WO, en este caso solo debe llevar el pais y el codigo de WO
	 * @return WorkOrderTrayDTO Objeto con la informacion necesaria para desplegar los detalles de una WO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public WorkOrderTrayDTO getWorkorderDetail(WorkOrderFilterTrayDTO filter) throws BusinessException;
	
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
	public List<ExpirationGroupingVO> getAllExpirationGroupingVO(WorkOrderFilterTrayDTO filter) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta todos los empleados responsables de las cuadrillas por pais y por dealer. SI el dealer viene nulo, se filtra solo
	 * por pais
	 * @param countryId
	 * @param dealerId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerId(Long countryId,Long dealerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene todas las categorias
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<ServiceCategoryVO> getAllServiceCategories() throws BusinessException;
	
	
	/**
	 * Método: Obtiene las caterogirias de servicio dada una lista de tipos de servicio
	 * @param typesId - List<Long> 
	 * @return List<ServiceCategoryVO> Asociadas a los tipos de servicio
	 * @throws BusinessException
	 */
	public List<ServiceCategoryVO> getServiceCategoryByTypesId(List<Long> typesId) throws BusinessException;
	
	/**
	 * Método: Obtiene los servicios asociado a la lista de categorías
	 * @param categoriesId - List<Long> Lista de categorías
	 * @return List<ServiceVO> - asociados a la lista de categorías
	 * @throws BusinessException
	 */
	public List<ServiceVO> getActiveServicesByServiceCategories(
			List<Long> categoriesId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene todos los tipos de WO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<WoTypeVO> getAllWorkorderTypes() throws BusinessException;
	
	/**
	 * 
	 * Metodo: consulta los programas asociados al dealer, si el dealer va nulo, no lo tiene en cuenta
	 * @param dealerId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<ProgramVO> getActiveProgramsByDealerId(Long dealerId)throws BusinessException;
	
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
	public List<DealerVO> getSaleDealersByWoAsignDealerIdAndCountry(Long dealerId, Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna los elementos requeridos por servicio
	 * @param TrayWOManagmentDTO request
	 * @return List<RequiredServiceElementVO>
	 * @throws BusinessException
	 * @author jalopez
	 */
	public RequiredServiceElementVO getRequiredServiceElements(TrayWOManagmentDTO request) throws BusinessException;
	
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
	public EnvelopeEncapsulateResponse workOrderFinalization(List<TrayWOManagmentDTO> trayRequest) throws BusinessException;

	/**
	 * Método: Permite consultar los Tipos de Servicio
	 * @return List<ServiceTypeVO>
	 * @throws BusinessException
	 * @author gfandino
	 */
	public List<ServiceTypeVO> getAllServiceType()throws BusinessException;

	/**
	 * Metodo: Obtiene la lista de estados de Work Order que se pueden consultar
	 * @return List<WorkorderStatusVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author gfandino
	 */
	public List<WorkorderStatusVO> getWorkOrderStatusForDealerTray()throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la informacion de un Elemento
	 * filtrando por el serial
	 * @param TrayWOManagmentDTO request
	 * @return SerializedVO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public SerializedVO getElementBySerialCode(TrayWOManagmentDTO request) throws BusinessException;
	
	/**
     * Method: Retorna un listado de Crews por el
     * id del Dealer
     * @param  Long dealerId
     * @return - List<CrewVO>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
	public List<CrewVO> getCrewsByDealerId(Long dealerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Valida que una lista de WO tengan agenda activa
	 * @param workOrderIds
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void validateWorkOrdersAgenda(List<Long> workOrderIds) throws BusinessException;
	
	/**
	 * Valida que un listado de wo sean o sea valido para generar un PDF 
	 * @param woIds List<Long>
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 */
	public List<EnvelopeEncapsulateResponse> validateWoBeforeGeneratingPdf(List<Long> woIds, Long countryId)  throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna las WorkOrders relacionadas
	 * al mismo cliente para realizar la finalizacion
	 * @param trayRequest
	 * @return List<WorkOrderTrayDTO>
	 * @throws BusinessException
	 * @author jalopez
	 */
	public List<WorkOrderTrayDTO> getWorkordersFinalization(TrayWOManagmentDTO trayRequest)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna las WorkOrders relacionadas al mismo cliente para realizar el agendamiento
	 * @param trayRequest
	 * @return List<WorkOrderTrayDTO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<WorkOrderTrayDTO> getWorkordersToSchedule(TrayWOManagmentDTO trayRequest)throws BusinessException;
	
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
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerIds(Long countryId, List<Long> dealerIds) throws BusinessException ;
	
	/**
	 * 
	 * Metodo: Obtiene los vendedores asociados a las WO asignadas a unos dealers. en caos que el dealer este nulo, consulta todos los
	 * vendedores
	 * @param dealerIds
	 * @param countryId
	 * @param isSeller identifica si se trata de un dealer vendedor
	 * @param isInstaller identifica si se trata de un dealer instalador
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getSaleDealersByWoAsignDealerIdsAndCountry(List<Long> dealerIds, Long countryId,
			String isSeller, String isInstaller) throws BusinessException;
	
	/**
	 * 
	 * Metodo: consulta los programas asociados a dealers, si los dealers van nulo, no lo tiene en cuenta
	 * @param dealerIds
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<ProgramVO> getActiveProgramsByDealerIds(List<Long> dealerIds)throws BusinessException;

	/**
	 * Llama al servicio de cliente IBS para obtener los elementos que estan asociados a este
	 * @param customerCode
	 * @return
	 * @throws BusinessException
	 */
	public CustomerVO getCustomerResources(String customerCode, Long userId) throws BusinessException;
	
    
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForDificulty(Long woId) throws BusinessException;
	
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForReject(Long woId) throws BusinessException;
	
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForAssignDinamicCrew(List<Long> woId) throws BusinessException;
	
	public List<WorkOrderTrayDTO> getWorkOrdersForTheSameClientForAssignStaticCrew(List<Long> woId) throws BusinessException;

	/**
	 * Metodo que verifica en la atencion de una work order de desconexion si los seriales ingresados coinciden con los asociados al servicio
	 * @param request datos de los seriales, la work order y el servicio al cual se le realizara la validacion
	 * @throws BusinessException
	 * @author aharker
	 */
	public void verifyDesconectionSerials(VerifyDesconectionSerialsDTO request) throws BusinessException;
	
	/**
	 * Metodo que verifica en la atencion de una work order de desconexion si los seriales ingresados coinciden con los asociados al servicio
	 * @param request datos de los seriales, la work order y el servicio al cual se le realizara la validacion
	 * @throws BusinessException
	 * @author aharker
	 */
	public String getSerialForAttentionDesconectionWO(Long woId, Long woServiceId) throws BusinessException;
}
