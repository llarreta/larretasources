package co.com.directv.sdii.ejb.business.assign.impl;

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
import co.com.directv.sdii.ejb.business.assign.SkillConfigurationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.ServiceType;
import co.com.directv.sdii.model.pojo.Skill;
import co.com.directv.sdii.model.pojo.SkillConfiguration;
import co.com.directv.sdii.model.pojo.SkillModeType;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;
import co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SkillDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD SkillConfiguration
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.SkillConfigurationDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillConfigurationBusinessBeanLocal
 */
@Stateless(name="SkillConfigurationBusinessBeanLocal",mappedName="ejb/SkillConfigurationBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SkillConfigurationBusinessBean extends BusinessBase implements SkillConfigurationBusinessBeanLocal {

    @EJB(name="SkillConfigurationDAOLocal", beanInterface=SkillConfigurationDAOLocal.class)
    private SkillConfigurationDAOLocal daoSkillConfiguration;
    
    @EJB(name="SkillDAOLocal", beanInterface=SkillDAOLocal.class)
    private SkillDAOLocal daoSkill;
    
    @EJB(name="SkillModeTypeDAOLocal", beanInterface=SkillModeTypeDAOLocal.class)
    private SkillModeTypeDAOLocal daoSkillModeType;
    
    private final static Logger log = UtilsBusiness.getLog4J(SkillConfigurationBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SkillConfigurationBusinessBeanLocal#getAllSkillConfigurations()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillConfigurationVO> getAllSkillConfigurations() throws BusinessException {
        log.debug("== Inicia getAllSkillConfigurations/SkillConfigurationBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoSkillConfiguration.getAllSkillConfigurations(), SkillConfigurationVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllSkillConfigurations/SkillConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillConfigurations/SkillConfigurationBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SkillConfigurationBusinessBeanLocal#getSkillConfigurationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SkillConfigurationVO getSkillConfigurationByID(Long id) throws BusinessException {
        log.debug("== Inicia getSkillConfigurationByID/SkillConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SkillConfiguration objPojo = daoSkillConfiguration.getSkillConfigurationByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SkillConfigurationVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSkillConfigurationByID/SkillConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillConfigurationByID/SkillConfigurationBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillConfigurationBusinessBeanLocal#createSkillConfiguration(co.com.directv.sdii.model.vo.SkillConfigurationVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSkillConfiguration(SkillConfigurationVO obj) throws BusinessException {
        log.debug("== Inicia createSkillConfiguration/SkillConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getSkillEvaluationType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getSkillEvaluationType().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SkillConfiguration objPojo =  UtilsBusiness.copyObject(SkillConfiguration.class, obj);
            daoSkillConfiguration.createSkillConfiguration(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createSkillConfiguration/SkillConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkillConfiguration/SkillConfigurationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillConfigurationBusinessBeanLocal#updateSkillConfiguration(co.com.directv.sdii.model.vo.SkillConfigurationVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSkillConfiguration(SkillConfigurationVO obj) throws BusinessException {
        log.debug("== Inicia updateSkillConfiguration/SkillConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SkillConfiguration objPojo =  UtilsBusiness.copyObject(SkillConfiguration.class, obj);
            if(objPojo.getStatus().equals(CodesBusinessEntityEnum.KPI_CONFIGURATION_STATUS_INACTIVE.getCodeEntity()) && objPojo.getSkillEvaluationType() == null)
            	daoSkillConfiguration.deleteSkillConfiguration(objPojo);
            else{
            	UtilsBusiness.assertNotNull(obj.getSkillEvaluationType(), ErrorBusinessMessages.ALLOCATOR_AL023.getCode(), ErrorBusinessMessages.ALLOCATOR_AL023.getMessage());
            	UtilsBusiness.assertNotNull(obj.getSkillEvaluationType().getId(), ErrorBusinessMessages.ALLOCATOR_AL023.getCode(), ErrorBusinessMessages.ALLOCATOR_AL023.getMessage());
            	daoSkillConfiguration.updateSkillConfiguration(objPojo);
            }
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateSkillConfiguration/SkillConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkillConfiguration/SkillConfigurationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillConfigurationBusinessBeanLocal#deleteSkillConfiguration(co.com.directv.sdii.model.vo.SkillConfigurationVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSkillConfiguration(SkillConfigurationVO obj) throws BusinessException {
        log.debug("== Inicia deleteSkillConfiguration/SkillConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SkillConfiguration objPojo =  UtilsBusiness.copyObject(SkillConfiguration.class, obj);
            daoSkillConfiguration.deleteSkillConfiguration(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSkillConfiguration/SkillConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkillConfiguration/SkillConfigurationBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.SkillConfigurationBusinessBeanLocal#getSkillConfigurationsByCriteria(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SkillConfigurationVO> getSkillConfigurationsByCriteria(
			String countryCode, String serviceTypeCode, String skillModeTypeCode)
			throws BusinessException {
		log.debug("== Inicia deleteSkillConfiguration/SkillConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceTypeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(skillModeTypeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<SkillConfiguration> objsPojo =  daoSkillConfiguration.getSkillConfigurationsByCriteria(countryCode, serviceTypeCode, skillModeTypeCode);
        	List<SkillConfigurationVO> result = UtilsBusiness.convertList(objsPojo, SkillConfigurationVO.class);
        	return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSkillConfiguration/SkillConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkillConfiguration/SkillConfigurationBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.SkillConfigurationBusinessBeanLocal#updateSkillConfigurations(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSkillConfigurations(List<SkillConfigurationVO> skillConfigurations)
			throws BusinessException {
		
		log.debug("== Inicia updateSkillConfigurations/KpiConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(skillConfigurations, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//persistir las modificaciones a las configuraciones
        	for (SkillConfigurationVO skillConfigurationVO : skillConfigurations) {
        		
        		updateSkillConfiguration(skillConfigurationVO);
        		
			}
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateSkillConfigurations/KpiConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkillConfigurations/KpiConfigurationBusinessBean ==");
        }
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.SkillConfigurationBusinessBeanLocal#getSkillConfigurationsByCountryIdAndCategoryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SkillConfigurationVO> getSkillConfigurationsByCountryIdAndCategoryId(Long countryId, Long serviceTypeId) throws BusinessException {
		log.debug("== Inicia getSkillConfigurationsByCountryIdAndCategoryId/SkillConfigurationBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceTypeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<SkillConfiguration> skillConfigurations =  daoSkillConfiguration.getSkillConfigurationsByCountryIdAndCategoryId(countryId, serviceTypeId);
        	
        	List<Skill> skills = daoSkill.getAll();
        	appendMissingSkillConfigurations(skillConfigurations, skills, countryId, serviceTypeId);
        	
        	List<SkillConfigurationVO> kpiConfigurationsVO = UtilsBusiness.convertList(skillConfigurations, SkillConfigurationVO.class);
        	
        	return kpiConfigurationsVO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSkillConfigurationsByCountryIdAndCategoryId/SkillConfigurationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillConfigurationsByCountryIdAndCategoryId/SkillConfigurationBusinessBean ==");
        }
	}


	private void appendMissingSkillConfigurations(List<SkillConfiguration> skillConfigurations, List<Skill> skills, Long countryId, Long serviceTypeId) throws PropertiesException, DAOServiceException, DAOSQLException {
		if(skills != null) {

			List<SkillModeType> skillModeTypes = daoSkillModeType.getAllSkillModeTypes();

			for (Skill skill : skills) {

				for (SkillModeType skillModeType : skillModeTypes) {
					SkillConfiguration skillConfig = findInConfigurationList(skillConfigurations, skill, skillModeType);
					
					if(skillConfig == null) {

						Country country =  new Country(countryId);
						ServiceType serviceType = new ServiceType(serviceTypeId);

						skillConfig = new SkillConfiguration();
						skillConfig.setCountry(country);
						skillConfig.setServiceType(serviceType);
						skillConfig.setSkill(skill);
						skillConfig.setStatus(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
						skillConfig.setSkillModeType(skillModeType);
						skillConfigurations.add(skillConfig);

					}
				}
			}
		}
	}


	private SkillConfiguration findInConfigurationList(List<SkillConfiguration> skillConfigurations, Skill skill, SkillModeType skillModeType) {
		if(skillConfigurations != null) {
			for (SkillConfiguration conf : skillConfigurations) {
				if(conf.getSkill() != null && conf.getSkillModeType() != null) {
					if(skill.getId().equals(conf.getSkill().getId()) && skillModeType.getId().equals(conf.getSkillModeType().getId())) {
						return conf;
					}
				}
			}
		}
		return null;
	}

}
