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
import co.com.directv.sdii.ejb.business.stock.ReferenceModTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ReferenceModType;
import co.com.directv.sdii.model.vo.ReferenceModTypeVO;
import co.com.directv.sdii.persistence.dao.stock.ReferenceModTypeDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ReferenceModType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceModTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModTypeBusinessBeanLocal
 */
@Stateless(name="ReferenceModTypeBusinessBeanLocal",mappedName="ejb/ReferenceModTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceModTypeBusinessBean extends BusinessBase implements ReferenceModTypeBusinessBeanLocal {

    @EJB(name="ReferenceModTypeDAOLocal", beanInterface=ReferenceModTypeDAOLocal.class)
    private ReferenceModTypeDAOLocal daoReferenceModType;
    
    private final static Logger log = UtilsBusiness.getLog4J(ReferenceModTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceModTypeBusinessBeanLocal#getAllReferenceModTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceModTypeVO> getAllReferenceModTypes() throws BusinessException {
        log.debug("== Inicia getAllReferenceModTypes/ReferenceModTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoReferenceModType.getAllReferenceModTypes(), ReferenceModTypeVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllReferenceModTypes/ReferenceModTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllReferenceModTypes/ReferenceModTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceModTypeBusinessBeanLocal#getReferenceModTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceModTypeVO getReferenceModTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getReferenceModTypeByID/ReferenceModTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceModType objPojo = daoReferenceModType.getReferenceModTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ReferenceModTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceModTypeByID/ReferenceModTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceModTypeByID/ReferenceModTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModTypeBusinessBeanLocal#createReferenceModType(co.com.directv.sdii.model.vo.ReferenceModTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createReferenceModType(ReferenceModTypeVO obj) throws BusinessException {
        log.debug("== Inicia createReferenceModType/ReferenceModTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceModType objPojo =  UtilsBusiness.copyObject(ReferenceModType.class, obj);
            daoReferenceModType.createReferenceModType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createReferenceModType/ReferenceModTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceModType/ReferenceModTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModTypeBusinessBeanLocal#updateReferenceModType(co.com.directv.sdii.model.vo.ReferenceModTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateReferenceModType(ReferenceModTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateReferenceModType/ReferenceModTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceModType objPojo =  UtilsBusiness.copyObject(ReferenceModType.class, obj);
            daoReferenceModType.updateReferenceModType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateReferenceModType/ReferenceModTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceModType/ReferenceModTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceModTypeBusinessBeanLocal#deleteReferenceModType(co.com.directv.sdii.model.vo.ReferenceModTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteReferenceModType(ReferenceModTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteReferenceModType/ReferenceModTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceModType objPojo =  UtilsBusiness.copyObject(ReferenceModType.class, obj);
            daoReferenceModType.deleteReferenceModType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteReferenceModType/ReferenceModTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceModType/ReferenceModTypeBusinessBean ==");
        }
	}
}
