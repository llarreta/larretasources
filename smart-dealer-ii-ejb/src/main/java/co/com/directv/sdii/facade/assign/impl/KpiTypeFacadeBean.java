package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.KpiTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.KpiTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD KpiType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.KpiTypeFacadeLocal
 */
@Stateless(name="KpiTypeFacadeLocal",mappedName="ejb/KpiTypeFacadeLocal")
public class KpiTypeFacadeBean implements KpiTypeFacadeBeanLocal {

		
    @EJB(name="KpiTypeBusinessBeanLocal", beanInterface=KpiTypeBusinessBeanLocal.class)
    private KpiTypeBusinessBeanLocal businessKpiType;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiTypeFacadeLocal#getAllKpiTypes()
     */
    public List<KpiTypeVO> getAllKpiTypes() throws BusinessException {
    	return businessKpiType.getAllKpiTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiTypeFacadeLocal#getKpiTypesByID(java.lang.Long)
     */
    public KpiTypeVO getKpiTypeByID(Long id) throws BusinessException {
    	return businessKpiType.getKpiTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiTypeFacadeLocal#createKpiType(co.com.directv.sdii.model.vo.KpiTypeVO)
	 */
	public void createKpiType(KpiTypeVO obj) throws BusinessException {
		businessKpiType.createKpiType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiTypeFacadeLocal#updateKpiType(co.com.directv.sdii.model.vo.KpiTypeVO)
	 */
	public void updateKpiType(KpiTypeVO obj) throws BusinessException {
		businessKpiType.updateKpiType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiTypeFacadeLocal#deleteKpiType(co.com.directv.sdii.model.vo.KpiTypeVO)
	 */
	public void deleteKpiType(KpiTypeVO obj) throws BusinessException {
		businessKpiType.deleteKpiType(obj);
	}
}
