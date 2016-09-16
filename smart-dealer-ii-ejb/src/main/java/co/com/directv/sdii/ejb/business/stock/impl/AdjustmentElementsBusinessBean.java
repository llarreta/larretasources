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
import co.com.directv.sdii.ejb.business.file.data.LoadMassiveSerializedAdjusmentData;
import co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.AdjustmentElements;
import co.com.directv.sdii.model.pojo.AdjustmentElementsStatus;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.AdjustmentElementsVO;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD AdjustmentElements
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.AdjustmentElementsDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal
 */
@Stateless(name="AdjustmentElementsBusinessBeanLocal",mappedName="ejb/AdjustmentElementsBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentElementsBusinessBean extends BusinessBase implements AdjustmentElementsBusinessBeanLocal {

    @EJB(name="AdjustmentElementsDAOLocal", beanInterface=AdjustmentElementsDAOLocal.class)
    private AdjustmentElementsDAOLocal daoAdjustmentElements;
    
    @EJB(name = "ElementStatusDAOLocal", beanInterface = ElementStatusDAOLocal.class)
	private ElementStatusDAOLocal daoElementStatus;
    
    @EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
	private ElementDAOLocal daoElement;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
    
    @EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
    private WarehouseDAOLocal daoWarehouse;
    
    @EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
    private ElementTypeDAOLocal daoElementType;
    
    @EJB(name="ImportLogItemDAOLocal", beanInterface=ImportLogItemDAOLocal.class)
	private ImportLogItemDAOLocal daoImportLogItem;
    
    @EJB(name = "ElementBusinessBeanLocal", beanInterface = ElementBusinessBeanLocal.class)
	private ElementBusinessBeanLocal elementBusinessBean;
    
    @EJB(name = "MovementElementBusinessBeanLocal", beanInterface = MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
    
    @EJB(name = "NotSerializedDAOLocal", beanInterface = NotSerializedDAOLocal.class)
	private NotSerializedDAOLocal daoNotSerialized;
    
    @EJB(name = "AdjustmentElementsStatusDAOLocal", beanInterface = AdjustmentElementsStatusDAOLocal.class)
	private AdjustmentElementsStatusDAOLocal daoAdjustmentElementsStatus;
    
    private final static Logger log = UtilsBusiness.getLog4J(AdjustmentElementsBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal#getAllAdjustmentElementss()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AdjustmentElementsVO> getAllAdjustmentElementss() throws BusinessException {
        log.debug("== Inicia getAllAdjustmentElementss/AdjustmentElementsBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoAdjustmentElements.getAllAdjustmentElementss(), AdjustmentElementsVO.class);

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllAdjustmentElementss/AdjustmentElementsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAdjustmentElementss/AdjustmentElementsBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal#getAdjustmentElementssByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AdjustmentElementsVO getAdjustmentElementsByID(Long id) throws BusinessException {
        log.debug("== Inicia getAdjustmentElementsByID/AdjustmentElementsBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            AdjustmentElements objPojo = daoAdjustmentElements.getAdjustmentElementsByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(AdjustmentElementsVO.class, objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAdjustmentElementsByID/AdjustmentElementsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAdjustmentElementsByID/AdjustmentElementsBusinessBean ==");
        }
    }

	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal#updateAdjustmentElements(co.com.directv.sdii.model.vo.AdjustmentElementsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException {
        log.debug("== Inicia updateAdjustmentElements/AdjustmentElementsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            AdjustmentElements objPojo =  UtilsBusiness.copyObject(AdjustmentElements.class, obj);
            daoAdjustmentElements.updateAdjustmentElements(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateAdjustmentElements/AdjustmentElementsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAdjustmentElements/AdjustmentElementsBusinessBean ==");
        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal#createAdjustmentElements(co.com.directv.sdii.model.vo.AdjustmentElementsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void createAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException {
        log.debug("== Inicia createAdjustmentElements/AdjustmentElementsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            AdjustmentElements objPojo =  UtilsBusiness.copyObject(AdjustmentElements.class, obj);
            daoAdjustmentElements.createAdjustmentElements(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createAdjustmentElements/AdjustmentElementsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAdjustmentElements/AdjustmentElementsBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.AdjustmentElementsBusinessBeanLocal#deleteAdjustmentElements(co.com.directv.sdii.model.vo.AdjustmentElementsVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException {
        log.debug("== Inicia deleteAdjustmentElements/AdjustmentElementsBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            AdjustmentElements objPojo =  UtilsBusiness.copyObject(AdjustmentElements.class, obj);
            daoAdjustmentElements.deleteAdjustmentElements(objPojo);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteAdjustmentElements/AdjustmentElementsBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAdjustmentElements/AdjustmentElementsBusinessBean ==");
        }
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void adjustmentOutputElementSerialized(AdjustmentVO adjustmentVO, 
			                                      AdjustmentElementDTO adjustmentElement, 
			                                      User user,
			                                      Warehouse warehouseAdjusTransit,
			                                      MovementElementDTO dtoGenerics) throws BusinessException {
		 log.debug("== Inicia adjustmentElementSerialized/AdjustmentElementsBusinessBean ==");
	        try {
	        	UtilsBusiness.assertNotNull(adjustmentElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		UtilsBusiness.assertNotNull(adjustmentElement.getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
        		
        		
            	//Consulto el elemento
            	Element element = daoElement.getElementByID(adjustmentElement.getElementId());
            	if(element==null){
            		throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
            	}
            	
            	//Valido que el tipo de elemento sea no serializado
            	if(element.getElementType().getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity())){
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN463.getCode(), ErrorBusinessMessages.STOCK_IN463.getMessage());
            	}
            	
            	Warehouse warehouseSource = null;
            	
            	//Se verifica que ele elemento exista en el sistema
            	Serialized ser = daoSerialized.getSerializedElementByElementId(element.getId());
            	if(ser==null){
            		throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
            	}
            	
            	//Se verifica que el elemento este ubicado dentro de el sistema
            	WarehouseElement warehouseElement = daoWarehouseElement.getWarehouseElementBySerialActive(ser.getSerialCode(),adjustmentVO.getCountry().getId());
            	if(warehouseElement== null){
            		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
            	}
            	warehouseSource = warehouseElement.getWarehouseId();
            	
            	
            	
            	if(warehouseSource==null){
            		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(), ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
            	}
            
            	//Crea el detalle del elemento
            	AdjustmentElementsVO detailRegister = new AdjustmentElementsVO();
            	detailRegister.setAdjustment(adjustmentVO);
            	detailRegister.setSerialized(ser);
            	detailRegister.setWarehouseSource(warehouseSource);
            	
            	AdjustmentElementsStatus adjustmentElementsStatus = new AdjustmentElementsStatus();
            	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity())){
            		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
            	}else{
            		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
            	}
            	detailRegister.setAdjustmentElementsStatus(adjustmentElementsStatus);
            	
            	this.createAdjustmentElements(detailRegister);
            	
            	//Se invoca la operación encarada de realizar el movimento del inventario en la bodega 
            	this.movementElementAdjustmentOutputSerialized(detailRegister, adjustmentVO.getTransferReason().getTransferReasonAuthorized(), user,warehouseAdjusTransit,dtoGenerics);
            	
	        } catch(Throwable ex){
	        	log.debug("== Error al tratar de ejecutar la operación adjustmentElementSerialized/AdjustmentElementsBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina adjustmentElementSerialized/AdjustmentElementsBusinessBean ==");
	        }
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createInputElementSerialized(AdjustmentVO adjustmentVO,
			AdjustmentElementDTO adjustmentElementDto, User user, Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics)
			throws BusinessException {
		log.debug("== Inicia adjustmentInputElementSerialized/AdjustmentElementsBusinessBean ==");
		try{
			//Valida que exista la bodega
    		UtilsBusiness.assertNotNull(adjustmentElementDto.getIdWarehouseDestination(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		//Valida que el serial no sea nulo
        	UtilsBusiness.assertNotNull(adjustmentElementDto.getSerialCode(), ErrorBusinessMessages.STOCK_IN419.getCode(), ErrorBusinessMessages.STOCK_IN419.getMessage());
    		
        	//Valida que venga el tipo de elemento
        	UtilsBusiness.assertNotNull(adjustmentElementDto.getElementTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	
        	Warehouse warehouseDestination = null;
        	warehouseDestination = daoWarehouse.getWarehouseByID(adjustmentElementDto.getIdWarehouseDestination());
        	
        	if(warehouseDestination==null){
        		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
        	}
        	
        	//Valida el tipo de elemento
        	ElementType elementType = daoElementType.getElementTypeByCode(adjustmentElementDto.getElementTypeCode());
        	if(elementType==null){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN370.getCode(), ErrorBusinessMessages.STOCK_IN370.getMessage());
        	}
        	
        	//Valido que el tipo de elemento sea no serializado
        	if(elementType.getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity())){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN463.getCode(), ErrorBusinessMessages.STOCK_IN463.getMessage());
        	}
        	
        	//Crea el serial en la base de datos
        	SerializedVO serializedVO = this.createSerializedForAdjustment(adjustmentVO,adjustmentElementDto, elementType);
        	
        	//Crea el detalle del elemento
        	AdjustmentElementsVO detailRegister = new AdjustmentElementsVO();
        	detailRegister.setAdjustment(adjustmentVO);
        	detailRegister.setWarehouseDestination(warehouseDestination);
        	detailRegister.setSerialized(UtilsBusiness.copyObject(Serialized.class, serializedVO));
        	
        	AdjustmentElementsStatus adjustmentElementsStatus = new AdjustmentElementsStatus();
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity())){
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        	}else{
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
        	}
        	detailRegister.setAdjustmentElementsStatus(adjustmentElementsStatus);
        	
        	this.createAdjustmentElements(detailRegister);
        	
        	//Ejecutar el movimiento del inventario
    		this.executeAdjustmentInputSerialized(detailRegister, adjustmentVO.getTransferReason().getTransferReasonAuthorized(), user,warehouseAdjusTransit,dtoGenerics);
    		
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación adjustmentInputElementSerialized/AdjustmentElementsBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina adjustmentInputElementSerialized/AdjustmentElementsBusinessBean ==");
		}
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void adjustmentTransferElementSerialized(AdjustmentVO adjustmentVO,
			AdjustmentElementDTO adjustmentElement, User user, Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics)
			throws BusinessException {
		log.debug("== Inicia adjustmentTransferElementSerialized/AdjustmentElementsBusinessBean ==");
		try{
			UtilsBusiness.assertNotNull(adjustmentElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		UtilsBusiness.assertNotNull(adjustmentElement.getIdWarehouseDestination(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		UtilsBusiness.assertNotNull(adjustmentElement.getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		
        	//Consulto el elemento
        	WarehouseElement warehouseElementActual = daoWarehouseElement.getWarehouseElementSerializedLastByElementID(adjustmentElement.getElementId());
        	if(warehouseElementActual==null){
        		throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
        	}
        	
        	//Valido que el tipo de elemento sea  serializado
        	if(warehouseElementActual.getSerialized().getElement().getElementType().getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity())){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN463.getCode(), ErrorBusinessMessages.STOCK_IN463.getMessage());
        	}
        	
        	
        	if(adjustmentElement.getIdWarehouse() == null || adjustmentElement.getIdWarehouse().longValue()<=0L){
        		adjustmentElement.setIdWarehouse(warehouseElementActual.getWarehouseId().getId());
        	}
        	
        	//Validar que el elemento se encuentre en la ubicación origen
        	if(warehouseElementActual.getWarehouseId().getId().longValue()!=adjustmentElement.getIdWarehouse().longValue()){
            		Object[] params = {warehouseElementActual.getSerialized().getSerialCode()};
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN383.getCode(), ErrorBusinessMessages.STOCK_IN383.getMessage(params));
            }
        	
        	//Consulto la bodega destino
        	Warehouse warehouseDestination = daoWarehouse.getWarehouseByID(adjustmentElement.getIdWarehouseDestination());
        	if(warehouseDestination==null){
        		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
        	}
        	
        	
        	//Valido que no sea la misma bodega
        	Object[] params = new Object[1];			
			params[0] = warehouseElementActual.getSerialized().getSerialCode();
        	if(warehouseDestination.getId().longValue()==warehouseElementActual.getWarehouseId().getId()){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN421.getCode(),ErrorBusinessMessages.STOCK_IN421.getMessage(params),UtilsBusiness.getParametersWS(params) );
        	}
        	
        	//Crea el detalle del elemento
        	AdjustmentElementsVO detailRegister = new AdjustmentElementsVO();
        	detailRegister.setAdjustment(adjustmentVO);
        	detailRegister.setSerialized(warehouseElementActual.getSerialized());
        	detailRegister.setWarehouseDestination(warehouseDestination);
        	detailRegister.setWarehouseSource(warehouseElementActual.getWarehouseId());
        	
        	AdjustmentElementsStatus adjustmentElementsStatus = new AdjustmentElementsStatus();
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity())){
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        	}else{
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
        	}
        	detailRegister.setAdjustmentElementsStatus(adjustmentElementsStatus);
        	
        	this.createAdjustmentElements(detailRegister);
        	
    		this.executeMovementAdjustmentTransferSerialized(detailRegister,  adjustmentVO.getTransferReason().getTransferReasonAuthorized(), user,warehouseAdjusTransit,dtoGenerics);
    		
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación adjustmentTransferElementSerialized/AdjustmentElementsBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina adjustmentTransferElementSerialized/AdjustmentElementsBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void adjustmentTransferElementSerializedMassive(AdjustmentVO adjustmentVO,
			AdjustmentElementDTO adjustmentElement, User user, Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics,
			LoadMassiveSerializedAdjusmentData dataAux,AdjustmentElementsStatus adjustmentElementsStatus)
			throws BusinessException {
		log.debug("== Inicia adjustmentTransferElementSerialized/AdjustmentElementsBusinessBean ==");
		try{
			UtilsBusiness.assertNotNull(adjustmentElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		UtilsBusiness.assertNotNull(adjustmentElement.getIdWarehouseDestination(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		UtilsBusiness.assertNotNull(adjustmentElement.getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    		
        	//Consulto el elemento
        	WarehouseElement warehouseElementActual = dataAux.getWarehouseElement();
        	if(warehouseElementActual==null){
        		throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
        	}
        	
        	//Valido que el tipo de elemento sea  serializado
        	if(warehouseElementActual.getSerialized().getElement().getElementType().getIsSerialized().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity())){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN463.getCode(), ErrorBusinessMessages.STOCK_IN463.getMessage());
        	}
        	
        	
        	if(adjustmentElement.getIdWarehouse() == null || adjustmentElement.getIdWarehouse().longValue()<=0L){
        		adjustmentElement.setIdWarehouse(warehouseElementActual.getWarehouseId().getId());
        	}
        	
        	//Validar que el elemento se encuentre en la ubicación origen
        	if(warehouseElementActual.getWarehouseId().getId().longValue()!=adjustmentElement.getIdWarehouse().longValue()){
            		Object[] params = {warehouseElementActual.getSerialized().getSerialCode()};
            		throw new BusinessException(ErrorBusinessMessages.STOCK_IN383.getCode(), ErrorBusinessMessages.STOCK_IN383.getMessage(params));
            }
        	
        	//Consulto la bodega destino
        	Warehouse warehouseDestination = dataAux.getWarehouseTargetVO();
        	if(warehouseDestination==null){
        		throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_DOESNT_EXIST.getMessage());
        	}
        	
        	
        	//Valido que no sea la misma bodega
        	Object[] params = new Object[1];			
			params[0] = warehouseElementActual.getSerialized().getSerialCode();
        	if(warehouseDestination.getId().longValue()==warehouseElementActual.getWarehouseId().getId()){
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN421.getCode(),ErrorBusinessMessages.STOCK_IN421.getMessage(params),UtilsBusiness.getParametersWS(params) );
        	}
        	
        	//Crea el detalle del elemento
        	AdjustmentElementsVO detailRegister = new AdjustmentElementsVO();
        	detailRegister.setAdjustment(adjustmentVO);
        	detailRegister.setSerialized(warehouseElementActual.getSerialized());
        	detailRegister.setWarehouseDestination(warehouseDestination);
        	detailRegister.setWarehouseSource(warehouseElementActual.getWarehouseId());
        	
        	detailRegister.setAdjustmentElementsStatus(adjustmentElementsStatus);
        	
        	this.createAdjustmentElements(detailRegister);
        	
    		this.executeMovementAdjustmentTransferSerialized(detailRegister,  adjustmentVO.getTransferReason().getTransferReasonAuthorized(), user,warehouseAdjusTransit,dtoGenerics);
    		
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación adjustmentTransferElementSerialized/AdjustmentElementsBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina adjustmentTransferElementSerialized/AdjustmentElementsBusinessBean ==");
		}
	}

	/**
	 * 
	 * @param adjustmentElementDTO
	 * @param elementType
	 * @return
	 * @throws BusinessException
	 */
	private SerializedVO createSerializedForAdjustment(AdjustmentVO adjustmentVO,
			AdjustmentElementDTO adjustmentElementDTO, ElementType elementType)throws BusinessException{
		log.debug("== Inicia createSerializedForAdjustment/AdjustmentElementsBusinessBean ==");
		UtilsBusiness.assertNotNull(adjustmentElementDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			ElementStatus elementStatus = daoElementStatus.getElementStatusByCode(CodesBusinessEntityEnum.ELEMENT_STATUS_NA.getCodeEntity());
			ElementVO element = new ElementVO();
			element.setElementType(elementType);
			element.setElementStatus(elementStatus);
			
			//Se adiciona el pais para la creacion o actualizacion del elemento
			element.setCountry(adjustmentVO.getCountry());
			
			SerializedVO serialized = new SerializedVO();
			serialized.setSerialCode(adjustmentElementDTO.getSerialCode().toUpperCase());
			serialized.setIrd(adjustmentElementDTO.getRid());
			Serialized serFound = daoSerialized.getSerializedBySerial(serialized.getSerialCode(),adjustmentVO.getCountry().getId());
			if(serFound!=null){
				//Verificar si el serial esta ubicado
				WarehouseElement weFound = daoWarehouseElement.getWhSerializedElementsLastByElementID(serFound.getElementId());
				if(weFound != null){
					Object params[] = {serialized.getSerialCode()};
    				throw new BusinessException(ErrorBusinessMessages.STOCK_IN417.getCode(),ErrorBusinessMessages.STOCK_IN417.getMessage(params), UtilsBusiness.getParametersWS(params));
				}
				
				//Verificar si el serial pertenece aun registro de importación pendiente.
				ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByElementId(serFound.getElementId());
				if(importLogItem!= null && !importLogItem.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity())){
					Object params[] = {serialized.getSerialCode(),importLogItem.getImportLog().getId().toString()};
					throw new BusinessException(ErrorBusinessMessages.STOCK_IN483.getCode(), ErrorBusinessMessages.STOCK_IN483.getMessage(params),  UtilsBusiness.getParametersWS(params));
				}
				
				Element elementFound = serFound.getElement();
				elementFound.setElementType(element.getElementType());
				elementFound.setElementStatus(elementStatus);
				daoElement.updateElement(elementFound);
				serFound.setElement(elementFound);
				serFound.setSerialized(null);
				daoSerialized.updateSerialized(serFound);
				
				//Busco si tenia un elemento vinculado y desvincular
				List<Serialized> listSerialLinked = daoSerialized.getLinkedSerializedBySerializedId(serFound.getElementId());
				if(listSerialLinked.size()>0){
					for(Serialized serLinked: listSerialLinked){
						serLinked.setSerialized(null);
						daoSerialized.updateSerialized(serLinked);
					}
				}
				
				serialized = UtilsBusiness.copyObject(SerializedVO.class, serFound);
			}else{
				elementBusinessBean.createSerializedElement(element, serialized);
			}
			return serialized;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación createSerializedForAdjustment/AdjustmentElementsBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createSerializedForAdjustment/AdjustmentElementsBusinessBean ==");
		}
	}
	/**
	 

	 * @throws BusinessException
	 * @autor waguilera
	 */
	/**
	 * Método encargado de realizar el ingreso de un elemento serializado,
	 * @param adjustmentElement elemento que se ingresara por el ajuste de entrada
	 * @param requiredAutorized si requiere autorizacion el ajuste
	 * @param user el usuario que realiza el ajuste
	 * @param warehouseAdjusTransit bodeja de transito donde queda el elemnto si necesita autorizacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 */
	private void executeAdjustmentInputSerialized(AdjustmentElementsVO adjustmentElement, 
			                                      String requiredAutorized, 
			                                      User user, 
			                                      Warehouse warehouseAdjusTransit,
			                                      MovementElementDTO dtoGenerics)
			throws BusinessException {
		log.debug("== Inicia executeAdjustmentInputSerialized/AdjustmentElementsBusinessBean ==");
		UtilsBusiness.assertNotNull(adjustmentElement, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(adjustmentElement.getSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		try{
			WarehouseVO targetWs = null;
			if(requiredAutorized.equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
				targetWs =  UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseDestination());
			}else{
				targetWs =  UtilsBusiness.copyObject(WarehouseVO.class, warehouseAdjusTransit);
			}
			MovementElementDTO dto = new MovementElementDTO(user, 
					targetWs, 
					adjustmentElement.getSerialized().getElementId(), 
					adjustmentElement.getAdjustment().getId(),
					CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(),
					dtoGenerics.getMovTypeCodeE(), 
					dtoGenerics.getRecordStatusU(), 
					dtoGenerics.getRecordStatusH(),  
					true, 
					CodesBusinessEntityEnum.PROCESS_CODE_IBS_ADJUSMENT.getCodeEntity(),
					dtoGenerics.getMovConfigStatusPending(),
					dtoGenerics.getMovConfigStatusNoConfig());
			businessMovementElement.moveSerializedElementToWarehouse(dto);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación executeAdjustmentInputSerialized/AdjustmentElementsBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina executeAdjustmentInputSerialized/AdjustmentElementsBusinessBean ==");
		}
	}

	/**
	 * Operación encargada de realizar el movimiento en la bodegas
	 * de elementos serializados por ajustes de salida 
	 * @param adjustmentElement elementos involucrados en el ajuste de salida
	 * @param requiredAutorized si el ajuste requiere o no autorizacion
	 * @param user usuario que realiza el ajuste
	 * @param warehouseAdjusTransit bodega de transito donde queda el elemnto en caso de necesitar autorizacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 */
	private void movementElementAdjustmentOutputSerialized(AdjustmentElementsVO adjustmentElement, 
			                                               String requiredAutorized, 
			                                               User user,
			                                               Warehouse warehouseAdjusTransit,
			                                               MovementElementDTO dtoGenerics)
			throws BusinessException {
		log.debug("== Inicia executeAdjustmentInputSerialized/AdjustmentElementsBusinessBean ==");
		try{	
			
			UtilsBusiness.assertNotNull(adjustmentElement.getSerialized(), ErrorBusinessMessages.STOCK_IN408.getCode(), ErrorBusinessMessages.STOCK_IN408.getMessage());
			Object[] params = new Object[1];			
			params[0] = adjustmentElement.getSerialized().getSerialCode();
			WarehouseElement warehouseElementPojo = daoWarehouseElement.getWarehouseElementBySerialActive(adjustmentElement.getSerialized().getSerialCode(),user.getCountry().getId());
			if(warehouseElementPojo==null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR032.getCode(), ErrorBusinessMessages.CORE_CR032.getMessage(params), UtilsBusiness.getParametersWS(params));
			}
			
			//Realiza el movimiento de salida del elemento si no requiere autorización
			if(requiredAutorized.equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
				MovementElementDTO dto = new MovementElementDTO(user, 
						UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseSource()), 
						adjustmentElement.getSerialized(),
						adjustmentElement.getAdjustment().getId(), 
						CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(),
						dtoGenerics.getMovTypeCodeS(),
						dtoGenerics.getRecordStatusU(),
						dtoGenerics.getRecordStatusH(), 
						true, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_ADJUSMENT.getCodeEntity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.deleteSerializedElementInWarehouse(dto);
			}else{
				MovementElementDTO dtoMovement = new MovementElementDTO(user,
						UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseSource()), 
						UtilsBusiness.copyObject(WarehouseVO.class, warehouseAdjusTransit),
						adjustmentElement.getSerialized(), 
						adjustmentElement.getAdjustment().getId(), 
						CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
						dtoGenerics.getMovTypeCodeE(), 
						dtoGenerics.getMovTypeCodeS(),
						dtoGenerics.getRecordStatusU(), 
						dtoGenerics.getRecordStatusH(), 
						true, 
						CodesBusinessEntityEnum.PROCESS_CODE_IBS_ADJUSMENT.getCodeEntity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
			}
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación executeAdjustmentInputSerialized/AdjustmentElementsBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina executeAdjustmentInputSerialized/AdjustmentElementsBusinessBean ==");
		}
	}

	/**
	 * Método encargado de realizar el movimiento de elementos serializados entre bodegas
	 * @param adjustmentElement elemento que se movera con el ajuste de inventario
	 * @param requiredAutorized Determina si el ajuste requiere o no autorizacion
	 * @param user usuario que ejecuta el movimiento de inventario
	 * @param warehouseAdjusTransit bodega de transito donde quedaran los elementos en caso de necesitar autorizacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 * @autor waguilera
	 */
	private void executeMovementAdjustmentTransferSerialized(AdjustmentElementsVO adjustmentElement, String requiredAutorized, User user
			, Warehouse warehouseAdjusTransit, MovementElementDTO dtoGenerics) throws BusinessException {
		log.debug("== Inicia executeAdjustmentTransferSerialized/AdjustmentElementsBusinessBean ==");
		UtilsBusiness.assertNotNull(adjustmentElement.getSerialized(), ErrorBusinessMessages.STOCK_IN408.getCode(), ErrorBusinessMessages.STOCK_IN408.getMessage());
		try{	
			Object[] params = new Object[1];			
			params[0] = adjustmentElement.getSerialized().getSerialCode();
			WarehouseElement warehouseElementPojo = daoWarehouseElement.getWarehouseElementBySerialActive(adjustmentElement.getSerialized().getSerialCode(),user.getCountry().getId());
			if(warehouseElementPojo==null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR032.getCode(), ErrorBusinessMessages.CORE_CR032.getMessage(params), UtilsBusiness.getParametersWS(params));
			}
			
			//Realiza el movimiento de traslado del elemento si no requiere autorización
			WarehouseVO targetWs = null;
			if(requiredAutorized.equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
				 targetWs =  UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseDestination());
			}else{
				 targetWs =  UtilsBusiness.copyObject(WarehouseVO.class, warehouseAdjusTransit);
			}
			MovementElementDTO dtoMovement = new MovementElementDTO(user,
					UtilsBusiness.copyObject(WarehouseVO.class, adjustmentElement.getWarehouseSource()), 
					targetWs, 
					adjustmentElement.getSerialized(), 
					adjustmentElement.getAdjustment().getId(), 
					CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
					dtoGenerics.getMovTypeCodeE(), 
					dtoGenerics.getMovTypeCodeS(),
					dtoGenerics.getRecordStatusU(), 
					dtoGenerics.getRecordStatusH(), 
					true, 
					CodesBusinessEntityEnum.PROCESS_CODE_IBS_ADJUSMENT.getCodeEntity(),
					dtoGenerics.getMovConfigStatusPending(),
					dtoGenerics.getMovConfigStatusNoConfig());
			businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación executeAdjustmentTransferSerialized/AdjustmentElementsBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina executeAdjustmentTransferSerialized/AdjustmentElementsBusinessBean ==");
		}
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void executeAdjustmentInputNotSerialized(AdjustmentElementsVO detailRegister,  
			                                        AdjustmentVO adjustmentVO, 
			                                        ElementType elementType,  
			                                        User user, 
			                                        Warehouse warehouseAdjusTransit,
			                                        MovementElementDTO dtoGenerics) throws BusinessException{
		log.debug("== Inicia executeAdjustmentInputSerialized/AdjustmentBusinessBean ==");
		try{
			//Consultar element status
			ElementStatus elementStatus = daoElementStatus.getElementStatusByCode(CodesBusinessEntityEnum.ELEMENT_STATUS_NA.getCodeEntity());
			
			ElementVO element = new ElementVO();
			element.setElementType(elementType);
			element.setElementStatus(elementStatus);
			element.setCountry(adjustmentVO.getCountry());
			NotSerializedVO noSerializado = null;
			noSerializado = UtilsBusiness.copyObject(NotSerializedVO.class, daoNotSerialized.getNotSerializedbyElementTypeID(elementType.getId(), adjustmentVO.getCountry().getId()));
			if(noSerializado == null){
				noSerializado = new NotSerializedVO();
				noSerializado.setInitialQuantity(detailRegister.getInitialQuantity());
			}
			elementBusinessBean.createNotSerializedElement(element, noSerializado,  null);
			detailRegister.setNotSerialized(noSerializado);

			WarehouseVO targetWs = null;
			AdjustmentElementsStatus adjustmentElementsStatus = new AdjustmentElementsStatus();
			//Si no requiere autorización realiza el ajuste a la ubicación indicada
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity())){
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        		targetWs =  UtilsBusiness.copyObject(WarehouseVO.class, warehouseAdjusTransit);
        	}else{
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
        		targetWs =  UtilsBusiness.copyObject(WarehouseVO.class,  detailRegister.getWarehouseDestination());
        	}
        	detailRegister.setAdjustmentElementsStatus(adjustmentElementsStatus);
			
			this.createAdjustmentElements(detailRegister);
			
			MovementElementDTO dtoMovement = new MovementElementDTO(user,
					null, 
					targetWs, 
					null, 
					element.getElementType().getId(), 
					element.getElementType().getTypeElementCode(),
					adjustmentVO.getId(), 
					CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
					dtoGenerics.getMovTypeCodeE(), 
					dtoGenerics.getMovTypeCodeS(), 
					dtoGenerics.getRecordStatusU(),
					dtoGenerics.getRecordStatusH(),
					detailRegister.getInitialQuantity(),
					dtoGenerics.getMovConfigStatusPending(),
					dtoGenerics.getMovConfigStatusNoConfig());
			businessMovementElement.moveNotSerializedElementToWarehouse(dtoMovement);
			
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación executeAdjustmentInputSerialized/AdjustmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina executeAdjustmentInputSerialized/AdjustmentBusinessBean ==");
		}

	}
	
	@Override
	public void executeAdjustmentOutputNotSerialized(AdjustmentElementsVO detailRegister, 
			                                         AdjustmentVO adjustmentVO, 
			                                         User user,
			                                         Warehouse warehouseAdjusTransit,
			                                         MovementElementDTO dtoGenerics) throws BusinessException{
		log.debug("== Inicia executeAdjustmentOutputSerialized/AdjustmentBusinessBean ==");
		try{
			
			ElementType elementType = detailRegister.getNotSerialized().getElement().getElementType();
			UtilsBusiness.assertNotNull(elementType, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
			
			AdjustmentElementsStatus adjustmentElementsStatus = new AdjustmentElementsStatus();
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity())){
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        	}else{
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
        	}
        	detailRegister.setAdjustmentElementsStatus(adjustmentElementsStatus);
			
			//Crear el detalle del documento de ajuste
			this.createAdjustmentElements(detailRegister);
			
			//Validar que la cantidad en la bodega origen
			Double whQuantityElement = this.daoWarehouseElement.countWHElementByActualElementNotSerialized(detailRegister.getWarehouseSource().getId(), detailRegister.getWarehouseSource().getCountry().getId(), null, null, elementType.getTypeElementCode(), CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity(), null, null);
			if(detailRegister.getInitialQuantity().doubleValue()> whQuantityElement.doubleValue()){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN464.getCode(), ErrorBusinessMessages.STOCK_IN464.getMessage());
			}

			//Si no requiere autorización realiza el ajuste a la ubicación indicada
			if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
				MovementElementDTO dto = new MovementElementDTO(user, 
						UtilsBusiness.copyObject(WarehouseVO.class, detailRegister.getWarehouseSource()), 
						null, elementType.getId(), 
						null, 
						adjustmentVO.getId(), 
						CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
						dtoGenerics.getMovTypeCodeS(), 
						dtoGenerics.getRecordStatusU(), 
						dtoGenerics.getRecordStatusH(), 
						detailRegister.getInitialQuantity(),
						dtoGenerics.getMovConfigStatusPending(),
						dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.deleteNotSerializedElementInWarehouse(dto);
			}else{
				 MovementElementDTO dtoMovement = new MovementElementDTO(user,
							UtilsBusiness.copyObject(WarehouseVO.class,  detailRegister.getWarehouseSource()), 
							UtilsBusiness.copyObject(WarehouseVO.class,  warehouseAdjusTransit),
							null, 
							elementType.getId(), 
							elementType.getTypeElementCode(),
							adjustmentVO.getId(), 
							CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
							dtoGenerics.getMovTypeCodeE(), 
							dtoGenerics.getMovTypeCodeS(), 
							dtoGenerics.getRecordStatusU(),
							dtoGenerics.getRecordStatusH(),
							detailRegister.getInitialQuantity(),
							dtoGenerics.getMovConfigStatusPending(),
							dtoGenerics.getMovConfigStatusNoConfig());
				businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMovement);
			}
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación executeAdjustmentOutputSerialized/AdjustmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina executeAdjustmentOutputSerialized/AdjustmentBusinessBean ==");
		}

	}
	
	
	@Override
	public void executeAdjustmentTransferNotSerialized(AdjustmentElementsVO detailRegister, 
			                                           AdjustmentVO adjustmentVO, 
			                                           User user,
			                                           Warehouse warehouseAdjusTransit,
			                                           MovementElementDTO dtoGenerics) throws BusinessException{
		log.debug("== Inicia executeAdjustmentOutputSerialized/AdjustmentBusinessBean ==");
		try{
			
			ElementType elementType = detailRegister.getNotSerialized().getElement().getElementType();
			UtilsBusiness.assertNotNull(elementType, ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
			
			AdjustmentElementsStatus adjustmentElementsStatus = new AdjustmentElementsStatus();
        	if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_YES.getCodeEntity())){
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PENDING.getCodeEntity());
        	}else{
        		adjustmentElementsStatus = daoAdjustmentElementsStatus.getAdjustmentElementsStatusByCode(CodesBusinessEntityEnum.ADJUSTMENT_ELEMENTS_STATUS_PROCESS.getCodeEntity());
        	}
        	detailRegister.setAdjustmentElementsStatus(adjustmentElementsStatus);
			
			//Crear el detalle del documento de ajuste
			this.createAdjustmentElements(detailRegister);

			//Validar que la cantidad en la bodega origen
			Double whQuantityElement = this.daoWarehouseElement.countWHElementByActualElementNotSerialized(detailRegister.getWarehouseSource().getId(), 
					detailRegister.getWarehouseSource().getCountry().getId(), 
					null, 
					null, 
					elementType.getTypeElementCode(), 
					CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity(), 
					null, 
					null);
			if(detailRegister.getInitialQuantity().doubleValue()> whQuantityElement.doubleValue()){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN464.getCode(), ErrorBusinessMessages.STOCK_IN464.getMessage());
			}

			WarehouseVO targetWs = null;
			//Si no requiere autorización realiza el ajuste a la ubicación indicada
			if(adjustmentVO.getTransferReason().getTransferReasonAuthorized().equals(CodesBusinessEntityEnum.MOVEMENT_TYPE_AUTHORIZED_NO.getCodeEntity())){
				 targetWs =  UtilsBusiness.copyObject(WarehouseVO.class,  detailRegister.getWarehouseDestination());
			}else{
				 targetWs =  UtilsBusiness.copyObject(WarehouseVO.class, warehouseAdjusTransit);
			}
			MovementElementDTO dtoMovement = new MovementElementDTO(user,
					UtilsBusiness.copyObject(WarehouseVO.class,  detailRegister.getWarehouseSource()), 
					targetWs, 
					null, 
					elementType.getId(), 
					elementType.getTypeElementCode(),
					adjustmentVO.getId(), 
					CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity(), 
					dtoGenerics.getMovTypeCodeE(), 
					dtoGenerics.getMovTypeCodeS(), 
					dtoGenerics.getRecordStatusU(),
					dtoGenerics.getRecordStatusH(),
					detailRegister.getInitialQuantity(),
					dtoGenerics.getMovConfigStatusPending(),
					dtoGenerics.getMovConfigStatusNoConfig());
			businessMovementElement.moveNotSerializedElementBetweenWarehouse(dtoMovement);
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación executeAdjustmentOutputSerialized/AdjustmentBusinessBean ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina executeAdjustmentOutputSerialized/AdjustmentBusinessBean ==");
		}

	}
	
}
