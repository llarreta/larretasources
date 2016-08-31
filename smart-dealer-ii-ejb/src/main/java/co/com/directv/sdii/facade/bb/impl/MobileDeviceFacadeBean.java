package co.com.directv.sdii.facade.bb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.bb.MobileDeviceBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.bb.MobileDeviceFacadeBeanLocal;
import co.com.directv.sdii.model.vo.MobileDeviceVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD MobileDevice
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.bb.MobileDeviceFacadeLocal
 */
@Stateless(name="MobileDeviceFacadeLocal",mappedName="ejb/MobileDeviceFacadeLocal")
public class MobileDeviceFacadeBean implements MobileDeviceFacadeBeanLocal {

		
    @EJB(name="MobileDeviceBusinessBeanLocal", beanInterface=MobileDeviceBusinessBeanLocal.class)
    private MobileDeviceBusinessBeanLocal businessMobileDevice;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MobileDeviceFacadeLocal#getAllMobileDevices()
     */
    public List<MobileDeviceVO> getAllMobileDevices() throws BusinessException {
    	return businessMobileDevice.getAllMobileDevices();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MobileDeviceFacadeLocal#getMobileDevicesByID(java.lang.Long)
     */
    public MobileDeviceVO getMobileDeviceByID(Long id) throws BusinessException {
    	return businessMobileDevice.getMobileDeviceByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MobileDeviceFacadeLocal#createMobileDevice(co.com.directv.sdii.model.vo.MobileDeviceVO)
	 */
	public void createMobileDevice(MobileDeviceVO obj) throws BusinessException {
		businessMobileDevice.createMobileDevice(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MobileDeviceFacadeLocal#updateMobileDevice(co.com.directv.sdii.model.vo.MobileDeviceVO)
	 */
	public void updateMobileDevice(MobileDeviceVO obj) throws BusinessException {
		businessMobileDevice.updateMobileDevice(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MobileDeviceFacadeLocal#deleteMobileDevice(co.com.directv.sdii.model.vo.MobileDeviceVO)
	 */
	public void deleteMobileDevice(MobileDeviceVO obj) throws BusinessException {
		businessMobileDevice.deleteMobileDevice(obj);
	}
}
