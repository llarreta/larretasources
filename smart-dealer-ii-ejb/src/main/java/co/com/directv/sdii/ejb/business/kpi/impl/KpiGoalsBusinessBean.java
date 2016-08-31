package co.com.directv.sdii.ejb.business.kpi.impl;

import java.util.Date;
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
import co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.KpiGoals;
import co.com.directv.sdii.model.vo.KpiGoalsVO;
import co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD KpiGoals
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.kpi.KpiGoalsDAOLocal
 * @see co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal
 */
@Stateless(name="KpiGoalsBusinessBeanLocal",mappedName="ejb/KpiGoalsBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiGoalsBusinessBean extends BusinessBase implements KpiGoalsBusinessBeanLocal {

    @EJB(name="KpiGoalsDAOLocal", beanInterface=KpiGoalsDAOLocal.class)
    private KpiGoalsDAOLocal daoKpiGoals;
    
    private final static Logger log = UtilsBusiness.getLog4J(KpiGoalsBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal#getAllKpiGoalss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiGoalsVO> getAllKpiGoalss() throws BusinessException {
        log.debug("== Inicia getAllKpiGoalss/KpiGoalsBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoKpiGoals.getAllKpiGoalss(), KpiGoalsVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllKpiGoalss/KpiGoalsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllKpiGoalss/KpiGoalsBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal#getKpiGoalssByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiGoalsVO getKpiGoalsByID(Long id) throws BusinessException {
        log.debug("== Inicia getKpiGoalsByID/KpiGoalsBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiGoals objPojo = daoKpiGoals.getKpiGoalsByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(KpiGoalsVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getKpiGoalsByID/KpiGoalsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiGoalsByID/KpiGoalsBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal#createKpiGoals(co.com.directv.sdii.model.vo.KpiGoalsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createKpiGoals(KpiGoalsVO obj) throws BusinessException {
        log.debug("== Inicia createKpiGoals/KpiGoalsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	List<KpiGoals> oldKpiGoals = daoKpiGoals.getKpiGoalsByIndicatorIdAndCountryId(obj.getIndicators().getId(), obj.getCountries().getId());
        	
        	for (KpiGoals oldKpiGoal : oldKpiGoals) {
        		if(oldKpiGoal.getEndDate() == null){
        			oldKpiGoal.setEndDate(new Date());
        			daoKpiGoals.updateKpiGoals(oldKpiGoal);
        		}
			}
            KpiGoals objPojo =  UtilsBusiness.copyObject(KpiGoals.class, obj);
            daoKpiGoals.createKpiGoals(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createKpiGoals/KpiGoalsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiGoals/KpiGoalsBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal#updateKpiGoals(co.com.directv.sdii.model.vo.KpiGoalsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateKpiGoals(KpiGoalsVO obj) throws BusinessException {
        log.debug("== Inicia updateKpiGoals/KpiGoalsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiGoals objPojo =  UtilsBusiness.copyObject(KpiGoals.class, obj);
            daoKpiGoals.updateKpiGoals(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateKpiGoals/KpiGoalsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiGoals/KpiGoalsBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal#deleteKpiGoals(co.com.directv.sdii.model.vo.KpiGoalsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteKpiGoals(KpiGoalsVO obj) throws BusinessException {
        log.debug("== Inicia deleteKpiGoals/KpiGoalsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiGoals objPojo =  UtilsBusiness.copyObject(KpiGoals.class, obj);
            daoKpiGoals.deleteKpiGoals(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteKpiGoals/KpiGoalsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiGoals/KpiGoalsBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal#getAllKpiGoalssAndByCountry(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiGoalsVO> getAllKpiGoalssAndByCountry(Long country) throws BusinessException {
        log.debug("== Inicia getAllKpiGoalssAndByCountry/KpiGoalsBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoKpiGoals.getAllKpiGoalssAndByCountry(country), KpiGoalsVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllKpiGoalssAndByCountry/KpiGoalsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllKpiGoalssAndByCountry/KpiGoalsBusinessBean ==");
        }
    }


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.kpi.KpiGoalsBusinessBeanLocal#getKpiGoalsByIndicatorIdAndCountryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<KpiGoalsVO> getKpiGoalsByIndicatorIdAndCountryId(
			Long indicatorId, Long countryId) throws BusinessException {
		log.debug("== Inicia getKpiGoalsByIndicatorIdAndCountryId/KpiGoalsBusinessBean ==");
        try {
        	List<KpiGoals> kpiGoals = daoKpiGoals.getKpiGoalsByIndicatorIdAndCountryId(indicatorId ,countryId);
            return UtilsBusiness.convertList(kpiGoals, KpiGoalsVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getKpiGoalsByIndicatorIdAndCountryId/KpiGoalsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiGoalsByIndicatorIdAndCountryId/KpiGoalsBusinessBean ==");
        }
	}
}
