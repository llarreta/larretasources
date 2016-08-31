package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.file.impl.FileDetailProcessBusinessBean;
import co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.stock.ImportLogItemDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ItemStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;

/**
 * Clase que implementa la funcionalidad para procesar archivos planos y pasarlos de
 * la bodega de Calidad a la de disponibles o devoluciones, dependiendo del estado del 
 * elemento 1 - Bueno | 0 - Defectuoso
 * @author hcorredor
 *
 */
@Stateless(name="FileProcessorConfirmSerialItemsFromImportLogLocal",mappedName="ejb/FileProcessorConfirmSerialItemsFromImportLogLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorConfirmSerialItemsFromImportLog extends BasicFileProcessor implements FileProcessorConfirmSerialItemsFromImportLogLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileDetailProcessBusinessBean.class);
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface=WarehouseElementBusinessBeanLocal.class)
    private WarehouseElementBusinessBeanLocal businessWarehouseElement;
	
	@EJB(name="ImportLogItemBusinessBeanLocal",beanInterface=ImportLogItemBusinessBeanLocal.class)
	private ImportLogItemBusinessBeanLocal businessImportLogItem;
	
	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;
	
	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
	
	@EJB(name="WarehouseDAOLocal",beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWarehouse;
	
	@EJB(name="ImportLogItemDAOLocal",beanInterface=ImportLogItemDAOLocal.class)
	private ImportLogItemDAOLocal daoImportLogItem;
	
	@EJB(name = "ItemStatusDAOLocal", beanInterface = ItemStatusDAOLocal.class)
	private ItemStatusDAOLocal daoItemStatus;
	
	@EJB(name = "ImportLogBusinessBeanLocal", beanInterface = ImportLogBusinessBeanLocal.class)
	private ImportLogBusinessBeanLocal businessImpLog;
	
	@EJB(name = "MovementElementBusinessBeanLocal", beanInterface = MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
	
	public FileProcessorConfirmSerialItemsFromImportLog() {
		String[] columnTitles = new String[] {
				ApplicationTextEnum.WAREHOUSE_QUALITY.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED_ELEMENT.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}
	
	private static int POS_WH_QA = 0;
	private static int POS_SERIAL = 1;
	private static int POS_LINKED_SERIAL = 2;
	
	private static String PARAM_SERIALIZED = "ser";
	private static String PARAM_WH_QA = "wh_qa";
	private static String PARAM_WH_DISPOSABLE = "wh_dis";
	private static String PARAM_WH_TRANSIT = "wh_tran";
	private static String PARAM_IMPORT_LOG = "imp_log";
	
	private static String PARAM_WH_STOCK_BULK = "stockBulk";
	
	private Warehouse whQuality = null;
	private Warehouse whDisposs = null;
	private Warehouse whDispossStockBulk = null;
	private boolean hasRecordsForAvailabilityWh = false;
	private boolean hasRecordsForQAWh = false;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor#processData(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO) throws BusinessException {
		
		try {
			String[] record = fileRecordDTO.getRowData();
			
			doValidationsprocessRecord(fileRecordDTO,record);
			String isWHQA = record[POS_WH_QA];
			String serial = record[POS_SERIAL];
			String linkedSerial = record[POS_LINKED_SERIAL];
			
			serial = toUpper(serial);
			linkedSerial = (linkedSerial == null ? null : linkedSerial.trim().toUpperCase());
			
			Serialized serialized = (Serialized) fileRecordDTO.getParam(PARAM_SERIALIZED);
			ImportLog importLog = (ImportLog) fileRecordDTO.getParam(PARAM_IMPORT_LOG);
			
			Long sourceWhId = (Long) fileRecordDTO.getParam(PARAM_WH_TRANSIT);
			Long targetWhId = 0L;
			
			//seleccionar el tipo de movimiento adecuado
			String movementTypeIn = null, movementTypeOut = null;
			if(importLog.getSupplier() != null && importLog.getSupplier() != null && importLog.getSupplier().getCountryId() != null) {
				if(importLog.getSupplier().getCountryId().getId().equals(getUser().getCountry().getId())){
					movementTypeIn = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_NATIONAL_BUY.getCodeEntity();
					movementTypeOut = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_EXIT.getCodeEntity();
				}else{
					movementTypeIn = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_INTERNATIONAL_BUY.getCodeEntity();
					movementTypeOut = CodesBusinessEntityEnum.WH_MOVEMENT_TYPE_TRANSIT_EXIT.getCodeEntity();
				}
			} else {
				throw new BusinessException("no fue posible obtener el pais del registro de importación con id = " + importLog.getId());
			}
			
			//seleccionar la bodega destino
			if (isWHQA.equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity())){
				targetWhId = (Long) fileRecordDTO.getParam(PARAM_WH_QA);
			}else if (isWHQA.equals(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity())){
				if(serialized.getSerialized()==null&&serialized.getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity())){
					targetWhId = (Long) fileRecordDTO.getParam(PARAM_WH_STOCK_BULK); 
				}else{
					targetWhId = (Long) fileRecordDTO.getParam(PARAM_WH_DISPOSABLE);
				}
			}
			ItemStatus itemStatus = daoItemStatus.getItemStatusByCode(CodesBusinessEntityEnum.ITEM_STATUS_RECEPTED.getCodeEntity());
			
			
			//Consulta de la bodegas involucradas en el proceso
			Warehouse warehouseTargetProcess = daoWarehouse.getWarehouseByID(targetWhId);
			Warehouse warehouseSourceProcess = daoWarehouse.getWarehouseByID(sourceWhId);
			
			//mover el elemento
			MovementElementDTO dtoGenerics = businessMovementElement.fillMovementTypeAndRecordStatus(movementTypeIn, movementTypeOut);
			MovementElementDTO dtoMovement = new MovementElementDTO(this.getUploadFile().getUser(), 
					UtilsBusiness.copyObject(WarehouseVO.class, warehouseSourceProcess),
					UtilsBusiness.copyObject(WarehouseVO.class, warehouseTargetProcess), 
					serialized,importLog.getId(), 
					CodesBusinessEntityEnum.DOCUMENT_CLASS_IMPORT_LOG.getCodeEntity(), 
					dtoGenerics.getMovTypeCodeE(), 
					dtoGenerics.getMovTypeCodeS(),
					dtoGenerics.getRecordStatusU(), 
					dtoGenerics.getRecordStatusH(), 
					true, 
					CodesBusinessEntityEnum.PROCESS_CODE_IBS_IMPORT_LOG.getCodeEntity(),
					dtoGenerics.getMovConfigStatusPending(),
					dtoGenerics.getMovConfigStatusNoConfig());
			businessMovementElement.moveSerializedElementBetweenWarehouse(dtoMovement);
			
			
			
			//Cambia el estado
			ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByElementId(serialized.getElementId());
			
			importLogItem.setItemStatus(itemStatus);
			daoImportLogItem.updateImportLogItem(importLogItem);
			
			if (importLog.getUser()!=null && importLog.getUser().getId()!=null){
				businessImpLog.verificaEstadoImportLog(importLog, importLog.getUser().getId());
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void doValidationsprocessRecord(FileRecordDTO fileRecordDTO,String[] record) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
		
		boolean firstImportLogFound = false;
		String isWHQA = "", 
		       serial = "", 
		       linkedSerial = "";
		ImportLogItem importLogItemIni = null;
		Warehouse whTransit = null;

		isWHQA = record[POS_WH_QA];
		serial = record[POS_SERIAL];
		linkedSerial = record[POS_LINKED_SERIAL];
		
		serial = toUpper(serial);
		linkedSerial = toUpper(linkedSerial);
		
		if(StringUtils.isBlank(isWHQA)) {
			throw new BusinessException("El campo " + getColumnTitles()[POS_WH_QA] + " es obligatorio");
		}
		
		if (!isValidSdiiBinary(isWHQA)) {
			throw new BusinessException("El valor de 'Bodega Calidad', debe ser S o N");
		}
		
		if(StringUtils.isBlank(serial)) {
			throw new BusinessException("El campo " + getColumnTitles()[POS_SERIAL] + " es obligatorio");
		}
		
		//validar que el elemento se encuentre en el país correcto
		WarehouseElementVO warehouseElementVO = businessWarehouseElement.getWarehouseElementBySerialActive(serial,this.getUploadFile().getCountry().getId());
		if(warehouseElementVO != null && warehouseElementVO.getWarehouseId() != null && warehouseElementVO.getWarehouseId().getCountry() != null) {
			Long elementCountryId = warehouseElementVO.getWarehouseId().getCountry().getId();
			if(!getUploadFile().getCountry().getId().equals(elementCountryId)) {
				throw new BusinessException("El elemento con serial " + serial + " se encuentra en una bodega que no está en el país desde el que se cargó el archivo");
			}
		}
		
		if (warehouseElementVO == null) {
			throw new BusinessException("El elemento con serial [" + serial + "] no existe en el sistema.");
		}
		
		Serialized serialized = warehouseElementVO.getSerialized();
		
		if (serialized == null) {
			throw new BusinessException("El elemento con serial [" + serial + "] no existe en el sistema.");
		}
		
		//validar que el elemento si tenga un elemento serializado
		if ( linkedSerial!=null && !linkedSerial.isEmpty() && serialized.getSerialized()==null ){
			throw new BusinessException("Elemento con serial "+ serial +" NO tiene vinculado ningun elemento");
		}
		
		//validar que el valor de la columna serial no esté vinculado a otro elemento
		List<Serialized> linkedElements = daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
		if(linkedElements != null && !linkedElements.isEmpty()) {
			throw new BusinessException("El serial " + serial + " está vinculado a otro elemento. Si realmente se desea confirmar este elemento, se debe poner este serial en la columna de serial vinculado");
		}
		
		//validar que el serial del vinculado sea efectivamente el que se puso en el archivo
		if ( serialized.getSerialized()!=null ){
			if ( !linkedSerial.equals( serialized.getSerialized().getSerialCode() ) ){
				throw new BusinessException("Elemento con serial "+ serial +", NO tiene como serial vinculado el serial "+ linkedSerial);
			}
		}
		
		//validar que el serial vinculado del elemento coincida con el registrado en el archivo
		Serialized linkedSerialized = serialized.getSerialized();
		if(linkedSerialized != null) {
			if(StringUtils.isBlank(linkedSerial)) {
				throw new BusinessException("Debe agregar el serial vinculado, ya que el elemento con serial " + serial + " tiene un elemento vinculado");
			}
			if(!linkedSerialized.getSerialCode().equals(linkedSerial)) {
				throw new BusinessException("El serial vinculado registrado no coincide con el serial vinculado del elemento");
			}
		}
		
		// Toma el registro de importacion del primer elemento. Se debe
		// validar que todos los elementos
		// sean del mismo registro de importacion.
		if (!firstImportLogFound) {
			importLogItemIni = daoImportLogItem.getImportLogItemByElementId(serialized.getElementId());
			
			if(importLogItemIni == null) {
				throw new BusinessException("no se encontró el registro de importación al buscarlo por el elemento con serial " + serial);
			}

			firstImportLogFound = true;
			
			whTransit = businessImportLogItem.getLogisticOperatorWarehouse(importLogItemIni.getImportLog().getDealer().getId() , CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
			
			if(whTransit == null) {
				throw new BusinessException("no se encontró la bodega tránsito");
			}
			
			// valida el pais del registro de importación y que el operador logistico seleccionado concide con el asociado al registro de importacion.
			if(hasRecordsForAvailabilityWh) {
				
				if(whDisposs != null && whDisposs.getCountry() != null && whDisposs.getCountry().getId() != null) {
					if(!whDisposs.getCountry().getId().equals( super.getUploadFile().getCountry().getId() )) {
						throw new BusinessException("La bodega de disponibles no corresponde con el país donde se cargó el archivo");
					}
				}
				
				validateLogisticOperator(importLogItemIni);
				
			}
			
			if(hasRecordsForQAWh) {
				
				if(whQuality != null && whQuality.getCountry() != null && whQuality.getCountry().getId() != null) {
					if(!whQuality.getCountry().getId().equals( super.getUploadFile().getCountry().getId() )) {
						throw new BusinessException("La bodega de calidad no corresponde con el país donde se cargó el archivo");
					}
				}
				
			}
			
			// valida que el elemento esta en estado enviado
			validateImportLogItemStatus(importLogItemIni);
			
			// Valida si registro de importacion esta en estado Enviado, confirmado Parcial o Inconsistente.
			validateImportLogStatus(importLogItemIni.getImportLog());
			
			if (importLogItemIni == null) {
				throw new BusinessException("El serial ingresado no pertenece a ningún registro de Importacion.");
			}
		}else {//se valida que cada elemento en el archivo pertenezcan al mismo registro de importación
			
			ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByElementId(serialized.getElementId());
			
			if(importLogItem == null) {
				throw new BusinessException("no se encontró el registro de importación al buscarlo por el elemento con serial " + serial);
			}
			
			if (importLogItem.getImportLog() == null || importLogItem.getImportLog().getId() == null) {
				throw new BusinessException("El serial ingresado no pertenece a ningún registro de Importacion.");
			}else if ( !importLogItemIni.getImportLog().getId().equals(importLogItem.getImportLog().getId()) ) {
				throw new BusinessException("El serial ingresado no pertenece al mismo registro de importacion de la primera fila del archivo.");
			}
			
		}

		fileRecordDTO.clearParams();
		fileRecordDTO.addParam(PARAM_SERIALIZED, serialized);//guardar el objeto para evitar una consulta en el momento de procesar el archivo
		fileRecordDTO.addParam(PARAM_WH_TRANSIT, whTransit.getId());
		fileRecordDTO.addParam(PARAM_WH_QA, whQuality != null ? whQuality.getId() : -1L);
		fileRecordDTO.addParam(PARAM_WH_DISPOSABLE, whDisposs != null ? whDisposs.getId() : -1L);
		fileRecordDTO.addParam(PARAM_IMPORT_LOG, importLogItemIni.getImportLog());
		fileRecordDTO.addParam(PARAM_WH_STOCK_BULK, whDispossStockBulk != null ? whDispossStockBulk.getId() : -1L );
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.IBasicFileProcessor#doGlobalValidationsAndDropNotValidRecords(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(List<FileRecordDTO> fileData) {
		
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		
//		boolean firstImportLogFound = false;
//		String isWHQA = "", serial = "", linkedSerial = "", elementType = "";
//		ImportLogItem importLogItemIni = null;
//		Warehouse whTransit = null;
		whQuality = null;
		whDisposs = null;
		whDispossStockBulk = null;
		
		hasRecordsForAvailabilityWh = hasRecordsForAvailabilityWh(fileData);
		hasRecordsForQAWh = hasRecordsForQAWh(fileData);
		
		try {
			if(hasRecordsForAvailabilityWh) {
				//1. Consulta el parámetro del registro de importación
				UploadFileParam paramWhAvailable = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_WHAVAILABLE.getCodeEntity());				
				if(paramWhAvailable != null && NumberUtils.isNumber(paramWhAvailable.getParamValue())) {
					whDisposs = daoWarehouse.getWarehouseByID(Long.parseLong(paramWhAvailable.getParamValue()));
					if(whDisposs!=null){
						whDispossStockBulk = businessImportLogItem.getLogisticOperatorWarehouse(whDisposs.getDealerId().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_S02.getCodeEntity());	
					}
					
				} else {
					throw new BusinessException("no se encontró un valor válido para el parámetro Bodega de Tipo Disponible.");
				}
			}
			
			if(hasRecordsForQAWh) {
				UploadFileParam paramWhQA = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_WHQUALITY.getCodeEntity());
				//Toma la referencia de las Bodegas
				if(paramWhQA != null && NumberUtils.isNumber(paramWhQA.getParamValue())) {
					whQuality = daoWarehouse.getWarehouseByID( Long.parseLong( paramWhQA.getParamValue()) );
				}else {
					throw new BusinessException("no se encontró un valor válido para el parámetro Bodega de Tipo Calidad.");
				}
			}
		} catch(Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()) );			
			fileData.clear();//no se debe procesar ningún registro
		}
		
