package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ElementModel entity. @author MyEclipse Persistence Tools
 */

public class ElementModel implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2469134315851575519L;
	private Long id;
	private ElementClass elementClass;
	private Technology technology;
	private String modelName;
	private String modelCode;
	private String modelDescription;
	private String isActive;
	private String isPrepaidModel;

	// Constructors

	/** default constructor */
	public ElementModel() {
	}

	/** minimal constructor */
	public ElementModel(ElementClass elementClass, String modelName,
			String modelCode, String isActive,String isPrepaidModel,Technology technology) {
		this.elementClass = elementClass;
		this.modelName = modelName;
		this.modelCode = modelCode;
		this.isActive = isActive;
		this.isPrepaidModel = isPrepaidModel;
		this.technology = technology;
	}

	/** full constructor */
	public ElementModel(ElementClass elementClass, String modelName,
			String modelCode, String modelDescription, String isActive,String isPrepaidModel,Technology technology) {
		this.elementClass = elementClass;
		this.modelName = modelName;
		this.modelCode = modelCode;
		this.modelDescription = modelDescription;
		this.isActive = isActive;
		this.isPrepaidModel = isPrepaidModel;
		this.technology = technology;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelCode() {
		return this.modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getModelDescription() {
		return this.modelDescription;
	}

	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	

	public ElementClass getElementClass() {
		return elementClass;
	}

	public void setElementClass(ElementClass elementClass) {
		this.elementClass = elementClass;
	}

	public String getIsPrepaidModel() {
		return isPrepaidModel;
	}

	public void setIsPrepaidModel(String isPrepaidModel) {
		this.isPrepaidModel = isPrepaidModel;
	}
	
	

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	@Override
	public String toString() {
		return "ElementModel [id=" + id + ", modelCode=" + modelCode + "]";
	}
	
}