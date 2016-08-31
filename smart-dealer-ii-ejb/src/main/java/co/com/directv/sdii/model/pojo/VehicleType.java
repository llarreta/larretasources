package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * VehicleType entity. @author MyEclipse Persistence Tools
 */

public class VehicleType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 440837524048291153L;
	private Long id;
	private String vehicleTypeName;
	private String vehicleTypeCode;

	// Constructors

	/** default constructor */
	public VehicleType() {
	}

	/** minimal constructor */
	public VehicleType(String vehicleTypeName, String vehicleTypeCode) {
		this.vehicleTypeName = vehicleTypeName;
		this.vehicleTypeCode = vehicleTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVehicleTypeName() {
		return this.vehicleTypeName;
	}

	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}

	public String getVehicleTypeCode() {
		return this.vehicleTypeCode;
	}

	public void setVehicleTypeCode(String vehicleTypeCode) {
		this.vehicleTypeCode = vehicleTypeCode;
	}

	@Override
	public String toString() {
		return "VehicleType [id=" + id + ", vehicleTypeCode=" + vehicleTypeCode
				+ "]";
	}
}