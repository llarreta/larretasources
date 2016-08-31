package co.com.directv.sdii.model.dto;

import java.util.List;

import co.com.directv.sdii.model.pojo.WorkOrder;

public class DispatchWorkOrderRequestDTO implements  java.io.Serializable {

	private static final long serialVersionUID = 2869426656971608959L;

	private String countryCode;
	private String technicianCode;
	private String technicianPIN;
	private List<WorkOrder> workOrders;
	
	public DispatchWorkOrderRequestDTO(String countryCode, String technicianCode,
			String technicianPIN, List<WorkOrder> workOrders) {
		super();
		this.countryCode = countryCode;
		this.technicianCode = technicianCode;
		this.technicianPIN = technicianPIN;
		this.workOrders = workOrders; 
	}

	public String getCountryCode() {
		return countryCode;
	}
	public String getTechnicianCode() {
		return technicianCode;
	}
	public String getTechnicianPIN() {
		return technicianPIN;
	}
	public List<WorkOrder> getWorkOrders() {
		return workOrders;
	}

	
}
