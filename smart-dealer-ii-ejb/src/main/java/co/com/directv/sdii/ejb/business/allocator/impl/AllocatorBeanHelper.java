/**
 * Creado 11/11/2010 16:06:00
 */
package co.com.directv.sdii.ejb.business.allocator.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.schedule.dto.ScheduleColorsConfig;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal;
import co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal;
import co.com.directv.sdii.ejb.business.assign.ServiceSuperCategoryBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.core.WoInfoEsbServiceBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.vo.AllocatorEventMasterVO;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.SystemParameterVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.ws.model.dto.AssignResponseDTO;

/**.
 * <Descripcion>
 *
 * Fecha de Creación: 11/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name = "AllocatorBeanHelperLocal",
           mappedName = "ejb/AllocatorBeanHelperLocal")
public class AllocatorBeanHelper extends BusinessBase
                                 implements AllocatorBeanHelperLocal {

    @EJB(name = "AllocatorBeanLocal",
         beanInterface = AllocatorBeanLocal.class)
    private AllocatorBeanLocal allocatorBean;

    @EJB(name = "CoreWOBusinessLocal",
         beanInterface = CoreWOBusinessLocal.class)

    private CoreWOBusinessLocal coreWOBusiness;

	@EJB(name="ServiceSuperCategoryBusinessBeanLocal",beanInterface=ServiceSuperCategoryBusinessBeanLocal.class)
    private ServiceSuperCategoryBusinessBeanLocal serviceSuperCategoryBusiness;
	
	@EJB(name="ConfigParametrosBusinessLocal",beanInterface=ConfigParametrosBusinessLocal.class)
	private ConfigParametrosBusinessLocal configParametrosBusiness;
	
	@EJB(name="WoInfoEsbServiceBusinessLocal",beanInterface=WoInfoEsbServiceBusinessLocal.class)
	private WoInfoEsbServiceBusinessLocal woInfoEsbServiceBusinessLocal;
	
    private final static Logger log = UtilsBusiness.getLog4J(AllocatorBeanHelper.class);
	
	private BusinessException manageAllocatorException(WorkOrderVO wo, Throwable ex,AllocatorEventMasterVO allocatorEventMasterVO) {
		try {
			BusinessException exc = this.manageException(ex);
			String errorCode = exc.getMessageCode();
			String message = exc.getMessage();
			log.warn("Hubo un error al tratar de asignar la work order con código: \"" + wo.getWoCode() + "\" Se realizará el registro del evento en la tabla ALLOCATOR_EVENTS el código de error es: \" " + errorCode + " \" errorMessage \"" + message + "\"");
			allocatorBean.changeProcessStatus2WorkOrder(wo, CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_EXECUTED_WITH_ERRORS.getCodeEntity(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
			reportAllocatorEvent(wo, errorCode, message,allocatorEventMasterVO);
			return exc;
		} catch (Throwable e) {
			return super.manageException(e);
		}
	}
	
	private BusinessException manageAllocatorException(WorkOrderVO wo, Throwable ex,
			Long woInfoEsbServiceId, Long countryId) {
		try {
			BusinessException exc = this.manageException(ex);
			String errorCode = exc.getMessageCode();
			String message = exc.getMessage();
			log.warn("Hubo un error al tratar de asignar la work order con código: \"" + wo.getWoCode() + "\" Se realizará el registro del evento en la tabla ALLOCATOR_EVENTS el código de error es: \" " + errorCode + " \" errorMessage \"" + message + "\"");
			allocatorBean.changeProcessStatus2WorkOrder(wo, CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_EXECUTED_WITH_ERRORS.getCodeEntity(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
			woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(woInfoEsbServiceId,null,ex.getMessage(),
					CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_ALLOCATOR.getCodeEntity(), 
				CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity(),countryId, false);
			return exc;
		} catch (Throwable e) {
			return super.manageException(e);
		}
	}
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#assignDealer(co.com.directv.sdii.model.vo.WorkOrderVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DealerVO assignDealer(WorkOrderVO workOrder, 
			                     AllocatorEventMasterVO allocatorEventMasterVO)
			throws BusinessException {
		
		log.debug("== Inicia assignDealer/AllocatorBeanHelper ==");
		
		DealerVO dealer = null;
		try{
			dealer = allocatorBean.assignDealer(workOrder,allocatorEventMasterVO);
			log.debug("== Termina assignDealer/AllocatorBeanHelper ==");
		}catch(Throwable ex){
			log.error("== Error al tratar de obtener el dealer para asignarlo a la work order con id: \""+workOrder.getId()+"\" assignDealer/AllocatorBean ==");
			BusinessException be = this.manageException(ex);
			try {
				//allocatorBean.changeWOStatus2Pending(workOrder, CodesBusinessEntityEnum.WORKORDER_REASON_PENDING_TECH_RESOURCES_ARENT_ENOUGHT.getCodeEntity(), CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity(), false);
				reportAllocatorEvent(workOrder, be.getMessageCode(), be.getMessage(),allocatorEventMasterVO);
			} catch (Throwable e) { 
				log.error(e); e.printStackTrace();
			}
			throw be;
		}
		
		try{
			if(dealer != null){
				log.debug("== Inicia assignDealer/AllocatorBeanHelper/ReportarIBS ==");
				allocatorBean.createSdiiAssignment(workOrder, dealer);
				log.debug("== Termina assignDealer/AllocatorBeanHelper/ReportarIBS ==");
			}else{
				allocatorBean.changeProcessStatus2WorkOrder(workOrder, CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_PROCESSED_BUT_NOT_ASSIGNED.getCodeEntity(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
			}
		}catch(Throwable ex){
			log.error("== Error al tratar de obtener el dealer para asignarlo a la work order con id: \""+workOrder.getId()+"\" assignDealer/AllocatorBean ==");
			throw manageAllocatorException(workOrder, ex,allocatorEventMasterVO);
		}
		return dealer;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#changeProcessStatus2WorkOrder(co.com.directv.sdii.model.vo.WorkOrderVO, java.lang.String)
	 */
	@Override
	public void changeProcessStatus2WorkOrder(WorkOrderVO workOrder,
			String processStatusCode) throws BusinessException {
			
		log.debug("== Inicia changeProcessStatus2WorkOrder/AllocatorBeanHelper ==");
		
		try{
			allocatorBean.changeProcessStatus2WorkOrder(workOrder, processStatusCode,CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
		}catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación changeProcessStatus2WorkOrder/AllocatorBeanHelper ==");
			throw this.manageException(ex);
		}
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#changeProcessStatus2WorkOrders(java.util.List, java.lang.String)
	 */
	@Override
	public void changeProcessStatus2WorkOrders(List<WorkOrderVO> workOrders,
			String processStatusCode) throws BusinessException {
		
		log.debug("== Inicia changeProcessStatus2WorkOrders/AllocatorBeanHelper ==");
		
		try{
			allocatorBean.changeProcessStatus2WorkOrders(workOrders, processStatusCode,CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
		}catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación changeProcessStatus2WorkOrders/AllocatorBeanHelper ==");
			throw this.manageException(ex);
		}
		
	}

//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#doWorkorderValidations(co.com.directv.sdii.model.vo.WorkOrderVO, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO)
//	 */
//	@Override
//	public void doWorkorderValidations(WorkOrderVO wo, Date agendaDate,
//			ServiceHourVO serviceHour) throws BusinessException {
//		try{
//			allocatorBean.doWorkorderValidations(wo, agendaDate, serviceHour);
//		}catch(Throwable ex){
//			throw manageAllocatorException(wo);
//		}
//	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#getCapacityDaysParam(co.com.directv.sdii.model.vo.CountryVO)
	 */
	@Override
	public Long getCapacityDaysParam(CountryVO country)
			throws BusinessException {
		return allocatorBean.getCapacityDaysParam(country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#getDealerByBuildingAttend(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.String)
	 */
	@Override
	public DealerVO getDealerByBuildingAttend(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		return allocatorBean.getDealerByBuildingAttend(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#getDealerByLastServiceAttend(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.String)
	 */
	@Override
	public List<DealerVO> getDealerByLastServiceAttend(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		return allocatorBean.getDealerByLastServiceAttend(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#getDealerBySaleAndInstall(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.String)
	 */
	@Override
	public List<DealerVO> getDealerBySaleAndInstall(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		return allocatorBean.getDealerBySaleAndInstall(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#getDealersByStock(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.String)
	 */
	@Override
	public List<DealerVO> getDealersByStock(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		return allocatorBean.getDealersByStock(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal#reportAllocatorEvent(co.com.directv.sdii.model.vo.WorkOrderVO, java.lang.String, java.lang.String)
	 */
	@Override
	public void reportAllocatorEvent(WorkOrderVO workorder, String eventCode,
			String eventMessage,AllocatorEventMasterVO allocatorEventMasterVO) throws BusinessException {
		allocatorBean.reportAllocatorEvent(workorder, eventCode, eventMessage,allocatorEventMasterVO);
	}
	
	/**
	 * Metodo: Implementacion de la Logica de asignador para un pais dado
	 * @param countryId id del pais para el que se ejecuta el proceso de asignador
	 * @throws BusinessException excepcion encapsulada en caso de fallar el proceso de asignador para dejar los eventos en la tabla de procesamiento
	 * 								en paralelo de core y asignador
	 * @author Aharker
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void searchWOAndSendEventParalle(Long countryId) throws BusinessException{
		log.debug("== Inicia assignDealer/AllocatorBean ==");
		try {
			//Se trae las wo prendientes por asignacion
			Long woId=null;
			List<WorkOrderVO> wo2Alloc = coreWOBusiness.getWorkOrdersByPage(countryId, woId, "'"+CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_NOT_STARTED.getCodeEntity()+"'");
			
			//Cambiar de estado las WO que van a ser asignadas
			List<String> status = new ArrayList<String>();
			status.add(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_PENDING_REPROCESSING.getCodeEntity());
			status.add(CodesBusinessEntityEnum.SDII_WO_INFO_ESB_PENDING.getCodeEntity());
			List<String> codesIgnore=woInfoEsbServiceBusinessLocal.getPendingProccessForCore(wo2Alloc, status, CodesBusinessEntityEnum.SDII_WO_INFO_ESB_CORE.getCodeEntity());
			if(wo2Alloc!=null){
				int tam=wo2Alloc.size();
				for(int i=tam-1; i>=0; --i){
					if(codesIgnore.contains(wo2Alloc.get(i).getWoCode())){
						wo2Alloc.remove(i);
					}
				}
			}
			
			log.info("Se encontraron " + wo2Alloc.size() + " para asignar");
			
			allocatorBean.changeProcessStatus2WorkOrders(wo2Alloc, CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_STARTED.getCodeEntity(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_PARALLEL.getCodeEntity());
			createProccesAllocator(countryId, wo2Alloc);			
		}catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación assignDealer/AllocatorBean ==");
			throw this.manageException(ex);
		}
	}
	
	/**
	 * Metodo que crea los eventos de procesamiento en paralelo de asignador en base de datos
	 * @param countryId id del pais para el cual se encolaran loos eventos de asignador
	 * @param wo2Alloc work orders que se encolaran para el proceso en paralelo de asignador
	 * @throws BusinessException
	 * @author Aharker
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void createProccesAllocator(Long countryId, List<WorkOrderVO> wo2Alloc) throws BusinessException{
		for(WorkOrderVO wovo : wo2Alloc){
			woInfoEsbServiceBusinessLocal.createWoInfoEsbServiceForAllocator(wovo.getCountry().getId(), wovo.getId(), wovo.getWoCode(), wovo.getCustomer().getCustomerCode());
		}
	}
	
	/**
	 * Metodo: Implementacion de la Logica de asignador para un pais dado
	 * @param countryId id del pais para el que se ejecuta el proceso de asignador
	 * @throws BusinessException excepcion encapsulada en caso de fallar el proceso de asignador para dejar los eventos en la tabla de procesamiento
	 * 								en paralelo de core y asignador
	 * @author Aharker
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void allocWorkOrders(Long countryId) throws BusinessException {

		searchWOAndSendEventParalle(countryId);

	}
	
	public ScheduleColorsConfig loadScheduleColorsConfig(Long serviceSuperCategoryByID,Long countryId) throws BusinessException {
		ScheduleColorsConfig result = new ScheduleColorsConfig();
		log.debug("== Inicia loadScheduleColorsConfig/AllocatorBean ==");
		try {
			
			SystemParameterVO scheduleLimitParam = getScheduleLimitParam(serviceSuperCategoryByID,countryId);
			
			SystemParameterVO scheduleAvailableColorParam = configParametrosBusiness.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_AVAILABLE_COLOR.getCodeEntity(), countryId);
			SystemParameterVO scheduleUnavailableColorParam = configParametrosBusiness.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_UNAVAILABLE_COLOR.getCodeEntity(), countryId);
			
			if(scheduleAvailableColorParam == null){
				throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage() +" no se encontró el parámetro con código: " + CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_AVAILABLE_COLOR.getCodeEntity() + " en el país con id: " + countryId);
			}
			if(scheduleUnavailableColorParam == null){
				throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage() +" no se encontró el parámetro con código: " + CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_SCHEDULE_UNAVAILABLE_COLOR.getCodeEntity() + " en el país con id: " + countryId);
			}
			Double limit = new Double(scheduleLimitParam.getValue());
			String availableColor = scheduleAvailableColorParam.getValue();
			String unavailableColor = scheduleUnavailableColorParam.getValue();
			
			result.setUnavailableColor(unavailableColor);
			result.setAvailableColor(availableColor);
			result.setScheduleLimit(limit);
			
		} catch (PropertiesException e) {
			log.error("== Error al tratar de ejecutar la operación loadScheduleColorsConfig/AllocatorBean ==");
			throw this.manageException(e);
		} finally {
			log.debug("== Termina loadScheduleColorsConfig/AllocatorBean ==");
		}
		return result;
	}

    private SystemParameterVO getScheduleLimitParam(Long serviceSuperCategoryByID,
    		                                        Long countryId)  throws BusinessException {

		SystemParameterVO scheduleLimitParam = null;
		String parameterCode = "";
		log.debug("== Inicia getScheduleLimitParam/AllocatorBean ==");
		try {
		
			String codeService = serviceSuperCategoryBusiness.getServiceSuperCategoryByID(serviceSuperCategoryByID).getCode();
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
			
			scheduleLimitParam = configParametrosBusiness.getSystemParameterByCodeAndCountryId(parameterCode, countryId);
			if(scheduleLimitParam == null){
				throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage() +" no se encontró el parámetro con código: " + parameterCode + " en el país con id: " + countryId);
			}
			return scheduleLimitParam;
		} catch (PropertiesException e) {
			log.error("== Error al tratar de ejecutar la operación getScheduleLimitParam/AllocatorBean ==");
			throw this.manageException(e);
		} finally {
          log.debug("== Termina getScheduleLimitParam/AllocatorBean ==");
		}

	}
    
	/**
	 * 
	 * @param woCode codigo de la orden de trabajo a la cual se le colocara el dealer mediante el proceso de asignador
	 * @param countryCode codigo del pais al que pertenece la WO
	 * @param woInfoEsbServiceId id del registro a procesar de procesamiento en paralelo de asignador
	 * @param countryId id del pais al que pertenece la WO
	 * @throws BusinessException Excepcion de negocio que encapsula cualquier error en la asignacion de un dealer a una wo
	 * @author Aharker
	 * @throws PropertiesException 
	 */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void assignDealer(String woCode, String countryCode,
			Long woInfoEsbServiceId, Long countryId) throws BusinessException, PropertiesException {
		log.debug("== inicia assignDealer/AllocatorBeanHelper ==");
		WorkOrderVO workOrderVO = null;
		AssignResponseDTO responseAssignDealer= new AssignResponseDTO();
		responseAssignDealer.setHaveTrace(false);
		try {
			workOrderVO = coreWOBusiness.getWorkOrderByCodeAndCountry(woCode, countryCode);

			//Valida que entre el proceso de descarga y la asignacion automatica no se haya asignado un dealer manualmente.
			//En caso que se haya asignado un dealer manualmente, se debe ignorar la asignación automatica.
			Long woId=workOrderVO.getId();
			//Se obtiene las WOs que tengan asignado un dealerDummy o no tengan asignado dealer, el woId sea el que se pasa por param y otros chequeos.
			List<WorkOrderVO> woToAssignDealer = coreWOBusiness.getWorkOrdersByPage(countryId, woId, "'"+CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_NOT_STARTED.getCodeEntity()+"'");
			if(woToAssignDealer == null){
				String logStr = "Un dealer se ha asignado manualmente a la WO con código: " + workOrderVO.getWoCode() +" previo a la asignación automática. Se ignora la asignación automática. ";
				log.info(logStr);
				woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(woInfoEsbServiceId,CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity(),logStr, 
						CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_ALLOCATOR.getCodeEntity(),
						CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity(), countryId, false);
				return;
			}
			
			log.info("Se iniciará el proceso de asignación para la WorkOrder con código: "+ workOrderVO.getWoCode());
			
			DealerVO dealer = allocatorBean.assignDealer(workOrderVO,responseAssignDealer);
			
			//se genera la traza de asignador
			woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(woInfoEsbServiceId,null,responseAssignDealer.getTraceAssignmentInformation(),null,
					CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity(), countryId, false);
			
			//Actualiza process status de la Wo
			if (dealer != null) {
				allocatorBean.changeProcessStatus2WorkOrder(workOrderVO,CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_CORRECT_FINISHED.getCodeEntity(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
			}else{
				allocatorBean.changeProcessStatus2WorkOrder(workOrderVO, CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_PROCESSED_BUT_NOT_ASSIGNED.getCodeEntity(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
			}

			//Actualiza la Wo si es un proceso de CSR
			allocatorBean.updateWorkOrderCSRByWoCode(workOrderVO.getWoCode());

			woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(woInfoEsbServiceId,CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED.getCodeEntity(),"Proceso terminado exitosamente",null,
					CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity(), countryId, true);
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la operación assignDealer/AllocatorBean ==");
			allocatorBean.changeProcessStatus2WorkOrder(workOrderVO,CodesBusinessEntityEnum.ALLOCATOR_PROCESS_STATUS_EXECUTED_WITH_ERRORS.getCodeEntity(),CodesBusinessEntityEnum.SDII_UPDATE_WORKORDER_ORIGIN_UPDATE_EVENT_ALLOCATOR.getCodeEntity());
			allocatorBean.updateWorkOrderCSRByWoCode(workOrderVO.getWoCode());
			
			
			//>Auditoria Andres
			if(responseAssignDealer.isHaveTrace()){
				woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(woInfoEsbServiceId,null,responseAssignDealer.getTraceAssignmentInformation(),null,
						CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity(), countryId, false);
			}
			
			woInfoEsbServiceBusinessLocal.generateLogCoreAndAllocator(woInfoEsbServiceId,CodesBusinessEntityEnum.SDII_WO_INFO_ESB_FINISHED_WITH_ERRORS.getCodeEntity(),e.getMessage(), 
				CodesBusinessEntityEnum.SDII_WO_INFO_ESB_NOTIFICATION_TYPE_ALLOCATOR.getCodeEntity(),
				CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity(), countryId, true);
			

		} finally {
			log.debug("== finaliza assignDealer/AllocatorBeanHelper ==");
		}

	}

	
    
}
