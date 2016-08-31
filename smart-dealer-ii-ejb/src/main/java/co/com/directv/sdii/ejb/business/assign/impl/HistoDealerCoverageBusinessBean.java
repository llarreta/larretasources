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
import co.com.directv.sdii.ejb.business.assign.HistoDealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.HistoDealerCoverage;
import co.com.directv.sdii.model.vo.HistoDealerCoverageVO;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerCoverageDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD HistoDealerCoverage
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerCoverageDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCoverageBusinessBeanLocal
 */
@Stateless(name="HistoDealerCoverageBusinessBeanLocal",mappedName="ejb/HistoDealerCoverageBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerCoverageBusinessBean extends BusinessBase implements HistoDealerCoverageBusinessBeanLocal {

    @EJB(name="HistoDealerCoverageDAOLocal", beanInterface=HistoDealerCoverageDAOLocal.class)
    private HistoDealerCoverageDAOLocal daoHistoDealerCoverage;
    
    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerCoverageBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCoverageBusinessBeanLocal#getAllHistoDealerCoverages()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerCoverageVO> getAllHistoDealerCoverages() throws BusinessException {
        log.debug("== Inicia getAllHistoDealerCoverages/HistoDealerCoverageBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoHistoDealerCoverage.getAllHistoDealerCoverages(), HistoDealerCoverageVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllHistoDealerCoverages/HistoDealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllHistoDealerCoverages/HistoDealerCoverageBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCoverageBusinessBeanLocal#getHistoDealerCoveragesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerCoverageVO getHistoDealerCoverageByID(Long id) throws BusinessException {
        log.debug("== Inicia getHistoDealerCoverageByID/HistoDealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerCoverage objPojo = daoHistoDealerCoverage.getHistoDealerCoverageByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(HistoDealerCoverageVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getHistoDealerCoverageByID/HistoDealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getHistoDealerCoverageByID/HistoDealerCoverageBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCoverageBusinessBeanLocal#createHistoDealerCoverage(co.com.directv.sdii.model.vo.HistoDealerCoverageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException {
        log.debug("== Inicia createHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerCoverage objPojo =  UtilsBusiness.copyObject(HistoDealerCoverage.class, obj);
            daoHistoDealerCoverage.createHistoDealerCoverage(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCoverageBusinessBeanLocal#updateHistoDealerCoverage(co.com.directv.sdii.model.vo.HistoDealerCoverageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException {
        log.debug("== Inicia updateHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerCoverage objPojo =  UtilsBusiness.copyObject(HistoDealerCoverage.class, obj);
            daoHistoDealerCoverage.updateHistoDealerCoverage(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerCoverageBusinessBeanLocal#deleteHistoDealerCoverage(co.com.directv.sdii.model.vo.HistoDealerCoverageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException {
        log.debug("== Inicia deleteHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerCoverage objPojo =  UtilsBusiness.copyObject(HistoDealerCoverage.class, obj);
            daoHistoDealerCoverage.deleteHistoDealerCoverage(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerCoverage/HistoDealerCoverageBusinessBean ==");
        }
	}
}
