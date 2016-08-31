package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.HistoDealerWeekCapacityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.HistoDealerWeekCapacityFacadeBeanLocal;
import co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD HistoDealerWeekCapacity
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.HistoDealerWeekCapacityFacadeLocal
 */
@Stateless(name="HistoDealerWeekCapacityFacadeLocal",mappedName="ejb/HistoDealerWeekCapacityFacadeLocal")
public class HistoDealerWeekCapacityFacadeBean implements HistoDealerWeekCapacityFacadeBeanLocal {

		
    @EJB(name="HistoDealerWeekCapacityBusinessBeanLocal", beanInterface=HistoDealerWeekCapacityBusinessBeanLocal.class)
    private HistoDealerWeekCapacityBusinessBeanLocal businessHistoDealerWeekCapacity;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerWeekCapacityFacadeLocal#getAllHistoDealerWeekCapacitys()
     */
    public List<HistoDealerWeekCapacityVO> getAllHistoDealerWeekCapacitys() throws BusinessException {
    	return businessHistoDealerWeekCapacity.getAllHistoDealerWeekCapacitys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerWeekCapacityFacadeLocal#getHistoDealerWeekCapacitysByID(java.lang.Long)
     */
    public HistoDealerWeekCapacityVO getHistoDealerWeekCapacityByID(Long id) throws BusinessException {
    	return businessHistoDealerWeekCapacity.getHistoDealerWeekCapacityByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerWeekCapacityFacadeLocal#createHistoDealerWeekCapacity(co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO)
	 */
	public void createHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException {
		businessHistoDealerWeekCapacity.createHistoDealerWeekCapacity(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerWeekCapacityFacadeLocal#updateHistoDealerWeekCapacity(co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO)
	 */
	public void updateHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException {
		businessHistoDealerWeekCapacity.updateHistoDealerWeekCapacity(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerWeekCapacityFacadeLocal#deleteHistoDealerWeekCapacity(co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO)
	 */
	public void deleteHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException {
		businessHistoDealerWeekCapacity.deleteHistoDealerWeekCapacity(obj);
	}
}
