package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ReferenceStatus
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ReferenceStatusFacadeLocal
 */
@Stateless(name="ReferenceStatusFacadeLocal",mappedName="ejb/ReferenceStatusFacadeLocal")
public class ReferenceStatusFacadeBean implements ReferenceStatusFacadeBeanLocal {

		
    @EJB(name="ReferenceStatusBusinessBeanLocal", beanInterface=ReferenceStatusBusinessBeanLocal.class)
    private ReferenceStatusBusinessBeanLocal businessReferenceStatus;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceStatusFacadeLocal#getAllReferenceStatuss()
     */
    public List<ReferenceStatusVO> getAllReferenceStatuss() throws BusinessException {
    	return businessReferenceStatus.getAllReferenceStatuss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceStatusFacadeLocal#getReferenceStatussByID(java.lang.Long)
     */
    public ReferenceStatusVO getReferenceStatusByID(Long id) throws BusinessException {
    	return businessReferenceStatus.getReferenceStatusByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceStatusFacadeLocal#createReferenceStatus(co.com.directv.sdii.model.vo.ReferenceStatusVO)
	 */
	public void createReferenceStatus(ReferenceStatusVO obj) throws BusinessException {
		businessReferenceStatus.createReferenceStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceStatusFacadeLocal#updateReferenceStatus(co.com.directv.sdii.model.vo.ReferenceStatusVO)
	 */
	public void updateReferenceStatus(ReferenceStatusVO obj) throws BusinessException {
		businessReferenceStatus.updateReferenceStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceStatusFacadeLocal#deleteReferenceStatus(co.com.directv.sdii.model.vo.ReferenceStatusVO)
	 */
	public void deleteReferenceStatus(ReferenceStatusVO obj) throws BusinessException {
		businessReferenceStatus.deleteReferenceStatus(obj);
	}
}
