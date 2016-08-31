package co.com.directv.sdii.model.dto;

import java.util.List;

public class ReportsPendingServicesByDateFilterDTO {
	private List<String> serviceCodes;
	private String categoryCode;
	private boolean isFileResponse;
	private String fileName;
	private String countryCode;
	private boolean isCsvFile;
	
	
	

	public ReportsPendingServicesByDateFilterDTO(List<String> serviceCodes,
			String categoryCode, boolean isFileResponse, String fileName,
			String countryCode, boolean isCsvFile) {
		super();
		this.serviceCodes = serviceCodes;
		this.categoryCode = categoryCode;
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
	public ReportsPendingServicesByDateFilterDTO(List<String> serviceCodes,
			String categoryCode, boolean isFileResponse, String fileName,String countryCode) {
		super();
		this.serviceCodes = serviceCodes;
		this.categoryCode = categoryCode;
		this.isFileResponse = isFileResponse;
		this.fileName = fileName;
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
	public ReportsPendingServicesByDateFilterDTO(List<String> serviceCodes,
			String categoryCode) {
		super();
		this.serviceCodes = serviceCodes;
		this.categoryCode = categoryCode;
	}
	public ReportsPendingServicesByDateFilterDTO() {
		super();
	}
	public List<String> getServiceCodes() {
		return serviceCodes;
	}
	public void setServiceCodes(List<String> serviceCodes) {
		this.serviceCodes = serviceCodes;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
}
