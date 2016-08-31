package co.com.directv.sdii.reports.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.dto.BaseRequest;

public class DealerDetailDTO extends BaseRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7719222438451564485L;
	private String dealerCode;
	private String depotCode;
	private String dealerName;
	private String woT;
	private String woD;
	private String build;
	private String attendTypeOddEven;
	
	
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
	public String getWoT() {
		return woT;
	}
	public void setWoT(String woT) {
		this.woT = woT;
	}
	public String getWoD() {
		return woD;
	}
	public void setWoD(String woD) {
		this.woD = woD;
	}
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public String getAttendTypeOddEven() {
		return attendTypeOddEven;
	}
	public void setAttendTypeOddEven(String attendTypeOddEven) {
		this.attendTypeOddEven = attendTypeOddEven;
	}

}
