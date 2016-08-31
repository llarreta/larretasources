package co.com.directv.sdii.model.pojo;

public class TypeServiceWorkOrders implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2030465176943763754L;

	private Long id;
	private ServiceType serviceType;
	private WoType workOrderType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	public WoType getWorkOrderType() {
		return workOrderType;
	}
	public void setWorkOrderType(WoType workOrderType) {
		this.workOrderType = workOrderType;
	}
	
	
	
}