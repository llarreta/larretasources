package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigTiposServicioBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigTiposServicioFacadeLocal;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceStatusVO;
import co.com.directv.sdii.model.vo.ServiceVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración SO Tipos Servicio.
 *
 * Caso de Uso CFG - 03 - Gestionar Códigos y Tipos Servicios
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigTiposServicioFacadeLocal",mappedName="ejb/ConfigTiposServicioFacadeLocal")
public class ConfigTiposServicioFacadeBean implements ConfigTiposServicioFacadeLocal {

    @EJB(name="ConfigTiposServicioBusinessLocal",beanInterface=ConfigTiposServicioBusinessLocal.class)
    private ConfigTiposServicioBusinessLocal business;

    public ServiceVO getServiceByID(Long id) throws BusinessException {
        return business.getServiceByID(id);
    }

    public ServiceVO getServiceByCode(String code) throws BusinessException {
        return business.getServiceByCode(code);
    }

    public List<ServiceVO> getServiceByName(String name) throws BusinessException {
        return business.getServiceByName(name);
    }

    public List<ServiceVO> getAll() throws BusinessException {
        return business.getAll();
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposServicioFacadeLocal#getAllByServiceCodeOpening(boolean)
     */
    public List<ServiceVO> getAllByServiceCodeOpening(boolean serviceCodeOpening) throws BusinessException {
    	return business.getAllByServiceCodeOpening(serviceCodeOpening);
    }

    public List<ServiceVO> getServicesByDealer(DealerVO dealer) throws BusinessException {
        return business.getServicesByDealer(dealer);
    }

    public void createService(ServiceVO obj, Long userId) throws BusinessException {
        business.createService(obj, userId);
    }

    public void updateService(ServiceVO obj) throws BusinessException {
        business.updateService(obj);
    }

    public void deleteService(ServiceVO obj) throws BusinessException {
        business.deleteService(obj);
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigTiposServicioFacadeLocal#getAllServicesStatus()
	 */
	@Override
	public List<ServiceStatusVO> getAllServicesStatus()
			throws BusinessException {
		return business.getAllServicesStatus();
	}
	
	/**
	 * @return
	 * @throws BusinessException
	 */
	public List<ServiceVO> getActiveServices() throws BusinessException{
		return business.getActiveServices();
	}
    
}
