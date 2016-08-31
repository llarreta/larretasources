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
import co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ImpLogConfirmation;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;
import co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ImpLogConfirmation
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal
 */
@Stateless(name="ImpLogConfirmationBusinessBeanLocal",mappedName="ejb/ImpLogConfirmationBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImpLogConfirmationBusinessBean extends BusinessBase implements ImpLogConfirmationBusinessBeanLocal {

    @EJB(name="ImpLogConfirmationDAOLocal", beanInterface=ImpLogConfirmationDAOLocal.class)
    private ImpLogConfirmationDAOLocal daoImpLogConfirmation;
    
    private final static Logger log = UtilsBusiness.getLog4J(ImpLogConfirmationBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal#getAllImpLogConfirmations()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ImpLogConfirmationVO> getAllImpLogConfirmations() throws BusinessException {
        log.debug("== Inicia getAllImpLogConfirmations/ImpLogConfirmationBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoImpLogConfirmation.getAllImpLogConfirmations(), ImpLogConfirmationVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllImpLogConfirmations/ImpLogConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllImpLogConfirmations/ImpLogConfirmationBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal#getImpLogConfirmationsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ImpLogConfirmationVO getImpLogConfirmationByID(Long id) throws BusinessException {
        log.debug("== Inicia getImpLogConfirmationByID/ImpLogConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogConfirmation objPojo = daoImpLogConfirmation.getImpLogConfirmationByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ImpLogConfirmationVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImpLogConfirmationByID/ImpLogConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImpLogConfirmationByID/ImpLogConfirmationBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal#createImpLogConfirmation(co.com.directv.sdii.model.vo.ImpLogConfirmationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException {
        log.debug("== Inicia createImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogConfirmation objPojo =  UtilsBusiness.copyObject(ImpLogConfirmation.class, obj);
            daoImpLogConfirmation.createImpLogConfirmation(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal#updateImpLogConfirmation(co.com.directv.sdii.model.vo.ImpLogConfirmationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException {
        log.debug("== Inicia updateImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogConfirmation objPojo =  UtilsBusiness.copyObject(ImpLogConfirmation.class, obj);
            daoImpLogConfirmation.updateImpLogConfirmation(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal#deleteImpLogConfirmation(co.com.directv.sdii.model.vo.ImpLogConfirmationVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException {
        log.debug("== Inicia deleteImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogConfirmation objPojo =  UtilsBusiness.copyObject(ImpLogConfirmation.class, obj);
            daoImpLogConfirmation.deleteImpLogConfirmation(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogConfirmation/ImpLogConfirmationBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal#deleteImpLogConfirmationByImpLogItemId(java.lang.Long)
	 */
	@Override
	public void deleteImpLogConfirmationByImpLogItemId(Long impLogItemId) throws BusinessException {
		log.debug("== Inicia deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationBusinessBean ==");
		UtilsBusiness.assertNotNull(impLogItemId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	daoImpLogConfirmation.deleteImpLogConfirmationByImpLogItemId(impLogItemId);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogConfirmationByImpLogItemId/ImpLogConfirmationBusinessBean ==");
        }
	}
}
