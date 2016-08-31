package co.com.directv.sdii.model.pojo;



/**
 * FileDetailProcess entity. @author MyEclipse Persistence Tools
 */

public class FileDetailProcess implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1836980316973486520L;
	// Fields

	private Long id;
	private UploadFile uploadFile;
	private Long fdprow;
	private String message;

	// Constructors

	/** default constructor */
	public FileDetailProcess() {
	}

	/** minimal constructor */
	public FileDetailProcess(Long id, UploadFile uploadFile,
			String message) {
		this.id = id;
		this.uploadFile = uploadFile;
		this.message = message;
	}

	/** full constructor */
	public FileDetailProcess(Long id, UploadFile uploadFile,
			Long row, String message) {
		this.id = id;
		this.uploadFile = uploadFile;
		this.fdprow = row;
		this.message = message;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UploadFile getUploadFile() {
		return this.uploadFile;
	}

	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getFdprow() {
		return fdprow;
	}

	public void setFdprow(Long fdprow) {
		this.fdprow = fdprow;
	}
}