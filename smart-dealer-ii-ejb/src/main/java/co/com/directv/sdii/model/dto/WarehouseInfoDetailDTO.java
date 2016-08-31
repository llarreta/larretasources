package co.com.directv.sdii.model.dto;

import java.io.Serializable;


/**
 * DTo que encapsula el detalle de ubicaciones de dealers instaladores
 * @author clopez
 *
 */

public class WarehouseInfoDetailDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String warehouseCode;
	private String warehouseName;
	private Long id;
	private Long principalDealerCode;
	private String branchDealerCode;
	private String branchDealerName;
	private String crewName;
	private String warehouseType;
	private String responsible;
	private String responsibleEmail;
	private String whAddress;
	private String isActive;
	private String depotCode;
	private String dealerName;

	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getBranchDealerName() {
		return branchDealerName;
	}
	public void setBranchDealerName(String branchDealerName) {
		this.branchDealerName = branchDealerName;
	}
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBranchDealerCode() {
		return branchDealerCode;
	}
	public void setBranchDealerCode(String branchDealerCode) {
		this.branchDealerCode = branchDealerCode;
	}
	public String getCrewName() {
		return crewName;
	}
	public void setCrewName(String crewName) {
		this.crewName = crewName;
	}
	public String getWarehouseType() {
		return warehouseType;
	}
	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	public String getResponsibleEmail() {
		return responsibleEmail;
	}
	public void setResponsibleEmail(String responsibleEmail) {
		this.responsibleEmail = responsibleEmail;
	}
	public String getWhAddress() {
		return whAddress;
	}
	public void setWhAddress(String whAddress) {
		this.whAddress = whAddress;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}	
	public Long getPrincipalDealerCode() {
		return principalDealerCode;
	}
	public void setPrincipalDealerCode(Long principalDealerCode) {
		this.principalDealerCode = principalDealerCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}	

}
