package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ElementStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ElementStatusVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ElementStatus
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ElementStatusFacadeLocal
 */
@Stateless(name="ElementStatusFacadeLocal",mappedName="ejb/ElementStatusFacadeLocal")
public class ElementStatusFacadeBean implements ElementStatusFacadeBeanLocal {

		
    @EJB(name="ElementStatusBusinessBeanLocal", beanInterface=ElementStatusBusinessBeanLocal.class)
    private ElementStatusBusinessBeanLocal businessElementStatus;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementStatusFacadeLocal#getAllElementStatuss()
     */
    public List<ElementStatusVO> getAllElementStatuss() throws BusinessException {
    	return businessElementStatus.getAllElementStatuss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ElementStatusFacadeLocal#getElementStatussByID(java.lang.Long)
     */
    public ElementStatusVO getElementStatusByID(Long id) throws BusinessException {
    	return businessElementStatus.getElementStatusByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementStatusFacadeLocal#createElementStatus(co.com.directv.sdii.model.vo.ElementStatusVO)
	 */
	public void createElementStatus(ElementStatusVO obj) throws BusinessException {
		businessElementStatus.createElementStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementStatusFacadeLocal#updateElementStatus(co.com.directv.sdii.model.vo.ElementStatusVO)
	 */
	public void updateElementStatus(ElementStatusVO obj) throws BusinessException {
		businessElementStatus.updateElementStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ElementStatusFacadeLocal#deleteElementStatus(co.com.directv.sdii.model.vo.ElementStatusVO)
	 */
	public void deleteElementStatus(ElementStatusVO obj) throws BusinessException {
		businessElementStatus.deleteElementStatus(obj);
	}
}
