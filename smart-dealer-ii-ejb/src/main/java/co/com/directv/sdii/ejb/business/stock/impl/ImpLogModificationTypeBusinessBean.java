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
import co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ImpLogModificationType;
import co.com.directv.sdii.model.vo.ImpLogModificationTypeVO;
import co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ImpLogModificationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ImpLogModificationTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal
 */
@Stateless(name="ImpLogModificationTypeBusinessBeanLocal",mappedName="ejb/ImpLogModificationTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImpLogModificationTypeBusinessBean extends BusinessBase implements ImpLogModificationTypeBusinessBeanLocal {

    @EJB(name="ImpLogModificationTypeDAOLocal", beanInterface=ImpLogModificationTypeDAOLocal.class)
    private ImpLogModificationTypeDAOLocal daoImpLogModificationType;
    
    private final static Logger log = UtilsBusiness.getLog4J(ImpLogModificationTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal#getAllImpLogModificationTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ImpLogModificationTypeVO> getAllImpLogModificationTypes() throws BusinessException {
        log.debug("== Inicia getAllImpLogModificationTypes/ImpLogModificationTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoImpLogModificationType.getAllImpLogModificationTypes(), ImpLogModificationTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllImpLogModificationTypes/ImpLogModificationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllImpLogModificationTypes/ImpLogModificationTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal#getImpLogModificationTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ImpLogModificationTypeVO getImpLogModificationTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getImpLogModificationTypeByID/ImpLogModificationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogModificationType objPojo = daoImpLogModificationType.getImpLogModificationTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ImpLogModificationTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImpLogModificationTypeByID/ImpLogModificationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImpLogModificationTypeByID/ImpLogModificationTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal#createImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException {
        log.debug("== Inicia createImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	ImpLogModificationType objPojo = daoImpLogModificationType.getImpLogModificationTypeByCode(obj.getModTypeCode());
        	if(objPojo != null){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            objPojo =  UtilsBusiness.copyObject(ImpLogModificationType.class, obj);
            daoImpLogModificationType.createImpLogModificationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal#updateImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	ImpLogModificationType objPojo = daoImpLogModificationType.getImpLogModificationTypeByCode(obj.getModTypeCode());
        	if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            objPojo =  UtilsBusiness.copyObject(ImpLogModificationType.class, obj);
            daoImpLogModificationType.updateImpLogModificationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal#deleteImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogModificationType objPojo =  UtilsBusiness.copyObject(ImpLogModificationType.class, obj);
            daoImpLogModificationType.deleteImpLogModificationType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImpLogModificationType/ImpLogModificationTypeBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal#getImpLogModificationTypeByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ImpLogModificationTypeVO getImpLogModificationTypeByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getImpLogModificationTypeByCode/ImpLogModificationTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImpLogModificationType objPojo = daoImpLogModificationType.getImpLogModificationTypeByCode(code);
            if (objPojo == null) {
            	return null;
            }
            return UtilsBusiness.copyObject(ImpLogModificationTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImpLogModificationTypeByCode/ImpLogModificationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImpLogModificationTypeByCode/ImpLogModificationTypeBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal#getActiveImpLogModificationTypes()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ImpLogModificationTypeVO> getActiveImpLogModificationTypes()
			throws BusinessException {
		log.debug("== Inicia getActiveImpLogModificationTypes/ImpLogModificationTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoImpLogModificationType.getActiveImpLogModificationTypes(), ImpLogModificationTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveImpLogModificationTypes/ImpLogModificationTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveImpLogModificationTypes/ImpLogModificationTypeBusinessBean ==");
        }
	}
}
