package co.com.directv.sdii.ws.business.core.wo.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.facade.config.StateMachineWOFacadeLocal;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
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
import co.com.directv.sdii.ws.business.core.wo.ICoreWoWS;

/**
 * 
 * Interfaz que define las operaciones de los web services de Core.
 * 
 * Casos de uso:
 * 
 * Caso de Uso ADS - 26 - Detalle del Cliente ==> Se debe realizar cuando se haga el modulo de Clientes
 * Caso de Uso ADS - 27 - Diligenciar Encuesta de Servicio
 * Caso de Uso ADS - 30 - Exportar Bandeja de Work Orders a Excel ==> Todas las tablas se deben exportar a Excel
 * Caso de Uso ADS - 60 - Registrar elementos NO serializados utilizados en la atencion del servicio ==> Se debe realizar cuando se haga el modulo de Inventarios 
 * Caso de Uso ADS - 61 - Registrar elementos serializados utilizados en la atencion del servicio ==> Se debe realizar cuando se haga el modulo de Inventarios
 * Caso de Uso ADS - 62 - Registrar  cambio  de  elementos serializados donde el cliente  en  la ate ==> Se debe realizar cuando se haga el modulo de Inventarios
 * Caso de Uso ADS - 63 - Registrar Recuperacion  de  elementos serializados  donde el cliente ==> Se debe realizar cuando se haga el modulo de Inventarios
 * Caso de Uso ADS - 64 - Registrar Recuperacion  de  elementos NO serializados  donde el cliente ==> Se debe realizar cuando se haga el modulo de Inventarios
 * 
 * Fecha de Creación: 21/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.core.co.com.directv.sdii.facade.core
 */
@MTOM(threshold=3072)
@WebService(serviceName="CoreWoService",
		endpointInterface="co.com.directv.sdii.ws.business.core.wo.ICoreWoWS",
		targetNamespace="http://core.business.ws.sdii.directv.com.co/",
		portName="CoreWoPort")	
@Stateless()
//@HandlerChain(file="handler-chain.xml")
public class CoreWoWS implements ICoreWoWS{
	
	@EJB
	private CoreWOFacadeLocal coreWOFacade;
	@EJB
	private StateMachineWOFacadeLocal stateMachine;

	
	/**
	 * Caso de Uso ADS - 17 - Crear Work Order.
	 * 
	 * Método que se encarga de crear una WorkOrder que proviene
	 * de IBS. Se debe almacenar en la tabla WORK_ORDERS.
	 * Se debe invocar un WebService de IBS que retornará la lista
	 * de WorkOrders a crear en SmartDealer II.
	 * 
	 * Se debe notificar cambio de estado de la WorkOrder a IBS.
	 * @throws BusinessException en caso de asicionar una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @throws PropertiesException 
	 */	
	public void addWorkOrder(String countryCode) throws BusinessException{
		//try{
			coreWOFacade.addWorkOrder(countryCode);
			/*woInfoEsbServiceFacadeLocal.processCoreAndAllocator(countriesFacadeBeanLocal.getCountriesByCode(countryCode).getId(), 
					CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity());*/
		/*}catch (PropertiesException e) {
			throw new BusinessException(e.getMessage(), e);
		}*/
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.ICoreWoWS#addServiceToWorkOrder(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public void addServiceToWorkOrder(Long workOrderId, Long serviceId,
			String decoSerialNumber, String countryCode, Long userId)
			throws BusinessException {
		coreWOFacade.addServiceToWorkOrder(workOrderId, serviceId, decoSerialNumber, countryCode, userId);
	}
	
