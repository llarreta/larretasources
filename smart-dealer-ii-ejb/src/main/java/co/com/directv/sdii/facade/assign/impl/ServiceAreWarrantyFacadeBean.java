package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.ServiceAreWarrantyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.ServiceAreWarrantyFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ServiceAreWarrantyVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ServiceAreWarranty
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.ServiceAreWarrantyFacadeLocal
 */
@Stateless(name="ServiceAreWarrantyFacadeLocal",mappedName="ejb/ServiceAreWarrantyFacadeLocal")
public class ServiceAreWarrantyFacadeBean implements ServiceAreWarrantyFacadeBeanLocal {

		
    @EJB(name="ServiceAreWarrantyBusinessBeanLocal", beanInterface=ServiceAreWarrantyBusinessBeanLocal.class)
    private ServiceAreWarrantyBusinessBeanLocal businessServiceAreWarranty;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ServiceAreWarrantyFacadeLocal#getAllServiceAreWarrantys()
     */
    public List<ServiceAreWarrantyVO> getAllServiceAreWarrantys() throws BusinessException {
    	return businessServiceAreWarranty.getAllServiceAreWarrantys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ServiceAreWarrantyFacadeLocal#getServiceAreWarrantysByID(java.lang.Long)
     */
    public ServiceAreWarrantyVO getServiceAreWarrantyByID(Long id) throws BusinessException {
    	return businessServiceAreWarranty.getServiceAreWarrantyByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceAreWarrantyFacadeLocal#createServiceAreWarranty(co.com.directv.sdii.model.vo.ServiceAreWarrantyVO)
	 */
	public void createServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException {
		businessServiceAreWarranty.createServiceAreWarranty(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceAreWarrantyFacadeLocal#updateServiceAreWarranty(co.com.directv.sdii.model.vo.ServiceAreWarrantyVO)
	 */
	public void updateServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException {
		businessServiceAreWarranty.updateServiceAreWarranty(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceAreWarrantyFacadeLocal#deleteServiceAreWarranty(co.com.directv.sdii.model.vo.ServiceAreWarrantyVO)
	 */
	public void deleteServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException {
		businessServiceAreWarranty.deleteServiceAreWarranty(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.ServiceAreWarrantyFacadeBeanLocal#getServiceAreWarrantiesByServiceTypeWarrantyId(java.lang.Long)
	 */
	@Override
	public List<ServiceAreWarrantyVO> getServiceAreWarrantiesByServiceTypeWarrantyId(
			Long serviceTypeWarrantyId) throws BusinessException {
		return businessServiceAreWarranty.getServiceAreWarrantiesByServiceTypeWarrantyId(serviceTypeWarrantyId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.ServiceAreWarrantyFacadeBeanLocal#updateServiceAreWarrantiesConfiguration(java.lang.Long, java.util.List)
	 */
	@Override
	public void updateServiceAreWarrantiesConfiguration(Long serviceTypeWarrantyId,
			List<ServiceAreWarrantyVO> serviceAreWarranties)
			throws BusinessException {
		businessServiceAreWarranty.updateServiceAreWarrantiesConfiguration(serviceTypeWarrantyId, serviceAreWarranties);
		
	}
	
}
