package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Kpi;
import co.com.directv.sdii.model.pojo.KpiCalculationType;
import co.com.directv.sdii.model.pojo.KpiConfiguration;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;
import co.com.directv.sdii.model.pojo.ZoneType;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;
import co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.ZoneTypesDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD KpiConfiguration
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.KpiConfigurationDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal
 */
@Stateless(name="KpiConfigurationBusinessBeanLocal",mappedName="ejb/KpiConfigurationBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiConfigurationBusinessBean extends BusinessBase implements KpiConfigurationBusinessBeanLocal {

    @EJB(name="KpiConfigurationDAOLocal", beanInterface=KpiConfigurationDAOLocal.class)
    private KpiConfigurationDAOLocal daoKpiConfiguration;
    
    @EJB(name="KpiCalculationTypeDAOLocal", beanInterface=KpiCalculationTypeDAOLocal.class)
    private KpiCalculationTypeDAOLocal daoKpiCalculationType;
    
    
    @EJB(name="KpiDAOLocal", beanInterface=KpiDAOLocal.class)
    private KpiDAOLocal daoKpi;
    
    @EJB(name="ZoneTypesDAOLocal", beanInterface=ZoneTypesDAOLocal.class)
    private ZoneTypesDAOLocal daoZoneTypes;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal userDao;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
    private SystemParameterDAOLocal systemParameterDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(KpiConfigurationBusinessBean.class);

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#getAllKpiConfigurations()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiConfigurationVO> getAllKpiConfigurations() throws BusinessException {
        log.debug("== Inicia getAllKpiConfigurations/KpiConfigurationBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoKpiConfiguration.getAllKpiConfigurations(), KpiConfigurationVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllKpiConfigurations/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllKpiConfigurations/KpiConfigurationBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#getKpiConfigurationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiConfigurationVO getKpiConfigurationByID(Long id) throws BusinessException {
        log.debug("== Inicia getKpiConfigurationByID/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiConfiguration objPojo = daoKpiConfiguration.getKpiConfigurationByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(KpiConfigurationVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getKpiConfigurationByID/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiConfigurationByID/KpiConfigurationBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#createKpiConfiguration(co.com.directv.sdii.model.vo.KpiConfigurationVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createKpiConfiguration(KpiConfigurationVO obj) throws BusinessException {
        log.debug("== Inicia createKpiConfiguration/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiConfiguration objPojo =  UtilsBusiness.copyObject(KpiConfiguration.class, obj);
            daoKpiConfiguration.createKpiConfiguration(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createKpiConfiguration/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiConfiguration/KpiConfigurationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#updateKpiConfiguration(co.com.directv.sdii.model.vo.KpiConfigurationVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateKpiConfiguration(KpiConfigurationVO obj) throws BusinessException {
        log.debug("== Inicia updateKpiConfiguration/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiConfiguration objPojo =  UtilsBusiness.copyObject(KpiConfiguration.class, obj);
            daoKpiConfiguration.updateKpiConfiguration(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateKpiConfiguration/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiConfiguration/KpiConfigurationBusinessBean ==");
        }
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#updateKpiConfigurations(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateKpiConfigurations(List<KpiConfigurationVO> newKpiConfigurations)
			throws BusinessException {
		log.debug("== Inicia updateKpiConfigurations/KpiConfigurationBusinessBean ==");
        
		try {
			//validar las modificaciones a las configuraciones
			validateUpdateKpiConfigurations(newKpiConfigurations);
        	
        	//persistir las modificaciones a las configuraciones
        	for (KpiConfigurationVO kpiConfigurationVO : newKpiConfigurations) {
    			KpiConfiguration kpiConfigurationPOJO = UtilsBusiness.copyObject(KpiConfiguration.class, kpiConfigurationVO);
    			daoKpiConfiguration.updateKpiConfiguration(kpiConfigurationPOJO);
        	}
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateKpiConfigurations/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiConfigurations/KpiConfigurationBusinessBean ==");
        }
		
	}
	
	/**
	 * Metodo: Permite validar si la configuracion trae todos los campos requeridos
	 * @param newKpiConfigurations 
	 * @throws DAOServiceException 
	 * @throws DAOSQLException 
	 * @throws PropertiesException 
	 * @throws BusinessException <tipo> <descripcion> 
	 * @author
	 */
	private void validateUpdateKpiConfigurations(List<KpiConfigurationVO> newKpiConfigurations) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException{
		
		for (KpiConfigurationVO kpiConfigurationVO : newKpiConfigurations) {
			kpiConfigurationVO.setDateLastChange(UtilsBusiness.fechaActual());
			kpiConfigurationVO.setKpiCalculationType(daoKpiCalculationType.getKpiCalculationTypeByID(kpiConfigurationVO.getKpiCalculationType().getId()));
			if(kpiConfigurationVO.getKpiCalculationType().getCode().equals(CodesBusinessEntityEnum.KPI_CALCULATION_TYPE_FRECUENCY.getCodeEntity()) 
					   && (kpiConfigurationVO.getFrecuencyExpression() == null || 
						   kpiConfigurationVO.getFrecuencyExpression().equals("")))
						throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL024.getCode(), ErrorBusinessMessages.ALLOCATOR_AL024.getMessage() );
			else if(kpiConfigurationVO.getKpiCalculationType().getCode().equals(CodesBusinessEntityEnum.KPI_CALCULATION_TYPE_FRECUENCY.getCodeEntity()) 
					   	&& (!kpiConfigurationVO.getFrecuencyExpression().equals("")))
				if(!UtilsBusiness.isNumericValue(kpiConfigurationVO.getFrecuencyExpression()))
					throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL018.getCode(), ErrorBusinessMessages.ALLOCATOR_AL018.getMessage(), Arrays.asList(kpiConfigurationVO.getFrecuencyExpression()) );
		}
		
	}
	
	private void validateCronExpressions(List<KpiConfigurationVO> newKpiConfigurations) throws BusinessException, PropertiesException {
		if(newKpiConfigurations != null) {
			String lastExpression = "";
			StringBuffer invalidExpressions = new StringBuffer();
			for (KpiConfigurationVO kpiConfigurationVO : newKpiConfigurations) {
				
				if(kpiConfigurationVO.getFrecuencyExpression() != null) {
					if(!lastExpression.equals(kpiConfigurationVO.getFrecuencyExpression())) {
						if( !CronExpression.isValidExpression(kpiConfigurationVO.getFrecuencyExpression()) ) {
							invalidExpressions.append(kpiConfigurationVO.getFrecuencyExpression()).append("\n");
						}
						lastExpression = kpiConfigurationVO.getFrecuencyExpression();
					}
				}
			}
			
			if(invalidExpressions.length() > 0) {
				throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL018.getCode(), ErrorBusinessMessages.ALLOCATOR_AL018.getMessage(), Arrays.asList(invalidExpressions.toString()) );
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#deleteKpiConfiguration(co.com.directv.sdii.model.vo.KpiConfigurationVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteKpiConfiguration(KpiConfigurationVO obj) throws BusinessException {
        log.debug("== Inicia deleteKpiConfiguration/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiConfiguration objPojo =  UtilsBusiness.copyObject(KpiConfiguration.class, obj);
            daoKpiConfiguration.deleteKpiConfiguration(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteKpiConfiguration/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiConfiguration/KpiConfigurationBusinessBean ==");
        }
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiConfigurationVO> getKpiConfigurationsByCalculationTypeCode(
			String calculationTypeCode) throws BusinessException {
		log.debug("== Inicia getKpiConfigurationsByCalculationTypeCode/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(calculationTypeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<KpiConfiguration> kpiConfigurations = daoKpiConfiguration.getKpiConfigurationsByCalculationTypeCode(calculationTypeCode);
            return UtilsBusiness.convertList(kpiConfigurations, KpiConfigurationVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getKpiConfigurationsByCalculationTypeCode/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiConfigurationsByCalculationTypeCode/KpiConfigurationBusinessBean ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiCalculateDTO> getKpiCalculateDTOByCalculationTypeCode(String calculationTypeCode,Long countryId) throws BusinessException {
		log.debug("== Inicia getKpiCalculateDTOByCalculationTypeCode/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(calculationTypeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	Long userId = UtilsBusiness.getUserIdAdmin(userDao, systemParameterDAO, countryId);
        	List<KpiCalculateDTO> kpiCalculateDTO = daoKpiConfiguration.getKpiCalculateDTOByCalculationTypeCode(calculationTypeCode,
        			                                                                                            countryId,
        			                                                                                            userId);
            return kpiCalculateDTO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getKpiCalculateDTOByCalculationTypeCode/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiCalculateDTOByCalculationTypeCode/KpiConfigurationBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public KpiConfigurationVO getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(
			Long countryId, Long superCategoryId, Long kpiId, Long zoneTypeId)
			throws BusinessException {
		log.debug("== Inicia getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(superCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(kpiId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(zoneTypeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	KpiConfiguration kpiConfiguration = daoKpiConfiguration.getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(countryId, superCategoryId, kpiId, zoneTypeId);
            return UtilsBusiness.copyObject(KpiConfigurationVO.class, kpiConfiguration);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiConfigurationVO> getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(
			Long countryId, Long serviceSuperCategoryId, Long zoneTypeId)
			throws BusinessException {
		log.debug("== Inicia getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceSuperCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(zoneTypeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<KpiConfiguration> kpiConfigurations = daoKpiConfiguration.getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(countryId, serviceSuperCategoryId, zoneTypeId);
            return UtilsBusiness.convertList(kpiConfigurations, KpiConfigurationVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType/KpiConfigurationBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public KpiConfigurationVO getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(
			String countryCode, String serviceSuperCategoryCode,
			String zoneTypeCode, String kpiCode) throws BusinessException {
		log.debug("== Inicia getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceSuperCategoryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(zoneTypeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(kpiCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	KpiConfiguration kpiConfiguration = daoKpiConfiguration.getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(countryCode, serviceSuperCategoryCode, zoneTypeCode, kpiCode);
            return UtilsBusiness.copyObject(KpiConfigurationVO.class, kpiConfiguration);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType/KpiConfigurationBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public KpiConfigurationVO getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(
			Long countryId, Long superCategoryId, Long zoneTypeId,
			String kpiCode) throws BusinessException {
		log.debug("== Inicia getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(superCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(zoneTypeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(kpiCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	KpiConfiguration kpiConfiguration = daoKpiConfiguration.getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(countryId, superCategoryId, kpiCode, zoneTypeId);
            return UtilsBusiness.copyObject(KpiConfigurationVO.class, kpiConfiguration);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode/KpiConfigurationBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal#getKPIConfigurationsByCountryIdAndSupercategoryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiConfigurationVO> getKPIConfigurationsByCountryIdAndSupercategoryId(
			Long countryId, Long serviceSuperCategoryId) throws BusinessException {
		log.debug("== Inicia getKPIConfigurationsByCountryIdAndSupercategoryId/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceSuperCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<KpiConfiguration> kpiConfigurations = daoKpiConfiguration.getKPIConfigurationsByCountryIdAndSupercategoryId(countryId, serviceSuperCategoryId);
        	
        	List<Kpi> kpis = daoKpi.getAllKpis();
        	List<ZoneType> zoneTypes = daoZoneTypes.getAllZoneTypes();
        	appendMissingKpiConfigurations(kpiConfigurations, kpis, zoneTypes, countryId, serviceSuperCategoryId);
        	
        	List<KpiConfigurationVO> kpiConfigurationsVO = UtilsBusiness.convertList(kpiConfigurations, KpiConfigurationVO.class);
        	
        	return kpiConfigurationsVO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getKPIConfigurationsByCountryIdAndSupercategoryId/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKPIConfigurationsByCountryIdAndSupercategoryId/KpiConfigurationBusinessBean ==");
        }
	}

	/**
	 * 
	 * Metodo: agrega a una lista de kpiConfigurations un grupo de configuraciones inactivas para
	 * llenar los kpis y los tipos de zona que no tienen configuraciones en eñ soste,a
	 * @param kpiConfigurations
	 * @param kpis
	 * @param zoneTypes
	 * @param serviceSuperCategoryId 
	 * @param countryId 
	 * @throws PropertiesException
	 */
	private void appendMissingKpiConfigurations(
			List<KpiConfiguration> kpiConfigurations, List<Kpi> kpis,
			List<ZoneType> zoneTypes, Long countryId, Long serviceSuperCategoryId) throws PropertiesException {
		
		for (Kpi kpi : kpis) {
			List<KpiConfiguration> especificKpiConfigs = findInConfigurationList(kpiConfigurations, kpi);

			//obtener el estado global de las configuraciones del kpi para poder asignarlo a las configuraciones que se deban crear
			KpiConfiguration fisrtKpiConf = (KpiConfiguration) UtilsBusiness.getFirstItem(especificKpiConfigs);
			
			Country country = null;
			ServiceSuperCategory serviceSuperCategory = null;
			KpiCalculationType calcType = null;
			String kpiConfStatus = null;
			if(fisrtKpiConf != null) {
				country = fisrtKpiConf.getCountry();
				serviceSuperCategory = fisrtKpiConf.getServiceSuperCategory();
				kpiConfStatus = fisrtKpiConf.getStatus();
				calcType = fisrtKpiConf.getKpiCalculationType();
			} else {
				country = new Country(countryId);
				serviceSuperCategory = new ServiceSuperCategory(serviceSuperCategoryId);
				kpiConfStatus = CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
				calcType = new KpiCalculationType();
				calcType.setCode(CodesBusinessEntityEnum.KPI_CALCULATION_TYPE_ONLINE.getCodeEntity());
			}
			
			for (ZoneType zoneType : zoneTypes) {
				KpiConfiguration kpiConf =  findInConfigurationList(especificKpiConfigs, zoneType);
				
				if(kpiConf == null) {
					kpiConf = new KpiConfiguration(kpi, zoneType, kpiConfStatus, country, serviceSuperCategory, calcType);
					kpiConfigurations.add(kpiConf);
				}
			}
			
		}
		
	}


	private KpiConfiguration findInConfigurationList(List<KpiConfiguration> especificKpiConfigs, ZoneType zoneType) {
		if(especificKpiConfigs != null) {
			for (KpiConfiguration kpiConfiguration : especificKpiConfigs) {
				if(zoneType.getId().equals(kpiConfiguration.getZoneTypes().getId())) {
					return kpiConfiguration;
				}
			}
		}
		return null;
	}


	private List<KpiConfiguration> findInConfigurationList(List<KpiConfiguration> kpiConfigurations, Kpi kpi) {
		List<KpiConfiguration> confs = new ArrayList<KpiConfiguration>();
		if(kpiConfigurations != null) {
			for (KpiConfiguration kpiConfiguration : kpiConfigurations) {
				if(kpi.getId().equals(kpiConfiguration.getKpi().getId())) {
					confs.add(kpiConfiguration);
				}
			}
		}
		return confs;
	}
	
}
