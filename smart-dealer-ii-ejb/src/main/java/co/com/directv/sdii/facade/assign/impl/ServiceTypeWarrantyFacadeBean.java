package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.ServiceTypeWarrantyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.ServiceTypeWarrantyFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ServiceTypeWarranty
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.ServiceTypeWarrantyFacadeLocal
 */
@Stateless(name="ServiceTypeWarrantyFacadeLocal",mappedName="ejb/ServiceTypeWarrantyFacadeLocal")
public class ServiceTypeWarrantyFacadeBean implements ServiceTypeWarrantyFacadeBeanLocal {

		
    @EJB(name="ServiceTypeWarrantyBusinessBeanLocal", beanInterface=ServiceTypeWarrantyBusinessBeanLocal.class)
    private ServiceTypeWarrantyBusinessBeanLocal businessServiceTypeWarranty;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ServiceTypeWarrantyFacadeLocal#getAllServiceTypeWarrantys()
     */
    public List<ServiceTypeWarrantyVO> getAllServiceTypeWarrantys() throws BusinessException {
    	return businessServiceTypeWarranty.getAllServiceTypeWarrantys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ServiceTypeWarrantyFacadeLocal#getServiceTypeWarrantysByID(java.lang.Long)
     */
    public ServiceTypeWarrantyVO getServiceTypeWarrantyByID(Long id) throws BusinessException {
    	return businessServiceTypeWarranty.getServiceTypeWarrantyByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceTypeWarrantyFacadeLocal#createServiceTypeWarranty(co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO)
	 */
	public void createServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException {
		businessServiceTypeWarranty.createServiceTypeWarranty(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceTypeWarrantyFacadeLocal#updateServiceTypeWarranty(co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO)
	 */
	public void updateServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException {
		businessServiceTypeWarranty.updateServiceTypeWarranty(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceTypeWarrantyFacadeLocal#deleteServiceTypeWarranty(co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO)
	 */
	public void deleteServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException {
		businessServiceTypeWarranty.deleteServiceTypeWarranty(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.ServiceTypeWarrantyFacadeBeanLocal#getServiceTypeWarrantiesConfigurationByCountryId(java.lang.Long)
	 */
	@Override
	public List<ServiceTypeWarrantyVO> getServiceTypeWarrantiesConfigurationByCountryId(
			Long countryId) throws BusinessException {
		return businessServiceTypeWarranty.getServiceTypeWarrantiesConfigurationByCountryId(countryId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.ServiceTypeWarrantyFacadeBeanLocal#updateServiceTypeWarrantiesConfiguration(java.util.List)
	 */
	@Override
	public void updateServiceTypeWarrantiesConfiguration(
			List<ServiceTypeWarrantyVO> serviceTypeWarranties)
			throws BusinessException {
		businessServiceTypeWarranty.updateServiceTypeWarrantiesConfiguration(serviceTypeWarranties);
	}
	
}
