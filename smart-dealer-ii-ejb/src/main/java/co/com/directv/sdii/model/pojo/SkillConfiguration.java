package co.com.directv.sdii.model.pojo;

/**
 * SkillConfiguration entity. @author MyEclipse Persistence Tools
 */

public class SkillConfiguration implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1158884780821244702L;
	private Long id;
	private ServiceType serviceType;
	private SkillEvaluationType skillEvaluationType;
	private Skill skill;
	private SkillModeType skillModeType;
	private Country country;
	private Long skillOrder;
	private String status;

	// Constructors

	/** default constructor */
	public SkillConfiguration() {
	}

	/** minimal constructor */
	public SkillConfiguration(ServiceType serviceType,
			SkillEvaluationType skillEvaluationType, Skill skill,
			SkillModeType skillModeType, Country country) {
		this.serviceType = serviceType;
		this.skillEvaluationType = skillEvaluationType;
		this.skill = skill;
		this.skillModeType = skillModeType;
		this.country = country;
	}

	/** full constructor */
	public SkillConfiguration(ServiceType serviceType,
			SkillEvaluationType skillEvaluationType, Skill skill,
			SkillModeType skillModeType, Country country, Long skillOrder) {
		this.serviceType = serviceType;
		this.skillEvaluationType = skillEvaluationType;
		this.skill = skill;
		this.skillModeType = skillModeType;
		this.country = country;
		this.skillOrder = skillOrder;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceType getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public SkillEvaluationType getSkillEvaluationType() {
		return this.skillEvaluationType;
	}

	public void setSkillEvaluationType(SkillEvaluationType skillEvaluationType) {
		this.skillEvaluationType = skillEvaluationType;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public SkillModeType getSkillModeType() {
		return this.skillModeType;
	}

	public void setSkillModeType(SkillModeType skillModeType) {
		this.skillModeType = skillModeType;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Long getSkillOrder() {
		return this.skillOrder;
	}

	public void setSkillOrder(Long skillOrder) {
		this.skillOrder = skillOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}