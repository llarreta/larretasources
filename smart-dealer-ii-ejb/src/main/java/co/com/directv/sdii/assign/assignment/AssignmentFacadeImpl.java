/**
 * Creado 14/06/2011 09:53:05
 */
package co.com.directv.sdii.assign.assignment;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.dto.AssignmentResultDTO;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.assign.assignment.skill.factory.AssignmentSkillFactoryImpl;
import co.com.directv.sdii.assign.kpi.DealerIndicator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.assign.schedule.ScheduleManager;
import co.com.directv.sdii.assign.schedule.dto.DayServiceHourWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.assign.schedule.dto.WorkLoad;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.assign.CSRBusinessLocal;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.KpiException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.model.pojo.AuditExternalSystemSchedule;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.vo.BusinessAreaVO;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignResponseDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleResponseDTO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 14/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="AssignmentFacade")
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local({AssignmentFacade.class})
@Remote({AssignmentFacadeRemote.class})
public class AssignmentFacadeImpl implements AssignmentFacade, AssignmentFacadeRemote {

	private final static Logger log = UtilsBusiness.getLog4J(AssignmentFacadeImpl.class);
	
	@EJB(name="CSRBusinessLocal",beanInterface=CSRBusinessLocal.class)
	private CSRBusinessLocal csrBusiness;
	
	@EJB
	private CountriesCRUDBeanLocal countriesCRUDBeanLocal;

	@EJB(name="WorkOrderBusinessBeanLocal",beanInterface=WorkOrderBusinessBeanLocal.class)
	private WorkOrderBusinessBeanLocal workOrderBusinessBeanLocal;
	
	@EJB(name="WoAssignmentDAOLocal", beanInterface=WoAssignmentDAOLocal.class)
	private WoAssignmentDAOLocal workAssignmentDAOBean;

