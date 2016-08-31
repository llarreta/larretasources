package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;



/**
 * SkillType entity. @author MyEclipse Persistence Tools
 */

public class SkillType implements java.io.Serializable,Auditable{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8239577477337559651L;
	private Long id;
	private String skillTypeName;
	private String skillTypeCode;
	private Country country;

	// Constructors

	/** default constructor */
	public SkillType() {
	}

	/** minimal constructor */
	public SkillType(String skillTypeName, String skillTypeCode) {
		this.skillTypeName = skillTypeName;
		this.skillTypeCode = skillTypeCode;
	}
	
	/** minimal constructor */
	public SkillType(String skillTypeName, String skillTypeCode,Country country) {
		this.skillTypeName = skillTypeName;
		this.skillTypeCode = skillTypeCode;
		this.country = country;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkillTypeName() {
		return this.skillTypeName;
	}

	public void setSkillTypeName(String skillTypeName) {
		this.skillTypeName = skillTypeName;
	}

	public String getSkillTypeCode() {
		return this.skillTypeCode;
	}

	public void setSkillTypeCode(String skillTypeCode) {
		this.skillTypeCode = skillTypeCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "SkillType [id=" + id + ", skillTypeCode=" + skillTypeCode + "]";
	}
}