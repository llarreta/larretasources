package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 14/09/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class UploadFileParamByFileTypeParamNameAndCodeDTO implements Serializable {

	private static final long serialVersionUID = -6075621275834359839L;
	
	private String uploadFileName;
	private Date uploadFileLoadDate;
	private String uploadFileUserName;
	
	/**
	 * Constructor: vacio
	 * @author
	 */
	public UploadFileParamByFileTypeParamNameAndCodeDTO() {
		super();
	}
	/**
	 * Constructor: permite instaciar seteando todos los atributos de la clase
	 * @param uploadFileName
	 * @param uploadFileLoadDate
	 * @param uploadFileUserName
	 * @author
	 */
	public UploadFileParamByFileTypeParamNameAndCodeDTO(String uploadFileName,
			Date uploadFileLoadDate, String uploadFileUserName) {
		super();
		this.uploadFileName = uploadFileName;
		this.uploadFileLoadDate = uploadFileLoadDate;
		this.uploadFileUserName = uploadFileUserName;
	}
	/**
	 * @return the uploadFileName
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}
	/**
	 * @param uploadFileName the uploadFileName to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	/**
	 * @return the uploadFileLoadDate
	 */
	public Date getUploadFileLoadDate() {
		return uploadFileLoadDate;
	}
	/**
	 * @param uploadFileLoadDate the uploadFileLoadDate to set
	 */
	public void setUploadFileLoadDate(Date uploadFileLoadDate) {
		this.uploadFileLoadDate = uploadFileLoadDate;
	}
	/**
	 * @return the uploadFileUserName
	 */
	public String getUploadFileUserName() {
		return uploadFileUserName;
	}
	/**
	 * @param uploadFileUserName the uploadFileUserName to set
	 */
	public void setUploadFileUserName(String uploadFileUserName) {
		this.uploadFileUserName = uploadFileUserName;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
