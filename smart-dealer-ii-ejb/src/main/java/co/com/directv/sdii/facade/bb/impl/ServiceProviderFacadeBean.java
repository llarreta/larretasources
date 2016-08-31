package co.com.directv.sdii.facade.bb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.bb.ServiceProviderBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.bb.ServiceProviderFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ServiceProviderVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ServiceProvider
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.bb.ServiceProviderFacadeLocal
 */
@Stateless(name="ServiceProviderFacadeLocal",mappedName="ejb/ServiceProviderFacadeLocal")
public class ServiceProviderFacadeBean implements ServiceProviderFacadeBeanLocal {

		
    @EJB(name="ServiceProviderBusinessBeanLocal", beanInterface=ServiceProviderBusinessBeanLocal.class)
    private ServiceProviderBusinessBeanLocal businessServiceProvider;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ServiceProviderFacadeLocal#getAllServiceProviders()
     */
    public List<ServiceProviderVO> getAllServiceProviders() throws BusinessException {
    	return businessServiceProvider.getAllServiceProviders();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ServiceProviderFacadeLocal#getServiceProvidersByID(java.lang.Long)
     */
    public ServiceProviderVO getServiceProviderByID(Long id) throws BusinessException {
    	return businessServiceProvider.getServiceProviderByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceProviderFacadeLocal#createServiceProvider(co.com.directv.sdii.model.vo.ServiceProviderVO)
	 */
	public void createServiceProvider(ServiceProviderVO obj) throws BusinessException {
		businessServiceProvider.createServiceProvider(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceProviderFacadeLocal#updateServiceProvider(co.com.directv.sdii.model.vo.ServiceProviderVO)
	 */
	public void updateServiceProvider(ServiceProviderVO obj) throws BusinessException {
		businessServiceProvider.updateServiceProvider(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceProviderFacadeLocal#deleteServiceProvider(co.com.directv.sdii.model.vo.ServiceProviderVO)
	 */
	public void deleteServiceProvider(ServiceProviderVO obj) throws BusinessException {
		businessServiceProvider.deleteServiceProvider(obj);
	}
}
