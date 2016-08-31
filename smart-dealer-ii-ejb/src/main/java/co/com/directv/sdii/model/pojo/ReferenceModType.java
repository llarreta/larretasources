package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * ReferenceModType entity. @author MyEclipse Persistence Tools
 */

public class ReferenceModType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107586697139390219L;
	private Long id;
	private String refModTypeCode;
	private String refModTypeName;
	// Constructors

	/** default constructor */
	public ReferenceModType() {
	}

	/** minimal constructor */
	public ReferenceModType(String refModTypeCode, String refModTypeName) {
		this.refModTypeCode = refModTypeCode;
		this.refModTypeName = refModTypeName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefModTypeCode() {
		return this.refModTypeCode;
	}

	public void setRefModTypeCode(String refModTypeCode) {
		this.refModTypeCode = refModTypeCode;
	}

	public String getRefModTypeName() {
		return this.refModTypeName;
	}

	public void setRefModTypeName(String refModTypeName) {
		this.refModTypeName = refModTypeName;
	}

	@Override
	public String toString() {
		return "ReferenceModType [id=" + id + ", refModTypeCode="
				+ refModTypeCode + "]";
	}
}