package co.com.directv.sdii.ejb.business.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.ejb.business.file.exception.FileProcessException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.vo.LoadFileVO;

/**
 * 
 * Procesador genérico para archivos con la primera fila con títulos
 * y el resto de filas con datos.
 * Contiene un bean fileProcessor con transaccionalidad por cada
 * registro.
 * 
 * Fecha de Creación: 22/07/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ClientBasicFileProcessor implements IFileProcessor {

	private final static Logger log = UtilsBusiness.getLog4J(ClientBasicFileProcessor.class);
	
	private IBasicFileProcessor fileProcessor;
	
	public void setRemoteFileProcessor(IBasicFileProcessor fileProcessor) {
		this.fileProcessor = fileProcessor;
	}
	
	private String[] columnTitles = null;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.file.FileProcessor#processFile()
	 * 
	 * Modificación
	 * release_correctiva_4.1.4_Validación para evitar la creación de remisiones con RN Existente 
	 * ialessan 
	 * febrero 2014
	 * 
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processFile(LoadFileVO loadFileVO) throws BusinessException {
		
		String loadFileName = new String();
		try{
			loadFileName = loadFileVO.getFile().getName();
		}catch (Exception e){
			loadFileName = "";
		}
		
		columnTitles = fileProcessor.getColumnTitles();
		List<FileRecordDTO> fileData = fileProcessor.getFileData();
		List<FileDetailProcess> globalErrorRecords = null;
		boolean hasProcessingErrors = false;
		if(fileData != null && fileData.size() > 1) {
			
			for (FileRecordDTO fileRecordDTO : fileData) {
				completeRecordWithEmptyValues(fileRecordDTO);
			}
			
			validateAndDropHeader(fileData);
			
			globalErrorRecords = fileProcessor.doGlobalValidationsAndDropNotValidRecords(fileData);
			if(globalErrorRecords!=null&&globalErrorRecords.size()>0){
				hasProcessingErrors = true;
				saveGlobalErrorRecords(globalErrorRecords);
			}
			
			for (FileRecordDTO fileRecordDTO : fileData) {
				try {
					if(fileRecordDTO != null && fileRecordDTO.getRowData() != null) {
						completeRecordWithEmptyValues(fileRecordDTO);
						fileProcessor.processRecord(fileRecordDTO);
					}
				} catch (BusinessException be) {
					hasProcessingErrors = true;
					saveFileDetailProcess((long) fileRecordDTO.getRow(), be.getMessage());
				}
			}
			globalErrorRecords = fileProcessor.doPostProcess();
			if(globalErrorRecords!=null&&globalErrorRecords.size()>0){
				hasProcessingErrors = true;
				saveGlobalErrorRecords(globalErrorRecords);
			}
			
			
		}else{
			FileDetailProcess fileDetailProcess = new FileDetailProcess((long)0, loadFileVO.getUploadFile(), ApplicationTextEnum.REGISTERS_IMPORTED_FILE.getApplicationTextValue());
			globalErrorRecords = new ArrayList<FileDetailProcess>();
			globalErrorRecords.add(0, fileDetailProcess);
			saveGlobalErrorRecords(globalErrorRecords);
			throw new FileProcessException();
		}
		//lanzar exception para garantizar que el estado del archivo sea PROCESADO CON ERRORES
		if( hasProcessingErrors || (globalErrorRecords != null && !globalErrorRecords.isEmpty()) ) {
			throw new FileProcessException();
		}
	}

	private void saveGlobalErrorRecords(List<FileDetailProcess> globalErrorRecords) {
		if(globalErrorRecords != null) {
			for (FileDetailProcess fileDetailProcess : globalErrorRecords) {
				try {
					if(fileDetailProcess.getMessage()==null){
						fileDetailProcess.setMessage(ApplicationTextEnum.NO_ERROR_IDENTIFIED.getApplicationTextValue());
					}
					saveFileDetailProcess( fileDetailProcess );
				} catch (Exception e) {
					log.error("no fue posible guardar en la base de datos un error en procesamiento de archivo. Causa: " + e.getMessage());
					if(fileDetailProcess != null) {
						log.error("id archivo con error: " + fileDetailProcess.getId() != null ? fileDetailProcess.getId() : "null");
						log.error(" fila: " + fileDetailProcess.getFdprow() != null ? fileDetailProcess.getFdprow() : "null");
						log.error(" mensaje: " + fileDetailProcess.getMessage() != null ? fileDetailProcess.getMessage() : "null");
					}
				}
			}
		}
	}
	
	/**
	 * Metodo: completa un registro con valores nulos, en caso que el tamaño del registro sea
	 * menor al número de columnas del archivo, con lo que se evita acceso a índices no válidos
	 * en los procesadores de archivos. Si el tamaño del registro es mayor al número de columnas,
	 * se descartan los últimos valores
	 * @param fileRecordDTO
	 * @author wjimenez
	 */
	private void completeRecordWithEmptyValues(FileRecordDTO fileRecordDTO) {
		if(fileRecordDTO.getRowData().length == columnTitles.length) {
			return;
		}
		String[] values = Arrays.copyOf(fileRecordDTO.getRowData(), columnTitles.length);
		fileRecordDTO.setRowData(values);
	}
	
	private void validateAndDropHeader(List<FileRecordDTO> fileData) throws BusinessException {
		if(columnTitles == null || columnTitles.length == 0) {
			throw new BusinessException("no se han asignado los nombres de columnas mediante el llamado a getColumnTitles() en el FileProcessor específico");
		}
		FileRecordDTO titles = fileData.remove(0);//eliminar la fila de títulos para que sea mas fácil el procesamiento
		if(fileData.isEmpty()) {
			throw new BusinessException("El archivo cargado no tiene datos. El archivo debe diligenciarse desde la primera fila de la primera hoja del archivo excel con los títulos en la primera fila y datos de carga a partir de la segunda fila");
		}
	}

	@Override
	public List<FileRecordDTO> getFileData() {
		return fileProcessor.getFileData();
	}

	public void readFile(LoadFileVO loadFileVO) throws BusinessException {
		fileProcessor.readFile(loadFileVO);
	}
	
	@Override
	public void deleteFile(String pathFile) throws BusinessException {
		fileProcessor.deleteFile(pathFile);
	}

	@Override
	public void saveFileDetailProcess(long row, String message) {
		fileProcessor.saveFileDetailProcess(row, message);
	}

	@Override
	public void saveFileDetailProcess(FileDetailProcess fileDetailProcess) {
		fileProcessor.saveFileDetailProcess(fileDetailProcess);
	}
	
	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}

	@Override
	public void saveFileDetailProcessMassive(List<FileDetailProcess> fileDetailProcess) {
		//NOT IMPLEMENTED
	}
	
}
