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
import co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ElementClass;
import co.com.directv.sdii.model.pojo.ElementModel;
import co.com.directv.sdii.model.pojo.collection.ElementClassResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementClassVO;
import co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ElementClass
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal
 */
@Stateless(name="ElementClassBusinessBeanLocal",mappedName="ejb/ElementClassBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementClassBusinessBean extends BusinessBase implements ElementClassBusinessBeanLocal {

    @EJB(name="ElementClassDAOLocal", beanInterface=ElementClassDAOLocal.class)
    private ElementClassDAOLocal daoElementClass;
    
    @EJB(name="ElementModelDAOLocal", beanInterface=ElementModelDAOLocal.class)
    private ElementModelDAOLocal daoElementModel;
    
    private final static Logger log = UtilsBusiness.getLog4J(ElementClassBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#getAllElementClasss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ElementClassVO> getAllElementClasss() throws BusinessException {
        log.debug("== Inicia getAllElementClasss/ElementClassBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementClass.getAllElementClasss(), ElementClassVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllElementClasss/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllElementClasss/ElementClassBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#getElementClasssByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ElementClassVO getElementClassByID(Long id) throws BusinessException {
        log.debug("== Inicia getElementClassByID/ElementClassBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementClass objPojo = daoElementClass.getElementClassByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ElementClassVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementClassByID/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementClassByID/ElementClassBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#createElementClass(co.com.directv.sdii.model.vo.ElementClassVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createElementClass(ElementClassVO obj) throws BusinessException {
        log.debug("== Inicia createElementClass/ElementClassBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
            //ElementClass objPojo =  this.daoElementClass.getElementClassByCode(obj.getElementClassCode());
            ElementClass objPojo =  this.daoElementClass.getElementClassByName(obj.getElementClassName());
            if(objPojo != null){
            	log.error("El objeto que intenta crear como nuevo ya existe");
        		//throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
            	//gfandino 03/06/2011 Se cambia mensaje de error po CU INV 107 V 2.1
            	throw new BusinessException(ErrorBusinessMessages.STOCK_IN341.getCode(), ErrorBusinessMessages.STOCK_IN341.getMessage());
            	
            }
            objPojo =  UtilsBusiness.copyObject(ElementClass.class, obj);
            //Para poder ingresar un código único temporal, se ingresa la horaminutosegundomilisegundo actual
            objPojo.setElementClassCode(UtilsBusiness.currentTimeToString());
            daoElementClass.createElementClass(objPojo);
            objPojo.setElementClassCode(CodesBusinessEntityEnum.ELEMENT_CLASS_CODE_PREFIX.getCodeEntity()+objPojo.getId());
            daoElementClass.updateElementClass(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createElementClass/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementClass/ElementClassBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#updateElementClass(co.com.directv.sdii.model.vo.ElementClassVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateElementClass(ElementClassVO obj) throws BusinessException {
        log.debug("== Inicia updateElementClass/ElementClassBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	ElementClass objPojo =  this.daoElementClass.getElementClassByCode(obj.getElementClassCode());
            if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
            	log.error("El objeto que intenta actualizar ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN341.getCode(), ErrorBusinessMessages.STOCK_IN341.getMessage());
            }
            ElementClass objPojoTmp =  this.daoElementClass.getElementClassByName(obj.getElementClassName());
            if(objPojoTmp != null && objPojoTmp.getId().longValue() != obj.getId().longValue()){
            	log.error("El objeto que intenta actualizar ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN341.getCode(), ErrorBusinessMessages.STOCK_IN341.getMessage());
            }
            
            //Validar que la clase se pueda inactivar solo si no esta en uso
            if(obj.getIsActive().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_STATUS_INACTIVE.getCodeEntity())){
            	List<ElementModel> listElementModel = daoElementModel.getElementModelByElementClassIdAndStatus(obj.getId(), CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_ACTIVE.getCodeEntity());
                if(listElementModel.size()>0){
                	throw new BusinessException(ErrorBusinessMessages.STOCK_IN425.getCode(), ErrorBusinessMessages.STOCK_IN425.getMessage());
                }
            }
            
            objPojo =  UtilsBusiness.copyObject(ElementClass.class, obj);
            daoElementClass.updateElementClass(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateElementClass/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementClass/ElementClassBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#deleteElementClass(co.com.directv.sdii.model.vo.ElementClassVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteElementClass(ElementClassVO obj) throws BusinessException {
        log.debug("== Inicia deleteElementClass/ElementClassBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementClass objPojo =  UtilsBusiness.copyObject(ElementClass.class, obj);
            daoElementClass.deleteElementClass(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteElementClass/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementClass/ElementClassBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#getElementClassByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClassVO getElementClassByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getElementClassByCode/ElementClassBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementClass objPojo = daoElementClass.getElementClassByCode(code);
            return UtilsBusiness.copyObject(ElementClassVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementClassByID/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementClassByCode/ElementClassBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#getElementClassByActiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementClassVO> getElementClassByActiveStatus()
			throws BusinessException {
		log.debug("== Inicia getElementClassByActiveStatus/ElementClassBusinessBean ==");
        try {
        	List<ElementClass> tmpList = daoElementClass.getElementClassByStatus(CodesBusinessEntityEnum.ELEMENT_CLASS_STATUS_ACTIVE.getCodeEntity());
        	if(tmpList == null){
        		return null;
        	}
        	List<ElementClassVO> tmpListVO = UtilsBusiness.convertList(tmpList, ElementClassVO.class);
        	for(ElementClassVO tmp: tmpListVO){
        		tmp.setElementClassNameToPrint(tmp.getElementClassCode()+"-"+tmp.getElementClassName());
        	}
            return tmpListVO;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementClassByActiveStatus/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementClassByActiveStatus/ElementClassBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#getElementClassByActiveStatus(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClassResponse getElementClassByActiveStatus(RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getElementClassByActiveStatus/ElementClassBusinessBean ==");
		List<ElementClass> tmpList;
        try {
        	
        	ElementClassResponse response = daoElementClass.getElementClassByStatus(CodesBusinessEntityEnum.ELEMENT_CLASS_STATUS_ACTIVE.getCodeEntity(),requestCollInfo);
        	tmpList = response.getElementClass();
        	response.setElementClassVO(UtilsBusiness.convertList(tmpList, ElementClassVO.class));
        	response.setElementClass(null);
        	for(ElementClassVO tmp: response.getElementClassVO()){
        		tmp.setElementClassNameToPrint(tmp.getElementClassCode()+"-"+tmp.getElementClassName());
        	}
            return response;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementClassByActiveStatus/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementClassByActiveStatus/ElementClassBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#getElementClassByInactiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementClassVO> getElementClassByInactiveStatus()
			throws BusinessException {
		log.debug("== Inicia getElementClassByInactiveStatus/ElementClassBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementClass.getElementClassByStatus(CodesBusinessEntityEnum.ELEMENT_CLASS_STATUS_INACTIVE.getCodeEntity()), ElementClassVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementClassByInactiveStatus/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementClassByInactiveStatus/ElementClassBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#getElementClassByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClassResponse getElementClassByAllStatusPage(String code,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getElementClassByAllStatusPage/ElementClassBusinessBean ==");
		List<ElementClass> tmpList;
        try {
        	
        	ElementClassResponse response = daoElementClass.getElementClassByAllStatusPage(code,requestCollInfo);
        	tmpList = response.getElementClass();
        	response.setElementClassVO(UtilsBusiness.convertList(tmpList, ElementClassVO.class));
        	response.setElementClass(null);
        	for(ElementClassVO tmp: response.getElementClassVO()){
        		tmp.setElementClassNameToPrint(tmp.getElementClassCode()+"-"+tmp.getElementClassName());
        	}
            return response;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementClassByAllStatusPage/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementClassByAllStatusPage/ElementClassBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementClassBusinessBeanLocal#getElementClassByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClass getElementClassByElementSerialNumber(String serialNumber) throws BusinessException {
		log.debug("== Inicia getElementClassByElementSerialNumber/ElementClassBusinessBean ==");
		try {
        	
        	ElementClass elementClass = daoElementClass.getElementClassByElementSerialNumber(serialNumber);
        	
            return elementClass;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementClassByElementSerialNumber/ElementClassBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementClassByElementSerialNumber/ElementClassBusinessBean ==");
        }
	}
	
}
