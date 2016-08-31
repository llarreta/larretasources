package co.com.directv.sdii.model.dto;

import java.io.Serializable;

public class WorkOrderCanceledFilterDTO extends BaseDTO implements Serializable {

	private Long principalDealerId;
	private Long branchId;
	private Long typeServiceId;
	private Long serviceId;
	private String woCode;
	public WorkOrderCanceledFilterDTO(Long principalDealerId, Long branchId,
			Long typeServiceId, Long serviceId, String woCode) {
		super();
		this.principalDealerId = principalDealerId;
		this.branchId = branchId;
		this.typeServiceId = typeServiceId;
		this.serviceId = serviceId;
		this.woCode = woCode;
	}
	public WorkOrderCanceledFilterDTO() {
		super();
	}
	public Long getPrincipalDealerId() {
		return principalDealerId;
	}
	public void setPrincipalDealerId(Long principalDealerId) {
		this.principalDealerId = principalDealerId;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getTypeServiceId() {
		return typeServiceId;
	}
	public void setTypeServiceId(Long typeServiceId) {
		this.typeServiceId = typeServiceId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	
	
	
}
