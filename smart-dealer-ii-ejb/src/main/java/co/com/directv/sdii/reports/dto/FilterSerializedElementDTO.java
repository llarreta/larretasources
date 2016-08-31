package co.com.directv.sdii.reports.dto;


public class FilterSerializedElementDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dealerId;
	private Long branchId;
	private Long warehouseTypeId;
	private Long warehouseId;
	private Long elementTypeId;
	private Long countryId;
	private String serialCode;
	
	
	
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public Long getBranchId() {
		return branchId;
	}
	
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	
	public Long getWarehouseId() {
		return warehouseId;
	}
	
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public Long getElementTypeId() {
		return elementTypeId;
	}
	
	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}

	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	
}
