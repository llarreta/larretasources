package co.com.directv.sdii.ws.model.dto;


/**
 * 
 * Data Transfer Object para encapsular la
 * respuesta del Web Services cuya respuesta es booleana expuestos
 * en  SmartDealerII para IVR  
 * 
 * Fecha de Creación: 2/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ResponseIVRBooleanDTO {
	
	private Boolean bolOk;
	
	private String errorCode;
	private String errorDescription;
	private String errorReason;
	
	public ResponseIVRBooleanDTO(){
		this.bolOk = false;
	}
	
	public Boolean isBolOk() {
		return bolOk;
	}
	public void setBolOk(Boolean bolOk) {
		this.bolOk = bolOk;
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
