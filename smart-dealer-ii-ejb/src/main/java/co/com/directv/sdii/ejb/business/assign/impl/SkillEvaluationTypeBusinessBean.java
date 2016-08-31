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
import co.com.directv.sdii.ejb.business.assign.SkillEvaluationTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.SkillEvaluationType;
import co.com.directv.sdii.model.vo.SkillEvaluationTypeVO;
import co.com.directv.sdii.persistence.dao.assign.SkillEvaluationTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD SkillEvaluationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.SkillEvaluationTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillEvaluationTypeBusinessBeanLocal
 */
@Stateless(name="SkillEvaluationTypeBusinessBeanLocal",mappedName="ejb/SkillEvaluationTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SkillEvaluationTypeBusinessBean extends BusinessBase implements SkillEvaluationTypeBusinessBeanLocal {

    @EJB(name="SkillEvaluationTypeDAOLocal", beanInterface=SkillEvaluationTypeDAOLocal.class)
    private SkillEvaluationTypeDAOLocal daoSkillEvaluationType;
    
    private final static Logger log = UtilsBusiness.getLog4J(SkillEvaluationTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SkillEvaluationTypeBusinessBeanLocal#getAllSkillEvaluationTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillEvaluationTypeVO> getAllSkillEvaluationTypes() throws BusinessException {
        log.debug("== Inicia getAllSkillEvaluationTypes/SkillEvaluationTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoSkillEvaluationType.getAllSkillEvaluationTypes(), SkillEvaluationTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllSkillEvaluationTypes/SkillEvaluationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillEvaluationTypes/SkillEvaluationTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SkillEvaluationTypeBusinessBeanLocal#getSkillEvaluationTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SkillEvaluationTypeVO getSkillEvaluationTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getSkillEvaluationTypeByID/SkillEvaluationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SkillEvaluationType objPojo = daoSkillEvaluationType.getSkillEvaluationTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SkillEvaluationTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSkillEvaluationTypeByID/SkillEvaluationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillEvaluationTypeByID/SkillEvaluationTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillEvaluationTypeBusinessBeanLocal#createSkillEvaluationType(co.com.directv.sdii.model.vo.SkillEvaluationTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException {
        log.debug("== Inicia createSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SkillEvaluationType objPojo =  UtilsBusiness.copyObject(SkillEvaluationType.class, obj);
            daoSkillEvaluationType.createSkillEvaluationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillEvaluationTypeBusinessBeanLocal#updateSkillEvaluationType(co.com.directv.sdii.model.vo.SkillEvaluationTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SkillEvaluationType objPojo =  UtilsBusiness.copyObject(SkillEvaluationType.class, obj);
            daoSkillEvaluationType.updateSkillEvaluationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillEvaluationTypeBusinessBeanLocal#deleteSkillEvaluationType(co.com.directv.sdii.model.vo.SkillEvaluationTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SkillEvaluationType objPojo =  UtilsBusiness.copyObject(SkillEvaluationType.class, obj);
            daoSkillEvaluationType.deleteSkillEvaluationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkillEvaluationType/SkillEvaluationTypeBusinessBean ==");
        }
	}
}
