package co.com.directv.sdii.ejb.business.stock.impl;

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
import co.com.directv.sdii.ejb.business.stock.MassiveMovementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.directv.sdii.model.pojo.MassiveMovement;
import co.com.directv.sdii.model.vo.MassiveMovementVO;
import co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD MassiveMovement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementBusinessBeanLocal
 */
@Stateless(name="MassiveMovementBusinessBeanLocal",mappedName="ejb/MassiveMovementBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MassiveMovementBusinessBean extends BusinessBase implements MassiveMovementBusinessBeanLocal {

    @EJB(name="MassiveMovementDAOLocal", beanInterface=MassiveMovementDAOLocal.class)
    private MassiveMovementDAOLocal daoMassiveMovement;
    
    private final static Logger log = UtilsBusiness.getLog4J(MassiveMovementBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementBusinessBeanLocal#getAllMassiveMovements()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MassiveMovementVO> getAllMassiveMovements() throws BusinessException {
        log.debug("== Inicia getAllMassiveMovements/MassiveMovementBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoMassiveMovement.getAllMassiveMovements(), MassiveMovementVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllMassiveMovements/MassiveMovementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMassiveMovements/MassiveMovementBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementBusinessBeanLocal#getMassiveMovementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public MassiveMovementVO getMassiveMovementByID(Long id) throws BusinessException {
        log.debug("== Inicia getMassiveMovementByID/MassiveMovementBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MassiveMovement objPojo = daoMassiveMovement.getMassiveMovementByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(MassiveMovementVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMassiveMovementByID/MassiveMovementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMassiveMovementByID/MassiveMovementBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementBusinessBeanLocal#createMassiveMovement(co.com.directv.sdii.model.vo.MassiveMovementVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMassiveMovement(MassiveMovementVO obj) throws BusinessException {
        log.debug("== Inicia createMassiveMovement/MassiveMovementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            MassiveMovement objPojo =  UtilsBusiness.copyObject(MassiveMovement.class, obj);
            daoMassiveMovement.createMassiveMovement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createMassiveMovement/MassiveMovementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMassiveMovement/MassiveMovementBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementBusinessBeanLocal#updateMassiveMovement(co.com.directv.sdii.model.vo.MassiveMovementVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateMassiveMovement(MassiveMovementVO obj) throws BusinessException {
        log.debug("== Inicia updateMassiveMovement/MassiveMovementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            MassiveMovement objPojo =  UtilsBusiness.copyObject(MassiveMovement.class, obj);
            daoMassiveMovement.updateMassiveMovement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateMassiveMovement/MassiveMovementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMassiveMovement/MassiveMovementBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementBusinessBeanLocal#deleteMassiveMovement(co.com.directv.sdii.model.vo.MassiveMovementVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMassiveMovement(MassiveMovementVO obj) throws BusinessException {
        log.debug("== Inicia deleteMassiveMovement/MassiveMovementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MassiveMovement objPojo =  UtilsBusiness.copyObject(MassiveMovement.class, obj);
            daoMassiveMovement.deleteMassiveMovement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteMassiveMovement/MassiveMovementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMassiveMovement/MassiveMovementBusinessBean ==");
        }
	}
}
