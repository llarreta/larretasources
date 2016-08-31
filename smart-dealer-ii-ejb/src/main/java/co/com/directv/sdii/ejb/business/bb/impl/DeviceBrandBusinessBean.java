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
import co.com.directv.sdii.ejb.business.bb.DeviceBrandBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.DeviceBrand;
import co.com.directv.sdii.model.vo.DeviceBrandVO;
import co.com.directv.sdii.persistence.dao.bb.DeviceBrandDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DeviceBrand
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.bb.DeviceBrandDAOLocal
 * @see co.com.directv.sdii.ejb.business.bb.DeviceBrandBusinessBeanLocal
 */
@Stateless(name="DeviceBrandBusinessBeanLocal",mappedName="ejb/DeviceBrandBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DeviceBrandBusinessBean extends BusinessBase implements DeviceBrandBusinessBeanLocal {

    @EJB(name="DeviceBrandDAOLocal", beanInterface=DeviceBrandDAOLocal.class)
    private DeviceBrandDAOLocal daoDeviceBrand;
    
    private final static Logger log = UtilsBusiness.getLog4J(DeviceBrandBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.DeviceBrandBusinessBeanLocal#getAllDeviceBrands()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DeviceBrandVO> getAllDeviceBrands() throws BusinessException {
        log.debug("== Inicia getAllDeviceBrands/DeviceBrandBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDeviceBrand.getAllDeviceBrands(), DeviceBrandVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDeviceBrands/DeviceBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDeviceBrands/DeviceBrandBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.DeviceBrandBusinessBeanLocal#getDeviceBrandsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DeviceBrandVO getDeviceBrandByID(Long id) throws BusinessException {
        log.debug("== Inicia getDeviceBrandByID/DeviceBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DeviceBrand objPojo = daoDeviceBrand.getDeviceBrandByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DeviceBrandVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDeviceBrandByID/DeviceBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDeviceBrandByID/DeviceBrandBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.DeviceBrandBusinessBeanLocal#createDeviceBrand(co.com.directv.sdii.model.vo.DeviceBrandVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDeviceBrand(DeviceBrandVO obj) throws BusinessException {
        log.debug("== Inicia createDeviceBrand/DeviceBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DeviceBrand objPojo =  UtilsBusiness.copyObject(DeviceBrand.class, obj);
            daoDeviceBrand.createDeviceBrand(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDeviceBrand/DeviceBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDeviceBrand/DeviceBrandBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.DeviceBrandBusinessBeanLocal#updateDeviceBrand(co.com.directv.sdii.model.vo.DeviceBrandVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDeviceBrand(DeviceBrandVO obj) throws BusinessException {
        log.debug("== Inicia updateDeviceBrand/DeviceBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DeviceBrand objPojo =  UtilsBusiness.copyObject(DeviceBrand.class, obj);
            daoDeviceBrand.updateDeviceBrand(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDeviceBrand/DeviceBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDeviceBrand/DeviceBrandBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.DeviceBrandBusinessBeanLocal#deleteDeviceBrand(co.com.directv.sdii.model.vo.DeviceBrandVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDeviceBrand(DeviceBrandVO obj) throws BusinessException {
        log.debug("== Inicia deleteDeviceBrand/DeviceBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DeviceBrand objPojo =  UtilsBusiness.copyObject(DeviceBrand.class, obj);
            daoDeviceBrand.deleteDeviceBrand(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDeviceBrand/DeviceBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDeviceBrand/DeviceBrandBusinessBean ==");
        }
	}
}
