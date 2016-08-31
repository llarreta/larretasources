package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;



/**
 * Skill entity. @author MyEclipse Persistence Tools
 */

public class Skill  implements java.io.Serializable,Auditable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -6496252258790894251L;
	private Long id;
     private SkillType skillType;
     private String skillName;
     private String skillCode;
     private String skillDescription;


    // Constructors

    /** default constructor */
    public Skill() {
    }

	/** minimal constructor */
    public Skill(SkillType skillType, String skillName, String skillCode) {
        this.skillType = skillType;
        this.skillName = skillName;
        this.skillCode = skillCode;
    }
    
    /** full constructor */
    public Skill(SkillType skillType, String skillName, String skillCode, String skillDescription) {
        this.skillType = skillType;
        this.skillName = skillName;
        this.skillCode = skillCode;
        this.skillDescription = skillDescription;
    }
   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public SkillType getSkillType() {
        return this.skillType;
    }
    
    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }

    public String getSkillName() {
        return this.skillName;
    }
    
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillCode() {
        return this.skillCode;
    }
    
    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public String getSkillDescription() {
        return this.skillDescription;
    }
    
    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

	@Override
	public String toString() {
		return "Skill [id=" + id + ", skillCode=" + skillCode + "]";
	}
}