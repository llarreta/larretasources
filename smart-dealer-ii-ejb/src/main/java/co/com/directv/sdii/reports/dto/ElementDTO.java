package co.com.directv.sdii.reports.dto;

public class ElementDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -45312783950316667L;
	
	private String elementTypeName;
	private String elementBrandName;
	private String elementModelName;
	private String elementClassName;
	private String isSerialized;
	private String measureUnitName;
	private Double warrantyPeriod;
	private String supplierName;
	
	public String getElementTypeName() {
		return elementTypeName;
	}
	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}
	public String getElementBrandName() {
		return elementBrandName;
	}
	public void setElementBrandName(String elementBrandName) {
		this.elementBrandName = elementBrandName;
	}
	public String getElementModelName() {
		return elementModelName;
	}
	public void setElementModelName(String elementModelName) {
		this.elementModelName = elementModelName;
	}
	public String getElementClassName() {
		return elementClassName;
	}
	public void setElementClassName(String elementClassName) {
		this.elementClassName = elementClassName;
	}
	public String getIsSerialized() {
		return isSerialized;
	}
	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}
	public String getMeasureUnitName() {
		return measureUnitName;
	}
	public void setMeasureUnitName(String measureUnitName) {
		this.measureUnitName = measureUnitName;
	}
	public Double getWarrantyPeriod() {
		return warrantyPeriod;
	}
	public void setWarrantyPeriod(Double warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
