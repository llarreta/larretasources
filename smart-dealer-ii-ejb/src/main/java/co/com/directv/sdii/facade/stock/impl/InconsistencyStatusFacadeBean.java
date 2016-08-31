package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.InconsistencyStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.InconsistencyStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.InconsistencyStatusVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD InconsistencyStatus
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.InconsistencyStatusFacadeLocal
 */
@Stateless(name="InconsistencyStatusFacadeLocal",mappedName="ejb/InconsistencyStatusFacadeLocal")
public class InconsistencyStatusFacadeBean implements InconsistencyStatusFacadeBeanLocal {

		
    @EJB(name="InconsistencyStatusBusinessBeanLocal", beanInterface=InconsistencyStatusBusinessBeanLocal.class)
    private InconsistencyStatusBusinessBeanLocal businessInconsistencyStatus;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.InconsistencyStatusFacadeLocal#getAllInconsistencyStatuss()
     */
    public List<InconsistencyStatusVO> getAllInconsistencyStatuss() throws BusinessException {
    	return businessInconsistencyStatus.getAllInconsistencyStatuss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.InconsistencyStatusFacadeLocal#getInconsistencyStatussByID(java.lang.Long)
     */
    public InconsistencyStatusVO getInconsistencyStatusByID(Long id) throws BusinessException {
    	return businessInconsistencyStatus.getInconsistencyStatusByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.InconsistencyStatusFacadeLocal#createInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	public void createInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException {
		businessInconsistencyStatus.createInconsistencyStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.InconsistencyStatusFacadeLocal#updateInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	public void updateInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException {
		businessInconsistencyStatus.updateInconsistencyStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.InconsistencyStatusFacadeLocal#deleteInconsistencyStatus(co.com.directv.sdii.model.vo.InconsistencyStatusVO)
	 */
	public void deleteInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException {
		businessInconsistencyStatus.deleteInconsistencyStatus(obj);
	}
}
