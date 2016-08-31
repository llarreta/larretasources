package co.com.directv.sdii.model.dto;

import java.io.Serializable;

public class TrayServiceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6531901726060058778L;

	private Long serviceId;
	private String serviceCode;
	private String serviceName;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}	
	
}
