package co.com.directv.sdii.ejb.business.assign.impl;

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
import co.com.directv.sdii.ejb.business.assign.ServiceSuperCategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;
import co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ServiceSuperCategory
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.ServiceSuperCategoryDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceSuperCategoryBusinessBeanLocal
 */
@Stateless(name="ServiceSuperCategoryBusinessBeanLocal",mappedName="ejb/ServiceSuperCategoryBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceSuperCategoryBusinessBean extends BusinessBase implements ServiceSuperCategoryBusinessBeanLocal {

    @EJB(name="ServiceSuperCategoryDAOLocal", beanInterface=ServiceSuperCategoryDAOLocal.class)
    private ServiceSuperCategoryDAOLocal daoServiceSuperCategory;
    
    private final static Logger log = UtilsBusiness.getLog4J(ServiceSuperCategoryBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceSuperCategoryBusinessBeanLocal#getAllServiceSuperCategorys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceSuperCategoryVO> getAllServiceSuperCategorys() throws BusinessException {
        log.debug("== Inicia getAllServiceSuperCategorys/ServiceSuperCategoryBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoServiceSuperCategory.getAllServiceSuperCategorys(), ServiceSuperCategoryVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllServiceSuperCategorys/ServiceSuperCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllServiceSuperCategorys/ServiceSuperCategoryBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceSuperCategoryBusinessBeanLocal#getServiceSuperCategorysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceSuperCategoryVO getServiceSuperCategoryByID(Long id) throws BusinessException {
        log.debug("== Inicia getServiceSuperCategoryByID/ServiceSuperCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceSuperCategory objPojo = daoServiceSuperCategory.getServiceSuperCategoryByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ServiceSuperCategoryVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getServiceSuperCategoryByID/ServiceSuperCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceSuperCategoryByID/ServiceSuperCategoryBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceSuperCategoryBusinessBeanLocal#createServiceSuperCategory(co.com.directv.sdii.model.vo.ServiceSuperCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException {
        log.debug("== Inicia createServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            ServiceSuperCategory objPojo =  UtilsBusiness.copyObject(ServiceSuperCategory.class, obj);
            daoServiceSuperCategory.createServiceSuperCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceSuperCategoryBusinessBeanLocal#updateServiceSuperCategory(co.com.directv.sdii.model.vo.ServiceSuperCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException {
        log.debug("== Inicia updateServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            ServiceSuperCategory objPojo =  UtilsBusiness.copyObject(ServiceSuperCategory.class, obj);
            daoServiceSuperCategory.updateServiceSuperCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceSuperCategoryBusinessBeanLocal#deleteServiceSuperCategory(co.com.directv.sdii.model.vo.ServiceSuperCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteServiceSuperCategory(ServiceSuperCategoryVO obj) throws BusinessException {
        log.debug("== Inicia deleteServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceSuperCategory objPojo =  UtilsBusiness.copyObject(ServiceSuperCategory.class, obj);
            daoServiceSuperCategory.deleteServiceSuperCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceSuperCategory/ServiceSuperCategoryBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.ServiceSuperCategoryBusinessBeanLocal#getServiceSuperCategoryByServiceCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ServiceSuperCategoryVO getServiceSuperCategoryByServiceCode(
			String serviceCode) throws BusinessException {
		log.debug("== Inicia getServiceSuperCategoryByServiceCode/ServiceSuperCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(serviceCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceSuperCategory objPojo = daoServiceSuperCategory.getServiceSuperCategoryByServiceCode(serviceCode);
            if (objPojo == null) {
            	Object[] params = null;
    			params = new Object[1];			
    			params[0] = serviceCode;
    			throw new BusinessException(ErrorBusinessMessages.CORE_CR049.getCode(), ErrorBusinessMessages.CORE_CR049.getMessage(params));
            }
            return UtilsBusiness.copyObject(ServiceSuperCategoryVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getServiceSuperCategoryByServiceCode/ServiceSuperCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceSuperCategoryByServiceCode/ServiceSuperCategoryBusinessBean ==");
        }
	}
}
