package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.UploadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ConfirmSerializedElementItemFromImportLogDTO;
import co.com.directv.sdii.model.dto.FilterUploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.MassiveMovementBetweenWareHouseDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogItemDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.UploadFileParamByFileTypeParamNameAndCodeDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.ImpLogConfirmation;
import co.com.directv.sdii.model.pojo.ImpLogModification;
import co.com.directv.sdii.model.pojo.ImpLogModificationType;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ImportLogStatus;
import co.com.directv.sdii.model.pojo.InconsistencyStatus;
import co.com.directv.sdii.model.pojo.InconsistencyType;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.MeasureUnit;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImpLogModificationDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogInconsistencyDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.InconsistencyTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseTypeDAOLocal;
import co.com.directv.sdii.reports.dto.SerializedElementImportLogDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD ImportLogItem
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal
 */
@Stateless(name="ImportLogItemBusinessBeanLocal",mappedName="ejb/ImportLogItemBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImportLogItemBusinessBean extends BusinessBase implements ImportLogItemBusinessBeanLocal {

    @EJB(name="ImportLogItemDAOLocal", beanInterface=ImportLogItemDAOLocal.class)
    private ImportLogItemDAOLocal daoImportLogItem;
    
    @EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
    private ElementDAOLocal daoElement;
    
    @EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal daoElementType;
    
    @EJB(name="ImpLogConfirmationDAOLocal", beanInterface=ImpLogConfirmationDAOLocal.class)
    private ImpLogConfirmationDAOLocal daoImpLogConfirmation;
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
    
    @EJB(name="ItemStatusDAOLocal", beanInterface=ItemStatusDAOLocal.class)
    private ItemStatusDAOLocal daoItemStatus;
    
    @EJB(name="ImportLogStatusDAOLocal", beanInterface=ImportLogStatusDAOLocal.class)
    private ImportLogStatusDAOLocal daoImportLogStatus;
    
    @EJB(name="ImportLogDAOLocal", beanInterface=ImportLogDAOLocal.class)
    private ImportLogDAOLocal daoImportLog;
    
    @EJB(name="ImportLogInconsistencyDAOLocal", beanInterface=ImportLogInconsistencyDAOLocal.class)
    private ImportLogInconsistencyDAOLocal daoImportLogInconsistency;
    
    @EJB(name="InconsistencyTypeDAOLocal", beanInterface=InconsistencyTypeDAOLocal.class)
    private InconsistencyTypeDAOLocal daoInconsistencyType;
    
    @EJB(name="InconsistencyStatusDAOLocal", beanInterface=InconsistencyStatusDAOLocal.class)
    private InconsistencyStatusDAOLocal daoInconsistencyStatus;
    
    @EJB(name="ImpLogModificationDAOLocal", beanInterface=ImpLogModificationDAOLocal.class)
    private ImpLogModificationDAOLocal daoLogImpModification;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
    
    @EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
    private NotSerializedDAOLocal daoNotSerialized;
    
    @EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
    
    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal daoDealer;
    
    @EJB(name = "WarehouseDAOLocal", beanInterface = WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWareHouse;

    @EJB(name = "WarehouseElementBusinessBeanLocal", beanInterface = WarehouseElementBusinessBeanLocal.class)
	private WarehouseElementBusinessBeanLocal businessWareHouseElement;
    
    @EJB(name="ElementStatusDAOLocal",beanInterface=ElementStatusDAOLocal.class)
	private ElementStatusDAOLocal daoElementStatus;
    
    @EJB(name="ImportLogBusinessBeanLocal", beanInterface=ImportLogBusinessBeanLocal.class)
    private ImportLogBusinessBeanLocal businessImportLog;
    
    @EJB(name="ImpLogConfirmationBusinessBeanLocal", beanInterface=ImpLogConfirmationBusinessBeanLocal.class)
    private ImpLogConfirmationBusinessBeanLocal businessImpLogConfirmation;
    
    @EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name="MovCmdQueueDAOLocal", beanInterface=MovCmdQueueDAOLocal.class)
	private MovCmdQueueDAOLocal daoMovCmdQueue;
    
    @EJB(name="WarehouseTypeDAOLocal", beanInterface=WarehouseTypeDAOLocal.class)
    private WarehouseTypeDAOLocal daoWarehouseType;
    
    @EJB(name="UploadFileBusinessBeanLocal",beanInterface=UploadFileBusinessBeanLocal.class)
	private UploadFileBusinessBeanLocal businessUploadFile;
    
    @EJB(name="ImpLogModificationBusinessBeanLocal", beanInterface=ImpLogModificationBusinessBeanLocal.class)
	private ImpLogModificationBusinessBeanLocal impLogModBusiness;
    
    @EJB (name="WarehouseTypeBusinessBeanLocal", beanInterface=WarehouseTypeBusinessBeanLocal.class)
	private WarehouseTypeBusinessBeanLocal businessWarehouseType;
    
    @EJB (name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal businessWarehouse;
    
    @EJB (name="MovementElementBusinessBeanLocal", beanInterface=MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
    
	@EJB(name = "ElementBusinessBeanLocal", beanInterface = ElementBusinessBeanLocal.class)
	private ElementBusinessBeanLocal elementBusinessBean;
    
    private final static Logger log = UtilsBusiness.getLog4J(ImportLogItemBusinessBean.class);
    
   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#getAllImportLogItems()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ImportLogItemVO> getAllImportLogItems() throws BusinessException {
        log.debug("== Inicia getAllImportLogItems/ImportLogItemBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoImportLogItem.getAllImportLogItems(), ImportLogItemVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getAllImportLogItems/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllImportLogItems/ImportLogItemBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#getImportLogItemsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportLogItemVO getImportLogItemByID(Long id) throws BusinessException {
        log.debug("== Inicia getImportLogItemByID/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogItem objPojo = daoImportLogItem.getImportLogItemByID(id);
            if (objPojo == null) {
            	return null;
            }
            return UtilsBusiness.copyObject(ImportLogItemVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getImportLogItemByID/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogItemByID/ImportLogItemBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#createImportLogItem(co.com.directv.sdii.model.vo.ImportLogItemVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createImportLogItem(ImportLogItemVO obj) throws BusinessException {
        log.debug("== Inicia createImportLogItem/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogItem objPojo =  UtilsBusiness.copyObject(ImportLogItem.class, obj);
            daoImportLogItem.createImportLogItem(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createImportLogItem/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLogItem/ImportLogItemBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#updateImportLogItem(co.com.directv.sdii.model.vo.ImportLogItemVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImportLogItem(ImportLogItemVO obj) throws BusinessException {
        log.debug("== Inicia updateImportLogItem/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogItem objPojo =  UtilsBusiness.copyObject(ImportLogItem.class, obj);
            objPojo.setElement( UtilsBusiness.copyObject(Element.class,  obj.getElementVO()) );
            ImpLogConfirmation impLogConf = daoImpLogConfirmation.getImpLogConfirmationByImpLogItemId(obj.getId());
            if(impLogConf!=null&& obj.getAmountExpected()<impLogConf.getConfirmedQuantity()){
            	throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_UPDATE_QUANTITY_ERROR.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_UPDATE_QUANTITY_ERROR.getMessage());
            }
            daoImportLogItem.updateImportLogItem(objPojo);
            //Actualizo el elemento
            Element element = UtilsBusiness.copyObject(Element.class, obj.getElementVO());
            element.setCountry(obj.getImportLog().getCountry());
            daoElement.updateElement(element);
            //Actualizo si es serializado o no
            if(obj.getSerializedVO() != null ){
            	obj.getSerializedVO().setElement(UtilsBusiness.copyObject(Element.class,  obj.getElementVO()));
            	obj.getSerializedVO().setElementId(obj.getElementVO().getId());
            	daoSerialized.updateSerialized(UtilsBusiness.copyObject(Serialized.class, obj.getSerializedVO()));
            } else if( obj.getNotSerializedVO() != null ){
            	obj.getNotSerializedVO().setElement(UtilsBusiness.copyObject(Element.class,  obj.getElementVO()));
            	obj.getNotSerializedVO().setElementId(obj.getElementVO().getId());
            	daoNotSerialized.updateNotSerialized(UtilsBusiness.copyObject(NotSerialized.class, obj.getNotSerializedVO()));
            }
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n updateImportLogItem/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImportLogItem/ImportLogItemBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#deleteImportLogItem(co.com.directv.sdii.model.vo.ImportLogItemVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteImportLogItem(ImportLogItemVO obj) throws BusinessException {
        log.debug("== Inicia deleteImportLogItem/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogItem objPojo =  UtilsBusiness.copyObject(ImportLogItem.class, obj);
            ImportLogItem objPojoTmp = this.daoImportLogItem.getImportLogItemByID(obj.getId());
            if(objPojoTmp != null && objPojoTmp.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity()) ){
            	throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_DELETE_ERROR.getCode(),ErrorBusinessMessages.IMPORT_LOG_ITEM_DELETE_ERROR.getMessage());
            }
            
            
            //Se eliminan las confirmaciones del elemento
            businessImpLogConfirmation.deleteImpLogConfirmationByImpLogItemId( objPojo.getId() );
            
            //modificado por gfandino
            //Borro el registro de importLogItem
            daoImportLogItem.deleteImportLogItem(objPojo);
            
            //Borro el elemento de la tabla Serialized o NotSerialized
            if(objPojoTmp.getElement().getIsSerialized().equals(ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue())){
            	Serialized serialized = daoSerialized.getSerializedByID(objPojoTmp.getElement().getId());
            	WarehouseElement warehouseElement = daoWarehouseElement.getWareHouseElementByserialID(ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue(), objPojoTmp.getElement().getId());
            	daoWarehouseElement.deleteWarehouseElement(warehouseElement);
            	MovCmdQueue movCmdQueue = daoMovCmdQueue.getMovCmdQueueByElementId(true, objPojoTmp.getElement().getId());
            	daoMovCmdQueue.delete(movCmdQueue);
            	daoSerialized.deleteSerialized(serialized);
            	//Verifica si es elemento Vinculado y elimina el elemento.
            	if (serialized.getSerialized() != null){
            		deleteImportLogItemLinked(serialized.getSerialized());
            	}
            } else if(objPojoTmp.getElement().getIsSerialized().equals(ApplicationTextEnum.ELEMENT_IS_NOT_SERIALIZED.getApplicationTextValue()) ){
            	NotSerialized notSerialized = daoNotSerialized.getNotSerializedByID(objPojoTmp.getElement().getId());
            	WarehouseElement warehouseElement = daoWarehouseElement.getWareHouseElementByserialID(ApplicationTextEnum.ELEMENT_IS_NOT_SERIALIZED.getApplicationTextValue(), objPojoTmp.getElement().getId());
            	daoWarehouseElement.deleteWarehouseElement(warehouseElement);
            	MovCmdQueue movCmdQueue = daoMovCmdQueue.getMovCmdQueueByElementId(false, objPojoTmp.getElement().getId());
            	daoMovCmdQueue.delete(movCmdQueue);
            	daoNotSerialized.deleteNotSerialized(notSerialized);
            }
            
            //Borro el elemento
            Element element = objPojoTmp.getElement();
            daoElement.deleteElement(element);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n deleteImportLogItem/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogItem/ImportLogItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#deleteImportLogItem(co.com.directv.sdii.model.vo.ImportLogItemVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void deleteImportLogItemLinked(Serialized obj) throws BusinessException {
		try{
					 //Se eliminan las confirmaciones del elemento
					ImportLogItem impLogItem = daoImportLogItem.getImportLogItemByElementId(obj.getElementId());
			        daoImpLogConfirmation.deleteImpLogConfirmationByImpLogItemId(impLogItem.getId().longValue());
			        
			        //Borro el registro de importLogItem
			        daoImportLogItem.deleteImportLogItem(impLogItem);
			        
			        WarehouseElement warehouseElement = daoWarehouseElement.getWareHouseElementByserialID(ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue(), obj.getElement().getId());
	            	daoWarehouseElement.deleteWarehouseElement(warehouseElement);
	            	MovCmdQueue movCmdQueue = daoMovCmdQueue.getMovCmdQueueByElementId(true, obj.getElement().getId());
	            	daoMovCmdQueue.delete(movCmdQueue);
	            	daoSerialized.deleteSerialized(obj);
	            	
	            	daoElement.deleteElement(obj.getElement());
        
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n deleteImportLogItem/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteImportLogItem/ImportLogItemBusinessBean ==");
        }
	}


	


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#getImportLogItemsByImportLog(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogItemResponse getImportLogItemsByImportLog(Long idImportLog, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getImportLogItemsByImportLog/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(idImportLog, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ImportLogItemResponse response = daoImportLogItem.getImportLogItemsByImportLog(idImportLog, requestCollInfo);
        	/*List<ImportLogItem> objPojo = response.getImportLogItems();
        	List<ImportLogItemVO> importLogItemVO = new ArrayList<ImportLogItemVO>();
        	if(objPojo != null){
        		for(ImportLogItem objItem : objPojo){
        			ImportLogItemVO vo = UtilsBusiness.copyObject(ImportLogItemVO.class, objItem);
        			//Asocia el serialize o el nor serialized
        			if(objItem.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
        				//Busco el elemento serializado y lo asocio al VO
        				vo.setSerializedVO(UtilsBusiness.copyObject(SerializedVO.class, daoSerialized.getSerializedByID(vo.getElement().getId())));
        			} else if(objItem.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity())){
        				//busco el elemento no serializado
        				vo.setNotSerializedVO(UtilsBusiness.copyObject(NotSerializedVO.class, daoNotSerialized.getNotSerializedByID(vo.getElement().getId())));
        			}
        			//Double totalConfirmed = daoImpLogConfirmation.getSumImpLogConfirmationsByImpLogItemId(vo.getId());
        			//vo.setConfirmedQuantity( totalConfirmed );
        			importLogItemVO.add(vo);
        		}
        	}
        	response.setImportLogItemsVO(importLogItemVO);*/
        	response.setImportLogItems(null);
        	
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getImportLogItemsByImportLog/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogItemsByImportLog/ImportLogItemBusinessBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogItemResponse getImportLogItemsByCriteria(ModifyImportLogItemDTO modifyImportLogItemCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException{
		log.debug("== Inicia getImportLogItemsByCriteria/ImportLogItemBusinessBean ==");
        
        try {
        	ImportLogItemResponse response = daoImportLogItem.getImportLogItemsByCriteria(modifyImportLogItemCriteria, requestCollInfo);
        	List<ImportLogItem> objPojo = response.getImportLogItems();
        	List<ImportLogItemVO> importLogItemVO = new ArrayList<ImportLogItemVO>();
        	if(objPojo != null){
        		for(ImportLogItem objItem : objPojo){
        			ImportLogItemVO vo = UtilsBusiness.copyObject(ImportLogItemVO.class, objItem);
        			//Asocia el serialize o el nor serialized
        			if(objItem.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
        				//Busco el elemento serializado y lo asocio al VO
        				vo.setSerializedVO(UtilsBusiness.copyObject(SerializedVO.class, daoSerialized.getSerializedByID(vo.getElement().getId())));
        			} else if(objItem.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity())){
        				//busco el elemento no serializado
        				vo.setNotSerializedVO(UtilsBusiness.copyObject(NotSerializedVO.class, daoNotSerialized.getNotSerializedByID(vo.getElement().getId())));
        			}
        			List<ImpLogConfirmation> confirmations = daoImpLogConfirmation.getImpLogConfirmationsByImpLogItemId(vo.getId());
        			double totalConfirmed = getTotalConfirmed(confirmations);
        			if( ! confirmations.isEmpty() )
        				vo.setConfirmedQuantity( totalConfirmed );
        			else
        				vo.setConfirmedQuantity( 0D );
        			importLogItemVO.add(vo);
        		}
        	}
        	response.setImportLogItemsVO(importLogItemVO);
        	response.setImportLogItems(null);
        	
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getImportLogItemsByCriteria/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogItemsByCriteria/ImportLogItemBusinessBean ==");
        }
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal#finishImportLog(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void finishImportLog(User user, Long importLogId, Warehouse targetWh, String movTypeCodeE, String movTypeCodeS, String processCode) throws BusinessException {
		log.debug("== Inicia finishImportLog/WarehouseElementBusinessBean ==");
		try {
			if(importLogId == null || importLogId.longValue() <= 0){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			//Se consulta el estado en el cual quedan los elementos cuando se finaliza un registro de importacion
			ItemStatus itemStatus = this.daoItemStatus.getItemStatusSended();
			//Se consultan los elementos no serializados del registro de importacion
			ImportLogItemResponse notSerResponse = daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLogId, false, null);
			
			
			if(notSerResponse != null && notSerResponse.getImportLogItems() != null && !notSerResponse.getImportLogItems().isEmpty()){
				
				MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movTypeCodeE,
																										 movTypeCodeS);
				
				for(ImportLogItem notSerItem : notSerResponse.getImportLogItems()){
					//Se hacen los movimientos					
					MovementElementDTO dtoMovement = new MovementElementDTO(user,
							null, 
							UtilsBusiness.copyObject(WarehouseVO.class, targetWh), 
							null, 
							notSerItem.getElement().getElementType().getId(), 
							notSerItem.getElement().getElementType().getTypeElementCode(),
							importLogId, 
							CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
							dtoGenerics.getMovTypeCodeE(), 
							dtoGenerics.getMovTypeCodeS(), 
							dtoGenerics.getRecordStatusU(),
							dtoGenerics.getRecordStatusH(),
							notSerItem.getAmountExpected(),
							dtoGenerics.getMovConfigStatusPending(),
							dtoGenerics.getMovConfigStatusNoConfig());
					
					businessMovementElement.moveNotSerializedElementToWarehouse(dtoMovement);
				}
			}
			
			
			
			//Se consultan los elementos serializados del registro de importacion
			ImportLogItemResponse serResponse = daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLogId, true, null);
			if(serResponse != null && serResponse.getImportLogItems() != null && !serResponse.getImportLogItems().isEmpty()){
				MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movTypeCodeE, null);
				for(ImportLogItem serItem : serResponse.getImportLogItems()){
					//Se hacen los movimientos	
					MovementElementDTO dto = new MovementElementDTO(user, 
							UtilsBusiness.copyObject(WarehouseVO.class, targetWh), 
							serItem.getElement().getId(), 
							importLogId, 
							CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
							dtoGenerics.getMovTypeCodeE(), 
							dtoGenerics.getRecordStatusU(), 
							dtoGenerics.getRecordStatusH(),  
							true, 
							processCode,
							dtoGenerics.getMovConfigStatusPending(),
							dtoGenerics.getMovConfigStatusNoConfig());
					businessMovementElement.moveSerializedElementToWarehouse(dto);
				}
			}
			
		} catch (Throwable t) {
			log.error(" == Error finishImportLog/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina finishImportLog/WarehouseElementBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateElementItemImportLog(List<ImportLogItemVO> importLogItemListUpdate, ElementStatus elementStatus, ItemStatus itemStatus, ImportLog importLog) throws BusinessException{
		try{
			
			for(ImportLogItemVO actualizar:importLogItemListUpdate){
				ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(actualizar.getId());
				if(importLogItem != null){
					actualizar.setItemStatus(itemStatus);
					actualizar.getElementVO().setElementStatus(elementStatus);
					importLogItem.setAmountExpected(actualizar.getAmountExpected());
					daoImportLogItem.updateImportLogItem(importLogItem);
				}
			}
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operaciÃ³n saveImportLogItemsAndByCountry/ImportLogItemBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina saveImportLogItemsAndByCountry/ImportLogItemBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteElementItemImportLog(ImportLog importLog, List<ImportLogItemVO> deleteItems, User user) throws BusinessException{
		log.debug("== Inicia deleteElementItemImportLog/ImportLogItemBusinessBean ==");
		try{
			Warehouse warehouseTransit = null;
			warehouseTransit = getWareHouseOrCreateIfNotExists(importLog.getDealer().getId(),CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
			
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(null, CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_DELETE_IMPORT_LOG.getCodeEntity());
			for(ImportLogItem deleteItem : deleteItems){
				ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(deleteItem.getId());
				ItemStatus newItemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_DELETED.getCodeEntity());
				importLogItem.setItemStatus(newItemStatus);
				daoImportLogItem.updateImportLogItem(importLogItem);
				
				Element element = daoElement.getElementByID(importLogItem.getElement().getId());
				if(element.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){
					if(importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())){
					
						//Realiza el movimiento de salida del elemento
						MovementElementDTO dto = new MovementElementDTO(user, 
								UtilsBusiness.copyObject(WarehouseVO.class, warehouseTransit), 
								new Serialized(element.getId()),
								importLog.getId(), 
								CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(),
								dtoGenerics.getMovTypeCodeS(),
								dtoGenerics.getRecordStatusU(),
								dtoGenerics.getRecordStatusH(), 
								true, 
								CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(),
								dtoGenerics.getMovConfigStatusPending(),
								dtoGenerics.getMovConfigStatusNoConfig());
						businessMovementElement.deleteSerializedElementInWarehouse(dto);

					}else{
						Long countRegisterWarehouse = this.daoWarehouseElement.existsWarehouseElementByCountryAndElementID(importLogItem.getElement().getId());
						if(countRegisterWarehouse.longValue() == 0L){
							this.daoWarehouseElement.deletetSerializedWarehouseElementByElementID(importLogItem.getElement().getId());
							this.daoImportLogItem.deleteImportLogItem(importLogItem);
							
							Serialized ser = this.daoSerialized.getSerializedByID(importLogItem.getElement().getId());
							this.daoSerialized.deleteSerialized(ser);
							if(ser.getSerialized()!=null&&ser.getSerialized().getSerialCode()!=null){
								this.daoSerialized.deleteSerialized(ser.getSerialized());
							}
							
						}
					}

				}else if (element.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity())){ 
					if(importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity()) ){
						//Borra elementos de la tabla 'warehous_elements'
						MovementElementDTO dto = new MovementElementDTO(user, 
								UtilsBusiness.copyObject(WarehouseVO.class, warehouseTransit), 
								null, 
								element.getElementType().getId(), 
								null, 
								importLog.getId(), 
								CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
								dtoGenerics.getMovTypeCodeS(), 
								dtoGenerics.getRecordStatusU(), 
								dtoGenerics.getRecordStatusH(), 
								importLogItem.getAmountExpected(),
								dtoGenerics.getMovConfigStatusPending(),
								dtoGenerics.getMovConfigStatusNoConfig());
						businessMovementElement.deleteNotSerializedElementInWarehouse(dto);
					}
				}
			}
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operaciÃ³n deleteElementItemImportLog/ImportLogItemBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deleteElementItemImportLog/ImportLogItemBusinessBean ==");
		}
	}
	
	/**
	 * Metodo: valida la existencia de archivos de cargue masivo de elementos a un registro de importaciÃ³n
	 * que no se han finalizado de procesar.
	 * @param importLogId identificador del registro de importaciÃ³n
	 * @throws BusinessException en caso que existan archivos con proceso sin finalizar
	 * @author wjimenez
	 */
	private void validatePendingItemsInFiles(Long importLogId) throws PropertiesException, BusinessException {
		List<FilterUploadFileParamByFileTypeParamNameAndCodeDTO> filters = new ArrayList<FilterUploadFileParamByFileTypeParamNameAndCodeDTO>();
		
		FilterUploadFileParamByFileTypeParamNameAndCodeDTO filterUploadFile = new FilterUploadFileParamByFileTypeParamNameAndCodeDTO();
		filterUploadFile.setFileTypeCode(CodesBusinessEntityEnum.FILE_TYPE_IMPORTLOG_SERIALIZEDELEMENTS.getCodeEntity());
		
		filterUploadFile.setParamName(CodesBusinessEntityEnum.FILE_PARAM_ID_IMPORT_LOG.getCodeEntity());
		filterUploadFile.setParamValue(importLogId.toString());
		
		filters.add(filterUploadFile);
		
		List<UploadFileParamByFileTypeParamNameAndCodeDTO> pendingOrProcessingFiles = businessUploadFile.getUploadFileParamByFileTypeParamNameAndCode(filters);
		if(pendingOrProcessingFiles != null && !pendingOrProcessingFiles.isEmpty()) {
			throw new BusinessException(ErrorBusinessMessages.STOCK_IN434.getCode(), ErrorBusinessMessages.STOCK_IN434.getMessage());
		}
		
	}

	/**
	 * @param itemStatus
	 * @param importLog
	 * @throws BusinessException
	 */
	public void updateImportLogStatusToConfirm(ItemStatus itemStatus , ImportLog importLog)throws BusinessException{
		log.debug("== Inicia updateIfNoChanges/ImportLogItemBusinessBean ==");
		try {
			// Actualiza los items del registro de importacion, de forma masiva
			// y no uno por uno.
			ItemStatus previousitemStatus = new ItemStatus(CodesBusinessEntityEnum.ITEM_STATUS_CREATED.getIdEntity(ItemStatus.class.getName()));
			daoImportLogItem.updateImportLogItemsStatusByImportLogId(importLog.getId().longValue(),
					previousitemStatus, itemStatus);
        	
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n updateIfNoChanges/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateIfNoChanges/ImportLogItemBusinessBean ==");
        }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createNewImportLogElementItems(List<ImportLogItemVO> importLogItems,ImportLogVO importLogId, ItemStatus itemStatus,boolean isFinished, User user) throws BusinessException {
		log.debug("== Inicia createNewImportLogElementItems/ImportLogItemBusinessBean ==");
		try {
			ImportLog importLog = UtilsBusiness.copyObject(ImportLog.class,importLogId);
			ElementStatus elementStatus = this.daoElementStatus.getElementStatusByCode(CodesBusinessEntityEnum.ELEMENT_STATUS_T01.getCodeEntity());
			//Ordena los elementos entre serializados y no serializados y no serializados
			for (ImportLogItemVO importLogItem : importLogItems) {
				if(importLogItem.getSerializedVO()!=null){
					if(importLogItem.getAmountExpected() != null && importLogItem.getAmountExpected() > 1){
						log.debug("El elemento serializado que desea crear no puede tener cantidad superior a 1");
						throw new BusinessException(ErrorBusinessMessages.SER_QUANTITY_CANNOT_BE_GREATER_THAN_ONE.getCode(),ErrorBusinessMessages.SER_QUANTITY_CANNOT_BE_GREATER_THAN_ONE.getMessage());
					}else{
						this.createElementSerializedForImportLog(importLogItem, elementStatus, importLog, itemStatus);
					}
				}else{
					this.createElementNotSerializedForImportLog(importLogItem, elementStatus, importLog, itemStatus);
				}		
			}

			
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createNewImportLogElementItems/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createNewImportLogElementItems/ImportLogItemBusinessBean ==");
        }
		
	}
	
	/**
	 * Metodo: Envia correo al operador logistico del pais indicado
	 * @param countryId Long ID del pais en donde se esta buscando el operador logistico
	 * @param logisticOperatorWh 
	 * @throws BusinessException
	 */
	public void sendEmailToLogisticOperator(Long importLogId , UserVO user, String newsTypeCode, String newsObservation, Warehouse logisticOperatorWh) throws BusinessException{
		log.debug("== Inicia sendEmailToLogisticOperator/ImportLogItemBusinessBean ==");
		try {
			//jjimenezh 2010-09-13 Se cambia debido a que los correos que se evÃ­an al operador logÃ­stico se deben enviar al encargado de la bodega de dicho operador
			if(logisticOperatorWh == null){
				log.error("No se ha especificado la bodega del operador logÃ­stico a la que se reportarÃ¡ el correo de notificaciÃ³n");
				return;
			}
			
			if(logisticOperatorWh.getResponsibleEmail() == null || logisticOperatorWh.getResponsibleEmail().trim().equalsIgnoreCase("")){
				log.error("No se ha especificado el correo electrÃ³nico del responsable de la bodega con id: "+logisticOperatorWh.getId()+" y cÃ³digo "+logisticOperatorWh.getWhCode()+" del operador logÃ­stico");
				return;
			}
			
			List<String> recipients = new ArrayList<String>();
    		recipients.add(logisticOperatorWh.getResponsibleEmail());
    		recipients.add(user.getEmail());
			sendMail(recipients, user.getName(), importLogId.toString(), newsTypeCode, newsObservation, user.getCountry().getId());
			
		} catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n sendEmailToLogisticOperator/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina sendEmailToLogisticOperator/ImportLogItemBusinessBean ==");
        }
	}
	
	/**
	 * Metodo: EnvÃ­a un correo electrÃ³nico a partir de los parÃ¡metros necesarios
	 * @param recipients Lista de correos electrÃ³nicos de los destinatarios del correo
	 * @param senderUserName Nombre de usuario que genera el evento de correo (El que estÃ¡ autenticado en el sistema)
	 * @param newsDocument NÃºmero de documento (ver cada caso de uso para este documento)
	 * @param newsType Tipo de novedad que se encuentra en la tabla EMAIL_TYPES o en la enumeraciÃ³n <code>co.com.directv.sdii.common.enumerations.EmailTypesEnum</code>
	 * @param comment comentario para el cuerpo del correo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimeneh
	 */
	private void sendMail(List<String> recipients, String senderUserName, String newsDocument, String newsType, String comment, Long countryId) throws BusinessException{
		SendEmailDTO email = new SendEmailDTO();
		email.setNewsType( newsType );
        email.setNewsDocument( newsDocument );
        email.setNewsObservation( comment );
        email.setNewsSourceUser( senderUserName );
        email.setRecipient(recipients);
        businessEmailType.sendEmailByEmailTypeCodeAsic(email, countryId);
	}
	
	
	
	/**
	 * Metodo: Permite crear la modificacion a un import log
	 * @param userId
	 * @param imporLogId
	 * @throws BusinessException
	 */
	private void createImportLogItemModification(Long userId, ImportLog imporLog , Long importLogModification) throws BusinessException {
		log.debug("== Inicia createImportLogItemModification/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(imporLog, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(importLogModification, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ImpLogModification impLogModification = new ImpLogModification();
        	
        	ImpLogModificationType impLogModType = new ImpLogModificationType();
        	impLogModType.setId(importLogModification);
        	
        	impLogModification.setImpLogModificationType(impLogModType);        	
        	impLogModification.setImportLog(imporLog);
        	impLogModification.setModificationDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, daoUser));
        	User user = this.daoUser.getUserById(userId);
        	impLogModification.setUser(user);
        	daoLogImpModification.createImpLogModification(impLogModification);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createImportLogItemModification/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createImportLogItemModification/ImportLogItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#getImportLogItemsByImportLogIdAndIsSerialized(java.lang.Long, boolean)
	 */
	/*@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			Long importLogId, boolean isSerialized,RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getImportLogItemsByImportLogIdAndIsSerialized/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(importLogId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ImportLogItemResponse response =   daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLogId, isSerialized,requestCollInfo);
        	List<ImportLogItem> objPojo = response.getImportLogItems();
        	List<ImportLogItemVO> importLogItemVO = null;
        	if(objPojo != null){
        		importLogItemVO = UtilsBusiness.convertList(objPojo, ImportLogItemVO.class);
        		fillConfirmations(importLogItemVO);
        	}
        	response.setImportLogItemsVO(importLogItemVO);
        	response.setImportLogItems(null);
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getImportLogItemsByImportLogIdAndIsSerialized/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogItemsByImportLogIdAndIsSerialized/ImportLogItemBusinessBean ==");
        }
	}*/
	
	@Override
	public List<SerializedElementImportLogDTO> getImportLogItemsByImportLogIdAndIsSerializedForExcel(
			ImportLogElementsFilterDTO filterImportLogElements) throws BusinessException {
		log.debug("== Inicia getImportLogItemsByImportLogIdAndIsSerializedForExcel/ImportLogItemBusinessBean ==");
		UtilsBusiness.assertNotNull(filterImportLogElements, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(filterImportLogElements.getImportLogID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	List<SerializedElementImportLogDTO> list =   daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerializedForExcel(filterImportLogElements);
            return list;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImportLogItemsByImportLogIdAndIsSerializedForExcel/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogItemsByImportLogIdAndIsSerializedForExcel/ImportLogItemBusinessBean ==");
        }
	}
	
	@Override
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getImportLogItemsByImportLogIdAndIsSerialized/ImportLogItemBusinessBean ==");
		UtilsBusiness.assertNotNull(filterImportLogElements, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(filterImportLogElements.getImportLogID(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ImportLogItemResponse response =   daoImportLogItem.getImportLogItemsByImportLogIdStatusAndIsSerialized(filterImportLogElements, requestCollInfo);
        	List<ImportLogItem> objPojo = response.getImportLogItems();
        	List<ImportLogItemVO> importLogItemVO = new ArrayList<ImportLogItemVO>();
        	if(objPojo != null){
        		for(ImportLogItem objItem : objPojo){
        			ImportLogItemVO vo = UtilsBusiness.copyObject(ImportLogItemVO.class, objItem);
        			//Asocia el serialize o el nor serialized
        			if(objItem.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
        				//Busco el elemento serializado y lo asocio al VO
        				vo.setSerializedVO(UtilsBusiness.copyObject(SerializedVO.class, daoSerialized.getSerializedByID(vo.getElement().getId())));
        			} else if(objItem.getElement().getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity())){
        				//busco el elemento no serializado
        				vo.setNotSerializedVO(UtilsBusiness.copyObject(NotSerializedVO.class, daoNotSerialized.getNotSerializedByID(vo.getElement().getId())));
        			}
        			List<ImpLogConfirmation> confirmations = daoImpLogConfirmation.getImpLogConfirmationsByImpLogItemId(vo.getId());
        			double totalConfirmed = getTotalConfirmed(confirmations);
        			if(!confirmations.isEmpty()){
        				vo.setConfirmedQuantity(totalConfirmed);
        			}else{
        				vo.setConfirmedQuantity(0D);
        			}
        			importLogItemVO.add(vo);
        		}
        	}
        	response.setImportLogItemsVO(importLogItemVO);
        	response.setImportLogItems(null);
        	response.setSerialized(filterImportLogElements.isSerialized());
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getImportLogItemsByImportLogIdAndIsSerialized/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogItemsByImportLogIdAndIsSerialized/ImportLogItemBusinessBean ==");
        }
	}
	
	private void fillConfirmations(List<ImportLogItemVO> importLogItems) throws DAOServiceException, DAOSQLException, BusinessException {
		Double confirmedQuantity = 0D;
        for(ImportLogItemVO importLogItemVO : importLogItems){
        	List<ImpLogConfirmation> impLogConfPojo = daoImpLogConfirmation.getImpLogConfirmationsByImpLogItemId(importLogItemVO.getId());
        	confirmedQuantity = 0D;
        	for (ImpLogConfirmation impLogConfirmation : impLogConfPojo) {
        		confirmedQuantity += impLogConfirmation.getConfirmedQuantity();
			}
        	importLogItemVO.setConfirmedQuantity(confirmedQuantity);
        	importLogItemVO.setImpLogConfirmations( UtilsBusiness.convertList(impLogConfPojo, ImpLogConfirmationVO.class)  );				
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#confirmSerializedElementItemFromImportLog(co.com.directv.sdii.model.vo.ImportLogVO, co.com.directv.sdii.model.vo.ImportLogItemVO, java.lang.String, co.com.directv.sdii.model.vo.UserVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void  confirmSerializedElementItemFromImportLog(ConfirmSerializedElementItemFromImportLogDTO dto)throws BusinessException {

		log.debug("== Inicia confirmSerializedElementItemFromImportLog/ImportLogItemBusinessBean ==");

		try{
			String errorCode = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode();
			String errorMessage = ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage();
			String errorCodeImportLog = ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode();
			String errorMessageImportLog = ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getMessage();
			UtilsBusiness.assertNotNull(dto.getImpotLogId(), errorCode, errorMessage + " Identificador del registro de importación");
			UtilsBusiness.assertNotNull(dto.getUserId(), errorCode, errorMessage + " Identificador del usuario");
			UtilsBusiness.assertNotNull(dto.getImportLogItemId(), errorCode, errorMessage + " Identificador del item de registro de importación");
			
			ImportLog objPojo = daoImportLog.getImportLogByIDAndByLogisticOp(dto.getImpotLogId(), dto.getLogisticOperatorDealerId());
			UtilsBusiness.assertNotNull(objPojo, ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getMessage());
		
			User userPojo = daoUser.getUserById(dto.getUserId());
			UtilsBusiness.assertNotNull(userPojo, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			UserVO user = UtilsBusiness.copyObject(UserVO.class, userPojo);

			ImportLogItem importLogitem = daoImportLogItem.getImportLogItemByID(dto.getImportLogItemId());
			Element element = daoElement.getElementByID(importLogitem.getElement().getId());
			
			//Verificar si el elemento tiene un elemento vinculado para poderlo confirmar tambien.
			Serialized serialized = daoSerialized.getSerializedByID(element.getId());
			UtilsBusiness.assertNotNull(serialized, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
			
			
			//jnova 27/10/2011 se agrega logica para informar que proceso se esta realizando
			String confirmationProcessCode = CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity();
			

			//gfandino-22/07/2011 Se agrega condicional para saber si se debe obtener bodega de calidad o disponibles
			Warehouse wareHousePojo = null;
			Warehouse wareHouseTransito = null;
			if(dto.isSend2QualityCtrlWh()){
				wareHousePojo = daoWareHouse.getWarehouseByID(dto.getQualityControlWhId());
			}else{
				if(serialized.getSerialized()==null&&serialized.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
					wareHousePojo = this.getLogisticOperatorWarehouse(dto.getLogisticOperatorDealerId(),CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity());
				}else{
					wareHousePojo = this.getLogisticOperatorWarehouse(dto.getLogisticOperatorDealerId(),CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES.getCodeEntity());
				}
			}
			wareHouseTransito = this.getLogisticOperatorWarehouse(dto.getLogisticOperatorDealerId(),CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
			if(wareHouseTransito == null){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
			}

			if(wareHousePojo == null){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
			}
			
			boolean allReceiptedSerialized = false;
			boolean allReceiptedNotSerialized = false;

			String importStatusCode = objPojo.getImportLogStatus().getStatusCode();
			/**
			 * Validar que el registro importacion se encuentre en estado
			 * enviado, confirmado parcial o en inconsistencia
			 */
			if (!importStatusCode.equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())
					&& importStatusCode.equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity())
					&& importStatusCode.equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity())) {
				throw new BusinessException(errorCodeImportLog,	errorMessageImportLog);
			}

			/**
			 * Actualizar el estado del item del registro de importación
			 **/
			businessImportLog.updateItemStatusInImportLog(UtilsBusiness.copyObject(ImportLogItemVO.class, importLogitem), user);
			

			/**
			 * si todos los items del registro de importacion fueron recibidos,
			 * entonces el registro de importacion cambia su estado a
			 * <b>recibido</b>, de lo contrario pasa a ser <b>confirmado
			 * parcial</b>
			 **/
			allReceiptedSerialized = businessImportLog.allImportLogItemAreRecepted(daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(dto.getImpotLogId(), true,null).getImportLogItems());
			allReceiptedNotSerialized = businessImportLog.allImportLogItemAreRecepted(daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(dto.getImpotLogId(), false,null).getImportLogItems());
			ImportLogStatus status = daoImportLogStatus.getImportLogStatusByCode( ( allReceiptedSerialized && allReceiptedNotSerialized) ? CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity() : CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity());

			objPojo.setImportLogStatus(status);
			daoImportLog.updateImportLog(objPojo);
			if (objPojo.getImportLogStatus() != null && objPojo.getImportLogStatus().getStatusCode()!=null)
				createImportLogItemModification(objPojo.getUser().getId(), objPojo, impLogModBusiness.importLogStatusToImportLogModification(objPojo.getImportLogStatus() ) );

			if (allReceiptedSerialized && allReceiptedNotSerialized) {
				/*if(wareHousePojo.getResponsibleEmail() != null){
					businessImportLog.sendMailToImportLogNotification(wareHousePojo, user, objPojo.getId());
				}*/
				List<User> usersMail = new ArrayList<User>();
				
				List<User> analistUsers = daoUser.getUsersByRoleTypeCodeAndCountryId(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity(), wareHousePojo.getCountry().getId());
				usersMail.addAll(analistUsers);
				
				List<User> operatorLogisticForDealer = daoUser.getUsersByDealerIdAndRoleTypeCode(objPojo.getDealer().getId(), CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity());
				usersMail.addAll(operatorLogisticForDealer);
				
				for(User u: usersMail){
					businessImportLog.sendMailToImportLogNotification(u, user, objPojo.getId());
				}
				
			}

			// Realiza los movimientos a la bodega destino
			
			//gfandino - 22/07/2011 Se adiciona condicional para determina si el movimiento es compra nacional o exterior
			String moveMovementType = null;
			String moveMovementTypeExit = null;
			//Si son del mismo paÃ­s es un movimiento nacional
			if(objPojo.getSupplier().getCountryId().getId().equals(user.getCountry().getId())) {
				moveMovementType = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_NATIONAL_BUY.getCodeEntity();
				moveMovementTypeExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_EXIT.getCodeEntity();
			}else{
				moveMovementType = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INTERNATIONAL_BUY.getCodeEntity();
				moveMovementTypeExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_EXIT.getCodeEntity();
			}
			
			ItemStatus itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
			//ElementMovementDTO elementMovementDTO = new ElementMovementDTO(wareHouseTransito.getId(), wareHousePojo.getId(), serialized.getElementId(), moveMovementType, moveMovementTypeExit, objPojo.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), serialized.getSerialCode(), null, true, confirmationProcessCode,itemStatus);
			//warehouseElementBusiness.moveSerializedElementToWareHouse(elementMovementDTO);
			
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(moveMovementType, moveMovementTypeExit);
			MovementElementDTO dtoMovement = new MovementElementDTO(user, 
					UtilsBusiness.copyObject(WarehouseVO.class, wareHouseTransito), 
					UtilsBusiness.copyObject(WarehouseVO.class, wareHousePojo), 
					serialized,
					objPojo.getId(), 
					CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
					dtoGenerics.getMovTypeCodeE(), 
					dtoGenerics.getMovTypeCodeS(),
					dtoGenerics.getRecordStatusU(), 
					dtoGenerics.getRecordStatusH(), 
					true, 
					confirmationProcessCode,
					dtoGenerics.getMovConfigStatusPending(),
					dtoGenerics.getMovConfigStatusNoConfig());
			businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
			
			
			

		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operación confirmSerializedElementItemFromImportLog/ImportLogItemBusinessBean ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina confirmSerializedElementItemFromImportLog/ImportLogItemBusinessBean ==");
		}
	}




	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#confirmImportLogItems(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmNotSerializedImportLogItems(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, boolean isWHQ)
			throws BusinessException {
		
		log.debug("== Inicia confirmNotSerializedImportLogItemsAndByCountry/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(impLogItems2Confirm, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(logisticOperatorDealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            
        	if(impLogItems2Confirm.isEmpty()){
        		log.debug("Se trató de confirmar un reporte de importación sin enviar Ã­tems de importación");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	User user = daoUser.getUserById(userId);
        	UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	Long importLogId = null;
        	Long countryId = null;
        	
        	for (ImportLogItemVO importLogItemVO : impLogItems2Confirm) {
				
        		validateImportLogItem(importLogItemVO);
				if(importLogId == null){
					importLogId = importLogItemVO.getImportLog().getId();
					countryId = importLogItemVO.getImportLog().getCountry().getId();
				}
				
				//Asignando el elemento
				Element element = daoElement.getElementByID(importLogItemVO.getElement().getId());
				UtilsBusiness.assertNotNull(element, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
				importLogItemVO.setElement(element);
				
				//Si tiene en confirmedQuantity lo mismo que en amount expected:
				ImpLogConfirmation impLogConfirmation = buildImpLogConfirmation(importLogItemVO, user);
				
				//Creando el registro de confirmaciÃ³n en la base de datos:
				daoImpLogConfirmation.createImpLogConfirmation(impLogConfirmation);
				
				//Actualizando el estado del Ã­tem de importaciÃ³n
				daoImportLogItem.updateImportLogItem(impLogConfirmation.getImportLogItem());
				
				updateElementStatus(impLogConfirmation.getImportLogItem());
			}
        	
        	Warehouse logisticOperatorQtyCtrlWh = getLogisticOperatorWarehouse(logisticOperatorDealerId, CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
        	Warehouse logisticOperatorTransitWh = getLogisticOperatorWarehouse(logisticOperatorDealerId, CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
        	Warehouse logisticOperatorDisponiWh = getLogisticOperatorWarehouse(logisticOperatorDealerId, CodesBusinessEntityEnum.WAREHOUSE_TYPE_DISPONIBILIDADES.getCodeEntity());
        	
        	WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorTransitWh);
           	WarehouseVO whVd1 = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorQtyCtrlWh);
           	WarehouseVO whVd2 = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorDisponiWh);
            MassiveMovementBetweenWareHouseDTO movementDto = null;
           	if (isWHQ)
        	  movementDto = buildMassiveMovementDTO(getListElementVO(impLogItems2Confirm),whVo,whVd1);
           	else
           	  movementDto = buildMassiveMovementDTO(getListElementVO(impLogItems2Confirm),whVo,whVd2);
        	businessWareHouseElement.massiveMovementOfNotSerializedElementsBetweenWareHouse(movementDto, importLogId, CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(),userId);
        	
        	Dealer dealer = daoDealer.getDealerByID(logisticOperatorDealerId);
        	
        	updateImportLogByConfirmationAndByCountry(importLogId, user, logisticOperatorQtyCtrlWh, dealer.getPostalCode().getCity().getState().getCountry().getId());
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n confirmNotSerializedImportLogItemsAndByCountry/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina confirmNotSerializedImportLogItemsAndByCountry/ImportLogItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#confirmImportLogItems(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmNotSerializedImportLogItemsCompany(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, long company, String Cod_WHDestiny)
			throws BusinessException {
		
		log.debug("== Inicia confirmNotSerializedImportLogItemsCompany/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(impLogItems2Confirm, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(logisticOperatorDealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            
        	if(impLogItems2Confirm.isEmpty()){
        		log.debug("Se tratÃ³ de confirmar un reporte de importaciÃ³n sin enviar Ã­tems de importaciÃ³n");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	User user = daoUser.getUserById(userId);
        	UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			UserVO userVO = UtilsBusiness.copyObject(UserVO.class, user);
        	Long importLogId = null;
        	
        	ImportLog importLog = null;
        	Long countryId = null;
        	for (ImportLogItemVO importLogItemVO : impLogItems2Confirm) {
				
        		validateImportLogItem(importLogItemVO);
				if(importLogId == null){
					importLog = importLogItemVO.getImportLog();
					if(importLog == null) {
						throw new BusinessException("no se pudo obtener el registro de importación del ítem con id: " + importLogItemVO.getId());
					}
					importLogId = importLog.getId();
					countryId = importLogItemVO.getImportLog().getCountry().getId();
				}
				
				//Asignando el elemento
				Element element = daoElement.getElementByID(importLogItemVO.getElement().getId());
				UtilsBusiness.assertNotNull(element, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
				importLogItemVO.setElement(element);
				
				//Si tiene en confirmedQuantity lo mismo que en amount expected:
				ImpLogConfirmation impLogConfirmation = buildImpLogConfirmation(importLogItemVO, user);
				
				//Creando el registro de confirmaciÃ³n en la base de datos:
				daoImpLogConfirmation.createImpLogConfirmation(impLogConfirmation);
				
				//Actualizando el estado del Ã­tem de importaciÃ³n
				daoImportLogItem.updateImportLogItem(impLogConfirmation.getImportLogItem());
				
				updateElementStatus(impLogConfirmation.getImportLogItem());
			}
			
        	Warehouse logisticOperatorTransitWh = getLogisticOperatorWarehouse(logisticOperatorDealerId, CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
        	Warehouse logisticOperatorDestinyWh = getWarehouse(company, Cod_WHDestiny);


			/**
			 * si todos los items del registro de importacion fueron recibidos,
			 * entonces el registro de importacion cambia su estado a
			 * <b>recibido</b>, de lo contrario pasa a ser <b>confirmado
			 * parcial</b>
			 **/
			boolean allReceiptedSerialized = false;
			boolean allReceiptedNotSerialized = false;
			allReceiptedSerialized = businessImportLog.allImportLogItemAreRecepted(daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLog.getId(), true,null).getImportLogItems());
			allReceiptedNotSerialized = businessImportLog.allImportLogItemAreRecepted(daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLog.getId(), false,null).getImportLogItems());
			
			if (allReceiptedSerialized && allReceiptedNotSerialized) {
				if (logisticOperatorDestinyWh != null && logisticOperatorDestinyWh.getResponsibleEmail() != null)
					businessImportLog.sendMailToImportLogNotification(logisticOperatorDestinyWh, userVO, importLog.getId());
			}
			
        	WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorTransitWh);
           	WarehouseVO whVd1 = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorDestinyWh);
            MassiveMovementBetweenWareHouseDTO movementDto = null;
           	movementDto = buildMassiveMovementDTO(getListElementVO(impLogItems2Confirm),whVo,whVd1);
           	
           	String moveMovementType = null;
			String moveMovementTypeExit = null;
			if(importLog.getSupplier() != null && importLog.getSupplier().getCountryId() != null) {
				if(importLog.getSupplier().getCountryId().getId().equals(user.getCountry().getId())) {
					moveMovementType = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_NATIONAL_BUY.getCodeEntity();
					moveMovementTypeExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_EXIT.getCodeEntity();
				}else{
					moveMovementType = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INTERNATIONAL_BUY.getCodeEntity();
					moveMovementTypeExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_EXIT.getCodeEntity();
				}
			} else {
				throw new BusinessException("no fue posible obtener el país del registro de importación con id = " + importLog.getId());
			}
			
        	businessWareHouseElement.massiveMovementOfNotSerializedElementsBetweenWareHouse(movementDto, moveMovementType, moveMovementTypeExit, importLog.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(),userId);
        	
        	Dealer dealer = daoDealer.getDealerByID(logisticOperatorDealerId);
        	
        	updateImportLogByConfirmationAndByCountry(importLogId, user, logisticOperatorDestinyWh, dealer.getPostalCode().getCity().getState().getCountry().getId());
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n confirmNotSerializedImportLogItemsAndByCountry/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina confirmNotSerializedImportLogItemsCompany/ImportLogItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#confirmImportLogItems(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmNotSerializedImportLogItemsLogisticOperator(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, long logisticOperator, String cod_WHDestiny)
			throws BusinessException {
		
		log.debug("== Inicia confirmNotSerializedImportLogItemsLogisticOperator/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(impLogItems2Confirm, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(logisticOperatorDealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            
        	if(impLogItems2Confirm.isEmpty()){
        		log.debug("Se tratÃ³ de confirmar un reporte de importaciÃ³n sin enviar Ã­tems de importaciÃ³n");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	User user = daoUser.getUserById(userId);
        	UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
			UserVO userVO = UtilsBusiness.copyObject(UserVO.class, user);
        	Long importLogId = null;
        	ImportLog importLog = null;
        	Long countryId = null;
        	for (ImportLogItemVO importLogItemVO : impLogItems2Confirm) {
				
        		validateImportLogItem(importLogItemVO);
				if(importLogId == null){
					importLog = importLogItemVO.getImportLog();
					if(importLog == null) {
						throw new BusinessException("no se pudo obtener el registro de importación del ítem con id: " + importLogItemVO.getId());
					}
					importLogId = importLog.getId();
					countryId = importLog.getCountry().getId();
				}
				
				//Asignando el elemento
				Element element = daoElement.getElementByID(importLogItemVO.getElement().getId());
				UtilsBusiness.assertNotNull(element, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
				importLogItemVO.setElement(element);
				
				//Si tiene en confirmedQuantity lo mismo que en amount expected:
				ImpLogConfirmation impLogConfirmation = buildImpLogConfirmation(importLogItemVO, user);
				
				//Creando el registro de confirmaciÃ³n en la base de datos:
				daoImpLogConfirmation.createImpLogConfirmation(impLogConfirmation);
				
				//Actualizando el estado del Ã­tem de importaciÃ³n
				daoImportLogItem.updateImportLogItem(impLogConfirmation.getImportLogItem());
				
				updateElementStatus(impLogConfirmation.getImportLogItem());
			}
        	
        	Warehouse logisticOperatorTransitWh = getLogisticOperatorWarehouse(logisticOperator, CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
        	Warehouse logisticOperatorDestinyWh = getLogisticOperatorWarehouse(logisticOperator, cod_WHDestiny);
			

			/**
			 * si todos los items del registro de importacion fueron recibidos,
			 * entonces el registro de importacion cambia su estado a
			 * <b>recibido</b>, de lo contrario pasa a ser <b>confirmado
			 * parcial</b>
			 **/
        	boolean allReceiptedSerialized = false;
			boolean allReceiptedNotSerialized = false;
			allReceiptedSerialized = businessImportLog.allImportLogItemAreRecepted(daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLog.getId(), true,null).getImportLogItems());
			allReceiptedNotSerialized = businessImportLog.allImportLogItemAreRecepted(daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLog.getId(), false,null).getImportLogItems());
			
			if (allReceiptedSerialized && allReceiptedNotSerialized) {
				if (logisticOperatorDestinyWh != null && logisticOperatorDestinyWh.getResponsibleEmail() != null)
					businessImportLog.sendMailToImportLogNotification(logisticOperatorDestinyWh, userVO, importLog.getId());
			}
			
        	WarehouseVO whVo = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorTransitWh);
           	WarehouseVO whVd1 = UtilsBusiness.copyObject(WarehouseVO.class, logisticOperatorDestinyWh);
            MassiveMovementBetweenWareHouseDTO movementDto = null;
           	movementDto = buildMassiveMovementDTO(getListElementVO(impLogItems2Confirm),whVo,whVd1);
           	
           	String moveMovementType = null;
			String moveMovementTypeExit = null;
			if(importLog.getSupplier() != null && importLog.getSupplier().getCountryId() != null) {
				if(importLog.getSupplier().getCountryId().getId().equals(user.getCountry().getId())) {
					moveMovementType = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_NATIONAL_BUY.getCodeEntity();
					moveMovementTypeExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_EXIT.getCodeEntity();
				}else{
					moveMovementType = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INTERNATIONAL_BUY.getCodeEntity();
					moveMovementTypeExit = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_EXIT.getCodeEntity();
				}
			} else {
				throw new BusinessException("no fue posible obtener el pais del registro de importación con id = " + importLog.getId());
			}

			businessWareHouseElement.massiveMovementOfNotSerializedElementsBetweenWareHouse(movementDto, moveMovementType, moveMovementTypeExit, importLog.getId(), CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(),userId);
        	
        	Dealer dealer = daoDealer.getDealerByID(logisticOperatorDealerId);
        	
        	updateImportLogByConfirmationAndByCountry(importLogId, user, logisticOperatorDestinyWh, dealer.getPostalCode().getCity().getState().getCountry().getId());
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n confirmNotSerializedImportLogItemsAndByCountry/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina confirmNotSerializedImportLogItemsLogisticOperator/ImportLogItemBusinessBean ==");
        }
	}
	
	
	
	
	/*
	 * Metodo util que retorna la lista de ElementVO de un ImportLogItem 
	 */
	public List<ElementVO> getListElementVO(List<ImportLogItemVO> impLogItems2Confirm){
		
		List<ElementVO> listElementVOToReturn = new ArrayList<ElementVO>();
		for (ImportLogItemVO importLogItemVO : impLogItems2Confirm) {
			ElementVO elementVO =  null;
			if (importLogItemVO.getSerializedVO()!=null){
			   elementVO = importLogItemVO.getElementVO();
			   elementVO.setMovedQuantity(1.0);
			   listElementVOToReturn.add(importLogItemVO.getElementVO());	
			}else{
				elementVO = importLogItemVO.getElementVO();
				elementVO.setId(null);
				listElementVOToReturn.add(elementVO);	
			}
				
			
		}
		return listElementVOToReturn;
	}	

	/**
	 * Metodo: Actualiza el estado del elemento dependiendo del estado del Ã­tem de importaciÃ³n
	 * @param importLogItem Registro de importaciÃ³n que tiene el estado actual
	 * @author jjimenezh
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 */
	private void updateElementStatus(ImportLogItem importLogItem) throws PropertiesException, DAOServiceException, DAOSQLException, BusinessException {
		Element element = importLogItem.getElement();
		String newElementStatusCode = null;
		if(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity().equalsIgnoreCase(importLogItem.getItemStatus().getStatusCode())){
			newElementStatusCode = CodesBusinessEntityEnum.ELEMENT_STATUS_S04.getCodeEntity();
		}else if(CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity().equalsIgnoreCase(importLogItem.getItemStatus().getStatusCode())){
			newElementStatusCode = CodesBusinessEntityEnum.ELEMENT_STATUS_T01.getCodeEntity();
		}else if(CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity().equalsIgnoreCase(importLogItem.getItemStatus().getStatusCode())){
			newElementStatusCode = CodesBusinessEntityEnum.ELEMENT_STATUS_D03.getCodeEntity();
		}
		
		if(newElementStatusCode == null){
			return;
		}
		ElementStatus es = daoElementStatus.getElementStatusByCode(newElementStatusCode);
		UtilsBusiness.assertNotNull(es, ErrorBusinessMessages.ELEMENT_STATUS_NOT_SETTED.getCode(), ErrorBusinessMessages.ELEMENT_STATUS_NOT_SETTED.getMessage());
		element = daoElement.getElementByID(importLogItem.getElement().getId());
		element.setElementStatus(es);
		element.setCountry(importLogItem.getImportLog().getCountry());
		daoElement.updateElement(element);
	}


	/**
	 * Metodo: Actualiza el registro de importaciÃ³n porque se ha registrado una confirmaciÃ³n de elementos<br>
	 * si el registro de importaciÃ³n tiene todos los elementos confirmados, invoca el caso de uso INV 08
	 * para informar al operador logÃ­stico que se ha confirmado el registro de importaciÃ³n
	 * @param importLogId identifciador del registro de importaciÃ³n que fuÃ© confirmado
	 * @param user informaciÃ³n del usuario que realizÃ³ la confirmaciÃ³n
	 * @param country - Long identificador del paï¿½s
	 * @throws DAOServiceException en caso de error al realizar la operaciÃ³n
	 * @throws DAOSQLException en caso de error al realizar la operaciÃ³n
	 * @throws PropertiesException en caso de error al realizar la operaciÃ³n
	 * @throws BusinessException en caso de error al realizar la operaciÃ³n
	 * @author jjimenezh
	 * @param logisticOperatorQtyCtrlWh 
	 */
	private void updateImportLogByConfirmationAndByCountry(Long importLogId, User user, Warehouse logisticOperatorQtyCtrlWh,Long country) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		//Iterando de nuevo sobre los elementos para ver si todos fueron confirmados:
		//operacion paginada, se envia en null el parametro ya que no es necesario paginar en este punto.
		RequestCollectionInfo requestCollInfo = null;
    	List<ImportLogItem> allImportLogItems = daoImportLogItem.getImportLogItemsByImportLog(importLogId, requestCollInfo).getImportLogItems();
    	boolean allRecepted = true;
    	for (ImportLogItem importLogItem : allImportLogItems) {
			if(! importLogItem.getItemStatus().getStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())){
				allRecepted = false;
				break;
			}
		}
    	ImportLog impLog = daoImportLog.getImportLogByID(importLogId);
    	String newImportLogStatusCode = null;
    	if(allRecepted){
    		newImportLogStatusCode = CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity();
    		UserVO userVo = UtilsBusiness.copyObject(UserVO.class, user);
    		//2010-07-27 Invocar el caso de uso INV 08 para informar al Analista de LogÃ­stica que el Registro de ImportaciÃ³n ha sido recepcionado
    		sendEmailToLogisticOperator(importLogId, userVo, EmailTypesEnum.IMPORT_LOG_HAS_BEEN_RECEIVED.getEmailTypecode(), " El registro de importación ha sido recepcionado ", logisticOperatorQtyCtrlWh);
    	}else if (!impLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity())){
    		newImportLogStatusCode = CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity();
    	}
    	
    	ImportLogStatus newImpLogstatus = daoImportLogStatus.getImportLogStatusByCode(newImportLogStatusCode);
    	UtilsBusiness.assertNotNull(newImpLogstatus, ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getMessage());
    	
    	impLog.setImportLogStatus(newImpLogstatus);
    	daoImportLog.updateImportLog(impLog);
    	if ( impLog.getImportLogStatus() != null && impLog.getImportLogStatus().getStatusCode()!=null )
    		createImportLogItemModification(user.getId(), impLog, impLogModBusiness.importLogStatusToImportLogModification( impLog.getImportLogStatus() ) );
	}


	/**
	 * Metodo: //Si la cantidad pendiente por confirmar es un valor negativo es decir que la cantidad confirmada ha sobrepasado la cantidad esperada:
	 * @param pendingToConfirm cantidad pendiente por confirmar
	 * @param totalConfirmed cantidad total confirmada
	 * @param importLogItemVO item de registro de importaciÃ³n
	 * @throws BusinessException En caso de error al realizar la operaciÃ³n
	 * @author jjimenezh
	 */
	private void validatePendingToConfirQty(Double pendingToConfirm, Double totalConfirmed, ImportLogItemVO importLogItemVO) throws BusinessException {
		//Si la cantidad pendiente por confirmar es un valor negativo es decir que la cantidad confirmada ha sobrepasado la cantidad esperada:
		if(pendingToConfirm.doubleValue() < 0){
			log.debug("Error: se encontrÃ³ que la cantidad pendiente por confirmar es negativa, es decir que se han " 
					+ "confirmado mas items de los que se esperaban: imporLogItemId: " + importLogItemVO.getId() 
					+ " total confirmado en las diferentes confirmaciones: " + totalConfirmed + " total de elementos esperados " 
					+ importLogItemVO.getAmountExpected());
			throw new BusinessException(ErrorBusinessMessages.CONFIRMED_QTY_EXCEEDS_THE_EXPECTED_QTY.getCode(), ErrorBusinessMessages.CONFIRMED_QTY_EXCEEDS_THE_EXPECTED_QTY.getMessage());
		}
	}


	/**
	 * Metodo: <Descripcion>
	 * @param impLogConfList
	 * @return Cantidad total confirmada
	 * @author suma la cantidad de las confirmaciones parciales de un item de registro de importaciÃ³n
	 */
	private Double getTotalConfirmed(List<ImpLogConfirmation> impLogConfList) {
		Double totalConfirmed = 0D;
		for (ImpLogConfirmation oldImpLogConfirmation : impLogConfList) {
			totalConfirmed += oldImpLogConfirmation.getConfirmedQuantity();
		}
		return totalConfirmed;
	}


	/**
	 * Metodo: Valida la informaciÃ³n de un item de registro de importaciÃ³n
	 * @param importLogItemVO informaciÃ³n del item de importaciÃ³n a ser validada
	 * @throws BusinessException En caso de error al ejecutar las validaciones
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @author jjimenezh
	 * @throws PropertiesException 
	 */
	private void validateImportLogItem(ImportLogItemVO importLogItemVO) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		if(importLogItemVO.getConfirmedQuantity() == null){
			log.debug("Se tratÃ³ de confirmar un reporte de importaciÃ³n con una cantidad de elemento recibido indeterminada para el Ã­tem de registro de importaciÃ³n con id: " + importLogItemVO.getId());
    		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
		
		UtilsBusiness.assertNotNull(importLogItemVO.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(importLogItemVO.getElement(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(importLogItemVO.getElement().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		ImportLogItem ilItem = daoImportLogItem.getImportLogItemByID(importLogItemVO.getId());
		
		if(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity().equalsIgnoreCase(ilItem.getItemStatus().getStatusCode())){
			log.error("El item de registro de importaciÃ³n con id: " + importLogItemVO.getId() + " ya ha sido confirmado");
			throw new BusinessException(ErrorBusinessMessages.IMPORT_LOG_ITEM_STATUS_IS_INVALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_ITEM_STATUS_IS_INVALID.getMessage());
		}
	}


	

	/**
	 * Metodo: <Descripcion>
	 * @param logisticOperatorDealerId
	 * @param whTypeCode
	 * @return
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Warehouse getLogisticOperatorWarehouse(
			Long logisticOperatorDealerId, String whTypeCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		Dealer logOperDealer = daoDealer.getDealerByID(logisticOperatorDealerId);
		UtilsBusiness.assertNotNull(logOperDealer, ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());
		
		//Si el tipo de dealer no es operador logÃ­stico:
		/*if(! CodesBusinessEntityEnum.CODE_DEALER_TYPE_LOGISTIC_OPERATOR.getCodeEntity().equalsIgnoreCase(logOperDealer.getDealerType().getDealerTypeCode())){
			log.error("El dealer especificador no es operador logÃ­stico");
			throw new BusinessException(ErrorBusinessMessages.DEALER_ISNT_LOGISTIC_OPERATOR.getCode(), ErrorBusinessMessages.DEALER_ISNT_LOGISTIC_OPERATOR.getMessage());
		}*/
		
		List<Warehouse> whs = daoWareHouse.getWhByCrewAndDealerAndWhType(logOperDealer.getId(),null, whTypeCode);
		if(whs.isEmpty()){
			log.error("El dealer operador logÃ­stico con id: \""+logOperDealer.getId()+"\" no tiene bodegas del cÃ³digo tipo: \""+whTypeCode+"\"");
			Object[] params = new Object[2];			
			params[0] = logOperDealer.getDealerName()+"";
			params[1] = daoWarehouseType.getWarehouseTypeByCode(whTypeCode).getWhTypeName();
        	throw new BusinessException(ErrorBusinessMessages.STOCK_IN424.getCode(),ErrorBusinessMessages.STOCK_IN424.getMessage(params), UtilsBusiness.getParametersWS(params) );
		}
		return whs.get(0);
	}
	
	public Warehouse getWarehouse(
			Long logisticOperatorDealerId, String whTypeCode) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException {
		Dealer logOperDealer = daoDealer.getDealerByID(logisticOperatorDealerId);
		UtilsBusiness.assertNotNull(logOperDealer, ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_EXIST.getMessage());
		
		
		List<Warehouse> whs = daoWareHouse.getWhByCrewAndDealerAndWhType(logOperDealer.getId(), null, whTypeCode);
		if(whs.isEmpty()){
			log.error("El dealer operador logÃ­stico con id: \""+logOperDealer.getId()+"\" no tiene bodegas del cÃ³digo tipo: \""+whTypeCode+"\"");
			throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
		}
		return whs.get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#buildMassiveMovementDTO(java.util.List, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO)
	 */
	@Override
	public MassiveMovementBetweenWareHouseDTO buildMassiveMovementDTO(List<ElementVO> whElements, WarehouseVO wareHouseSource, WarehouseVO wareHouseTarget) throws PropertiesException {
		MassiveMovementBetweenWareHouseDTO movementDto = new MassiveMovementBetweenWareHouseDTO();
		movementDto.setListObjectToMove(whElements);
		movementDto.setWareHouseSource(wareHouseSource);
		movementDto.setWareHouseTarget(wareHouseTarget);
		movementDto.setProcessCodeIBS(CodesBusinessEntityEnum.PROCESS_CODE_IBS_OTHER.getCodeEntity());
		return movementDto;
	}

	/**
	 * Metodo: Construye la confirmaciÃ³n de un item de importaciÃ³n
	 * @param importLogItemVO informaciÃ³n del item de importaciÃ³n a ser confirmado
	 * @param user usuario que realiza la confirmaciÃ³n
	 * @return InformaciÃ³n de la confirmaciÃ³n del item de importaciÃ³n
	 * @throws BusinessException en caso de error
	 * @throws PropertiesException en caso de error
	 * @throws DAOServiceException en caso de error
	 * @throws DAOSQLException en caso de error
	 * @author jjimenezh
	 */
	private ImpLogConfirmation buildImpLogConfirmation(
			ImportLogItemVO importLogItemVO, User user) throws BusinessException, PropertiesException, DAOServiceException, DAOSQLException {
		
		ImpLogConfirmation impLogConfirmation = new ImpLogConfirmation();
		impLogConfirmation.setConfirmationDate(new Date());
		impLogConfirmation.setUser(user);
		ImportLogItem impLogItemPojo = UtilsBusiness.copyObject(ImportLogItem.class, importLogItemVO);
		impLogConfirmation.setImportLogItem(impLogItemPojo);
		
		String newItemStatusCode = null;
		
		//Obteniendo las confirmaciones parciales:
		List<ImpLogConfirmation> impLogConfList = daoImpLogConfirmation.getImpLogConfirmationsByImpLogItemId(importLogItemVO.getId());
		Double totalConfirmed = 0D;
		Double pendingToConfirm = 0D;
		
		
		//Si la cantidad confirmada es mayor que la cantidad esperada, generar error:
		if(importLogItemVO.getConfirmedQuantity().doubleValue() > importLogItemVO.getAmountExpected().doubleValue()){
			log.debug("Error de validaciÃ³n para el Ã­tem de registro de importaciÃ³n con id: "+importLogItemVO.getId()
					+ ", La cantidad confirmada "+importLogItemVO.getConfirmedQuantity()
					+ " es mayor que la cantidad esperada: " + importLogItemVO.getAmountExpected());
			throw new BusinessException(ErrorBusinessMessages.CONFIRMED_QTY_EXCEEDS_THE_EXPECTED_QTY.getCode(), ErrorBusinessMessages.CONFIRMED_QTY_EXCEEDS_THE_EXPECTED_QTY.getMessage());
		}
		
		//Si no existen confirmaciones parciales previamente:
		
		impLogConfirmation.setConfirmedQuantity(importLogItemVO.getConfirmedQuantity());
		if(! impLogConfList.isEmpty()){
			totalConfirmed = getTotalConfirmed(impLogConfList);
			pendingToConfirm = importLogItemVO.getAmountExpected() - (totalConfirmed + importLogItemVO.getConfirmedQuantity());
			validatePendingToConfirQty(pendingToConfirm, totalConfirmed, importLogItemVO);
			
		}
		
		//Si la cantidad confirmada es igual a la cantidad esperada:
		if(importLogItemVO.getAmountExpected().doubleValue() == importLogItemVO.getConfirmedQuantity().doubleValue()){
			
			impLogConfirmation.setPendQuantity(0D);
			
			if(totalConfirmed != 0D){
				impLogConfirmation.setConfirmedQuantity(pendingToConfirm);
			}
			
			//Obteniendo el estado con el que quedara el importLogItem
			newItemStatusCode = CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity();
		
		//Si la cantidad confirmada es diferente a la cantidad esperada	
		}else{
			//Sumando las confirmaciones parciales a la que se reporta:
			totalConfirmed += importLogItemVO.getConfirmedQuantity();
			//Si la suma de las confirmaciones parciales es mayor que la esperada:
			if(totalConfirmed.doubleValue() > importLogItemVO.getAmountExpected()){
				log.debug("Error de validaciÃ³n para el Ã­tem de registro de importaciÃ³n con id: "+importLogItemVO.getId()
						+ ", La suma de las confirmaciones parciales "+totalConfirmed
						+ " es mayor que la cantidad esperada: " + importLogItemVO.getAmountExpected());
				throw new BusinessException(ErrorBusinessMessages.CONFIRMED_QTY_EXCEEDS_THE_EXPECTED_QTY.getCode(), ErrorBusinessMessages.CONFIRMED_QTY_EXCEEDS_THE_EXPECTED_QTY.getMessage());
			}
			
			if(totalConfirmed.doubleValue() == importLogItemVO.getAmountExpected().doubleValue()){
				newItemStatusCode = CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity();
				impLogConfirmation.setPendQuantity(0D);
			}else{
				newItemStatusCode = CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getCodeEntity();
				pendingToConfirm = importLogItemVO.getAmountExpected().doubleValue() - totalConfirmed.doubleValue();
				impLogConfirmation.setPendQuantity(pendingToConfirm);
			}
		}
		if(log.isDebugEnabled()){
			log.debug("Se consultarÃ¡ el estado del item con el nuevo cÃ³digo: " + newItemStatusCode);
		}
		ItemStatus itemStatus1 = daoItemStatus.getItemStatusByCode(newItemStatusCode);
		UtilsBusiness.assertNotNull(itemStatus1, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		impLogItemPojo.setItemStatus(itemStatus1);
		impLogConfirmation.setImportLogItem(impLogItemPojo);
		return impLogConfirmation;
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#reportInconsistentItemsAndByCountry(java.util.List, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void reportInconsistentItems(List<ImportLogItemVO> inconsistentLogItems, String comment, Long incTypeId,	Long userId) throws BusinessException {
		log.debug("== Inicia reportInconsistentItemsAndByCountry/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(inconsistentLogItems, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(comment, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(incTypeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            //Modificando el estado del registro de importaciÃ³n a inconsistente
        	String newStatusImportLog = CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity();
        	ImportLogStatus importLogStatus = daoImportLogStatus.getImportLogStatusByCode(newStatusImportLog);
        	if(inconsistentLogItems.isEmpty()){
        		log.debug("Error: No se enviaron registros de Ã­tems de registro de importaciÃ³n para reportar las inconsistencias");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	Long importLogId = inconsistentLogItems.get(0).getImportLog().getId();
        	ImportLog impLog = daoImportLog.getImportLogByID(importLogId);
        	UtilsBusiness.assertNotNull(impLog, ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getMessage());
        	
        	impLog.setImportLogStatus(importLogStatus);
        	daoImportLog.updateImportLog(impLog);
        	if ( impLog.getImportLogStatus() != null && impLog.getImportLogStatus().getStatusCode()!=null )
        		createImportLogItemModification(impLog.getUser().getId(), impLog, impLogModBusiness.importLogStatusToImportLogModification( impLog.getImportLogStatus() ) );
        	
        	//Actualizando los estados de los items:
        	String newItemStatusCode = CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getCodeEntity();
        	ItemStatus newStatus = daoItemStatus.getItemStatusByCode(newItemStatusCode);
        	UtilsBusiness.assertNotNull(newStatus, ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getMessage());
        	ImportLogItem importLogItemPojo = null;
        	
        	for (ImportLogItemVO importLogItemVO : inconsistentLogItems) {
        		importLogItemPojo = daoImportLogItem.getImportLogItemByID(importLogItemVO.getId());
        		importLogItemPojo.setItemStatus(newStatus);
        		daoImportLogItem.updateImportLogItem(importLogItemPojo);
        		updateElementStatus(importLogItemVO);
        		//Registrando la inconsistencia:
            	reportImpLogInconsistency(impLog, comment, incTypeId, userId, importLogItemVO.getId() );
			}
        	
        	//2010-07-28 Invocar caso de uso INV -08 para informar al Analista de LogÃ­stica informando novedad de inconsistencia en el Registro de ImportaciÃ³n
        	User user = daoUser.getUserById(userId);
        	UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	UserVO userVo = UtilsBusiness.copyObject(UserVO.class, user);
        	
        	Warehouse logisticOperatorQtyCtrlWh = getLogisticOperatorWarehouse(impLog.getDealer().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
        	sendEmailToLogisticOperator(impLog.getId(), userVo, EmailTypesEnum.INCONSISTENCY_IN_IMPORT_REGISTRY.getEmailTypecode(), " Inconsistencia en registro de importación ", logisticOperatorQtyCtrlWh);
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n reportInconsistentItemsAndByCountry/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina reportInconsistentItemsAndByCountry/ImportLogItemBusinessBean ==");
        }
	}


	/**
	 * Metodo: reporta una inconsistencia en el registro de importaciÃ³n
	 * @param impLog 
	 * @param comment <tipo> <descripcion>
	 * @throws PropertiesException 
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 * @throws BusinessException 
	 * @author jjimenezh 
	 * @param userId2 
	 */
	private void reportImpLogInconsistency(ImportLog impLog, String comment, Long incTypeId, Long userId, Long itemId) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		ImportLogInconsistency impLogInconsistency = new ImportLogInconsistency();
    	impLogInconsistency.setComments(comment);
    	impLogInconsistency.setImportLog(impLog.getId());
    	impLogInconsistency.setInconsistencyDate(new Date());
    	InconsistencyStatus inconsistencyStatus = daoInconsistencyStatus.getInconsistencyStatusByCode(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity());
    	UtilsBusiness.assertNotNull(inconsistencyStatus, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity() + " No existe estado de inconsistencia con el código: " + CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity());
    	impLogInconsistency.setInconsistencyStatus(inconsistencyStatus);
    	InconsistencyType inconsistencyType = daoInconsistencyType.getInconsistencyTypeByID(incTypeId);
    	UtilsBusiness.assertNotNull(inconsistencyType, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No existe el tipo de inconsistencia con el id: " + incTypeId);
    	impLogInconsistency.setInconsistencyType(inconsistencyType);
    	User usr = daoUser.getUserById(userId);
    	impLogInconsistency.setUser(usr.getId());
    	impLogInconsistency.setImportLogItem(itemId);
    	daoImportLogInconsistency.createImportLogInconsistency(impLogInconsistency);
    	
		
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ImportLogItemResponse getImportLogItemsByImportLogWithConfirmations(Long idImportLog,RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getImportLogItemsByImportLogWithConfirmations/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(idImportLog, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	ImportLogItemResponse response = daoImportLogItem.getImpLogItemsByIndividualElementsSerialized(idImportLog,requestCollInfo); 
            List<ImportLogItem> objPojo = response.getImportLogItems();
            List<ImportLogItemVO> items = UtilsBusiness.convertList( objPojo,ImportLogItemVO.class);            
            ImpLogConfirmation impLogConfPojo;
            List<ImportLogItemVO> importLogItems = new LinkedList<ImportLogItemVO>();
            
            for (ImportLogItemVO importLogItemVO : items) {            	
            	impLogConfPojo = daoImpLogConfirmation.getImpLogConfirmationByImpLogItemId(importLogItemVO.getId());            	
            	importLogItemVO.setImpLogConfirmation( UtilsBusiness.copyObject( ImpLogConfirmationVO.class, impLogConfPojo) );
            	List<Serialized> list = daoSerialized.getSerializedByElementId(importLogItemVO.getElement().getId());
            	if(list != null && !list.isEmpty()){
            		importLogItemVO.setElementVO(UtilsBusiness.copyObject(ElementVO.class,importLogItemVO.getElement()));
            		importLogItemVO.getElementVO().setSerializedElement(UtilsBusiness.copyObject(SerializedVO.class,list.get(0)));
            	}
            	importLogItems.add(importLogItemVO);
			}
            response.setImportLogItemsVO(importLogItems);
            response.setImportLogItems(null);
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getImportLogItemsByImportLogWithConfirmations/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getImportLogItemsByImportLogWithConfirmations/ImportLogItemBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#confirmSerializedElementsImportLogAndByCountry(java.lang.Long, java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmSerializedElementsImportLog(Long pImpLogId, List<ImportLogItemVO> pImpLogItems, Dealer dealer) throws BusinessException {
		
		log.debug("== Inicia confirmSerializedElementsImportLog/ImportLogItemBusinessBean ==");
		try{	
			if( pImpLogId == null ){
				log.debug("== El id del registro de importacion es requerido ==");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
	        UtilsBusiness.assertNotNull(pImpLogItems, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        
	        if( pImpLogItems.isEmpty() ){
	        	log.debug("== La lista de elementos para confirmar se encuentra vacia ==");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());			
	        }
	        
	        ImportLog impLog = daoImportLog.getImportLogByID(pImpLogId);
        	UtilsBusiness.assertNotNull(impLog, ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getMessage());
        	
        	try{
        		long dealerCountryId = dealer.getPostalCode().getCity().getState().getCountry().getId();
        	}catch(Exception e){
        		dealer = daoDealer.getDealerByID(dealer.getId());
        	}
        	
        	//Se consulta la bodega de calidad del operador logisitco para poner los elementos en esta bodega
        	List<Warehouse> whs = daoWareHouse.getWhByDealerTypeAndWhTypeCodeAndCountry(CodesBusinessEntityEnum.CODE_DEALER_TYPE_LOGISTIC_OPERATOR.getCodeEntity(), dealer.getPostalCode().getCity().getState().getCountry().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_CALIDAD.getCodeEntity());
    		Warehouse wareHousePojo;
    		if(whs.isEmpty()){
    			throw new BusinessException( ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
    		}
    		wareHousePojo = whs.get(0);
        	
        	for(int i = 0 ; i < pImpLogItems.size() ; i++){

        		//Toma el elemento individual del registro de importacion a confirmar	        
        		ImportLogItemVO importLogItemVO = pImpLogItems.get(i);	        
        		UtilsBusiness.assertNotNull(importLogItemVO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

        		//Consulta el usuario que realiza la confirmacion
        		User user = daoUser.getUserById(importLogItemVO.getImpLogConfirmation().getUser().getId());
        		UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.USER_NOT_EXIST.getCode(), ErrorBusinessMessages.USER_NOT_EXIST.getMessage());

        		importLogItemVO.getImpLogConfirmation().setUser(user);
        		ImpLogConfirmation impLogConfPojo = importLogItemVO.getImpLogConfirmation();   		


        		//Validaciones de las cantidades del registro de importacion para ser confirmado
        		validateAmountInportLogConfirmed(importLogItemVO);

        		//Realiza las validaciones de las reglas de negocio del elemento serializado
        		if( validationSerializedElementsConfirmation(importLogItemVO.getElement(),importLogItemVO.getElementVO(), pImpLogId).booleanValue()) {

        			ImportLogItem impLogItemPojo = UtilsBusiness.copyObject(ImportLogItem.class, importLogItemVO);

        			//Crea o actualiza el registro de confirmacion
        			createIncinsistencyImportLogSerialized(impLogConfPojo);

        			//valida que los parametros requeridos del elemento serialiado.
        			if( !BusinessRuleValidationManager.getInstance().isValid( UtilsBusiness.copyObject( SerializedVO.class,importLogItemVO.getElementVO().getSerializedElement() ) ) ){
        				log.error("== Error en la Capa de Negocio debido parametros requeridos nulos ==");
        				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        			}
        			//Se actualizan los elementos serializados con los seriales y RID   
        			Long elementId = importLogItemVO.getElementVO().getSerializedElement().getElement().getId();
        			importLogItemVO.getElementVO().getSerializedElement().setElementId( elementId );
        			Serialized serilizedInBD = daoSerialized.getSerializedByID(importLogItemVO.getElementVO().getSerializedElement().getElementId());
        			serilizedInBD.setIrd(importLogItemVO.getElementVO().getSerializedElement().getIrd());
        			serilizedInBD.setSerialCode(importLogItemVO.getElementVO().getSerializedElement().getSerialCode());
        			daoSerialized.updateSerialized( serilizedInBD );

        			//Se cambia el estado del elemento individual
        			ItemStatus itemStatus = new ItemStatus();
        			if( importLogItemVO.getImpLogConfirmation().getPendQuantity() > 0 )
        				itemStatus.setId( CodesBusinessEntityEnum.ITEM_STATUS_PARTIALLY_CONFIRMED.getIdEntity(ItemStatus.class.getName()) );
        			else
        				itemStatus.setId( CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getIdEntity(ItemStatus.class.getName()) );
        			impLogItemPojo.setItemStatus(itemStatus);
        			daoImportLogItem.updateImportLogItem(impLogItemPojo);

        			//Se actualiza el estado del elemento serializado
        			Element elementSerialized = impLogItemPojo.getElement();
        			ElementStatus elementStatus = new ElementStatus();
        			if( importLogItemVO.getImpLogConfirmation().getPendQuantity() > 0 )
        				elementStatus.setId(CodesBusinessEntityEnum.ELEMENT_STATUS_T01.getIdEntity(ElementStatus.class.getName()));   
        			else
        				elementStatus.setId(CodesBusinessEntityEnum.ELEMENT_STATUS_S04.getIdEntity(ElementStatus.class.getName()));
        			elementSerialized.setElementStatus(elementStatus);
        			elementSerialized.setCountry(impLog.getCountry());
        			daoElement.updateElement(elementSerialized);       		
        		}  
        		
        	}
        	
        	//Si todos los elementos serializados del registro de importacion ya fueron confirmados
    		//se cambia el estado del registro de importacion a Recepcionado
    		//si faltan elementos por confirmar se cambia a Confirmado Parcial
    		//updateStatusEntertaimentImpLog(impLog);
        	updateImportLogByConfirmationForSerialize(impLog.getId());
    		
    		//Usa el caso de uso INV-08 para informar al Analista de LogÃ­stica que el Registro de ImportaciÃ³n ha sido recepcionado.
    		//Se consulta la bodega asociada a un operador logistico para el paÃ­s enviado
    		//Por regla de negocio un paÃ­s tiene un operador logistico
    		String observation = EmailTypesEnum.REGISTER_IMPORT_LOG_HAS_BEEN_CONFIRMED.getDescription();
    		String newsType = EmailTypesEnum.REGISTER_IMPORT_LOG_HAS_BEEN_CONFIRMED.getEmailTypecode();
    		sendMailConfirmationImportLogNotification( wareHousePojo, newsType ,observation ,impLog.getId());
			
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n confirmSerializedElementsImportLog/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina confirmSerializedElementsImportLog/ImportLogItemBusinessBean ==");
	    }
	}
	
	private void updateImportLogByConfirmationForSerialize(Long importLogId) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException {
		//Iterando de nuevo sobre los elementos para ver si todos fueron confirmados:
		//operacion paginada, se envia en null el parametro ya que no es necesario paginar en este punto.
		RequestCollectionInfo requestCollInfo = null;
    	List<ImportLogItem> allImportLogItems = daoImportLogItem.getImportLogItemsByImportLog(importLogId, requestCollInfo).getImportLogItems();
    	boolean allRecepted = true;
    	for (ImportLogItem importLogItem : allImportLogItems) {
			if(! importLogItem.getItemStatus().getStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())){
				allRecepted = false;
				break;
			}
		}
    	ImportLog impLog = daoImportLog.getImportLogByID(importLogId);
    	String newImportLogStatusCode = null;
    	if(allRecepted){
    		newImportLogStatusCode = CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity();
    	}else{
    		newImportLogStatusCode = CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity();
    	}
    	
    	ImportLogStatus newImpLogstatus = daoImportLogStatus.getImportLogStatusByCode(newImportLogStatusCode);
    	UtilsBusiness.assertNotNull(newImpLogstatus, ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_STATUS_IS_NOT_VALID.getMessage());
    	
    	impLog.setImportLogStatus(newImpLogstatus);
    	daoImportLog.updateImportLog(impLog);
    	if ( impLog.getImportLogStatus() != null && impLog.getImportLogStatus().getStatusCode()!=null )
    		createImportLogItemModification(impLog.getUser().getId(), impLog, impLogModBusiness.importLogStatusToImportLogModification( impLog.getImportLogStatus() ) );
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#reportInconsistentSerializedImpLogElementsAndByCountry(java.lang.Long, java.util.List, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void reportInconsistentSerializedImpLogElements(Long pImpLogId, List<ImportLogItemVO> impLogItems, String comment,Long pUserId, Long incType,Long country) throws BusinessException{
		
		log.debug("== Inicia reportInconsistentSerializedImpLogElementsAndByCountry/ImportLogItemBusinessBean ==");		
		try{
			
			ImportLog impLog = daoImportLog.getImportLogByID(pImpLogId);
        	UtilsBusiness.assertNotNull(impLog, ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getCode(), ErrorBusinessMessages.IMPORT_LOG_NOT_EXIST_OR_STATUS_INVALID.getMessage());
        	        	
			//Validaciones de parametros requeridos
        	if(impLog.getImportLogStatus().getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity() )){
        		log.error("== El Registro de Importacion con id "+impLog.getId()+" se encuentra en estado En Inconsistencia ==");
        		throw new BusinessException(ErrorBusinessMessages.INVENTORY_IMPORT_LOG_STATUS_IS_INCONSISTENCY.getCode(),ErrorBusinessMessages.INVENTORY_IMPORT_LOG_STATUS_IS_INCONSISTENCY.getMessage());
        	}
        	if(impLog.getImportLogStatus().getStatusCode().equals( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getCodeEntity() ) ){
        		log.error("== El Registro de Importacion con id "+impLog.getId()+" se encuentra en estado Recepcionado ==");
        		throw new BusinessException(ErrorBusinessMessages.INVENTORY_INCONSISTENCY_IS_INVALID.getCode(),ErrorBusinessMessages.INVENTORY_INCONSISTENCY_IS_INVALID.getMessage());        		
        	}
			if( pImpLogId == null ){
				log.debug("== El id del registro de importacion es requerido ==");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			if( comment == null || comment.equals("") ){
				log.debug("== El comentario de la inconsistencia es requerida ==");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			if( pUserId == null ){
				log.debug("== El usuario de la inconsistencia es requerido ==");
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
					
			
        	if(impLogItems.isEmpty()){
        		log.debug("== No se enviaron elementos individaules del registro de importacion para reportar la inconsistencia ==");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	//Toma el elemento individual del registro de importacion a confirmar	        
	        ImportLogItemVO importLogItemVO = impLogItems.get(0);	
	        ImpLogConfirmation impLogConfPojo = importLogItemVO.getImpLogConfirmation();  
	        
			//Crea la inconsistencia
        	ImportLogInconsistency impLogInconsistency = new ImportLogInconsistency();
        	impLogInconsistency.setComments(comment);
        	impLogInconsistency.setImportLog(impLog.getId());
        	impLogInconsistency.setInconsistencyDate(new Date());
        	InconsistencyStatus inconsistencyStatus = new InconsistencyStatus();
        	Long incStatusId = CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getIdEntity(InconsistencyStatus.class.getName());
        	inconsistencyStatus.setId(incStatusId);
        	impLogInconsistency.setInconsistencyStatus(inconsistencyStatus);
        	InconsistencyType inconsistencyType = new InconsistencyType();        	
        	inconsistencyType.setId(incType);
        	impLogInconsistency.setInconsistencyType(inconsistencyType);
        	User usr = daoUser.getUserById(pUserId);
        	impLogInconsistency.setUser(usr.getId());        	
        	daoImportLogInconsistency.createImportLogInconsistency(impLogInconsistency);
        	
        	//Cambia el estado del item del registro de importacion a "En Inconsistencia"
        	ImportLogItem importLogItem = null;
        	ItemStatus itemStatus = new ItemStatus();
    		itemStatus.setId( CodesBusinessEntityEnum.ITEM_STATUS_INCONSISTENCY_PROCESS.getIdEntity(ItemStatus.class.getName()) );    		

    		importLogItem = UtilsBusiness.copyObject(ImportLogItem.class, importLogItemVO);
    		importLogItem.setItemStatus(itemStatus);
    		daoImportLogItem.updateImportLogItem(importLogItem);
			
    		//Crea o actualiza el registro de confirmacion
			createIncinsistencyImportLogSerialized(impLogConfPojo);
			
        	//Cambia el estado del registro de importacion a "En Inconsistencia"
        	ImportLogStatus impLogStatus = new ImportLogStatus();
        	impLogStatus.setId( CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getIdEntity(ImportLogStatus.class.getName()) );
        	impLog.setImportLogStatus(impLogStatus);
        	daoImportLog.updateImportLog(impLog);
        	if ( impLog.getImportLogStatus() != null && impLog.getImportLogStatus().getStatusCode()!=null )
        		createImportLogItemModification(pUserId, impLog, impLogModBusiness.importLogStatusToImportLogModification( impLog.getImportLogStatus() ) );
        	
        	//Usa el caso de uso INV-08 para informar al Analista de LogÃ­stica informando novedad 
        	//de inconsistencia en el Registro de ImportaciÃ³n
        	//Se consulta la bodega asociada a un operador logistico para el paÃ­s enviado
			//Por regla de negocio un paÃ­s tiene un operador logistico
			List<Warehouse> whs = daoWareHouse.getWhByDealerTypeAndCountry(CodesBusinessEntityEnum.CODE_DEALER_TYPE_LOGISTIC_OPERATOR.getCodeEntity(), usr.getCountry().getId());
			if(whs.size()==0){
				throw new BusinessException( ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
			}
			String observation = EmailTypesEnum.REGISTER_IMPORT_LOG_INCONSISTENCY.getDescription();
			String newsType = EmailTypesEnum.REGISTER_IMPORT_LOG_INCONSISTENCY.getEmailTypecode();
			sendMailConfirmationImportLogNotification( whs.get(0), newsType ,observation ,impLog.getId());
        	
		}catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n reportInconsistentSerializedImpLogElementsAndByCountry/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina reportInconsistentSerializedImpLogElementsAndByCountry/ImportLogItemBusinessBean ==");
	    }
	}
	
	/**	 
	 * Metodo: Crea un registro de confirmacion para
	 * CU INV 04
	 * el registro de importacion de elementos serializados
	 * @param impLogConfPojo ImpLogConfirmation
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	private void createIncinsistencyImportLogSerialized(ImpLogConfirmation impLogConfPojo) throws BusinessException{
		
		try{
			
			//valida que los parametros requeridos de la confirmacion.
			if(!BusinessRuleValidationManager.getInstance().isValid( UtilsBusiness.copyObject(ImpLogConfirmationVO.class,impLogConfPojo) )){
	            log.error("== Error en la Capa de Negocio debido parametros requeridos nulos ==");
	            throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	        }
			//solo puede ser confirmado un solo elemento serializado
			if( impLogConfPojo.getConfirmedQuantity() > 1 || impLogConfPojo.getConfirmedQuantity() < 0 ){
				log.error("== La cantidad confirmada no puede ser mayor a 1 y menor que 0 ==");
				throw new BusinessException(ErrorBusinessMessages.INVENTORY_ONLY_ONE_SERIALIZED_CONFIRMED.getCode(), ErrorBusinessMessages.INVENTORY_ONLY_ONE_SERIALIZED_CONFIRMED.getMessage());
			}			
			if( impLogConfPojo.getPendQuantity() > 1 || impLogConfPojo.getPendQuantity() < 0 ){
				log.error("== La cantidad pendiente no puede ser mayor a 1 y menor que 0 ==");
				throw new BusinessException(ErrorBusinessMessages.INVENTORY_ONLY_ONE_SERIALIZED_PENDING.getCode(), ErrorBusinessMessages.INVENTORY_ONLY_ONE_SERIALIZED_PENDING.getMessage());
			}
			
			//Si el id del registro de confirmacion es null se debe crear.
			if(impLogConfPojo.getId() == null){	        			
				daoImpLogConfirmation.createImpLogConfirmation(impLogConfPojo);
			//Si el id del registro de confirmacion no es null se actualiza.
			}else{
				daoImpLogConfirmation.updateImpLogConfirmation(impLogConfPojo);
			}
		}catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n createIncinsistencyImportLogSerialized/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina createIncinsistencyImportLogSerialized/ImportLogItemBusinessBean ==");
	    }
	}
	/**
	 * Method: Valida las cantidades del registro de confirmacion
	 * CU INV 04
	 * @param importLogItemVO ImportLogItemVO -
	 * @throws BusinessException
	 */
	private void validateAmountInportLogConfirmed( ImportLogItemVO importLogItemVO ) throws BusinessException{
		log.debug("== Inicia validateAmountInportLogConfirmed/ImportLogItemBusinessBean ==");
		try{
			//Se consulta el item del emport log para validar las cantidades
			ImportLogItem importlogItem = daoImportLogItem.getImportLogItemByID( importLogItemVO.getId() );
			UtilsBusiness.assertNotNull(importlogItem, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			Double cantidadEsperada = importlogItem.getAmountExpected();
			Double cantidadPendiente = importLogItemVO.getImpLogConfirmation().getPendQuantity();
			Double cantidadConfirmada = importLogItemVO.getImpLogConfirmation().getConfirmedQuantity();
			Double cantidadTotal = cantidadConfirmada + cantidadPendiente;
			if( cantidadConfirmada > cantidadEsperada ){
				log.error("== La cantidad confirmada no puede ser mayor a la cantidad esperada ==");
				throw new BusinessException(ErrorBusinessMessages.INVENTORY_AMOUNT_EXPECTED_CONFIRMED_SERIALIZED.getCode(), ErrorBusinessMessages.INVENTORY_AMOUNT_EXPECTED_CONFIRMED_SERIALIZED.getMessage());
			}
			if( cantidadTotal > cantidadEsperada ){
				log.error("== La cantidad confirmada mas la cantidad pendiente no puede ser mayor a la cantidad esperada ==");
				throw new BusinessException(ErrorBusinessMessages.INVENTORY_AMOUNT_PENDING_CONFIRMED_SERIALIZED.getCode(), ErrorBusinessMessages.INVENTORY_AMOUNT_PENDING_CONFIRMED_SERIALIZED.getMessage());
			}
		}catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n validateAmountInportLogConfirmed/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina validateAmountInportLogConfirmed/ImportLogItemBusinessBean ==");
	    }
	}
	
	/**
	 * Metodo: Permite enviar un email al operador logistico indicando 
	 * la confirmacion del registro de importacion
	 * CU INV 04
	 * @param wareHouse Warehouse - Bodega asociada al operador logistico
	 * @param String newsType - tipo de novedad
	 * @param String observation - Observacion del email a enviar
	 * @throws BusinessException en caso de error al enviar email al operador logistico
	 * @throws DAOServiceException en caso de error al enviar email al operador logistico
	 * @throws DAOSQLException en caso de error al enviar email al operador logistico
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void sendMailConfirmationImportLogNotification(Warehouse wareHouse, String newsType, String observation,Long importLogId) throws BusinessException {
		log.debug("== Inicia sendMailConfirmationImportLogNotification/ImportLogItemBusinessBean ==");
		try{
			SendEmailDTO email = new SendEmailDTO();
	
			email.setNewsType( newsType );
			email.setNewsDocument( importLogId.toString() );
			email.setNewsObservation( observation );
			email.setNewsSourceUser( wareHouse.getDealerId().getDealerName() );
			List<String> recipients = new ArrayList<String>();
			recipients.add( wareHouse.getResponsibleEmail());
			email.setRecipient( recipients );
			businessEmailType.sendEmailByEmailTypeCodeAsic( email, wareHouse.getCountry().getId());
		}catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n sendMailConfirmationImportLogNotification/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina sendMailConfirmationImportLogNotification/ImportLogItemBusinessBean ==");
	    }
	}
	
	/**
	 * 
	 * Metodo: Cambia el estado del registro de importacion a "Recepcionado",
	 * si todos loe elementos serializados fueron confirmados, si faltan
	 * elementos por confirmar cambia el estado a "Confirmado Parcial"
	 * CU Inv 04
	 * @param ImportLog pImpLog, Registro de Importacion
	 * @author jalopez
	 * @throws BusinessException 
	 */
	private void updateStatusEntertaimentImpLog(ImportLog pImpLog) throws BusinessException{
		 log.debug("== Inicia updateStatusEntertaimentImpLog/ImportLogItemBusinessBean ==");
		try{
			ImportLog impLog = pImpLog;
			List<ImportLogItem> impLogItems =  daoImportLogItem.getImpLogItemsByIndividualElements(impLog.getId());			
			ImportLogStatus implogStatus = new ImportLogStatus();		
			
			if( impLogItems.isEmpty() ){				    	        			
				implogStatus.setId(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_RECEIVED.getIdEntity(ImportLogStatus.class.getName()));
				impLog.setImportLogStatus(implogStatus);
			}else{
				implogStatus.setId(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getIdEntity(ImportLogStatus.class.getName()));
				impLog.setImportLogStatus(implogStatus);
			}
			
			daoImportLog.updateImportLog( impLog );
			if ( impLog.getImportLogStatus() != null && impLog.getImportLogStatus().getStatusCode()!=null )
				createImportLogItemModification(impLog.getUser().getId(), impLog, impLogModBusiness.importLogStatusToImportLogModification( impLog.getImportLogStatus() ) );
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n updateStatusEntertaimentImpLog/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina updateStatusEntertaimentImpLog/ImportLogItemBusinessBean ==");
	    }
	}
	
	/**
	 * 
	 * Metodo: Realiza las validaciones de las reglas de negocio
	 * para confirmar elementos serializados de un registos de 
	 * importaciÃ³n.
	 * CU Inv 04
	 * @param pElementVO ElementVO - Elemento serializado
	 * @param Element pElement - Elemento individual
	 * @param Long pImpLogId - id del registro de importacion
	 * @return Boolean isCorrect, true si la validacin es correcta.
	 * @author jalopez
	 * @throws BusinessException 
	 */
	private Boolean validationSerializedElementsConfirmation(Element pElement,ElementVO pElementVO, Long pImpLogId) throws BusinessException {
		log.debug("== Inicia validationSerializedElementsConfirmation/ImportLogItemBusinessBean ==");
		try{
			Boolean isCorrect = Boolean.valueOf(true);
			//Se consulta el elemento enviado para verificar que el estado sea diferente de confirmado
			Element element  = daoElement.getElementByID( pElement.getId() );
			if ( element.getElementStatus().getElementStatusCode().equals(CodesBusinessEntityEnum.ELEMENT_STATUS_S04.getCodeEntity()) ){
				isCorrect = Boolean.valueOf(false);
				log.error("== El elemento con id "+element.getId()+" ya ha sido confirmado, no se puede modificar. ==");
				throw new BusinessException(ErrorBusinessMessages.INVENTORY_ELEMENT_STATUS_IS_CONFIRMED.getCode(),ErrorBusinessMessages.INVENTORY_ELEMENT_STATUS_IS_CONFIRMED.getMessage());
			}	
			Serialized serializedPojo = daoSerialized.getSerializedByID(element.getId());
			//El serial es requerido
			if(pElementVO.getSerializedElement() == null && serializedPojo != null){
				pElementVO.setSerializedElement(UtilsBusiness.copyObject(SerializedVO.class, serializedPojo));
			}
			
			if( pElementVO.getSerializedElement().getSerialCode() == null || pElementVO.getSerializedElement().getSerialCode().equals("") ){
				if(serializedPojo != null){
					pElementVO.setSerializedElement(UtilsBusiness.copyObject(SerializedVO.class, serializedPojo));
				}else{
					isCorrect = Boolean.valueOf(false);
					log.error("== El Serial para elemento con id "+pElementVO.getId()+" es requerido ==");				
					throw new BusinessException(ErrorBusinessMessages.INVENTORY_SERIAL_RID_REQUIRED.getCode(), ErrorBusinessMessages.INVENTORY_SERIAL_RID_REQUIRED.getMessage());
				}
			}				
			
			//solo puede existir un elemento serializado con el mismo serial para el registo de importacion
			//operacion paginada, se envia en null el parametro ya que no es necesario paginar en este punto.
			RequestCollectionInfo requestCollInfo = null;
			List<ImportLogItem> importLogItems = daoImportLogItem.getImportLogItemsByImportLog(pImpLogId, requestCollInfo).getImportLogItems();
    		for (ImportLogItem importLogItem : importLogItems) {
    			List<Serialized> serializeds = daoSerialized.getSerializedByElementIdBySerialCode( importLogItem.getElement().getId(), pElementVO.getSerializedElement().getSerialCode() );
    			
    			if ( !serializeds.isEmpty() ){
    				for (Serialized serialized : serializeds) {
    					//solo si el serial existe para otro elemento no para el mismo
						if( serialized.getElement().getId().longValue() != importLogItem.getElement().getId().longValue() ){
							log.error("== El elemento con id "+element.getId()+" ya tiene un elemento serializado con codigo de serial "+pElementVO.getSerializedElement().getSerialCode()+"  ==");
		    				throw new BusinessException( ErrorBusinessMessages.INVENTORY_ONLY_ELEMENT_SERIALIZED_IMPORT_LOG.getCode(), ErrorBusinessMessages.INVENTORY_ONLY_ELEMENT_SERIALIZED_IMPORT_LOG.getMessage() );
						}
					}    				
    			}
			} 
			
			return isCorrect;
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n validationSerializedElementsConfirmation/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina validationSerializedElementsConfirmation/ImportLogItemBusinessBean ==");
	    }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#confirmSerializedAndNotSerializedImportLogItems(java.util.List, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmSerializedAndNotSerializedImportLogItems(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, Dealer dealer)
			throws BusinessException {
		log.debug("== Inicia confirmSerializedAndNotSerializedImportLogItems/ImportLogItemBusinessBean ==");
		try{
			UtilsBusiness.assertNotNull(impLogItems2Confirm, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(logisticOperatorDealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(userId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Long importLogId = 0L;
			ImportLogItem impLogItem = null;
			List<ImportLogItemVO> notSerialized = new ArrayList<ImportLogItemVO>();
			List<ImportLogItemVO> serialized = new ArrayList<ImportLogItemVO>();
			for (ImportLogItemVO importLogItemVO : impLogItems2Confirm) {
				if(importLogId.longValue() == 0L){
					importLogId = importLogItemVO.getImportLog().getId();
				}
				impLogItem = daoImportLogItem.getImportLogItemByID(importLogItemVO.getId());
				if(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity().equalsIgnoreCase(impLogItem.getElement().getIsSerialized())){
					serialized.add(importLogItemVO);
				}else if (CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity().equalsIgnoreCase(impLogItem.getElement().getIsSerialized())){
					notSerialized.add(importLogItemVO);
				}
			}
			if( !notSerialized.isEmpty() )
				confirmNotSerializedImportLogItems(notSerialized, userId, logisticOperatorDealerId, true);
			if( !serialized.isEmpty() )
				confirmSerializedElementsImportLog(importLogId, serialized, dealer);
			if( notSerialized.isEmpty() && serialized.isEmpty() ){
				if( importLogId != null && importLogId > 0L ){
					ImportLog importLog = daoImportLog.getImportLogByID(importLogId);
					updateStatusEntertaimentImpLog(importLog);
				}
			}
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n confirmSerializedAndNotSerializedImportLogItems/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina confirmSerializedAndNotSerializedImportLogItems/ImportLogItemBusinessBean ==");
	    }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal#updateImportLogItemByImportLogId(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateImportLogItemByImportLogId(Long importLogId,
			String newImportLogItemStatusCode) throws BusinessException {
		log.debug("== Inicia updateImportLogItemByImportLogId/ImportLogItemBusinessBean ==");
		try{
			//operacion paginada, se envia en null el parametro ya que no es necesario paginar en este punto.
			RequestCollectionInfo requestCollInfo = null;
			List<ImportLogItem> items = daoImportLogItem.getImportLogItemsByImportLog(importLogId,requestCollInfo).getImportLogItems();
			ItemStatus newStatus = daoItemStatus.getItemStatusByCode( newImportLogItemStatusCode );
			if( newStatus == null ){
				log.debug("No se obtuvo el nuevo estado del item");
				throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
			}
			if( items != null ){
				for(ImportLogItem item : items){
					item.setItemStatus(newStatus);
					daoImportLogItem.updateImportLogItem(item);
				}
			}
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n updateImportLogItemByImportLogId/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina updateImportLogItemByImportLogId/ImportLogItemBusinessBean ==");
	    }
		
	}


	@Override 
	public void confirmSerializedElementsImportLog(Long pImpLogId,
			List<ImportLogItemVO> pImpLogItems, Long logisticOpId)
			throws BusinessException {

		
	}	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateQuantityItemImportLogNotSerializedElements(List <ImportLogItemVO> importLogItemsVO)  throws BusinessException {
		log.debug("== Inicia updateQuantityItemImportLog/ImportLogItemBusinessBean ==");
		try{
			
			for (ImportLogItemVO importLogItemVO: importLogItemsVO){
				//Actualizar Import Log Item
				UtilsBusiness.assertNotNull(importLogItemVO.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
				ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByID(importLogItemVO.getId() );
				importLogItem.setAmountExpected(importLogItemVO.getAmountExpected());
				daoImportLogItem.updateImportLogItem(importLogItem);
				//Actualizar not Serialized
				NotSerialized notSerialized = daoNotSerialized.getNotSerializedByID(importLogItem.getElement().getId());
				notSerialized.setInitialQuantity(importLogItemVO.getAmountExpected());
				daoNotSerialized.updateNotSerialized(notSerialized);
				//No modifica en la bodega porque ya se realizaron movimeintos
				//y lo que esta actualmente es un historial. Esto para el caso de 
				//la confirmacion parcial.
			}
			
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operaciÃ³n updateQuantityItemImportLog/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina updateQuantityItemImportLog/ImportLogItemBusinessBean ==");
	    }
				
		
	}
	
	/**
	 * Metodo que trae el identificador de la Bodega, si no existe la ubicaciÃ³n de transito la crea de nuevo
	 * @param dealerId
	 * @param whTypeCode
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @throws BusinessException 
	 * @throws PropertiesException 
	 * @author waguilera
	 */
	private Warehouse getWareHouseOrCreateIfNotExists(Long dealerId, String whTypeCode) throws DAOSQLException, DAOServiceException, BusinessException, PropertiesException{
		log.debug("== Inicia getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
		try{
			Warehouse warehouseFind =  daoWareHouse.getWarehousesByDealerIdAndWhTypeCode(dealerId, whTypeCode);
			if (warehouseFind == null){
				Dealer actualDealer = daoDealer.getDealerByID(dealerId);
				Warehouse warehouseCreate = new Warehouse();
				Warehouse warehouseTemp = new Warehouse();
				warehouseTemp.setDealerId(new Dealer());
				warehouseTemp.getDealerId().setId(dealerId);
				warehouseTemp.setWhResponsible(ApplicationTextEnum.EMPTY.getApplicationTextValue());
				warehouseTemp.setResponsibleEmail(ApplicationTextEnum.EMPTY.getApplicationTextValue());
				warehouseTemp.setIsActive(CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity());
				warehouseTemp.setCountry(actualDealer.getPostalCode().getCity().getState().getCountry());
				WarehouseType whTypeTrans = this.businessWarehouseType.getWarehouseTypeByCode(CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity()); 
				warehouseCreate = this.businessWarehouse.createWarehouseTransit( UtilsBusiness.copyObject(WarehouseVO.class, warehouseTemp), UtilsBusiness.copyObject(WarehouseTypeVO.class, whTypeTrans));
				return warehouseCreate;
			}else{
				return warehouseFind;
			}
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina getWareHouseOrCreateIfNotExists/ImportLogItemBusinessBean ==");
	    }
		
	}

	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createElementSerializedForNewImportLog(ImportLogItemVO importLogItem, 
			ElementStatus elementStatus, ImportLog importLog, ItemStatus itemStatus, 
			boolean isSendStatus, boolean isNewItems, MovementElementDTO dtoGenerics, User user,
			Warehouse warehouseTarget)  throws BusinessException{
		log.debug("== Inicia createElementSerializedForImportLogByStatus/ImportLogItemBusinessBean ==");
		try{
			if(isNewItems){
				this.createElementSerializedForImportLog(importLogItem, elementStatus, importLog, itemStatus);
			}
			if(isSendStatus){
				this.setImportLogItemsSerializedInWarehouse(user, importLogItem, importLog, warehouseTarget, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(), itemStatus, dtoGenerics);
			}
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación createElementSerializedForImportLogByStatus/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina createElementSerializedForImportLogByStatus/ImportLogItemBusinessBean ==");
	    }
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createElementNotSerializedForNewImport(ImportLogItemVO importLogItem, 
			ElementStatus elementStatus, ImportLog importLog, ItemStatus itemStatus, 
			boolean isSendStatus, boolean isNewItems, MovementElementDTO dtoGenerics, User user,
			Warehouse warehouseTarget)  throws BusinessException{
		log.debug("== Inicia createElementSerializedForImportLogByStatus/ImportLogItemBusinessBean ==");
		try{
			if(isNewItems){
				this.createElementNotSerializedForImportLog(importLogItem, elementStatus, importLog, itemStatus);
			}
			if(isSendStatus){
				this.setImportLogItemsNotSerializedInWarehouse(user, importLogItem, importLog, warehouseTarget, CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(), itemStatus, dtoGenerics);
			}
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación createElementSerializedForImportLogByStatus/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina createElementSerializedForImportLogByStatus/ImportLogItemBusinessBean ==");
	    }
	}
	
	/**
	 * Operacion encargada de la creación de los elementos no serializados en el sistema, 
	 * crea el item del registro de importación  
	 * @param importLogItem
	 * @param elementStatus
	 * @param importLog
	 * @param itemStatus
	 * @throws BusinessException
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void createElementNotSerializedForImportLog(ImportLogItemVO importLogItem, ElementStatus elementStatus, ImportLog importLog, ItemStatus itemStatus) throws BusinessException{
		log.debug("== Inicia createElementNotSerializedForImportLog/ImportLogBusinessBean ==");
		try{  
			ElementVO elementTmp =  importLogItem.getElementVO();
			elementTmp.setElementStatus(elementStatus);
			elementTmp.setCountry(importLog.getCountry());
			this.elementBusinessBean.createNotSerializedElement(elementTmp, importLogItem.getNotSerializedVO(), importLog.getId() );
			importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, elementTmp.getElementType().getMeasureUnit()));
			importLogItem.setElement(UtilsBusiness.copyObject(Element.class, elementTmp));
			importLogItem.setImportLog(importLog);
			importLogItem.setItemStatus(itemStatus);
			daoImportLogItem.createImportLogItem(UtilsBusiness.copyObject(ImportLogItem.class,importLogItem));
			//Almacenar el valor moved Quantity, para ser almacenado en la bodega.
			NotSerializedVO notSerializedVO = importLogItem.getNotSerializedVO();
			notSerializedVO = assignMovedQuantity(notSerializedVO);
			notSerializedVO.setElement(elementTmp);
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación createElementNotSerializedForImportLog/ImportLogBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina createElementNotSerializedForImportLog/ImportLogBusinessBean ==");
	    }
	}
	
	/**
	 * Metodo encargado de asignar la cantidad a mover
	 * @param notSerializedVO
	 * @return
	 */
	private NotSerializedVO assignMovedQuantity(NotSerializedVO notSerializedVO){
		notSerializedVO.setMovedQuantity(notSerializedVO.getInitialQuantity());
		return notSerializedVO;
	}
	
	
	/**
	 * Operacion encargada de la creación de los elementos serializados en el sistema, 
	 * crea el item del registro de importación  
	 * @param importLogItem
	 * @param elementStatus
	 * @param importLog
	 * @param itemStatus
	 * @throws BusinessException
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	private void createElementSerializedForImportLog(ImportLogItemVO importLogItem, ElementStatus elementStatus, ImportLog importLog, ItemStatus itemStatus) throws BusinessException{
		log.debug("== Inicia createElementSerializedForImportLog/ImportLogBusinessBean ==");
		try{     
			ElementVO elementTmp =  importLogItem.getElementVO();
			ElementType elementType	= daoElementType.getElementTypeByID(elementTmp.getElementType().getId());
			ElementVO elementVO = null;
			if(!elementType.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity())){				
				throw new BusinessException(
						ErrorBusinessMessages.STOCK_IN408.getCode(),
						ErrorBusinessMessages.STOCK_IN408.getMessage());
			}
			elementTmp.setElementStatus(elementStatus);
			importLogItem.getSerializedVO().setElement(elementTmp);
			importLogItem.setElementVO(null);
			
			if(importLogItem.getSerializedVO().getSerialized()!=null){
				ElementVO elementTempLink = UtilsBusiness.copyObject(ElementVO.class, importLogItem.getSerializedVO().getSerialized().getElement());
				elementTempLink.setElementStatus(elementStatus);
				importLogItem.getSerializedVO().getSerialized().setElement(elementTempLink);
			}
			
			//Colocar el elemento tipo deco como principal en caso de que esten invertidos
			importLogItem.setSerializedVO(elementBusinessBean.orderLinkedElement(importLogItem.getSerializedVO()));
			
			//Crear al elemento vunculado si se recibe como parametro
			SerializedVO serializedLinked = UtilsBusiness.copyObject(SerializedVO.class, importLogItem.getSerializedVO().getSerialized());
			if(importLogItem.getSerializedVO().getSerialized()!=null&&importLogItem.getSerializedVO().getSerialCode()!=null){
				if ( !((serializedLinked.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity()) 
						&& importLogItem.getSerializedVO().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity()))
						||(serializedLinked.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity())
						&& importLogItem.getSerializedVO().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())))){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN365.getCode(),ErrorBusinessMessages.STOCK_IN365.getMessage());
				}
				boolean isValidLinked = false;
				boolean existDeco = false;
				boolean existSmarcard = false;
				if((serializedLinked.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity()))
					|| (importLogItem.getSerializedVO().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity()))){
					existSmarcard = true;
				}
				if((serializedLinked.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity()))
					|| (importLogItem.getSerializedVO().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equals(CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity()))){
					existDeco = true;
				}
				if(existDeco&& existSmarcard){
					isValidLinked = true;
				}
				
				if(!isValidLinked){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN365.getCode(),ErrorBusinessMessages.STOCK_IN365.getMessage());
				}
				
				
					
				if(!(serializedLinked.getElement().getElementType().getElementModel().getIsPrepaidModel().equals(importLogItem.getSerializedVO().getElement().getElementType().getElementModel().getIsPrepaidModel()))){
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN445.getCode(),ErrorBusinessMessages.STOCK_IN445.getMessage());
				}
				
				elementVO = UtilsBusiness.copyObject(ElementVO.class,serializedLinked.getElement());
				elementVO.setCountry(importLog.getCountry());
				this.elementBusinessBean.createSerializedElement(elementVO, serializedLinked);
			}
			
			SerializedVO serializedVO = importLogItem.getSerializedVO();
			serializedVO.setSerialized(serializedLinked);
			
			elementVO = UtilsBusiness.copyObject(ElementVO.class, serializedVO.getElement());
			elementVO.setCountry(importLog.getCountry());
			this.elementBusinessBean.createSerializedElement(elementVO, serializedVO);
			
			importLogItem.setMeasureUnit(UtilsBusiness.copyObject(MeasureUnit.class, serializedVO.getElement().getElementType().getMeasureUnit()));				
			importLogItem.setElement(UtilsBusiness.copyObject(Element.class, serializedVO.getElement()));
			importLogItem.setImportLog(importLog);
			importLogItem.setItemStatus(itemStatus);
			importLogItem.setAmountExpected(1D);
			daoImportLogItem.createImportLogItem(UtilsBusiness.copyObject(ImportLogItem.class,importLogItem));
	    } catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación createElementSerializedForImportLog/ImportLogBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina createElementSerializedForImportLog/ImportLogBusinessBean ==");
	    }
	}
	
	/**
	 * Operacion encargada de localizar los elementos recibidos en la lista en la ubicacion de
	 * transito de la compañia asociada al documento de importación
	 * @param user
	 * @param listSerialized
	 * @param importLog
	 * @param targetWh
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @param processCode
	 * @param itemStatus
	 * @throws BusinessException
	 * @author waguilera
	 */
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void setImportLogItemsSerializedInWarehouse(User user, ImportLogItem serItem, ImportLog importLog, Warehouse targetWh,  String processCode, ItemStatus itemStatus, MovementElementDTO dtoGenerics) throws BusinessException {
		log.debug("== Inicia setImportLogItemsSerializedInWarehouse/WarehouseElementBusinessBean ==");
		try {
			
			//Se consultan los elementos serializados del registro de importacion
			//ImportLogItemResponse serResponse = daoImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(importLogId, true, null);
			if(serItem!= null){
				
				//Se hacen los movimientos	
				MovementElementDTO dto = new MovementElementDTO(user, 
							UtilsBusiness.copyObject(WarehouseVO.class, targetWh), 
							serItem.getElement().getId(), 
							importLog.getId(), 
							CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
							dtoGenerics.getMovTypeCodeE(), 
							dtoGenerics.getRecordStatusU(), 
							dtoGenerics.getRecordStatusH(),  
							true, 
							processCode,
							dtoGenerics.getMovConfigStatusPending(),
							dtoGenerics.getMovConfigStatusNoConfig());
					businessMovementElement.moveSerializedElementToWarehouse(dto);
				
			}
			
		} catch (Throwable t) {
			log.error(" == Error setImportLogItemsSerializedInWarehouse/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina setImportLogItemsSerializedInWarehouse/WarehouseElementBusinessBean ==");
		}
	}
	
	
	/**
	 * Operacion encargada de localizar los elementos recibidos en la lista en la ubicacion de
	 * transito de la compañia asociada al documento de importación
	 * @param user
	 * @param listNotSerialized
	 * @param importLog
	 * @param targetWh
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @param processCode
	 * @param itemStatus
	 * @throws BusinessException
	 * @author waguilera
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void setImportLogItemsNotSerializedInWarehouse(User user, ImportLogItem notSerItem, ImportLog importLog, Warehouse targetWh, String processCode, ItemStatus itemStatus, MovementElementDTO dtoGenerics) throws BusinessException {
		log.debug("== Inicia setImportLogItemsNotSerializedInWarehouse/WarehouseElementBusinessBean ==");
		try {

			if(notSerItem != null){
				//Se hacen los movimientos					
				MovementElementDTO dtoMovement = new MovementElementDTO(user,
						null, 
						UtilsBusiness.copyObject(WarehouseVO.class, targetWh), 
						null, 
						notSerItem.getElement().getElementType().getId(), 
						notSerItem.getElement().getElementType().getTypeElementCode(),
						importLog.getId(), 
						CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
						dtoGenerics.getMovTypeCodeE(), 
						dtoGenerics.getMovTypeCodeS(), 
						dtoGenerics.getRecordStatusU(),
						dtoGenerics.getRecordStatusH(),
						notSerItem.getAmountExpected(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());

				businessMovementElement.moveNotSerializedElementToWarehouse(dtoMovement);
			}

		} catch (Throwable t) {
			log.error(" == Error setImportLogItemsNotSerializedInWarehouse/WarehouseElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina setImportLogItemsNotSerializedInWarehouse/WarehouseElementBusinessBean ==");
		}
	}
	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setElementSerializedInWareHouseTransit(User user, ImportLogItem importLogItem,
			ImportLog importLog, Warehouse warehouseTarget, ItemStatus itemStatus, MovementElementDTO dtoGenerics) throws BusinessException{
		log.debug("== Inicia setElementSerializedInwareHouseTrensit/ImportLogItemBusinessBean ==");
		try{
			this.setImportLogItemsSerializedInWarehouse(user, importLogItem, importLog, warehouseTarget, 
					CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(), itemStatus, dtoGenerics);
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación setElementSerializedInwareHouseTrensit/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina setElementSerializedInwareHouseTrensit/ImportLogItemBusinessBean ==");
	    }
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setElementNotSerializedInWareHouseTransit(User user, ImportLogItem importLogItem,
			ImportLog importLog, Warehouse warehouseTarget, ItemStatus itemStatus, MovementElementDTO dtoGenerics) throws BusinessException{
		log.debug("== Inicia setElementSerializedInwareHouseTrensit/ImportLogItemBusinessBean ==");
		try{
			this.setImportLogItemsNotSerializedInWarehouse(user, importLogItem, importLog, warehouseTarget, 
					CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(), itemStatus, dtoGenerics);
		} catch(Throwable ex){
	    	log.error("== Error al tratar de ejecutar la operación setElementSerializedInwareHouseTrensit/ImportLogItemBusinessBean ==");
	    	throw this.manageException(ex);
	    } finally {
	        log.debug("== Termina setElementSerializedInwareHouseTrensit/ImportLogItemBusinessBean ==");
	    }
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateImportLogItemStatus(ImportLogItem importLogItem) throws BusinessException {
        log.debug("== Inicia updateImportLogItemStatus/ImportLogItemBusinessBean ==");
        UtilsBusiness.assertNotNull(importLogItem, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            ImportLogItem objPojo =  UtilsBusiness.copyObject(ImportLogItem.class, importLogItem);
            daoImportLogItem.updateImportLogItem(objPojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n updateImportLogItemStatus/ImportLogItemBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateImportLogItemStatus/ImportLogItemBusinessBean ==");
        }
		
	}

	
}