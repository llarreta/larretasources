package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 14/09/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class FilterUploadFileParamByFileTypeParamNameAndCodeDTO implements Serializable {

	private static final long serialVersionUID = -6075621275834359839L;
	
	/**
	 * Codigo del Tipo de Archivo
	 */
	private String fileTypeCode;//fileType
	
	/**
	 * nombre del parametro
	 */
	private String paramName;//UploadFileParam
	
	/**
	 * valor del parametro
	 */
	private String paramValue;//UploadFileParam
	
	/**
	 * Constructor: vacio
	 * @author
	 */
	public FilterUploadFileParamByFileTypeParamNameAndCodeDTO() {
		super();
	}

	/**
	 * Constructor: permite instaciar seteando todos los atributos de la clase
	 * @param fileTypeCode
	 * @param paramName
	 * @param paramValue
	 * @author
	 */
	public FilterUploadFileParamByFileTypeParamNameAndCodeDTO(
			String fileTypeCode, String paramName, String paramValue) {
		super();
		this.fileTypeCode = fileTypeCode;
		this.paramName = paramName;
		this.paramValue = paramValue;
	}

	/**
	 * @return the fileTypeCode
	 */
	public String getFileTypeCode() {
		return fileTypeCode;
	}

	/**
	 * @param fileTypeCode the fileTypeCode to set
	 */
	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
}
