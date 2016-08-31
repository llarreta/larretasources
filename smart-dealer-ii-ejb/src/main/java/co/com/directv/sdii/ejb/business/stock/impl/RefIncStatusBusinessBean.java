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
import co.com.directv.sdii.ejb.business.stock.RefIncStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.RefIncStatus;
import co.com.directv.sdii.model.vo.RefIncStatusVO;
import co.com.directv.sdii.persistence.dao.stock.RefIncStatusDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD RefIncStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.RefIncStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.RefIncStatusBusinessBeanLocal
 */
@Stateless(name="RefIncStatusBusinessBeanLocal",mappedName="ejb/RefIncStatusBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefIncStatusBusinessBean extends BusinessBase implements RefIncStatusBusinessBeanLocal {

    @EJB(name="RefIncStatusDAOLocal", beanInterface=RefIncStatusDAOLocal.class)
    private RefIncStatusDAOLocal daoRefIncStatus;
    
    private final static Logger log = UtilsBusiness.getLog4J(RefIncStatusBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RefIncStatusBusinessBeanLocal#getAllRefIncStatuss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RefIncStatusVO> getAllRefIncStatuss() throws BusinessException {
        log.debug("== Inicia getAllRefIncStatuss/RefIncStatusBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoRefIncStatus.getAllRefIncStatuss(), RefIncStatusVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllRefIncStatuss/RefIncStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllRefIncStatuss/RefIncStatusBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RefIncStatusBusinessBeanLocal#getRefIncStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RefIncStatusVO getRefIncStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getRefIncStatusByID/RefIncStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefIncStatus objPojo = daoRefIncStatus.getRefIncStatusByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(RefIncStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefIncStatusByID/RefIncStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefIncStatusByID/RefIncStatusBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefIncStatusBusinessBeanLocal#createRefIncStatus(co.com.directv.sdii.model.vo.RefIncStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createRefIncStatus(RefIncStatusVO obj) throws BusinessException {
        log.debug("== Inicia createRefIncStatus/RefIncStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefIncStatus objPojo =  UtilsBusiness.copyObject(RefIncStatus.class, obj);
            daoRefIncStatus.createRefIncStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createRefIncStatus/RefIncStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRefIncStatus/RefIncStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefIncStatusBusinessBeanLocal#updateRefIncStatus(co.com.directv.sdii.model.vo.RefIncStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateRefIncStatus(RefIncStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateRefIncStatus/RefIncStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefIncStatus objPojo =  UtilsBusiness.copyObject(RefIncStatus.class, obj);
            daoRefIncStatus.updateRefIncStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateRefIncStatus/RefIncStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefIncStatus/RefIncStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefIncStatusBusinessBeanLocal#deleteRefIncStatus(co.com.directv.sdii.model.vo.RefIncStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteRefIncStatus(RefIncStatusVO obj) throws BusinessException {
        log.debug("== Inicia deleteRefIncStatus/RefIncStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefIncStatus objPojo =  UtilsBusiness.copyObject(RefIncStatus.class, obj);
            daoRefIncStatus.deleteRefIncStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteRefIncStatus/RefIncStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefIncStatus/RefIncStatusBusinessBean ==");
        }
	}
}
