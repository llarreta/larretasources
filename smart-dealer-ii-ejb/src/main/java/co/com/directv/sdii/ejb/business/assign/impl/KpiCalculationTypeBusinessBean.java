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
import co.com.directv.sdii.ejb.business.assign.KpiCalculationTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.directv.sdii.model.pojo.KpiCalculationType;
import co.com.directv.sdii.model.vo.KpiCalculationTypeVO;
import co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD KpiCalculationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.KpiCalculationTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.KpiCalculationTypeBusinessBeanLocal
 */
@Stateless(name="KpiCalculationTypeBusinessBeanLocal",mappedName="ejb/KpiCalculationTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiCalculationTypeBusinessBean extends BusinessBase implements KpiCalculationTypeBusinessBeanLocal {

    @EJB(name="KpiCalculationTypeDAOLocal", beanInterface=KpiCalculationTypeDAOLocal.class)
    private KpiCalculationTypeDAOLocal daoKpiCalculationType;
    
    private final static Logger log = UtilsBusiness.getLog4J(KpiCalculationTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiCalculationTypeBusinessBeanLocal#getAllKpiCalculationTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiCalculationTypeVO> getAllKpiCalculationTypes() throws BusinessException {
        log.debug("== Inicia getAllKpiCalculationTypes/KpiCalculationTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoKpiCalculationType.getAllKpiCalculationTypes(), KpiCalculationTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllKpiCalculationTypes/KpiCalculationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllKpiCalculationTypes/KpiCalculationTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiCalculationTypeBusinessBeanLocal#getKpiCalculationTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiCalculationTypeVO getKpiCalculationTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getKpiCalculationTypeByID/KpiCalculationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiCalculationType objPojo = daoKpiCalculationType.getKpiCalculationTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(KpiCalculationTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getKpiCalculationTypeByID/KpiCalculationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiCalculationTypeByID/KpiCalculationTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiCalculationTypeBusinessBeanLocal#createKpiCalculationType(co.com.directv.sdii.model.vo.KpiCalculationTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException {
        log.debug("== Inicia createKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiCalculationType objPojo =  UtilsBusiness.copyObject(KpiCalculationType.class, obj);
            daoKpiCalculationType.createKpiCalculationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiCalculationTypeBusinessBeanLocal#updateKpiCalculationType(co.com.directv.sdii.model.vo.KpiCalculationTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiCalculationType objPojo =  UtilsBusiness.copyObject(KpiCalculationType.class, obj);
            daoKpiCalculationType.updateKpiCalculationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiCalculationTypeBusinessBeanLocal#deleteKpiCalculationType(co.com.directv.sdii.model.vo.KpiCalculationTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteKpiCalculationType(KpiCalculationTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiCalculationType objPojo =  UtilsBusiness.copyObject(KpiCalculationType.class, obj);
            daoKpiCalculationType.deleteKpiCalculationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiCalculationType/KpiCalculationTypeBusinessBean ==");
        }
	}
}
