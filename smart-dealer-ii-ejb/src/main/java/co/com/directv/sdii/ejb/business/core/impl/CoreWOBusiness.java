package co.com.directv.sdii.ejb.business.core.impl;


import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.schedule.DealerWorkCapacityLoaderImpl;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacity;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.MailMessage;
import co.com.directv.sdii.common.util.MailSenderLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.BuildingBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.ServiceSuperCategoryBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.broker.CoreWorkOrderServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.CustomerInterfaceManagmentBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.DistributedQueueMessageBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.ManageWorkForceServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal;
import co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.IbsContactBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.StateMachineWOBusinessLocal;
import co.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.ejb.business.dealers.EmployeeMediaContactCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.optimus.WorkOrderMarkOptimusBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.EmailMessageException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ScheduleException;
import co.com.directv.sdii.model.dto.AssignWorkOrderDTO;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.dto.CustomerInquiriesByCriteriaIBSDTO;
import co.com.directv.sdii.model.dto.CustomerProductDTO;
import co.com.directv.sdii.model.dto.DealersCodesDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateDetailResponse;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.MovementInventoryDTO;
import co.com.directv.sdii.model.dto.MovementInventoryListDTO;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.WorkOrderDTO;
import co.com.directv.sdii.model.dto.WorkOrderInfoServiceVinculationDTO;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.CustomerAddresses;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DocumentType;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.ProcessStatus;
import co.com.directv.sdii.model.pojo.Program;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.ShippingOrderDetail;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WoCrewAssigments;
import co.com.directv.sdii.model.pojo.WoProcessSource;
import co.com.directv.sdii.model.pojo.WoProgramAssigments;
import co.com.directv.sdii.model.pojo.WoServiceStatus;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WoType;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.model.pojo.WorkOrderMark;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderReason;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WOActiveAndSuspendByCountryIdPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByCustomerQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerDateCrewQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WOByDealerWorkOrderQBEPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderServiceResponse;
import co.com.directv.sdii.model.vo.BuildingVO;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.EmployeeMediaContactVO;
import co.com.directv.sdii.model.vo.IbsContactVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ShippingOrderVO;
import co.com.directv.sdii.model.vo.SystemParameterVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WoAssignmentVO;
import co.com.directv.sdii.model.vo.WoStatusHistoryVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkOrderAgendaVO;
import co.com.directv.sdii.model.vo.WorkOrderMarkVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;
import co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal;
import co.com.directv.sdii.persistence.dao.config.AddressTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerDAOLocal;
import co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ProgramDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderReasonDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoPdfAnnexDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoProgramAssigmentsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DocumentTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WoServiceStatusDAOLocal;
import co.com.directv.sdii.reports.ReportsGenerator;
import co.com.directv.sdii.reports.ReportsGeneratorLocal;
import co.com.directv.sdii.ws.model.dto.EditCustomerWorkOrderDTO;
import co.com.directv.sdii.ws.model.dto.ResponseSendMailDTO;

import com.directvla.schema.businessdomain.customer.customerinterfacemanagement.v1_0.CustomerInquiry;

/**
 * Clase que implementa los meregisterTechnicianWOIbstodos de negocio de los servicios de Core.
 * 
 * Casos de uso:
 * 
 * Caso de Uso ADS - 26 - Detalle del Cliente ==> Se debe realizar cuando se haga el mï¿½dulo de Clientes
 * Caso de Uso ADS - 27 - Diligenciar Encuesta de Servicio
 * Caso de Uso ADS - 30 - Exportar Bandeja de Work Orders a Excel ==> Todas las tablas se deben exportar a Excel
 * Caso de Uso ADS - 60 - Registrar elementos NO serializados utilizados en la atenciï¿½n del servicio ==> Se debe realizar cuando se haga el módulo de Inventarios 
 * Caso de Uso ADS - 61 - Registrar elementos serializados utilizados en la atenciï¿½n del servicio ==> Se debe realizar cuando se haga el módulo de Inventarios
 * Caso de Uso ADS - 62 - Registrar  cambio  de  elementos serializados donde el cliente  en  la ate ==> Se debe realizar cuando se haga el módulo de Inventarios
 * Caso de Uso ADS - 63 - Registrar Recuperación  de  elementos serializados  donde el cliente ==> Se debe realizar cuando se haga el módulo de Inventarios
 * Caso de Uso ADS - 64 - Registrar Recuperación  de  elementos NO serializados  donde el cliente ==> Se debe realizar cuando se haga el módulo de Inventarios
 * 
 * @author Jimmy Vélez Muñóz
 *
 */
