package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * Rol entity. @author MyEclipse Persistence Tools
 */

public class RoleType implements java.io.Serializable,Auditable {


	private static final long serialVersionUID = -3298521614575799994L;
	
	
	private Long id;
	private String roleTypeCode;
	private String name;
	private String description;

	// Constructors

	/** default constructor */
	public RoleType() {
	}

	/** minimal constructor */
	public RoleType(String name) {
		this.name = name;
	}

	/** full constructor */
	public RoleType(String roleTypeCode, String name, String description) {
		this.roleTypeCode = roleTypeCode;
		this.name = name;
		this.description = description;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleTypeCode() {
		return this.roleTypeCode;
	}

	public void setRoleTypeCode(String rolCode) {
		this.roleTypeCode = rolCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "RoleType [id=" + id + ", roleTypeCode=" + roleTypeCode + "]";
	}

}