package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * ItemStatus entity. @author MyEclipse Persistence Tools
 */

public class ItemStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6214833832019182693L;
	private Long id;
	private String statusCode;
	private String statusName;
	
	// Constructors

	/** default constructor */
	public ItemStatus() {
	}
	
	public ItemStatus(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public ItemStatus(String statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "ItemStatus [id=" + id + ", statusCode=" + statusCode + "]";
	}
	
}