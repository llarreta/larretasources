package co.com.directv.sdii.model.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

public class WorkOrderCSRDTO implements java.io.Serializable {

	private static final long serialVersionUID = 3014567718299181906L;
	private Long id;
	private Long serviceHourId;
	private String contactPerson;
	private String descriptionError;
	private String commentManagment;
	private Date dateLastChange;
	private String woCode;
	private Long woReasonId; 
	private Date scheduleDate;
	private Long countryId;
	private Long dealerId;
	private Long userId;
	private Long workOrderCSRStatusId;
	private boolean reSchedule;
	private Long actualStatusId;
	private String actualStatusCode;
	private String addressCode;
	private String ibsCustomerCode;
	private String ibsBuildingCode;
	private String ibsCustomerTypeCode;
	private String ibsSaleDealerCode;
	private String postalCode;
	private List<String> services;
	private List<Long> shippingOrders;
	
	public WorkOrderCSRDTO() {
		super();
	}

	public WorkOrderCSRDTO(Long id, Long serviceHourId, String contactPerson,
			String descriptionError, String commentManagment,
			Date dateLastChange, String woCode, Long woStatusId,
			Long woReasonId, Date agendationDate, Long countryId,
			Long dealerId, Long userId, String status) {
		super();
		this.id = id;
		this.serviceHourId = serviceHourId;
		this.contactPerson = contactPerson;
		this.descriptionError = descriptionError;
		this.commentManagment = commentManagment;
		this.dateLastChange = dateLastChange;
		this.woCode = woCode;
		this.woReasonId = woReasonId;
		this.countryId = countryId;
		this.dealerId = dealerId;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getServiceHourId() {
		return serviceHourId;
	}

	public void setServiceHourId(Long serviceHourId) {
		this.serviceHourId = serviceHourId;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getDescriptionError() {
		return descriptionError;
	}

	public void setDescriptionError(String descriptionError) {
		this.descriptionError = descriptionError;
	}

	public String getCommentManagment() {
		return commentManagment;
	}

	public void setCommentManagment(String commentManagment) {
		this.commentManagment = commentManagment;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getDateLastChange() {
		return dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public Long getWoReasonId() {
		return woReasonId;
	}

	public void setWoReasonId(Long woReasonId) {
		this.woReasonId = woReasonId;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getWorkOrderCSRStatusId() {
		return workOrderCSRStatusId;
	}

	public void setWorkOrderCSRStatusId(Long workOrderCSRStatusId) {
		this.workOrderCSRStatusId = workOrderCSRStatusId;
	}

	public boolean isReSchedule() {
		return reSchedule;
	}

	public void setReSchedule(boolean reSchedule) {
		this.reSchedule = reSchedule;
	}

	public Long getActualStatusId() {
		return actualStatusId;
	}

	public void setActualStatusId(Long actualStatusId) {
		this.actualStatusId = actualStatusId;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getIbsCustomerCode() {
		return ibsCustomerCode;
	}

	public void setIbsCustomerCode(String ibsCustomerCode) {
		this.ibsCustomerCode = ibsCustomerCode;
	}

	public String getIbsBuildingCode() {
		return ibsBuildingCode;
	}

	public void setIbsBuildingCode(String ibsBuildingCode) {
		this.ibsBuildingCode = ibsBuildingCode;
	}

	public String getIbsCustomerTypeCode() {
		return ibsCustomerTypeCode;
	}

	public void setIbsCustomerTypeCode(String ibsCustomerTypeCode) {
		this.ibsCustomerTypeCode = ibsCustomerTypeCode;
	}

	public String getIbsSaleDealerCode() {
		return ibsSaleDealerCode;
	}

	public void setIbsSaleDealerCode(String ibsSaleDealerCode) {
		this.ibsSaleDealerCode = ibsSaleDealerCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public List<Long> getShippingOrders() {
		return shippingOrders;
	}

	public void setShippingOrders(List<Long> shippingOrders) {
		this.shippingOrders = shippingOrders;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getActualStatusCode() {
		return actualStatusCode;
	}

	public void setActualStatusCode(String actualStatusCode) {
		this.actualStatusCode = actualStatusCode;
	}	
}