package co.com.directv.sdii.reports.dto;

public class ReferencePDFDTOModification {

	private String dateModified;
	private String userModified;
	private String modificationType;
	
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public String getUserModified() {
		return userModified;
	}
	public void setUserModified(String userModified) {
		this.userModified = userModified;
	}
	public String getModificationType() {
		return modificationType;
	}
	public void setModificationType(String modificationType) {
		this.modificationType = modificationType;
	}
	
}
