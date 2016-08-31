package co.com.directv.sdii.reports.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.dto.BaseRequest;

/**
 * Objeto que encapsula la información de un DealerConfCoverage
 * 
 * Fecha de Creación: 17/01/2014
 * @author ssanabri <a href="mailto:facundo.sanabria@everis.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerConfCoverage    
 */
public class DealerConfCoverageDTO extends BaseRequest implements Serializable {

	private static final long serialVersionUID = 2401849720343540763L;

	private String businessAreaName;
	private String customerCategoryName;
	private String postalCodeName;
	private String cityName;
	private String stateName;
	private String dealerCode;
	private String depotCode;
	private String coverageTypeName;
	
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
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
	public String getCoverageTypeName() {
		return coverageTypeName;
	}
	public void setCoverageTypeName(String coverageTypeName) {
		this.coverageTypeName = coverageTypeName;
	}
	public String getBusinessAreaName() {
		return businessAreaName;
	}
	public void setBusinessAreaName(String businessAreaName) {
		this.businessAreaName = businessAreaName;
	}
	public String getCustomerCategoryName() {
		return customerCategoryName;
	}
	public void setCustomerCategoryName(String customerCategoryName) {
		this.customerCategoryName = customerCategoryName;
	}
	
}
