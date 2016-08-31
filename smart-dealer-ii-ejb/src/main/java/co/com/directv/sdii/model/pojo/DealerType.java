package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * DealerType entity. @author MyEclipse Persistence Tools
 */

public class DealerType implements java.io.Serializable, Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8559303126331688594L;
	private Long id;
	private String dealerTypeName;
	private String dealerTypeCode;
	private String isAlloc;

	// Constructors

	/** default constructor */
	public DealerType() {
	}

	/** minimal constructor */
	public DealerType(String dealerTypeName, String dealerTypeCode) {
		this.dealerTypeName = dealerTypeName;
		this.dealerTypeCode = dealerTypeCode;
	}
	
	/** full constructor */
	public DealerType(String dealerTypeName, String dealerTypeCode,String isAlloc) {
		this.dealerTypeName = dealerTypeName;
		this.dealerTypeCode = dealerTypeCode;
		this.isAlloc = isAlloc;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDealerTypeName() {
		return this.dealerTypeName;
	}

	public void setDealerTypeName(String dealerTypeName) {
		this.dealerTypeName = dealerTypeName;
	}

	public String getDealerTypeCode() {
		return this.dealerTypeCode;
	}

	public void setDealerTypeCode(String dealerTypeCode) {
		this.dealerTypeCode = dealerTypeCode;
	}

	public String getIsAlloc() {
		return isAlloc;
	}

	public void setIsAlloc(String isAlloc) {
		this.isAlloc = isAlloc;
	}

	@Override
	public String toString() {
		return "DealerType [dealerTypeCode=" + dealerTypeCode + ", id=" + id
				+ "]";
	}

}