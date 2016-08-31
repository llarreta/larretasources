package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * MeasureUnit entity. @author MyEclipse Persistence Tools
 */

public class MeasureUnit implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8479791730498396559L;
	private Long id;
	private String unitCode;
	private String unitName;
	private String unitDescription;
	private String unitAlias;
	private String isActive;
	// Constructors

	/** default constructor */
	public MeasureUnit() {
	}

	/** minimal constructor */
	public MeasureUnit(String unitCode, String unitName, String isActive) {
		this.unitCode = unitCode;
		this.unitName = unitName;
		this.isActive = isActive;
	}

	/** full constructor */
	public MeasureUnit(String unitCode, String unitName,
			String unitDescription, String unitAlias, String isActive) {
		this.unitCode = unitCode;
		this.unitName = unitName;
		this.unitDescription = unitDescription;
		this.unitAlias = unitAlias;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitDescription() {
		return this.unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public String getUnitAlias() {
		return this.unitAlias;
	}

	public void setUnitAlias(String unitAlias) {
		this.unitAlias = unitAlias;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "MeasureUnit [id=" + id + ", unitCode=" + unitCode + "]";
	}
	
	
}