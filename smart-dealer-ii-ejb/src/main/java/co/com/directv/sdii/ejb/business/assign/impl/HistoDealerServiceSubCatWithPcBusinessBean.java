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
import co.com.directv.sdii.ejb.business.assign.HistoDealerServiceSubCatWithPcBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.HistoDealerServiceSubCatWithPc;
import co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCatWithPcDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD HistoDealerServiceSubCatWithPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCatWithPcDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCatWithPcBusinessBeanLocal
 */
@Stateless(name="HistoDealerServiceSubCatWithPcBusinessBeanLocal",mappedName="ejb/HistoDealerServiceSubCatWithPcBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerServiceSubCatWithPcBusinessBean extends BusinessBase implements HistoDealerServiceSubCatWithPcBusinessBeanLocal {

    @EJB(name="HistoDealerServiceSubCatWithPcDAOLocal", beanInterface=HistoDealerServiceSubCatWithPcDAOLocal.class)
    private HistoDealerServiceSubCatWithPcDAOLocal daoHistoDealerServiceSubCatWithPc;
    
    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerServiceSubCatWithPcBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCatWithPcBusinessBeanLocal#getAllHistoDealerServiceSubCatWithPcs()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerServiceSubCatWithPcVO> getAllHistoDealerServiceSubCatWithPcs() throws BusinessException {
        log.debug("== Inicia getAllHistoDealerServiceSubCatWithPcs/HistoDealerServiceSubCatWithPcBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoHistoDealerServiceSubCatWithPc.getAllHistoDealerServiceSubCatWithPcs(), HistoDealerServiceSubCatWithPcVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllHistoDealerServiceSubCatWithPcs/HistoDealerServiceSubCatWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllHistoDealerServiceSubCatWithPcs/HistoDealerServiceSubCatWithPcBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCatWithPcBusinessBeanLocal#getHistoDealerServiceSubCatWithPcsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerServiceSubCatWithPcVO getHistoDealerServiceSubCatWithPcByID(Long id) throws BusinessException {
        log.debug("== Inicia getHistoDealerServiceSubCatWithPcByID/HistoDealerServiceSubCatWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerServiceSubCatWithPc objPojo = daoHistoDealerServiceSubCatWithPc.getHistoDealerServiceSubCatWithPcByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(HistoDealerServiceSubCatWithPcVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getHistoDealerServiceSubCatWithPcByID/HistoDealerServiceSubCatWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getHistoDealerServiceSubCatWithPcByID/HistoDealerServiceSubCatWithPcBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCatWithPcBusinessBeanLocal#createHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException {
        log.debug("== Inicia createHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerServiceSubCatWithPc objPojo =  UtilsBusiness.copyObject(HistoDealerServiceSubCatWithPc.class, obj);
            daoHistoDealerServiceSubCatWithPc.createHistoDealerServiceSubCatWithPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCatWithPcBusinessBeanLocal#updateHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException {
        log.debug("== Inicia updateHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerServiceSubCatWithPc objPojo =  UtilsBusiness.copyObject(HistoDealerServiceSubCatWithPc.class, obj);
            daoHistoDealerServiceSubCatWithPc.updateHistoDealerServiceSubCatWithPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCatWithPcBusinessBeanLocal#deleteHistoDealerServiceSubCatWithPc(co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException {
        log.debug("== Inicia deleteHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerServiceSubCatWithPc objPojo =  UtilsBusiness.copyObject(HistoDealerServiceSubCatWithPc.class, obj);
            daoHistoDealerServiceSubCatWithPc.deleteHistoDealerServiceSubCatWithPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerServiceSubCatWithPc/HistoDealerServiceSubCatWithPcBusinessBean ==");
        }
	}
}
