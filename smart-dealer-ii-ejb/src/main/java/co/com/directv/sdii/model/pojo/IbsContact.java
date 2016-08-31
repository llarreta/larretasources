package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * ReferenceModification entity. @author cduarte
 */

public class IbsContact implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 456326954443337476L;
	private Long id;
	private String ibsContactCode;
	private Customer customer;
	private WorkOrder workOrder;
	private ContactStatus contactStatus;
	private IbsContactReason ibsContactReason;
	private Country country;
	private String description;
	private Date creationDate;
	private String characteristicValue;
	private String userName;
	private String actionTaken;

	// Constructors
	public IbsContact() {
		super();
	}

	public IbsContact(Long id, String ibsContactCode, Customer customer,
			WorkOrder workOrder, ContactStatus contactStatus,
			IbsContactReason ibsContactReason, Country country,
			String description, Date creationDate, String characteristicValue,
			String userName, String actionTaken) {
		super();
		this.id = id;
		this.ibsContactCode = ibsContactCode;
		this.customer = customer;
		this.workOrder = workOrder;
		this.contactStatus = contactStatus;
		this.ibsContactReason = ibsContactReason;
		this.country = country;
		this.description = description;
		this.creationDate = creationDate;
		this.characteristicValue = characteristicValue;
		this.userName = userName;
		this.actionTaken = actionTaken;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIbsContactCode() {
		return ibsContactCode;
	}

	public void setIbsContactCode(String ibsContactCode) {
		this.ibsContactCode = ibsContactCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public ContactStatus getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(ContactStatus contactStatus) {
		this.contactStatus = contactStatus;
	}

	public IbsContactReason getIbsContactReason() {
		return ibsContactReason;
	}

	public void setIbsContactReason(IbsContactReason ibsContactReason) {
		this.ibsContactReason = ibsContactReason;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCharacteristicValue() {
		return characteristicValue;
	}

	public void setCharacteristicValue(String characteristicValue) {
		this.characteristicValue = characteristicValue;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	
}