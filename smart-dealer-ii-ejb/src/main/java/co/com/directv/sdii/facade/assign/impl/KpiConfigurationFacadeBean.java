package co.com.directv.sdii.facade.assign.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.KpiConfigurationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.KpiConfigurationFacadeBeanLocal;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD KpiConfiguration
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.KpiConfigurationFacadeLocal
 */
@Stateless(name="KpiConfigurationFacadeLocal",mappedName="ejb/KpiConfigurationFacadeLocal")
public class KpiConfigurationFacadeBean implements KpiConfigurationFacadeBeanLocal {

		
    @EJB(name="KpiConfigurationBusinessBeanLocal", beanInterface=KpiConfigurationBusinessBeanLocal.class)
    private KpiConfigurationBusinessBeanLocal businessKpiConfiguration;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAOLocal;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiConfigurationFacadeLocal#getAllKpiConfigurations()
     */
    public List<KpiConfigurationVO> getAllKpiConfigurations() throws BusinessException {
    	return businessKpiConfiguration.getAllKpiConfigurations();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiConfigurationFacadeLocal#getKpiConfigurationsByID(java.lang.Long)
     */
    public KpiConfigurationVO getKpiConfigurationByID(Long id) throws BusinessException {
    	return businessKpiConfiguration.getKpiConfigurationByID(id);
    }

    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiConfigurationFacadeLocal#getKpiConfigurationsByID(java.lang.Long)
     */
    public void calculateAndSaveNextExcecutionDate(Long id) throws BusinessException {
		KpiConfigurationVO kpiConfigurationVO = this.getKpiConfigurationByID(id);
		System.out.println("el id = "+id);
		Date nextExecutionDate = kpiConfigurationVO.getNextExecutionDate();
		Long minPeriod = Long.parseLong(kpiConfigurationVO.getFrecuencyExpression());
		Calendar calendar = Calendar.getInstance();
		System.out.println("fecha actual = "+nextExecutionDate.getTime());
		calendar.setTimeInMillis(nextExecutionDate.getTime());
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.setTime(co.com.directv.sdii.common.util.UtilsBusiness.getDateLastChangeOfUser(kpiConfigurationVO.getCountry().getId(), userDao, systemParameterDAOLocal));
		while(calendar.before(calendarNow)){
			calendar.add(Calendar.SECOND, minPeriod.intValue());
		}
		kpiConfigurationVO.setNextExecutionDate(calendar.getTime());
		this.updateKpiConfiguration(kpiConfigurationVO);
		System.out.println("el calendar = "+calendar.getTime());
    }
    
	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiConfigurationFacadeLocal#createKpiConfiguration(co.com.directv.sdii.model.vo.KpiConfigurationVO)
	 */
	public void createKpiConfiguration(KpiConfigurationVO obj) throws BusinessException {
		businessKpiConfiguration.createKpiConfiguration(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiConfigurationFacadeLocal#updateKpiConfiguration(co.com.directv.sdii.model.vo.KpiConfigurationVO)
	 */
	public void updateKpiConfiguration(KpiConfigurationVO obj) throws BusinessException {
		businessKpiConfiguration.updateKpiConfiguration(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiConfigurationFacadeLocal#deleteKpiConfiguration(co.com.directv.sdii.model.vo.KpiConfigurationVO)
	 */
	public void deleteKpiConfiguration(KpiConfigurationVO obj) throws BusinessException {
		businessKpiConfiguration.deleteKpiConfiguration(obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.KpiConfigurationFacadeBeanLocal#updateKpiConfigurations(java.util.List)
	 */
	public void updateKpiConfigurations(List<KpiConfigurationVO> newKpiConfigurations) throws BusinessException {
		businessKpiConfiguration.updateKpiConfigurations(newKpiConfigurations);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.KpiConfigurationFacadeBeanLocal#getKPIConfigurationsByCountryIdAndSupercategoryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<KpiConfigurationVO> getKPIConfigurationsByCountryIdAndSupercategoryId(
			Long countryId, Long superCategoryId) throws BusinessException {
		return businessKpiConfiguration.getKPIConfigurationsByCountryIdAndSupercategoryId(countryId, superCategoryId);
	}
	
}
