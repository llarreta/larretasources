package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * Rol entity. @author MyEclipse Persistence Tools
 */

public class Rol implements java.io.Serializable,Auditable {


	private static final long serialVersionUID = -3298521614575799994L;
	
	
	private Long id;
	private String rolCode;
	private String name;
	private String description;
	private RoleType roleType;

	// Constructors

	/** default constructor */
	public Rol() {
	}

	/** minimal constructor */
	public Rol(String name) {
		this.name = name;
	}

	/** full constructor */
	public Rol(String rolCode, String name, String description) {
		this.rolCode = rolCode;
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

	public String getRolCode() {
		return this.rolCode;
	}

	public void setRolCode(String rolCode) {
		this.rolCode = rolCode;
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

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", rolCode=" + rolCode + "]";
	}
}