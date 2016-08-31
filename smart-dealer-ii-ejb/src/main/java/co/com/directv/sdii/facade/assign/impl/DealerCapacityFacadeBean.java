package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerCapacityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerCapacityFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerCapacityVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DealerCapacity
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerCapacityFacadeLocal
 */
@Stateless(name="DealerCapacityFacadeLocal",mappedName="ejb/DealerCapacityFacadeLocal")
public class DealerCapacityFacadeBean implements DealerCapacityFacadeBeanLocal {

		
    @EJB(name="DealerCapacityBusinessBeanLocal", beanInterface=DealerCapacityBusinessBeanLocal.class)
    private DealerCapacityBusinessBeanLocal businessDealerCapacity;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerCapacityFacadeLocal#getAllDealerCapacitys()
     */
    public List<DealerCapacityVO> getAllDealerCapacitys() throws BusinessException {
    	return businessDealerCapacity.getAllDealerCapacitys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerCapacityFacadeLocal#getDealerCapacitysByID(java.lang.Long)
     */
    public DealerCapacityVO getDealerCapacityByID(Long id) throws BusinessException {
    	return businessDealerCapacity.getDealerCapacityByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCapacityFacadeLocal#createDealerCapacity(co.com.directv.sdii.model.vo.DealerCapacityVO)
	 */
	public void createDealerCapacity(DealerCapacityVO obj) throws BusinessException {
		businessDealerCapacity.createDealerCapacity(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCapacityFacadeLocal#updateDealerCapacity(co.com.directv.sdii.model.vo.DealerCapacityVO)
	 */
	public void updateDealerCapacity(DealerCapacityVO obj) throws BusinessException {
		businessDealerCapacity.updateDealerCapacity(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCapacityFacadeLocal#deleteDealerCapacity(co.com.directv.sdii.model.vo.DealerCapacityVO)
	 */
	public void deleteDealerCapacity(DealerCapacityVO obj) throws BusinessException {
		businessDealerCapacity.deleteDealerCapacity(obj);
	}
}
