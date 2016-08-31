package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * MovementType entity. @author MyEclipse Persistence Tools
 */

public class MovementType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1268258333995499664L;
	private Long id;
	private String movTypeCode;
	private String movTypeName;
	private String movTypeDescription;
	private String movTypeStatus;
	private String movClass;
	//gfandino-02/06/2011 Se adiciona por INV106 v2.3
	private String movTypeChangeInventory;
	private String movTypeAuthorized;
	private String movTypeAutomatic;
	

	// Constructors

	/** default constructor */
	public MovementType() {
	}

	public MovementType(Long id) {
		this.id = id;
	}
	
	/** minimal constructor */
	public MovementType(String movTypeCode, String movTypeName,
			String movTypeStatus) {
		this.movTypeCode = movTypeCode;
		this.movTypeName = movTypeName;
		this.movTypeStatus = movTypeStatus;
	}

	/** full constructor */
	public MovementType(String movTypeCode, String movTypeName,
			String movTypeDescription, String movTypeStatus, String movClass) {
		this.movTypeCode = movTypeCode;
		this.movTypeName = movTypeName;
		this.movTypeDescription = movTypeDescription;
		this.movTypeStatus = movTypeStatus;
		this.movClass = movClass;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovTypeCode() {
		return this.movTypeCode;
	}

	public void setMovTypeCode(String movTypeCode) {
		this.movTypeCode = movTypeCode;
	}

	public String getMovTypeName() {
		return this.movTypeName;
	}

	public void setMovTypeName(String movTypeName) {
		this.movTypeName = movTypeName;
	}

	public String getMovTypeDescription() {
		return this.movTypeDescription;
	}

	public void setMovTypeDescription(String movTypeDescription) {
		this.movTypeDescription = movTypeDescription;
	}

	public String getMovTypeStatus() {
		return this.movTypeStatus;
	}

	public void setMovTypeStatus(String movTypeStatus) {
		this.movTypeStatus = movTypeStatus;
	}

	public String getMovClass() {
		return movClass;
	}

	public void setMovClass(String movClass) {
		this.movClass = movClass;
	}	
	
	public String getMovTypeChangeInventory() {
		return movTypeChangeInventory;
	}

	public void setMovTypeChangeInventory(String movTypeChangeInventory) {
		this.movTypeChangeInventory = movTypeChangeInventory;
	}

	public String getMovTypeAuthorized() {
		return movTypeAuthorized;
	}

	public void setMovTypeAuthorized(String movTypeAuthorized) {
		this.movTypeAuthorized = movTypeAuthorized;
	}

	public String getMovTypeAutomatic() {
		return movTypeAutomatic;
	}

	public void setMovTypeAutomatic(String movTypeAutomatic) {
		this.movTypeAutomatic = movTypeAutomatic;
	}

	@Override
	public String toString() {
		return "MovementType [id=" + id + ", movTypeCode=" + movTypeCode + "]";
	}
	
}