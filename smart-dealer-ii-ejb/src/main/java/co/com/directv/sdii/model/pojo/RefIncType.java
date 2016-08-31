package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * RefIncType entity. @author MyEclipse Persistence Tools
 */

public class RefIncType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5614243671669951733L;
	private Long id;
	private String refIncTypeCode;
	private String refIncTypeName;
	private String refIncTypeDescription;
	
	// Constructors

	/** default constructor */
	public RefIncType() {
	}

	/** minimal constructor */
	public RefIncType(String refIncTypeCode, String refIncTypeName) {
		this.refIncTypeCode = refIncTypeCode;
		this.refIncTypeName = refIncTypeName;
	}

	/** full constructor */
	public RefIncType(String refIncTypeCode, String refIncTypeName,
			String refIncTypeDescription) {
		this.refIncTypeCode = refIncTypeCode;
		this.refIncTypeName = refIncTypeName;
		this.refIncTypeDescription = refIncTypeDescription;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefIncTypeCode() {
		return this.refIncTypeCode;
	}

	public void setRefIncTypeCode(String refIncTypeCode) {
		this.refIncTypeCode = refIncTypeCode;
	}

	public String getRefIncTypeName() {
		return this.refIncTypeName;
	}

	public void setRefIncTypeName(String refIncTypeName) {
		this.refIncTypeName = refIncTypeName;
	}

	public String getRefIncTypeDescription() {
		return this.refIncTypeDescription;
	}

	public void setRefIncTypeDescription(String refIncTypeDescription) {
		this.refIncTypeDescription = refIncTypeDescription;
	}

	@Override
	public String toString() {
		return "RefIncType [id=" + id + ", refIncTypeCode=" + refIncTypeCode
				+ "]";
	}
}