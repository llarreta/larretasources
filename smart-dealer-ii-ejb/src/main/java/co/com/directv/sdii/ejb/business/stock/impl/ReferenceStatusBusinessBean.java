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
import co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ReferenceStatus;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;
import co.com.directv.sdii.persistence.dao.stock.ReferenceStatusDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ReferenceStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ReferenceStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal
 */
@Stateless(name="ReferenceStatusBusinessBeanLocal",mappedName="ejb/ReferenceStatusBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReferenceStatusBusinessBean extends BusinessBase implements ReferenceStatusBusinessBeanLocal {

    @EJB(name="ReferenceStatusDAOLocal", beanInterface=ReferenceStatusDAOLocal.class)
    private ReferenceStatusDAOLocal daoReferenceStatus;
    
    private final static Logger log = UtilsBusiness.getLog4J(ReferenceStatusBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal#getAllReferenceStatuss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReferenceStatusVO> getAllReferenceStatuss() throws BusinessException {
        log.debug("== Inicia getAllReferenceStatuss/ReferenceStatusBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoReferenceStatus.getAllReferenceStatuss(), ReferenceStatusVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllReferenceStatuss/ReferenceStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllReferenceStatuss/ReferenceStatusBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal#getReferenceStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReferenceStatusVO getReferenceStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getReferenceStatusByID/ReferenceStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceStatus objPojo = daoReferenceStatus.getReferenceStatusByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ReferenceStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getReferenceStatusByID/ReferenceStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getReferenceStatusByID/ReferenceStatusBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal#createReferenceStatus(co.com.directv.sdii.model.vo.ReferenceStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createReferenceStatus(ReferenceStatusVO obj) throws BusinessException {
        log.debug("== Inicia createReferenceStatus/ReferenceStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceStatus objPojo =  UtilsBusiness.copyObject(ReferenceStatus.class, obj);
            daoReferenceStatus.createReferenceStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createReferenceStatus/ReferenceStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReferenceStatus/ReferenceStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal#updateReferenceStatus(co.com.directv.sdii.model.vo.ReferenceStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateReferenceStatus(ReferenceStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateReferenceStatus/ReferenceStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceStatus objPojo =  UtilsBusiness.copyObject(ReferenceStatus.class, obj);
            daoReferenceStatus.updateReferenceStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateReferenceStatus/ReferenceStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReferenceStatus/ReferenceStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal#deleteReferenceStatus(co.com.directv.sdii.model.vo.ReferenceStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteReferenceStatus(ReferenceStatusVO obj) throws BusinessException {
        log.debug("== Inicia deleteReferenceStatus/ReferenceStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ReferenceStatus objPojo =  UtilsBusiness.copyObject(ReferenceStatus.class, obj);
            daoReferenceStatus.deleteReferenceStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteReferenceStatus/ReferenceStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReferenceStatus/ReferenceStatusBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ReferenceStatusBusinessBeanLocal#getReferenceStatusByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ReferenceStatusVO getReferenceStatusByCode(String code)throws BusinessException{
		 log.debug("== Inicia deleteReferenceStatus/ReferenceStatusBusinessBean ==");
	        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        try {
	            ReferenceStatus objPojo = daoReferenceStatus.getReferenceByCode(code);
	            return UtilsBusiness.copyObject(ReferenceStatusVO.class, objPojo);
	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación deleteReferenceStatus/ReferenceStatusBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina deleteReferenceStatus/ReferenceStatusBusinessBean ==");
	        }
	}
}
