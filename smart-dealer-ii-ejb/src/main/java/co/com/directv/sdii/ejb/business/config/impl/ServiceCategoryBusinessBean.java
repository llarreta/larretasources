package co.com.directv.sdii.ejb.business.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ServiceCategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ServiceCategory;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;
import co.com.directv.sdii.persistence.dao.config.ServiceCategoryDAOLocal;

/**
 * Esta clase inplementa las operaciones de negocio de la 
 * interfaz ServiceCategoryBusinessBeanLocal
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 */
@Stateless(name = "ServiceCategoryBusinessBean", mappedName = "ejb/ServiceCategoryBusinessBean")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceCategoryBusinessBean extends BusinessBase implements ServiceCategoryBusinessBeanLocal {
	
	@EJB(beanInterface=ServiceCategoryDAOLocal.class, name="ServiceCategoryDAOLocal")
	private ServiceCategoryDAOLocal daoServiceCategory;
	private final static Logger log = UtilsBusiness.getLog4J(ServiceCategoryBusinessBean.class);
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ServiceCategoryBusinessBeanLocal#getAllServiceCategoryByTypeId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceCategoryVO> getAllServiceCategoryByTypeId(Long id) throws BusinessException{
		log.debug("== Inicia getAllServiceCategoryByTypeId/ServiceCategoryBusinessBean ==");
        try {
           List<ServiceCategory> serviceCategories = daoServiceCategory.getServiceCategoryByTypeId(id);
           if(serviceCategories == null)
        	   return null;
           return UtilsBusiness.convertList(serviceCategories, ServiceCategoryVO.class);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getAllServiceCategoryByTypeId/ServiceCategoryBusinessBean");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllServiceCategoryByTypeId/ServiceCategoryBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ServiceCategoryBusinessBeanLocal#getServiceCategoryByCode(java.lang.String)
	 */
	@Override
	public ServiceCategoryVO getServiceCategoryByCode(String serviceCategoryCode)
			throws BusinessException {
		log.debug("== Inicia getServiceCategoryByCode/ServiceCategoryBusinessBean ==");
        try {
           return UtilsBusiness.copyObject(ServiceCategoryVO.class, daoServiceCategory.getServiceCategoryByCode(serviceCategoryCode));
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getServiceCategoryByCode/ServiceCategoryBusinessBean");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceCategoryByCode/ServiceCategoryBusinessBean ==");
        }
	}
	

	@Override
	public ServiceCategoryVO getServiceCategoryByServiceCode(String serviceCode) throws BusinessException {
		log.debug("== Inicia getServiceCategoryByServiceCode/ServiceCategoryBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(serviceCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()); 
        	return UtilsBusiness.copyObject(ServiceCategoryVO.class, daoServiceCategory.getServiceCategoryByServiceCode(serviceCode));
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getServiceCategoryByServiceCode/ServiceCategoryBusinessBean");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceCategoryByServiceCode/ServiceCategoryBusinessBean ==");
        }
	}
	
}
