package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ServiceCategory entity. @author MyEclipse Persistence Tools
 */

public class ServiceCategory implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7824317869255947071L;
	private Long id;
	private ServiceType serviceType;
	private String serviceCategoryName;
	private String serviceCategoryCode;

	// Constructors

	/** default constructor */
	public ServiceCategory() {
	}

	/** minimal constructor */
	public ServiceCategory(ServiceType serviceType, String serviceCategoryName,
			String serviceCategoryCode) {
		this.serviceType = serviceType;
		this.serviceCategoryName = serviceCategoryName;
		this.serviceCategoryCode = serviceCategoryCode;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceType getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceCategoryName() {
		return this.serviceCategoryName;
	}

	public void setServiceCategoryName(String serviceCategoryName) {
		this.serviceCategoryName = serviceCategoryName;
	}

	public String getServiceCategoryCode() {
		return this.serviceCategoryCode;
	}

	public void setServiceCategoryCode(String serviceCategoryCode) {
		this.serviceCategoryCode = serviceCategoryCode;
	}

	@Override
	public String toString() {
		return "ServiceCategory [id=" + id + ", serviceCategoryCode="
				+ serviceCategoryCode + "]";
	}

}