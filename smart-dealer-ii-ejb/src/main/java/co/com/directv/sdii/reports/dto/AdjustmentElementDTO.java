package co.com.directv.sdii.reports.dto;


public class AdjustmentElementDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1300370510673117471L;
	
	private Long idWarehouse;
	private Long dealerId;
	private Long branchDealerId;
	private Long crewId;
	private Long warehouseTypeId;
	private String elementTypeCode;
	private String serialCode;
	private Long elementId;
	private Long idWarehouseDestination;
	private Double quantity;
	private String rid;
	private Long countryId;
	
	public String getRid(){
		return rid;
	}
	
	public void setRid(String ridParam){
		rid=ridParam;
	}
	
	public Long getIdWarehouse() {
		return idWarehouse;
	}
	public void setIdWarehouse(Long idWarehouse) {
		this.idWarehouse = idWarehouse;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public Long getBranchDealerId() {
		return branchDealerId;
	}
	public void setBranchDealerId(Long branchDealerId) {
		this.branchDealerId = branchDealerId;
	}
	public Long getCrewId() {
		return crewId;
	}
	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}
	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}
	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}
	public String getElementTypeCode() {
		return elementTypeCode;
	}
	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getElementId() {
		return elementId;
	}
	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}
	public Long getIdWarehouseDestination() {
		return idWarehouseDestination;
	}
	public void setIdWarehouseDestination(Long idWarehouseDestination) {
		this.idWarehouseDestination = idWarehouseDestination;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
}
