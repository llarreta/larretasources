package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.SaleChannelInstallerBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.SaleChannelInstallerFacadeBeanLocal;
import co.com.directv.sdii.model.vo.SaleChannelInstallerVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD SaleChannelInstaller
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.SaleChannelInstallerFacadeLocal
 */
@Stateless(name="SaleChannelInstallerFacadeLocal",mappedName="ejb/SaleChannelInstallerFacadeLocal")
public class SaleChannelInstallerFacadeBean implements SaleChannelInstallerFacadeBeanLocal {

		
    @EJB(name="SaleChannelInstallerBusinessBeanLocal", beanInterface=SaleChannelInstallerBusinessBeanLocal.class)
    private SaleChannelInstallerBusinessBeanLocal businessSaleChannelInstaller;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SaleChannelInstallerFacadeLocal#getAllSaleChannelInstallers()
     */
    public List<SaleChannelInstallerVO> getAllSaleChannelInstallers() throws BusinessException {
    	return businessSaleChannelInstaller.getAllSaleChannelInstallers();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SaleChannelInstallerFacadeLocal#getSaleChannelInstallersByID(java.lang.Long)
     */
    public SaleChannelInstallerVO getSaleChannelInstallerByID(Long id) throws BusinessException {
    	return businessSaleChannelInstaller.getSaleChannelInstallerByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChannelInstallerFacadeLocal#createSaleChannelInstaller(co.com.directv.sdii.model.vo.SaleChannelInstallerVO)
	 */
	public void createSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException {
		businessSaleChannelInstaller.createSaleChannelInstaller(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChannelInstallerFacadeLocal#updateSaleChannelInstaller(co.com.directv.sdii.model.vo.SaleChannelInstallerVO)
	 */
	public void updateSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException {
		businessSaleChannelInstaller.updateSaleChannelInstaller(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SaleChannelInstallerFacadeLocal#deleteSaleChannelInstaller(co.com.directv.sdii.model.vo.SaleChannelInstallerVO)
	 */
	public void deleteSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException {
		businessSaleChannelInstaller.deleteSaleChannelInstaller(obj);
	}
}
