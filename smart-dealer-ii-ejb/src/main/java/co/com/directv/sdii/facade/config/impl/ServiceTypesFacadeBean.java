package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.ejb.business.config.ServiceTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ServiceTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
 
/**
 * Session Bean implementation class ServiceTypesFacadeBean
 */
@Stateless(name = "ServiceTypesFacadeBeanLocal", mappedName = "ejb/ServiceTypesFacadeBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceTypesFacadeBean implements ServiceTypesFacadeBeanLocal {

	@EJB(name = "ServiceTypeBusinessBeanLocal", beanInterface = ServiceTypeBusinessBeanLocal.class)
	private ServiceTypeBusinessBeanLocal serviceTypeBean;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceTypeVO> getAllServiceType() throws BusinessException {
		return serviceTypeBean.getAllServiceType();
	}

}
