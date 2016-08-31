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
import co.com.directv.sdii.ejb.business.stock.RefIncTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.RefIncType;
import co.com.directv.sdii.model.vo.RefIncTypeVO;
import co.com.directv.sdii.persistence.dao.stock.RefIncTypeDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD RefIncType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.RefIncTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.RefIncTypeBusinessBeanLocal
 */
@Stateless(name="RefIncTypeBusinessBeanLocal",mappedName="ejb/RefIncTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefIncTypeBusinessBean extends BusinessBase implements RefIncTypeBusinessBeanLocal {

    @EJB(name="RefIncTypeDAOLocal", beanInterface=RefIncTypeDAOLocal.class)
    private RefIncTypeDAOLocal daoRefIncType;
    
    private final static Logger log = UtilsBusiness.getLog4J(RefIncTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RefIncTypeBusinessBeanLocal#getAllRefIncTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<RefIncTypeVO> getAllRefIncTypes() throws BusinessException {
        log.debug("== Inicia getAllRefIncTypes/RefIncTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoRefIncType.getAllRefIncTypes(), RefIncTypeVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllRefIncTypes/RefIncTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllRefIncTypes/RefIncTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.RefIncTypeBusinessBeanLocal#getRefIncTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RefIncTypeVO getRefIncTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getRefIncTypeByID/RefIncTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefIncType objPojo = daoRefIncType.getRefIncTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(RefIncTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRefIncTypeByID/RefIncTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefIncTypeByID/RefIncTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefIncTypeBusinessBeanLocal#createRefIncType(co.com.directv.sdii.model.vo.RefIncTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createRefIncType(RefIncTypeVO obj) throws BusinessException {
        log.debug("== Inicia createRefIncType/RefIncTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefIncType objPojo =  UtilsBusiness.copyObject(RefIncType.class, obj);
            daoRefIncType.createRefIncType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createRefIncType/RefIncTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRefIncType/RefIncTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefIncTypeBusinessBeanLocal#updateRefIncType(co.com.directv.sdii.model.vo.RefIncTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateRefIncType(RefIncTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateRefIncType/RefIncTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefIncType objPojo =  UtilsBusiness.copyObject(RefIncType.class, obj);
            daoRefIncType.updateRefIncType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateRefIncType/RefIncTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateRefIncType/RefIncTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.RefIncTypeBusinessBeanLocal#deleteRefIncType(co.com.directv.sdii.model.vo.RefIncTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteRefIncType(RefIncTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteRefIncType/RefIncTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            RefIncType objPojo =  UtilsBusiness.copyObject(RefIncType.class, obj);
            daoRefIncType.deleteRefIncType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteRefIncType/RefIncTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRefIncType/RefIncTypeBusinessBean ==");
        }
	}
}