	/**
	 * Caso de Uso ADS - 21 - Asignar Cuadrilla Dinamica a Work Orders.
	 *  
	 * Método que se encarga de asignar una cuadrilla dinémica a una WorkOrder.
	 * Se debe almacenar en la tabla WO_ASSIGNMENTS.
	 * La WorkOrder ya debe existir en la tabla WORK_ORDERS y WO_ASSIGNMENTS.
	 * 
	 * No cambia el estado de la WorkOrder en IBS.
	 * 
	 * @param workorder
	 * @param crew
	 * @throws BusinessException en caso de asignar una cuadrilla dinamica a una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public boolean assignDynamicCrewToWorkOrder(@WebParam(name = "workOrderList")List<WorkOrderVO> workOrderList, 
			@WebParam(name = "crew")CrewVO crew,
			@WebParam(name = "userId") Long userId) throws BusinessException{
		return coreWOFacade.assignDynamicCrewToWorkOrder(workOrderList, crew, userId);
	}
	
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
	 * @throws BusinessException En caso de asignar un programa a una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public void assignProgramToWorkOrder(@WebParam(name = "workorder")WorkOrderVO workorder, @WebParam(name = "program")ProgramVO program) throws BusinessException{
		coreWOFacade.assignProgramToWorkOrder(workorder, program);
	}
	
	
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
	 * @throws BusinessException En caso de asignar un programa a una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public void assignProgramToWorkOrders(@WebParam(name = "workorders")List<WorkOrderVO> workorders, @WebParam(name = "program")ProgramVO program) throws BusinessException{
		coreWOFacade.assignProgramToWorkOrders(workorders, program);
	}
	
	/**
	 * Query By Example. Obtiene la workorders que cumplan con alguno de los criterios de entrada del Dealer.
	 * 
	 * @param Dealer
	 * @param RequestCollectionInfo
	 * @return WOByDealerPaginationResponse
	 * @throws BusinessException en caso de obtener las asignaciones de wo por un dealer
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public WOByDealerPaginationResponse getWorkOrderByDealer(@WebParam(name = "dealer")DealerVO dealer, @WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo) throws BusinessException{		
		return coreWOFacade.getWorkOrderByDealer(dealer, requestCollectionInfo);
	}
	
	/**
	 * Caso de Uso ADS - 25 - Detalle de la Work Order.
	 * 
	 * Método que se encarga de obtener una WorkOrder por ID.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param id
	 * @return WorkOrderVO
	 * @throws BusinessException en caso de obtener una workorder por un dealer
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public WorkOrderVO getWorkOrderByID(@WebParam(name = "id")Long id)throws BusinessException{
		return coreWOFacade.getWorkOrderByID(id);
	}
	
	/**
	 * Metodo que garantiza que un dealer q no es el logueado no vea la workorder correspondiente y retorna una workorder por el id
	 * @param dealerId
	 * @param workOrderId
	 * @return WorkOrderVO
	 * @throws BusinessException En caso de obtener una wo por el dealerId y la woId
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public WorkOrderVO getWorkOrderByDealerIdAndWorkOrderId(@WebParam(name = "dealerId")Long dealerId, @WebParam(name = "workOrderId")Long workOrderId)throws BusinessException{
		return coreWOFacade.getWorkOrderByDealerIdAndWorkOrderId(dealerId, workOrderId);
	}
	
	
	/**
	 * 
	 * Metodo: realiza la validacion del cambio
	 * de estado actual a otro y verifica si es posible 
	 * realizar el cambio de estado.
	 * Retorna true si es posible realizar el cambio, de 
	 * lo contrario genera un BusinessException.
	 * Este metodo sera cambiando por validateStatusChangeWorkOrderByCodes
	 * @param WorkOrderVO workOrder
	 * @param Long statusChange
	 * @return boolean, true si es posible el cambio de estado.
	 * @throws BusinessException En casi de validar el cambio de estado de una workorder 
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jalopez
	 */
	@Deprecated
	public boolean validateStatusChangeWorkOrder(@WebParam(name = "workOrder")WorkOrderVO workOrder,@WebParam(name = "statusChange")Long statusChange) throws BusinessException{
		return stateMachine.validateStatusChangeWorkOrder(workOrder,statusChange);
	}
	
