package co.com.directv.sdii.model.pojo;

import java.sql.Blob;


/**
 * LoadFile entity
 * 
 * Fecha de Creación: 25/01/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class LoadFile implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3222460643584178118L;
	// Fields

	private Long id;
	private UploadFile uploadFile;
	private LoadFileType loadFileType;
	private Blob objectFile;
	private String objectName;

	// Constructors

	/** default constructor */
	public LoadFile() {
	}

	/** full constructor */
	public LoadFile(Long id, UploadFile uploadFile, LoadFileType loadFileType,
			Blob objectFile, String objectName) {
		super();
		this.id = id;
		this.uploadFile = uploadFile;
		this.loadFileType = loadFileType;
		this.objectFile = objectFile;
		this.objectName = objectName;
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

	public LoadFileType getLoadFileType() {
		return loadFileType;
	}

	public void setLoadFileType(LoadFileType loadFileType) {
		this.loadFileType = loadFileType;
	}

	public Blob getObjectFile() {
		return objectFile;
	}

	public void setObjectFile(Blob objectFile) {
		this.objectFile = objectFile;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}