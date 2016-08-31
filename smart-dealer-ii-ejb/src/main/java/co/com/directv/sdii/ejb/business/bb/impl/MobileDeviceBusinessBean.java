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
import co.com.directv.sdii.ejb.business.bb.MobileDeviceBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.MobileDevice;
import co.com.directv.sdii.model.vo.MobileDeviceVO;
import co.com.directv.sdii.persistence.dao.bb.MobileDeviceDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD MobileDevice
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.bb.MobileDeviceDAOLocal
 * @see co.com.directv.sdii.ejb.business.bb.MobileDeviceBusinessBeanLocal
 */
@Stateless(name="MobileDeviceBusinessBeanLocal",mappedName="ejb/MobileDeviceBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MobileDeviceBusinessBean extends BusinessBase implements MobileDeviceBusinessBeanLocal {

    @EJB(name="MobileDeviceDAOLocal", beanInterface=MobileDeviceDAOLocal.class)
    private MobileDeviceDAOLocal daoMobileDevice;
    
    private final static Logger log = UtilsBusiness.getLog4J(MobileDeviceBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.MobileDeviceBusinessBeanLocal#getAllMobileDevices()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MobileDeviceVO> getAllMobileDevices() throws BusinessException {
        log.debug("== Inicia getAllMobileDevices/MobileDeviceBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoMobileDevice.getAllMobileDevices(), MobileDeviceVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllMobileDevices/MobileDeviceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMobileDevices/MobileDeviceBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.MobileDeviceBusinessBeanLocal#getMobileDevicesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public MobileDeviceVO getMobileDeviceByID(Long id) throws BusinessException {
        log.debug("== Inicia getMobileDeviceByID/MobileDeviceBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MobileDevice objPojo = daoMobileDevice.getMobileDeviceByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(MobileDeviceVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMobileDeviceByID/MobileDeviceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMobileDeviceByID/MobileDeviceBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.MobileDeviceBusinessBeanLocal#createMobileDevice(co.com.directv.sdii.model.vo.MobileDeviceVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMobileDevice(MobileDeviceVO obj) throws BusinessException {
        log.debug("== Inicia createMobileDevice/MobileDeviceBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            MobileDevice objPojo =  UtilsBusiness.copyObject(MobileDevice.class, obj);
            daoMobileDevice.createMobileDevice(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createMobileDevice/MobileDeviceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMobileDevice/MobileDeviceBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.MobileDeviceBusinessBeanLocal#updateMobileDevice(co.com.directv.sdii.model.vo.MobileDeviceVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateMobileDevice(MobileDeviceVO obj) throws BusinessException {
        log.debug("== Inicia updateMobileDevice/MobileDeviceBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            MobileDevice objPojo =  UtilsBusiness.copyObject(MobileDevice.class, obj);
            daoMobileDevice.updateMobileDevice(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateMobileDevice/MobileDeviceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMobileDevice/MobileDeviceBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.MobileDeviceBusinessBeanLocal#deleteMobileDevice(co.com.directv.sdii.model.vo.MobileDeviceVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMobileDevice(MobileDeviceVO obj) throws BusinessException {
        log.debug("== Inicia deleteMobileDevice/MobileDeviceBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MobileDevice objPojo =  UtilsBusiness.copyObject(MobileDevice.class, obj);
            daoMobileDevice.deleteMobileDevice(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteMobileDevice/MobileDeviceBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMobileDevice/MobileDeviceBusinessBean ==");
        }
	}
}
