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
import co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.directv.sdii.model.pojo.KpiType;
import co.com.directv.sdii.model.vo.KpiTypeVO;
import co.com.directv.sdii.persistence.dao.assign.KpiTypeDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD KpiType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.KpiTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal
 */
@Stateless(name="KpiTypeBusinessBeanLocal",mappedName="ejb/KpiTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiTypeBusinessBean extends BusinessBase implements KpiTypeBusinessBeanLocal {

    @EJB(name="KpiTypeDAOLocal", beanInterface=KpiTypeDAOLocal.class)
    private KpiTypeDAOLocal daoKpiType;
    
    private final static Logger log = UtilsBusiness.getLog4J(KpiTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal#getAllKpiTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<KpiTypeVO> getAllKpiTypes() throws BusinessException {
        log.debug("== Inicia getAllKpiTypes/KpiTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoKpiType.getAllKpiTypes(), KpiTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllKpiTypes/KpiTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllKpiTypes/KpiTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal#getKpiTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public KpiTypeVO getKpiTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getKpiTypeByID/KpiTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiType objPojo = daoKpiType.getKpiTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(KpiTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getKpiTypeByID/KpiTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getKpiTypeByID/KpiTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal#createKpiType(co.com.directv.sdii.model.vo.KpiTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createKpiType(KpiTypeVO obj) throws BusinessException {
        log.debug("== Inicia createKpiType/KpiTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiType objPojo =  UtilsBusiness.copyObject(KpiType.class, obj);
            daoKpiType.createKpiType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createKpiType/KpiTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createKpiType/KpiTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal#updateKpiType(co.com.directv.sdii.model.vo.KpiTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateKpiType(KpiTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateKpiType/KpiTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            KpiType objPojo =  UtilsBusiness.copyObject(KpiType.class, obj);
            daoKpiType.updateKpiType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateKpiType/KpiTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateKpiType/KpiTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.KpiTypeBusinessBeanLocal#deleteKpiType(co.com.directv.sdii.model.vo.KpiTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteKpiType(KpiTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteKpiType/KpiTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            KpiType objPojo =  UtilsBusiness.copyObject(KpiType.class, obj);
            daoKpiType.deleteKpiType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteKpiType/KpiTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteKpiType/KpiTypeBusinessBean ==");
        }
	}
}
