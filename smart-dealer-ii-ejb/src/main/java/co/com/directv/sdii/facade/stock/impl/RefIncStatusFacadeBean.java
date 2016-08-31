package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.RefIncStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.RefIncStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.RefIncStatusVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD RefIncStatus
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.RefIncStatusFacadeLocal
 */
@Stateless(name="RefIncStatusFacadeLocal",mappedName="ejb/RefIncStatusFacadeLocal")
public class RefIncStatusFacadeBean implements RefIncStatusFacadeBeanLocal {

		
    @EJB(name="RefIncStatusBusinessBeanLocal", beanInterface=RefIncStatusBusinessBeanLocal.class)
    private RefIncStatusBusinessBeanLocal businessRefIncStatus;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RefIncStatusFacadeLocal#getAllRefIncStatuss()
     */
    public List<RefIncStatusVO> getAllRefIncStatuss() throws BusinessException {
    	return businessRefIncStatus.getAllRefIncStatuss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RefIncStatusFacadeLocal#getRefIncStatussByID(java.lang.Long)
     */
    public RefIncStatusVO getRefIncStatusByID(Long id) throws BusinessException {
    	return businessRefIncStatus.getRefIncStatusByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefIncStatusFacadeLocal#createRefIncStatus(co.com.directv.sdii.model.vo.RefIncStatusVO)
	 */
	public void createRefIncStatus(RefIncStatusVO obj) throws BusinessException {
		businessRefIncStatus.createRefIncStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefIncStatusFacadeLocal#updateRefIncStatus(co.com.directv.sdii.model.vo.RefIncStatusVO)
	 */
	public void updateRefIncStatus(RefIncStatusVO obj) throws BusinessException {
		businessRefIncStatus.updateRefIncStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefIncStatusFacadeLocal#deleteRefIncStatus(co.com.directv.sdii.model.vo.RefIncStatusVO)
	 */
	public void deleteRefIncStatus(RefIncStatusVO obj) throws BusinessException {
		businessRefIncStatus.deleteRefIncStatus(obj);
	}
}
