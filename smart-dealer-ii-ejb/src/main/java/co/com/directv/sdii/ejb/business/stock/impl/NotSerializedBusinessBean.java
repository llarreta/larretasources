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
import co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD NotSerialized
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal
 */
@Stateless(name="NotSerializedBusinessBeanLocal",mappedName="ejb/NotSerializedBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotSerializedBusinessBean extends BusinessBase implements NotSerializedBusinessBeanLocal {

    @EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
    private NotSerializedDAOLocal daoNotSerialized;
    
    private final static Logger log = UtilsBusiness.getLog4J(NotSerializedBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal#getAllNotSerializeds()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<NotSerializedVO> getAllNotSerializeds() throws BusinessException {
        log.debug("== Inicia getAllNotSerializeds/NotSerializedBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoNotSerialized.getAllNotSerializeds(), NotSerializedVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllNotSerializeds/NotSerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllNotSerializeds/NotSerializedBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal#getNotSerializedsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NotSerializedVO getNotSerializedByID(Long id) throws BusinessException {
        log.debug("== Inicia getNotSerializedByID/NotSerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerialized objPojo = daoNotSerialized.getNotSerializedByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(NotSerializedVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getNotSerializedByID/NotSerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getNotSerializedByID/NotSerializedBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal#createNotSerialized(co.com.directv.sdii.model.vo.NotSerializedVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createNotSerialized(NotSerializedVO obj) throws BusinessException {
        log.debug("== Inicia createNotSerialized/NotSerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerialized objPojo =  UtilsBusiness.copyObject(NotSerialized.class, obj);
            daoNotSerialized.createNotSerialized(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createNotSerialized/NotSerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createNotSerialized/NotSerializedBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal#updateNotSerialized(co.com.directv.sdii.model.vo.NotSerializedVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateNotSerialized(NotSerializedVO obj) throws BusinessException {
        log.debug("== Inicia updateNotSerialized/NotSerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerialized objPojo =  UtilsBusiness.copyObject(NotSerialized.class, obj);
            daoNotSerialized.updateNotSerialized(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateNotSerialized/NotSerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateNotSerialized/NotSerializedBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal#deleteNotSerialized(co.com.directv.sdii.model.vo.NotSerializedVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteNotSerialized(NotSerializedVO obj) throws BusinessException {
        log.debug("== Inicia deleteNotSerialized/NotSerializedBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            NotSerialized objPojo =  UtilsBusiness.copyObject(NotSerialized.class, obj);
            daoNotSerialized.deleteNotSerialized(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteNotSerialized/NotSerializedBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteNotSerialized/NotSerializedBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.NotSerializedBusinessBeanLocal#getNotSerializedByImportLogId(java.lang.Long)
	 */
	public List<NotSerializedVO> getNotSerializedByImportLogId(Long importLogId)
			throws BusinessException {
		log.debug("== Inicia getNotSerializedByImportLogId/NotSerializedBusinessBean ==");
        return null;
	}



}
