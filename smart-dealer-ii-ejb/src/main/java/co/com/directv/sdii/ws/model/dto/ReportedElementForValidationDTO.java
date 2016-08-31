package co.com.directv.sdii.ws.model.dto;

public class ReportedElementForValidationDTO {

	private Long elementTypeId;
	private String serial;
	private Long referenceId;
	private Double quantity;
	private Double quantityInReference;
	private String name;
	private boolean isSerialized;
	private Long referenceElementId;
	
	private Double quantityAvailable;
	
	
	public Long getElementTypeId() {
		return elementTypeId;
	}
	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getQuantityInReference() {
		return quantityInReference;
	}
	public void setQuantityInReference(Double quantityInReference) {
		this.quantityInReference = quantityInReference;
	}
	public Long getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
	public boolean isSerialized() {
		return isSerialized;
	}
	public void setSerialized(boolean isSerialized) {
		this.isSerialized = isSerialized;
	}
	public Long getReferenceElementId() {
		return referenceElementId;
	}
	public void setReferenceElementId(Long referenceElementId) {
		this.referenceElementId = referenceElementId;
	}
	public Double getQuantityAvailable() {
		return quantityAvailable;
	}
	public void setQuantityAvailable(Double quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	
}
