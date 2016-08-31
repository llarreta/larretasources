package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * RefIncStatus entity. @author MyEclipse Persistence Tools
 */

public class RefIncStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1313072070517093864L;
	private Long id;
	private String refIncStatusCode;
	private String refIncStatusName;

	// Constructors

	/** default constructor */
	public RefIncStatus() {
	}

	/** minimal constructor */
	public RefIncStatus(String refIncStatusCode, String refIncStatusName) {
		this.refIncStatusCode = refIncStatusCode;
		this.refIncStatusName = refIncStatusName;
	}
	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefIncStatusCode() {
		return this.refIncStatusCode;
	}

	public void setRefIncStatusCode(String refIncStatusCode) {
		this.refIncStatusCode = refIncStatusCode;
	}

	public String getRefIncStatusName() {
		return this.refIncStatusName;
	}

	public void setRefIncStatusName(String refIncStatusName) {
		this.refIncStatusName = refIncStatusName;
	}

	@Override
	public String toString() {
		return "RefIncStatus [id=" + id + ", refIncStatusCode="
				+ refIncStatusCode + "]";
	}
}