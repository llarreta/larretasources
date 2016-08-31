package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * EmployeeRol entity. @author MyEclipse Persistence Tools
 */

public class EmployeeRol implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1513671664483111915L;
	private Long id;
	private String rolName;
	private String rolCode;

	// Constructors

	/** default constructor */
	public EmployeeRol() {
	}

	/** minimal constructor */
	public EmployeeRol(String rolName, String rolCode) {
		this.rolName = rolName;
		this.rolCode = rolCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRolName() {
		return this.rolName;
	}

	public void setRolName(String rolName) {
		this.rolName = rolName;
	}

	public String getRolCode() {
		return this.rolCode;
	}

	public void setRolCode(String rolCode) {
		this.rolCode = rolCode;
	}

	@Override
	public String toString() {
		return "EmployeeRol [id=" + id + ", rolCode=" + rolCode + "]";
	}

}