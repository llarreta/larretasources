package co.com.directv.sdii.ejb.business.core.tray.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.enumerations.TrayQueryEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.WorkOrderMarkBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.IbsContactBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayManagmentBusinessHelperLocal;
import co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal;
import co.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.CrewsCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersMediaContactCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DocumentTypesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.dto.CustomerDTO;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.EmployeeCrewDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;
import co.com.directv.sdii.model.dto.ServiceAttendResponseDTO;
import co.com.directv.sdii.model.dto.ServiceAttentionRequestDTO;
import co.com.directv.sdii.model.dto.TrayServiceDTO;
import co.com.directv.sdii.model.dto.TrayWOManagmentDTO;
import co.com.directv.sdii.model.dto.TrayWorkOrderServiceDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderMarkDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;
import co.com.directv.sdii.model.pojo.City;
import co.com.directv.sdii.model.pojo.CustomerAddresses;
import co.com.directv.sdii.model.pojo.CustomerCategory;
import co.com.directv.sdii.model.pojo.CustomerClass;
import co.com.directv.sdii.model.pojo.CustomerType;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.EmployeeMediaContact;
import co.com.directv.sdii.model.pojo.EmployeeVehicle;
import co.com.directv.sdii.model.pojo.ExpirationGrouping;
import co.com.directv.sdii.model.pojo.OptimusStatus;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceStatus;
import co.com.directv.sdii.model.pojo.ShippingOrder;
import co.com.directv.sdii.model.pojo.ShippingOrderDetail;
import co.com.directv.sdii.model.pojo.State;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.TypeServiceWorkOrders;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WoCrewAssigments;
import co.com.directv.sdii.model.pojo.WoProcessSource;
import co.com.directv.sdii.model.pojo.WoStatusHistory;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WorkOrderResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderServiceResponse;
import co.com.directv.sdii.model.pojo.collection.WorkOrderTrayResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerMediaContactVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.DocumentTypeVO;
import co.com.directv.sdii.model.vo.ExpirationGroupingVO;
import co.com.directv.sdii.model.vo.OptimusStatusVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ProgramVO;
import co.com.directv.sdii.model.vo.RequiredServiceElementVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.ShippingOrderElementVO;
import co.com.directv.sdii.model.vo.ShippingOrderVO;
import co.com.directv.sdii.model.vo.TechnologyVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WoTypeVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.model.vo.WorkorderReasonVO;
import co.com.directv.sdii.model.vo.WorkorderStatusVO;
import co.com.directv.sdii.persistence.dao.config.CustomerMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ExpirationGroupingDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ProgramDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceCategoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.TypeServiceWorkOrdersDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoStatusHistoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WoTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.core.ShippingOrderDetailDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoCrewAssigmentsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeMediaContactDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeVehicleDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * Caso de Uso ADS - 18 - Visualizar Bandeja de Work Orders de la Compañía Instaladora
 * Caso de Uso ADS - 35 - Bandeja de Work Orders para Asignación Manual o Cancelación
 * Caso de Uso ADS - 38 - Atención de la Work Order por Torre de Control
 * Caso de Uso ADS - 65 - Atencion y Finalizacion Work Orders
 * Caso de Uso ADS - 66 -  Finalizacion Work Orders
 * Implementa las operaciones de negocio para la gestion de WorkOrders
 * 
 * Fecha de Creación: 6/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="TrayWorkOrderManagmentBusinessLocal",mappedName="ejb/TrayWorkOrderManagmentBusinessLocal")
