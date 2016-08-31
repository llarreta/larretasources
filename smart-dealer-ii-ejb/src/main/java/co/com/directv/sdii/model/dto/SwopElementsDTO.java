package co.com.directv.sdii.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.WarehouseElement;

/**
 * 
 * Data Transfer Object para  la realización del proceso de swop desd hsp
 * 
 * @version 1.0
 * @author waguilera
 */
public class SwopElementsDTO implements Serializable {

	private String customerCode;
	
	private Long countryId;
	
	private String serialDecoInstall;
	
	private String serialSCInstall;
	
	private String serialDecoRecovery;
	
	private String serialSCRecovery;
	
	private Long dealerCode;
	
	private WarehouseElement warehouseElementDecoInstall;
	
	private WarehouseElement warehouseElementSCInstall;
	
	private WarehouseElement warehouseElementDecoRecovery;
	
	private WarehouseElement warehouseElementSCRecovery;
	
	public String getCustomerCode() {
		return customerCode;
	}
	
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public Long getCountryId() {
		return countryId;
	}
	
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	public String getSerialDecoInstall() {
		return serialDecoInstall;
	}
	
	public void setSerialDecoInstall(String serialDecoInstall) {
		this.serialDecoInstall = serialDecoInstall;
	}
	
	public String getSerialSCInstall() {
		return serialSCInstall;
	}
	
	public void setSerialSCInstall(String serialSCInstall) {
		this.serialSCInstall = serialSCInstall;
	}
	
	public String getSerialDecoRecovery() {
		return serialDecoRecovery;
	}
	
	public void setSerialDecoRecovery(String serialDecoRecovery) {
		this.serialDecoRecovery = serialDecoRecovery;
	}
	
	public String getSerialSCRecovery() {
		return serialSCRecovery;
	}
	
	public void setSerialSCRecovery(String serialSCRecovery) {
		this.serialSCRecovery = serialSCRecovery;
	}

	public Long getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}

	public WarehouseElement getWarehouseElementDecoInstall() {
		return warehouseElementDecoInstall;
	}

	public void setWarehouseElementDecoInstall(
			WarehouseElement warehouseElementDecoInstall) {
		this.warehouseElementDecoInstall = warehouseElementDecoInstall;
	}

	public WarehouseElement getWarehouseElementSCInstall() {
		return warehouseElementSCInstall;
	}

	public void setWarehouseElementSCInstall(
			WarehouseElement warehouseElementSCInstall) {
		this.warehouseElementSCInstall = warehouseElementSCInstall;
	}

	public WarehouseElement getWarehouseElementDecoRecovery() {
		return warehouseElementDecoRecovery;
	}

	public void setWarehouseElementDecoRecovery(
			WarehouseElement warehouseElementDecoRecovery) {
		this.warehouseElementDecoRecovery = warehouseElementDecoRecovery;
	}

	public WarehouseElement getWarehouseElementSCRecovery() {
		return warehouseElementSCRecovery;
	}

	public void setWarehouseElementSCRecovery(
			WarehouseElement warehouseElementSCRecovery) {
		this.warehouseElementSCRecovery = warehouseElementSCRecovery;
	}
	
	
	
}
