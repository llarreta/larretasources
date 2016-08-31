package co.com.directv.sdii.model.pojo;

import java.util.Date;

public class FiltersFindUpdateFile {

	private String fileTypeCode;
	private String fileStatusCode; 
	private Date initialUploadDate; 
	private Date finalUploadDate;
	private String  loginNameUser;
	private String countryCode; 
	   /* Este es el codigo ISO2 */
	
	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}
	public String getFileTypeCode() {
		return fileTypeCode;
	}
	public void setFileStatusCode(String fileStatusCode) {
		this.fileStatusCode = fileStatusCode;
	}
	public String getFileStatusCode() {
		return fileStatusCode;
	}
	public void setInitialUploadDate(Date initialUploadDate) {
		this.initialUploadDate = initialUploadDate;
	}
	public Date getInitialUploadDate() {
		return initialUploadDate;
	}
	public void setFinalUploadDate(Date finalUploadDate) {
		this.finalUploadDate = finalUploadDate;
	}
	public Date getFinalUploadDate() {
		return finalUploadDate;
	}
	public void setLoginNameUser(String loginNameUser) {
		this.loginNameUser = loginNameUser;
	}
	public String getLoginNameUser() {
		return loginNameUser;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
}
