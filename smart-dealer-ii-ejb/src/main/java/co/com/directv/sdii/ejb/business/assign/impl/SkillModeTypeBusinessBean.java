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
import co.com.directv.sdii.ejb.business.assign.SkillModeTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.SkillModeType;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;
import co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD SkillModeType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillModeTypeBusinessBeanLocal
 */
@Stateless(name="SkillModeTypeBusinessBeanLocal",mappedName="ejb/SkillModeTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SkillModeTypeBusinessBean extends BusinessBase implements SkillModeTypeBusinessBeanLocal {

    @EJB(name="SkillModeTypeDAOLocal", beanInterface=SkillModeTypeDAOLocal.class)
    private SkillModeTypeDAOLocal daoSkillModeType;
    
    private final static Logger log = UtilsBusiness.getLog4J(SkillModeTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SkillModeTypeBusinessBeanLocal#getAllSkillModeTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillModeTypeVO> getAllSkillModeTypes() throws BusinessException {
        log.debug("== Inicia getAllSkillModeTypes/SkillModeTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoSkillModeType.getAllSkillModeTypes(), SkillModeTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllSkillModeTypes/SkillModeTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillModeTypes/SkillModeTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.SkillModeTypeBusinessBeanLocal#getSkillModeTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SkillModeTypeVO getSkillModeTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getSkillModeTypeByID/SkillModeTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SkillModeType objPojo = daoSkillModeType.getSkillModeTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SkillModeTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSkillModeTypeByID/SkillModeTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillModeTypeByID/SkillModeTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillModeTypeBusinessBeanLocal#createSkillModeType(co.com.directv.sdii.model.vo.SkillModeTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createSkillModeType(SkillModeTypeVO obj) throws BusinessException {
        log.debug("== Inicia createSkillModeType/SkillModeTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SkillModeType objPojo =  UtilsBusiness.copyObject(SkillModeType.class, obj);
            daoSkillModeType.createSkillModeType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createSkillModeType/SkillModeTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkillModeType/SkillModeTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillModeTypeBusinessBeanLocal#updateSkillModeType(co.com.directv.sdii.model.vo.SkillModeTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSkillModeType(SkillModeTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateSkillModeType/SkillModeTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            SkillModeType objPojo =  UtilsBusiness.copyObject(SkillModeType.class, obj);
            daoSkillModeType.updateSkillModeType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateSkillModeType/SkillModeTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkillModeType/SkillModeTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.SkillModeTypeBusinessBeanLocal#deleteSkillModeType(co.com.directv.sdii.model.vo.SkillModeTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSkillModeType(SkillModeTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteSkillModeType/SkillModeTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SkillModeType objPojo =  UtilsBusiness.copyObject(SkillModeType.class, obj);
            daoSkillModeType.deleteSkillModeType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteSkillModeType/SkillModeTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkillModeType/SkillModeTypeBusinessBean ==");
        }
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public SkillModeTypeVO getSkillModeTypeByCode(String skillModeTypeCode)
			throws BusinessException {
		log.debug("== Inicia getSkillModeTypeByID/SkillModeTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(skillModeTypeCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            SkillModeType objPojo = daoSkillModeType.getSkillModeTypeByCode(skillModeTypeCode);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(SkillModeTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getSkillModeTypeByID/SkillModeTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillModeTypeByID/SkillModeTypeBusinessBean ==");
        }
	}
}
