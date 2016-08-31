package co.com.directv.sdii.reports.dto;

public class ReferencePDFDTOInconsistency {

	private String modificationType;
	private String observations;
	private String registrationUser;
	private String registrationDate;
	private String responseInconsistency;
	private String responseUser;
	private String responseDate;
	private String inconsistencyStatus;
	
	public String getModificationType() {
		return modificationType;
	}
	public void setModificationType(String modificationType) {
		this.modificationType = modificationType;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	public String getRegistrationUser() {
		return registrationUser;
	}
	public void setRegistrationUser(String registrationUser) {
		this.registrationUser = registrationUser;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getResponseInconsistency() {
		return responseInconsistency;
	}
	public void setResponseInconsistency(String responseInconsistency) {
		this.responseInconsistency = responseInconsistency;
	}
	public String getResponseUser() {
		return responseUser;
	}
	public void setResponseUser(String responseUser) {
		this.responseUser = responseUser;
	}
	public String getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}
	public String getInconsistencyStatus() {
		return inconsistencyStatus;
	}
	public void setInconsistencyStatus(String inconsistencyStatus) {
		this.inconsistencyStatus = inconsistencyStatus;
	}
	
}
