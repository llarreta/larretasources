package co.com.directv.sdii.model.pojo;

/**
 * Entidad que representa la tabla Upload_File_Param
 * @author gfandino
 *
 */
public class UploadFileParam implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -110510918190559174L;
	
	private Long id;
	private UploadFile uploadFile;
	private String paramName;
	private String paramValue;
	
	
	public UploadFileParam() {
	}
	
	
	public UploadFileParam(Long id, UploadFile uploadFile, String paramName,
			String paramValue) {
		super();
		this.id = id;
		this.uploadFile = uploadFile;
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UploadFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	
	
	

}

