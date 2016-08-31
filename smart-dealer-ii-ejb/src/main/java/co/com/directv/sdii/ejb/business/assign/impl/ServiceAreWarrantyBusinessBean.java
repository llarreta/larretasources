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
import co.com.directv.sdii.ejb.business.assign.ServiceAreWarrantyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ServiceAreWarranty;
import co.com.directv.sdii.model.vo.ServiceAreWarrantyVO;
import co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ServiceAreWarranty
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.ServiceAreWarrantyDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceAreWarrantyBusinessBeanLocal
 */
@Stateless(name="ServiceAreWarrantyBusinessBeanLocal",mappedName="ejb/ServiceAreWarrantyBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceAreWarrantyBusinessBean extends BusinessBase implements ServiceAreWarrantyBusinessBeanLocal {

    @EJB(name="ServiceAreWarrantyDAOLocal", beanInterface=ServiceAreWarrantyDAOLocal.class)
    private ServiceAreWarrantyDAOLocal daoServiceAreWarranty;
    
    private final static Logger log = UtilsBusiness.getLog4J(ServiceAreWarrantyBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceAreWarrantyBusinessBeanLocal#getAllServiceAreWarrantys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceAreWarrantyVO> getAllServiceAreWarrantys() throws BusinessException {
        log.debug("== Inicia getAllServiceAreWarrantys/ServiceAreWarrantyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoServiceAreWarranty.getAllServiceAreWarrantys(), ServiceAreWarrantyVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllServiceAreWarrantys/ServiceAreWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllServiceAreWarrantys/ServiceAreWarrantyBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceAreWarrantyBusinessBeanLocal#getServiceAreWarrantysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceAreWarrantyVO getServiceAreWarrantyByID(Long id) throws BusinessException {
        log.debug("== Inicia getServiceAreWarrantyByID/ServiceAreWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceAreWarranty objPojo = daoServiceAreWarranty.getServiceAreWarrantyByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ServiceAreWarrantyVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getServiceAreWarrantyByID/ServiceAreWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceAreWarrantyByID/ServiceAreWarrantyBusinessBean ==");
        }
    }

    /*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.ServiceAreWarrantyBusinessBeanLocal#getServiceAreWarrantiesByServiceTypeWarrantyId(java.lang.Long)
	 */
	@Override
	public List<ServiceAreWarrantyVO> getServiceAreWarrantiesByServiceTypeWarrantyId(
			Long serviceTypeWarrantyId) throws BusinessException {
		log.debug("== Inicia getServiceAreWarrantiesByServiceTypeWarrantyId/ServiceAreWarrantyBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoServiceAreWarranty.getServiceAreWarrantiesByServiceTypeWarrantyId(serviceTypeWarrantyId), ServiceAreWarrantyVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getServiceAreWarrantiesByServiceTypeWarrantyId/ServiceAreWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServiceAreWarrantiesByServiceTypeWarrantyId/ServiceAreWarrantyBusinessBean ==");
        }
	}
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceAreWarrantyBusinessBeanLocal#createServiceAreWarranty(co.com.directv.sdii.model.vo.ServiceAreWarrantyVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException {
        log.debug("== Inicia createServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            ServiceAreWarranty objPojo =  UtilsBusiness.copyObject(ServiceAreWarranty.class, obj);
            daoServiceAreWarranty.createServiceAreWarranty(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceAreWarrantyBusinessBeanLocal#updateServiceAreWarranty(co.com.directv.sdii.model.vo.ServiceAreWarrantyVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException {
        log.debug("== Inicia updateServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            ServiceAreWarranty objPojo =  UtilsBusiness.copyObject(ServiceAreWarranty.class, obj);
            daoServiceAreWarranty.updateServiceAreWarranty(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.ServiceAreWarrantyBusinessBeanLocal#deleteServiceAreWarranty(co.com.directv.sdii.model.vo.ServiceAreWarrantyVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteServiceAreWarranty(ServiceAreWarrantyVO obj) throws BusinessException {
        log.debug("== Inicia deleteServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ServiceAreWarranty objPojo =  UtilsBusiness.copyObject(ServiceAreWarranty.class, obj);
            daoServiceAreWarranty.deleteServiceAreWarranty(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteServiceAreWarranty/ServiceAreWarrantyBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.ServiceAreWarrantyBusinessBeanLocal#updateServiceAreWarrantiesConfiguration(java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateServiceAreWarrantiesConfiguration(Long serviceTypeWarrantyId,
			List<ServiceAreWarrantyVO> serviceAreWarranties)
			throws BusinessException {
		log.debug("== Inicia updateServiceAreWarrantiesConfiguration/ServiceAreWarrantyBusinessBean ==");
		try {
			daoServiceAreWarranty.deleteByServiceTypeWarrantyId(serviceTypeWarrantyId);
			for (ServiceAreWarrantyVO serviceAreWarrantyVO : serviceAreWarranties) {
				serviceAreWarrantyVO.setInclusionDate(UtilsBusiness.fechaActual());
				createServiceAreWarranty(serviceAreWarrantyVO);
			}
		} catch(Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación updateServiceAreWarrantiesConfiguration/ServiceAreWarrantyBusinessBean ==");
        	throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateServiceAreWarrantiesConfiguration/ServiceAreWarrantyBusinessBean ==");
		}
		
	}

}
