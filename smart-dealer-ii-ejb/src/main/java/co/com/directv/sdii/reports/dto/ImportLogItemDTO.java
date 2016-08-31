package co.com.directv.sdii.reports.dto;

public class ImportLogItemDTO implements java.io.Serializable{

	/**
	 * 
	 */
	//Modificado para Requerimiento: CC057
	private static final long serialVersionUID = -4288543497844193963L;
	
	private String elementTypeName;
	private Double amountExpected;
	private Double confirmedQuantity;
	private String serial;
	private String serialLinked;
	private String elementStatus;
	private String measureUnit;
	private String elementTypeCode;
	
		
	
	
	public String getElementTypeCode() {
		return elementTypeCode;
	}
	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getSerialLinked() {
		return serialLinked;
	}
	public void setSerialLinked(String serialLinked) {
		this.serialLinked = serialLinked;
	}
	
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getElementTypeName() {
		return elementTypeName;
	}
	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}
	public Double getAmountExpected() {
		return amountExpected;
	}
	public void setAmountExpected(Double amountExpected) {
		this.amountExpected = amountExpected;
	}
	public Double getConfirmedQuantity() {
		return confirmedQuantity;
	}
	public void setConfirmedQuantity(Double confirmedQuantity) {
		this.confirmedQuantity = confirmedQuantity;
	}
	public void setElementStatus(String elementStatus) {
		this.elementStatus = elementStatus;
	}
	public String getElementStatus() {
		return elementStatus;
	}
	
}
