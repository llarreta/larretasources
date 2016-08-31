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
import co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ElementTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ElementTypeAndModelDTO;
import co.com.directv.sdii.model.pojo.ElementModel;
import co.com.directv.sdii.model.pojo.collection.ElementModelResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementModelVO;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ElementModel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ElementModelDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal
 */
@Stateless(name="ElementModelBusinessBeanLocal",mappedName="ejb/ElementModelBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementModelBusinessBean extends BusinessBase implements ElementModelBusinessBeanLocal {

    @EJB(name="ElementModelDAOLocal", beanInterface=ElementModelDAOLocal.class)
    private ElementModelDAOLocal daoElementModel;
    
    @EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
    private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
    private ElementDAOLocal daoElement;
    
    @EJB(name = "ElementTypeBusinessBeanLocal", beanInterface = ElementTypeBusinessBeanLocal.class)
	private ElementTypeBusinessBeanLocal elementTypeBusinessBean;
    
    private final static Logger log = UtilsBusiness.getLog4J(ElementModelBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getAllElementModels()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ElementModelResponse getAllElementModels(RequestCollectionInfo requestCollInfo) throws BusinessException {
        log.debug("== Inicia getAllElementModels/ElementModelBusinessBean ==");
        try {
        	ElementModelResponse response = daoElementModel.getAllElementModels(requestCollInfo);
        	List<ElementModel> elements = response.getElementModel();
        	response.setElementModelVO( UtilsBusiness.convertList(elements, ElementModelVO.class ));
        	response.setElementModel(null);
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllElementModels/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllElementModels/ElementModelBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ElementModelVO getElementModelByID(Long id) throws BusinessException {
        log.debug("== Inicia getElementModelByID/ElementModelBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementModel objPojo = daoElementModel.getElementModelByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(ElementModelVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelByID/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelByID/ElementModelBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#createElementModel(co.com.directv.sdii.model.vo.ElementModelVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createElementModel(ElementModelVO obj) throws BusinessException {
        log.debug("== Inicia createElementModel/ElementModelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getElementClass(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getElementClass().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        //UtilsBusiness.assertNotNull(obj.getTechnology(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        //UtilsBusiness.assertNotNull(obj.getTechnology().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getIsPrepaidModel(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
            ElementModel objPojo =  this.daoElementModel.getElementModelByCode(obj.getModelCode());
            if(objPojo != null){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN343.getCode(), ErrorBusinessMessages.STOCK_IN343.getMessage());
        	}
            ElementModel objPojoTmp =  this.daoElementModel.getElementModelByName(obj.getModelName());
            if(objPojoTmp != null){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN344.getCode(), ErrorBusinessMessages.STOCK_IN344.getMessage());
        	}
            
			//Valida que el tipo de elemento no tenga caracteres especiales.
			if (!obj.getModelCode().matches("^[^&<>%$ #]*$")){
				log.error("El codigo del objeto contine caracteres especiales, no permitidos");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN398.getCode(), ErrorBusinessMessages.STOCK_IN398.getMessage());
			}

            
            objPojo =  UtilsBusiness.copyObject(ElementModel.class, obj);
            daoElementModel.createElementModel(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createElementModel/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementModel/ElementModelBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#updateElementModel(co.com.directv.sdii.model.vo.ElementModelVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateElementModel(ElementModelVO obj) throws BusinessException {
        log.debug("== Inicia updateElementModel/ElementModelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getElementClass(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getElementClass().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        //UtilsBusiness.assertNotNull(obj.getTechnology(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        //UtilsBusiness.assertNotNull(obj.getTechnology().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getIsPrepaidModel(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if( areThereElementsOfModelInAnyWarehouse(obj.getId()) ) {				
	        		log.error("No se puede cambiar la clase del modelo, ya que existen elementos relacionados en bodega.");
	        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN429.getCode(), ErrorBusinessMessages.STOCK_IN429.getMessage());				
			}
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	ElementModel objPojo =  this.daoElementModel.getElementModelByCode(obj.getModelCode());
            if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN343.getCode(), ErrorBusinessMessages.STOCK_IN343.getMessage());
        	}
            ElementModel objPojoTmp =  this.daoElementModel.getElementModelByName(obj.getModelName());
            if(objPojoTmp != null && objPojoTmp.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN344.getCode(), ErrorBusinessMessages.STOCK_IN344.getMessage());
        	}
            //Valida que el tipo de elemento no tenga caracteres especiales.
			if (!obj.getModelCode().matches("^[^&<>%$ #]*$")){
				log.error("El codigo del objeto contine caracteres especiales, no permitidos");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN398.getCode(), ErrorBusinessMessages.STOCK_IN398.getMessage());
			}
			
			//si el modelo de elemento es de clase Decodificador o SmartCard y hay elementos relacionados en alguna bodega, no se debe permitir el cambio de clase 
			if( isModelOfClassDecoOrSmartcard(obj) ) {
				
				if( areThereElementsOfModelInAnyWarehouse(obj.getId()) ) {
					
					//cuando se detecta que ha cambiado la clase, se lanza el mensaje de error
					 if( objPojo != null && objPojo.getElementClass() != null && !objPojo.getElementClass().getId().equals(obj.getElementClass().getId()) ) {
		        		log.error("No se puede cambiar la clase del modelo, ya que existen elementos relacionados en bodega.");
		        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN429.getCode(), ErrorBusinessMessages.STOCK_IN429.getMessage());
		        	}
					
				}
				
			}
			
            objPojo =  UtilsBusiness.copyObject(ElementModel.class, obj);
            daoElementModel.updateElementModel(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateElementModel/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementModel/ElementModelBusinessBean ==");
        }
		
	}

	/**
	 * Metodo: indica si existen elementos en cualquier bodega que correspondan al modelo de elemento especificado
	 * @author wjimenez
	 */
	private boolean areThereElementsOfModelInAnyWarehouse(Long elementModelId) throws DAOServiceException, DAOSQLException {
		Long quantity = daoElement.countElementByElementModel(elementModelId);
		return quantity > 0;
	}

	/**
	 * Metodo: indica si el modelo de elemento pertenece a la clase de elemento Decodificador o Smartcard
	 * @author wjimenez
	 */
	private boolean isModelOfClassDecoOrSmartcard(ElementModelVO elementModelVO) throws PropertiesException {
		boolean isModelOfClassDecoOrSmartcard = false;
		if(elementModelVO != null && elementModelVO.getElementClass() != null
				&& elementModelVO.getElementClass().getElementClassCode() != null ) {
		
			isModelOfClassDecoOrSmartcard =
				elementModelVO.getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())
				|| elementModelVO.getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity());
			
		}
		
		return isModelOfClassDecoOrSmartcard;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#deleteElementModel(co.com.directv.sdii.model.vo.ElementModelVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteElementModel(ElementModelVO obj) throws BusinessException {
        log.debug("== Inicia deleteElementModel/ElementModelBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementModel objPojo =  UtilsBusiness.copyObject(ElementModel.class, obj);
            daoElementModel.deleteElementModel(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteElementModel/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementModel/ElementModelBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementModelVO getElementModelByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getElementModelByCode/ElementModelBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ElementModel objPojo = daoElementModel.getElementModelByCode(code);
            return UtilsBusiness.copyObject(ElementModelVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelByCode/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelByCode/ElementModelBusinessBean ==");
        }
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByActiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementModelVO> getElementModelsByActiveStatus()
			throws BusinessException {
		log.debug("== Inicia getElementModelsByActiveStatus/ElementModelBusinessBean ==");
        try {
            
        	List<ElementModelVO> tempListVO = UtilsBusiness.convertList(daoElementModel.getElementModelsByStatus(CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_ACTIVE.getCodeEntity()), ElementModelVO.class);
        	
        	for(ElementModelVO tmp : tempListVO){
        		tmp.setCodeNameToPrint(tmp.getModelCode()+"-"+tmp.getModelName());
        	}
            return tempListVO;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByActiveStatus/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByActiveStatus/ElementModelBusinessBean ==");
        }
	}


	 /* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByInActiveStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementModelVO> getElementModelsByInActiveStatus()
			throws BusinessException {
		 log.debug("== Inicia getElementModelsByInActiveStatus/ElementModelBusinessBean ==");
	        try {
	            List<ElementModelVO> tempListVO = UtilsBusiness.convertList(daoElementModel.getElementModelsByStatus(CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_INACTIVE.getCodeEntity()), ElementModelVO.class);
	        	
	        	for(ElementModelVO tmp : tempListVO){
	        		tmp.setCodeNameToPrint(tmp.getModelCode()+"-"+tmp.getModelName());
	        	}
	            return tempListVO;

	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación getElementModelsByInActiveStatus/ElementModelBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getElementModelsByInActiveStatus/ElementModelBusinessBean ==");
	        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByElementClass(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementModelVO> getElementModelsByElementClass(
			String codeElementClass) throws BusinessException {
		log.debug("== Inicia getElementModelsByElementClass/ElementModelBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementModel.getElementModelByElementClass(codeElementClass), ElementModelVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByElementClass/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByElementClass/ElementModelBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByElementClassAndActiveStatus(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementModelVO> getElementModelsByElementClassAndActiveStatus(
			String codeElementClass) throws BusinessException {
		log.debug("== Inicia getElementModelsByElementClassAndActiveStatus/ElementModelBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementModel.getElementModelByElementClassAndStatus(codeElementClass,CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_ACTIVE.getCodeEntity()), ElementModelVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByElementClassAndActiveStatus/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByElementClassAndActiveStatus/ElementModelBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByElementClassAndInactiveStatus(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementModelVO> getElementModelsByElementClassAndInactiveStatus(
			String codeElementClass) throws BusinessException {
		log.debug("== Inicia getElementModelsByElementClassAndInactiveStatus/ElementModelBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementModel.getElementModelByElementClassAndStatus(codeElementClass,CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_INACTIVE.getCodeEntity()), ElementModelVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByElementClassAndInactiveStatus/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByElementClassAndInactiveStatus/ElementModelBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByElementClassId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementModelVO> getElementModelsByElementClassId(
			Long idElementClass) throws BusinessException {
		log.debug("== Inicia getElementModelsByElementClassId/ElementModelBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementModel.getElementModelByElementClassId(idElementClass), ElementModelVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByElementClassId/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByElementClassId/ElementModelBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByElementClassIdAndActiveStatus(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementModelVO> getElementModelsByElementClassIdAndActiveStatus(
			Long idElementClass) throws BusinessException {
		log.debug("== Inicia getElementModelsByElementClassIdAndActiveStatus/ElementModelBusinessBean ==");
        try {
        	List<ElementModel> tmpList = daoElementModel.getElementModelByElementClassIdAndStatus(idElementClass,CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_ACTIVE.getCodeEntity());
        	if(tmpList==null){
        		return null;
        	}
        	List<ElementModelVO> tmpListVO = UtilsBusiness.convertList(tmpList, ElementModelVO.class);
        	for(ElementModelVO tmp:tmpListVO){
        		tmp.setCodeNameToPrint(tmp.getModelCode()+"-"+tmp.getModelName());
        	}
            return tmpListVO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByElementClassIdAndActiveStatus/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByElementClassIdAndActiveStatus/ElementModelBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByElementClassIdAndActiveStatus(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ElementModelResponse getElementModelsByElementClassIdAndActiveStatus(
			Long idElementClass,RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getElementModelsByElementClassIdAndActiveStatus/ElementModelBusinessBean ==");
		List<ElementModel> tmpList;
        try {
        	ElementModelResponse response = daoElementModel.getElementModelByElementClassIdAndStatus(idElementClass,CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_ACTIVE.getCodeEntity(),requestCollInfo); 
        	tmpList = response.getElementModel();
        	response.setElementModelVO(UtilsBusiness.convertList(tmpList, ElementModelVO.class));
        	response.setElementModel(null);
        	
        	for(ElementModelVO tmp:response.getElementModelVO()){
        		tmp.setCodeNameToPrint(tmp.getModelCode()+"-"+tmp.getModelName());
        	}
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByElementClassIdAndActiveStatus/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByElementClassIdAndActiveStatus/ElementModelBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByElementClassIdAndInactiveStatus(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ElementModelVO> getElementModelsByElementClassIdAndInactiveStatus(
			Long idElementClass) throws BusinessException {
		log.debug("== Inicia getElementModelsByElementClassIdAndInactiveStatus/ElementModelBusinessBean ==");
        try {
        	List<ElementModel> tmpList = daoElementModel.getElementModelByElementClassIdAndStatus(idElementClass,CodesBusinessEntityEnum.ELEMENT_MODEL_STATUS_INACTIVE.getCodeEntity());
        	
        	if(tmpList==null){
        		return null;
        	}
        	List<ElementModelVO> tmpListVO = UtilsBusiness.convertList(tmpList, ElementModelVO.class);
        	for(ElementModelVO tmp:tmpListVO){
        		tmp.setCodeNameToPrint(tmp.getModelCode()+"-"+tmp.getModelName());
        	}
            return tmpListVO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByElementClassIdAndInactiveStatus/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByElementClassIdAndInactiveStatus/ElementModelBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ElementModelBusinessBeanLocal#getElementModelsByElementClassIdAndAllStatusPage(java.lang.Long, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ElementModelResponse getElementModelsByElementClassIdAndAllStatusPage(
			Long idElementClass, String code,RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getElementModelsByElementClassIdAndAllStatusPage/ElementModelBusinessBean ==");
		List<ElementModel> tmpList;
        try {
        	ElementModelResponse response = daoElementModel.getElementModelsByElementClassIdAndAllStatusPage(idElementClass,code,requestCollInfo); 
        	tmpList = response.getElementModel();
        	response.setElementModelVO(UtilsBusiness.convertList(tmpList, ElementModelVO.class));
        	response.setElementModel(null);
        	
        	for(ElementModelVO tmp:response.getElementModelVO()){
        		tmp.setCodeNameToPrint(tmp.getModelCode()+"-"+tmp.getModelName());
        	}
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByElementClassIdAndAllStatusPage/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByElementClassIdAndAllStatusPage/ElementModelBusinessBean ==");
        }
	}


	@Override
	public List<ElementModelVO> getElementModelsByActiveStatusAndIsPrepaid(
			String isPrepaid) throws BusinessException {
		log.debug("== Inicia getElementModelsByActiveStatusAndIsPrepaid/ElementModelBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoElementModel.getElementModelsByActiveStatusAndIsPrepaid(isPrepaid), ElementModelVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByActiveStatusAndIsPrepaid/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByActiveStatusAndIsPrepaid/ElementModelBusinessBean ==");
        }
	}
	
	@Override
	public ElementTypeAndModelDTO getElementModelsByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId) throws BusinessException {
		log.debug("== Inicia getElementModelsByWarehouseIdAndElementModelId/ElementModelBusinessBean ==");
        try {
        	
        	ElementTypeAndModelDTO elementTypeAndModelDTO = new ElementTypeAndModelDTO();
        	elementTypeAndModelDTO.setElementModelVO(UtilsBusiness.convertList(daoElementModel.getElementModelsByWarehouseIdAndElementModelId(warehouseId,elementModelId), ElementModelVO.class));
        	for(ElementModelVO tmp:elementTypeAndModelDTO.getElementModelVO()){
        		tmp.setCodeNameToPrint(tmp.getModelCode()+"-"+tmp.getModelName());
        	}
        	elementTypeAndModelDTO.setElementTypeVO(elementTypeBusinessBean.getElementTypeByWarehouseIdAndElementModelId(warehouseId,elementModelId));

        	return elementTypeAndModelDTO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getElementModelsByWarehouseIdAndElementModelId/ElementModelBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementModelsByWarehouseIdAndElementModelId/ElementModelBusinessBean ==");
        }
	}
	
	
	
}
