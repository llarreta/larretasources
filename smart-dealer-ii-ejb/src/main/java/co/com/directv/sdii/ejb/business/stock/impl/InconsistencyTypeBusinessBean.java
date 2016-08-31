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
import co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.InconsistencyType;
import co.com.directv.sdii.model.vo.InconsistencyTypeVO;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD InconsistencyType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.InconsistencyTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal
 */
@Stateless(name="InconsistencyTypeBusinessBeanLocal",mappedName="ejb/InconsistencyTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InconsistencyTypeBusinessBean extends BusinessBase implements InconsistencyTypeBusinessBeanLocal {

    @EJB(name="InconsistencyTypeDAOLocal", beanInterface=InconsistencyTypeDAOLocal.class)
    private InconsistencyTypeDAOLocal daoInconsistencyType;
    
    private final static Logger log = UtilsBusiness.getLog4J(InconsistencyTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal#getAllInconsistencyTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<InconsistencyTypeVO> getAllInconsistencyTypes() throws BusinessException {
        log.debug("== Inicia getAllInconsistencyTypes/InconsistencyTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoInconsistencyType.getAllInconsistencyTypes(), InconsistencyTypeVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllInconsistencyTypes/InconsistencyTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllInconsistencyTypes/InconsistencyTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal#getInconsistencyTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public InconsistencyTypeVO getInconsistencyTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getInconsistencyTypeByID/InconsistencyTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            InconsistencyType objPojo = daoInconsistencyType.getInconsistencyTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(InconsistencyTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getInconsistencyTypeByID/InconsistencyTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getInconsistencyTypeByID/InconsistencyTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal#createInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createInconsistencyType(InconsistencyTypeVO obj) throws BusinessException {
        log.debug("== Inicia createInconsistencyType/InconsistencyTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	InconsistencyType objPojo = daoInconsistencyType.getInconsistencyTypeByCode(obj.getIncTypeCode());
        	if(objPojo != null){
        		log.debug("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            objPojo =  UtilsBusiness.copyObject(InconsistencyType.class, obj);
            daoInconsistencyType.createInconsistencyType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createInconsistencyType/InconsistencyTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createInconsistencyType/InconsistencyTypeBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal#updateInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateInconsistencyType(InconsistencyTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateInconsistencyType/InconsistencyTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	InconsistencyType objPojo = daoInconsistencyType.getInconsistencyTypeByCode(obj.getIncTypeCode());
        	if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.debug("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            objPojo =  UtilsBusiness.copyObject(InconsistencyType.class, obj);
            daoInconsistencyType.updateInconsistencyType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateInconsistencyType/InconsistencyTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateInconsistencyType/InconsistencyTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal#deleteInconsistencyType(co.com.directv.sdii.model.vo.InconsistencyTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteInconsistencyType(InconsistencyTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteInconsistencyType/InconsistencyTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            InconsistencyType objPojo =  UtilsBusiness.copyObject(InconsistencyType.class, obj);
            daoInconsistencyType.deleteInconsistencyType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteInconsistencyType/InconsistencyTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteInconsistencyType/InconsistencyTypeBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal#getInconsistencyTypeByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public InconsistencyTypeVO getInconsistencyTypeByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getInconsistencyTypeByCode/InconsistencyTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            InconsistencyType objPojo = daoInconsistencyType.getInconsistencyTypeByCode(code);
            if (objPojo == null) {
            	return null;
            }
            return UtilsBusiness.copyObject(InconsistencyTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getInconsistencyTypeByCode/InconsistencyTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getInconsistencyTypeByCode/InconsistencyTypeBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.InconsistencyTypeBusinessBeanLocal#getActiveInconsistencyTypes()
	 */
	@Override
	public List<InconsistencyTypeVO> getActiveInconsistencyTypes()
			throws BusinessException {
		log.debug("== Inicia getActiveInconsistencyTypes/InconsistencyTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoInconsistencyType.getActiveInconsistencyTypes(), InconsistencyTypeVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getActiveInconsistencyTypes/InconsistencyTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveInconsistencyTypes/InconsistencyTypeBusinessBean ==");
        }
	}
}
