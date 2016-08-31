package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.AdjustmentElementsFacadeBeanLocal;
import co.com.directv.sdii.model.vo.AdjustmentElementsVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD AdjustmentElements
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.AdjustmentElementsFacadeLocal
 */
@Stateless(name="AdjustmentElementsFacadeLocal",mappedName="ejb/AdjustmentElementsFacadeLocal")
public class AdjustmentElementsFacadeBean implements AdjustmentElementsFacadeBeanLocal {

		
    @EJB(name="AdjustmentElementsBusinessBeanLocal", beanInterface=AdjustmentElementsBusinessBeanLocal.class)
    private AdjustmentElementsBusinessBeanLocal businessAdjustmentElements;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.AdjustmentElementsFacadeLocal#getAllAdjustmentElementss()
     */
    public List<AdjustmentElementsVO> getAllAdjustmentElementss() throws BusinessException {
    	return businessAdjustmentElements.getAllAdjustmentElementss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.AdjustmentElementsFacadeLocal#getAdjustmentElementssByID(java.lang.Long)
     */
    public AdjustmentElementsVO getAdjustmentElementsByID(Long id) throws BusinessException {
    	return businessAdjustmentElements.getAdjustmentElementsByID(id);
    }


	/* (non-Javadoc)
	 * @see #{$business_package_name$}.AdjustmentElementsFacadeLocal#updateAdjustmentElements(co.com.directv.sdii.model.vo.AdjustmentElementsVO)
	 */
	public void updateAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException {
		businessAdjustmentElements.updateAdjustmentElements(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.AdjustmentElementsFacadeLocal#deleteAdjustmentElements(co.com.directv.sdii.model.vo.AdjustmentElementsVO)
	 */
	public void deleteAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException {
		businessAdjustmentElements.deleteAdjustmentElements(obj);
	}
}
