package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ServiceType entity. @author MyEclipse Persistence Tools
 */

public class ServiceType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2224455375435697219L;
	private Long id;
	private ServiceSuperCategory superCategory;
	private String serviceTypeName;
	private String serviceTypeCode;
	//Req-0096 - Requerimiento Consolidado Asignador
	private Long businessAreaId;

	// Constructors

	/** default constructor */
	public ServiceType() {
	}

	public ServiceType(Long id) {
		this.id = id;
	}
	
	/** minimal constructor */
	public ServiceType(String serviceTypeName, String serviceTypeCode) {
		this.serviceTypeName = serviceTypeName;
		this.serviceTypeCode = serviceTypeCode;
	}
	
	
	/** FULL constructor */
	public ServiceType(Long id, ServiceSuperCategory superCategory,
			String serviceTypeName, String serviceTypeCode) {
		super();
		this.id = id;
		this.superCategory = superCategory;
		this.serviceTypeName = serviceTypeName;
		this.serviceTypeCode = serviceTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceTypeName() {
		return this.serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getServiceTypeCode() {
		return this.serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public ServiceSuperCategory getSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(ServiceSuperCategory superCategory) {
		this.superCategory = superCategory;
	}

	@Override
	public String toString() {
		return "ServiceType [id=" + id + ", serviceTypeCode=" + serviceTypeCode
				+ "]";
	}

	public Long getBusinessAreaId() {
		return businessAreaId;
	}

	public void setBusinessAreaId(Long businessAreaId) {
		this.businessAreaId = businessAreaId;
	}
	
	

}