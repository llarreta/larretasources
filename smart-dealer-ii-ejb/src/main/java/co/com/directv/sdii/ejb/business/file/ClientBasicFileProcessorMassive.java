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
import co.com.directv.sdii.ejb.business.file.data.LoadMassiveSerializedAdjusmentData;
import co.com.directv.sdii.ejb.business.file.exception.FileProcessException;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.pojo.UploadFile;
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
public class ClientBasicFileProcessorMassive implements IFileProcessor {

	private final static Logger log = UtilsBusiness.getLog4J(ClientBasicFileProcessorMassive.class);
	
	private IBasicFileProcessorMassive fileProcessor;
	
	public void setRemoteFileProcessor(IBasicFileProcessorMassive fileProcessor) {
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
		log.debug("Comienza el procesamiento del fichero.");		
		columnTitles = fileProcessor.getColumnTitles();
		List<FileRecordDTO> fileData = fileProcessor.getFileData();
		List<FileDetailProcess> globalErrorRecords = new ArrayList<FileDetailProcess>();
		boolean hasProcessingErrors = false;
		if(fileData != null && fileData.size() > 1) {
			
			for (FileRecordDTO fileRecordDTO : fileData) {
				completeRecordWithEmptyValues(fileRecordDTO);
			}
			
			validateAndDropHeader(fileData);
			
			globalErrorRecords = fileProcessor.doGlobalValidationsAndDropNotValidRecords(fileData);
			if(!globalErrorRecords.isEmpty()){
				hasProcessingErrors = true;
				saveGlobalErrorRecords(globalErrorRecords);
			}
			
			List<Object> listaElementos = fileProcessor.processRecordMassive(fileData);
			
			List<FileDetailProcess> individualErrorsRecord = this.generaErrores(listaElementos,loadFileVO.getUploadFile());
			if(individualErrorsRecord != null && !individualErrorsRecord.isEmpty()){
				hasProcessingErrors = true;
			}
			
			individualErrorsRecord.addAll(fileProcessor.doPostProcess());
			saveGlobalErrorRecords(individualErrorsRecord);		
			
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

	private List<FileDetailProcess> generaErrores(List<Object> listaElementos,UploadFile uploadFile) {
		List<FileDetailProcess> lSalida = new ArrayList<FileDetailProcess>();
		for(Object objectAux : listaElementos){
			if(objectAux instanceof LoadMassiveSerializedAdjusmentData){
				LoadMassiveSerializedAdjusmentData dataAux = (LoadMassiveSerializedAdjusmentData) objectAux;
				if(dataAux.isError()){
					FileDetailProcess aux = new FileDetailProcess();
					aux.setMessage(transformaError(dataAux.getsError()));
					aux.setUploadFile(uploadFile);
					aux.setFdprow(Long.valueOf(dataAux.getFileRecord().getRow()));
					lSalida.add(aux);
				}
			}else if(objectAux instanceof BasicProcessorAuxData){
				BasicProcessorAuxData dataAux = (BasicProcessorAuxData) objectAux;
				if(dataAux.isError()){
					FileDetailProcess aux = new FileDetailProcess();
					aux.setMessage(transformaError(dataAux.getBeException().getMessage()));
					aux.setUploadFile(uploadFile);
					aux.setFdprow(Long.valueOf(dataAux.getFileRecordDTO().getRow()));
					lSalida.add(aux);
				}
			}
		}
			
		return lSalida;
	}
	
	private String transformaError(String sAux){
		String sSalida = "";
		if(sAux != null){
			if(sAux.length() >= 3999){
				sSalida = sAux.substring(0, 3998); 
			}else{
				sSalida = sAux;
			}
		}
		return sSalida;
	}

	public void saveGlobalErrorRecords(List<FileDetailProcess> globalErrorRecords) {
		if(globalErrorRecords != null) {
			for (FileDetailProcess fileDetailProcess : globalErrorRecords) {
				if(fileDetailProcess.getMessage()==null){
					fileDetailProcess.setMessage(ApplicationTextEnum.NO_ERROR_IDENTIFIED.getApplicationTextValue());
				}
			}
			//Guardamos
			this.saveFileDetailProcessMassive(globalErrorRecords);
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
		fileData.remove(0);//eliminar la fila de títulos para que sea mas fácil el procesamiento
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
	public void saveFileDetailProcessMassive(List<FileDetailProcess> fileDetailProcess) {
		fileProcessor.saveFileDetailProcessMassive(fileDetailProcess);
	}
	
	@Override
	public void saveFileDetailProcess(FileDetailProcess fileDetailProcess) {
		fileProcessor.saveFileDetailProcess(fileDetailProcess);
	}
	
	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
}
