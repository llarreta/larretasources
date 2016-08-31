package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * InconsistencyStatus entity. @author MyEclipse Persistence Tools
 */

public class InconsistencyStatus implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1924882544034737531L;
	// Fields

	private Long id;
	private String incStatusCode;
	private String incStatusName;

	// Constructors

	/** default constructor */
	public InconsistencyStatus() {
	}

	/** minimal constructor */
	public InconsistencyStatus(String incStatusCode, String incStatusName) {
		this.incStatusCode = incStatusCode;
		this.incStatusName = incStatusName;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIncStatusCode() {
		return this.incStatusCode;
	}

	public void setIncStatusCode(String incStatusCode) {
		this.incStatusCode = incStatusCode;
	}

	public String getIncStatusName() {
		return this.incStatusName;
	}

	public void setIncStatusName(String incStatusName) {
		this.incStatusName = incStatusName;
	}

	@Override
	public String toString() {
		return "InconsistencyStatus [id=" + id + ", incStatusCode="
				+ incStatusCode + "]";
	}

}