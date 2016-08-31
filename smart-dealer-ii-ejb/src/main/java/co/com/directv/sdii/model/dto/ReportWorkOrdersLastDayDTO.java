package co.com.directv.sdii.model.dto;

import java.util.Date;

import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Employee;

/**
 * 
 * 
 * Fecha de Creación: 2012/5/17
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class ReportWorkOrdersLastDayDTO {
	

	private String customerCode;
	private String woCode;
	private Date statusDate;
	private String employeeFirstName;
	private String employeeLastName;
	private String dealerCode;
	private String dealerDepodCode;
	private String dealerName;
	
	public ReportWorkOrdersLastDayDTO(String customerCode, String woCode,
			Date statusDate, String employeeFirstName, String employeeLastName,
			String dealerCode, String dealerDepodCode, String dealerName) {
		super();
		this.customerCode = customerCode;
		this.woCode = woCode;
		this.statusDate = statusDate;
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.dealerCode = dealerCode;
		this.dealerDepodCode = dealerDepodCode;
		this.dealerName = dealerName;
	}
	
	public ReportWorkOrdersLastDayDTO(String customerCode, String woCode,
			Date statusDate, Employee employee, Dealer dealer) {
		super();
		this.customerCode = customerCode;
		this.woCode = woCode;
		this.statusDate = statusDate;
		if(employee!=null){
			this.employeeFirstName = employee.getFirstName();
			this.employeeLastName = employee.getLastName();			
		}else{
			this.employeeFirstName = "";
			this.employeeLastName = "";
		}
		if(dealer!=null){
			this.dealerCode = dealer.getDealerCode().toString();
			this.dealerDepodCode = dealer.getDepotCode();
			this.dealerName = dealer.getDealerName();		
		}else{
			this.dealerCode = "";
			this.dealerDepodCode = "";
			this.dealerName = "";			
		}
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
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFistName) {
		this.employeeFirstName = employeeFistName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerDepodCode() {
		return dealerDepodCode;
	}
	public void setDealerDepodCode(String dealerDepodCode) {
		this.dealerDepodCode = dealerDepodCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	
}
