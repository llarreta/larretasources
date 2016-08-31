package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Service entity. @author MyEclipse Persistence Tools
 */

public class Service implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2745508598523931321L;

	private Long id;

    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ServiceStatus serviceStatus;
    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ServiceCategory serviceCategory;
    @BusinessRequired
	private String serviceName;
    @BusinessRequired
	private String serviceCode;
    @BusinessRequired
	private String isShippingOrderRequired;
    @BusinessRequired
   	private String allowAddService;
    @BusinessRequired
   	private String allowDeleteService;
    @BusinessRequired
   	private String allowRecordingElement;
    @BusinessRequired
   	private String allowRetrieveElement;
    @BusinessRequired
   	private String allowChangeElement;
    @BusinessRequired
   	private String allowAddServiceEnd;
    @BusinessRequired
    private String serviceIbsCode;
    
    private String isContractRequired;
    
    private String isPriority;
    
    private String denyActivateWO;
    
	// Constructors

	/** default constructor */
	public Service() {
	}

	public Service(Long id) {
		this.id = id;
	}
	
	/** full constructor */
	public Service(ServiceStatus serviceStatus,
			ServiceCategory serviceCategory, String serviceName,
			String serviceCode, String isShippingOrderRequired, 
			String allowAddService, String allowDeleteService, 
			String allowRecordingElement, String allowRetrieveElement,
			String allowChangeElement, String allowAddServiceEnd,
			String serviceIbsCode) {
		this.serviceStatus = serviceStatus;
		this.serviceCategory = serviceCategory;
		this.serviceName = serviceName;
		this.serviceCode = serviceCode;
		this.isShippingOrderRequired = isShippingOrderRequired;
		this.allowAddService = allowAddService;
		this.allowDeleteService = allowDeleteService;
		this.allowRecordingElement = allowRecordingElement;
		this.allowRetrieveElement = allowRetrieveElement;
		this.allowChangeElement = allowChangeElement;
		this.allowAddServiceEnd = allowAddServiceEnd;
		this.serviceIbsCode = serviceIbsCode;
	}
	
	/** minimal constructor */
	public Service(ServiceStatus serviceStatus,
			ServiceCategory serviceCategory, String serviceName,
			String serviceCode, String isShippingOrderRequired) {
		this.serviceStatus = serviceStatus;
		this.serviceCategory = serviceCategory;
		this.serviceName = serviceName;
		this.serviceCode = serviceCode;
		this.isShippingOrderRequired = isShippingOrderRequired;
	}
	
	/** minimal constructor */
	public Service(Long id, ServiceCategory serviceCategory, String serviceName, 
			String serviceCode, String isShippingOrderRequired, 
			String allowAddService, String allowDeleteService, 
			String allowRecordingElement, String allowRetrieveElement) {
		this.id = id;
		this.serviceCategory = serviceCategory;
		this.serviceName = serviceName;
		this.serviceCode = serviceCode;
		this.isShippingOrderRequired = isShippingOrderRequired;
		this.allowAddService = allowAddService;
		this.allowDeleteService = allowDeleteService;
		this.allowRecordingElement = allowRecordingElement;
		this.allowRetrieveElement = allowRetrieveElement;
	}
	
	

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceStatus getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public ServiceCategory getServiceCategory() {
		return this.serviceCategory;
	}

	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getIsShippingOrderRequired() {
		return this.isShippingOrderRequired;
	}

	public void setIsShippingOrderRequired(String isShippingOrderRequired) {
		this.isShippingOrderRequired = isShippingOrderRequired;
	}
	
	public String getAllowAddService() {
		return allowAddService;
	}

	public void setAllowAddService(String allowAddService) {
		this.allowAddService = allowAddService;
	}

	public String getAllowDeleteService() {
		return allowDeleteService;
	}

	public void setAllowDeleteService(String allowDeleteService) {
		this.allowDeleteService = allowDeleteService;
	}

	public String getAllowRecordingElement() {
		return allowRecordingElement;
	}

	public void setAllowRecordingElement(String allowRecordingElement) {
		this.allowRecordingElement = allowRecordingElement;
	}

	public String getAllowRetrieveElement() {
		return allowRetrieveElement;
	}

	public void setAllowRetrieveElement(String allowRetrieveElement) {
		this.allowRetrieveElement = allowRetrieveElement;
	}

	public String getAllowChangeElement() {
		return allowChangeElement;
	}

	public void setAllowChangeElement(String allowChangeElement) {
		this.allowChangeElement = allowChangeElement;
	}

	public String getAllowAddServiceEnd() {
		return allowAddServiceEnd;
	}

	public void setAllowAddServiceEnd(String allowAddServiceEnd) {
		this.allowAddServiceEnd = allowAddServiceEnd;
	}
	
	public String getServiceIbsCode() {
		return serviceIbsCode;
	}

	public void setServiceIbsCode(String serviceIbsCode) {
		this.serviceIbsCode = serviceIbsCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Service other = (Service) obj;
		if (serviceCode == null) {
			if (other.getServiceCode() != null)
				return false;
		} else if (!serviceCode.equalsIgnoreCase(other.getServiceCode()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", serviceCode=" + serviceCode + "]";
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public String getIsContractRequired() {
		return isContractRequired;
	}

	public void setIsContractRequired(String isContractRequired) {
		this.isContractRequired = isContractRequired;
	}

	public String getIsPriority() {
		return isPriority;
	}

	public void setIsPriority(String isPriority) {
		this.isPriority = isPriority;
	}
	
	public String getDenyActivateWO() {
		return denyActivateWO;
	}

	public void setDenyActivateWO(String denyActivateWO) {
		this.denyActivateWO = denyActivateWO;
	}

}