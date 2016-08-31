package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * DocumentType entity. @author MyEclipse Persistence Tools
 */

public class DocumentClass implements java.io.Serializable,Auditable {

	private static final long serialVersionUID = 8020854575454180627L;
	private Long id;
	private String documentClassName;
	private String documentClassCode;
	private String description;

	// Constructors

	/** default constructor */
	public DocumentClass() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentClassName() {
		return documentClassName;
	}

	public void setDocumentClassName(String documentClassName) {
		this.documentClassName = documentClassName;
	}

	public String getDocumentClassCode() {
		return documentClassCode;
	}

	public void setDocumentClassCode(String documentClassCode) {
		this.documentClassCode = documentClassCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}