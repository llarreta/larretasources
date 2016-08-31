package co.com.directv.sdii.ejb.business.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.CoreWorkOrderServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.impl.ResourceProvisioningServiceBrokerImpl;
import co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.StateMachineWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.WoLoadBusinessLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayManagmentBusinessDelegateLocal;
import co.com.directv.sdii.ejb.business.optimus.WorkOrderMarkOptimusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateDetailResponse;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;
import co.com.directv.sdii.model.dto.ServiceAttendResponseDTO;
import co.com.directv.sdii.model.dto.ServiceAttentionRequestDTO;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.TrayWorkOrderServiceDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.pojo.AttentionStatus;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WoAttentionStatus;
import co.com.directv.sdii.model.pojo.WoManagmentElement;
import co.com.directv.sdii.model.pojo.WoServiceStatus;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.SystemParameterVO;
import co.com.directv.sdii.model.vo.WoAttentionStatusVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.RequiredServiceElementDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.core.AttentionStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoAttentionStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoManagmentElementDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal;
import co.com.directv.sdii.ws.model.dto.EditCustomerWorkOrderDTO;


/**
 * 
  * Para ejecutar el Proceso de negocio de la atención de work order:
  * 1. Usando la maquina de estados: verificar que se puede realizar el cambio de estado de la workorder a ATENDIDA
  * 2. Realizar el cambio de estado de la workOrder a ATENDIDA si el campo finished = false, FINALIZADA si el campo es true
  * 3. Por cada WorkOrderService validar si fué registrado o devuelto y realizar los cambios necesarios en la BD
  * 4. Afectar el inventario con los cambios de registro o devolución.
  * Para cada uno de los servicios:
  * 5. Invocar el caso de uso ADS - 61 Registrar elementos serializados utilizados en la atención del servicio
  * 6. Invocar el caso de uso ADS - 60 Registrar elementos NO serializados utilizados en la atención del servicio
  * 7. Invocar el caso de uso ADS - 63 Registrar Recuperación  de  elementos serializados  donde el cliente
  * 8. Invocar el caso de uso ADS - 64 Registrar Recuperación  de  elementos NO serializados  donde el cliente
  * 9. Si el servicio es instalación: Invocar el servicio de IBS para los servicios que fueron activados con el fin que IBS los active
  * 10. Si el servicio es técnico o cambio de equipo: Invocar el servicio de IBS para cambio de equipos
  * 11. Invocar el caso de uso ADS - 33 Notificar cambio de estado de Work Order a IBS
  * 12. Actualizar la fecha de atención de la Workorder en el sistema.
  * 13. Crear una nueva work order con los servicios que no fueron atendidos
 * @author jvelezmu
 * @author jjimenezh
 */

