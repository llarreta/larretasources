package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package

/**
 * ZoneType entity. @author MyEclipse Persistence Tools
 */

public class ZoneType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8581475644962876414L;
	private Long id;
	private String zoneTypeName;
	private String zoneTypeCode;

	// Constructors

	/** default constructor */
	public ZoneType() {
	}

	/** minimal constructor */
	public ZoneType(String zoneTypeName, String zoneTypeCode) {
		this.zoneTypeName = zoneTypeName;
		this.zoneTypeCode = zoneTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZoneTypeName() {
		return this.zoneTypeName;
	}

	public void setZoneTypeName(String zoneTypeName) {
		this.zoneTypeName = zoneTypeName;
	}

	public String getZoneTypeCode() {
		return this.zoneTypeCode;
	}

	public void setZoneTypeCode(String zoneTypeCode) {
		this.zoneTypeCode = zoneTypeCode;
	}

	@Override
	public String toString() {
		return "ZoneType [id=" + id + ", zoneTypeCode=" + zoneTypeCode + "]";
	}
}