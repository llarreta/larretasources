package co.com.directv.sdii.model.dto;

import java.util.Date;

public class ReportsComplyAndScheduleFilterDTO {
	
	private Date beginDate;
	private Date endDate;
	private boolean isFileResponse;
	private String fileName;
	private String countryCode;
	private boolean isCsvFile;
	
	

	public ReportsComplyAndScheduleFilterDTO(Date beginDate, Date endDate,
			boolean isFileResponse, String fileName, String countryCode,
			boolean isCsvFile) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.isFileResponse = isFileResponse;
		this.fileName = fileName;
		this.countryCode = countryCode;
		this.isCsvFile = isCsvFile;
	}
	public boolean isCsvFile() {
		return isCsvFile;
	}
	public void setCsvFile(boolean isCsvFile) {
		this.isCsvFile = isCsvFile;
	}
	public ReportsComplyAndScheduleFilterDTO(Date beginDate, Date endDate,
			boolean isFileResponse, String fileName,String countryCode) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.isFileResponse = isFileResponse;
		this.fileName = fileName;
		this.countryCode = countryCode;
	}
	public boolean isFileResponse() {
		return isFileResponse;
	}
	public void setFileResponse(boolean isFileResponse) {
		this.isFileResponse = isFileResponse;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public ReportsComplyAndScheduleFilterDTO() {
		super();
	}
	public ReportsComplyAndScheduleFilterDTO(Date beginDate, Date endDate) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
