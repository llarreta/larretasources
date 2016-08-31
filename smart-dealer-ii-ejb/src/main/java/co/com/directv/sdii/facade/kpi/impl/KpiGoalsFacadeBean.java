package co.com.directv.sdii.facade.kpi.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.kpi.KpiGoalsFacadeBeanLocal;
import co.com.directv.sdii.model.vo.KpiGoalsVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD KpiGoals
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.kpi.KpiGoalsFacadeLocal
 */
@Stateless(name="KpiGoalsFacadeLocal",mappedName="ejb/KpiGoalsFacadeLocal")
public class KpiGoalsFacadeBean implements KpiGoalsFacadeBeanLocal {

		
    @EJB(name="KpiGoalsBusinessBeanLocal", beanInterface=KpiGoalsBusinessBeanLocal.class)
    private KpiGoalsBusinessBeanLocal businessKpiGoals;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiGoalsFacadeLocal#getAllKpiGoalss()
     */
    public List<KpiGoalsVO> getAllKpiGoalss() throws BusinessException {
    	return businessKpiGoals.getAllKpiGoalss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiGoalsFacadeLocal#getKpiGoalssByID(java.lang.Long)
     */
    public KpiGoalsVO getKpiGoalsByID(Long id) throws BusinessException {
    	return businessKpiGoals.getKpiGoalsByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiGoalsFacadeLocal#createKpiGoals(co.com.directv.sdii.model.vo.KpiGoalsVO)
	 */
	public void createKpiGoals(KpiGoalsVO obj) throws BusinessException {
		businessKpiGoals.createKpiGoals(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiGoalsFacadeLocal#updateKpiGoals(co.com.directv.sdii.model.vo.KpiGoalsVO)
	 */
	public void updateKpiGoals(KpiGoalsVO obj) throws BusinessException {
		businessKpiGoals.updateKpiGoals(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiGoalsFacadeLocal#deleteKpiGoals(co.com.directv.sdii.model.vo.KpiGoalsVO)
	 */
	public void deleteKpiGoals(KpiGoalsVO obj) throws BusinessException {
		businessKpiGoals.deleteKpiGoals(obj);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.kpi.KpiGoalsFacadeBeanLocal#getAllKpiGoalssAndByCountry(java.lang.Long)
	 */
	public List<KpiGoalsVO> getAllKpiGoalssAndByCountry(Long country) throws BusinessException {
    	return businessKpiGoals.getAllKpiGoalssAndByCountry(country);
    }


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.kpi.KpiGoalsFacadeBeanLocal#getKpiGoalsByIndicatorIdAndCountryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<KpiGoalsVO> getKpiGoalsByIndicatorIdAndCountryId(Long indicatorId, Long countryId) throws BusinessException {
		return businessKpiGoals.getKpiGoalsByIndicatorIdAndCountryId(indicatorId, countryId);
	}
}
