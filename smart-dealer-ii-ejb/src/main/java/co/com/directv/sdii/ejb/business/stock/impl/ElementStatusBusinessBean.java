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
import co.com.directv.sdii.ejb.business.stock.ElementStatusBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.ElementStatusVO;
import co.com.directv.sdii.persistence.dao.stock.ElementStatusDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ElementStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ElementStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ElementStatusBusinessBeanLocal
 */
@Stateless(name="ElementStatusBusinessBeanLocal",mappedName="ejb/ElementStatusBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementStatusBusinessBean extends BusinessBase implements ElementStatusBusinessBeanLocal {

    @EJB(name="ElementStatusDAOLocal", beanInterface=ElementStatusDAOLocal.class)
    private ElementStatusDAOLocal daoElementStatus;
    
    @EJB(name="WarehouseBusinessBeanLocal", beanInterface= WarehouseBusinessBeanLocal.class)
    private WarehouseBusinessBeanLocal businessWarehouse;
    
    private final static Logger log = UtilsBusiness.getLog4J(ElementStatusBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementStatusBusinessBeanLocal#getAllElementStatuss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ElementStatusVO> getAllElementStatuss() throws BusinessException {
        log.debug("== Inicia getAllElementStatuss/ElementStatusBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementStatus.getAllElementStatuss(), ElementStatusVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllElementStatuss/ElementStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllElementStatuss/ElementStatusBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementStatusBusinessBeanLocal#getElementStatussByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ElementStatusVO getElementStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getElementStatusByID/ElementStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementStatus objPojo = daoElementStatus.getElementStatusByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ElementStatusVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getElementStatusByID/ElementStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementStatusByID/ElementStatusBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementStatusBusinessBeanLocal#createElementStatus(co.com.directv.sdii.model.vo.ElementStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createElementStatus(ElementStatusVO obj) throws BusinessException {
        log.debug("== Inicia createElementStatus/ElementStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
            ElementStatus objPojo =  this.daoElementStatus.getElementStatusByCode(obj.getElementStatusCode());
            if(objPojo != null){
        		log.debug("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
            objPojo =  UtilsBusiness.copyObject(ElementStatus.class, obj);
            daoElementStatus.createElementStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createElementStatus/ElementStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementStatus/ElementStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementStatusBusinessBeanLocal#updateElementStatus(co.com.directv.sdii.model.vo.ElementStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateElementStatus(ElementStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateElementStatus/ElementStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	 ElementStatus objPojo =  this.daoElementStatus.getElementStatusByCode(obj.getElementStatusCode());
             if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
         		log.debug("El objeto que intenta crear como nuevo ya existe");
         		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
         	}
             objPojo =  UtilsBusiness.copyObject(ElementStatus.class, obj);
            daoElementStatus.updateElementStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateElementStatus/ElementStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementStatus/ElementStatusBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementStatusBusinessBeanLocal#deleteElementStatus(co.com.directv.sdii.model.vo.ElementStatusVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteElementStatus(ElementStatusVO obj) throws BusinessException {
        log.debug("== Inicia deleteElementStatus/ElementStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementStatus objPojo =  UtilsBusiness.copyObject(ElementStatus.class, obj);
            daoElementStatus.deleteElementStatus(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteElementStatus/ElementStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementStatus/ElementStatusBusinessBean ==");
        }
	}
	
	@Override
	public ElementStatus estadoElementoSegunBodegaDestino (Warehouse warehouse) throws BusinessException{
		log.debug("== Inicia estadoElementoSegunBodegaDestino/ElementStatusBusinessBean ==");
        UtilsBusiness.assertNotNull(warehouse, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(warehouse.getWarehouseType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
			
			String strElementStatus = null;
			if ( warehouse.getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES.getCodeEntity() ) ){
				strElementStatus = CodesBusinessEntityEnum.ELEMENT_STATUS_S01.getCodeEntity();
			}else{
				if ( warehouse.getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity() ) ){
					strElementStatus = CodesBusinessEntityEnum.ELEMENT_STATUS_S02.getCodeEntity();
				}else{
					if ( warehouse.getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity() ) ){
						strElementStatus = CodesBusinessEntityEnum.ELEMENT_STATUS_S04.getCodeEntity();
					}else{
						if ( warehouse.getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S05.getCodeEntity() ) ){
							strElementStatus = CodesBusinessEntityEnum.ELEMENT_STATUS_S05.getCodeEntity();
						}else{
							if ( warehouse.getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WAREHOUSE_TYPE_S06.getCodeEntity() ) ){
								strElementStatus = CodesBusinessEntityEnum.ELEMENT_STATUS_S06.getCodeEntity();
							}else{
								if ( warehouse.getWarehouseType().getWhTypeCode().equals( CodesBusinessEntityEnum.WAREHOUSE_TYPE_DEVOLUCIONES.getCodeEntity() ) ){
									strElementStatus = CodesBusinessEntityEnum.ELEMENT_STATUS_D03.getCodeEntity();
								}
							}
						}
					}
				}
			}
			
			ElementStatus elementStatus = daoElementStatus.getElementStatusByCode(strElementStatus);
			if ( elementStatus != null ){
				return elementStatus;
			}
			return null;
			
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación estadoElementoSegunBodegaDestino/ElementStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina estadoElementoSegunBodegaDestino/ElementStatusBusinessBean ==");
        }
	}
}