	/**
	 * Servicio que permite asignar cuadrilla a una orden de trabajo
	 * @param workorder - WorkOrderVO
	 * @param crew - crew
	 * @throws BusinessException En caso de asignar una cuadrilla fija a una workorder 
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	public void assignFixedCrewToWorkOrder(@WebParam(name = "workorder")WorkOrderVO workorder,
			@WebParam(name = "crew")CrewVO crew,
			@WebParam(name = "userId") Long userId) throws BusinessException{
		coreWOFacade.assignFixedCrewToWorkOrder(workorder, crew, userId);
	}
	
	/**
	 * Servicio que permite asignar cuadrilla a una orden de trabajo.
	 * @param workorder - WorkOrderVO
	 * @param crew - crew
	 * @throws BusinessException En casi de asignar una cuadrilla fija a un conjunto de workorders
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino 
	 */
	public void assignFixedCrewToWorkOrders(@WebParam(name = "workordersCrewsList")List<WorkOrderInfoServiceVinculationDTO> workordersCrewsList,
			@WebParam(name = "userId") Long userId) throws BusinessException{
		try{
			coreWOFacade.assignFixedCrewToWorkOrders(workordersCrewsList, userId);
		}catch(Throwable ex){
			System.out.println("entra al error");
			throw (BusinessException)ex;
		}
	}
	
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
	 * @throws BusinessException En caso de asignar un conjunto de workorders a un dealer
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public void assignWorkOrdersToDealer(@WebParam(name = "dto") AssignWorkOrderDTO dto,@WebParam(name = "workorders") List<WorkOrderVO> workorders) throws BusinessException{
		coreWOFacade.assignWorkOrdersToDealer(dto, workorders);
	}
	
