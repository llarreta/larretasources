package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;


/**
 * WorkorderStatus entity. @author MyEclipse Persistence Tools
 */

public class WorkorderStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4843610133059735391L;

	private Long id;

        @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Ibs6Status ibs6Status;

        @BusinessRequired
	private String woStateName;

        @BusinessRequired
	private String woStateCode;

	// Constructors

	/** default constructor */
	public WorkorderStatus() {
	}

	/** minimal constructor */
	public WorkorderStatus(Ibs6Status ibs6Status, 
			String woStateName, String woStateCode) {
		this.ibs6Status = ibs6Status;
		this.woStateName = woStateName;
		this.woStateCode = woStateCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ibs6Status getIbs6Status() {
		return this.ibs6Status;
	}

	public void setIbs6Status(Ibs6Status ibs6Status) {
		this.ibs6Status = ibs6Status;
	}

	public String getWoStateName() {
		return this.woStateName;
	}

	public void setWoStateName(String woStateName) {
		this.woStateName = woStateName;
	}

	public String getWoStateCode() {
		return this.woStateCode;
	}

	public void setWoStateCode(String woStateCode) {
		this.woStateCode = woStateCode;
	}

	@Override
	public String toString() {
		return "WorkorderStatus [id=" + id + ", woStateCode=" + woStateCode
				+ "]";
	}

}