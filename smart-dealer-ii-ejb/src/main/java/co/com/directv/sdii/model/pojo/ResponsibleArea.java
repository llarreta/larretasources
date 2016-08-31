package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ResponsibleArea entity. @author MyEclipse Persistence Tools
 */

public class ResponsibleArea implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3108872878309959193L;
	private Long id;
	private String areaName;
	private String areaCode;

	// Constructors

	/** default constructor */
	public ResponsibleArea() {
	}

	/** minimal constructor */
	public ResponsibleArea(String areaName, String areaCode) {
		this.areaName = areaName;
		this.areaCode = areaCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Override
	public String toString() {
		return "ResponsibleArea [areaCode=" + areaCode + ", id=" + id + "]";
	}
}