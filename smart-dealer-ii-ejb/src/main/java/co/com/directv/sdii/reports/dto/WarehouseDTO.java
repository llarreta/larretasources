package co.com.directv.sdii.reports.dto;

public class WarehouseDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3109329396324556386L;
	
	private String whCode;
	private String principalDealerName;
	private String branchDealerName;
	private String depotCode;
	private String whType;
	private String whResponsable;
	private String whResponsableMail;
	private String whAddress;
	private String crewInfo;
	private String whName;
	private String statusWh;
	
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getPrincipalDealerName() {
		return principalDealerName;
	}
	public void setPrincipalDealerName(String principalDealerName) {
		this.principalDealerName = principalDealerName;
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
	public String getWhType() {
		return whType;
	}
	public void setWhType(String whType) {
		this.whType = whType;
	}
	public String getWhResponsable() {
		return whResponsable;
	}
	public void setWhResponsable(String whResponsable) {
		this.whResponsable = whResponsable;
	}
	public String getWhResponsableMail() {
		return whResponsableMail;
	}
	public void setWhResponsableMail(String whResponsableMail) {
		this.whResponsableMail = whResponsableMail;
	}
	public String getWhAddress() {
		return whAddress;
	}
	public void setWhAddress(String whAddress) {
		this.whAddress = whAddress;
	}
	public String getCrewInfo() {
		return crewInfo;
	}
	public void setCrewInfo(String crewInfo) {
		this.crewInfo = crewInfo;
	}
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}
	public String getStatusWh() {
		return statusWh;
	}
	public void setStatusWh(String statusWh) {
		this.statusWh = statusWh;
	}
	
	

}
