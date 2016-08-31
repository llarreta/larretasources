package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * Ibs6Status entity. @author MyEclipse Persistence Tools
 */

public class Ibs6Status implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2512669233603731270L;
	private Long id;
	private String ibs6StateName;
	private String ibs6StateCode;

	// Constructors

	/** default constructor */
	public Ibs6Status() {
	}

	/** minimal constructor */
	public Ibs6Status(String ibs6StateName, String ibs6StateCode) {
		this.ibs6StateName = ibs6StateName;
		this.ibs6StateCode = ibs6StateCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIbs6StateName() {
		return this.ibs6StateName;
	}

	public void setIbs6StateName(String ibs6StateName) {
		this.ibs6StateName = ibs6StateName;
	}

	public String getIbs6StateCode() {
		return this.ibs6StateCode;
	}

	public void setIbs6StateCode(String ibs6StateCode) {
		this.ibs6StateCode = ibs6StateCode;
	}

	@Override
	public String toString() {
		return "Ibs6Status [ibs6StateCode=" + ibs6StateCode + ", id=" + id
				+ "]";
	}

}