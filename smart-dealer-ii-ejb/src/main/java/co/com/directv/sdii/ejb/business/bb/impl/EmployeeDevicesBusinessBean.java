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
import co.com.directv.sdii.ejb.business.bb.EmployeeDevicesBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.EmployeeDevices;
import co.com.directv.sdii.model.vo.EmployeeDevicesVO;
import co.com.directv.sdii.persistence.dao.bb.EmployeeDevicesDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD EmployeeDevices
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.bb.EmployeeDevicesDAOLocal
 * @see co.com.directv.sdii.ejb.business.bb.EmployeeDevicesBusinessBeanLocal
 */
@Stateless(name="EmployeeDevicesBusinessBeanLocal",mappedName="ejb/EmployeeDevicesBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeDevicesBusinessBean extends BusinessBase implements EmployeeDevicesBusinessBeanLocal {

    @EJB(name="EmployeeDevicesDAOLocal", beanInterface=EmployeeDevicesDAOLocal.class)
    private EmployeeDevicesDAOLocal daoEmployeeDevices;
    
    private final static Logger log = UtilsBusiness.getLog4J(EmployeeDevicesBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.EmployeeDevicesBusinessBeanLocal#getAllEmployeeDevicess()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmployeeDevicesVO> getAllEmployeeDevicess() throws BusinessException {
        log.debug("== Inicia getAllEmployeeDevicess/EmployeeDevicesBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoEmployeeDevices.getAllEmployeeDevicess(), EmployeeDevicesVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllEmployeeDevicess/EmployeeDevicesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeDevicess/EmployeeDevicesBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.EmployeeDevicesBusinessBeanLocal#getEmployeeDevicessByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EmployeeDevicesVO getEmployeeDevicesByID(Long id) throws BusinessException {
        log.debug("== Inicia getEmployeeDevicesByID/EmployeeDevicesBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            EmployeeDevices objPojo = daoEmployeeDevices.getEmployeeDevicesByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(EmployeeDevicesVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmployeeDevicesByID/EmployeeDevicesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeDevicesByID/EmployeeDevicesBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.EmployeeDevicesBusinessBeanLocal#createEmployeeDevices(co.com.directv.sdii.model.vo.EmployeeDevicesVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException {
        log.debug("== Inicia createEmployeeDevices/EmployeeDevicesBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            EmployeeDevices objPojo =  UtilsBusiness.copyObject(EmployeeDevices.class, obj);
            daoEmployeeDevices.createEmployeeDevices(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createEmployeeDevices/EmployeeDevicesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeeDevices/EmployeeDevicesBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.EmployeeDevicesBusinessBeanLocal#updateEmployeeDevices(co.com.directv.sdii.model.vo.EmployeeDevicesVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException {
        log.debug("== Inicia updateEmployeeDevices/EmployeeDevicesBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            EmployeeDevices objPojo =  UtilsBusiness.copyObject(EmployeeDevices.class, obj);
            daoEmployeeDevices.updateEmployeeDevices(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateEmployeeDevices/EmployeeDevicesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeeDevices/EmployeeDevicesBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.EmployeeDevicesBusinessBeanLocal#deleteEmployeeDevices(co.com.directv.sdii.model.vo.EmployeeDevicesVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteEmployeeDevices(EmployeeDevicesVO obj) throws BusinessException {
        log.debug("== Inicia deleteEmployeeDevices/EmployeeDevicesBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            EmployeeDevices objPojo =  UtilsBusiness.copyObject(EmployeeDevices.class, obj);
            daoEmployeeDevices.deleteEmployeeDevices(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteEmployeeDevices/EmployeeDevicesBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeDevices/EmployeeDevicesBusinessBean ==");
        }
	}
}
