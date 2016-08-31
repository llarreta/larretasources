package co.com.directv.sdii.ejb.business.report.impl;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.ExcelGeneratorLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.report.ReportGeneratorInventoryModule01BusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.ImportLogItemBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementQueueBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.dto.collection.MovCmdQueueDTOResponse;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;
import co.com.directv.sdii.reports.dto.SerializedElementImportLogDTO;

/**
 * Implementación de negocio para generar reportes de inventarios
 * @author waguilera
 *
 */
@Stateless(name="ReportGeneratorInventoryModule01BusinessBeanLocal",mappedName="ejb/ReportGeneratorInventoryModule01BusinessBeanLocal")
@Local({ReportGeneratorInventoryModule01BusinessBeanLocal.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportGeneratorInventoryModule01BusinessBean extends BusinessBase implements ReportGeneratorInventoryModule01BusinessBeanLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(ReportGeneratorInventoryModule01BusinessBean.class);
	
	@EJB
	private ExcelGeneratorLocal excelGenerator;
	
	@EJB(name="MovementQueueBusinessBeanLocal", beanInterface=MovementQueueBusinessBeanLocal.class)
	private MovementQueueBusinessBeanLocal businessMovementQueue;
	
	@EJB(name="ImportLogBusinessBeanLocal", beanInterface=ImportLogBusinessBeanLocal.class)
	private ImportLogBusinessBeanLocal businessImportLog;
	
	@EJB(name="ImportLogItemBusinessBeanLocal", beanInterface=ImportLogItemBusinessBeanLocal.class)
	private ImportLogItemBusinessBeanLocal businessImportLogItem;

	@EJB(name="AdjustmentBusinessBeanLocal", beanInterface=AdjustmentBusinessBeanLocal.class)
	private AdjustmentBusinessBeanLocal adjustmentBusinessBeanLocal;
	
	@EJB(name="AdjustmentBusinessBeanLocal", beanInterface=AdjustmentBusinessBeanLocal.class)
	private AdjustmentBusinessBeanLocal adjustmentBusinessBean;
	
	@Override
	public FileResponseDTO getMovementQueueHspToIbsByFilter(
			MovCmdQueueFilterDTO filter) throws BusinessException {
		log.debug("== Termina getMovementQueueHspToIbsByFilter/ReportGeneratorInventoryModule01BusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			MovCmdQueueDTOResponse responseData = this.businessMovementQueue.getMovementQueueHspToIbsByFilter(filter, null, false);
			
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.INVENTORY_MOVEMENTS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if(responseData != null && responseData.getMovementlist() != null && !responseData.getMovementlist().isEmpty()){
				baos = excelGenerator.createExcelStreamWithJasper(responseData.getMovementlist(), null, null, CodesBusinessEntityEnum.REPORT_MOVEMENT_QUEUE_HSP_TO_IBS_BY_FILTER.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray(), "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getMovementQueueHspToIbsByFilter/ReportGeneratorInventoryModule01BusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getMovementQueueHspToIbsByFilter/ReportGeneratorInventoryModule01BusinessBean ==");
		}
	}


	@Override
	public FileResponseDTO getImportLogByCriteria(
			ModifyImportLogDTO modifyImportLogCriteria, Long userId)
			throws BusinessException {
		log.debug("== Termina getImportLogByCriteria/ReportGeneratorInventoryModule01BusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			ImportLogResponse responseData = this.businessImportLog.getImportLogByCriteria(modifyImportLogCriteria, userId, null);
			
			
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.IMPORT_RECORDS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if(responseData != null && responseData.getImportLogVO() != null && !responseData.getImportLogVO().isEmpty()){
				baos = excelGenerator.createExcelStreamWithJasper(responseData.getImportLogVO(), null, null, CodesBusinessEntityEnum.REPORT_IMPORT_LOG_BY_CRITERIA.getCodeEntity());
				//La siguiente linea es para realizar pruebas que dejan en el servidor
				//excelGenerator.createExcelFileWithJasper(responseData.getImportLogVO(), null, null, CodesBusinessEntityEnum.REPORT_IMPORT_LOG_BY_CRITERIA.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray(), "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getImportLogByCriteria/ReportGeneratorInventoryModule01BusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getImportLogByCriteria/ReportGeneratorInventoryModule01BusinessBean ==");
		}
	}

	@Override
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements)
			throws BusinessException {
		log.debug("== Termina getImportLogItemsByImportLogIdAndIsSerialized/ReportGeneratorInventoryModule01BusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			filterImportLogElements.setSerialized(true);
			List<SerializedElementImportLogDTO> responseData = this.businessImportLogItem.getImportLogItemsByImportLogIdAndIsSerializedForExcel(filterImportLogElements);
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.SERIALIZED_IMPORT_RECORDS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if(responseData != null && !responseData.isEmpty()){
				baos = excelGenerator.createExcelStreamWithJasper(responseData, null, null, CodesBusinessEntityEnum.REPORT_IMPORT_LOG_SERIALIZED_ELEMENTS.getCodeEntity());
				//La siguiente linea es para realizar pruebas que dejan en el servidor
				excelGenerator.createExcelFileWithJasper(responseData, null, null, CodesBusinessEntityEnum.REPORT_IMPORT_LOG_SERIALIZED_ELEMENTS.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray(), "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getImportLogItemsByImportLogIdAndIsSerialized/ReportGeneratorInventoryModule01BusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getImportLogItemsByImportLogIdAndIsSerialized/ReportGeneratorInventoryModule01BusinessBean ==");
		}
	}

	@Override
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsNotSerialized(
			ImportLogElementsFilterDTO filterImportLogElements)
			throws BusinessException {
		log.debug("== Termina getImportLogItemsByImportLogIdAndIsNotSerialized/ReportGeneratorInventoryModule01BusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			filterImportLogElements.setSerialized(false);
			ImportLogItemResponse responseData = this.businessImportLogItem.getImportLogItemsByImportLogIdAndIsSerialized(filterImportLogElements, null);
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.NOT_SERIALIZED_IMPORT_RECORDS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if(responseData != null && responseData.getImportLogItemsVO() != null && !responseData.getImportLogItemsVO().isEmpty()){
				baos = excelGenerator.createExcelStreamWithJasper(responseData.getImportLogItemsVO(), null, null, CodesBusinessEntityEnum.REPORT_IMPORT_LOG_NOT_SERIALIZED_ELEMENTS.getCodeEntity());
				//La siguiente linea es para realizar pruebas que dejan en el servidor
				//excelGenerator.createExcelFileWithJasper(responseData.getImportLogItemsVO(), null, null, CodesBusinessEntityEnum.REPORT_IMPORT_LOG_NOT_SERIALIZED_ELEMENTS.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray(), "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getImportLogItemsByImportLogIdAndIsNotSerialized/ReportGeneratorInventoryModule01BusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getImportLogItemsByImportLogIdAndIsNotSerialized/ReportGeneratorInventoryModule01BusinessBean ==");
		}
	}



	@Override
	public FileResponseDTO searchAdjustmentsBySearchParameters(AdjustmentRequestDTO filter) throws BusinessException {
		log.debug("== Inicia searchAdjustmentsBySearchParameters/ReportGeneratorInventoryModule01BusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			AdjustmentElementsResponse responseData = this.adjustmentBusinessBeanLocal.searchAdjustmentsBySearchParameters(filter, null);
			
			
			Date now = new Date();
			String fileName = ApplicationTextEnum.ADJUSTMENTS.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			ByteArrayOutputStream baos = null;
			if(responseData != null && responseData.getAdjustmentDTOElements() != null && !responseData.getAdjustmentDTOElements().isEmpty()){
				baos = excelGenerator.createExcelStreamWithJasper(responseData.getAdjustmentDTOElements(), null, null, CodesBusinessEntityEnum.ADJUSTMENT_REPORT_FOR_AUTHORIZATION.getCodeEntity());
				//excelGenerator.createExcelFileWithJasper(responseData.getAdjustmentDTOElements(), null, null, CodesBusinessEntityEnum.ADJUSTMENT_REPORT_FOR_AUTHORIZATION.getCodeEntity());
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray(), "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina searchAdjustmentsBySearchParameters/ReportGeneratorInventoryModule01BusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina searchAdjustmentsBySearchParameters/ReportGeneratorInventoryModule01BusinessBean ==");
		}
	}
	

	


	
	@Override
	public FileResponseDTO getAdjustmentElementsForAuthorization(
			AdjustmenElementsRequestDTO request) throws BusinessException {
		log.debug("== Termina getAdjustmentElementsForAuthorization/ReportGeneratorInventoryModule01BusinessBean ==");
		try{
			FileResponseDTO response = new FileResponseDTO();
			
			AdjustmentElementCollDTO responseData = adjustmentBusinessBean.getAdjustmentElementsForAuthorization(request, null);
			Date now = new Date();
			String fileName = ApplicationTextEnum.ELEMENTS_SET_AUTHORIZATION.getApplicationTextValue() + UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime() + ".xls";
			String jasperName;
			
			if(responseData.isSerialized()){
				jasperName = CodesBusinessEntityEnum.REPORT_ADJUSTMENT_ELEMENTS_SERIALIZED_AUTHORIZATION.getCodeEntity();
			}else{
				jasperName = CodesBusinessEntityEnum.REPORT_ADJUSTMENT_ELEMENTS_NOT_SERIALIZED_AUTHORIZATION.getCodeEntity();
			}
			
			ByteArrayOutputStream baos = null;
			if(responseData != null && responseData.getAdjustmentElementsResponse() != null && !responseData.getAdjustmentElementsResponse().isEmpty()){
				baos = excelGenerator.createExcelStreamWithJasper(responseData.getAdjustmentElementsResponse(), null, null, jasperName);
			}
			if(baos == null){
				throw new BusinessException(ErrorBusinessMessages.CORE_CR051.getCode(), ErrorBusinessMessages.CORE_CR051.getMessage());
			}
			DataSource ds = new  ByteArrayDataSource(baos.toByteArray(), "application/vnd.ms-excel");
			DataHandler dh = new DataHandler(ds);
			response.setDataHandler(dh);
			response.setFileName(fileName);
			return response;
		} catch (Throwable e) {
			log.debug("== Termina getAdjustmentElementsForAuthorization/ReportGeneratorInventoryModule01BusinessBean ==");
			throw this.manageException(e);
		} finally{
			log.debug("== Termina getAdjustmentElementsForAuthorization/ReportGeneratorInventoryModule01BusinessBean ==");
		}
	}	

}
