package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * DeviceModel entity. @author MyEclipse Persistence Tools
 */

public class DeviceModel implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8221719673144918648L;
	private Long id;
	private DeviceBrand deviceBrand;
	private String devModelCode;
	private String devModelName;

	// Constructors

	/** default constructor */
	public DeviceModel() {
	}

	/** minimal constructor */
	public DeviceModel(Long id, DeviceBrand deviceBrand, String devModelCode,
			String devModelName) {
		this.id = id;
		this.deviceBrand = deviceBrand;
		this.devModelCode = devModelCode;
		this.devModelName = devModelName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeviceBrand getDeviceBrand() {
		return this.deviceBrand;
	}

	public void setDeviceBrand(DeviceBrand deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDevModelCode() {
		return this.devModelCode;
	}

	public void setDevModelCode(String devModelCode) {
		this.devModelCode = devModelCode;
	}

	public String getDevModelName() {
		return this.devModelName;
	}

	public void setDevModelName(String devModelName) {
		this.devModelName = devModelName;
	}

	@Override
	public String toString() {
		return "DeviceModel [devModelCode=" + devModelCode + ", id=" + id + "]";
	}
	
}