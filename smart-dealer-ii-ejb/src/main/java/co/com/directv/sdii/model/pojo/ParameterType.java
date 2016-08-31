package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ParameterType entity. @author MyEclipse Persistence Tools
 */

public class ParameterType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2267266024335149867L;
	private Long id;
	private String parameterTypeName;
	private String parameterTypeCode;

	// Constructors

	/** default constructor */
	public ParameterType() {
	}

	/** minimal constructor */
	public ParameterType(String parameterTypeName, String parameterTypeCode) {
		this.parameterTypeName = parameterTypeName;
		this.parameterTypeCode = parameterTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParameterTypeName() {
		return this.parameterTypeName;
	}

	public void setParameterTypeName(String parameterTypeName) {
		this.parameterTypeName = parameterTypeName;
	}

	public String getParameterTypeCode() {
		return this.parameterTypeCode;
	}

	public void setParameterTypeCode(String parameterTypeCode) {
		this.parameterTypeCode = parameterTypeCode;
	}

	@Override
	public String toString() {
		return "ParameterType [id=" + id + ", parameterTypeCode="
				+ parameterTypeCode + "]";
	}
}