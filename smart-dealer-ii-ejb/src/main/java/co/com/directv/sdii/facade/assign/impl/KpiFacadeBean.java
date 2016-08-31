package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.KpiFacadeBeanLocal;
import co.com.directv.sdii.model.vo.KpiVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Kpi
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.KpiFacadeLocal
 */
@Stateless(name="KpiFacadeLocal",mappedName="ejb/KpiFacadeLocal")
public class KpiFacadeBean implements KpiFacadeBeanLocal {

		
    @EJB(name="KpiBusinessBeanLocal", beanInterface=KpiBusinessBeanLocal.class)
    private KpiBusinessBeanLocal businessKpi;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiFacadeLocal#getAllKpis()
     */
    public List<KpiVO> getAllKpis() throws BusinessException {
    	return businessKpi.getAllKpis();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiFacadeLocal#getKpisByID(java.lang.Long)
     */
    public KpiVO getKpiByID(Long id) throws BusinessException {
    	return businessKpi.getKpiByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiFacadeLocal#createKpi(co.com.directv.sdii.model.vo.KpiVO)
	 */
	public void createKpi(KpiVO obj) throws BusinessException {
		businessKpi.createKpi(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiFacadeLocal#updateKpi(co.com.directv.sdii.model.vo.KpiVO)
	 */
	public void updateKpi(KpiVO obj) throws BusinessException {
		businessKpi.updateKpi(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiFacadeLocal#deleteKpi(co.com.directv.sdii.model.vo.KpiVO)
	 */
	public void deleteKpi(KpiVO obj) throws BusinessException {
		businessKpi.deleteKpi(obj);
	}
}
