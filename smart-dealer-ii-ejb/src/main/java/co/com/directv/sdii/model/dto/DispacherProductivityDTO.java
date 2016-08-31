package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

public class DispacherProductivityDTO implements Serializable {

	private String woCode;
	private String customerCode;
	private String dispacher;
	private String state;
	private Date stateDate;
	private String serviceHourName;
	private String crewResponsible;
	private String depotCodeDealer;
	private String nameDealer;
	private String serviceCategory;
	private String serviceCategoryCode;
	private String serviceName;
	private Date creationDate;
	private String dispacherName;

	public DispacherProductivityDTO(String woCode, String customerCode,
			String dispacher, String state, Date stateDate,
			String serviceHourName, String crewResponsible,
			String depotCodeDealer, String nameDealer, String serviceCategory,
			String serviceCategoryCode, String serviceName, Date creationDate,
			String dispacherName) {
		super();
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.dispacher = dispacher;
		this.state = state;
		this.stateDate = stateDate;
		this.serviceHourName = serviceHourName;
		this.crewResponsible = crewResponsible;
		this.depotCodeDealer = depotCodeDealer;
		this.nameDealer = nameDealer;
		this.serviceCategory = serviceCategory;
		this.serviceCategoryCode = serviceCategoryCode;
		this.serviceName = serviceName;
		this.creationDate = creationDate;
		this.dispacherName = dispacherName;
	}
	public String getDispacherName() {
		return dispacherName;
	}
	public void setDispacherName(String dispacherName) {
		this.dispacherName = dispacherName;
	}
	public DispacherProductivityDTO(String woCode, String customerCode,
			String dispacher, String state, Date stateDate,
			String serviceHourName, String crewResponsible,
			String depotCodeDealer, String nameDealer, String serviceCategory,
			String serviceCategoryCode, String serviceName, Date creationDate) {
		super();
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.dispacher = dispacher;
		this.state = state;
		this.stateDate = stateDate;
		this.serviceHourName = serviceHourName;
		this.crewResponsible = crewResponsible;
		this.depotCodeDealer = depotCodeDealer;
		this.nameDealer = nameDealer;
		this.serviceCategory = serviceCategory;
		this.serviceCategoryCode = serviceCategoryCode;
		this.serviceName = serviceName;
		this.creationDate = creationDate;
	}
	public DispacherProductivityDTO() {
		super();
	}
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getDispacher() {
		return dispacher;
	}
	public void setDispacher(String dispacher) {
		this.dispacher = dispacher;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getStateDate() {
		return stateDate;
	}
	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}
	public String getServiceHourName() {
		return serviceHourName;
	}
	public void setServiceHourName(String serviceHourName) {
		this.serviceHourName = serviceHourName;
	}
	public String getCrewResponsible() {
		return crewResponsible;
	}
	public void setCrewResponsible(String crewResponsible) {
		this.crewResponsible = crewResponsible;
	}
	public String getDepotCodeDealer() {
		return depotCodeDealer;
	}
	public void setDepotCodeDealer(String depotCodeDealer) {
		this.depotCodeDealer = depotCodeDealer;
	}
	public String getNameDealer() {
		return nameDealer;
	}
	public void setNameDealer(String nameDealer) {
		this.nameDealer = nameDealer;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getServiceCategoryCode() {
		return serviceCategoryCode;
	}
	public void setServiceCategoryCode(String serviceCategoryCode) {
		this.serviceCategoryCode = serviceCategoryCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
