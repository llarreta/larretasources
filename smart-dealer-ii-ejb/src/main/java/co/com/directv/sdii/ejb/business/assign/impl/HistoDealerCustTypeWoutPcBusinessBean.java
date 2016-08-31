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
import co.com.directv.sdii.ejb.business.assign.HistoDealerCustTypeWoutPcBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.HistoDealerCustTypeWoutPc;
import co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerCustTypeWoutPcDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD HistoDealerCustTypeWoutPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCustTypeWoutPcDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCustTypeWoutPcBusinessBeanLocal
 */
@Stateless(name="HistoDealerCustTypeWoutPcBusinessBeanLocal",mappedName="ejb/HistoDealerCustTypeWoutPcBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerCustTypeWoutPcBusinessBean extends BusinessBase implements HistoDealerCustTypeWoutPcBusinessBeanLocal {

    @EJB(name="HistoDealerCustTypeWoutPcDAOLocal", beanInterface=HistoDealerCustTypeWoutPcDAOLocal.class)
    private HistoDealerCustTypeWoutPcDAOLocal daoHistoDealerCustTypeWoutPc;
    
    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerCustTypeWoutPcBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCustTypeWoutPcBusinessBeanLocal#getAllHistoDealerCustTypeWoutPcs()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerCustTypeWoutPcVO> getAllHistoDealerCustTypeWoutPcs() throws BusinessException {
        log.debug("== Inicia getAllHistoDealerCustTypeWoutPcs/HistoDealerCustTypeWoutPcBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoHistoDealerCustTypeWoutPc.getAllHistoDealerCustTypeWoutPcs(), HistoDealerCustTypeWoutPcVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllHistoDealerCustTypeWoutPcs/HistoDealerCustTypeWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllHistoDealerCustTypeWoutPcs/HistoDealerCustTypeWoutPcBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCustTypeWoutPcBusinessBeanLocal#getHistoDealerCustTypeWoutPcsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerCustTypeWoutPcVO getHistoDealerCustTypeWoutPcByID(Long id) throws BusinessException {
        log.debug("== Inicia getHistoDealerCustTypeWoutPcByID/HistoDealerCustTypeWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerCustTypeWoutPc objPojo = daoHistoDealerCustTypeWoutPc.getHistoDealerCustTypeWoutPcByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(HistoDealerCustTypeWoutPcVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getHistoDealerCustTypeWoutPcByID/HistoDealerCustTypeWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getHistoDealerCustTypeWoutPcByID/HistoDealerCustTypeWoutPcBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCustTypeWoutPcBusinessBeanLocal#createHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException {
        log.debug("== Inicia createHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerCustTypeWoutPc objPojo =  UtilsBusiness.copyObject(HistoDealerCustTypeWoutPc.class, obj);
            daoHistoDealerCustTypeWoutPc.createHistoDealerCustTypeWoutPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCustTypeWoutPcBusinessBeanLocal#updateHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException {
        log.debug("== Inicia updateHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerCustTypeWoutPc objPojo =  UtilsBusiness.copyObject(HistoDealerCustTypeWoutPc.class, obj);
            daoHistoDealerCustTypeWoutPc.updateHistoDealerCustTypeWoutPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCustTypeWoutPcBusinessBeanLocal#deleteHistoDealerCustTypeWoutPc(co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException {
        log.debug("== Inicia deleteHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerCustTypeWoutPc objPojo =  UtilsBusiness.copyObject(HistoDealerCustTypeWoutPc.class, obj);
            daoHistoDealerCustTypeWoutPc.deleteHistoDealerCustTypeWoutPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerCustTypeWoutPc/HistoDealerCustTypeWoutPcBusinessBean ==");
        }
	}
}
