package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * DeviceBrand entity. @author MyEclipse Persistence Tools
 */

public class DeviceBrand implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1710442872398525012L;
	private Long id;
	private String devBrandCode;
	private String devBrandName;

	// Constructors

	/** default constructor */
	public DeviceBrand() {
	}

	/** full constructor */
	public DeviceBrand(Long id, String devBrandCode, String devBrandName) {
		this.id = id;
		this.devBrandCode = devBrandCode;
		this.devBrandName = devBrandName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevBrandCode() {
		return this.devBrandCode;
	}

	public void setDevBrandCode(String devBrandCode) {
		this.devBrandCode = devBrandCode;
	}

	public String getDevBrandName() {
		return this.devBrandName;
	}

	public void setDevBrandName(String devBrandName) {
		this.devBrandName = devBrandName;
	}

	@Override
	public String toString() {
		return "DeviceBrand [devBrandCode=" + devBrandCode + ", id=" + id + "]";
	}
	
}