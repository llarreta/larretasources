package co.com.directv.sdii.assign.assignment.fileprocessor;

import java.util.List;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.ejb.business.file.FileProcessor;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.FileDetailProcess;
import co.com.directv.sdii.model.vo.LoadFileVO;

public abstract class BasicFileProcessor extends FileProcessor {
	
	/**
	 * títulos de las columnas de datos. Deben asignarse en cada subclase
	 */
	private String[] columnTitles = null;

	public String[] getColumnTitles() {
		return columnTitles;
	}
	
	public void setColumnTitles(String[] columnTitles) {
		this.columnTitles = columnTitles;
	}

	@Override
	public void processFile(LoadFileVO loadFileVO) throws BusinessException {
		throw new BusinessException("Operación no soportada: esta es una implementación de IBasicFileProcessor, y solo se debe usar el método processRecord");
	}	
	
	/**
	 * Metodo: obtiene el valor numérico de la cadena value. Si es null o vacio retorna cero
	 * @param value
	 * @param title título de la columna a usar en el mensaje de error en caso de que value no sea
	 * un valor numérico
	 * @return
	 * @throws BusinessException 
	 * @author wjimenez
	 */
	public Long getLongValue(String value, String title) throws BusinessException {
		try {
			Long longValue = Double.valueOf(value).longValue();
			return longValue;
		} catch (Exception e) {
			if(value == null || (value != null && value.trim().equals(""))) {
				return 0L;
			}
			throw new BusinessException("la columna {" + title + "} no tiene un valor numérico: " + value);
		}
	}

	/**
	 * Metodo: obtiene el valor de estado S o N ignorando el case 
	 * @param value
	 * @param title
	 * @return S o N. Nulo si el valor de entrada es nulo
	 * @throws BusinessException cuando no se pasa un valor válido como parámetro
	 * @author wjimenez
	 * @throws PropertiesException 
	 */
	public String getValidatedStatusValue(String value, String title) throws BusinessException {
		
		if(value == null || (value != null && value.trim().equals(""))) {
			return null;
		}
		value = value.trim().toUpperCase();
		try {
			if( value.equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) || value.equals(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity()) ) {
				return value;
			} else {
				throw new BusinessException("la columna {" + title + "} no tiene un valor de estado válido: " + value);	
			}
		} catch (PropertiesException e) {
			throw new BusinessException("no se pudo leer la propiedad BOOLEAN_TRUE o la BOOLEAN_FALSE. " + e.getMessage(), e);
		}
		
	}

	public String getStringFromNumericValue(String value, String title) throws BusinessException {
		try {
			return value.split("\\.")[0];
		} catch (Exception e) {
			if(value == null) {
				return "";
			}
			throw new BusinessException("la columna {" + title + "} no tiene un valor de válido: " + value);	
		}
	}
	
	public boolean isValidSdiiBinary(String strToValidate) throws PropertiesException{
		boolean isValidData = true;
		strToValidate = (strToValidate != null ? strToValidate.trim().toUpperCase() : "");
		isValidData = strToValidate.equals(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()) || strToValidate.equals(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
		return isValidData;
	}
	
	public FileDetailProcess buildFileDetailProcess(int rowNum, String message) {
		FileDetailProcess fileDetailProcess = new FileDetailProcess((long)rowNum, super.getUploadFile(),(long)rowNum,  message);
		return fileDetailProcess;
	}
	
	public boolean isNumber01(String strToValidate){
		return strToValidate != null && (strToValidate.equals("0") || strToValidate.equals("1"));
	}
	
	public String toUpper(String text) {
		return text == null ? "" : text.trim().toUpperCase();
	}
	
	/**
	 * Metodo: Método que debe ser sobreescrito por los procesadores que requieran validaciones
	 * que no son posibles de realizarse sobre cada registro independientemente
	 * @param fileData
	 * @return
	 * @return listado de los errores de validación que se presentaron
	 */
	public List<FileDetailProcess> doGlobalValidationsAndDropNotValidRecords(
			List<FileRecordDTO> fileData) {		
		return null;
	}
	
	public  List<FileDetailProcess> doPostProcess(){
		return null;
	}
	
}
