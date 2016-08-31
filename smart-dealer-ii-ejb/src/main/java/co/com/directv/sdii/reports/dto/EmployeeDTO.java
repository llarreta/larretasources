package co.com.directv.sdii.reports.dto;

public class EmployeeDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3221398965087777113L;
	
	private String documentNumber;
	private String employeeName;
	private String positionName;
	private String employeeStatusName;
	private Long dealerCode;
	private String dealerName;
	
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getEmployeeStatusName() {
		return employeeStatusName;
	}
	public void setEmployeeStatusName(String employeeStatusName) {
		this.employeeStatusName = employeeStatusName;
	}
	public Long getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	

}
