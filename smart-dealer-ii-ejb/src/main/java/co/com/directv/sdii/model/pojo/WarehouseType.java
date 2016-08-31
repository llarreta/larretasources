package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * WarehouseType entity. @author MyEclipse Persistence Tools
 */

public class WarehouseType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2477000918750265698L;
	private Long id;
	private String whTypeCode;
	private String whTypeName;
	private String whTypeDesc;
	//gfandino 31/05/2011 se adiciona campo de estado según INV -  103 V2.2
	private String isActive;
	private String isVisible;
	
	private String isVirtual;

	// Constructors

	/** default constructor */
	public WarehouseType() {
	}

	/** minimal constructor */
	public WarehouseType(String whTypeCode, String whTypeName) {
		this.whTypeCode = whTypeCode;
		this.whTypeName = whTypeName;
	}

	/** full constructor */
	public WarehouseType(String whTypeCode, String whTypeName,
			String whTypeDesc) {
		this.whTypeCode = whTypeCode;
		this.whTypeName = whTypeName;
		this.whTypeDesc = whTypeDesc;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWhTypeCode() {
		return this.whTypeCode;
	}

	public void setWhTypeCode(String whTypeCode) {
		this.whTypeCode = whTypeCode;
	}

	public String getWhTypeName() {
		return this.whTypeName;
	}

	public void setWhTypeName(String whTypeName) {
		this.whTypeName = whTypeName;
	}

	public String getWhTypeDesc() {
		return this.whTypeDesc;
	}

	public void setWhTypeDesc(String whTypeDesc) {
		this.whTypeDesc = whTypeDesc;
	}
	
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public String getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(String isVirtual) {
		this.isVirtual = isVirtual;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "WarehouseType [id=" + id + ", whTypeCode=" + whTypeCode + "]";
	}
}