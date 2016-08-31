package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ItemStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ItemStatusVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ItemStatus
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ItemStatusFacadeLocal
 */
@Stateless(name="ItemStatusFacadeLocal",mappedName="ejb/ItemStatusFacadeLocal")
public class ItemStatusFacadeBean implements ItemStatusFacadeBeanLocal {

		
    @EJB(name="ItemStatusBusinessBeanLocal", beanInterface=ItemStatusBusinessBeanLocal.class)
    private ItemStatusBusinessBeanLocal businessItemStatus;
    
  
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ItemStatusFacadeLocal#getAllItemStatuss()
     */
    public List<ItemStatusVO> getAllItemStatuss() throws BusinessException {
    	return businessItemStatus.getAllItemStatuss();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ItemStatusFacadeLocal#getItemStatussByID(java.lang.Long)
     */
    public ItemStatusVO getItemStatusByID(Long id) throws BusinessException {
    	return businessItemStatus.getItemStatusByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ItemStatusFacadeLocal#createItemStatus(co.com.directv.sdii.model.vo.ItemStatusVO)
	 */
	public void createItemStatus(ItemStatusVO obj) throws BusinessException {
		businessItemStatus.createItemStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ItemStatusFacadeLocal#updateItemStatus(co.com.directv.sdii.model.vo.ItemStatusVO)
	 */
	public void updateItemStatus(ItemStatusVO obj) throws BusinessException {
		businessItemStatus.updateItemStatus(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ItemStatusFacadeLocal#deleteItemStatus(co.com.directv.sdii.model.vo.ItemStatusVO)
	 */
	public void deleteItemStatus(ItemStatusVO obj) throws BusinessException {
		businessItemStatus.deleteItemStatus(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ItemStatusFacadeBeanLocal#getItemStatusByCode(java.lang.String)
	 */
	@Override
	public ItemStatusVO getItemStatusByCode(String itemStatusCode)
			throws BusinessException {
		return businessItemStatus.getItemStatusByCode(itemStatusCode);
	}
}
