package co.com.directv.sdii.ws.model.dto;


/**
 * 
 * Data Transfer Object para encapsular la
 * respuesta del Web Services de Dealer expuesto
 * en  SmartDealerII para IVR  
 * 
 * Fecha de Creación: 5/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ResponseIVRDealerDTO {
	
	private Long depotNr;
	private String depotUserKey ;
	private String cuRefNumber;
	private String depotType;
	private String depotTypeName;
	private String activeYN;
	
	private String errorCode;
	private String errorDescription;
	private String errorReason;
	
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
	public Long getDepotNr() {
		return depotNr;
	}
	public void setDepotNr(Long depotNr) {
		this.depotNr = depotNr;
	}
	public String getDepotUserKey() {
		return depotUserKey;
	}
	public void setDepotUserKey(String depotUserKey) {
		this.depotUserKey = depotUserKey;
	}
	public String getCuRefNumber() {
		return cuRefNumber;
	}
	public void setCuRefNumber(String cuRefNumber) {
		this.cuRefNumber = cuRefNumber;
	}
	public String getDepotType() {
		return depotType;
	}
	public void setDepotType(String depotType) {
		this.depotType = depotType;
	}
	public String getDepotTypeName() {
		return depotTypeName;
	}
	public void setDepotTypeName(String depotTypeName) {
		this.depotTypeName = depotTypeName;
	}
	public String getActiveYN() {
		return activeYN;
	}
	public void setActiveYN(String activeYN) {
		this.activeYN = activeYN;
	}

}
