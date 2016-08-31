package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.ServiceSuperCategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.ServiceSuperCategoryFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ServiceSuperCategory
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.ServiceSuperCategoryFacadeLocal
 */
@Stateless(name="ServiceSuperCategoryFacadeLocal",mappedName="ejb/ServiceSuperCategoryFacadeLocal")
public class ServiceSuperCategoryFacadeBean implements ServiceSuperCategoryFacadeBeanLocal {

		
    @EJB(name="ServiceSuperCategoryBusinessBeanLocal", beanInterface=ServiceSuperCategoryBusinessBeanLocal.class)
    private ServiceSuperCategoryBusinessBeanLocal businessServiceSuperCategory;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ServiceSuperCategoryFacadeLocal#getAllServiceSuperCategorys()
     */
    public List<ServiceSuperCategoryVO> getAllServiceSuperCategorys() throws BusinessException {
    	return businessServiceSuperCategory.getAllServiceSuperCategorys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ServiceSuperCategoryFacadeLocal#getServiceSuperCategorysByID(java.lang.Long)
     */
    public ServiceSuperCategoryVO getServiceSuperCategoryByID(Long id) throws BusinessException {
    	return businessServiceSuperCategory.getServiceSuperCategoryByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceSuperCategoryFacadeLocal#createServiceSuperCategory(co.com.directv.sdii.model.vo.ServiceSuperCategoryVO)
	 */
	public void createServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException {
		businessServiceSuperCategory.createServiceSuperCategory(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceSuperCategoryFacadeLocal#updateServiceSuperCategory(co.com.directv.sdii.model.vo.ServiceSuperCategoryVO)
	 */
	public void updateServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException {
		businessServiceSuperCategory.updateServiceSuperCategory(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ServiceSuperCategoryFacadeLocal#deleteServiceSuperCategory(co.com.directv.sdii.model.vo.ServiceSuperCategoryVO)
	 */
	public void deleteServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException {
		businessServiceSuperCategory.deleteServiceSuperCategory(obj);
	}
}
