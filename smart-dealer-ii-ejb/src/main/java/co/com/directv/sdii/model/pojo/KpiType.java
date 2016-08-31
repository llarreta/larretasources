package co.com.directv.sdii.model.pojo;


/**
 * KpiType entity. @author MyEclipse Persistence Tools
 */

public class KpiType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2594083676625326157L;
	private Long id;
	private String code;
	private String name;
	private String description;

	// Constructors

	/** default constructor */
	public KpiType() {
	}

	/** full constructor */
	public KpiType(String code, String name, String description) {
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