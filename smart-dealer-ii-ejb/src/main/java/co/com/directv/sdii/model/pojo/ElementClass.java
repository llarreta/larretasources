package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ElementClass entity. @author MyEclipse Persistence Tools
 */

public class ElementClass implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6910586586489657113L;
	private Long id;
	private String elementClassCode;
	private String elementClassName;
	private String elementClassDesc;
	private String isActive;

	// Constructors

	/** default constructor */
	public ElementClass() {
	}

	/** minimal constructor */
	public ElementClass(String elementClassCode, String elementClassName,
			String elementClassFamily, String isActive) {
		this.elementClassCode = elementClassCode;
		this.elementClassName = elementClassName;
		this.isActive = isActive;
	}

	public ElementClass(String elementClassCode, String elementClassName, String isActive) {
		this.elementClassCode = elementClassCode;
		this.elementClassName = elementClassName;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getElementClassCode() {
		return this.elementClassCode;
	}

	public void setElementClassCode(String elementClassCode) {
		this.elementClassCode = elementClassCode;
	}

	public String getElementClassName() {
		return this.elementClassName;
	}

	public void setElementClassName(String elementClassName) {
		this.elementClassName = elementClassName;
	}

	public String getElementClassDesc() {
		return this.elementClassDesc;
	}

	public void setElementClassDesc(String elementClassDesc) {
		this.elementClassDesc = elementClassDesc;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "ElementClass [elementClassCode=" + elementClassCode + ", id="
				+ id + "]";
	}
	
}