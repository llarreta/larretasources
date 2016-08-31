package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * DTO para empaquetar la informacion de la wh.
 * 
 * Fecha de Creación: 20/03/2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WareHouseRequestDTO implements Serializable{

	private Long dealerId;
	private Long crewId;
	private Long countryId;
    private Long customerId;
    private String customerCode;
    private String serialCode;
    private Long warehouseId;
    private Long warehouseTypeId;
    private Long elementTypeId;
    private Long branchId;
    private RequestCollectionInfo requestCollInfo;
    
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	
	public Long getDealerId() {
		return dealerId;
	}
	
	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}
	
	public Long getCrewId() {
		return crewId;
	}
	
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	public Long getCountryId() {
		return countryId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public Long getWarehouseId() {
		return warehouseId;
	}
	
	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}
	
	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}
	
	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}
	
	public Long getElementTypeId() {
		return elementTypeId;
	}
	
	public void setRequestCollInfo(RequestCollectionInfo requestCollInfo) {
		this.requestCollInfo = requestCollInfo;
	}
	
	public RequestCollectionInfo getRequestCollInfo() {
		return requestCollInfo;
	}
	
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public String getCustomerCode() {
		return customerCode;
	}
	
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	
	public Long getBranchId() {
		return branchId;
	}
	
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	
	public String getSerialCode() {
		return serialCode;
	}
    
}
