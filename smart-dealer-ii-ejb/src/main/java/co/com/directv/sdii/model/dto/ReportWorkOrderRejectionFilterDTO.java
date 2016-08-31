package co.com.directv.sdii.model.dto;


/**
 * Contiene los filtros del reporte de work order rechazadas 
 * 
 * Fecha de Creación: 30/10/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ReportWorkOrderRejectionFilterDTO {
	
	private boolean isFileResponse;
	private String fileName;
	private String countryCode;
	private boolean isCsvFile;
	
	public ReportWorkOrderRejectionFilterDTO() {
		super();
	}
	
	public ReportWorkOrderRejectionFilterDTO(boolean isFileResponse,
			String fileName, String countryCode, boolean isCsvFile) {
		super();
		this.isFileResponse = isFileResponse;
		this.fileName = fileName;
		this.countryCode = countryCode;
		this.isCsvFile = isCsvFile;
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
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public boolean isCsvFile() {
		return isCsvFile;
	}
	public void setCsvFile(boolean isCsvFile) {
		this.isCsvFile = isCsvFile;
	}
	
}