//		for (Iterator<FileRecordDTO> iterator = fileData.iterator(); iterator.hasNext();) {
//			
//			FileRecordDTO fileRecordDTO = iterator.next();
//			
//			String[] record = fileRecordDTO.getRowData();	
//			isWHQA = record[POS_WH_QA];
//			serial = record[POS_SERIAL];
//			linkedSerial = record[POS_LINKED_SERIAL];
//			elementType = record[POS_ELEMENT_TYPE_CODE];
//			
//			serial = toUpper(serial);
//			linkedSerial = toUpper(linkedSerial);
//			
//			try {
//			
//				if(StringUtils.isBlank(isWHQA)) {
//					throw new BusinessException("El campo " + columnTitles[POS_WH_QA] + " es obligatorio");
//				}
//				
//				if (!isValidSdiiBinary(isWHQA)) {
//					throw new BusinessException("El valor de 'Bodega Calidad', debe ser S o N");
//				}
//				
//				if(StringUtils.isBlank(serial)) {
//					throw new BusinessException("El campo " + columnTitles[POS_SERIAL] + " es obligatorio");
//				}
//				
//				if(StringUtils.isBlank(elementType)) {
//					throw new BusinessException("El campo " + columnTitles[POS_ELEMENT_TYPE_CODE] + " es obligatorio");
//				}
//				
//				//validar que el elemento se encuentre en el país correcto
//				WarehouseElementVO warehouseElementVO = businessWarehouseElement.getWarehouseElementBySerialActive(serial);
//				if(warehouseElementVO != null && warehouseElementVO.getWarehouseId() != null && warehouseElementVO.getWarehouseId().getCountry() != null) {
//					Long elementCountryId = warehouseElementVO.getWarehouseId().getCountry().getId();
//					if(!getUploadFile().getCountry().getId().equals(elementCountryId)) {
//						throw new BusinessException("El elemento con serial " + serial + " se encuentra en una bodega que no está en el país desde el que se cargó el archivo");
//					}
//				}
//				
//				if (warehouseElementVO == null) {
//					throw new BusinessException("El elemento con serial [" + serial + "] no existe en el sistema.");
//				}
//				
//				Serialized serialized = warehouseElementVO.getSerialized();
//				
//				if (serialized == null) {
//					throw new BusinessException("El elemento con serial [" + serial + "] no existe en el sistema.");
//				}
//				
//				//validar que el elemento si tenga un elemento serializado
//				if ( linkedSerial!=null && !linkedSerial.isEmpty() && serialized.getSerialized()==null ){
//					throw new BusinessException("Elemento vinculado con serial "+ serial +" no tiene vinculado ningun elemento, verifique la columna Serial Elemento Vinculado");
//				}
//				
//				//validar que el tipo de elemento en el archivo coincida con el tipo de elemento del elemento serializado 
//				if(serialized.getElement() != null && serialized.getElement().getElementType() != null && serialized.getElement().getElementType().getTypeElementCode() != null) {
//					if(!serialized.getElement().getElementType().getTypeElementCode().equals(elementType)) {
//						throw new BusinessException("El tipo de elemento registrado no corresponde con el tipo de elemento según el serial del elemento");
//					}
//				} else {
//					throw new BusinessException("no se pudo determinar el tipo de elemento almacenado en el sistema para el serial: " + serial);
//				}
//				
//				//validar que el valor de la columna serial no esté vinculado a otro elemento
//				List<Serialized> linkedElements = daoSerialized.getLinkedSerializedBySerializedId(serialized.getElementId());
//				if(linkedElements != null && !linkedElements.isEmpty()) {
//					throw new BusinessException("El serial " + serial + " está vinculado a otro elemento. Si realmente se desea confirmar este elemento, se debe poner este serial en la columna de serial vinculado");
//				}
//				
//				//validar que el serial del vinculado sea efectivamente el que se puso en el archivo
//				if ( serialized.getSerialized()!=null ){
//					if ( !linkedSerial.equals( serialized.getSerialized().getSerialCode() ) ){
//						throw new BusinessException("Elemento vinculado con serial "+ serial +", no corresponde a elemento con serial "+ linkedSerial);
//					}
//				}
//				
//				//validar que el serial vinculado del elemento coincida con el registrado en el archivo
//				Serialized linkedSerialized = serialized.getSerialized();
//				if(linkedSerialized != null) {
//					if(StringUtils.isBlank(linkedSerial)) {
//						throw new BusinessException("Debe agregar el serial vinculado, ya que el elemento con serial " + serial + " tiene un elemento vinculado");
//					}
//					if(!linkedSerialized.getSerialCode().equals(linkedSerial)) {
//						throw new BusinessException("El serial vinculado registrado no coincide con el serial vinculado del elemento");
//					}
//				}
//				
//				// Toma el registro de importacion del primer elemento. Se debe
//				// validar que todos los elementos
//				// sean del mismo registro de importacion.
//				if (!firstImportLogFound) {
//					importLogItemIni = daoImportLogItem.getImportLogItemByElementId(serialized.getElementId());
//					
//					if(importLogItemIni == null) {
//						throw new BusinessException("no se encontró el registro de importación al buscarlo por el elemento con serial " + serial);
//					}
//
//					firstImportLogFound = true;
//					
//					whTransit = businessImportLogItem.getLogisticOperatorWarehouse(importLogItemIni.getImportLog().getDealer().getId() , CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
//					
//					if(whTransit == null) {
//						throw new BusinessException("no se encontró la bodega tránsito");
//					}
//					
//					// valida el pais del registro de importación y que el operador logistico seleccionado concide con el asociado al registro de importacion.
//					if(hasRecordsForAvailabilityWh) {
//						
//						if(whDisposs != null && whDisposs.getCountry() != null && whDisposs.getCountry().getId() != null) {
//							if(!whDisposs.getCountry().getId().equals( super.getUploadFile().getCountry().getId() )) {
//								throw new BusinessException("La bodega de disponibles no corresponde con el país donde se cargó el archivo");
//							}
//						}
//						
//						validateLogisticOperator(importLogItemIni);
//						
//					}
//					
//					if(hasRecordsForQAWh) {
//						
//						if(whQuality != null && whQuality.getCountry() != null && whQuality.getCountry().getId() != null) {
//							if(!whQuality.getCountry().getId().equals( super.getUploadFile().getCountry().getId() )) {
//								throw new BusinessException("La bodega de calidad no corresponde con el país donde se cargó el archivo");
//							}
//						}
//						
//					}
//					
//					// valida que el elemento esta en estado enviado
//					validateImportLogItemStatus(importLogItemIni);
//					
//					// Valida si registro de importacion esta en estado Enviado, confirmado Parcial o Inconsistente.
//					validateImportLogStatus(importLogItemIni.getImportLog());
//					
//					if (importLogItemIni == null) {
//						throw new BusinessException("El serial ingresado no pertenece a ningún registro de Importacion.");
//					}
//				}
//				
//				else {//se valida que cada elemento en el archivo pertenezcan al mismo registro de importación
//					
//					ImportLogItem importLogItem = daoImportLogItem.getImportLogItemByElementId(serialized.getElementId());
//					
//					if(importLogItem == null) {
//						throw new BusinessException("no se encontró el registro de importación al buscarlo por el elemento con serial " + serial);
//					}
//					
//					if (importLogItem.getImportLog() == null || importLogItem.getImportLog().getId() == null) {
//						throw new BusinessException("El serial ingresado no pertenece a ningún registro de Importacion.");
//					}
//					else if ( !importLogItemIni.getImportLog().getId().equals(importLogItem.getImportLog().getId()) ) {
//						throw new BusinessException("El serial ingresado no pertenece al mismo registro de importacion de la primera fila del archivo.");
//					}
//					
//				}
//
//				fileRecordDTO.clearParams();
//				fileRecordDTO.addParam(PARAM_SERIALIZED, serialized);//guardar el objeto para evitar una consulta en el momento de procesar el archivo
//				fileRecordDTO.addParam(PARAM_WH_TRANSIT, whTransit.getId());
//				fileRecordDTO.addParam(PARAM_WH_QA, whQuality != null ? whQuality.getId() : -1L);
//				fileRecordDTO.addParam(PARAM_WH_DISPOSABLE, whDisposs != null ? whDisposs.getId() : -1L);
//				fileRecordDTO.addParam(PARAM_IMPORT_LOG, importLogItemIni.getImportLog());
//				fileRecordDTO.addParam(PARAM_WH_STOCK_BULK, whDispossStockBulk != null ? whDispossStockBulk.getId() : -1L );
//				
//				
//			} catch (Exception e) {
//				errors.add( buildFileDetailProcess(fileRecordDTO.getRow(), e.getMessage()) );
//				iterator.remove();//eliminar el registro para que no sea procesado posteriormente
//			}
//			
//		}
		
		return errors;
		
	}
	
	private boolean hasRecordsForAvailabilityWh(List<FileRecordDTO> fileData) {
		if(fileData != null) {
			for (FileRecordDTO fd : fileData) {
				try {
					String isWHQA = fd.getRowData()[0];
					isWHQA = (isWHQA != null ? isWHQA.trim().toUpperCase() : "");
					if ( isWHQA.equals(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity()) ) {
						return true;
					}
				} catch (PropertiesException pe) {
					log.error("no se pudo leer la configuración para CodesBusinessEntityEnum.BOOLEAN_FALSE en el método FileProcessorConfirmSerialItemsFromImportLog.areAllRecordsForAvailabilityWh", pe);
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		return false;
	}
	
	private boolean hasRecordsForQAWh(List<FileRecordDTO> fileData) {
		if(fileData != null) {
			for (FileRecordDTO fd : fileData) {
				try {
					String isWHQA = fd.getRowData()[0];
					isWHQA = (isWHQA != null ? isWHQA.trim().toUpperCase() : "");
					if ( isWHQA.equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) ) {
						return true;
					}
				} catch (PropertiesException pe) {
					log.error("no se pudo leer la configuración para CodesBusinessEntityEnum.BOOLEAN_TRUE en el método FileProcessorConfirmSerialItemsFromImportLog.areAllRecordsForAvailabilityWh", pe);
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		return false;
	}
	
	private void validateLogisticOperator(ImportLogItem importLogItemIni) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException{
		
		//1. Consulta el parámetro del registro de importación
		UploadFileParam paramWhAvailable = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(), CodesBusinessEntityEnum.FILE_PARAM_WHAVAILABLE.getCodeEntity());
		
		if(paramWhAvailable == null || StringUtils.isBlank(paramWhAvailable.getParamValue())) {
			throw new BusinessException("No se seleccionó un operador logístico al cargar el archivo. Este es requerido ya que el archivo indica que los elementos van para bodega de disponibilidad");				
		}
		
		if(!NumberUtils.isNumber(paramWhAvailable.getParamValue())) {
			throw new BusinessException("El parámetro generado al cargar el archivo con nombre {whAvailable} debe ser un id de bodega válido.");
		}
		
		Long whDispossId = Long.parseLong(paramWhAvailable.getParamValue());
		Warehouse whDisposs = daoWarehouse.getWarehouseByID(whDispossId);
		
		if(whDisposs == null) {
			throw new BusinessException("No se encontró la bodega tipo disponible para el operador logístico con id = " + whDispossId);
		}
		
		Dealer opLogSelected = whDisposs.getDealerId();
		
		Warehouse whTransit = businessImportLogItem.getLogisticOperatorWarehouse(importLogItemIni.getImportLog().getDealer().getId(), CodesBusinessEntityEnum.WAREHOUSE_TYPE_TRANSITO.getCodeEntity());
		Dealer opLogRegImp = whTransit.getDealerId();
		
		if ( !opLogSelected.getId().equals(opLogRegImp.getId()) ){
			throw new BusinessException("El operador logistico seleccionado, no coincide con el operador logistico asociado al registro de importacion.");
		}
		
	}
	
	private void validateImportLogItemStatus(ImportLogItem importLogItem) throws PropertiesException, BusinessException{
		if ( importLogItem == null || importLogItem.getItemStatus() == null 
			 || !importLogItem.getItemStatus().getStatusCode().equals(CodesBusinessEntityEnum.ITEM_STATUS_SENDED.getCodeEntity())) { //Enviado 4
			throw new BusinessException("El estado del registro del elemento no es: Enviado.");
		} 
	}
	
	private void validateImportLogStatus(ImportLog importLog) throws PropertiesException, BusinessException{
		if (importLog == null || importLog.getImportLogStatus() == null || importLog.getImportLogStatus().getStatusCode() == null 
			|| !(importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_SEND.getCodeEntity())
			|| importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_PARTIALLY_CONFIRMED.getCodeEntity()) 
			|| importLog.getImportLogStatus().getStatusCode().equals(CodesBusinessEntityEnum.IMPORT_LOG_STATUS_IN_INCONSISTENCY.getCodeEntity())) ) {
			throw new BusinessException("El estado del registro de importación no es: Enviado, confirmado parcial o inconsistente.");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.IFileProcessor#validateFile()
	 */
	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
	
}
