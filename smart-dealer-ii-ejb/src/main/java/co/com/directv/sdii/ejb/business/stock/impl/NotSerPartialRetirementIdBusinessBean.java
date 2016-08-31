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
import co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementIdBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.NotSerPartialRetirementId;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO;
import co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementIdDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD NotSerPartialRetirementId
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementIdDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementIdBusinessBeanLocal
 */
@Stateless(name="NotSerPartialRetirementIdBusinessBeanLocal",mappedName="ejb/NotSerPartialRetirementIdBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotSerPartialRetirementIdBusinessBean extends BusinessBase implements NotSerPartialRetirementIdBusinessBeanLocal {

    @EJB(name="NotSerPartialRetirementIdDAOLocal", beanInterface=NotSerPartialRetirementIdDAOLocal.class)
    private NotSerPartialRetirementIdDAOLocal daoNotSerPartialRetirementId;
    
    private final static Logger log = UtilsBusiness.getLog4J(NotSerPartialRetirementIdBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementIdBusinessBeanLocal#getAllNotSerPartialRetirementIds()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<NotSerPartialRetirementIdVO> getAllNotSerPartialRetirementIds() throws BusinessException {
        log.debug("== Inicia getAllNotSerPartialRetirementIds/NotSerPartialRetirementIdBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoNotSerPartialRetirementId.getAllNotSerPartialRetirementIds(), NotSerPartialRetirementIdVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllNotSerPartialRetirementIds/NotSerPartialRetirementIdBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllNotSerPartialRetirementIds/NotSerPartialRetirementIdBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementIdBusinessBeanLocal#getNotSerPartialRetirementIdsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NotSerPartialRetirementIdVO getNotSerPartialRetirementIdByID(Long id) throws BusinessException {
        log.debug("== Inicia getNotSerPartialRetirementIdByID/NotSerPartialRetirementIdBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerPartialRetirementId objPojo = daoNotSerPartialRetirementId.getNotSerPartialRetirementIdByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(NotSerPartialRetirementIdVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getNotSerPartialRetirementIdByID/NotSerPartialRetirementIdBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getNotSerPartialRetirementIdByID/NotSerPartialRetirementIdBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementIdBusinessBeanLocal#createNotSerPartialRetirementId(co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException {
        log.debug("== Inicia createNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerPartialRetirementId objPojo =  UtilsBusiness.copyObject(NotSerPartialRetirementId.class, obj);
            daoNotSerPartialRetirementId.createNotSerPartialRetirementId(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementIdBusinessBeanLocal#updateNotSerPartialRetirementId(co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException {
        log.debug("== Inicia updateNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerPartialRetirementId objPojo =  UtilsBusiness.copyObject(NotSerPartialRetirementId.class, obj);
            daoNotSerPartialRetirementId.updateNotSerPartialRetirementId(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementIdBusinessBeanLocal#deleteNotSerPartialRetirementId(co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException {
        log.debug("== Inicia deleteNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerPartialRetirementId objPojo =  UtilsBusiness.copyObject(NotSerPartialRetirementId.class, obj);
            daoNotSerPartialRetirementId.deleteNotSerPartialRetirementId(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteNotSerPartialRetirementId/NotSerPartialRetirementIdBusinessBean ==");
        }
	}
}
