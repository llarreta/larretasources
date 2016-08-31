package co.com.directv.sdii.ws.model.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * Data Transfer Object para encapsular la
 * respuesta del Web Services de Work Order expuesto
 * en  SmartDealerII para IVR  
 * 
 * Fecha de Creación: 2/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ResponseIVRWoDTO {
	
	private String jobCardId;
	private String jobCardStatus;
	private String jobCardType;
	private String customerCode;
	private String installerDealerCode;
	private Date completionDate;
	private Date dateRegistered;
	private String serialNr;
	private Date dateToInstall;
	private List<ResponseIVRWoServiceDTO> services;
	
	private String errorCode;
	private String errorDescription;
	private String errorReason;
	
	
	
	
	
	public String getInstallerDealerCode() {
		return installerDealerCode;
	}
	public void setInstallerDealerCode(String installerDealerCode) {
		this.installerDealerCode = installerDealerCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getJobCardId() {
		return jobCardId;
	}
	public void setJobCardId(String jobCardId) {
		this.jobCardId = jobCardId;
	}
	public String getJobCardStatus() {
		return jobCardStatus;
	}
	public void setJobCardStatus(String jobCardStatus) {
		this.jobCardStatus = jobCardStatus;
	}
	public String getJobCardType() {
		return jobCardType;
	}
	public void setJobCardType(String jobCardType) {
		this.jobCardType = jobCardType;
	}

	public Date getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	public Date getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}
	public String getSerialNr() {
		return serialNr;
	}
	public void setSerialNr(String serialNr) {
		this.serialNr = serialNr;
	}
	public Date getDateToInstall() {
		return dateToInstall;
	}
	public void setDateToInstall(Date dateToInstall) {
		this.dateToInstall = dateToInstall;
	}
	public List<ResponseIVRWoServiceDTO> getServices() {
		return services;
	}
	public void setServices(List<ResponseIVRWoServiceDTO> services) {
		this.services = services;
	}

	


}
