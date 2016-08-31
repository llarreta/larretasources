package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;


/**
 * ElementType entity. @author MyEclipse Persistence Tools
 */

public class ElementType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6433959554233579353L;
	private Long id;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private MeasureUnit measureUnit;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ElementModel elementModel;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ElementBrand elementBrand;
	@BusinessRequired
	private String typeElementCode;
	@BusinessRequired
	private String typeElementName;
	@BusinessRequired
	private String typeElementDescription;
	@BusinessRequired
	private String isActive;
	@BusinessRequired
	private String isSerialized;
	private String typeRegEx;

	// Constructors

	/** default constructor */
	public ElementType() {
	}
	
	public ElementType(String code, Long id, String name) {
		super();
		setTypeElementName(name);
		setTypeElementCode(code);
		setId(id);
	}

	/** minimal constructor */
	public ElementType(String typeElementCode, String typeElementName,
			String isActive,ElementModel elementModel,ElementBrand elementBrand) {
		this.typeElementCode = typeElementCode;
		this.typeElementName = typeElementName;
		this.isActive = isActive;
		this.elementModel = elementModel;
		this.elementBrand=elementBrand;
	}

	/** full constructor */
	public ElementType(MeasureUnit measureUnit, String typeElementCode, String typeElementName,
			String typeElementDescription, String isActive, String isSerialized,ElementModel elementModel,ElementBrand elementBrand) {
		this.measureUnit = measureUnit;
		this.typeElementCode = typeElementCode;
		this.typeElementName = typeElementName;
		this.typeElementDescription = typeElementDescription;
		this.isActive = isActive;
		this.isSerialized = isSerialized;
		this.elementModel = elementModel;
		this.elementBrand=elementBrand;
	}

	public ElementType(String isSerialized){
		this.isSerialized = isSerialized;
	}
	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeElementCode() {
		return this.typeElementCode;
	}

	public void setTypeElementCode(String typeElementCode) {
		this.typeElementCode = typeElementCode;
	}

	public String getTypeElementName() {
		return this.typeElementName;
	}

	public void setTypeElementName(String typeElementName) {
		this.typeElementName = typeElementName;
	}

	public String getTypeElementDescription() {
		return this.typeElementDescription;
	}

	public void setTypeElementDescription(String typeElementDescription) {
		this.typeElementDescription = typeElementDescription;
	}

	public String getIsActive() {
		return this.isActive;
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

	public MeasureUnit getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}
	
	public ElementModel getElementModel() {
		return elementModel;
	}

	public void setElementModel(ElementModel elementModel) {
		this.elementModel = elementModel;
	}

	
	
	public ElementBrand getElementBrand() {
		return elementBrand;
	}

	public void setElementBrand(ElementBrand elementBrand) {
		this.elementBrand = elementBrand;
	}
	
	public String getTypeRegEx() {
		return typeRegEx;
	}

	public void setTypeRegEx(String typeRegEx) {
		this.typeRegEx = typeRegEx;
	}

	@Override
	public String toString() {
		return "ElementType [id=" + id + ", typeElementCode=" + typeElementCode
				+ "]";
	}
	
	
	
}