@Stateless(name="CoreWOBusinessLocal",mappedName="ejb/CoreWOBusinessLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoreWOBusiness extends BusinessBase implements CoreWOBusinessLocal {
	
	@EJB private AddressTypeDAOLocal addressTypeDAOLocal;
	
	@EJB(name="CoreWorkOrderServiceBrokerLocal",beanInterface=CoreWorkOrderServiceBrokerLocal.class)
	private CoreWorkOrderServiceBrokerLocal coreWOServiceBroker;
	
	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAOBean;

	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal workAssignmentDAOBean;

	@EJB(name="WoStatusHistoryDAOLocal", beanInterface=WoStatusHistoryDAOLocal.class)
	private WoStatusHistoryDAOLocal woStatusHistoryDAO;

	@EJB(name="WorkorderReasonDAOLocal", beanInterface=WorkorderReasonDAOLocal.class)
	private WorkorderReasonDAOLocal woReasonDAO;

	@EJB(name="CustomerDAOLocal", beanInterface=CustomerDAOLocal.class)
	private CustomerDAOLocal customerDAO;

	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDAO;

	@EJB(name="ServiceDAOLocal", beanInterface=ServiceDAOLocal.class)
	private ServiceDAOLocal serviceDAO;

	@EJB(name="WorkOrderServiceDAOLocal", beanInterface=WorkOrderServiceDAOLocal.class)
	private WorkOrderServiceDAOLocal woServiceDAO;

	@EJB(name="WorkOrderAgendaDAOLocal", beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAO;

	@EJB(name="StateMachineWOBusinessLocal", beanInterface=StateMachineWOBusinessLocal.class)
	private StateMachineWOBusinessLocal stateMachine;

	@EJB(name="MailSenderLocal", beanInterface=MailSenderLocal.class)
	private MailSenderLocal mailSenderLocal;

	@EJB(name="ReportsGeneratorLocal", beanInterface=ReportsGeneratorLocal.class)
	private ReportsGeneratorLocal reportsGeneratorLocal;

	@EJB(name="EmployeeMediaContactCRUDBeanLocal", beanInterface=EmployeeMediaContactCRUDBeanLocal.class)
	private EmployeeMediaContactCRUDBeanLocal mediaContactCRUDBeanLocal;

	@EJB(name="EmployeesCrewDAOLocal", beanInterface=EmployeesCrewDAOLocal.class)
	private EmployeesCrewDAOLocal employeesCrewDAO;

	@EJB(name="WorkOrderAgendaDAOLocal", beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAOLocal;

	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealersDAO;

	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal woAssignmentDAO;

	@EJB(name="CrewsDAOLocal", beanInterface=CrewsDAOLocal.class)
	private CrewsDAOLocal crewsDAO;

	@EJB(name="ShippingOrderDAOLocal", beanInterface=ShippingOrderDAOLocal.class)
	private ShippingOrderDAOLocal shippingOrderDAO;

	@EJB(name="ShippingOrderElementDAOLocal", beanInterface=ShippingOrderElementDAOLocal.class)
	private ShippingOrderElementDAOLocal shippingOrderElementDAO;
	
	@EJB(name="SecurityBusinessBeanLocal", beanInterface=SecurityBusinessBeanLocal.class)
	private SecurityBusinessBeanLocal securityBusinessBean;

	@EJB(name="DocumentTypesDAOLocal", beanInterface=DocumentTypesDAOLocal.class)
	private DocumentTypesDAOLocal documentTypesDao;

	@EJB(name="Ibs6StatusDAOLocal", beanInterface=Ibs6StatusDAOLocal.class)
	private Ibs6StatusDAOLocal ibs6StatusDAO;

	@EJB(name="WoServiceStatusDAOLocal", beanInterface=WoServiceStatusDAOLocal.class)
	private WoServiceStatusDAOLocal woServiceStatusDao;

	@EJB(name="CustomerBusinessBeanLocal", beanInterface=CustomerBusinessBeanLocal.class)
	private CustomerBusinessBeanLocal customerBusinessBean;

	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;

	@EJB(name="ProcessStatusDAOLocal", beanInterface=ProcessStatusDAOLocal.class)
	private ProcessStatusDAOLocal processStatusDao;
	
	@EJB(name="ContactBusinessBeanLocal", beanInterface=ContactBusinessBeanLocal.class)
	private ContactBusinessBeanLocal contactBusiness;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;

	@EJB(name="ProgramDAOLocal", beanInterface=ProgramDAOLocal.class)
	private ProgramDAOLocal programDao;
	
	@EJB(name="WoProgramAssigmentsDAOLocal", beanInterface=WoProgramAssigmentsDAOLocal.class)
	private WoProgramAssigmentsDAOLocal woProgramDAO;
	
	@EJB(name="WoCrewAssigmentsDAOLocal", beanInterface=WoCrewAssigmentsDAOLocal.class)
	private WoCrewAssigmentsDAOLocal woCrewAssigmentDAO;
	
	@EJB(name="BuildingDAOLocal", beanInterface=BuildingDAOLocal.class)
	private BuildingDAOLocal buildingDAO;
	
	@EJB(name="BuildingBusinessBeanLocal", beanInterface=BuildingBusinessBeanLocal.class)
	private BuildingBusinessBeanLocal buildingBusinessBean;
	
	@EJB(name="ServiceHourDAOLocal", beanInterface=ServiceHourDAOLocal.class)
	private ServiceHourDAOLocal serviceHourDAO;
	
	@EJB(name="WoPdfAnnexDAOLocal", beanInterface=WoPdfAnnexDAOLocal.class)
	private WoPdfAnnexDAOLocal woPdfAnnexDAOLocal;		
	
	@EJB(name="ShippingOrderDetailDAOLocal", beanInterface=ShippingOrderDetailDAOLocal.class)
	private ShippingOrderDetailDAOLocal shippingOrderDetailDAO;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDAO;
	
	@EJB(name="ServiceSuperCategoryBusinessBeanLocal", beanInterface=ServiceSuperCategoryBusinessBeanLocal.class)
	private ServiceSuperCategoryBusinessBeanLocal serviceSuperCategoryBusinessBean;
	
	@EJB(name="ConfigParametrosBusinessLocal", beanInterface=ConfigParametrosBusinessLocal.class)
	private ConfigParametrosBusinessLocal configParametrosBusinessBean;
	
	@EJB(name="DistributedQueueMessageBrokerLocal", beanInterface=DistributedQueueMessageBrokerLocal.class)
	private DistributedQueueMessageBrokerLocal distributedQueueMessageBroker;
	
	@EJB(name="WorkOrderCSRDAOLocal", beanInterface=WorkOrderCSRDAOLocal.class)
	private WorkOrderCSRDAOLocal workOrderCSRDAO;
	
	@EJB(name="ManageWorkForceServiceBrokerLocal",beanInterface=ManageWorkForceServiceBrokerLocal.class)
	private ManageWorkForceServiceBrokerLocal manageWorkForceServiceBroker;
	
	@EJB(name="DealersCRUDLocal", beanInterface=DealersCRUDLocal.class)
	private DealersCRUDLocal dealersCRUD;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal daoUser;

	@EJB(name="WorkOrderMarkBusinessBeanLocal",beanInterface=WorkOrderMarkBusinessBeanLocal.class)
	private WorkOrderMarkBusinessBeanLocal workOrderMarkBusiness;
	
	@EJB(name="CustomerInterfaceManagmentBrokerLocal",beanInterface=CustomerInterfaceManagmentBrokerLocal.class)
	private CustomerInterfaceManagmentBrokerLocal customerInterfaceManagmentBroker;
	
	@EJB(name="IbsContactBusinessBeanLocal",beanInterface=IbsContactBusinessBeanLocal.class)
	private IbsContactBusinessBeanLocal ibsContactBusinessBean;

	@EJB(name="EmployeeDAOLocal", beanInterface=EmployeeDAOLocal.class)
	private EmployeeDAOLocal employeeDAO;
	
	@EJB(name="WorkOrderMarkOptimusBusinessBeanLocal", beanInterface=WorkOrderMarkOptimusBusinessBeanLocal.class)
	private WorkOrderMarkOptimusBusinessBeanLocal workOrderMarkOptimusBusinessBean;
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreWOBusiness.class);

	/**
	 * Default constructor. 
	 */
	public CoreWOBusiness() {
	}

	/**
	 * Asigna un programa a la ultima WorkOrder registrada en el sistema (Caso de uso 19)
	 * @param workorder Workorder a la cual se le asocia el programa
	 * @param program Programa al cual sera asociado la work order
	 * @throws BusinessException Se lanza cuando no se encuentran asignaciones de work order o cuando hay un problema de base de datos
	 * @see CoreWOBusinessLocal#assignProgramToWorkOrder(WorkOrderVO, ProgramVO)
	 * @author jcasas
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignProgramToWorkOrder(WorkOrderVO workorder, ProgramVO program) throws BusinessException{
		log.debug("== Inicia assignProgramToWorkOrder/CoreWOBusiness ==");
		try {

			UtilsBusiness.assertNotNull(workorder, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(workorder.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(program, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(program.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			long woId = workorder.getId();
			long progId = program.getId();
			
			// Consulta la ultima asignacion
			WoAssignment workAssignment = this.workAssignmentDAOBean.getLastDealerAssignmentByWoId(woId);
			// Valida que se encuentren asignaciones para la orden de trabajo
			if (workAssignment == null) {
				log.debug(ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
			}
			
			WorkOrder woPojo =  workOrderDAOBean.getWorkOrderByID(woId);
			
			if(woPojo==null){
				log.debug("No se encontro la workorder con el id: " + woId);
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());		    
			}
			
			//jnova 30-06-2011 CC Se agrega validacion para no dejar que se asigne programa en estado atendido
			validateWoStatusToAsignProgram(woPojo);
			
			Program programPojo = programDao.getProgramByID( program.getId() );
			
			if( programPojo == null ){
				log.debug("No se encontro el programa con el id: " + progId);
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());		    
			}

			workAssignment.setCrewAssignmentDate(null);
			workAssignment.setCrewId(null);
			workAssignment.setProgram(programPojo);
			workAssignment.setProgramAssignmentDate(new Date());
			workAssignmentDAOBean.updateWoAssignment( workAssignment );
				
			//Se crea el historial de asignacion de programa
			WoProgramAssigments woProgramAssigment = new WoProgramAssigments();
			woProgramAssigment.setProgramAssigmentDate( UtilsBusiness.fechaActual() );
			woProgramAssigment.setProgramId( program );
			woProgramAssigment.setWoId( woPojo );
			woProgramDAO.createWoProgramAssigments(woProgramAssigment);
				   
			//En caso que tenga una cuadrilla asignada se marca en el historial que ya no se encuentra asignada
			woCrewAssigmentDAO.unassignWoCrewAssigment(woId);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/assignProgramToWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina assignProgramToWorkOrder/CoreWOBusiness ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Valida que el estado de la WO sea permitido para la asignacion de programa
	 * @param woPojo 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void validateWoStatusToAsignProgram(WorkOrder woPojo) throws BusinessException{
		log.debug("== Inicia validateWoStatusToAsignProgram/CoreWOBusiness");
		try{
			if( woPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equals( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity() ) ){
				log.debug("La work order: "+woPojo.getId()+" se encuentra en estado WORKORDER_STATUS_REALIZED" );
				throw new BusinessException(ErrorBusinessMessages.CORE_CR050.getCode(), ErrorBusinessMessages.CORE_CR050.getMessage());
			}else if( woPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equals( CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity() ) ){
				log.debug("La work order: "+woPojo.getId()+" se encuentra en estado WORKORDER_STATUS_FINISHED" );
				throw new BusinessException(ErrorBusinessMessages.WORK_ORDER_FINISHED_STATED.getCode(), ErrorBusinessMessages.WORK_ORDER_FINISHED_STATED.getMessage());		    
			}
		}catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion validateWoStatusToAsignProgram/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateWoStatusToAsignProgram/CoreWOBusiness ==");
		}
	}

	/**
	 * Hace el procedimiento similar al metodo  assignProgramToWorkOrder pero con una lista de wo y no con una sola
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param workorders
	 * @param program
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignProgramToWorkOrders(List<WorkOrderVO> workorders, ProgramVO program) throws BusinessException{
		log.debug("== Inicia assignProgramToWorkOrders/CoreWOBusiness ==");
		try {
			for (WorkOrderVO workOrderVO : workorders) {
				assignProgramToWorkOrder(workOrderVO, program);
			}
		}finally {
			log.debug("== Termina assignProgramToWorkOrders/CoreWOBusiness ==");
		}

	}

	/**
	 * @author gfandino
	 * @see ICoreWOBusiness#assignFixedCrewToWorkOrder(WorkOrderVO, CrewVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignFixedCrewToWorkOrder(WorkOrderVO workorder, CrewVO crew, Long userId) throws BusinessException {
		log.debug("== Inicia assignFixedCrewToWorkOrder/CoreWOBusiness ==");
		try {
			
			//Valida que los campos no sean nulos
			UtilsBusiness.assertNotNull(workorder, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(workorder.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(crew, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(crew.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			// Invoca la validacion para determinar si la orden de trabajo se
			// encuentra y el estado requerido
			this.validateAssignFixedCrewToWorkOrder(workorder);
			
			// Consulta la ultima asignacion
			WoAssignment workAssignment = this.workAssignmentDAOBean.getLastDealerAssignmentByWoId(workorder.getId());
			// Valida que se encuentren asignaciones para la orden de trabajo
			if (workAssignment == null) {
				log.debug(ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
			}
			
			workAssignment.setProgram(null);
			workAssignment.setProgramAssignmentDate(null);
			workAssignment.setCrewId( crew.getId() );
			workAssignment.setCrewAssignmentDate( new Date() );
			workAssignmentDAOBean.updateWoAssignment( workAssignment );			
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/assignFixedCrewToWorkOrder");
			throw super.manageException(ex);
		} finally {
			log
			.debug("== Termina assignFixedCrewToWorkOrder/CoreWOBusiness ==");
		}
	}
	
	/**
	 * Método encargado de registar la asignación de la cuadrilla
	 * @param crew
	 * @param workorderVOList
	 * @param user
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void createWoCrewAssigment(Crew crew , List<WorkOrderVO> workorderVOList, User user, boolean isIBSChange) throws BusinessException{
		log.debug("== Inicia createWoCrewAssigment/CoreWOBusiness ==");
		try {
			validateMaxWOToAssignOrUnassignTechnician(workorderVOList.size(), user.getCountry().getId());
			
			List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
			for(WorkOrderVO workorderVO : workorderVOList){
				//Poblar la info de la Wo para obtener el pais de la  base de datos
				WorkOrder workorder = this.workOrderDAOBean.getWorkOrderByID(workorderVO.getId());
				workOrderList.add(workorder);
				
				//Se crea el registro en WO Status History
				WoStatusHistory lastWoStatusHistory = woStatusHistoryDAO.getLastWoStatusHistoryByWoIdAndWoStatus(workorder.getId(), workorder.getWorkorderStatusByActualStatusId().getId());			
				WorkorderReason woReason = (lastWoStatusHistory!=null) ? lastWoStatusHistory.getWorkorderReason() : null;
				String description = (lastWoStatusHistory!=null) ? lastWoStatusHistory.getDescription() : null;				
				
				this.createWoStatusHistoryHSP(workorder, woReason, description, workorder.getWorkorderStatusByActualStatusId(),null, user.getId());
							
				workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(workorder);
				workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(workorder, user.getId());	
			}
			
			//Consultar el responsable de la cuadrilla
			Employee employee = employeesCrewDAO.getEmployeeResponsibleByCrewId(crew.getId());
			
			SystemParameterVO spValTechDocVO = this.configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.SP_VALIDATE_TECHNICAL_WITH_DOCUMENT.getCodeEntity()
					, user.getCountry().getId());

			Object[] params = new Object[1];
			if(spValTechDocVO.getValue().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity()) && employee.getIbsTechnical()==null){
				params[0] = employee.getDocumentNumber();
				throw new BusinessException(ErrorBusinessMessages.CORE_CR072.getCode(), ErrorBusinessMessages.CORE_CR072.getMessage(params), UtilsBusiness.getParametersWS(params));
			}
			
			//Registrar tecnico en cola de dispatch workorder.
			this.registerTechnicianWOIbs(employee, workOrderList, user, isIBSChange);	
			
			for(WorkOrderVO workorderVO : workorderVOList){
				WoCrewAssigments woCrewAssigment = new WoCrewAssigments();
				woCrewAssigment.setCrewId(crew);
				woCrewAssigment.setWoId(workorderVO);
				woCrewAssigment.setCrewAssigmentDate(UtilsBusiness.fechaActual());
				woCrewAssigmentDAO.createWoCrewAssigments(woCrewAssigment);
				//Marca en el historial de asignacion de programa que ya no se encuentra activo
				woProgramDAO.unassignWoProgramAssigment(workorderVO.getId());


				
			}
			
		
			
			
		}catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/createWoCrewAssigment");
			throw super.manageException(ex);
		} finally {
			log
			.debug("== Termina createWoCrewAssigment/CoreWOBusiness ==");
		}
	}

	/**
	 * Valida que la lista de WO no sea mayor que el maximo permitido configurado en SystemParameter.
	 * @param woSize
	 * @param countryId
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	private void validateMaxWOToAssignOrUnassignTechnician(Integer woSize, Long countryId) throws BusinessException, PropertiesException {
		SystemParameterVO spMaxWOToAssignCrew = this.configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.MAX_WO_TO_ASSIGN_CREW.getCodeEntity(), countryId);
		Integer maxWOTOAssignCrew = new Integer(spMaxWOToAssignCrew.getValue());
		if(woSize > maxWOTOAssignCrew){
			Object[] params = new Object[1];
			params[0] = (maxWOTOAssignCrew).toString();
			throw new BusinessException(ErrorBusinessMessages.CORE_CR107.getCode(), ErrorBusinessMessages.CORE_CR107.getMessage(params), UtilsBusiness.getParametersWS(params));
		}
	}
	
	/**
	 * Metodo encargado de registrar el técnico y las WOs en IBS y en Optimus en caso que corresponda por medio de una cola de ESB.
	 * Tambien se encargará de informar a Optimus por aquellos técnicos a los que se les desasigna una WO.<br/>
	 * Dado una particularidad de Optimus las WOs se tienen que enviar agrupadas por Técnico y Customer. Si el tipo de de WO no es de instalación 
	 * Se enviarán en un mensaje separado. En caso de ser instalaciones se enviarán agrupadas en el mismo mensaje.
	 * @param employee
	 * @param workOrderList
	 * @throws BusinessException
	 */
	private void registerTechnicianWOIbs(Employee employee, List<WorkOrder> workOrderList, User user, boolean isIBSChange) throws BusinessException{
		log.debug("== Inicia registerTechnicianWOIbs/CoreWOBusiness ==");
		try {
			String systemToNotify = CodesBusinessEntityEnum.SYSTEM_NAME_ALL.getCodeEntity();
			
			if(isIBSChange){
				return;
			}
			
			SystemParameter optimusSP = this.configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.OPTIMUS_DISPATCHER_ACTIVE.getCodeEntity(), user.getCountry().getId());
			Boolean isOptimusActive = (optimusSP != null && optimusSP.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()));
			
			//Si el param para informar a optimus esta apagado o la WO esta en Terminada o Finalizada, solo informo a IBS.
			//Si el pin del empleado es null, solo informo a IBS.
			//busco solo en la 1ra WO porque las wos pueden venir en cualquier estado menos T o F. si viene en T o F viene una sola WO.
			if(!isOptimusActive
				|| employee.getPin() == null
				|| workOrderList.get(0).getWorkorderStatusByActualStatusId().getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity()) 
				|| workOrderList.get(0).getWorkorderStatusByActualStatusId().getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity())  ){
				systemToNotify = CodesBusinessEntityEnum.SYSTEM_NAME_IBS.getCodeEntity();
			}
			
			//Dispatch (Asignaciones de Tecnicos a WOs)
			List<List<WorkOrder>> listWoList = divideWOListByCustomerAndWoType(workOrderList);
			for(List<WorkOrder> woList : listWoList){
				distributedQueueMessageBroker.sendESBDispatchWorkOrderMessages(systemToNotify, user, employee, woList);
			}
			
			//Withdraw (Desasignaciones de Tecnicos a WOs)
			this.withDrawWOFromTechnicianInOptimus(isOptimusActive, user, workOrderList);
			
			
		}catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion registerTechnicianWOIbs/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina registerTechnicianWOIbs/CoreWOBusiness ==");
		}
	}
	
	/**
	 * Dada una lista de WOs se separa en varias listas de WOs. <br/>
	 * El criterio para separar las listas es que se creará una lista por cada customer y por tipo de wo.
	 * Pero todas las WOs de instalacion del mismo customer se agruparán en una misma lista.
	 * Por ejemplo: Si se llama al método con las WOs:
	 * <ul>
	 * <li>ID: 1 | CUST: C1 | TYPE: S</li> <li>ID: 2 | CUST: C1 | TYPE: S</li><li>ID: 3 | CUST: C1 | TYPE: I</li> <li>ID: 4 | CUST: C2 | TYPE: S</li>
	 * <li>ID: 5 | CUST: C1 | TYPE: I</li> <li>ID: 6 | CUST: C2 | TYPE: S</li><li>ID: 7 | CUST: C2 | TYPE: I</li>
	 * </ul>
	 * El resultado será:
	 * <ul><li> LISTA 1: <ul><li> 1 | C1 | S</li></ul></li><li> LISTA 2: <ul><li> 2 | C1 | S</li></ul></li>
	 * <li> LISTA 3: <ul><li> 3 | C1 | I</li><li> 5 | C1 | I</li></ul></li><li> LISTA 4: <ul><li> 4 | C2 | I</li><li> 7 | C2 | I</li></ul></li>
	 * <li> LISTA 5: <ul><li> 6 | C2 | S</li></ul></li><ul>
	 * @param workOrderList
	 * @return
	 * @throws PropertiesException 
	 */
	private List<List<WorkOrder>> divideWOListByCustomerAndWoType(List<WorkOrder> workOrderList) throws PropertiesException{
		List<List<WorkOrder>> result = new ArrayList<List<WorkOrder>>();
		for(WorkOrder wo : workOrderList){
			boolean founded = false;
			String installWoTypeCode = CodesBusinessEntityEnum.WORKORDER_TYPE_INSTALL.getCodeEntity();
			String moveWoTypeCode = CodesBusinessEntityEnum.WORKORDER_TYPE_MOVE.getCodeEntity();
			String woTypeCode = wo.getWoTypeByWoTypeId().getWoTypeCode();  
			if(result.size() > 0 && woTypeCode.equals(installWoTypeCode)){
				for(List<WorkOrder> wos : result){
					String firstWoTypeCode = wos.get(0).getWoTypeByWoTypeId().getWoTypeCode();
					String firstCustomerCode = wos.get(0).getCustomer().getCustomerCode();
					if(firstCustomerCode.equals(wo.getCustomer().getCustomerCode()) 
							&& firstWoTypeCode.equals(installWoTypeCode)){
						wos.add(wo);
						founded = true;
						break;
					}
				}
			}
			 if(result.size() > 0 && woTypeCode.equals(moveWoTypeCode)){
			        for(List<WorkOrder> wos : result){
			          String firstWoTypeCode = wos.get(0).getWoTypeByWoTypeId().getWoTypeCode();
			          String firstCustomerCode = wos.get(0).getCustomer().getCustomerCode();
			          if(firstCustomerCode.equals(wo.getCustomer().getCustomerCode()) 
			              && firstWoTypeCode.equals(moveWoTypeCode)){
			            wos.add(wo);
			            founded = true;
			            break;
			          }
			        }
			      } 

			if (!founded){ //es nuevo par customer-wotype
				List<WorkOrder> woList = new ArrayList<WorkOrder>();
				woList.add(wo);
				result.add(woList);
			}					
		}
		return result;
	}
	
	/**
	 * Método encargado de recibir una lista de WoCrewAssignment y las separarlas por cuadrilla. <br/>
	 * devolviendo asi un Map con el Id de la cuadrilla como clave, y la lista de de WO de esa cuadrilla como valor.
	 * 
	 * @param woCrewAssigmentList
	 * @return
	 */
	private Map<Long,List<WorkOrder>> divideWoCrewAssignmentByCrew(List<WoCrewAssigments> woCrewAssigmentList){
		Map<Long,List<WorkOrder>> crewWoMap = new HashMap<Long, List<WorkOrder>>();
		for(WoCrewAssigments woCrewAssignment : woCrewAssigmentList){
			if(crewWoMap.containsKey(woCrewAssignment.getCrewId().getId())){
				crewWoMap.get(woCrewAssignment.getCrewId().getId()).add(woCrewAssignment.getWoId());
			}else{
				List<WorkOrder> woList = new ArrayList<WorkOrder>();
				woList.add(woCrewAssignment.getWoId());
				crewWoMap.put(woCrewAssignment.getCrewId().getId(), woList);
			}
		}	
		return crewWoMap;
	}
	
	/**
	 * Método encargado de generar mensajes en la cola para que ESB avise a optimus la desasignación de técnicos.<br/>
	 * En caso que las WOs que se pasan por parámetro ya tienen un técnico asignado se debe informar al dicho técnico su desasignación. <br/>
	 * Dado una particularidad de Optimus las WOs se tienen que enviar agrupadas por Técnico y Customer. Si el tipo de de WO no es de instalación 
	 * Se enviarán en un mensaje separado. En caso de ser instalaciones se enviarán agrupadas en el mismo mensaje.
	 * @param isOptimusActive
	 * @param user
	 * @param workOrderList
	 * @throws BusinessException
	 */
	private void withDrawWOFromTechnicianInOptimus(Boolean isOptimusActive, User user, List<WorkOrder> workOrderList) throws BusinessException{
		log.debug("== Inicia withDrawWOFromTechnicianInOptimus/CoreWOBusiness ==");
		try {
			if(isOptimusActive){
				String systemToNotify = CodesBusinessEntityEnum.SYSTEM_NAME_OPTIMUS.getCodeEntity();
				//Separar las WOs por Tecnico para llamar al servicio de ESB.
				List<WoCrewAssigments> woCrewAssignments = woCrewAssigmentDAO.getActiveWoCrewAssigmentsByWoIds(workOrderList);
				
				Set<Entry<Long, List<WorkOrder>>> crewWoSet = divideWoCrewAssignmentByCrew(woCrewAssignments).entrySet();
				for(Entry<Long, List<WorkOrder>> crewWo : crewWoSet){
					Employee oldEmployee = this.employeesCrewDAO.getEmployeeResponsibleByCrewId(crewWo.getKey());
					if(oldEmployee.getPin() != null){
						List<List<WorkOrder>> listWoListWithdraw = divideWOListByCustomerAndWoType(crewWo.getValue());
						for(List<WorkOrder> woList : listWoListWithdraw){
							distributedQueueMessageBroker.sendESBWithdrawWorkOrderMessages(systemToNotify, user, oldEmployee, woList);
						}
					}
				}
			}
		}catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion withDrawWOFromTechnicianInOptimus/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina withDrawWOFromTechnicianInOptimus/CoreWOBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#assignFixedCrewToWorkOrder(co.com.directv.sdii.model.vo.WorkOrderVO, co.com.directv.sdii.model.vo.CrewVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignFixedCrewToWorkOrders(List<WorkOrderInfoServiceVinculationDTO> workordersCrews, Long userId) throws BusinessException {
		log.debug("== Inicia assignFixedCrewToWorkOrder/CoreWOBusiness ==");
		try {
			User user = daoUser.getUserById(userId);
			for(WorkOrderInfoServiceVinculationDTO workordersCrew : workordersCrews){
	    		UtilsBusiness.assertNotEmpty(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
				CrewVO crewVO = new CrewVO(workordersCrew.getCrewId());
				List<WorkOrderVO> workOrderVOList = workordersCrew.getWorkOrderList();
				for (WorkOrderVO workorder : workOrderVOList) {
					this.assignFixedCrewToWorkOrder(workorder, crewVO, user.getId());
				}
				createWoCrewAssigment(crewVO,workOrderVOList, user, false);
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/assignFixedCrewToWorkOrders");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina assignFixedCrewToWorkOrder/CoreWOBusiness ==");
		}
	}

	/**
	 * Valida si la orden de trabajo no estï¿½ en estado terminado o finalizado
	 * @param workorder - WorkOrderVO
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void validateAssignFixedCrewToWorkOrder(WorkOrderVO workorder) throws BusinessException {
		log.debug("== Inicia validateAssignFixedCrewToWorkOrder/CoreWOBusiness ==");
		try {
			//Consulta si la orden de trabajo existe
			WorkOrder validacion = workOrderDAOBean.getWorkOrderByID(workorder.getId());
			if(validacion==null){
				log.debug(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}
			//Valida que no se encuentre en estado terminado o finalizado
			if(validacion.getWorkorderStatusByActualStatusId().getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity())){
				log.debug("La work order: "+workorder.getId()+" se encuentra en estado WORKORDER_STATUS_FINISHED" );
				throw new BusinessException(ErrorBusinessMessages.WORK_ORDER_FINISHED_STATED.getCode(), ErrorBusinessMessages.WORK_ORDER_FINISHED_STATED.getMessage());
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/validateAssignFixedCrewToWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateAssignFixedCrewToWorkOrder/CoreWOBusiness ==");
		}		
	}

	/**
	 * @author jjimenezh 2010-05-24
	 * @throws BusinessException 
	 * @see ICoreWOBusiness#assignWorkOrdersToDealer(DealerVO, List<WorkOrderVO>)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignWorkOrdersToDealer(AssignWorkOrderDTO dto,List<WorkOrderVO> workorders) throws BusinessException{
		if( dto != null && workorders != null && dto.getDealer() != null ){
			for (WorkOrderVO workOrderVO : workorders) {
				dto.setWorkorder(workOrderVO);
				this.assignWorkOrderToDealer(dto);
			}
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto, String workorderReasonCode) throws BusinessException {
		this.assignWorkOrderToDealer(dto, workorderReasonCode, true);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#assignWorkOrderToDealer(co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.vo.WorkOrderVO, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void assignWorkOrderToDealer(AssignWorkOrderDTO dto, String pWorkorderReasonCode, boolean notifyIbs) throws BusinessException {
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getWorkorder(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getWorkorder().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getDealer(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getDealer().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		/*
		 * Para este caso de uso:
		 * 1. Validar que el estado actual puede llevar a ASIGNADA
		 * 2. Actualizar la tabla de WorkOrders cambiando el estado a: ASIGNADA
		 * 3. Agregar un nuevo registro en la tabla WorkOrderNovedades
		 * 4. Si la work order tiene una asignación previa de un dealer se crea una nueva, si tiene una asignación previa pero no tiene dealer se actualiza
		 * 5. Si la work order tiene una fecha de agendamiento con la asignación previa sin dealer (Esto quiere decir que vino agendada desde IBS pero no había sido asignada),
		 *    Se debe afectar la capacidad del dealer en la fecha de agendamiento.
		 * 6. Notificar a IBS el cambio de estado de la WO ejecutando el caso de uso ADS 33   
		 */

		log.debug("== Inicia assignWorkOrderToDealer/CoreWOBusiness ==");
		try {
			//jnova 12-10-2011 se agrega funcionalidad de leer el id del usuario que asigna para obtener la fecha localizada de este
			if( dto.getUserId() == null || dto.getUserId().longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			Date assignationDate = UtilsBusiness.getCurrentTimeZoneDateByUserId(dto.getUserId(), this.userDao);
			
			//Se almacena la reason local, para determinar si es asignacion o reasignacion 
			//y enviar la reason adecuada a IBS
			String workorderReasonCode = pWorkorderReasonCode;
			Boolean isDealerAssign = false;
			
			//Ejecutando 1:
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID(dto.getWorkorder().getId());
			Dealer dealerPojo = dealersDAO.getDealerByID(dto.getDealer().getId());
			UtilsBusiness.assertNotNull(dealerPojo, ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage() + " No se encontró el dealer con el id: " + dto.getDealer().getId());
			
			WoAssignment woReAssignment = woAssignmentDAO.getLastDealerAssignmentByAssignmentDate( dto.getWorkorder().getId() );					
			WorkorderStatus woStatus = null;
			
			//Si la work order ya había sido asignada en el pasado, se debe pasar a estado reasignada
			if( woReAssignment != null ){
				//Se almacena la reason de reasignacion
				isDealerAssign = true;
				workorderReasonCode = UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_REASSIGNED.getCodeEntity(), workOrderDAOBean.getCountryIdOfWorkOrderId(dto.getWorkorder().getId()), systemParameterDAO);
				//workorderReasonCode = CodesBusinessEntityEnum.WORKORDER_REASON_REASSIGNED.getCodeEntity();
				woStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
			}else{
				woStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
			}
			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());

			WorkOrderVO workOrderTmp = new WorkOrderVO();
			workOrderTmp.setWorkorderStatusByActualStatusId(workOrder.getWorkorderStatusByActualStatusId());
			workOrderTmp.setWorkorderStatusByPreviusStatusId(workOrder.getWorkorderStatusByPreviusStatusId());

			this.stateMachine.validateStatusChangeWorkOrder(workOrderTmp,woStatus.getId());            

			//Ejecutando 2 y 3:
			Long newStatusId = woStatus.getId();
			WorkorderReason workorderReason = woReasonDAO.getWorkorderReasonByCode( workorderReasonCode );

			if(workorderReason == null){
				throw new BusinessException(ErrorBusinessMessages.HANDMADE_WORKORDER_REASON_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.HANDMADE_WORKORDER_REASON_DOES_NOT_EXIST.getMessage() + " No se encontró la WO Reason con el código: " + workorderReasonCode);
			}
			if( !isDealerAssign ){
				workorderReasonCode = UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_ASSIGNED.getCodeEntity(), workOrderDAOBean.getCountryIdOfWorkOrderId(dto.getWorkorder().getId()), systemParameterDAO);
				//workorderReasonCode = CodesBusinessEntityEnum.WORKORDER_REASON_ASSIGNED.getCodeEntity();
			}

			String comment = workorderReason.getWorkorderReasonName();
			WorkOrderDTO woDTO = new WorkOrderDTO();
			woDTO.setWorkOrder(workOrder);
			woDTO.setDealerAssignmentCode( dealerPojo.getDealerCode() );
			
			WorkOrderCSR workOrderCSR = workOrderCSRDAO.getWorkOrderCSRByWoCode( dto.getWorkorder().getWoCode() );
			boolean isCSR=false;
			if(workOrderCSR != null)
				isCSR=true;
			
			workOrder = this.changeWorkOrderStatusToAssignReassign(woDTO, newStatusId, workorderReason, comment, dto.getUserId(),workorderReasonCode,isDealerAssign,isCSR);
			workOrder.setDealerId(dto.getDealer().getId());
			//jnova 10-10-2011 Como es una gestion por pantalla se pone el source process en nulo
			workOrder.setProcessSourceId(null);
			workOrderDAOBean.updateWorkOrder(workOrder,CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());

			//Ejecutando 4:	
			WoAssignment lastWoAssignment = woAssignmentDAO.getLastDealerAssignmentByWoId( dto.getWorkorder().getId() );
			if( (lastWoAssignment != null && lastWoAssignment.getDealerId() == null)|| ( isCSR && dto.isAssignFromAllocator())){				
				lastWoAssignment.setDealerId(dto.getDealer().getId());
				lastWoAssignment.setDealerAssignmentDate(assignationDate);
				woAssignmentDAO.updateWoAssignment(lastWoAssignment);
				//Si la asignación existente tiene agendamiento se cambia la work order a agendada:
				this.agendaWorkorderFromAssignmentProcess(woStatus, woDTO, lastWoAssignment,dto.isAssignFromAllocator(),isCSR);
			//Si no existe asignación previamente creo el registro en wo_asigment
			}else{
				this.createNewWoAssignment(dto.getDealer().getId(), workOrder,assignationDate);
			}
			
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(workOrder);
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(workOrder, dto.getUserId());
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/assignWorkOrderToDealer");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina assignWorkOrderToDealer/CoreWOBusiness ==");
		}
	}
	
	/**
	 * Metodo: Genera el proceso de agendamiento en caso que la work order venga activa con una agenda comprometida con el cliente
	 * y se asigne por HSP
	 * @param assignStatus objeto que encapsula el estado de asignación
	 * @param woDTO objeto que encapsula la información de la work order
	 * @param woAssignment objeto que encapsula la asignación
	 * @param isFromAllocator
	 * @throws DAOServiceException en caso de error al tratar de realizar la operación
	 * @throws DAOSQLException en caso de error al tratar de realizar la operación
	 * @throws PropertiesException en caso de error al tratar de realizar la operación
	 * @throws BusinessException en caso de error al tratar de realizar la operación
	 * @author jalopez
	 */
	private void agendaWorkorderFromAssignmentProcess(WorkorderStatus assignStatus, WorkOrderDTO woDTO, WoAssignment woAssignment,boolean isFromAllocator,boolean isCsr) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		
		UserVO user = securityBusinessBean.getIBSAdminUserByCountryId(woDTO.getWorkOrder().getCountry().getId());
		WorkOrderAgenda woAgenda = workOrderAgendaDAO.getLastWorkOrderAgendaByWoAssignmentId(woAssignment.getId());
		if( woAgenda == null ){
			return;
		}
		
		String workOrderReasonCode="";
		String description = "";
		
		WorkorderStatus workorderStatus = null;
		WorkorderStatus agendaStatus = null;
		if(woDTO!=null)
			if(woDTO.getWorkOrder()!=null)
			    if(	woDTO.getWorkOrder().getIbsActualStatus()!=null)
			    	if(	woDTO.getWorkOrder().getIbsActualStatus().getIbs6StateCode()!=null)
			    		workorderStatus = workorderStatusDAO.getWorkorderStatusByIBS6StatusCode(woDTO.getWorkOrder().getIbsActualStatus().getIbs6StateCode());
		
		//Se consulta la wo reason para el caso de agendamiento.
		if(workorderStatus != null && workorderStatus.getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity())){
			workOrderReasonCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_RESCHEDULED.getCodeEntity(), woDTO.getWorkOrder().getCountry().getId(), systemParameterDAO);
			description = ApplicationTextEnum.AUTOMATICALLY_RESCHEDULED.getApplicationTextValue()+" "+UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_APPLICATION_NAME.getCodeEntity(), woDTO.getWorkOrder().getCountry().getId(), systemParameterDAO)+ApplicationTextEnum.AFTER_SCHEDULE_DETECTION_IN_WO_IMPORT.getApplicationTextValue();
			agendaStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
			assignStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
		//Se consulta la wo reason para el caso de reAgendamiento.
		}else {
			workOrderReasonCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_SCHEDULED.getCodeEntity(), woDTO.getWorkOrder().getCountry().getId(), systemParameterDAO);
			description = ApplicationTextEnum.AUTOMATICALLY_SCHEDULE.getApplicationTextValue()+" "+UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_APPLICATION_NAME.getCodeEntity(), woDTO.getWorkOrder().getCountry().getId(), systemParameterDAO)+ApplicationTextEnum.AFTER_SCHEDULE_DETECTION_IN_WO_IMPORT.getApplicationTextValue();
			agendaStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
		}
		
		WorkOrderVO workOrderTmp = new WorkOrderVO();
		workOrderTmp.setWorkorderStatusByActualStatusId(assignStatus);

		//Ejecutando 4:
		//Usar la máquina de estados para validar el cambio de estado a AGENDADA
		this.stateMachine.validateStatusChangeWorkOrderByCodes(workOrderTmp,agendaStatus.getWoStateCode());

		WorkorderReason agendaReason = woReasonDAO.getWorkorderReasonByCode(workOrderReasonCode);
		if(agendaReason == null){
			throw new BusinessException(ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getMessage());
		}
		if( isFromAllocator ){
			WoProcessSource woProcessSource = getWoProcessSourceByWoCodeActualStatus(woDTO.getWorkOrder().getWoCode(), agendaStatus.getWoStateCode());
			if( woProcessSource != null && woProcessSource.getId() != null && woProcessSource.getId().longValue() > 0
					&& woDTO != null && woDTO.getWorkOrder() != null ){
				woDTO.getWorkOrder().setProcessSourceId( woProcessSource.getId() );
			}
		}
		woDTO.setWoAgenda(woAgenda);
		
		if(!isCsr){
			this.changeWorkOrderStatus(woDTO, agendaStatus.getId(), agendaReason, description , user.getId(), new WoStatusHistory(),null);
		}else{
			this.changeWorkOrderStatusWithoutIbsNotificationScheduleCSR(woDTO, agendaStatus.getId(), agendaReason, description , user.getId());
		}
		
		workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(woDTO.getWorkOrder());
		workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(woDTO.getWorkOrder(), user.getId());
		
	}

	private WorkOrder changeWorkOrderStatusToAssignReassign(WorkOrderDTO woDTO, Long newStatusId, WorkorderReason woReason, String description,Long userId,String reasonCode,boolean useReason,boolean isCSR) throws BusinessException{
		log.debug("== Inicia changeWorkOrderStatusToAssignReassign/CoreWOBusiness ==");
		try{
			//Actualizacion del estado de la Base de Datos.
			woDTO.getWorkOrder().setWorkorderStatusByPreviusStatusId(woDTO.getWorkOrder().getWorkorderStatusByActualStatusId());
			WorkorderStatus newWorkorderStatus = workorderStatusDAO.getWorkorderStatusByID(newStatusId);
			UtilsBusiness.assertNotNull(newWorkorderStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
			woDTO.getWorkOrder().setWorkorderStatusByActualStatusId(newWorkorderStatus);
			workOrderDAOBean.updateWorkOrder(woDTO.getWorkOrder(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
			
			//String ibsHistoryEventCode = this.coreWOServiceBroker.notifyWorkOrderAssignment(woDTO.getWorkOrder(), woDTO.getDealerAssignmentCode(), reasonCode);
			EditCustomerWorkOrderDTO editCustomerWorkOrderDTO = new EditCustomerWorkOrderDTO(woDTO.getWorkOrder().getWoCode(),
						                                                                     woDTO.getWorkOrder().getCountry().getCountryCode(),
																		                     woDTO.getDealerAssignmentCode(),
																		                     null,
																		                     reasonCode,
																		                     null);
			String ibsHistoryEventCode = manageWorkForceServiceBroker.editCustomerWorkOrder(editCustomerWorkOrderDTO);
			
			//Creacion del historico del cambio de estado de la WO
			//jnova 29/11/2011 en caso que se este asignando no se debe poner reason en wo_status_history
			WoStatusHistory woStatusHistory = null;
			if(isCSR){
				ibsHistoryEventCode = null;
			}
				
			if( useReason ){
				woStatusHistory = this.createWoStatusHistoryHSP(woDTO.getWorkOrder(), woReason, description, newWorkorderStatus,ibsHistoryEventCode, userId);
			} else {
				woStatusHistory = this.createWoStatusHistoryHSP(woDTO.getWorkOrder(), null, null, newWorkorderStatus,ibsHistoryEventCode, userId);
			}
				
			
			//CC Creacion de Contacts jalopez			
			this.createWoContact(woDTO.getWorkOrder().getId(), woStatusHistory, woReason,userId);			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/changeWorkOrderStatusToAssignReassign");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatusToAssignReassign/CoreWOBusiness ==");
		}
		return woDTO.getWorkOrder();
	}
	
	/**
	 * @author jjimenezh 2010-04-20
	 * @throws BusinessException 
	 * @see ICoreWOBusiness#assignWorkOrderToDealer(DealerVO, WorkOrderVO)
	 */
	@Override 
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto) throws BusinessException {
		log.debug("== Inicia assignWorkOrderToDealer/CoreWOBusiness ==");
		try{
			String handMadeAssignedReasonCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_HANDMADE_ASSIGNED.getCodeEntity(), workOrderDAOBean.getCountryIdOfWorkOrderId(dto.getWorkorder().getId()), systemParameterDAO);
			this.assignWorkOrderToDealer(dto, handMadeAssignedReasonCode);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/assignWorkOrderToDealer");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina assignWorkOrderToDealer/CoreWOBusiness ==");
		}
	}
	
	@Override 
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignWorkOrderToDealer(AssignWorkOrderDTO dto, boolean notify2Ibs) throws BusinessException {
		log.debug("== Inicia assignWorkOrderToDealer/CoreWOBusiness ==");
		try{
			String handMadeAssignedReasonCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_HANDMADE_ASSIGNED.getCodeEntity(), workOrderDAOBean.getCountryIdOfWorkOrderId(dto.getWorkorder().getId()), systemParameterDAO);
			this.assignWorkOrderToDealer(dto, handMadeAssignedReasonCode, notify2Ibs);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/assignWorkOrderToDealer");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina assignWorkOrderToDealer/CoreWOBusiness ==");
		}
	}

	/**
	 * Metodo: Crea una asignación para una work order
	 * @param dealerId
	 * @param workOrder
	 * @param assignDate
	 * @return asignación generada para la work order
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	private WoAssignment createNewWoAssignment(Long dealerId, WorkOrder workOrder,Date assignDate) throws DAOServiceException, DAOSQLException {
		WoAssignment workAssignment = new WoAssignment();
		workAssignment.setDealerAssignmentDate(assignDate);
		workAssignment.setDealerId(dealerId);
		workAssignment.setWorkOrder(workOrder);
		this.workAssignmentDAOBean.createWoAssignment(workAssignment);
		return workAssignment;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByCustomerAndCreationDate(CustomerVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByCustomerAndCreationDate(CustomerVO customer, Date initDate, Date endDate) throws BusinessException {
		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByCustomerAndCreationDate/CoreWOBusiness ==");
		try {

			woList = workOrderDAOBean.getWorkOrderByCustomerAndCreationDate(UtilsBusiness.copyObject(Customer.class, customer),initDate,endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByCustomerAndCreationDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByCustomerAndCreationDate/CoreWOBusiness ==");
		}
		return wos;
	}

//	/**
//	 * Notifica el cambio de fecha de atencion de una orden de trabajo a IBS (Caso de uso 41)
//	 * @param workorder Orden de trabajo a ser cambiada 
//	 * @param newRealizationDate Nueva fecha de asignacion de la orden de trabajo
//	 * @author jcasas
//	 * @throws BusinessException
//	 */
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public Long changeRealizationDateIBS(Long customerID, Long workOrderNumer, String newRealizationDate, String workOrderCountryId) throws BusinessException{
//		log.debug("== Inicia changeRealizationDateIBS/CoreWOBusiness ==");
//		try {
//
//			//V1
//			UtilsBusiness.assertNotNull(customerID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//			UtilsBusiness.assertNotNull(workOrderNumer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//			UtilsBusiness.assertNotNull(newRealizationDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//			UtilsBusiness.assertNotNull(workOrderCountryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//			Date newDate = UtilsBusiness.stringToDate(newRealizationDate);
//			//La Work Order debe encontrarse registrada en el sistema
//			WorkOrder woPojo = workOrderDAOBean.getWorkOrderByCodeAndCountryAndCustomer(workOrderNumer.toString(),workOrderCountryId.toString(),customerID.toString());
//			if(woPojo==null){
//				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
//			}
//			//La nueva fecha de atención debe ser posterior a la fecha actual
//			if(Calendar.getInstance().getTime().after(newDate)){
//				throw new BusinessException(ErrorBusinessMessages.INVALID_DATE.getCode(), ErrorBusinessMessages.INVALID_DATE.getMessage());
//			}
//
//			//La Work Order debe encontrarse en estado diferente a C – Cancelado. A – Atendido o F-Finalizado
//			WorkorderStatus actualStatus = woPojo.getWorkorderStatusByActualStatusId();
//			if(actualStatus==null ||  actualStatus.getWoStateCode()==null){
//				throw new BusinessException(ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
//			}
//			String stateCode = actualStatus.getWoStateCode();
//			
//			//String handMadeAssignedReasonCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_HANDMADE_ASSIGNED.getCodeEntity(), dto.getCountryId(), systemParameterDAO);
//			
//			if(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity().equals(stateCode) ||
//					CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity().equals(stateCode) || 
//					CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity().equals(stateCode)){
//
//				log.debug(ErrorBusinessMessages.WORKORDER_STATUS_FINISHED_OR_REALIZED.getMessage());
//				throw new BusinessException(ErrorBusinessMessages.WORKORDER_STATUS_FINISHED_OR_REALIZED.getCode(),ErrorBusinessMessages.WORKORDER_STATUS_FINISHED_OR_REALIZED.getMessage());
//
//			}
//			//Fin V1
//
//			WorkOrderAgenda orderAgenda = this.workOrderAgendaDAOLocal.getLastWorkOrderAgendaByWoID(woPojo.getId());
//
//			if(orderAgenda!=null){
//				Set<WorkOrderService> woSe =  woPojo.getWorkOrderServices(); //servicios por workOrder
//
//				double totalCapacity = 0d;
//
//				for (WorkOrderService workOrderService : woSe) {
//					// capacidad que tiene el servicio
//					List<RequiredCapacityService>  requiredCapacityServices = requiredCapacityService.getRequiredCapacityServiceByService(workOrderService.getService().getId());
//					for (RequiredCapacityService requiredCapacityService : requiredCapacityServices) {
//						//Es el mismo dia al que se le va afectar la capacidad?
//						if(DateUtils.isSameDay(requiredCapacityService.getInitDate(), woPojo.getWoRealizationDate())){
//							totalCapacity += requiredCapacityService.getCapacity();	
//						}
//					}
//				}
//				woPojo.setWoRealizationDate(newDate);
//				workOrderDAOBean.updateWorkOrder(woPojo);
//
//				return woPojo.getId();
//
//			}else{
//				return -1L;
//			}
//
//		} catch (Throwable ex) {
//			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/changeRealizationDateIBS");
//			throw super.manageException(ex);
//		} finally {
//			log.debug("== Termina changeRealizationDateIBS/CoreWOBusiness ==");
//		}
//	}

	/**
	 * @author gfandino
	 * @throws BusinessException 
	 * @see ICoreWOBusiness#addServiceToWorkOrder()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addServiceToWorkOrder(Long workOrderId, Long serviceId, String decoSerialNumber, String countryCode, Long userId) throws BusinessException {
		log.debug("== Inicia addServiceToWorkOrder/CoreWOBusiness ==");
		try {
			//Valida que los parametros no sean nulos
			UtilsBusiness.assertNotNull(workOrderId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(serviceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Service serviceTmp =  this.serviceDAO.getServiceByID(serviceId);
			if(serviceTmp == null){
				log.debug(ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage());
				throw new BusinessException(ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage());
			}
			
			WorkOrder wo = this.workOrderDAOBean.getWorkOrderByID(workOrderId);
			if(wo == null){
				log.debug(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}
			
			//Valida que la orden de trabajo y servicio existan (consulta por código)
			validateAddServiceToWorkOrder(wo.getWoCode(), serviceTmp.getServiceCode(), countryCode);
			
			//Asigna los valores requeridos en el Servico de la orden de trabajo
			WorkOrderService woService = new WorkOrderService();
			
			woService.setService(serviceTmp);
			
			woService.setWoId(wo.getId());
			woService.setSerialNumber(decoSerialNumber);
			Date currentDate = UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, userDao);
			woService.setServiceInclusionDate(currentDate);
			WoServiceStatus woServiceStatus = woServiceStatusDao.getWoServiceStatusByCode(CodesBusinessEntityEnum.WO_SERVICE_STATUS_UNATTENDED.getCodeEntity());
			UtilsBusiness.assertNotEmpty(woServiceStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se ha encontrado el estado del servicio en la tabla WO_SERVICE_STATUS con el código: " + CodesBusinessEntityEnum.WO_SERVICE_STATUS_UNATTENDED.getCodeEntity());
			woService.setWoServiceStatus(woServiceStatus);
			//TODO jjimenezh 2011-01-22 Vovler a enviar el código del servicio y nó el id
			//Notificando a IBS que se ha agregado un nuevo servicio:
			String ibsServiceKey = manageWorkForceServiceBroker.addServiceToWorkOrder(wo.getWoCode(), "" + serviceTmp.getId(), decoSerialNumber, countryCode);
			//coreWOServiceBroker.addServiceToWorkOrder(wo.getWoCode(), "" + serviceTmp.getId(), decoSerialNumber, countryCode);
			woService.setIbsServiceKey(ibsServiceKey);
			
			//Crea un nuevo Servicio en la Orden de trabajo
			this.woServiceDAO.createWorkOrderService(woService);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/addServiceToWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina addServiceToWorkOrder/CoreWOBusiness ==");
		}
	}

	/**
	 * Valida que la orden de servicio y el servicio existan (por su cï¿½digo)
	 * @param workOrderCode
	 * @param serviceCode 
	 * @param countryCode
	 * @throws BusinessException 
	 * @author gfandino
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void validateAddServiceToWorkOrder(String workOrderCode,
			String serviceCode, String countryCode) throws BusinessException {
		log.debug("== Inicia validateAddServiceToWorkOrder/CoreWOBusiness ==");
		try {
			//Consulta la orden de trabajo por código
			WorkOrder validacionWO = workOrderDAOBean.getWorkOrderByCodeAndCountry(workOrderCode, countryCode);
			if(validacionWO==null){
				log.debug(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}
			//Consulta el servicio por cï¿½digo
			Service validacionService = this.serviceDAO.getServiceByCode(serviceCode); 
			if(validacionService==null){
				log.debug(ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage());
				throw new BusinessException(ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage());
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/validateAddServiceToWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateAddServiceToWorkOrder/CoreWOBusiness ==");
		}		

	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByCustomerAndProgrammingDate(CustomerVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByCustomerAndProgrammingDate(CustomerVO customer, Date initDate, Date endDate)throws BusinessException {
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;
		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		log.debug("== Inicia getWorkOrderByCustomerAndProgrammingDate/CoreWOBusiness ==");
		try {

			woList = workOrderDAOBean.getWorkOrderByCustomerAndProgrammingDate(UtilsBusiness.copyObject(Customer.class, customer),initDate,endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByCustomerAndProgrammingDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByCustomerAndProgrammingDate/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByActualType(WoTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByActualType(WoTypeVO actualType) throws BusinessException {
		UtilsBusiness.assertNotNull(actualType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualType.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByActualType/CoreWOBusiness ==");
		try {

			woList = workOrderDAOBean.getWorkOrderByActualType(UtilsBusiness.copyObject(WoType.class, actualType));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByActualType");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByActualType/CoreWOBusiness ==");
		}
		return wos;
	}
	
	/**
	 * @see ICoreWOBusiness#getWorkOrderByDealerAndProgrammingDate(DealerVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByDealerAndProgrammingDate(DealerVO dealer, Date initDate, Date endDate)throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByDealerAndProgrammingDate/CoreWOBusiness ==");
		try {

			woList = workOrderDAOBean.getWorkOrderByDealerAndProgrammingDate(UtilsBusiness.copyObject(Dealer.class, dealer),initDate,endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealerAndProgrammingDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerAndProgrammingDate/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByProgrammingDate(Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByProgrammingDate(Date initDate, Date endDate) throws BusinessException {
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByProgrammingDate/CoreWOBusiness ==");
		try {

			woList = workOrderDAOBean.getWorkOrderByProgrammingDate(initDate,endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByProgrammingDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByProgrammingDate/CoreWOBusiness ==");
		}
		return wos;
	}


	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<String> getWorkOrdersFilesByDealer(final Long dealerCode) throws BusinessException {
		try {
			log.debug("== inicia la operacion CoreWOBusiness/getWorkOrdersFilesByDealer");
			UtilsBusiness.assertNotNull(dealerCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			String pathToRead = ReportsGenerator.getReportsPath();
			Dealer dealer = dealersDAO.getDealerByDealerCode(dealerCode);

			UtilsBusiness.assertNotNull(pathToRead, ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());
			UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());

			File reportFolder = new File(pathToRead);

			if(!reportFolder.exists() || !reportFolder.isDirectory() ){
				log.error("La ruta "+pathToRead+" no es valida para la generacion de reportes");
				throw new BusinessException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());
			}

			List<String> files = new ArrayList<String>();
			List<Crew> crews = crewsDAO.getCrewsByDealerId(dealer.getId());

			for (Crew crew : crews) {
				for (String file : reportFolder.list()) {
					if(file!=null && crew!=null && file.toLowerCase().startsWith("crew_"+crew.getId()+"_work_orders")){
						files.add(file);
					}
				}
			}
			return files;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrdersFilesByDealer");
			throw super.manageException(ex);
		} finally{
			log.debug("==finaliza operacion CoreWOBusiness/getWorkOrdersFilesByDealer");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrdersFilesByCrewId(java.lang.Long)
	 */
	@Override
	public List<String> getWorkOrdersFilesByCrewId(Long crewId)
	throws BusinessException {
		try {
			log.debug("== inicia la operacion CoreWOBusiness/getWorkOrdersFilesByCrewId");
			UtilsBusiness.assertNotNull(crewId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			String pathToRead = ReportsGenerator.getReportsPath();
			UtilsBusiness.assertNotNull(pathToRead, ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());

			File reportFolder = new File(pathToRead);

			if(!reportFolder.exists() || !reportFolder.isDirectory() ){
				log.error("La ruta "+pathToRead+" no es valida para la generacion de reportes");
				throw new BusinessException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());
			}

			List<String> files = new ArrayList<String>();

			for (String file : reportFolder.list()) {
				if(file!=null && file.toLowerCase().startsWith("crew_"+crewId+"_work_orders")){
					files.add(file);
				}
			}
			return files;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrdersFilesByCrewId");
			throw super.manageException(ex);
		} finally{
			log.debug("==finaliza operacion CoreWOBusiness/getWorkOrdersFilesByCrewId");
		}
	}

	/**
	 * Metodo: envia el PDF de work orders via email
	 * @param fileNames
	 * @param filterCrewId
	 * @param countryId
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @throws BusinessException 
	 * @see ICoreWOBusiness#sendWorkOrdersPDFEmail(String, CrewVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseSendMailDTO sendWorkOrdersPDFEmail(String[] fileNames, final Long filterCrewId, final Long countryId) throws BusinessException {
		try{

			ResponseSendMailDTO response = new ResponseSendMailDTO(); 
			String pathToRead = ReportsGenerator.getReportsPath();
			UtilsBusiness.assertNotNull(pathToRead, ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());
			UtilsBusiness.assertNotEmpty(fileNames, ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());

			log.debug("Enviando por correo "+fileNames.length+" archivos de WorkOrder");

			Map<Long, List<File>> crewsByFile = new TreeMap<Long, List<File>>();
			List<EmployeeMediaContactVO> mediaContactEmployeeList = null;

			for (String currentFile : fileNames) {
				File reportFolder = new File(pathToRead+currentFile);
				log.debug("Validando archivo "+currentFile);

				long crewId = filterCrewId!=null && filterCrewId.longValue()>0?validateFileFormat(currentFile, reportFolder):-1;

				List<File> files = new ArrayList<File>();
				if(crewsByFile.containsKey(crewId)){
					files = crewsByFile.get(crewId);
				}
				if(!files.contains(reportFolder)){
					files.add(reportFolder);
				}
				crewsByFile.put(crewId,files); //adicion de archivos por  cuadrillas
			}

			final List<MailMessage> mailsToSend = new ArrayList<MailMessage>();

			for (Iterator<Map.Entry<Long, List<File> >> iter = crewsByFile.entrySet().iterator(); iter.hasNext();) {
				Map.Entry<Long, List<File>> entry = iter.next();

				Long crewId = entry.getKey();
				List<EmployeeCrew> ec =  employeesCrewDAO.getEmployeesCrewByCrewID(crewId);

				log.debug("Se encontraron empleados "+ec.size()+ " de la crew: "+crewId);
				cycle1:
					for (EmployeeCrew employeeCrewVO : ec) {
						if(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equals(employeeCrewVO.getIsResponsible())){ // es responsable???
							mediaContactEmployeeList = mediaContactCRUDBeanLocal.getEmployeeMediaContactByEmployeeId(employeeCrewVO.getEmployee().getId()); 
							for (EmployeeMediaContactVO employeeMediaContactVo : mediaContactEmployeeList) {
								if("MAIL".equalsIgnoreCase(employeeMediaContactVo.getMediaContactType().getMediaCode())){ // Es mail?
									String email = employeeMediaContactVo.getMediaContactValue();
									if(email == null || !UtilsBusiness.isEmail(email) ){
										throw new EmailMessageException(ErrorBusinessMessages.RESPONSIBLE_MAIL_IS_NOT_CORRECT.getCode(),ErrorBusinessMessages.RESPONSIBLE_MAIL_IS_NOT_CORRECT.getMessage());
									}
									log.debug("Adicionando "+entry.getValue().size()+" archivos adjuntos al correo");
									File[] attFiles = new File[entry.getValue().size()];//numero de files en el array del hashmap
									for (int i = 0; i < entry.getValue().size(); i++) {
										File file = entry.getValue().get(i);
										if(file==null){
											log.warn("Archivo en la posicion "+i+" nulo");
											continue;
										}
										attFiles[i] = file;
									}
									mailsToSend.add(new MailMessage(email, "Work Orders", "PDF Work Orders",attFiles));
									break cycle1;
								}
							}
						}
					}
			}
			if(mailsToSend == null || mailsToSend.size() == 0 ){
				log.debug("== No existen correos asociados a la cuadrilla CoreWOBusiness/sendWorkOrdersPDFEmail");
				throw new BusinessException(ErrorBusinessMessages.NO_RECIPIENTS_TO_SEND_MAIL.getCode(),ErrorBusinessMessages.NO_RECIPIENTS_TO_SEND_MAIL.getMessage());
			}
			// Envio asincrono de mails
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						mailSenderLocal.send(mailsToSend, countryId, false);
					} catch (EmailMessageException email) {
						log.debug("== Error al tratar de enviar el mail == ", email);
					}
				}
			};
			new Thread(runnable).start();			

			for (MailMessage mailMessage : mailsToSend) {
				response.getRecipientsMail().addAll(mailMessage.getTos());
			}
			return response;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/sendWorkOrdersPDFEmail");
			throw this.manageException(ex);
		}
	}

	/**
	 * Valida la informacion correspondiente al archivo actual que se envio desde la interfaz 
	 * que este bien formateado y q la cuadrilla este correcta
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param currentFile
	 * @param reportFolder
	 * @return long
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	private long validateFileFormat(String currentFile, File reportFolder) throws BusinessException, DAOServiceException, DAOSQLException {
		log.debug("Cargando por defecto todos los archivos de todas las cuadrillas");
		final String index1 = "crew_"; //estandares del nombre del archivo: crew_{id_cuadrilla}_work_orders_{dd-MM-yyyy_HH-mm-ss}.pdf
		final String index2 = "_work_orders_";

		if(!reportFolder.exists()){
			log.error("La ruta ["+reportFolder.getAbsolutePath()+"] no es valida para la generacion de reportes");
			throw new BusinessException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());
		}
		if(currentFile==null || !currentFile.startsWith(index1) || !currentFile.contains(index2)){
			log.error("El archivo "+currentFile+" no tiene el formato valido para el envio");
			throw new BusinessException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());
		}

		String crewIdStr = currentFile.substring(currentFile.indexOf(index1)+index1.length(), currentFile.indexOf(index2));
		if(!NumberUtils.isNumber(crewIdStr)){
			log.error("El archivo "+currentFile+" no tiene el formato valido para el envio");
			throw new BusinessException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),ErrorBusinessMessages.REPORT_PATH_INVALID.getMessage());    		
		}
		long crewId = Long.parseLong(crewIdStr);
		Crew crew = crewsDAO.getCrewById(crewId); //se valida q la cuadrilla efectivamente exista
		UtilsBusiness.assertNotNull(crew, ErrorBusinessMessages.CREW_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.CREW_DOES_NOT_EXIST.getMessage());

		return crewId;

	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByCreationDate(Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByCreationDate(Date initDate, Date endDate)throws BusinessException {
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByCreationDate/CoreWOBusiness ==");
		try {

			woList = workOrderDAOBean.getWorkOrderByCreationDate(initDate,endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByCreationDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByCreationDate/CoreWOBusiness ==");
		}
		return wos;
	}


	/**
	 * @param workOrderIds
	 * @param crew
	 * @return
	 * @throws PDFException 
	 * @throws BusinessException 
	 * @see ICoreWOBusiness#generateCrewWorkOrdersPDF(List<Long>, CrewVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String generateCrewWorkOrdersPDF(List<Long> workOrderIds, CrewVO crew) throws PDFException, BusinessException {
		try{
			log.debug("== Inicia generateCrewWorkOrdersPDF/CoreWOBusiness ==");
			UtilsBusiness.assertNotNull(crew, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(crew.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			Crew crewPojo = crewsDAO.getCrewById(crew.getId());
			UtilsBusiness.assertNotNull(crewPojo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			Long maxNumberWoPerPdfFile = UtilsBusiness.getNumericSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_PDF_MAX_WORK_ORDERS_BY_PDF_FILE.getCodeEntity(), crewPojo.getDealer().getPostalCode().getCity().getState().getCountry().getId(), systemParameterDAO);

			Map<WorkOrder, WorkOrderAgenda> workOrders = workOrderDAOBean.getWorkOrdersByIdsAndCrewAssignment(workOrderIds, crew.getId(), maxNumberWoPerPdfFile);

			if(workOrders.isEmpty()){
				log.debug("Se trató de generar la planilla de visitas de varias WO que no están asignadas a la cuadrilla especificada con id: " + crew.getId());
				throw new BusinessException(ErrorBusinessMessages.NONE_OF_SELECTED_WORK_ORDERS_ARE_ASSIGNED_TO_SELECTED_CREW.getCode(), ErrorBusinessMessages.NONE_OF_SELECTED_WORK_ORDERS_ARE_ASSIGNED_TO_SELECTED_CREW.getMessage());
			}

			Employee employeeCrew = employeesCrewDAO.getEmployeeResponsibleByCrewId(crew.getId());
			
			if(employeeCrew==null ){
				throw new BusinessException(ErrorBusinessMessages.CREW_NOT_RESPONSIBLE_SPECIFIED.getCode() ,"No se encontro ningun empleado responsable de la WorkOrder de los "+crew.getEmployeesCrew().size()+" que tiene ");
			}
			return reportsGeneratorLocal.generateCrewWorkOrdersPDF(workOrders, employeeCrew);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/generateCrewWorkOrdersPDF");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateCrewWorkOrdersPDF/CoreWOBusiness ==");
		}
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String generateCrewWorkOrdersPDF(List<Long> workOrderIds, List<Long> crewIds) throws PDFException, BusinessException {
		try{
			log.debug("== Inicia generateCrewWorkOrdersPDF/CoreWOBusiness ==");
			UtilsBusiness.assertNotNull(workOrderIds, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(crewIds, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			if( workOrderIds.size() != crewIds.size() ){
				log.debug("La cantidad de wo es diferente a la cantidad de cuadrillas");
				throw new BusinessException(ErrorBusinessMessages.CORE_CR030.getCode(), ErrorBusinessMessages.CORE_CR030.getMessage());
			}
			
			Map<Long,List > crewWosMap = new HashMap<Long, List>();
			
			for( int i = 0 ; i < workOrderIds.size() ; i++ ){
				Long crewId = crewIds.get(i);
				if( !crewWosMap.containsKey( crewId ) ){
					List<Long> woIds = new ArrayList<Long>();
					woIds.add( workOrderIds.get(i) );
					crewWosMap.put(crewId, woIds);
				} else{
					List<Long> woIds = crewWosMap.get( crewId );
					woIds.add( workOrderIds.get(i) );
					crewWosMap.remove( crewId );
					crewWosMap.put(crewId, woIds);
				}
			}
			StringBuffer sb = new StringBuffer();
			Iterator iterator = crewWosMap.entrySet().iterator();
			while (iterator.hasNext()) {
				if( !sb.toString().equals("") ){
					sb.append(",");
				} 
		        Map.Entry entry = (Map.Entry)iterator.next();
		        CrewVO crew = new CrewVO();
				crew.setId( (Long) entry.getKey() );
				sb.append( generateCrewWorkOrdersPDF((List<Long>) entry.getValue(), crew) );
		    }
			//26-07-2011 se comenta para hacer mejor uso de iterador de mapa	
			return sb.toString();
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/generateCrewWorkOrdersPDF");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina generateCrewWorkOrdersPDF/CoreWOBusiness ==");
		}
	}

	/**
	 * Obtiene el detalle de una orden de trabajo (Caso de uso 25)
	 * @param id - Id de la orden de trabajo
	 * @return Orden de trabajo solicitada
	 * @author jcasas
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderVO getWorkOrderByID(Long id)throws BusinessException {
		UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		WorkOrderVO wosVO = null;
		WorkOrder wo = null;

		log.debug("== Inicia getWorkOrderByID/CoreWOBusiness ==");
		try {
			wo = workOrderDAOBean.getWorkOrderByID(id);
			if(wo == null){
				return null;
			}
			wosVO = UtilsBusiness.copyObject(WorkOrderVO.class, wo);
			this.fillDataWorkOrderDetailHelper(wosVO);
			return wosVO;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByID");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByID/CoreWOBusiness ==");
		}
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByDealerPostalCodeCreationDate(DealerVO, PostalCodeVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeCreationDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate) throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(postalCode.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByDealerPostalCodeCreationDate/CoreWOBusiness ==");
		try {

			woList = workOrderDAOBean.getWorkOrderByDealerPostalCodeCreationDate(UtilsBusiness.copyObject(Dealer.class, dealer),
					UtilsBusiness.copyObject(PostalCode.class, postalCode),
					initDate,endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealerPostalCodeCreationDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerPostalCodeCreationDate/CoreWOBusiness ==");
		}
		return wos;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#reportDifficultyToWorkOrder(co.com.directv.sdii.model.dto.TrayWOManagmentDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void reportDifficultyToWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException {

		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkOrderId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorderReason(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorderReason().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getCommentManagment(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		/*
		 * Para este caso de uso:
		 * 1. Validar que el estado actual puede llevar a PENDIENTE
		 * 2. Actualizar la tabla de WorkOrders cambiando el estado a: PENDIENTE
		 * 3. Agregar un nuevo registro en la tabla WorkOrderNovedades
		 * 4. Eliminar agenda, y asignaciones de cuadrilla o progrmas
		 */

		log.debug("== Inicia reportDifficultyToWorkOrder/CoreWOBusiness ==");
		try {

			//Ejecutando 1:
			//Ejecutar la operación de la máquina de estados que permite validar el cambio
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID(trayWOManagmentDTO.getWorkOrderId());
			this.changeToFalseAgendationExpired(workOrder);
			WorkorderStatus woStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());

			WorkOrderVO workOrderTmp = new WorkOrderVO();
			workOrderTmp.setWorkorderStatusByActualStatusId(workOrder.getWorkorderStatusByActualStatusId());
			workOrderTmp.setWorkorderStatusByPreviusStatusId(workOrder.getWorkorderStatusByPreviusStatusId());

			this.stateMachine.validateStatusChangeWorkOrder(workOrderTmp, woStatus.getId());
			
			//Ejecutando 2 y 3:
			WorkorderReason workorderReasonFromBD = woReasonDAO.getWorkorderReasonByID(trayWOManagmentDTO.getWorkorderReason().getId());
			//jnova 10-10-2011 se pone en nulo el process source porque se gestiona por HSP
			workOrder.setProcessSourceId(null);
			WorkOrderDTO woDTO = new WorkOrderDTO();
			woDTO.setWorkOrder(workOrder);
			WoStatusHistory woStatusHistory = new WoStatusHistory();
			String woDescription = this.validWorkOrderStatusPending(woStatus,workOrder.getWorkorderStatusByActualStatusId(),workOrder.getWoDescription());
			this.changeWorkOrderStatus(woDTO, 
					                   woStatus.getId(), 
					                   workorderReasonFromBD, 
					                   trayWOManagmentDTO.getCommentManagment(),
					                   trayWOManagmentDTO.getUserId(), 
					                   woStatusHistory,
					                   woDescription);
			
			List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
			workOrderList.add(workOrder);
			
			User user = userDao.getUserById(trayWOManagmentDTO.getUserId());

			SystemParameter optimusSP = this.configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.OPTIMUS_DISPATCHER_ACTIVE.getCodeEntity(), user.getCountry().getId());
			Boolean isOptimusActive = (optimusSP != null && optimusSP.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()));
			
			this.withDrawWOFromTechnicianInOptimus(isOptimusActive, user, workOrderList);
			
			//08-18-2011 jalopez se inactivan todas las asignaciones que tiene la WO
			//se envia el parametro en false ya que no se debe desactivar la asignacion
			this.deleteWOAssignmentAgendaCrewAndProgram(  trayWOManagmentDTO.getWorkOrderId(), false,woStatusHistory );

			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(workOrder);
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(workOrder, user.getId());
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/reportDifficultyToWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina reportDifficultyToWorkOrder/CoreWOBusiness ==");
		}
	}

	/**
	 * Cambia el estado de una orden de trabajo, realizando los procesos necesarios:<br>
	 * 1. pasar el estado actual a estado anterior<br>
	 * 2. Asignar a estado actual el nuevo estado que se debe especificar usando las constantes de <code>CodesBusinessEntityEnum.WORKORDER_STATUS_</code><br>
	 * 3. Generar un registro en el histórico de estados de la workOrder<br>
	 * @param woDTO identificador de la workOrder a la que se le actualizará el estado
	 * @param newStatusId identificador del nuevo estado dado por <code>CodesBusinessEntityEnum.WORKORDER_STATUS_</code>
	 * @param woReason Razón del cambio de estado de la WorkOrder
	 * @param description Descripción del cambio de estado, este es necesario para persistir el registro de histórico
	 * @param userId id del usuario
	 * @return WorkOrder actualizada con los nuevos estados 
	 * @throws BusinessException en caso que no exista la WorkOrder con el identificador especificado
	 * @author jjimenezh 2010-04-20
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrder changeWorkOrderStatus(WorkOrderDTO woDTO, 
			                               Long newStatusId, 
			                               WorkorderReason woReason, 
			                               String description,
			                               Long userId,
			                               WoStatusHistory woStatusHistory,
			                               String woDescription) throws BusinessException{
		log.debug("== Inicia changeWorkOrderStatus/CoreWOBusiness ==");
		try{
			//Actualizacion del estado de la Base de Datos.
			woDTO.getWorkOrder().setWorkorderStatusByPreviusStatusId(woDTO.getWorkOrder().getWorkorderStatusByActualStatusId());
			WorkorderStatus newWorkorderStatus = workorderStatusDAO.getWorkorderStatusByID(newStatusId);
			UtilsBusiness.assertNotNull(newWorkorderStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
			woDTO.getWorkOrder().setWorkorderStatusByActualStatusId(newWorkorderStatus);
			if(woDescription!= null && !woDescription.trim().isEmpty()){
				woDTO.getWorkOrder().setWoDescription(woDescription);
			}
			workOrderDAOBean.updateWorkOrder(woDTO.getWorkOrder(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE.getCodeEntity());
			
			Date woAgenda = null;
			if(woDTO.getWoAgenda() != null){
				if( woDTO.getWoAgenda().getAgendationDate() != null ){		
					woAgenda = woDTO.getWoAgenda().getAgendationDate();
				}
			}
			Long serviceProviderId=null;
			if(woDTO.getDealerAssignmentCode() != null && woDTO.getDealerAssignmentCode().longValue() > 0 ){
				serviceProviderId=woDTO.getDealerAssignmentCode();
			}
			
			//String ibsHistoryEventCode = this.coreWOServiceBroker.updateWorkOrderStatus(woDTO, woReason);
			EditCustomerWorkOrderDTO editCustomerWorkOrderDTO = new EditCustomerWorkOrderDTO(woDTO.getWorkOrder().getWoCode(),
																		                     woDTO.getWorkOrder().getCountry().getCountryCode(),
																		                     serviceProviderId,
																		                     null,
																		                     woReason.getWorkorderReasonCode(),
																		                     woAgenda,
																		                     woDescription);
			String ibsHistoryEventCode = manageWorkForceServiceBroker.editCustomerWorkOrder(editCustomerWorkOrderDTO);
			
			//Creacion del historico del cambio de estado de la WO
			WoStatusHistory woStatusHistory2 = this.createWoStatusHistoryHSP(woDTO.getWorkOrder(), woReason, description, newWorkorderStatus,ibsHistoryEventCode, userId);	
			
			woStatusHistory.setId(woStatusHistory2.getId());
			woStatusHistory.setDescription(woStatusHistory2.getDescription());
			woStatusHistory.setIbsHistoryCodeEvent(woStatusHistory2.getIbsHistoryCodeEvent());
			woStatusHistory.setStatusDate(woStatusHistory2.getStatusDate());
			woStatusHistory.setTypeChange(woStatusHistory2.getTypeChange());
			woStatusHistory.setUser(woStatusHistory2.getUser());
			woStatusHistory.setWorkOrder(woStatusHistory2.getWorkOrder());
			woStatusHistory.setWorkorderReason(woStatusHistory2.getWorkorderReason());
			woStatusHistory.setWorkorderStatus(woStatusHistory2.getWorkorderStatus());
			
			//CC Creacion de Contacts jalopez			
			this.createWoContact(woDTO.getWorkOrder().getId(), woStatusHistory, woReason,userId);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatus/CoreWOBusiness ==");
		}
		return woDTO.getWorkOrder();
	}
	
	/**
	 * Cambia el estado de una orden de trabajo sin notificar a ibs por que previamente ya se habia enviado un agendamiento
	 * @param workOrderId identificador de la workOrder a la que se le actualizará el estado
	 * @param newStatusId identificador del nuevo estado dado por <code>CodesBusinessEntityEnum.WORKORDER_STATUS_</code>
	 * @param woReason Razón del cambio de estado de la WorkOrder
	 * @param description Descripción del cambio de estado, este es necesario para persistir el registro de histórico
	 * @return WorkOrder actualizada con los nuevos estados
	 * @throws DAOServiceException en caso de error en la capa de persistencia
	 * @throws DAOSQLException en caso de error relacionado con sintaxis de consultas SQL 
	 * @throws BusinessException en caso que no exista la WorkOrder con el identificador especificado
	 * @author jjimenezh 2010-04-20
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrder changeWorkOrderStatusWithoutIbsNotificationScheduleCSR(WorkOrderDTO woDTO, Long newStatusId, WorkorderReason woReason, String description,Long userId) throws BusinessException{
		log.debug("== Inicia changeWorkOrderStatus/CoreWOBusiness ==");
		
		try{
			
			//Actualizacion del estado de la Base de Datos.
			woDTO.getWorkOrder().setWorkorderStatusByPreviusStatusId(woDTO.getWorkOrder().getWorkorderStatusByActualStatusId());
			WorkorderStatus newWorkorderStatus = workorderStatusDAO.getWorkorderStatusByID(newStatusId);
			UtilsBusiness.assertNotNull(newWorkorderStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
			woDTO.getWorkOrder().setWorkorderStatusByActualStatusId(newWorkorderStatus);
			workOrderDAOBean.updateWorkOrder(woDTO.getWorkOrder(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE.getCodeEntity());
			
			//Creacion del historico del cambio de estado de la WO
			this.createWoStatusHistory( woDTO.getWorkOrder(), 
										null, //woReason -> no usa utiliza el que viene como parametro
										description, 
										newWorkorderStatus,
										null,
										CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP_CSR_SCHEDULE.getCodeEntity(), 
										userId
										);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatus/CoreWOBusiness ==");
		}
		return woDTO.getWorkOrder();
		
	}
	
	/**
	 * 
	 * Metodo: Crea el historico de cambio de estado
	 * de la WorkOrder
	 * @param workOrder
	 * @param woReason
	 * @param description
	 * @param newWorkorderStatus
	 * @param ibsHistoryEventCode
	 * @param typeChange
	 * @return WoStatusHistory
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WoStatusHistory createWoStatusHistory( WorkOrder 		workOrder,
			                                      WorkorderReason 	woReason, 
			                                      String 			description, 
			                                      WorkorderStatus 	newWorkorderStatus,
			                                      String 			ibsHistoryEventCode,
			                                      String 			typeChange, 
			                                      Long userId) 
    throws BusinessException{
		log.debug("== Inicia createwoStatusHistory/CoreWOBusiness ==");	
		WoStatusHistory woStatusHistory = null;
		try{
			woStatusHistory = new WoStatusHistory();
			woStatusHistory.setDescription(description);
			woStatusHistory.setStatusDate(UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrder.getUserId(), userDAO));
			woStatusHistory.setWorkOrder(workOrder);
			woStatusHistory.setWorkorderReason(woReason);
			woStatusHistory.setWorkorderStatus(newWorkorderStatus);
			woStatusHistory.setIbsHistoryCodeEvent(ibsHistoryEventCode);
			woStatusHistory.setTypeChange(typeChange);
			woStatusHistory.setUser(new User(userId));
			woStatusHistoryDAO.createWoStatusHistory(woStatusHistory);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación createwoStatusHistory/createwoStatusHistory");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createwoStatusHistory/CoreWOBusiness ==");
		}
		return woStatusHistory;
	}
	
	/**
	 * 
	 * Metodo: Crea el historico de cambio de estado
	 * de la WorkOrder
	 * @param workOrder
	 * @param woReason
	 * @param description
	 * @param newWorkorderStatus
	 * @param ibsHistoryEventCode
	 * @return WoStatusHistory
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WoStatusHistory createWoStatusHistoryHSP(WorkOrder workOrder,
			                                     WorkorderReason woReason, 
			                                     String description, 
			                                     WorkorderStatus newWorkorderStatus,
			                                     String ibsHistoryEventCode,
			                                     Long userId)     
   throws BusinessException{
		log.debug("== Inicia createwoStatusHistory/CoreWOBusiness ==");	
		WoStatusHistory woStatusHistory = null;
		try{
			woStatusHistory = new WoStatusHistory();
			woStatusHistory.setDescription(description);
			woStatusHistory.setStatusDate(UtilsBusiness.getCurrentTimeZoneDateByUserId( workOrder.getUserId(), userDAO));
			woStatusHistory.setWorkOrder(workOrder);
			woStatusHistory.setWorkorderReason(woReason);
			woStatusHistory.setWorkorderStatus(newWorkorderStatus);
			woStatusHistory.setIbsHistoryCodeEvent(ibsHistoryEventCode);
			woStatusHistory.setTypeChange(CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_HSP.getCodeEntity());
			woStatusHistory.setUser(new User(userId));

			woStatusHistoryDAO.createWoStatusHistory(woStatusHistory);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación createwoStatusHistory/createwoStatusHistory");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createwoStatusHistory/CoreWOBusiness ==");
		}
		return woStatusHistory;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWoContact(Long workOrderId, WoStatusHistory woStatusHistory, WorkorderReason woReason, Long userId) throws BusinessException{
		log.debug("== Inicia createWoContact/CoreWOBusiness ==");	
		try{
			//CC Creacion de Contacts jalopez			
			ContactDTO contactDTO = new ContactDTO();
			contactDTO.setWoId(workOrderId);
			contactDTO.setWoStatusHistoryId(woStatusHistory.getId());
			contactDTO.setWoReasonId( woReason.getId() );
			contactDTO.setUserId(userId);
			contactBusiness.createContactCore( contactDTO );
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación createWoContact/createWoContact");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createWoContact/CoreWOBusiness ==");
		}		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrder changeWorkOrderHistoryCreateContact(Long workOrderId, Long newStatusId, WorkorderReason woReason, String description,Long userId) throws BusinessException{
		log.debug("== Inicia changeWorkOrderHistoryContact/CoreWOBusiness ==");	
		WorkOrder workOrder = null;
		try{
			workOrder = workOrderDAOBean.getWorkOrderByID(workOrderId);
			if(workOrder == null){
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage() + workOrderId);
			}
			workOrder.setWorkorderStatusByPreviusStatusId(workOrder.getWorkorderStatusByActualStatusId());
			WorkorderStatus newWorkorderStatus = workorderStatusDAO.getWorkorderStatusByID(newStatusId);
			UtilsBusiness.assertNotNull(newWorkorderStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
			workOrder.setWorkorderStatusByActualStatusId(newWorkorderStatus);
			WoStatusHistory woStatusHistory = this.createWoStatusHistoryHSP(workOrder, woReason, description, newWorkorderStatus,null, userId);
			this.createWoContact(workOrderId, woStatusHistory, woReason,userId);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación changeWorkOrderHistoryContact/changeWorkOrderHistoryCreateContact");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderHistoryContact/CoreWOBusiness ==");
		}
		return workOrder;
	}

	/**
	 * @author jjimenezh 2010-04-20
	 * @see ICoreWOBusiness#changeWorkOrderStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeWorkOrderStatus(Long customerCode, 
			Long countryId, 
			String workOrderCode, 
			String workOrderReason2ChangeCode, 
			String workOrderReason2CompleteCode, 
			String flag2KnowProcess2Execute, 
			String newWorkOrderStatusCode) throws BusinessException {

		UtilsBusiness.assertNotNull(customerCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderReason2ChangeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderReason2CompleteCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(flag2KnowProcess2Execute, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(newWorkOrderStatusCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		/*
		 * Para ejecutar este caso de uso:
		 * 1. Verificar que existe la orden de trabajo
		 * 2. Verificar que se puede realizar la transición de un estado a otro.
		 * 3. Verificar que el cliente existe
		 * 4. Verificar que la orden de trabajo está asociada a ese cliente
		 * 5. Verificar que la workorder reason existe
		 * 6. Verificar que el nuevo estado exista dado el código
		 * 7. Ejecutar el caso de uso ADS - 33 para actualizar el estado en IBS
		 */

		log.debug("== Inicia changeWorkOrderStatus/CoreWOBusiness ==");
		try {

			//Ejecutando 1:
			Country country = countryDao.getCountriesByID(countryId);
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByCodeAndCountry(workOrderCode, country.getCountryCode());

			if(workOrder == null){
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}

			//Ejecutando 2
			//Ejecutar la operación de la máquina de estados que permite validar el cambio
			WorkorderStatus woStatus = workorderStatusDAO.getWorkorderStatusByIBS6StatusCode(newWorkOrderStatusCode);
			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage() + " no existe dado el código de ibs: " + newWorkOrderStatusCode);

			WorkOrderVO workOrderTmp = new WorkOrderVO();
			workOrderTmp.setWorkorderStatusByActualStatusId(workOrder.getWorkorderStatusByActualStatusId());
			workOrderTmp.setWorkorderStatusByPreviusStatusId(workOrder.getWorkorderStatusByPreviusStatusId());

			this.stateMachine.validateStatusChangeWorkOrder(workOrderTmp,woStatus.getId());


			//Ejecutando 3:
				Customer customer = customerDAO.getCustomerByCode(""+customerCode);
				if(customer == null){
					throw new BusinessException(ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage() + " No se encontró el cliente por el código especificado: " + customerCode);
				}

				//Ejecutando 4:
				if(workOrder.getCustomer().getId().longValue() != customer.getId().longValue()){
					throw new BusinessException(ErrorBusinessMessages.WORKORDER_CUSTOMERID_AND_CUSTOMERID_DOES_NOT_MATCH.getCode(), ErrorBusinessMessages.WORKORDER_CUSTOMERID_AND_CUSTOMERID_DOES_NOT_MATCH.getMessage());
				}

				//Ejecutando 5:
				WorkorderReason workorderReason = woReasonDAO.getWorkorderReasonByCode(workOrderReason2ChangeCode);
				if(workorderReason == null){
					throw new BusinessException(ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getMessage());
				}

				//Ejecutando 6:
				Long newStatusId = woStatus.getId();
				String comment = workorderReason.getWorkorderReasonName();
				WorkOrderDTO woDTO = new WorkOrderDTO();
				woDTO.setWorkOrder(workOrder);
				workOrder = this.changeWorkOrderStatus(woDTO, newStatusId, workorderReason, comment, 1L, new WoStatusHistory(),null);

				//Se encapsula la informacion en un DTO para pasarlo al broker
				
				//Ejecutando 7:


		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatus/CoreWOBusiness ==");
		}
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByPreviusStatus(WorkorderStatusVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByPreviusStatus(WorkorderStatusVO previusStatus)throws BusinessException {
		UtilsBusiness.assertNotNull(previusStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(previusStatus.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByPreviusStatus/CoreWOBusiness ==");
		try {

			woList = workOrderDAOBean.getWorkOrderByPreviusStatus(UtilsBusiness.copyObject(WorkorderStatus.class, previusStatus));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByPreviusStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByPreviusStatus/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByActualStatus(WorkorderStatusVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByActualStatus(WorkorderStatusVO actualStatus) throws BusinessException {
		UtilsBusiness.assertNotNull(actualStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualStatus.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByActualStatus/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por estado actual
			woList = workOrderDAOBean.getWorkOrderByActualStatus(actualStatus);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByPreviusStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByActualStatus/CoreWOBusiness ==");
		}
		return wos;

	}

	/**
	 * Cancela una orden de trabajo y registra una novedad respecto a la cancelacion
	 * @param workorder Orden de trabajo a ser cancelada
	 * @param comment Comentarios acerca de la cancelacion de la orden de trabajo
	 * @see ICoreWOBusiness#cancelWorkOrder(WorkOrderVO, String)
	 * @author jcasas
	 * @throws BusinessException Se lanza cuando hay un problema transaccional
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cancelWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException {
		log.debug("== Inicia cancelWorkOrder/CoreWOBusiness ==");
		try {
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorder(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorder().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getCommentManagment(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorderReason(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorderReason().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			WorkorderStatus statusCanceled = this.workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
			UtilsBusiness.assertNotNull(statusCanceled, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			WorkOrder woPojo = this.workOrderDAOBean.getWorkOrderByID(trayWOManagmentDTO.getWorkorder().getId());
			this.changeToFalseAgendationExpired(woPojo);
			UtilsBusiness.assertNotNull(woPojo, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			trayWOManagmentDTO.setWorkorder( UtilsBusiness.copyObject(WorkOrderVO.class, woPojo) );

			if(!this.stateMachine.validateStatusChangeWorkOrder(trayWOManagmentDTO.getWorkorder(), statusCanceled.getId())){
				log.debug(ErrorBusinessMessages.WO_NO_CAN_CHANGE_STATUS.getMessage());
				throw new BusinessException(ErrorBusinessMessages.WO_NO_CAN_CHANGE_STATUS.getCode(), ErrorBusinessMessages.WO_NO_CAN_CHANGE_STATUS.getMessage());
			}
			WorkorderReason reasonPojo = this.woReasonDAO.getWorkorderReasonByID( trayWOManagmentDTO.getWorkorderReason().getId() );
			
			woPojo.setProcessSourceId(null);
			//Cambio de estado a cancelada
			WorkOrderDTO woDTO = new WorkOrderDTO();
			
			woDTO.setWorkOrder(woPojo);
			woPojo = this.changeWorkOrderStatusToCancel(woDTO, statusCanceled.getId());
			manageWorkForceServiceBroker.cancelWorkOrder(woDTO.getWorkOrder().getWoCode(), trayWOManagmentDTO.getCommentManagment(), reasonPojo.getWorkorderReasonCode(),woDTO.getWorkOrder().getCountry().getCountryCode(), woPojo.getCustomer().getCustomerCode());
			//String ibsHistoryEventCode = coreWOServiceBroker.notifyWoCancelation(woDTO.getWorkOrder().getWoCode(), trayWOManagmentDTO.getCommentManagment(), reasonPojo.getWorkorderReasonCode(),woDTO.getWorkOrder().getCountry().getCountryCode());

			//Creacion del historico del cambio de estado de la WO
			WoStatusHistory woStatusHistory = this.createWoStatusHistoryHSP(woDTO.getWorkOrder(), reasonPojo, trayWOManagmentDTO.getCommentManagment(), statusCanceled, null, trayWOManagmentDTO.getUserId());	
			
			//08-07-2011 jnova se agrega inactivacion de todas las asignaciones de la WO
			//se envia el parametro true ya que se debe desactivar la asignacion
			this.deleteWOAssignmentAgendaCrewAndProgram( trayWOManagmentDTO.getWorkorder().getId(), true ,woStatusHistory);
			//jnova 10-10-2011 se pone en nulo el process source porque se gestiona por HSP
			
			this.createWoContact(woDTO.getWorkOrder().getId(), woStatusHistory, reasonPojo, trayWOManagmentDTO.getUserId());

			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(woPojo);
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(woPojo, trayWOManagmentDTO.getUserId());

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/cancelWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina cancelWorkOrder/CoreWOBusiness ==");
		}
	}

	/**
	 * Cancela una orden de trabajo proveniente desde IBS
	 * @param workOrderDTO Orden de trabajo a ser cancelada
	 * @author ltaboada
	 * @throws BusinessException Se lanza cuando hay un problema transaccional
	 */
	@Deprecated
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void cancelWorkOrderFromIBS(WorkOrderDTO workOrderDTO, String countryCode) throws BusinessException {
		log.debug("== Inicia cancelWorkOrderFromIBS/CoreWOBusiness ==");
		try {

			WorkorderStatus statusCanceled = this.workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
			UtilsBusiness.assertNotNull(statusCanceled, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			WorkOrder woPojo = this.workOrderDAOBean.getWorkOrderByID(workOrderDTO.getWorkOrder().getId());
			UtilsBusiness.assertNotNull(woPojo, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			
			if(!woPojo.getWorkorderStatusByActualStatusId().getWoStateCode().equals(statusCanceled.getWoStateCode())){
				WoProcessSource woProcessSource = getWoProcessSourceByWoCodeActualStatus(workOrderDTO.getWorkOrder().getWoCode(), statusCanceled.getWoStateCode());
				if( woProcessSource != null ){
					woPojo.setProcessSourceId(woProcessSource.getId());
				}
			}
			
			//Cambio de estado a cancelada
			WorkOrderDTO woDTO = new WorkOrderDTO();
			
			woDTO.setWorkOrder(woPojo);
			woPojo.setIbsActualStatus(workOrderDTO.getWorkOrder().getIbsActualStatus());
			woPojo = this.changeWorkOrderStatusToCancel(woDTO, statusCanceled.getId());
						
			//Creacion del historico del cambio de estado de la WO
			/*
			 * Se coloca el usuario de ibs
			 * */
			
			Long userId=UtilsBusiness.getUserIdAdmin(userDao,systemParameterDAO,countryDao.getCountriesByCode(countryCode).getId());
			
			String typeChange = "";
			if(workOrderDTO.isUpdateWorkOrderIBSToHSP()){
				typeChange = CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_UPDATE.getCodeEntity();
			}else{
				typeChange = CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_IBS.getCodeEntity();
			}

			WoStatusHistory woStatusHistory = this.createWoStatusHistory(	woDTO.getWorkOrder(), //workOrder
																			null, 			      //woReason
																			null, 				  //description
																			statusCanceled, 	  //newWorkorderStatus
																			null,				  //ibsHistoryEventCode
																			typeChange, 		  //typeChange
																			userId 				  //userId
																			);
			
			//26-10-2011 ltaboada se agrega inactivacion de todas las asignaciones de la WO
			//se envia el parametro true ya que se debe desactivar la asignacion
			this.deleteWOAssignmentAgendaCrewAndProgram( woPojo.getId(), true,woStatusHistory );
			//26-10-2011 ltaboada se pone en nulo el process source porque se gestiona por HSP
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/cancelWorkOrderFromIBS");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina cancelWorkOrderFromIBS/CoreWOBusiness ==");
		}
	}
	
	private WorkOrder changeWorkOrderStatusToCancel(WorkOrderDTO woDTO, Long newStatusId) throws BusinessException{
		log.debug("== Inicia changeWorkOrderStatus/CoreWOBusiness ==");
		try{
			//Actualizacion del estado de la Base de Datos.
			woDTO.getWorkOrder().setWorkorderStatusByPreviusStatusId(woDTO.getWorkOrder().getWorkorderStatusByActualStatusId());
			WorkorderStatus newWorkorderStatus = workorderStatusDAO.getWorkorderStatusByID(newStatusId);
			UtilsBusiness.assertNotNull(newWorkorderStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());
			woDTO.getWorkOrder().setWorkorderStatusByActualStatusId(newWorkorderStatus);
			
			// Se coloca la fecha de cancelación de la WO. Si la WO se cancelo en HSP se toma el usuario que la cancelo, 
			// Si se cancelo en IBS se toma el usuario de IBS configurado en la lista de parametros del sistema
			if(woDTO.getWorkOrder().getUserId() != null) {
				woDTO.getWorkOrder().setCancelationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId( woDTO.getWorkOrder().getUserId(), userDAO));
			}else {
				woDTO.getWorkOrder().setCancelationDate(new Date());
			}
			workOrderDAOBean.updateWorkOrder(woDTO.getWorkOrder(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE.getCodeEntity());
						
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatus/CoreWOBusiness ==");
		}
		return woDTO.getWorkOrder();
	}

	/**
	 * Hace el procedimiento similar al metodo cancelWorkOrder pero no con una sola wo sino con una lista de wo
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param workorders
	 * @param reason
	 * @param comment
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cancelWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException {
		log.debug("== Inicia cancelWorkOrders/CoreWOBusiness ==");
		try {
			
			UtilsBusiness.assertNotNull(trayWOManagmentDTOList, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			if(trayWOManagmentDTOList.size() == 0){
				log.error("== Parametro obligatorio vacio ==");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			UtilsBusiness.assertNotNull(trayWOManagmentDTOList.get(0).getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Country country = countryDao.getCountriesByID(trayWOManagmentDTOList.get(0).getCountryId());
			this.validateMaxWOToAssignOrUnassignTechnician(trayWOManagmentDTOList.size(), country.getId());
			
			List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
			for (TrayWOManagmentDTO dto : trayWOManagmentDTOList) {
				WorkOrder woPojo = this.workOrderDAOBean.getWorkOrderByID(dto.getWorkorder().getId());
				workOrderList.add(woPojo);
			}

			User user = userDAO.getUserById(trayWOManagmentDTOList.get(0).getUserId());
			
			//Consultar parametro de sistema para saber si debe informar a optimus
			SystemParameter optimusSP = this.configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.OPTIMUS_DISPATCHER_ACTIVE.getCodeEntity(), user.getCountry().getId());
			Boolean isOptimusActive = (optimusSP != null && optimusSP.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()));
			
			this.withDrawWOFromTechnicianInOptimus(isOptimusActive, user , workOrderList);
						

			for (TrayWOManagmentDTO dto : trayWOManagmentDTOList) {
				cancelWorkOrder(dto);
			}
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación cancelWorkOrders/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina cancelWorkOrders/CoreWOBusiness ==");
		}
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByActualType(DealerVO, WoTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByActualType(DealerVO dealer, WoTypeVO actualType)throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualType.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByActualType/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por tipo actual
			woList = workOrderDAOBean.getWorkOrderByActualType(UtilsBusiness.copyObject(Dealer.class,dealer),
					UtilsBusiness.copyObject(WoType.class,actualType));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByActualType");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByActualType/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByActualStatus(DealerVO, WorkorderStatusVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByActualStatus(DealerVO dealer, WorkorderStatusVO actualStatus)throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualStatus.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByActualStatus/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por estado actual
			woList = workOrderDAOBean.getWorkOrderByActualStatus(UtilsBusiness.copyObject(Dealer.class,dealer),
					UtilsBusiness.copyObject(WorkorderStatus.class,actualStatus));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByActualStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByActualStatus/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByActualAndPreviusType(DealerVO, WoTypeVO, WoTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByActualAndPreviusType(DealerVO dealer, WoTypeVO actualType, WoTypeVO previusType)throws BusinessException  {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualType.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByActualAndPreviusType/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por tipos de WO y dealer
			woList = workOrderDAOBean.getWorkOrderByActualAndPreviusType(UtilsBusiness.copyObject(Dealer.class,dealer),
					UtilsBusiness.copyObject(WoType.class,actualType),UtilsBusiness.copyObject(WoType.class,previusType));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByActualStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByActualAndPreviusType/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByCustomer(CustomerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByCustomer(CustomerVO customer) throws BusinessException {
		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByCustomer/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por Customer
			woList = workOrderDAOBean.getWorkOrderByCustomer(UtilsBusiness.copyObject(Customer.class,customer));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByCustomer");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByCustomer/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByActualAndPreviusType(WoTypeVO, WoTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByActualAndPreviusType(WoTypeVO actualType, WoTypeVO previusType)throws BusinessException {
		UtilsBusiness.assertNotNull(actualType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(actualType.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(previusType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(previusType.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByActualAndPreviusType/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por tipos de WO
			woList = workOrderDAOBean.getWorkOrderByActualAndPreviusType(UtilsBusiness.copyObject(WoType.class,actualType),
					UtilsBusiness.copyObject(WoType.class,previusType));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByActualAndPreviusType");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByActualAndPreviusType/CoreWOBusiness ==");
		}
		return wos;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#rejectWorkOrder(co.com.directv.sdii.model.dto.TrayWOManagmentDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void rejectWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException {
		log.debug("== Inicia rejectWorkOrder/CoreWOBusiness ==");
		try {
			//Valida que los campos no sean nulos
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkOrderId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getCommentManagment(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorderReason(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorderReason().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			WorkOrder workOrderPojo = workOrderDAOBean.getWorkOrderByID(trayWOManagmentDTO.getWorkOrderId());
			this.changeToFalseAgendationExpired(workOrderPojo);
			//Valida que la orden de trabajo exista
			if(workOrderPojo == null){
				log.debug(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}
			//Cambiar el estado de la orden de trabajo
			//Validar con mï¿½quina de estados si se puede pasar del estado actual a pendiente.
			WorkorderStatus woNewStatus  = this.workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity());
			WorkOrderVO workOrderTmp = new WorkOrderVO();
			workOrderTmp.setWorkorderStatusByActualStatusId(workOrderPojo.getWorkorderStatusByActualStatusId());
			this.stateMachine.validateStatusChangeWorkOrderByCodes(workOrderTmp, woNewStatus.getWoStateCode());   
			WorkOrderDTO woDTO = new WorkOrderDTO();
			//jnova 10-10-2011 se pone en nulo el process source porque se gestiona por HSP
			workOrderPojo.setProcessSourceId(null);
			woDTO.setWorkOrder(workOrderPojo);
			WorkorderReason woReason = woReasonDAO.getWorkorderReasonByID(trayWOManagmentDTO.getWorkorderReason().getId());
			UtilsBusiness.assertNotNull(woReason, ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getMessage());
			
			WoStatusHistory woStatusHistory = new WoStatusHistory();
			String woDescription = this.validWorkOrderStatusPending(woNewStatus,workOrderPojo.getWorkorderStatusByActualStatusId(),workOrderPojo.getWoDescription());
			workOrderPojo = this.changeWorkOrderStatus(woDTO, woNewStatus.getId(), UtilsBusiness.copyObject(WorkorderReason.class, woReason) , trayWOManagmentDTO.getCommentManagment(),trayWOManagmentDTO.getUserId(), woStatusHistory ,woDescription);
		
			
			List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
			workOrderList.add(workOrderPojo);
			User user = userDAO.getUserById(trayWOManagmentDTO.getUserId());
			
			SystemParameter optimusSP = this.configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.OPTIMUS_DISPATCHER_ACTIVE.getCodeEntity(), user.getCountry().getId());
			Boolean isOptimusActive = (optimusSP != null && optimusSP.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()));
			
			this.withDrawWOFromTechnicianInOptimus(isOptimusActive, user, workOrderList);
			
			//08-07-2011 jnova se inactivan todas las asignaciones que tiene la WO
			//se envia el parametro true ya que se debe desactivar la asignacion
			this.deleteWOAssignmentAgendaCrewAndProgram(  workOrderPojo.getId(), true,woStatusHistory );
			
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(workOrderPojo);
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(workOrderPojo, user.getId());
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/rejectWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina rejectWorkOrder/CoreWOBusiness ==");
		}
	}
	
	/**
	 * @see ICoreWOBusiness#addWorkOrder()
	 * @author jjimenezh 2010-06-03
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<com.directvla.schema.crm.common.v1_1.WorkOrder> getWorkordersFromIBS(String countryCode) throws BusinessException{
		log.debug("== Inicia getWorkordersFromIBS/CoreWOBusiness ==");
		try {
			List<com.directvla.schema.crm.common.v1_1.WorkOrder> workOrdersDto = coreWOServiceBroker.importWorkOrders(countryCode);
			return workOrdersDto;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkordersFromIBS");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkordersFromIBS/CoreWOBusiness ==");
		}
	}

	/**
	 * Metodo: Convierte la work order de IBS en una de SDII
	 * @param ibsWo
	 * @param countryCode
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WorkOrderDTO convertIbsWoIntoSdiiWo(co.com.directv.sdii.dto.esb.WorkOrder ibsWorkOrder, String countryCode)throws BusinessException{
		log.debug("== Inicia convertIbsWoIntoSdiiWo/CoreWOBusiness ==");
		try {
			WorkOrderDTO workOrderDto = coreWOServiceBroker.convertIbsWoIntoSdiiWo(ibsWorkOrder, countryCode);
			return workOrderDto;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion convertIbsWoIntoSdiiWo/getWorkordersFromIBS");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina convertIbsWoIntoSdiiWo/CoreWOBusiness ==");
		}
	}

	
	/**
	 * @see ICoreWOBusiness#addWorkOrder()
	 * @author jjimenezh 2010-06-03
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persistWorkOrder(String countryCode, WorkOrderDTO workOrderDto) throws BusinessException{
		/*
		 * Para realizar este caso de uso:
		 * 1. Validar que la work order consultada no exista aún en el sistema, si existe, se debe lanzar un excepción de work order ya existe en el sistema
		 * 2. Obtener los datos de la work order invocando el servicio de ibs
		 * 3. Si alguno de los services dentro de la work order consiste en una instalación nueva, debe venir un Código del Cliente (IBS) no registrado en el sistema
		 * 4. Las fechas ingresadas debe ser válida e igual o inferior a la fecha del día actual
		 * 5. Los Códigos de Servicios deben corresponder a servicios registrados en el sistema
		 * 6. El Código Postal de debe venir en la estructura de códigos correcta
		 * 7. Al crearse las Work Orders en el sistema, los cambios de estado deben manejarse en la siguiente manera:
		 * 		a. Si no viene pre agendada en el sistema debe quedar el estado ACTIVA para que se asigne de forma manual y en IBS debe quedar en estado A (ACTIVA)
		 * 		b. Si viene pre agendada en el sistema debe quedar el estado ACTIVA y en IBS debe quedar en estado AG (AGENDADO), se debe crear el registro en WO agenda
		 * 8. Se crea la shipping order asociada a la work order
		 * 9. Se almacenan los productos técnicos asociados a esa shipping order
		 * 10. Se almacenan los productos comerciales asociados a la shipping order
		 * 11. Se almacenan los datos de contrato del cliente en la tabla agreemments
		 * 12. Se notifica a IBS el cambio de estado mediante el caso de uso ADS 33 Notificar cambio de estado a IBS
		 */
		log.debug("== Inicia addWorkOrder/CoreWOBusiness ==");
		try {
			//Realizando: 1, 2, 3, 5, 6
			WorkOrder workOrder;
			boolean woExist = false;

			workOrder = workOrderDto.getWorkOrder();
			woExist = (workOrder.getId() == null || workOrder.getId().longValue() == 0L) ? false : true;

			if(woExist){				
				log.info("Se actualizará una wo que ya existia con el código: " + workOrder.getWoCode() + " en estado: " + workOrder.getWorkorderStatusByActualStatusId().getWoStateName());
			}
			
			//Realizando 7
			//jjimeenzh 2010-09-28 Se puede recibir una nueva notificación de work order desde IBS para actualización y no se debe cambiar el estado de la WO, solo se cambia el estado si la WO no existe previamente:
			Set<WorkOrderService> services = workOrder.getWorkOrderServices();
			if(services == null){
				log.error("== Error: no se encontraron servicios asociados a la work order que se intenta importar con código: "+workOrder.getWoCode()+" por lo tanto no se guardará o actualizará en SDII ==");
				return;
			}

			//Se almacena la informacion del edificio
			Building building = workOrderDto.getBuilding();
			if( building != null ){
				BuildingVO buildingVo = UtilsBusiness.copyObject(BuildingVO.class, building);
				if( building.getId() == null || building.getId().longValue() <= 0 ){
					buildingBusinessBean.createBuilding(buildingVo);
				} else {
					buildingBusinessBean.updateBuilding(buildingVo);
				}
				workOrder.getCustomer().setBuilding(building);
				workOrder.getCustomer().getBuilding().setId(buildingVo.getId());
			}
			
			
			Customer customer = workOrder.getCustomer();
			CustomerVO customerVo = UtilsBusiness.copyObject(CustomerVO.class, customer);
			if(customer.getId() == null || customer.getId().longValue() == 0L){
				customerBusinessBean.createCustomer(customerVo);
			}else{
				customerBusinessBean.updateCustomer(customerVo);
			}

			customer.setId(customerVo.getId());
			workOrder.setCustomer(customer);			

			Long userId = UtilsBusiness.getUserIdAdmin(userDao, systemParameterDAO,countryDao.getCountriesByCode(countryCode).getId());
			if(woExist ){
				//Se pone el set en nulo para no tener problemas con la actualizacion
				workOrder.setWorkOrderServices(null);
				log.info("Se actualizará una wo en el proceso de descarga.");
				
				if(workOrder.getWoAddressCode()!=null && workOrder.getCustomer()!=null && workOrder.getCustomer().getCustomerAddresses()!=null){
					boolean haveCode = false;
					for(CustomerAddresses ca : workOrder.getCustomer().getCustomerAddresses()){
						if(ca.getAddressCode().equalsIgnoreCase(workOrder.getWoAddressCode())){
							workOrder.setCustomerAddress(ca);
							haveCode=true;
							break;
						}
					}
					if(!haveCode){
						Long idDefault=addressTypeDAOLocal.getAddressTypeByCode(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_DEFAULT.getCodeEntity()).getId();
						for(CustomerAddresses ca : workOrder.getCustomer().getCustomerAddresses()){
							if(ca.getAddressType().getId().equals(idDefault)){
								workOrder.setCustomerAddress(ca);
								haveCode=true;
								break;
							}
						}
					}
				}
				
				// se borra la marca de Reclamación de WO cuando se realiza la atención desde IBS
				if( workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equals( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity() ) || 
						workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equals( CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity() )){					
					workOrderMarkBusiness.unMarkWorkOrderAttention(workOrder.getId(), userId);
				}
				
				//se borran las marcas en todo update.
				workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(workOrder);
				workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(workOrder, userId);
				
				workOrderDAOBean.updateWorkOrder(workOrder,CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE.getCodeEntity());
				
			}else{
				
				ProcessStatus processStatus = null;
				if ( workOrderDto.isWorkOrderStatusCancel()){
					processStatus = processStatusDao.getProcessStatusByCode(CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_CORRECT_FINISHED.getCodeEntity());
					UtilsBusiness.assertNotNull(processStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se ha encontrado el estado de proceso con el código: " + CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_CORRECT_FINISHED.getCodeEntity());
				}else{
					processStatus = processStatusDao.getProcessStatusByCode(CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_NOT_STARTED.getCodeEntity());
					UtilsBusiness.assertNotNull(processStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se ha encontrado el estado de proceso con el código: " + CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_NOT_STARTED.getCodeEntity());
				}
				workOrderDto.getWorkOrder().setProcessStatus(processStatus);
				
	            if(workOrder!=null && workOrder.getManageCancelation()==null){
	            	workOrder.setManageCancelation(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
	            }
				
				if(workOrder.getWoAddressCode()!=null && workOrder.getCustomer()!=null && workOrder.getCustomer().getCustomerAddresses()!=null){
					boolean haveCode = false;
					for(CustomerAddresses ca : workOrder.getCustomer().getCustomerAddresses()){
						if(ca.getAddressCode().equalsIgnoreCase(workOrder.getWoAddressCode())){
							workOrder.setCustomerAddress(ca);
							haveCode=true;
							break;
						}
					}
					if(!haveCode){
						Long idDefault=addressTypeDAOLocal.getAddressTypeByCode(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_DEFAULT.getCodeEntity()).getId();
						for(CustomerAddresses ca : workOrder.getCustomer().getCustomerAddresses()){
							if(ca.getAddressType().getId().equals(idDefault)){
								workOrder.setCustomerAddress(ca);
								haveCode=true;
								break;
							}
						}
					}
				}

				String custTypeCode = workOrder.getCustomer().getCustType().getCustomerType().getCustomerTypeCode();
				if (custTypeCode.equals(CodesBusinessEntityEnum.CUSTOMER_CODE_TYPE_MDU_BACKBONE.getCodeEntity()) 
						|| custTypeCode.equals(CodesBusinessEntityEnum.CUSTOMER_CODE_TYPE_MDU_WITHOUT_BACKBONE.getCodeEntity())){
					workOrder.setBuilding(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
				}else{
					workOrder.setBuilding(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
				}
				
				workOrderDAOBean.createWorkOrder(workOrder);
				
			}
			
			//Se crea el historial de la descarga de la WO
			String typeChange = "";
			if(workOrderDto.isUpdateWorkOrderIBSToHSP()){
				typeChange = CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_UPDATE.getCodeEntity();
			}else{
				typeChange = CodesBusinessEntityEnum.WO_STATUS_WORKORDER_TYPE_CHANGE_IBS.getCodeEntity();
			}
			WoStatusHistory woStatusHistory = createWoDownloadHistoryEvent(workOrderDto,workOrder,typeChange, countryCode,userId);
			if(woExist ){
				//se llama la operacion de negocio que desactiva las asignacion y elimina agenda para las WO
				//que bajan en estado Pendiente o Cancelado.
				this.deleteAssignment(workOrder,woStatusHistory, userId);
			}
			
			//actualiza o crea los servicios asociados a la WO 
			this.createWOServicesFromIbsByWoExist(workOrder, services,woExist);

			WoAssignment woAssignment = workOrderDto.getWoAssignment();

			if( woAssignment != null && workOrderDto.getCreateAssignment()){
				woAssignment.setWorkOrder(workOrder);
				woAssignmentDAO.createWoAssignment(woAssignment);
			}else if( woAssignment != null && workOrderDto.getUpdateAssignment() ){
				woAssignmentDAO.updateWoAssignment(woAssignment);
			}

			//Se asigna cuadrilla 
			if (workOrder.getIbsTechnical() != null && workOrder.getIbsTechnical() != 0L) {
				Employee employee = employeeDAO.getEmployeeByIbsTechnical(null,workOrder.getIbsTechnical(),workOrder.getCountry().getId());
				List<EmployeeCrew> employeesCrew = employeesCrewDAO.getEmployeesCrewByEmpIdAndCrewStatus(employee.getId(),CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
				if (!employeesCrew.isEmpty()) {
					Crew crew = crewsDAO.getCrewById(employeesCrew.get(0).getId().getCrewId());
					User user = daoUser.getUserById(userId);
					WorkOrderVO woVO = UtilsBusiness.copyObject(WorkOrderVO.class, workOrder);
					List<WorkOrderVO> workOrderVOList = new ArrayList<WorkOrderVO>();
					workOrderVOList.add(woVO);
					this.createWoCrewAssigment(crew,workOrderVOList,user, true);
					
					WoAssignment assignment = workAssignmentDAOBean.getWoAssignmentByAssignmentCrew(workOrder.getId(), workOrder.getDealerId());
					assignment.setCrewId( crew.getId() );
					assignment.setCrewAssignmentDate( new Date() );					
					woAssignmentDAO.updateWoAssignment(assignment);
				}
			}
			
			WorkOrderAgenda woAgenda = workOrderDto.getWoAgenda();
			if(woAgenda != null){
				woAgenda.setStatus(CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
				woAgenda.setWoAssignment(woAssignment);
				workOrderAgendaDAO.createWorkOrderAgenda(woAgenda);
			}

			//Realizando 8.
			//Validando que la work order tenga shipping order
			ShippingOrder so = workOrderDto.getShippingOrder();
			if(so != null){

				//Si la work order existe, se borra la anterior shipping order y se actualiza con la nueva
				if(woExist){
					shippingOrderElementDAO.deleteShippingOrderElementByWoId(workOrder.getId());
					shippingOrderDetailDAO.deleteShippingOrderDetailsByWorkOrderId( workOrder.getId() );
					shippingOrderDAO.deleteShippingOrderByWoId(workOrder.getId());
				}

				so.setWorkOrderId(workOrder.getId());
				shippingOrderDAO.createShippingOrder(so);
				
				//Se agregan los detalles de la SO
				if( workOrderDto.getShippingOrderDetails() != null && !workOrderDto.getShippingOrderDetails().isEmpty() ){
					for( ShippingOrderDetail detail : workOrderDto.getShippingOrderDetails() ){
						detail.setShippingOrder( so );
						shippingOrderDetailDAO.createShippingOrderDetail( detail );
					}
				}

				//TODO jalopez, se inactiva el proceso de realcionar los elementos de la SO hasta no se cree el modulo de Inventarios en SDII.
				//Realizando 9 y 10
			}
			
			//Realizando 11: no es necesario ya que no se almacena el contrato, los datos del código y número de contrato estan en shippingOrder.

			//Realizando 12.
			//jjimenezh 2010-10-21 volver a habilitar la invocación de cambio de estado a IBS
			
			
			markWorkOrderpersistWorkOrder(workOrderDto,userId);
			
			//CC se envian los elementos obtenidos de la WO que fueron movidos en IBs apra actualziar inventario en HSP
			this.sendInventoryUpdate( workOrderDto );
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/addWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina addWorkOrder/CoreWOBusiness ==");
		}
	}
	
	
	/**
	 * Metodo: CC 059 Se desmarca la workOrder
	 * @author
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 * @throws HelperException 
	 */
	private void markWorkOrderpersistWorkOrder(WorkOrderDTO workOrderDto,Long userId) throws BusinessException, HelperException, PropertiesException{
		
		List<WorkOrderMarkVO> workOrderMarkVOS = null;
		
		if(workOrderDto.getUnMarkWorkOrderAttention() != null && workOrderDto.getUnMarkWorkOrderAttention().booleanValue()){
			workOrderMarkVOS = workOrderMarkBusiness.getWorkOrderMarkByIsUnMarkAttention();
			
		}
		
		//CC 056 se marca la workOrder
		if(checkWorkOrderWorkOrderMarkVOByCode(workOrderMarkVOS,CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_REQUIRED_CONTRACT.getCodeEntity())){
			this.markOrUnMarkWorkOrder(workOrderDto.getWorkOrder().getId(),
                    userId,
                    false,
                    CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_REQUIRED_CONTRACT.getIdEntity(WorkOrderMark.class.getName()));
		}else if(workOrderDto.getMarkWorkOrderRequiredContract()!=null){
			this.markOrUnMarkWorkOrder(workOrderDto.getWorkOrder().getId(),
					                                   userId,
					                                   workOrderDto.getMarkWorkOrderRequiredContract().booleanValue(),
					                                   CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_REQUIRED_CONTRACT.getIdEntity(WorkOrderMark.class.getName()));
		}
		
		//CC 059 se marca la workOrder
		if(checkWorkOrderWorkOrderMarkVOByCode(workOrderMarkVOS,CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_PRIORITY_SERVICE.getCodeEntity())){
			this.markOrUnMarkWorkOrder(workOrderDto.getWorkOrder().getId(),
                    userId,
                    false,
                    CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_PRIORITY_SERVICE.getIdEntity(WorkOrderMark.class.getName()));
		}else if(workOrderDto.getMarkWorkOrderPriorityService()!=null){
			this.markOrUnMarkWorkOrder(workOrderDto.getWorkOrder().getId(),
					                                   userId,
					                                   workOrderDto.getMarkWorkOrderPriorityService().booleanValue(),
					                                   CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_PRIORITY_SERVICE.getIdEntity(WorkOrderMark.class.getName()));
		}
		
		//CC051 nexus
		if(checkWorkOrderWorkOrderMarkVOByCode(workOrderMarkVOS,CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_PRIORITY_NEXUS.getCodeEntity())){
			this.markOrUnMarkWorkOrder(workOrderDto.getWorkOrder().getId(),
                    userId,
                    false,
                    CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_PRIORITY_NEXUS.getIdEntity(WorkOrderMark.class.getName()));
		}else if(workOrderDto.getMarkPriorityNexus()!=null){
			this.markOrUnMarkWorkOrder(workOrderDto.getWorkOrder().getId(),
					                                   userId,
					                                   workOrderDto.getMarkPriorityNexus().booleanValue(),
					                                   CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_PRIORITY_NEXUS.getIdEntity(WorkOrderMark.class.getName()));
		}
		
		// se marca la workOrder para Edificios
		/*if (checkWorkOrderWorkOrderMarkVOByCode(workOrderMarkVOS,CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_PRIORITY_BUILDINGS.getCodeEntity())){
			this.markOrUnMarkWorkOrder(workOrderDto.getWorkOrder().getId(),
                    userId,
                    workOrderDto.getMarkPriorityNexus().booleanValue(), //debo agregar getMorkOrder()
                    CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_PRIORITY_BUILDINGS.getIdEntity(WorkOrderMark.class.getName()));
		}*/
		
		
	}
	
	private boolean checkWorkOrderWorkOrderMarkVOByCode(List<WorkOrderMarkVO> workOrderMarkVOS,String codeWorkOrderMark){
		
		if(workOrderMarkVOS!=null && !workOrderMarkVOS.isEmpty()){
			for (WorkOrderMarkVO workOrderMarkVO : workOrderMarkVOS) {
				if(workOrderMarkVO.getCode().equals(codeWorkOrderMark)){
					return true;
				}
			}
		}
		return false;
		
	}
	
	private void markOrUnMarkWorkOrder(Long woId,Long userId,boolean markOrUnMark,Long workOrderMarkId) throws BusinessException, HelperException, PropertiesException{
		
		workOrderMarkBusiness.markOrUnMarkWorkOrder(woId, workOrderMarkId, userId, markOrUnMark);
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#callEsbByChangeStateScheduleBeforePersistentWorkOrder(java.lang.String, co.com.directv.sdii.model.dto.WorkOrderDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void callEsbByChangeStateScheduleBeforePersistentWorkOrder(String countryCode,WorkOrderDTO workOrderDto) throws BusinessException{
		
		//Se informa a IBS el agendamiento y se captura el error para que no afecte la descarga de WO
		try{
			String workOrderReasonAssignedCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_SCHEDULED.getCodeEntity(), countryDao.getCountriesByCode(countryCode).getId(), systemParameterDAO);
		    //String workOrderReasonAssignedCode = CodesBusinessEntityEnum.WORKORDER_REASON_SCHEDULED.getCodeEntity();
		    
			WorkorderReason workorderReason = woReasonDAO.getWorkorderReasonByCode(workOrderReasonAssignedCode);
			
			Date woAgenda = null;
			if(workOrderDto.getWoAgenda() != null){
				if( workOrderDto.getWoAgenda().getAgendationDate() != null ){		
					woAgenda = workOrderDto.getWoAgenda().getAgendationDate();
				}
			}
			Long serviceProviderId=null;
			if(workOrderDto.getDealerAssignmentCode() != null && workOrderDto.getDealerAssignmentCode().longValue() > 0 ){
				serviceProviderId=workOrderDto.getDealerAssignmentCode();
			}
			//String ibsHistoryEventCode = this.coreWOServiceBroker.updateWorkOrderStatus(workOrderDto, workorderReason);
			EditCustomerWorkOrderDTO editCustomerWorkOrderDTO = new EditCustomerWorkOrderDTO(workOrderDto.getWorkOrder().getWoCode(),
					workOrderDto.getWorkOrder().getCountry().getCountryCode(),
                    serviceProviderId,
                    null,
                    workorderReason.getWorkorderReasonCode(),
                    woAgenda);
			String ibsHistoryEventCode = manageWorkForceServiceBroker.editCustomerWorkOrder(editCustomerWorkOrderDTO);
			
			workOrderDto.setCreateHistoryWoStatusHistory( true );
			workOrderDto.setHistoryWoReason( workorderReason );
			workOrderDto.setHistoryDescription( workOrderDto.getWoAgenda().getDescription() );
			workOrderDto.setIbsHistoryEventCode( ibsHistoryEventCode );
		} catch(Throwable ex){
			workOrderDto.setWarning(true);
			BusinessException e = super.manageException(ex);
			workOrderDto.setErrorCode(e.getMessageCode());
			workOrderDto.setErrorMessage(e.getMessage());
			return;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#assignDealerSaleInformation(java.lang.String, co.com.directv.sdii.model.dto.WorkOrderDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void assignDealerSaleInformation(WorkOrderDTO workOrderDto) throws BusinessException {
		
		Dealer dealerDummy = null;
		try{
			//Informacion del vendedor
			Country country = workOrderDto.getWorkOrder().getCountry();
			Long dealerCode = workOrderDto.getDealerSalesId();	
			Long dealerDummyCode = workOrderDto.getDealerDummyCodeLong().longValue();
			
			dealerDummy = dealersDAO.getDealerByDealerCodeAndCountryId(dealerDummyCode,country.getId());
			
			if(dealerCode == null || dealerCode.longValue() == 0){
				dealerCode = dealerDummyCode;			
				log.info("Se asignará como dealer que realiza la venta el dealer dummy ya que no se recibió del servicio, el código del dealer dummy configurador para este país es: " + dealerDummyCode);			
			}		
			Dealer dealer = dealersDAO.getDealerByDealerCodeAndCountryId(dealerCode, country.getId());
			//validateResult("No se encontró en SDII el dealer con el código: " + dealerCode, dealer, ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());
			//21-07-2011 jnova DTV solicita que si el dealer no existe en HSP+ se cree durante este proceso
			if( dealer == null ){
				DealersCodesDTO codeDTO = new DealersCodesDTO();
				codeDTO.setDealerCode(dealerCode);
				codeDTO.setCountryId(country.getId());
				DealerVO dealerVO = dealersCRUD.createSaleDealerForWoImporter( codeDTO,workOrderDto);
				if( dealerVO != null && dealerVO.getId().longValue() > 0 )
					workOrderDto.getWorkOrder().setSaleDealer( dealerVO );
				else
					validateResult("No se encontró en SDII el dealer con el código: " + dealerCode, dealerVO, ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());
			} else {
				workOrderDto.getWorkOrder().setSaleDealer( dealer );
			}
		} catch(Throwable ex){
			workOrderDto.setWarning(true);
			BusinessException e = super.manageException(ex);
			workOrderDto.setErrorCode(e.getMessageCode());
			workOrderDto.setErrorMessage(e.getMessage());
			workOrderDto.getWorkOrder().setSaleDealer( dealerDummy );
		}
		
	}
	
	
	/**
	 * Metodo: Permite crear el historico de descarga de la workOrder
	 * @param workOrderDTO
	 * @param workOrder
	 * @param typeChange
	 * @param countryCode
	 * @param userId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private WoStatusHistory createWoDownloadHistoryEvent(WorkOrderDTO workOrderDTO, WorkOrder workOrder,String typeChange, String countryCode,Long userId) throws BusinessException{
		// Se crea otro historial para el caso que se agende la WO cuando baja
		// con agenda comprometida
		WoStatusHistory woStatusHistory = null;
		try {
			if (workOrderDTO.isCreateHistoryWoStatusHistory()) {
				woStatusHistory = this.createWoStatusHistory(workOrder, 
															workOrderDTO.getHistoryWoReason(),
															workOrderDTO.getHistoryDescription(), 
															workOrder.getWorkorderStatusByActualStatusId(), 
															workOrderDTO.getIbsHistoryEventCode(), 
															typeChange, 
															userId);
			} else {
				// Se crea el historico de creación o actualización de la work
				// order
				woStatusHistory = this.createWoStatusHistory(workOrder, null, null, workOrder.getWorkorderStatusByActualStatusId(), null, typeChange, userId);
			}

		} catch (Throwable e) {
			super.manageException(e);
		}
		return woStatusHistory;
	}
	
	/**
	 * 
	 * Metodo: Envia los elementos que se movieron en IBS a la cola de inventarios para realizar actualizacion en HSP 
	 * @param workOrderDto <tipo> <descripcion>
	 * @author jnova
	 * @throws BusinessException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void sendInventoryUpdate( WorkOrderDTO workOrderDto ) 
	throws BusinessException{
		try{
				if( 	workOrderDto != null 
					 && workOrderDto.getMovementInventoryDTO() != null 
					&& !workOrderDto.getMovementInventoryDTO().isEmpty() ){
					
					MovementInventoryListDTO movementInventoryListDTO = new MovementInventoryListDTO();
					movementInventoryListDTO.setMovementInventoryDTOList(new ArrayList<MovementInventoryDTO>());
					
					for( MovementInventoryDTO dto : workOrderDto.getMovementInventoryDTO() ){
						dto.setUserId( workOrderDto.getWorkOrder().getUserId() );
						dto.setCustomerId( workOrderDto.getWorkOrder().getCustomer().getId() );
						dto.setWoId( workOrderDto.getWorkOrder().getId() );
						movementInventoryListDTO.getMovementInventoryDTOList().add(dto);
					}
					
					movementInventoryListDTO.setUserId( workOrderDto.getWorkOrder().getUserId() );
					movementInventoryListDTO.setCustomerId( workOrderDto.getWorkOrder().getCustomer().getId() );
					List<CustomerProductDTO> customerProductDTOList = new ArrayList<CustomerProductDTO>();
					
					if(workOrderDto!=null
							&& workOrderDto.getWorkOrder()!=null
							&& workOrderDto.getWorkOrder().getCountry()!=null
							&& workOrderDto.getWorkOrder().getCountry().getId()!=null){
						SystemParameter systemParameterInSuscriptorState = null;
						try {
							systemParameterInSuscriptorState = configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum
									.PRODUCT_STATUS_IN_SUSCRIPTOR.getCodeEntity(), workOrderDto.getWorkOrder().getCountry().getId());
						} catch (PropertiesException e) {
							manageException(e);
						}
						
						if(systemParameterInSuscriptorState != null){
							for(CustomerProductDTO cpdto: workOrderDto.getCustomerInfoAggregatedDTO().getCustomerProductDTO()){
			   				  //if(cpdto.getIdProductStatus().equalsIgnoreCase(systemParameterInSuscriptorState.getValue())){
								if(systemParameterInSuscriptorState.getValue().equalsIgnoreCase(cpdto.getIdProductStatus())){
									customerProductDTOList.add(cpdto);
								}
							}
						}
					}
					movementInventoryListDTO.setCustomerProductDTO(customerProductDTOList);
					movementInventoryListDTO.setWoId(workOrderDto.getWorkOrder().getId());
		
					distributedQueueMessageBroker.setQueueMovementInventory(movementInventoryListDTO);
				}
				
			/*
			 * ialessan abril 2014
			 * IN400990 - Descarga de Eventos
			 * Se agrega el catch  	
			 */
				
				
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion CoreWOBusiness/sendInventoryUpdate ==> "+ex.getMessage());
			throw super.manageException(ex);
		}     			
	}
	

	/**
	 * 
	 * Metodo: Elimina las asignaciones y la agenda
	 * de las WO que bajan en estado Pendiente o Cancelada
	 * segun el caso.
	 * Si es pendiente o cancelada se informará a la cuadrilla en optimus.
	 * @param workOrder
	 * @throws BusinessException
	 * @throws PropertiesException
	 * @author jalopez
	 */
	private void deleteAssignment(WorkOrder workOrder,WoStatusHistory woStatusHistory, Long userId) throws BusinessException, PropertiesException{
		try{
			WorkorderStatus status = workOrder.getWorkorderStatusByActualStatusId();
			Boolean isPending = status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
			Boolean isCanceled = status.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity());
			
			if ( isPending || isCanceled ) { 
		
				List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
				workOrderList.add(workOrder);

				User user = userDao.getUserById(userId);
				
				SystemParameter optimusSP = this.configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.OPTIMUS_DISPATCHER_ACTIVE.getCodeEntity(), user.getCountry().getId());
				Boolean isOptimusActive = (optimusSP != null && optimusSP.getValue().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()));
				
				this.withDrawWOFromTechnicianInOptimus(isOptimusActive, user, workOrderList );
				
				//Si es pendiente no se debe desactivar la asignacion, si es cancelada se debe desactivar la asignacion
				Boolean disableAssignment  = !isPending;
				//se inactivan todas las asignaciones que tiene la WO
				this.deleteWOAssignmentAgendaCrewAndProgram( workOrder.getId(), disableAssignment, woStatusHistory );
			}
			
		} catch (Throwable e) {
			super.manageException(e);
		}
	}

	/**
	 * 
	 * Metodo: Metodo que crea los serivicios asociados a una WO a partir del service key que viene del servicio para importarlas
	 * @param workOrder
	 * @param services
	 * @param woExist
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void createWOServicesFromIbsByWoExist(WorkOrder workOrder, Set<WorkOrderService> services,boolean woExist) throws BusinessException {
		//1. Si la WO no existe, se crean los servicio
		//2. Si la WO existe:
			//2.1 Si la WO tiene un servicio en HSP y no viene en la respuesta de IBS se elimina el servicio
			//2.2 Si existe el serviceKey entre los servicios que tiene la WO asignada se actualiza
			//2.3. Si no existe el servicio, se crea el servicio que viene de la respuesta de IBS
		log.debug("== Inicia createWOServicesFromIbs/CoreWOBusiness ==");
		try{
			if( !woExist ){
				createWOServicesFromIbs(workOrder,services);
			} else{
				List<WorkOrderService> woServices = this.woServiceDAO.getWorkOrderServiceByWoId(workOrder.getId());
				//Caso que la WO no tenga servicios
				if( woServices == null || woServices.isEmpty() ){
					createWOServicesFromIbs(workOrder,services);
				} else {
					for (WorkOrderService ibsWorkOrderService : services) {
						boolean serviceExist = false;
						for( WorkOrderService sdiiWorkOrderService : woServices ){
							//Si existe en mismo serviceKey se actualiza el que existe con el que viene
							if( ibsWorkOrderService.getIbsServiceKey() != null
									&& ibsWorkOrderService.getIbsServiceKey().equals( sdiiWorkOrderService.getIbsServiceKey() ) ){
								serviceExist = true;
								sdiiWorkOrderService.setService( ibsWorkOrderService.getService() );
								if (ibsWorkOrderService.getLinkedSerialNumber()!=null)
									sdiiWorkOrderService.setLinkedSerialNumber(ibsWorkOrderService.getLinkedSerialNumber());
								sdiiWorkOrderService.setSerialNumber( ibsWorkOrderService.getSerialNumber() );
								sdiiWorkOrderService.setServiceInclusionDate( UtilsBusiness.fechaActual() );
								woServiceDAO.updateWorkOrderService(sdiiWorkOrderService);
							}
						}
						//Si el servicio no existe lo crea
						if( !serviceExist ){
							String activeStatusCode = CodesBusinessEntityEnum.WO_SERVICE_STATUS_UNATTENDED.getCodeEntity();
							WoServiceStatus activeStatus = woServiceStatusDao.getWoServiceStatusByCode(activeStatusCode);
							ibsWorkOrderService.setWoId(workOrder.getId());
							ibsWorkOrderService.setWoServiceStatus(activeStatus);
							woServiceDAO.createWorkOrderService(ibsWorkOrderService);
						}
					}
					//2.1 Se recorren las colecciones al reves para borrar en caso que no vengan en el servicio y existan en HSP+
					for( WorkOrderService sdiiWorkOrderService : woServices ){
						boolean serviceExist = false;
						for (WorkOrderService ibsWorkOrderService : services) {
							if( ibsWorkOrderService.getIbsServiceKey() != null
									&& ibsWorkOrderService.getIbsServiceKey().equals( sdiiWorkOrderService.getIbsServiceKey() ) ){
								serviceExist = true;
							}
						}
						if( !serviceExist ){
							woServiceDAO.deleteWorkOrderService( sdiiWorkOrderService );
						}
					}
				}
			}
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion createWOServicesFromIbs/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createWOServicesFromIbs/CoreWOBusiness ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Crea los servicios asociados a una WO sin ningun tipo de validacion
	 * @param workOrder
	 * @param services
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void createWOServicesFromIbs(WorkOrder workOrder, Set<WorkOrderService> services) throws BusinessException {
		log.debug("== Inicia createWOServicesFromIbs/CoreWOBusiness ==");
		try{
			String activeStatusCode = CodesBusinessEntityEnum.WO_SERVICE_STATUS_UNATTENDED.getCodeEntity();
			WoServiceStatus activeStatus = woServiceStatusDao.getWoServiceStatusByCode(activeStatusCode);
			UtilsBusiness.assertNotNull(activeStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			for (WorkOrderService workOrderService : services) {
				workOrderService.setWoId(workOrder.getId());
				workOrderService.setWoServiceStatus(activeStatus);
				woServiceDAO.createWorkOrderService(workOrderService);
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion createWOServicesFromIbs/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createWOServicesFromIbs/CoreWOBusiness ==");
		}
	}

	/**
	 * Method:Operacion para asignar workorders de un
	 * dealer a una cuadrilla.
	 * @param workOrderList
	 * @param crew
	 * @return Tx exitosa
	 * @throws BusinessException
	 * @see  co.com.directv.sdii.ejb.business.core.assignDynamicCrewToWorkOrder
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean assignDynamicCrewToWorkOrder(List<WorkOrderVO> workOrderList, CrewVO crew, Long userId) throws BusinessException{

		try{
			
			User user = daoUser.getUserById(userId);
    		UtilsBusiness.assertNotEmpty(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());

			if(workOrderList.isEmpty()){
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			}
			if(crew == null){
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			}
			if( crew.getId() == null )
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );

			for (WorkOrderVO workOrderVO : workOrderList) {

				if( workOrderVO.getId() == null )
					throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );

				if( workOrderVO.getDealerId() == null )
					throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );

				WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID(workOrderVO.getId());	
				if(workOrder == null){
					throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
				}
				
				WoAssignment assignment = workAssignmentDAOBean.getWoAssignmentByAssignmentCrew(workOrderVO.getId(), workOrderVO.getDealerId());
				if(assignment == null){
					throw new BusinessException(ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
				}
				
				assignment.setProgram(null);
				assignment.setProgramAssignmentDate(null);
				assignment.setCrewId( crew.getId() );
				assignment.setCrewAssignmentDate( new Date() );
				workAssignmentDAOBean.updateWoAssignment( assignment );
			}     
			createWoCrewAssigment(crew,workOrderList, user, false);
			return true;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/assignDynamicCrewToWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByActualStatus/CoreWOBusiness ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#agendaWorkOrder(co.com.directv.sdii.model.dto.TrayWOManagmentDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void agendaWorkOrder(TrayWOManagmentDTO trayWOManagmentDTO) throws BusinessException {

		UtilsBusiness.assertNotNull(trayWOManagmentDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorder(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getWorkorder().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getServiceHour(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getServiceHour().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getAgendationDate(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getContactPerson(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getCommentManagment(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getDealerId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(trayWOManagmentDTO.getCountryId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		/*
		 *Para agendar una work order:
		 *0. Validar que la fecha de agendamiento no puede ser menor a la fecha actual
		 *1. Validar que la workOrder exista en la tabla WORK_ORDERS
		 *2. Validar que exista un registro relacionado en WO_ASSIGMENTS
		 *3. Se valida si la WO ya tiene agenda, para determinar si es un reagendamiento y se asigna la reason.
		 *4. Usando la máquina de estados validar que se pueda modificar el estado de la workorder a AGENDADA
		 *5. Generar registro en la tabla WORK_ORDER_AGENDA
		 *6. Afectar la capacidad del dealer en DealerServiceCapacity invocando el caso de uso ADS-34
		 *7. Actualizar la fecha de programación de la workOrder por la de agendamiento
		 *8. Cambiar el estado de la workorder
		 *9. Notificar el cambio de estado de la workOrder a IBS usando el CU-ADS 33
		 */

		log.debug("== Inicia agendaWorkOrder/CoreWOBusiness ==");
		try {

			//Se consulta la jornada
			//Se valida que la jornada sea enviada
			Object[] params = null;
			params = new Object[2];	
			params[1] = "agenda - reagendamiento";
			params[0] = "jornada";
			UtilsBusiness.validateRequestResponseWebService(params, trayWOManagmentDTO.getServiceHour(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			UtilsBusiness.validateRequestResponseWebService(params, trayWOManagmentDTO.getServiceHour().getId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
				
			ServiceHour  serviceHourComplete = serviceHourDAO.getServiceHourByID(trayWOManagmentDTO.getServiceHour().getId());
			UtilsBusiness.validateRequestResponseWebService(params, serviceHourComplete, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			trayWOManagmentDTO.setAgendationDate(UtilsBusiness.addHoursToDate(serviceHourComplete.getEndTime(), trayWOManagmentDTO.getAgendationDate()));
			
			
			//Se verifica si ya paso la jornada del dia actual y que la fecha no sea antes de la actual
			this.checkServiceHour(trayWOManagmentDTO.getUserId(),
							      trayWOManagmentDTO.getCountryId(),
							      trayWOManagmentDTO.getAgendationDate(),
							      serviceHourComplete);
			
			//Ejecutando 1:
			WorkOrder workOrderPojo = workOrderDAOBean.getWorkOrderByID(trayWOManagmentDTO.getWorkorder().getId());
			this.changeToFalseAgendationExpired(workOrderPojo);
			if(workOrderPojo == null){
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}

			//Ejecutando 2:
			WoAssignment woAssignment = this.workAssignmentDAOBean.getLastDealerAssignmentByWoId(workOrderPojo.getId());
			if(woAssignment == null){
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_ASSIGMENT_DOES_NOT_EXIST.getMessage());
			}

			//Ejecutando 3:
			//Se valida si la WO ya tiene agenda, para determinar si es un reagendamiento
			WorkorderReason workorderReason;
			WorkOrderAgenda oldWoAgenda = workOrderAgendaDAO.getLastWorkOrderAgendaByWoID(trayWOManagmentDTO.getWorkorder().getId());
			WorkorderStatus woStatus = null;
						
			// Si el estado previo de la orden es "rechazada" y el estado actual es "reasignada", o si la WO ya tiene agenda
			// pero se encuentra en estado "asignada", se hace un agendamiento		
			if ( getValidateWorkOrderScheduled( trayWOManagmentDTO.getWorkorder().getId(),  trayWOManagmentDTO.getDealerId() ) ) {				
				woStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
				//Si ya existe una agenda se inactiva la existente para crear una nueva
				if(oldWoAgenda != null){
					oldWoAgenda.setStatus(CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_INACTIVE.getCodeEntity());
					workOrderAgendaDAO.updateWorkOrderAgenda(oldWoAgenda);
				}
				
				//validacion de la reason, puesto que para el reagendamiento es requerida.
				Object[] paramsReason = null;
				paramsReason = new Object[2];	
				paramsReason[1] = "reagendamiento";
				paramsReason[0] = "motivo";
				UtilsBusiness.validateRequestResponseWebService(params, trayWOManagmentDTO.getWorkorderReason(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(paramsReason));
				UtilsBusiness.validateRequestResponseWebService(params, trayWOManagmentDTO.getWorkorderReason().getId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(paramsReason));
				//Se asigna la reason que llega por parametro para el caso en que sea un reagendamiento
				workorderReason = woReasonDAO.getWorkorderReasonByID( trayWOManagmentDTO.getWorkorderReason().getId() );
				
			}else {				
				woStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
				
				//Se consulta la wo reason para el caso de agendamiento.
				String workOrderReasonAssignedCode=UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.WORKORDER_REASON_SCHEDULED.getCodeEntity(), trayWOManagmentDTO.getCountryId(), systemParameterDAO);
				workorderReason = woReasonDAO.getWorkorderReasonByCode(workOrderReasonAssignedCode);
				if(workorderReason == null){
					throw new BusinessException(ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getMessage());
				}
			}
			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage());

			WorkOrderVO workOrderTmp = new WorkOrderVO();
			workOrderTmp.setWorkorderStatusByActualStatusId(workOrderPojo.getWorkorderStatusByActualStatusId());
			workOrderTmp.setWorkorderStatusByPreviusStatusId(workOrderPojo.getWorkorderStatusByPreviusStatusId());

			//Ejecutando 4:
			//Usar la máquina de estados para validar el cambio de estado a AGENDADA
			this.stateMachine.validateStatusChangeWorkOrder(workOrderTmp,woStatus.getId());

			//Ejecutando 5:
			WorkOrderAgenda newWoAgenda = new WorkOrderAgenda();
			newWoAgenda.setStatus(CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_ACTIVE.getCodeEntity());
			newWoAgenda.setAgendationDate(trayWOManagmentDTO.getAgendationDate());
			newWoAgenda.setContactPerson(trayWOManagmentDTO.getContactPerson());
			newWoAgenda.setDescription(trayWOManagmentDTO.getCommentManagment());
			newWoAgenda.setServiceHour(serviceHourComplete);
			newWoAgenda.setWoAssignment(woAssignment);
			workOrderAgendaDAO.createWorkOrderAgenda(newWoAgenda);
			
			//Ejecutando 6:
			DealerVO dealer = new DealerVO();
			dealer.setId(workOrderPojo.getDealerId());
			PostalCodeVO postalCode = new PostalCodeVO();
			postalCode.setId(workOrderPojo.getPostalCode().getId());

			trayWOManagmentDTO.setWorkorder( UtilsBusiness.copyObject(WorkOrderVO.class, workOrderPojo) );

			//Ejecutando 7:
			workOrderPojo.setWoProgrammingDate(trayWOManagmentDTO.getAgendationDate());
			//jnova 10-10-2011 se pone en nulo el process source porque se gestiona por HSP
			workOrderPojo.setProcessSourceId(null);
			workOrderDAOBean.updateWorkOrder(workOrderPojo,CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE.getCodeEntity());

			//Se encapsula la informacion en un DTO para pasarlo al broker
			WorkOrderDTO woDTO = new WorkOrderDTO();
			woDTO.setWorkOrder(workOrderPojo);
			WorkOrderAgenda woAgenda = new WorkOrderAgenda();
			woAgenda.setAgendationDate(trayWOManagmentDTO.getAgendationDate());
			woDTO.setWoAgenda(woAgenda);
			
			//String woDescription = validWorkOrderStatusPending(woStatus,workOrderPojo.getWorkorderStatusByActualStatusId(),workOrderPojo.getWoDescription());
			//Ejecutando 8:			
			this.changeWorkOrderStatus(woDTO, woStatus.getId(), workorderReason, trayWOManagmentDTO.getCommentManagment(),trayWOManagmentDTO.getUserId(), new WoStatusHistory(),null);
			
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatus(workOrderPojo);
			workOrderMarkOptimusBusinessBean.deleteWorkOrderOptimusStatusDecline(workOrderPojo, trayWOManagmentDTO.getUserId());
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/agendaWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina agendaWorkOrder/CoreWOBusiness ==");
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
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#checkServiceHourForWoDownload(java.lang.Long, java.lang.Long, java.util.Date, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean checkServiceHourForWoDownload(Long userId,
									             Long countryId,
									             Date agendationDate, 
									             ServiceHour serviceHour) throws BusinessException{
		log.debug("== Inicia checkServiceHourForWoDownload/CoreWOBusiness ==");
		boolean response = true;
		try{
			this.checkServiceHour( userId, countryId, agendationDate, serviceHour);
		} catch (BusinessException ex) {
			log.debug("== No pasa la validacion de checkServiceHourForWoDownload/CoreWOBusiness ==");
			String serviceHourErrorCode1 = ErrorBusinessMessages.INVALID_DATE_WITH_LOCK_PRIOR_CALENDAR.getCode();
			String serviceHourErrorCode2 = ErrorBusinessMessages.INVALID_DATE.getCode();
			if( ex.getMessageCode().equalsIgnoreCase( serviceHourErrorCode1 ) 
					|| ex.getMessageCode().equalsIgnoreCase( serviceHourErrorCode2 ) ){
				response = false;
			}
		} catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/agendaWorkOrder", e);
			throw super.manageException(e);
		}finally {
			log.debug("== Termina checkServiceHourForWoDownload/CoreWOBusiness ==");
		}
		return response;
	}
	
	private void checkServiceHour(Long userId, 
			                      Long countryId, 
			                      Date agendationDate, 
			                      ServiceHour serviceHour) throws BusinessException, PropertiesException, DAOServiceException, DAOSQLException {
			log.debug("== Inicia checkServiceHour/CoreWOBusiness ==");
		
			//Se valida que la jornada sea valida
			User userVO=userDao.getUserById(userId);
			Date userDate=UtilsBusiness.getCurrentDateByTimeZoneKey(userVO.getSdiiTimeZone().getTimeZoneKey(),new Date());
			
			//Se valida que la fecha de agendamiento no sea anterior a la fecha actual
			if(UtilsBusiness.dateTo12am(userDate).after(UtilsBusiness.dateTo12pm(agendationDate))){
				log.error("Error de validación de fechas: la fecha de agendamiento " + agendationDate + " es menor a la fecha actual");
				throw new BusinessException(ErrorBusinessMessages.INVALID_DATE.getCode(), ErrorBusinessMessages.INVALID_DATE.getMessage());
			}
			
			Calendar actualTime = Calendar.getInstance();
			actualTime.setTime(userDate);
			Calendar serviceHourTime = Calendar.getInstance();
			Long lockPriorCalendar=0L;
			String strlockPriorCalendar = configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.CODE_SYS_PARAM_LOCK_PRIOR_CALENDAR.getCodeEntity(), countryId).getValue();
			
			if( strlockPriorCalendar != null &&  !strlockPriorCalendar.trim().equals("") ){
				try{
					lockPriorCalendar=Long.parseLong(strlockPriorCalendar);
					if(lockPriorCalendar.doubleValue()>24)
						lockPriorCalendar=24L;
				}catch (Exception e) {
					lockPriorCalendar=0L;
				}
				
			}
			boolean isTheFirtsDay = UtilsBusiness.isTheSameDateAsToday(actualTime, agendationDate);
			int hourMinuteActual=0,hourMinuteService=0,hourMinuteActualWithLockPriorCalendar=0;
			if(isTheFirtsDay){
				if(userVO.getDealer()==null){
					serviceHourTime.setTime(serviceHour.getInitTime());
					//Si la hora actual es mayor que la hora de inicio de la jornada no se calculará la carga para esa compañía
					
					hourMinuteActual =  60*actualTime.get(Calendar.HOUR_OF_DAY) + actualTime.get(Calendar.MINUTE);
					hourMinuteService = 60*serviceHourTime.get(Calendar.HOUR_OF_DAY) + serviceHourTime.get(Calendar.MINUTE);
					
					hourMinuteActualWithLockPriorCalendar = hourMinuteActual + (int)(lockPriorCalendar.doubleValue()*60);
					if(hourMinuteActual > hourMinuteService){
						log.error("Error de validación de fechas: la fecha de agendamiento " + agendationDate + " es menor a la hora de la jornada");
						throw new BusinessException(ErrorBusinessMessages.CORE_CR061.getCode(), ErrorBusinessMessages.CORE_CR061.getMessage());
					}else if(hourMinuteActualWithLockPriorCalendar > hourMinuteService && userVO.getDealer()==null){
						log.error("Esta jornada solamente se puede seleccionar " + strlockPriorCalendar +" horas antes.");
						throw new BusinessException(ErrorBusinessMessages.INVALID_DATE_WITH_LOCK_PRIOR_CALENDAR.getCode(), ErrorBusinessMessages.INVALID_DATE_WITH_LOCK_PRIOR_CALENDAR.getMessage(), Arrays.asList(new String[]{strlockPriorCalendar}));
					} 
				}else{
					serviceHourTime.setTime(serviceHour.getEndTime());
					//Si la hora actual es mayor que la hora de inicio de la jornada no se calculará la carga para esa compañía
					
					hourMinuteActual=60*actualTime.get(Calendar.HOUR_OF_DAY) + actualTime.get(Calendar.MINUTE);
					hourMinuteService=60*serviceHourTime.get(Calendar.HOUR_OF_DAY) + serviceHourTime.get(Calendar.MINUTE);
					
					hourMinuteActualWithLockPriorCalendar = hourMinuteActual + (int)(lockPriorCalendar.doubleValue()*60);
					if(hourMinuteActual > hourMinuteService){
						log.error("Error de validación de fechas: la fecha de agendamiento " + agendationDate + " es menor a la hora de la jornada");
						throw new BusinessException(ErrorBusinessMessages.CORE_CR061.getCode(), ErrorBusinessMessages.CORE_CR061.getMessage());
					}
				}
			}
	}


	/**
	 * @throws BusinessException 
	 * @see ICoreWOBusiness#getWorkOrderByCustomerAndRealizationDate(CustomerVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByCustomerAndRealizationDate(CustomerVO customer, Date initDate, Date endDate) throws BusinessException {
		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByActualAndPreviusType/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por el cliente y su fecha de realizaciï¿½n
			woList = workOrderDAOBean.getWorkOrderByCustomerAndRealizationDate(UtilsBusiness.copyObject(Customer.class,customer),
					initDate, endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByCustomerAndRealizationDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByActualAndPreviusType/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByPreviusType(WoTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByPreviusType(WoTypeVO previusType)throws BusinessException {
		UtilsBusiness.assertNotNull(previusType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(previusType.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByPreviusType/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por el cliente y su fecha de realizaciï¿½n
			woList = workOrderDAOBean.getWorkOrderByPreviusType(UtilsBusiness.copyObject(WoType.class,previusType));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByPreviusType");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByPreviusType/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByDealerAndRealizationDate(DealerVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByDealerAndRealizationDate(DealerVO dealer, Date initDate, Date endDate)throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByDealerAndRealizationDate/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por el cliente y su fecha de realizaciï¿½n
			woList = workOrderDAOBean.getWorkOrderByDealerAndRealizationDate(UtilsBusiness.copyObject(Dealer.class,dealer),
					initDate, endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealerAndRealizationDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerAndRealizationDate/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#changeWorkOrderStatus(WorkOrderVO, WorkorderStatusVO, WorkorderReasonVO)
	 * @author jjimenezh 2010-06-08
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeWorkOrderStatus(WorkOrderVO workorder, WorkorderStatusVO newWorkOrderStatus, WorkorderReasonVO reason) throws BusinessException{
		log.debug("== Inicia changeWorkOrderStatus/CoreWOBusiness ==");
		try {

			WorkorderStatus newStatusPojo = UtilsBusiness.copyObject(WorkorderStatus.class, newWorkOrderStatus);
			workorder.setWorkorderStatusByActualStatusId(newStatusPojo);
			//Se encapsula la informacion en un DTO para pasarlo al broker
			WorkOrderDTO woDTO = new WorkOrderDTO();
			woDTO.setWorkOrder(workorder);
			
			//coreWOServiceBroker.updateWorkOrderStatus(woDTO, reason);
			EditCustomerWorkOrderDTO editCustomerWorkOrderDTO = new EditCustomerWorkOrderDTO(woDTO.getWorkOrder().getWoCode(),
												 						                     woDTO.getWorkOrder().getCountry().getCountryCode(),
												 						                     woDTO.getDealerAssignmentCode(),
												 						                     null,
												 						                     reason.getWorkorderReasonCode(),
												 						                     null);
			manageWorkForceServiceBroker.editCustomerWorkOrder(editCustomerWorkOrderDTO);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatus/CoreWOBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#changeWorkOrderStatusWithoutIbsNotification(co.com.directv.sdii.model.vo.WorkOrderVO, co.com.directv.sdii.model.vo.WorkorderStatusVO, co.com.directv.sdii.model.vo.WorkorderReasonVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void changeWorkOrderStatusWithoutIbsNotification(
			WorkOrderVO workOrder, WorkorderStatusVO workorderStatusVO,
			WorkorderReasonVO woReasonVO) throws BusinessException {

		log.debug("== Inicia changeWorkOrderStatusWithoutIbsNotification/CoreWOBusiness ==");
		try {
			stateMachine.validateStatusChangeWorkOrderByCodes(workOrder, workorderStatusVO.getWoStateCode());
			WorkOrderDTO woDTO = new WorkOrderDTO();
			woDTO.setWorkOrder(workOrder);
			this.changeWorkOrderStatus(woDTO, workorderStatusVO.getId(), woReasonVO, woReasonVO.getWorkorderReasonName(),null, new WoStatusHistory(),null);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/changeWorkOrderStatusWithoutIbsNotification");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatusWithoutIbsNotification/CoreWOBusiness ==");
		}
	}

	/**
	 * @throws BusinessException 
	 * @see ICoreWOBusiness#getWorkOrderByDealerAndPostalCode(DealerVO, PostalCodeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByDealerAndPostalCode(DealerVO dealer, PostalCodeVO postalCode) throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(postalCode.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByDealerAndPostalCode/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por el cliente y su fecha de realizaciï¿½n
			woList = workOrderDAOBean.getWorkOrderByDealerAndPostalCode(UtilsBusiness.copyObject(Dealer.class,dealer),
					UtilsBusiness.copyObject(PostalCode.class,postalCode));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getAllServiceType");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerAndPostalCode/CoreWOBusiness ==");
		}
		return wos;
	}


	/**
	 * @throws BusinessException 
	 * @throws BusinessException 
	 * @see ICoreWOBusiness#getWorkOrderByDealerAndPostalCode(DealerVO, PostalCodeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderVO getWorkOrderByDealerIdAndWorkOrderId(final Long dealerId, final Long workOrderId) throws BusinessException  {

		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		log.debug("== Inicia getWorkOrderByDealerIdAndWorkOrderId/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por el cliente y su fecha de realizaciï¿½n
			WorkOrder woPojo = workOrderDAOBean.getWorkOrderByDealerIdAndWorkOrderId(dealerId, workOrderId);
			if(woPojo!=null){
				WorkOrderVO workOrderVo = UtilsBusiness.copyObject(WorkOrderVO.class,woPojo);
				this.fillDataWorkOrderDetailHelper(workOrderVo);
				return workOrderVo;
			}
			return null;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getAllServiceType");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerIdAndWorkOrderId/CoreWOBusiness ==");
		}
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByRealizationDate(Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByRealizationDate(Date initDate, Date endDate)throws BusinessException {
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByRealizationDate/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por su fecha de realizaciï¿½n
			woList = workOrderDAOBean.getWorkOrderByDealerAndRealizationDate(initDate, endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealerAndPostalCode");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByRealizationDate/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * Obtiene todos los workorders por dealer     
	 * @return - List<WorkOrder>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 *
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrderByDealer(co.com.directv.sdii.model.vo.DealerVO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOByDealerPaginationResponse getWorkOrderByDealer(DealerVO dealer, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		List<WoAssignmentVO> wos = null;
		WOByDealerPaginationResponse r = null;

		log.debug("== Inicia getWorkOrderByDealer/CoreWOBusiness ==");
		if( dealer == null ){
			throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
		}
		if( dealer.getId() == null || dealer.getId().longValue() <= 0){
			throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
		}
		try {
			// Obtener las WorkOrder por Dealer
			r = workOrderDAOBean.getWorkOrderByDealer(dealer, requestCollectionInfo);
			wos = UtilsBusiness.convertList(r.getCollection(), WoAssignmentVO.class);
			r.setCollection(null);
			r.setCollectionVO(wos);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealer");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealer/CoreWOBusiness ==");
		}
		return r;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByDealerAndCode(DealerVO, String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderVO getWorkOrderByDealerAndCode(DealerVO dealer, String code) throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		WorkOrderVO wos = null;		
		log.debug("== Inicia getWorkOrderByDealerAndCode/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por Dealer
			wos = UtilsBusiness.copyObject(WorkOrderVO.class,workOrderDAOBean.getWorkOrderByDealerAndCode(UtilsBusiness.copyObject(Dealer.class, dealer) ,code) );
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealerAndCode");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerAndCode/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByPreviusStatus(DealerVO, WorkorderStatusVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByPreviusStatus(DealerVO dealer, WorkorderStatusVO previusStatus) throws BusinessException{
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(previusStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(previusStatus.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByPreviusStatus/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por Dealer
			woList = workOrderDAOBean.getWorkOrderByPreviusStatus(UtilsBusiness.copyObject(Dealer.class, dealer),
					UtilsBusiness.copyObject(WorkorderStatus.class, previusStatus));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByPreviusStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByPreviusStatus/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByPreviusType(DealerVO, WoTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByPreviusType(DealerVO dealer, WoTypeVO previusType) throws BusinessException{
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(previusType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(previusType.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByPreviusType/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por Dealer
			woList = workOrderDAOBean.getWorkOrderByPreviusType(UtilsBusiness.copyObject(Dealer.class, dealer),
					UtilsBusiness.copyObject(WoType.class, previusType));
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByPreviusType");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByPreviusType/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByDealerPostalCodeProgrammingDate(DealerVO, PostalCodeVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeProgrammingDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate)throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(postalCode.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByDealerPostalCodeProgrammingDate/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por Dealer
			woList = workOrderDAOBean.getWorkOrderByDealerPostalCodeProgrammingDate(UtilsBusiness.copyObject(Dealer.class, dealer),
					UtilsBusiness.copyObject(PostalCode.class, postalCode),initDate,endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealerPostalCodeProgrammingDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerPostalCodeProgrammingDate/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByDealerPostalCodeRealizationDate(DealerVO, PostalCodeVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByDealerPostalCodeRealizationDate(DealerVO dealer, PostalCodeVO postalCode, Date initDate, Date endDate)throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(postalCode.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByDealerPostalCodeProgrammingDate/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por Dealer
			woList = workOrderDAOBean.getWorkOrderByDealerPostalCodeRealizationDate(UtilsBusiness.copyObject(Dealer.class, dealer),
					UtilsBusiness.copyObject(PostalCode.class, postalCode),initDate,endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealerPostalCodeRealizationDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerPostalCodeProgrammingDate/CoreWOBusiness ==");
		}
		return wos;
	}

	/**
	 * @author jjimenezh 2010-04-20
	 * @see ICoreWOBusiness#deleteServiceToWorkOrder(Long, Long, String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteServiceToWorkOrder(Long workOrderId, Long serviceId, String decoderSerialNumber, String countryCode) throws BusinessException {
		UtilsBusiness.assertNotNull(workOrderId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(serviceId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		/*
		 * Para ejecutar este caso de uso:
		 * 1. Verificar que existe la orden de trabajo en el sistema
		 * 2. Verificar que existe el servicio identificado con ese código
		 * 3. Verificar que existe workOrderService con el id de la workOrder, el id del Service y el serial especificados
		 * 4. Borrar de la workOrder el servicio especificado
		 * 5. Notificar a IBS la eliminación de un servicio de la Work Order
		 */

		log.debug("== Inicia deleteServiceToWorkOrder/CoreWOBusiness ==");
		try {

			//Ejecutando 1:
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID(workOrderId);
			if(workOrder == null){
				log.error("No existe la WO con el ID " + workOrderId + " En el país: " + countryCode);
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}

			//Ejecutando 2:
			Service service = serviceDAO.getServiceByID(serviceId); 
			if(service == null){
				log.error("No se encontró servicio con el ID especificado: " + serviceId);
				throw new BusinessException(ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage());
			}

			//Ejecutando 3:
			List<WorkOrderService> workOrderServiceList = woServiceDAO.getWOServiceByWorkorderIdAndServiceIdAndSerailNumber(workOrder.getId(), service.getId(), decoderSerialNumber);  

			//Ejecutando 4:
			for (WorkOrderService workOrderService : workOrderServiceList) {
				woServiceDAO.deleteWorkOrderService(workOrderService);
			}
			
			//Ejecutando 5
			//TODO jjimenezh 2011-01-22 Vovler a enviar el código del servicio y nó el id
			manageWorkForceServiceBroker.removeServiceFromWorkOrder(workOrder.getWoCode(), service.getServiceCode(), countryCode);
			//coreWOServiceBroker.removeServiceFromWorkOrder(workOrder.getWoCode(), "" + service.getId(), countryCode);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/deleteServiceToWorkOrder");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina deleteServiceToWorkOrder/CoreWOBusiness ==");
		}
	}

	/**
	 * @see ICoreWOBusiness#getWorkOrderByDealerAndCreationDate(DealerVO, Date, Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrderByDealerAndCreationDate(DealerVO dealer, Date initDate, Date endDate) throws BusinessException {
		UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(initDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(endDate, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = null;

		log.debug("== Inicia getWorkOrderByDealerAndCreationDate/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por su fecha de creaciï¿½n
			woList = workOrderDAOBean.getWorkOrderByDealerAndCreationDate(UtilsBusiness.copyObject(Dealer.class,dealer),initDate, endDate);
			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getWorkOrderByDealerAndCreationDate");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByDealerAndCreationDate/CoreWOBusiness ==");
		}
		return wos;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrdersActiveAndSuspend()
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrdersActiveAndSuspend()
	throws BusinessException {
		List<WorkOrderVO> wos = null;
		List<WorkOrder> woList = new ArrayList<WorkOrder>();
		WorkorderStatus activeStatus, suspendStatus;
		WorkorderStatusVO activeStatusVO, suspendStatusVO;

		log.debug("== Inicia getWorkOrdersActiveAndSuspend/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por estado ACTIVE
			activeStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity());
			activeStatusVO = UtilsBusiness.copyObject(WorkorderStatusVO.class, activeStatus);
			woList.addAll(workOrderDAOBean.getWorkOrderByActualStatus(activeStatusVO));
			// Obtener las WorkOrder por estado SUSPEND
			suspendStatus = workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
			suspendStatusVO = UtilsBusiness.copyObject(WorkorderStatusVO.class, suspendStatus);
			woList.addAll(workOrderDAOBean.getWorkOrderByActualStatus(suspendStatusVO));

			wos = UtilsBusiness.convertList(woList, WorkOrderVO.class);
			fillLastWoAssigments(wos);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrdersActiveAndSuspend");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersActiveAndSuspend/CoreWOBusiness ==");
		}
		return wos;


	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrdersActiveAndSuspendByCountryId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersActiveAndSuspendByCountryId(Long countryId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		List<WorkOrderVO> wos = null;
		WOActiveAndSuspendByCountryIdPaginationResponse r = null;
		log.debug("== Inicia getWorkOrdersActiveAndSuspendByCountryId/CoreWOBusiness ==");
		try {
			// Obtener las WorkOrder por estado ACTIVE y PENDING
			r = workOrderDAOBean.getWorkOrderByActualStatusCodeAndCountryId(countryId, requestCollectionInfo, CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity(), CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
			// Obtener las WorkOrder por estado SUSPEND
			wos = UtilsBusiness.convertList(r.getCollection(), WorkOrderVO.class);
			fillLastWoAssigments(wos);
			r.setCollectionVO(wos);
			r.setCollection(null);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrdersActiveAndSuspendByCountryId");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersActiveAndSuspendByCountryId/CoreWOBusiness ==");
		}
		return r;
	}

	/**
	 * Metodo: <Descripcion>
	 * @param wos lista de work orders
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private void fillLastWoAssigments(List<WorkOrderVO> wos) throws DAOServiceException, DAOSQLException, BusinessException {
		if(wos==null || wos.size()==0){
			log.warn("Lista de workOrders vacia o nula");
			return;
		}
		WoAssignment woAssignment = null;
		for (WorkOrderVO workOrderVO : wos) {
			woAssignment = workAssignmentDAOBean.getLastDealerAssignmentByWoIdWithoutWo(workOrderVO.getId());
			if(woAssignment != null){
				woAssignment.setWorkOrder(null);
				workOrderVO.setWorkOrderAssignment(UtilsBusiness.copyObject(WoAssignmentVO.class, woAssignment));
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOByCustomerQBEPaginationResponse getWorkOrdersByDealerCustomerQBE(Long dealerId, String ibsCode,
			String officePhone, String homePhone, String faxPhone,
			String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo)
			throws BusinessException {
		List<WoAssignmentVO> wos = null;
		WOByCustomerQBEPaginationResponse r = null;
		log.debug("== Inicia getWorkOrdersByDealerCustomerQBE/CoreWOBusiness ==");
		try {
			r = workOrderDAOBean.getWorkOrdersByDealerCustomerQBE(dealerId, ibsCode, officePhone, homePhone, faxPhone, cellPhone, idNumber, name, lastName, requestCollectionInfo);
			wos = UtilsBusiness.convertList(r.getCollection(), WoAssignmentVO.class);
			r.setCollectionVO(wos);
			r.setCollection(null);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrdersByDealerCustomerQBE");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersByDealerCustomerQBE/CoreWOBusiness ==");
		}
		return r;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOByDealerDateCrewQBEPaginationResponse getWorkOrdersByDealerDateCrewQBE(Long dealerId,
			Long dateId, Long crewId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		List<WoAssignmentVO> wos = null;
		WOByDealerDateCrewQBEPaginationResponse r = null;
		log.debug("== Inicia getWorkOrdersByDealerDateCrewQBE/CoreWOBusiness ==");
		try {
			r = workOrderDAOBean.getWorkOrdersByDealerDateCrewQBE(dealerId, dateId, crewId, requestCollectionInfo);
			wos = UtilsBusiness.convertList(r.getCollection(), WoAssignmentVO.class);
			r.setCollectionVO(wos);
			r.setCollection(null);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrdersByDealerDateCrewQBE");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersByDealerDateCrewQBE/CoreWOBusiness ==");
		}
		return r;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOByDealerWorkOrderQBEPaginationResponse getWorkOrdersByDealerWorkOrderQBE(Long dealerId, String woCode,
			Long ServiceTypeId, Long serviceCategoryId, Long woStatusId,
			Long stateId, Long cityId, Long postalCodeId, Date creationDate,
			Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		List<WoAssignmentVO> listToReturn=null;
		WOByDealerWorkOrderQBEPaginationResponse r = null;

		log.debug("== Inicia getWorkOrdersByDealerWorkOrderQBE/CoreWOBusiness ==");
		try {
			 r = workOrderDAOBean.getWorkOrdersByDealerWorkOrderQBE(dealerId, woCode, ServiceTypeId, serviceCategoryId, woStatusId, stateId, cityId, postalCodeId, creationDate, programmingDate, requestCollectionInfo);

			 listToReturn = new ArrayList<WoAssignmentVO>();
			 List<Long> cacheList = new ArrayList<Long>();
			
			 for (WoAssignment item : r.getCollection()) {
				if( !cacheList.contains( item.getWorkOrder().getId() ) ){
					listToReturn.add( UtilsBusiness.copyObject(WoAssignmentVO.class, item) );
					cacheList.add( item.getWorkOrder().getId() );
				}
			 }
			r.setCollectionVO(listToReturn);
			r.setCollection(null);			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrdersByDealerWorkOrderQBE");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersByDealerWorkOrderQBE/CoreWOBusiness ==");
		}
		return r;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByCustomerDataQBE(Long countryId, String ibsCode,
			String officePhone, String homePhone, String faxPhone,
			String cellPhone, String idNumber, String name, String lastName, RequestCollectionInfo requestCollectionInfo)
			throws BusinessException {
		log.debug("== Inicia getWorkOrdersByCustomerDataQBE/CoreWOBusiness ==");
		WOActiveAndSuspendByCountryIdPaginationResponse r = null;

		try {
			UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se ha especificado el identificador del país");
			r = workOrderDAOBean.getWorkOrdersByCustomerDataQBE(countryId, ibsCode, officePhone, homePhone, faxPhone, cellPhone, idNumber, name, lastName,requestCollectionInfo);
			r.setCollectionVO(UtilsBusiness.convertList(r.getCollection(), WorkOrderVO.class));
			fillLastWoAssigments(r.getCollectionVO());
			r.setCollection( null );
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrdersByCustomerDataQBE");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersByCustomerDataQBE/CoreWOBusiness ==");
		}
		return r;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WOActiveAndSuspendByCountryIdPaginationResponse getWorkOrdersByWorkOrderDataQBE(Long dealerId,
			String woCode, Long ServiceTypeId, Long serviceCategoryId,
			Long woStatusId, Long countryId, Long stateId, Long cityId, Long postalCodeId,
			Date creationDate, Date programmingDate, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		log.debug("== Inicia getWorkOrdersByWorkOrderDataQBE/CoreWOBusiness ==");
		WOActiveAndSuspendByCountryIdPaginationResponse r = null;
		try {
			UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se ha especificado el identificador del país");
			r = workOrderDAOBean.getWorkOrdersByWorkOrderDataQBE(dealerId, woCode, ServiceTypeId, serviceCategoryId, woStatusId, countryId, stateId, cityId, postalCodeId, creationDate, programmingDate,requestCollectionInfo);
			r.setCollectionVO(UtilsBusiness.convertList(r.getCollection(), WorkOrderVO.class));
			fillLastWoAssigments(r.getCollectionVO());
			r.setCollection( null );
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrdersByWorkOrderDataQBE");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersByWorkOrderDataQBE/CoreWOBusiness ==");
		}
		return r;
	}

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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WoStatusHistoryVO getWorkorderReasonByWoHistory(Long woId, Long woStatusId) throws BusinessException {
		log.debug("== Inicia getWorkorderReasonByWoHistory/CoreWOBusiness ==");
		WoStatusHistoryVO historyVo;
		try {
			historyVo = UtilsBusiness.copyObject(WoStatusHistoryVO.class,woStatusHistoryDAO.getWorkorderReasonByWoHistory(woId, woStatusId) );
			return historyVo;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkorderReasonByWoHistory");
			throw super.manageException(ex);
		}
	}

	/**
	 * 
	 * Metodo: Retorna un listado de WorkOrderAgenda, filtrando por
	 * el dealerId y WorkOrderId por medio de la WorkOrder Assignment
	 * @param woId Long
	 * @param dealerId Long
	 * @return List<WorkOrderAgendaVO>
	 * @throws BusinessException
	 * @author jalopez
	 */
	public List<WorkOrderAgendaVO> getWorkOrderAgendaByWoAssignment(Long woId,Long dealerId) throws BusinessException {

		log.debug("== Inicia getWorkOrderAgendaByWoAssignment/CoreWOBusiness ==");		
		if(woId == null || dealerId == null ){
			throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
		}
		try {
			return UtilsBusiness.convertList(workOrderAgendaDAOLocal.getWorkOrderAgendaByWoAssignment(woId, dealerId),WorkOrderAgendaVO.class );			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrderAgendaByWoAssignment");
			throw super.manageException(ex);
		}
	}

	/**
	 * Llena la informacion necesaria para generar el reporte de ordenes de trabajo.
	 * @param workOrderVO - Orden de trabajo a la cual se le debe completar la informacion relacionada
	 * @throws BusinessException 
	 */
	private void fillDataWorkOrderDetailHelper(WorkOrderVO workOrderVO) throws BusinessException{
		try{
			log.debug("== Inicia fillDataWorkOrderDetailHelper/CoreWOBusiness ==");

			Dealer dealer = this.dealersDAO.getDealerByID(workOrderVO.getDealerId());
			WorkOrderAgenda woAgenda =  this.workOrderAgendaDAO.getLastWorkOrderAgendaByWoID(workOrderVO.getId());
			WoAssignment woAssignment = woAssignmentDAO.getLastDealerAssignmentByWoId(workOrderVO.getId());
			Crew crew = null;
			if(woAssignment!=null && woAssignment.getCrewId() !=null){
				crew = crewsDAO.getCrewById(woAssignment.getCrewId());
				if(crew != null){
					//Obteniendo el nombre del técnico responsable de la cuadrilla:
					Employee resposible = employeesCrewDAO.getEmployeeResponsibleByCrewId(crew.getId());
					if(resposible != null){
						workOrderVO.setTecnicianName(resposible.getLastName() + " " + resposible.getFirstName());
					}else{
						workOrderVO.setTecnicianName(ApplicationTextEnum.NOT_FOUND_RESPONSIBLE_CREW.getApplicationTextValue());
					}
				}else{
					workOrderVO.setTecnicianName(ApplicationTextEnum.NO_CREW_ASSIGNED_WO.getApplicationTextValue());
				}
			}
			
			if(woAgenda != null){
				workOrderVO.setTieneServiceHour(true);
				workOrderVO.setServiceHour(woAgenda.getAgendationDate());
				workOrderVO.setJornada(UtilsBusiness.copyObject(ServiceHourVO.class, woAgenda.getServiceHour()) );
				//workOrderVO.setLastService(woAgenda.getAgendationDate());
				//Módificaciones realizadas por gfandino el 28 de julio de 2010
				//Para consultar última fecha de atención a un Customer
				//1) Se consulta la wo para obtener el cliente
				//2) Se consultan las wo del cliente.
				//3) se obtiene la última wo
				//4) Se setea la última realización en lastService
				//1) Se consulta la wo para obtener el cliente
				
			}else{
				workOrderVO.setTieneServiceHour(false);
				workOrderVO.setServiceHour(null);
			}

			WorkOrder woTmp =  this.workOrderDAOBean.getWorkOrderByID(workOrderVO.getId());
			//2) Se consultan las wo del cliente.
			List<WorkOrder>woListTmp= this.workOrderDAOBean.getWorkOrderByCustomerAndRealizationDateExist(woTmp.getCustomer());
			//3) se obtiene la última wo
			//4) Se setea la última realización en lastService
			if(woListTmp.size()!=0){
				workOrderVO.setTieneLastService(true);
				workOrderVO.setLastService(woListTmp.get(0).getWoRealizationDate());
			}else{
				workOrderVO.setTieneLastService(false);
				workOrderVO.setLastService(null);
			}

			if(dealer != null){
				workOrderVO.setDealerInstaller(dealer.getDealerName());
			}
			//Fin de los cambios realizados
			if(crew!=null){
				workOrderVO.setServiceVehicle(crew.getVehicle());	
			}
			if(workOrderVO.getSaleDealer()!=null){
				workOrderVO.setSellerName(workOrderVO.getSaleDealer().getDealerName());
				workOrderVO.setSellerCode("" + workOrderVO.getSaleDealer().getDealerCode());
			}

			List<ShippingOrder> shippingOrders = shippingOrderDAO.getShippingOrdersByWorkOrder(workOrderVO.getId());
			workOrderVO.setShippingOrders(UtilsBusiness.convertList(shippingOrders, ShippingOrderVO.class));

			if(log.isDebugEnabled()){
				log.debug("Se encontraron "+shippingOrders.size()+" S.O. de la woId: "+workOrderVO.getId());
				log.debug("Se encontraron " + workOrderVO.getWorkOrderServices().size() + " WorkOrderServices");
			}

			workOrderVO.setCommentsIBS(null);

			//Limpieza de relaciones innecesarias para evitar trafico innecesario de informacion
			workOrderVO.setSaleDealer(null);

		}catch (Throwable e) {
			log.error("Error en CoreWOBusiness\fillDataWorkOrderDetailHelper");
			throw super.manageException(e);
		}finally{
			log.debug("== Termina fillDataWorkOrderDetailHelper/CoreWOBusiness ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrderReasonByWoStatus(co.com.directv.sdii.model.vo.WorkorderStatusVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkorderReasonVO> getWorkOrderReasonByWoStatus(WorkorderStatusVO woStatus,Long userId) throws BusinessException {
		log.debug("== Inicia getWorkOrderReasonByWoStatus/CoreWOBusiness ==");		
		UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(woStatus.getWoStateCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			WorkorderStatus woStatusTmp = this.workorderStatusDAO.getWorkorderStatusByCode(woStatus.getWoStateCode());
			List<WorkorderReason> daoReponse = null;
			if( userId == null || userId.longValue() <= 0 )
				daoReponse = this.woReasonDAO.getWorkOrderReasonByWoStatus(woStatusTmp.getId());
			else{
				User user = this.userDao.getUserById(userId);
				//Si el usuario es backoffice puede ver todos los estados
				if( user.getRol().getRoleType().getRoleTypeCode().equals( CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity() ) )
					daoReponse = this.woReasonDAO.getWorkOrderReasonByWoStatus(woStatusTmp.getId());
				else
					daoReponse = this.woReasonDAO.getWorkOrderReasonByWoStatusAndBySolveByCI(woStatusTmp.getId(),CodesBusinessEntityEnum.REASON_IS_SOLVE_BY_CI.getCodeEntity());
			}
			return UtilsBusiness.convertList(daoReponse, WorkorderReasonVO.class) ;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrderReasonByWoStatus");
			throw super.manageException(ex);
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getCustomerByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CustomerVO getCustomerByCode(String customerCode)
	throws BusinessException {
		log.debug("== Inicia getCustomerByCode/CoreWOBusiness ==");		
		UtilsBusiness.assertNotNull(customerCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			Customer customerPojo = customerDAO.getCustomerByCode(customerCode);
			if(customerPojo == null){
				throw new BusinessException(ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage());
			}
			CustomerVO customer = UtilsBusiness.copyObject(CustomerVO.class, customerPojo);
			fillAditionalCustomerData(customer);
			return customer;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getCustomerByCode/getWorkOrderReasonByWoStatus");
			throw super.manageException(ex);
		}finally{
			log.debug("== Termina getCustomerByCode/CoreWOBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getCustomerById(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CustomerVO getCustomerById(Long customerId) throws BusinessException {
		log.debug("== Inicia getCustomerById/CoreWOBusiness ==");		
		UtilsBusiness.assertNotNull(customerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try {
			Customer customerPojo = customerDAO.getCustomerByID(customerId);
			CustomerVO customer = UtilsBusiness.copyObject(CustomerVO.class, customerPojo);
			fillAditionalCustomerData(customer);
			return customer;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getCustomerById/getWorkOrderReasonByWoStatus");
			throw super.manageException(ex);
		}finally{
			log.debug("== Termina getCustomerById/CoreWOBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getCustomerByWoIdOrWoCode(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CustomerVO getCustomerByWoIdOrWoCode(Long workOrderId,
			String workOrderCode) throws BusinessException {
		log.debug("== Inicia getCustomerByWoIdOrWoCode/CoreWOBusiness ==");		
		if(workOrderId == null && workOrderCode == null){
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
		try {
			Customer customerPojo = null;
			if(workOrderId != null && workOrderId.longValue() != 0L){
				customerPojo = customerDAO.getCustomerByWoId(workOrderId);
			}else if(workOrderCode != null && workOrderCode.trim().length() > 0){
				customerPojo = customerDAO.getCustomerByWoCode(workOrderCode);
			}
			CustomerVO customer = UtilsBusiness.copyObject(CustomerVO.class, customerPojo);
			fillAditionalCustomerData(customer);
			return customer;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getCustomerByWoIdOrWoCode/getWorkOrderReasonByWoStatus");
			throw super.manageException(ex);
		}finally{
			log.debug("== Termina getCustomerByWoIdOrWoCode/CoreWOBusiness ==");
		}
	}

	/**
	 * Metodo: <Descripcion>
	 * @param customer cliente
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	private void fillAditionalCustomerData(CustomerVO customer) throws DAOServiceException, DAOSQLException{
		Long doucmentTypeId = customer.getDocumentTypeId();
		DocumentType documentType = documentTypesDao.getDocumentTypesByID(doucmentTypeId);
		if(documentType != null){
			customer.setDocumentTypeName(documentType.getDocumentTypeName());
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getLastWorkOrderAssigmentByWoId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WoAssignmentVO getLastWorkOrderAssigmentByWoId(Long workOrderId) throws BusinessException{
		log.debug("== Inicia getLastWorkOrderAssigmentByWoId/CoreWOBusiness ==");
		try {
			WoAssignment woAssignmentPojo = woAssignmentDAO.getLastDealerAssignmentByWoId(workOrderId);
			WoAssignmentVO result = UtilsBusiness.copyObject(WoAssignmentVO.class, woAssignmentPojo);
			return result;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getLastWorkOrderAssigmentByWoId");
			throw super.manageException(ex);
		}finally{
			log.debug("== Termina getLastWorkOrderAssigmentByWoId/CoreWOBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrderServicesByWoId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WorkOrderServiceVO> getWorkOrderServicesByWoId(Long workOrderId)throws BusinessException{
		log.debug("== Inicia getWorkOrderServicesByWoId/CoreWOBusiness ==");
		try {
			RequestCollectionInfo requestCollectionInfo = null; //no requiere paginar en este punto.
			WorkOrderServiceResponse response = woServiceDAO.getWOServiceByWorkorderId(workOrderId,requestCollectionInfo);
			List<WorkOrderService> worServPojoList = response.getWorkOrderServices();
			List<WorkOrderServiceVO> worServVoList = UtilsBusiness.convertList(worServPojoList, WorkOrderServiceVO.class);
			return worServVoList;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWorkOrderServicesByWoId");
			throw super.manageException(ex);
		}finally{
			log.debug("== Termina getWorkOrderServicesByWoId/CoreWOBusiness ==");
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrderByIDAndCountry(java.lang.Long, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderVO getWorkOrderByCodeAndCountry(String id, String countryCode)throws BusinessException {
		UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		WorkOrderVO wosVO = null;
		WorkOrder wo = null;
		log.debug("== Inicia getWorkOrderByCodeAndCountry/CoreWOBusiness ==");
		try {
			wo = workOrderDAOBean.getWorkOrderByCodeAndCountry(id,countryCode);
			if(wo == null){
				return null;
			}
			wosVO = UtilsBusiness.copyObject(WorkOrderVO.class, wo);
			this.fillDataWorkOrderDetailHelper(wosVO);
			return wosVO;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrderByCodeAndCountry");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByCodeAndCountry/CoreWOBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWorkOrderByIDAndCountry(java.lang.Long, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WorkOrder getWorkOrderByWoCodeAndCountryCode(String countryCode,String woCode)throws BusinessException {
		log.debug("== Inicia getWorkOrderByWoCodeAndCountryCode/CoreWOBusiness ==");
		try {
			return workOrderDAOBean.getWorkOrderByCodeAndCountry(woCode,countryCode);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrderByWoCodeAndCountryCode");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderByWoCodeAndCountryCode/CoreWOBusiness ==");
		}
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getShippingOrderByCodeAndWOCodeAndCountry(java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ShippingOrderVO getShippingOrderByCodeAndWOCodeAndCountry(String shipOrderID, String woCode, String countryCode)throws BusinessException {
		UtilsBusiness.assertNotNull(shipOrderID, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(woCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		ShippingOrderVO shippingOrderVO = null;
		ShippingOrder shippingOrder = null;
		log.debug("== Inicia getShippingOrderByCodeAndWOCodeAndCountry/CoreWOBusiness ==");
		try {
			shippingOrder = shippingOrderDAO.getShippingOrderByCodeAndWOCodeAndCountry(shipOrderID, woCode, countryCode);
			if(shippingOrder == null){
				return null;
			}
			shippingOrderVO = UtilsBusiness.copyObject(ShippingOrderVO.class, shippingOrder);
			return shippingOrderVO;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getShippingOrderByCodeAndWOCodeAndCountry");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getShippingOrderByCodeAndWOCodeAndCountry/CoreWOBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getShippingOrdersByWOCodeAndCountry(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ShippingOrderVO> getShippingOrdersByWOCodeAndCountry(String woCode, String countryCode)throws BusinessException {
		UtilsBusiness.assertNotNull(woCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		List<ShippingOrderVO> listShippingOrderVO = null;
		List<ShippingOrder> listShippingOrder = null;
		log.debug("== Inicia getShippingOrdersByWOCodeAndCountry/CoreWOBusiness ==");
		try {
			listShippingOrder = shippingOrderDAO.getShippingOrderByWOCodeAndCountry(woCode, countryCode);
			if(listShippingOrder == null){
				return null;
			}
			listShippingOrderVO = UtilsBusiness.convertList(listShippingOrder,ShippingOrderVO.class);
			return listShippingOrderVO;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getShippingOrdersByWOCodeAndCountry");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getShippingOrdersByWOCodeAndCountry/CoreWOBusiness ==");
		}
	}

    /**
     * Método: Permite consultar las work order paginadas
     * resultado
     * @return Lista de work orders
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderVO> getWorkOrdersByPage(Long countryId, Long woId, String statusCodeProccess)
	throws BusinessException {
		log.debug("== Inicia getWorkOrdersByPage/CoreWOBusiness ==");
		try {
			Long pageSize = getWorkOrders2AllocPageSize(countryId);
			int delayAllocator = getDelayAllocator(countryId);
			int delayWorkOrderCSRStatusPendent = getDelayWorkOrderCSRStatusPendent(countryId);
			Long userId = getUserIdAdmin(countryId) ;
			Long dealerDummy = getDealerIdDummy(countryId);
			List<WorkOrder> resultPojo = workOrderDAOBean.getWorkOrdersByPage(woId, 
					                                                          pageSize,
					                                                          delayAllocator,
					                                                          delayWorkOrderCSRStatusPendent,
					                                                          userId,
					                                                          dealerDummy,
					                                                          countryId, statusCodeProccess);
			List<WorkOrderVO> result = UtilsBusiness.convertList(resultPojo, WorkOrderVO.class);
			return result;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/getWorkOrdersByPage");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersByPage/CoreWOBusiness ==");
		}
	}
	
	/**
	 * Metodo: <Descripcion>
	 * @param countryId id del pais
	 * @return <tipo> <descripcion>
	 * @author jjimenezh
	 * @throws BusinessException 
	 */
	private long getDealerIdDummy(Long countryId) throws BusinessException {
		long result = 0L;
		String parameterCode = null;
		Dealer d = null;
		Long dealerId = null; 
		try {
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_DEALER_DUMMY_CODE.getCodeEntity();
			SystemParameter pageSizeParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,countryId);
			if(pageSizeParam!=null){
				result = Long.parseLong(pageSizeParam.getValue());
				d = dealersDAO.getDealerByDealerCode( result);
				if(d==null){
					String error = ": el dealer dummy con código " + result + " del país " + countryId + " no existe.";
	        		throw new BusinessException(ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage() + error);
				}
				dealerId = d.getId();
			}
			return dealerId;
		} catch (Exception e) {
			log.error("Al tratar de obtener el parámetro del sistema dealer dummy: " + parameterCode, e);
			throw super.manageException(e);
		}
	}

	/**
	 * Metodo: <Descripcion>
	 * @param countryId id del pais
	 * @return <tipo> <descripcion>
	 * @author jjimenezh
	 */
	private Long getWorkOrders2AllocPageSize(Long countryId) {
		Long result = 100L;
		String parameterCode = null;
		try {
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_WORKORDERS_2_ALLOC_PAGE_SIZE.getCodeEntity();
			SystemParameter pageSizeParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,countryId);
			if(pageSizeParam!=null){
				result = Long.parseLong(pageSizeParam.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Al tratar de obtener el parámetro del sistema que determina la cantidad de work orders por página para asignar, el código del parámetro es: " + parameterCode, e);
		}
		return result;
	}

	private int getDelayAllocator(Long countryId) {
		int result = 5;
		String parameterCode = null;
		try {
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_DELAY_ALLOCATOR.getCodeEntity();
			SystemParameter pageSizeParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,countryId);
			if(pageSizeParam!=null){
				result = Integer.parseInt(pageSizeParam.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Al tratar de obtener el parámetro del sistema que determina el tiempo de espera para obtener las wo: " + parameterCode, e);
		}
		return result;
	}
	
	private Long getUserIdAdmin(Long countryId) {
		Long userId = null;
		String paramIbs = "";
		String parameterCode = null;
		try {
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_WORK_ORDER_OWNER_USER_LOGIN.getCodeEntity();
			SystemParameter pageSizeParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,countryId);
			if(pageSizeParam!=null){
				paramIbs = pageSizeParam.getValue();
				User sample = new User();
				sample.setLogin( paramIbs );
				List<User> ibsUser = this.userDao.getUserBySample(sample);
				if( ibsUser != null && !ibsUser.isEmpty() ){
					userId = ibsUser.get(0).getId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Al tratar de obtener el parámetro del sistema que determina el tiempo de espera para obtener las wo: " + parameterCode, e);
		}
		return userId;
	}
	
	/**
	 * Metodo: <Descripcion>
	 * @param countryId id del pais
	 * @return <tipo> <descripcion>
	 * @author jjimenezh
	 */
	private int getDelayWorkOrderCSRStatusPendent(Long countryId) {
		int result = 5;
		String parameterCode = null;
		try {
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_DELAY_WORKORDERS_CSR_PENDING.getCodeEntity();
			SystemParameter pageSizeParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,countryId);
			if(pageSizeParam!=null){
				result = Integer.parseInt(pageSizeParam.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Al tratar de obtener el parámetro del sistema que determina el tiempo de espera para obtener las wo del csr pendientes: " + parameterCode, e);
		}
		return result;
	}
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#changeWorkOrderStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void changeWorkOrderStatus(String customerCode, String countryCode, String workOrderCode, String workOrderReason2ChangeCode,String newWorkOrderStatusCode) throws BusinessException {

		UtilsBusiness.assertNotNull(customerCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(workOrderReason2ChangeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(newWorkOrderStatusCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

		/*
		 * Para ejecutar este caso de uso:
		 * 1. Verificar que existe la orden de trabajo
		 * 2. Verificar que se puede realizar la transición de un estado a otro.
		 * 3. Verificar que el cliente existe
		 * 4. Verificar que la orden de trabajo está asociada a ese cliente
		 * 5. Verificar que la workorder reason existe
		 * 6. Verificar que el nuevo estado exista dado el código
		 * 7. Ejecutar el caso de uso ADS - 33 para actualizar el estado en IBS
		 */

		log.debug("== Inicia changeWorkOrderStatus/CoreWOBusiness ==");
		try {

			//Ejecutando 1:
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByCodeAndCountry(workOrderCode,countryCode);

			if(workOrder == null){
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
			}

			//Ejecutando 2
			//Ejecutar la operación de la máquina de estados que permite validar el cambio
			WorkorderStatus woStatus = workorderStatusDAO.getWorkorderStatusByIBS6StatusCode(newWorkOrderStatusCode);
			UtilsBusiness.assertNotNull(woStatus, ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_STATUS_DOES_NOT_EXIST.getMessage() + " no existe dado el código de ibs: " + newWorkOrderStatusCode);

			WorkOrderVO workOrderTmp = new WorkOrderVO();
			workOrderTmp.setWorkorderStatusByActualStatusId(workOrder.getWorkorderStatusByActualStatusId());
			workOrderTmp.setWorkorderStatusByPreviusStatusId(workOrder.getWorkorderStatusByPreviusStatusId());

			this.stateMachine.validateStatusChangeWorkOrder(workOrderTmp,woStatus.getId());


			//Ejecutando 3:
				Customer customer = customerDAO.getCustomerByCode(""+customerCode);
				if(customer == null){
					throw new BusinessException(ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_DOES_NOT_EXIST.getMessage() + " No se encontró el cliente por el código especificado: " + customerCode);
				}

				//Ejecutando 4:
				if(workOrder.getCustomer().getId().longValue() != customer.getId().longValue()){
					throw new BusinessException(ErrorBusinessMessages.WORKORDER_CUSTOMERID_AND_CUSTOMERID_DOES_NOT_MATCH.getCode(), ErrorBusinessMessages.WORKORDER_CUSTOMERID_AND_CUSTOMERID_DOES_NOT_MATCH.getMessage());
				}

				//Ejecutando 5:
				WorkorderReason workorderReason = woReasonDAO.getWorkorderReasonByCode(workOrderReason2ChangeCode);
				if(workorderReason == null){
					throw new BusinessException(ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_REASON_DOES_NOT_EXIST.getMessage());
				}

				//Ejecutando 6:
				Long newStatusId = woStatus.getId();
				String comment = workorderReason.getWorkorderReasonName();
				WorkOrderDTO woDTO = new WorkOrderDTO();
				woDTO.setWorkOrder(workOrder);
				workOrder = changeWorkOrderStatus(woDTO, newStatusId, workorderReason, comment,null, new WoStatusHistory(),null);

				//Se encapsula la informacion en un DTO para pasarlo al broker
				
				//Ejecutando 7:

		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatus/CoreWOBusiness ==");
		}
	}

	/**
	 * Metodo: Valida un objeto determinando si es nulo, en caso que sea nulo lanza
	 * una excepción y escribe en el log el atributo que fué validado
	 * @param parameterName Nombre del atributo a ser validado o mensaje para
	 * ser escrito en el log en caso que el objeto sea nulo
	 * @param value2Validate objeto a ser validado
	 * @param errorCode código de error a ser lanzado
	 * @param errorMessage mensaje de error a ser lanzado
	 * @throws BusinessException En caso que el objeto a validar sea nulo
	 * @author jjimenezh
	 */
	private void validateResult(String parameterName, Object value2Validate, String errorCode, String errorMessage) throws BusinessException{
		try {
			UtilsBusiness.assertNotNull(value2Validate, errorCode, errorMessage);
		} catch (BusinessException e) {
			log.debug("== Error de validación de parámetros de respuesta de un WS de ibs: el parámetro: \""+parameterName+"\" llegó nulo ==");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Method: Valida si una Work order tiene agendamiento. En caso de que haya sido rechazada devuelve false
	 * @param workOrderId id de la work order
	 * @param dealerId id del dealer
	 * @return Boolean
	 * @throws BusinessException
	 * @author jalopez
	 */
	@Override
	public Boolean getValidateWorkOrderScheduled(Long workOrderId, Long dealerId) throws BusinessException{
		log.debug("== Inicia changeWorkOrderStatus/CoreWOBusiness ==");
		try{
			UtilsBusiness.assertNotNull(workOrderId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
						
			// Si el estado previo de la orden es "rechazada" y el estado actual es "reasignada" el m�todo devuelve false:
			if(this.wasWorkOrderRejectedBeforeReassigned(workOrderId)){
				/*cuando el dealaer que rechazó la wo, sea el mismo al que se va a agendar, dejar continuar*/
				List<WoAssignment> woAssignments = workAssignmentDAOBean.getWorkOrderAssignments(workOrderId);
				if(woAssignments.size()>=2){
					/*si el dealer de la anteúltima y la actual asignación son distintos, no continuar analisis*/
					if(woAssignments.get(1).getDealerId()!=null && !woAssignments.get(1).getDealerId().equals(dealerId))
						return false;
				} else
					return false;
			}
			
			Boolean isScheduled;
			WorkOrder wo = workOrderDAOBean.getWorkOrderByID(workOrderId);
			WorkorderStatus assignedStatus = this.workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
			List<WorkOrderAgenda> listWOAgenda = workOrderAgendaDAO.getWorkOrderAgendaByWoDealer(workOrderId, dealerId);
			
			// Si la WO ya tiene registros de agendamiento y no se encuentra en estado ASIGNADO, quiere decir que previamente fue agendada
			if(listWOAgenda != null && !listWOAgenda.isEmpty() && !wo.getWorkorderStatusByActualStatusId().getWoStateCode().equals(assignedStatus.getWoStateCode())){
				isScheduled = true;
			}else{
				isScheduled = false;				
			}
			return isScheduled;
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación CoreWOBusiness/changeWorkOrderStatus");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina changeWorkOrderStatus/CoreWOBusiness ==");
		}
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getDealerUsedCapacity(co.com.directv.sdii.assign.schedule.DealerWorkCapacityCriteria)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getDealerUsedCapacity(DealerWorkCapacityCriteria dealerWCCriteria) throws ScheduleException {
		log.debug("== Inicia getDealerUsedCapacity/CoreWOBusiness ==");
		int response = 0;
		try {
			if( dealerWCCriteria == null ){
				throw new ScheduleException(ErrorBusinessMessages.ALLOCATOR_AL037.getCode(),ErrorBusinessMessages.ALLOCATOR_AL037.getMessage());
			} else if( dealerWCCriteria.getCapacityDate() == null ){
				throw new ScheduleException(ErrorBusinessMessages.ALLOCATOR_AL030.getCode(),ErrorBusinessMessages.ALLOCATOR_AL030.getMessage());
			} else if( dealerWCCriteria.getCountryId() == null || dealerWCCriteria.getCountryId().longValue() <= 0 ){
				throw new ScheduleException(ErrorBusinessMessages.ALLOCATOR_AL032.getCode(),ErrorBusinessMessages.ALLOCATOR_AL032.getMessage());
			} else if( dealerWCCriteria.getDealerId() == null || dealerWCCriteria.getDealerId().longValue() <= 0 ){
				throw new ScheduleException(ErrorBusinessMessages.ALLOCATOR_AL035.getCode(),ErrorBusinessMessages.ALLOCATOR_AL035.getMessage());
			} else if( dealerWCCriteria.getServiceHourId() == null || dealerWCCriteria.getServiceHourId().longValue() <= 0 ){
				throw new ScheduleException(ErrorBusinessMessages.ALLOCATOR_AL044.getCode(),ErrorBusinessMessages.ALLOCATOR_AL044.getMessage());
			} else if( dealerWCCriteria.getSuperCategoryServiceId() == null || dealerWCCriteria.getSuperCategoryServiceId().longValue() <= 0 ){
				throw new ScheduleException(ErrorBusinessMessages.ALLOCATOR_AL046.getCode(),ErrorBusinessMessages.ALLOCATOR_AL046.getMessage());
			}
			response = this.workOrderDAOBean.getDealerUsedCapacity(dealerWCCriteria);
		} catch (ScheduleException e) {
			throw e;
		} catch (Throwable e) {
			log.debug("== Error getDealerUsedCapacity/CoreWOBusiness ==",e);
			throw new ScheduleException(ErrorBusinessMessages.ALLOCATOR_AL004.getCode(),ErrorBusinessMessages.ALLOCATOR_AL004.getMessage());
		} finally{
			log.debug("== Termina getDealerUsedCapacity/CoreWOBusiness ==");
		}
		return response;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#validateWoBeforeGeneratingPdf(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EnvelopeEncapsulateResponse> validateWoBeforeGeneratingPdf(List<Long> woIds, Long countryId) throws BusinessException {
		log.debug("== Inicia validateWoBeforeGeneratingPdf/CoreWOBusiness ==");
		// valida que la lista de WO tenga wo.
		if (woIds == null) {
			throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
		}
		if (woIds.size() == 0 || woIds.isEmpty()) {
			throw new BusinessException(ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage());
		}
		// simulacion del where realizado en el jasper
		WorkOrder pdfWorkOrder = null;
		List<Service> woService = null;
		WoAssignment woAssignment = null;
		//06-07-2011 jnova se elimina validacion de agendamiento

		List<EnvelopeEncapsulateResponse> woExc = new ArrayList<EnvelopeEncapsulateResponse>();

		try {
			// valida la cantidad maxima de work orders para generar PDF
			String parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_WO_PDF_MAX_WORK_ORDERS_BY_PDF_FILE.getCodeEntity();
			Long maxWorkOrdersPerPdfFile = UtilsBusiness.getNumericSystemParameter(parameterCode, countryId, systemParameterDAO);

			if (maxWorkOrdersPerPdfFile < woIds.size()) {
				//Error code: BL121
				EnvelopeEncapsulateResponse pdfExceptionDTO = new EnvelopeEncapsulateResponse();
				EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.MAX_WORK_ORDES_PER_PDF_FILE_EXCEEDED.getCode(),
						ErrorBusinessMessages.MAX_WORK_ORDES_PER_PDF_FILE_EXCEEDED.getMessage());
				pdfExceptionDTO.getExceptions().add(exception);
				pdfExceptionDTO.setWoCode(ErrorBusinessMessages.MAX_WORK_ORDES_PER_PDF_FILE_EXCEEDED.getCode());
				woExc.add(pdfExceptionDTO);
				return woExc;
			}

			for (Long workOrderIds : woIds) {
				// Valida que en la tabla wo_pdf_annex exista un registro asociado al pais con la WO
	    		String woJasperName = null;
	    		// Genera el nombre de la WO a crear
	    		woJasperName = UtilsBusiness.generateWoName(countryId,woPdfAnnexDAOLocal);
	    		if(woJasperName.equalsIgnoreCase("continue")){
					EnvelopeEncapsulateResponse pdfExceptionDTO = new EnvelopeEncapsulateResponse();
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR048.getCode(),
							ErrorBusinessMessages.CORE_CR048.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
					pdfExceptionDTO.setWoCode(workOrderIds.toString());
					woExc.add(pdfExceptionDTO);
					break;
	    		}				
				
				EnvelopeEncapsulateResponse pdfExceptionDTO = new EnvelopeEncapsulateResponse();
				// Obtengo la WO para realizarle las validaciones pertinentes
				pdfWorkOrder = workOrderDAOBean.getWorkOrderByID(workOrderIds);

				if (pdfWorkOrder == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR034.getCode(),
							ErrorBusinessMessages.CORE_CR034.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
					pdfExceptionDTO.setWoCode(workOrderIds.toString());
					woExc.add(pdfExceptionDTO);
					continue;
				}
				// se valida que la WO tenga tanto un servicio.
				woService = woServiceDAO.getServicesOfWorkOrderServiceByWorkorderId(workOrderIds);
				if (woService == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR035.getCode(),
							ErrorBusinessMessages.CORE_CR035.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
					pdfExceptionDTO.setWoCode(pdfWorkOrder.getWoCode());
					woExc.add(pdfExceptionDTO);
					continue;
				}
				if (woService.size() == 0 || woService.isEmpty()) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR035.getCode(),
							ErrorBusinessMessages.CORE_CR035.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
					pdfExceptionDTO.setWoCode(pdfWorkOrder.getWoCode());
					woExc.add(pdfExceptionDTO);
					continue;
				}
				// se valida que la WO tenga tanto un assignment.
				woAssignment = workAssignmentDAOBean.getLastDealerAssignmentByWoId(pdfWorkOrder.getId());
				if (woAssignment == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR036.getCode(),
							ErrorBusinessMessages.CORE_CR036.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
					pdfExceptionDTO.setWoCode(pdfWorkOrder.getWoCode());
					woExc.add(pdfExceptionDTO);
					continue;
				}
				// se valida que el sale dealer exista.
				if (pdfWorkOrder.getSaleDealer() == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR037.getCode(),
							ErrorBusinessMessages.CORE_CR037.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
				}
				// se valida que el customer exista.
				if (pdfWorkOrder.getCustomer() == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR038.getCode(),
							ErrorBusinessMessages.CORE_CR038.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
				}
				// Se valida que el assignment este activo
				// se valida que el assignment tenga una agenda y q esta este en
				// estado S
				//06-07-2011 jnova se elimina la validacion del agendamiento de la WO
				// Se valida que el customer tenga un customer code type
				if (pdfWorkOrder.getCustomer().getCustType() == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR041.getCode(),
							ErrorBusinessMessages.CORE_CR041.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
				}
				// Se valida que el customer tenga un codigoPostal
				if (pdfWorkOrder.getCustomer().getPostalCode() == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR042.getCode(),
							ErrorBusinessMessages.CORE_CR042.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
				}
				// Se valida que el postal code tenga una ciudad
				if (pdfWorkOrder.getCustomer().getPostalCode().getCity() == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR043.getCode(),
							ErrorBusinessMessages.CORE_CR043.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
				}
				// Se valida que la ciudad tenga un estado
				if (pdfWorkOrder.getCustomer().getPostalCode().getCity().getState() == null) {
					EnvelopeEncapsulateDetailResponse exception = new EnvelopeEncapsulateDetailResponse(ErrorBusinessMessages.CORE_CR044.getCode(),
							ErrorBusinessMessages.CORE_CR044.getMessage());
					pdfExceptionDTO.getExceptions().add(exception);
				}
				
				//si la wo no genera problema en ningun nivel, continua con la siguiente WO
				//sino entonces le asigna el codigo de la WO a las excepciones generadas.
				if (pdfExceptionDTO.getExceptions() == null) {
					continue;
				}
				if (pdfExceptionDTO.getExceptions().size() == 0 || pdfExceptionDTO.getExceptions().isEmpty()) {
					continue;
				}

				pdfExceptionDTO.setWoCode(pdfWorkOrder.getWoCode());
				woExc.add(pdfExceptionDTO);
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación validateWoBeforeGeneratingPdf/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateWoBeforeGeneratingPdf/CoreWOBusiness ==");
		}

		return woExc;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#agendaWorkOrders(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void agendaWorkOrders(List<TrayWOManagmentDTO> trayWOManagmentDTOList) throws BusinessException {
		log.debug("== Inicia agendaWorkOrders/CoreWOBusiness ==");
		try{
			if( trayWOManagmentDTOList != null && !trayWOManagmentDTOList.isEmpty() ){
				for( TrayWOManagmentDTO dto : trayWOManagmentDTOList ){
					this.agendaWorkOrder(dto);
				}
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación agendaWorkOrders/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina agendaWorkOrders/CoreWOBusiness ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Inactiva la asignacion de la WO
	 * @param woId id de la work order
	 * @param inactiveDealerAssignment boolean, true si se debe inactivar la asignacion del dealer
	 * @throws BusinessException
	 * @author jnova
	 */
	private void deleteWOAssignmentAgendaCrewAndProgram(Long woId, boolean inactiveDealerAssignment,WoStatusHistory woStatusHistory) throws BusinessException {
		log.debug("== Inicia deleteWOAssignmentAgendaCrewAndProgram/CoreWOBusiness ==");
		try{
			WoAssignment woAssignment = woAssignmentDAO.getLastDealerAssignmentByWoId(woId);
			
			if( woAssignment != null ){
				if(woStatusHistory != null && woStatusHistory.getUser() != null){
					Date sysdate = UtilsBusiness.getCurrentTimeZoneDateByUserId(woStatusHistory.getUser().getId(), userDao);
					woAssignment.setWoStatusHistory(woStatusHistory);
					woAssignment.setDealerUnassignmentDate(sysdate);
				}
				
				WorkOrderAgenda woAgenda = workOrderAgendaDAO.getLastWorkOrderAgendaByWoID( woId );
					
				if(woAgenda != null){
					woAgenda.setStatus( CodesBusinessEntityEnum.WORKORDER_AGENDA_STATUS_INACTIVE.getCodeEntity() );
					workOrderAgendaDAO.updateWorkOrderAgenda(woAgenda);
				}
				if( woAssignment.getCrewId() != null && woAssignment.getCrewId().longValue() > 0 ){
					WoCrewAssigments crewAssignment = this.woCrewAssigmentDAO.getLastWoCrewAssigments( woId );
					if( crewAssignment != null ){
						crewAssignment.setIsActive( CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity() );
						woCrewAssigmentDAO.updateWoCrewAssigments( crewAssignment );
					}
				}	
				if( woAssignment.getProgram() != null ){
					WoProgramAssigments programAssignment = this.woProgramDAO.getLastWoProgramAssigments(woId);
					programAssignment.setIsActive( CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity() );
					woProgramDAO.updateWoProgramAssigments(programAssignment);
				}
				
				//se inactiva la asignacion
				if(inactiveDealerAssignment){
					woAssignment.setIsActive( CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity() );
				}else{//se eliminan las posibles asignaciones de cuadrilla o programa
					Program program = null;
					woAssignment.setCrewId(null);
					woAssignment.setCrewAssignmentDate(null);
					woAssignment.setProgram(program);
					woAssignment.setProgramAssignmentDate(null);
				}
				woAssignmentDAO.updateWoAssignment(woAssignment);
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación deleteWOAssignmentAgendaCrewAndProgram/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina deleteWOAssignmentAgendaCrewAndProgram/CoreWOBusiness ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<WoStatusHistoryVO> getWoStatusHistoryByWoCodeAndHistoryKey(String woCode, String historyKey) throws BusinessException {
		log.debug("== Inicia getWoStatusHistoryByWoCodeAndHistoryKey/CoreWOBusiness ==");
		try{
			List<WoStatusHistory> woStatusHistory = this.woStatusHistoryDAO.getWoStatusHistoryByWoCodeAndHistoryKey(woCode, historyKey);
			if( woStatusHistory != null )
				return UtilsBusiness.convertList(woStatusHistory, WoStatusHistoryVO.class);
			else return null;
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWoStatusHistoryByWoCodeAndHistoryKey/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWoStatusHistoryByWoCodeAndHistoryKey/CoreWOBusiness ==");
		}
	}
	
	/**
	 * Verifica si una work order en estado "reasignada" estuvo previamente en estado "rechazada"
	 * @param workOrderId. Id de la workorder
	 * @return True si se cumple la verificaci�n.
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @throws PropertiesException
	 */
	private Boolean wasWorkOrderRejectedBeforeReassigned(Long workOrderId) throws DAOSQLException, DAOServiceException, PropertiesException
	{
		WorkorderStatus rejectedStatus = this.workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity());
		WorkorderStatus reassignedStatus = this.workorderStatusDAO.getWorkorderStatusByCode(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
		WorkOrder workOrder = this.workOrderDAOBean.getWorkOrderByID(workOrderId);
		
		return workOrder.getWorkorderStatusByPreviusStatusId() != null && workOrder.getWorkorderStatusByPreviusStatusId().getWoStateCode().equals(rejectedStatus.getWoStateCode()) 
		       && workOrder.getWorkorderStatusByActualStatusId() != null && workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equals(reassignedStatus.getWoStateCode());
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#checkAboveScheduled(co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria, int)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduled(DealerWorkCapacityCriteria dealerWorkCapacityCriteria, int quantityWorkOrder){
		log.debug("== Inicia checkAboveScheduled/CoreWOBusiness ==");
		//Se crea el objeto a retornar
		DealerWorkLoadCapacityCriteriaDTO dealerWorkLoadCapacityCriteriaDTO = new DealerWorkLoadCapacityCriteriaDTO();
		try{

			SystemParameter scheduleLimitParam = getScheduleLimitParam(dealerWorkCapacityCriteria.getSuperCategoryServiceId(),dealerWorkCapacityCriteria.getCountryId());
			Double limit = new Double(scheduleLimitParam.getValue());
			
			//Se carga la capacidad del dealer
			DealerWorkCapacityLoaderImpl dealerWorkCapacityLoaderImpl=new DealerWorkCapacityLoaderImpl();
			DealerWorkCapacity d= dealerWorkCapacityLoaderImpl.loadCapacity(dealerWorkCapacityCriteria);
			dealerWorkLoadCapacityCriteriaDTO.setMaxCapacity(d.getMaxCapacity());
			int dealerUsedCapacity = this.getDealerUsedCapacity(dealerWorkCapacityCriteria);
			dealerWorkLoadCapacityCriteriaDTO.setUsedCapacity(dealerUsedCapacity);
			
			dealerWorkLoadCapacityCriteriaDTO.setAboveScheduled(checkMaxCapacityAndUsedCapacityWithLimit(dealerWorkLoadCapacityCriteriaDTO,limit));
			
			//Si no esta sobreagendado
			if(!dealerWorkLoadCapacityCriteriaDTO.isAboveScheduled()){
				dealerWorkLoadCapacityCriteriaDTO.setUsedCapacity(dealerUsedCapacity+quantityWorkOrder);
				dealerWorkLoadCapacityCriteriaDTO.setAboveScheduled(checkMaxCapacityAndUsedCapacityWithLimit(dealerWorkLoadCapacityCriteriaDTO,limit));
			}else
				//si esta sobreagendado no muestra el mensaje
				dealerWorkLoadCapacityCriteriaDTO.setAboveScheduled(false);
			
		} catch (Throwable ex) {
			//En caso de error no muestra el mensaje
			dealerWorkLoadCapacityCriteriaDTO.setAboveScheduled(false);
		} finally {
			log.debug("== Termina checkAboveScheduled/CoreWOBusiness ==");
		}
		return dealerWorkLoadCapacityCriteriaDTO;
	}
	
	/**
	 * @param serviceSuperCategoryByID
	 * @param countryId, id del pais
	 * @return true si el limite es mayor o igual a la razond entre la capacidad de usoy la maxima capacidad
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	private SystemParameterVO getScheduleLimitParam(Long serviceSuperCategoryByID,Long countryId) throws BusinessException, PropertiesException{
		
		SystemParameterVO scheduleLimitParam = null;
		String parameterCode = "";
		
		String codeService = serviceSuperCategoryBusinessBean.getServiceSuperCategoryByID(serviceSuperCategoryByID).getCode();
		if(codeService == null || codeService.equals(""))
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage() +" no se encontró la supercategoria del servicio." );
		else if(codeService.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_TECHNICAL_ASSISTANCE.getCodeEntity())){
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_ASISTENCIA_TECNICA.getCodeEntity();
		}else if(codeService.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_INSTALLATION.getCodeEntity())){
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_INSTALACION_MUDANZA_UPGRADE.getCodeEntity();
		}else if(codeService.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_RECOVERY.getCodeEntity())){
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_RECUPERACION.getCodeEntity();
		}else if(codeService.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_COBRANZAS.getCodeEntity())){
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_COBRANZA.getCodeEntity();
		}else if(codeService.equals(CodesBusinessEntityEnum.SUPER_CATEGORY_RESERVED_LOCAL_USE.getCodeEntity())){
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_LIMIT_RESERVA_LOCAL.getCodeEntity();
		}
		
		scheduleLimitParam = configParametrosBusinessBean.getSystemParameterByCodeAndCountryId(parameterCode, countryId);
		
		if(scheduleLimitParam == null)
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage() +" no se encontró el parámetro con código: " + parameterCode + " en el país con id: " + countryId);
		
		return scheduleLimitParam;
		
	}
	
	/**
	 * @param dealerWorkLoadCapacityCriteriaDTO
	 * @param limit
	 * @return
	 * @return true si el limite es mayor o igual a la razond entre la capacidad de usoy la maxima capacidad
	 */
	private boolean checkMaxCapacityAndUsedCapacityWithLimit(DealerWorkLoadCapacityCriteriaDTO dealerWorkLoadCapacityCriteriaDTO,Double limit){
		Double result = 0D;
		//si no se encontro 
		if(dealerWorkLoadCapacityCriteriaDTO.getMaxCapacity() == 0)
			result = 1D;
		else
			result = (new BigDecimal(dealerWorkLoadCapacityCriteriaDTO.getUsedCapacity()).divide(new BigDecimal(dealerWorkLoadCapacityCriteriaDTO.getMaxCapacity()), 2, RoundingMode.HALF_UP)).doubleValue();
		//si el parametro limite configurado por pais es menor returna true
		return !(result < limit);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateIBSStatusForWorkOrder(String woCode, String ibsStatusCode) throws BusinessException{
		log.debug("== Inicia updateIBSStatusForWorkOrder/CoreWOBusiness ==");
		try{
			//Verificar si existe la WorkOrder
			WorkOrder wo = this.workOrderDAOBean.getWorkOrderByCode(woCode);
			if(wo!=null){
				//Consultar el estado de ibs
				Ibs6Status ibsStatus = this.ibs6StatusDAO.getIbs6StatusByIbsStateCode(ibsStatusCode);
				wo.setIbsActualStatus(ibsStatus);
				this.workOrderDAOBean.updateWorkOrder(wo,CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_CORE.getCodeEntity());
			}
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación updateIBSStatusForWorkOrder/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina updateIBSStatusForWorkOrder/CoreWOBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getWoProcessSourceByWoCodeActualStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public WoProcessSource getWoProcessSourceByWoCodeActualStatus(String woCode, String actualStatusCode) throws BusinessException{
		String processSourceSource = "I";
		log.debug("== Inicia getWoProcessSourceByWoCodeActualStatus/CoreWOBusiness ==");
		try{
		
			boolean isWorkOrderCSR = workOrderCSRDAO.getCountWorkOrderCSRByWoCodeStatusReSchedule(woCode,actualStatusCode);
			if(isWorkOrderCSR){
				processSourceSource="C";
			}
			return workorderStatusDAO.getWoProcessSourceByWoStatus( actualStatusCode,processSourceSource);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operación getWoProcessSourceByWoCodeActualStatus/CoreWOBusiness");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWoProcessSourceByWoCodeActualStatus/CoreWOBusiness ==");
		}
	}
	
	/**
	 * @see ICoreWOBusiness#addWorkOrder()
	 * @author jjimenezh 2010-06-03
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder getCustomerWorkOrdersById(String countryCode,String woCode) throws BusinessException{
		
		log.debug("== Inicia getCustomerWorkOrdersById/CoreWOBusiness ==");
		try {
			return manageWorkForceServiceBroker.getCustomerWorkOrdersById(countryCode,woCode);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getCustomerWorkOrdersById");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerWorkOrdersById/CoreWOBusiness ==");
		}
	}
	
	/**
	 * Metodo: Permite determinar si la workOrder se va a cambiar de estado pendiente a estado pendiente
	 * @param woStatusNew
	 * @param woStatusOld
	 * @return
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	private String validWorkOrderStatusPending(WorkorderStatus woStatusNew,WorkorderStatus woStatusOld,String woDescription) throws PropertiesException{
		
		String description = null;
		if(((woStatusNew.getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()) || 
		   woStatusNew.getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity()))
		   &&(woStatusOld.getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity()) || 
			  woStatusOld.getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity())))){
			description = woDescription + ".";
		}
		
		return description;
		
	}
	
/**
	 * Metodo: Consulta los contacts de IBS invocando una operacion del ESB
	 * @param request
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private List<com.directvla.schema.businessdomain.customer.customerinterfacemanagement.v1_0.CustomerInquiry> getCustomerInquiriesByCriteria(CustomerInquiriesByCriteriaIBSDTO request) throws BusinessException{
		
		log.debug("== Inicia getCustomerInquiriesByCriteria/CoreWOBusiness ==");
		try {
			return customerInterfaceManagmentBroker.getCustomerInquiriesByCriteria(request);
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/getCustomerInquiriesByCriteria");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerInquiriesByCriteria/CoreWOBusiness ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#downloadWorkOrderContact(java.lang.Long, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public CustomerInquiriesByCriteriaIBSDTO populateCustomerInquiriesByCriteriaIBSDTO(Long countryId, Date processDate) throws BusinessException{
		
		log.debug("== Inicia populateCustomerInquiriesByCriteriaIBSDTO/CoreWOBusiness ==");
		try {
			
			//Se consulta el pais
			Country country = countryDao.getCountriesByID(countryId);
			
			Long userId = UtilsBusiness.getUserIdAdmin(userDAO, systemParameterDAO, country.getId());
			
			//constante utilizada para obtener contacts de workorders
			String workOrderIdGreaterThan = CodesBusinessEntityEnum.DOWNLOAD_CUSTOMER_CONTACT_GREATER_THAN_WORK_ORDER_ID.getCodeEntity();
			
			
			//contacts a descargar
			int pageSize = Integer.parseInt(CodesBusinessEntityEnum.DOWNLOAD_CUSTOMER_CONTACT_PAGE_SIZE.getCodeEntity());
			
			//Latencia de tiempo en segundos para descargar un contacts, por defecto es cero
			String parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_DELAY_DOWNLOAD_CUSTOMER_CONTACT.getCodeEntity();
			SystemParameter pageSizeParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,country.getId());
			Long delayDownloadCustomerContact = 0l;
			if(pageSizeParam!=null){
				delayDownloadCustomerContact = Long.parseLong(pageSizeParam.getValue());
			}

			//Se calcula la fecha final de consulta de contacts
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(processDate);
			endCalendar.add(Calendar.SECOND,delayDownloadCustomerContact.intValue()*-1);
			
			//Fecha inicial de consulta de contacts
			parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_BEGIN_DATE_DOWNLOAD_CUSTOMER_CONTACT.getCodeEntity();
			SystemParameter beginDateParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,country.getId());
			Date beginDateDownloadCustomerContact = null;
			if(beginDateParam!=null){
				String strBeginDate = beginDateParam.getValue();
				beginDateDownloadCustomerContact = UtilsBusiness.stringToDate(strBeginDate,UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS);
			}else{
				throw new BusinessException(ErrorBusinessMessages.CORE_CR073.getCode(),ErrorBusinessMessages.CORE_CR073.getMessage());
			}
			
			//Se crea CustomerInquiriesByCriteriaIBSDTO utilizado para consultar los contacts
			return new CustomerInquiriesByCriteriaIBSDTO(country, 
																  				  		  	  beginDateDownloadCustomerContact,
																	  				  		  endCalendar,
																		                      workOrderIdGreaterThan,
																		                      1,
																		                      pageSize,
																		                      userId);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/populateCustomerInquiriesByCriteriaIBSDTO");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina populateCustomerInquiriesByCriteriaIBSDTO/CoreWOBusiness ==");
		}
	}
	//REQ001 - WO Canceladas.
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#getCanceledWorkOrdersByDealerId(java.lang.Long)
	 */
	@Override
	public List<WorkOrderVO> getCanceledWorkOrdersByDealerId(Long dealerId) throws BusinessException {
		log.debug("== Inicia getCanceledWorkrdersByDealerId/CoreWOBusiness ==");
		try{
			UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			Date fromDate = UtilsBusiness.getFirstMomentOfDay(new Date());
			Date toDate = UtilsBusiness.getLastMomentOfDay(UtilsBusiness.addDate(new Date(), 3));
			 
			List<WorkOrder> woList = workOrderDAOBean.getCanceledWorkOrdersByDealerIdAndDates(dealerId, fromDate, toDate);
			List<WorkOrderVO> woVOList = UtilsBusiness.convertList(woList, WorkOrderVO.class);
			return woVOList;
		}catch (Throwable ex){
			throw super.manageException(ex);	
		}finally{
			log.debug("== Termina getCanceledWorkrdersByDealerId/CoreWOBusiness ==");			
		}
	}
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal#downloadWorkOrderContact(java.lang.Long, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void downloadWorkOrderContact(CustomerInquiriesByCriteriaIBSDTO request) throws BusinessException{
		
		log.debug("== Inicia downloadCustomerContact/CoreWOBusiness ==");
		try {
			
			//Variable utilizada para almacenar los contacts de IBS
			List<com.directvla.schema.businessdomain.customer.customerinterfacemanagement.v1_0.CustomerInquiry> response = null;
			
			do {
				
				//Se invoca el servicio para consultar los contacts en ibs
				response = this.getCustomerInquiriesByCriteria(request);
				
				//Si retorna algun contact se persiste en HSP+
				if(response!=null){

					for (CustomerInquiry customerInquiry : response) {
						if ( (customerInquiry.getType().getId().equals(CodesBusinessEntityEnum.CODE_IBS_CONTACT_REASON_TECHNICAL_VISIT.getCodeEntity())) ||
								customerInquiry.getType().getId().equals(CodesBusinessEntityEnum.CODE_IBS_CONTACT_REASON_TECHNICAL_DEFAULT.getCodeEntity()) ){
							
							persistIbsContact(customerInquiry,
							          request.getCountry(),request.getUserId());
							
						}
												
					}
					
				}
				
				//Se incrementa el numero de pagina a consultar
				request.setPageIndex(request.getPageIndex()+1);
				
			//Se realiza la consulta hasta que no retorne mas contacts
			} while (response!=null);
				
			this.updateSystemParameterBeginDateDownloadCustomerContact(request);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion CoreWOBusiness/downloadCustomerContact");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina downloadCustomerContact/CoreWOBusiness ==");
		}
	}

	/**
	 * Metodo: Permite insertar un contact IBS si no exite en HSP, de lo contrario lo actualiza
	 * @param customerInquiry
	 * @param country
	 * @param userId
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws HelperException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void persistIbsContact(CustomerInquiry customerInquiry,
			                       Country country,
                                   Long userId) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException, HelperException{
		
		IbsContactVO ibsContactVO = customerInterfaceManagmentBroker.populateIbsContactVO(customerInquiry,country);
		if(ibsContactVO!=null){
			//se agrega validacion para que no vuelva a marcar la wo para los estados 'T - Realizada' , 'C - Cancelada' o 'F - Finalizada'
			if ( !ibsContactVO.getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity()) && 
					!ibsContactVO.getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity()) &&
					   !ibsContactVO.getWorkOrder().getWorkorderStatusByActualStatusId().getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getCodeEntity())){
				
				ibsContactBusinessBean.mergeIbsContact(ibsContactVO);
				if(ibsContactVO.getIbsContactReason().getIsReclaim().equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
					this.markOrUnMarkWorkOrder(ibsContactVO.getWorkOrder().getId(),
	                        userId,
	                        true,
	                        CodesBusinessEntityEnum.CODE_WORK_ORDER_MARK_RECLAIM.getIdEntity(WorkOrderMark.class.getName()));
				}
				
			}
						
		}
			
	}
	
	/**
	 * Metodo: Permite actualizar el parametro del sistema begin_date_download_customer_contact
	 * @param request
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void updateSystemParameterBeginDateDownloadCustomerContact(CustomerInquiriesByCriteriaIBSDTO request) throws DAOServiceException, DAOSQLException, PropertiesException{
		
		request.getEndDate().add(Calendar.SECOND,1);
		//Se actualiza el parametro del sistema para la siguiente consulta de contacts
		String parameterCode = CodesBusinessEntityEnum.SYSTEM_PARAM_BEGIN_DATE_DOWNLOAD_CUSTOMER_CONTACT.getCodeEntity();
		SystemParameter beginDateParam = systemParameterDAO.getSysParamByCodeAndCountryId(parameterCode,request.getCountry().getId());
		beginDateParam.setValue(UtilsBusiness.dateToString(request.getEndDate().getTime(),UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS));
		systemParameterDAO.updateSystemParameter(beginDateParam);
			
	}
	
}