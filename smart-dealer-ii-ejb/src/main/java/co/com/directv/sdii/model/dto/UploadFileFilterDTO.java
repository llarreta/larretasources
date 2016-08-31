package co.com.directv.sdii.model.dto;

import java.util.Date;


/**
 * UploadFile entity. @author MyEclipse Persistence Tools
 */

public class UploadFileFilterDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3222460643584178118L;
	// Fields

	private Long id;
	private String fileStatusCode;
	private String fileTypeCode;
	private Long country ;
	private String loginNameUser ;
	private String name;
	private String location;
	private Date initialUploadDate;
	private Date finalUploadDate;

	// Constructors

	/** default constructor */
	public UploadFileFilterDTO() {
	}

	/** minimal constructor */
	public UploadFileFilterDTO(Long id, String fileTypeCode, String fileStatusCode,
			Long country, String loginNameUser, String name, String location) {
		this.id = id;
		this.fileTypeCode = fileTypeCode;
		this.fileStatusCode = fileStatusCode;
		this.country = country ;
		this.loginNameUser = loginNameUser;
		this.name = name;
		this.location = location;
	}

	/** full constructor */
	public UploadFileFilterDTO(Long id, String fileStatusCode, String fileTypeCode,
			Long country,  String loginNameUser, String name, String location,
			Date initialUploadDate, Date finalUploadDate) {
		this.id = id;
		this.fileStatusCode = fileStatusCode;
		this.fileTypeCode = fileTypeCode;
		this.country = country;
		this.loginNameUser = loginNameUser;
		this.name = name;
		this.location = location;
		this.initialUploadDate = initialUploadDate;
		this.finalUploadDate = finalUploadDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getFileStatusCode() {
		return fileStatusCode;
	}

	public void setFileStatusCode(String fileStatusCode) {
		this.fileStatusCode = fileStatusCode;
	}

	public String getFileTypeCode() {
		return fileTypeCode;
	}

	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}

	public Long getCountries() {
		return this.country;
	}

	public void setCountries(Long countries) {
		this.country = countries;
	}

	

	public String getLoginNameUser() {
		return loginNameUser;
	}

	public void setLoginNameUser(String loginNameUser) {
		this.loginNameUser = loginNameUser;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getInitialUploadDate() {
		return initialUploadDate;
	}

	public void setInitialUploadDate(Date initialUploadDate) {
		this.initialUploadDate = initialUploadDate;
	}

	public Date getFinalUploadDate() {
		return finalUploadDate;
	}

	public void setFinalUploadDate(Date finalUploadDate) {
		this.finalUploadDate = finalUploadDate;
	}

	
	
}