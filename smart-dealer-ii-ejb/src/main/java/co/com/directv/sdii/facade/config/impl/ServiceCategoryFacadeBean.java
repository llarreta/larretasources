package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.ejb.business.config.ServiceCategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ServiceCategoryFacadeBeanLocal;
import co.com.directv.sdii.facade.config.ServiceCategoryFacadeBeanRemote;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;
 
/**
 * Session Bean implementation class ServiceCategoryFacadeBean
 */
@Stateless(name = "ServiceCategoryFacadeBeanLocal", mappedName = "ejb/ServiceCategoryFacadeBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local({ServiceCategoryFacadeBeanLocal.class})
@Remote({ServiceCategoryFacadeBeanRemote.class})
public class ServiceCategoryFacadeBean implements ServiceCategoryFacadeBeanLocal, ServiceCategoryFacadeBeanRemote {
	@EJB(name = "ServiceCategoryBusinessBeanLocal", beanInterface = ServiceCategoryBusinessBeanLocal.class)
	private ServiceCategoryBusinessBeanLocal serviceCategoryBean;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceCategoryVO> getAllServiceCategoryByTypeId(Long id) throws BusinessException{
		return serviceCategoryBean.getAllServiceCategoryByTypeId(id);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ServiceCategoryVO getServiceCategoryByServiceCode(String serviceCode) throws BusinessException{
		return serviceCategoryBean.getServiceCategoryByServiceCode(serviceCode);
	}
    
}
