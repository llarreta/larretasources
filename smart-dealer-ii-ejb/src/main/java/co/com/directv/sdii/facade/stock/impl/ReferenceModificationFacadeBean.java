package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ReferenceModificationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceModificationFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ReferenceModification
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ReferenceModificationFacadeLocal
 */
@Stateless(name="ReferenceModificationFacadeLocal",mappedName="ejb/ReferenceModificationFacadeLocal")
public class ReferenceModificationFacadeBean implements ReferenceModificationFacadeBeanLocal {

		
    @EJB(name="ReferenceModificationBusinessBeanLocal", beanInterface=ReferenceModificationBusinessBeanLocal.class)
    private ReferenceModificationBusinessBeanLocal businessReferenceModification;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceModificationFacadeLocal#getAllReferenceModifications()
     */
    public List<ReferenceModificationVO> getAllReferenceModifications() throws BusinessException {
    	return businessReferenceModification.getAllReferenceModifications();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceModificationFacadeLocal#getReferenceModificationsByID(java.lang.Long)
     */
    public ReferenceModificationVO getReferenceModificationByID(Long id) throws BusinessException {
    	return businessReferenceModification.getReferenceModificationByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceModificationFacadeLocal#createReferenceModification(co.com.directv.sdii.model.vo.ReferenceModificationVO)
	 */
	public void createReferenceModification(ReferenceModificationVO obj) throws BusinessException {
		businessReferenceModification.createReferenceModification(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceModificationFacadeLocal#updateReferenceModification(co.com.directv.sdii.model.vo.ReferenceModificationVO)
	 */
	public void updateReferenceModification(ReferenceModificationVO obj) throws BusinessException {
		businessReferenceModification.updateReferenceModification(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceModificationFacadeLocal#deleteReferenceModification(co.com.directv.sdii.model.vo.ReferenceModificationVO)
	 */
	public void deleteReferenceModification(ReferenceModificationVO obj) throws BusinessException {
		businessReferenceModification.deleteReferenceModification(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReferenceModificationFacadeBeanLocal#getReferenceModificationsByReferenceID(java.lang.Long)
	 */
	public List<ReferenceModificationVO> getReferenceModificationsByReferenceID(
			Long refID) throws BusinessException {
		return businessReferenceModification.getReferenceModificationsByReferenceID(refID);
	}
}
