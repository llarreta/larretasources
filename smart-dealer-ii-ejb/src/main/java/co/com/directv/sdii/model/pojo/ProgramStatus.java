package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ProgramStatus entity. @author MyEclipse Persistence Tools
 */

public class ProgramStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1908339835561043320L;
	private Long id;
	private String programStatusName;
	private String programStatusCode;

	// Constructors

	/** default constructor */
	public ProgramStatus() {
	}

	/** minimal constructor */
	public ProgramStatus(String programStatusName, String programStatusCode) {
		this.programStatusName = programStatusName;
		this.programStatusCode = programStatusCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProgramStatusName() {
		return this.programStatusName;
	}

	public void setProgramStatusName(String programStatusName) {
		this.programStatusName = programStatusName;
	}

	public String getProgramStatusCode() {
		return this.programStatusCode;
	}

	public void setProgramStatusCode(String programStatusCode) {
		this.programStatusCode = programStatusCode;
	}

	@Override
	public String toString() {
		return "ProgramStatus [id=" + id + ", programStatusCode="
				+ programStatusCode + "]";
	}
}