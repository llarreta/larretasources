package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * EmployeeDevices entity. @author MyEclipse Persistence Tools
 */

public class EmployeeDevices implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8483317779063943876L;
	private EmployeeDevicesId id;
	private String bbUser;
	private String bbPasswd;
	private String isCurrent;
	private String assignDate;

	// Constructors

	/** default constructor */
	public EmployeeDevices() {
	}

	/** full constructor */
	public EmployeeDevices(EmployeeDevicesId id, String bbUser,
			String bbPasswd, String isCurrent, String assignDate) {
		this.id = id;
		this.bbUser = bbUser;
		this.bbPasswd = bbPasswd;
		this.isCurrent = isCurrent;
		this.assignDate = assignDate;
	}

	// Property accessors

	public EmployeeDevicesId getId() {
		return this.id;
	}

	public void setId(EmployeeDevicesId id) {
		this.id = id;
	}

	public String getBbUser() {
		return this.bbUser;
	}

	public void setBbUser(String bbUser) {
		this.bbUser = bbUser;
	}

	public String getBbPasswd() {
		return this.bbPasswd;
	}

	public void setBbPasswd(String bbPasswd) {
		this.bbPasswd = bbPasswd;
	}

	public String getIsCurrent() {
		return this.isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getAssignDate() {
		return this.assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	@Override
	public String toString() {
		return "EmployeeDevices [id=" + id + "]";
	}

}