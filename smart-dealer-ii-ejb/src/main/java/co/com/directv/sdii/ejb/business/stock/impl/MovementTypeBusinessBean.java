package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.MovementTypeClassDTO;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MovementTypeVO;
import co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD MovementType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal
 */
@Stateless(name="MovementTypeBusinessBeanLocal",mappedName="ejb/MovementTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovementTypeBusinessBean extends BusinessBase implements MovementTypeBusinessBeanLocal {

    @EJB(name="MovementTypeDAOLocal", beanInterface=MovementTypeDAOLocal.class)
    private MovementTypeDAOLocal daoMovementType;
    
    private final static Logger log = UtilsBusiness.getLog4J(MovementTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#getAllMovementTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MovementTypeVO> getAllMovementTypes() throws BusinessException {
        log.debug("== Inicia getAllMovementTypes/MovementTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoMovementType.getAllMovementTypes(), MovementTypeVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllMovementTypes/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMovementTypes/MovementTypeBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#getAllMovementTypesClass()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MovementTypeClassDTO> getAllMovementTypesClass() throws BusinessException {
        log.debug("== Inicia getAllMovementTypesClass/MovementTypeBusinessBean ==");
        try {
            return daoMovementType.getAllMovementTypesClass();

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllMovementTypesClass/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMovementTypesClass/MovementTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#getMovementTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public MovementTypeVO getMovementTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getMovementTypeByID/MovementTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MovementType objPojo = daoMovementType.getMovementTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(MovementTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getMovementTypeByID/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMovementTypeByID/MovementTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#createMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMovementType(MovementTypeVO obj) throws BusinessException {
        log.debug("== Inicia createMovementType/MovementTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getMovTypeName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	//MovementType objPojo = daoMovementType.getMovementTypeByCode(obj.getMovTypeCode());
        	obj.setMovTypeName( obj.getMovTypeName().trim() );
        	MovementType objPojo = daoMovementType.getMovementTypeByName(obj.getMovTypeName());
        	if(objPojo != null){
        		log.debug("El objeto que intenta crear como nuevo ya existe");
        		//throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        		//gfandino-02/06/2011 Se adiciona por INV106 v2.3
            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN340.getCode(),ErrorBusinessMessages.STOCK_IN340.getMessage());
        	}
        	
        	objPojo =  UtilsBusiness.copyObject(MovementType.class, obj);
        	objPojo.setMovTypeCode(UtilsBusiness.currentTimeToString());
        	objPojo.setMovTypeAutomatic(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTOMATIC_NO.getCodeEntity());
            daoMovementType.createMovementType(objPojo);
            objPojo.setMovTypeCode(CodesBusinessEntityEnum.MOVEMENT_TYPE_CODE_PREFIX.getCodeEntity()+objPojo.getId());
            daoMovementType.updateMovementType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createMovementType/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMovementType/MovementTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#updateMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateMovementType(MovementTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateMovementType/MovementTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	MovementType objPojo = daoMovementType.getMovementTypeByCode(obj.getMovTypeCode());
        	if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){        		
        		//throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        		//gfandino-02/06/2011 Se adiciona por INV106 v2.3
            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN340.getCode(),ErrorBusinessMessages.STOCK_IN340.getMessage());
        	}
        	MovementType objPojoTmp = daoMovementType.getMovementTypeByName(obj.getMovTypeName());
        	if(objPojoTmp != null && objPojoTmp.getId().longValue() != obj.getId().longValue()){        		
        		//gfandino-02/06/2011 Se adiciona por INV106 v2.3
            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN340.getCode(),ErrorBusinessMessages.STOCK_IN340.getMessage());
        	}
        	
        	
            objPojo =  UtilsBusiness.copyObject(MovementType.class, obj);
            objPojo.setMovTypeName( objPojo.getMovTypeName().trim() );
            daoMovementType.updateMovementType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateMovementType/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMovementType/MovementTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#deleteMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMovementType(MovementTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteMovementType/MovementTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MovementType objPojo =  UtilsBusiness.copyObject(MovementType.class, obj);
            daoMovementType.deleteMovementType(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteMovementType/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMovementType/MovementTypeBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#getMovementTypeByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementTypeVO getMovementTypeByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getMovementTypeByCode/MovementTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MovementType objPojo = daoMovementType.getMovementTypeByCode(code);
            if (objPojo == null) {
            	return null;
            }
            return UtilsBusiness.copyObject(MovementTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getMovementTypeByCode/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMovementTypeByCode/MovementTypeBusinessBean ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#getMovementTypesActiveStatus()
	 */
	
	public List<MovementTypeVO> getMovementTypesActiveStatus()
			throws BusinessException {
		log.debug("== Inicia getMovementTypesActiveStatus/MovementTypeBusinessBean ==");
        try {
        	List<MovementType> tmp = daoMovementType.getMovementTypesStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_STATUS_ACTIVE.getCodeEntity());
        	if(tmp==null){
        		return null;
        	}
        	List<MovementTypeVO> movTypeVO = UtilsBusiness.convertList(tmp, MovementTypeVO.class); 
        	for(MovementTypeVO tmpVO : movTypeVO){
        		tmpVO.setMovTypeToPrint(tmpVO.getMovTypeCode()+"-"+tmpVO.getMovTypeName());
        	}
            return movTypeVO;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getMovementTypesActiveStatus/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMovementTypesActiveStatus/MovementTypeBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#getMovementTypesActiveStatus(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public MovementTypeResponse getMovementTypesActiveStatus(RequestCollectionInfo requestCollInfo) throws BusinessException{
		log.debug("== Inicia getMovementTypesActiveStatus/MovementTypeBusinessBean ==");
		List<MovementType> list;
        try {
        	MovementTypeResponse response = daoMovementType.getMovementTypesStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_STATUS_ACTIVE.getCodeEntity(),requestCollInfo); 
        	list = response.getMovType();
        	response.setMovTypeVO( UtilsBusiness.convertList(list, MovementTypeVO.class));
        	for(MovementTypeVO tmpVO : response.getMovTypeVO()){
        		tmpVO.setMovTypeToPrint(tmpVO.getMovTypeCode()+"-"+tmpVO.getMovTypeName());
        	}
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getMovementTypesActiveStatus/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMovementTypesActiveStatus/MovementTypeBusinessBean ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#getMovementTypesInActiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MovementTypeVO> getMovementTypesInActiveStatus()
			throws BusinessException {
		log.debug("== Inicia getMovementTypesInActiveStatus/MovementTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoMovementType.getMovementTypesStatus(CodesBusinessEntityEnum.MOVEMENT_TYPE_STATUS_INACTIVE.getCodeEntity()), MovementTypeVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getMovementTypesInActiveStatus/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMovementTypesInActiveStatus/MovementTypeBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal#getMovementTypesAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovementTypeResponse getMovementTypesAllStatusPage(String code,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getMovementTypesAllStatusPage/MovementTypeBusinessBean ==");
		List<MovementType> list;
        try {
        	MovementTypeResponse response = daoMovementType.getMovementTypesAllStatusPage(code,requestCollInfo); 
        	list = response.getMovType();
        	response.setMovTypeVO( UtilsBusiness.convertList(list, MovementTypeVO.class));
        	for(MovementTypeVO tmpVO : response.getMovTypeVO()){
        		tmpVO.setMovTypeToPrint(tmpVO.getMovTypeCode()+"-"+tmpVO.getMovTypeName());
        	}
            return response;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getMovementTypesAllStatusPage/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMovementTypesAllStatusPage/MovementTypeBusinessBean ==");
        }
	}


	@Override
	public List<MovementTypeVO> getMovementTypesAtiveByClass(String moveClass)
			throws BusinessException {
		log.debug("== Inicia getMovementTypesAtiveByClass/MovementTypeBusinessBean ==");

        try {
            return UtilsBusiness.convertList(daoMovementType.getMovementTypesAtiveByClass(moveClass), MovementTypeVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllMovementTypes/MovementTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMovementTypesAtiveByClass/MovementTypeBusinessBean ==");
        }
	}
	
}
