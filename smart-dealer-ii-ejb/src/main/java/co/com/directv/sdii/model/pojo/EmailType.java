package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * EmailType entity. @author MyEclipse Persistence Tools
 */

public class EmailType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1364251617865718314L;
	private Long id;
	private String emailTypeCode;
	private String emailTypeName;
	private String description;
	private String emailTypeSubject;
	private String emailTypeText;
	private String isActive;
	private String emailHTMLText;

	// Constructors

	/** default constructor */
	public EmailType() {
	}

	/** full constructor */
	public EmailType(String emailTypeCode, String emailTypeName,
			String description, String emailTypeSubject, String emailTypeText,
			String isActive) {
		this.emailTypeCode = emailTypeCode;
		this.emailTypeName = emailTypeName;
		this.description = description;
		this.emailTypeSubject = emailTypeSubject;
		this.emailTypeText = emailTypeText;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailTypeCode() {
		return this.emailTypeCode;
	}

	public void setEmailTypeCode(String emailTypeCode) {
		this.emailTypeCode = emailTypeCode;
	}

	public String getEmailTypeName() {
		return this.emailTypeName;
	}

	public void setEmailTypeName(String emailTypeName) {
		this.emailTypeName = emailTypeName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmailTypeSubject() {
		return this.emailTypeSubject;
	}

	public void setEmailTypeSubject(String emailTypeSubject) {
		this.emailTypeSubject = emailTypeSubject;
	}

	public String getEmailTypeText() {
		return this.emailTypeText;
	}

	public void setEmailTypeText(String emailTypeText) {
		this.emailTypeText = emailTypeText;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setEmailHTMLText(String emailHTMLText) {
		this.emailHTMLText = emailHTMLText;
	}

	public String getEmailHTMLText() {
		return emailHTMLText;
	}

	@Override
	public String toString() {
		return "EmailType [emailTypeCode=" + emailTypeCode + ", id=" + id + "]";
	}

}