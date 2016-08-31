package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.ExcelGeneratorLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.LoadFileBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.file.DTO.WarehousePhysicalInventorySmartdealerDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.UploadFileParam;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.UploadFileParamDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.reports.dto.WarehouseSerializedNotSerializedActualDTO;

/**
 * Clase encargada de la serilizacion masiva de elementos mediante
 * la carga de un archivo
 * 
 * Fecha de Creación: 17/11/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="FileProcessorCompareWarehousePhysicalInventorySmartdealer",mappedName="ejb/FileProcessorCompareWarehousePhysicalInventorySmartdealer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorCompareWarehousePhysicalInventorySmartdealer extends BasicFileProcessor implements FileProcessorCompareWarehousePhysicalInventorySmartdealerLocal {

	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorCompareWarehousePhysicalInventorySmartdealer.class);

	@EJB(name="UploadFileParamDAOLocal", beanInterface=UploadFileParamDAOLocal.class)
	private UploadFileParamDAOLocal uploadFileParamDAO;

	@EJB(name="ElementDAOLocal", beanInterface=ElementDAOLocal.class)
	private ElementDAOLocal elementDAO;

	@EJB(name="WarehouseElementDAOLocal", beanInterface=WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal warehouseElementDAO;

	@EJB(name="WarehouseDAOLocal", beanInterface=WarehouseDAOLocal.class)
	private WarehouseDAOLocal warehouseDAO;
	
	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
	private SerializedDAOLocal serializedDAO;
	
	@EJB(name="LoadFileBusinessBeanLocal", beanInterface=LoadFileBusinessBeanLocal.class)
	private LoadFileBusinessBeanLocal loadFileBusinessBean;
	
	@EJB
	private ExcelGeneratorLocal excelGenerator;
	
	private Warehouse warehouse = null;

	private static int TIPO_DE_ELEMENTO = 0;
	private static int CODIGO_DE_ELEMENTO = 1;
	private static int UNIDAD_DE_MEDIDA_DEL_ELEMENTO = 2;
	private static int CANTIDAD_ENCONTRADA_EN_LA_BODEGA = 3;
	private static int MODELO = 4;
	private static int SERIAL_DEL_ELEMENTO = 5;
	private static int RID = 6;
	private static int SERIAL_DEL_ELEMENTO_VINCULADO = 7;
	private static String EXISTE;
	private static String NOT_EXISTE;
	
	public FileProcessorCompareWarehousePhysicalInventorySmartdealer(){
		String[] columnTitles = new String[] {
				ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue(),
				ApplicationTextEnum.ELEMENT_CODE.getApplicationTextValue(),
				ApplicationTextEnum.ELEMENT_STATUS.getApplicationTextValue(),
				ApplicationTextEnum.UNIT_MEASURE_ITEM.getApplicationTextValue(),
				ApplicationTextEnum.QUANTITY_FOUND_WAREHOUSE.getApplicationTextValue(),
				ApplicationTextEnum.MODEL.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_ELEMENT.getApplicationTextValue(),
				ApplicationTextEnum.RID.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED_ELEMENT.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}

	private List<WarehousePhysicalInventorySmartdealerDTO> listNotSerialized = null;
	private List<WarehousePhysicalInventorySmartdealerDTO> listSerialized = null;
	private HashMap<Long, Long> elementsFile = null;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO)
			throws BusinessException {
		try{
			
			EXISTE=CodesBusinessEntityEnum.STRING_TRUE.getCodeEntity();
			NOT_EXISTE=CodesBusinessEntityEnum.STRING_FALSE.getCodeEntity();
			String FISICO=ApplicationTextEnum.ARCHIVE.getApplicationTextValue();
			String SMARTDEALER=ApplicationTextEnum.HSP.getApplicationTextValue();
			
			//Declaracion
			Double quantity=0D;
			Element element = null;
			WarehousePhysicalInventorySmartdealerDTO elementRow = null;
			Date maxDateInWarehouseElement = null;
			
			String elementType = "";
			String elementCode = "";
			
			String measureUnitName = "";
			String quantityFoundInWarehouse = "";
			
			String elementModel = "";
			String elementSerie = "";
			String rid = "";
			String elementLinkSerie = "";
			String elementLinkSerieSmartDealer = "";
			String checkElementLinkSerie = "";
			
			if(fileRecordDTO.getRowData().length<2){
				throw new BusinessException("Registro no valido. Se requiere Tipo de Elemento y codigo del elemento");
			}
			
			//Asignacion
			elementType = fileRecordDTO.getRowData()[TIPO_DE_ELEMENTO];
			elementCode = fileRecordDTO.getRowData()[CODIGO_DE_ELEMENTO];
			measureUnitName = fileRecordDTO.getRowData()[UNIDAD_DE_MEDIDA_DEL_ELEMENTO];
			quantityFoundInWarehouse = fileRecordDTO.getRowData()[CANTIDAD_ENCONTRADA_EN_LA_BODEGA];
			elementModel = fileRecordDTO.getRowData()[MODELO];
			elementSerie = fileRecordDTO.getRowData()[SERIAL_DEL_ELEMENTO];
			rid = fileRecordDTO.getRowData()[RID];
			elementLinkSerie = fileRecordDTO.getRowData()[SERIAL_DEL_ELEMENTO_VINCULADO];
			
			//Validaciones
			if (StringUtils.isBlank(elementType) 
			    || (!elementType.equals(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) 
			    	&& !elementType.equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()))
			    ) {
				throw new BusinessException("Debe digitar si es tipo de elemento serializado o no (S/N).");
			}else{
				elementType=elementType.toUpperCase();
			}
			
			if (StringUtils.isBlank(elementCode)) {
				throw new BusinessException("Debe digitar un codigo del elemento");
			}
			
			//si es no serializado
			if(elementType.equals(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity())){
				
				//Se busca el elemento not serializado
				element = elementDAO.getElementsByElementTypeCodeAndIsSerialized(elementCode,false,this.getUploadFile().getCountry().getId());
				//Si no esta se genera un error
				if(element==null){
					//Si el tipo de elemento no es no serializado
					element = elementDAO.getElementsByElementTypeCodeAndIsSerialized(elementCode,true,this.getUploadFile().getCountry().getId());
					if(element==null)
						throw new BusinessException("El elemento no existe.");
					else
						throw new BusinessException("El tipo de elemento no corresponde a un no serializado.");
				}else{
					//Si la cantidad es igual a vacio se inicializa en 0
					if (StringUtils.isBlank(quantityFoundInWarehouse)) {
						quantityFoundInWarehouse="0";
					}
					
					//Si el codigo de la unidad esta en blanco se coloca no aplica
					if (StringUtils.isBlank(measureUnitName)) {
						throw new BusinessException("El codigo de la unidad se encuentra vacia.");
					//sino se coloca en mayuscula
					}else{
						measureUnitName=measureUnitName.toUpperCase();
					}
					
					//Si la unidad de medida no es la misma
					if(!element.getElementType().getMeasureUnit().getUnitCode().toUpperCase().equals(measureUnitName)){
						throw new BusinessException("La Unidad de medida no corresponde al tipo de elemento.");
					}
					
					//Se consulta la cantidad actual en la bodega
					quantity = warehouseElementDAO.countWHElementByActualElementNotSerialized(warehouse.getId(),warehouse.getCountry().getId(),null,null,elementCode,CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity(),null,null);
					if(quantity==null)
						quantity=0D;
					//Se construye la fila para registrarlo en el archivo de diferencias
					elementRow = new WarehousePhysicalInventorySmartdealerDTO(FISICO,elementType,
					                                                          elementCode,
							                                                  element.getElementType().getTypeElementCode(),
							                                                  element.getElementType().getMeasureUnit().getUnitCode().toUpperCase(),
							                                                  quantityFoundInWarehouse,
							                                                  quantity.toString(),
							                                                  String.valueOf((Double.valueOf(quantityFoundInWarehouse) - quantity)));
					elementsFile.put(element.getId(), element.getId());
					
				}
				listNotSerialized.add(elementRow);
				
			//Si es serializado
			}else if(elementType.equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
				
				
				//Se busca el elemento serializado
				element = elementDAO.getElementBySerialAndTypeAndRidAndLinkedSerial(elementSerie,elementCode,null,null,this.getUploadFile().getCountry().getId());
				//Si no esta se genera un error
				if(element==null){
					//Si el tipo de elemento no es no serializado
					element = elementDAO.getElementsByElementTypeCodeAndIsSerialized(elementCode,false,this.getUploadFile().getCountry().getId());
					if(element==null)
						elementRow = new WarehousePhysicalInventorySmartdealerDTO(FISICO,
								  elementType,
								  elementCode,
								  elementModel,
		                          elementSerie,
		                          rid,
		                          elementLinkSerie,
		                          "",
		                          NOT_EXISTE,
		                          NOT_EXISTE);
					else
						throw new BusinessException("El tipo de elemento no corresponde a un serializado.");
				}else{
				
					//si el modelo del elemento esta vacio
					if (StringUtils.isBlank(elementModel)) {
						throw new BusinessException("Debe digitar un modelo.");
					}else{
						elementModel=elementModel.toUpperCase();
					}
					
					//si el serial del elemento esta vacio
					if (StringUtils.isBlank(elementSerie)) {
						throw new BusinessException("Debe digitar un serial del elemento.");
					}else{
						elementSerie=elementSerie.toUpperCase();
					}
					elementsFile.put(element.getId(), element.getId());
					
					//Si no esta vacio el rid se muestra en mayuscula
					if (!StringUtils.isBlank(rid)) {
						rid=rid.toUpperCase();
					}
					
					//Si no esta vacio el elementLinkSerie se muestra en mayuscula
					if (!StringUtils.isBlank(elementLinkSerie)) {
						elementLinkSerie=elementLinkSerie.toUpperCase();
					}
					
					Serialized serialized = serializedDAO.getSerializedBySerial(elementSerie,this.getUploadFile().getCountry().getId());
					
					//Si no existe el elemento serializado
					if(serialized == null){
						//Se construye la fila para registrarlo en el archivo de diferencias
						elementRow = new WarehousePhysicalInventorySmartdealerDTO(FISICO,elementType,
	                            												  elementCode,
														                          element.getElementType().getElementModel().getModelCode(),
														                          elementSerie,
														                          "",
														                          "",
														                          "",
														                          NOT_EXISTE,
														                          NOT_EXISTE);
					//Si existe elemento serializado
					}else{
						
						//Se consulta la fechaMaxima de warehouseElement
						maxDateInWarehouseElement = warehouseElementDAO.maxDateWHElementByActualElementNotSerialized(warehouse.getId(),warehouse.getCountry().getId(),null,null,elementCode,CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity(),null,null);
						
						elementLinkSerieSmartDealer="";
						//Si contiene un serializado
						if(serialized.getSerialized()!=null)
							elementLinkSerieSmartDealer=serialized.getSerialized().getSerialCode();
						
						//Si el codigo del elemento vinculado en el fisico es igual al de smartDealer 
						if(elementLinkSerieSmartDealer.toUpperCase().equals(elementLinkSerie))
							checkElementLinkSerie=EXISTE;
						else
							checkElementLinkSerie=NOT_EXISTE;
						
						//Se construye la fila para registrarlo en el archivo de diferencias
						elementRow = new WarehousePhysicalInventorySmartdealerDTO(FISICO,elementType,
	                                												  elementCode,
																                      element.getElementType().getElementModel().getModelCode(),
																                      serialized.getSerialCode(),
																                      serialized.getIrd(),
																                      elementLinkSerieSmartDealer,
																                      maxDateInWarehouseElement == null ? "":UtilsBusiness.dateToString(maxDateInWarehouseElement),
																                      maxDateInWarehouseElement == null ? NOT_EXISTE:EXISTE ,
																                      checkElementLinkSerie);
							
					}
				}
				listSerialized.add(elementRow);
			//Si no es serializado o no serializado.
			}else{
				throw new BusinessException("En la columna Elemento Serializado se debe Digitar \"S\" o \"N\" y se ingreso ["+elementType+"]");
			}
			
		}catch (Throwable e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}
	
	public boolean validateFile() throws BusinessException {
		return true;	
	}

	@Override
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(
			List<FileRecordDTO> fileData) {
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		try{

			//1. Consulta parametro bodega
			UploadFileParam param = uploadFileParamDAO.getUploadFileParamByUploadFileAndName(this.getUploadFile().getId(),CodesBusinessEntityEnum.FILE_PARAM_WAREHOUSE_ID.getCodeEntity());
			if (param==null || param.getParamValue().trim().length()==0){
				throw new BusinessException(" No se selecciono una bodega.");
			}

			//2. Consulta el warehouse encontrado en el parametro
			warehouse = warehouseDAO.getWarehouseByID(Long.valueOf(param.getParamValue()));
			
			listNotSerialized  = new ArrayList<WarehousePhysicalInventorySmartdealerDTO>();
			listSerialized  = new ArrayList<WarehousePhysicalInventorySmartdealerDTO>();
			elementsFile = new HashMap<Long, Long>();
			
			
		}catch (Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()));			
			fileData.clear();//no se debe procesar ningún registro
		}
		return errors;
	}


	@Override
	public List<FileDetailProcess>  doPostProcess(){
		
		List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		
		List<WarehousePhysicalInventorySmartdealerDTO> response = new ArrayList<WarehousePhysicalInventorySmartdealerDTO>();
		ByteArrayOutputStream baos = null;
		Serialized serialized=null;
		NotSerialized notSerialized=null;
		Double actualQuantity=null;
		WarehousePhysicalInventorySmartdealerDTO elementRow = null;
		
		String elementType = "";
		String elementCode = "";
		String elementLinkCode = "";
		String quantityFoundInWarehouse = "0";
		String elementSerie = "";
		String elementModelCode = "";
		String measureUnitName = "";
		String strTemp = "";
		Date maxDateInWarehouseElement=null;
		String rid = "";
		
		String FISICO=ApplicationTextEnum.ARCHIVE.getApplicationTextValue();
		String SMARTDEALER=ApplicationTextEnum.HSP.getApplicationTextValue();
					
		try {
			EXISTE=CodesBusinessEntityEnum.STRING_TRUE.getCodeEntity();
			NOT_EXISTE=CodesBusinessEntityEnum.STRING_FALSE.getCodeEntity();
			
			List<WarehouseSerializedNotSerializedActualDTO> warehouseSerializedNotSerializedActualDTOs = warehouseElementDAO.getWarehouseElementsByWHIdAndRecordLast(warehouse.getId());
			for (WarehouseSerializedNotSerializedActualDTO warehouseElementActual : warehouseSerializedNotSerializedActualDTOs) {
				serialized=warehouseElementActual.getSerialized();
				notSerialized=warehouseElementActual.getNotSerialized();
				actualQuantity=warehouseElementActual.getActualQuantity();
				maxDateInWarehouseElement=warehouseElementActual.getMovementDate();
				Long comp=null;
				//si es serializado
				if(serialized!=null){
					comp=elementsFile.get(serialized.getElement().getId());
					if(comp==null){
							elementType = ApplicationTextEnum.IS_ELEMENT_TYPE.getApplicationTextValue();
							strTemp="";
							if(serialized.getElement()!= null) 
								if(serialized.getElement().getElementType()!= null)
											strTemp=serialized.getElement().getElementType().getTypeElementCode();
							elementCode=strTemp;
							
							strTemp="";
							if(serialized.getElement()!= null) 
								if(serialized.getElement().getElementType()!= null)
									if(serialized.getElement().getElementType().getElementModel()!= null)
											strTemp=serialized.getElement().getElementType().getElementModel().getModelCode();
							elementModelCode=strTemp;
							
							elementSerie=serialized.getSerialCode();
							
							strTemp="";
							if(serialized.getSerialized()!= null) 
								 strTemp=serialized.getSerialized().getSerialCode();
							elementLinkCode=strTemp;
							
							rid=serialized.getIrd();
							
							elementRow = new WarehousePhysicalInventorySmartdealerDTO(SMARTDEALER,elementType,
									  elementCode,
									  elementModelCode,
			                        elementSerie,
			                        rid,
			                        elementLinkCode,
			                        maxDateInWarehouseElement == null ? "":UtilsBusiness.dateToString(maxDateInWarehouseElement),
			                        EXISTE,
			                        EXISTE);
							listSerialized.add(elementRow);
					}
				}else if(notSerialized!=null){
					comp=elementsFile.get(notSerialized.getElement().getId());
					if(comp==null){
							elementType = ApplicationTextEnum.NOT_ELEMENT_TYPE.getApplicationTextValue();
							
							strTemp="";
							if(notSerialized.getElement()!= null) 
								if(notSerialized.getElement().getElementType()!= null)
											strTemp=notSerialized.getElement().getElementType().getTypeElementCode();
							elementCode=strTemp;
							
							strTemp="";
							if(notSerialized.getElement()!= null)
								if(notSerialized.getElement().getElementType()!= null)
									if(notSerialized.getElement().getElementType().getMeasureUnit()!= null)
											strTemp=notSerialized.getElement().getElementType().getMeasureUnit().getUnitCode();
							measureUnitName=strTemp;
								
							elementRow = new WarehousePhysicalInventorySmartdealerDTO(SMARTDEALER,elementType,
			                        elementCode,
			                        elementCode,
			                        measureUnitName,
			                        quantityFoundInWarehouse,
			                        actualQuantity.toString(),
			                        String.valueOf((0 - actualQuantity)));
							listSerialized.add(elementRow);
					}
				}
				
			}
			
		} catch (DAOServiceException e1) {
			e1.printStackTrace();
		} catch (DAOSQLException e1) {
			e1.printStackTrace();
		} catch (PropertiesException e) {
			e.printStackTrace();
		}
		
		response.addAll(listNotSerialized);
		response.addAll(listSerialized);
		
		try{
			
			baos = excelGenerator.createExcelStreamWithJasper(response, null, null, CodesBusinessEntityEnum.WAREHOUSE_PHYSICAL_INVENTORY_SMARTDEALER_EXCEL_JASPER_FILE.getCodeEntity());
			String code = CodesBusinessEntityEnum.LOAD_FILE_TYPE_OUT.getCodeEntity();
			loadFileBusinessBean.createLoadFile(this.getUploadFile(), code, baos.toByteArray());
		}catch (Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()));
		}
		
		return errors;
		
	}
	
}
