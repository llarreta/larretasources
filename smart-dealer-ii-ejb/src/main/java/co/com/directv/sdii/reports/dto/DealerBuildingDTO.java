package co.com.directv.sdii.reports.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.dto.BaseRequest;

public class DealerBuildingDTO extends BaseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1740610458652801802L;
	
	private String stateName;
	private String cityName;
	private String buildCode;
	private String dealerCode;
	private String depotCode;
	private String dealerName;
	private String buildName;
	private String buildAdd;
	
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
	public String getBuildCode() {
		return buildCode;
	}
	public void setBuildCode(String buildCode) {
		this.buildCode = buildCode;
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
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getBuildAdd() {
		return buildAdd;
	}
	public void setBuildAdd(String buildAdd) {
		this.buildAdd = buildAdd;
	}
	
	
	

}
