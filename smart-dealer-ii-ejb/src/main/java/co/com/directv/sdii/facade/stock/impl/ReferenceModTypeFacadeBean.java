package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ReferenceModTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceModTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ReferenceModTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ReferenceModType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ReferenceModTypeFacadeLocal
 */
@Stateless(name="ReferenceModTypeFacadeLocal",mappedName="ejb/ReferenceModTypeFacadeLocal")
public class ReferenceModTypeFacadeBean implements ReferenceModTypeFacadeBeanLocal {

		
    @EJB(name="ReferenceModTypeBusinessBeanLocal", beanInterface=ReferenceModTypeBusinessBeanLocal.class)
    private ReferenceModTypeBusinessBeanLocal businessReferenceModType;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceModTypeFacadeLocal#getAllReferenceModTypes()
     */
    public List<ReferenceModTypeVO> getAllReferenceModTypes() throws BusinessException {
    	return businessReferenceModType.getAllReferenceModTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReferenceModTypeFacadeLocal#getReferenceModTypesByID(java.lang.Long)
     */
    public ReferenceModTypeVO getReferenceModTypeByID(Long id) throws BusinessException {
    	return businessReferenceModType.getReferenceModTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceModTypeFacadeLocal#createReferenceModType(co.com.directv.sdii.model.vo.ReferenceModTypeVO)
	 */
	public void createReferenceModType(ReferenceModTypeVO obj) throws BusinessException {
		businessReferenceModType.createReferenceModType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceModTypeFacadeLocal#updateReferenceModType(co.com.directv.sdii.model.vo.ReferenceModTypeVO)
	 */
	public void updateReferenceModType(ReferenceModTypeVO obj) throws BusinessException {
		businessReferenceModType.updateReferenceModType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReferenceModTypeFacadeLocal#deleteReferenceModType(co.com.directv.sdii.model.vo.ReferenceModTypeVO)
	 */
	public void deleteReferenceModType(ReferenceModTypeVO obj) throws BusinessException {
		businessReferenceModType.deleteReferenceModType(obj);
	}
}
