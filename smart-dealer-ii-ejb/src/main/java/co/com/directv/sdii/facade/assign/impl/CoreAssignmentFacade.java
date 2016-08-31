
/**
 * Creado 26/05/2011 18:16:20
 */
package co.com.directv.sdii.facade.assign.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoad;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.ejb.business.assign.CSRBusinessLocal;
import co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerConfigurationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerCustomerTypesWoutPcBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerDetailBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerWeekCapacityBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.KpiGeneratorBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.SaleChannelSellerBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.ServiceSuperCategoryBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.SkillConfigurationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.SkillModeTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.ConfigJornadasBusinessLocal;
import co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal;
import co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal;
import co.com.directv.sdii.ejb.business.config.ServiceTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.ejb.business.dealers.EmployeeCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ScheduleException;
import co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerClassVO;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;
import co.com.directv.sdii.model.vo.KpiResultVO;
import co.com.directv.sdii.model.vo.KpiTypeVO;
import co.com.directv.sdii.model.vo.KpiVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;
import co.com.directv.sdii.model.vo.SystemParameterVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;

/**
 * Implementación de la interface que define las operaciones que usará el componente de asignación
 * 
 * Fecha de Creación: 26/05/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="CoreAssignmentFacadeRemote")
@Remote({CoreAssignmentFacadeRemote.class})
public class CoreAssignmentFacade implements CoreAssignmentFacadeRemote {

	@EJB(name="CountriesCRUDBeanLocal",beanInterface=CountriesCRUDBeanLocal.class)
	private CountriesCRUDBeanLocal countryBusiness;
	
	@EJB(name="ConfigJornadasBusinessLocal",beanInterface=ConfigJornadasBusinessLocal.class)
	private ConfigJornadasBusinessLocal serviceHourBusiness;
	
	@EJB(name="ConfigParametrosBusinessLocal",beanInterface=ConfigParametrosBusinessLocal.class)
	private ConfigParametrosBusinessLocal systemParameterBusiness;
	
	@EJB(name="SkillConfigurationBusinessBeanLocal",beanInterface=SkillConfigurationBusinessBeanLocal.class)
	private SkillConfigurationBusinessBeanLocal skillConfigurationBusiness;
	
	@EJB(name="SkillModeTypeBusinessBeanLocal",beanInterface=SkillModeTypeBusinessBeanLocal.class)
	private SkillModeTypeBusinessBeanLocal skillModeTypeBusiness;
	
	@EJB(name="DealerDetailBusinessBeanLocal",beanInterface=DealerDetailBusinessBeanLocal.class)
	private DealerDetailBusinessBeanLocal dealerDetailBusiness;
	
	@EJB(name="EmployeeCRUDBeanLocal",beanInterface=EmployeeCRUDBeanLocal.class)
	private EmployeeCRUDBeanLocal employeeBusiness;
	
	@EJB(name="DealerWeekCapacityBusinessBeanLocal",beanInterface=DealerWeekCapacityBusinessBeanLocal.class)
	private DealerWeekCapacityBusinessBeanLocal dealerWeekCapacityBusiness;
	
	@EJB(name="ServiceTypeBusinessBeanLocal",beanInterface=ServiceTypeBusinessBeanLocal.class)
	private ServiceTypeBusinessBeanLocal serviceTypeBusiness;
	
	@EJB(name="ServiceSuperCategoryBusinessBeanLocal",beanInterface=ServiceSuperCategoryBusinessBeanLocal.class)
	private ServiceSuperCategoryBusinessBeanLocal serviceSuperCategoryBusiness;
	
	@EJB(name="DealersCRUDLocal",beanInterface=DealersCRUDLocal.class)
	private DealersCRUDLocal dealersBusiness;
	
	@EJB(name="DealerServiceSubCategoryBusinessBeanLocal",beanInterface=DealerServiceSubCategoryBusinessBeanLocal.class)
	private DealerServiceSubCategoryBusinessBeanLocal dealersServiceSubCatBusiness;
	
	@EJB(name="KpiConfigurationBusinessBeanLocal",beanInterface=KpiConfigurationBusinessBeanLocal.class)
	private KpiConfigurationBusinessBeanLocal kpiConfigurationBusiness;
	
	@EJB(name="KpiBusinessBeanLocal",beanInterface=KpiBusinessBeanLocal.class)
	private KpiBusinessBeanLocal kpiBusiness;
	
	@EJB(name="KpiTypeBusinessBeanLocal",beanInterface=KpiTypeBusinessBeanLocal.class)
	private KpiTypeBusinessBeanLocal kpiTypeBusiness;
	
	@EJB(name="DealerServiceSubCategoryWithPcBusinessBeanLocal",beanInterface=DealerServiceSubCategoryWithPcBusinessBeanLocal.class)
	private DealerServiceSubCategoryWithPcBusinessBeanLocal dealersServiceSubCatWithPcBusiness;	
	
	@EJB(name="KpiGeneratorBusinessBeanLocal",beanInterface=KpiGeneratorBusinessBeanLocal.class)
	private KpiGeneratorBusinessBeanLocal kpiGeneratorBusinessBean;
	
	@EJB(name="DealerCustomerTypesWoutPcBusinessBeanLocal",beanInterface=DealerCustomerTypesWoutPcBusinessBeanLocal.class)
	private DealerCustomerTypesWoutPcBusinessBeanLocal dealerCustTypeWoutPcBusiness;

	@EJB(name="CoreWOBusinessLocal",beanInterface=CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal coreWOBusiness;
	
	@EJB(name="DealerCapacityBusinessBeanLocal",beanInterface=DealerCapacityBusinessBeanLocal.class)
	private DealerCapacityBusinessBeanLocal dealerCapacityBusinessBean;
	
	@EJB(name="KpiResultBusinessBeanLocal",beanInterface=KpiResultBusinessBeanLocal.class)
	private KpiResultBusinessBeanLocal kpiResultBusiness;
	
	@EJB(name="WorkOrderBusinessBeanLocal",beanInterface=WorkOrderBusinessBeanLocal.class)	
	private WorkOrderBusinessBeanLocal workOrderBusiness; 
	
	@EJB(name="DealersBusinessBeanLocal",beanInterface=DealersBusinessBeanLocal.class)
	private DealersBusinessBeanLocal dealersBusinessBean;
	
	@EJB(name="SaleChannelSellerBusinessBeanLocal",beanInterface=SaleChannelSellerBusinessBeanLocal.class)
	private SaleChannelSellerBusinessBeanLocal saleChannelSellerBusinessBean;
	
	@EJB(name="ConfigTiposClienteBusinessLocal",beanInterface=ConfigTiposClienteBusinessLocal.class)
	private ConfigTiposClienteBusinessLocal customerTypeBusiness;
	
	@EJB(name="PostalCodesCRUDBeanLocal",beanInterface=PostalCodesCRUDBeanLocal.class)
	private PostalCodesCRUDBeanLocal postalCodeBusiness;
		
	@EJB(name="DealerCoverageBusinessBeanLocal",beanInterface=DealerCoverageBusinessBeanLocal.class)
	private DealerCoverageBusinessBeanLocal dealerCoverageBusinessBean;

	@EJB(name="CSRBusinessLocal",beanInterface=CSRBusinessLocal.class)
	private CSRBusinessLocal csrBusiness;
	
	@EJB(name="WorkOrderBusinessBeanLocal",beanInterface=WorkOrderBusinessBeanLocal.class)
	private WorkOrderBusinessBeanLocal workOrderBusinessBean;

	@EJB(name="DealerConfigurationBusinessBeanLocal",beanInterface=DealerConfigurationBusinessBeanLocal.class)
	private DealerConfigurationBusinessBeanLocal dealerConfigurationBusinessBean;
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerById(java.lang.Long, java.lang.Long)
	 */
	@Override
	public DealerVO getDealerById(Long dealerId, Long countryId)
			throws BusinessException {
		return dealersBusiness.getDealerByID(dealerId);
	}
	
	public Long getDealerIdByDealerCode(Long dealerCode)
			throws BusinessException {
		return dealersBusiness.getDealerIdByDealerCode(dealerCode);
	}
	
	public WorkOrderVO getWorkOrderByCode(String woCode) throws BusinessException {
			return workOrderBusinessBean.getWorkOrderByCode(woCode);
	}
	
	public DealerVO getDealerByDealerCode(Long dealerCode)
			throws BusinessException {
		return dealersBusiness.getDealerByCode(dealerCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getCustomerTypeByCode(java.lang.String)
	 */
	@Override
	public CustomerClassTypeVO getCustomerClassTypeByCodeType(String customerClassCode, Long countryId)
			throws BusinessException {
		List<CustomerClassTypeVO> customerTypes = customerTypeBusiness.getCustomerClassTypeByCodeType(customerClassCode, countryId);
		if(customerTypes == null || customerTypes.isEmpty()){
			return null;
		}
		return customerTypes.get(0);
	}

	@Override
	public CustomerClassTypeVO getCustomerClassTypeByCodeClass(String customerTypeCode, Long countryId)
			throws BusinessException {
		List<CustomerClassTypeVO> customerTypes = customerTypeBusiness.getCustomerClassTypeByCodeClass(customerTypeCode, countryId);
		if(customerTypes == null || customerTypes.isEmpty()){
			return null;
		}
		return customerTypes.get(0);
	}
	
	@Override
	public PostalCodeVO getPostalCodeByCode(String postalCode, Long countryId)
			throws BusinessException {
		return postalCodeBusiness.getPostalCodesByCodeByCountryId(postalCode, countryId);
	}

	@Override
	public ServiceHourVO getServiceHourByCode(String serviceHourCode,
			Long countryId) throws BusinessException {
		return serviceHourBusiness.getServiceHoursByNameAndCountryId(serviceHourCode, countryId);
	}

	@Override
	public ServiceSuperCategoryVO getServiceSuperCategoryByServiceCode(
			String serviceCode) throws BusinessException {
		return serviceSuperCategoryBusiness.getServiceSuperCategoryByServiceCode(serviceCode);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getServiceSuperCategoryByID(java.lang.Long)
	 */
	@Override
	public ServiceSuperCategoryVO getServiceSuperCategoryByID(Long id) throws BusinessException {
		return serviceSuperCategoryBusiness.getServiceSuperCategoryByID(id);
	}

	@Override
	public SkillModeTypeVO getSkillModeTypeByCode(String skillModeTypeCode)
			throws BusinessException {
		return skillModeTypeBusiness.getSkillModeTypeByCode(skillModeTypeCode);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getCountryByCode(java.lang.String)
	 */
	@Override
	public CountryVO getCountryByCode(String countryIso2Code)
			throws BusinessException {
		return countryBusiness.getCountriesByCode(countryIso2Code);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getCountriesByID(java.lang.Long)
	 */
	@Override
	public CountryVO getCountriesByID(Long countryId) throws BusinessException {
		return countryBusiness.getCountriesByID(countryId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getKpiResultByCriteria(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public KpiResultVO getKpiResultByCriteria(Long countryId, Long serviceSuperCategoryId, Long zoneTypeId, Long indicatorId,
			Long dealerId) throws BusinessException {
		return kpiResultBusiness.getKpiResultByCriteria(countryId, serviceSuperCategoryId, zoneTypeId, indicatorId, dealerId);
	}	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerCodeWoutCoverage(java.lang.Long)
	 */
	@Override
	public long getDealerCodeWoutCoverage(Long countryId) throws BusinessException {
		return systemParameterBusiness.getDealerCodeWoutCoverage(countryId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getSystemParameterByCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	public SystemParameterVO getSystemParameterByCodeAndCountryId(
			String parameterCode, Long countryId) throws BusinessException {
		return systemParameterBusiness.getSystemParameterByCodeAndCountryId(parameterCode, countryId);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getKpiById(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public KpiVO getKpiById(Long kpiId) throws BusinessException {
		return kpiBusiness.getKpiByID(kpiId);
	}
		
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getActiveServiceHours(java.lang.Long)
	 */
	@Override
	public List<ServiceHourVO> getActiveServiceHoursByCountryId(Long countryId) throws BusinessException{
		return serviceHourBusiness.getAllActiveServiceHoursByCountryId(countryId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getSkillConfigurationsByCriteria(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<SkillConfigurationVO> getSkillConfigurationsByCriteria(String countryCode, String serviceTypeCode, String skillModeTypeCode)
			throws BusinessException {
		return skillConfigurationBusiness.getSkillConfigurationsByCriteria(countryCode, 
				serviceTypeCode, 
				skillModeTypeCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getServiceHourById(java.lang.Long)
	 */
	@Override
	public ServiceHourVO getServiceHourById(Long serviceHourId)
			throws BusinessException {
		return serviceHourBusiness.getServiceHoursByID(serviceHourId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerDetailByDealerId(java.lang.Long)
	 */
	@Override
	public DealerDetailVO getDealerDetailByDealerId(Long dealerId)
			throws BusinessException {
		return dealerDetailBusiness.getDealerDetailByID(dealerId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerDayCapacity(java.lang.Long)
	 */
	@Override
	public Long getDealerDayCapacity(Long dealerId) throws BusinessException{
		return dealerDetailBusiness.getDealerDayCapacity(dealerId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getActiveDealerTechniciansQty(java.lang.Long)
	 */
	@Override
	public Long getActiveDealerTechniciansQty(Long dealerId)
			throws BusinessException {
		return employeeBusiness.getActiveTechniciansQtyByDealerId(dealerId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public DealerWeekCapacityVO getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(
			Long countryId, Long dealerId, Long serviceHourId,
			Long serviceSuperCategoryId)throws BusinessException {
		return dealerWeekCapacityBusiness.getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(countryId, dealerId, serviceHourId, serviceSuperCategoryId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getServiceTypeByServiceCode(java.lang.String)
	 */
	@Override
	public ServiceTypeVO getServiceTypeByServiceCode(String serviceCode)
			throws BusinessException {
		return serviceTypeBusiness.getServiceTypeByServiceCode(serviceCode);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getActiveDealersByCountryId(java.lang.Long)
	 */
	@Override
	public List<DealerVO> getActiveDealersByCountryId(Long countryId) throws BusinessException, PropertiesException{
		return dealersBusiness.getDealersByCountryIdAndTypeCode(countryId, CodesBusinessEntityEnum.CODE_DEALER_TYPE_INSTALLER.getCodeEntity());
	}
	
	@Override
	public List<DealerVO> getDealersForGenerateKpis(Long countryId) throws BusinessException, PropertiesException{
		return dealersBusiness.getDealersForGenerateKpis(countryId);
	}

	@Override
	public List<DealerVO> getDealersWhoAttendServiceCategory(
			String countryCode, String serviceCode) throws BusinessException {
		return dealersServiceSubCatBusiness.getDealersWhoAttendServiceCategory(countryCode, serviceCode);
	}

	@Override
	public List<KpiConfigurationVO> getKPIConfigurationsByCalculationTypeCode(String calculationTypeCode) throws BusinessException {
		return kpiConfigurationBusiness.getKpiConfigurationsByCalculationTypeCode(calculationTypeCode);
	}
	
	@Override
	public List<KpiCalculateDTO> getKpiCalculateDTOByCalculationTypeCode(String calculationTypeCode,Long countryId) throws BusinessException {
		return kpiConfigurationBusiness.getKpiCalculateDTOByCalculationTypeCode(calculationTypeCode,countryId);
	}

	@Override
	public List<DealerVO> getDealersWhoAttendServiceSubCategory(
			String countryCode, String serviceCode) throws BusinessException {
		return dealersServiceSubCatBusiness.getDealersWhoAttendServiceSubCategory(countryCode, serviceCode);
	}	
	
	public List<DealerVO> getDealersWhoAttendServiceSubCategoryWithCoverage(String countryCode, String serviceCode, String postalCode) throws BusinessException {
		return dealersServiceSubCatWithPcBusiness.getDealersWhoAttendServiceSubCategoryWithCoverage(countryCode, serviceCode, postalCode);	
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#calculateCycleTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	public Double calculateCycleTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException {
		return kpiGeneratorBusinessBean.calculateCycleTimeIndicator(dealerIndicatorDto,startDate,endDate);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#calculateOnTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	public Double calculateOnTimeIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate)throws BusinessException {
		return kpiGeneratorBusinessBean.calculateOnTimeIndicator(dealerIndicatorDto, startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#calculateBackLogInDaysIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	public Double calculateBackLogInDaysIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate) throws BusinessException {
		return kpiGeneratorBusinessBean.calculateBackLogInDaysIndicator(dealerIndicatorDto, startDate, endDate);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealersWhoAttendCustomerTypeWoutCoverage(java.lang.String, java.lang.String)
	 */
	@Override
	public List<DealerVO> getDealersWhoAttendCustomerTypeWoutCoverage(
			String countryCode, Long customerClassTypeId)  throws BusinessException{
		return dealerCustTypeWoutPcBusiness.getDealersWhoAttendCustomerTypeWoutCoverage(countryCode, customerClassTypeId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#calculateSoS90Indicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	public Double calculateSoS90Indicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate) throws BusinessException {
		return kpiGeneratorBusinessBean.calculateSoS90Indicator(dealerIndicatorDto, startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#calculateAgingIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	public Double calculateAgingIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate)throws BusinessException {
		return kpiGeneratorBusinessBean.calculateAgingIndicator(dealerIndicatorDto, startDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#calculateRetrievalIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO, java.util.Date, java.util.Date)
	 */
	@Override
	public Double calculateRetrievalIndicator(DealerIndicatorDTO dealerIndicatorDto, Date startDate, Date endDate)throws BusinessException {
		return kpiGeneratorBusinessBean.calculateRetrievalIndicator(dealerIndicatorDto, startDate, endDate);
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerUsedCapacity(co.com.directv.sdii.assign.schedule.DealerWorkCapacityCriteria)
	 */
	@Override
	public int getDealerUsedCapacity(DealerWorkCapacityCriteria dealerWCCriteria) throws ScheduleException {
		return this.coreWOBusiness.getDealerUsedCapacity(dealerWCCriteria);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#saveDealerCapacity(co.com.directv.sdii.assign.schedule.DealerWorkLoad)
	 */
	@Override
	public void saveDealerCapacity(DealerWorkLoad dealerCapacity) throws ScheduleException {
		this.dealerCapacityBusinessBean.saveDealerCapacity(dealerCapacity);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#saveKpiResult(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO)
	 */
	@Override
	public void saveKpiResult(DealerIndicatorResultDTO dealerIndicatorResultDTO) throws BusinessException {
		this.kpiResultBusiness.createKpiResult(dealerIndicatorResultDTO.toKpiResultVO());
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerIndicatorResult(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO)
	 */
	@Override
	public KpiResultVO getDealerIndicatorResult(DealerIndicatorDTO dealerIndicatorDto) throws BusinessException {
		Long countryId = dealerIndicatorDto.getCountryId();
		Long superCategoryId = dealerIndicatorDto.getServiceSuperCategoryId();
		Long kpiId = dealerIndicatorDto.getIndicatorId();
		Long zoneTypeId = dealerIndicatorDto.getZoneTypeId();
		KpiConfigurationVO kpiConfigurationVO = kpiConfigurationBusiness.getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(countryId, superCategoryId, kpiId, zoneTypeId);
		if(kpiConfigurationVO != null) {
			return kpiResultBusiness.getDealerIndicatorResultByKpiConfigurationIdAndDealerId(kpiConfigurationVO.getId(), dealerIndicatorDto.getDealerId());
		}else { 
			String params = new StringBuffer("countryId = ").append(countryId != null ? countryId  : "null")
				.append("superCategoryId = ").append(superCategoryId != null ? superCategoryId  : "null")
				.append("kpiId = ").append(kpiId != null ? kpiId  : "null")
				.append("zoneTypeId = ").append(zoneTypeId != null ? zoneTypeId  : "null").toString();
			throw new BusinessException("No hay una configuración de KPI activa para los parámetros indicados. " + params);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealersForBalancedCapacitySkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	public List<DealerVO> getDealersForBalancedCapacitySkill(SkillEvaluationDTO parameters)throws BusinessException {
		return kpiGeneratorBusinessBean.getDealersForBalancedCapacitySkill(parameters);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getWorkOrdersByCountryCustomerPostalCodeScheduleDateWOAddress(java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String)
	 */
	public DealerVO otherServices2CustmerSkill(String countryIso2Code, String ibsCustomerCode, String postalCode, java.util.Date scheduleDate, String addressCode, String address)
	   throws BusinessException {
		return this.workOrderBusiness.otherServices2CustmerSkill(countryIso2Code, ibsCustomerCode, postalCode, scheduleDate, addressCode, address);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealersForPrioritizedCapacitySkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	public List<DealerVO> getDealersForPrioritizedCapacitySkill(SkillEvaluationDTO parameters)throws BusinessException {
		return kpiGeneratorBusinessBean.getDealersForPrioritizedCapacitySkill(parameters);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealersForSaleAndInstallSameDealerSkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	public List<DealerVO> getDealersForSaleAndInstallSameDealerSkill(SkillEvaluationDTO parameters) throws BusinessException {
		return dealersBusinessBean.getDealersForSaleAndInstallSameDealerSkill(parameters);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealersForSaleAndInstallDealerAssociatedSkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	public List<DealerVO> getDealersForSaleAndInstallDealerAssociatedSkill(SkillEvaluationDTO parameters) throws BusinessException {
		return saleChannelSellerBusinessBean.getDealersForSaleAndInstallDealerAssociatedSkill(parameters);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealersForEvenOrOddCustomerCodeSkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	public List<DealerVO> getDealersForEvenOrOddCustomerCodeSkill(SkillEvaluationDTO parameters) throws BusinessException {
		return dealersBusinessBean.getDealersForEvenOrOddCustomerCodeSkill(parameters);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealersForKpiSkill(co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO)
	 */
	@Override
	public List<DealerVO> getDealersForKpiSkill(SkillEvaluationDTO parameters) throws BusinessException {
		return kpiGeneratorBusinessBean.getDealersForKpiSkill(parameters);
	}


	@Override
	public List<KpiTypeVO> getActiveKpiTypes() throws BusinessException {
		return kpiTypeBusiness.getAllKpiTypes();
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<KpiConfigurationVO> getKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(
			Long countryId, Long serviceSuperCategoryId, Long zoneTypeId)
			throws BusinessException {
		
		return kpiConfigurationBusiness.getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(countryId, serviceSuperCategoryId, zoneTypeId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public KpiConfigurationVO getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(
			String countryCode, String serviceSuperCategoryCode,
			String zoneTypeCode, String kpiCode) throws BusinessException {
		return kpiConfigurationBusiness.getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(countryCode, serviceSuperCategoryCode, zoneTypeCode, kpiCode);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public KpiConfigurationVO getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(
			Long countryId, Long superCategoryId, Long zoneTypeId,
			String kpiCode) throws BusinessException {
		return kpiConfigurationBusiness.getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(countryId, superCategoryId, zoneTypeId, kpiCode);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public KpiConfigurationVO getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(
			Long countryId, Long superCategoryId, Long kpiId,Long zoneTypeId) throws BusinessException {
		return kpiConfigurationBusiness.getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(countryId, superCategoryId, kpiId,zoneTypeId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealersInMicrozoneWithExMode(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<DealerVO> getDealersInMicrozoneWithExMode(String executionMode,
			String postalCode, String countryIso2Code, 
            Long dealerId) throws BusinessException {
		return dealerCoverageBusinessBean.getDealersInMicrozoneWithExMode( executionMode,
				 postalCode,  countryIso2Code,dealerId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#countDealersInMicrozoneWithTypePerm(java.lang.String, java.lang.String, java.lang.Long)
	 */
	public boolean checkDealersInMicrozoneWithTypePerm(String postalCode, String countryIso2Code, 
            Long dealerId) throws BusinessException {
		return dealerCoverageBusinessBean.checkDealersInMicrozoneWithTypePerm(postalCode,countryIso2Code,dealerId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerFromLastWoFromCustomer(java.lang.String)
	 */
	@Override
	public List<DealerVO> getDealerFromLastWoFromCustomer(String ibsCustomerCode, Long countryCode) throws BusinessException{
		return workOrderBusiness.getDealerFromLastWoFromCustomer(ibsCustomerCode, countryCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerFromWoWithWarranty(java.lang.String, java.lang.Long, java.util.List)
	 */
	@Override
	public List<DealerVO> getDealerFromWoWithWarranty(String ibsCustomerCode,
			Long countryCode, List<String> serviceCode) throws BusinessException {
		return workOrderBusiness.getDealerFromWoWithWarranty(ibsCustomerCode, countryCode, serviceCode);
	}
	
    public List<DealerVO> getDealersWhoAttendsABuildingOrAttendsBuildingsIntoPostalCode(String ibsBuildingCode, String postalCode, String countryCode) throws BusinessException {
    	return this.dealersBusinessBean.getDealersWhoAttendsABuildingOrAttendsBuildingsIntoPostalCode(ibsBuildingCode, postalCode, countryCode) ;
    }
    
    //Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
    public List<DealerVO> getDealersWhoAttendsABuilding(Long buildingId) throws BusinessException {
    	return this.dealersBusinessBean.getDealersWhoAttendsABuilding(buildingId) ;
    }
    
    public WorkorderStatus getWorkorderStatusByIBS6StatusCode(String ibs6StatusCode) throws BusinessException {
    	return workOrderBusiness.getWorkorderStatusByIBS6StatusCode(ibs6StatusCode) ;
    }
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerFromWoWithWarranty(java.lang.String, java.lang.Long, java.util.List)
	 */
	@Override
    public void sendMailCsr(AssignRequestDTO assignRequestDTO, Long countryId){
    
		csrBusiness.sendMailCsr(assignRequestDTO, countryId);
    }
	
	public void sendWorkOrdersForLastDayReport(Long countryId) throws BusinessException {
    	workOrderBusiness.sendWorkOrdersForLastDayReport(countryId) ;
    }

	@Override
	public CustomerClassVO getCustomerClassByCode(String customerClassCode, Long countryId)
			throws BusinessException {
		return customerTypeBusiness.getCustomerClassByCode(customerClassCode, countryId);
	}
	
	@Override
	public CustomerClassTypeVO getCustomerClassTypeByCode(
			String customerClassCode, String customerClassTypeCode, Long countryId)
			throws BusinessException {
		return customerTypeBusiness.getCustomerClassTypeByCodeTypeAndCodeClass(customerClassTypeCode,customerClassCode , countryId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote#getDealerFromLastWoFromCustomer(java.lang.String)
	 */
	@Override
	public List<DealerVO> getDealerFromDealerByCustomerType(Long customerCategoryId, Long businesAreaId, Long customerClassTypeId, 
    		Long serviceCategoryId, Long postalCodeId, Long countryCode) throws BusinessException{
		return dealerConfigurationBusinessBean.getDealerFromDealerByCustomerType(customerCategoryId, businesAreaId, customerClassTypeId, 
	    		serviceCategoryId, postalCodeId, countryCode);
	}
    
}
