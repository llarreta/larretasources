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
import co.com.directv.sdii.ejb.business.assign.HistoDealerWeekCapacityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.HistoDealerWeekCapacity;
import co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerWeekCapacityDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD HistoDealerWeekCapacity
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerWeekCapacityDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerWeekCapacityBusinessBeanLocal
 */
@Stateless(name="HistoDealerWeekCapacityBusinessBeanLocal",mappedName="ejb/HistoDealerWeekCapacityBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerWeekCapacityBusinessBean extends BusinessBase implements HistoDealerWeekCapacityBusinessBeanLocal {

    @EJB(name="HistoDealerWeekCapacityDAOLocal", beanInterface=HistoDealerWeekCapacityDAOLocal.class)
    private HistoDealerWeekCapacityDAOLocal daoHistoDealerWeekCapacity;
    
    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerWeekCapacityBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerWeekCapacityBusinessBeanLocal#getAllHistoDealerWeekCapacitys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerWeekCapacityVO> getAllHistoDealerWeekCapacitys() throws BusinessException {
        log.debug("== Inicia getAllHistoDealerWeekCapacitys/HistoDealerWeekCapacityBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoHistoDealerWeekCapacity.getAllHistoDealerWeekCapacitys(), HistoDealerWeekCapacityVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllHistoDealerWeekCapacitys/HistoDealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllHistoDealerWeekCapacitys/HistoDealerWeekCapacityBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerWeekCapacityBusinessBeanLocal#getHistoDealerWeekCapacitysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerWeekCapacityVO getHistoDealerWeekCapacityByID(Long id) throws BusinessException {
        log.debug("== Inicia getHistoDealerWeekCapacityByID/HistoDealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerWeekCapacity objPojo = daoHistoDealerWeekCapacity.getHistoDealerWeekCapacityByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(HistoDealerWeekCapacityVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getHistoDealerWeekCapacityByID/HistoDealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getHistoDealerWeekCapacityByID/HistoDealerWeekCapacityBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerWeekCapacityBusinessBeanLocal#createHistoDealerWeekCapacity(co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException {
        log.debug("== Inicia createHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerWeekCapacity objPojo =  UtilsBusiness.copyObject(HistoDealerWeekCapacity.class, obj);
            daoHistoDealerWeekCapacity.createHistoDealerWeekCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerWeekCapacityBusinessBeanLocal#updateHistoDealerWeekCapacity(co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException {
        log.debug("== Inicia updateHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerWeekCapacity objPojo =  UtilsBusiness.copyObject(HistoDealerWeekCapacity.class, obj);
            daoHistoDealerWeekCapacity.updateHistoDealerWeekCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerWeekCapacityBusinessBeanLocal#deleteHistoDealerWeekCapacity(co.com.directv.sdii.model.vo.HistoDealerWeekCapacityVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteHistoDealerWeekCapacity(HistoDealerWeekCapacityVO obj) throws BusinessException {
        log.debug("== Inicia deleteHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerWeekCapacity objPojo =  UtilsBusiness.copyObject(HistoDealerWeekCapacity.class, obj);
            daoHistoDealerWeekCapacity.deleteHistoDealerWeekCapacity(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerWeekCapacity/HistoDealerWeekCapacityBusinessBean ==");
        }
	}
}
