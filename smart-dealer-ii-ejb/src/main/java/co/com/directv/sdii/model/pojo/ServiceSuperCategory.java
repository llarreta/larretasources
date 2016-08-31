package co.com.directv.sdii.model.pojo;



/**
 * ServiceSuperCategory entity. @author MyEclipse Persistence Tools
 */

public class ServiceSuperCategory implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 304678145290279148L;
	private Long id;
	private String code;
	private String name;

	// Constructors

	/** default constructor */
	public ServiceSuperCategory() {
	}
	
	public ServiceSuperCategory(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public ServiceSuperCategory(String code, String name) {
		this.code = code;
		this.name = name;
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
}