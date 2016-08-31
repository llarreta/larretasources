package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ShippingOrderElementFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ShippingOrderElementVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ShippingOrderElement
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ShippingOrderElementFacadeLocal
 */
@Stateless(name="ShippingOrderElementFacadeLocal",mappedName="ejb/ShippingOrderElementFacadeLocal")
public class ShippingOrderElementFacadeBean implements ShippingOrderElementFacadeBeanLocal {

		
    @EJB(name="ShippingOrderElementBusinessBeanLocal", beanInterface=ShippingOrderElementBusinessBeanLocal.class)
    private ShippingOrderElementBusinessBeanLocal businessShippingOrderElement;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ShippingOrderElementFacadeLocal#getAllShippingOrderElements()
     */
    public List<ShippingOrderElementVO> getAllShippingOrderElements() throws BusinessException {
    	return businessShippingOrderElement.getAllShippingOrderElements();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ShippingOrderElementFacadeLocal#getShippingOrderElementsByID(java.lang.Long)
     */
    public ShippingOrderElementVO getShippingOrderElementByID(Long id) throws BusinessException {
    	return businessShippingOrderElement.getShippingOrderElementByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ShippingOrderElementFacadeLocal#createShippingOrderElement(co.com.directv.sdii.model.vo.ShippingOrderElementVO)
	 */
	public void createShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException {
		businessShippingOrderElement.createShippingOrderElement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ShippingOrderElementFacadeLocal#updateShippingOrderElement(co.com.directv.sdii.model.vo.ShippingOrderElementVO)
	 */
	public void updateShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException {
		businessShippingOrderElement.updateShippingOrderElement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ShippingOrderElementFacadeLocal#deleteShippingOrderElement(co.com.directv.sdii.model.vo.ShippingOrderElementVO)
	 */
	public void deleteShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException {
		businessShippingOrderElement.deleteShippingOrderElement(obj);
	}
}
