package co.com.directv.sdii.model.dto;

public class ContractWorkOrderRequestDTO implements  java.io.Serializable{

	private String countryCode;
	private String serviceIbsCode;
	private String customerCode;
	
	public ContractWorkOrderRequestDTO() {
		super();
	}
	
	public ContractWorkOrderRequestDTO(String countryCode, String serviceIbsCode,
			String customerCode) {
		super();
		this.countryCode = countryCode;
		this.serviceIbsCode = serviceIbsCode;
		this.customerCode = customerCode;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getServiceIbsCode() {
		return serviceIbsCode;
	}

	public void setServiceIbsCode(String serviceIbsCode) {
		this.serviceIbsCode = serviceIbsCode;
	}
	
	
	
}
