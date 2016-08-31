package co.com.directv.sdii.reports.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.dto.BaseRequest;

public class DealerServiceSubCategoryWithPcDTO extends BaseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179990151692693134L;
	
	private String postalCodeName;
	private String dealerCode;
	private String depotCode;
	private String subCatSerCode;
	private String subCatSerName;
	
	public String getPostalCodeName() {
		return postalCodeName;
	}
	public void setPostalCodeName(String postalCodeName) {
		this.postalCodeName = postalCodeName;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public String getSubCatSerCode() {
		return subCatSerCode;
	}
	public void setSubCatSerCode(String subCatSerCode) {
		this.subCatSerCode = subCatSerCode;
	}
	public String getSubCatSerName() {
		return subCatSerName;
	}
	public void setSubCatSerName(String subCatSerName) {
		this.subCatSerName = subCatSerName;
	}

}
