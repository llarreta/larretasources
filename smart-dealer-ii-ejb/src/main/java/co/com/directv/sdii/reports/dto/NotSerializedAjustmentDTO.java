package co.com.directv.sdii.reports.dto;

import java.util.Date;

public class NotSerializedAjustmentDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9186133391948250300L;
	
	private String elementTypeCode;
	private String elementMeasureUnitName;
	private String elementStatusName;
	private String elementLote;
	private Double elementInitialQuantity;
	private Double elementActualQuantity;
	private Date entryDate;
	
	public String getElementTypeCode() {
		return elementTypeCode;
	}
	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}
	public String getElementMeasureUnitName() {
		return elementMeasureUnitName;
	}
	public void setElementMeasureUnitName(String elementMeasureUnitName) {
		this.elementMeasureUnitName = elementMeasureUnitName;
	}
	public String getElementStatusName() {
		return elementStatusName;
	}
	public void setElementStatusName(String elementStatusName) {
		this.elementStatusName = elementStatusName;
	}
	public String getElementLote() {
		return elementLote;
	}
	public void setElementLote(String elementLote) {
		this.elementLote = elementLote;
	}
	public Double getElementInitialQuantity() {
		return elementInitialQuantity;
	}
	public void setElementInitialQuantity(Double elementInitialQuantity) {
		this.elementInitialQuantity = elementInitialQuantity;
	}
	public Double getElementActualQuantity() {
		return elementActualQuantity;
	}
	public void setElementActualQuantity(Double elementActualQuantity) {
		this.elementActualQuantity = elementActualQuantity;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

}
