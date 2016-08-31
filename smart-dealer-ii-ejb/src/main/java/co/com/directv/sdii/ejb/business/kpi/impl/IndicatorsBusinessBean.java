package co.com.directv.sdii.ejb.business.kpi.impl;

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
import co.com.directv.sdii.ejb.business.kpi.IndicatorsBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Indicators;
import co.com.directv.sdii.model.vo.IndicatorsVO;
import co.com.directv.sdii.persistence.dao.kpi.IndicatorsDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD Indicators
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.kpi.IndicatorsDAOLocal
 * @see co.com.directv.sdii.ejb.business.kpi.IndicatorsBusinessBeanLocal
 */
@Stateless(name="IndicatorsBusinessBeanLocal",mappedName="ejb/IndicatorsBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IndicatorsBusinessBean extends BusinessBase implements IndicatorsBusinessBeanLocal {

    @EJB(name="IndicatorsDAOLocal", beanInterface=IndicatorsDAOLocal.class)
    private IndicatorsDAOLocal daoIndicators;
    
    private final static Logger log = UtilsBusiness.getLog4J(IndicatorsBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.kpi.IndicatorsBusinessBeanLocal#getAllIndicatorss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<IndicatorsVO> getAllIndicatorss() throws BusinessException {
        log.debug("== Inicia getAllIndicatorss/IndicatorsBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoIndicators.getAllIndicatorss(), IndicatorsVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllIndicatorss/IndicatorsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllIndicatorss/IndicatorsBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.kpi.IndicatorsBusinessBeanLocal#getIndicatorssByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public IndicatorsVO getIndicatorsByID(Long id) throws BusinessException {
        log.debug("== Inicia getIndicatorsByID/IndicatorsBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Indicators objPojo = daoIndicators.getIndicatorsByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(IndicatorsVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getIndicatorsByID/IndicatorsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getIndicatorsByID/IndicatorsBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.kpi.IndicatorsBusinessBeanLocal#createIndicators(co.com.directv.sdii.model.vo.IndicatorsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createIndicators(IndicatorsVO obj) throws BusinessException {
        log.debug("== Inicia createIndicators/IndicatorsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            Indicators objPojo =  UtilsBusiness.copyObject(Indicators.class, obj);
            daoIndicators.createIndicators(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createIndicators/IndicatorsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createIndicators/IndicatorsBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.kpi.IndicatorsBusinessBeanLocal#updateIndicators(co.com.directv.sdii.model.vo.IndicatorsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateIndicators(IndicatorsVO obj) throws BusinessException {
        log.debug("== Inicia updateIndicators/IndicatorsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            Indicators objPojo =  UtilsBusiness.copyObject(Indicators.class, obj);
            daoIndicators.updateIndicators(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateIndicators/IndicatorsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateIndicators/IndicatorsBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.kpi.IndicatorsBusinessBeanLocal#deleteIndicators(co.com.directv.sdii.model.vo.IndicatorsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteIndicators(IndicatorsVO obj) throws BusinessException {
        log.debug("== Inicia deleteIndicators/IndicatorsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Indicators objPojo =  UtilsBusiness.copyObject(Indicators.class, obj);
            daoIndicators.deleteIndicators(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteIndicators/IndicatorsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteIndicators/IndicatorsBusinessBean ==");
        }
	}
}
