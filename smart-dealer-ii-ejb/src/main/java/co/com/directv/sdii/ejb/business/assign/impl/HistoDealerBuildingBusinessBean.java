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
import co.com.directv.sdii.ejb.business.assign.HistoDealerBuildingBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.HistoDealerBuilding;
import co.com.directv.sdii.model.vo.HistoDealerBuildingVO;
import co.com.directv.sdii.persistence.dao.assign.HistoDealerBuildingDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD HistoDealerBuilding
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.HistoDealerBuildingDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerBuildingBusinessBeanLocal
 */
@Stateless(name="HistoDealerBuildingBusinessBeanLocal",mappedName="ejb/HistoDealerBuildingBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoDealerBuildingBusinessBean extends BusinessBase implements HistoDealerBuildingBusinessBeanLocal {

    @EJB(name="HistoDealerBuildingDAOLocal", beanInterface=HistoDealerBuildingDAOLocal.class)
    private HistoDealerBuildingDAOLocal daoHistoDealerBuilding;
    
    private final static Logger log = UtilsBusiness.getLog4J(HistoDealerBuildingBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerBuildingBusinessBeanLocal#getAllHistoDealerBuildings()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<HistoDealerBuildingVO> getAllHistoDealerBuildings() throws BusinessException {
        log.debug("== Inicia getAllHistoDealerBuildings/HistoDealerBuildingBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoHistoDealerBuilding.getAllHistoDealerBuildings(), HistoDealerBuildingVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllHistoDealerBuildings/HistoDealerBuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllHistoDealerBuildings/HistoDealerBuildingBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerBuildingBusinessBeanLocal#getHistoDealerBuildingsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public HistoDealerBuildingVO getHistoDealerBuildingByID(Long id) throws BusinessException {
        log.debug("== Inicia getHistoDealerBuildingByID/HistoDealerBuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerBuilding objPojo = daoHistoDealerBuilding.getHistoDealerBuildingByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(HistoDealerBuildingVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getHistoDealerBuildingByID/HistoDealerBuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getHistoDealerBuildingByID/HistoDealerBuildingBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerBuildingBusinessBeanLocal#createHistoDealerBuilding(co.com.directv.sdii.model.vo.HistoDealerBuildingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException {
        log.debug("== Inicia createHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerBuilding objPojo =  UtilsBusiness.copyObject(HistoDealerBuilding.class, obj);
            daoHistoDealerBuilding.createHistoDealerBuilding(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerBuildingBusinessBeanLocal#updateHistoDealerBuilding(co.com.directv.sdii.model.vo.HistoDealerBuildingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException {
        log.debug("== Inicia updateHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            HistoDealerBuilding objPojo =  UtilsBusiness.copyObject(HistoDealerBuilding.class, obj);
            daoHistoDealerBuilding.updateHistoDealerBuilding(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.HistoDealerBuildingBusinessBeanLocal#deleteHistoDealerBuilding(co.com.directv.sdii.model.vo.HistoDealerBuildingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException {
        log.debug("== Inicia deleteHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            HistoDealerBuilding objPojo =  UtilsBusiness.copyObject(HistoDealerBuilding.class, obj);
            daoHistoDealerBuilding.deleteHistoDealerBuilding(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteHistoDealerBuilding/HistoDealerBuildingBusinessBean ==");
        }
	}
}
