package co.com.directv.sdii.reports.dto;


public class ElementTypeDTO implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 9054376266261478984L;
	
	private String measureUnit;
	private String elementClass;
	private String elementModel;
	private String elementBrand;
	private String typeElementCode;
	private String typeElementName;
	private String typeElementDescription;
	private String isActive;
	private String isSerialized;
	private String typeRegEx;
	
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getElementClass() {
		return elementClass;
	}
	public void setElementClass(String elementClass) {
		this.elementClass = elementClass;
	}
	public String getElementModel() {
		return elementModel;
	}
	public void setElementModel(String elementModel) {
		this.elementModel = elementModel;
	}
	public String getElementBrand() {
		return elementBrand;
	}
	public void setElementBrand(String elementBrand) {
		this.elementBrand = elementBrand;
	}
	public String getTypeElementCode() {
		return typeElementCode;
	}
	public void setTypeElementCode(String typeElementCode) {
		this.typeElementCode = typeElementCode;
	}
	public String getTypeElementName() {
		return typeElementName;
	}
	public void setTypeElementName(String typeElementName) {
		this.typeElementName = typeElementName;
	}
	public String getTypeElementDescription() {
		return typeElementDescription;
	}
	public void setTypeElementDescription(String typeElementDescription) {
		this.typeElementDescription = typeElementDescription;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsSerialized() {
		return isSerialized;
	}
	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}
	public String getTypeRegEx() {
		return typeRegEx;
	}
	public void setTypeRegEx(String typeRegEx) {
		this.typeRegEx = typeRegEx;
	}
	
	

}
