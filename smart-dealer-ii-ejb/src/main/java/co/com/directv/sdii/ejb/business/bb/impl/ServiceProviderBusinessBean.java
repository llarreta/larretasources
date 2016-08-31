package co.com.directv.sdii.ejb.business.bb.impl;

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
import co.com.directv.sdii.ejb.business.bb.ServiceProviderBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ServiceProvider;
import co.com.directv.sdii.model.vo.ServiceProviderVO;
import co.com.directv.sdii.persistence.dao.bb.ServiceProviderDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ServiceProvider
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.bb.ServiceProviderDAOLocal
 * @see co.com.directv.sdii.ejb.business.bb.ServiceProviderBusinessBeanLocal
 */
@Stateless(name="ServiceProviderBusinessBeanLocal",mappedName="ejb/ServiceProviderBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceProviderBusinessBean extends BusinessBase implements ServiceProviderBusinessBeanLocal {

    @EJB(name="ServiceProviderDAOLocal", beanInterface=ServiceProviderDAOLocal.class)
    private ServiceProviderDAOLocal daoServiceProvider;
    
    private final static Logger log = UtilsBusiness.getLog4J(ServiceProviderBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.ServiceProviderBusinessBeanLocal#getAllServiceProviders()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceProviderVO> getAllServiceProviders() throws BusinessException {
        log.debug("== Inicia getAllServiceProviders/ServiceProviderBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoServiceProvider.getAllServiceProviders(), ServiceProviderVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllServiceProviders/ServiceProviderBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllServiceProviders/ServiceProviderBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.ServiceProviderBusinessBeanLocal#getServiceProvidersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceProviderVO getServiceProviderByID(Long id) throws BusinessException {
        log.debug("== Inicia getServiceProviderByID/ServiceProviderBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceProvider objPojo = daoServiceProvider.getServiceProviderByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ServiceProviderVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getServiceProviderByID/ServiceProviderBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceProviderByID/ServiceProviderBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.ServiceProviderBusinessBeanLocal#createServiceProvider(co.com.directv.sdii.model.vo.ServiceProviderVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createServiceProvider(ServiceProviderVO obj) throws BusinessException {
        log.debug("== Inicia createServiceProvider/ServiceProviderBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            ServiceProvider objPojo =  UtilsBusiness.copyObject(ServiceProvider.class, obj);
            daoServiceProvider.createServiceProvider(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createServiceProvider/ServiceProviderBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceProvider/ServiceProviderBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.ServiceProviderBusinessBeanLocal#updateServiceProvider(co.com.directv.sdii.model.vo.ServiceProviderVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateServiceProvider(ServiceProviderVO obj) throws BusinessException {
        log.debug("== Inicia updateServiceProvider/ServiceProviderBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            ServiceProvider objPojo =  UtilsBusiness.copyObject(ServiceProvider.class, obj);
            daoServiceProvider.updateServiceProvider(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateServiceProvider/ServiceProviderBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceProvider/ServiceProviderBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.ServiceProviderBusinessBeanLocal#deleteServiceProvider(co.com.directv.sdii.model.vo.ServiceProviderVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteServiceProvider(ServiceProviderVO obj) throws BusinessException {
        log.debug("== Inicia deleteServiceProvider/ServiceProviderBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceProvider objPojo =  UtilsBusiness.copyObject(ServiceProvider.class, obj);
            daoServiceProvider.deleteServiceProvider(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteServiceProvider/ServiceProviderBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceProvider/ServiceProviderBusinessBean ==");
        }
	}
}
