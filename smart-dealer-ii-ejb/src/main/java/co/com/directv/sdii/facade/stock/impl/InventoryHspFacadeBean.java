package co.com.directv.sdii.facade.stock.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.WorkOrderBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.InventoryHspBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.InventoryFacadeHspBeanLocal;
import co.com.directv.sdii.model.pojo.collection.InventoryElementGroupDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Implementación Fachada con las operaciones relacionadas con inventarios
 * 
 * Fecha de Creación: 28/05/2012
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 */
@Stateless(name="InventoryFacadeHspBeanLocal",mappedName="ejb/InventoryFacadeHspBeanLocal")
public class InventoryHspFacadeBean implements InventoryFacadeHspBeanLocal {

		
    @EJB(name="InventoryHspBusinessBeanLocal", beanInterface=InventoryHspBusinessBeanLocal.class)
    private InventoryHspBusinessBeanLocal businessInventoryHsp;
    
    @EJB(name="WorkOrderBusinessBeanLocal", beanInterface=WorkOrderBusinessBeanLocal.class)
    private WorkOrderBusinessBeanLocal workOrderBusinessBean;
    

	@Override
	public InventoryElementGroupDTOResponse getElementGroupStock(String countryCode, RequestCollectionInfo requestCollInfo)throws BusinessException {
		return businessInventoryHsp.getElementGroupStock(countryCode, requestCollInfo);
	}
	
	@Override
	public InventoryElementGroupDTOResponse getElementGroupTransit(String countryCode, RequestCollectionInfo requestCollInfo)throws BusinessException {
		return businessInventoryHsp.getElementGroupTransit(countryCode, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.InventoryFacadeHspBeanLocal#sendWorkOrdersForLastDayReport(java.lang.Long)
	 */
	public void sendWorkOrdersForLastDayReport(Long countryId) throws BusinessException{
		workOrderBusinessBean.sendWorkOrdersForLastDayReport(countryId);
	}
	
	
    
}
