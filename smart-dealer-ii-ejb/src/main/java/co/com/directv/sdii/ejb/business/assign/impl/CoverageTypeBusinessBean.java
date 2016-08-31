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
import co.com.directv.sdii.ejb.business.assign.CoverageTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CoverageType;
import co.com.directv.sdii.model.vo.CoverageTypeVO;
import co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD CoverageType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.CoverageTypeBusinessBeanLocal
 */
@Stateless(name="CoverageTypeBusinessBeanLocal",mappedName="ejb/CoverageTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoverageTypeBusinessBean extends BusinessBase implements CoverageTypeBusinessBeanLocal {

    @EJB(name="CoverageTypeDAOLocal", beanInterface=CoverageTypeDAOLocal.class)
    private CoverageTypeDAOLocal daoCoverageType;
    
    private final static Logger log = UtilsBusiness.getLog4J(CoverageTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.CoverageTypeBusinessBeanLocal#getAllCoverageTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CoverageTypeVO> getAllCoverageTypes() throws BusinessException {
        log.debug("== Inicia getAllCoverageTypes/CoverageTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoCoverageType.getAllCoverageTypes(), CoverageTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllCoverageTypes/CoverageTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllCoverageTypes/CoverageTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.CoverageTypeBusinessBeanLocal#getCoverageTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CoverageTypeVO getCoverageTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getCoverageTypeByID/CoverageTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            CoverageType objPojo = daoCoverageType.getCoverageTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(CoverageTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getCoverageTypeByID/CoverageTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCoverageTypeByID/CoverageTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.CoverageTypeBusinessBeanLocal#createCoverageType(co.com.directv.sdii.model.vo.CoverageTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createCoverageType(CoverageTypeVO obj) throws BusinessException {
        log.debug("== Inicia createCoverageType/CoverageTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            CoverageType objPojo =  UtilsBusiness.copyObject(CoverageType.class, obj);
            daoCoverageType.createCoverageType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createCoverageType/CoverageTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createCoverageType/CoverageTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.CoverageTypeBusinessBeanLocal#updateCoverageType(co.com.directv.sdii.model.vo.CoverageTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCoverageType(CoverageTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateCoverageType/CoverageTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            CoverageType objPojo =  UtilsBusiness.copyObject(CoverageType.class, obj);
            daoCoverageType.updateCoverageType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateCoverageType/CoverageTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateCoverageType/CoverageTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.CoverageTypeBusinessBeanLocal#deleteCoverageType(co.com.directv.sdii.model.vo.CoverageTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCoverageType(CoverageTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteCoverageType/CoverageTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            CoverageType objPojo =  UtilsBusiness.copyObject(CoverageType.class, obj);
            daoCoverageType.deleteCoverageType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteCoverageType/CoverageTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteCoverageType/CoverageTypeBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.CoverageTypeBusinessBeanLocal#getCoverageTypePermanent()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CoverageTypeVO getCoverageTypePermanent() throws BusinessException {
		try {
			return UtilsBusiness.copyObject(CoverageTypeVO.class, daoCoverageType.getCoverageTypePermanent());
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getCoverageTypePermanent/CoverageTypeBusinessBean ==" + ex.getMessage());
        	throw this.manageException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.CoverageTypeBusinessBeanLocal#getCoverageTypeOccasional()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CoverageTypeVO getCoverageTypeOccasional() throws BusinessException {
		try {
			return UtilsBusiness.copyObject(CoverageTypeVO.class, daoCoverageType.getCoverageTypeOccasional());
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación getCoverageTypeOccasional/CoverageTypeBusinessBean ==" + ex.getMessage());
        	throw this.manageException(ex);
		}
	}
	
}
