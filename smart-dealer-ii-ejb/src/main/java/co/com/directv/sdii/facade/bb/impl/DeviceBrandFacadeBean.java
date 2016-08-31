package co.com.directv.sdii.facade.bb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.bb.DeviceBrandBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.bb.DeviceBrandFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DeviceBrandVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DeviceBrand
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.bb.DeviceBrandFacadeLocal
 */
@Stateless(name="DeviceBrandFacadeLocal",mappedName="ejb/DeviceBrandFacadeLocal")
public class DeviceBrandFacadeBean implements DeviceBrandFacadeBeanLocal {

		
    @EJB(name="DeviceBrandBusinessBeanLocal", beanInterface=DeviceBrandBusinessBeanLocal.class)
    private DeviceBrandBusinessBeanLocal businessDeviceBrand;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DeviceBrandFacadeLocal#getAllDeviceBrands()
     */
    public List<DeviceBrandVO> getAllDeviceBrands() throws BusinessException {
    	return businessDeviceBrand.getAllDeviceBrands();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DeviceBrandFacadeLocal#getDeviceBrandsByID(java.lang.Long)
     */
    public DeviceBrandVO getDeviceBrandByID(Long id) throws BusinessException {
    	return businessDeviceBrand.getDeviceBrandByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeviceBrandFacadeLocal#createDeviceBrand(co.com.directv.sdii.model.vo.DeviceBrandVO)
	 */
	public void createDeviceBrand(DeviceBrandVO obj) throws BusinessException {
		businessDeviceBrand.createDeviceBrand(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeviceBrandFacadeLocal#updateDeviceBrand(co.com.directv.sdii.model.vo.DeviceBrandVO)
	 */
	public void updateDeviceBrand(DeviceBrandVO obj) throws BusinessException {
		businessDeviceBrand.updateDeviceBrand(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeviceBrandFacadeLocal#deleteDeviceBrand(co.com.directv.sdii.model.vo.DeviceBrandVO)
	 */
	public void deleteDeviceBrand(DeviceBrandVO obj) throws BusinessException {
		businessDeviceBrand.deleteDeviceBrand(obj);
	}
}
