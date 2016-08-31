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
import co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareheouseTypeResponse;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD WarehouseType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal
 */
@Stateless(name="WarehouseTypeBusinessBeanLocal",mappedName="ejb/WarehouseTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WarehouseTypeBusinessBean extends BusinessBase implements WarehouseTypeBusinessBeanLocal {

    @EJB(name="WarehouseTypeDAOLocal", beanInterface=WarehouseTypeDAOLocal.class)
    private WarehouseTypeDAOLocal daoWarehouseType;
    
    private final static Logger log = UtilsBusiness.getLog4J(WarehouseTypeBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#getAllWarehouseTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WarehouseTypeVO> getAllWarehouseTypes() throws BusinessException {
        log.debug("== Inicia getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoWarehouseType.getAllWarehouseTypes(), WarehouseTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#getWarehouseTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WarehouseTypeVO getWarehouseTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getWarehouseTypeByID/WarehouseTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            WarehouseType objPojo = daoWarehouseType.getWarehouseTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(WarehouseTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseTypeByID/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseTypeByID/WarehouseTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#createWarehouseType(co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWarehouseType(WarehouseTypeVO obj) throws BusinessException {
        log.debug("== Inicia createWarehouseType/WarehouseTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getWhTypeName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getIsActive(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
            WarehouseType objPojo = this.daoWarehouseType.getWarehouseTypeByName(obj.getWhTypeName().trim().toUpperCase());
            if(objPojo != null){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN334.getCode(), ErrorBusinessMessages.STOCK_IN334.getMessage());
        		//throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
            objPojo =  UtilsBusiness.copyObject(WarehouseType.class, obj);
            objPojo.setWhTypeName(objPojo.getWhTypeName().trim().toUpperCase());
            objPojo.setIsVisible(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_VISIBLE.getCodeEntity());
            objPojo.setIsVirtual(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
            objPojo.setWhTypeCode(UtilsBusiness.currentTimeToString());
            daoWarehouseType.createWarehouseType(objPojo);
            objPojo.setWhTypeCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_CODE_PREFIX.getCodeEntity()+objPojo.getId());
            daoWarehouseType.updateWarehouseType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createWarehouseType/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWarehouseType/WarehouseTypeBusinessBean ==");
        }
		
	}
	
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#updateWarehouseType(co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateWarehouseType(WarehouseTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateWarehouseType/WarehouseTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getWhTypeName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getIsActive(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getWhTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getIsVisible(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	WarehouseType objPojo = this.daoWarehouseType.getWarehouseTypeByCode(obj.getWhTypeCode());
            if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN334.getCode(), ErrorBusinessMessages.STOCK_IN334.getMessage());
        	}
            WarehouseType objPojoTmp = this.daoWarehouseType.getWarehouseTypeByName(obj.getWhTypeName());
            if(objPojoTmp != null && objPojoTmp.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN334.getCode(), ErrorBusinessMessages.STOCK_IN334.getMessage());
        	}
            if(obj.getIsVirtual()==null){
            	obj.setIsVirtual(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
            }
            objPojo =  UtilsBusiness.copyObject(WarehouseType.class, obj);
            daoWarehouseType.updateWarehouseType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateWarehouseType/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWarehouseType/WarehouseTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#deleteWarehouseType(co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteWarehouseType(WarehouseTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteWarehouseType/WarehouseTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            WarehouseType objPojo =  UtilsBusiness.copyObject(WarehouseType.class, obj);
            daoWarehouseType.deleteWarehouseType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteWarehouseType/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWarehouseType/WarehouseTypeBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#getWarehouseTypeByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseTypeVO getWarehouseTypeByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getWarehouseTypeByCode/WarehouseTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            WarehouseType objPojo = daoWarehouseType.getWarehouseTypeByCode(code);
            return UtilsBusiness.copyObject(WarehouseTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseTypeByCode/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseTypeByCode/WarehouseTypeBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#getWarehouseTypeActive()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseTypeVO> getWarehouseTypeActive()
			throws BusinessException {
		log.debug("== Inicia getWarehouseTypeActive/WarehouseTypeBusinessBean ==");
        try {
            List<WarehouseType> listPojo = daoWarehouseType.getWarehouseTypeByStatus(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
            if(listPojo==null){
            	return null;
            }
            return UtilsBusiness.convertList(listPojo, WarehouseTypeVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseTypeActive/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseTypeActive/WarehouseTypeBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#getWarehouseTypeActive(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WareheouseTypeResponse getWarehouseTypeActive(RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getWarehouseTypeActive/WarehouseTypeBusinessBean ==");
		List<WarehouseType> list;
        try {
        	WareheouseTypeResponse response = daoWarehouseType.getWarehouseTypeByStatus(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity(),requestCollInfo);
        	list = response.getWhTypePojo();
        	response.setWhTypeVO(UtilsBusiness.convertList(list, WarehouseTypeVO.class));
            response.setWhTypePojo(null);
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseTypeActive/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseTypeActive/WarehouseTypeBusinessBean ==");
        }
	}
	
	  
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#getAllWarehouseTypes(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WareheouseTypeResponse getAllWarehouseTypes(String code,
				RequestCollectionInfo requestCollInfo)throws BusinessException{
		  log.debug("== Inicia getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
			List<WarehouseType> list;
	        try {
	        	WareheouseTypeResponse response = daoWarehouseType.getAllWarehouseTypes(code,requestCollInfo);
	        	list = response.getWhTypePojo();
	        	response.setWhTypeVO(UtilsBusiness.convertList(list, WarehouseTypeVO.class));
	            response.setWhTypePojo(null);
	            return response;
	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
	        }
	    }


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal#getWarehouseTypeNoActive()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseTypeVO> getWarehouseTypeNoActive()
			throws BusinessException {
		log.debug("== Inicia getWarehouseTypeNoActive/WarehouseTypeBusinessBean ==");
        try {
            List<WarehouseType> listPojo = daoWarehouseType.getWarehouseTypeByStatus(CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_INACTIVE.getCodeEntity());
            if(listPojo==null){
            	return null;
            }
            return UtilsBusiness.convertList(listPojo, WarehouseTypeVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getWarehouseTypeNoActive/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWarehouseTypeNoActive/WarehouseTypeBusinessBean ==");
        }
	}

	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesActiveAndNotVirtual()
			throws BusinessException {
		log.debug("== Inicia getAllWarehouseTypesActiveAndNotVirtual/WarehouseTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoWarehouseType.getAllWarehouseTypesActiveAndNotVirtual(), WarehouseTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllWarehouseTypesActiveAndNotVirtual/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllWarehouseTypesActiveAndNotVirtual/WarehouseTypeBusinessBean ==");
        }
	}
	
	
	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesActive()
			throws BusinessException {
		log.debug("== Inicia getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoWarehouseType.getAllWarehouseTypesActive(), WarehouseTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllWarehouseTypes/WarehouseTypeBusinessBean ==");
        }
	}


	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesByDealerID(Long dealerId)
			throws BusinessException {
		log.debug("== Inicia getAllWarehouseTypesByDealerID/WarehouseTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoWarehouseType.getAllWarehouseTypesByDealerID(dealerId), WarehouseTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllWarehouseTypesByDealerID/WarehouseTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllWarehouseTypesByDealerID/WarehouseTypeBusinessBean ==");
        }
	}
	
	
}
