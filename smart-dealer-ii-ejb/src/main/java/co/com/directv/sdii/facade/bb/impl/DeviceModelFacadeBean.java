package co.com.directv.sdii.facade.bb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.bb.DeviceModelBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.bb.DeviceModelFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DeviceModelVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DeviceModel
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.bb.DeviceModelFacadeLocal
 */
@Stateless(name="DeviceModelFacadeLocal",mappedName="ejb/DeviceModelFacadeLocal")
public class DeviceModelFacadeBean implements DeviceModelFacadeBeanLocal {

		
    @EJB(name="DeviceModelBusinessBeanLocal", beanInterface=DeviceModelBusinessBeanLocal.class)
    private DeviceModelBusinessBeanLocal businessDeviceModel;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DeviceModelFacadeLocal#getAllDeviceModels()
     */
    public List<DeviceModelVO> getAllDeviceModels() throws BusinessException {
    	return businessDeviceModel.getAllDeviceModels();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DeviceModelFacadeLocal#getDeviceModelsByID(java.lang.Long)
     */
    public DeviceModelVO getDeviceModelByID(Long id) throws BusinessException {
    	return businessDeviceModel.getDeviceModelByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeviceModelFacadeLocal#createDeviceModel(co.com.directv.sdii.model.vo.DeviceModelVO)
	 */
	public void createDeviceModel(DeviceModelVO obj) throws BusinessException {
		businessDeviceModel.createDeviceModel(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeviceModelFacadeLocal#updateDeviceModel(co.com.directv.sdii.model.vo.DeviceModelVO)
	 */
	public void updateDeviceModel(DeviceModelVO obj) throws BusinessException {
		businessDeviceModel.updateDeviceModel(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DeviceModelFacadeLocal#deleteDeviceModel(co.com.directv.sdii.model.vo.DeviceModelVO)
	 */
	public void deleteDeviceModel(DeviceModelVO obj) throws BusinessException {
		businessDeviceModel.deleteDeviceModel(obj);
	}
}
