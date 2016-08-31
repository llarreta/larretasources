package co.com.directv.sdii.model.pojo;

import java.util.Date;


/**
 * UploadFile entity. @author MyEclipse Persistence Tools
 */

public class UploadFile implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3222460643584178118L;
	// Fields

	private Long id;
	private FileStatus fileStatus;
	private FileType fileType;
	private Country country ;
	private User user ;
	private String name;
	private Date loadDate;
	private Date processDate;

	// Constructors

	/** default constructor */
	public UploadFile() {
	}

	public UploadFile(Long id) {
		this.id = id;
	}
	
	/** minimal constructor */
	public UploadFile(Long id, FileStatus fileStatus, FileType fileType,
			Country country, User user, String name) {
		this.id = id;
		this.fileStatus = fileStatus;
		this.fileType = fileType;
		this.country = country ;
		this.user = user;
		this.name = name;
	}

	/** full constructor */
	public UploadFile(Long id, FileStatus fileStatus, FileType fileType,
			Country country, User user, String name,
			Date loadDate, Date processDate ) {
		this.id = id;
		this.fileStatus = fileStatus;
		this.fileType = fileType;
		this.country = country;
		this.user = user;
		this.name = name;
		this.loadDate = loadDate;
		this.processDate = processDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FileStatus getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(FileStatus fileStatus) {
		this.fileStatus = fileStatus;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
}