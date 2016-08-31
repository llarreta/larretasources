package co.com.directv.sdii.ejb.business.allocator.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacade;
import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.StateMachineWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.WoInfoEsbServiceBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.AssignWorkOrderDTO;
import co.com.directv.sdii.model.pojo.AllocatorEvent;
import co.com.directv.sdii.model.pojo.AllocatorEventMaster;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.ProcessStatus;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.WorkorderCSRStatus;
import co.com.directv.sdii.model.vo.AllocatorEventMasterVO;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.ShippingOrderVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.allocator.AllocatorEventDAOLocal;
import co.com.directv.sdii.persistence.dao.allocator.ProcessStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceHourDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCSRDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignResponseDTO;

/**
 * Session Bean implementation class AllocatorBean
 * 
 * Implementación de métodos de negocio del módulo de asignación.
 * 
 * @author Jimmy Vélez Muñoz
 */
@Stateless(name="AllocatorBeanLocal",mappedName="ejb/AllocatorBean")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AllocatorBean extends BusinessBase implements AllocatorBeanLocal {

	private final static Logger log = UtilsBusiness
			.getLog4J(AllocatorBean.class);

	private static final String IS_ALLOC_CODE = "S";

	@EJB(name="DealersDAOLocal",beanInterface=DealersDAOLocal.class)
	private DealersDAOLocal dealersDAO;

	@EJB(name="WorkOrderDAOLocal",beanInterface=WorkOrderDAOLocal.class)
	private WorkOrderDAOLocal woDao;
	
	@EJB(name="ServiceHourDAOLocal",beanInterface=ServiceHourDAOLocal.class)
	private ServiceHourDAOLocal serviceHourDAO;
		
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDao;
	
	@EJB(name="WorkOrderAgendaDAOLocal", beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAO;
	
	@EJB(name="WorkOrderServiceDAOLocal", beanInterface=WorkOrderServiceDAOLocal.class)
	private WorkOrderServiceDAOLocal woServiceDAO;
	
	@EJB(name="StateMachineWOBusinessLocal", beanInterface=StateMachineWOBusinessLocal.class)
	private StateMachineWOBusinessLocal stateMachine;
	
	@EJB(name="ProcessStatusDAOLocal", beanInterface=ProcessStatusDAOLocal.class)
	private ProcessStatusDAOLocal processStatusDao;
	
	@EJB(name="AllocatorEventDAOLocal", beanInterface=AllocatorEventDAOLocal.class)
	private AllocatorEventDAOLocal allocatorEventDao;
	
	@EJB(name="CoreWOBusinessLocal", beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal coreWOBusiness;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	@EJB(name="AssignmentFacade", beanInterface=AssignmentFacade.class)
	private AssignmentFacade assignmentFacade;
	
	@EJB(name="WorkOrderCSRDAOLocal", beanInterface=WorkOrderCSRDAOLocal.class)
	private WorkOrderCSRDAOLocal workOrderCSRDAO;
	
	/**
	 * Default constructor.
	 */
	public AllocatorBean() {
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#assignDealer(co.com.directv.sdii.model.vo.WorkOrderVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public DealerVO assignDealer(WorkOrderVO workOrder,AllocatorEventMasterVO allocatorEventMasterVO) throws BusinessException {
		log.debug("== Inicia assignDealer/AllocatorBean ==");
		try {
			UtilsBusiness.assertNotNull(workOrder, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage() + " NO existe la work order con el identificador especificado: " + workOrder.getId());
			UtilsBusiness.assertNotNull(workOrder.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se especificó el identificador de la Work Order");
			
			WorkOrder woPojo = woDao.getWorkOrderByID(workOrder.getId());
			UtilsBusiness.assertNotNull(woPojo, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage() + " NO existe la work order con el identificador especificado: " + workOrder.getId());
			workOrder = UtilsBusiness.copyObject(WorkOrderVO.class, woPojo);
			DealerVO result = null;
			DealerVO dealerWouthCoverage = null;
			//Se creo el objeto necesario para invocar el asignador
			AssignRequestDTO request = new AssignRequestDTO();
			request.setIbsCustomerCode( workOrder.getCustomer().getCustomerCode() );
			request.setIbsCustomerTypeCode( workOrder.getCustomer().getCustType().getCustomerType().getCustomerTypeCode() );
			request.setIbsCustomerClassCode( workOrder.getCustomer().getCustType().getCustomerClass().getCustomerClassCode() );
			request.setPostalCode( workOrder.getCustomerAddress().getPostalCode().getPostalCodeCode() );
			request.setExecutionMode( CodesBusinessEntityEnum.SKILL_MODE_TYPE_ASSIGN.getCodeEntity() );
			request.setCountryIso2Code( workOrder.getCountry().getCountryCode() );
			request.setIbsSaleDealerCode( workOrder.getSaleDealer().getDealerCode().toString() );
			List<Service> woServices = this.woServiceDAO.getServicesOfWorkOrderServiceByWorkorderId( workOrder.getId() );
			if( woServices != null && !woServices.isEmpty() ){
				List<String> services = new ArrayList<String>();
				for( Service service : woServices ){
					services.add( service.getServiceCode() );
				}
				request.setServices(services);
			}

			List<Long> woShippingOrder = new ArrayList<Long>();
			if(workOrder.getShippingOrders() != null && !workOrder.getShippingOrders().isEmpty() )
				for( ShippingOrderVO shippingOrderVO : workOrder.getShippingOrders()){
					woShippingOrder.add( shippingOrderVO.getId() );
				}
			request.setShippingOrders(woShippingOrder);
			
			WorkOrderAgenda woAgenda = this.workOrderAgendaDAO.getLastWorkOrderAgendaByWoID( workOrder.getId() );
			if( woAgenda != null ){
				request.setScheduleDate( woAgenda.getAgendationDate() );
				request.setSdiiServiceHourCode( woAgenda.getServiceHour().getServiceHoursName() );
			}
			request.setIbsBuildingCode( workOrder.getBuildingCode() );
			request.setIbsCustomerAddressCode(workOrder.getWoAddressCode());
			
			AssignResponseDTO response = assignmentFacade.assignDealer( request );
			String traceInfo = response.getTraceAssignmentInformation();
			if(traceInfo != null && traceInfo.trim().length() > 0){
				if(traceInfo.trim().length() > 4000){
					traceInfo = traceInfo.substring(0, 3950-1);
				}
				reportAllocatorEvent(workOrder, CodesBusinessEntityEnum.ALLOCATOR_PROCESS_ASSIGNMENT_TRACE.getCodeEntity(), traceInfo,allocatorEventMasterVO);
			}

			//Se valida que si el dealer es el dealer sin cobertura validar si existe
			if( response != null && response.getDealer() != null 
					&& response.getDealer().getDealerCode() != null){
				if(response.getDealer().getDealerCode().longValue() != AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerCodeWoutCoverage(workOrder.getCountry().getId())){
					result = response.getDealer();
				}else{
					
					dealerWouthCoverage = UtilsBusiness.copyObject(DealerVO.class, 
							                                       this.dealersDAO.getDealerByDealerCodeAndCountryId( response.getDealer().getDealerCode(),
							                                    		                                              workOrder.getCountry().getId()));
					if(dealerWouthCoverage==null){
						throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL050.getCode(), ErrorBusinessMessages.ALLOCATOR_AL050.getMessage(), Arrays.asList(new String[]{""+response.getDealer().getDealerCode().longValue()}));
					}
					result = dealerWouthCoverage;
					
				}
			}
			
			return result;
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación assignDealer/AllocatorBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina assignDealer/AllocatorBean ==");
        }
	}
	
	/**
	 * Metodo: Construye la asignación de la work order
	 * @param workOrder identificador de la work order
	 * @param dealer objeto dealer al cual se le asignará la work order
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 * @throws PropertiesException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createSdiiAssignment(WorkOrderVO workOrder, DealerVO dealer) throws BusinessException{
		log.debug("== Inicia createSdiiAssignment/AllocatorBean ==");
		//Se consulta el usuario IBs de los parametros de sistema
		Long userId = null;
		try{
			SystemParameter sysParam = this.systemParameterDao.getSysParamByCodeAndCountryId( CodesBusinessEntityEnum.SYSTEM_PARAM_WORK_ORDER_OWNER_USER_LOGIN.getCodeEntity() , workOrder.getCountry().getId());
			if( sysParam != null && sysParam.getValue() != null ){
				User sample = new User();
				sample.setLogin( sysParam.getValue() );
				List<User> ibsUser = this.userDao.getUserBySample(sample);
				if( ibsUser != null && !ibsUser.isEmpty() ){
					userId = ibsUser.get(0).getId();
				}
			}
			AssignWorkOrderDTO dto = new AssignWorkOrderDTO();
			dto.setDealer(dealer);
			dto.setWorkorder(workOrder);
			dto.setUserId(userId);
			dto.setAssignFromAllocator(true);
			
			//Se realiza la asignación de la Work Order sin notificar a IBS
			coreWOBusiness.assignWorkOrderToDealer(dto, true);
			
		} catch (Throwable e) {
			log.error("== Error createSdiiAssignment/AllocatorBean ==",e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina createSdiiAssignment/AllocatorBean ==");
		}
	}
	
	/**
	 * Metodo: Valida en la lista de dealers, todos tengan el tipo de dealer capaz de recibir asignación
	 * de work orders
	 * @param dealers lista con los dealers a ser validados
	 * @throws BusinessException En caso de error al realizar la validación, es decir en caso que se encuentre 1 dealer
	 * cuyo tipo no soporta asignación
	 * @author jjimenezh
	 */
	private void validateDealerTypes(List<DealerVO> dealers) throws BusinessException {
		for (DealerVO dealerVO : dealers) {
			if(! IS_ALLOC_CODE.equalsIgnoreCase(dealerVO.getDealerType().getIsAlloc())){
				throw new BusinessException(ErrorBusinessMessages.DEALER_IS_NOT_ALLOCABLE.getCode(), ErrorBusinessMessages.DEALER_IS_NOT_ALLOCABLE.getMessage() + "El dealer consultado con id: " + dealerVO.getId() + " No puede recibir asignaciones debido a que su propiedad is_alloc tiene un valor diferente de 'S'");
			}
		}
	}
		
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#getDealersByServicesAndPostalCode(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public List<DealerVO> getDealersByServicesAndPostalCode(
//			List<DealerVO> dealers, CustomerVO customer,
//			PostalCodeVO postalCode, List<ServiceVO> services, Date date,
//			ServiceHourVO serviceHour, String buildingCode)
//			throws BusinessException {
//		log.debug("== Inicia getDealersByServicesAndPostalCode/AllocatorBean ==");
//		UtilsBusiness.assertNotNull(dealers, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió la información de los dealers en la evaluación de habilidad tipo servicio");
//		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió la información del cliente en la evaluación de habilidad tipo servicio");
//		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió el identificador del cliente en la evaluación de habilidad tipo servicio");
//		UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió la información del código postal en la evaluación de habilidad tipo servicio");
//		UtilsBusiness.assertNotNull(postalCode.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió el identificador del código postal en la evaluación de habilidad tipo servicio");
//		UtilsBusiness.assertNotNull(services, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió la información de los servicios asociados a la WO en la evaluación de habilidad tipo servicio");
//		/*jjmenezh 2010-1-06 Se realiza cambio por control de cambios 
//		UtilsBusiness.assertNotNull(date, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//		UtilsBusiness.assertNotNull(serviceHour, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//		UtilsBusiness.assertNotNull(serviceHour.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//		UtilsBusiness.assertNotNull(buildingCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//		//*/
//		
//		List<DealerVO> dealerList = new ArrayList<DealerVO>();
//		List<Dealer> dealerListPojo;
//		try {
//			if (dealers == null || dealers.isEmpty() || dealers.size() == 0) {
//				// Se deben de tomar todas las compa��as instaladoras activas para el c�digo postal en
//				// el sistema
//				dealerListPojo = dealersDAO.getAllActiveByCountryIdAndIsAlloc(postalCode.getCity().getState().getCountry().getId());
//				dealerList = UtilsBusiness.convertList(dealerListPojo, DealerVO.class);
//			} else{
//				this.validateDealerTypes(dealers);
//				dealerList = dealers;
//			}
//			
//			// Se eliminan del arreglo las que no presten servicios en el c�digo postal ingresado
//			dealerList = filterDealersByServicesAtPostalCode(dealerList, postalCode, services);
//			return dealerList;
//		} catch(Throwable ex){
//        	log.error("== Error al tratar de ejecutar la operación getDealersByServicesAndPostalCode/AllocatorBean ==");
//        	throw this.manageException(ex);
//        } finally {
//			log.debug("== Termina getDealersByServicesAndPostalCode/AllocatorBean ==");
//		}
//		
//	}

//	private List<DealerVO> filterDealersByServicesAtPostalCode(List<DealerVO> dealerList, final PostalCodeVO postalCode,  List<ServiceVO> services) throws DAOServiceException, DAOSQLException {
//		
//		List<DealerVO> tempDealerList = new ArrayList<DealerVO>();
//		int countOfServices = services.size();
//		int servicesCounter = 0;
//		for (DealerVO dealer : dealerList) {
//			List<DealerService> dealerServices = dealerServicesDAO.getDealerServicesByDealerPostalCode(dealer.getId(), postalCode.getId());
//			if (dealerServices == null || dealerServices.isEmpty()){
//				// Se remueve del arreglo pues no presta los servicios en ese c�digo postal
//				continue;
//			}
//			servicesCounter = 0;
//			for (DealerService dealerService : dealerServices) {
//				for (ServiceVO service : services) {
//					if(dealerService.getService().getId().longValue() == service.getId().longValue()){
//						servicesCounter ++;
//					}
//				}
//			}
//			if(servicesCounter == countOfServices){
//				tempDealerList.add(dealer);
//			}
//			
//		}
//		return tempDealerList;
//	}

	/**
	 * 
	 * Metodo: Filtra una lista de dealers para encontrar los que están activos
	 * @param dealers lista con dealers que pueden o nó estar activos
	 * @author jjimenezh
	 */
	private void filterActiveDealers(List<DealerVO> dealers) {
		
		CollectionUtils.filter(dealers, new Predicate() {
			@Override
			public boolean evaluate(Object dealer) {
				if(dealer==null){
					return false;
				}
				try {
					if(((Dealer)dealer).getDealerStatus() == null){
						log.error("No se encontró el estado del dealer!!!");
						return false;
					}
					String statusCode = ((Dealer)dealer).getDealerStatus().getStatusCode();
					if(CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity().equalsIgnoreCase(statusCode)){
						return true;
					}
				} catch (PropertiesException e) {
					e.printStackTrace();
					log.error("Error al tratar de comparar los estados de un dealer: " + e.getMessage(),e);
				}
				return false;
			}
		});
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#getDealersByCustomerTypeAndPostalCode(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
//	public List<DealerVO> getDealersByCustomerTypeAndPostalCode(
//			List<DealerVO> dealers, CustomerVO customer,
//			PostalCodeVO postalCode, List<ServiceVO> services, Date date,
//			ServiceHourVO serviceHour, String buildingCode)
//			throws BusinessException {
//		log.debug("== Inicia getDealersByCustomerTypeAndPostalCode/AllocatorBean ==");
//		UtilsBusiness.assertNotNull(dealers, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibieron dealers para la evaluación de la habilidad tipo cliente");
//		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió la información del cliente para la evaluación de la habilidad tipo cliente");
//		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió el identificador del cliente para la evaluación de la habilidad tipo cliente");
//		UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió la información del código postal para la evaluación de la habilidad tipo cliente");
//		UtilsBusiness.assertNotNull(postalCode.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió el identificador del código postal para la evaluación de la habilidad tipo cliente");
//		UtilsBusiness.assertNotNull(services, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibieron servicios asociados a la WO para la evaluación de la habilidad tipo cliente");
//		/*
//		UtilsBusiness.assertNotNull(date, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//		UtilsBusiness.assertNotNull(serviceHour, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//		UtilsBusiness.assertNotNull(serviceHour.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//		UtilsBusiness.assertNotNull(buildingCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
//		//*/
//		
//		List<DealerVO> dealerList = new ArrayList<DealerVO>();
//		List<Dealer> dealerListPojo;
//		try {
//			if (dealers == null || dealers.isEmpty() || dealers.size() == 0) {
//				// Se deben tomar todas las compañías instaladoras activas para el código postal en
//				// el sistema
//				dealerListPojo = dealersDAO.getAllActiveByCountryIdAndIsAlloc(postalCode.getCity().getState().getCountry().getId());
//				dealerList = UtilsBusiness.convertList(dealerListPojo, DealerVO.class);
//			} else{
//				this.validateDealerTypes(dealers);
//				dealerList.addAll(dealers);
//			}
//			
//			// Se eliminan del arreglo las que no presten para el tipo de cliente en el c�digo postal
//			filterDealersWhoDoesntAttendCustomerType(dealerList, postalCode, customer.getCustType());
//			return dealerList;
//		} catch(Throwable ex){
//        	log.error("== Error al tratar de ejecutar la operación getDealersByCustomerTypeAndPostalCode/AllocatorBean ==");
//        	throw this.manageException(ex);
//        } finally {
//			log.debug("== Termina getDealersByCustomerTypeAndPostalCode/AllocatorBean ==");
//		}
//	}

	/**
	 * Se remueven de la lista de dealers los dealers que no atiendan el tipo de cliente en el código
	 * postal especificado
	 * @param dealerList Lista de dealers a filtrar los que no atienden el tipo de cliente en el código postal
	 * @param postalCode código postal a evaluar
	 * @param custType tipo de cliente a atender
	 * @author jjimenezh
	 */
//	private void filterDealersWhoDoesntAttendCustomerType(
//			List<DealerVO> dealerList, final PostalCodeVO postalCode,
//			final CustomerCodeType custType) {
//		CollectionUtils.filter(dealerList, new Predicate() {
//			
//			@Override
//			public boolean evaluate(Object arg0) {
//				if (arg0 instanceof DealerVO) {
//					DealerVO dealer = (DealerVO) arg0;
//					List<DealerCustomerType> dealerCustomerType = null;
//					try {
//						dealerCustomerType = dealerCustomerTypeDAO.getDealerCustomerTypeByDealerPostalCodeCustomerCodeType(dealer, postalCode, custType);
//					} catch (Throwable e) {
//						log.error("Al tratar de obtener los tipos de cliente que atiende el dealer en el código postal", e);
//						e.printStackTrace();
//					} 
//					if (dealerCustomerType == null || dealerCustomerType.isEmpty()){
//						// Se remueve del arreglo pues no presta para este c�digo postal para ese tipo de clientes
//						return false;
//					}else{
//						return true;
//					}
//				}else{
//					return false;
//				}
//			}
//		});
//	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#getDealerByLastServiceAttend(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealerByLastServiceAttend(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		UtilsBusiness.assertNotNull(dealers, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información de dealers en la evaluación de la habilidad atendió último servicio");
		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información del cliente en la evaluación de la habilidad atendió último servicio");
		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió el identificador del cliente en la evaluación de la habilidad atendió último servicio");
		/*
		UtilsBusiness.assertNotNull(date, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(serviceHour, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(serviceHour.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(buildingCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		//*/
		
		/*
		 * 1. Dado el CustomerVO se busca en la tabla WO la última con fecha de realización diferente de nulo
		 * 2. Se consulta el dealer y se adiciona a la lista 
		 */
		
		log.debug("== Inicia getDealerByLastServiceAttend/AllocatorBean ==");
		List<DealerVO> dealerList = new ArrayList<DealerVO>();
		List<Dealer> dealerListPojo;
		try {
			if (dealers == null || dealers.isEmpty() || dealers.size() == 0) {
				// Se deben de tomar todas las compañías instaladoras activas para el código postal en
				// el sistema
				dealerListPojo = dealersDAO.getAllActiveByCountryIdAndIsAlloc(postalCode.getCity().getState().getCountry().getId(), null, null);
				dealerList = UtilsBusiness.convertList(dealerListPojo, DealerVO.class);
			} else{
				this.validateDealerTypes(dealers);
				dealerList.addAll(dealers); 
			}
			
			/*1. Dado el CustomerVO se busca en la tabla WO la última con fecha de realización diferente de nulo*/
			List<WorkOrder> wo = woDao.getWorkOrderByCustomerAndRealizationDateExist(UtilsBusiness.copyObject(Customer.class, customer));
			
			if(wo.size()>0){
				/*Si existe una WO realizada para un cliente*/
				Dealer dealerTmp =  new Dealer();
				dealerTmp.setId(wo.get(0).getDealerId());
				
				if(dealerList.contains(dealerTmp)){
					/*Si el dealer se encuentra en la lista se retorna el dealer*/
					dealerList = new ArrayList<DealerVO>();
					dealerList.add(UtilsBusiness.copyObject(DealerVO.class, this.dealersDAO.getDealerByID(wo.get(0).getDealerId())));
				}else{
					/*Si el dealer no se encuentra en la lista se retorna vacia*/
					return new ArrayList<DealerVO>();
				}
			}else{
				/*Si no existe una WO realizada para un cliente*/
				return new ArrayList<DealerVO>();
			}
			
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByLastServiceAttend/AllocatorBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getDealerByLastServiceAttend/AllocatorBean ==");
		}
		return dealerList;
		
	}

	/**
	 * Caso de Uso ADS - 11 - Evaluar Habilidad Vende Instala.
	 * 
	 * La finalidad de este método es definir para una instalación de servicio 
	 * en un cliente nuevo si la compañía que realizó la venta realizará la atención del servicio
	 * @param workOrder
	 * @param dealers
	 * @param customer
	 * @param postalCode
	 * @param services
	 * @param date
	 * @param serviceHour
	 * @param buildingCode
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerVO getDealerBySaleAndInstall(WorkOrderVO workOrder, List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		UtilsBusiness.assertNotNull(dealers, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información de dealers para la evaluación de la habilidad Vende Instala");
		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información del cliente para la evaluación de la habilidad Vende Instala");
		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió el identifiador del cliente para la evaluación de la habilidad Vende Instala");
		UtilsBusiness.assertNotNull(buildingCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() +" No se encontró el código del edificio al tratar de evaluar la habilidad vende instala");
		/*
		 * 1. Dado el CustomerVO se busca en la tabla WO la �ltima con fecha de realizaci�n diferente de nulo
		 * 2. Se consulta el dealer y se adiciona a la lista 
		 */
		log.debug("== Inicia getDealerBySaleAndInstall/AllocatorBean ==");
		List<Dealer> dealerListPojo;
		try {
			if (dealers == null || dealers.isEmpty() || dealers.size() == 0) {
				dealerListPojo = dealersDAO.getAllActiveByPostalCode(UtilsBusiness.copyObject(PostalCode.class, postalCode));
				dealers = UtilsBusiness.convertList(dealerListPojo, DealerVO.class);
			}
			
			WorkOrder wo = woDao.getWorkOrderByID(workOrder.getId());
			UtilsBusiness.assertNotNull(wo, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage() + " No se encontró la información de work order con el id: "+workOrder.getId()+" en la habilidad vende instala");
			
			Dealer saleDealer = wo.getSaleDealer();
			
			if(saleDealer != null){
				for (DealerVO dealerInList : dealers) {
					if(dealerInList.getId().longValue() == saleDealer.getId().longValue()){
						return UtilsBusiness.copyObject(DealerVO.class, saleDealer);
					}
				}
			}
			return null;
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByLastServiceAttend/AllocatorBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getDealerBySaleAndInstall/AllocatorBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#getDealerByBuildingAttend(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerVO getDealerByBuildingAttend(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		UtilsBusiness.assertNotNull(dealers, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información de dealers en la evaluación de la habilidad atiende edificio");
		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información del cliente en la evaluación de la habilidad atiende edificio");
		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información del identificador del cliente en la evaluación de la habilidad atiende edificio");
		/*
		UtilsBusiness.assertNotNull(date, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(serviceHour, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(serviceHour.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(buildingCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		//*/
		
		/*
		 * 1. Dada la direcci�n del Customer (CustomerVO) se busca en la tabla WO la �ltima para ese edificio (para cualqueir Custumer)con fecha de realizaci�n diferente de nulo
		 * 2. Se consulta el dealer y se retorna 
		 */
		
		log.debug("== Inicia getDealerByBuildingAttend/AllocatorBean ==");
		List<DealerVO> dealerList = new ArrayList<DealerVO>();
		List<Dealer> dealerListPojo;
		try {
			if (dealers == null || dealers.isEmpty() || dealers.size() == 0) {
				// Se deben de tomar todas las compañías instaladoras activas para el código postal en
				// el sistema
				dealerListPojo = dealersDAO.getAllActiveByCountryIdAndIsAlloc(postalCode.getCity().getState().getCountry().getId(), null, null);
				dealerList = UtilsBusiness.convertList(dealerListPojo, DealerVO.class);
			} else{
				this.validateDealerTypes(dealers);
				dealerList.addAll(dealers); 
			}
			
			/*1. Dada la direcci�n del Customer (CustomerVO) se busca en la tabla WO la última para ese edificio (para cualqueir Custumer)con fecha de realización diferente de nulo*/
			//List<WorkOrder> wo = woDao.getWorkOrderByCustomerAddressAndRealizationDateExist(UtilsBusiness.copyObject(Customer.class, customer));
			List<WorkOrder> wo = woDao.getAttendedWorkOrdersByBuildingCodeAndCountryId(buildingCode, postalCode.getCity().getState().getCountry().getId());
			
			if(wo.size()>0){
				/*Si existe una WO realizada para un cliente*/
				long dealerId = wo.get(0).getDealerId();
				Dealer dealerTmp = dealersDAO.getDealerByID(dealerId);
				boolean exist = true;
				for(DealerVO dealer : dealerList){
					if(dealer.getId().longValue() == dealerId){
						exist = true;
					}
				}
				if(exist){
					/*Si el dealer se encuentra en la lista se retorna el dealer*/
					return UtilsBusiness.copyObject(DealerVO.class, dealerTmp);
				}else{
					/*Si el dealer no se encuentra en la lista se retorna vacia*/
					return null;
				}
			}else{
				/*Si no existe una WO realizada para un cliente*/
				return null;
			}
			
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerByBuildingAttend/AllocatorBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getDealerByBuildingAttend/AllocatorBean ==");
		}
	}
	
	/**
	 * @author jjimenezh 2010-05-20
	 *  (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#getDealersByStock(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerVO> getDealersByStock(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		
		/*
		 *Para evaluar la habilidad por inventario:
		 *1. Si el arreglo de compañías viene vacio asume que la evaluación es 
		 *para todas las compañías instaladoras registradas y activas
		 *2. Determina el inventario requerido para realizar la atención de los
		 * servicios consultando la Matriz de Inventario Requerido por Servicio 
		 * de acuerdo a los servicios ingresados.
		 * 3. Para cada compañía que viene en el arreglo debe invocar el caso 
		 * INV  47 – Consultar  las cantidades existentes de cada  elemento 
		 * en una bodega pasándole la salida 2, el cual indica si la  compañía 
		 * instaladora cuenta con inventario para atender los servicios.
		 */
		
		log.debug("== Inicia getDealersByStock/AllocatorBean ==");
		
		UtilsBusiness.assertNotNull(dealers, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibión la información de dealers en la evaluación de la habilidad de inventario ");
		UtilsBusiness.assertNotNull(customer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibión la información de cliente en la evaluación de la habilidad de inventario ");
		UtilsBusiness.assertNotNull(customer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibión la información de identificador de cliente en la evaluación de la habilidad de inventario ");
		UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibión la información de código postal en la evaluación de la habilidad de inventario ");
		UtilsBusiness.assertNotNull(postalCode.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibión la información de identificador de código postal en la evaluación de la habilidad de inventario ");
		UtilsBusiness.assertNotNull(services, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibión la información de servicios asociados a la WO en la evaluación de la habilidad de inventario ");
		/*
		UtilsBusiness.assertNotNull(date, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(serviceHour, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(serviceHour.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(buildingCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		//*/
		
		
		try {
			
			//Realizando 1:
			if(dealers.isEmpty()){
				dealers = UtilsBusiness.convertList(dealersDAO.getAllActiveByPostalCode(postalCode), DealerVO.class);
			}
			
			if(dealers.isEmpty()){
				return dealers;
			}
			
			//Realizando 2:
			List<Service> servicesPojo = new ArrayList<Service>();
			
			for (ServiceVO serviceVO : services) {
				servicesPojo.add(UtilsBusiness.copyObject(Service.class, serviceVO));
			}
			
			// List<RequiredServiceElement> serviceElements = requiredServiceElementDao.getRequiredServiceElementByServices(servicesPojo);
			 
			
			//Realizando 3:
			//for (DealerVO dealerVO : dealers) {
				//jjimenezh 2010-05-20 Se debe agregar la invocación al caso de uso INV - 47 – Consultar las cantidades existentes de cada elemento en una bodega.
				//(jjimenezh 2010-08-21 No se realizará porque esta habilidad no será evaluada)
			//}
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersByStock/AllocatorBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersByStock/AllocatorBean ==");
        }
		
		return dealers;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#getCapacityDaysParam(co.com.directv.sdii.model.vo.CountryVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long getCapacityDaysParam(CountryVO country) throws BusinessException{
		try {
			UtilsBusiness.assertNotNull(country, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			return UtilsBusiness.getNumericSystemParameter(CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_MCAPACIDAD.getCodeEntity(),
					country.getId(), systemParameterDao);
		} catch (Throwable e) {
			log.error("Error retornando el parametro de dias capacidad ",e);
			throw super.manageException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#doWorkorderValidations(co.com.directv.sdii.model.vo.WorkOrderVO, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void doWorkorderValidations(WorkOrderVO wo, Date agendaDate, ServiceHourVO serviceHour)throws BusinessException{
		log.debug("== Inicia doWorkorderValidations/AllocatorBean ==");
		/*
		 * 0. Si la fecha de agendamiento es igual a la fecha nula de la interfaz, quiere decir que hay que hacer la fecha nula
		 * Si la jornada tiene el id de a la jornada nula, hay que hacer nula la jornada
		 * 1. La fecha ingresada debe ser válida e igual o posterior a la fecha del día siguiente
		 * 2. La jornada debe corresponder a una jornada registrada en el sistema
		 * 3. Los servicios deben corresponder a servicios registrados en el sistema
		 * 4. El Código Postal debe venir en la estructura de códigos correcta
		 * 5. Validar que la work order pueda cambiar a estado "asignada" 
		 */
		try{
			
			//realizando 0
			if(AllocatorBeanLocal.NULL_AGENDATION_DATE.compareTo(agendaDate) == 0){
				agendaDate = null;
			}
			if(AllocatorBeanLocal.NULL_SERVICE_HOUR_ID.longValue() == serviceHour.getId().longValue()){
				serviceHour = null;
			}
			
			//Realizando 1:
			if(agendaDate != null){
				Calendar cal = Calendar.getInstance();
				cal.roll(Calendar.DAY_OF_YEAR, 1);
				Calendar agendaCal = Calendar.getInstance();
				agendaCal.setTime(agendaDate);
				
				Date agendaDate1 = UtilsBusiness.dateTo12am(agendaCal.getTime());
				Date actualDate = UtilsBusiness.dateTo12am(cal.getTime());
				
				if(actualDate.after(agendaDate1)){
					cal.roll(Calendar.DAY_OF_YEAR, -1);
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String message = ": La fecha de agendamiento de la work order \""+dateFormat.format(agendaDate)+"\" no es superior en por lo menos un día de la fecha actual: \""+dateFormat.format(cal.getTime())+"\"" ;
					log.error(message);
					throw new BusinessException(ErrorBusinessMessages.INVALID_DATE.getCode(), ErrorBusinessMessages.INVALID_DATE.getMessage() + message);
				}
			}

			//Realizando 2:
			if(serviceHour != null){
				ServiceHour serviHourPojo = serviceHourDAO.getServiceHourByID(serviceHour.getId());
				UtilsBusiness.assertNotNull(serviHourPojo, ErrorBusinessMessages.SERVICE_HOUR_BY_COUNTRY_NOT_FOUND.getCode(), ErrorBusinessMessages.SERVICE_HOUR_BY_COUNTRY_NOT_FOUND.getMessage() + " No se encontró la jornada con el id especificado: " + serviceHour.getId());
			}
			//Realizando 3
			WorkOrder woPojo = woDao.getWorkOrderByID(wo.getId());
			UtilsBusiness.assertNotNull(woPojo, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage() + " No se encontró la WO con el ID especificado: " + wo.getId());
			wo = UtilsBusiness.copyObject(WorkOrderVO.class, woPojo);
			
			Set<WorkOrderService> woServices = woPojo.getWorkOrderServices();
			
			for (WorkOrderService workOrderService : woServices) {
				if(CodesBusinessEntityEnum.CODIGO_SERVICE_STATUS_INACTIVE.getCodeEntity().equalsIgnoreCase(workOrderService.getService().getServiceStatus().getServiceStatusCode())){
					String message =": La work order con id: \""+woPojo.getId()+"\" contiene el servicio con id: \""+workOrderService.getService().getId()+"\" que se encuentra inactivo"; 
					log.error(message);
					throw new BusinessException(ErrorBusinessMessages.WORK_ORDER_HAS_INACTIVE_SERVICE.getCode(),ErrorBusinessMessages.WORK_ORDER_HAS_INACTIVE_SERVICE.getMessage() + message);
				}
			}
			
			//Realizando 4:
			UtilsBusiness.assertNotNull(woPojo.getPostalCode(), ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST.getMessage() + ": La work order no tiene asociado un código postal ");
			
			//Realizando 5
			stateMachine.validateStatusChangeWorkOrderByCodes(wo, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación doWorkorderValidations/AllocatorBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina doWorkorderValidations/AllocatorBean ==");
		}
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#changeProcessStatus2WorkOrder(co.com.directv.sdii.model.vo.WorkOrderVO, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void changeProcessStatus2WorkOrder(WorkOrderVO workOrder,
			String processStatusCode,String originUpdateEvent) throws BusinessException {
		log.debug("== Inicia changeProcessStatus2WorkOrder/AllocatorBean ==");
		
		try {
			ProcessStatus processStatus = processStatusDao.getProcessStatusByCode(processStatusCode);
			UtilsBusiness.assertNotNull(processStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se encontró el estado del proceso con código: " + processStatusCode);
			WorkOrder wo = woDao.getWorkOrderByID(workOrder.getId());
			wo.setProcessStatus(processStatus);
			log.info("Se actualizará una wo en el proceso de asignador.");
			woDao.updateWorkOrder(wo,originUpdateEvent);
			log.debug("== Termina changeProcessStatus2WorkOrder/AllocatorBean ==");
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación changeProcessStatus2WorkOrder/AllocatorBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Finaliza changeProcessStatus2WorkOrder/AllocatorBean ==");
		}
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#changeProcessStatus2WorkOrders(java.util.List, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void changeProcessStatus2WorkOrders(List<WorkOrderVO> workOrders,
			String processStatusCode,String originUpdateEvent) throws BusinessException {
		log.debug("== Inicia changeProcessStatus2WorkOrders/AllocatorBean ==");
		
		try {
			for (WorkOrderVO workOrderVO : workOrders) {
				this.changeProcessStatus2WorkOrder(workOrderVO, processStatusCode,originUpdateEvent);
			}
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación changeProcessStatus2WorkOrders/AllocatorBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina changeProcessStatus2WorkOrders/AllocatorBean ==");
		}
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#reportAllocatorEvent(co.com.directv.sdii.model.vo.WorkOrderVO, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void reportAllocatorEvent(WorkOrderVO workorder, String eventCode,String eventMessage, AllocatorEventMasterVO allocatorEventMasterVO) throws BusinessException {
		log.debug("== Inicia reportAllocatorEvent/AllocatorBean ==");
		try {
			UtilsBusiness.assertNotNull(workorder, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información de la Wo para reportar el evento de asignación");
			UtilsBusiness.assertNotNull(eventMessage, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se recibió información del mensaje del evento para reportar el evento de asignación");
			
			WorkOrder workOrderPojo = UtilsBusiness.copyObject(WorkOrder.class, workorder);
			AllocatorEventMaster allocatorEventMasterPojo = UtilsBusiness.copyObject(AllocatorEventMaster.class, allocatorEventMasterVO);
			if(eventCode==null){
				eventCode = CodesBusinessEntityEnum.ALLOCATOR_PROCESS_ASSIGNMENT_TRACE.getCodeEntity();
			}
			AllocatorEvent allocatorEvent = new AllocatorEvent(workOrderPojo, eventCode, eventMessage, new Date(),allocatorEventMasterPojo);
			if(allocatorEvent.getEventMessage().length()>3950){
				allocatorEvent.setEventMessage(allocatorEvent.getEventMessage().substring(0,3950-1));
			}
			
			allocatorEventDao.createAllocatorEvent(allocatorEvent);
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación reportAllocatorEvent/AllocatorBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina reportAllocatorEvent/AllocatorBean ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#getDealerBySaleAndInstall(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.String)
	 */
	@Override
	@Deprecated
	public List<DealerVO> getDealerBySaleAndInstall(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#updateWorkOrderCSRByWoCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateWorkOrderCSRByWoCode(String woCode) throws BusinessException {
		
		log.debug("== Inicia updateWorkOrderCSRByWoCode/AllocatorBean ==");
		try {
		
			WorkOrderCSR workOrderCSR = workOrderCSRDAO.getWorkOrderCSRByWoCode(woCode);
			if(workOrderCSR!=null){
				WorkorderCSRStatus workOrderCSRStatus = UtilsBusiness.getWorkorderCSRStatusTerm();
				workOrderCSR.setWorkOrderCSRStatus(workOrderCSRStatus);
				workOrderCSRDAO.updateWorkOrderCSR(workOrderCSR);
			}
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación updateWorkOrderCSRByWoCode/AllocatorBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateWorkOrderCSRByWoCode/AllocatorBean ==");
		}
		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal#assignDealer(co.com.directv.sdii.model.vo.WorkOrderVO, co.com.directv.sdii.ws.model.dto.AssignResponseDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DealerVO assignDealer(WorkOrderVO workOrder, 
			                     AssignResponseDTO responseAssign
    ) throws BusinessException {

			log.debug("== Inicia assignDealer/AllocatorBean ==");
			try {
				UtilsBusiness.assertNotNull(workOrder, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage() + " NO existe la work order con el identificador especificado: " + workOrder.getId());
				UtilsBusiness.assertNotNull(workOrder.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se especificó el identificador de la Work Order");
				
				WorkOrder woPojo = woDao.getWorkOrderByID(workOrder.getId());
				UtilsBusiness.assertNotNull(woPojo, ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_DOES_NOT_EXIST.getMessage() + " NO existe la work order con el identificador especificado: " + workOrder.getId());
				workOrder = UtilsBusiness.copyObject(WorkOrderVO.class, woPojo);
				DealerVO result = null;
				DealerVO dealerWouthCoverage = null;
				//Se creo el objeto necesario para invocar el asignador
				AssignRequestDTO request = new AssignRequestDTO();
				request.setIbsCustomerCode( workOrder.getCustomer().getCustomerCode() );
				
				//Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
				if(workOrder.getCustomer().getBuilding() != null){
					request.setIbsBuildingCode(workOrder.getCustomer().getBuilding().getId().toString());
				}
				
				request.setIbsCustomerTypeCode( workOrder.getCustomer().getCustType().getCustomerType().getCustomerTypeCode() );
				request.setIbsCustomerClassCode( workOrder.getCustomer().getCustType().getCustomerClass().getCustomerClassCode() );
				request.setCustomerCategoryId( workOrder.getCustomer().getCustType().getCustomerClass().getCategory().getId() );
				request.setPostalCode( workOrder.getCustomerAddress().getPostalCode().getPostalCodeCode() );
				request.setExecutionMode( CodesBusinessEntityEnum.SKILL_MODE_TYPE_ASSIGN.getCodeEntity() );
				request.setCountryIso2Code( workOrder.getCountry().getCountryCode() );
				request.setIbsSaleDealerCode( workOrder.getSaleDealer().getDealerCode().toString() );
				
				List<Service> woServices = this.woServiceDAO.getServicesOfWorkOrderServiceByWorkorderId( workOrder.getId() );
				
				if( woServices != null && !woServices.isEmpty() ){
					List<String> services = new ArrayList<String>();
					for( Service service : woServices ){
						services.add( service.getServiceCode() );
					}
					request.setServices(services);
					
					//Como al menos tiene que tener un servicio y por proceso no puede tener dos servicios de distinta area de negocio, tomo el primero.
					request.setBusinessAreaId(woServices.get(0).getServiceCategory().getServiceType().getBusinessAreaId());
					request.setServiceCategoryId(woServices.get(0).getServiceCategory().getId());
				}
				
				
				
				
				List<Long> woShippingOrder = new ArrayList<Long>();
				if(workOrder.getShippingOrders() != null && !workOrder.getShippingOrders().isEmpty() )
					for( ShippingOrderVO shippingOrderVO : workOrder.getShippingOrders()){
						woShippingOrder.add( shippingOrderVO.getId() );
					}
				request.setShippingOrders(woShippingOrder);
				
				WorkOrderAgenda woAgenda = this.workOrderAgendaDAO.getLastWorkOrderAgendaByWoID( workOrder.getId() );
				if( woAgenda != null ){
					request.setScheduleDate( woAgenda.getAgendationDate() );
					request.setSdiiServiceHourCode( woAgenda.getServiceHour().getServiceHoursName() );
				}
				//request.setIbsBuildingCode( workOrder.getBuildingCode() );
				request.setIbsCustomerAddressCode(workOrder.getWoAddressCode());
				
				AssignResponseDTO response = assignmentFacade.assignDealer( request );
				
				responseAssign.setHaveTrace(false);
				
				String traceInfo = response.getTraceAssignmentInformation();
				
				if(traceInfo != null && traceInfo.trim().length() > 0){
					if(traceInfo.trim().length() > 3950){
						traceInfo = traceInfo.substring(0, 3950-1);
					}
					responseAssign.setHaveTrace(true);
					responseAssign.setTraceAssignmentInformation(traceInfo);
				}

				//Se valida que si el dealer es el dealer sin cobertura validar si existe
				if( response != null && response.getDealer() != null && response.getDealer().getDealerCode() != null){
					
					if(response.getDealer().getDealerCode().longValue() != 
							AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerCodeWoutCoverage(workOrder.getCountry().getId())){
						
						result = response.getDealer();
						
					}else{
						
						dealerWouthCoverage = UtilsBusiness.copyObject(DealerVO.class, 
								                                       this.dealersDAO.getDealerByDealerCodeAndCountryId( response.getDealer().getDealerCode(),
								                                    		                                              workOrder.getCountry().getId()));
						if(dealerWouthCoverage==null){
							throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL050.getCode(), ErrorBusinessMessages.ALLOCATOR_AL050.getMessage(), Arrays.asList(new String[]{""+response.getDealer().getDealerCode().longValue()}));
						}
						result = dealerWouthCoverage;
						
					}
				}
				
				if(result != null){
					log.debug("== Inicia assignDealer/AllocatorBeanHelper/ReportarIBS ==");
					this.createSdiiAssignment(workOrder, result);
					log.debug("== Termina assignDealer/AllocatorBeanHelper/ReportarIBS ==");
				}
				return result;
				
			} catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación assignDealer/AllocatorBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina assignDealer/AllocatorBean ==");
	        }

	}
}
