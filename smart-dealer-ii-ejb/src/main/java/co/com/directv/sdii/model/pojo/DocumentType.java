package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * DocumentType entity. @author MyEclipse Persistence Tools
 */

public class DocumentType implements java.io.Serializable,Auditable {

	private static final long serialVersionUID = 8020854575454180627L;
	private Long id;
	private String documentTypeName;
	private String documentTypeCode;
	private Country country;

	// Constructors

	/** default constructor */
	public DocumentType() {
	}

	
	
	public DocumentType(Long id, String documentTypeName,
			String documentTypeCode, Country country) {
		super();
		this.id = id;
		this.documentTypeName = documentTypeName;
		this.documentTypeCode = documentTypeCode;
		this.country = country;
	}



	/** minimal constructor */
	public DocumentType(String documentTypeName, String documentTypeCode) {
		this.documentTypeName = documentTypeName;
		this.documentTypeCode = documentTypeCode;
	}
	
	/** full constructor */
	public DocumentType(String documentTypeName, String documentTypeCode,Country country) {
		this.documentTypeName = documentTypeName;
		this.documentTypeCode = documentTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentTypeName() {
		return this.documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getDocumentTypeCode() {
		return this.documentTypeCode;
	}

	public void setDocumentTypeCode(String documentTypeCode) {
		this.documentTypeCode = documentTypeCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "DocumentType [documentTypeCode=" + documentTypeCode + ", id="
				+ id + "]";
	}

}