@Local({TrayWorkOrderManagmentBusinessLocal.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TrayWorkOrderManagmentBusinessBean extends BusinessBase implements TrayWorkOrderManagmentBusinessLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(TrayWorkOrderManagmentBusinessBean.class);
	
	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAO;
	
	@EJB(name="WorkOrderServiceDAOLocal",beanInterface=WorkOrderServiceDAOLocal.class)
	private WorkOrderServiceDAOLocal woServiceDAO;
	
	@EJB(name="WoStatusHistoryDAOLocal",beanInterface=WoStatusHistoryDAOLocal.class)
	private WoStatusHistoryDAOLocal woStatusHistoryDAO;
	
	@EJB(name="WorkOrderAgendaDAOLocal",beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAO;
	
	@EJB(name="WoCrewAssigmentsDAOLocal",beanInterface=WoCrewAssigmentsDAOLocal.class)
	private WoCrewAssigmentsDAOLocal woCrewAssigmentsDAO;
	
	@EJB(name="EmployeesCrewDAOLocal",beanInterface=EmployeesCrewDAOLocal.class)
	private EmployeesCrewDAOLocal employeesCrewDAO;
		
	@EJB(name="ServiceDAOLocal",beanInterface=ServiceDAOLocal.class)
	private ServiceDAOLocal serviceDAO;
	
	@EJB(name="ShippingOrderDAOLocal",beanInterface=ShippingOrderDAOLocal.class)
	private ShippingOrderDAOLocal shippingOrderDAO;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
    private WarehouseElementDAOLocal warehouseElementDAO;
	
	@EJB(name="WorkorderStatusDAOLocal", beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workorderStatusDAO;
	
	@EJB(name="CustomerMediaContactDAOLocal", beanInterface=CustomerMediaContactDAOLocal.class)
	private CustomerMediaContactDAOLocal customerMediaContactDAO;
	
	@EJB(name="ContactBusinessBeanLocal", beanInterface=ContactBusinessBeanLocal.class)
	private ContactBusinessBeanLocal contactBusiness;
	
	@EJB(name="EmployeeMediaContactDAOLocal", beanInterface=EmployeeMediaContactDAOLocal.class)
	private EmployeeMediaContactDAOLocal employeeMediaContactDAO;
	
	@EJB(name="EmployeeVehicleDAOLocal", beanInterface=EmployeeVehicleDAOLocal.class)
	private EmployeeVehicleDAOLocal employeeVehicleDAO;
	
	@EJB(name="DocumentTypesCRUDBeanLocal", beanInterface=DocumentTypesCRUDBeanLocal.class)
	private DocumentTypesCRUDBeanLocal documentTypesCRUD;
	
	@EJB(name="TypeServiceWorkOrdersDAOLocal", beanInterface=TypeServiceWorkOrdersDAOLocal.class)
	private TypeServiceWorkOrdersDAOLocal typeServiceWorkOrdersDAO;
	
	@EJB(name="TrayManagmentBusinessHelperLocal", beanInterface=TrayManagmentBusinessHelperLocal.class)
	private TrayManagmentBusinessHelperLocal trayHelper;
	
	@EJB(name="ExpirationGroupingDAOLocal", beanInterface=ExpirationGroupingDAOLocal.class)
	private ExpirationGroupingDAOLocal expirationGroupingDAO;
	
	@EJB(name="ServiceCategoryDAOLocal", beanInterface=ServiceCategoryDAOLocal.class)
	private ServiceCategoryDAOLocal serviceCategoryDAO;
	
	@EJB(name="WoTypeDAOLocal", beanInterface=WoTypeDAOLocal.class)
	private WoTypeDAOLocal woTypeDAO;
	
	@EJB(name="ProgramDAOLocal", beanInterface=ProgramDAOLocal.class)
	private ProgramDAOLocal programDAO;
	
	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal woAssignmentDAO;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealersDAO;
		
	@EJB(name="ServiceStatusDAOLocal",beanInterface=ServiceStatusDAOLocal.class)
    private ServiceStatusDAOLocal serviceStatusDAO;
	
	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal workAssignmentDAOBean;
	
	@EJB(name="CrewsCRUDBeanLocal", beanInterface=CrewsCRUDBeanLocal.class)
	private CrewsCRUDBeanLocal crewBusiness;
	
	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal workOrderDAOBean;
	
	@EJB(name="ShippingOrderDetailDAOLocal",beanInterface=ShippingOrderDetailDAOLocal.class)
	private ShippingOrderDetailDAOLocal shippingOrderDetailDAO;
	
	@EJB(name="ElementModelDAOLocal",beanInterface=ElementModelDAOLocal.class)
	private ElementModelDAOLocal elementModelDAO;
	
	@EJB(name="DealersMediaContactCRUDBeanLocal", beanInterface=DealersMediaContactCRUDBeanLocal.class)
	private DealersMediaContactCRUDBeanLocal dealersMediaContactCRUDBean;
	
	@EJB(name="TechnologyBusinessBeanLocal", beanInterface=TechnologyBusinessBeanLocal.class)
	private TechnologyBusinessBeanLocal technologyBusinessBean;
    
	@EJB(name="CustomerBusinessBeanLocal", beanInterface=CustomerBusinessBeanLocal.class)
	private CustomerBusinessBeanLocal customerBuss;
	
	@EJB(name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal warehouseBusinessBean;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal sysParamDao;
	
	@EJB(name="WorkOrderMarkBusinessBeanLocal", beanInterface=WorkOrderMarkBusinessBeanLocal.class)
	private WorkOrderMarkBusinessBeanLocal workOrderMarkBusinessBean;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal daoUser;
	
	@EJB(name="IbsContactBusinessBeanLocal", beanInterface=IbsContactBusinessBeanLocal.class)
	private IbsContactBusinessBeanLocal ibsContactBusinessBean;
	
	@EJB	
	private WorkOrderBusinessBeanLocal workOrderBusiness; 
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderTrayResponse getWorkOrdersAttentionFinalization(WorkOrderFilterTrayDTO filterDTO, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
    	log.debug("== Inicia getWorkOrdersAttentionFinalization/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( filterDTO == null || filterDTO.getCountryId() == null || filterDTO.getCountryId() <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			//Validacion de rangos de fechas
			validateTrayDates(filterDTO.getWoCreationDateInit(), filterDTO.getWoCreationDateEnd(), 2);
			
			if( filterDTO.getWoStatusIds() == null || filterDTO.getWoStatusIds().isEmpty() ){
				/*ASIGNA LOS ESTADOS PERMITIDOS EN LA BANDEJA DE ATENCION Y FINALIZACION*/
				WorkorderStatus scheduleStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity() );
				WorkorderStatus reScheduleStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity() );
				WorkorderStatus realizedStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity() );
				List<Long> woStatusIds = new ArrayList<Long>();
				if( scheduleStatus != null )
					woStatusIds.add( scheduleStatus.getId() );
				if( reScheduleStatus != null )
					woStatusIds.add( reScheduleStatus.getId() );
				if( realizedStatus != null )
					woStatusIds.add( realizedStatus.getId() );
				filterDTO.setWoStatusIds(woStatusIds);
			}
			this.setDealerForUser(filterDTO);
			
			//Se valida que se envie una sucursal o una principal para darle valor a los ids de dealers por los cuales se filtra
			boolean isDealerId = ( filterDTO.getDealersIds() == null || filterDTO.getDealersIds().isEmpty() ) ? false : true;
	    	boolean isBranchId = ( filterDTO.getBranchIds() == null || filterDTO.getBranchIds().isEmpty() )  ? false : true;
	    	if( isBranchId ){
	    		filterDTO.setDealersIds(null);
	    	} else if( isDealerId ){
	    		List<Long> tempList = new ArrayList<Long>();
	    		for( Long dealerId : filterDTO.getDealersIds() ){
	    			List<Dealer> dealers = dealersDAO.getDealerByBranchId( dealerId );
	        		if( dealers != null && !dealers.isEmpty() ){
	        			for(Dealer d : dealers){
	        				tempList.add( d.getId() );
	        			}
	        		}
	    		}
	    		filterDTO.getDealersIds().addAll(tempList);
	    		filterDTO.setBranchIds(null);
	    	}  else {
	    		filterDTO.setDealersIds(null);
        		filterDTO.setBranchIds(null);
        	}
	    	
			WorkOrderResponse daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filterDTO,requestCollectionInfo,false); 
			WorkOrderTrayResponse response = new WorkOrderTrayResponse();
			List<WorkOrderTrayDTO> workOrderList = new ArrayList<WorkOrderTrayDTO>();
			if( daoResponse.getWorkOrdersIds() != null && !daoResponse.getWorkOrdersIds().isEmpty() ){
				
				/*for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();				
					
					//ADICIONA LA WO A LA LISTA DE RETORNO
					workOrderList.add( fillWorkOrderInformation(woId) );
				}		
				response.setWorkOrderList(workOrderList);*/
				List<Long> woIds = new ArrayList<Long>();
				for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();
					woIds.add(woId);
				}
				response.setWorkOrderList( this.fillWorkOrderInformationOptimized(woIds,filterDTO.getUserId()) );
			}
		if( requestCollectionInfo != null )
				this.populatePaginationInfo(response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), daoResponse.getRowCount(), daoResponse.getTotalRowCount());
			
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersAttentionFinalization/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersAttentionFinalization/TrayWorkOrderManagmentBusinessBean ==");
		}
    }
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderServiceResponse getWorkOrderServices(Long woId, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		log.debug("== Inicia getWorkOrderServices/TrayWorkOrderManagmentBusinessBean ==");
		try{
			WorkOrderServiceResponse response = woServiceDAO.getWOServiceByWorkorderId(woId, requestCollectionInfo);
			response.setWorkOrderServicesVO(UtilsBusiness.convertList(response.getWorkOrderServices(), WorkOrderServiceVO.class));
			response.setWorkOrderServices(null);
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrderServices/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderServices/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderServiceResponse getWorkOrderServicesAttention(Long woId, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		log.debug("== Inicia getWorkOrderServicesAttention/TrayWorkOrderManagmentBusinessBean ==");
		try{
			WorkOrderServiceResponse response = this.getWorkOrderServices(woId, requestCollectionInfo);
			response.setWorkOrderServicesVO(UtilsBusiness.convertList(response.getWorkOrderServices(), WorkOrderServiceVO.class));
			response.setWorkOrderServices(null);
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrderServicesAttention/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderServicesAttention/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderServiceResponse getWorkOrderServicesFinalization(Long woId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		log.debug("== Inicia getWorkOrderServicesFinalization/TrayWorkOrderManagmentBusinessBean ==");
		try {
			WorkOrderServiceResponse response = this.getWorkOrderServices(woId, requestCollectionInfo);
			response.setWorkOrderServicesVO(UtilsBusiness.convertList(response.getWorkOrderServices(), WorkOrderServiceVO.class));
			response.setWorkOrderServices(null);
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrderServicesFinalization/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderServicesFinalization/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attentionWorkOrder(TrayWOManagmentDTO request) throws BusinessException {
		try {
			log.debug("== Inicia attentionWorkOrder/TrayWorkOrderManagmentBusinessBean ==");

			//objetos globales al proceso
			WOAttentionsRequestDTO woAttentionDTO = null;			
			ServiceAttentionRequestDTO attendService = null;
			
			//Se carga el objeto para el proceso de atencion con la informacion que llega por referencia
			woAttentionDTO = this.populateObjectAttentionFinalization(request);			 			
						
			//validacion de los datos de entrada para realizar la atencion
			trayHelper.validateDataWOAttention(request);
			
			//Invoca el componente de negocio para realizar las validaciones para la atencion
			trayHelper.validateDataBusinessWOAttentions(woAttentionDTO);
			
			//se invoca la validacion de la existencia de elementos en la bodega de la cuadrilla
			//trayHelper.validateExistenceElements(woAttentionDTO.getDealerId(), woAttentionDTO.getWorkorderServices(), woAttentionDTO.getWorkorderVo().getId() );				
			
			//Se obtiene la ultima asignacion de la WO.
			WoAssignment lastAssignment = workAssignmentDAOBean.getLastDealerAssignmentByWoId( woAttentionDTO.getWorkorderVo().getId() );
			
			//Se obtiene la informacion de la WO
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID( woAttentionDTO.getWorkorderVo().getId() );
			woAttentionDTO.setWorkorderVo( UtilsBusiness.copyObject(WorkOrderVO.class, workOrder) );
						
			//Proceso de Atención de la WorkOrder
			attendService = new ServiceAttentionRequestDTO();
			ServiceAttendResponseDTO serviceAttend = null;
			attendService.setWorkorderVo( UtilsBusiness.copyObject(WorkOrderVO.class, workOrder) );
			attendService.setUserId( woAttentionDTO.getUserId() );
			List<WOAttentionElementsRequestDTO> attentionElementsList = new ArrayList<WOAttentionElementsRequestDTO>();
			
			//Se crea la bodega del cliente
			this.warehouseBusinessBean.createCustomerWarehouse( request.getCustomerCode() , request.getCountryCode() );		
			
			// Se crea un contador para verificar que solo se invoque el servicio de reportar los elementos
			// empleados en la atencion hasta que se hayan agregado los elementos de todos los servicios
			Integer serviceCount = 0;
			for (TrayWorkOrderServiceDTO woServiceDTO : request.getTrayWOServiceDTO()) {				

				// Se aumenta el contador de servicios
				serviceCount++;

				//consulta el servicio asociado en la WO para realizar el flujo de registro de elmentos
				Service service = serviceDAO.getServiceByID( woServiceDTO.getTrayServiceDTO().getServiceId() );

				//proceso de atencion de un servicio, cambio de estado y elementos empleados.
				attendService.setWorkorderService( this.populateObjectWorkOrderService( woServiceDTO ) );
				serviceAttend = new ServiceAttendResponseDTO();
				serviceAttend.setLastAssignment( lastAssignment );
				serviceAttend = trayHelper.atenderService(attendService);

				//Si la WO esta en proceso de Atencion y Finalizacion, se carga la lista de servicios
				//independiente al proceso para asignar valores de la atencion que se requieren para
				//la finalizacion.
				if ( request.getWorkOrderFinished() ) {
					if ( woServiceDTO.getAddService() ) {	
						woServiceDTO.setWorkOrderServiceId(serviceAttend.getWorkOrderServiceId());
					}
				}

				//Activar Elementos en IBS, solo para servicios de Instalacion
				/* Determina si es upgrade -downgrade o instalacion basando en categorias */
				Boolean isUpgradeDowngrade = this.validateCalledActivation( service );

				//reportar elementos empleados en la atencion de cada uno de los servicios;
				WOAttentionElementsRequestDTO attentionElements = this.populateAttentionElements(woServiceDTO, woAttentionDTO, request, serviceAttend);
				//Crea los WoManagementElements utilizados y recuperados
				this.reportAttentionElements( attentionElements, service );		

				//Se invocan los servicios del ESB y la interfaz de Inventarios si se registraron elementos.
				if ( ( attentionElements.getInstallationSerializedElements() != null && !attentionElements.getInstallationSerializedElements().isEmpty() )
						|| ( attentionElements.getRecoverySerializedElements() != null && !attentionElements.getRecoverySerializedElements().isEmpty() ) ) {

					attentionElementsList.add(attentionElements);
					if(serviceCount == request.getTrayWOServiceDTO().size()){
						InventoryDTO inventoryDTO =  this.populateObjectInventoryAttention(request,attentionElementsList,service, woServiceDTO);
						inventoryDTO.setUpgradeDowngrade(isUpgradeDowngrade);
						//Reporta los elementos empleados en la atencion para afectar el inventario.
						trayHelper.updateRecordSerializedInventory(inventoryDTO);
					}
				}

				// Invocar el servicio de IBS para cambio de equipos, solo para servicios de Instalacion SWAP
				if ((service.getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.SERVICE_TYPE_INSTALL.getCodeEntity()) 
						&& service.getServiceCategory().getServiceCategoryCode().equalsIgnoreCase(CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_SWAP.getCodeEntity())) 
						|| service.getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.SERVICE_TYPE_SERVICE.getCodeEntity())) {

					if (service.getAllowChangeElement().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())) {
						trayHelper.informarCambioDeIRDIBS(attentionElements);
					}
				}

				if ( isUpgradeDowngrade != null ) {
					if ( !isUpgradeDowngrade ) {
						trayHelper.activarElementosEnIBS( attentionElements );
					} else {
						trayHelper.upgradeDowngradeResource( attentionElements );
					}
				}		
			}


			//invocar el cambio de estado de la WorkOrder a Atendida
			woAttentionDTO.setComments( request.getAttentionComments() );
			trayHelper.actualizarCambioDeEstadoWO( woAttentionDTO );

			//notificar el cambio de estado solo atendida
			if ( !request.getWorkOrderFinished() ) {
				trayHelper.notificarIbsCambioDeEstadoWO( woAttentionDTO );
			}		

			//Invoca el componente para realizar la finalizacion			
			if ( request.getWorkOrderFinished() ) {
				List<TrayWOManagmentDTO> trayRequest = new LinkedList<TrayWOManagmentDTO>();
				trayRequest.add( request );
				this.finalizationWorkOrder( trayRequest );

				//invocar el cambio de estado de la WorkOrder en HSP+
				//esta operacion retorna la reason de finalizacion y el tiempo de trabajo de la WO
				woAttentionDTO.setComments( request.getFinalizationComments() );
				woAttentionDTO = trayHelper.stateChangeUpdateWOFinalization( woAttentionDTO );

				//notificar el cambio de estado de la WorkOrder a Atendida y Finalizada
				//en el caso de que sea el proceso Atencion y Finalizacion
				//ya que si es para el flujo de ambos de debe cambiar el estado
				//al final de todo el proceso, puesto que no existe compesacion en IBS.	
				trayHelper.notificarIbsCambioDeEstadoWO( woAttentionDTO );
				//invocar el cambio de estado de la WO en IBS
				trayHelper.completeWorkOrderFinalization( woAttentionDTO );
			}		
			
			//CC 059 Se desmarca la workOrder
			workOrderMarkBusinessBean.unMarkWorkOrderAttention(request.getWorkOrderId(),request.getUserId());
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación attentionWorkOrder/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina attentionWorkOrder/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EnvelopeEncapsulateResponse finalizationWorkOrder(List<TrayWOManagmentDTO> trayRequest) throws BusinessException {
		try {
			log.debug("== Inicia finalizationWorkOrder/TrayWorkOrderManagmentBusinessBean ==");
			//Objetos Globales
			EnvelopeEncapsulateResponse response = new EnvelopeEncapsulateResponse();
			WOAttentionsRequestDTO woAttentionDTO = null;
			TrayWOManagmentDTO principalWorkOrder = null;
			
			//Valida que el request no venga null
			Object[] params = null;
			params = new Object[2];	
			params[0] = "List<TrayWOManagmentDTO>";
			params[1] = "finalizationWorkOrder";			
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest, ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			
			//Se cargan los objetos para la validacion de las WO para finalizacion Masiva
			if ( trayRequest.size() > 1 ) {
				//Se carga la WO principal con la cual se comparan los datos de las WO asociadas
				for (TrayWOManagmentDTO trayWOManagmentDTO : trayRequest) {	
					if ( trayWOManagmentDTO.getPricipalWorkOrder()) {
						principalWorkOrder = trayWOManagmentDTO;
					}
				}
				//Se validan las WO asociadas, para determinar que cumplan con las reglas de negocio
				for (TrayWOManagmentDTO trayWOManagmentDTO : trayRequest) {	
					if ( !trayWOManagmentDTO.getPricipalWorkOrder() ) {
						woAttentionDTO = new WOAttentionsRequestDTO();
						woAttentionDTO.setPrincipalWorkOrder( principalWorkOrder );
						woAttentionDTO.setAssociatedWorkOrder(trayWOManagmentDTO);
						trayHelper.massiveValidationWorkOrders(woAttentionDTO);
					}
				}
			}
			
			for (TrayWOManagmentDTO trayWOManagmentDTO : trayRequest) {					
				//Objetos transversales al proceso de finalizacion
				ServiceAttendResponseDTO serviceAttend = null;
				ServiceAttentionRequestDTO attendService = new ServiceAttentionRequestDTO();	
				
				//validacion de los datos de entrada para realizar la finalizacion
				trayHelper.validateDataWOFinalization(trayWOManagmentDTO);
				
				//Se carga el objeto para el proceso de atencion con la informacion que llega por referencia
				woAttentionDTO = null;
				woAttentionDTO = this.populateObjectAttentionFinalization(trayWOManagmentDTO);
				
				//Invoca el componente de negocio para realizar las validaciones de la Finalización		
				trayHelper.validateDataBusinessWOFinalization(woAttentionDTO);
				
				//Se obtiene la ultima asignacion de la WO.
				WoAssignment lastAssignment = workAssignmentDAOBean.getLastDealerAssignmentByWoId( woAttentionDTO.getWorkorderVo().getId() );
				
				//Se obtiene la informacion de la WO
				WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID( woAttentionDTO.getWorkorderVo().getId() );
				woAttentionDTO.setWorkorderVo( UtilsBusiness.copyObject(WorkOrderVO.class, workOrder) );
				
				//Proceso de Finalizacion de la WorkOrder
				attendService.setUserId( woAttentionDTO.getUserId() );
				
				//consulta el servicio asociado en la WO para realizar el flujo de registro de elmentos e informar a IBS
				for(int i = 0; i<woAttentionDTO.getWorkorderServices().size();i++){
					Service service = serviceDAO.getServiceByID(woAttentionDTO.getWorkorderServices().get(i).getService().getId() );
					woAttentionDTO.getWorkorderServices().get(i).setService(service);
				}
				
				// Se crea un contador para verificar que solo se invoque el servicio de reportar los elementos
				// empleados en la atencion hasta que se hayan agregado los elementos de todos los servicios
				Integer serviceCount = 0;
				InventoryDTO inventoryDTO = new InventoryDTO();
				for (WorkOrderServiceVO woServiceVO : woAttentionDTO.getWorkorderServices()) {					
					serviceAttend = new ServiceAttendResponseDTO();					
					serviceAttend.setLastAssignment( lastAssignment );
					attendService.setWorkorderService( woServiceVO );
					attendService.setWorkorderVo( woAttentionDTO.getWorkorderVo() );
					attendService.setWorkOrderAttendFinished(trayWOManagmentDTO.getWorkOrderFinished());
					serviceAttend = trayHelper.serviceFinalization(attendService);
					
					//Si a la WO se le adiciono un servicio				
					if ( woServiceVO.getAddService() ) {
						//se verifica que el proceso de adicion del servicio provenga de 
						//la Atencion y Finalizacion
						if ( !attendService.getWorkOrderAttendFinished() ) {
							woServiceVO.setId(serviceAttend.getWorkOrderServiceId());	
						}
					}					
					
					
					//se cra el obeto que encapsula los datos para la finalizacion
					WOAttentionElementsRequestDTO attentionElements = new WOAttentionElementsRequestDTO();
					attentionElements.setWorkOrderServiceId(woServiceVO.getId());
					attentionElements.setUserId( woAttentionDTO.getUserId() );
					attentionElements.setWorkorderVo( woAttentionDTO.getWorkorderVo() );
					
					//reportar elementos empleados en la finalizacion de cada uno de los servicios;
					serviceCount++;
					
					if(log.isDebugEnabled())
						log.debug("Esta finalizando el servicio de codigo "+woServiceVO.getService().getServiceCode() +" que esta marcado con allow recording "+woServiceVO.getService().getAllowRecordingElement());
					
					
					//if ( service.getAllowRetrieveElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() )
						//	|| service.getAllowRecordingElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() )) {
						//Se valida que se hayan registrado elementos en la finalizacion, puesto que no son requeridos
						if ( woServiceVO.getWoServiceElements() != null && !woServiceVO.getWoServiceElements().isEmpty() ) {
							for (WorkOrderServiceElementVO woServiceElementVO : woServiceVO.getWoServiceElements()) {
								if ( woServiceElementVO.getIsSerialized().equals( CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity() ) ) {
									attentionElements.setWoServiceElementVO(new WorkOrderServiceElementVO());
									attentionElements.setWoServiceElementVO(woServiceElementVO);
									trayHelper.reportElementsUsedFinalization(attentionElements);
								}
							}						
						
							//Le da valor al objeto para reportar el movimiento de no serializados
							this.populateObjectInventoryFinalization(inventoryDTO, trayWOManagmentDTO, woAttentionDTO, woServiceVO );
						}
					//}
					//En caso que haya valores para enviar al servicio de inventarios, se llama el servicio
					if ( serviceCount == woAttentionDTO.getWorkorderServices().size() && inventoryDTO != null && inventoryDTO.isInvokeInventoryService() ) {
						log.debug("Se va a notificar a inventarios los elementos no serializados");
						trayHelper.updateRecordNotSerializedInventory( inventoryDTO );
					}
					
				}

				//Si la work order no fue finalizada en el proceso de atencion
				//ser realiza la notificacion del cambio de estado desde este
				//metodo, de lo contrario se realiza en el de atencion
				if ( !trayWOManagmentDTO.getWorkOrderFinished() ) {					
					//invocar el cambio de estado de la WorkOrder en HSP+
					//esta operacion retorna la reason de finalizacion y el tiempo de trabajo de la WO
					woAttentionDTO.setComments( trayWOManagmentDTO.getFinalizationComments() );
					woAttentionDTO = trayHelper.stateChangeUpdateWOFinalization(woAttentionDTO);
				}	

				//Se envia a IBS todos los servicios que se adicionan a la WO. 
				for(WorkOrderServiceVO woServiceVO : woAttentionDTO.getWorkorderServices()){
					//se invoca el proceso para informar la adicion de servicios a IBS
					if (woServiceVO.getAddService()) {
						trayHelper.addingServicesReport( woServiceVO, woAttentionDTO.getWorkorderVo().getId() );
					}
				}
				
				if ( !trayWOManagmentDTO.getWorkOrderFinished() ) {
					//invocar el cambio de estado de la WO en IBS
					trayHelper.completeWorkOrderFinalization( woAttentionDTO );
				}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación finalizationWorkOrder/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina finalizationWorkOrder/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Encapsula la informacion de los elementos
	 * serializados empleados en la atencion.
	 * @param woServiceDTO TrayWorkOrderServiceDTO
	 * @param woAttentionDTO woAttentionDTO
	 * @param request TrayWOManagmentDTO
	 * @return WOAttentionElementsRequestDTO
	 * @throws PropertiesException
	 * @author jalopez
	 */
	public WOAttentionElementsRequestDTO populateAttentionElements(TrayWorkOrderServiceDTO woServiceDTO, WOAttentionsRequestDTO woAttentionDTO, TrayWOManagmentDTO request, ServiceAttendResponseDTO serviceAttend) throws PropertiesException{
		WOAttentionElementsRequestDTO attentionElements = new WOAttentionElementsRequestDTO();
		attentionElements.setInstallationSerializedElements( this.getSerializedAttentionElements(woServiceDTO.getWoServiceElements()) );
		attentionElements.setRecoverySerializedElements( this.getSerializedAttentionElements(woServiceDTO.getWoServiceElementsRecovery()) );
		attentionElements.setSwapFallaTecnicaNew( CodesBusinessEntityEnum.CORE_SWAP_FALLA_TECNICA_NEW.getCodeEntity() );
		attentionElements.setSwapFallaTecnicaOld( CodesBusinessEntityEnum.CORE_SWAP_FALLA_TECNICA_OLD.getCodeEntity() );
		attentionElements.setWorkorderVo( woAttentionDTO.getWorkorderVo() );	
		attentionElements.setWorkOrderServiceId( woServiceDTO.getWorkOrderServiceId() );
		attentionElements.setDealerCode( request.getDealerCode() );
		attentionElements.setUserId( woAttentionDTO.getUserId() );	
		attentionElements.setCrewResponsibleName( request.getResponsibleCrewName() );
		attentionElements.setAttentionComment( request.getAttentionComments() );
		attentionElements.setCustomerCode( request.getCustomerCode() );
		attentionElements.setWoService( serviceAttend.getWoService() );
		attentionElements.setCountryCode( woAttentionDTO.getCountryCode() );
					
		return attentionElements;
	}

	/**
	 * 
	 * Metodo: Retorna los elementos serializados utilizados
	 * en la atencion de la WorkOrder
	 * @param serviceElements
	 * @return List<WorkOrderServiceElementVO>
	 * @throws PropertiesException
	 * @author jalopez
	 */
	public List<WorkOrderServiceElementVO> getSerializedAttentionElements(List<WorkOrderServiceElementVO> serviceElements) throws PropertiesException{
		List<WorkOrderServiceElementVO> workOrderServiceElements = new ArrayList<WorkOrderServiceElementVO>();
		if (serviceElements != null && !serviceElements.isEmpty()) {
			for (WorkOrderServiceElementVO woServiceElementVO : serviceElements ) {
				if ( woServiceElementVO.getIsSerialized().equals( CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() ) ) {
					workOrderServiceElements.add( woServiceElementVO );
				}
			}
		}
		return workOrderServiceElements;
	}
	
	/**
	 * 
	 * Metodo: Resgistra los elementos empleados en la atencion
	 * en la Base de Datos de HSP+.
	 * @param attentionElements
	 * @param service
	 * @throws PropertiesException
	 * @throws BusinessException
	 * @author jalopez
	 */
	private void reportAttentionElements(WOAttentionElementsRequestDTO attentionElements, Service service) throws PropertiesException, BusinessException {
		
		//Invoca el componente de negocio para reportar los elementos empleados en la atencion
		if ( service.getAllowRecordingElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) 
				|| service.getAllowChangeElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) 
				|| service.getAllowRetrieveElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ) {
			
			if ( attentionElements.getInstallationSerializedElements() != null && !attentionElements.getInstallationSerializedElements().isEmpty() ) {
				trayHelper.reportarElementosUtilizadosEnServicios( attentionElements );				
			}
			
			if ( attentionElements.getRecoverySerializedElements() != null && !attentionElements.getRecoverySerializedElements().isEmpty() ) {
				trayHelper.reportarElementosRecuperadosEnServicios( attentionElements );			
			}
		}		
	}
	
	/**
	 * 
	 * Metodo: Valida si el servicio es Upgrade o Downgrade.
	 * @param service Service
	 * @return Boolean
	 * Retorna null si no es un servicio de instalacion
	 * Retorna true si es un servicio Upgrade o Downgrade
	 * Retorna False si es un servicio de instalacion y permite 
	 * el registro o cambio de elementos. 
	 * @throws PropertiesException
	 * @author jalopez
	 */
	private Boolean validateCalledActivation(Service service) throws PropertiesException{
		
		Boolean isUpgradeDowngrade = null;
		
		//Si es un servicio de Instalacion y la categoria del servicio es
		//Basica ó Adicional mismo dia ó Adicional segunda visita se debe generar la activacion, 
		//solo si el servicio permite registro de elementos o cambio de elementtos
		if( service.getServiceCategory().getServiceType().getServiceTypeCode().equalsIgnoreCase( CodesBusinessEntityEnum.SERVICE_TYPE_INSTALL.getCodeEntity() ) 
				&& ( service.getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_BASIC.getCodeEntity() ) 
						||	service.getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_ADDITIONAL_DAY.getCodeEntity()) 
						|| 	service.getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_ADDITIONAL_SECOND_VISIT.getCodeEntity() ) ) ){
			
			//Se valida que el servicio este parametrizado para permitir el registro o cambio de elementos
			if(service.getAllowChangeElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) 
			   || service.getAllowRecordingElement().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
				isUpgradeDowngrade = new Boolean( false );
				
				//Se verifica si el servicio es de la Categoria Upgrade o Downgrade, puesto que se debe invocar un servicio 
				//de IBS diferente para estos servicios
				if( service.getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_UPGRADE.getCodeEntity() ) 
				    || service.getServiceCategory().getServiceCategoryCode().equalsIgnoreCase( CodesBusinessEntityEnum.CORE_SERVICE_CATEGORY_DOWNGRADE.getCodeEntity() ) ){
					isUpgradeDowngrade = new Boolean( true );				
				}
			}
		}
		
		return isUpgradeDowngrade;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderTrayResponse getWorkOrdersForDealerTray(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		log.debug("== Inicia getWorkOrdersForDealerTray/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( filter == null || filter.getCountryId() == null || filter.getCountryId() <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			//En caso que el filtro venga del acordeon se llama metodo que realiza la consulta de las workorders de la bandeja
			//del dealer con filtro de acordeon
			if( filter.isFromExpirationGrouping() )
				return this.getWorkOrdersForDealerTrayWithExpirationGrouping(filter, requestCollectionInfo);
			
			//Validacion de rangos de fechas
			validateTrayDates(filter.getWoAgendationDateInit(), filter.getWoAgendationDateEnd(), 2);
			validateTrayDates(filter.getWoAttentionDateInit(), filter.getWoAttentionDateEnd(), 2);
			validateTrayDates(filter.getWoCreationDateInit(), filter.getWoCreationDateEnd(), 2);
			validateTrayDates(filter.getWoFinalizationDateInit(), filter.getWoFinalizationDateEnd(), 2);
			
			List<String> processSourceCodes = new ArrayList<String>();
			if(filter.isScheduledCSR()){
				for(Long woStatusId : filter.getWoStatusIds()){ 
					WorkorderStatus scheduleStatus = workorderStatusDAO.getWorkorderStatusByID(woStatusId);
					if (scheduleStatus != null && scheduleStatus.getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity())){
						processSourceCodes.add(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_PROCESS_STATUS_CSR_AG.getCodeEntity());
					}else { //Es RESCHEDULED
						processSourceCodes.add(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_PROCESS_STATUS_CSR_REAG.getCodeEntity());
					}
				}			
				filter.setProcessSourceCodes(processSourceCodes);
			}
			//En caso que no tenga filtro por estado, filtra por los estados permitidos en esta bandeja
			if( filter.getWoStatusIds() == null || filter.getWoStatusIds().isEmpty() ){
				WorkorderStatus asignStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity() );
				WorkorderStatus reAsignStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity() );
				WorkorderStatus scheduleStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity() );
				WorkorderStatus reScheduleStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity() );
				//WorkorderStatus realizedStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity() );
				WorkorderStatus pendingStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity() );
				
				List<Long> woStatusIds = new ArrayList<Long>();
				if( asignStatus != null )
					woStatusIds.add( asignStatus.getId() );
				if( reAsignStatus != null )
					woStatusIds.add( reAsignStatus.getId() );
				if( scheduleStatus != null )
					woStatusIds.add( scheduleStatus.getId() );
				if( reScheduleStatus != null )
					woStatusIds.add( reScheduleStatus.getId() );

				/*if( realizedStatus != null )
					woStatusIds.add( realizedStatus.getId() );*/
				if( pendingStatus != null )
					woStatusIds.add( pendingStatus.getId() );
				filter.setWoStatusIds(woStatusIds);
			}
			this.setDealerForUser(filter);
			this.fillDealerAndBranchIds(filter);
			
			
			WorkOrderResponse daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filter,requestCollectionInfo, true);
			WorkOrderTrayResponse response = new WorkOrderTrayResponse();
			List<WorkOrderTrayDTO> workOrderList = new ArrayList<WorkOrderTrayDTO>();
			if( daoResponse.getWorkOrdersIds() != null && !daoResponse.getWorkOrdersIds().isEmpty() ){
				
				/*for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();					
					//ADICIONA LA WO A LA LISTA DE RETORNO
					workOrderList.add( this.fillWorkOrderInformation(woId) );
				}			
				response.setWorkOrderList(workOrderList);*/
				List<Long> woIds = new ArrayList<Long>();
				for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();
					woIds.add(woId);
				}
				response.setWorkOrderList( this.fillWorkOrderInformationOptimized(woIds,filter.getUserId()) );
			}
			
			if( requestCollectionInfo != null )
				this.populatePaginationInfo(response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), daoResponse.getRowCount(), daoResponse.getTotalRowCount());
			
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersForDealerTray/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForDealerTray/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/**
	 * Método encargado de realizar el fill del dealer y sucursal según el usuario recibido
	 * @param filter
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void setDealerForUser(WorkOrderFilterTrayDTO filter) throws BusinessException{
		log.debug("== Inicia getWorkOrdersForAllocator/TrayWorkOrderManagmentBusinessBean ==");
		UtilsBusiness.assertNotNull(filter, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(filter.getUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			User user = daoUser.getUserById(filter.getUserId());
			if(user == null){
				throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),  ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			}
			if(user.getDealer()!=null){
				if(user.getDealer().getDealer()!=null){
					
					//Adiciona compañia principal
					List<Long> dealerIds = new ArrayList<Long>();
					dealerIds.add(user.getDealer().getDealer().getId());
					filter.setDealersIds(dealerIds);
					
					//Adiciona compañia la sucursal asociada
					List<Long> dealerBranchesIds = new ArrayList<Long>();
					dealerBranchesIds.add(user.getDealer().getId());
					filter.setBranchIds(dealerBranchesIds);
					
					
				}else{
					List<Long> dealerIds = new ArrayList<Long>();
					dealerIds.add(user.getDealer().getId());
					filter.setDealersIds(dealerIds);
				}
			}
			
			//filter.set
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersForDealerTray/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForDealerTray/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderTrayResponse getWorkOrdersForAllocator(WorkOrderFilterTrayDTO filter, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		log.debug("== Inicia getWorkOrdersForAllocator/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( filter == null || filter.getCountryId() == null || filter.getCountryId() <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			//Se coloca el indicador de que el dealer tiene que ir contra WorkOrders
			filter.setDealerToWO(true);
			
			//En caso que no tenga filtro por estado, filtra por los estados permitidos en esta bandeja
			if( filter.getWoStatusIds() == null || filter.getWoStatusIds().isEmpty() ){
				WorkorderStatus activeStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity() );
				WorkorderStatus rejectedStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity() );
				List<Long> woStatusIds = new ArrayList<Long>();
				if( activeStatus != null )
					woStatusIds.add( activeStatus.getId() );
				if( rejectedStatus != null )
					woStatusIds.add( rejectedStatus.getId() );
				filter.setWoStatusIds(woStatusIds);
			}
			//Validacion de rangos de fechas
			validateTrayDates(filter.getWoCreationDateInit(), filter.getWoCreationDateEnd(), 2);
			validateTrayDates(filter.getWoCancelationDateInit(), filter.getWoCancelationDateEnd(), 2);
			
			this.fillDealerAndBranchIds(filter);
			
			WorkOrderResponse daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filter,requestCollectionInfo,true);
			WorkOrderTrayResponse response = new WorkOrderTrayResponse();
			if( daoResponse.getWorkOrdersIds() != null && !daoResponse.getWorkOrdersIds().isEmpty() ){
				List<WorkOrderTrayDTO> workOrderList = new ArrayList<WorkOrderTrayDTO>();
				for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();
					//ADICIONA LA WO A LA LISTA DE RETORNO
					workOrderList.add( this.fillWorkOrderInformationn(woId) );
				}
				response.setWorkOrderList(workOrderList);
				/*List<Long> woIds = new ArrayList<Long>();
				for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();
					woIds.add(woId);
				}
				response.setWorkOrderList( this.fillWorkOrderInformationOptimized(woIds) );*/
			}
			if( requestCollectionInfo != null )
				this.populatePaginationInfo(response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), daoResponse.getRowCount(), daoResponse.getTotalRowCount());
			
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersForAllocator/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForAllocator/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public RequiredServiceElementVO getRequiredServiceElements(TrayWOManagmentDTO request) throws BusinessException{
		try{
			log.debug("== Inicia getRequiredServiceElements/TrayWorkOrderManagmentBusinessBean ==");
			//Objetos Globales
			InventoryDTO inventoryDTO = new InventoryDTO();
			WorkOrderVO workorderVO = new WorkOrderVO();
			TrayServiceDTO serviceDTO = new TrayServiceDTO();
		
			//Validacion de datos requeridos
			Object[] params = null;
			params = new Object[2];			
			params[1] = "getRequiredServiceElements";	
			params[0] = "WorkOrderId";
			UtilsBusiness.validateRequestResponseWebService(params, request.getWorkOrderId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "ServiceCode";
			UtilsBusiness.validateRequestResponseWebService(params, request.getServiceDTO(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));			
			params[0] = "ServiceCode";
			UtilsBusiness.validateRequestResponseWebService(params, request.getServiceDTO().getServiceCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "ServiceId";
			UtilsBusiness.validateRequestResponseWebService(params, request.getServiceDTO().getServiceId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "CountryId";
			UtilsBusiness.validateRequestResponseWebService(params, request.getCountryId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			
			
			workorderVO.setId(request.getWorkOrderId());
			serviceDTO.setServiceCode( request.getServiceDTO().getServiceCode() );
			serviceDTO.setServiceId( request.getServiceDTO().getServiceId() );
			inventoryDTO.setWorkOrderVO( workorderVO );
			inventoryDTO.setServiceDTO( serviceDTO );
			
			//Se realiza la consulta del parametro del sistema que define los servicios de inventarios que se deben invocar,
			//debido a que la aplicacion puede ser conectada a los servicios de SmartDealerI o los propios de HSP.
			SystemParameter invokeInternalInventory = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_INVOKE_HSP_INVENTORY_SERVICE.getCodeEntity(), request.getCountryId());
			if (invokeInternalInventory == null) {
				log.fatal("No se ha encontrado el parametro del sistema INVOKE_HSP_INVENTORY_SERVICE");
				throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
			}
			inventoryDTO.setInvokeInternalInventory(invokeInternalInventory.getValue());	
			
			RequiredServiceElementDTO requiredService =  trayHelper.getRequiredServiceElements(inventoryDTO);		
			
			return requiredService.getRequiredServiceElement();
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getRequiredServiceElements/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getRequiredServiceElements/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#validateTrayDates(java.util.Date, java.util.Date, int)
	 */
	public void validateTrayDates(Date initDate , Date endDate,int maxRange) throws BusinessException{
		Calendar init = null;
    	Calendar end = null;
		if( initDate != null && endDate != null ){
			initDate = UtilsBusiness.dateTo12am(initDate);
			endDate = UtilsBusiness.dateTo12pm(endDate);
			init = Calendar.getInstance();
			end = Calendar.getInstance();
			init.setTime( initDate );
			end.setTime( endDate );
			if (init.get(Calendar.YEAR) != 1 && end.get(Calendar.YEAR) != 1){
				if(init.after(end)){
					throw new BusinessException(ErrorBusinessMessages.TRAY_INIT_DATE_AFTER_END_DATE.getCode(),ErrorBusinessMessages.TRAY_INIT_DATE_AFTER_END_DATE.getMessage());
				}else{
					end.add(Calendar.MONTH, maxRange*-1);
					end.add(Calendar.DAY_OF_MONTH, -1);
			    	end.getTime();
			    	init.getTime();
			    	if(end.after(init)){
			    		throw new BusinessException(ErrorBusinessMessages.DATE_RANGE_CANNOT_BE_GREATER_THAN_TWO.getCode(),ErrorBusinessMessages.DATE_RANGE_CANNOT_BE_GREATER_THAN_TWO.getMessage());
			    	} 
				}
			}
    	}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#fillWoReason(java.lang.Long, java.util.List)
	 */
	public WorkorderReasonVO fillWoReason(Long woId , List<Long> woStatusIds) throws BusinessException{
		log.debug("== Inicia fillWoReason/TrayWorkOrderManagmentBusinessBean ==");
		try{
			WorkorderReasonVO response = null;
			WoStatusHistory woStatusHistory = null;
			if( woStatusIds != null && !woStatusIds.isEmpty() ){
				for( int i = 0 ; i < woStatusIds.size() ; i++ ){
					Long woStatusId = woStatusIds.get(i);
					WoStatusHistory temp = woStatusHistoryDAO.getWorkorderReasonByWoHistory(woId, woStatusId );
					if( ( woStatusHistory == null || woStatusHistory.getStatusDate() == null )
							&& temp != null && temp.getStatusDate() != null ){
						woStatusHistory = UtilsBusiness.copyObject( WoStatusHistory.class , temp);
					} else if( woStatusHistory != null && woStatusHistory.getStatusDate() != null
							&& temp != null && temp.getStatusDate() != null
							&& woStatusHistory.getStatusDate().before( temp.getStatusDate() ) ){
						woStatusHistory = UtilsBusiness.copyObject( WoStatusHistory.class , temp);
					} 
				}
			}
			if( woStatusHistory != null && woStatusHistory.getWorkorderReason() != null ){
				response = UtilsBusiness.copyObject( WorkorderReasonVO.class , woStatusHistory.getWorkorderReason() );
				response.setWorkorderReasonCategory( null );
				response.setWorkorderStatus( null );
				response.setResponsibleArea( null );
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación fillWoReason/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina fillWoReason/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getServicesByWoTypeId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceVO> getServicesByWoTypeId(Long woTypeId) throws BusinessException {
		log.debug("== Inicia getServicesByWoTypeId/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( woTypeId == null || woTypeId <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			List<Service> daoResponse = serviceDAO.getServicesByWoTypeId(woTypeId);			
			//Se registra en el log si el servicio no tiene inforamcion paramtrizada en la tabla TYPE_SERVICE_WORK_ORDERS
			if( daoResponse == null || daoResponse.isEmpty() ){
				log.warn( ErrorBusinessMessages.WO_TYPE_DOESNT_HAVE_SERVICE_TYPE.getMessageCode() );
				return null;
			}
			return UtilsBusiness.convertList(daoResponse, ServiceVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getServicesByWoTypeId/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getServicesByWoTypeId/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getElementBySerialAndDealerId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElementVO getElementBySerialAndDealerId(String elementSerial, Long dealerId) throws BusinessException {
		log.debug("== Inicia getElementBySerialAndDealerId/TrayWorkOrderManagmentBusinessBean ==");		
		UtilsBusiness.assertNotNull(elementSerial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			WarehouseElement pojo = warehouseElementDAO.getElementBySerialAndDealerId(elementSerial, dealerId, CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES.getCodeEntity());
			if( pojo != null ){
				WarehouseElementVO vo = UtilsBusiness.copyObject(WarehouseElementVO.class, pojo); 
				this.fillWarehouseElementVOInfo(vo);
				return vo;
			}
			pojo = warehouseElementDAO.getElementBySerialAndDealerIdInCrewWh(elementSerial, dealerId, CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES.getCodeEntity());
			if( pojo != null ){
				WarehouseElementVO vo = UtilsBusiness.copyObject(WarehouseElementVO.class, pojo);
				this.fillWarehouseElementVOInfo(vo);
				return vo;
			}
			Object[] params = null;
			params = new Object[2];
			params[0] = elementSerial;
			params[1] = "Disponibles";
			throw new BusinessException(ErrorBusinessMessages.INVENTORY_IN333.getCode(), ErrorBusinessMessages.INVENTORY_IN333.getMessage(params));
		} catch( Throwable ex ) {
			log.error("== Error al tratar de ejecutar la tarea getElementBySerialAndDealerId/TrayWorkOrderManagmentBusinessBean ==");
	    	throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerId/TrayWorkOrderManagmentBusinessBean ==");
		}		
	}
	
	/**
	 * 
	 * Metodo: Elimina la informacion innecesaria en el objeto Warehouseelement para retornar informacion de elemento 
	 * @param pojo WarehouseElement informacion de elemento
	 * @author jnova
	 */
	private void fillWarehouseElementVOInfo(WarehouseElementVO vo) {
		vo.setWarehouseId(null);
		vo.setRecordStatus(null);
		vo.setMovementType(null);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getElementBySerialAndCustomerId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElementVO getElementBySerialAndCustomerId(String elementSerial, Long customerId) throws BusinessException {
		log.debug("== Inicia getElementBySerialAndCustomerId/TrayWorkOrderManagmentBusinessBean ==");		
		UtilsBusiness.assertNotNull(elementSerial, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			WarehouseElement pojo = warehouseElementDAO.getElementBySerialAndCustomerId(elementSerial, customerId);
			if( pojo != null ){
				WarehouseElementVO vo = UtilsBusiness.copyObject(WarehouseElementVO.class, pojo); 
				this.fillWarehouseElementVOInfo(vo);
				return vo;
			}
			throw new BusinessException(ErrorBusinessMessages.ELEMENT_DOESNT_EXIST_IN_CUSTOMER_WH.getCode(), ErrorBusinessMessages.ELEMENT_DOESNT_EXIST_IN_CUSTOMER_WH.getMessage());
		} catch( Throwable ex ) {
			log.error("== Error al tratar de ejecutar la tarea getElementBySerialAndCustomerId/TrayWorkOrderManagmentBusinessBean ==");
	    	throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndCustomerId/TrayWorkOrderManagmentBusinessBean ==");
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getDealerTrayFilterStatuss()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkorderStatusVO> getDealerTrayFilterStatuss() throws BusinessException {
		log.debug("== Inicia getDealerTrayFilterStatuss/TrayWorkOrderManagmentBusinessBean ==");		
		try{
			return UtilsBusiness.convertList( workorderStatusDAO.getDealerTrayFilterStatuss() , WorkorderStatusVO.class);
		} catch( Throwable ex ) {
			log.error("== Error al tratar de ejecutar la tarea getDealerTrayFilterStatuss/TrayWorkOrderManagmentBusinessBean ==");
	    	throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerTrayFilterStatuss/TrayWorkOrderManagmentBusinessBean ==");
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getWorkorderDetail(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderTrayDTO getWorkorderDetail(WorkOrderFilterTrayDTO filter) throws BusinessException {
		log.debug("== Inicia getWorkorderDetail/TrayWorkOrderManagmentBusinessBean ==");		
		try{
			if( filter.getCountryId() == null || filter.getCountryId() <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			if( filter.getWoCode() == null || filter.getWoCode().equals("") ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			WorkOrderResponse daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filter,null,false);
			WorkOrderTrayDTO dto = new WorkOrderTrayDTO();
			/**
			 * Se le asignan los valores al la respuesta
			 * */
			if( daoResponse != null && daoResponse.getWorkOrdersIds() != null && !daoResponse.getWorkOrdersIds().isEmpty() ){
				Object[] object = daoResponse.getWorkOrdersIds().get(0);
				Long woId = ( (BigDecimal) object[0] ).longValue();				
				
				/*CONSULTA OBJETO WO EN LA BD*/
				WorkOrder workOrder = workOrderDAO.getWorkOrderByID( woId );
				
				/*SECCION PARA AGREGAR EL NOMBRE DEL CLIENTE*/
				if( workOrder.getCustomer() != null ){
					CustomerDTO customer = new CustomerDTO();
					customer.setFirstNames( workOrder.getCustomer().getFirstName() != null ? workOrder.getCustomer().getFirstName() : null );
					customer.setLastNames( workOrder.getCustomer().getLastName() != null ? workOrder.getCustomer().getLastName() : null );
					customer.setAddress( workOrder.getCustomer().getCustomeraddress() != null ? workOrder.getCustomer().getCustomeraddress() : null );
					customer.setCustomerCode( workOrder.getCustomer().getCustomerCode() != null ? workOrder.getCustomer().getCustomerCode() : null );
					customer.setDocumentNumber( workOrder.getCustomer().getDocumentNumber() != null ? workOrder.getCustomer().getDocumentNumber() : null );
					customer.setType( UtilsBusiness.copyObject( CustomerClassTypeVO.class , workOrder.getCustomer().getCustType() ) );
					customer.setDocumentType( documentTypesCRUD.getDocumentTypesByID( workOrder.getCustomer().getDocumentTypeId() ) );
					customer.setCustomerMediaContacts( UtilsBusiness.convertList( customerMediaContactDAO.getCustomerMediaContactByCustomerId( workOrder.getCustomer().getId() ) , CustomerMediaContactVO.class) );
					
					dto.setCustomerDTO(customer);
				}
				
				/*SECCION PARA DARLE VALOR AL CODIGO POSTAL Y LA DIRECCION DE LA WO*/
				if(workOrder!=null 
						&& workOrder.getCustomerAddress()!=null 
						&& workOrder.getCustomerAddress().getPostalCode()!=null){
					dto.setCity(workOrder.getCustomerAddress().getPostalCode().getCity().getCityName());
					dto.setStateCity(workOrder.getCustomerAddress().getPostalCode().getCity().getState().getStateName());
					dto.setPerimeter(workOrder.getCustomerAddress().getPostalCode().getZoneType().getZoneTypeName());
				}
				
				
				dto.setPostalCode( UtilsBusiness.copyObject(PostalCodeVO.class, workOrder.getPostalCode()) );
				dto.setAddress( workOrder.getCustomer().getCustomeraddress() );				
				
				/*SECCION PARA DARLE VALOR A INFORMACION DE LA WO*/
				dto.setWorkorderCode( workOrder.getWoCode() );
				dto.setWorkOrderTypeVO( UtilsBusiness.copyObject(WoTypeVO.class, workOrder.getWoTypeByWoTypeId()) );
				dto.setWorkorderStatusVO( UtilsBusiness.copyObject(WorkorderStatusVO.class, workOrder.getWorkorderStatusByActualStatusId()) );
				dto.setServicesByWorkOrder( this.getWorkOrderServices(woId , null).getWorkOrderServicesVO() );
				dto.setWorkOrderDescription( workOrder.getWoDescription() != null ? workOrder.getWoDescription() : null );
				dto.setCreationDate( workOrder.getCreationDate() != null ? workOrder.getCreationDate() : null);
				dto.setWoId( woId );
				dto.setImportDate( workOrder.getImportDate() );
				
				dto.setWorkOrderDescription( workOrder.getWoDescription() );
				dto.setCreationDate( workOrder.getCreationDate() );
				
				/*SECCION PARA ASIGNAR INFORMACION DE SHIPPING ORDER*/
				dto.setShippingOrderList( fillShippingOrderListByWOId( woId ) );
				
				/*SECCION PARA ASIGNAR INFORMACION DE CONTACTS DE WO*/
				ContactDTO contactDto = new ContactDTO();
				contactDto.setWoCode( workOrder.getWoCode() );
				dto.setContacts( contactBusiness.getContactsWorkOrderByDealer( contactDto ) );
				
				/*ASIGNA LA FECHA DE AGENDAMIENTO DEL ULTIMO SERVICIO ASOCIADO AL CLIENTE*/
				WorkOrderAgenda lastWorkOrderagenda = workOrderAgendaDAO.getLastWorkOrderAgendaByCustomerIdAndWoStatus(workOrder.getCustomer().getId(), CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
				if( lastWorkOrderagenda != null && lastWorkOrderagenda.getAgendationDate() != null )
					dto.setLastAttentionServiceDate( lastWorkOrderagenda.getAgendationDate() );
				
				/*SECCION PARA DARLE VALOR A INFORMACION DE AGENDAMIENTO Y JORNADA*/
				WorkOrderAgenda workOrderagenda = workOrderAgendaDAO.getLastWorkOrderAgendaByWoID(woId);
				if( workOrderagenda != null ){					
					dto.setAgendationDate( workOrderagenda.getAgendationDate() != null ? workOrderagenda.getAgendationDate() : null );
					if( workOrderagenda.getServiceHour() != null )
						dto.setServiceHourVO( UtilsBusiness.copyObject(ServiceHourVO.class, workOrderagenda.getServiceHour() ) );
				}
				/*FECHA DE ATENCION*/
				dto.setAttentionDate( workOrder.getWoRealizationDate() != null ? workOrder.getWoRealizationDate() : null );
				/*FECHA DE FINALIZACION*/
				dto.setFinalizationDate( workOrder.getFinalizationDate() != null ? workOrder.getFinalizationDate() : null );
				
				
				/*SECCION para agregar informacion adicional de dealer*/
				WoAssignment woAssignment = this.woAssignmentDAO.getLastDealerAssignmentByWoId( woId );
				if( woAssignment != null && woAssignment.getDealerId() != null && woAssignment.getDealerId().longValue() > 0){
					Dealer assignDealer = this.dealersDAO.getDealerByID( woAssignment.getDealerId() );
					dto.setDealerCode( assignDealer.getDealerCode() );
					dto.setDealerName( assignDealer.getDealerName() );
					dto.setDealerId( woAssignment.getDealerId() );
				}
				/*SECCION para agregar informacion adicional de dealer*/
				WoCrewAssigments woCrewAssigment = woCrewAssigmentsDAO.getLastWoCrewAssigments( woId );
				if( woCrewAssigment != null ){
					Employee employee = employeesCrewDAO.getEmployeeResponsibleByCrewId( woCrewAssigment.getCrewId().getId() );
					if( employee != null ){
						dto.setResponsableName( employee.getFirstName() +" "+ employee.getLastName() );
						dto.setResponsableDocumentNumber( employee.getDocumentNumber() );
						EmployeeMediaContact emc = employeeMediaContactDAO.getEmployeeMediaContactByEmployeeIdAndMediaContCode(employee.getId(), CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_MOBILE_CODE.getCodeEntity());
						if( emc != null )
							dto.setResponsableMobileNumber( emc.getMediaContactValue() );
						EmployeeVehicle ev = employeeVehicleDAO.getEmployeeVehicleByEmployeeId( employee.getId() );
						if( ev != null )
							dto.setPlate( ev.getVehicle().getPlate() );
					}
				}
					
				/*AGREGA INFORMACION DEL VENDEDOR*/
				if( workOrder.getSaleDealer() != null ){
					dto.setSaleDealerCode( workOrder.getSaleDealer().getDealerCode() );
					dto.setSaleDealerName( workOrder.getSaleDealer().getDealerName() );
					dto.setSaleDealerMediaContacts( this.dealersMediaContactCRUDBean.fillDealerMediaContacts( workOrder.getSaleDealer().getId() ) );
				}
				
				if(workOrder!=null
						&& workOrder.getCustomer()!=null
						&& workOrder.getCustomer().getCustomerAddresses()!=null
						&& !workOrder.getCustomer().getCustomerAddresses().isEmpty()){
					boolean haveDefault=false;
					for(CustomerAddresses ca : workOrder.getCustomer().getCustomerAddresses()){
						if(ca.getAddressType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_DEFAULT.getCodeEntity())){
							if(dto.getCustomerDTO()==null){
								dto.setCustomerDTO(new CustomerDTO());
								dto.getCustomerDTO().setCustomerId(workOrder.getCustomer().getId());
							}
							dto.getCustomerDTO().setCity(ca.getPostalCode().getCity().getCityName());
							dto.getCustomerDTO().setStateCity(ca.getPostalCode().getCity().getState().getStateName());
							dto.getCustomerDTO().setPerimeter(ca.getPostalCode().getZoneType().getZoneTypeName());
							haveDefault=true;
							break;
						}
					}
					if(!haveDefault){
						for(CustomerAddresses ca : workOrder.getCustomer().getCustomerAddresses()){
							dto.getCustomerDTO().setCity(ca.getPostalCode().getCity().getCityName());
							dto.getCustomerDTO().setStateCity(ca.getPostalCode().getCity().getState().getStateName());
							dto.getCustomerDTO().setPerimeter(ca.getPostalCode().getZoneType().getZoneTypeName());
							break;
						}
					}
				}
				
				//Se colocan los contact de ibs a la consulta de la wo
				dto.setIbsContactDTO(ibsContactBusinessBean.getIbsContactDTOByWorkOrderId(woId, filter.getCountryId()));
				
				
			}
			return dto;
		} catch( Throwable ex ) {
			log.error("== Error al tratar de ejecutar la tarea getWorkorderDetail/TrayWorkOrderManagmentBusinessBean ==");
	    	throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWorkorderDetail/TrayWorkOrderManagmentBusinessBean ==");
		}	
	}
	
	
	/**
	 * 
	 * Metodo: Obtiene la informacion de las shipping order por WO junto con los elementos asociados a cada una
	 * @param woId Identificador de la WO
	 * @return List<ShippingOrderVO> Lista de SO con elementos asociados
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private List<ShippingOrderVO> fillShippingOrderListByWOId(Long woId) throws BusinessException{
		log.debug("== Inicia fillShippingOrderListByWOId/TrayWorkOrderManagmentBusinessBean ==");
		try{
			List<ShippingOrder> pojoList = shippingOrderDAO.getShippingOrdersByWorkOrder( woId );
			List<ShippingOrderVO> response = null;
			if( pojoList != null && !pojoList.isEmpty() ){
				response = new ArrayList<ShippingOrderVO>();
				for( ShippingOrder so : pojoList ){
					ShippingOrderVO vo = UtilsBusiness.copyObject(ShippingOrderVO.class, so);
					//jnova 05-05-2011 Se comenta para darle valor a los SO elements con solo las tecnologias
					//vo.setShippingOrderElements( fillShippingOrderElements ( shippingOrderElementBusiness.getShippingOrderElementBySOID(vo.getId() ) ) );
					vo.setShippingOrderElements( getShippingOrderElements ( vo.getId() ) );
					//vo.setShippingOrderTechnologies( UtilsBusiness.convertList(shippingOrderDetailDAO.getShippingOrderTechnologiesBySOId( vo.getId() ), TechnologyVO.class) );
					response.add(vo);
				}
			}
			return response;
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la tarea fillShippingOrderListByWOId/TrayWorkOrderManagmentBusinessBean ==", e);
	    	throw this.manageException(e);
		} finally {
			log.debug("== Termina fillShippingOrderListByWOId/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getShippingOrderElements(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ShippingOrderElementVO> getShippingOrderElements(Long soId) throws BusinessException{
		log.debug("== Inicia fillShippingOrderElementsTechnologies/TrayWorkOrderManagmentBusinessBean ==");
		try{
			List<ShippingOrderElementVO> response = null;
			List<ShippingOrderDetail> soDetails = shippingOrderDetailDAO.getShippingOrderDetailsByShippingOrderId(soId);
			if( soDetails != null && !soDetails.isEmpty() ){
				response = new ArrayList<ShippingOrderElementVO>();
				for( ShippingOrderDetail detail : soDetails ){
					ShippingOrderElementVO vo = new ShippingOrderElementVO();
					vo.setTechnology( UtilsBusiness.copyObject(TechnologyVO.class, detail.getTechnology()) );
					vo.setElementModel( elementModelDAO.getElementModelByCode( detail.getIbsModelCode() ) );
					vo.setSerial( detail.getSerialCode() );
					response.add( vo );
				}
			}
			/*List<Technology> technologies = shippingOrderDetailDAO.getShippingOrderTechnologiesBySOId( soId );
			if( technologies != null && !technologies.isEmpty() ){
				response = new ArrayList<ShippingOrderElementVO>();
				for( Technology tec : technologies ){
					ShippingOrderElementVO vo = new ShippingOrderElementVO();
					vo.setTechnology( UtilsBusiness.copyObject(TechnologyVO.class, tec) );
					response.add( vo );
				}
			}*/
			return response;
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la tarea fillShippingOrderElementsTechnologies/TrayWorkOrderManagmentBusinessBean ==", e);
	    	throw this.manageException(e);
		} finally {
			log.debug("== Termina fillShippingOrderElementsTechnologies/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#validateServiceTypeByWoTypeId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateServiceTypeByWoTypeId(Long serviceTypeId, Long woTypeId) throws BusinessException {
		log.debug("== Inicia validateServiceTypeByWoTypeId/TrayWorkOrderManagmentBusinessBean ==");
		try{//typeServiceWorkOrdersDAO
			if( woTypeId == null || woTypeId <= 0L || serviceTypeId == null || serviceTypeId <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			TypeServiceWorkOrders typeServiceWorkOrders = typeServiceWorkOrdersDAO.getTypeServiceWorkOrdersByServiceTypeIdAndWoTypeId(serviceTypeId, woTypeId);
			if( typeServiceWorkOrders == null )
				throw new BusinessException(ErrorBusinessMessages.SERVICE_TYPE_DOESNT_HAVE_WO_TYPE.getCode(),ErrorBusinessMessages.SERVICE_TYPE_DOESNT_HAVE_WO_TYPE.getMessage());			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación validateServiceTypeByWoTypeId/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateServiceTypeByWoTypeId/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getWorkOrdersInfoByWoIds(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderTrayResponse getWorkOrdersInfoByWoIds(List<Long> woIds) throws BusinessException {
		log.debug("== Inicia getWorkOrdersInfoByWoIds/TrayWorkOrderManagmentBusinessBean ==");
		try{
			WorkOrderTrayResponse response = new WorkOrderTrayResponse();
			if( woIds != null && !woIds.isEmpty() ){
				/*List<WorkOrderTrayDTO> workOrderList = new ArrayList<WorkOrderTrayDTO>();
				for( Long woId  : woIds ){
					ADICIONA LA WO A LA LISTA DE RETORNO
					workOrderList.add( fillWorkOrderInformation(woId) );
				}
				response.setWorkOrderList(workOrderList);*/
				response.setWorkOrderList( this.fillWorkOrderInformationOptimized(woIds) );
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersInfoByWoIds/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersInfoByWoIds/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	

	private HashMap<Long, List<WorkOrderServiceVO>> getWorkOrderServicesByWorkOrderId(List<WorkOrderServiceVO> listServicesWos) throws BusinessException{
		log.debug("== Inicia getWorkOrderServicesByWorkOrderId/TrayWorkOrderManagmentBusinessBean ==");
		try {
			HashMap<Long, List<WorkOrderServiceVO>> hashServices = new HashMap<Long, List<WorkOrderServiceVO>>();
			if(listServicesWos.size()>0){
				for(WorkOrderServiceVO orderServiceVO: listServicesWos){
					List<WorkOrderServiceVO> listServicesWorkOrder = null;
					Long woId = orderServiceVO.getWoId();
					listServicesWorkOrder=  hashServices.get(woId);
					if(listServicesWorkOrder == null){
						listServicesWorkOrder = new ArrayList<WorkOrderServiceVO>();
						listServicesWorkOrder.add(orderServiceVO);
						hashServices.put(woId, listServicesWorkOrder);
					}else{
						hashServices.remove(woId);
						listServicesWorkOrder.add(orderServiceVO);
						hashServices.put(woId, listServicesWorkOrder);
					}
				}
					
					
			}
			return hashServices;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersInfoByWoIds/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderServicesByWorkOrderId/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	private int nombre (int... n_cantidad_de_parametros){ 
		 for (int numero : n_cantidad_de_parametros)
		      System.out.println(numero);
		 return 0;
	}
	
	/**
	 * 
	 * Metodo: Obtiene la informacion de una WO a partir de su ID optimizada
	 * @param woId
	 * @return WorkOrderTrayDTO Objeto con informacion de WO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private List<WorkOrderTrayDTO> fillWorkOrderInformationOptimized(List<Long> woIds, Long... idUsuario) throws BusinessException {
		log.debug("== Inicia getWorkOrderInformation/TrayWorkOrderManagmentBusinessBean ==");
		try {
			List<WorkOrderTrayDTO> dtos = new ArrayList<WorkOrderTrayDTO>();
			
			List<TechnologyVO> technologies = this.technologyBusinessBean.getAllIRDTechnologies();
	
			Iterator<Object[]> results = this.workOrderDAO.getWorkOrderByIDForTray(woIds, technologies, true, idUsuario).iterator();
		
			List<WorkOrderService> listServices = this.woServiceDAO.getWorkOrderServiceByWoIds(woIds);
			List<WorkOrderServiceVO> listWorkOrderServiceVOs = UtilsBusiness.convertList(listServices, WorkOrderServiceVO.class);
			
			HashMap<Long, List<WorkOrderServiceVO>> listHashServicesWos = this.getWorkOrderServicesByWorkOrderId(listWorkOrderServiceVOs);
			
			String tmp;
			while ( results.hasNext() ) {
				WorkOrderTrayDTO dto = new WorkOrderTrayDTO();
				Object[] row = (Object[]) results.next();
				Long woId = ( (BigDecimal) row[ TrayQueryEnum.WORK_ORDER_ID.getRowNum() ] ).longValue();
				dto.setWoId( woId );
				
				/*SECCION PARA DARLE VALOR A LOS SERVICIOS ASOCIADOS A LA WO*/
				dto.setServicesByWorkOrder(listHashServicesWos.get(woId));
				
				//dto.setServicesByWorkOrder( this.getWorkOrderServices(woId , null).getWorkOrderServicesVO() );
				
				/*AGREGA INFORMACION DE LA SHIPPING ORDER*/
				List<ShippingOrder> shippingOrderList = shippingOrderDAO.getShippingOrdersByWorkOrder(woId);
				/*if( shippingOrderList != null && !shippingOrderList.isEmpty() ){
					dto.setShippingOrderCode( shippingOrderList.get(0).getShippingOrderCode() != null ? shippingOrderList.get(0).getShippingOrderCode() : null );
					ShippingOrderVO vo = UtilsBusiness.copyObject(ShippingOrderVO.class, shippingOrderList.get(0));
					vo.setShippingOrderTechnologies( this.technologyBusinessBean.getShippingorderTechnology(vo.getId()) );
					List<ShippingOrderVO> soList = new ArrayList<ShippingOrderVO>();
					soList.add( vo );
					dto.setShippingOrderList(soList);
				}*/

				dto.setStateCity(row[TrayQueryEnum.STATE_NAME.getRowNum()]!=null?row[TrayQueryEnum.STATE_NAME.getRowNum()].toString():"");
				dto.setCity(row[TrayQueryEnum.CITY_NAME.getRowNum()]!=null?row[TrayQueryEnum.CITY_NAME.getRowNum()].toString():"");
				dto.setPerimeter(row[TrayQueryEnum.ZONE_TYPE_NAME.getRowNum()]!=null?row[TrayQueryEnum.ZONE_TYPE_NAME.getRowNum()].toString():"");
				
				dto.setCodeProductTechnology(row[TrayQueryEnum.TEC_CODE.getRowNum()]!=null?row[TrayQueryEnum.TEC_CODE.getRowNum()].toString():"");
				dto.setProductTechnology(row[TrayQueryEnum.TEC_NAME.getRowNum()]!=null?row[TrayQueryEnum.TEC_NAME.getRowNum()].toString():"");
				dto.setShippingOrderCode(row[TrayQueryEnum.SHIPPING_ORDER_CODE.getRowNum()]!=null?row[TrayQueryEnum.SHIPPING_ORDER_CODE.getRowNum()].toString():"");
				
				tmp = ""; 
				if(row[TrayQueryEnum.WORK_ORDER_CODE.getRowNum()]!=null){
					tmp = row[TrayQueryEnum.WORK_ORDER_CODE.getRowNum()].toString(); 
				} 
				dto.setWorkorderCode( tmp );					
				tmp = ""; 
				if(row[TrayQueryEnum.WORK_ORDER_DESCRIPTION.getRowNum()]!=null){ 
					tmp = row[TrayQueryEnum.WORK_ORDER_DESCRIPTION.getRowNum()].toString(); 
				} 
				dto.setWorkOrderDescription(tmp);			
				if(row[TrayQueryEnum.WORK_ORDER_CREATION_DATE.getRowNum()]!=null){
					dto.setCreationDate((Date) row[TrayQueryEnum.WORK_ORDER_CREATION_DATE.getRowNum()]);
				} 												
				WorkorderStatusVO workorderStatusVO = new WorkorderStatusVO();
				if(row[TrayQueryEnum.WORK_ORDER_STATUS_ID.getRowNum()]!=null 
						&& row[TrayQueryEnum.WORK_ORDER_STATUS_CODE.getRowNum()]!=null 
						&& !row[TrayQueryEnum.WORK_ORDER_STATUS_CODE.getRowNum()].toString().equals("")){
					workorderStatusVO.setId(((BigDecimal) row[TrayQueryEnum.WORK_ORDER_STATUS_ID.getRowNum()]).longValue());										
					workorderStatusVO.setWoStateCode(row[TrayQueryEnum.WORK_ORDER_STATUS_CODE.getRowNum()].toString());												
					tmp = ""; 
					if(row[TrayQueryEnum.WORK_ORDER_STATUS_NAME.getRowNum()]!=null){ 
						tmp = row[TrayQueryEnum.WORK_ORDER_STATUS_NAME.getRowNum()].toString(); 
					} 
					workorderStatusVO.setWoStateName(tmp);												
				}
				dto.setWorkorderStatusVO(workorderStatusVO);
				
				/*SECCION PARA DARLE VALOR A LA REASON DE LA WO EN CASO QUE ESTE EN ESTADO PENDIENTE*/
				if(row[TrayQueryEnum.WORK_ORDER_REASON_ID.getRowNum()]!=null 
						&& row[TrayQueryEnum.WORK_ORDER_REASON_CODE.getRowNum()]!=null 
						&& !row[TrayQueryEnum.WORK_ORDER_REASON_CODE.getRowNum()].toString().equals("")){
					WorkorderReasonVO workOrderReason = new WorkorderReasonVO();
					workOrderReason.setId(((BigDecimal) row[TrayQueryEnum.WORK_ORDER_REASON_ID.getRowNum()] ).longValue());										
					tmp = ""; 
					if (row[TrayQueryEnum.WORK_ORDER_REASON_CODE.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.WORK_ORDER_REASON_CODE.getRowNum()].toString(); 
					} 
					workOrderReason.setWorkorderReasonCode(tmp);			
					tmp = ""; 
					if (row[TrayQueryEnum.WORK_ORDER_REASON_NAME.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.WORK_ORDER_REASON_NAME.getRowNum()].toString();
					} 
					workOrderReason.setWorkorderReasonName(tmp);			
					dto.setWorkOrderReason(workOrderReason);
				}
				
				/*SECCION PARA DARLE VALOR AL CODIGO POSTAL*/
				if( row[TrayQueryEnum.POSTAL_CODE_ID_WO.getRowNum()]!=null 
						&& row[TrayQueryEnum.POSTAL_CODE_CODE_WO.getRowNum()]!=null 
						&& !row[TrayQueryEnum.POSTAL_CODE_CODE_WO.getRowNum()].toString().equals("")){
					PostalCodeVO postalCodeVO = new PostalCodeVO();
					postalCodeVO.setId(((BigDecimal) row[TrayQueryEnum.POSTAL_CODE_ID_WO.getRowNum()]).longValue());											
					postalCodeVO.setPostalCodeCode(row[TrayQueryEnum.POSTAL_CODE_CODE_WO.getRowNum()].toString());	
					tmp = ""; 
					if(row[TrayQueryEnum.POSTAL_CODE_NAME_WO.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.POSTAL_CODE_NAME_WO.getRowNum()].toString(); 
					} 
					postalCodeVO.setPostalCodeName(tmp);
					if(row[TrayQueryEnum.CITY_NAME_WO.getRowNum()] != null && !row[TrayQueryEnum.CITY_NAME_WO.getRowNum()].toString().equals("")){
						City city = new City();
						city.setCityName(row[TrayQueryEnum.CITY_NAME_WO.getRowNum()].toString());
						if(row[TrayQueryEnum.STATE_NAME_WO.getRowNum()] != null && !row[TrayQueryEnum.STATE_NAME_WO.getRowNum()].toString().equals("")){
							State state = new State();
							state.setStateName(row[TrayQueryEnum.STATE_NAME_WO.getRowNum()].toString());
							city.setState(state);
						}
						postalCodeVO.setCity( city );
					}
					dto.setPostalCode(postalCodeVO);
				}
				
				/*SECCION PARA AGREGAR EL NOMBRE DEL CLIENTE*/
				if(row[TrayQueryEnum.CUSTOMER_CODE.getRowNum()]!=null && !row[TrayQueryEnum.CUSTOMER_CODE.getRowNum()].toString().equals("")){
					CustomerDTO customer = new CustomerDTO();
					tmp = ""; 
					if(row[TrayQueryEnum.CUSTOMER_CODE.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.CUSTOMER_CODE.getRowNum()].toString();
					} 
					customer.setCustomerCode(tmp);
					tmp = ""; 
					if(row[TrayQueryEnum.CUSTOMER_FIRST_NAME.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.CUSTOMER_FIRST_NAME.getRowNum()].toString(); 
					} 
					customer.setFirstNames(tmp);
					tmp = ""; 
					if(row[TrayQueryEnum.CUSTOMER_LAST_NAME.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.CUSTOMER_LAST_NAME.getRowNum()].toString(); 
					} 
					customer.setLastNames(tmp);
					tmp = ""; 
					if(row[TrayQueryEnum.CUSTOMER_ADDRESS.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.CUSTOMER_ADDRESS.getRowNum()].toString(); 
					} 
					customer.setAddress(tmp);
					tmp = ""; 
					if(row[TrayQueryEnum.CUSTOMER_ADDRESS.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.CUSTOMER_ADDRESS.getRowNum()].toString(); 
					} 
					dto.setAddress(tmp);
					tmp = ""; 
					if(row[TrayQueryEnum.CUSTOMER_DOCUMENT_NUMBER.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.CUSTOMER_DOCUMENT_NUMBER.getRowNum()].toString(); 
					} 
					customer.setDocumentNumber(tmp);
					tmp = ""; if ( row[TrayQueryEnum.IS_CUSTOMER_MIGRATED.getRowNum()]!=null ){ tmp = row[TrayQueryEnum.IS_CUSTOMER_MIGRATED.getRowNum()].toString(); } customer.setIsMigrated( tmp );
					
					if(customer.getIsMigrated() != null && customer.getIsMigrated().equals(CodesBusinessEntityEnum.CUSTOMER_AGREEMENT_MIGRATED.getCodeEntity())){
						customer.setIsMigratedDescription(CustomerDTO.CUSTOMER_NAME_IS_MIGRATED);
					}else{
						customer.setIsMigratedDescription(CustomerDTO.CUSTOMER_NAME_IS_NOT_MIGRATED);
					}
					
					if(row[TrayQueryEnum.CUSTOMER_CLASS_TYPE_ID.getRowNum()]!=null 
							&& row[TrayQueryEnum.CUSTOMER_TYPE_CODE.getRowNum()]!=null 
							&& !row[TrayQueryEnum.CUSTOMER_CLASS_CODE.getRowNum()].toString().equals("")
							&& row[TrayQueryEnum.CUSTOMER_CLASS_ID.getRowNum()]!=null 
							&& row[TrayQueryEnum.CUSTOMER_TYPE_ID.getRowNum()]!=null 
							){
						CustomerClassTypeVO customerClassTypeVO = new CustomerClassTypeVO();
						customerClassTypeVO.setId(((BigDecimal) row[TrayQueryEnum.CUSTOMER_CLASS_TYPE_ID.getRowNum()]).longValue());
						customerClassTypeVO.setCustomerType(new CustomerType());
						customerClassTypeVO.getCustomerType().setId(((BigDecimal)row[TrayQueryEnum.CUSTOMER_TYPE_ID.getRowNum()]).longValue());
						customerClassTypeVO.getCustomerType().setCustomerTypeCode(row[TrayQueryEnum.CUSTOMER_TYPE_CODE.getRowNum()].toString());
						tmp = ""; 
						if (row[TrayQueryEnum.CUSTOMER_TYPE_NAME.getRowNum()]!=null){ 
							tmp = row[TrayQueryEnum.CUSTOMER_TYPE_NAME.getRowNum()].toString(); 
						} 
						customerClassTypeVO.getCustomerType().setCustomerTypeName(tmp);
						
						customerClassTypeVO.setCustomerClass(new CustomerClass());
						customerClassTypeVO.getCustomerClass().setId(((BigDecimal)row[TrayQueryEnum.CUSTOMER_CLASS_ID.getRowNum()]).longValue());
						customerClassTypeVO.getCustomerClass().setCustomerClassCode(row[TrayQueryEnum.CUSTOMER_CLASS_CODE.getRowNum()].toString());
						tmp = ""; 
						if (row[TrayQueryEnum.CUSTOMER_CLASS_NAME.getRowNum()]!=null){ 
							tmp = row[TrayQueryEnum.CUSTOMER_CLASS_NAME.getRowNum()].toString(); 
						} 
						customerClassTypeVO.getCustomerClass().setCustomerClassName(tmp);
						
						customerClassTypeVO.getCustomerClass().setCategory(new CustomerCategory());
						customerClassTypeVO.getCustomerClass().getCategory().setId(((BigDecimal)row[TrayQueryEnum.CUSTOMER_CATEGORY_ID.getRowNum()]).longValue());
						customerClassTypeVO.getCustomerClass().getCategory().setCode(row[TrayQueryEnum.CUSTOMER_CATEGORY_CODE.getRowNum()].toString());
						tmp = ""; 
						if (row[TrayQueryEnum.CUSTOMER_CATEGORY_NAME.getRowNum()]!=null){ 
							tmp = row[TrayQueryEnum.CUSTOMER_CATEGORY_NAME.getRowNum()].toString(); 
						} 
						customerClassTypeVO.getCustomerClass().getCategory().setName(tmp);
						
						customer.setType(customerClassTypeVO);
					}
					if(row[TrayQueryEnum.DOCUMENT_TYPE_ID.getRowNum()]!=null 
							&& row[TrayQueryEnum.DOCUMENT_TYPE_CODE.getRowNum()]!=null 
							&& !row[TrayQueryEnum.DOCUMENT_TYPE_CODE.getRowNum()].toString().equals("") ){
						DocumentTypeVO documentTypeVO = new DocumentTypeVO();
						documentTypeVO.setId(((BigDecimal) row[TrayQueryEnum.DOCUMENT_TYPE_ID.getRowNum()]).longValue());
						documentTypeVO.setDocumentTypeCode(row[TrayQueryEnum.DOCUMENT_TYPE_CODE.getRowNum()].toString());
						tmp = ""; 
						if(row[TrayQueryEnum.DOCUMENT_TYPE_NAME.getRowNum()]!=null){
							tmp = row[TrayQueryEnum.DOCUMENT_TYPE_NAME.getRowNum()].toString();
						}
						documentTypeVO.setDocumentTypeName(tmp);
						customer.setDocumentType(documentTypeVO);
					}
					if(row[TrayQueryEnum.CUSTOMER_ID.getRowNum()]!=null){
						Long customerId = ((BigDecimal)row[TrayQueryEnum.CUSTOMER_ID.getRowNum()]).longValue();
						customer.setCustomerMediaContacts(UtilsBusiness.convertList(customerMediaContactDAO.getCustomerMediaContactByCustomerId(customerId) , CustomerMediaContactVO.class) );
					}
					
					dto.setCustomerDTO(customer);
				}
				
				/*SECCION PARA DARLE VALOR A INFORMACION DE AGENDAMIENTO Y JORNADA*/
				if(row[TrayQueryEnum.AGENDATION_DATE.getRowNum()]!=null){
					dto.setAgendationDate( (Date)row[TrayQueryEnum.AGENDATION_DATE.getRowNum()] );
					if(row[TrayQueryEnum.SERVICE_HOUR_ID.getRowNum()]!=null){
						ServiceHourVO serviceHourVO = new ServiceHourVO();
						serviceHourVO.setId(((BigDecimal)row[TrayQueryEnum.SERVICE_HOUR_ID.getRowNum()]).longValue());
						tmp = ""; 
						if(row[TrayQueryEnum.SERVICE_HOUR_NAME.getRowNum()]!=null){ 
							tmp = row[TrayQueryEnum.SERVICE_HOUR_NAME.getRowNum()].toString(); 
						} 
						serviceHourVO.setServiceHoursName(tmp);
						dto.setServiceHourVO(serviceHourVO);
					}
				}
				
				/*SECCION PARA DARLE VALOR AL TECNICO RESPONSABLE DE LA CUADRILLA ASIGNADA A LA wo
				 *  O AL PROGRAMA ASIGNADO A LA WO*/
				if(row[TrayQueryEnum.CREW_ID.getRowNum()]!=null){
					dto.setCrewId(((BigDecimal) row[TrayQueryEnum.CREW_ID.getRowNum()]).longValue());
					tmp = ""; 
					if(row[TrayQueryEnum.RESPONSABLE_NAME.getRowNum()]!=null){ 
						tmp = row[TrayQueryEnum.RESPONSABLE_NAME.getRowNum()].toString(); 
					} 
					dto.setResponsableName(tmp);
					tmp = ""; 
					if(row[TrayQueryEnum.RESPONSABLE_DOCUMENT_NUMBER.getRowNum()]!=null){ 
						tmp = row[TrayQueryEnum.RESPONSABLE_DOCUMENT_NUMBER.getRowNum()].toString(); 
					} 
					dto.setResponsableDocumentNumber(tmp);
				} else if(row[TrayQueryEnum.PROGRAM_ASSIGNMENT.getRowNum()]!=null && !row[33].toString().equals("")){
					tmp = ""; 
					if(row[TrayQueryEnum.PROGRAM_ASSIGNMENT.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.PROGRAM_ASSIGNMENT.getRowNum()].toString(); 
					} 
					dto.setAssignment(tmp);
				}
				
				/*SECCION PARA ASIGNAR LOS DIAS DE LA WO*/
				if(row[TrayQueryEnum.WORK_ORDER_DAYS.getRowNum()]!=null){
					int dias = ( (BigDecimal) row[TrayQueryEnum.WORK_ORDER_DAYS.getRowNum()] ).intValue();
					dto.setWoDays( 0 );
					if(dias>0)
						dto.setWoDays( dias );
				}
					
				/*SECCION PARA DARLE VALOR AL TIPO DE WO*/
				if(row[TrayQueryEnum.WORK_ORDER_TYPE_ID.getRowNum()]!=null 
						&& row[TrayQueryEnum.WORK_ORDER_TYPE_CODE.getRowNum()]!=null 
						&& !row[TrayQueryEnum.WORK_ORDER_TYPE_CODE.getRowNum()].toString().equals("")){
					WoTypeVO woTypeVO = new WoTypeVO();
					woTypeVO.setId(((BigDecimal) row[TrayQueryEnum.WORK_ORDER_TYPE_ID.getRowNum()]).longValue());
					woTypeVO.setWoTypeCode(row[TrayQueryEnum.WORK_ORDER_TYPE_CODE.getRowNum()].toString());
					tmp = ""; 
					if(row[TrayQueryEnum.WORK_ORDER_TYPE_NAME.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.WORK_ORDER_TYPE_NAME.getRowNum()].toString(); 
					} 
					woTypeVO.setWoTypeName(tmp);
					dto.setWorkOrderTypeVO(woTypeVO);
				}
				
				/*SECCION PARA AGREGAR INFORMACION DEL DEALER ASIGNADO A LA WORKORDER*/
				if(row[TrayQueryEnum.DEALER_ID.getRowNum()]!=null 
						&& row[TrayQueryEnum.DEALER_CODE.getRowNum()]!=null){
					dto.setDealerId(((BigDecimal) row[TrayQueryEnum.DEALER_ID.getRowNum()]).longValue());
					dto.setDealerCode(((BigDecimal) row[TrayQueryEnum.DEALER_CODE.getRowNum()]).longValue());
					tmp = ""; 
					if(row[TrayQueryEnum.DEALER_NAME.getRowNum()]!=null){
						tmp = row[TrayQueryEnum.DEALER_NAME.getRowNum()].toString();
					} 
					dto.setDealerName(tmp);
				}
				
				/*ASIGNA EL VALOR DEL ORIGEN DEL PROCESO DE IMPORTACION*/
				if(row[TrayQueryEnum.PROCESS_STATUS_ID.getRowNum()]!=null){
					String processStatusCodeCode = ""; 
					if(row[TrayQueryEnum.PROCESS_STATUS_CODE.getRowNum()]!=null){
						processStatusCodeCode = row[TrayQueryEnum.PROCESS_STATUS_CODE.getRowNum()].toString();
					}
					String wpsDescription = ""; 
					if(row[TrayQueryEnum.WORK_ORDER_PROCESS_SOURCE_DESCRIPTION.getRowNum()]!=null){
						wpsDescription = row[TrayQueryEnum.WORK_ORDER_PROCESS_SOURCE_DESCRIPTION.getRowNum()].toString();
					} 
					if(!wpsDescription.equals("")){
						if(dto.getWorkorderStatusVO() != null && dto.getWorkorderStatusVO().getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity()) 
								&& processStatusCodeCode.equals(CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_CORRECT_FINISHED.getCodeEntity())){
							dto.setWoProcessDescription(false);
							dto.setWoProcessSourceDescription(wpsDescription);
						} else {
							dto.setWoProcessDescription(true);
							dto.setWoProcessSourceDescription(wpsDescription);
						}
					} else {
						dto.setWoProcessDescription( false );
						dto.setWoProcessSourceDescription( null );
					}
				}
			
				/*SE ASIGNA EL VALOR QUE DEFINE SI LA WO BAJO CON LA AGENDA VENCIDA*/
				if( row[TrayQueryEnum.WORK_ORDER_AGENDATION_EXPIRED.getRowNum()]!=null && row[TrayQueryEnum.WORK_ORDER_AGENDATION_EXPIRED.getRowNum()].toString().equals( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() ) )
					dto.setAgendationExpired( true );
				else
					dto.setAgendationExpired( false );
				
				tmp = ""; if (row[TrayQueryEnum.WORK_ORDER_MARKS.getRowNum()]!=null ){ tmp = row[TrayQueryEnum.WORK_ORDER_MARKS.getRowNum()].toString();} 	
				if(tmp!=null && !tmp.trim().equals("")){
						dto.setWorkOrderMarkDTO(buildWorkOrderMarkDTOs(tmp));
				}
				
			    /*SE ASIGNA EL OPTIMUS STATUS EN CASO DE QUE TENGA */
				if(row[TrayQueryEnum.OPTIMUS_STATUS_CODE.getRowNum()]!=null ||
						row[TrayQueryEnum.OPTIMUS_STATUS_DESCRIPTION.getRowNum()]!=null){
					OptimusStatusVO optimusStatus = new OptimusStatusVO();
					
					optimusStatus.setCode(row[TrayQueryEnum.OPTIMUS_STATUS_CODE.getRowNum()].toString());
					optimusStatus.setDescription(row[TrayQueryEnum.OPTIMUS_STATUS_DESCRIPTION.getRowNum()].toString());
					
					dto.setOptimusStatusVO(optimusStatus);
				}
				
				if(row[TrayQueryEnum.OPTIMUS_DECLINE_CODE.getRowNum()]!=null ||
						row[TrayQueryEnum.OPTIMUS_DECLINE_DESCRIPTION.getRowNum()]!=null){

					dto.setOptimusDeclineCode(row[TrayQueryEnum.OPTIMUS_DECLINE_CODE.getRowNum()].toString());
					dto.setOptimusDeclineDescription(row[TrayQueryEnum.OPTIMUS_DECLINE_DESCRIPTION.getRowNum()].toString());
				}
			
				dtos.add(dto);
			}
			return dtos;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrderInformation/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderInformation/TrayWorkOrderManagmentBusinessBean ==");
		}
	}	
	
	/**
	 * Metodo: permite partir una cadena y obtener una lista de WorkOrderMarkDTO ejemplo RC;Requiere contrato;#FF0033,RC;Requiere contrato;#FF0033
	 * @return <tipo> <descripcion>
	 * @author
	 */
	private List<WorkOrderMarkDTO> buildWorkOrderMarkDTOs(String workOrderMarkConc){
		List<WorkOrderMarkDTO> workOrderMarkDTOS = new ArrayList<WorkOrderMarkDTO>();
		
		String [] strWorkOrderMark = workOrderMarkConc.split(",");
		for (String string : strWorkOrderMark) {
			WorkOrderMarkDTO workOrderMarkDTO = new WorkOrderMarkDTO();
			String [] strWorkOrderMarkField = string.split(";");
			if(strWorkOrderMarkField!=null && strWorkOrderMarkField.length>=3){
				workOrderMarkDTO.setCode(strWorkOrderMarkField[0]);
				workOrderMarkDTO.setName(strWorkOrderMarkField[1]);
				workOrderMarkDTO.setColor(strWorkOrderMarkField[2]);
				
				workOrderMarkDTOS.add(workOrderMarkDTO);
			}
		}
		
		
		return workOrderMarkDTOS;
	}
	
	
	/**
	 * 
	 * Metodo: Obtiene la informacion de una WO a partir de su ID
	 * @param woId
	 * @return WorkOrderTrayDTO Objeto con informacion de WO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private WorkOrderTrayDTO fillWorkOrderInformationn(Long woId) throws BusinessException {
		log.debug("== Inicia getWorkOrderInformation/TrayWorkOrderManagmentBusinessBean ==");
		try {
			WorkOrderTrayDTO dto = new WorkOrderTrayDTO();
			
			/*SECCION PARA DARLE VALOR A LOS SERVICIOS ASOCIADOS A LA WO*/
			dto.setServicesByWorkOrder( this.getWorkOrderServices(woId , null).getWorkOrderServicesVO() );
			
			/*SECCION PARA DARLE VALOR A LOS CAMPOS PROVENIENTES DE LA TABLA WORK_ORDERS*/
			WorkOrder workOrder = workOrderDAO.getWorkOrderByID( woId );
			if (workOrder.getDealerId() != null) {
				Dealer woDealer = dealersDAO.getDealerByID( workOrder.getDealerId() );
				if( woDealer != null ){
					dto.setWorkOrderDealerCode( woDealer.getDealerCode() );
					dto.setWorkOrderDealerName( woDealer.getDealerName() );
				}				
			}
			if(workOrder!=null 
					&& workOrder.getCustomerAddress()!=null 
					&& workOrder.getCustomerAddress().getPostalCode()!=null){
				dto.setCity(workOrder.getCustomerAddress().getPostalCode().getCity().getCityName());
				dto.setStateCity(workOrder.getCustomerAddress().getPostalCode().getCity().getState().getStateName());
				dto.setPerimeter(workOrder.getCustomerAddress().getPostalCode().getZoneType().getZoneTypeName());
			}
			
			dto.setWorkorderCode( workOrder.getWoCode() );
			dto.setWorkOrderDescription( workOrder.getWoDescription() );
			dto.setCreationDate( workOrder.getCreationDate() );
			dto.setWorkorderStatusVO( UtilsBusiness.copyObject(WorkorderStatusVO.class, workOrder.getWorkorderStatusByActualStatusId()) );
			dto.setWoId( woId );
			
			/*SECCION PARA DARLE VALOR A LA REASON DE LA WO EN CASO QUE ESTE EN ESTADO PENDIENTE*/
			List<Long> woStatusIds = new ArrayList<Long>();
			woStatusIds.add( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getIdEntity( WorkorderStatus.class.getName() ) );
			woStatusIds.add( CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getIdEntity( WorkorderStatus.class.getName() ) );
			dto.setWorkOrderReason( this.fillWoReason( woId, woStatusIds ) );
			
			/*SECCION PARA DARLE VALOR AL CODIGO POSTAL Y LA DIRECCION DE LA WO*/
			dto.setPostalCode( UtilsBusiness.copyObject(PostalCodeVO.class, workOrder.getPostalCode()) );
			dto.setAddress( workOrder.getCustomer().getCustomeraddress() );
						
			/*SECCION PARA DARLE VALOR A INFORMACION DE AGENDAMIENTO Y JORNADA*/
			WorkOrderAgenda workOrderagenda = workOrderAgendaDAO.getLastWorkOrderAgendaByWoID(woId);
			if( workOrderagenda != null ){
				dto.setAgendationDate( workOrderagenda.getAgendationDate() != null ? workOrderagenda.getAgendationDate() : null );
				if( workOrderagenda.getServiceHour() != null )
					dto.setServiceHourVO( UtilsBusiness.copyObject(ServiceHourVO.class, workOrderagenda.getServiceHour() ) );
			}
			
			/*SECCION PARA DARLE VALOR AL TECNICO RESPONSABLE DE LA CUADRILLA ASIGNADA A LA wo
			 *  O AL PROGRAMA ASIGNADO A LA WO*/
			WoAssignment woCrewAssigment = woAssignmentDAO.getWorkOrdersCrewActiveAssignment( woId );
			if( woCrewAssigment != null ){
				if(woCrewAssigment.getDealerAssignmentDate()!=null){
					dto.setWoDays( UtilsBusiness.getDaysBetween(UtilsBusiness.fechaActual(), woCrewAssigment.getDealerAssignmentDate()) );
				}else{
					dto.setWoDays(0);
				}
				Employee employee = employeesCrewDAO.getEmployeeResponsibleByCrewId( woCrewAssigment.getCrewId() );
				if( employee != null ) {
					dto.setResponsableName( employee.getFirstName() +" "+ employee.getLastName() );
					dto.setCrewId( woCrewAssigment.getCrewId() != null ? woCrewAssigment.getCrewId() : null );
					dto.setResponsableDocumentNumber( employee.getDocumentNumber() );
				}
					
			} else{
				WoAssignment woProgramAssigment = woAssignmentDAO.getWorkOrdersProgramActiveAssignment(woId);
				if( woProgramAssigment != null ){
					/*SECCION PARA ASIGNAR LOS DIAS DE LA WO*/	
					if(woProgramAssigment.getDealerAssignmentDate()!=null){
						dto.setWoDays( UtilsBusiness.getDaysBetween(UtilsBusiness.fechaActual(), woProgramAssigment.getDealerAssignmentDate()) );
					}else{
						dto.setWoDays(0);
					}
					dto.setAssignment( woProgramAssigment.getProgram() != null && woProgramAssigment.getProgram().getProgramName() != null ? woProgramAssigment.getProgram().getProgramName() : null );
				}
			}
			

			
			
			/*AGREGA INFORMACION DE LA SHIPPING ORDER*/
			List<ShippingOrder> shippingOrderList = shippingOrderDAO.getShippingOrdersByWorkOrder(woId);
			if( shippingOrderList != null && !shippingOrderList.isEmpty() ){
				dto.setShippingOrderCode( shippingOrderList.get(0).getShippingOrderCode() != null ? shippingOrderList.get(0).getShippingOrderCode() : null );
				ShippingOrderVO vo = UtilsBusiness.copyObject(ShippingOrderVO.class, shippingOrderList.get(0));
				vo.setShippingOrderTechnologies( this.technologyBusinessBean.getShippingorderTechnology(vo.getId()) );
				List<ShippingOrderVO> soList = new ArrayList<ShippingOrderVO>();
				soList.add( vo );
				dto.setShippingOrderList(soList);
			}
				
			
			/*SECCION PARA DARLE VALOR AL TIPO DE WO*/
			dto.setWorkOrderTypeVO( UtilsBusiness.copyObject(WoTypeVO.class, workOrder.getWoTypeByWoTypeId()) );
			
			/*SECCION PARA AGREGAR EL NOMBRE DEL CLIENTE*/
			if( workOrder.getCustomer() != null ){
				CustomerDTO customer = new CustomerDTO();
				customer.setFirstNames( workOrder.getCustomer().getFirstName() != null ? workOrder.getCustomer().getFirstName() : null );
				customer.setLastNames( workOrder.getCustomer().getLastName() != null ? workOrder.getCustomer().getLastName() : null );
				customer.setAddress( workOrder.getCustomer().getCustomeraddress() != null ? workOrder.getCustomer().getCustomeraddress() : null );
				customer.setCustomerCode( workOrder.getCustomer().getCustomerCode() != null ? workOrder.getCustomer().getCustomerCode() : null );
				customer.setDocumentNumber( workOrder.getCustomer().getDocumentNumber() != null ? workOrder.getCustomer().getDocumentNumber() : null );
				customer.setType( UtilsBusiness.copyObject( CustomerClassTypeVO.class , workOrder.getCustomer().getCustType() ) );
				customer.setDocumentType( documentTypesCRUD.getDocumentTypesByID( workOrder.getCustomer().getDocumentTypeId() ) );
				customer.setCustomerMediaContacts( UtilsBusiness.convertList( customerMediaContactDAO.getCustomerMediaContactByCustomerId( workOrder.getCustomer().getId() ) , CustomerMediaContactVO.class) );
				customer.setIsMigrated(workOrder.getCustomer().getIsMigrated());
				if(customer.getIsMigrated() != null && customer.getIsMigrated().equals(CodesBusinessEntityEnum.CUSTOMER_AGREEMENT_MIGRATED.getCodeEntity())){
					customer.setIsMigratedDescription(CustomerDTO.CUSTOMER_NAME_IS_MIGRATED);
				}else{
					customer.setIsMigratedDescription(CustomerDTO.CUSTOMER_NAME_IS_NOT_MIGRATED);
				}
				dto.setCustomerDTO(customer);
			}
			
			/*SECCION PARA AGREGAR INFORMACION DEL DEALER ASIGNADO A LA WORKORDER*/
			WoAssignment woAssignment= woAssignmentDAO.getLastDealerAssignmentByWoId(woId);
			if(woAssignment != null && woAssignment.getDealerId() != null){
				Dealer dealer = dealersDAO.getDealerByID( woAssignment.getDealerId() );
				if(dealer != null){
					dto.setDealerId(dealer.getId());
					dto.setDealerCode(dealer.getDealerCode());
					dto.setDealerName(dealer.getDealerName());
				}
			}
			
			/*ASIGNA EL VALOR DEL ORIGEN DEL PROCESO DE IMPORTACION*/
			//release_correctiva_4.1.4_IN336380_Icono de Agendamiento en Linea 
			if( 	   workOrder.getProcessSourceId() != null  //existen wo con ProcessSourceId() == null
					&& workOrder.getProcessSourceId().longValue() > 0 
			  ){
				//dto es WorkOrderTrayDTO
				WoProcessSource wps = this.workorderStatusDAO.getWoProcessSourceByProcessId( workOrder.getProcessSourceId() );
				
				if( wps != null ){
					
					//Valida si la WO se encuentra agendada y fue agendada por el asignador
					if(        dto.getWorkorderStatusVO() != null 
							&& dto.getWorkorderStatusVO().getWoStateCode().equals( CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity() )
							//sdii_CODE_workorder_status_agendada=AG
							&& workOrder.getProcessStatus() != null 
							&& workOrder.getProcessStatus().getStatusCode().equals( CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_CORRECT_FINISHED.getCodeEntity() )
							//sdii_CODE_allocator_process_status_correct_finished=TC
							
							//imgAttentionRed--> ViewProcessIBS (true-> rojo) -> El agendamiento por CSR  + ok ejecución Asignador Automatico
						){
							dto.setWoProcessDescription(false); 					     
							dto.setWoProcessSourceDescription( wps.getProcessSourceDescription() );
					} else {
						dto.setWoProcessDescription(true); //imgAttentionYellow-> woProcessDescriptionField (true-> amarillo) -> “Estado agendada generado en IBS”
						dto.setWoProcessSourceDescription( wps.getProcessSourceDescription() );
					}
					
				} else{
					dto.setWoProcessDescription( false );    //imgAttentionYellow-> woProcessDescriptionField (true-> amarillo) -> “Estado agendada generado en IBS”
					dto.setWoProcessSourceDescription( null );
				}
			}
			
			/*SE ASIGNA EL VALOR QUE DEFINE SI LA WO BAJO CON LA AGENDA VENCIDA*/
			if( workOrder.getAgendationExpired().equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() ) )
				dto.setAgendationExpired( true );
			else
				dto.setAgendationExpired( false );
			
			dto.setWorkOrderMarkDTO(workOrderMarkBusinessBean.getWorkOrderMarkDTOIsActiveByWoId(woId));
			
			if(workOrder!=null
					&& workOrder.getCustomer()!=null
					&& workOrder.getCustomer().getCustomerAddresses()!=null
					&& !workOrder.getCustomer().getCustomerAddresses().isEmpty()){
				boolean haveDefault=false;
				for(CustomerAddresses ca : workOrder.getCustomer().getCustomerAddresses()){
					if(ca.getAddressType().getCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODE_ADDRESS_TYPE_DEFAULT.getCodeEntity())){
						if(dto.getCustomerDTO()==null){
							dto.setCustomerDTO(new CustomerDTO());
							dto.getCustomerDTO().setCustomerId(workOrder.getCustomer().getId());
						}
						dto.getCustomerDTO().setCity(ca.getPostalCode().getCity().getCityName());
						dto.getCustomerDTO().setStateCity(ca.getPostalCode().getCity().getState().getStateName());
						dto.getCustomerDTO().setPerimeter(ca.getPostalCode().getZoneType().getZoneTypeName());
						haveDefault=true;
						break;
					}
				}
				if(!haveDefault){
					for(CustomerAddresses ca : workOrder.getCustomer().getCustomerAddresses()){
						dto.getCustomerDTO().setCity(ca.getPostalCode().getCity().getCityName());
						dto.getCustomerDTO().setStateCity(ca.getPostalCode().getCity().getState().getStateName());
						dto.getCustomerDTO().setPerimeter(ca.getPostalCode().getZoneType().getZoneTypeName());
						break;
					}
				}
			}
			
			return dto;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrderInformation/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrderInformation/TrayWorkOrderManagmentBusinessBean ==");
		}
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getAllExpirationGroupingVO(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ExpirationGroupingVO> getAllExpirationGroupingVO(WorkOrderFilterTrayDTO filter) throws BusinessException {
		log.debug("== Inicia getAllExpirationGroupingVO/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( filter == null || filter.getCountryId() == null || filter.getCountryId() <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			List<ExpirationGroupingVO> response = null;
			List<ExpirationGrouping> pojoList = expirationGroupingDAO.getAllExpirationGrouping();
			if( pojoList != null && !pojoList.isEmpty() ){
				response = new ArrayList<ExpirationGroupingVO>();
				boolean isDealerId = ( filter.getDealersIds() == null || filter.getDealersIds().isEmpty() ) ? false : true;
				this.validateStatusDealers(filter.getDealersIds());
				this.fillDealerAndBranchIds(filter);
				
				
				for( ExpirationGrouping pojo : pojoList ){
					Calendar calInit = Calendar.getInstance();
					Calendar calEnd = Calendar.getInstance();
 					ExpirationGroupingVO vo = UtilsBusiness.copyObject( ExpirationGroupingVO.class , pojo);
					filter.setExpirationGroupingInit( null );
					filter.setExpirationGroupingEnd( null );
					filter.setWoStatusIds( null );
					if( pojo.getExpirationGroupingInitValue() != null && pojo.getExpirationGroupingEndValue() != null ){
						if( vo.getExpirationGroupingInitValue().intValue() < 0 ){
							calInit.add(Calendar.DAY_OF_MONTH, vo.getExpirationGroupingEndValue().intValue());
							calEnd.add(Calendar.DAY_OF_MONTH, vo.getExpirationGroupingInitValue().intValue());
						} else {
							calInit.add(Calendar.DAY_OF_MONTH, vo.getExpirationGroupingInitValue().intValue());	
							calEnd.add(Calendar.DAY_OF_MONTH, vo.getExpirationGroupingEndValue().intValue());
						}
						
					} else {
						if( pojo.getExpirationGroupingCode().equals(CodesBusinessEntityEnum.EXPIRATION_GROUPING_WITHOUT.getCodeEntity() ) ){
							calInit.set(Calendar.YEAR, 1901);							
							calEnd.set(Calendar.YEAR, 1901);
						} else if( pojo.getExpirationGroupingCode().equals(CodesBusinessEntityEnum.EXPIRATION_GROUPING_ALL.getCodeEntity() ) ){
							calInit.set(Calendar.YEAR, 1900);
							calEnd.set(Calendar.YEAR, 1900);
						}
					}
					vo.setExpirationInitDate( calInit.getTime() );
					vo.setExpirationEndDate( calEnd.getTime() );
					filter.setExpirationGroupingInit( vo.getExpirationInitDate() );		
					filter.setExpirationGroupingEnd( vo.getExpirationEndDate() );
					vo.setWoStatusIds( getWoStatusByExpirationGroupingCode( pojo.getExpirationGroupingCode() , isDealerId ) );
					filter.setWoStatusIds( vo.getWoStatusIds() );
					vo.setWoTotal( workOrderDAO.countWoForExpirationGrouping(filter) );
					response.add( vo );
				}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllExpirationGroupingVO/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllExpirationGroupingVO/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de validar una lista de dealers
	 * con el fin que todos esten en estado Normal (Principal y sucursal)
	 * @param delaerIds
	 * @throws BusinessException
	 * @author waguilera
	 */
	private void validateStatusDealers(List<Long> dealerIds) throws BusinessException{
		log.debug("== Termina validateStatusDealers/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if(dealerIds !=null){
				for(Long idDealer: dealerIds){
					Dealer dealer = this.dealersDAO.getDealerByID(idDealer);
					if(dealer !=null){
						if(!dealer.getDealerStatus().getStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity())){
							List<String> params = new ArrayList<String>();
							if(dealer.getDealer()==null){
								params.add(CodesBusinessEntityEnum.NAME_TYPE_DEALER_PRINCIPAL.getCodeEntity());
							}else{
								params.add(CodesBusinessEntityEnum.NAME_TYPE_DEALER_BRANCH.getCodeEntity());
							}
							params.add(dealer.getDealerName());
							throw new BusinessException(ErrorBusinessMessages.CORE_CR063.getCode(), 
									ErrorBusinessMessages.CORE_CR063.getMessage(), params);
						}else if(dealer.getDealer()!=null){
							if(!dealer.getDealer().getDealerStatus().getStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity())){
								List<String> params = new ArrayList<String>();
								params.add(CodesBusinessEntityEnum.NAME_TYPE_DEALER_PRINCIPAL.getCodeEntity());
								params.add(dealer.getDealer().getDealerName());
								throw new BusinessException(ErrorBusinessMessages.CORE_CR063.getCode(), 
										ErrorBusinessMessages.CORE_CR063.getMessage(), params);
							}
						}
					}
				}
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación validateStatusDealers/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateStatusDealers/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Obtiene los ids de los estados permitidos a partir de la agrupacion por vencimiento
	 * @param expirationGroupingCode Codigo de la agrupacion por vencimiento
	 * @param isDealer indica si el conteo se esta invocanco por un sueprusuario o por un dealer
	 * @return Lista de ids de estados
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private List<Long> getWoStatusByExpirationGroupingCode( String expirationGroupingCode , boolean isDealer) throws BusinessException{
		log.debug("== Inicia getWoStatusByExpirationGroupingCode/TrayWorkOrderManagmentBusinessBean ==");
		try{
			List<Long> woStatusIds = new ArrayList<Long>();
			WorkorderStatus asignStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity() );
			WorkorderStatus reAsignStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity() );
			WorkorderStatus scheduleStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity() );
			WorkorderStatus reScheduleStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity() );
			WorkorderStatus pendingStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity() );
			if( !isDealer ){
				WorkorderStatus rejectedStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity() );
				WorkorderStatus activeStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity() );
				if( rejectedStatus != null )
					woStatusIds.add( rejectedStatus.getId() );
				if( activeStatus != null )
					woStatusIds.add( activeStatus.getId() );
			}
			
			if( asignStatus != null )
				woStatusIds.add( asignStatus.getId() );
			if( reAsignStatus != null )
				woStatusIds.add( reAsignStatus.getId() );
			if( scheduleStatus != null )
				woStatusIds.add( scheduleStatus.getId() );
			if( reScheduleStatus != null )
				woStatusIds.add( reScheduleStatus.getId() );
			if( pendingStatus != null )
				woStatusIds.add( pendingStatus.getId() );
			return woStatusIds;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWoStatusByExpirationGroupingCode/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWoStatusByExpirationGroupingCode/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getAllResponsableEmployeeCrewByCountryAndDealerId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerId(Long countryId, Long dealerId) throws BusinessException {
		log.debug("== Inicia getAllResponsableEmployeeCrewByCountryAndDealerId/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( countryId == null || countryId.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			List<EmployeeCrew> pojoList = employeesCrewDAO.getAllResponsableEmployeeCrewByCountryAndDealerId(countryId, dealerId);
			List<EmployeeCrewDTO> response = null;
			if( pojoList != null && !pojoList.isEmpty() ){
				response = new ArrayList<EmployeeCrewDTO>();
				for( EmployeeCrew pojo : pojoList ){
					EmployeeCrewDTO dto = new EmployeeCrewDTO();
					dto.setEmployeeId( pojo.getEmployee().getId() );
					dto.setEmployeeFirstName( pojo.getEmployee().getFirstName() != null ? pojo.getEmployee().getFirstName() : null );
					dto.setEmployeeLastName( pojo.getEmployee().getLastName() != null ? pojo.getEmployee().getLastName() : null );
					dto.setCrewId( pojo.getCrew().getId() );
					response.add( dto );
				}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllResponsableEmployeeCrewByCountryAndDealerId/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllResponsableEmployeeCrewByCountryAndDealerId/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getAllServiceCategories()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceCategoryVO> getAllServiceCategories() throws BusinessException{
		log.debug("== Inicia getAllServiceCategories/TrayWorkOrderManagmentBusinessBean ==");
		try{
			return UtilsBusiness.convertList(serviceCategoryDAO.getAll(), ServiceCategoryVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllServiceCategories/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllServiceCategories/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getActiveServicesByServiceCategories(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceVO> getActiveServicesByServiceCategories(
			List<Long> categoriesId) throws BusinessException {
		log.debug("== Inicia getActiveServicesByServiceCategories/TrayWorkOrderManagmentBusinessBean ==");
		try{
			ServiceStatus activeServiceStatus = serviceStatusDAO.getServiceStatusByCode(CodesBusinessEntityEnum.CODIGO_SERVICE_STATUS_ACTIVE.getCodeEntity());
        	UtilsBusiness.assertNotNull(activeServiceStatus, ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage());
			return UtilsBusiness.convertList(serviceDAO.getActiveServicesByServiceCategories(categoriesId,activeServiceStatus), ServiceVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getActiveServicesByServiceCategories/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getActiveServicesByServiceCategories/TrayWorkOrderManagmentBusinessBean ==");
		}		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getServiceCategoryByTypesId(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceCategoryVO> getServiceCategoryByTypesId(
			List<Long> typesId) throws BusinessException {
		log.debug("== Inicia getServiceCategoryByTypesId/TrayWorkOrderManagmentBusinessBean ==");
		try{
			return UtilsBusiness.convertList(serviceCategoryDAO.getServiceCategoryByTypesId(typesId), ServiceCategoryVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getServiceCategoryByTypesId/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getServiceCategoryByTypesId/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getAllWorkorderTypes()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WoTypeVO> getAllWorkorderTypes() throws BusinessException {
		log.debug("== Inicia getAllWorkorderTypes/TrayWorkOrderManagmentBusinessBean ==");
		try{
			return UtilsBusiness.convertList(woTypeDAO.getAll(), WoTypeVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllWorkorderTypes/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllWorkorderTypes/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getActiveProgramsByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProgramVO> getActiveProgramsByDealerId(Long dealerId) throws BusinessException {
		log.debug("== Inicia getActiveProgramsByDealerId/TrayWorkOrderManagmentBusinessBean ==");
		try{
			return UtilsBusiness.convertList(programDAO.getProgramsByDealerIdAndStatusCode(dealerId,CodesBusinessEntityEnum.PROGRAM_STATUS_ACTIVE.getCodeEntity()), ProgramVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getActiveProgramsByDealerId/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getActiveProgramsByDealerId/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getSaleDealersByWoAsignDealerIdAndCountry(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getSaleDealersByWoAsignDealerIdAndCountry(Long dealerId, Long countryId) throws BusinessException {
		log.debug("== Inicia getSaleDealersByWoAsignDealerIdAndCountry/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( countryId == null || countryId.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			List<Dealer> pojoList = workOrderDAO.getSaleDealersByWoAsignDealerIdAndCountry(dealerId, countryId);
			List<DealerVO> response = null;
			if( pojoList != null && !pojoList.isEmpty() ){
				response = new ArrayList<DealerVO>();
				for( Dealer pojo : pojoList ){
					DealerVO vo = UtilsBusiness.copyObject(DealerVO.class, pojo);
					vo.setChannelType(null);
					vo.setPostalCode(null);
					vo.setDealerType(null);
					vo.setDealerStatus(null);
					vo.setDealer(null);
					response.add(vo);
				}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getSaleDealersByWoAsignDealerIdAndCountry/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getSaleDealersByWoAsignDealerIdAndCountry/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
		
	/**
	 * 
	 * Metodo: Se carga el objeto para el proceso de atencion 
	 * con la informacion que llega por referencia
	 * @param request TrayWOManagmentDTO
	 * @return WOAttentionsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public WOAttentionsRequestDTO populateObjectAttentionFinalization(TrayWOManagmentDTO request) throws BusinessException {
		log.debug("== Inicia populateObjectAttention/TrayWorkOrderManagmentBusinessBean ==");
		
		WOAttentionsRequestDTO woAttentionDTO = new WOAttentionsRequestDTO();
		try {

			//Se llena la informacion de la WO
			WorkOrderVO workorderVo = new WorkOrderVO();
			workorderVo.setId( request.getWorkOrderId() );
			woAttentionDTO.setWorkorderVo( workorderVo );
			woAttentionDTO.setDealerId( request.getDealerId() );
			woAttentionDTO.setUserId( request.getUserId() );
			woAttentionDTO.setWorkOrderFinished( request.getWorkOrderFinished() );
			woAttentionDTO.setCountryCode( request.getCountryCode() );
			woAttentionDTO.setDealerCode( request.getDealerCode() );
			//Se llena la informacion de los servicios
			List<WorkOrderServiceVO> workorderServices = null;
			workorderServices = new ArrayList<WorkOrderServiceVO>();
			for (TrayWorkOrderServiceDTO woServiceDTO : request.getTrayWOServiceDTO()) {				
				workorderServices.add( this.populateObjectWorkOrderService(woServiceDTO) );				
			}
			woAttentionDTO.setWorkorderServices(workorderServices);
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación populateObjectAttention/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina populateObjectAttention/TrayWorkOrderManagmentBusinessBean ==");
		}
		return woAttentionDTO;
	}

	/**
	 * 
	 * Metodo: Pobla los elementos para la finalizacion
	 * para el proceso de Atencion y Finalizacion
	 * @param woAttentionEndDTO
	 * @return TrayWOManagmentDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public WorkOrderServiceVO populateObjectWorkOrderService (TrayWorkOrderServiceDTO woServiceDTO) throws BusinessException {
		log.debug("== Inicia populateObjectWorkOrderService/TrayWorkOrderManagmentBusinessBean ==");
		WorkOrderServiceVO woService = null;
		try{					
			Service service = null;			
				
			//informacion del servicio				
			woService = new WorkOrderServiceVO();
			service = new Service();
			service.setId(woServiceDTO.getTrayServiceDTO().getServiceId());
			service.setServiceCode(woServiceDTO.getTrayServiceDTO().getServiceCode());
			woService.setId(woServiceDTO.getWorkOrderServiceId());
			woService.setService(service);
			woService.setAddService( woServiceDTO.getAddService() );
			
			//informacion de los elementos empleados en el servicio				
			woService.setWoServiceElements( woServiceDTO.getWoServiceElements() );
			woService.setWoServiceElementsRecovery( woServiceDTO.getWoServiceElementsRecovery() );						
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación populateObjectWorkOrderService/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina populateObjectWorkOrderService/TrayWorkOrderManagmentBusinessBean ==");
		}
		return woService;
	}
	
	/**
	 * 
	 * Metodo: Elmina los elementos serialziados o no serializados dependiendo del parametro para atender o finalizar
	 * @param woAttentionDTO
	 * @param isSerialized
	 * @return WOAttentionsRequestDTO
	 * @author jnova
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 */
	public WOAttentionsRequestDTO getElementsBySerializedOrNotSerialized(WOAttentionsRequestDTO woAttentionDTO,boolean isSerialized) throws PropertiesException, BusinessException{
		//define si va a tener en cuenta los serializados o los no serializados
		String isElementSerialized = isSerialized ? CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity() : CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity();
		
		WOAttentionsRequestDTO attentionDTO = new WOAttentionsRequestDTO();
		attentionDTO.setWorkorderVo( UtilsBusiness.copyObject( WorkOrderVO.class , woAttentionDTO.getWorkorderVo()) );
		if( woAttentionDTO.getWorkorderServices() != null ){
			List<WorkOrderServiceVO> newWoServices = new ArrayList<WorkOrderServiceVO>();
			
			for (WorkOrderServiceVO woService : woAttentionDTO.getWorkorderServices()) {
				WorkOrderServiceVO newWoService = new WorkOrderServiceVO();
				List<WorkOrderServiceElementVO> elements = new ArrayList<WorkOrderServiceElementVO>();
				//verifica que en los elementos venga el elemento que serializado o no y lo agrega a la nueva lista
				if( woService.getWoServiceElements() != null ){
					for (WorkOrderServiceElementVO serviceElements : woService.getWoServiceElements()) {
						if( serviceElements.getIsSerialized().equalsIgnoreCase( isElementSerialized ) ){
							elements.add( serviceElements );
						}
					}
					
					if(elements != null && !elements.isEmpty()){
						newWoService.setWoServiceElements(elements);
					}
				}
				//verifica que en los elementos de recovery venga el elemento que serializado o no y lo agrega a la nueva lista
				if( woService.getWoServiceElementsRecovery() != null ){
					for (WorkOrderServiceElementVO serviceElements : woService.getWoServiceElementsRecovery()) {
						if( serviceElements.getIsSerialized().equalsIgnoreCase( isElementSerialized ) ){
							elements.add( serviceElements );
						}
					}
					
					if(elements != null && !elements.isEmpty()){
						newWoService.setWoServiceElements(elements);
					}		
				}
				
				if(newWoService.getWoServiceElements() != null && !newWoService.getWoServiceElements().isEmpty()){
					newWoService.setWoServiceElements(elements);
					newWoServices.add( newWoService );
				}
			}			
			
			if( newWoServices != null && !newWoServices.isEmpty() ){
				attentionDTO.setWorkorderServices(newWoServices);
			}
		}
		return attentionDTO;
	}

	/**
	 * 
	 * Metodo: pobla el objeto de inventarios para
	 * la interaccion con la interfaz
	 * @param request TrayWOManagmentDTO
	 * @param attentionElementsList List<WOAttentionElementsRequestDTO>
	 * @return InventoryDTO
	 * @author jalopez
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public InventoryDTO populateObjectInventoryAttention(TrayWOManagmentDTO request, List<WOAttentionElementsRequestDTO> attentionElementsList, Service service, TrayWorkOrderServiceDTO woServiceDTO) throws PropertiesException, BusinessException, DAOServiceException, DAOSQLException{
		
		InventoryDTO inventoryDTO = new InventoryDTO();
		inventoryDTO.setUserId( request.getUserId() );
		DealerVO dealerVO = new DealerVO();
		CustomerVO customerVO = new CustomerVO();		
		dealerVO.setDealerCode( request.getDealerCode() );
		dealerVO.setDepotCode( request.getDepotCode() );
		customerVO.setCustomerCode( request.getCustomerCode() );
		inventoryDTO.setAttentionElementsList( attentionElementsList );
		inventoryDTO.setDealer( dealerVO );
		inventoryDTO.setCountryCode( request.getCountryCode() );
		inventoryDTO.setDocumentResponsibleCrew( request.getDocumentResponsibleCrew() );
		inventoryDTO.setCustomer( customerVO );
		WorkOrderVO woVO = new WorkOrderVO();
		woVO.setId( request.getWorkOrderId() );
		inventoryDTO.setWorkOrderVO( woVO );
		inventoryDTO.setWoService( service );
		
		inventoryDTO.setWorkOrderServiceId(woServiceDTO.getWorkOrderServiceId());
		
		//Se realiza la consulta del parametro del sistema que define los servicios de inventarios que se deben invocar,
		//debido a que la aplicacion puede ser conectada a los servicios de SmartDealerI o los propios de HSP.
		SystemParameter invokeInternalInventory = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_INVOKE_HSP_INVENTORY_SERVICE.getCodeEntity(), request.getCountryId());
		if (invokeInternalInventory == null) {
			log.fatal("No se ha encontrado el parametro del sistema INVOKE_HSP_INVENTORY_SERVICE");
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		inventoryDTO.setInvokeInternalInventory(invokeInternalInventory.getValue());		
		inventoryDTO.setCountryId(request.getCountryId());
		return inventoryDTO;
	}
	
	/**
	 * 
	 * Metodo: pobla el objeto de inventarios para
	 * la interaccion con la interfaz
	 * @param request
	 * @param woAttentionDTO
	 * @param WorkOrderServiceVO
	 * @author jalopez
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public void populateObjectInventoryFinalization(InventoryDTO inventoryDTO , TrayWOManagmentDTO request, WOAttentionsRequestDTO woAttentionDTO,WorkOrderServiceVO woServiceVO) throws PropertiesException, BusinessException, DAOServiceException, DAOSQLException{
		
		if( inventoryDTO != null && inventoryDTO.getDealer() == null ) {
			DealerVO dealerVO = new DealerVO();
			CustomerVO customerVO = new CustomerVO();
			dealerVO.setDealerCode( request.getDealerCode() );
			dealerVO.setDepotCode( request.getDepotCode() );
			dealerVO.setId( request.getDealerId() );
			customerVO.setCustomerCode( request.getCustomerCode() );
			WorkOrderVO workOrderVO = new WorkOrderVO();
			workOrderVO.setId( request.getWorkOrderId() );
			inventoryDTO.setWorkOrderVO(workOrderVO);
			inventoryDTO.setWoAttentionDTO( this.getElementsBySerializedOrNotSerialized(woAttentionDTO, false) );
			inventoryDTO.setDealer( dealerVO );
			inventoryDTO.setCountryCode( request.getCountryCode() );
			inventoryDTO.setDocumentResponsibleCrew( request.getDocumentResponsibleCrew() );
			inventoryDTO.setCustomer(customerVO);	
			inventoryDTO.setCountryId( request.getCountryId() );
			inventoryDTO.setAssignCrewId( request.getCrewId() );
			inventoryDTO.setUserId( request.getUserId() );
			
			//Se realiza la consulta del parametro del sistema que define los servicios de inventarios que se deben invocar,
			//debido a que la aplicacion puede ser conectada a los servicios de SmartDealerI o los propios de HSP.
			SystemParameter invokeInternalInventory = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_INVOKE_HSP_INVENTORY_SERVICE.getCodeEntity(), request.getCountryId());
			if (invokeInternalInventory == null) {
				log.fatal("No se ha encontrado el parametro del sistema INVOKE_HSP_INVENTORY_SERVICE");
				throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
			}
			inventoryDTO.setInvokeInternalInventory(invokeInternalInventory.getValue());		
			
			//Se valida que vengan elementos nos serializados para invocar el servicio de inventarios.
			if (inventoryDTO.getWoAttentionDTO() != null ) {
				if (inventoryDTO.getWoAttentionDTO().getWorkorderServices() != null && !inventoryDTO.getWoAttentionDTO().getWorkorderServices().isEmpty()) {
					inventoryDTO.setInvokeInventoryService(true);
				} else {
					inventoryDTO.setInvokeInventoryService(false);
				}
			}
			
			//Se valida que vengan elementos nos serializados para invocar el servicio de inventarios.
//			if ( woServiceVO.getWoServiceElements() != null &&  !woServiceVO.getWoServiceElements().isEmpty()) {
//				WorkOrderServiceElementVO element = woServiceVO.getWoServiceElements().get(0);
//				if ( element == null ) {
//					inventoryDTO = null;
//				}
//			} else {
//				inventoryDTO = null;
//			}
		} 
	}
	
	/**
	 * 
	 * Metodo: Retorna la informacion de un Elemento
	 * filtrando por el serial
	 * @param TrayWOManagmentDTO request
	 * @return SerializedVO
	 * @throws BusinessException
	 * @author jalopez
	 */
	@Override
	public SerializedVO getElementBySerialCode(TrayWOManagmentDTO request) throws BusinessException {
		try{
			log.debug("== Inicia getElementBySerialCode/TrayWorkOrderManagmentBusinessBean ==");
			//Objetos Globales
			InventoryDTO inventoryDTO = new InventoryDTO();
			ElementDTO elementDTO = new ElementDTO();
			SerializedVO serializedVO = new SerializedVO();
			WorkOrderVO workOrderVO = new WorkOrderVO();
			DealerVO dealerVO = new DealerVO();
			
			//Validaciones
			Object[] params = null;
			params = new Object[2];			
			params[1] = "getElementBySerialCode";	
			params[0] = "ElementSerial";
			UtilsBusiness.validateRequestResponseWebService(params, request.getElementVO(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "ElementSerial";
			UtilsBusiness.validateRequestResponseWebService(params, request.getElementVO().getElementSerial(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));			
			params[0] = "WorkOrderId";
			UtilsBusiness.validateRequestResponseWebService(params, request.getWorkOrderId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "DealerId";
			UtilsBusiness.validateRequestResponseWebService(params, request.getDealerId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "CountryCode";
			UtilsBusiness.validateRequestResponseWebService(params, request.getCountryCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "IsInstallation";
			UtilsBusiness.validateRequestResponseWebService(params, request.getIsInstallation(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "CustomerCode";
			UtilsBusiness.validateRequestResponseWebService(params, request.getCustomerCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "IsSwap";
			UtilsBusiness.validateRequestResponseWebService(params, request.getIsSwap(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "DealerCode";
			UtilsBusiness.validateRequestResponseWebService(params, request.getDealerCode(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "CountryId";
			UtilsBusiness.validateRequestResponseWebService(params, request.getCountryId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			
			//Asignacion de datos
			workOrderVO.setId( request.getWorkOrderId() );
			serializedVO.setSerialCode( request.getElementVO().getElementSerial() );
			elementDTO.setSerializedVO( serializedVO );
			inventoryDTO.setElementDTO( elementDTO );
			inventoryDTO.setCountryCode( request.getCountryCode() );
			inventoryDTO.setWorkOrderVO( workOrderVO );
			dealerVO.setId( request.getDealerId() );
			dealerVO.setDealerCode( request.getDealerCode() );
			inventoryDTO.setDealer( dealerVO );
			inventoryDTO.setIsInstallation( request.getIsInstallation() );
			CustomerVO customerVO = new CustomerVO();
			customerVO.setCustomerCode( request.getCustomerCode() );
			inventoryDTO.setCustomer( customerVO );
			inventoryDTO.setIsSwap( request.getIsSwap() );
			
			//Se realiza la consulta del parametro del sistema que define los servicios de inventarios que se deben invocar,
			//debido a que la aplicacion puede ser conectada a los servicios de SmartDealerI o los propios de HSP.
			SystemParameter invokeInternalInventory = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_INVOKE_HSP_INVENTORY_SERVICE.getCodeEntity(), request.getCountryId());
			if (invokeInternalInventory == null) {
				log.fatal("No se ha encontrado el parametro del sistema INVOKE_HSP_INVENTORY_SERVICE");
				throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
			}
			inventoryDTO.setInvokeInternalInventory(invokeInternalInventory.getValue());		
			
			elementDTO = trayHelper.getElementBySerialCode( inventoryDTO );			
			
			//Valida que el elemento ingresado sea del tipo IRD o SmartCard segun el caso
			this.validateAttentionElementsTypes( elementDTO, request.getElementVO().getIsIrdElement() );
			
			if(request.getWorkOrderId() != null){
				WorkOrder wo=workOrderDAO.getWorkOrderByID(request.getWorkOrderId());
				if(wo!=null
						&& wo.getWoTypeByWoTypeId() != null
						&& wo.getWoTypeByWoTypeId().getWoTypeCode()!=null
						&& invokeInternalInventory!=null
						&& invokeInternalInventory.getValue()!=null
						&& invokeInternalInventory.getValue().equalsIgnoreCase(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())
						&& wo.getWoTypeByWoTypeId().getWoTypeCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_TYPE_DISCONNECTION.getCodeEntity())
						&& elementDTO!=null && elementDTO.getSerializedVO()!=null){
					
					elementDTO.getSerializedVO().setSerialized(null);
					
				}
			}
			
			return elementDTO.getSerializedVO();
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getElementBySerialCode/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialCode/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Realiza la validacion de los elementos
	 * ingresados, para determinar que el serial 
	 * corresponda a un Decodificador o una SmartCard segun 
	 * el caso.
	 * @param elementDTO ElementDTO, informacion del elemento consultado
	 * @param isIrdElement Boolean, indica si es un deco o una smartcard
	 * @throws BusinessException
	 * @author jalopez
	 */
	private void validateAttentionElementsTypes(ElementDTO elementDTO, Boolean isIrdElement) throws BusinessException{
		log.debug("== Inicia validateAttentionElementsTypes/TrayWorkOrderManagmentBusinessBean ==");
		try{
			//Validaciones
			Object[] params = null;
			params = new Object[3];			
			
			if( isIrdElement ){
				//Valida que el tipo de elemento sea un Decodificador
				if( !elementDTO.getSerializedVO().getDeviceTypeId().equals( CodesBusinessEntityEnum.INVENTORY_INTERFACE_ELEMENT_TYPE_DECO.getCodeEntity() ) ){
					params[0] = elementDTO.getSerializedVO().getSerialCode();
					params[1] = "Decodificador";
					params[2] = "Decodificador";
					throw new  BusinessException( ErrorBusinessMessages.CORE_CR057.getCode(), ErrorBusinessMessages.CORE_CR057.getMessage(params), UtilsBusiness.getParametersWS(params) );
				}
			}else{
				//Valida que el tipo de elemento sea una SmartCard
				if( !elementDTO.getSerializedVO().getDeviceTypeId().equals( CodesBusinessEntityEnum.INVENTORY_INTERFACE_ELEMENT_TYPE_SMARTCARD.getCodeEntity() ) ){
					params[0] = elementDTO.getSerializedVO().getSerialCode();
					params[1] = "SmartCard";
					params[2] = "SmartCard";
					throw new  BusinessException( ErrorBusinessMessages.CORE_CR057.getCode(), ErrorBusinessMessages.CORE_CR057.getMessage(params), UtilsBusiness.getParametersWS(params) );
				}
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación validateAttentionElementsTypes/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateAttentionElementsTypes/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/**
     * Method: Retorna un listado de Crews por el
     * id del Dealer
     * @param  Long dealerId
     * @return - List<CrewVO>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
	@Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<CrewVO> getCrewsByDealerId(Long dealerId) throws BusinessException{
    	return crewBusiness.getActiveCrewsByDealerId(dealerId);
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#validateWorkOrdersAgenda(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void validateWorkOrdersAgenda(List<Long> workOrderIds)throws BusinessException {
		log.debug("== Inicia validateWorkOrdersAgenda/TrayWorkOrderManagmentBusinessBean ==");
		try{
			List<Object[]> woCodesWithoutAgenda = workOrderDAO.getWorkOrdersActiveAgendaByIds(workOrderIds);
			List<String> params = null;
			List<Object[]> woCodesWithoutAgendaAndCrew = workOrderDAO.getWorkOrdersAgendaByIdsAndCrewNotNull(workOrderIds);
			//Caso que haya sin agenda y sin cuadrilla
			if( woCodesWithoutAgenda != null && !woCodesWithoutAgenda.isEmpty() 
					&& woCodesWithoutAgendaAndCrew != null && !woCodesWithoutAgendaAndCrew.isEmpty()){
				Object[] woCodes = null;
				woCodes = new Object[2];
				params = new ArrayList<String>();
				
				StringBuffer sbAgenda = new StringBuffer();
				for(Object woCode : woCodesWithoutAgendaAndCrew){
					if( !sbAgenda.toString().equals("") )
						sbAgenda.append(", ");
					sbAgenda.append( woCode );
				}
				woCodes[0] = sbAgenda.toString();
				
				StringBuffer sbCrew = new StringBuffer();
				for(Object woCode : woCodesWithoutAgendaAndCrew){
					if( !sbCrew.toString().equals("") )
						sbCrew.append(", ");
					sbCrew.append( woCode );
				}
				woCodes[1] = sbCrew.toString();
				
				params.add( sbAgenda.toString() );
				params.add( sbCrew.toString() );
				
				throw new BusinessException(ErrorBusinessMessages.CORE_CR052.getCode(),ErrorBusinessMessages.CORE_CR052.getMessage(woCodes),params);
			//caso que solo hay wo's sin cuadrilla
			}else if( ( woCodesWithoutAgenda == null || woCodesWithoutAgenda.isEmpty() ) 
						&& woCodesWithoutAgendaAndCrew != null && !woCodesWithoutAgendaAndCrew.isEmpty() ){
				Object[] woCodes = null;
				woCodes = new Object[1];
				params = new ArrayList<String>();
				StringBuffer sb = new StringBuffer();
				for(Object woCode : woCodesWithoutAgendaAndCrew){
					if( !sb.toString().equals("") )
						sb.append(", ");
					sb.append( woCode );
				}
				woCodes[0] = sb.toString();
				params.add( sb.toString() );
				throw new BusinessException(ErrorBusinessMessages.CORE_CR053.getCode(),ErrorBusinessMessages.CORE_CR053.getMessage(woCodes),params);	
			//caso que solo hay wo's sin agenda
			}else if( ( woCodesWithoutAgendaAndCrew == null || woCodesWithoutAgendaAndCrew.isEmpty() ) 
						&& woCodesWithoutAgenda != null && !woCodesWithoutAgenda.isEmpty() ){
				Object[] woCodes = null;
				woCodes = new Object[1];
				params = new ArrayList<String>();
				StringBuffer sb = new StringBuffer();
				for(Object woCode : woCodesWithoutAgendaAndCrew){
					if( !sb.toString().equals("") )
						sb.append(", ");
					sb.append( woCode );
				}
				woCodes[0] = sb.toString();
				params.add( sb.toString() );
				throw new BusinessException(ErrorBusinessMessages.CORE_CR031.getCode(),ErrorBusinessMessages.CORE_CR031.getMessage(woCodes),params);	
			}
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación validateWorkOrdersAgenda/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina validateWorkOrdersAgenda/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderTrayDTO> getWorkordersFinalization(TrayWOManagmentDTO trayRequest)throws BusinessException {
		log.debug("== Inicia getWorkordersFinalization/TrayWorkOrderManagmentBusinessBean ==");
		try{
			//Objetos globales
			WorkOrderTrayResponse response = new WorkOrderTrayResponse();
			List<WorkOrderTrayDTO> workOrderList = new ArrayList<WorkOrderTrayDTO>();
			RequestCollectionInfo requestCollectionInfo = null;
			WorkOrderFilterTrayDTO filter = new WorkOrderFilterTrayDTO();
			List<Long> woStatusIds = new ArrayList<Long>();
			List<Long> woTypeIds = new ArrayList<Long>(); 
			List<Long> dealersIds = new ArrayList<Long>(); 
			
			Object[] params = null;
			params = new Object[2];	
		
			params[1] = "getWorkordersFinalization";			
			params[0] = "WorkOrderId";
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest.getWorkOrderId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "DealerId";
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest.getDealerId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "CountryId";
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest.getCountryId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "UserId";
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest.getUserId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
				
			
			//Se consulta la WO para obtener la información para asignar a los filtros.
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID( trayRequest.getWorkOrderId() );
			
			//Para finalizacion masiva las WO deben estar Atendidas
			WorkorderStatus realizedStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity() );
			
			//Asignacion de filtros para la consulta
			woStatusIds.add( realizedStatus.getId() );
			woTypeIds.add(workOrder.getWoTypeByWoTypeId().getId());
			dealersIds.add( trayRequest.getDealerId() );
			
			filter.setWoStatusIds( woStatusIds );
			filter.setDealersIds( dealersIds );
			filter.setCountryId( trayRequest.getCountryId() );
			filter.setWoTypeIds(woTypeIds);
			filter.setCustomerCode(workOrder.getCustomer().getCustomerCode());
			filter.setCustomerIbs(workOrder.getCustomer().getCustomerCode());
			filter.setServiceAddress(workOrder.getCustomer().getCustomeraddress());
			filter.setDealerId( trayRequest.getDealerId() );
			
			WorkOrderResponse daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filter,requestCollectionInfo,false);
			if( daoResponse.getWorkOrdersIds() != null && !daoResponse.getWorkOrdersIds().isEmpty() ){				
				/*for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();	
					
					//ADICIONA LA WO A LA LISTA DE RETORNO, SIN LA WO PRINCIPAL, SOLO LAS ASOCIADAS
					if( woId.compareTo(trayRequest.getWorkOrderId()) != 0)
						workOrderList.add( fillWorkOrderInformation(woId) );
				}		
				response.setWorkOrderList(workOrderList);*/
				List<Long> woIds = new ArrayList<Long>();
				boolean existData = false;
				for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();
					if(woId.compareTo(trayRequest.getWorkOrderId()) != 0){
						woIds.add(woId);
						existData = true;
					}
					
				}
				if(existData){
					response.setWorkOrderList( this.fillWorkOrderInformationOptimized(woIds,trayRequest.getUserId()) );
				}
				
			}
			return response.getWorkOrderList();
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkordersFinalization/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkordersFinalization/TrayWorkOrderManagmentBusinessBean ==");
		}
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getWorkordersToSchedule(co.com.directv.sdii.model.dto.TrayWOManagmentDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WorkOrderTrayDTO> getWorkordersToSchedule(TrayWOManagmentDTO trayRequest)throws BusinessException {
		log.debug("== Inicia getWorkordersToSchedule/TrayWorkOrderManagmentBusinessBean ==");
		try{
			//Objetos globales
			WorkOrderTrayResponse response = new WorkOrderTrayResponse();
			List<WorkOrderTrayDTO> workOrderList = new ArrayList<WorkOrderTrayDTO>();
			RequestCollectionInfo requestCollectionInfo = null;
			WorkOrderFilterTrayDTO filter = new WorkOrderFilterTrayDTO();
			List<Long> woStatusIds = new ArrayList<Long>();
			List<Long> dealersIds = new ArrayList<Long>(); 
			
			Object[] params = null;
			params = new Object[2];	
		
			params[1] = "getWorkordersToSchedule";			
			params[0] = "WorkOrderId";
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest.getWorkOrderId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "DealerId";
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest.getDealerId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "CountryId";
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest.getCountryId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
			params[0] = "UserId";
			UtilsBusiness.validateRequestResponseWebService(params, trayRequest.getUserId(), ErrorBusinessMessages.BUSINESS_BL186.getCode(), ErrorBusinessMessages.BUSINESS_BL186.getMessage(params));
				
			
			//Se consulta la WO para obtener la información para asignar a los filtros.
			WorkOrder workOrder = workOrderDAOBean.getWorkOrderByID( trayRequest.getWorkOrderId() );
			
			//Para agendamiento masivo las WO deben estar asignada - reagendada - agendada - N pendiente
			WorkorderStatus assignedStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity() );
			WorkorderStatus reAssignedStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity() );
			WorkorderStatus scheduledStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity() );
			WorkorderStatus reScheduledStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity() );
			WorkorderStatus pendingStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity() );
			
			//Asignacion de filtros para la consulta
			woStatusIds.add( assignedStatus.getId() );
			woStatusIds.add( reAssignedStatus.getId() );
			woStatusIds.add( scheduledStatus.getId() );
			woStatusIds.add( reScheduledStatus.getId() );
			woStatusIds.add( pendingStatus.getId() );
			dealersIds.add( trayRequest.getDealerId() );
			
			filter.setWoStatusIds( woStatusIds );
			filter.setDealersIds( dealersIds );
			filter.setCountryId( trayRequest.getCountryId() );
			filter.setCustomerCode(workOrder.getCustomer().getCustomerCode());
			filter.setCustomerIbs(workOrder.getCustomer().getCustomerCode());
			filter.setServiceAddress(workOrder.getCustomer().getCustomeraddress());
			
			WorkOrderResponse daoResponse = workOrderDAO.getWorkOrdersForDealerTray(filter,requestCollectionInfo,false);
			if( daoResponse.getWorkOrdersIds() != null && !daoResponse.getWorkOrdersIds().isEmpty() ){				
				/*for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();	
					
					//ADICIONA LA WO A LA LISTA DE RETORNO, SIN LA WO PRINCIPAL, SOLO LAS ASOCIADAS
					if( woId.compareTo(trayRequest.getWorkOrderId()) != 0)
						workOrderList.add( fillWorkOrderInformation(woId) );
				}		
				response.setWorkOrderList(workOrderList);*/
				List<Long> woIds = new ArrayList<Long>();
				boolean existData = false;
				for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();
					if(woId.compareTo(trayRequest.getWorkOrderId()) != 0){
						woIds.add(woId);
						existData = true;
					}
				}
				if(existData){
					response.setWorkOrderList( this.fillWorkOrderInformationOptimized(woIds, trayRequest.getUserId()) );
				}
			}
			return response.getWorkOrderList();
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkordersToSchedule/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkordersToSchedule/TrayWorkOrderManagmentBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getWorkOrdersForDealerTrayWithExpirationGrouping(co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WorkOrderTrayResponse getWorkOrdersForDealerTrayWithExpirationGrouping(WorkOrderFilterTrayDTO filter,RequestCollectionInfo requestCollectionInfo)throws BusinessException {
		log.debug("== Inicia getWorkOrdersForDealerTrayWithExpirationGrouping/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( filter == null || filter.getCountryId() == null || filter.getCountryId() <= 0L ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			//Validacion de rangos de fechas
			validateTrayDates(filter.getWoAgendationDateInit(), filter.getWoAgendationDateEnd(), 2);
			validateTrayDates(filter.getWoAttentionDateInit(), filter.getWoAttentionDateEnd(), 2);
			validateTrayDates(filter.getWoCreationDateInit(), filter.getWoCreationDateEnd(), 2);
			validateTrayDates(filter.getWoFinalizationDateInit(), filter.getWoFinalizationDateEnd(), 2);
			
			//En caso que no tenga filtro por estado, filtra por los estados permitidos en esta bandeja
			if( filter.getWoStatusIds() == null || filter.getWoStatusIds().isEmpty() ){
				WorkorderStatus asignStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity() );
				WorkorderStatus reAsignStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity() );
				WorkorderStatus scheduleStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity() );
				WorkorderStatus reScheduleStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity() );
				WorkorderStatus realizedStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity() );
				WorkorderStatus pendingStatus = workorderStatusDAO.getWorkorderStatusByCode( CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity() );
				List<Long> woStatusIds = new ArrayList<Long>();
				if( asignStatus != null )
					woStatusIds.add( asignStatus.getId() );
				if( reAsignStatus != null )
					woStatusIds.add( reAsignStatus.getId() );
				if( scheduleStatus != null )
					woStatusIds.add( scheduleStatus.getId() );
				if( reScheduleStatus != null )
					woStatusIds.add( reScheduleStatus.getId() );
				if( realizedStatus != null )
					woStatusIds.add( realizedStatus.getId() );
				if( pendingStatus != null )
					woStatusIds.add( pendingStatus.getId() );
				filter.setWoStatusIds(woStatusIds);
			}
			
			this.fillDealerAndBranchIds(filter);
			
			WorkOrderResponse daoResponse = workOrderDAO.getWorkOrdersForDealerTrayWithExpirationGrouping(filter,requestCollectionInfo);
			WorkOrderTrayResponse response = new WorkOrderTrayResponse();
			List<WorkOrderTrayDTO> workOrderList = new ArrayList<WorkOrderTrayDTO>();
			if( daoResponse.getWorkOrdersIds() != null && !daoResponse.getWorkOrdersIds().isEmpty() ){
				
				/*for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();					
					//ADICIONA LA WO A LA LISTA DE RETORNO
					workOrderList.add( this.fillWorkOrderInformation(woId) );
				}			
				response.setWorkOrderList(workOrderList);*/
				List<Long> woIds = new ArrayList<Long>();
				for( Object[] object : daoResponse.getWorkOrdersIds() ){
					Long woId = ( (BigDecimal) object[0] ).longValue();
					woIds.add(woId);
				}
				response.setWorkOrderList( this.fillWorkOrderInformationOptimized(woIds, filter.getUserId()) );
			}
			if( requestCollectionInfo != null )
				this.populatePaginationInfo(response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), daoResponse.getRowCount(), daoResponse.getTotalRowCount());
			
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getWorkOrdersForDealerTrayWithExpirationGrouping/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getWorkOrdersForDealerTrayWithExpirationGrouping/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getAllResponsableEmployeeCrewByCountryAndDealerIds(java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<EmployeeCrewDTO> getAllResponsableEmployeeCrewByCountryAndDealerIds(Long countryId, List<Long> dealerIds) throws BusinessException {
		log.debug("== Inicia getAllResponsableEmployeeCrewByCountryAndDealerIds/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( countryId == null || countryId.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			List<EmployeeCrew> pojoList = employeesCrewDAO.getAllResponsableEmployeeCrewByCountryAndDealerIds(countryId, dealerIds); 
			List<EmployeeCrewDTO> response = null;
			if( pojoList != null && !pojoList.isEmpty() ){
				response = new ArrayList<EmployeeCrewDTO>();
				for( EmployeeCrew pojo : pojoList ){
					EmployeeCrewDTO dto = new EmployeeCrewDTO();
					dto.setEmployeeId( pojo.getEmployee().getId() );
					dto.setEmployeeFirstName( pojo.getEmployee().getFirstName() != null ? pojo.getEmployee().getFirstName() : null );
					dto.setEmployeeLastName( pojo.getEmployee().getLastName() != null ? pojo.getEmployee().getLastName() : null );
					dto.setCrewId( pojo.getCrew().getId() );
					response.add( dto );
				}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getAllResponsableEmployeeCrewByCountryAndDealerIds/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllResponsableEmployeeCrewByCountryAndDealerIds/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getSaleDealersByWoAsignDealerIdsAndCountry(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getSaleDealersByWoAsignDealerIdsAndCountry(List<Long> dealerIds, Long countryId,
			String isSeller, String isInstaller) throws BusinessException {
		log.debug("== Inicia getSaleDealersByWoAsignDealerIdsAndCountry/TrayWorkOrderManagmentBusinessBean ==");
		try{
			if( countryId == null || countryId.longValue() <= 0 ){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			List<Dealer> pojoList = workOrderDAO.getSaleDealersByWoAsignDealersIdAndCountry(dealerIds, countryId, isSeller, isInstaller);
			List<DealerVO> response = null;
			if( pojoList != null && !pojoList.isEmpty() ){
				response = new ArrayList<DealerVO>();
				for( Dealer pojo : pojoList ){
					DealerVO vo = UtilsBusiness.copyObject(DealerVO.class, pojo);
					vo.setChannelType(null);
					vo.setPostalCode(null);
					vo.setDealerType(null);
					vo.setDealerStatus(null);
					vo.setDealer(null);
					response.add(vo);
				}
			}
			return response;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getSaleDealersByWoAsignDealerIdsAndCountry/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getSaleDealersByWoAsignDealerIdsAndCountry/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.tray.TrayWorkOrderManagmentBusinessLocal#getActiveProgramsByDealerIds(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProgramVO> getActiveProgramsByDealerIds(List<Long> dealerIds)throws BusinessException{
		log.debug("== Inicia public List<ProgramVO> getActiveProgramsByDealerIds(List<Long> dealerIds)throws BusinessException/TrayWorkOrderManagmentBusinessBean ==");
		try{
			return UtilsBusiness.convertList(programDAO.getProgramsByDealerIdsAndStatusCode(dealerIds,CodesBusinessEntityEnum.PROGRAM_STATUS_ACTIVE.getCodeEntity()), ProgramVO.class);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación public List<ProgramVO> getActiveProgramsByDealerIds(List<Long> dealerIds)throws BusinessException/TrayWorkOrderManagmentBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina public List<ProgramVO> getActiveProgramsByDealerIds(List<Long> dealerIds)throws BusinessException/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Centraliza llenar la lista de delaers o branches para la consulta de WO
	 * @param filter
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	private void fillDealerAndBranchIds(WorkOrderFilterTrayDTO filter) throws DAOServiceException, DAOSQLException{
		//Se valida que se envie una sucursal o una principal para darle valor a los ids de dealers por los cuales se filtra
		boolean isDealerId = ( filter.getDealersIds() == null || filter.getDealersIds().isEmpty() ) ? false : true;
    	boolean isBranchId = ( filter.getBranchIds() == null || filter.getBranchIds().isEmpty() )  ? false : true;
    	if( isBranchId ){
    		filter.setDealersIds(null);
    	} else if( isDealerId ){
    		List<Long> tempList = new ArrayList<Long>();
    		for( Long dealerId : filter.getDealersIds() ){
    			List<Dealer> dealers = dealersDAO.getDealerByBranchId( dealerId );
        		if( dealers != null && !dealers.isEmpty() ){
        			for(Dealer d : dealers){
        				tempList.add( d.getId() );
        			}
        		}
    		}
    		filter.getDealersIds().addAll(tempList);
    		filter.setBranchIds(null);
    	}
	}


	
	@Override
	public CustomerVO getCustomerResources(String customerCode, Long userId) throws BusinessException {
		return customerBuss.getCustomerResources(customerCode,userId);
	}
	
}
