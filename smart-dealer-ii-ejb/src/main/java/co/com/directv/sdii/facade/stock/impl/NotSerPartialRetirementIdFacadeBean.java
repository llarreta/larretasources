package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementIdBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.NotSerPartialRetirementIdFacadeBeanLocal;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD NotSerPartialRetirementId
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.NotSerPartialRetirementIdFacadeLocal
 */
@Stateless(name="NotSerPartialRetirementIdFacadeLocal",mappedName="ejb/NotSerPartialRetirementIdFacadeLocal")
public class NotSerPartialRetirementIdFacadeBean implements NotSerPartialRetirementIdFacadeBeanLocal {

		
    @EJB(name="NotSerPartialRetirementIdBusinessBeanLocal", beanInterface=NotSerPartialRetirementIdBusinessBeanLocal.class)
    private NotSerPartialRetirementIdBusinessBeanLocal businessNotSerPartialRetirementId;
    
  
    /* (non-Javadoc)
     * @see #{$business_package_name$}.NotSerPartialRetirementIdFacadeLocal#getAllNotSerPartialRetirementIds()
     */
    public List<NotSerPartialRetirementIdVO> getAllNotSerPartialRetirementIds() throws BusinessException {
    	return businessNotSerPartialRetirementId.getAllNotSerPartialRetirementIds();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.NotSerPartialRetirementIdFacadeLocal#getNotSerPartialRetirementIdsByID(java.lang.Long)
     */
    public NotSerPartialRetirementIdVO getNotSerPartialRetirementIdByID(Long id) throws BusinessException {
    	return businessNotSerPartialRetirementId.getNotSerPartialRetirementIdByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerPartialRetirementIdFacadeLocal#createNotSerPartialRetirementId(co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO)
	 */
	public void createNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException {
		businessNotSerPartialRetirementId.createNotSerPartialRetirementId(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerPartialRetirementIdFacadeLocal#updateNotSerPartialRetirementId(co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO)
	 */
	public void updateNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException {
		businessNotSerPartialRetirementId.updateNotSerPartialRetirementId(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.NotSerPartialRetirementIdFacadeLocal#deleteNotSerPartialRetirementId(co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO)
	 */
	public void deleteNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException {
		businessNotSerPartialRetirementId.deleteNotSerPartialRetirementId(obj);
	}
}
