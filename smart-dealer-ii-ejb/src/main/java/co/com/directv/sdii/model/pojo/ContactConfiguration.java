package co.com.directv.sdii.model.pojo;


/**
 * ContactConfiguration entity. @author MyEclipse Persistence Tools
 */

public class ContactConfiguration implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -77859707165423534L;
	private Long id;
	private Long countryId;
	private Long woTypeId;
	private InputMethod inputMethod;
	private ContactStatus contactStatus;
	private WorkorderStatus workorderStatus;
	private Category category;

	// Constructors

	/** default constructor */
	public ContactConfiguration() {
	}

	/**
	 * 
	 * @param countryId
	 * @param inputMethod
	 * @param contactStatus
	 * @param workorderStatus
	 * @param category
	 */
	public ContactConfiguration(Long countryId, InputMethod inputMethod,
			ContactStatus contactStatus, WorkorderStatus workorderStatus,
			Category category) {
		this.countryId = countryId;
		this.inputMethod = inputMethod;
		this.contactStatus = contactStatus;
		this.workorderStatus = workorderStatus;
		this.category = category;
	}

	/**
	 * 
	 * @param countryId
	 * @param woTypeId
	 * @param inputMethod
	 * @param contactStatus
	 * @param workorderStatus
	 * @param category
	 */
	public ContactConfiguration(Long countryId, Long woTypeId,
			InputMethod inputMethod, ContactStatus contactStatus,
			WorkorderStatus workorderStatus, Category category) {
		this.countryId = countryId;
		this.woTypeId = woTypeId;
		this.inputMethod = inputMethod;
		this.contactStatus = contactStatus;
		this.workorderStatus = workorderStatus;
		this.category = category;
	}

	// Property accessors
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getWoTypeId() {
		return woTypeId;
	}

	public void setWoTypeId(Long woTypeId) {
		this.woTypeId = woTypeId;
	}

	public InputMethod getInputMethod() {
		return inputMethod;
	}

	public void setInputMethod(InputMethod inputMethod) {
		this.inputMethod = inputMethod;
	}

	public ContactStatus getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(ContactStatus contactStatus) {
		this.contactStatus = contactStatus;
	}

	public WorkorderStatus getWorkorderStatus() {
		return workorderStatus;
	}

	public void setWorkorderStatus(WorkorderStatus workorderStatus) {
		this.workorderStatus = workorderStatus;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ContactConfiguration [id=" + id + "]";
	}
	
	
}