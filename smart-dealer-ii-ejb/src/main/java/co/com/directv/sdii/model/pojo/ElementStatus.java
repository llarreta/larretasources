package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ElementStatus entity. @author MyEclipse Persistence Tools
 */

public class ElementStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1527434061734562107L;
	private Long id;
	private String elementStatusCode;
	private String elementStatusName;
	private String ibsCode;

	// Constructors

	/** default constructor */
	public ElementStatus() {
	}

	/** minimal constructor */
	public ElementStatus(String elementStatusCode, String elementStatusName) {
		this.elementStatusCode = elementStatusCode;
		this.elementStatusName = elementStatusName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getElementStatusCode() {
		return this.elementStatusCode;
	}

	public void setElementStatusCode(String elementStatusCode) {
		this.elementStatusCode = elementStatusCode;
	}

	public String getElementStatusName() {
		return this.elementStatusName;
	}

	public void setElementStatusName(String elementStatusName) {
		this.elementStatusName = elementStatusName;
	}
	

	public String getIbsCode() {
		return ibsCode;
	}

	public void setIbsCode(String ibsCode) {
		this.ibsCode = ibsCode;
	}

	@Override
	public String toString() {
		return "ElementStatus [elementStatusCode=" + elementStatusCode
				+ ", id=" + id + "]";
	}
	
}