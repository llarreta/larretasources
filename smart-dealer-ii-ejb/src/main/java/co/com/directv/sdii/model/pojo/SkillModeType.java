package co.com.directv.sdii.model.pojo;


/**
 * SkillModeType entity. @author MyEclipse Persistence Tools
 */

public class SkillModeType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5094149950563270198L;
	private Long id;
	private String code;
	private String name;
	private String description;

	// Constructors

	/** default constructor */
	public SkillModeType() {
	}

	/** minimal constructor */
	public SkillModeType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/** full constructor */
	public SkillModeType(String code, String name, String description) {
		this.code = code;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

}