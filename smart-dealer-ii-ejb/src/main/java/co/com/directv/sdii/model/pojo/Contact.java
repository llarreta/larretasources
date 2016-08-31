package co.com.directv.sdii.model.pojo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Contact entity. @author MyEclipse Persistence Tools
 */

public class Contact implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3028227162964808526L;
	
	private Long id;
	//Se realiza la relación con WoStatusHistory
	/*@BusinessRequired
	private Long woStatusHistoryId;*/
	@BusinessRequired
	private WoStatusHistory woStatusHistoryId;
	private String contactCode;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private ContactConfiguration contactConfiguration;
	@BusinessRequired
	private String woCode;
	@BusinessRequired
	private String customerCode;
	private String shippingOrderCode;
	@BusinessRequired
	private String problemDescription;
	@BusinessRequired
	private Date creationDate;
	private Category category;
	
	// Constructors

	/** default constructor */
	public Contact() {
	}

	/**
	 * 
	 * @param woStatusHistoryId
	 * @param woCode
	 * @param problemDescription
	 * @param creationDate
	 */
	public Contact(WoStatusHistory woStatusHistoryId, String woCode,
			String problemDescription, Date creationDate) {
		this.woStatusHistoryId = woStatusHistoryId;
		this.woCode = woCode;
		this.problemDescription = problemDescription;
		this.creationDate = creationDate;
	}

	/**
	 * 
	 * @param woStatusHistoryId
	 * @param contactConfiguration
	 * @param woCode
	 * @param shippingOrderCode
	 * @param problemDescription
	 * @param creationDate
	 * @param contactCode
	 * @param customerCode
	 */
	public Contact(WoStatusHistory woStatusHistoryId,
			ContactConfiguration contactConfiguration, String woCode,
			String shippingOrderCode, String problemDescription,
			Date creationDate,String contactCode,String customerCode) {
		this.woStatusHistoryId = woStatusHistoryId;
		this.contactConfiguration = contactConfiguration;
		this.woCode = woCode;
		this.shippingOrderCode = shippingOrderCode;
		this.problemDescription = problemDescription;
		this.creationDate = creationDate;
		this.contactCode=contactCode;
		this.customerCode=customerCode;
	}
	
	/**
	 * 
	 * @param woStatusHistoryId
	 * @param contactConfiguration
	 * @param woCode
	 * @param shippingOrderCode
	 * @param problemDescription
	 * @param creationDate
	 * @param contactCode
	 */
	public Contact(WoStatusHistory woStatusHistoryId,
			ContactConfiguration contactConfiguration, String woCode,
			String shippingOrderCode, String problemDescription,
			Date creationDate,String contactCode) {
		this.woStatusHistoryId = woStatusHistoryId;
		this.contactConfiguration = contactConfiguration;
		this.woCode = woCode;
		this.shippingOrderCode = shippingOrderCode;
		this.problemDescription = problemDescription;
		this.creationDate = creationDate;
		this.contactCode=contactCode;
	}
	
	// Property accessors

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WoStatusHistory getWoStatusHistoryId() {
		return woStatusHistoryId;
	}

	public void setWoStatusHistoryId(WoStatusHistory woStatusHistoryId) {
		this.woStatusHistoryId = woStatusHistoryId;
	}

	public ContactConfiguration getContactConfiguration() {
		return contactConfiguration;
	}

	public void setContactConfiguration(ContactConfiguration contactConfiguration) {
		this.contactConfiguration = contactConfiguration;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public String getShippingOrderCode() {
		return shippingOrderCode;
	}

	public void setShippingOrderCode(String shippingOrderCode) {
		this.shippingOrderCode = shippingOrderCode;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getContactCode() {
		return contactCode;
	}

	public void setContactCode(String contactCode) {
		this.contactCode = contactCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Contact [contactCode=" + contactCode + ", id=" + id + "]";
	}	
	
}