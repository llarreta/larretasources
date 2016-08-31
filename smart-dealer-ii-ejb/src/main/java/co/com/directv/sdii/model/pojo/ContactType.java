package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * ContactType entity. @author MyEclipse Persistence Tools
 */

public class ContactType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3415898499997598707L;
	private Long id;
	private String contactTypeName;
	private String contactTypeCode;

	// Constructors

	/** default constructor */
	public ContactType() {
	}

	/**
	 * 
	 * @param contactTypeName
	 * @param contactTypeCode
	 */
	public ContactType(String contactTypeName, String contactTypeCode) {
		this.contactTypeName = contactTypeName;
		this.contactTypeCode = contactTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContactTypeName() {
		return this.contactTypeName;
	}

	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
	}

	public String getContactTypeCode() {
		return this.contactTypeCode;
	}

	public void setContactTypeCode(String contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}

	@Override
	public String toString() {
		return "ContactType [contactTypeCode=" + contactTypeCode + ", id=" + id
				+ "]";
	}

}