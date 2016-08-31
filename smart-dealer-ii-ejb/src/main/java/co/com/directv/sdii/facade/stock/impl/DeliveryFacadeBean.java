package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.DeliveryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.DeliveryFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DeliveryVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Delivery
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.DeliveryFacadeLocal
 */
@Stateless(name="DeliveryFacadeLocal",mappedName="ejb/DeliveryFacadeLocal")
public class DeliveryFacadeBean implements DeliveryFacadeBeanLocal {

		
    @EJB(name="DeliveryBusinessBeanLocal", beanInterface=DeliveryBusinessBeanLocal.class)
    private DeliveryBusinessBeanLocal businessDelivery;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DeliveryFacadeLocal#getAllDeliverys()
     */
    public List<DeliveryVO> getAllDeliverys() throws BusinessException {
    	return businessDelivery.getAllDeliverys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DeliveryFacadeLocal#getDeliverysByID(java.lang.Long)
     */
    public DeliveryVO getDeliveryByID(Long id) throws BusinessException {
    	return businessDelivery.getDeliveryByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeliveryFacadeLocal#createDelivery(co.com.directv.sdii.model.vo.DeliveryVO)
	 */
	public void createDelivery(DeliveryVO obj) throws BusinessException {
		businessDelivery.createDelivery(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeliveryFacadeLocal#updateDelivery(co.com.directv.sdii.model.vo.DeliveryVO)
	 */
	public void updateDelivery(DeliveryVO obj) throws BusinessException {
		businessDelivery.updateDelivery(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeliveryFacadeLocal#deleteDelivery(co.com.directv.sdii.model.vo.DeliveryVO)
	 */
	public void deleteDelivery(DeliveryVO obj) throws BusinessException {
		businessDelivery.deleteDelivery(obj);
	}
}
