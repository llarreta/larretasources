package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.AdjustmentHelperBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;

import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.DocumentClass;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.MovCmdConfig;
import co.com.directv.sdii.model.pojo.MovCmdStatus;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.NotSerPartialRetirement;
import co.com.directv.sdii.model.pojo.NotSerPartialRetirementId;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdConfigDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerPartialRetirementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.NotSerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.RecordStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.impl.MovCmdStatusDAO;

/**
 * EJB que implementa las operaciones de movimientos de elementos de inventario
 * 
 * Fecha de Creación: Ene 10, 2011
 * @author waguilera
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal
 */
@Stateless(name="MovementElementBusinessBeanLocal",mappedName="ejb/MovementElementBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovementElementBusinessBean extends BusinessBase implements MovementElementBusinessBeanLocal {

   
    @EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
    
    @EJB(name="MovementTypeDAOLocal", beanInterface=MovementTypeDAOLocal.class)
	private MovementTypeDAOLocal daoMovementType;
    
    @EJB(name="RecordStatusDAOLocal", beanInterface=RecordStatusDAOLocal.class)
	private RecordStatusDAOLocal daoRecordStatus;
    
    @EJB(name="MovCmdConfigDAOLocal",beanInterface=MovCmdConfigDAOLocal.class)
	private MovCmdConfigDAOLocal daoMovCmdConfig;
    
    @EJB(name="MovCmdStatusDAOLocal",beanInterface=MovCmdStatusDAOLocal.class)
	private MovCmdStatusDAOLocal daoMovCmdStatus;
    
    @EJB(name="MovementConfigBusinessBeanLocal",beanInterface=MovementConfigBusinessBeanLocal.class)
	private MovementConfigBusinessBeanLocal movementConfigBusiness;

    @EJB(name="NotSerPartialRetirementDAOLocal", beanInterface=NotSerPartialRetirementDAOLocal.class)
	private NotSerPartialRetirementDAOLocal daoNotSerPartialRetirement;
    
    @EJB(name="NotSerializedDAOLocal", beanInterface=NotSerializedDAOLocal.class)
	private NotSerializedDAOLocal daoNotSerialized;
    
    @EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal daoElementType;
    
	@EJB(name="AdjustmentDAOLocal", beanInterface=AdjustmentDAOLocal.class)
    private AdjustmentDAOLocal daoAdjustment;
	
    @EJB(name = "AdjustmentHelperBusinessBeanLocal", beanInterface = AdjustmentHelperBusinessBeanLocal.class)
	private AdjustmentHelperBusinessBeanLocal adjustmentHelperBusinessBean;
    
    @EJB(name="AdjustmentStatusDAOLocal", beanInterface=AdjustmentStatusDAOLocal.class)
    private AdjustmentStatusDAOLocal daoAdjustmentStatus;
	
    
    private final static Logger log = UtilsBusiness.getLog4J(MovementElementBusinessBean.class);


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void moveSerializedElementBetweenWarehouse(MovementElementDTO dto)
			throws BusinessException {
		log.debug("== Inicia moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		try {
			this.validateMoveSerializedElementBetweenWarehouse(dto);
			//consulto el elemento en la bodega actual
			WarehouseElement warehouseElementLast = daoWarehouseElement.getWarehouseElementSerializedLastByElementID(dto.getSerialized().getElementId());
			if(warehouseElementLast==null){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
			}
			dto.setSerialized(warehouseElementLast.getSerialized());
			
			//Valido que el elemento no este el la bodega destino
			if(warehouseElementLast.getWarehouseId().getId().longValue()
					==dto.getTargetWs().getId()){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_ALREADY_IN_WH.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_ALREADY_IN_WH.getMessage());
			}
			
			//Valido que el elemento este en la bodega origen
			if(warehouseElementLast.getWarehouseId().getId().longValue()
					!=dto.getSourceWh().getId()){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
			}
			
			// Se realiza la validación que exista el tipo de movimiento de entrada
			dto.setMovTypeCodeE(validateMovementType(dto.getMovTypeCodeE()));
			
			//Se realiza la validación que exista el tipo de movimiento de salida
			dto.setMovTypeCodeS(validateMovementType(dto.getMovTypeCodeS()));

			//Se realiza la validación que exista el estado del registro último
			dto.setRecordStatusU(validateRecordStatus(dto.getRecordStatusU(), CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()));

			// Se realiza la validación que exista el estado del registro histórico
			dto.setRecordStatusH(validateRecordStatus(dto.getRecordStatusH(), CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity()));
			
			
			// Se actualiza el estado del registro en la bodega de origen a 'H'
			warehouseElementLast.setRecordStatus(dto.getRecordStatusH());
			daoWarehouseElement.updateWarehouseElement(warehouseElementLast);
			
			// Se crea un nuevo registro en la bodega de origen es estado 'H' que represente la salida del elemento.
			// ActualQuantity=0 e InitialQuantity=1
			WarehouseElement warehouseElementOut = this.createWarehouseElementRecord(dto.getSerialized(), null, 0D, 1D, dto.getSourceWh(), dto.getMovTypeCodeS(), dto.getRecordStatusH(), null, 1D, null, dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());
		
			// Se crea un nuevo registro en la bodega de destino es estado 'U' que represente la entrada del elemento.
			// ActualQuantity=1 e InitialQuantity=0
			this.createWarehouseElementRecord(dto.getSerialized(), null, 1D, 0D, dto.getTargetWs(), dto.getMovTypeCodeE(), dto.getRecordStatusU(), null, 1D, warehouseElementOut,  dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());
			
			MovementElementDTO dtoLinked = UtilsBusiness.copyObject(MovementElementDTO.class, dto);
			if(dto.isReportIbs()){
			    this.createMovementCommandQueue(dto);
			}
			 //waguilera validaciones para movimiento automático del vinculado
			if(dto.getSerialized().getSerialized()!=null&&dto.getSerialized().getSerialized().getSerialCode()!=null){
			    dtoLinked.setSerialized(dto.getSerialized().getSerialized());
			    dtoLinked.setReportIbs(false);
			    this.moveSerializedElementBetweenWarehouse(dtoLinked);
			}
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");				throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		}
		
	}

	
	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void moveSerializedElementToWarehouse(MovementElementDTO dto)
			throws BusinessException {
		log.debug("== Inicia moveSerializedElementToWarehouse/MovementElementBusinessBean ==");
		try {
			this.validateMoveSerializedElementToWarehouse(dto);
			dto.setSerialized(daoSerialized.getSerializedElementByElementId(dto.getSerialized().getElementId()));
			if(dto.getSerialized()==null){
				throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
			}
			WarehouseElement warehouseElementLast = daoWarehouseElement.getWarehouseElementSerializedLastByElementID(dto.getSerialized().getElementId());
			if(warehouseElementLast!=null){
				Object[] params = new Object[1];			
				params[0] = dto.getSerialized().getSerialCode();
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN421.getCode(),ErrorBusinessMessages.STOCK_IN421.getMessage(params),UtilsBusiness.getParametersWS(params));
			}
			
			// Se realiza la validación que exista el tipo de movimiento
			dto.setMovTypeCodeE(validateMovementType(dto.getMovTypeCodeE()));

			// Se realiza la validación que exista el estado del registro ultimo
			dto.setRecordStatusU(validateRecordStatus(dto.getRecordStatusU(), CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()));
			
			// Se crea el movimiento
			this.createWarehouseElementRecord(UtilsBusiness.copyObject(Serialized.class, dto.getSerialized()), null, 1D, 0D, UtilsBusiness.copyObject(Warehouse.class,  dto.getTargetWs()), dto.getMovTypeCodeE(), dto.getRecordStatusU(), null, 1D, null, dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());
			
			if(dto.isReportIbs()){
				this.createMovementCommandQueue(dto);
			}
			
			if(dto.getSerialized().getSerialized()!=null && dto.getSerialized().getSerialized().getElementId().longValue()>0){
				dto.setReportIbs(false);
				dto.setSerialized(dto.getSerialized().getSerialized());
				this.moveSerializedElementToWarehouse(dto);
			}
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveSerializedElementToWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveSerializedElementToWarehouse/MovementElementBusinessBean ==");
		}
		
	}
	
	@Override
	public void deleteSerializedElementInWarehouse(MovementElementDTO dto) throws BusinessException{
		log.debug("== Inicia deleteSerializedElementInWarehouse/MovementElementBusinessBean ==");
		try {
			this.validateDeleteSerializedElementInWarehouse(dto);
			dto.setSerialized(daoSerialized.getSerializedElementByElementId(dto.getSerialized().getElementId()));
			if(dto.getSerialized()==null){
				throw new BusinessException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(), ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
			}
			
			//consulto el elemento en la bodega actual
			WarehouseElement warehouseElementLast = daoWarehouseElement.getWarehouseElementSerializedLastByElementID(dto.getSerialized().getElementId());
			if(warehouseElementLast==null){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
			}
			dto.setSerialized(warehouseElementLast.getSerialized());
			
			//Valido que el elemento este en la bodega origen
			if(warehouseElementLast.getWarehouseId().getId().longValue()
					!=dto.getSourceWh().getId()){
				throw new BusinessException(ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getCode(),ErrorBusinessMessages.WAREHOUSE_ELEMENT_DOESNT_EXIST.getMessage());
			}
			
			// Se realiza la validación que exista el tipo de movimiento
			dto.setMovTypeCodeS(validateMovementType(dto.getMovTypeCodeS()));
			
			//Se realiza la validación que exista el estado del registro histórico
			dto.setRecordStatusH(validateRecordStatus(dto.getRecordStatusH(), CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity()));
			
			// Se actualiza el estado del registro en la bodega de origen a 'H'
			warehouseElementLast.setRecordStatus(dto.getRecordStatusH());
			daoWarehouseElement.updateWarehouseElement(warehouseElementLast);
			
			
			// Se crea un nuevo registro en la bodega de origen es estado 'H' que represente la salida del elemento.
			// ActualQuantity=0 e InitialQuantity=1
			this.createWarehouseElementRecord(dto.getSerialized(), null, 0D, 1D, dto.getSourceWh(), dto.getMovTypeCodeS(), dto.getRecordStatusH(), null, 1D, null, dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());
			
			if(dto.isReportIbs()){
				WarehouseVO warehouseSource = dto.getSourceWh();
				this.createMovementCommandQueue(dto);
				dto.setSourceWh(warehouseSource);
			}
			
			if(dto.getSerialized().getSerialized()!=null && dto.getSerialized().getSerialized().getElementId().longValue()>0){
				dto.setReportIbs(false);
				dto.setSerialized(dto.getSerialized().getSerialized());
				this.deleteSerializedElementInWarehouse(dto);
			}
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación deleteSerializedElementInWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina deleteSerializedElementInWarehouse/MovementElementBusinessBean ==");
		}
	}
	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void moveNotSerializedElementBetweenWarehouse(MovementElementDTO dto)
			throws BusinessException {
		log.debug("== Termina moveNotSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		
		NotSerialized notSerializedElement = null;
		//Se recorre la lista para realizar los movimientos
		try{
			this.validateMoveNotSerializedElementBetweenWarehouse(dto);
			// Se realiza la validación que exista el tipo de movimiento de entrada
			dto.setMovTypeCodeE(validateMovementType(dto.getMovTypeCodeE()));
			
			//Se realiza la validación que exista el tipo de movimiento de salida
			dto.setMovTypeCodeS(validateMovementType(dto.getMovTypeCodeS()));

			//Se realiza la validación que exista el estado del registro último			
			dto.setRecordStatusU(validateRecordStatus(dto.getRecordStatusU(), CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()));

			// Se realiza la validación que exista el estado del registro histórico
			dto.setRecordStatusH(validateRecordStatus(dto.getRecordStatusH(), CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity()));
			
			//Consultar el elemento
			if(dto.getNotSerialized()!=null && dto.getNotSerialized().getElementId() !=null){
				notSerializedElement = daoNotSerialized.getNotSerializedByID(dto.getNotSerialized().getElementId());
			}else if (dto.getElementTypeId()!=null && dto.getElementTypeId().longValue()>0){
				notSerializedElement = daoNotSerialized.getNotSerializedbyElementTypeID(dto.getElementTypeId(), dto.getUser().getCountry().getId());
			}else if(dto.getElementTypeCode() !=null && !dto.getElementTypeCode().isEmpty()){
				ElementType elementType = daoElementType.getElementTypeByCode(dto.getElementTypeCode());
				notSerializedElement = daoNotSerialized.getNotSerializedbyElementTypeID(elementType.getId(), dto.getUser().getCountry().getId());
			}else{
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			dto.setNotSerialized(notSerializedElement);
			
			// Se consultan los elementos en la bodega de origen por el id de elemento y estado de registro sea 'U' 
			WarehouseElement warehouseElement = daoWarehouseElement.getWareHouseElementByActualWhAndWhAndElement(dto.getSourceWh().getId(),  dto.getNotSerialized().getElementId());
			Double actualQuantitySource = 0D;
			if(warehouseElement!=null){
				actualQuantitySource = warehouseElement.getActualQuantity();
			}
			if(dto.getQuantity().doubleValue() > actualQuantitySource.doubleValue()){
				throw new BusinessException(ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getCode(),ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getMessage());
			}
			
			//Cerrando la vigencia del registro actual:
			warehouseElement.setRecordStatus(dto.getRecordStatusH());
			this.daoWarehouseElement.updateWarehouseElement(warehouseElement);

			//2. En caso que sobre cantidad del elemento, Crear un nuevo registro en la bodega origen en donde se refleja la cantidad actual del elemento
			Double initialQSource = warehouseElement.getActualQuantity();
			Double newSourceWhActualQty = initialQSource - dto.getQuantity();
			WarehouseElement warehouseElementSource = null;
			if(newSourceWhActualQty.doubleValue() > 0){
				// SI Sobra cantidad del elemento en la bodega
				warehouseElementSource = this.createWarehouseElementRecord(null, dto.getNotSerialized(), newSourceWhActualQty, initialQSource, UtilsBusiness.copyObject(WarehouseVO.class, dto.getSourceWh()), dto.getMovTypeCodeS(), dto.getRecordStatusU(), null, dto.getQuantity(), null, dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());
			} else {
				// NO Sobra cantidad del elemento en la bodega
				warehouseElementSource = this.createWarehouseElementRecord(null, dto.getNotSerialized(), 0D, initialQSource, UtilsBusiness.copyObject(WarehouseVO.class, dto.getSourceWh()), dto.getMovTypeCodeS(), dto.getRecordStatusH(), null, dto.getQuantity(), null, dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());
			}

			//Generar nueva entrada en la bodega de destino
			WarehouseElement we = daoWarehouseElement.getWareHouseElementByActualWhAndWhAndElement(dto.getTargetWs().getId(), dto.getNotSerialized().getElementId());
			Double actualQTarget = 0D; 
			if (we != null){
				actualQTarget = we.getActualQuantity();
				//Se cierra la vigencia del registro en la bodega de destino antes de crear uno nuevo:
				we.setRecordStatus(dto.getRecordStatusH());
				this.daoWarehouseElement.updateWarehouseElement(we);
			}
			
			this.createWarehouseElementRecord(null, warehouseElement.getNotSerialized(), (dto.getQuantity() + actualQTarget) , actualQTarget, UtilsBusiness.copyObject(Warehouse.class, dto.getTargetWs()), dto.getMovTypeCodeE(), dto.getRecordStatusU(), null, dto.getQuantity(), warehouseElementSource, dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());

			//Se registra el retiro parcial del elemento no serializado
			this.createPartialRetirement(warehouseElement, dto.getQuantity(), UtilsBusiness.copyObject(Warehouse.class, dto.getSourceWh()), UtilsBusiness.copyObject(Warehouse.class, dto.getTargetWs()));
			
		}catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveNotSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		}finally{
			log.debug("== Termina moveNotSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		}
	}

	@Override
	public void moveNotSerializedElementToWarehouse(MovementElementDTO dto)
			throws BusinessException {
		log.debug("== Termina moveNotSerializedElementToWarehouse/MovementElementBusinessBean ==");
		NotSerialized notSerializedElement = null;
		try{
			this.validateMoveNotSerializedElementToWarehouse(dto);
			Double initialQ = 0D;
			Double actualQ = 0D;
			// Se realiza la validación que exista el tipo de movimiento
			dto.setMovTypeCodeE(validateMovementType(dto.getMovTypeCodeE()));

			// Se realiza la validación que exista el estado del registro ultimo
			dto.setRecordStatusU(validateRecordStatus(dto.getRecordStatusU(), CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()));
			
			// Se realiza la validación que exista el estado del registro ultimo
			dto.setRecordStatusH(validateRecordStatus(dto.getRecordStatusH(), CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity()));
			
			//Consultar el elemento
			if(dto.getNotSerialized()!=null && dto.getNotSerialized().getElementId() !=null){
				notSerializedElement = daoNotSerialized.getNotSerializedByID(dto.getNotSerialized().getElementId());
			}else if (dto.getElementTypeId()!=null && dto.getElementTypeId().longValue()>0){
				notSerializedElement = daoNotSerialized.getNotSerializedbyElementTypeID(dto.getElementTypeId(), dto.getUser().getCountry().getId());
			}else if(dto.getElementTypeCode() !=null && !dto.getElementTypeCode().isEmpty()){
				ElementType elementType = daoElementType.getElementTypeByCode(dto.getElementTypeCode());
				notSerializedElement = daoNotSerialized.getNotSerializedbyElementTypeID(elementType.getId(), dto.getUser().getCountry().getId());
			}else{
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			dto.setNotSerialized(notSerializedElement);
			
			// Se consulta el elemento en la bodega de destino cuyo estado de registro sea 'U' 
			WarehouseElement warehouseElementU = daoWarehouseElement.getWareHouseElementByActualWhAndWhAndElement(dto.getTargetWs().getId(), dto.getNotSerialized().getElementId());
			if (warehouseElementU != null){
				initialQ = warehouseElementU.getActualQuantity();
				// Se cambia el estado del registro de 'U' a 'H'
				warehouseElementU.setRecordStatus(dto.getRecordStatusH());
				daoWarehouseElement.updateWarehouseElement(warehouseElementU);
			}
			
			actualQ = initialQ + dto.getQuantity();

			// Se crea un nuevo registro en la bodega de destino en estado 'U' que represente la entrada del elemento.
			this.createWarehouseElementRecord(null,
					dto.getNotSerialized(), 
					actualQ, 
					initialQ, 
					UtilsBusiness.copyObject(Warehouse.class, dto.getTargetWs()), 
					dto.getMovTypeCodeE(), 
					dto.getRecordStatusU(), 
					null, 
					dto.getQuantity(), 
					null, 
					dto.getDocumentId(), 
					dto.getDocumentClass(),
					dto.getUser());
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveNotSerializedElementToWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveNotSerializedElementToWarehouse/MovementElementBusinessBean ==");
		}
	}
	
	@Override
	public void deleteNotSerializedElementInWarehouse(MovementElementDTO dto) throws BusinessException{
		log.debug("== Termina deleteNotSerializedElementInWarehouse/MovementElementBusinessBean ==");
		try{
			NotSerialized notSerializedElement = null;
			this.validateDeleteNotSerializedElementInWarehouse(dto);
			
			// Se realiza la validación que exista el tipo de movimiento
			dto.setMovTypeCodeS(validateMovementType(dto.getMovTypeCodeS()));

			// Se realiza la validación que exista el estado del registro ultimo
			dto.setRecordStatusU(validateRecordStatus(dto.getRecordStatusU(), CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()));
			
			// Se realiza la validación que exista el estado del registro ultimo
			dto.setRecordStatusH(validateRecordStatus(dto.getRecordStatusH(), CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity()));
			
			//Consultar el elemento
			if(dto.getNotSerialized()!=null && dto.getNotSerialized().getElementId() !=null){
				notSerializedElement = daoNotSerialized.getNotSerializedByID(dto.getNotSerialized().getElementId());
			}else if (dto.getElementTypeId()!=null && dto.getElementTypeId().longValue()>0){
				notSerializedElement = daoNotSerialized.getNotSerializedbyElementTypeID(dto.getElementTypeId(), dto.getUser().getCountry().getId());
			}else if(dto.getElementTypeCode() !=null && !dto.getElementTypeCode().isEmpty()){
				ElementType elementType = daoElementType.getElementTypeByCode(dto.getElementTypeCode());
				notSerializedElement = daoNotSerialized.getNotSerializedbyElementTypeID(elementType.getId(), dto.getUser().getCountry().getId());
			}else{
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			dto.setNotSerialized(notSerializedElement);
			
			
			//Se consultan los elementos en la bodega de origen por el id de elemento y estado de registro sea 'U' 
			WarehouseElement warehouseElement = daoWarehouseElement.getWareHouseElementByActualWhAndWhAndElement(dto.getSourceWh().getId(),  dto.getNotSerialized().getElementId());
			Double actualQuantitySource = 0D;
			if(warehouseElement!=null){
				actualQuantitySource = warehouseElement.getActualQuantity();
			}
			if(dto.getQuantity().doubleValue() > actualQuantitySource.doubleValue()){
				throw new BusinessException(ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getCode(),ErrorBusinessMessages.ACTUAL_QUANTITY_LOWER_THAN_REQUIRED_MOVEMENT.getMessage());
			}
			
			
			//Cerrando la vigencia del registro actual:
			warehouseElement.setRecordStatus(dto.getRecordStatusH());
			this.daoWarehouseElement.updateWarehouseElement(warehouseElement);

			//2. En caso que sobre cantidad del elemento, Crear un nuevo registro en la bodega origen en donde se refleja la cantidad actual del elemento
			Double initialQSource = warehouseElement.getActualQuantity();
			Double newSourceWhActualQty = initialQSource - dto.getQuantity();
			if(newSourceWhActualQty.doubleValue() > 0){
				// SI Sobra cantidad del elemento en la bodega
				this.createWarehouseElementRecord(null, dto.getNotSerialized(), newSourceWhActualQty, initialQSource, UtilsBusiness.copyObject(WarehouseVO.class, dto.getSourceWh()), dto.getMovTypeCodeS(), dto.getRecordStatusU(), null, dto.getQuantity(), null, dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());
			} else {
				// NO Sobra cantidad del elemento en la bodega
				this.createWarehouseElementRecord(null, dto.getNotSerialized(), 0D, initialQSource, UtilsBusiness.copyObject(WarehouseVO.class, dto.getSourceWh()), dto.getMovTypeCodeS(), dto.getRecordStatusH(), null, dto.getQuantity(), null, dto.getDocumentId(), dto.getDocumentClass(), dto.getUser());
			}
			//Se registra el retiro parcial del elemento no serializado
			this.createPartialRetirement(warehouseElement, dto.getQuantity(), UtilsBusiness.copyObject(Warehouse.class, dto.getSourceWh()), UtilsBusiness.copyObject(Warehouse.class, dto.getTargetWs()));
			
		}catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación deleteNotSerializedElementInWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina deleteNotSerializedElementInWarehouse/MovementElementBusinessBean ==");
		}
	}
	
	
	/**
	 * 
	 * Metodo: Método encargado de validar los valires requeridos
	 * para la ejecución de la opreración validateMoveSerializedElementBetweenWarehouse
	 * @param dto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void validateMoveSerializedElementBetweenWarehouse(MovementElementDTO dto) throws BusinessException{
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSerialized().getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeE(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeE().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeS(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeS().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone().getTimeZoneKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}
	
	/**
	 * 
	 * Metodo: Método encargado de validar los valires requeridos
	 * para la ejecución de la opreración validateMoveSerializedElementToWarehouse
	 * @param dto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void validateMoveSerializedElementToWarehouse(MovementElementDTO dto) throws BusinessException{
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSerialized().getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeE(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeE().getMovTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone().getTimeZoneKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}
	
	/**
	 * 
	 * Metodo: Método encargado de validar los valores requeridos
	 * para la ejecución de la opreración DeleteSerializedElementInWarehouse
	 * @param dto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void validateDeleteSerializedElementInWarehouse(MovementElementDTO dto) throws BusinessException{
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSerialized().getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeS(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeS().getMovTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone().getTimeZoneKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
	}
	
	/**
	 * 
	 * Metodo: Método encargado de validar los valires requeridos
	 * para la ejecución de la opreración moveNotSerializedElementBetweenWarehouse
	 * @param dto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void validateMoveNotSerializedElementBetweenWarehouse(MovementElementDTO dto) throws BusinessException{
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeE(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeE().getMovTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone().getTimeZoneKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getQuantity(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		if(dto.getQuantity().doubleValue()<=0){
			log.debug("La cantidad no puede ser negativa");
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}

	}
	
	/**
	 * 
	 * Metodo: Método encargado de validar los valires requeridos
	 * para la ejecución de la opreración moveNotSerializedElementToWarehouse
	 * @param dto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void validateMoveNotSerializedElementToWarehouse(MovementElementDTO dto) throws BusinessException{
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getTargetWs().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeE(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeE().getMovTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone().getTimeZoneKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getQuantity(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		if(dto.getQuantity().doubleValue()<=0){
					log.debug("La cantidad no puede ser negativa");
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
	}
	
	
	/**
	 * 
	 * Metodo: Método encargado de validar los valires requeridos
	 * para la ejecución de la opreración moveNotSerializedElementToWarehouse
	 * @param dto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void validateDeleteNotSerializedElementInWarehouse(MovementElementDTO dto) throws BusinessException{
		UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getSourceWh().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeS(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getMovTypeCodeS().getMovTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getUser().getSdiiTimeZone().getTimeZoneKey(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dto.getQuantity(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		if(dto.getQuantity().doubleValue()<=0){
					log.debug("La cantidad no puede ser negativa");
			throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		}
	}
	
	/**
	 * Método utilitario para registrar un retiro parcial de un elemento no serializado.
	 * 
	 * Fecha de creación: 09/06/2011
	 * 
	 * @param warehouseElement
	 * @param quantity
	 * @param sourceWh
	 * @param targetWh
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	private void createPartialRetirement(WarehouseElement warehouseElement, Double quantity, Warehouse sourceWh, Warehouse targetWh) throws BusinessException {
		NotSerPartialRetirementId idPartialRet =  new NotSerPartialRetirementId();
		try {
			Long notSerPartialRetirementSec =  daoNotSerPartialRetirement.getNotSerPartialRetirementSequence();
			idPartialRet.setId(notSerPartialRetirementSec);
			idPartialRet.setRetirementDate(new Date());
			idPartialRet.setRetirementQuantity(quantity);
			idPartialRet.setWarehouseElement(warehouseElement);			
			NotSerPartialRetirement partialRet = new NotSerPartialRetirement();
			partialRet.setWarehouseSource(sourceWh);
			partialRet.setWarehouseTarget(targetWh);
			partialRet.setId(idPartialRet);
			this.daoNotSerPartialRetirement.createNotSerPartialRetirement(partialRet);
		} catch (Throwable t) {
			throw this.manageException(t);
		}
	}

	
	/**
	 * 
	 * Metodo: Método utilitario que permite la creación de la cola de notificaciones hacia IBS.
	 * @param dto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private void createMovementCommandQueue(MovementElementDTO dto) throws BusinessException{
		log.debug("== Inicia createMovementCommandQueue/MovementElementBusinessBean ==");
		MovCmdConfig movCmdConfig = null;
		MovCmdStatus movCmdStatus = null;
		
		try {
			// Todos los parámetros son obligatorios
			UtilsBusiness.assertNotNull(dto, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(dto.getSerialized(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(dto.getSerialized().getElementId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

			MovCmdQueueVO vo = new MovCmdQueueVO();
			
			vo.setSerialized(dto.getSerialized());
			vo.setSourceWarehouse(dto.getSourceWh());
			vo.setTargetWarehouse(dto.getTargetWs());
			dto.setMovCmdQueueVO(vo);
			
			
			Long configId = daoMovCmdConfig.getMovCmdConfigByElementMovementDTOCriteria(dto);
			if (configId != null && configId.longValue() > 0){
				movCmdConfig = new MovCmdConfig(configId);
				movCmdStatus = dto.getMovConfigStatusPending();
			} else {
				movCmdStatus = dto.getMovConfigStatusNoConfig();
			}
			dto.getMovCmdQueueVO().setMovCmdStatus(movCmdStatus);
			dto.getMovCmdQueueVO().setMovCmdConfig(movCmdConfig);
			
			//Setea al objeto MovCmdQueue el número del documento y el tipo de documento
			setDocumentIdAndDocumentTypeForQueue(dto);
			
			movementConfigBusiness.createMovementCommandQueue(dto);
		} catch (Throwable t) {
			log.debug("== Error createMovementCommandQueue/MovementElementBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createMovementCommandQueue/MovementElementBusinessBean ==");
		}
	}
	
	/**
	 * Metodo encargado de setear el docuemento sobre la cola
	 * @param dto
	 * @throws BusinessException
	 */
	private void setDocumentIdAndDocumentTypeForQueue(MovementElementDTO dto) throws BusinessException{
		log.debug("== Inicia setDocumentIdAndDocumentTypeForQueue/MovementElementBusinessBean ==");
		try{
			DocumentClass documentClassPojo = getDocumentClassByCode(dto.getDocumentClass());
			if(documentClassPojo!=null){
				dto.getMovCmdQueueVO().setDocumentClass(documentClassPojo);
				if(dto.getDocumentClass().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity())){
					dto.getMovCmdQueueVO().setAdjustment(new Adjustment());
					dto.getMovCmdQueueVO().getAdjustment().setId(dto.getDocumentId());
				}else if(dto.getDocumentClass().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity())){
					dto.getMovCmdQueueVO().setImportLog(new ImportLog());
					dto.getMovCmdQueueVO().getImportLog().setId(dto.getDocumentId());
				}else if(dto.getDocumentClass().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity())){
					dto.getMovCmdQueueVO().setReference(new Reference());
					dto.getMovCmdQueueVO().getReference().setId(dto.getDocumentId());
				}else if(dto.getDocumentClass().equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity())){
					dto.getMovCmdQueueVO().setWorkOrder(new WorkOrder());
					dto.getMovCmdQueueVO().getWorkOrder().setId(dto.getDocumentId());
				}
			}
		} catch (Throwable t) {
			log.error("== Error setDocumentIdAndDocumentTypeForQueue/MovementElementBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina setDocumentIdAndDocumentTypeForQueue/MovementElementBusinessBean ==");
		
		}
	}
	
	/**
	 * 
	 * Metodo: Método utilitario para crear un nuevo registro de WarehouseElement.
	 * @param serializedElement
	 * @param notSerializedElement
	 * @param actualQ
	 * @param initialQ
	 * @param warehouse
	 * @param movementType
	 * @param recordStatus
	 * @param causeAdjustment
	 * @param elementType
	 * @param quantity
	 * @param sourceWarehouseElementRecord
	 * @param documentId
	 * @param documentClass
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	private WarehouseElement createWarehouseElementRecord(Serialized serializedElement, NotSerialized notSerializedElement, Double actualQ, Double initialQ, Warehouse warehouse, 
			MovementType movementType, RecordStatus recordStatus, 
			ElementType elementType, Double quantity, WarehouseElement sourceWarehouseElementRecord, Long documentId, String documentClass, User user) throws BusinessException {
		log.debug("== Inicia createWarehouseElementRecord/MovementElementBusinessBean ==");
		WarehouseElement warehouseElement;
		try {
			warehouseElement = new WarehouseElement();
			warehouseElement.setNotSerialized(notSerializedElement);
			warehouseElement.setSerialized(serializedElement);
			warehouseElement.setActualQuantity(actualQ);
			warehouseElement.setInitialQuantity(initialQ);
			warehouseElement.setWarehouseId(warehouse);
			warehouseElement.setMovementType(movementType);
			warehouseElement.setMovementDate(UtilsBusiness.getCurrentDateByTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey(), new Date()));
			warehouseElement.setRecordStatus(recordStatus);
			
			// asigna el elemento vinculado
			if(serializedElement != null){
			    warehouseElement.setLinkedSerId(serializedElement.getSerialized());
			}
			
			DocumentClass documentClassPojo = getDocumentClassByCode(documentClass);
			if(documentClassPojo!=null){
				warehouseElement.setDocumentId(documentId);
				warehouseElement.setDocumentClass(documentClassPojo);
			}
			
			if (notSerializedElement != null &&notSerializedElement.getElement()!=null && (elementType == null || elementType.getId() == null || elementType.getId().longValue() == 0)){
				elementType = notSerializedElement.getElement().getElementType(); 
			}
			
			if (serializedElement != null && serializedElement.getElement() != null && (elementType == null || elementType.getId() == null || elementType.getId().longValue() == 0)){
				elementType = serializedElement.getElement().getElementType(); 
			}
			warehouseElement.setElementType(elementType);
			warehouseElement.setMovedQuantity(quantity);
			warehouseElement.setSourceRecord(sourceWarehouseElementRecord);
		
			warehouseElement = daoWarehouseElement.createWarehouseElement(warehouseElement);
			return warehouseElement;
		} catch (Throwable t){
			log.error("== Error createWarehouseElementRecord/MovementElementBusinessBean ==");
			throw this.manageException(t);
		} finally {
			log.debug("== Termina createWarehouseElementRecord/MovementElementBusinessBean ==");
		}
	}

	/**
	 * 
	 * Metodo:Método utilitario encargado de Crear el objeto DocumentClass a partir de el código 
	 * @param codeDocumentClass
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	private DocumentClass getDocumentClassByCode(String codeDocumentClass) throws BusinessException{
		DocumentClass documentClass = new DocumentClass();
		try{
			
			if(codeDocumentClass==null || codeDocumentClass.equals("")){
				return null;
			}else{
				if(codeDocumentClass.equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT.getCodeEntity())){
					documentClass.setId(new Long(CodesBusinessEntityEnum.DOCUMENT_CLASS_ADJUSTMENT_ID.getCodeEntity()));
				}else if(codeDocumentClass.equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity())){
					documentClass.setId(new Long(CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG_ID.getCodeEntity()));
				}else if(codeDocumentClass.equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE.getCodeEntity())){
					documentClass.setId(new Long(CodesBusinessEntityEnum.DOCUMENT_CLASS_REFERENCE_ID.getCodeEntity()));
				}else if(codeDocumentClass.equals(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity())){
					documentClass.setId(new Long(CodesBusinessEntityEnum.DOCUMENT_CLASS_WORK_ORDER.getCodeEntity()));
				}
			}
			
		}catch (Throwable e) {
			this.manageException(e);
		}
		return documentClass;
	}


	@Override
	public MovementElementDTO fillMovementTypeAndRecordStatus(String movementTypeCodeE, String movementTypeCodeS)
			throws BusinessException {
		log.debug("== Inicia moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		
		//UtilsBusiness.assertNotNull(dto.getMovTypeCodeS().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		MovementElementDTO dto = null;
		MovementType movementTypeE = null;
		MovementType movementTypeS = null;
		RecordStatus recordStatusU = null;
		RecordStatus recordStatusH = null;
		try {
			dto = new MovementElementDTO();
			
			// Se realiza la validación que exista el estado del registro último
			recordStatusU = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			if(recordStatusU == null){
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
			}
			
			// Se realiza la validación que exista el estado del registro histórico
			recordStatusH = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
			if (recordStatusH == null){
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
			}
			
			// Se realiza la validación que exista el tipo de movimiento de entrada
			if(movementTypeCodeE!=null && !movementTypeCodeE.isEmpty()){
				movementTypeE = daoMovementType.getMovementTypeByCode(movementTypeCodeE);
				if(movementTypeE == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				}
			}
			
			// Se realiza la validación que exista el tipo de movimiento de salida
			if(movementTypeCodeS!=null && !movementTypeCodeS.isEmpty()){
				movementTypeS = daoMovementType.getMovementTypeByCode(movementTypeCodeS);
				if (movementTypeS == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				}
			}
			
			//Se consultan los estados de la cola de IBS
			List<MovCmdStatus> movCmdStatusList= null;
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			
			this.fillStatusQueue(dto);
			
			dto.setRecordStatusU(recordStatusU);
			dto.setRecordStatusH(recordStatusH);
			dto.setMovTypeCodeE(movementTypeE);
			dto.setMovTypeCodeS(movementTypeS);
			return dto;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		}
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MovementElementDTO fillMovementTypeAndRecordStatusMassive(String movementTypeCodeE, String movementTypeCodeS)
			throws BusinessException {
		log.debug("== Inicia moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		
		//UtilsBusiness.assertNotNull(dto.getMovTypeCodeS().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		MovementElementDTO dto = null;
		MovementType movementTypeE = null;
		MovementType movementTypeS = null;
		RecordStatus recordStatusU = null;
		RecordStatus recordStatusH = null;
		try {
			dto = new MovementElementDTO();
			
			// Se realiza la validación que exista el estado del registro último
			recordStatusU = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			if(recordStatusU == null){
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
			}
			
			// Se realiza la validación que exista el estado del registro histórico
			recordStatusH = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
			if (recordStatusH == null){
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
			}
			
			// Se realiza la validación que exista el tipo de movimiento de entrada
			if(movementTypeCodeE!=null && !movementTypeCodeE.isEmpty()){
				movementTypeE = daoMovementType.getMovementTypeByCode(movementTypeCodeE);
				if(movementTypeE == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				}
			}
			
			// Se realiza la validación que exista el tipo de movimiento de salida
			if(movementTypeCodeS!=null && !movementTypeCodeS.isEmpty()){
				movementTypeS = daoMovementType.getMovementTypeByCode(movementTypeCodeS);
				if (movementTypeS == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				}
			}
			
			//Se consultan los estados de la cola de IBS
			List<MovCmdStatus> movCmdStatusList= null;
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			
			this.fillStatusQueue(dto);
			
			dto.setRecordStatusU(recordStatusU);
			dto.setRecordStatusH(recordStatusH);
			dto.setMovTypeCodeE(movementTypeE);
			dto.setMovTypeCodeS(movementTypeS);
			return dto;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		}
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void fillData(String movementTypeCodeE, String movementTypeCodeS, MovementType movementTypeE, 
			MovementType movementTypeS, RecordStatus recordStatusU, RecordStatus recordStatusH) throws BusinessException{
		
		MovementType movementTypeETmp = null;
		MovementType movementTypeSTmp = null;
		RecordStatus recordStatusUTmp = null;
		RecordStatus recordStatusHTmp = null;
		
		try {
			recordStatusUTmp = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			if(recordStatusUTmp == null){
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
			} else {
				BeanUtils.copyProperties(recordStatusU, recordStatusUTmp);
			}
			
			recordStatusHTmp = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
			if (recordStatusHTmp == null){
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
			} else {
				BeanUtils.copyProperties(recordStatusH, recordStatusHTmp);
			}

			if(movementTypeCodeE!=null && !movementTypeCodeE.isEmpty()){
				movementTypeETmp = daoMovementType.getMovementTypeByCode(movementTypeCodeE);
				if(movementTypeETmp == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				} else {
					BeanUtils.copyProperties(movementTypeE, movementTypeETmp);
				}
			}
			
			if(movementTypeCodeS!=null && !movementTypeCodeS.isEmpty()){
				movementTypeSTmp = daoMovementType.getMovementTypeByCode(movementTypeCodeS);
				if (movementTypeSTmp == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				} else {
					BeanUtils.copyProperties(movementTypeS, movementTypeSTmp);
				}
			}
			
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		}
		
		
	}
	
	@Override
	public MovementElementDTO fillMovementTypeAndRecordStatusMassive(String movementTypeCodeE, String movementTypeCodeS,RecordStatus[] recordStatusU,RecordStatus[] recordStatusH)
			throws BusinessException {
		log.debug("== Inicia moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		
		//UtilsBusiness.assertNotNull(dto.getMovTypeCodeS().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		MovementElementDTO dto = null;
		MovementType movementTypeE = null;
		MovementType movementTypeS = null;
		try {
			dto = new MovementElementDTO();
			
			// Se realiza la validación que exista el estado del registro último
			if(recordStatusU[0] == null){
				recordStatusU[0] = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			}
			if(recordStatusU[0] == null){
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
			}
			
			// Se realiza la validación que exista el estado del registro histórico
			if(recordStatusH[0] == null){
				recordStatusH[0] = daoRecordStatus.getRecordStatusByCode(CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
			}
			if (recordStatusH[0] == null){
				throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
			}
			
			// Se realiza la validación que exista el tipo de movimiento de entrada
			if(movementTypeCodeE!=null && !movementTypeCodeE.isEmpty()){
				movementTypeE = daoMovementType.getMovementTypeByCode(movementTypeCodeE);
				if(movementTypeE == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				}
			}
			
			// Se realiza la validación que exista el tipo de movimiento de salida
			if(movementTypeCodeS!=null && !movementTypeCodeS.isEmpty()){
				movementTypeS = daoMovementType.getMovementTypeByCode(movementTypeCodeS);
				if (movementTypeS == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				}
			}
			
			//Se consultan los estados de la cola de IBS
			List<MovCmdStatus> movCmdStatusList= null;
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			
			this.fillStatusQueue(dto);
			
			dto.setRecordStatusU(recordStatusU[0]);
			dto.setRecordStatusH(recordStatusH[0]);
			dto.setMovTypeCodeE(movementTypeE);
			dto.setMovTypeCodeS(movementTypeS);
			return dto;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		}
		
	}
	
	@Override
	public MovementElementDTO fillMovementTypeAndRecordStatusMassive(MovementType movementTypeE, MovementType movementTypeS, RecordStatus recordStatusU, RecordStatus recordStatusH)
			throws BusinessException {
		log.debug("== Inicia moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		
		MovementElementDTO dto = null;

		try {
			dto = new MovementElementDTO();
									
			//Se consultan los estados de la cola de IBS
			List<MovCmdStatus> movCmdStatusList= null;
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			
			this.fillStatusQueue(dto);
			
			dto.setRecordStatusU(recordStatusU);
			dto.setRecordStatusH(recordStatusH);
			dto.setMovTypeCodeE(movementTypeE);
			dto.setMovTypeCodeS(movementTypeS);
			return dto;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina moveSerializedElementBetweenWarehouse/MovementElementBusinessBean ==");
		}
		
	}
	
	/**
	 * Se consultan los estados de la cola de IBS y de mapean en el DTO
	 * @throws BusinessException
	 */
	private void fillStatusQueue(MovementElementDTO dto) throws BusinessException{
		log.debug("== Inicia fillStatusQueue/MovementElementBusinessBean ==");
		try{

			//
			List<MovCmdStatus> movCmdStatusList= null;
			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_NO_CONFIG.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			dto.setMovConfigStatusNoConfig(movCmdStatusList.get(0));

			movCmdStatusList = daoMovCmdStatus.findByProperty(MovCmdStatusDAO.CMD_STATUS_CODE, CodesBusinessEntityEnum.MOV_CMD_STATUS_PENDING.getCodeEntity());
			UtilsBusiness.assertNotEmpty(movCmdStatusList, ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getCode(), ErrorBusinessMessages.MOV_CMD_STATUS_NOT_EXIST.getMessage());
			dto.setMovConfigStatusPending(movCmdStatusList.get(0));
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación fillStatusQueue/MovementElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina fillStatusQueue/MovementElementBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Encargado de validar si existe el objeto movement Type
	 * Si no viene se consulta a partir del código
	 * @param movementType <tipo> <descripcion>
	 * @author waguilera
	 */
	private MovementType validateMovementType(MovementType movementType) throws BusinessException{
		log.debug("== Inicia validateMovementType/WarehouseElementBusinessBean ==");
		try{
			if(movementType.getId()==null || movementType.getId().longValue()<=0L){
				movementType = daoMovementType.getMovementTypeByCode(movementType.getMovTypeCode());
				if(movementType == null){
					throw new BusinessException(ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getCode(),ErrorBusinessMessages.MOVEMENT_TYPE_NOT_EXIST.getMessage());
				}
			}
			return movementType;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación validateMovementType/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina validateMovementType/WarehouseElementBusinessBean ==");
		}
	}
	

	/**
	 * 
	 * Metodo: Encargado de validar si existe el objeto movement Type
	 * Si no viene se consulta a partir del código
	 * @param movementType <tipo> <descripcion>
	 * @author waguilera
	 */
	private RecordStatus validateRecordStatus(RecordStatus recordStatus, String codeRecordStatus) throws BusinessException{
		log.debug("== Inicia validateRecordStatus/WarehouseElementBusinessBean ==");
		try{
			if(recordStatus==null){
				recordStatus = daoRecordStatus.getRecordStatusByCode(codeRecordStatus);
				if(recordStatus == null){
					throw new BusinessException(ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getCode(),ErrorBusinessMessages.RECORD_STATUS_NOT_EXIST.getMessage());
				}
			}
			return recordStatus;
		} catch(Throwable ex){
			log.error("== Error al tratar de ejecutar la operación validateRecordStatus/WarehouseElementBusinessBean ==");
			throw this.manageException(ex);
		} finally{
			log.debug("== Termina validateRecordStatus/WarehouseElementBusinessBean ==");
		}
	}




	
}
