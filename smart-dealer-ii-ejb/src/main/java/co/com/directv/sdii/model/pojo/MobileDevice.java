package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * MobileDevice entity. @author MyEclipse Persistence Tools
 */

public class MobileDevice implements java.io.Serializable,Auditable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 5392223484221841588L;
	private Long id;
	private ServiceProvider serviceProvider;
	private DeviceModel deviceModel;
	private String pin;
	private String imei;
	private String phoneNumber;
	private String firmware;
	private String isActive;

	// Constructors

	/** default constructor */
	public MobileDevice() {
	}

	/** minimal constructor */
	public MobileDevice(Long id, ServiceProvider serviceProvider,
			DeviceModel deviceModel, String pin, String imei,
			String phoneNumber, String firmware, String isActive) {
		this.id = id;
		this.serviceProvider = serviceProvider;
		this.deviceModel = deviceModel;
		this.pin = pin;
		this.imei = imei;
		this.phoneNumber = phoneNumber;
		this.firmware = firmware;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceProvider getServiceProvider() {
		return this.serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public DeviceModel getDeviceModel() {
		return this.deviceModel;
	}

	public void setDeviceModel(DeviceModel deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirmware() {
		return this.firmware;
	}

	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "MobileDevice [id=" + id + "]";
	}
	
	
}