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
import co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.NotSerPartialRetirement;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD NotSerPartialRetirement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementBusinessBeanLocal
 */
@Stateless(name="NotSerPartialRetirementBusinessBeanLocal",mappedName="ejb/NotSerPartialRetirementBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotSerPartialRetirementBusinessBean extends BusinessBase implements NotSerPartialRetirementBusinessBeanLocal {

    @EJB(name="NotSerPartialRetirementDAOLocal", beanInterface=NotSerPartialRetirementDAOLocal.class)
    private NotSerPartialRetirementDAOLocal daoNotSerPartialRetirement;
    
    private final static Logger log = UtilsBusiness.getLog4J(NotSerPartialRetirementBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementBusinessBeanLocal#getAllNotSerPartialRetirements()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<NotSerPartialRetirementVO> getAllNotSerPartialRetirements() throws BusinessException {
        log.debug("== Inicia getAllNotSerPartialRetirements/NotSerPartialRetirementBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoNotSerPartialRetirement.getAllNotSerPartialRetirements(), NotSerPartialRetirementVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllNotSerPartialRetirements/NotSerPartialRetirementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllNotSerPartialRetirements/NotSerPartialRetirementBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementBusinessBeanLocal#getNotSerPartialRetirementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NotSerPartialRetirementVO getNotSerPartialRetirementByID(Long id) throws BusinessException {
        log.debug("== Inicia getNotSerPartialRetirementByID/NotSerPartialRetirementBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerPartialRetirement objPojo = daoNotSerPartialRetirement.getNotSerPartialRetirementByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(NotSerPartialRetirementVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getNotSerPartialRetirementByID/NotSerPartialRetirementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getNotSerPartialRetirementByID/NotSerPartialRetirementBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementBusinessBeanLocal#createNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException {
        log.debug("== Inicia createNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerPartialRetirement objPojo =  UtilsBusiness.copyObject(NotSerPartialRetirement.class, obj);
            daoNotSerPartialRetirement.createNotSerPartialRetirement(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementBusinessBeanLocal#updateNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException {
        log.debug("== Inicia updateNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerPartialRetirement objPojo =  UtilsBusiness.copyObject(NotSerPartialRetirement.class, obj);
            daoNotSerPartialRetirement.updateNotSerPartialRetirement(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerPartialRetirementBusinessBeanLocal#deleteNotSerPartialRetirement(co.com.directv.sdii.model.vo.NotSerPartialRetirementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteNotSerPartialRetirement(NotSerPartialRetirementVO obj) throws BusinessException {
        log.debug("== Inicia deleteNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerPartialRetirement objPojo =  UtilsBusiness.copyObject(NotSerPartialRetirement.class, obj);
            daoNotSerPartialRetirement.deleteNotSerPartialRetirement(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteNotSerPartialRetirement/NotSerPartialRetirementBusinessBean ==");
        }
	}
}