	@EJB(name="WorkOrderAgendaDAOLocal", beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal workOrderAgendaDAO;

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentFacade#assignDealer(co.com.directv.sdii.ws.model.dto.AssignRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AssignResponseDTO assignDealer(AssignRequestDTO request)throws BusinessException {
		try {
			doAssignmentValidations(request);
			SkillEvaluationDTO evaluationData = buildEvaluationData(request,null);
			AssignmentResultDTO assignmentResult = AssignmentSkillCoordinator.getInstance().doSkillEvaluations(evaluationData);
			List<DealerVO> resultDealers = assignmentResult.getSelectedDealers();
			DealerVO resultDealer = null;
			
			if(resultDealers != null && !resultDealers.isEmpty()){
				resultDealer = resultDealers.get(0);
			}

			AssignResponseDTO result = new AssignResponseDTO();
			result.setDealer(resultDealer);
			result.setTraceAssignmentInformation(assignmentResult.getTraceAssignmentInformation());
			return result;
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessageCode(), e.getMessage(), e.getParameters());
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentFacade#doSkillEvaluation(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters, String skillCode)throws AssignmentSkillException {
		try {
			AssignmentSkill skill = new AssignmentSkillFactoryImpl().buildAssignmentSkill(skillCode);
			List<DealerVO> result = skill.doSkillEvaluation(parameters);//visto***
			return result;
		} catch (BusinessException e) {
			throw new AssignmentSkillException(e.getMessageCode(), e.getMessage(), skillCode, e.getParameters());
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentFacade#getSchedule(co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AssignScheduleResponseDTO getSchedule(AssignScheduleRequestDTO request) throws BusinessException{
		AssignScheduleResponseDTO response = new AssignScheduleResponseDTO();
		WorkLoad workLoad;
		SkillEvaluationDTO skillEvaluation = buildEvaluationData(request.getServiceCharacteristics(),null);
		if(request.getDealerId() == null || request.getDealerId().longValue() <= 0){
			workLoad = ScheduleManager.getInstance().getScheduleForAllDealers(skillEvaluation,//evaluationData 
					request.getStartingDate());
		}else{
			workLoad = ScheduleManager.getInstance().getScheduleForOneDealer(skillEvaluation, request.getStartingDate(), request.getDealerId());
		}
		response.setWorkLoad(workLoad);
		return response;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentFacade#getScheduleCSR(co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AssignScheduleResponseDTO getScheduleCSR(AssignScheduleRequestDTO request) throws BusinessException, PropertiesException{
		WorkLoad workLoad;
		AssignRequestDTO assignRequestDTO = request.getServiceCharacteristics();
		AssignScheduleResponseDTO response = new AssignScheduleResponseDTO();
		WorkOrderCSRDTO workOrderCSRDTO = null;
		AuditExternalSystemSchedule auditExternalSystemSchedule = null;
		//Se trae la informacion de IBS
		Long countryId = getCountryId(request.getServiceCharacteristics().getCountryIso2Code());
		
		String serviceCode = assignRequestDTO.getServices().size() > 0 ? assignRequestDTO.getServices().get(0) : null;
		String customerClassCode = assignRequestDTO.getIbsCustomerClassCode();
		
		// se obtiene el area de negocio.
		BusinessAreaVO businessAreaVO = AssignmentFacadeLocator.getInstance().getConfigBusinessAreasFacade().getBusinessAreaByServiceCode(serviceCode);
		assignRequestDTO.setBusinessAreaId(businessAreaVO.getId());
		
		// se obtiene la categoria de cliente
		CustomerCategoryVO customerCategoryVO = AssignmentFacadeLocator.getInstance().getConfigTiposClienteFacade().getCustomerCategoryByCustomerClassCode(customerClassCode);
		assignRequestDTO.setCustomerCategoryId(customerCategoryVO.getId());
		
		// Se obtiene la categoria de servicio
		ServiceCategoryVO serviceCategoryVO = AssignmentFacadeLocator.getInstance().getServiceCategoryFacade().getServiceCategoryByServiceCode(serviceCode);
		assignRequestDTO.setServiceCategoryId(serviceCategoryVO.getId());
		
		
		//Se valida si la consulta es de un sistema externo, de ser verdadero se guardan los datos del sistema externo
		if(assignRequestDTO.isExternalSystem()){
			auditExternalSystemSchedule = buildAuditExternalSystemSchedule(assignRequestDTO,countryId, request.getStartingDate());
			csrBusiness.createAuditExternalSystemSchedule(auditExternalSystemSchedule);
		}
		
		if(assignRequestDTO.getActualStatusId()==null){
			assignRequestDTO = ScheduleManager.getInstance().getAssignRequestForWorkOrder(request,countryId);
		}
		
		//Se valida si la consulta no es de un sistema externo, de ser verdadero se guardan los datos del sistema externo
		if(!assignRequestDTO.isExternalSystem()){
			workOrderCSRDTO = buildWorkOrderCSRDTO(assignRequestDTO,countryId);
			csrBusiness.getWorkOrderCSRDTOByWoCode(workOrderCSRDTO);
		}
		
		//Se construye el skillEvaluation utilizado para asignador
		Long workOrderCSRDTOId = null; 
		if(workOrderCSRDTO!=null)
			workOrderCSRDTOId = workOrderCSRDTO.getId(); 
		
		SkillEvaluationDTO skillEvaluation = buildEvaluationData(assignRequestDTO,workOrderCSRDTOId);
		//Si la workOrder tiene algun dealer
		if(assignRequestDTO.getDealerId() == null || assignRequestDTO.getDealerId().longValue() <= 0){
			workLoad = ScheduleManager.getInstance().getScheduleForAllDealers(skillEvaluation, request.getStartingDate());
		}else{
			workLoad = ScheduleManager.getInstance().getScheduleForOneDealer(skillEvaluation, request.getStartingDate(), assignRequestDTO.getDealerId());
		}
		
		//si no trae agendas
		if(workLoad != null){
			workLoad.setWorkOrderCSRDTO(workOrderCSRDTO);
		}
		
		response.setWorkLoad(workLoad);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentFacade#getScheduleCSR(co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AssignRequestDTO getPreScheduleCSR(AssignScheduleRequestDTO request) throws BusinessException, PropertiesException{
		request.getServiceCharacteristics().setDealerId(request.getDealerId());
		AssignRequestDTO assignRequestDTO;
		//Se trae la informacion de IBS
		Long countryId = getCountryId(request.getServiceCharacteristics().getCountryIso2Code());
		assignRequestDTO = ScheduleManager.getInstance().getAssignRequestForWorkOrder(request,countryId);
		assignRequestDTO.setServiceSuperCatName(getServiceSuperCatName(assignRequestDTO.getServices()));
		return assignRequestDTO;
	}
	
	/**
	 * Metodo: Contruye un WorkOrderCSRDTO a partir de un AssignRequestDTO
	 * @param request
	 * @param countryId
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	private WorkOrderCSRDTO buildWorkOrderCSRDTO(AssignRequestDTO request,Long countryId) throws BusinessException, PropertiesException{
		WorkOrderCSRDTO workOrderCSRDTO = new WorkOrderCSRDTO();
		
		workOrderCSRDTO.setWoCode(request.getWoCode());
		workOrderCSRDTO.setScheduleDate(request.getScheduleDate());
		workOrderCSRDTO.setDealerId(request.getDealerId());
		workOrderCSRDTO.setUserId(request.getUserId());
		workOrderCSRDTO.setAddressCode(request.getIbsCustomerAddressCode());
		workOrderCSRDTO.setIbsCustomerCode(request.getIbsCustomerCode());
		workOrderCSRDTO.setIbsBuildingCode(request.getIbsBuildingCode());
		workOrderCSRDTO.setIbsCustomerTypeCode(request.getIbsCustomerTypeCode());
		workOrderCSRDTO.setIbsSaleDealerCode(request.getIbsSaleDealerCode());
		workOrderCSRDTO.setPostalCode(request.getPostalCode());
		workOrderCSRDTO.setServices(request.getServices());
		workOrderCSRDTO.setShippingOrders(request.getShippingOrders());
		workOrderCSRDTO.setCountryId(countryId);
		
		//Si es una work order para reagendar
		if(request.getDealerIdIBS() != null 
		   && request.getScheduleDateIBS() != null 
		   && (request.getActualStatusCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity()) 
			   || request.getActualStatusCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity()))){
			workOrderCSRDTO.setReSchedule(true);
        }
				
		//Si el estado en IBS es N-Pendiente/R-Pendiente verifico en HSP, si en HSP es N-Pendiente hago RG.
		if(request.getWoCode() != null 
				&& request.getDealerIdIBS() != null 
				&& request.getScheduleDateIBS() != null
				&& ( request.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity())
				|| request.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity()) ) ){
			WorkOrderVO woVO = workOrderBusinessBeanLocal.getWorkOrderByCode(request.getWoCode());
			if(woVO != null && 
					woVO.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity())){
				workOrderCSRDTO.setReSchedule(true);
			}
		}

		//Si es una work order para reagendar
		if(request.getWoCode()!=null 
			&& request.getDealerIdIBS() != null 
			&& request.getScheduleDateIBS() != null 
			&& request.getActualStatusCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity())) {
			WorkOrderVO woVO = workOrderBusinessBeanLocal.getWorkOrderByCode(request.getWoCode());
			if(woVO!=null
				&& woVO.getWorkorderStatusByPreviusStatusId().getWoStateCode().equals(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity())){ 
				try{
					List<WorkOrderAgenda> listWOAgenda = workOrderAgendaDAO.getWorkOrderAgendaByWoDealer(woVO.getId(), request.getDealerId());
					if(listWOAgenda!=null && !listWOAgenda.isEmpty()){
					List<WoAssignment> woAssignments = workAssignmentDAOBean.getWorkOrderAssignments(woVO.getId());
					if(woAssignments!=null && woAssignments.size()>=1){
						if(woAssignments.get(0).getIsActive().equals('S')){ 
							if(woAssignments.size()>=2)
								/*si el dealer de la anteúltima y la actual asignación son distintos, no continuar analisis*/
								if(woAssignments.get(1).getDealerId().equals(request.getDealerId()))
									workOrderCSRDTO.setReSchedule(true);
						}else{
							if(woAssignments.get(0).getDealerId().equals(request.getDealerId()))
								workOrderCSRDTO.setReSchedule(true);							
						}
					}	
					}
				} catch(Throwable ex){ 
			       	log.debug("== Error al tratar de ejecutar la operación getWorkOrderByCode/DealerCustomerTypesWoutPcBusinessBean ==", ex);
		        	throw new BusinessException(ex.getMessage());
				}
			}
		}

		workOrderCSRDTO.setActualStatusId(request.getActualStatusId());
		workOrderCSRDTO.setActualStatusCode(request.getActualStatusCode());
		return workOrderCSRDTO;
	}
	
	/**
	 * Metodo: Contruye un AuditExternalSystemSchedule a partir de un AssignRequestDTO
	 * @param request
	 * @param countryId
	 * @param startDate
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException
	 */
	private AuditExternalSystemSchedule buildAuditExternalSystemSchedule(AssignRequestDTO request,Long countryId, java.util.Date startDate) throws BusinessException, PropertiesException{
		
		AuditExternalSystemSchedule auditExternalSystemSchedule = new AuditExternalSystemSchedule();
		
		auditExternalSystemSchedule.setUserNameSystemExternal(request.getUserNameSystemExternal());
		User user = new User();
		user.setId(request.getUserId());
		auditExternalSystemSchedule.setUser(user);
		auditExternalSystemSchedule.setIpSystemExternal(request.getIpSystemExternal());
		auditExternalSystemSchedule.setUrlSystemExternal(request.getUrlSystemExternal());
		auditExternalSystemSchedule.setWoCode(request.getWoCode());
		auditExternalSystemSchedule.setScheduleDate(startDate);
		Country country = new Country();
		country.setId(countryId);
		auditExternalSystemSchedule.setCountry(country);
		
		return auditExternalSystemSchedule;
	}
	
	/**
	 * Metodo: Se deberán realizar las siguientes validaciones:
	 * 1. que el cliente con el código especificado exista
	 * 2. 
	 * @param request datos de la petición a ser validados
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	private void doAssignmentValidations(AssignRequestDTO request) throws BusinessException {
		String errorCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
		String errorMsj = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
		if( request == null ){
			throw new BusinessException( errorCode, errorMsj +" Parametros para evaluacion nulos");
		} else if( request.getIbsCustomerCode() == null || request.getIbsCustomerCode().equals("") ){
			throw new BusinessException( errorCode, errorMsj +" Parametro codigo de cliente para evaluacion nulo");
		} else if( request.getIbsCustomerTypeCode() == null || request.getIbsCustomerTypeCode().equals("") ){
			throw new BusinessException( errorCode, errorMsj +" Parametro codigo de tipo cliente para evaluacion nulo");
		} else if( request.getPostalCode() == null || request.getPostalCode().equals("") ){
			throw new BusinessException( errorCode, errorMsj +" Parametro codigo postal para evaluacion nulo");
		} else if( request.getServices() == null || request.getServices().isEmpty() ){
			throw new BusinessException( errorCode, errorMsj +" Parametro servicios para evaluacion nulo");
		} else if( request.getExecutionMode() == null || request.getExecutionMode().equals("") ){
			throw new BusinessException( errorCode, errorMsj +" Parametro modo de ejecicion para evaluacion nulo");
		} else if( request.getCountryIso2Code() == null || request.getCountryIso2Code().equals("") ){
			throw new BusinessException( errorCode, errorMsj +" Parametro codigo de pais para evaluacion nulo");
		}
	}

	/**
	 * Metodo: Construye los datos de evaluación
	 * @param request información del request
	 * @param workOrderCSRId id del registro de workOrderCSR
	 * @return Información para realizar la evaluación
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	private SkillEvaluationDTO buildEvaluationData(AssignRequestDTO request,Long workOrderCSRId) throws BusinessException{
		SkillEvaluationDTO evaluationData = new SkillEvaluationDTO();
		evaluationData.setAddress(null);
		evaluationData.setAddressCode(request.getIbsCustomerAddressCode());
		Long countryId = getCountryId(request.getCountryIso2Code());
		evaluationData.setCountryId(countryId);
		evaluationData.setCountryIso2Code(request.getCountryIso2Code());
		evaluationData.setDealerList(null);
		evaluationData.setExecutionMode(request.getExecutionMode());
		Long executionModeId = getExecModeId(request.getExecutionMode());
		evaluationData.setExecutionModeId(executionModeId);
		
		//Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
		//evaluationData.setIbsBuildingCode(request.getIbsBuildingCode()); 
		if (request.getIbsBuildingCode() != null){
			evaluationData.setIbsBuildingCode(request.getIbsBuildingCode()); 
		}
		evaluationData.setIbsCustomerCode(request.getIbsCustomerCode());
		
		Country c=countriesCRUDBeanLocal.getCountriesByCode(request.getCountryIso2Code());
		
		evaluationData.setCustomerCategoryId(request.getCustomerCategoryId());
		evaluationData.setBusinessAreaId(request.getBusinessAreaId());
		Long ibsCustomerTypeId = getCustomerTypeId(request.getIbsCustomerTypeCode(), request.getIbsCustomerClassCode(), c.getId());
		evaluationData.setIbsCustomerTypeId(ibsCustomerTypeId);
		evaluationData.setIbsSaleDealerCode(request.getIbsSaleDealerCode());
		evaluationData.setPostalCode(request.getPostalCode());
		Long postalCodeId = getPostalCodeId(request.getPostalCode(), countryId);
		evaluationData.setPostalCodeId(postalCodeId);
		evaluationData.setRecoveryAmout(request.getRecoveryAmout());
		evaluationData.setScheduleDate(request.getScheduleDate());
		evaluationData.setServiceHourCode(request.getSdiiServiceHourCode());
		Long serviceHourId = getServiceHourId(request.getSdiiServiceHourCode(),countryId);
		evaluationData.setServiceHourId(serviceHourId);
		evaluationData.setServices(request.getServices());
		evaluationData.setShippingOrders(request.getShippingOrders());		
		Long serviceSuperCatId = getServiceSuperCatId(request.getServices());
		evaluationData.setServiceSupercategoyId(serviceSuperCatId);
		evaluationData.setServiceCategoryId(request.getServiceCategoryId());
		evaluationData.setUserId(request.getUserId());
		evaluationData.setWorkOrderCSRId(workOrderCSRId);
		evaluationData.setCSR(request.isCSR());
		return evaluationData;
	}

	private Long getServiceSuperCatId(List<String> services) throws BusinessException {
		if(services == null || services.isEmpty()){
			throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),  ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se han recibido servicios a atender en el cliente y este parámetro es obligatorio");
		}
		ServiceSuperCategoryVO serviceSuperCat = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getServiceSuperCategoryByServiceCode(services.get(0));
		UtilsBusiness.assertNotNull(serviceSuperCat, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se encontró la super categoría de servicio por el código de servicio especificado: " + services.get(0));
		return serviceSuperCat.getId();
	}
	
	private String getServiceSuperCatName(List<String> services) throws BusinessException {
		if(services == null || services.isEmpty()){
			throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),  ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se han recibido servicios a atender en el cliente y este parámetro es obligatorio");
		}
		ServiceSuperCategoryVO serviceSuperCat = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getServiceSuperCategoryByServiceCode(services.get(0));
		UtilsBusiness.assertNotNull(serviceSuperCat, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se encontró la super categoría de servicio por el código de servicio especificado: " + services.get(0));
		return serviceSuperCat.getName();
	}

	private Long getServiceHourId(String serviceHourCode, Long countryId) throws BusinessException {
		if(serviceHourCode == null || serviceHourCode.trim().length() == 0){
			return null;
		}
		ServiceHourVO serviceHour = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getServiceHourByCode(serviceHourCode, countryId);
		UtilsBusiness.assertNotNull(serviceHour, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró la jornada por el código especificado: " + serviceHourCode + " en el país especificado: " + countryId);
		return serviceHour.getId();
	}

	private Long getPostalCodeId(String postalCode, Long countryId) throws BusinessException {
		PostalCodeVO postalCodeVo = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getPostalCodeByCode(postalCode, countryId);
		UtilsBusiness.assertNotNull(postalCodeVo, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró el código postal por el código especificado: " + postalCode + " en el país especificado: " + countryId);
		return postalCodeVo.getId();
	}

	private Long getCustomerTypeId(String customerTypeCode, String customerClassCode, Long countryId) throws BusinessException {
		CustomerClassTypeVO customerType = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getCustomerClassTypeByCode(customerClassCode, customerTypeCode, countryId);
		UtilsBusiness.assertNotNull(customerType, ErrorBusinessMessages.CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST.getMessage());
		return customerType.getId();
	}
	
	private Long getCustomerTypeClassId(String customerClassCode, String customerTypeCode, Long countryId) throws BusinessException {
		CustomerClassTypeVO customerClassType = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getCustomerClassTypeByCode(customerClassCode,customerTypeCode, countryId);
		UtilsBusiness.assertNotNull(customerClassType, ErrorBusinessMessages.CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.CUSTOMER_CLASS_OR_TYPE_DOES_NOT_EXIST.getMessage());
		return customerClassType.getId();
	}
	
	
	private Long getExecModeId(String executionMode) throws BusinessException {
		SkillModeTypeVO skillModeType = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getSkillModeTypeByCode(executionMode);
		UtilsBusiness.assertNotNull(skillModeType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró la modalidad de ejecución por el código especificado: " + executionMode);
		return skillModeType.getId();
	}

	private Long getCountryId(String countryIso2Code) throws BusinessException {
		CountryVO country = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getCountryByCode(countryIso2Code);
		UtilsBusiness.assertNotNull(country, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se encontró país por el código especificado: " + countryIso2Code);
		return country.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#calculateAndPersistDealerIndicator(co.com.directv.sdii.assign.kpi.DealerIndicator, co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void calculateAndPersistDealerIndicator(DealerIndicator dealerIndicator, DealerIndicatorDTO dealerIndicatorDto) throws BusinessException {
		try {
			DealerIndicatorResultDTO dealerIndicatorResultDTO = dealerIndicator.calculateIndicator(dealerIndicatorDto);
			if(dealerIndicatorResultDTO == null) {
				log.warn("el resultado del calculo del KPI fue nulo. " + getExceptionComplementInfo(dealerIndicatorDto));
			} 
		} catch (KpiException e) {
			log.error("no se pudo calcular el KPI. " + getExceptionComplementInfo(dealerIndicatorDto), e);
		} catch (BusinessException e) {							
			log.error("no se pudo guardar el resultado del kpi" + getExceptionComplementInfo(dealerIndicatorDto), e);
		}
	}
	
	private String getExceptionComplementInfo(DealerIndicatorDTO dealerIndicatorDto) {
		return new StringBuffer("kpiId = ")
		.append(dealerIndicatorDto != null ? dealerIndicatorDto.getIndicatorId() : "null")
		.append(", dealerId = ").append(dealerIndicatorDto != null ? dealerIndicatorDto.getDealerId() : "null").toString();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentFacade#agendaWorkOrdersCSR(co.com.directv.sdii.model.dto.WorkOrderCSRDTO)
	 */
	public void agendaWorkOrdersCSR(WorkOrderCSRDTO request) throws BusinessException{
		csrBusiness.agendaWorkOrdersCSR(request);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentFacade#traceAssignmentWorkOrderCSR(java.lang.Long, java.lang.String)
	 */
	public void traceAssignmentWorkOrderCSR(Long id, String trace) throws BusinessException{
		csrBusiness.traceAssignmentWorkOrderCSR(id, trace);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.AssignmentFacade#checkAboveScheduled(co.com.directv.sdii.assign.schedule.dto.DayServiceHourWorkLoad, int)
	 */
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduledCSR(DayServiceHourWorkLoad dayServiceHourWorkLoad,
			                                                        int quantityWorkOrder) throws BusinessException{
		DealerWorkLoadCapacityCriteriaDTO dealerWorkLoadCapacityCriteriaDTO = new DealerWorkLoadCapacityCriteriaDTO();
		double usedCapacity = 0d;
		double maxCapacity = 0d;
		dealerWorkLoadCapacityCriteriaDTO.setMaxCapacity(dayServiceHourWorkLoad.getMaxCapacity());
		dealerWorkLoadCapacityCriteriaDTO.setUsedCapacity(dayServiceHourWorkLoad.getUsedCapacity());
		dealerWorkLoadCapacityCriteriaDTO.setAboveScheduled(false);
		if(dayServiceHourWorkLoad!=null){
			
			maxCapacity = dayServiceHourWorkLoad.getMaxCapacity();
			usedCapacity = dealerWorkLoadCapacityCriteriaDTO.getUsedCapacity()+quantityWorkOrder;
			
			if(usedCapacity > maxCapacity){
				dealerWorkLoadCapacityCriteriaDTO.setAboveScheduled(true);
			}else{
				dealerWorkLoadCapacityCriteriaDTO.setAboveScheduled(false);
			}
			
		}
		return dealerWorkLoadCapacityCriteriaDTO;
	}
	
}
