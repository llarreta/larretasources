package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.NotSerPartialRetirementFacadeBeanLocal;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD NotSerPartialRetirement
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.NotSerPartialRetirementFacadeLocal
 */
@Stateless(name="NotSerPartialRetirementFacadeLocal",mappedName="ejb/NotSerPartialRetirementFacadeLocal")
public class NotSerPartialRetirementFacadeBean implements NotSerPartialRetirementFacadeBeanLocal {

		
    @EJB(name="NotSerPartialRetirementBusinessBeanLocal", beanInterface=NotSerPartialRetirementBusinessBeanLocal.class)
    private NotSerPartialRetirementBusinessBeanLocal businessNotSerPartialRetirement;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.NotSerPartialRetirementFacadeLocal#getAllNotSerPartialRetirements()
     */
    public List<NotSerPartialRetirementVO> getAllNotSerPartialRetirements() throws BusinessException {
    	return businessNotSerPartialRetirement.getAllNotSerPartialRetirements();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.NotSerPartialRetirementFacadeLocal#getNotSerPartialRetirementsByID(java.lang.Long)
     */
    public NotSerPartialRetirementVO getNotSerPartialRetirementByID(Long id) throws BusinessException {
    	return businessNotSerPartialRetirement.getNotSerPartialRetirementByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerPartialRetirementFacadeLocal#createNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	public void createNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException {
		businessNotSerPartialRetirement.createNotSerPartialRetirement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerPartialRetirementFacadeLocal#updateNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	public void updateNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException {
		businessNotSerPartialRetirement.updateNotSerPartialRetirement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerPartialRetirementFacadeLocal#deleteNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	public void deleteNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException {
		businessNotSerPartialRetirement.deleteNotSerPartialRetirement(obj);
	}
}
