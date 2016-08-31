package co.com.directv.sdii.reports.dto;

public class PreloadReferenceDTO implements java.io.Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3957947858431108337L;
	private String referenceId;
	private String whSourceName;
	private String whTargetName;
	private String isPrepaidRef;
	
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}	
	
	public String getWhSourceName() {
		return whSourceName;
	}
	public void setWhSourceName(String whSourceName) {
		this.whSourceName = whSourceName;
	}
	public String getWhTargetName() {
		return whTargetName;
	}
	public void setWhTargetName(String whTargetName) {
		this.whTargetName = whTargetName;
	}
	public String getIsPrepaidRef() {
		return isPrepaidRef;
	}
	public void setIsPrepaidRef(String isPrepaidRef) {
		this.isPrepaidRef = isPrepaidRef;
	}
	
	
	
	
	

}
