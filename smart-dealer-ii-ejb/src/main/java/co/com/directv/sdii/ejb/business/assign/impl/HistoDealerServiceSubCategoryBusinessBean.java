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
import co.com.directv.sdii.ejb.business.assign.HistoDealerServiceSubCategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.HistoDealerServiceSubCategory;
import co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCategoryDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD HistoDealerServiceSubCategory
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerServiceSubCategoryDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCategoryBusinessBeanLocal
 */
@Stateless(name="HistoDealerServiceSubCategoryBusinessBeanLocal",mappedName="ejb/HistoDealerServiceSubCategoryBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerServiceSubCategoryBusinessBean extends BusinessBase implements HistoDealerServiceSubCategoryBusinessBeanLocal {

    @EJB(name="HistoDealerServiceSubCategoryDAOLocal", beanInterface=HistoDealerServiceSubCategoryDAOLocal.class)
    private HistoDealerServiceSubCategoryDAOLocal daoHistoDealerServiceSubCategory;
    
    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerServiceSubCategoryBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCategoryBusinessBeanLocal#getAllHistoDealerServiceSubCategorys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerServiceSubCategoryVO> getAllHistoDealerServiceSubCategorys() throws BusinessException {
        log.debug("== Inicia getAllHistoDealerServiceSubCategorys/HistoDealerServiceSubCategoryBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoHistoDealerServiceSubCategory.getAllHistoDealerServiceSubCategorys(), HistoDealerServiceSubCategoryVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllHistoDealerServiceSubCategorys/HistoDealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllHistoDealerServiceSubCategorys/HistoDealerServiceSubCategoryBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCategoryBusinessBeanLocal#getHistoDealerServiceSubCategorysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerServiceSubCategoryVO getHistoDealerServiceSubCategoryByID(Long id) throws BusinessException {
        log.debug("== Inicia getHistoDealerServiceSubCategoryByID/HistoDealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerServiceSubCategory objPojo = daoHistoDealerServiceSubCategory.getHistoDealerServiceSubCategoryByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(HistoDealerServiceSubCategoryVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getHistoDealerServiceSubCategoryByID/HistoDealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getHistoDealerServiceSubCategoryByID/HistoDealerServiceSubCategoryBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCategoryBusinessBeanLocal#createHistoDealerServiceSubCategory(co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException {
        log.debug("== Inicia createHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerServiceSubCategory objPojo =  UtilsBusiness.copyObject(HistoDealerServiceSubCategory.class, obj);
            daoHistoDealerServiceSubCategory.createHistoDealerServiceSubCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCategoryBusinessBeanLocal#updateHistoDealerServiceSubCategory(co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException {
        log.debug("== Inicia updateHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerServiceSubCategory objPojo =  UtilsBusiness.copyObject(HistoDealerServiceSubCategory.class, obj);
            daoHistoDealerServiceSubCategory.updateHistoDealerServiceSubCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerServiceSubCategoryBusinessBeanLocal#deleteHistoDealerServiceSubCategory(co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException {
        log.debug("== Inicia deleteHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerServiceSubCategory objPojo =  UtilsBusiness.copyObject(HistoDealerServiceSubCategory.class, obj);
            daoHistoDealerServiceSubCategory.deleteHistoDealerServiceSubCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerServiceSubCategory/HistoDealerServiceSubCategoryBusinessBean ==");
        }
	}
}
