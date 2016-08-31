package co.com.directv.sdii.ws.model.dto;


/**
 * 
 * Data Transfer Object para encapsular la
 * respuesta del Web Services de validacion
 * de deco o SC expuesto
 * en  SmartDealerII para IVR  
 * 
 * Fecha de Creación: 5/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ResponseIVRValidaDecoSCDTO {
	
	private String serialNR;
	private String decSCStatus;
	private String decSCModel;
	private String decSCdepotn;
	
	private String errorCode;
	private String errorDescription;
	private String errorReason;
	
	public String getSerialNR() {
		return serialNR;
	}
	public void setSerialNR(String serialNR) {
		this.serialNR = serialNR;
	}
	public String getDecSCStatus() {
		return decSCStatus;
	}
	public void setDecSCStatus(String decSCStatus) {
		this.decSCStatus = decSCStatus;
	}
	public String getDecSCModel() {
		return decSCModel;
	}
	public void setDecSCModel(String decSCModel) {
		this.decSCModel = decSCModel;
	}
	public String getDecSCdepotn() {
		return decSCdepotn;
	}
	public void setDecSCdepotn(String decSCdepotn) {
		this.decSCdepotn = decSCdepotn;
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
	
	

}
