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
import co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.directv.sdii.model.pojo.Kpi;
import co.com.directv.sdii.model.vo.KpiVO;
import co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD Kpi
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.KpiDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal
 */
@Stateless(name="KpiBusinessBeanLocal",mappedName="ejb/KpiBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiBusinessBean extends BusinessBase implements KpiBusinessBeanLocal {

    @EJB(name="KpiDAOLocal", beanInterface=KpiDAOLocal.class)
    private KpiDAOLocal daoKpi;
    
    private final static Logger log = UtilsBusiness.getLog4J(KpiBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal#getAllKpis()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiVO> getAllKpis() throws BusinessException {
        log.debug("== Inicia getAllKpis/KpiBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoKpi.getAllKpis(), KpiVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllKpis/KpiBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllKpis/KpiBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal#getKpisByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiVO getKpiByID(Long id) throws BusinessException {
        log.debug("== Inicia getKpiByID/KpiBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Kpi objPojo = daoKpi.getKpiByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(KpiVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getKpiByID/KpiBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiByID/KpiBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal#createKpi(co.com.directv.sdii.model.vo.KpiVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createKpi(KpiVO obj) throws BusinessException {
        log.debug("== Inicia createKpi/KpiBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            Kpi objPojo =  UtilsBusiness.copyObject(Kpi.class, obj);
            daoKpi.createKpi(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createKpi/KpiBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpi/KpiBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal#updateKpi(co.com.directv.sdii.model.vo.KpiVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateKpi(KpiVO obj) throws BusinessException {
        log.debug("== Inicia updateKpi/KpiBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            Kpi objPojo =  UtilsBusiness.copyObject(Kpi.class, obj);
            daoKpi.updateKpi(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateKpi/KpiBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpi/KpiBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiBusinessBeanLocal#deleteKpi(co.com.directv.sdii.model.vo.KpiVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteKpi(KpiVO obj) throws BusinessException {
        log.debug("== Inicia deleteKpi/KpiBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Kpi objPojo =  UtilsBusiness.copyObject(Kpi.class, obj);
            daoKpi.deleteKpi(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteKpi/KpiBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpi/KpiBusinessBean ==");
        }
	}
}
