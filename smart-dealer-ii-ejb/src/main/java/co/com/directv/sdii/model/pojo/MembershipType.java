package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * MembershipType entity. @author MyEclipse Persistence Tools
 */

public class MembershipType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -758361743650697672L;
	private Long id;
	private String membershipTypeName;
	private String membershipTypeCode;

	// Constructors

	/** default constructor */
	public MembershipType() {
	}

	/** minimal constructor */
	public MembershipType(String membershipTypeName, String membershipTypeCode) {
		this.membershipTypeName = membershipTypeName;
		this.membershipTypeCode = membershipTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMembershipTypeName() {
		return this.membershipTypeName;
	}

	public void setMembershipTypeName(String membershipTypeName) {
		this.membershipTypeName = membershipTypeName;
	}

	public String getMembershipTypeCode() {
		return this.membershipTypeCode;
	}

	public void setMembershipTypeCode(String membershipTypeCode) {
		this.membershipTypeCode = membershipTypeCode;
	}

	@Override
	public String toString() {
		return "MembershipType [id=" + id + ", membershipTypeCode="
				+ membershipTypeCode + "]";
	}

}