package co.com.directv.sdii.model.pojo;

import java.util.Date;

public class WorkOrderCSR implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3014567718299181906L;
	
	
	
	private Long id;
	private ServiceHour serviceHour;
	private String contactPerson;
	private String descriptionError;
	private String commentManagment;
	private Date dateLastChange;
	private String woCode;
	private WorkorderStatus woStatus;
	private WorkorderReason woReason; 
	private Date scheduleDate;
	private Country country;
	private Dealer dealer;
	private User user;
	private WorkorderCSRStatus workOrderCSRStatus;
	private String reSchedule;
	private String addressCode;
	private String ibsCustomerCode;
	private String ibsBuildingCode;
	private String ibsCustomerTypeCode;
	private String ibsSaleDealerCode;
	private String postalCode;
	
	// Constructors

	/** default constructor */
	public WorkOrderCSR() {
	}
	
	public WorkOrderCSR(Long id, ServiceHour serviceHour, String contactPerson,
			String descriptionError, String commentManagment,
			Date dateLastChange, String woCode, WorkorderStatus woStatus,
			WorkorderReason woReason, Date scheduleDate, Country country,
			Dealer dealer, User user, WorkorderCSRStatus workOrderCSRStatus,
			String reSchedule, String addressCode, String ibsCustomerCode,
			String ibsBuildingCode, String ibsCustomerTypeCode,
			String ibsSaleDealerCode, String postalCode) {
		super();
		this.id = id;
		this.serviceHour = serviceHour;
		this.contactPerson = contactPerson;
		this.descriptionError = descriptionError;
		this.commentManagment = commentManagment;
		this.dateLastChange = dateLastChange;
		this.woCode = woCode;
		this.woStatus = woStatus;
		this.woReason = woReason;
		this.scheduleDate = scheduleDate;
		this.country = country;
		this.dealer = dealer;
		this.user = user;
		this.workOrderCSRStatus = workOrderCSRStatus;
		this.reSchedule = reSchedule;
		this.addressCode = addressCode;
		this.ibsCustomerCode = ibsCustomerCode;
		this.ibsBuildingCode = ibsBuildingCode;
		this.ibsCustomerTypeCode = ibsCustomerTypeCode;
		this.ibsSaleDealerCode = ibsSaleDealerCode;
		this.postalCode = postalCode;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceHour getServiceHour() {
		return serviceHour;
	}

	public void setServiceHour(ServiceHour serviceHour) {
		this.serviceHour = serviceHour;
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

	public WorkorderStatus getWoStatus() {
		return woStatus;
	}

	public void setWoStatus(WorkorderStatus woStatus) {
		this.woStatus = woStatus;
	}

	public WorkorderReason getWoReason() {
		return woReason;
	}

	public void setWoReason(WorkorderReason woReason) {
		this.woReason = woReason;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WorkorderCSRStatus getWorkOrderCSRStatus() {
		return workOrderCSRStatus;
	}

	public void setWorkOrderCSRStatus(WorkorderCSRStatus workOrderCSRStatus) {
		this.workOrderCSRStatus = workOrderCSRStatus;
	}

	public String getReSchedule() {
		return reSchedule;
	}

	public void setReSchedule(String reSchedule) {
		this.reSchedule = reSchedule;
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
	
}