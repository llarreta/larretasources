package co.com.directv.sdii.reports.dto;

public class ReferencePDFDTOnoSeralized {
	private String codeTypeElement;
	private String typeElement;
	private String measureUnit;
	private String amountRemitted;
	private String partialConfirmationDate;
	private String partialAmount;
	
	public String getPartialConfirmationDate() {
		return partialConfirmationDate;
	}
	public void setPartialConfirmationDate(String partialConfirmationDate) {
		this.partialConfirmationDate = partialConfirmationDate;
	}
	public String getPartialAmount() {
		return partialAmount;
	}
	public void setPartialAmount(String partialAmount) {
		this.partialAmount = partialAmount;
	}
	
	
	//agregar columnas
	
	public String getCodeTypeElement() {
		return codeTypeElement;
	}
	public void setCodeTypeElement(String codeTypeElement) {
		this.codeTypeElement = codeTypeElement;
	}
	public String getTypeElement() {
		return typeElement;
	}
	public void setTypeElement(String typeElement) {
		this.typeElement = typeElement;
	}	
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getAmountRemitted() {
		return amountRemitted;
	}
	public void setAmountRemitted(String amountRemitted) {
		this.amountRemitted = amountRemitted;
	}
	
}
