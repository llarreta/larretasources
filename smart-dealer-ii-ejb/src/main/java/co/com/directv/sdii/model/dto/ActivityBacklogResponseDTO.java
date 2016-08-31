package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

public class ActivityBacklogResponseDTO implements Serializable{

	private String phone;
	private String numWorkOrder;
	private String ibsClient;
	private String address;
	private Date programmingDate;
	private String serviceHour;
	private Date creationDate;
	private String description;
	private String stateWorkOrder;
	private String ibsState;
	private String crewResponsible;
	private String depotCodeDealer;
	private Long reschedule;
	private String dispatcher;
	private String state;
	private String city;
	private String customerTypeName;
	private String observationCompany;
	private Long contDecos;
	private String depotCodeSalerCompany;
	private String nameCodeSalerCompany;
	private String serviceCategory;
	private String serviceCategoryCode;
	private String serviceName;
	private String serviceCode;
	private String serviceTypeName;
	private Long pendingDays;
	private String lastReason;
	private String nameDealer;
	private String dispacherName;

	public ActivityBacklogResponseDTO(String phone, String numWorkOrder,
			String ibsClient, String address, Date programmingDate,
			String serviceHour, Date creationDate, String description,
			String stateWorkOrder, String ibsState, String crewResponsible,
			String depotCodeDealer, Long reschedule, String dispatcher,
			String state, String city, String customerTypeName,
			String observationCompany, Long contDecos,
			String depotCodeSalerCompany, String nameCodeSalerCompany,
			String serviceCategory, String serviceCategoryCode,
			String serviceName, String serviceCode, String serviceTypeName,
			Long pendingDays, String lastReason, String nameDealer,
			String dispacherName) {
		super();
		this.phone = phone;
		this.numWorkOrder = numWorkOrder;
		this.ibsClient = ibsClient;
		this.address = address;
		this.programmingDate = programmingDate;
		this.serviceHour = serviceHour;
		this.creationDate = creationDate;
		this.description = description;
		this.stateWorkOrder = stateWorkOrder;
		this.ibsState = ibsState;
		this.crewResponsible = crewResponsible;
		this.depotCodeDealer = depotCodeDealer;
		this.reschedule = reschedule;
		this.dispatcher = dispatcher;
		this.state = state;
		this.city = city;
		this.customerTypeName = customerTypeName;
		this.observationCompany = observationCompany;
		this.contDecos = contDecos;
		this.depotCodeSalerCompany = depotCodeSalerCompany;
		this.nameCodeSalerCompany = nameCodeSalerCompany;
		this.serviceCategory = serviceCategory;
		this.serviceCategoryCode = serviceCategoryCode;
		this.serviceName = serviceName;
		this.serviceCode = serviceCode;
		this.serviceTypeName = serviceTypeName;
		this.pendingDays = pendingDays;
		this.lastReason = lastReason;
		this.nameDealer = nameDealer;
		this.dispacherName = dispacherName;
	}
	public String getDispacherName() {
		return dispacherName;
	}
	public void setDispacherName(String dispacherName) {
		this.dispacherName = dispacherName;
	}
	public ActivityBacklogResponseDTO(String phone, String numWorkOrder,
			String ibsClient, String address, Date programmingDate,
			String serviceHour, Date creationDate, String description,
			String stateWorkOrder, String ibsState, String crewResponsible,
			String depotCodeDealer, Long reschedule, String dispatcher,
			String state, String city, String customerTypeName,
			String observationCompany, Long contDecos,
			String depotCodeSalerCompany, String nameCodeSalerCompany,
			String serviceCategory, String serviceCategoryCode,
			String serviceName, String serviceCode, String serviceTypeName,
			Long pendingDays, String lastReason, String nameDealer) {
		super();
		this.phone = phone;
		this.numWorkOrder = numWorkOrder;
		this.ibsClient = ibsClient;
		this.address = address;
		this.programmingDate = programmingDate;
		this.serviceHour = serviceHour;
		this.creationDate = creationDate;
		this.description = description;
		this.stateWorkOrder = stateWorkOrder;
		this.ibsState = ibsState;
		this.crewResponsible = crewResponsible;
		this.depotCodeDealer = depotCodeDealer;
		this.reschedule = reschedule;
		this.dispatcher = dispatcher;
		this.state = state;
		this.city = city;
		this.customerTypeName = customerTypeName;
		this.observationCompany = observationCompany;
		this.contDecos = contDecos;
		this.depotCodeSalerCompany = depotCodeSalerCompany;
		this.nameCodeSalerCompany = nameCodeSalerCompany;
		this.serviceCategory = serviceCategory;
		this.serviceCategoryCode = serviceCategoryCode;
		this.serviceName = serviceName;
		this.serviceCode = serviceCode;
		this.serviceTypeName = serviceTypeName;
		this.pendingDays = pendingDays;
		this.lastReason = lastReason;
		this.nameDealer = nameDealer;
	}
	public ActivityBacklogResponseDTO() {
		super();
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNumWorkOrder() {
		return numWorkOrder;
	}
	public void setNumWorkOrder(String numWorkOrder) {
		this.numWorkOrder = numWorkOrder;
	}
	public String getIbsClient() {
		return ibsClient;
	}
	public void setIbsClient(String ibsClient) {
		this.ibsClient = ibsClient;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getProgrammingDate() {
		return programmingDate;
	}
	public void setProgrammingDate(Date programmingDate) {
		this.programmingDate = programmingDate;
	}
	public String getServiceHour() {
		return serviceHour;
	}
	public void setServiceHour(String serviceHour) {
		this.serviceHour = serviceHour;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStateWorkOrder() {
		return stateWorkOrder;
	}
	public void setStateWorkOrder(String stateWorkOrder) {
		this.stateWorkOrder = stateWorkOrder;
	}
	public String getIbsState() {
		return ibsState;
	}
	public void setIbsState(String ibsState) {
		this.ibsState = ibsState;
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
	public Long getReschedule() {
		return reschedule;
	}
	public void setReschedule(Long reschedule) {
		this.reschedule = reschedule;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCustomerTypeName() {
		return customerTypeName;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	public String getObservationCompany() {
		return observationCompany;
	}
	public void setObservationCompany(String observationCompany) {
		this.observationCompany = observationCompany;
	}
	public Long getContDecos() {
		return contDecos;
	}
	public void setContDecos(Long contDecos) {
		this.contDecos = contDecos;
	}
	public String getDepotCodeSalerCompany() {
		return depotCodeSalerCompany;
	}
	public void setDepotCodeSalerCompany(String depotCodeSalerCompany) {
		this.depotCodeSalerCompany = depotCodeSalerCompany;
	}
	public String getNameCodeSalerCompany() {
		return nameCodeSalerCompany;
	}
	public void setNameCodeSalerCompany(String nameCodeSalerCompany) {
		this.nameCodeSalerCompany = nameCodeSalerCompany;
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
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public Long getPendingDays() {
		return pendingDays;
	}
	public void setPendingDays(Long pendingDays) {
		this.pendingDays = pendingDays;
	}
	public String getLastReason() {
		return lastReason;
	}
	public void setLastReason(String lastReason) {
		this.lastReason = lastReason;
	}
	public String getNameDealer() {
		return nameDealer;
	}
	public void setNameDealer(String nameDealer) {
		this.nameDealer = nameDealer;
	}

}
