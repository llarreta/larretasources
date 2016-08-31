package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.SaleChannelSellerBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.SaleChannelSellerFacadeBeanLocal;
import co.com.directv.sdii.model.vo.SaleChannelSellerVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD SaleChannelSeller
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.SaleChannelSellerFacadeLocal
 */
@Stateless(name="SaleChannelSellerFacadeLocal",mappedName="ejb/SaleChannelSellerFacadeLocal")
public class SaleChannelSellerFacadeBean implements SaleChannelSellerFacadeBeanLocal {

		
    @EJB(name="SaleChannelSellerBusinessBeanLocal", beanInterface=SaleChannelSellerBusinessBeanLocal.class)
    private SaleChannelSellerBusinessBeanLocal businessSaleChannelSeller;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SaleChannelSellerFacadeLocal#getAllSaleChannelSellers()
     */
    public List<SaleChannelSellerVO> getAllSaleChannelSellers() throws BusinessException {
    	return businessSaleChannelSeller.getAllSaleChannelSellers();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SaleChannelSellerFacadeLocal#getSaleChannelSellersByID(java.lang.Long)
     */
    public SaleChannelSellerVO getSaleChannelSellerByID(Long id) throws BusinessException {
    	return businessSaleChannelSeller.getSaleChannelSellerByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChannelSellerFacadeLocal#createSaleChannelSeller(co.com.directv.sdii.model.vo.SaleChannelSellerVO)
	 */
	public void createSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException {
		businessSaleChannelSeller.createSaleChannelSeller(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChannelSellerFacadeLocal#updateSaleChannelSeller(co.com.directv.sdii.model.vo.SaleChannelSellerVO)
	 */
	public void updateSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException {
		businessSaleChannelSeller.updateSaleChannelSeller(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChannelSellerFacadeLocal#deleteSaleChannelSeller(co.com.directv.sdii.model.vo.SaleChannelSellerVO)
	 */
	public void deleteSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException {
		businessSaleChannelSeller.deleteSaleChannelSeller(obj);
	}
}
