package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * CustomerAgreements entity. 
 * @author waguileraS
 */

public class CustomerAgreementType implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -531835088836918512L;

	private Long id;
	
	private String customerAgreementCode;
	
	private String description;
	
	private String isMigrated;
	
	private Long ibsAdreementId;
	
	private Country country;
	
	
	
	// Constructors

	/** default constructor */
	public CustomerAgreementType() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerAgreementCode() {
		return customerAgreementCode;
	}

	public void setCustomerAgreementCode(String customerAgreementCode) {
		this.customerAgreementCode = customerAgreementCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsMigrated() {
		return isMigrated;
	}

	public void setIsMigrated(String isMigrated) {
		this.isMigrated = isMigrated;
	}

	public Long getIbsAdreementId() {
		return ibsAdreementId;
	}

	public void setIbsAdreementId(Long ibsAdreementId) {
		this.ibsAdreementId = ibsAdreementId;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "CustomerAgreements [id=" + id + ", description=" + description + "]";
	}
	
}