	/**
	 * Servicio que permite rechazar una orden de trabajo
	 * @param TrayWOManagmentDTO objeto que encapsula informacion para agendamiento
	 * @throws BusinessException En caso de rechazo de una wordorder asignandole una woReason
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	public void rejectWorkOrder(List<TrayWOManagmentDTO> trayWOManagmentDTO) throws BusinessException{
		coreWOFacade.rejectWorkOrder(trayWOManagmentDTO);
	}
	
	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignacién Manual o Cancelacién.
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders en estado WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param actualStatus
	 * @return List<WorkOrderVO>
	 * @throws BusinessException En caso de error al obtener las wo acticvas y pendientes
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public List<WorkOrderVO> getWorkOrdersActiveAndSuspend() throws BusinessException{
		return coreWOFacade.getWorkOrdersActiveAndSuspend();
	}
	
	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignacién Manual o Cancelacién.
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders en estado WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param actualStatus
	 * @param requestCollectionInfo
	 * 
	 * @return WOActiveAndSuspendByCountryIdPaginationResponse
	 * @throws BusinessException En caso de error al obtener las wo acticvas y pendientes
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersActiveAndSuspendByCountryId(Long countryId, @WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		return coreWOFacade.getWorkOrdersActiveAndSuspendByCountryId(countryId, requestCollectionInfo);
	}
	
	/**
	 * Caso de Uso ADS - 24 - Dificultad en Work Order.
	 * 
	 * Método que se encarga de registrar una dificultad en una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS, ejecutando el caso de uso
	 * CU ADS - 33 Notificar cambio de estado de Work Order a IBS.
	 * 
	 * @param TrayWOManagmentDTO trayWOManagmentDTO dto que encapsula de la WO
	 * @throws BusinessException En caso de error al reportar dificultad a una work order
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public void reportDifficultyToWorkOrder(List<TrayWOManagmentDTO> trayWOManagmentDTO) throws BusinessException{
		coreWOFacade.reportDifficultyToWorkOrder(trayWOManagmentDTO);
	}
	
	/**
	 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignacién Manual o Cancelacién.
	 * 
	 * Método que se encarga de obtener una lista de WorkOrders en estado WORKORDER_STATUS_ACTIVE y WORKORDER_STATUS_PENDING.
	 * Se lee de la tabla WORK_ORDERS.
	 * 
	 * @param actualStatus
	 * @return List<WorkOrderVO>
	 * @throws BusinessException En caso de error al tratar de asignar una workorder a un dealer
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public void assignWorkOrderToDealer(@WebParam(name = "dto") AssignWorkOrderDTO dto) throws BusinessException {
		coreWOFacade.assignWorkOrderToDealer(dto);
	}
	
	/**
	 * Servicio que permite consultar la lista de workorders por varios criterios de consulta.
	 * 
	 * @author Jimmy Vélez Muéoz
	 * @param dealerId
	 * @param woCode
	 * @param serviceCategoryId
	 * @param ServiceTypeId
	 * @param woStatusId
	 * @param stateId
	 * @param cityId
	 * @param address
	 * @param creationDate
	 * @param programmingDate
	 * @param requestCollectionInfo
	 * @return WOByDealerWorkOrderQBEPaginationResponse
	 * @throws BusinessException En caso de error al tratar de obtener una lista de asignaciones de workorders por los criterios espeficificados
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public WOByDealerWorkOrderQBEPaginationResponse getWorkOrdersByWorkOrderQBE(@WebParam(name = "dealerId")Long dealerId, @WebParam(name = "woCode")String woCode,
			@WebParam(name = "serviceCategoryId")Long serviceCategoryId, @WebParam(name = "ServiceTypeId")Long ServiceTypeId, @WebParam(name = "woStatusId")Long woStatusId,
			@WebParam(name = "stateId")Long stateId, @WebParam(name = "cityId")Long cityId, @WebParam(name = "postalCodeId")Long postalCodeId, @WebParam(name = "creationDate")Date creationDate,
			@WebParam(name = "programmingDate")Date programmingDate, @WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		return coreWOFacade.getWorkOrdersByDealerWorkOrderQBE(dealerId, woCode, ServiceTypeId, serviceCategoryId, woStatusId, stateId, cityId, postalCodeId, creationDate, programmingDate, requestCollectionInfo);
	}
	
	/**
	 * Servicio que permite consultar la lista de workorders por dealer, fecha y cuadrilla.
	 * 
	 * @author Jimmy Vélez Muéoz
	 * @param dealerId
	 * @param dateId
	 * @param crewId
	 * @param requestCollectionInfo
	 * @return WOByDealerDateCrewQBEPaginationResponse
	 * @throws BusinessException En caso de error al tratar de obtener una lista de asignaciones de wo por dealer, fecha o crew
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public WOByDealerDateCrewQBEPaginationResponse getWorkOrdersByDealerDateCrewQBE(@WebParam(name = "dealerId")Long dealerId,
			@WebParam(name = "dateId")Long dateId, @WebParam(name = "crewId")Long crewId, @WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return coreWOFacade.getWorkOrdersByDealerDateCrewQBE(dealerId, dateId, crewId, requestCollectionInfo);
	}
	
	
	/**
	 * Servicio que permite consultar la lista de workorders por datos del cliente.
	 * 
	 * @author Jimmy Vélez Muéoz
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
	 * @throws BusinessException En caso de error al tratar de obtener una lista de agendamientos de wo, por criterios del clientes
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public WOByCustomerQBEPaginationResponse getWorkOrdersByCustomerQBE(@WebParam(name = "dealerId")Long dealerId, @WebParam(name = "ibsCode")String ibsCode,
			@WebParam(name = "officePhone")String officePhone, @WebParam(name = "homePhone")String homePhone, @WebParam(name = "faxPhone")String faxPhone,
			@WebParam(name = "cellPhone")String cellPhone, @WebParam(name = "idNumber")String idNumber, @WebParam(name = "name")String name, @WebParam(name = "lastName")String lastName, @WebParam(name = "requestCollectionInfo")RequestCollectionInfo requestCollectionInfo)
			throws BusinessException {
		return coreWOFacade.getWorkOrdersByDealerCustomerQBE(dealerId, ibsCode, officePhone, homePhone, faxPhone, cellPhone, idNumber, name, lastName, requestCollectionInfo);
	}
	
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
	 * @param workorder
	 * @param serviceHour
	 * @param agendationDate
	 * @param contactPerson
	 * @param comment
	 * @param WorkorderReasonVO reason
	 * @throws BusinessException En caso de error al tratar de agendar una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public void agendaWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException{
		coreWOFacade.agendaWorkOrder(trayWOManagmentDTO);
	}
	
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
	 * @throws BusinessException En caso de error al tratar de obtener una lista de workorders por los criterios asociados
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByWorkOrderDataQBE(@WebParam(name = "dealerId")Long dealerId, @WebParam(name = "woCode")String woCode,
			@WebParam(name = "ServiceTypeId")Long ServiceTypeId, @WebParam(name = "serviceCategoryId")Long serviceCategoryId, @WebParam(name = "woStatusId")Long woStatusId, @WebParam(name = "countryId")Long countryId,
			@WebParam(name = "stateId")Long stateId, @WebParam(name = "cityId")Long cityId, @WebParam(name = "postalCodeId")Long postalCodeId, @WebParam(name = "creationDate")Date creationDate,
			@WebParam(name = "programmingDate")Date programmingDate,@WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		return coreWOFacade.getWorkOrdersByWorkOrderDataQBE(dealerId, woCode, ServiceTypeId, serviceCategoryId, woStatusId, countryId, stateId, cityId, postalCodeId, creationDate, programmingDate,requestCollectionInfo);
		
	}
	
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
	 * @throws BusinessException En caso de error al tratar de obtener una workOrder por los datos del customer
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	@Override
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByCustomerDataQBE(
			@WebParam(name = "countryId")Long countryId,
			@WebParam(name = "ibsCode")String ibsCode,
			@WebParam(name = "officePhone")String officePhone, @WebParam(name = "homePhone")String homePhone, @WebParam(name = "faxPhone")String faxPhone,
			@WebParam(name = "cellPhone")String cellPhone, @WebParam(name = "idNumber")String idNumber, @WebParam(name = "name")String name, @WebParam(name = "lastName")String lastName,
			@WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo)
			throws BusinessException{
		return coreWOFacade.getWorkOrdersByCustomerDataQBE(countryId, ibsCode, officePhone, homePhone, faxPhone, cellPhone, idNumber, name, lastName, requestCollectionInfo);
	}
	
	
	/**
	 * Caso de Uso ADS - 37 - Cancelar Work Order.
	 * 
	 * Método que se encarga de registrar la razén de la cancelacién de una WorkOrder.
	 * 
	 * Se debe notificar el cambio de estado de la WorkOrder a IBS.
	 * Se debe afectar la capacidad del DEALER en DEALERS_SERVICE_CAPACITY (invocar método updateDealerServiceCapacity).
	 * 
	 * @param List<TrayWOManagmentDTO> trayWOManagmentDTOList
	 * @throws BusinessException En caso de error al tratar de cancelar una lista de workorders por una reason asociada
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public void cancelWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException{
		coreWOFacade.cancelWorkOrders(trayWOManagmentDTOList);
	}
	
	/**
	 * Lee las pdfs asociadas a un dealer especificado para luego ser enviadas por email
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param dealerCode
	 * @throws BusinessException En caso de error al tratar de obtener los archivos de workorders en PDF por el codigo del dealer
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public List<String> getWorkOrdersFilesByDealer(@WebParam(name = "dealerCode")Long dealerCode)throws BusinessException {
		return coreWOFacade.getWorkOrdersFilesByDealer(dealerCode);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.ICoreWoWS#getWorkOrdersFilesByCrewId(java.lang.Long)
	 */
	public List<String> getWorkOrdersFilesByCrewId(Long crewId)throws BusinessException{
		return coreWOFacade.getWorkOrdersFilesByCrewId(crewId);
	}
	/**
	 * 
	 * Metodo: Retorna un WoStatusHistory con la workorderreason
	 * del ultimo estado de una workorder.
	 * @param woId Long
	 * @param woStatusId Long
	 * @return WoStatusHistoryVO
	 * @throws BusinessException En caso de error al tratar de obtener una woReason por la woHistory
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jalopez
	 */
	public WoStatusHistoryVO getWorkorderReasonByWoHistory(@WebParam(name = "woId")Long woId, @WebParam(name = "woStatusId")Long woStatusId) throws BusinessException{
		return coreWOFacade.getWorkorderReasonByWoHistory(woId, woStatusId);
	}
	
	
	/**
	 * Metodo: Retorna la lista de WorkorderReasonVO que se encuentran asociadas a una WorkorderStatusVO
	 * @param woStatus - WorkorderStatusVO
	 * @return List<WorkorderReasonVO>
	 * @throws BusinessException En caso de error al tratar de obtener una razón de una wo por el estado
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	public List<WorkorderReasonVO> getWorkOrderReasonByWoStatus(WorkorderStatusVO woStatus, Long userId) throws BusinessException{
		return coreWOFacade.getWorkOrderReasonByWoStatus(woStatus, userId);
	}
	
	
	/**
     * 
     * Metodo: Retorna un listado de WorkOrderAgenda, filtrando por
     * el dealerId y WorkOrderId por medio de la WorkOrder Assignment
     * @param woId Long
     * @param dealerId Long
     * @return List<WorkOrderAgendaVO>
	 * @throws BusinessException En caso de error al tratar de obtener una woAgenda por su asignación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * @author jalopez
     */
	public List<WorkOrderAgendaVO> getWorkOrderAgendaByWoAssignment(@WebParam(name = "woId")Long woId,@WebParam(name = "dealerId")Long dealerId) throws BusinessException{
		return coreWOFacade.getWorkOrderAgendaByWoAssignment(woId, dealerId);
	}
	
