package co.com.directv.sdii.ejb.business.assign.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.kpi.KPIFacade;
import co.com.directv.sdii.assign.kpi.KPIFacadeImpl;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.DealerDetailBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.EmailType;
import co.com.directv.sdii.model.pojo.KpiConfiguration;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.KpiGeneratorDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.PostalCodesDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal;

@Stateless(name="KpiGeneratorBusinessBeanLocal",mappedName="ejb/KpiGeneratorBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiGeneratorBusinessBean extends BusinessBase implements KpiGeneratorBusinessBeanLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(KpiGeneratorBusinessBean.class);
	
	@EJB(name="KpiGeneratorDAOLocal",beanInterface=KpiGeneratorDAOLocal.class)
	private KpiGeneratorDAOLocal kpiGeneratorDAO;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB(name="DealerDetailBusinessBeanLocal", beanInterface=DealerDetailBusinessBeanLocal.class)
	private DealerDetailBusinessBeanLocal dealerDetailBusinessBean;
	
	@EJB(name="DealerCoverageDAOLocal", beanInterface=DealerCoverageDAOLocal.class)
	private DealerCoverageDAOLocal dealerCoverageDAO;
	
	@EJB(name="KpiConfigurationDAOLocal", beanInterface=KpiConfigurationDAOLocal.class)
	private KpiConfigurationDAOLocal kpiConfigurationDAO;
	
	@EJB(name="PostalCodesDAOLocal", beanInterface=PostalCodesDAOLocal.class)
	private PostalCodesDAOLocal postalCodesDAO;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDAO;
	
	@EJB(name="EmailTypeDAOLocal", beanInterface=EmailTypeDAOLocal.class)
	private EmailTypeDAOLocal emailTypeDAO;
	
	@EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
	
	@EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal daoDealers;
	
	@EJB(name="ServiceSuperCategoryDAOLocal", beanInterface=ServiceSuperCategoryDAOLocal.class)
    private ServiceSuperCategoryDAOLocal serviceSuperCategoryDAO;
	
    
	/**
	 * 
	 * Metodo: Valida que los atributos del DTO para calculador el indicador no vengan nulos
	 * @param dealerIndicatorDto parametro a ser validado
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void validateDealerIndicatorDTO(DealerIndicatorDTO dealerIndicatorDto) throws BusinessException{
		log.debug("== Inicia validateDealerIndicatorDTO/KpiGeneratorBusinessBean ==");
		try{
			//Valida que ninguno de los datos del dto venga nulo
			String paramsNullCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String paramsNullMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			if( dealerIndicatorDto == null ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			} else if( dealerIndicatorDto.getCountryId() == null || !(dealerIndicatorDto.getCountryId().longValue() > 0) ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			} else if( dealerIndicatorDto.getDealerId() == null || !(dealerIndicatorDto.getDealerId().longValue() > 0) ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			} else if( dealerIndicatorDto.getServiceSuperCategoryId() == null || !(dealerIndicatorDto.getServiceSuperCategoryId().longValue() > 0) ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			}  else if( dealerIndicatorDto.getZoneTypeId() == null || !(dealerIndicatorDto.getZoneTypeId().longValue() > 0) ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			}
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación validateDealerIndicatorDTO/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina validateDealerIndicatorDTO/KpiGeneratorBusinessBean ==");
		}	
	}
	
	/**
	 * 
	 * Metodo: Obtiene los ids de los estados atentida y finalizada
	 * @return List<Long> lista de identificadores de los estados de las WO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private List<Long> getRealizedAndFinishedWoStatus() throws BusinessException {
		log.debug("== Inicia getValidWoStatusTofindKpisWo/KpiGeneratorBusinessBean ==");
		try{
			//Se consultan los ids de los estados
			List<Long> woStatus = new ArrayList<Long>();
			Long realizedStatusId = CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getIdEntity( WorkorderStatus.class.getName() );
			if( realizedStatusId != null && realizedStatusId.longValue() > 0 )
				woStatus.add( realizedStatusId );
			Long finishedStatusId = CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getIdEntity( WorkorderStatus.class.getName() );
			if( finishedStatusId != null && finishedStatusId.longValue() > 0 )
				woStatus.add( finishedStatusId );
			
			return woStatus;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación getValidWoStatusTofindKpisWo/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getValidWoStatusTofindKpisWo/KpiGeneratorBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Obtiene los ids de los estados atentida, finalizada y cancelada
	 * @return List<Long> lista de identificadores de los estados de las WO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private List<Long> getRealizedAndFinishedAndCancelledWoStatus() throws BusinessException {
		log.debug("== Inicia getValidWoStatusTofindKpisWo/KpiGeneratorBusinessBean ==");
		try{
			//Se consultan los ids de los estados
			List<Long> woStatus = new ArrayList<Long>();
			Long realizedStatusId = CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getIdEntity( WorkorderStatus.class.getName() );
			if( realizedStatusId != null && realizedStatusId.longValue() > 0 )
				woStatus.add( realizedStatusId );
			Long finishedStatusId = CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getIdEntity( WorkorderStatus.class.getName() );
			if( finishedStatusId != null && finishedStatusId.longValue() > 0 )
				woStatus.add( finishedStatusId );
			Long canceledStatusId = CodesBusinessEntityEnum.WORKORDER_STATUS_CANCELED.getIdEntity( WorkorderStatus.class.getName() );
			if( canceledStatusId != null && canceledStatusId.longValue() > 0 )
				woStatus.add( canceledStatusId );
			return woStatus;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación getValidWoStatusTofindKpisWo/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getValidWoStatusTofindKpisWo/KpiGeneratorBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#calculateCycleTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double calculateCycleTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException {
		log.debug("== Inicia calculateCycleTimeIndicator/KpiGeneratorBusinessBean ==");
		try{
			//Valida el dto
			this.validateDealerIndicatorDTO(dealerIndicatorDto);
			
			//Se consultan la cantidad WO que cumplen con el filtro del DTO
			return kpiGeneratorDAO.getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateCycleTimeIndicator(dealerIndicatorDto,startDate,endDate,getRealizedAndFinishedWoStatus());
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateCycleTimeIndicator/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateCycleTimeIndicator/KpiGeneratorBusinessBean ==");
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#calculateOnTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double calculateOnTimeIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate)throws BusinessException {
		log.debug("== Inicia calculateOnTimeIndicator/KpiGeneratorBusinessBean ==");
		try{
			//Valida el dto
			this.validateDealerIndicatorDTO(dealerIndicatorDto);
			
			Long onTimeSystemHourquantity  = UtilsBusiness.getNumericSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_KPI_ONT_TIME_HOUR_QUANTITY.getCodeEntity(), dealerIndicatorDto.getCountryId() , systemParameterDAO);
			if( onTimeSystemHourquantity == null || onTimeSystemHourquantity.longValue() <= 0 )
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL003.getCode(),ErrorBusinessMessages.ALLOCATOR_AL003.getMessage());
			
			return kpiGeneratorDAO.getWoIdAndRealizationDateByIndicatorDtoInStatusToCalculateOnTimeIndicator(dealerIndicatorDto,startDate,endDate,getRealizedAndFinishedWoStatus(),onTimeSystemHourquantity);
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateOnTimeIndicator/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateOnTimeIndicator/KpiGeneratorBusinessBean ==");
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#calculateBackLogInDaysIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double calculateBackLogInDaysIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate) throws BusinessException {
		log.debug("== Inicia calculateBackLogInDaysIndicator/KpiGeneratorBusinessBean ==");
		try{
			//Valida el dto
			this.validateDealerIndicatorDTO(dealerIndicatorDto);
			
			Double response = 0D;
			
			Long woAsignToDealerAndNotEndStatus = kpiGeneratorDAO.getWoToCalculateBackLogIndicator(dealerIndicatorDto, startDate, endDate, getRealizedAndFinishedAndCancelledWoStatus()); 
			
			if( woAsignToDealerAndNotEndStatus != null && woAsignToDealerAndNotEndStatus.longValue() > 0 ){
				Double dealerCapacity = calculateDealerDailyCapacity(dealerIndicatorDto, endDate);
				if(dealerCapacity != null && dealerCapacity.doubleValue() > 0D  )
					response = woAsignToDealerAndNotEndStatus.doubleValue() / dealerCapacity.doubleValue();
			}
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateBackLogInDaysIndicator/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateBackLogInDaysIndicator/KpiGeneratorBusinessBean ==");
		}		
	}

	/**
	 * Metodo: Calcula la capacidad diaria de atención dado el promedio de atencion diaria de la
	 * compañía en la super categoria de servicio en los últmos n días donde n es un parámetro
	 * del sistema
	 * @param dealerIndicatorDto información con el identificador del dealer y la super categoría de servicio
	 * @param endDate fecha final a la cual será restado el n para obtener la fecha inicial a partir de la que se debe obtener el promedio 
	 * @return cantidad de work orders diaria en promedio que ha atendido la compañía 
	 * @author jjimenezh
	 * @throws PropertiesException 
	 * @throws BusinessException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	private Double calculateDealerDailyCapacity(DealerIndicatorDTO dealerIndicatorDto, Date endDate) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		Long dayCount2Average = UtilsBusiness.getNumericSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_DAY_COUNT_2_CALCULATE_DAILY_CAPACITY.getCodeEntity(), dealerIndicatorDto.getCountryId(), systemParameterDAO);
		if(dayCount2Average == null || dayCount2Average.longValue() <= 0){
			throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL021.getCode(), ErrorBusinessMessages.ALLOCATOR_AL021.getMessage(), Arrays.asList(new String[]{CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_DAY_COUNT_2_CALCULATE_DAILY_CAPACITY.getCodeEntity()}));
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.DAY_OF_YEAR, dayCount2Average.intValue() * -1);
		Date startDate = cal.getTime();
		
		Double woRealizedAndFinishedCount = new Double(kpiGeneratorDAO.getWoAttendedOrFinishedCountToCalculateBackLogIndicator(dealerIndicatorDto, startDate, endDate, getRealizedAndFinishedWoStatus()));
		Double result = woRealizedAndFinishedCount / dayCount2Average.intValue();
				
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#calculateSoS90Indicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double calculateSoS90Indicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate) throws BusinessException {
		log.debug("== Inicia calculateSoS90Indicator/KpiGeneratorBusinessBean ==");
		try{
			//Valida el dto
			String paramsNullCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String paramsNullMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			if( dealerIndicatorDto == null ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			} else if( dealerIndicatorDto.getCountryId() == null || !(dealerIndicatorDto.getCountryId().longValue() > 0) ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			} else if( dealerIndicatorDto.getDealerId() == null || !(dealerIndicatorDto.getDealerId().longValue() > 0) ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			} else if( dealerIndicatorDto.getZoneTypeId() == null || !(dealerIndicatorDto.getZoneTypeId().longValue() > 0) ){
				throw new BusinessException(paramsNullCode,paramsNullMessage);
			}
			
			Double response = 0D;
			//Se consultan las WO de categoria de asistencia tecnica
			Long wosSum=this.kpiGeneratorDAO.getWoCustIdAndCreationDateByIndicatorDayCountAndTechnicalAssistanceCat(dealerIndicatorDto, startDate, endDate, this.getRealizedAndFinishedWoStatus());
			
			//Si se han sumado wo que el dealer haya atendido que hagan referencia a cliente q tienen wo de asistencia tecnica
			if( wosSum.doubleValue() > 0 ){
				//Se consulta las wo atendidas por el dealer en los 90 - n dias
				Calendar newStartCalendar = Calendar.getInstance();
				newStartCalendar.setTime( startDate );
				newStartCalendar.roll(Calendar.DAY_OF_YEAR, -90);
				Date newStartDate = UtilsBusiness.dateTo12am(newStartCalendar.getTime());
				Long dealerAtendedWo = this.kpiGeneratorDAO.getNumberOfWoInStatusByFilter(dealerIndicatorDto, newStartDate, endDate, this.getRealizedAndFinishedWoStatus());
				if( dealerAtendedWo != null && dealerAtendedWo.longValue() > 0 )
					response = wosSum.doubleValue()/dealerAtendedWo;
			}	

			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateSoS90Indicator/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateSoS90Indicator/KpiGeneratorBusinessBean ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#calculateAgingIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double calculateAgingIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate)throws BusinessException {
		log.debug("== Inicia calculateAgingIndicator/KpiGeneratorBusinessBean ==");
		try{
			//Valida el dto
			this.validateDealerIndicatorDTO(dealerIndicatorDto);
			
			//Consulta las wo asignadas a la compania instaladora que no se encuentran en estado atendido, finalizado, cancelado
			return kpiGeneratorDAO.getWoIdAndRealizationDateByIndicatorDtoNotInStatusToCalculateAgingIndicator(dealerIndicatorDto,startDate,endDate,getRealizedAndFinishedAndCancelledWoStatus());
			
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateAgingIndicator/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateAgingIndicator/KpiGeneratorBusinessBean ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#calculateRetrievalIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double calculateRetrievalIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate)throws BusinessException {
		log.debug("== Inicia calculateRetrievalIndicator/KpiGeneratorBusinessBean ==");
		try{
			//Valida el dto
			this.validateDealerIndicatorDTO(dealerIndicatorDto);
			
			Double response = 0D;
			
			//Consulta la cantidad de wo atendidas o finalizadas asignadas la dealer en categoria de recuperacion
			Long qRealizedFinalizedWos = kpiGeneratorDAO.getAttendedOrFinalizedWoOfRecoveryService(dealerIndicatorDto,startDate,endDate,getRealizedAndFinishedWoStatus());
			if( qRealizedFinalizedWos != null && qRealizedFinalizedWos.longValue() > 0 ){
				//Consulta la cantidad de wo asignadas al dealer en categoria de recuperacion
				Long qAsignedWos = kpiGeneratorDAO.getAssignedWoOfRecoveryService(dealerIndicatorDto, startDate, endDate);
				if( qAsignedWos != null && qAsignedWos.longValue() > 0)
					response = qRealizedFinalizedWos.doubleValue()/qAsignedWos.doubleValue();
			}
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateRetrievalIndicator/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateRetrievalIndicator/KpiGeneratorBusinessBean ==");
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#getDealersForBalancedCapacitySkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersForBalancedCapacitySkill(SkillEvaluationDTO parameters) throws BusinessException {
		log.debug("== Inicia getDealersForBalancedCapacitySkill/KpiGeneratorBusinessBean ==");
		try{
			if( parameters == null ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL040.getCode(),ErrorBusinessMessages.ALLOCATOR_AL040.getMessage());
			}else if( parameters.getCountryId() == null || parameters.getCountryId().longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL032.getCode(),ErrorBusinessMessages.ALLOCATOR_AL032.getMessage());
			} else if( parameters.getPostalCodeId() == null || parameters.getPostalCodeId().longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL041.getCode(),ErrorBusinessMessages.ALLOCATOR_AL041.getMessage());
			} else if( parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL036.getCode(),ErrorBusinessMessages.ALLOCATOR_AL036.getMessage());
			}
			
			ServiceSuperCategory serviceSuperCategory = serviceSuperCategoryDAO.getServiceSuperCategoryByID(parameters.getServiceSupercategoyId());
			String codeServiceSuperCategory = serviceSuperCategory.getCode();
			
			Double lesserBackLog = null; 
			DealerVO lesserDealerVO = parameters.getDealerList().get(0);
			Long lesserWoAsignToDealerAndNotEndStatus = kpiGeneratorDAO.getWoToCalculateBackLogForBalancedSkill( parameters.getCountryId(), 
					                                                                                             lesserDealerVO.getId(),  
					                                                                                             parameters.getPostalCodeId(), 
					                                                                                             getRealizedAndFinishedAndCancelledWoStatus(),
					                                                                                             codeServiceSuperCategory); 
			
			Long lesserDealerCapacity = this.dealerDetailBusinessBean.getDealerDayCapacity( lesserDealerVO.getId() );
			if(lesserDealerCapacity != null && lesserDealerCapacity.longValue() > 0  ){
				lesserBackLog = ( lesserWoAsignToDealerAndNotEndStatus.doubleValue() + 1D) / lesserDealerCapacity;
			}else{
				String dealerName=daoDealers.getDealerByID(lesserDealerVO.getId()).getDealerName();
				throw new BusinessException(ErrorBusinessMessages.DEALER_DOES_NOT_HAVE_DETAILS.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_HAVE_DETAILS.getMessage() + " No se ha encontrado la capacidad diaria del dealer con id: "+lesserDealerVO.getId()+" o se ha configurado en cero",Arrays.asList(new String[]{""+ dealerName}));
			}
				
			if( parameters.getDealerList().size() > 1 ){
				for( int i = 1 ; i < parameters.getDealerList().size() ; i ++ ){
					DealerVO dealer = parameters.getDealerList().get(i);
					Double actualBackLog = null;
					Long woAsignToDealerAndNotEndStatus = kpiGeneratorDAO.getWoToCalculateBackLogForBalancedSkill( parameters.getCountryId(), 
							                                                                                       dealer.getId(),  
							                                                                                       parameters.getPostalCodeId(), 
							                                                                                       getRealizedAndFinishedAndCancelledWoStatus(),
							                                                                                       codeServiceSuperCategory); 
					
					Long dealerCapacity = this.dealerDetailBusinessBean.getDealerDayCapacity( dealer.getId() );
					if(dealerCapacity != null && dealerCapacity.longValue() > 0  ){
						actualBackLog = ( woAsignToDealerAndNotEndStatus.doubleValue() + 1D ) / dealerCapacity.doubleValue();
					}else{
						String dealerName=daoDealers.getDealerByID(dealer.getId()).getDealerName();
						throw new BusinessException(ErrorBusinessMessages.DEALER_DOES_NOT_HAVE_DETAILS.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_HAVE_DETAILS.getMessage() + " No se ha encontrado la capacidad diaria del dealer con id: "+dealer.getId()+" o se ha configurado en cero",Arrays.asList(new String[]{""+ dealerName}));
					}
					
					if( actualBackLog.compareTo(lesserBackLog) < 0){
						lesserBackLog = actualBackLog;
						lesserDealerVO = dealer;
					}
				}
			}
			List<DealerVO> response = null;
			if( lesserDealerVO != null ){
				response = new ArrayList<DealerVO>();
				response.add( lesserDealerVO );
			}
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación getDealersForBalancedCapacitySkill/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getDealersForBalancedCapacitySkill/KpiGeneratorBusinessBean ==");
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#getDealersForPrioritizedCapacitySkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersForPrioritizedCapacitySkill(SkillEvaluationDTO parameters)throws BusinessException {
		log.debug("== Inicia getDealersForPrioritizedCapacitySkill/KpiGeneratorBusinessBean ==");
		try{
			//1. Se consulta la tabla dealer_coverage para order por cubrimiento la lista que llega por parametro
			//2. En el orden de prioridad se consulta el backlog permitido por dia en la tabla kpi_configuration para obtener la meta por 
			//	 tipo de zona, pais y super categoria de servicio que se encuentre activa.
			//3. Se calcula el backlog por dia de cada dealer de la lista ordenada por cubrimiento
			//4. Si el backlog por dia es <= a la meta se devuelve el dealer en la lista de respuesta. Si no se encuentra ningun dealer 
			//	 que cumpla con la condicion se retorna el primero de la lista ordenada y se se generará un e-mail de alerta al los usuarios
			//	 con el rol backoffice de DIRECTV en el país seleccionado
			//String paramsNullCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			//String paramsNullMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			if( parameters == null ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL040.getCode(),ErrorBusinessMessages.ALLOCATOR_AL040.getMessage());
			} else if( parameters.getCountryId() == null || parameters.getCountryId() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL032.getCode(),ErrorBusinessMessages.ALLOCATOR_AL032.getMessage());
			} else if( parameters.getPostalCodeId() == null || parameters.getPostalCodeId().longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL041.getCode(),ErrorBusinessMessages.ALLOCATOR_AL041.getMessage());
			} else if( parameters.getServiceSupercategoyId() == null || parameters.getServiceSupercategoyId().longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL046.getCode(),ErrorBusinessMessages.ALLOCATOR_AL046.getMessage());
			} else if( parameters.getDealerList() == null || parameters.getDealerList().isEmpty()){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL036.getCode(),ErrorBusinessMessages.ALLOCATOR_AL036.getMessage());
			}
			//Se realiza 1.
			List<Dealer> orderedDealerList = dealerCoverageDAO.getDealersByDealerIdOrderByDealerPriority(parameters.getDealerList(), parameters.getPostalCodeId(), parameters.getCountryId());
			Dealer responseDealer = null;
			Double kpiGoal = 0D;
			PostalCode postalCode = null;
			if( orderedDealerList != null && !orderedDealerList.isEmpty() ){
				//Se realiza 2.
				//Se consulta le codigo postal para obtener el tipo de zona
				postalCode = postalCodesDAO.getPostalCodesByID(parameters.getPostalCodeId());
				if( postalCode != null && postalCode.getZoneType() != null && postalCode.getZoneType().getId() != null && postalCode.getZoneType().getId().longValue() > 0 ){
					//Se consulta la configuracion para el kpi de backlog
					KpiConfiguration kpiConfiguration = kpiConfigurationDAO.getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(parameters.getCountryId(), parameters.getServiceSupercategoyId(), CodesBusinessEntityEnum.KPI_BACKLOG.getCodeEntity(), postalCode.getZoneType().getId());
					if( kpiConfiguration != null && kpiConfiguration.getGoal() != null ){
						kpiGoal = kpiConfiguration.getGoal();
						Double actualBackLog = null;
						for( Dealer dealer : orderedDealerList ){
							//Se realiza 3.
							Long dealerBackLog = this.kpiGeneratorDAO.getWoToCalculateBackLogForPrioritizedSkill(parameters.getCountryId(), dealer.getId(), parameters.getPostalCodeId(), postalCode.getZoneType().getId(), parameters.getServiceSupercategoyId(), this.getRealizedAndFinishedAndCancelledWoStatus());
							Long dealerCapacity = this.dealerDetailBusinessBean.getDealerDayCapacity( dealer.getId() );
							if(dealerCapacity == null || dealerCapacity.longValue() <= 0  ){
								continue;
							}
							
							actualBackLog = ( dealerBackLog.doubleValue() ) / dealerCapacity.doubleValue();
							//Se realiza 4. comparar el backlog de cada dealer con la meta
							if( actualBackLog.compareTo( kpiGoal ) < 0 ){
								responseDealer = dealer;
								break;
							}	
						}
					} else {
						throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL008.getCode(), ErrorBusinessMessages.ALLOCATOR_AL008.getMessage());
					}
				} else {
					throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL007.getCode(), ErrorBusinessMessages.ALLOCATOR_AL007.getMessage());
				}
			} else{
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL006.getCode(), ErrorBusinessMessages.ALLOCATOR_AL006.getMessage());
			}
			List<DealerVO> response = new ArrayList<DealerVO>();			
			if( responseDealer != null ){
				response.add( UtilsBusiness.copyObject(DealerVO.class, responseDealer) );
			} else {
				//Se realiza 4. si no se encuentra ninguno que cumpla se devuelve el primero en el ordenamiento y se envia mail
				if( orderedDealerList != null && !orderedDealerList.isEmpty() ){
					response.add( UtilsBusiness.copyObject(DealerVO.class, orderedDealerList.get(0)) );
					if( postalCode != null && postalCode.getZoneType() != null )
						sendMailToBackOfficeUserByCountry(kpiGoal,postalCode,orderedDealerList,parameters.getCountryId());
				}
			}
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación getDealersForPrioritizedCapacitySkill/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getDealersForPrioritizedCapacitySkill/KpiGeneratorBusinessBean ==");
		}		
	} 
	
	/**
	 * 
	 * Metodo: Envia mail a los usuarios Backoffice de directv por pais
	 * @param countryId
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private void sendMailToBackOfficeUserByCountry(Double backlogGoal,PostalCode postalCode,List<Dealer> dealers,Long countryId) throws BusinessException{
		log.debug("== Inicia sendMailToBackOfficeUserByCountry/KpiGeneratorBusinessBean ==");
		try{
			List<User> backOfficeUsers = userDAO.getUsersByRoleTypeCode( CodesBusinessEntityEnum.ROLE_TYPE_BACKOFFICE.getCodeEntity());
			if( backOfficeUsers != null && !backOfficeUsers.isEmpty() ){
				String mail = getPriorityCapacityMailText( backlogGoal, postalCode, dealers);
				for( User user : backOfficeUsers ){
					if( user .getEmail() != null && !user.getEmail().equals("") ){
						SendEmailDTO emailDto = createEmail(user.getEmail(), mail);
						businessEmailType.sendEmailByEmailTypeCodeAsicFormated( emailDto, countryId);
					}
				}
			}
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación sendMailToBackOfficeUserByCountry/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina sendMailToBackOfficeUserByCountry/KpiGeneratorBusinessBean ==");
		}	
	}
	
	/**
	 * 
	 * Metodo: Construye el texto del correo para el calculo de la capacidad por prioridad
	 * @param backlogGoal
	 * @param postalCode
	 * @param dealers
	 * @return Texto del correo a ser enviado
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private String getPriorityCapacityMailText(Double backlogGoal,PostalCode postalCode,List<Dealer> dealers) throws BusinessException{
		log.debug("== Inicia getPriorityCapacityMailText/KpiGeneratorBusinessBean ==");
		try{
			EmailType emailType = emailTypeDAO.getEmailTypeByCode( EmailTypesEnum.BACKLOG_LIMIT.getEmailTypecode() );
			String text = "";
			if( emailType != null && emailType.getEmailTypeText() != null && !emailType.getEmailTypeText().equals("") ){
				text = emailType.getEmailTypeText();
				MessageFormat mf = new MessageFormat(text);
				Object params[] = {backlogGoal.toString(),postalCode.getZoneType().getZoneTypeName(),getDealersNameToSendMailForPriorityCapacity(dealers),postalCode.getPostalCodeName()};
				text = mf.format(params, new StringBuffer(), null).toString();
			}
			return text;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación getPriorityCapacityMailText/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getPriorityCapacityMailText/KpiGeneratorBusinessBean ==");
		}	
	}
	
	/**
	 * 
	 * Metodo: Obtiene texto con información de dealers en el momento de enviar mail cuando se calcula capacidad por prioridad
	 * @param dealers
	 * @return Cadena ordenada con informacion de dealers para mostrar en el correo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private String getDealersNameToSendMailForPriorityCapacity(List<Dealer> dealers) throws BusinessException{
		log.debug("== Inicia getDealersNameToSendMailForPriorityCapacity/KpiGeneratorBusinessBean ==");
		try{
			StringBuffer sb = new StringBuffer();
			if( dealers != null && !dealers.isEmpty() ){
				sb.append("\n");
				sb.append(ApplicationTextEnum.COMPANY_CODE.getApplicationTextValue());
				sb.append("\t");
				sb.append(ApplicationTextEnum.COMPANY_NAME.getApplicationTextValue());
				sb.append("\n");
				for(Dealer dealer : dealers){
					sb.append(dealer.getDealerCode().toString());
					sb.append("\t");
					sb.append(dealer.getDealerName());
					sb.append("\n");
				}
				sb.append("\n");
			}
			return sb.toString();
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación getDealersNameToSendMailForPriorityCapacity/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getDealersNameToSendMailForPriorityCapacity/KpiGeneratorBusinessBean ==");
		}	
	}
	
	/**
	 * 
	 * Metodo: Arma el objeto para enviar mail
	 * @param recipientMail
	 * @param mailContent
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	private SendEmailDTO createEmail(String recipientMail, String mailContent) throws BusinessException {
		SendEmailDTO emailDto= new SendEmailDTO();
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipientMail);
		emailDto.setRecipient(recipients);
		emailDto.setNewsType(EmailTypesEnum.BACKLOG_LIMIT.getEmailTypecode());
		emailDto.setNewsObservation(mailContent);
		emailDto.setNewsDocument(EmailTypesEnum.BACKLOG_LIMIT.getEmailTypeName());
		emailDto.setNewsSourceUser(".");
		return emailDto;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal#getDealersForKpiSkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersForKpiSkill(SkillEvaluationDTO parameters) throws BusinessException {
		log.debug("== Inicia getDealersForKpiSkill/KpiGeneratorBusinessBean ==");
		try{
			if( parameters == null ){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL040.getCode(),ErrorBusinessMessages.ALLOCATOR_AL040.getMessage());
			} else if( parameters.getCountryId() == null || parameters.getCountryId() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL032.getCode(),ErrorBusinessMessages.ALLOCATOR_AL032.getMessage());
			} else if( parameters.getPostalCodeId() == null || parameters.getPostalCodeId().longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL042.getCode(),ErrorBusinessMessages.ALLOCATOR_AL042.getMessage());
			} else if( parameters.getServiceSupercategoyId() == null || parameters.getServiceSupercategoyId().longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL046.getCode(),ErrorBusinessMessages.ALLOCATOR_AL046.getMessage());
			} 
			PostalCode postalCode = postalCodesDAO.getPostalCodesByID( parameters.getPostalCodeId() );
			if( postalCode == null ){
				throw new BusinessException(ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST.getCode(),ErrorBusinessMessages.POSTAL_CODE_DOES_NOT_EXIST.getMessage());
			}
			
			Long goalKpiSkill = UtilsBusiness.getNumericSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_ASSIGNMENT_KPI_SKILL_GOAL.getCodeEntity(), parameters.getCountryId(), systemParameterDAO);
			List<DealerVO> response = new ArrayList<DealerVO>();
			//1. Si la lista de dealers no viene vacia se calculan los jpi por cada dealer
			if( parameters.getDealerList() != null && !parameters.getDealerList().isEmpty() ){
				KPIFacade kpiFacade = new KPIFacadeImpl();
				log.debug("GetDealersForKpiSkill");
				for( DealerVO dealer : parameters.getDealerList() ){
					log.debug("gdfks-DealerCode="+dealer.getDealerCode());
					Double calculatedValue = kpiFacade.getDealerKPI( parameters.getCountryId() , parameters.getServiceSupercategoyId() , dealer.getId(), postalCode.getZoneType().getId());
					log.debug("gdfks-Puntaje Final="+calculatedValue);
					if( goalKpiSkill <= calculatedValue){
						response.add( dealer );
					}
				}
			}
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación getDealersForKpiSkill/KpiGeneratorBusinessBean ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getDealersForKpiSkill/KpiGeneratorBusinessBean ==");
		}	
	}

}