@Stateless(name="CoreWOAttentionsBusinessLocal",mappedName="ejb/CoreWOAttentionsBusinessLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoreWOAttentionsBusiness extends BusinessBase implements CoreWOAttentionsBusinessLocal {

	private ResourceProvisioningServiceBrokerImpl resourceProvisioningBroker;

	@EJB(name="CoreWOBusinessLocal",beanInterface=CoreWOBusinessLocal.class)
    private CoreWOBusinessLocal coreWoBusiness;

	@EJB(name="WoStatusHistoryDAOLocal", beanInterface=WoStatusHistoryDAOLocal.class)
	private WoStatusHistoryDAOLocal woStatusHistoryDAO;

	@EJB
	private ConfigParametrosBusinessLocal configParametrosBusinessBean;
	
	@EJB
	private ConfigParametrosBusinessLocal configParametrosBusiness;
	
	@EJB(name="CoreWorkOrderServiceBrokerLocal",beanInterface=CoreWorkOrderServiceBrokerLocal.class)
	private CoreWorkOrderServiceBrokerLocal coreWOServiceBroker;
	
	@EJB(name="ManageWorkForceServiceBrokerLocal",beanInterface=ManageWorkForceServiceBrokerLocal.class)
	private ManageWorkForceServiceBrokerLocal manageWorkForceServiceBroker;
	
	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAOBean;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDAO;
	
	@EJB(name="StateMachineWOBusinessLocal", beanInterface=StateMachineWOBusinessLocal.class)
	private StateMachineWOBusinessLocal stateMachine;
	
	@EJB(name="WorkorderReasonDAOLocal", beanInterface=WorkorderReasonDAOLocal.class)
	private WorkorderReasonDAOLocal woReasonDAO;
	
	@EJB(name="WoServiceStatusDAOLocal", beanInterface=WoServiceStatusDAOLocal.class)
	private WoServiceStatusDAOLocal woServiceStatusDao;

	@EJB(name="ServiceDAOLocal", beanInterface=ServiceDAOLocal.class)
	private ServiceDAOLocal serviceDAO;
	
	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal serializedDAO;
	
	@EJB(name="WorkOrderServiceDAOLocal", beanInterface=WorkOrderServiceDAOLocal.class)
	private WorkOrderServiceDAOLocal woServiceDAO;
	
	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal workAssignmentDAOBean;
	
	@EJB(name="WorkOrderAgendaDAOLocal", beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAO;
	
	@EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal elementTypeDAO;
	
	@EJB(name="WoAttentionStatusDAOLocal", beanInterface=WoAttentionStatusDAOLocal.class)
	private WoAttentionStatusDAOLocal woAttentionStatusDao;
	
	@EJB(name="TrayManagmentBusinessDelegateLocal", beanInterface=TrayManagmentBusinessDelegateLocal.class)
	private TrayManagmentBusinessDelegateLocal trayManagmentBusinessDelegate;
	
	@EJB(name="AttentionStatusDAOLocal", beanInterface=AttentionStatusDAOLocal.class)
	private AttentionStatusDAOLocal attentionStatusDao;
	
	@EJB(name="WoManagmentElementDAOLocal", beanInterface=WoManagmentElementDAOLocal.class)
	private WoManagmentElementDAOLocal woManagmentDAO;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDAO;	
	
	@EJB(name="WoLoadBusinessLocal",beanInterface=WoLoadBusinessLocal.class)
	private WoLoadBusinessLocal woLoadBusiness;
	
	@EJB(name="RequiredServiceElementDAOLocal", beanInterface=RequiredServiceElementDAOLocal.class)
	private RequiredServiceElementDAOLocal requiredServiceDao;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal daoCountries;
	
	@EJB(name="EmployeesCrewDAOLocal", beanInterface=EmployeesCrewDAOLocal.class)
	private EmployeesCrewDAOLocal employeesCrewDAO;
	
	@EJB(name="WorkOrderMarkOptimusBusinessBeanLocal", beanInterface=WorkOrderMarkOptimusBusinessBeanLocal.class)
	private WorkOrderMarkOptimusBusinessBeanLocal workOrderMarkOptimusBusinessBean;
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreWOBusiness.class);
	private final static int INIT_MESSAGE_WO_ATTENTIONS = 0;
	private final static int END_MESSAGE_WO_ATTENTIONS = 490;

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#validarDatosWOAttentions(co.com.directv.sdii.ws.model.dto.WOAttentionsRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateDataBusinessWOAttentions(WOAttentionsRequestDTO request) throws BusinessException {
		
		log.debug("== Inicia validarDatosWOAttentions/CoreWOAttentionsBusiness ==");
		try {
			this.validateAttentionRequest(request);
			/*
			 * Se realizarán las siguientes validaciones:
			 * 1. Validar que tenga código de edificio
			 * 2. validación de cambio de estado
			 * 3. validar que esté asignada a un dealer
			 * 4. Validar que tenga agenda
			 * 5. Validar que tenga cliente
			 * 6. Validar que tenga cuadrilla asignada
			 * 8. Una Work Order con service de Instalación debe registrar y activar al menos una pareja de Deco y SC
			 * 9. Una Work de Order con service de Recuperación debe devolver al menos una pareja de Deco y SC
			 * 10. Una Work Order para Cambio de Equipo (sea por upgrade o servicio técnico) debe registrar una pareja Deco y SC y devolver una pareja Deco y SC" 
			 */
			//WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID(request.getWorkorderVo().getId());
			//UtilsBusiness.assertNotNull(workOrder, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			//Boolean workOrderFinished = request.getWorkOrderFinished();
			
			//Realizando 1:
			/*
			if(workOrder.getBuildingCode() == null || workOrder.getBuildingCode().trim().length() == 0){
				log.error("La work order que se pretende atender con id: " + workOrder.getId() + " ");
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOESNT_HAS_BUILDING_CODE.getCode(), ErrorBusinessMessages.WORKORDER_DOESNT_HAS_BUILDING_CODE.getMessage());
			}
			*/

			//Realizando 2:
			//String newWorkOrderStatusCode = null;
			//Si se ha seleccionado que la work order se finalizó
			//if(workOrderFinished != null && workOrderFinished.booleanValue()){
				//newWorkOrderStatusCode = CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity();
			//}//Si no se ha finalizado la work order
			//else{
				//newWorkOrderStatusCode = CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity();
			//}
			//WorkorderStatus woStatus = workorderStatusDAO.getWorkorderStatusByCode(newWorkOrderStatusCode);
			//UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());

			//WorkOrderVO workOrderTmp = new WorkOrderVO();
			//workOrderTmp.setWorkorderStatusByActualStatusId(workOrder.getWorkorderStatusByActualStatusId());
			//workOrderTmp.setWorkorderStatusByPreviusStatusId(workOrder.getWorkorderStatusByPreviusStatusId());

			//this.stateMachine.validateStatusChangeWorkOrderByCodes(workOrderTmp, newWorkOrderStatusCode);
			
			//this.validateWoStatusChange(workOrder, newWorkOrderStatusCode);
			
			//Realizando 3:
			//WoAssignment lastAssignment = workAssignmentDAOBean.getLastDealerAssignmentByWoId(workOrder.getId());
			//UtilsBusiness.assertNotNull(lastAssignment, ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
			//UtilsBusiness.assertNotNull(lastAssignment.getDealerId(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage() + " No se ha asignado el dealer a la WO");
			
			//Realizando 6:
			//UtilsBusiness.assertNotNull(lastAssignment.getCrewId(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage() + " No se ha asignado la cuadrilla a la WO");
			
			//Realizando 4:
			//WorkOrderAgenda woAgenda = workOrderAgendaDAO.getLastWorkOrderAgendaByWoID(workOrder.getId());
			//UtilsBusiness.assertNotNull(woAgenda, ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getMessage());
			
			//Realizando 5:
			//UtilsBusiness.assertNotNull(workOrder.getCustomer(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage());
			
			//Validaciones Transversales a la Atencion y la Finalizacion (3,4,5)
			this.validateDataWOAttentionFinalization(request, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
			
			//Realizando 8, 9 y 10
			//this.validateDecosAndSmartCards(request);
			
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validarDatosWOAttentions/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validarDatosWOAttentions/CoreWOAttentionsBusiness ==");
		}
	}

	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateDataWOAttentionFinalization(WOAttentionsRequestDTO request, String changeStatusCode) throws BusinessException {
		try{			
			log.debug("== Inicia validateDataWOAttentionFinalization/CoreWOAttentionsBusiness ==");
			
			//valida que exista la WorkOrder
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID(request.getWorkorderVo().getId());
			UtilsBusiness.assertNotNull(workOrder, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			
			//Valida el cambio de estado de la WorkOrder
			this.validateWoStatusChange(workOrder, changeStatusCode);
			
			//Validar que este asignada a un dealer
			WoAssignment lastAssignment = workAssignmentDAOBean.getLastDealerAssignmentByWoId(workOrder.getId());
			UtilsBusiness.assertNotNull(lastAssignment, ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
			UtilsBusiness.assertNotNull(lastAssignment.getDealerId(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage() + " No se ha asignado el dealer a la WO");
			
			//Validar que tenga Agenda
			WorkOrderAgenda woAgenda = workOrderAgendaDAO.getLastWorkOrderAgendaByWoID(workOrder.getId());
			UtilsBusiness.assertNotNull(woAgenda, ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getMessage());
			
			//Validar que tenga Cliente
			UtilsBusiness.assertNotNull(workOrder.getCustomer(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage());
			
			//Se valida que la relacion entre los servicios y la workorder exista
			//para el caso de atencion de un servicio sin finalizacion.
			for (WorkOrderServiceVO woServiceVO : request.getWorkorderServices()) {
				if(!woServiceVO.getAddService()){
					WorkOrderService workOrderService = woServiceDAO.getWorkOrderServiceByID( woServiceVO.getId() );
					UtilsBusiness.assertNotNull(workOrderService, ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage() + " No se encontró el WorkOrderService con id: "+woServiceVO.getId()+" asociado a la Work order: " + request.getWorkorderVo().getWoCode());
				}
			}
			
			//Valida que la WorkOrder tenga una cuadrilla asociada en la atencion o finalizacion
			UtilsBusiness.assertNotNull(lastAssignment, ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
			Object[] params = null;
			params = new Object[1];
			params[0] = workOrder.getWoCode();
			if( lastAssignment.getCrewId() == null ){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR025.getCode(), ErrorBusinessMessages.CORE_CR025.getMessage(params), UtilsBusiness.getParametersWS(params));
			}else{
				Employee employee = employeesCrewDAO.getEmployeeResponsibleByCrewId(lastAssignment.getCrewId());
				
				SystemParameterVO systemParameterVO= this.configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.SP_VALIDATE_TECHNICAL_WITH_DOCUMENT.getCodeEntity()
						, workOrder.getCountry().getId());
				
				String validateWithDocument=systemParameterVO.getValue();
				
				if(validateWithDocument.equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity())
						&& employee.getIbsTechnical()==null){
					params[0] = employee.getDocumentNumber();
					throw new BusinessException(ErrorBusinessMessages.CORE_CR072.getCode(), ErrorBusinessMessages.CORE_CR072.getMessage(params), UtilsBusiness.getParametersWS(params));
				}
				request.setIbsTechnicalDocument(employee.getDocumentNumber());
				request.setIbsTechnical(employee.getIbsTechnical());
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateDataWOAttentionFinalization/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateDataWOAttentionFinalization/CoreWOAttentionsBusiness ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateDataBusinessWOFinalization(WOAttentionsRequestDTO request) throws BusinessException {
		try{
			log.debug("== Inicia validateDataBusinessWOFinalization/CoreWOAttentionsBusiness ==");	
	
			
			//Valida que la WO exista
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID(request.getWorkorderVo().getId());
			UtilsBusiness.assertNotNull(workOrder, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			
			//Valida que la WO se encuentre Atendida (en estado Realizada)
			WorkOrder workOrderStatus = workOrderDAOBean.getWorkOrderByID(workOrder.getId());
			if(!workOrderStatus.getWorkorderStatusByActualStatusId().getWoStateCode().equals( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity() )){
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_NOT_REALIZED.getCode(), ErrorBusinessMessages.WORKORDER_NOT_REALIZED.getMessage());	
			}
		
			//Validaciones Transversales a la Atencion y la Finalizacion
			this.validateDataWOAttentionFinalization(request, CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateDataBusinessWOFinalization/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateDataBusinessWOFinalization/CoreWOAttentionsBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#atenderService(co.com.directv.sdii.ws.model.dto.ServiceAttentionRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ServiceAttendResponseDTO atenderService(ServiceAttentionRequestDTO request) throws BusinessException {
		log.debug("== Inicia atenderService/CoreWOAttentionsBusiness ==");
		try{
			ServiceAttendResponseDTO response = new ServiceAttendResponseDTO();
			
			//invocacion de la validacion de objetos requeridos para la atencion
			this.validateServiceAttentionRequest(request);
			
			//Si el servicio fue agregado en el proceso de Atencion Y Finalizacion
			//se adiciona el servicio a la Workorder
			if( request.getWorkorderService().getAddService() ){
				WoServiceStatus unAttendedWoServiceStatus = woServiceStatusDao.getWoServiceStatusByCode(CodesBusinessEntityEnum.WO_SERVICE_STATUS_ATTENDED.getCodeEntity());
				WorkOrderService woOrderService = new WorkOrderService();				
				woOrderService.setId(null);
				woOrderService.setService(request.getWorkorderService().getService());
				woOrderService.setWoId( request.getWorkorderVo().getId() );
				woOrderService.setWoServiceStatus( unAttendedWoServiceStatus );
				woOrderService.setServiceInclusionDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO));
				woServiceDAO.createWorkOrderService(woOrderService);		
				
				//se asigna el id de la workorderservice asignada
				request.getWorkorderService().setId(woOrderService.getId());
				response.setWorkOrderServiceId(woOrderService.getId());
			}

			
			//Se consulta el objeto de WorkOrderService para actualizar la atencion del servicio relacionado a la WO.
			WorkOrderService workOrderService = woServiceDAO.getWorkOrderServiceByID( request.getWorkorderService().getId() );
			WorkOrderServiceVO workOrderServiceVO = UtilsBusiness.copyObject(WorkOrderServiceVO.class, workOrderService);
			
			//Obteniendo los estados posibles de la relación de una work order y un servicio
			WoServiceStatus attendedWoServiceStatus = woServiceStatusDao.getWoServiceStatusByCode(CodesBusinessEntityEnum.WO_SERVICE_STATUS_ATTENDED.getCodeEntity());			
			UtilsBusiness.assertNotNull(attendedWoServiceStatus, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			
			workOrderServiceVO.setWoServiceStatus(attendedWoServiceStatus);						
			
			//Actualizando la relación entre la work order y el servicio para asignar el estado atendido o nó
			WorkOrderService woService = UtilsBusiness.copyObject(WorkOrderService.class, workOrderServiceVO);
			woServiceDAO.updateWorkOrderService(woService);
			
			//se encapsula la relacion del WorkOrderService para ser utilizada en el proceso de atencion.
			response.setWoService(woService);
			
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion atenderService/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina atenderService/CoreWOAttentionsBusiness ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ServiceAttendResponseDTO serviceFinalization(ServiceAttentionRequestDTO request) throws BusinessException {
		try {
			log.debug("== Inicia serviceFinalization/CoreWOAttentionsBusiness ==");
			ServiceAttendResponseDTO response = new ServiceAttendResponseDTO();
			
			//Si la WO no fue finalizada en el proceso de atencion se inserta el 
			//el registro ya que el flujo viene directamente de la accion de finalizacion
			if ( !request.getWorkOrderAttendFinished() ) {
				if ( request.getWorkorderService().getAddService() ) {
					WoServiceStatus unAttendedWoServiceStatus = woServiceStatusDao.getWoServiceStatusByCode(CodesBusinessEntityEnum.WO_SERVICE_STATUS_ATTENDED.getCodeEntity());
					WorkOrderService woOrderService = new WorkOrderService();				
					woOrderService.setId(null);
					woOrderService.setService(request.getWorkorderService().getService());
					woOrderService.setWoId( request.getWorkorderVo().getId() );
					woOrderService.setWoServiceStatus( unAttendedWoServiceStatus );
					woOrderService.setServiceInclusionDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO));
					woServiceDAO.createWorkOrderService(woOrderService);		
					
					//se asigna el id de la workorderservice asignada
					request.getWorkorderService().setId(woOrderService.getId());
					response.setWorkOrderServiceId(woOrderService.getId());
				}
			}
			
			//Se consulta el objeto de WorkOrderService para actualizar la finalizacion del servicio relacionado a la WO.
			WorkOrderService workOrderService = woServiceDAO.getWorkOrderServiceByID( request.getWorkorderService().getId() );
			//se encapsula la relacion del WorkOrderService para ser utilizada en el proceso de atencion.
			response.setWoService(workOrderService);			

			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion serviceFinalization/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina serviceFinalization/CoreWOAttentionsBusiness ==");
		}
	}	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#reportarElementosUtilizadosEnServicios(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void reportarElementosUtilizadosEnServicios(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException {
		log.debug("== Inicia reportarElementosUtilizadosEnServicios/CoreWOAttentionsBusiness ==");
		try {
			//proceso de registrar elementos utilizados en la atencion
			User user = userDAO.getUserById( request.getUserId() );
			
			WorkOrderService woService =  woServiceDAO.getWorkOrderServiceByID( request.getWorkOrderServiceId() );
			WoManagmentElement element;
			
			for (WorkOrderServiceElementVO woServiceElementVO : request.getInstallationSerializedElements()) {	
				//Registrar Decodificador			
				element = new WoManagmentElement();
				element.setElementId( woServiceElementVO.getElementTypeId() );
				element.setElementCode( woServiceElementVO.getElementTypeCode() );
				element.setIsSerialized( woServiceElementVO.getIsSerialized() );
				element.setManagmentDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
				element.setSerialCode(woServiceElementVO.getElementSerial());
				element.setDateLastChange( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
				element.setUserLastChange( user.getLogin() );
				element.setLinkedSerialCode( woServiceElementVO.getLinkedWOServiceElement().getElementSerial() );
				element.setWoId( request.getWorkorderVo().getId() );
				element.setServiceId( woService.getService().getId() );
				element.setCodeTypeMovement( codeTypeMovement );
				element.setCodeElementClassType( CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_DECO.getCodeEntity() );
				
				element.setWorkOrderService(new WorkOrderService());
				element.getWorkOrderService().setId(request.getWorkOrderServiceId());
				
				woManagmentDAO.createWoManagmentElement(element);
				
				//Registrar SmartCard			
				element = new WoManagmentElement();
				element.setElementId( woServiceElementVO.getLinkedWOServiceElement().getElementTypeId() );
				element.setElementCode( woServiceElementVO.getLinkedWOServiceElement().getElementTypeCode() );
				element.setIsSerialized( woServiceElementVO.getLinkedWOServiceElement().getIsSerialized() );
				element.setManagmentDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
				element.setSerialCode( woServiceElementVO.getLinkedWOServiceElement().getElementSerial() );
				element.setDateLastChange( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
				element.setUserLastChange( user.getLogin() );
				element.setWoId( request.getWorkorderVo().getId() );
				element.setServiceId( woService.getService().getId() );
				element.setCodeTypeMovement( codeTypeMovement );
				element.setCodeElementClassType( CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_SC.getCodeEntity() );
				
				element.setWorkOrderService(new WorkOrderService());
				element.getWorkOrderService().setId(request.getWorkOrderServiceId());
				
				woManagmentDAO.createWoManagmentElement(element);
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportarElementosUtilizadosEnServicios/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina reportarElementosUtilizadosEnServicios/CoreWOAttentionsBusiness ==");
		}
	}
	
		
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void reportElementsUsedFinalization(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException {
		log.debug("== Inicia reportElementsUsedFinalization/CoreWOAttentionsBusiness ==");
		try {
			//proceso de registrar elementos utilizados en la finalizacion
			User user = userDAO.getUserById( request.getUserId() );
			WorkOrderService woService =  woServiceDAO.getWorkOrderServiceByID( request.getWorkOrderServiceId() );
			WoManagmentElement element;
			
			//Registrar Decodificador			
			element = new WoManagmentElement();
			element.setElementId( request.getWoServiceElementVO().getElementTypeId() );
			element.setElementCode( request.getWoServiceElementVO().getElementTypeCode() );
			element.setIsSerialized( request.getWoServiceElementVO().getIsSerialized() );
			element.setManagmentDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
			element.setDateLastChange( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
			element.setUserLastChange( user.getLogin() );
			element.setQuantity(request.getWoServiceElementVO().getQuantity());
			element.setWoId( request.getWorkorderVo().getId() );
			element.setServiceId( woService.getService().getId() );
			element.setCodeTypeMovement( codeTypeMovement );
			element.setCodeElementClassType( CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_DECO.getCodeEntity() );
			
			element.setWorkOrderService(new WorkOrderService());
			element.getWorkOrderService().setId(request.getWorkOrderServiceId());
			
			woManagmentDAO.createWoManagmentElement(element);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportElementsUsedFinalization/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina reportElementsUsedFinalization/CoreWOAttentionsBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#reportarElementosRecuperadosEnServicios(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void reportarElementosRecuperadosEnServicios(WOAttentionElementsRequestDTO request,String codeTypeMovement) throws BusinessException {
		log.debug("== Inicia reportarElementosRecuperadosEnServicios/CoreWOAttentionsBusiness ==");
		try {

			//proceso de registrar elementos utilizados en la atencion
			User user = userDAO.getUserById( request.getUserId() );
			WorkOrderService woService =  woServiceDAO.getWorkOrderServiceByID( request.getWorkOrderServiceId() );
			WoManagmentElement element;
			
			for (WorkOrderServiceElementVO woServiceElementVO : request.getRecoverySerializedElements()) {
				
				//Registrar Decodificador			
				element = new WoManagmentElement();
				element.setElementId( woServiceElementVO.getElementTypeId() );
				element.setElementCode( woServiceElementVO.getElementTypeCode() );
				element.setIsSerialized( woServiceElementVO.getIsSerialized() );
				element.setManagmentDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
				element.setSerialCode(woServiceElementVO.getElementSerial());
				element.setDateLastChange( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
				element.setUserLastChange( user.getLogin() );
				element.setLinkedSerialCode( woServiceElementVO.getLinkedWOServiceElement().getElementSerial() );
				element.setWoId( request.getWorkorderVo().getId() );
				element.setServiceId( woService.getService().getId() );
				element.setCodeTypeMovement( codeTypeMovement );
				element.setCodeElementClassType( CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_DECO.getCodeEntity() );
				
				element.setWorkOrderService(new WorkOrderService());
				element.getWorkOrderService().setId(request.getWorkOrderServiceId());
				
				woManagmentDAO.createWoManagmentElement(element);
				
				//Registrar SmartCard			
				element = new WoManagmentElement();
				element.setElementId( woServiceElementVO.getLinkedWOServiceElement().getElementTypeId() );
				element.setElementCode( woServiceElementVO.getLinkedWOServiceElement().getElementTypeCode() );
				element.setIsSerialized( woServiceElementVO.getLinkedWOServiceElement().getIsSerialized() );
				element.setManagmentDate( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
				element.setSerialCode( woServiceElementVO.getLinkedWOServiceElement().getElementSerial() );
				element.setDateLastChange( UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO) );
				element.setUserLastChange( user.getLogin() );
				element.setWoId( request.getWorkorderVo().getId() );
				element.setServiceId( woService.getService().getId() );
				element.setCodeTypeMovement( codeTypeMovement );
				element.setCodeElementClassType( CodesBusinessEntityEnum.WO_MANAGMENT_ELEMENT_CLASS_SC.getCodeEntity() );
				
				element.setWorkOrderService(new WorkOrderService());
				element.getWorkOrderService().setId(request.getWorkOrderServiceId());
				
				woManagmentDAO.createWoManagmentElement(element);
			}

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportarElementosRecuperadosEnServicios/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina reportarElementosRecuperadosEnServicios/CoreWOAttentionsBusiness ==");
		}
	}	
	
	/**
	 * Metodo: Cambia el parametro de la WO que indica que la fecha de agendamiento bajo con fecha de agendamiento invaldia
	 * @param workOrderPojo
	 * @author jnova
	 * @throws PropertiesException 
	 */
	private void changeToFalseAgendationExpired(WorkOrder workOrderPojo) throws PropertiesException{
		workOrderPojo.setAgendationExpired( CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity() );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#actualizarCambioDeEstadoWO(co.com.directv.sdii.ws.model.dto.WOAttentionsRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException {

		log.debug("== Inicia actualizarCambioDeEstadoWO/CoreWOAttentionsBusiness ==");
		try{
			//se carga el objeto de la WO para actualizar el estado y generar el historico			
			WorkOrderVO workorderVo = request.getWorkorderVo();
			WorkOrder workOrder = UtilsBusiness.copyObject(WorkOrder.class, workorderVo);
			String comments = request.getComments();
			WorkorderReason woReason = getWoAttentionReason(workOrderDAOBean.getCountryIdOfWorkOrderId(workOrder.getId()));
			
			//Se asigna el previous y actual status para generar el historico y actualizar la WO
			WorkorderStatus newActualStatus = new WorkorderStatus();
			newActualStatus.setId( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getIdEntity(WorkorderStatus.class.getName()) );
			 
			//Se crea el histrico y el Contact
			//jnova 10-10-2011 se pone en nulo el process source porque se gestiona por HSP
			workOrder.setProcessSourceId(null);
			changeToFalseAgendationExpired(workOrder);
			this.changeWorkOrderHistoryAndContact(workOrder, newActualStatus, comments, woReason, request.getUserId());
			 	   		
			//Si la WO fue Atendida y Finalizada no se actualiza el estado a Atendida
			//puesto que se registra el cambio a finalizada.
			if(!request.getWorkOrderFinished()){
		   		 Date woRealizationDate = UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO);

		   		 workOrder.setWoRealizationDate(woRealizationDate);		   		 
		   		 WorkorderStatus previousStatus = new WorkorderStatus();
		   		 previousStatus.setId( workOrder.getWorkorderStatusByActualStatusId().getId() );	   		
		   		 workOrder.setWorkorderStatusByActualStatusId(newActualStatus);
		   		 workOrder.setWorkorderStatusByPreviusStatusId(previousStatus);
		   		 
		   		 //Se actualiza la WO con la informacion de la Atencion
		   		 workOrderDAOBean.updateWorkOrder(workOrder,CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE.getCodeEntity());
			}
			
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(workOrder);
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(workOrder, request.getUserId());
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion actualizarCambioDeEstadoWO/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina actualizarCambioDeEstadoWO/CoreWOAttentionsBusiness ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOAttentionsRequestDTO stateChangeUpdateWOFinalization (WOAttentionsRequestDTO request) throws BusinessException {
		log.debug("== Inicia stateChangeUpdateWOFinalization/CoreWOAttentionsBusiness ==");
		try{			
			//se carga el objeto de la WO para actualizar el estado y generar el historico
			WorkOrderVO workorderVo = request.getWorkorderVo();
			WorkOrder workOrder = UtilsBusiness.copyObject(WorkOrder.class, workorderVo);
			String comments = request.getComments();
			WorkorderReason woReason = this.getFinalizationReason(workOrderDAOBean.getCountryIdOfWorkOrderId(request.getWorkorderVo().getId()));
			
			//Se asigna el previous y actual status para generar el historico y actualizar la WO
			WorkorderStatus newActualStatus = new WorkorderStatus();
			newActualStatus.setId( CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getIdEntity(WorkorderStatus.class.getName()) );
			WorkorderStatus previousStatus = new WorkorderStatus();
	   		previousStatus.setId( workOrder.getWorkorderStatusByActualStatusId().getId() );	   	
	   		
	   		//Se crea el histrico y el Contact
	   		//jnova 10-10-2011 se pone en nulo el process source porque se gestiona por HSP
			workOrder.setProcessSourceId(null);
			this.changeToFalseAgendationExpired(workOrder);
			this.changeWorkOrderHistoryAndContact(workOrder, newActualStatus, comments, woReason, request.getUserId());
			 
			//Se calacula el tiempo de trabajo de la WorkOrder
			Date woStartDate = workOrder.getCreationDate();
	   		Date woFinishDate = UtilsBusiness.getCurrentTimeZoneDateByUserId( request.getUserId(), userDAO);
	   		double workingTime = woFinishDate.getTime() - woStartDate.getTime();
	   		//Convirtiendo el tiempo de trabajo a horas: 
	   		workingTime = ((workingTime /1000)/60)/60;
	   		 
			//Si la WO fue Atendida y finalizada se registra la informacion
			//de la atencion
			if( request.getWorkOrderFinished() ){	   		 
	   		 workOrder.setWoRealizationDate( woFinishDate );
	   		 previousStatus.setId( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getIdEntity(WorkorderStatus.class.getName()) );
			}
			
	   		//Se actualiza la WO con la informacion de la Finalizacion
			workOrder.setWorkingTime(workingTime);
	   		workOrder.setWorkorderStatusByActualStatusId(newActualStatus);
	   		workOrder.setWorkorderStatusByPreviusStatusId(previousStatus);
	   		workOrder.setFinalizationDate( woFinishDate  );
	   		workOrderDAOBean.updateWorkOrder(workOrder,CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE.getCodeEntity());
	   		
	   		//Se almacena la informacion necesaria para continuar el proceso, la cual
	   		//es calcula o es generada en esta operacion
	   		//Se almacena el tiempo de trabajo de la WorkOrder desde la creacion hasta la finalizacion	   		
	   		//se almacena la reason de la finalizacion
	   		request.setWorkingTime( workingTime );
	   		request.setWoReasonFinalization( woReason );
	   		request.setFinalizationDate( woFinishDate );
	   		
	   		workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(workOrder);
	   		workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(workOrder, request.getUserId());
	   		
	   		return request;
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion stateChangeUpdateWOFinalization/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina stateChangeUpdateWOFinalization/CoreWOAttentionsBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#notificarIbsCambioDeEstadoWO(co.com.directv.sdii.ws.model.dto.WOAttentionsRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void notificarIbsCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException {
		log.debug("== Inicia notificarIbsCambioDeEstadoWO/CoreWOAttentionsBusiness ==");
		try{			
			//Se crea el objeto que se pasara al broker
			WorkOrderVO workorderVo = request.getWorkorderVo();
//			WorkOrder workOrder = new WorkOrder();
//			workOrder.setWoCode(workorderVo.getWoCode());
//			workOrder.setCountry(workorderVo.getCountry());
			
//			//Se asigna el estado finalizado para actualizarlo en IBS
//			String newWorkOrderStatusCode = CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity();
//			WorkorderStatus woStatus = workorderStatusDAO.getWorkorderStatusByCode(newWorkOrderStatusCode);
//			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage() +" No se encontró el estado de WO con código: " + newWorkOrderStatusCode);
//			workOrder.setWorkorderStatusByActualStatusId(woStatus);
			
			//Se obtiene la reason	
			WorkorderReason woReason = getWoAttentionReason(workorderVo.getCountry().getId());
			
//			//Se encapsula la informacion en un DTO para pasarlo al broker
//			WorkOrderDTO woDTO = new WorkOrderDTO();
//			woDTO.setWorkOrder(workOrder);
			
			//Notificando a IBS el cambio de estado
			//this.coreWOServiceBroker.updateWorkOrderStatus(woDTO, woReason);

			EditCustomerWorkOrderDTO editCustomerWorkOrderDTO = new EditCustomerWorkOrderDTO(workorderVo.getWoCode(),
					                                                                         workorderVo.getCountry().getCountryCode(),
					                                                                         null,
					                                                                         null,
					                                                                         woReason.getWorkorderReasonCode(),
					                                                                         null); 
			manageWorkForceServiceBroker.editCustomerWorkOrder(editCustomerWorkOrderDTO);

			SystemParameter reasonAttentionTechnician = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.CODE_REASON_ATTENTION_TECHNICIAN.getCodeEntity(), workorderVo.getCountry().getId());
			
			String reasonAttentionTechnicianString = reasonAttentionTechnician.getValue();
			
			EditCustomerWorkOrderDTO editCustomerWorkOrderTechnicalDTO = new EditCustomerWorkOrderDTO(workorderVo.getWoCode(),
                    workorderVo.getCountry().getCountryCode(),
                    null,
                    request.getIbsTechnicalDocument(),request.getIbsTechnical(),
                    reasonAttentionTechnicianString,
                    null); 
			String ibsHistoryEventCode=manageWorkForceServiceBroker.editCustomerWorkOrder(editCustomerWorkOrderTechnicalDTO);
			
			WorkorderStatus woStatusAtten=null;
			if(request.getWorkOrderFinished()){
				woStatusAtten = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
			}
			else{
				woStatusAtten = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
			}

			WoStatusHistory lastWoStatusHistory=woStatusHistoryDAO.getLastWoStatusHistoryByWoIdAndWoStatus(workorderVo.getId(), woStatusAtten.getId());
			
			if(lastWoStatusHistory!=null){
				this.coreWoBusiness.createWoStatusHistoryHSP(workorderVo, lastWoStatusHistory.getWorkorderReason(), lastWoStatusHistory.getDescription(), woStatusAtten,ibsHistoryEventCode, request.getUserId());
			}
			else{
				this.coreWoBusiness.createWoStatusHistoryHSP(workorderVo, null, null, woStatusAtten ,ibsHistoryEventCode, request.getUserId());
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion notificarIbsCambioDeEstadoWO/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina notificarIbsCambioDeEstadoWO/CoreWOAttentionsBusiness ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void notifyStatusChangeFinalizationIBS(WOAttentionsRequestDTO request) throws BusinessException {
		try{			
			log.debug("== Inicia notifyStatusChangeFinalizationIBS/CoreWOAttentionsBusiness ==");
			//Se crea el objeto que se pasara al broker
			WorkOrderVO workorderVo = request.getWorkorderVo();
//			WorkOrder workOrder = new WorkOrder();
//			workOrder.setWoCode(workorderVo.getWoCode());
//			workOrder.setCountry(workorderVo.getCountry());
			
			//Se asigna el estado finalizado para actualizarlo en IBS
//			String newWorkOrderStatusCode = CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity();
//			WorkorderStatus woStatus = workorderStatusDAO.getWorkorderStatusByCode(newWorkOrderStatusCode);
//			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage() +" No se encontró el estado de WO con código: " + newWorkOrderStatusCode);
//			workOrder.setWorkorderStatusByActualStatusId(woStatus);
			
			//Se obtiene la reason	
			WorkorderReason woReason = this.getFinalizationReason(workorderVo.getCountry().getId());
			
			//Se encapsula la informacion en un DTO para pasarlo al broker
//			WorkOrderDTO woDTO = new WorkOrderDTO();
//			woDTO.setWorkOrder(workOrder);
			
			//Notificando a IBS el cambio de estado
			//this.coreWOServiceBroker.updateWorkOrderStatus(woDTO, woReason);
			EditCustomerWorkOrderDTO editCustomerWorkOrderDTO = new EditCustomerWorkOrderDTO(workorderVo.getWoCode(),
																		                     workorderVo.getCountry().getCountryCode(),
																		                     null,
																		                     null,
																		                     woReason.getWorkorderReasonCode(),
																		                     null);
			manageWorkForceServiceBroker.editCustomerWorkOrder(editCustomerWorkOrderDTO);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion notifyStatusChangeFinalizationIBS/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina notifyStatusChangeFinalizationIBS/CoreWOAttentionsBusiness ==");
		}
	}
		
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addingServicesReport(WorkOrderServiceVO workorderService, Long woId) throws BusinessException {
		log.debug("== Inicia addingServicesReport/CoreWOAttentionsBusiness ==");
		try {
			//Si el servicio se agregó se debe notificar a IBS que se agrega el servicio a la work order
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID(woId);			
			//for (WorkOrderServiceVO woServiceVO : workorderServices) {		
				if ( workorderService.getAddService() ) {
					//if(woServiceVO.getId() == null || woServiceVO.getId().longValue() == 0){
						String serialCode = "";	
						String ibsServiceKey = manageWorkForceServiceBroker.addServiceToWorkOrder(workOrder.getWoCode(), workorderService.getService().getServiceIbsCode(), serialCode, workOrder.getCountry().getCountryCode());
						WorkOrderService woService = woServiceDAO.getWorkOrderServiceByID(workorderService.getId());
						woService.setIbsServiceKey(ibsServiceKey);
						woServiceDAO.updateWorkOrderService(woService);
						//coreWOServiceBroker.addServiceToWorkOrder(workOrder.getWoCode(), workorderService.getService().getServiceIbsCode(), serialCode, workOrder.getCountry().getCountryCode());
					//}
				}
			//}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion addingServicesReport/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina addingServicesReport/CoreWOAttentionsBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#reportWoAttentionStatus(co.com.directv.sdii.model.vo.WoAttentionStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void reportWoAttentionStatus(WoAttentionStatusVO woAttentionStatus)throws BusinessException {
		log.debug("== Inicia reportWoAttentionStatus/CoreWOAttentionsBusiness ==");
		try {
			//se obtienen solo 450 para que no genere error el motor al intentar insertar un tamaño mayor.
			if (woAttentionStatus.getMessage() != null && !woAttentionStatus.getMessage().isEmpty()) {
				if (woAttentionStatus.getMessage().length() > END_MESSAGE_WO_ATTENTIONS) {
					String message = woAttentionStatus.getMessage().substring(INIT_MESSAGE_WO_ATTENTIONS, END_MESSAGE_WO_ATTENTIONS);
					woAttentionStatus.setMessage(message);
				}
			}
			WoAttentionStatus woAttePojo = UtilsBusiness.copyObject(WoAttentionStatus.class, woAttentionStatus);			
			woAttentionStatusDao.createWoAttentionStatus(woAttePojo);
			woAttentionStatus.setId(woAttePojo.getId());
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportWoAttentionStatus/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina reportWoAttentionStatus/CoreWOAttentionsBusiness ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public AttentionStatus getAttentionStatus(String woAttentionStatusCode) throws BusinessException {
		log.debug("== Inicia reportWoAttentionStatus/CoreWOAttentionsBusiness ==");
		try{
			AttentionStatus attStatus = attentionStatusDao.getAttentionStatusByCode(woAttentionStatusCode);
			return attStatus;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion reportWoAttentionStatus/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina reportWoAttentionStatus/CoreWOAttentionsBusiness ==");
		}
	}
	
	
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public void validateExistenceElements(Long dealerId, List<WorkOrderServiceVO> woServices) throws BusinessException {
//		log.debug("== Inicia validateExistenceElements/CoreWOAttentionsBusiness ==");
//		try{			
//			for (WorkOrderServiceVO serviceVO : woServices) {
//				//consulta el servicio asociado en la WO para realizar las respectivas validaciones
//				Service service = serviceDAO.getServiceByID( serviceVO.getService().getId() );
//				
//				//Permite Registrar Elementos, se debe validar que exista el elmento a instalar en la Bodega de la Cuadrilla
//				if( service.getAllowRecordingElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ){
//					for (WorkOrderServiceElementVO workOrderServiceElementVO : serviceVO.getWoServiceElements()) {						
//						trayBusiness.getElementBySerialAndDealerId(workOrderServiceElementVO.getElementSerial(), dealerId);				
//					}
//				}
//				//Permite Cambio de Elemento, se debe validar que exista el elmento a instalar en la Bodega de la Cuadrilla
//				if( service.getAllowChangeElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ){
//					for (WorkOrderServiceElementVO workOrderServiceElementVO : serviceVO.getWoServiceElements()) {						
//						trayBusiness.getElementBySerialAndDealerId(workOrderServiceElementVO.getElementSerial(), dealerId);				
//					}
//				}				
//			}
//		} catch (Throwable ex) {
//			log.debug("== Error al tratar de ejecutar la operacion validateExistenceElements/CoreWOAttentionsBusiness");
//			throw super.manageException(ex);
//		} finally {
//			log.debug("== Termina validateExistenceElements/CoreWOAttentionsBusiness ==");
//		}
//	}
	
	/**
	 * Metodo: Realiza la validación de la petición a los WS de atención a la work order
	 * @param request petición que se recibe y que será validada
	 * @throws BusinessException En caso de error al realizar la validación
	 * @author jjimenezh
	 */
	private void validateAttentionRequest(WOAttentionsRequestDTO request) throws BusinessException {
		UtilsBusiness.assertNotNull(request, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getWorkorderVo(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getWorkorderVo().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getWorkorderServices(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}
	
	/**
	 * 
	 * Metodo: Retorna la Wo reason para la atencion
	 * @param countryId
	 * @return WorkorderReason
	 * @throws PropertiesException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author jalopez
	 */
	private WorkorderReason getWoAttentionReason(Long countryId) throws PropertiesException, DAOServiceException, DAOSQLException, BusinessException {
		String workOrderReasonCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_REALIZED.getCodeEntity(), countryId, systemParameterDAO);
		WorkorderReason woReason = woReasonDAO.getWorkorderReasonByCode(workOrderReasonCode);
		UtilsBusiness.assertNotNull(woReason,ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getMessage());
		return woReason;
	}
	
	/**
	 * 
	 * Metodo: Retorna la Wo reason para la finalizacion
	 * @param countryId
	 * @return WorkorderReason
	 * @throws PropertiesException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @author jalopez
	 */
	private WorkorderReason getFinalizationReason(Long countryId) throws PropertiesException, DAOServiceException, DAOSQLException, BusinessException {
		String workOrderReasonCode = UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_FINISH.getCodeEntity(), countryId, systemParameterDAO);
		WorkorderReason woReason = woReasonDAO.getWorkorderReasonByCode(workOrderReasonCode);
		UtilsBusiness.assertNotNull(woReason,ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getMessage());
		return woReason;
	}
	
	/**
	 * Realiza las siguientes validaciones:
	 * Una Work Order con service de Instalación debe registrar y activar al menos una pareja de Deco y SC
	 * Una Work de Order con service de Recuperación debe devolver al menos una pareja de Deco y SC
	 * Una Work Order para Cambio de Equipo (sea por upgrade o servicio técnico) debe registrar una pareja Deco y SC y devolver una pareja Deco y SC"
	 * @param request información de la atención de la WO
	 * @throws PropertiesException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	@Deprecated
	private void validateDecosAndSmartCards(WOAttentionsRequestDTO request,Long countryId) throws PropertiesException, DAOServiceException, DAOSQLException, BusinessException {
		String installServiceTypeCode = CodesBusinessEntityEnum.SERVICE_TYPE_INSTALL.getCodeEntity();
		String recoveryServiceTypeCode = CodesBusinessEntityEnum.SERVICE_TYPE_RECOVERY.getCodeEntity();
		String serviceServiceTypeCode = CodesBusinessEntityEnum.SERVICE_TYPE_SERVICE.getCodeEntity();
		
		List<WorkOrderServiceVO> woServices = request.getWorkorderServices();
		
		String serviceTypeCode;
		WorkOrderService woServicePojo;
		
		
		for (WorkOrderServiceVO workOrderServiceVO : woServices) {
			//consulta el servicio asociado en la WO para realizar las respectivas validaciones
			Service service = serviceDAO.getServiceByID( workOrderServiceVO.getService().getId() );
			
			woServicePojo = woServiceDAO.getWorkOrderServiceByID(workOrderServiceVO.getId());
			UtilsBusiness.assertNotNull(woServicePojo, ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage() + " No se encontró el WorkOrderService con id: "+workOrderServiceVO.getId()+" asociado a la Work order con id: " + request.getWorkorderVo().getId());
			serviceTypeCode = woServicePojo.getService().getServiceCategory().getServiceType().getServiceTypeCode();
			
			//Realizando 8. Una Work Order con service de Instalación debe registrar y activar al menos una pareja de Deco y SC
			if(installServiceTypeCode.equalsIgnoreCase(serviceTypeCode)){
				this.existACoupleDecoSC(workOrderServiceVO, service,countryId);
			//Realizando 9. Una Work de Order con service de Recuperación debe devolver al menos una pareja de Deco y SC
			}else if(recoveryServiceTypeCode.equalsIgnoreCase(serviceTypeCode)){
				this.existACoupleDecoSC(workOrderServiceVO, service,countryId);
			//Realizando 10. Una Work Order para Cambio de Equipo (sea por upgrade o servicio técnico) debe registrar una pareja Deco y SC y devolver una pareja Deco y SC
			}else if(serviceServiceTypeCode.equalsIgnoreCase(serviceTypeCode)){
				this.existACoupleDecoSC(workOrderServiceVO, service,countryId);
			}
		}
	}
	
	/**
	 * Metodo: Invoca el componente de negocio para validar
	 * el cambio de estado de la Workorder.
	 * @param workOrder
	 * @param workOrderStatusCode
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void validateWoStatusChange(WorkOrder workOrder, String workOrderStatusCode) throws BusinessException {
		try{			
			log.debug("== Inicia validateWoStatusChange/CoreWOAttentionsBusiness ==");
			
			WorkorderStatus woStatus = workorderStatusDAO.getWorkorderStatusByCode(workOrderStatusCode);
			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());

			WorkOrderVO workOrderTmp = new WorkOrderVO();
			workOrderTmp.setWorkorderStatusByActualStatusId(workOrder.getWorkorderStatusByActualStatusId());
			workOrderTmp.setWorkorderStatusByPreviusStatusId(workOrder.getWorkorderStatusByPreviusStatusId());

			this.stateMachine.validateStatusChangeWorkOrderByCodes(workOrderTmp, workOrderStatusCode);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateWoStatusChange/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateWoStatusChange/CoreWOAttentionsBusiness ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Validacion de Objetos requeridos para la atencion
	 * @param request
	 * @throws BusinessException
	 * @author jalopez
	 */
	private void validateServiceAttentionRequest(ServiceAttentionRequestDTO request) throws BusinessException {
		UtilsBusiness.assertNotNull(request, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getWorkorderVo(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getWorkorderVo().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getWorkorderService(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}
		
	/**
	 * 
	 * Metodo: validacion de objetos requeridos
	 * para la recuperacion de elementos en el 
	 * proceso de Atencion.
	 * @param request WOAttentionElementsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	private void validateReportRecoveryElementsRequest(WOAttentionElementsRequestDTO request) throws BusinessException {
		UtilsBusiness.assertNotNull(request, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());		
		UtilsBusiness.assertNotNull(request.getInstallationSerializedElements(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		UtilsBusiness.assertNotNull(request.getWorkorderVo(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(request.getWorkorderVo().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}
	
	/**
	 * 
	 * Metodo: validacion de objetos requeridos
	 * para la recuperacion de elementos en el 
	 * proceso de Atencion y/o finalizacion.
	 * @param request WOAttentionElementsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	private void validateReportRecoveryElementsRequestSerialized(WOAttentionElementsRequestDTO request) throws BusinessException {
		UtilsBusiness.assertNotNull(request.getInstallationNotSerializedElements(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}
	
	/**
	 * Metodo: Realiza las validaciones de los elementos
	 * serializados empleados en la atencion de la WorkOrder
	 * @param workOrderServiceVO
	 * @param service
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author jalopez
	 */
	@Deprecated
	private void existACoupleDecoSC(WorkOrderServiceVO workOrderServiceVO, Service service, Long countryId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
				
		Long elementType = null;
		String elementSerial  = null;
		String linkedSerial  = null;
		Long linkedElementType  = null;
		
		//Si el servicio permite cambio de elementos, se valida que venga el Decodificador y su SmartCard al igual que los recuperados
		if( service.getAllowChangeElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ){
			//Los elementos instalados son requeridos
			UtilsBusiness.assertNotNull(workOrderServiceVO.getWoServiceElements(),ErrorBusinessMessages.CORE_CR015.getCode(),ErrorBusinessMessages.CORE_CR015.getMessage());
			//Los elementos recuperados son requeridos
			UtilsBusiness.assertNotNull(workOrderServiceVO.getWoServiceElementsRecovery(),ErrorBusinessMessages.CORE_CR015.getCode(),ErrorBusinessMessages.CORE_CR015.getMessage());
			
			//Se  realiza la validacion de los elementos instalados
			for (WorkOrderServiceElementVO workOrderServiceElementVO : workOrderServiceVO.getWoServiceElements()) {
				//elemento empleado en la atencion
				elementType = workOrderServiceElementVO.getElementTypeId();		
				elementSerial = workOrderServiceElementVO.getElementSerial();
				linkedSerial = workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial();
				linkedElementType = workOrderServiceElementVO.getLinkedWOServiceElement().getElementTypeId();
				this.validateRegisterElements(elementType, elementSerial, linkedSerial, linkedElementType,countryId);							
			}
			
			//Se realiza la validacion de los elementos recuperados
			for (WorkOrderServiceElementVO workOrderServiceElementVO : workOrderServiceVO.getWoServiceElementsRecovery()) {
				//elemento empleado en la atencion
				elementType = workOrderServiceElementVO.getElementTypeId();		
				elementSerial = workOrderServiceElementVO.getElementSerial();
				linkedSerial = workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial();
				linkedElementType = workOrderServiceElementVO.getLinkedWOServiceElement().getElementTypeId();
				this.validateRegisterElements(elementType, elementSerial, linkedSerial, linkedElementType,countryId);
			}
		}
		
		//Si el servicio permite registrar elementos, se valida que venga el Decodificador y su SmartCard
		if( service.getAllowRecordingElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ){
			//Los elementos instalados son requeridos
			UtilsBusiness.assertNotNull(workOrderServiceVO.getWoServiceElements(),ErrorBusinessMessages.CORE_CR016.getCode(),ErrorBusinessMessages.CORE_CR016.getMessage());
			
			for (WorkOrderServiceElementVO workOrderServiceElementVO : workOrderServiceVO.getWoServiceElements()) {
				//elemento empleado en la atencion
				elementType = workOrderServiceElementVO.getElementTypeId();		
				elementSerial = workOrderServiceElementVO.getElementSerial();
				linkedSerial = workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial();
				linkedElementType = workOrderServiceElementVO.getLinkedWOServiceElement().getElementTypeId();
				this.validateRegisterElements(elementType, elementSerial, linkedSerial, linkedElementType,countryId);			
			}
		}
		
		//Si el servicio permite recuperar elementos se debe validar que venga el Decodificar y la SmartCard
		if( service.getAllowRetrieveElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ){
			//Los elementos recuperados son requeridos
			UtilsBusiness.assertNotNull(workOrderServiceVO.getWoServiceElementsRecovery(),ErrorBusinessMessages.CORE_CR016.getCode(),ErrorBusinessMessages.CORE_CR016.getMessage());
			
			//Se realiza la validacion de los elementos recuperados
			for (WorkOrderServiceElementVO workOrderServiceElementVO : workOrderServiceVO.getWoServiceElementsRecovery()) {
				//elemento empleado en la atencion
				elementType = workOrderServiceElementVO.getElementTypeId();		
				elementSerial = workOrderServiceElementVO.getElementSerial();
				linkedSerial = workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial();
				linkedElementType = workOrderServiceElementVO.getLinkedWOServiceElement().getElementTypeId();
				this.validateRegisterElements(elementType, elementSerial, linkedSerial, linkedElementType,countryId);
			}
		}
	}
	
	/**
	 * 
	 * Metodo: Realiza la validacion de los elentos empleados en la
	 * atencion del servicio.
	 * @param elementType Long
	 * @param elementSerial String
	 * @param linkedSerial String
	 * @param linkedElementType Long
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @author jalopez
	 */
	@Deprecated
	public void validateRegisterElements( Long elementType, String elementSerial, String linkedSerial, Long linkedElementType,Long countryId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
		
		Serialized serializedElement, linkedElement;
		Object[] params = null;
		
		//Obteniendo el identificador del tipo de elemento decodificador
		ElementType decoderElementType = elementTypeDAO.getElementTypeByCode(CodesBusinessEntityEnum.DECODIFICADOR_ELEMENT_TYPE.getCodeEntity());
		UtilsBusiness.assertNotNull(decoderElementType, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage() + " No se encontró el tipo de elemento decodificador con el código " + CodesBusinessEntityEnum.DECODIFICADOR_ELEMENT_TYPE.getCodeEntity() + " configurado en el archivo de propiedades CodesBusinessEntity bajo la llave: \"sdii_CODE_decodificador_element\"");
		Long decoderElementTypeId = decoderElementType.getId();
		
		//Obteniendo el identificador del tipo de elemento smart card
		ElementType smartCardElementType = elementTypeDAO.getElementTypeByCode(CodesBusinessEntityEnum.TARJETA_ELEMENT_TYPE.getCodeEntity());
		UtilsBusiness.assertNotNull(smartCardElementType, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage() + " No se encontró el tipo de elemento smart card con el código " + CodesBusinessEntityEnum.DECODIFICADOR_ELEMENT_TYPE.getCodeEntity() + " configurado en el archivo de propiedades CodesBusinessEntity bajo la llave: \"sdii_CODE_tarjeta_element\"");
		Long smartCardElementTypeId = smartCardElementType.getId();
		
		//Se valida la informacion del Decodificador
		UtilsBusiness.assertNotNull(elementType, ErrorBusinessMessages.CORE_CR017.getCode(), ErrorBusinessMessages.CORE_CR017.getMessage());
		UtilsBusiness.assertNotNull(elementSerial, ErrorBusinessMessages.CORE_CR017.getCode(), ErrorBusinessMessages.CORE_CR017.getMessage());
		
		if(decoderElementTypeId.longValue() != elementType.longValue()){
			throw new BusinessException(ErrorBusinessMessages.CORE_CR021.getCode(), ErrorBusinessMessages.CORE_CR021.getMessage());	
		}
		if(decoderElementTypeId.longValue() == elementType.longValue()){
			//Se valida que se envie la Smartcard vinculada al Decodificador
			UtilsBusiness.assertNotNull(linkedElementType, ErrorBusinessMessages.CORE_CR017.getCode(), ErrorBusinessMessages.CORE_CR017.getMessage());			
			UtilsBusiness.assertNotNull(linkedSerial, ErrorBusinessMessages.CORE_CR017.getCode(), ErrorBusinessMessages.CORE_CR017.getMessage());
			
			if(linkedElementType.longValue() != smartCardElementTypeId.longValue()){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR018.getCode(), ErrorBusinessMessages.CORE_CR018.getMessage());	
			}					
		}
		//se realiza la validacion de la existencia del Decodificador
		serializedElement = serializedDAO.getSerializedBySerial( elementSerial,countryId );
		params = new Object[2];
		params[0] = "Decodificador";
		params[1] = elementSerial;
		UtilsBusiness.assertNotNull(serializedElement, ErrorBusinessMessages.CORE_CR019.getCode(), ErrorBusinessMessages.CORE_CR019.getMessage(params));
		
		//se realiza la validacion de la existencia de la SmartCard
		linkedElement = serializedDAO.getSerializedBySerial( linkedSerial,countryId );
		params = new Object[2];
		params[0] = "SmartCard";
		params[1] = linkedSerial;
		UtilsBusiness.assertNotNull(linkedElement, ErrorBusinessMessages.CORE_CR019.getCode(), ErrorBusinessMessages.CORE_CR019.getMessage(params));
		
		//se valida que el decodificador tenga elmento vinculado, es decir que tenga una SmartCard
		params = new Object[1];
		params[0] =elementSerial;
		UtilsBusiness.assertNotNull(serializedElement.getSerialized(),ErrorBusinessMessages.CORE_CR024.getCode(),ErrorBusinessMessages.CORE_CR024.getMessage(params));
		
		//se valida que el serial de la smartcard empleada en la atencion sea el elmento vinculado al decodificador.
		if( !serializedElement.getSerialized().getSerialCode().equalsIgnoreCase( linkedSerial ) ){
			params = new Object[2];
			params[0] = linkedSerial;
			params[1] = elementSerial;
			throw new BusinessException(ErrorBusinessMessages.CORE_CR020.getCode(), ErrorBusinessMessages.CORE_CR020.getMessage(params));	
		}
	}	
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateDataWOFinalization(TrayWOManagmentDTO request) throws BusinessException {
		try{
			log.debug("== Inicia validateDataWOFinalization/TrayWorkOrderManagmentBusinessBean ==");	
			Object[] params = null;
			params = new Object[2];		
			params[1] = "validateDataWOFinalization";
			
			//validacion de objetos transversales al proceso de atencion y/o finalizacion
			this.validateDataWOAttentionFinalization(request);
			
			//Validaciones de datos requeridos propios de la finalizacion
			params[0] = "WorkOrderTypeCode";
			UtilsBusiness.validateRequestResponseWebService(params, request.getWorkOrderTypeCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "CustomerAddress";
			UtilsBusiness.validateRequestResponseWebService(params, request.getCustomerAddress(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
						
			UtilsBusiness.validateRequestResponseWebService(params, request.getFinalizationComments(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			
			for (TrayWorkOrderServiceDTO serviceDTO : request.getTrayWOServiceDTO()) {			
			
				//validacion de los datos requeridos del servicio
				this.validateDataServiceWOAttentionFinalization(serviceDTO,serviceDTO.getAddService());
				
				//consulta el servicio asociado en la WO para realizar las respectivas validaciones
				Service service = serviceDAO.getServiceByID( serviceDTO.getTrayServiceDTO().getServiceId() );
				
				//Si el servicio permite registrar elementos, se valida que vengan los elementos no serializados empleados en la atencion
				//if( service.getAllowRecordingElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ){
					
					//UtilsBusiness.assertNotNull( serviceDTO.getWoServiceElements(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
					//se valida que se hayan registrado elementos en la finalizacion, puesto que no es requerido que se ingresen
					if(serviceDTO.getWoServiceElements() != null && !serviceDTO.getWoServiceElements().isEmpty()){
						for (WorkOrderServiceElementVO serviceElement : serviceDTO.getWoServiceElements()) {						
							
							//validacion de datos requeridos del elemento
							this.validateDataElementsAttentionFinalization(serviceElement);
							if( serviceElement.getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity() ) ){
								params[0] = "Quantity";
								UtilsBusiness.validateRequestResponseWebService(params, serviceElement.getQuantity(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
							}
						}
					}
			}			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateDataWOFinalization/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateDataWOFinalization/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateDataWOAttention(TrayWOManagmentDTO request) throws BusinessException {
		try {
			log.debug("== Inicia validateDataWOAttention/TrayWorkOrderManagmentBusinessBean ==");
			
			Object[] params = null;
			params = new Object[2];
			params[1] = "validateDataWOAttention";
			
			//validacion de objetos transversales al proceso de atencion y/o finalizacion
			this.validateDataWOAttentionFinalization(request);
			
			params[0] = "AttentionComments";
		 	UtilsBusiness.validateRequestResponseWebService(params, request.getAttentionComments(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			
			for (TrayWorkOrderServiceDTO serviceDTO : request.getTrayWOServiceDTO()) {

				//validacion de los datos requeridos del servicio
				this.validateDataServiceWOAttentionFinalization(serviceDTO, serviceDTO.getAddService());				
				
				//consulta el servicio asociado en la WO para realizar las respectivas validaciones
				Service service = serviceDAO.getServiceByID( serviceDTO.getTrayServiceDTO().getServiceId() );
				
				if ( !service.getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_SERVICE.getCodeEntity()) ) { 
						
					//Si el servicio permite registrar elementos, se valida que vengan los elementos serializados empleados en la atencion
					if ( service.getAllowRecordingElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ) {
						
						params[0] = "WoServiceElements";
						UtilsBusiness.validateRequestResponseWebService(params, serviceDTO.getWoServiceElements(), ErrorBusinessMessages.CORE_CR016.getCode(), ErrorBusinessMessages.CORE_CR016.getMessage());					
						for (WorkOrderServiceElementVO serviceElement : serviceDTO.getWoServiceElements()) {
							
							//validacion de datos requeridos del elemento
							this.validateDataElementsAttentionFinalization(serviceElement);
							//validacion de Decodificadores y SmartCard
							if ( serviceElement.getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() ) ) {
								UtilsBusiness.assertNotNull( serviceElement.getLinkedWOServiceElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());						
								this.validateDecoSmartCard(serviceElement.getElementTypeId(), serviceElement.getLinkedWOServiceElement().getElementTypeId(), serviceElement.getElementSerial(), serviceElement.getLinkedWOServiceElement().getElementSerial());
							}
						}
					}	
					//Si el servicio permite recuperar elementos, se valida que vengan los elementos recuperados serializados empleados en la atencion
					if ( service.getAllowRetrieveElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ) {
						
						params[0] = "WoServiceElementsRecovery";
						UtilsBusiness.validateRequestResponseWebService(params, serviceDTO.getWoServiceElementsRecovery(), ErrorBusinessMessages.CORE_CR016.getCode(), ErrorBusinessMessages.CORE_CR016.getMessage());					
						for (WorkOrderServiceElementVO serviceElement : serviceDTO.getWoServiceElementsRecovery()) {
							
							//validacion de datos requeridos del elemento
							this.validateDataElementsAttentionFinalization(serviceElement);
							//validacion de Decodificadores y SmartCard
							if ( serviceElement.getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() ) ) {
								UtilsBusiness.assertNotNull( serviceElement.getLinkedWOServiceElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
								this.validateDecoSmartCard(serviceElement.getElementTypeId(), serviceElement.getLinkedWOServiceElement().getElementTypeId(), serviceElement.getElementSerial(), serviceElement.getLinkedWOServiceElement().getElementSerial());
							}
						}
					}
					
					//Si el servicio permite cambio de elementos, se valida que vengan los elementos serializados instaldos y recuperados empleados en la atencion
					if ( service.getAllowChangeElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ) {
						
						params[0] = "WoServiceElements";
						UtilsBusiness.validateRequestResponseWebService(params, serviceDTO.getWoServiceElements(), ErrorBusinessMessages.CORE_CR016.getCode(), ErrorBusinessMessages.CORE_CR016.getMessage());					
						for (WorkOrderServiceElementVO serviceElement : serviceDTO.getWoServiceElements()) {
							
							//validacion de datos requeridos del elemento
							this.validateDataElementsAttentionFinalization(serviceElement);
							//validacion de Decodificadores y SmartCard
							if ( serviceElement.getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() ) ) {
								UtilsBusiness.assertNotNull( serviceElement.getLinkedWOServiceElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
								this.validateDecoSmartCard(serviceElement.getElementTypeId(), serviceElement.getLinkedWOServiceElement().getElementTypeId(), serviceElement.getElementSerial(), serviceElement.getLinkedWOServiceElement().getElementSerial());
							}
						}
						
						params[0] = "WoServiceElementsRecovery";
						UtilsBusiness.validateRequestResponseWebService(params, serviceDTO.getWoServiceElementsRecovery(), ErrorBusinessMessages.CORE_CR016.getCode(), ErrorBusinessMessages.CORE_CR016.getMessage());					
						for (WorkOrderServiceElementVO serviceElement : serviceDTO.getWoServiceElementsRecovery()) {
							
							//validacion de datos requeridos del elemento
							this.validateDataElementsAttentionFinalization(serviceElement);
							//validacion de Decodificadores y SmartCard
							if ( serviceElement.getIsSerialized().equalsIgnoreCase( CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() ) ) {
								UtilsBusiness.assertNotNull( serviceElement.getLinkedWOServiceElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
								this.validateDecoSmartCard(serviceElement.getElementTypeId(), serviceElement.getLinkedWOServiceElement().getElementTypeId(), serviceElement.getElementSerial(), serviceElement.getLinkedWOServiceElement().getElementSerial());
							}
						}
					}
				}					
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateDataWOAttention/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateDataWOAttention/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#activarElementosEnIBS(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void activarElementosEnIBS(WOAttentionElementsRequestDTO request) throws BusinessException {

		log.debug("== Inicia attendWorkOrder/CoreWOAttentionsBusiness ==");

		try {
			UtilsBusiness.assertNotNull(request, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			//WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID( request.getWorkorderVo().getId() );
			WorkOrder workOrder = request.getWorkorderVo();
			UtilsBusiness.assertNotNull(workOrder, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());

			//Se consulta parametro de sistema para saber que servicio de activacion se tiene que invocar
			SystemParameter addActivationCoreParam = systemParameterDAO.getSysParamByCodeAndCountryId( CodesBusinessEntityEnum.SYSTEM_PARAM_ADD_ACTIVATION_CORE.getCodeEntity() , workOrder.getCountry().getId());
			if ( addActivationCoreParam != null && addActivationCoreParam.getValue() != null 
					&& ( addActivationCoreParam.getValue().equals( CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity() ) 
							|| addActivationCoreParam.getValue().equals( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() ) ) ) {
				
				//Ejecutando 9. Si el servicio es instalación: Invocar el servicio de IBS para los servicios que fueron activados con el fin que IBS los active
				if ( request.getInstallationSerializedElements() != null && !request.getInstallationSerializedElements().isEmpty() ) {
					//Caso que este en Y el parametro se llama al metodo addActivation
					if ( addActivationCoreParam.getValue().equals( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() ) ) {
						List<String> newSerials = this.getSerialsFromElements( request.getInstallationSerializedElements() );
						this.coreWOServiceBroker.addActivation(workOrder.getWoCode(), workOrder.getCountry().getCountryCode(), newSerials);
					} else if ( addActivationCoreParam.getValue().equals( CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity() ) ) { //Caso que este en N el parametro se llama al metodo ShipOrder
						int custId = Integer.valueOf( request.getCustomerCode() );
						int woCode = Integer.valueOf( workOrder.getWoCode() );
						for ( WorkOrderServiceElementVO vo : request.getInstallationSerializedElements() ){
							if ( vo.getIsSerialized().equals( CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() ) ) {
								String serviceResponse = this.trayManagmentBusinessDelegate.shipOrder( custId, woCode, vo.getLinkedWOServiceElement().getElementSerial(), vo.getElementSerial(), request.getAttentionComment(), request.getCrewResponsibleName());
							}	
						}
					}
				}
			} else {
				throw new BusinessException(ErrorBusinessMessages.CORE_CR054.getCode(), ErrorBusinessMessages.CORE_CR054.getMessage());
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion activarElementosEnIBS/CoreWOAttentionsBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina activarElementosEnIBS/CoreWOAttentionsBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOAttentionsBusinessLocal#informarCambioDeIRDIBS(co.com.directv.sdii.ws.model.dto.WOAttentionElementsRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED) 
	public void informarCambioDeIRDIBS( WOAttentionElementsRequestDTO request )	throws BusinessException {
		log.debug("== Inicia informarCambioDeIRDIBS/CoreWOAttentionsBusiness ==");
		try{			
			//Ejecutando 10. Si el servicio es técnico o cambio de equipo: Invocar el servicio de IBS para cambio de equipos
			resourceProvisioningBroker = new ResourceProvisioningServiceBrokerImpl();
			if(request.getInstallationSerializedElements() != null && !request.getInstallationSerializedElements().isEmpty() 
					&& request.getRecoverySerializedElements() != null && !request.getRecoverySerializedElements().isEmpty() ){
				WorkOrderServiceElementVO installedDeco = request.getInstallationSerializedElements().get(0);
				WorkOrderServiceElementVO installedSC = request.getInstallationSerializedElements().get(0).getLinkedWOServiceElement();
				WorkOrderServiceElementVO recoveryDeco = request.getRecoverySerializedElements().get(0);
				WorkOrderServiceElementVO recoverySC = request.getRecoverySerializedElements().get(0).getLinkedWOServiceElement();
				List<WorkOrderServiceElementVO> installationSerializedElements = new ArrayList<WorkOrderServiceElementVO>();
				List<WorkOrderServiceElementVO> recoverySerializedElements = new ArrayList<WorkOrderServiceElementVO>();
				

				//Consultar el pais
				Country country = this.daoCountries.getCountriesByCode(request.getCountryCode());
				SystemParameter sysParamNewReason = this.systemParameterDAO.getSysParamByCodeAndCountryId( CodesBusinessEntityEnum.SYSTEM_PARAM_WO_REASON_SWOP_NEW_HSP_TO_IBS.getCodeEntity() , country.getId());
				SystemParameter sysParamoldReason = this.systemParameterDAO.getSysParamByCodeAndCountryId( CodesBusinessEntityEnum.SYSTEM_PARAM_WO_REASON_SWOP_OLD_HSP_TO_IBS.getCodeEntity() , country.getId());
				
				//1. Si en las dos listas el deco y la SC es diferente se invoca el servicio dos veces
				if( !installedDeco.getElementSerial().equalsIgnoreCase( recoveryDeco.getElementSerial() ) 
						&& !installedSC.getElementSerial().equalsIgnoreCase( recoverySC.getElementSerial() ) ){
					
					installationSerializedElements.add( installedSC );
					recoverySerializedElements.add( recoverySC );
					
					
					//Se hace el llamado con los DECO
					resourceProvisioningBroker.addIRDChanges(request, sysParamNewReason, sysParamoldReason);
					//Se hace el llamado con las SC
					request.setInstallationSerializedElements(installationSerializedElements);
					request.setRecoverySerializedElements( recoverySerializedElements );
					resourceProvisioningBroker.addIRDChanges(request, sysParamNewReason, sysParamoldReason);
				
				//2. Caso que envien el mismo deco y diferentes SC
				}else if( installedDeco.getElementSerial().equalsIgnoreCase( recoveryDeco.getElementSerial() ) 
						&& !installedSC.getElementSerial().equalsIgnoreCase( recoverySC.getElementSerial() ) ){
					//Se hace el llamado con las SC
					installationSerializedElements.add( installedSC );
					recoverySerializedElements.add( recoverySC );
					request.setInstallationSerializedElements(installationSerializedElements);
					request.setRecoverySerializedElements( recoverySerializedElements );
					resourceProvisioningBroker.addIRDChanges(request, sysParamNewReason, sysParamoldReason);
				//3. Caso que envien diferente SC y mismo Deco
				}else if( !installedDeco.getElementSerial().equalsIgnoreCase( recoveryDeco.getElementSerial() ) 
						&& installedSC.getElementSerial().equalsIgnoreCase( recoverySC.getElementSerial() ) ){
					//Se hace el llamado con los DECO
					resourceProvisioningBroker.addIRDChanges(request, sysParamNewReason, sysParamoldReason);
				}
				
			}
						
		} catch (Throwable ex) {
			 log.debug("== Error al tratar de ejecutar la operacion informarCambioDeIRDIBS/CoreWOAttentionsBusiness");
			 throw super.manageException(ex);
		 } finally {
			 log.debug("== Termina informarCambioDeIRDIBS/CoreWOAttentionsBusiness ==");
		 }
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendTrayManagementErrorMail(Long workOrderId, String messageCode, WoAttentionStatusVO statusVo, String message, User ... user) throws BusinessException{
		log.debug("== Inicia sendTrayManagementErrorMail/TrayManagmentBusinessHelper ==");
		WorkOrder workOrder = null;		
		try {
			workOrder = workOrderDAOBean.getWorkOrderByID(workOrderId);
			String woCode = workOrder.getWoCode();
			Long countryId = workOrder.getCountry().getId();
			String countryName = workOrder.getCountry().getCountryName();
			String errorCode = messageCode;
			Date attDate = statusVo.getAttentionDate();
			String processCode = statusVo.getAttentionStatus().getCode();
			String processName = statusVo.getAttentionStatus().getName();
			String idDoc = statusVo.getId() + " - " + statusVo.getWoId();
			woLoadBusiness.sendTrayManagementErrorMail(woCode, countryId, countryName, errorCode, attDate, message, processCode, processName, idDoc, user);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion sendTrayManagementErrorMail/TrayManagmentBusinessHelper");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina sendTrayManagementErrorMail/TrayManagmentBusinessHelper ==");
		}
	}	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public RequiredServiceElementDTO getRequiredServiceElements(InventoryDTO inventoryDTO) throws BusinessException {
		log.debug("== Inicia getRequiredServiceElements/TrayManagmentBusinessHelper ==");
		try {
			RequiredServiceElementDTO requiredElement = new RequiredServiceElementDTO();
			RequiredServiceElementVO requiredElementVO = new RequiredServiceElementVO();			
			requiredElementVO.setSerializedElements( requiredServiceDao.getRequiredServiceElementsSerialized(inventoryDTO.getServiceDTO().getServiceId()) );			
			requiredElementVO.setNotSerializedElements( requiredServiceDao.getRequiredServiceElementsNotSerialized(inventoryDTO.getServiceDTO().getServiceId()) );
			requiredElement.setRequiredServiceElement(requiredElementVO);
			return requiredElement;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion getRequiredServiceElements/TrayManagmentBusinessHelper");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getRequiredServiceElements/TrayManagmentBusinessHelper ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EnvelopeEncapsulateResponse massiveValidationWorkOrders(WOAttentionsRequestDTO woAttentionDTO) throws BusinessException {
		log.debug("== Inicia massiveValidationWorkOrders/TrayManagmentBusinessHelper ==");		
		
		try {
			//Objetos Globales
			EnvelopeEncapsulateResponse response = new EnvelopeEncapsulateResponse();
			List<EnvelopeEncapsulateDetailResponse> exceptions = new ArrayList<EnvelopeEncapsulateDetailResponse>();
			TrayWOManagmentDTO principalWorkOrder = woAttentionDTO.getPrincipalWorkOrder();
			TrayWOManagmentDTO associateWorkOrder = woAttentionDTO.getAssociatedWorkOrder();
			
			//Objeto para las validaciones
			Object[] params = null;
			
			//Objetos para encapsular el mensaje de error de la Workorder
			EnvelopeEncapsulateDetailResponse detail = null;
			response.setWoCode( principalWorkOrder.getWorkOrderCode() );	
			//Parametros comunes al error
			params = null;
			params = new Object[7];	
			params[0] = principalWorkOrder.getWorkOrderCode();	
			params[2] = principalWorkOrder.getDealerCode();
			params[3] = principalWorkOrder.getWorkOrderTypeCode();
			params[4] = principalWorkOrder.getCustomerCode();
			params[5] = principalWorkOrder.getCustomerAddress();
			
			//Valida que el dealer sea el mismo para todas las WO, tomando como punto de comparacion la WO principal				
			if( principalWorkOrder.getDealerId().compareTo(associateWorkOrder.getDealerId()) != 0){
				detail = new EnvelopeEncapsulateDetailResponse();										
				params[1] = "Dealer";							
				detail.setExceptionCode(ErrorBusinessMessages.CORE_CR046.getCode());
				detail.setExceptionMessage(ErrorBusinessMessages.CORE_CR046.getMessage(params));
				exceptions.add(detail);
			}
			//Valida que el dealer sea el mismo para todas las WO, tomando como punto de comparacion la WO principal	
			if( !principalWorkOrder.getWorkOrderTypeCode().equalsIgnoreCase(associateWorkOrder.getWorkOrderTypeCode()) ){
				detail = new EnvelopeEncapsulateDetailResponse();		
				params[1] = "Tipo de Work Order";							
				detail.setExceptionCode(ErrorBusinessMessages.CORE_CR046.getCode());
				detail.setExceptionMessage(ErrorBusinessMessages.CORE_CR046.getMessage(params));
				exceptions.add(detail);
			}
			if( !principalWorkOrder.getCustomerCode().equalsIgnoreCase(associateWorkOrder.getCustomerCode()) ){
				detail = new EnvelopeEncapsulateDetailResponse();	
				params[1] = "Cliente";							
				detail.setExceptionCode(ErrorBusinessMessages.CORE_CR046.getCode());
				detail.setExceptionMessage(ErrorBusinessMessages.CORE_CR046.getMessage(params));
				exceptions.add(detail);
			}	
			if( !principalWorkOrder.getCustomerAddress().equalsIgnoreCase(associateWorkOrder.getCustomerAddress()) ){
				detail = new EnvelopeEncapsulateDetailResponse();	
				params[1] = "Direccion de Instalacion";							
				detail.setExceptionCode(ErrorBusinessMessages.CORE_CR046.getCode());
				detail.setExceptionMessage(ErrorBusinessMessages.CORE_CR046.getMessage(params));
				exceptions.add(detail);
			}	
			response.setExceptions( exceptions );
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion massiveValidationWorkOrders/TrayManagmentBusinessHelper");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina massiveValidationWorkOrders/TrayManagmentBusinessHelper ==");
		}
	}

	/**
	 * Metodo: Obtiene los seriales de los elementos a partir de una lista
	 * de objetos que encapsula la información de los elementos usados en 
	 * la prestación de los servicios de una work order
	 * a partir del objeto WorkOrderServiceElementVO
	 * @param request List<WorkOrderServiceElementVO> 
	 * @return Lista con los seriales que se extraen de los servicios
	 * @author jalopez
	 */
	private List<String> getSerialsFromElements(List<WorkOrderServiceElementVO> request) {
		
		List<String> result = new ArrayList<String>();
		for (WorkOrderServiceElementVO workOrderServiceElementVO : request) {			
			result.add(workOrderServiceElementVO.getElementSerial());
			result.add(workOrderServiceElementVO.getLinkedWOServiceElement().getElementSerial());			
		}
		return result;
	}
	
	/**
	 * Metodo: Cambia el estado de la work order dado un código de nuevo estado
	 * @param workOrder
	 * @param woStatus
	 * @param woReason
	 * @param comments
	 * @param userId
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author jalopez
	 */
	private WorkOrder changeWorkOrderHistoryAndContact(WorkOrder workOrder, WorkorderStatus woStatus, String comments, WorkorderReason woReason, Long userId) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		workOrder = coreWoBusiness.changeWorkOrderHistoryCreateContact(workOrder.getId(), woStatus.getId(), woReason, comments, userId);
		return workOrder;
		
	}	
	
	/**
	 * 
	 * Metodo: Valida los datos comunes requeridos para
	 * el proceso de atencion y/o finalizacion.
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	private void validateDataWOAttentionFinalization(TrayWOManagmentDTO request) throws BusinessException {
		Object[] params = null;
		params = new Object[2];		
		
		params[1] = "validateDataWOAttentionFinalization";
		
		UtilsBusiness.assertNotNull(request, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		params[0] = "DealerId";
		UtilsBusiness.validateRequestResponseWebService(params, request.getDealerId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "DealerCode";
		UtilsBusiness.validateRequestResponseWebService(params, request.getDealerCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "CustomerId";
		UtilsBusiness.validateRequestResponseWebService(params, request.getCustomerId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "CustomerCode";
		UtilsBusiness.validateRequestResponseWebService(params, request.getCustomerCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "WorkOrderFinished";
		UtilsBusiness.validateRequestResponseWebService(params, request.getWorkOrderFinished(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "WorkOrderId";
		UtilsBusiness.validateRequestResponseWebService(params, request.getWorkOrderId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "WorkOrderCode";
		UtilsBusiness.validateRequestResponseWebService(params, request.getWorkOrderCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));

		params[0] = "UserId";
		UtilsBusiness.validateRequestResponseWebService(params, request.getUserId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "CountryCode";
		UtilsBusiness.validateRequestResponseWebService(params, request.getCountryCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "CountryId";
		UtilsBusiness.validateRequestResponseWebService(params, request.getCountryId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		UtilsBusiness.assertNotNull(request.getTrayWOServiceDTO(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());		
	}

	/**
	 * Metodo: valida la informacion requerida
	 * del servicio
	 * @param serviceDTO TrayWorkOrderServiceDTO
	 * @param addService
	 * @throws BusinessException
	 * @author jalopez
	 */
	private void validateDataServiceWOAttentionFinalization(TrayWorkOrderServiceDTO serviceDTO, boolean addService ) throws BusinessException {
		Object[] params = null;
		params = new Object[2];	
		
		params[1] = "validateDataServiceWOAttentionFinalization";
		
		//si el servicio fue adicionado no es requerido validar el workorderserviceid
		if( !addService ){
			params[0] = "WorkOrderServiceId";
		 	UtilsBusiness.validateRequestResponseWebService(params, serviceDTO.getWorkOrderServiceId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		}
		params[0] = "ServiceId";
		UtilsBusiness.validateRequestResponseWebService(params, serviceDTO.getTrayServiceDTO().getServiceId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "ServiceCode";
		UtilsBusiness.validateRequestResponseWebService(params, serviceDTO.getTrayServiceDTO().getServiceCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: Validaciones de objetos requeridos
	 * para el decodificador y smartcard
	 * @param elementTypeId Long
	 * @param linkedElementTypeId Long
	 * @param elementSerial String
	 * @param linkedSerial String
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateDecoSmartCard(Long elementTypeId, Long linkedElementTypeId, String elementSerial, String linkedSerial) throws BusinessException {
		Object[] params = null;
		params = new Object[2];	
		
		params[1] = "validateDecoSmartCard";
		
		//Decodificador
		params[0] = "Decodificador-ElementType";		
		UtilsBusiness.validateRequestResponseWebService(params, elementTypeId, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "Decodificador-ElementSerial";		
		UtilsBusiness.validateRequestResponseWebService(params, elementSerial, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		//SmartCard		
		params[0] = "SmartCard-ElementType";		
		UtilsBusiness.validateRequestResponseWebService(params, linkedElementTypeId, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "SmartCard-ElementSerial";		
		UtilsBusiness.validateRequestResponseWebService(params, linkedSerial, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}
	
	/**
	 * 
	 * Metodo: validacion de objetos requeridos
	 * del elemento para la atención y/o
	 * finalización
	 * @param serviceElement
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateDataElementsAttentionFinalization(WorkOrderServiceElementVO serviceElement)  throws BusinessException {
		
		Object[] params = null;
		params = new Object[2];	
	
		params[1] = "validateDataElementsAttentionFinalization";
		
		params[0] = "ElementTypeCode";
		UtilsBusiness.validateRequestResponseWebService(params, serviceElement.getElementTypeCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "ElementTypeId";
		UtilsBusiness.validateRequestResponseWebService(params, serviceElement.getElementTypeId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
		
		params[0] = "IsSerialized";
		UtilsBusiness.validateRequestResponseWebService(params, serviceElement.getIsSerialized(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
	}	
}
