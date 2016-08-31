package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.KpiCalculationTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.KpiCalculationTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.KpiCalculationTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD KpiCalculationType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.KpiCalculationTypeFacadeLocal
 */
@Stateless(name="KpiCalculationTypeFacadeLocal",mappedName="ejb/KpiCalculationTypeFacadeLocal")
public class KpiCalculationTypeFacadeBean implements KpiCalculationTypeFacadeBeanLocal {

		
    @EJB(name="KpiCalculationTypeBusinessBeanLocal", beanInterface=KpiCalculationTypeBusinessBeanLocal.class)
    private KpiCalculationTypeBusinessBeanLocal businessKpiCalculationType;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiCalculationTypeFacadeLocal#getAllKpiCalculationTypes()
     */
    public List<KpiCalculationTypeVO> getAllKpiCalculationTypes() throws BusinessException {
    	return businessKpiCalculationType.getAllKpiCalculationTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiCalculationTypeFacadeLocal#getKpiCalculationTypesByID(java.lang.Long)
     */
    public KpiCalculationTypeVO getKpiCalculationTypeByID(Long id) throws BusinessException {
    	return businessKpiCalculationType.getKpiCalculationTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiCalculationTypeFacadeLocal#createKpiCalculationType(co.com.directv.sdii.model.vo.KpiCalculationTypeVO)
	 */
	public void createKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException {
		businessKpiCalculationType.createKpiCalculationType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiCalculationTypeFacadeLocal#updateKpiCalculationType(co.com.directv.sdii.model.vo.KpiCalculationTypeVO)
	 */
	public void updateKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException {
		businessKpiCalculationType.updateKpiCalculationType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiCalculationTypeFacadeLocal#deleteKpiCalculationType(co.com.directv.sdii.model.vo.KpiCalculationTypeVO)
	 */
	public void deleteKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException {
		businessKpiCalculationType.deleteKpiCalculationType(obj);
	}
}
