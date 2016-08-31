package co.com.directv.sdii.reports.dto;

public class ElementInImportLogDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4657293923956848958L;

	private Long elementCode;
	private Double amountExpected;
	private Double confirmedQuantity;
	private String measureUnitName;
	
	public Long getElementCode() {
		return elementCode;
	}
	public void setElementCode(Long elementCode) {
		this.elementCode = elementCode;
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
	public String getMeasureUnitName() {
		return measureUnitName;
	}
	public void setMeasureUnitName(String measureUnitName) {
		this.measureUnitName = measureUnitName;
	}
	
	
	
}
