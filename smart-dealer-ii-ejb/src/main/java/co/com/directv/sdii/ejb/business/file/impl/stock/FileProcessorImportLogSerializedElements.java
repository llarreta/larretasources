package co.com.directv.sdii.ejb.business.file.impl.stock;

import java.util.ArrayList;
import java.util.Iterator;
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
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;

@Stateless(name="FileProcessorImportLogSerializedElementsLocal",mappedName="ejb/FileProcessorImportLogSerializedElementsLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FileProcessorImportLogSerializedElements extends BasicFileProcessor implements FileProcessorImportLogSerializedElementsLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(FileProcessorImportLogSerializedElements.class);
	
	@EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal daoElementType;
	
	@EJB(name="ImportLogItemBusinessBeanLocal", beanInterface=ImportLogItemBusinessBeanLocal.class)
	private ImportLogItemBusinessBeanLocal businessImportLogItems;
	
	@EJB(name="ImportLogBusinessBeanLocal", beanInterface=ImportLogBusinessBeanLocal.class)
	private ImportLogBusinessBeanLocal businessImportLog;
	
	public FileProcessorImportLogSerializedElements() {
		String[] columnTitles = new String[] {
				ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL.getApplicationTextValue(),
				ApplicationTextEnum.RID.getApplicationTextValue(),
				ApplicationTextEnum.LINKED_ELEMENT_TYPE.getApplicationTextValue(),
				ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}
	
	private static int POS_ELEMENT_TYPE = 0;
	private static int POS_SERIAL = 1;
	private static int POS_RID = 2;
	private static int POS_LINKED_ELEMENT_TYPE = 3;	
	private static int POS_LINKED_SERIAL = 4;
	
	private static String PARAM_ITEM_STATUS = "itemS";
	private static String PARAM_ELEMENT_STATUS = "elemS";
	private static String PARAM_IMPORT_LOG = "imp_log";
	private static String PARAM_IMPORT_LOG_D = "wh_tran";
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO) throws BusinessException {
		try {
			
			String elementType = fileRecordDTO.getRowData()[POS_ELEMENT_TYPE];
			String serial = fileRecordDTO.getRowData()[POS_SERIAL];
			String rid = fileRecordDTO.getRowData()[POS_RID];
			String elementTypeLinked = fileRecordDTO.getRowData()[POS_LINKED_ELEMENT_TYPE];
			String serialCodeLinked = fileRecordDTO.getRowData()[POS_LINKED_SERIAL];
						
			ImportLog importLog = (ImportLog) fileRecordDTO.getParam(PARAM_IMPORT_LOG);
			ElementStatus elementStatus = (ElementStatus) fileRecordDTO.getParam(PARAM_ELEMENT_STATUS);
			ItemStatus itemStatus = (ItemStatus) fileRecordDTO.getParam(PARAM_ITEM_STATUS);
			boolean isLinked = false;
			if (StringUtils.isBlank(serialCodeLinked)){
				isLinked = false;
			}else{
				isLinked = true;
			}
			
			ImportLogItemVO importLogItemVO =  buildImportLogItem(importLog, elementStatus, elementType, serial, rid, isLinked, elementTypeLinked, serialCodeLinked);
			businessImportLogItems.createElementSerializedForNewImportLog(importLogItemVO, elementStatus, importLog, itemStatus, false, true, null, this.getUploadFile().getUser(), null);
			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(List<FileRecordDTO> fileData) {
		
        List<FileDetailProcess> errors = new ArrayList<FileDetailProcess>();
		
		ImportLog importLog = null;
		ElementStatus elementStatus = null;
		ItemStatus itemStatus  = null;
		FileRecordDTO fileRecordDTO  = new FileRecordDTO();
		
		try {
			
			List<Object> object = businessImportLog.doGlobalValidationsFileProcessorImportLogSerializedAllElements(this.getUploadFile());
			
			elementStatus = (ElementStatus) object.get(0);
			itemStatus = (ItemStatus) object.get(1);
			importLog = (ImportLog) object.get(2);
			
		} catch(Exception e) {
			log.error(e);
			errors.add( buildFileDetailProcess(0, e.getMessage()));			
			fileData.clear();//no se debe procesar ningún registro
		}
		
		for (Iterator<FileRecordDTO> iterator = fileData.iterator(); iterator.hasNext();) {
			
			try {
				
				fileRecordDTO = iterator.next();
				businessImportLog.doGlobalValidationsFileProcessorImportLogSerializedAllElementsPost(fileRecordDTO,
																				                      elementStatus,
																				                      itemStatus,
																				                      importLog);
			
			} catch (Exception e) {
				errors.add( buildFileDetailProcess(fileRecordDTO.getRow(), e.getMessage()) );
				iterator.remove();//eliminar el registro para que no sea procesado posteriormente
			}
			
		}
		
		return errors;
		
	}

	private ImportLogItemVO buildImportLogItem(ImportLog importLog, ElementStatus elementStatus, String elementTypeCode, String serialCode, String ird, boolean isLinked, String elementTypeCodeLinked, String serialCodeLinked) throws DAOServiceException, DAOSQLException{
		ImportLogItemVO importlogItemVO = new ImportLogItemVO();
		importlogItemVO.setImportLog(importLog);
		Element element = new Element();
		element.setIsSerialized(ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue());
		element.setElementStatus(elementStatus);
		element.setCountry(this.getUploadFile().getCountry());
		ElementType elementType = daoElementType.getElementTypeByCode(elementTypeCode);
		element.setElementType(elementType);
		Element elementLinked = new Element();
		if (isLinked){
			elementLinked.setIsSerialized(ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue());
			elementLinked.setElementStatus(elementStatus);
			elementLinked.setCountry(this.getUploadFile().getCountry());
			ElementType elementTypeLinked = daoElementType.getElementTypeByCode(elementTypeCodeLinked);
			elementLinked.setElementType(elementTypeLinked);
		}
		
		
		importlogItemVO.setElement(element);
		SerializedVO serializedVO = new SerializedVO();
		serializedVO.setElement(element);
		serializedVO.setSerialCode(serialCode);
		serializedVO.setIrd(ird);
		
		Serialized linkedSerialized = new Serialized();
		linkedSerialized.setElement(elementLinked);
		linkedSerialized.setSerialCode(serialCodeLinked);
		if (isLinked){
			serializedVO.setSerialized(linkedSerialized);
		}
		importlogItemVO.setSerializedVO(serializedVO);
		ElementVO elementVO = new ElementVO();
		elementVO.setElementStatus(elementStatus);
		elementVO.setElementType(elementType);
		elementVO.setIsSerialized(ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue());
		elementVO.setSerializedElement(serializedVO);
		importlogItemVO.setElementVO(elementVO);
		
		return importlogItemVO; 
		
	}
	
	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
	
}
