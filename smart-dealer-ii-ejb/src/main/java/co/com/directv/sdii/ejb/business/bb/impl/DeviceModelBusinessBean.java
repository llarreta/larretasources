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
import co.com.directv.sdii.ejb.business.bb.DeviceModelBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.DeviceModel;
import co.com.directv.sdii.model.vo.DeviceModelVO;
import co.com.directv.sdii.persistence.dao.bb.DeviceModelDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DeviceModel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.bb.DeviceModelDAOLocal
 * @see co.com.directv.sdii.ejb.business.bb.DeviceModelBusinessBeanLocal
 */
@Stateless(name="DeviceModelBusinessBeanLocal",mappedName="ejb/DeviceModelBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DeviceModelBusinessBean extends BusinessBase implements DeviceModelBusinessBeanLocal {

    @EJB(name="DeviceModelDAOLocal", beanInterface=DeviceModelDAOLocal.class)
    private DeviceModelDAOLocal daoDeviceModel;
    
    private final static Logger log = UtilsBusiness.getLog4J(DeviceModelBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.DeviceModelBusinessBeanLocal#getAllDeviceModels()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DeviceModelVO> getAllDeviceModels() throws BusinessException {
        log.debug("== Inicia getAllDeviceModels/DeviceModelBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDeviceModel.getAllDeviceModels(), DeviceModelVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDeviceModels/DeviceModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDeviceModels/DeviceModelBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.bb.DeviceModelBusinessBeanLocal#getDeviceModelsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DeviceModelVO getDeviceModelByID(Long id) throws BusinessException {
        log.debug("== Inicia getDeviceModelByID/DeviceModelBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DeviceModel objPojo = daoDeviceModel.getDeviceModelByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DeviceModelVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDeviceModelByID/DeviceModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDeviceModelByID/DeviceModelBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.DeviceModelBusinessBeanLocal#createDeviceModel(co.com.directv.sdii.model.vo.DeviceModelVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDeviceModel(DeviceModelVO obj) throws BusinessException {
        log.debug("== Inicia createDeviceModel/DeviceModelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DeviceModel objPojo =  UtilsBusiness.copyObject(DeviceModel.class, obj);
            daoDeviceModel.createDeviceModel(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDeviceModel/DeviceModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDeviceModel/DeviceModelBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.DeviceModelBusinessBeanLocal#updateDeviceModel(co.com.directv.sdii.model.vo.DeviceModelVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDeviceModel(DeviceModelVO obj) throws BusinessException {
        log.debug("== Inicia updateDeviceModel/DeviceModelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DeviceModel objPojo =  UtilsBusiness.copyObject(DeviceModel.class, obj);
            daoDeviceModel.updateDeviceModel(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDeviceModel/DeviceModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDeviceModel/DeviceModelBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.bb.DeviceModelBusinessBeanLocal#deleteDeviceModel(co.com.directv.sdii.model.vo.DeviceModelVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDeviceModel(DeviceModelVO obj) throws BusinessException {
        log.debug("== Inicia deleteDeviceModel/DeviceModelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DeviceModel objPojo =  UtilsBusiness.copyObject(DeviceModel.class, obj);
            daoDeviceModel.deleteDeviceModel(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDeviceModel/DeviceModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDeviceModel/DeviceModelBusinessBean ==");
        }
	}
}
