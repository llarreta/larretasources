package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * WoType entity. @author MyEclipse Persistence Tools
 */

public class WoType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3520943371579466063L;
	private Long id;
	private String woTypeCode;
	private String woTypeName;

	// Constructors

	/** default constructor */
	public WoType() {
	}

	/** minimal constructor */
	public WoType(String woTypeCode, String woTypeName) {
		this.woTypeCode = woTypeCode;
		this.woTypeName = woTypeName;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWoTypeCode() {
		return this.woTypeCode;
	}

	public void setWoTypeCode(String woTypeCode) {
		this.woTypeCode = woTypeCode;
	}

	public String getWoTypeName() {
		return this.woTypeName;
	}

	public void setWoTypeName(String woTypeName) {
		this.woTypeName = woTypeName;
	}

	@Override
	public String toString() {
		return "WoType [id=" + id + ", woTypeCode=" + woTypeCode + "]";
	}

	
}