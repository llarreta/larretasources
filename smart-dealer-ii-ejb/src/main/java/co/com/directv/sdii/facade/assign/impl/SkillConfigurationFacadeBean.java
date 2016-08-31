package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.SkillConfigurationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.SkillConfigurationFacadeBeanLocal;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD SkillConfiguration
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.SkillConfigurationFacadeLocal
 */
@Stateless(name="SkillConfigurationFacadeLocal",mappedName="ejb/SkillConfigurationFacadeLocal")
public class SkillConfigurationFacadeBean implements SkillConfigurationFacadeBeanLocal {

		
    @EJB(name="SkillConfigurationBusinessBeanLocal", beanInterface=SkillConfigurationBusinessBeanLocal.class)
    private SkillConfigurationBusinessBeanLocal businessSkillConfiguration;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SkillConfigurationFacadeLocal#getAllSkillConfigurations()
     */
    public List<SkillConfigurationVO> getAllSkillConfigurations() throws BusinessException {
    	return businessSkillConfiguration.getAllSkillConfigurations();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SkillConfigurationFacadeLocal#getSkillConfigurationsByID(java.lang.Long)
     */
    public SkillConfigurationVO getSkillConfigurationByID(Long id) throws BusinessException {
    	return businessSkillConfiguration.getSkillConfigurationByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillConfigurationFacadeLocal#createSkillConfiguration(co.com.directv.sdii.model.vo.SkillConfigurationVO)
	 */
	public void createSkillConfiguration(SkillConfigurationVO obj) throws BusinessException {
		businessSkillConfiguration.createSkillConfiguration(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillConfigurationFacadeLocal#updateSkillConfiguration(co.com.directv.sdii.model.vo.SkillConfigurationVO)
	 */
	public void updateSkillConfiguration(SkillConfigurationVO obj) throws BusinessException {
		businessSkillConfiguration.updateSkillConfiguration(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillConfigurationFacadeLocal#deleteSkillConfiguration(co.com.directv.sdii.model.vo.SkillConfigurationVO)
	 */
	public void deleteSkillConfiguration(SkillConfigurationVO obj) throws BusinessException {
		businessSkillConfiguration.deleteSkillConfiguration(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.SkillConfigurationFacadeBeanLocal#getSkillConfigurationsByCountryIdAndCategoryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<SkillConfigurationVO> getSkillConfigurationsByCountryIdAndCategoryId(
			Long countryId, Long serviceTypeId) throws BusinessException {
		return businessSkillConfiguration.getSkillConfigurationsByCountryIdAndCategoryId(countryId, serviceTypeId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.SkillConfigurationFacadeBeanLocal#updateKpiConfigurations(java.util.List)
	 */
	@Override
	public void updateSkillConfigurations(List<SkillConfigurationVO> skillConfigurations) throws BusinessException {
		businessSkillConfiguration.updateSkillConfigurations(skillConfigurations);
		
	}
	
}