	/**
	 * Caso de Uso ADS - 40 - Notificar cambio de estado de Work Order por IBS.
	 * 
	 * Método que se encarga de cambiar el estado de una WorkOrder en SmartDealer II, proveniente desde IBS.
	 * 
	 * Por cada cambio de estado se debe almacenar un registro en la tabla WO_STATUS_HISTORY.
	 * 
	 * Antes de realizar el cambio de estado es necesario validar si el cambio es posible. Estos cambios
	 * de estado se validan con la operacién workOrderStateMachine.
	 * 
	 * Esté método invoca la operacién (WebService) sobre IBS que permite consultar los cambio de estado.
	 * 
	 * @throws BusinessException En caso de error al tratar de cambiar el estado de una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public void changeWorkOrderStatus(@WebParam(name = "customerId")Long customerId, @WebParam(name = "countryId")Long countryId, @WebParam(name = "workOrderCode")String workOrderCode, @WebParam(name = "workOrderReason2ChangeCode")String workOrderReason2ChangeCode,@WebParam(name = "workOrderReason2CompleteCode") String workOrderReason2CompleteCode, @WebParam(name = "flag2KnowProcess2Execute")String flag2KnowProcess2Execute,@WebParam(name = "newWorkOrderStatusCode") String newWorkOrderStatusCode) throws BusinessException {
		coreWOFacade.changeWorkOrderStatus(customerId, countryId, workOrderCode, workOrderReason2ChangeCode, workOrderReason2CompleteCode, flag2KnowProcess2Execute, newWorkOrderStatusCode);
	}
	
	/**
	 * Caso de Uso ADS - 43 - Quitar Service de Work Order.
	 * 
	 * Método que permite quitar un servicio a una WorkOrder proveniente de IBS.
	 * @param decoderSerialNumber número de serie del decodificador
	 * @param serviceCode código del servicio a ser removido
	 * @param workOrderCode Código de la work order a la que se le borrará el servicio
	 * 
	 * @throws BusinessException En caso de error al tratar de eliminar un servicio de una workorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	@Override
	public void deleteServiceToWorkOrder(Long workOrderId, Long serviceId, String decoderSerialNumber, String countryCode) throws BusinessException{
		coreWOFacade.deleteServiceToWorkOrder(workOrderId, serviceId, decoderSerialNumber, countryCode);
	}

	/**
	 * 
	 * @param actualStatus
	 * @return List<WorkOrderVO>
	 * @throws BusinessException En caso de error al tratar de obtener una workorder por su estado
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 */
	public List<WorkOrderVO> getWorkOrderByActualStatus(@WebParam(name = "actualStatus")WorkorderStatusVO actualStatus) throws BusinessException{
		return coreWOFacade.getWorkOrderByActualStatus(actualStatus);
	}
	
