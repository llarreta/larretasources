package co.com.directv.sdii.model.dto;

public class SearchElementTypesDTO {
	private Boolean isSerialized;
	private String elementModelCode;
	private Boolean elementTypeStatus;
	public Boolean getIsSerialized() {
		return isSerialized;
	}
	public void setIsSerialized(Boolean isSerialized) {
		this.isSerialized = isSerialized;
	}
	public String getElementModelCode() {
		return elementModelCode;
	}
	public void setElementModelCode(String elementModelCode) {
		this.elementModelCode = elementModelCode;
	}
	public Boolean getElementTypeStatus() {
		return elementTypeStatus;
	}
	public void setElementTypeStatus(Boolean elementTypeStatus) {
		this.elementTypeStatus = elementTypeStatus;
	}
	
}
