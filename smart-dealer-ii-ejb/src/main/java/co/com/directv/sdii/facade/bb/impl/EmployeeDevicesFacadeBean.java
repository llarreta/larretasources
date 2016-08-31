package co.com.directv.sdii.facade.bb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.bb.EmployeeDevicesBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.bb.EmployeeDevicesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EmployeeDevicesVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD EmployeeDevices
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.bb.EmployeeDevicesFacadeLocal
 */
@Stateless(name="EmployeeDevicesFacadeLocal",mappedName="ejb/EmployeeDevicesFacadeLocal")
public class EmployeeDevicesFacadeBean implements EmployeeDevicesFacadeBeanLocal {

		
    @EJB(name="EmployeeDevicesBusinessBeanLocal", beanInterface=EmployeeDevicesBusinessBeanLocal.class)
    private EmployeeDevicesBusinessBeanLocal businessEmployeeDevices;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.EmployeeDevicesFacadeLocal#getAllEmployeeDevicess()
     */
    public List<EmployeeDevicesVO> getAllEmployeeDevicess() throws BusinessException {
    	return businessEmployeeDevices.getAllEmployeeDevicess();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.EmployeeDevicesFacadeLocal#getEmployeeDevicessByID(java.lang.Long)
     */
    public EmployeeDevicesVO getEmployeeDevicesByID(Long id) throws BusinessException {
    	return businessEmployeeDevices.getEmployeeDevicesByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.EmployeeDevicesFacadeLocal#createEmployeeDevices(co.com.directv.sdii.model.vo.EmployeeDevicesVO)
	 */
	public void createEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException {
		businessEmployeeDevices.createEmployeeDevices(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.EmployeeDevicesFacadeLocal#updateEmployeeDevices(co.com.directv.sdii.model.vo.EmployeeDevicesVO)
	 */
	public void updateEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException {
		businessEmployeeDevices.updateEmployeeDevices(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.EmployeeDevicesFacadeLocal#deleteEmployeeDevices(co.com.directv.sdii.model.vo.EmployeeDevicesVO)
	 */
	public void deleteEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException {
		businessEmployeeDevices.deleteEmployeeDevices(obj);
	}
}