	/**
	 * 
	 * @param customerId
	 * @return CustomerVO
	 * @throws BusinessException En caso de error al tratar de obtener un cliente por su id
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<b
	 */
	public CustomerVO getCustomerById(@WebParam(name = "customerId")Long customerId)throws BusinessException{
		return coreWOFacade.getCustomerById(customerId);
	}
	
	/**
	 * 
	 * @param customerCode
	 * @return CustomerVO
	 * @throws BusinessException En caso de error al tratar de obtener un cliente por su code
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<b
	 */
	public CustomerVO getCustomerByCode(@WebParam(name = "customerCode")String customerCode)throws BusinessException{
		return coreWOFacade.getCustomerByCode(customerCode);
	}
	
	/**
	 * 
	 * @param workOrderId
	 * @param workOrderCode
	 * @return CustomerVO
	 * @throws BusinessException En caso de error al tratar de obtener un cliente por su code o id
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<b
	 */
	public CustomerVO getCustomerByWoIdOrWoCode(@WebParam(name = "workOrderId")Long workOrderId, @WebParam(name = "workOrderCode")String workOrderCode)throws BusinessException{
		return coreWOFacade.getCustomerByWoIdOrWoCode(workOrderId, workOrderCode);
	}
	
	
	/**
	 * Metodo: Obtiene la última asignación del dealer a una work order
	 * @param workOrderId identificador de la work order
	 * @return asignación de la work order
	 * @throws BusinessException En caso de error al tratar de obtener una asignaciónd de wo por la workorderId
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<b
	 * @author jjimenezh
	 */
	public WoAssignmentVO getLastWorkOrderAssigmentByWoId(@WebParam(name = "workOrderId")Long workOrderId) throws BusinessException{
		return coreWOFacade.getLastWorkOrderAssigmentByWoId(workOrderId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.ICoreWoWS#getWorkOrderServicesByWoId(java.lang.Long)
	 */
	@Override
	public List<WorkOrderServiceVO> getWorkOrderServicesByWoId(Long workOrderId) throws BusinessException {
		return coreWOFacade.getWorkOrderServicesByWoId(workOrderId);
	}

	@Override
	public Boolean getValidateWorkOrderScheduled(Long workOrderId, Long dealerId) throws BusinessException {		
		return coreWOFacade.getValidateWorkOrderScheduled(workOrderId, dealerId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.wo.ICoreWoWS#agendaWorkOrders(java.util.List)
	 */
	@Override
	public void agendaWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList)throws BusinessException {
		coreWOFacade.agendaWorkOrders(trayWOManagmentDTOList);
	}	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.wo.ICoreWoWS#checkAboveScheduled(co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria, int)
	 */
	@Override
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduled(DealerWorkCapacityCriteria dealerWorkCapacityCriteria, int quantityWorkOrder)throws BusinessException {
		return coreWOFacade.checkAboveScheduled(dealerWorkCapacityCriteria,quantityWorkOrder);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.wo.ICoreWoWS#updateInfoWorkOrderIBSToHSP(java.lang.String, java.lang.String)
	 */
	public void updateInfoWorkOrderIBSToHSP(String countryCode,String woCode) throws BusinessException{
		coreWOFacade.updateInfoWorkOrderIBSToHSP(countryCode,woCode);
	}

	//REQ001 - WO Canceladas.
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.wo.ICoreWoWS#getCanceledWorkOrdersByDealerId(java.lang.Long)
	 */
	@Override
	public List<WorkOrderVO> getCanceledWorkOrdersByDealerId(Long dealerId) throws BusinessException {
		return coreWOFacade.getCanceledWorkOrdersByDealerId(dealerId);
	}
	
	
}
