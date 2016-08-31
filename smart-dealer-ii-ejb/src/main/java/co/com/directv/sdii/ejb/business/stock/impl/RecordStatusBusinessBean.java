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
import co.com.directv.sdii.ejb.business.stock.RecordStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.vo.RecordStatusVO;
import co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD RecordStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.RecordStatusBusinessBeanLocal
 */
@Stateless(name="RecordStatusBusinessBeanLocal",mappedName="ejb/RecordStatusBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RecordStatusBusinessBean extends BusinessBase implements RecordStatusBusinessBeanLocal {

    @EJB(name="RecordStatusDAOLocal", beanInterface=RecordStatusDAOLocal.class)
    private RecordStatusDAOLocal daoRecordStatus;
    
    private final static Logger log = UtilsBusiness.getLog4J(RecordStatusBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RecordStatusBusinessBeanLocal#getAllRecordStatuss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RecordStatusVO> getAllRecordStatuss() throws BusinessException {
        log.debug("== Inicia getAllRecordStatuss/RecordStatusBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoRecordStatus.getAllRecordStatuss(), RecordStatusVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllRecordStatuss/RecordStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllRecordStatuss/RecordStatusBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RecordStatusBusinessBeanLocal#getRecordStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RecordStatusVO getRecordStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getRecordStatusByID/RecordStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RecordStatus objPojo = daoRecordStatus.getRecordStatusByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(RecordStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getRecordStatusByID/RecordStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRecordStatusByID/RecordStatusBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RecordStatusBusinessBeanLocal#createRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createRecordStatus(RecordStatusVO obj) throws BusinessException {
        log.debug("== Inicia createRecordStatus/RecordStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RecordStatus objPojo =  UtilsBusiness.copyObject(RecordStatus.class, obj);
            daoRecordStatus.createRecordStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createRecordStatus/RecordStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRecordStatus/RecordStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RecordStatusBusinessBeanLocal#updateRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateRecordStatus(RecordStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateRecordStatus/RecordStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RecordStatus objPojo =  UtilsBusiness.copyObject(RecordStatus.class, obj);
            daoRecordStatus.updateRecordStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateRecordStatus/RecordStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRecordStatus/RecordStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RecordStatusBusinessBeanLocal#deleteRecordStatus(co.com.directv.sdii.model.vo.RecordStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteRecordStatus(RecordStatusVO obj) throws BusinessException {
        log.debug("== Inicia deleteRecordStatus/RecordStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RecordStatus objPojo =  UtilsBusiness.copyObject(RecordStatus.class, obj);
            daoRecordStatus.deleteRecordStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteRecordStatus/RecordStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRecordStatus/RecordStatusBusinessBean ==");
        }
	}
}
