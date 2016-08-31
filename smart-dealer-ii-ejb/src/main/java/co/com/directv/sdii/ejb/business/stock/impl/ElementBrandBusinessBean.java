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
import co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ElementBrand;
import co.com.directv.sdii.model.pojo.collection.ElementBrandResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementBrandVO;
import co.com.directv.sdii.persistence.dao.stock.ElementBrandDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ElementBrand
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal
 */
@Stateless(name="ElementBrandBusinessBeanLocal",mappedName="ejb/ElementBrandBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementBrandBusinessBean extends BusinessBase implements ElementBrandBusinessBeanLocal {

    @EJB(name="ElementBrandDAOLocal", beanInterface=ElementBrandDAOLocal.class)
    private ElementBrandDAOLocal daoElementBrand;
    
    private final static Logger log = UtilsBusiness.getLog4J(ElementBrandBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#getAllElementBrands()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ElementBrandVO> getAllElementBrands() throws BusinessException {
        log.debug("== Inicia getAllElementBrands/ElementBrandBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementBrand.getAllElementBrands(), ElementBrandVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllElementBrands/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllElementBrands/ElementBrandBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#getElementBrandsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ElementBrandVO getElementBrandByID(Long id) throws BusinessException {
        log.debug("== Inicia getElementBrandByID/ElementBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementBrand objPojo = daoElementBrand.getElementBrandByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ElementBrandVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementBrandByID/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementBrandByID/ElementBrandBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#createElementBrand(co.com.directv.sdii.model.vo.ElementBrandVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createElementBrand(ElementBrandVO obj) throws BusinessException {
        log.debug("== Inicia createElementBrand/ElementBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	//Ya no se busca por código sino por nombre debido a que el código lo genera el sistema
        	//ElementBrand objPojo =  this.daoElementBrand.getElementBrandByCode(obj.getBrandCode());
        	ElementBrand objPojo =  this.daoElementBrand.getElementBrandByName(obj.getBrandName());
            if(objPojo != null){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		//throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        		//gfandino 03/06/2011 Se adiciona por caso de uso INV 108 v2.1
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN342.getCode(), ErrorBusinessMessages.STOCK_IN342.getMessage());
        		
        	}
            objPojo =  UtilsBusiness.copyObject(ElementBrand.class, obj);
            objPojo.setBrandCode(UtilsBusiness.currentTimeToString());
            daoElementBrand.createElementBrand(objPojo);
            objPojo.setBrandCode(CodesBusinessEntityEnum.ELEMENT_BRAND_CODE_PREFIX.getCodeEntity()+objPojo.getId());
            daoElementBrand.updateElementBrand(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createElementBrand/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementBrand/ElementBrandBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#updateElementBrand(co.com.directv.sdii.model.vo.ElementBrandVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateElementBrand(ElementBrandVO obj) throws BusinessException {
        log.debug("== Inicia updateElementBrand/ElementBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getBrandCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getBrandName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
            ElementBrand objPojo =  this.daoElementBrand.getElementBrandByCode(obj.getBrandCode());
            if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN342.getCode(), ErrorBusinessMessages.STOCK_IN342.getMessage());
        	}
            ElementBrand objPojoTmp =  this.daoElementBrand.getElementBrandByName(obj.getBrandName());
            if(objPojoTmp != null && objPojoTmp.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN342.getCode(), ErrorBusinessMessages.STOCK_IN342.getMessage());
        	}
            objPojo =  UtilsBusiness.copyObject(ElementBrand.class, obj);
            daoElementBrand.updateElementBrand(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateElementBrand/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementBrand/ElementBrandBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#deleteElementBrand(co.com.directv.sdii.model.vo.ElementBrandVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteElementBrand(ElementBrandVO obj) throws BusinessException {
        log.debug("== Inicia deleteElementBrand/ElementBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementBrand objPojo =  UtilsBusiness.copyObject(ElementBrand.class, obj);
            daoElementBrand.deleteElementBrand(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteElementBrand/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementBrand/ElementBrandBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#getElementBrandByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementBrandVO getElementBrandByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getElementBrandByCode/ElementBrandBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementBrand objPojo = daoElementBrand.getElementBrandByCode(code);
            return UtilsBusiness.copyObject(ElementBrandVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementBrandByCode/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementBrandByCode/ElementBrandBusinessBean ==");
        }
	}
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#getElementBrandByActiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementBrandVO> getElementBrandByActiveStatus()
			throws BusinessException {
		log.debug("== Inicia getElementBrandByActiveStatus/ElementBrandBusinessBean ==");
        try {
        	List<ElementBrand> tmpList = daoElementBrand.getElementBrandByStatus(CodesBusinessEntityEnum.ELEMENT_BRAND_STATUS_ACTIVE.getCodeEntity());
        	if(tmpList==null){
        		return null;
        	}
        	List<ElementBrandVO> tmpListVO = UtilsBusiness.convertList(tmpList, ElementBrandVO.class);
        	for(ElementBrandVO tmp : tmpListVO){
        		tmp.setElemenBrandNameToPrint(CodesBusinessEntityEnum.ELEMENT_BRAND_CODE_PREFIX.getCodeEntity()+"-"+tmp.getBrandName());
        	}
        	
            return tmpListVO;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementBrandByActiveStatus/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementBrandByActiveStatus/ElementBrandBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#getElementBrandByActiveStatus(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ElementBrandResponse getElementBrandByActiveStatus(RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getElementBrandByActiveStatus/ElementBrandBusinessBean ==");
		List<ElementBrand> tmpList;
        try {
        	ElementBrandResponse response = daoElementBrand.getElementBrandByStatus(CodesBusinessEntityEnum.ELEMENT_BRAND_STATUS_ACTIVE.getCodeEntity(),requestCollInfo); 
        	tmpList = response.getElementBrand();
        	response.setElementBrandVO(UtilsBusiness.convertList(tmpList, ElementBrandVO.class));
        	response.setElementBrand(null);
        	for(ElementBrandVO tmp : response.getElementBrandVO()){
        		tmp.setElemenBrandNameToPrint(CodesBusinessEntityEnum.ELEMENT_BRAND_CODE_PREFIX.getCodeEntity()+"-"+tmp.getBrandName());
        	}
        	
            return response;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementBrandByActiveStatus/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementBrandByActiveStatus/ElementBrandBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementBrandBusinessBeanLocal#getElementBrandByInActiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementBrandVO> getElementBrandByInActiveStatus()
			throws BusinessException {
		log.debug("== Inicia getElementBrandByInActiveStatus/ElementBrandBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementBrand.getElementBrandByStatus(CodesBusinessEntityEnum.ELEMENT_BRAND_STATUS_INACTIVE.getCodeEntity()), ElementBrandVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementBrandByInActiveStatus/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementBrandByInActiveStatus/ElementBrandBusinessBean ==");
        }
	}


	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ElementBrandResponse getElementBrandByAllStatuPage(String code,
			RequestCollectionInfo requestCollInfo)   throws BusinessException{
		log.debug("== Inicia getElementBrandByAllStatuPage/ElementBrandBusinessBean ==");
		List<ElementBrand> tmpList;
        try {
        	ElementBrandResponse response = daoElementBrand.getElementBrandByAllStatuPage(code,requestCollInfo); 
        	tmpList = response.getElementBrand();
        	response.setElementBrandVO(UtilsBusiness.convertList(tmpList, ElementBrandVO.class));
        	response.setElementBrand(null);
        	for(ElementBrandVO tmp : response.getElementBrandVO()){
        		tmp.setElemenBrandNameToPrint(CodesBusinessEntityEnum.ELEMENT_BRAND_CODE_PREFIX.getCodeEntity()+"-"+tmp.getBrandName());
        	}
        	
            return response;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementBrandByAllStatuPage/ElementBrandBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementBrandByAllStatuPage/ElementBrandBusinessBean ==");
        }
	}
}
