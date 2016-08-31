package co.com.directv.sdii.model.dto;


public class ReportWorkOrderRejectionDTO {
	
	private String customerCode;
	private String woCode;
	private String woStateCode;
	private String serviceCode;
	private String cityName;
	private String serialNumber;
	private String   creationDate;
	private String   dateUnassignmentDealer;
	private String dealerAssignment;
	private String   dateAssignmentDealer;
	private String woReasonName;
	private String observation;
	private String   countDate;
	
	public ReportWorkOrderRejectionDTO() {
		super();
	}	

	public ReportWorkOrderRejectionDTO(String customerCode, String woCode,
			String woStateCode, String serviceCode, String cityName,
			String serialNumber, String creationDate,
			String dateUnassignmentDealer, String dealerAssignment,
			String dateAssignmentDealer, String woReasonName,
			String observation, String countDate) {
		super();
		this.customerCode = customerCode;
		this.woCode = woCode;
		this.woStateCode = woStateCode;
		this.serviceCode = serviceCode;
		this.cityName = cityName;
		this.serialNumber = serialNumber;
		this.creationDate = creationDate;
		this.dateUnassignmentDealer = dateUnassignmentDealer;
		this.dealerAssignment = dealerAssignment;
		this.dateAssignmentDealer = dateAssignmentDealer;
		this.woReasonName = woReasonName;
		this.observation = observation;
		this.countDate = countDate;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public String getWoStateCode() {
		return woStateCode;
	}

	public void setWoStateCode(String woStateCode) {
		this.woStateCode = woStateCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDateUnassignmentDealer() {
		return dateUnassignmentDealer;
	}

	public void setDateUnassignmentDealer(String dateUnassignmentDealer) {
		this.dateUnassignmentDealer = dateUnassignmentDealer;
	}

	public String getDealerAssignment() {
		return dealerAssignment;
	}

	public void setDealerAssignment(String dealerAssignment) {
		this.dealerAssignment = dealerAssignment;
	}

	public String getDateAssignmentDealer() {
		return dateAssignmentDealer;
	}

	public void setDateAssignmentDealer(String dateAssignmentDealer) {
		this.dateAssignmentDealer = dateAssignmentDealer;
	}

	public String getWoReasonName() {
		return woReasonName;
	}

	public void setWoReasonName(String woReasonName) {
		this.woReasonName = woReasonName;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}
	
	
}
