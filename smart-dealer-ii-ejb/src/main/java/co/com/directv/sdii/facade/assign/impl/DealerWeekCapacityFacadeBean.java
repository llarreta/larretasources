package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerWeekCapacityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerWeekCapacityFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DealerWeekCapacity
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerWeekCapacityFacadeLocal
 */
@Stateless(name="DealerWeekCapacityFacadeLocal",mappedName="ejb/DealerWeekCapacityFacadeLocal")
public class DealerWeekCapacityFacadeBean implements DealerWeekCapacityFacadeBeanLocal {

		
    @EJB(name="DealerWeekCapacityBusinessBeanLocal", beanInterface=DealerWeekCapacityBusinessBeanLocal.class)
    private DealerWeekCapacityBusinessBeanLocal businessDealerWeekCapacity;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerWeekCapacityFacadeLocal#getAllDealerWeekCapacitys()
     */
    public List<DealerWeekCapacityVO> getAllDealerWeekCapacitys() throws BusinessException {
    	return businessDealerWeekCapacity.getAllDealerWeekCapacitys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerWeekCapacityFacadeLocal#getDealerWeekCapacitysByID(java.lang.Long)
     */
    public DealerWeekCapacityVO getDealerWeekCapacityByID(Long id) throws BusinessException {
    	return businessDealerWeekCapacity.getDealerWeekCapacityByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerWeekCapacityFacadeLocal#createDealerWeekCapacity(co.com.directv.sdii.model.vo.DealerWeekCapacityVO)
	 */
	public void createDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException {
		businessDealerWeekCapacity.createDealerWeekCapacity(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerWeekCapacityFacadeLocal#updateDealerWeekCapacity(co.com.directv.sdii.model.vo.DealerWeekCapacityVO)
	 */
	public void updateDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException {
		businessDealerWeekCapacity.updateDealerWeekCapacity(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerWeekCapacityFacadeLocal#deleteDealerWeekCapacity(co.com.directv.sdii.model.vo.DealerWeekCapacityVO)
	 */
	public void deleteDealerWeekCapacity(DealerWeekCapacityVO obj) throws BusinessException {
		businessDealerWeekCapacity.deleteDealerWeekCapacity(obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerWeekCapacityFacadeBeanLocal#getDealerWeekCapacityByDealerIdAndCountryId(java.lang.Long, java.lang.Long)
	 */
	public List<DealerWeekCapacityVO> getDealerWeekCapacityByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException {
		return businessDealerWeekCapacity.getDealerWeekCapacityByDealerIdAndCountryId(dealerId, countryId);
	}
	
}
