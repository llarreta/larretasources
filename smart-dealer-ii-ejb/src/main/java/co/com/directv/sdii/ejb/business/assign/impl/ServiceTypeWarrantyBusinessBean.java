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
import co.com.directv.sdii.ejb.business.assign.ServiceTypeWarrantyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ServiceTypeWarranty;
import co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO;
import co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ServiceTypeWarranty
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.ServiceTypeWarrantyDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceTypeWarrantyBusinessBeanLocal
 */
@Stateless(name="ServiceTypeWarrantyBusinessBeanLocal",mappedName="ejb/ServiceTypeWarrantyBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceTypeWarrantyBusinessBean extends BusinessBase implements ServiceTypeWarrantyBusinessBeanLocal {

    @EJB(name="ServiceTypeWarrantyDAOLocal", beanInterface=ServiceTypeWarrantyDAOLocal.class)
    private ServiceTypeWarrantyDAOLocal daoServiceTypeWarranty;
    
    private final static Logger log = UtilsBusiness.getLog4J(ServiceTypeWarrantyBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceTypeWarrantyBusinessBeanLocal#getAllServiceTypeWarrantys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceTypeWarrantyVO> getAllServiceTypeWarrantys() throws BusinessException {
        log.debug("== Inicia getAllServiceTypeWarrantys/ServiceTypeWarrantyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoServiceTypeWarranty.getAllServiceTypeWarrantys(), ServiceTypeWarrantyVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllServiceTypeWarrantys/ServiceTypeWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllServiceTypeWarrantys/ServiceTypeWarrantyBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceTypeWarrantyBusinessBeanLocal#getServiceTypeWarrantysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceTypeWarrantyVO getServiceTypeWarrantyByID(Long id) throws BusinessException {
        log.debug("== Inicia getServiceTypeWarrantyByID/ServiceTypeWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceTypeWarranty objPojo = daoServiceTypeWarranty.getServiceTypeWarrantyByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ServiceTypeWarrantyVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getServiceTypeWarrantyByID/ServiceTypeWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceTypeWarrantyByID/ServiceTypeWarrantyBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceTypeWarrantyBusinessBeanLocal#createServiceTypeWarranty(co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException {
        log.debug("== Inicia createServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            ServiceTypeWarranty objPojo =  UtilsBusiness.copyObject(ServiceTypeWarranty.class, obj);
            daoServiceTypeWarranty.createServiceTypeWarranty(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceTypeWarrantyBusinessBeanLocal#updateServiceTypeWarranty(co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException {
        log.debug("== Inicia updateServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            ServiceTypeWarranty objPojo =  UtilsBusiness.copyObject(ServiceTypeWarranty.class, obj);
            objPojo.setDateLastChange(UtilsBusiness.fechaActual());
            daoServiceTypeWarranty.updateServiceTypeWarranty(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceTypeWarrantyBusinessBeanLocal#deleteServiceTypeWarranty(co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteServiceTypeWarranty(ServiceTypeWarrantyVO obj) throws BusinessException {
        log.debug("== Inicia deleteServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceTypeWarranty objPojo =  UtilsBusiness.copyObject(ServiceTypeWarranty.class, obj);
            daoServiceTypeWarranty.deleteServiceTypeWarranty(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceTypeWarranty/ServiceTypeWarrantyBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.ServiceTypeWarrantyBusinessBeanLocal#getServiceTypeWarrantiesConfigurationByCountryId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ServiceTypeWarrantyVO> getServiceTypeWarrantiesConfigurationByCountryId(
			Long countryId) throws BusinessException {
		log.debug("== Inicia getServiceTypeWarrantiesConfigurationByCountryId/ServiceTypeWarrantyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoServiceTypeWarranty.getServiceTypeWarrantiesConfigurationByCountryId(countryId), ServiceTypeWarrantyVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getServiceTypeWarrantiesConfigurationByCountryId/ServiceTypeWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceTypeWarrantiesConfigurationByCountryId/ServiceTypeWarrantyBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.ServiceTypeWarrantyBusinessBeanLocal#updateServiceTypeWarrantiesConfiguration(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateServiceTypeWarrantiesConfiguration(
			List<ServiceTypeWarrantyVO> serviceTypeWarranties)
			throws BusinessException {
		log.debug("== Inicia updateServiceTypeWarrantiesConfiguration/ServiceTypeWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(serviceTypeWarranties, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
	        for (ServiceTypeWarrantyVO serviceTypeWarrantyVO : serviceTypeWarranties) {
				updateServiceTypeWarranty(serviceTypeWarrantyVO);
			}
        } catch (BusinessException e) {
        	throw e;
        } finally {
        	log.debug("== Termina updateServiceTypeWarrantiesConfiguration/ServiceTypeWarrantyBusinessBean ==");
        }
	}
}
