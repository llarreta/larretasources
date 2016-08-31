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
import co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ShippingOrderElement;
import co.com.directv.sdii.model.vo.ShippingOrderElementVO;
import co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD ShippingOrderElement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ShippingOrderElementDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal
 */
@Stateless(name="ShippingOrderElementBusinessBeanLocal",mappedName="ejb/ShippingOrderElementBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShippingOrderElementBusinessBean extends BusinessBase implements ShippingOrderElementBusinessBeanLocal {

    @EJB(name="ShippingOrderElementDAOLocal", beanInterface=ShippingOrderElementDAOLocal.class)
    private ShippingOrderElementDAOLocal daoShippingOrderElement;
    
    private final static Logger log = UtilsBusiness.getLog4J(ShippingOrderElementBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal#getAllShippingOrderElements()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ShippingOrderElementVO> getAllShippingOrderElements() throws BusinessException {
        log.debug("== Inicia getAllShippingOrderElements/ShippingOrderElementBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoShippingOrderElement.getAllShippingOrderElements(), ShippingOrderElementVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllShippingOrderElements/ShippingOrderElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllShippingOrderElements/ShippingOrderElementBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal#getShippingOrderElementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ShippingOrderElementVO getShippingOrderElementByID(Long id) throws BusinessException {
        log.debug("== Inicia getShippingOrderElementByID/ShippingOrderElementBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ShippingOrderElement objPojo = daoShippingOrderElement.getShippingOrderElementByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ShippingOrderElementVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getShippingOrderElementByID/ShippingOrderElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderElementByID/ShippingOrderElementBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal#createShippingOrderElement(co.com.directv.sdii.model.vo.ShippingOrderElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException {
        log.debug("== Inicia createShippingOrderElement/ShippingOrderElementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ShippingOrderElement objPojo =  UtilsBusiness.copyObject(ShippingOrderElement.class, obj);
            daoShippingOrderElement.createShippingOrderElement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createShippingOrderElement/ShippingOrderElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createShippingOrderElement/ShippingOrderElementBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal#updateShippingOrderElement(co.com.directv.sdii.model.vo.ShippingOrderElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException {
        log.debug("== Inicia updateShippingOrderElement/ShippingOrderElementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ShippingOrderElement objPojo =  UtilsBusiness.copyObject(ShippingOrderElement.class, obj);
            daoShippingOrderElement.updateShippingOrderElement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateShippingOrderElement/ShippingOrderElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateShippingOrderElement/ShippingOrderElementBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal#deleteShippingOrderElement(co.com.directv.sdii.model.vo.ShippingOrderElementVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteShippingOrderElement(ShippingOrderElementVO obj) throws BusinessException {
        log.debug("== Inicia deleteShippingOrderElement/ShippingOrderElementBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ShippingOrderElement objPojo =  UtilsBusiness.copyObject(ShippingOrderElement.class, obj);
            daoShippingOrderElement.deleteShippingOrderElement(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteShippingOrderElement/ShippingOrderElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteShippingOrderElement/ShippingOrderElementBusinessBean ==");
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ShippingOrderElementBusinessBeanLocal#getShippingOrderElementBySOID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ShippingOrderElementVO> getShippingOrderElementBySOID(Long id) throws BusinessException {
        log.debug("== Inicia getShippingOrderElementBySOID/ShippingOrderElementBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            List<ShippingOrderElement> listShippingOrderElement = daoShippingOrderElement.getShippingOrderElementBySOID(id);
            if (listShippingOrderElement == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.convertList(listShippingOrderElement,ShippingOrderElementVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getShippingOrderElementBySOID/ShippingOrderElementBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderElementBySOID/ShippingOrderElementBusinessBean ==");
        }
    }
}
