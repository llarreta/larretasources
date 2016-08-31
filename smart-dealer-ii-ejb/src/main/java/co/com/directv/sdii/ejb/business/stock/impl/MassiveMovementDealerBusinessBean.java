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
import co.com.directv.sdii.ejb.business.stock.MassiveMovementDealerBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.directv.sdii.model.pojo.MassiveMovementDealer;
import co.com.directv.sdii.model.vo.MassiveMovementDealerVO;
import co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD MassiveMovementDealer
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.MassiveMovementDealerDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementDealerBusinessBeanLocal
 */
@Stateless(name="MassiveMovementDealerBusinessBeanLocal",mappedName="ejb/MassiveMovementDealerBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MassiveMovementDealerBusinessBean extends BusinessBase implements MassiveMovementDealerBusinessBeanLocal {

    @EJB(name="MassiveMovementDealerDAOLocal", beanInterface=MassiveMovementDealerDAOLocal.class)
    private MassiveMovementDealerDAOLocal daoMassiveMovementDealer;
    
    private final static Logger log = UtilsBusiness.getLog4J(MassiveMovementDealerBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementDealerBusinessBeanLocal#getAllMassiveMovementDealers()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MassiveMovementDealerVO> getAllMassiveMovementDealers() throws BusinessException {
        log.debug("== Inicia getAllMassiveMovementDealers/MassiveMovementDealerBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoMassiveMovementDealer.getAllMassiveMovementDealers(), MassiveMovementDealerVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllMassiveMovementDealers/MassiveMovementDealerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMassiveMovementDealers/MassiveMovementDealerBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementDealerBusinessBeanLocal#getMassiveMovementDealersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public MassiveMovementDealerVO getMassiveMovementDealerByID(Long id) throws BusinessException {
        log.debug("== Inicia getMassiveMovementDealerByID/MassiveMovementDealerBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MassiveMovementDealer objPojo = daoMassiveMovementDealer.getMassiveMovementDealerByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(MassiveMovementDealerVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMassiveMovementDealerByID/MassiveMovementDealerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMassiveMovementDealerByID/MassiveMovementDealerBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementDealerBusinessBeanLocal#createMassiveMovementDealer(co.com.directv.sdii.model.vo.MassiveMovementDealerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException {
        log.debug("== Inicia createMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            MassiveMovementDealer objPojo =  UtilsBusiness.copyObject(MassiveMovementDealer.class, obj);
            daoMassiveMovementDealer.createMassiveMovementDealer(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementDealerBusinessBeanLocal#updateMassiveMovementDealer(co.com.directv.sdii.model.vo.MassiveMovementDealerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException {
        log.debug("== Inicia updateMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            MassiveMovementDealer objPojo =  UtilsBusiness.copyObject(MassiveMovementDealer.class, obj);
            daoMassiveMovementDealer.updateMassiveMovementDealer(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MassiveMovementDealerBusinessBeanLocal#deleteMassiveMovementDealer(co.com.directv.sdii.model.vo.MassiveMovementDealerVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMassiveMovementDealer(MassiveMovementDealerVO obj) throws BusinessException {
        log.debug("== Inicia deleteMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MassiveMovementDealer objPojo =  UtilsBusiness.copyObject(MassiveMovementDealer.class, obj);
            daoMassiveMovementDealer.deleteMassiveMovementDealer(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMassiveMovementDealer/MassiveMovementDealerBusinessBean ==");
        }
	}


	@Override
	public List<MassiveMovementDealerVO> getMassiveMovementDealerByMovementID(
			Long id) throws BusinessException {
		log.debug("== Inicia getMassiveMovementDealerByMovementID/MassiveMovementDealerBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	
        	List<MassiveMovementDealer> massiveMovementDealer = daoMassiveMovementDealer.getMassiveMovementDealerByMovementID(id);
        	List<MassiveMovementDealerVO> result = UtilsBusiness.convertList(massiveMovementDealer, MassiveMovementDealerVO.class);
        	return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMassiveMovementDealerByMovementID/MassiveMovementDealerBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMassiveMovementDealerByMovementID/MassiveMovementDealerBusinessBean ==");
        }
	}
}
