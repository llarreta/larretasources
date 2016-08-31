package co.com.directv.sdii.facade.kpi.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.kpi.IndicatorsBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.kpi.IndicatorsFacadeBeanLocal;
import co.com.directv.sdii.model.vo.IndicatorsVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Indicators
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.kpi.IndicatorsFacadeLocal
 */
@Stateless(name="IndicatorsFacadeLocal",mappedName="ejb/IndicatorsFacadeLocal")
public class IndicatorsFacadeBean implements IndicatorsFacadeBeanLocal {

		
    @EJB(name="IndicatorsBusinessBeanLocal", beanInterface=IndicatorsBusinessBeanLocal.class)
    private IndicatorsBusinessBeanLocal businessIndicators;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.IndicatorsFacadeLocal#getAllIndicatorss()
     */
    public List<IndicatorsVO> getAllIndicatorss() throws BusinessException {
    	return businessIndicators.getAllIndicatorss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.IndicatorsFacadeLocal#getIndicatorssByID(java.lang.Long)
     */
    public IndicatorsVO getIndicatorsByID(Long id) throws BusinessException {
    	return businessIndicators.getIndicatorsByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.IndicatorsFacadeLocal#createIndicators(co.com.directv.sdii.model.vo.IndicatorsVO)
	 */
	public void createIndicators(IndicatorsVO obj) throws BusinessException {
		businessIndicators.createIndicators(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.IndicatorsFacadeLocal#updateIndicators(co.com.directv.sdii.model.vo.IndicatorsVO)
	 */
	public void updateIndicators(IndicatorsVO obj) throws BusinessException {
		businessIndicators.updateIndicators(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.IndicatorsFacadeLocal#deleteIndicators(co.com.directv.sdii.model.vo.IndicatorsVO)
	 */
	public void deleteIndicators(IndicatorsVO obj) throws BusinessException {
		businessIndicators.deleteIndicators(obj);
	}
}
