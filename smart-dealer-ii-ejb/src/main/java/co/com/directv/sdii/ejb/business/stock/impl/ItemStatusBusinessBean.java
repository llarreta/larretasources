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
import co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.vo.ItemStatusVO;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ItemStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal
 */
@Stateless(name="ItemStatusBusinessBeanLocal",mappedName="ejb/ItemStatusBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ItemStatusBusinessBean extends BusinessBase implements ItemStatusBusinessBeanLocal {

    @EJB(name="ItemStatusDAOLocal", beanInterface=ItemStatusDAOLocal.class)
    private ItemStatusDAOLocal daoItemStatus;
    
    private final static Logger log = UtilsBusiness.getLog4J(ItemStatusBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal#getAllItemStatuss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ItemStatusVO> getAllItemStatuss() throws BusinessException {
        log.debug("== Inicia getAllItemStatuss/ItemStatusBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoItemStatus.getAllItemStatuss(), ItemStatusVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllItemStatuss/ItemStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllItemStatuss/ItemStatusBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal#getItemStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ItemStatusVO getItemStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getItemStatusByID/ItemStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ItemStatus objPojo = daoItemStatus.getItemStatusByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ItemStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getItemStatusByID/ItemStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getItemStatusByID/ItemStatusBusinessBean ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ItemStatusVO getItemStatusByCode(String itemStatusCode) throws BusinessException {
        log.debug("== Inicia getItemStatusByCode/ItemStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(itemStatusCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ItemStatus objPojo = daoItemStatus.getItemStatusByCode(itemStatusCode);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ItemStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getItemStatusByCode/ItemStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getItemStatusByCode/ItemStatusBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal#createItemStatus(co.com.directv.sdii.model.vo.ItemStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createItemStatus(ItemStatusVO obj) throws BusinessException {
        log.debug("== Inicia createItemStatus/ItemStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ItemStatus objPojo =  UtilsBusiness.copyObject(ItemStatus.class, obj);
            daoItemStatus.createItemStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createItemStatus/ItemStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createItemStatus/ItemStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal#updateItemStatus(co.com.directv.sdii.model.vo.ItemStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateItemStatus(ItemStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateItemStatus/ItemStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ItemStatus objPojo =  UtilsBusiness.copyObject(ItemStatus.class, obj);
            daoItemStatus.updateItemStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateItemStatus/ItemStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateItemStatus/ItemStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ItemStatusBusinessBeanLocal#deleteItemStatus(co.com.directv.sdii.model.vo.ItemStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteItemStatus(ItemStatusVO obj) throws BusinessException {
        log.debug("== Inicia deleteItemStatus/ItemStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ItemStatus objPojo =  UtilsBusiness.copyObject(ItemStatus.class, obj);
            daoItemStatus.deleteItemStatus(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteItemStatus/ItemStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteItemStatus/ItemStatusBusinessBean ==");
        }
	}
}
